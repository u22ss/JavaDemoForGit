package com.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	User findByUsername(String username);
	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
}
