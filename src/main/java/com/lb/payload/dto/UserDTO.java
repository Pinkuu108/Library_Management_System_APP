package com.lb.payload.dto;

import java.time.LocalDateTime;

import com.lb.domain.UserRole;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private Long id;

	@NotNull(message = "Email is Required")
	private String email;
	
	@NotNull(message = "Password is required")
	private String password;
	
	private String phone;
	
	@NotNull(message = "Full name is Required")
	private String fullName;
	
	private UserRole role;
	
	private String userName;
	
	private LocalDateTime lastLogin;
	

}
