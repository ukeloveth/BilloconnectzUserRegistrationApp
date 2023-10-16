package com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<CustomPasswordValidation, String> {
    private String notEmptyMessage;

    @Override
    public void initialize(CustomPasswordValidation constraintAnnotation) {
        notEmptyMessage = constraintAnnotation.notEmptyMessage();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(notEmptyMessage).addConstraintViolation();
            return false;
        }

        // Define your custom password validation logic here
        // For example, check if the password matches the provided regular expression
        if (!password.matches("^(?=.*[A-Z])(?=.*[!@#\\$%^&*])(?=.*[0-9]).{8,}$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password is not strong").addConstraintViolation();
            return false;
        }

        return true;
    }
}
