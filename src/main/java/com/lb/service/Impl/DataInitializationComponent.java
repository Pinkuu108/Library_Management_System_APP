package com.lb.service.Impl;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.lb.domain.UserRole;
import com.lb.entity.User;
import com.lb.genreRepository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initializeAdminUser();
    }

    //pkll
    private void initializeAdminUser() {
        String adminEmail = "Pinkunaprusty3231@gmail.com";
        String adminPassword = "9078372542@PINKU";
        if (userRepository.findByEmail(adminEmail) == null) {
            User user = User
                    .builder()
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .fullName("Pinkuna Prusty")
                    .role(UserRole.ROLE_ADMIN)
                    .build();

            User admin = userRepository.save(user);
        }

    }
}
