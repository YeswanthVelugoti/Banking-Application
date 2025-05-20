package com.bankingapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingapplication.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
	List<User> searchUsersByName(@Param("name") String name);

	List<User> findByEmailContainingIgnoreCase(String email);
}
