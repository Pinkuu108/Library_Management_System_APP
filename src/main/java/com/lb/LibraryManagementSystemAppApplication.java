package com.lb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LibraryManagementSystemAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemAppApplication.class, args);
	}

}
