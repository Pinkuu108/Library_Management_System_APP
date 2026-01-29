package com.lb.genreRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lb.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	 User findByEmail(String email);
}
