package com.bankingapplication.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoanDTO {

	private Long id;

	@NotNull(message = "User cannot be null")
	private UserDTO user;

	@NotNull(message = "Amount cannot be null")
	@DecimalMin(value = "0.0", inclusive = true, message = "Amount must be a positive value")
	private BigDecimal amount;

	@NotNull(message = "Apply date cannot be null")
	private LocalDateTime applyDate;

	@NotNull(message = "Approval date cannot be null")
	private LocalDateTime approvalDate;

	@NotNull(message = "Disbursement date cannot be null")
	private LocalDateTime disbursementDate;

	@NotNull(message = "Balance cannot be null")
	@DecimalMin(value = "0.0", inclusive = true, message = "Balance must be a positive value")
	private BigDecimal balance;

	@NotNull(message = "Status cannot be null")
	@Size(min = 1, max = 20, message = "Status must be between 1 and 20 characters")
	@Pattern(regexp = "APPLIED|APPROVED|DISBURSED|REPAID", message = "Status must be one of 'APPLIED', 'APPROVED', 'DISBURSED', 'REPAID'")
	private String status;

	public LoanDTO() {
		super();
	}

	public LoanDTO(Long id, UserDTO user, BigDecimal amount, LocalDateTime applyDate, LocalDateTime approvalDate,
			LocalDateTime disbursementDate, BigDecimal balance, String status) {
		super();
		this.id = id;
		this.user = user;
		this.amount = amount;
		this.applyDate = applyDate;
		this.approvalDate = approvalDate;
		this.disbursementDate = disbursementDate;
		this.balance = balance;
		this.status = status;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(LocalDateTime applyDate) {
		this.applyDate = applyDate;
	}

	public LocalDateTime getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(LocalDateTime approvalDate) {
		this.approvalDate = approvalDate;
	}

	public LocalDateTime getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(LocalDateTime disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
