package com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<CustomEmailValidation, String> {
    private String notEmptyMessage;
    private String emailMessage;

    @Override
    public void initialize(CustomEmailValidation constraintAnnotation) {
        notEmptyMessage = constraintAnnotation.notEmptyMessage();
        emailMessage = constraintAnnotation.emailMessage();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(notEmptyMessage).addConstraintViolation();
            return false;
        }

        // Use Apache Commons EmailValidator to check if the email is valid
        EmailValidator emailValidator = EmailValidator.this;
        if (!emailValidator.isValid(email,context)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(emailMessage).addConstraintViolation();
            return false;
        }

        return true;
    }
}



