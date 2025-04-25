package com.alxsshv.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

/**
 * Класс, реализующий логику проверки
 * указанной даты на корректность.
 * @author Шварёв Алексей
 * @version 1.0
 */
public class DateValidator implements ConstraintValidator<IsValidDate, LocalDate> {

    /**Метод, реализующий логику проверки
     * указанной даты на корректность.
     * @param date - дата, проверяемая на корректность.
     * @param constraintValidatorContext - контекст валидации.
     * @return возвращает true если дата, указанная в параметре date,
     * наступила после даты, указанной в константе LocalDate.EPOCH
     * (1970-01-01) или возвращает false если параметр date равен
     * или наступил раньше LocalDate.EPOCH.*/
    @Override
    public boolean isValid(final LocalDate date,
                           final ConstraintValidatorContext constraintValidatorContext) {
        return date.isAfter(LocalDate.EPOCH);
    }
}
