package com.alxsshv.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FoodIntakeDateValidator.class)
public @interface IsValidFoodIntakeDate {
    String message() default "Дата приёма пищи не указана или указана не верно";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
