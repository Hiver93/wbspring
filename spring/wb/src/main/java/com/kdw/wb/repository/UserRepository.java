package com.kdw.wb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdw.wb.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	public Optional<User> findByUsernameAndPassword(String username, String password);
	public boolean existsByUsername(String username);
}
