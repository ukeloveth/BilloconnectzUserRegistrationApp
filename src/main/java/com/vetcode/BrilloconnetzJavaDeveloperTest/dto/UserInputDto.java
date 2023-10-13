package com.vetcode.BrilloconnetzJavaDeveloperTest.dto;

import com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation.Age;
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
    @NotEmpty(message = "Username must not be empty")
    @Pattern(regexp = "^.{4,}$", message = "Username must be at least 4 characters long")
    private String username;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotEmpty(message = "Password must not be empty")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[!@#\\$%^&*])(?=.*[0-9]).{8,}$",
        message = "Password is not strong"
    )
    private String password;

    @Age
    @NotEmpty(message = "Date of Birth must not be empty")
    //@Min(value = 16, message = "Age should be 16 or greater")// use custom validation
    private Date dateOfBirth;
}
