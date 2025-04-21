package com.alxsshv.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Аннотация для применения логики проверки
 * на отсутствие пользователя в БД.
 * Аннотация может быть применения к
 * параметрам метода.
 * @author Шварёв Алексей
 * @version 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNotExistValidator.class)
public @interface UserNotExist {
    String message() default "Пользователь с таким" +
            " адресом электронной почты уже существует";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
