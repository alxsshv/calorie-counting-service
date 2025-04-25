package com.alxsshv.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для применения логики проверки
 * на наличие пользователя в БД.
 * Аннотация может быть применена к полям и
 * параметрам метода.
 * @author Шварёв Алексей
 * @version 1.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserIsPresentValidator.class)
public @interface UserIsPresent {
    /**Сообщение, выводимое
     * при ошибке валидации
     * (когда пользователь не существует).
     * @return возвращает строку с сообщением.*/
    String message() default "Пользователь с указанным идентификатором не найден";

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
