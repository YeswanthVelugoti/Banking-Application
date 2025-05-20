package com.bankingapplication.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.bankingapplication.dto.AccountDTO;
import com.bankingapplication.dto.TransactionDTO;

public interface AccountService {
	AccountDTO createAccount(Long userId, AccountDTO accountDTO);

	AccountDTO getAccountById(Long userId, Long accountId);

	AccountDTO updateAccountById(Long userId, Long accountId, AccountDTO accountDTO);

	Boolean deleteAccountById(Long userId, Long accountId);

	List<AccountDTO> getAccountsByUserId(Long userId);

	BigDecimal getAccountBalance(Long userId, Long accountId);

	Boolean transferFunds(Long userId, Long fromAccountId, Long toAccountId, BigDecimal amount);

	List<TransactionDTO> getAccountStatements(Long userId, Long accountId, LocalDateTime startDate,
			LocalDateTime endDate);
}
