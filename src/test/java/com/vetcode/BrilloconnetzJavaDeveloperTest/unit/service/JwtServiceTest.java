package com.vetcode.BrilloconnetzJavaDeveloperTest.unit.service;

import com.vetcode.BrilloconnetzJavaDeveloperTest.dto.UserInputDto;
import com.vetcode.BrilloconnetzJavaDeveloperTest.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserInputDto mockUserInput;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "jwtSecret", "12345lopp");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000);
    }

    @Test
    public void testGenerateAndVerifyValidToken() {
        String username = "testuser";
        String password = "StrongPassword123!";
        String email = "testuser@example.com";
        Date dateOfBirth = new Date();
        Collection<? extends GrantedAuthority> authorities = null;

        Mockito.when(mockUserInput.getUsername()).thenReturn(username);
        Mockito.when(mockUserInput.getPassword()).thenReturn(password);
        Mockito.when(mockUserInput.getEmail()).thenReturn(email);
        Mockito.when(mockUserInput.getDateOfBirth()).thenReturn(dateOfBirth);

        String token = jwtService.generateTokenForValidatedUser(mockUserInput);
        String verificationResult = jwtService.verifyToken(token);

        assertEquals("verification pass", verificationResult);
    }

    @Test
    public void testVerifyInvalidToken() {
        String invalidToken = "invalid-token";

        String verificationResult = jwtService.verifyToken(invalidToken);

        assertEquals("verification fails", verificationResult);
    }

    // more test cases
}
