package com.TravallingSystem.repo;

//src/main/java/com/example/demo/repository/UserRepository.java

import org.springframework.data.jpa.repository.JpaRepository;

import com.TravallingSystem.EntityClas.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	// TODO Auto-generated method stub

	public User findByEmail(String email);
	// Optional<User> findByEmail(String email);

}
