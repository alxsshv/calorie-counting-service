package com.alxsshv.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DishNotExistValidator.class)
public @interface DishNotExist {
    String message() default "Блюдо с таким"
            + " названием уже существует";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
