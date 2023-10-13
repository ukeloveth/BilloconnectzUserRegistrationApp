package com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeConstraintValidator.class)
public @interface Age {
    String message() default "Age should be 16 or greater";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
