package com.alxsshv.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserIsPresentValidator.class)
public @interface UserIsPresent {
    String message() default "Пользователь с указанным идентификатором не найден";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
