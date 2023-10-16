package com.vetcode.BrilloconnetzJavaDeveloperTest.custom.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface CustomPasswordValidation {
    String message() default "Password is not strong";
    String notEmptyMessage() default "Password must not be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
