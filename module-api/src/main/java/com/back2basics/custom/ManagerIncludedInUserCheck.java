package com.back2basics.custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = ManagerIncludedInUserCheckValidator.class)
public @interface ManagerIncludedInUserCheck {

    String message() default "개발사, 고객사 담당자는 프로젝트의 각 멤버에 포함되어야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
