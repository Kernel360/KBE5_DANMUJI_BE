package com.back2basics.infra.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CustomEnumCheckValidator implements ConstraintValidator<CustomEnumCheck, Enum<?>> {

    private Enum<?>[] enumConstants;

    @Override
    public void initialize(CustomEnumCheck constraintAnnotation) {
        this.enumConstants = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null 허용
        }

        return Arrays.stream(enumConstants)
            .anyMatch(e -> e.name().equals(value.name()));
    }
}