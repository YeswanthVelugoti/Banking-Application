package com.bankingapplication.controller;

import java.math.BigDecimal;
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

import com.bankingapplication.dto.LoanDTO;
import com.bankingapplication.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{userId}/loans")
public class LoanController {

	@Autowired
	private LoanService loanService;

	@PostMapping
	public ResponseEntity<LoanDTO> applyForLoan(@PathVariable Long userId, @RequestBody @Valid LoanDTO loanDTO) {
		LoanDTO createdLoan = loanService.applyForLoan(userId, loanDTO);
		return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
	}

	@GetMapping("/{loanId}")
	public ResponseEntity<LoanDTO> getLoanDetailsById(@PathVariable Long userId, @PathVariable Long loanId) {
		LoanDTO loan = loanService.getLoanDetailsById(userId, loanId);
		return new ResponseEntity<>(loan, HttpStatus.OK);
	}

	@PutMapping("/{loanId}")
	public ResponseEntity<LoanDTO> updateLoanDetailsById(@PathVariable Long userId, @PathVariable Long loanId,
			@RequestBody @Valid LoanDTO loanDTO) {
		LoanDTO updatedLoan = loanService.updateLoanDetailsById(userId, loanId, loanDTO);
		return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
	}

	@DeleteMapping("/{loanId}")
	public ResponseEntity<Void> deleteLoanApplicationById(@PathVariable Long userId, @PathVariable Long loanId) {
		loanService.deleteLoanApplicationById(userId, loanId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<List<LoanDTO>> getLoansByUserId(@PathVariable Long userId) {
		List<LoanDTO> loans = loanService.getLoansByUserId(userId);
		return new ResponseEntity<>(loans, HttpStatus.OK);
	}

	@PostMapping("/{loanId}/repay")
	public ResponseEntity<Void> repayLoan(@PathVariable Long userId, @PathVariable Long loanId,
			@RequestParam BigDecimal amount) {
		loanService.repayLoan(userId, loanId, amount);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{loanId}/status")
	public ResponseEntity<String> getLoanStatus(@PathVariable Long userId, @PathVariable Long loanId) {
		String status = loanService.getLoanStatus(userId, loanId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
}
