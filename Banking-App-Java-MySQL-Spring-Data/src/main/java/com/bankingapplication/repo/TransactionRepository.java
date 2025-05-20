package com.bankingapplication.repo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingapplication.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("SELECT t FROM Transaction t WHERE t.account.user.id = :userId ORDER BY t.date ASC")
	Page<Transaction> findTransactionsByUserId(@Param("userId") Long userId, Pageable pageable);

	List<Transaction> findByAccountUserIdAndDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

	List<Transaction> findByAccountUserIdAndAmountBetween(Long userId, BigDecimal minAmount, BigDecimal maxAmount);
}
