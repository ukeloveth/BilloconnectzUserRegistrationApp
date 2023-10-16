package com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface CustomEmailValidation {
    String message() default "Email must be a valid email address"; // Default error message
    String notEmptyMessage() default "Email must not be empty"; // Custom error message for @NotEmpty
    String emailMessage() default "Email must be a valid email address"; // Custom error message for @Email
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
