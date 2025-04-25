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
 * Аннотация может быть применена к
 * параметрам метода.
 * @author Шварёв Алексей
 * @version 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNotExistValidator.class)
public @interface UserNotExist {
    /**Сообщение, выводимое
     * при ошибке валидации
     * (когда пользователь существует).
     * @return возвращает строку с сообщением.*/
    String message() default "Пользователь с таким"
            + " адресом электронной почты уже существует";

    /**Группы ограничений.
     * @return возвращает массив классов.*/
    Class<?>[] groups() default { };

    /**Набор классов полезной нагрузки,
     * логика которых выполняется при
     * возникновении ошибки валидации.
     * @return возвращает массив классов,
     * расширяющих класс Payload.*/
    Class<? extends Payload>[] payload() default { };
}
