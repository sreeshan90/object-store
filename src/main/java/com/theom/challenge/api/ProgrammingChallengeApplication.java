package com.theom.challenge.api;

import com.theom.challenge.controller.UserController;
import com.theom.challenge.service.DBService;
import com.theom.challenge.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {UserController.class, UserService.class, DBService.class})

public class ProgrammingChallengeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProgrammingChallengeApplication.class, args);
	}
}
