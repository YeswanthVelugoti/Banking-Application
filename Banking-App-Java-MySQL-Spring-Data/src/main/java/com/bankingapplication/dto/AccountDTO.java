package com.bankingapplication.dto;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AccountDTO {

	private Long id;

	@NotNull(message = "User cannot be null")
	private UserDTO user;

	@NotNull(message = "Account number cannot be null")
	@Size(min = 10, max = 10, message = "Account number must be exactly 10 characters long")
	@Pattern(regexp = "\\d{10}", message = "Account number must be numeric")
	private String accountNumber;

	@NotNull(message = "Balance cannot be null")
	@DecimalMin(value = "0.0", inclusive = true, message = "Balance must be a positive value")
	private BigDecimal balance;

	private Set<TransactionDTO> transactions;

	public AccountDTO() {
		super();
	}

	public AccountDTO(Long id, UserDTO user, String accountNumber, BigDecimal balance,
			Set<TransactionDTO> transactions) {
		super();
		this.id = id;
		this.user = user;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.transactions = transactions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Set<TransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<TransactionDTO> transactions) {
		this.transactions = transactions;
	}
}
