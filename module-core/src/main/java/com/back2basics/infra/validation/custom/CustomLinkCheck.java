package com.back2basics.infra.validation.custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CustomLinkCheckValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomLinkCheck {
    String message() default "링크 목록에 중복이 있습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}