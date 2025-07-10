package com.back2basics.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomStringLengthValidator implements ConstraintValidator<CustomStringLengthCheck, String> {

    private int min;
    private int max;

    @Override
    public void initialize(CustomStringLengthCheck constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        int length = value.length();
        return length >= min && length <= max;
    }
}
