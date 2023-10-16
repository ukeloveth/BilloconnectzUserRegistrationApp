package com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<CustomUsernameValidation, String> {
    private String notEmptyMessage;
    private String patternMessage;

    @Override
    public void initialize(CustomUsernameValidation constraintAnnotation) {
        notEmptyMessage = constraintAnnotation.notEmptyMessage();
        patternMessage = constraintAnnotation.patternMessage();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return false;
        }

        if (username.length() < 4) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(patternMessage).addConstraintViolation();
            return false;
        }

        return true;
    }
}

