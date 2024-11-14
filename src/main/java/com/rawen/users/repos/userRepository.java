package com.rawen.users.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rawen.users.entities.User;
	public interface userRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	Optional<User> findByEmail(String email); 

	}

