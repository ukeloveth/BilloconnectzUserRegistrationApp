package com.vetcode.BrilloconnetzJavaDeveloperTest.service;

import com.vetcode.BrilloconnetzJavaDeveloperTest.dto.UserInputDto;
import com.vetcode.BrilloconnetzJavaDeveloperTest.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ValidationService {

    private final JwtService jwtService;


    public ValidationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Async
    public CompletableFuture<Map<String, String>> validateUserInput(UserInputDto userInput) {
        Map<String, String> validationResults = new HashMap<>();
        
        String username = userInput.getUsername();
        if (username == null || username.length() < 4) {
            validationResults.put("Username", "Username must be at least 4 characters long");
        }

        String email = userInput.getEmail();
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            validationResults.put("Email", "Email must be a valid email address");
        }

        String password = userInput.getPassword();
        if (password == null || !password.matches("^(?=.*[A-Z])(?=.*[!@#\\$%^&*])(?=.*[0-9]).{8,}$")) {
            validationResults.put("Password", "Password is not strong");
        }

        Date dateOfBirth = userInput.getDateOfBirth();
        if (dateOfBirth == null) {
            validationResults.put("Date of Birth", "Date of Birth must not be empty");
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateOfBirth);
            int age = Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
            if (age < 16) {
                validationResults.put("Date of Birth", "Age should be 16 or greater");
            }
        }

        if (validationResults.isEmpty()) {
            String jwtToken = jwtService.generateTokenForValidatedUser(userInput);
            validationResults.put("JWT Token", jwtToken);
        }
        
        return CompletableFuture.completedFuture(validationResults);
    }

    public String verifyToken(String token) {
        return jwtService.verifyToken(token);
    }
}
