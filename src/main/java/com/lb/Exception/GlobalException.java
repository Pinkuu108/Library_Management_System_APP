package com.lb.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lb.payload.response.ApiResponse;

@RestControllerAdvice
public class GlobalException {
	@ExceptionHandler(GenreException.class)
    public ResponseEntity<ApiResponse> handleGenreException(GenreException e)
    {
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    			.body(new ApiResponse(e.getMessage(),false));
    }
}
