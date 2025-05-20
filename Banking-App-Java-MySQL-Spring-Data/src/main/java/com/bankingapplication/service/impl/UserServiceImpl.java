package com.bankingapplication.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingapplication.dto.AccountDTO;
import com.bankingapplication.dto.LoanDTO;
import com.bankingapplication.dto.TransactionDTO;
import com.bankingapplication.dto.UserDTO;
import com.bankingapplication.entity.Account;
import com.bankingapplication.entity.Loan;
import com.bankingapplication.entity.Transaction;
import com.bankingapplication.entity.User;
import com.bankingapplication.exception.NotFoundException;
import com.bankingapplication.repo.UserRepository;
import com.bankingapplication.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
		return convertToDTO(user, false);
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = convertToEntity(userDTO);
		User savedUser = userRepository.save(user);
		return convertToDTO(savedUser, false);
	}

	@Override
	public UserDTO updateUserById(Long id, UserDTO userDTO) {
		User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
		existingUser.setName(userDTO.getName());
		existingUser.setEmail(userDTO.getEmail());
		existingUser.setPassword(userDTO.getPassword());
		User updatedUser = userRepository.save(existingUser);
		return convertToDTO(updatedUser, false);
	}

	@Override
	public Boolean deleteUserById(Long id) {
		User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
		userRepository.delete(existingUser);
		return true;
	}

	@Override
	public List<UserDTO> searchUsersByName(String name) {
		List<User> users = userRepository.searchUsersByName(name);
		return users.stream().map(user -> convertToDTO(user, false)).collect(Collectors.toList());
	}

	private UserDTO convertToDTO(User user, boolean includeAccountsAndLoans) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());

		if (includeAccountsAndLoans) {
			if (user.getAccounts() != null) {
				userDTO.setAccounts(user.getAccounts().stream().map(account -> convertAccountToDTO(account, false))
						.collect(Collectors.toSet()));
			}

			if (user.getLoans() != null) {
				userDTO.setLoans(user.getLoans().stream().map(loan -> convertLoanToDTO(loan, false))
						.collect(Collectors.toSet()));
			}
		}

		return userDTO;
	}

	private User convertToEntity(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());

		if (userDTO.getAccounts() != null) {
			user.setAccounts(
					userDTO.getAccounts().stream().map(this::convertAccountToEntity).collect(Collectors.toSet()));
		}

		if (userDTO.getLoans() != null) {
			user.setLoans(userDTO.getLoans().stream().map(this::convertLoanToEntity).collect(Collectors.toSet()));
		}

		return user;
	}

	private AccountDTO convertAccountToDTO(Account account, boolean includeUser) {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setId(account.getId());
		if (includeUser) {
			accountDTO.setUser(convertToDTO(account.getUser(), false));
		}
		accountDTO.setAccountNumber(account.getAccountNumber());
		accountDTO.setBalance(account.getBalance());

		if (account.getTransactions() != null) {
			accountDTO.setTransactions(
					account.getTransactions().stream().map(this::convertTransactionToDTO).collect(Collectors.toSet()));
		}

		return accountDTO;
	}

	private Account convertAccountToEntity(AccountDTO accountDTO) {
		Account account = new Account();
		account.setId(accountDTO.getId());
		account.setUser(convertToEntity(accountDTO.getUser()));
		account.setAccountNumber(accountDTO.getAccountNumber());
		account.setBalance(accountDTO.getBalance());

		if (accountDTO.getTransactions() != null) {
			account.setTransactions(accountDTO.getTransactions().stream().map(this::convertTransactionToEntity)
					.collect(Collectors.toSet()));
		}

		return account;
	}

	private LoanDTO convertLoanToDTO(Loan loan, boolean includeUser) {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setId(loan.getId());
		if (includeUser) {
			loanDTO.setUser(convertToDTO(loan.getUser(), false));
		}
		loanDTO.setAmount(loan.getAmount());
		loanDTO.setApplyDate(loan.getApplyDate());
		loanDTO.setApprovalDate(loan.getApprovalDate());
		loanDTO.setDisbursementDate(loan.getDisbursementDate());
		loanDTO.setBalance(loan.getBalance());
		loanDTO.setStatus(loan.getStatus());

		return loanDTO;
	}

	private Loan convertLoanToEntity(LoanDTO loanDTO) {
		Loan loan = new Loan();
		loan.setId(loanDTO.getId());
		loan.setUser(convertToEntity(loanDTO.getUser()));
		loan.setAmount(loanDTO.getAmount());
		loan.setApplyDate(loanDTO.getApplyDate());
		loan.setApprovalDate(loanDTO.getApprovalDate());
		loan.setDisbursementDate(loanDTO.getDisbursementDate());
		loan.setBalance(loanDTO.getBalance());
		loan.setStatus(loanDTO.getStatus());

		return loan;
	}

	private TransactionDTO convertTransactionToDTO(Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setId(transaction.getId());
		transactionDTO.setAccount(convertAccountToDTO(transaction.getAccount(), false));
		transactionDTO.setAmount(transaction.getAmount());
		transactionDTO.setDate(transaction.getDate());
		transactionDTO.setType(transaction.getType());

		return transactionDTO;
	}

	private Transaction convertTransactionToEntity(TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction();
		transaction.setId(transactionDTO.getId());
		transaction.setAccount(convertAccountToEntity(transactionDTO.getAccount()));
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setDate(transactionDTO.getDate());
		transaction.setType(transactionDTO.getType());

		return transaction;
	}
}
