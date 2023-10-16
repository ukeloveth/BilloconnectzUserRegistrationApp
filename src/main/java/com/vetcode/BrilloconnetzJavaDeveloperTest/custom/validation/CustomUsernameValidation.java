package com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface CustomUsernameValidation {
    String message() default "Invalid username"; // Default error message
    String notEmptyMessage() default "Username must not be empty"; // Custom error message for @NotEmpty
    String patternMessage() default "Username must be at least 4 characters long"; // Custom error message for @Pattern
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
