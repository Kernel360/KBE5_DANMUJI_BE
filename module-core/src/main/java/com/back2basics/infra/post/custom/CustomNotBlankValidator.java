package com.back2basics.infra.post.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomNotBlankValidator implements ConstraintValidator<CustomNotBlank, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        // 비어있거나 공백만 있는 경우는 허용하지 않도록
        return !value.isBlank();
    }
}
