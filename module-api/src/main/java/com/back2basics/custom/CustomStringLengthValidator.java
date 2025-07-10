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
        // 해당 커스텀어노테이션만 붙이면 @NotBlank가 적용되도록 null, isEmpty 체크
        if (value == null || value.trim().isEmpty()) return false;
        int length = value.length();
        return length >= min && length <= max;
    }
}
