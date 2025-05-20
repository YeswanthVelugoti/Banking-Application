package com.bankingapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingapplication.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

	@Query("SELECT l FROM Loan l WHERE l.user.id = :userId")
	List<Loan> findLoansByUserId(@Param("userId") Long userId);

	List<Loan> findByStatus(String status);
}
