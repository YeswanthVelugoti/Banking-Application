package com.bankingapplication.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingapplication.dto.TransactionDTO;
import com.bankingapplication.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{userId}/accounts/{accountId}/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<TransactionDTO> addTransaction(@PathVariable Long userId, @PathVariable String accountId,
			@RequestBody @Valid TransactionDTO transactionDTO) {
		TransactionDTO createdTransaction = transactionService.addTransaction(userId, accountId, transactionDTO);
		return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<TransactionDTO>> getAllTransactionsForUser(@PathVariable Long userId,
			@RequestParam int page, @RequestParam int size) {
		List<TransactionDTO> transactions = transactionService.getAllTransactionsForUser(userId, page, size);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	@GetMapping("/date-range")
	public ResponseEntity<List<TransactionDTO>> getUserTransactionsWithDateRange(@PathVariable Long userId,
			@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
		List<TransactionDTO> transactions = transactionService.getUserTransactionsWithDateRange(userId, startDate,
				endDate);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	@GetMapping("/amount-range")
	public ResponseEntity<List<TransactionDTO>> getUserTransactionsWithAmountRange(@PathVariable Long userId,
			@RequestParam BigDecimal minAmount, @RequestParam BigDecimal maxAmount) {
		List<TransactionDTO> transactions = transactionService.getUserTransactionsWithAmountRange(userId, minAmount,
				maxAmount);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}
}
