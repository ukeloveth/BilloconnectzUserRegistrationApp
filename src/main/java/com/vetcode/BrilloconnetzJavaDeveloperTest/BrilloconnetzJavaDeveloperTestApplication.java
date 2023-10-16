package com.vetcode.BrilloconnetzJavaDeveloperTest;

import com.vetcode.BrilloconnetzJavaDeveloperTest.dto.UserInputDto;
import com.vetcode.BrilloconnetzJavaDeveloperTest.jwt.JwtService;
import com.vetcode.BrilloconnetzJavaDeveloperTest.service.ValidationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = {"com.vetcode.BrilloconnetzJavaDeveloperTest.service","com.vetcode.BrilloconnetzJavaDeveloperTest.jwt"})
public class BrilloconnetzJavaDeveloperTestApplication {


	private final ValidationService validationService;
	private final JwtService jwtService;

	public BrilloconnetzJavaDeveloperTestApplication(ValidationService validationService, JwtService jwtService) {
		this.validationService = validationService;
		this.jwtService = jwtService;
	}


	public static void main(String[] args) {
		SpringApplication.run(BrilloconnetzJavaDeveloperTestApplication.class, args);

		ValidationService validationService = new ValidationService(new JwtService());
		JwtService jwtService = new JwtService();
		BrilloconnetzJavaDeveloperTestApplication app = new BrilloconnetzJavaDeveloperTestApplication(validationService, jwtService);

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter username: ");
			String username = scanner.nextLine();

			System.out.print("Enter email: ");
			String email = scanner.nextLine();

			System.out.print("Enter password: ");
			String password = scanner.nextLine();

			System.out.print("Enter date of birth (yyyy-MM-dd): ");
			String dateOfBirthStr = scanner.nextLine();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dateOfBirth = dateFormat.parse(dateOfBirthStr);

			UserInputDto userInput = app.createUserInput(username, email, password, dateOfBirth);

			Map<String, String> validationResult = app.validateUserInput(userInput);

			if (validationResult.isEmpty()) {
				String jwtToken = app.jwtService.generateTokenForValidatedUser(userInput);
				System.out.println("JWT Token for User: " + userInput.getUsername() + " - " + jwtToken);

				String verificationResult = app.jwtService.verifyToken(jwtToken);
				System.out.println("JWT Token Verification Result: " + verificationResult);
			} else {
				System.out.println("Validation errors for User: " + userInput.getUsername());
				for (Map.Entry<String, String> entry : validationResult.entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				}
			}
		} catch (ParseException e) {
			System.out.println("Invalid date format. Please use yyyy-MM-dd.");
		}
	}

	public UserInputDto createUserInput(String username, String email, String password, Date dateOfBirth) {
		UserInputDto userInput = new UserInputDto();
		userInput.setUsername(username);
		userInput.setEmail(email);
		userInput.setPassword(password);
		userInput.setDateOfBirth(dateOfBirth);
		return userInput;
	}

	public Map<String, String> validateUserInput(UserInputDto userInput) {
		CompletableFuture<Map<String, String>> validationResultFuture = validationService.validateUserInput(userInput);
		return validationResultFuture.join();
	}
}

