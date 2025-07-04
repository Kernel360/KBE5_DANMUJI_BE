package com.back2basics.infra.validator.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class CustomLinkCheckValidator implements ConstraintValidator<CustomLinkCheck, List<String>> {

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.stream().allMatch(link ->
            link.startsWith("http://") || link.startsWith("https://")
        );
    }
}