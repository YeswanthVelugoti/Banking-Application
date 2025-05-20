package com.bankingapplication.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankingapplication.dto.AccountDTO;
import com.bankingapplication.dto.TransactionDTO;
import com.bankingapplication.entity.Account;
import com.bankingapplication.entity.Transaction;
import com.bankingapplication.entity.User;
import com.bankingapplication.exception.NotFoundException;
import com.bankingapplication.repo.AccountRepository;
import com.bankingapplication.repo.TransactionRepository;
import com.bankingapplication.repo.UserRepository;
import com.bankingapplication.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public AccountDTO createAccount(Long userId, AccountDTO accountDTO) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

		Account account = new Account();
		account.setUser(user);
		account.setAccountNumber(accountDTO.getAccountNumber());
		account.setBalance(accountDTO.getBalance());

		if (accountDTO.getTransactions() != null) {
			account.setTransactions(
					accountDTO.getTransactions().stream().map(this::convertToEntity).collect(Collectors.toSet()));
		}

		account = accountRepository.save(account);

		return mapToDTO(account);
	}

	@Override
	public AccountDTO getAccountById(Long userId, Long accountId) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new NotFoundException("Account not found"));
		if (!account.getUser().getId().equals(userId)) {
			throw new NotFoundException("Account not found");
		}
		return mapToDTO(account);
	}

	@Override
	public AccountDTO updateAccountById(Long userId, Long accountId, AccountDTO accountDTO) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new NotFoundException("Account not found"));
		if (!account.getUser().getId().equals(userId)) {
			throw new NotFoundException("Account not found");
		}
		account.setAccountNumber(accountDTO.getAccountNumber());
		account.setBalance(accountDTO.getBalance());

		account = accountRepository.save(account);

		return mapToDTO(account);
	}

	@Override
	public Boolean deleteAccountById(Long userId, Long accountId) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new NotFoundException("Account not found"));
		if (!account.getUser().getId().equals(userId)) {
			throw new NotFoundException("Account not found");
		}
		accountRepository.delete(account);
		return true;
	}

	@Override
	public List<AccountDTO> getAccountsByUserId(Long userId) {
		List<Account> accounts = accountRepository.findAccountsByUserId(userId);
		return accounts.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public BigDecimal getAccountBalance(Long userId, Long accountId) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new NotFoundException("Account not found"));
		if (!account.getUser().getId().equals(userId)) {
			throw new NotFoundException("Account not found");
		}
		return account.getBalance();
	}

	@Transactional
	@Override
	public Boolean transferFunds(Long userId, Long fromAccountId, Long toAccountId, BigDecimal amount) {
		Account fromAccount = accountRepository.findById(fromAccountId)
				.orElseThrow(() -> new NotFoundException("Account not found"));
		Account toAccount = accountRepository.findById(toAccountId)
				.orElseThrow(() -> new NotFoundException("Account not found"));

		if (!fromAccount.getUser().getId().equals(userId)) {
			throw new NotFoundException("From account not found");
		}

		if (fromAccount.getBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException("Insufficient balance");
		}

		fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
		toAccount.setBalance(toAccount.getBalance().add(amount));

		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
		return true;
	}

	@Override
	public List<TransactionDTO> getAccountStatements(Long userId, Long accountId, LocalDateTime startDate,
			LocalDateTime endDate) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new NotFoundException("Account not found"));
		if (!account.getUser().getId().equals(userId)) {
			throw new NotFoundException("Account not found");
		}

		List<Transaction> transactions = transactionRepository.findByAccountUserIdAndDateBetween(userId, startDate,
				endDate);
		return transactions.stream().map(this::mapToTransactionDTO).collect(Collectors.toList());
	}

	private AccountDTO mapToDTO(Account account) {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setId(account.getId());
		accountDTO.setAccountNumber(account.getAccountNumber());
		accountDTO.setBalance(account.getBalance());
		if (account.getTransactions() != null) {
			accountDTO.setTransactions(
					account.getTransactions().stream().map(this::mapToTransactionDTO).collect(Collectors.toSet()));
		}
		return accountDTO;
	}

	private TransactionDTO mapToTransactionDTO(Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setId(transaction.getId());
		transactionDTO.setAccount(mapToDTO(transaction.getAccount()));
		transactionDTO.setAmount(transaction.getAmount());
		transactionDTO.setDate(transaction.getDate());
		transactionDTO.setType(transaction.getType());
		return transactionDTO;
	}

	private Transaction convertToEntity(TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction();
		transaction.setId(transactionDTO.getId());
		transaction.setAccount(accountRepository.findById(transactionDTO.getAccount().getId())
				.orElseThrow(() -> new NotFoundException("Account not found")));
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setDate(transactionDTO.getDate());
		transaction.setType(transactionDTO.getType());
		return transaction;
	}
}
