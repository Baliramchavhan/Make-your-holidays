package com.TravallingSystem.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TravallingSystem.EntityClas.PasswordResetToken;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    public PasswordResetToken findByToken(String token);
    void deleteByToken(String token);
}
