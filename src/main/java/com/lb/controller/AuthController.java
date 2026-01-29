package com.lb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lb.Exception.UserException;
import com.lb.payload.dto.UserDTO;
import com.lb.payload.request.ForgotPasswordRequest;
import com.lb.payload.request.LoginRequest;
import com.lb.payload.request.ResetPasswordRequest;
import com.lb.payload.response.ApiResponse;
import com.lb.payload.response.AuthResponse;
import com.lb.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
     //=/auth/signup
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signupHandler(@RequestBody UserDTO req) throws UserException {

		AuthResponse res = authService.signup(req);
		return ResponseEntity.ok(res);

	}
	//=/auth/login
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody @Valid LoginRequest req) throws UserException {

		AuthResponse res = authService.login(req.getEmail(), req.getPassword());
		return ResponseEntity.ok(res);

	}

	@PostMapping("/forgot-password")
	public ResponseEntity<ApiResponse> forgotpassword(@RequestBody ForgotPasswordRequest request) throws UserException {

		authService.createPasswordResetToken(request.getEmail());
		ApiResponse res = new ApiResponse("A Reset Link was sent to your email.", true);
		return ResponseEntity.ok(res);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ApiResponse> resetpassword(@RequestBody ResetPasswordRequest request) throws Exception {
		authService.resetPassword(request.getToken(), request.getPassword());
		ApiResponse res = new ApiResponse("Password Reset Sucessful", true);
		return ResponseEntity.ok(res);
	}

}
