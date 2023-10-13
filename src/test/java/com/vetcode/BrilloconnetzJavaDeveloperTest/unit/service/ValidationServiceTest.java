package com.vetcode.BrilloconnetzJavaDeveloperTest.unit.service;

import com.vetcode.BrilloconnetzJavaDeveloperTest.dto.UserInputDto;
import com.vetcode.BrilloconnetzJavaDeveloperTest.jwt.JwtService;
import com.vetcode.BrilloconnetzJavaDeveloperTest.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ValidationServiceTest {

    @InjectMocks
    private ValidationService validationService;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void testValidateUserInput_ValidUserInput() {
//        UserInputDto userInput = new UserInputDto();
//        userInput.setUsername("validUser");
//        userInput.setEmail("valid@example.com");
//        userInput.setPassword("StrongPass1!");
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.YEAR, -20); // 20 years old
//        userInput.setDateOfBirth(calendar.getTime());
//
//        Map<String, String> validationResults = validationService.validateUserInput(userInput).join();
//
//        assertTrue(validationResults.isEmpty());
//    }

    @Test
    void testValidateUserInput_ValidUserInput() {
        UserInputDto userInput = new UserInputDto();
        userInput.setUsername("validUser");
        userInput.setEmail("valid@example.com");
        userInput.setPassword("StrongPass1!");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -20); // 20 years old
        userInput.setDateOfBirth(calendar.getTime());

        Map<String, String> validationResults = validationService.validateUserInput(userInput).join();

        if (!validationResults.isEmpty()) {
            for (Map.Entry<String, String> entry : validationResults.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }

        assertTrue(validationResults.isEmpty(), "Validation results should be empty");
        assertTrue(validationResults.containsKey("Username"), "Username validation error should be present");

    }



    @Test
    void testValidateUserInput_InvalidUsername() {
        UserInputDto userInput = new UserInputDto();
        userInput.setUsername("usr");

        Map<String, String> validationResults = validationService.validateUserInput(userInput).join();

        assertTrue(validationResults.containsKey("Username"));
        assertEquals("Username must be at least 4 characters long", validationResults.get("Username"));
    }

    @Test
    void testValidateUserInput_InvalidDateOfBirth() {
        UserInputDto userInput = new UserInputDto();
        userInput.setUsername("validUser");
        userInput.setEmail("valid@example.com");
        userInput.setPassword("StrongPass1!");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -15);
        userInput.setDateOfBirth(calendar.getTime());

        Map<String, String> validationResults = validationService.validateUserInput(userInput).join();

        assertTrue(validationResults.containsKey("Date of Birth"));
        assertEquals("Age should be 16 or greater", validationResults.get("Date of Birth"));
    }


}
