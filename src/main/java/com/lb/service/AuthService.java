package com.lb.service;

import com.lb.Exception.UserException;
import com.lb.payload.dto.UserDTO;
import com.lb.payload.response.AuthResponse;

public interface AuthService {
	
	
	AuthResponse login(String username,String password) throws UserException;
	AuthResponse signup(UserDTO req) throws UserException;
	
	void createPasswordResetToken(String email) throws UserException;
	void resetPassword(String token,String newPassword) throws Exception;

}
