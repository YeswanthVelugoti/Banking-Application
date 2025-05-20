package com.bankingapplication.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankingapplication.dto.AccountDTO;
import com.bankingapplication.dto.TransactionDTO;
import com.bankingapplication.dto.UserDTO;
import com.bankingapplication.entity.Account;
import com.bankingapplication.entity.Transaction;
import com.bankingapplication.entity.User;
import com.bankingapplication.exception.NotFoundException;
import com.bankingapplication.repo.AccountRepository;
import com.bankingapplication.repo.TransactionRepository;
import com.bankingapplication.repo.UserRepository;
import com.bankingapplication.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public TransactionDTO addTransaction(Long userId, String accountId, TransactionDTO transactionDTO) {
		System.out.println(Long.parseLong(accountId));
		Account account = accountRepository
				.findById(Long.parseLong(accountId))
				.orElseThrow(() -> new NotFoundException("Account not found"));

		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

		if (!account.getUser().getId().equals(userId)) {
			throw new NotFoundException("Account does not belong to user");
		}

		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setDate(transactionDTO.getDate());
		transaction.setType(transactionDTO.getType());

		Transaction savedTransaction = transactionRepository.save(transaction);
		return mapToDTO(savedTransaction);
	}

	@Override
	public List<TransactionDTO> getAllTransactionsForUser(Long userId, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("date").ascending());
		List<Transaction> transactions = transactionRepository.findTransactionsByUserId(userId, pageRequest)
				.getContent();
		return transactions.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public List<TransactionDTO> getUserTransactionsWithDateRange(Long userId, LocalDateTime startDate,
			LocalDateTime endDate) {
		List<Transaction> transactions = transactionRepository.findByAccountUserIdAndDateBetween(userId, startDate,
				endDate);
		return transactions.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public List<TransactionDTO> getUserTransactionsWithAmountRange(Long userId, BigDecimal minAmount,
			BigDecimal maxAmount) {
		List<Transaction> transactions = transactionRepository.findByAccountUserIdAndAmountBetween(userId, minAmount,
				maxAmount);
		return transactions.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	private TransactionDTO mapToDTO(Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setId(transaction.getId());
		transactionDTO.setAccount(mapToAccountDTO(transaction.getAccount()));
		transactionDTO.setAmount(transaction.getAmount());
		transactionDTO.setDate(transaction.getDate());
		transactionDTO.setType(transaction.getType());
		return transactionDTO;
	}

	private AccountDTO mapToAccountDTO(Account account) {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setId(account.getId());
		accountDTO.setAccountNumber(account.getAccountNumber());
		accountDTO.setBalance(account.getBalance());
		accountDTO.setUser(mapToUserDTO(account.getUser()));
		return accountDTO;
	}

	private UserDTO mapToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		return userDTO;
	}
}
