package com.lb.genreRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lb.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{

	Optional<PasswordResetToken> findByToken(String token);
}
