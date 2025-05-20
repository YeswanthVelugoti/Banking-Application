package com.bankingapplication.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankingapplication.dto.LoanDTO;
import com.bankingapplication.dto.UserDTO;
import com.bankingapplication.entity.Loan;
import com.bankingapplication.entity.User;
import com.bankingapplication.exception.NotFoundException;
import com.bankingapplication.repo.LoanRepository;
import com.bankingapplication.repo.UserRepository;
import com.bankingapplication.service.LoanService;

@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public LoanDTO applyForLoan(Long userId, LoanDTO loanDTO) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
		Loan loan = convertToEntity(loanDTO);
		loan.setUser(user);
		loan = loanRepository.save(loan);
		return convertToDTO(loan);
	}

	@Override
	public LoanDTO getLoanDetailsById(Long userId, Long loanId) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
		if (!loan.getUser().getId().equals(userId)) {
			throw new NotFoundException("Loan not found");
		}
		return convertToDTO(loan);
	}

	@Override
	@Transactional
	public LoanDTO updateLoanDetailsById(Long userId, Long loanId, LoanDTO loanDTO) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
		if (!loan.getUser().getId().equals(userId)) {
			throw new NotFoundException("Loan not found");
		}
		updateEntityFromDTO(loanDTO, loan);
		loan = loanRepository.save(loan);
		return convertToDTO(loan);
	}

	@Override
	@Transactional
	public Boolean deleteLoanApplicationById(Long userId, Long loanId) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
		if (!loan.getUser().getId().equals(userId)) {
			throw new NotFoundException("Loan not found");
		}
		loanRepository.delete(loan);
		return true;
	}

	@Override
	public List<LoanDTO> getLoansByUserId(Long userId) {
		List<Loan> loans = loanRepository.findLoansByUserId(userId);
		return loans.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Boolean repayLoan(Long userId, Long loanId, BigDecimal amount) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
		if (!loan.getUser().getId().equals(userId)) {
			throw new NotFoundException("Loan not found");
		}
		// Business logic for repayment
		loan.setBalance(loan.getBalance().subtract(amount));
		loanRepository.save(loan);
		return true;
	}

	@Override
	public String getLoanStatus(Long userId, Long loanId) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
		if (!loan.getUser().getId().equals(userId)) {
			throw new NotFoundException("Loan not found");
		}
		return loan.getStatus();
	}

	private LoanDTO convertToDTO(Loan loan) {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setId(loan.getId());
		loanDTO.setUser(convertUserToDTO(loan.getUser()));
		loanDTO.setAmount(loan.getAmount());
		loanDTO.setApplyDate(loan.getApplyDate());
		loanDTO.setApprovalDate(loan.getApprovalDate());
		loanDTO.setDisbursementDate(loan.getDisbursementDate());
		loanDTO.setBalance(loan.getBalance());
		loanDTO.setStatus(loan.getStatus());
		return loanDTO;
	}

	private Loan convertToEntity(LoanDTO loanDTO) {
		Loan loan = new Loan();
		loan.setId(loanDTO.getId());
		loan.setUser(convertDTOToUser(loanDTO.getUser()));
		loan.setAmount(loanDTO.getAmount());
		loan.setApplyDate(loanDTO.getApplyDate());
		loan.setApprovalDate(loanDTO.getApprovalDate());
		loan.setDisbursementDate(loanDTO.getDisbursementDate());
		loan.setBalance(loanDTO.getBalance());
		loan.setStatus(loanDTO.getStatus());
		return loan;
	}

	private void updateEntityFromDTO(LoanDTO loanDTO, Loan loan) {
		loan.setAmount(loanDTO.getAmount());
		loan.setApplyDate(loanDTO.getApplyDate());
		loan.setApprovalDate(loanDTO.getApprovalDate());
		loan.setDisbursementDate(loanDTO.getDisbursementDate());
		loan.setBalance(loanDTO.getBalance());
		loan.setStatus(loanDTO.getStatus());
	}

	private UserDTO convertUserToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		return userDTO;
	}

	private User convertDTOToUser(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		return user;
	}
}
