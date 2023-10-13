package com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class AgeConstraintValidator implements ConstraintValidator<Age, Date> {

    @Override
    public void initialize(Age age) {
    }

    @Override
    public boolean isValid(Date dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return false;
        }

        Date currentDate = new Date();
        long ageInMillis = currentDate.getTime() - dateOfBirth.getTime();
        int ageInYears = (int) (ageInMillis / (365 * 24 * 60 * 60 * 1000L));

        return ageInYears >= 16;
    }
}
