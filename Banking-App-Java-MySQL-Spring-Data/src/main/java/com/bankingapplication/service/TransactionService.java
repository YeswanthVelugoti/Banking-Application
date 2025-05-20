package com.bankingapplication.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.bankingapplication.dto.TransactionDTO;

public interface TransactionService {
	TransactionDTO addTransaction(Long userId, String accountId, TransactionDTO transactionDTO);

	List<TransactionDTO> getAllTransactionsForUser(Long userId, int page, int size);

	List<TransactionDTO> getUserTransactionsWithDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

	List<TransactionDTO> getUserTransactionsWithAmountRange(Long userId, BigDecimal minAmount, BigDecimal maxAmount);
}
