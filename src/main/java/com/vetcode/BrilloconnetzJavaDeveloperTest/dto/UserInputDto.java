package com.vetcode.BrilloconnetzJavaDeveloperTest.dto;

import com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation.Age;
import com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation.CustomEmailValidation;
import com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation.CustomPasswordValidation;
import com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation.CustomUsernameValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDto {

    @CustomUsernameValidation(notEmptyMessage = "Username must not be empty", patternMessage = "Username must be at least 4 characters long")
    private String username;

    @CustomEmailValidation(notEmptyMessage = "Email must not be empty", emailMessage = "Email must be a valid email address")
    private String email;

    @CustomPasswordValidation(notEmptyMessage = "Password must not be empty")
    private String password;

    @Age
    @NotEmpty(message = "Date of Birth must not be empty")
    private Date dateOfBirth;
}
