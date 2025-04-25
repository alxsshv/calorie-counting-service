package com.alxsshv.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для применения логики проверки
 * на корректность указанной даты.
 * @author Шварёв Алексей
 * @version 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface IsValidDate {
    /**Сообщение, выводимое
     * при ошибке валидации
     * (когда дата не корректна).
     * @return возвращает строку с сообщением.*/
    String message() default "Дата не указана или указана не верно";

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
