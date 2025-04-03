package com.alxsshv.service.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNotExistValidator.class)
public @interface UserNotExist {
    String message() default "Пользователь с таким" +
            " адресом электронной почты уже существует";
}
