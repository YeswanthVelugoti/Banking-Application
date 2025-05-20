package com.bankingapplication.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingapplication.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT a FROM Account a WHERE a.user.id = :userId")
	List<Account> findAccountsByUserId(@Param("userId") Long userId);

	List<Account> findByBalanceGreaterThan(BigDecimal balance);
}
