package com.bankingapplication.service;

import java.math.BigDecimal;
import java.util.List;

import com.bankingapplication.dto.LoanDTO;

public interface LoanService {
	LoanDTO applyForLoan(Long userId, LoanDTO loanDTO);

	LoanDTO getLoanDetailsById(Long userId, Long loanId);

	LoanDTO updateLoanDetailsById(Long userId, Long loanId, LoanDTO loanDTO);

	Boolean deleteLoanApplicationById(Long userId, Long loanId);

	List<LoanDTO> getLoansByUserId(Long userId);

	Boolean repayLoan(Long userId, Long loanId, BigDecimal amount);

	String getLoanStatus(Long userId, Long loanId);
}
