package com.bankingapplication.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TransactionDTO {

	private Long id;

	@NotNull(message = "Account cannot be null")
	private AccountDTO account;

	@NotNull(message = "Amount cannot be null")
	@DecimalMin(value = "0.0", inclusive = true, message = "Amount must be a positive value")
	private BigDecimal amount;

	@NotNull(message = "Date cannot be null")
	private LocalDateTime date;

	@NotNull(message = "Type cannot be null")
	@Size(min = 1, max = 6, message = "Type must be either 'DEBIT' or 'CREDIT'")
	@Pattern(regexp = "DEBIT|CREDIT", message = "Type must be either 'DEBIT' or 'CREDIT'")
	private String type;

	public TransactionDTO() {
		super();
	}

	public TransactionDTO(Long id, AccountDTO account, BigDecimal amount, LocalDateTime date, String type) {
		super();
		this.id = id;
		this.account = account;
		this.amount = amount;
		this.date = date;
		this.type = type;
	}

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AccountDTO getAccount() {
		return account;
	}

	public void setAccount(AccountDTO account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
