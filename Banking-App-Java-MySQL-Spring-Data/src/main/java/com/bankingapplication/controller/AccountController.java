package com.bankingapplication.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingapplication.dto.AccountDTO;
import com.bankingapplication.dto.TransactionDTO;
import com.bankingapplication.service.AccountService;

@RestController
@RequestMapping("/users/{userId}/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@PathVariable Long userId, @RequestBody AccountDTO accountDTO) {
		AccountDTO createdAccount = accountService.createAccount(userId, accountDTO);
		return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long userId, @PathVariable Long accountId) {
		AccountDTO account = accountService.getAccountById(userId, accountId);
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	@PutMapping("/{accountId}")
	public ResponseEntity<AccountDTO> updateAccountById(@PathVariable Long userId, @PathVariable Long accountId,
			@RequestBody AccountDTO accountDTO) {
		AccountDTO updatedAccount = accountService.updateAccountById(userId, accountId, accountDTO);
		return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
	}

	@DeleteMapping("/{accountId}")
	public ResponseEntity<Void> deleteAccountById(@PathVariable Long userId, @PathVariable Long accountId) {
		accountService.deleteAccountById(userId, accountId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAccountsByUserId(@PathVariable Long userId) {
		List<AccountDTO> accounts = accountService.getAccountsByUserId(userId);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping("/{accountId}/balance")
	public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable Long userId, @PathVariable Long accountId) {
		BigDecimal balance = accountService.getAccountBalance(userId, accountId);
		return new ResponseEntity<>(balance, HttpStatus.OK);
	}

	@PostMapping("/transfer")
	public ResponseEntity<Void> transferFunds(@PathVariable Long userId, @RequestParam Long fromAccountId,
			@RequestParam Long toAccountId, @RequestParam BigDecimal amount) {
		accountService.transferFunds(userId, fromAccountId, toAccountId, amount);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{accountId}/statements")
	public ResponseEntity<List<TransactionDTO>> getAccountStatements(@PathVariable Long userId,
			@PathVariable Long accountId, @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
		List<TransactionDTO> statements = accountService.getAccountStatements(userId, accountId, startDate, endDate);
		return new ResponseEntity<>(statements, HttpStatus.OK);
	}
}
