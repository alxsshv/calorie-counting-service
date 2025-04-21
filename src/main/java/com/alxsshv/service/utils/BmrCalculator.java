package com.alxsshv.service.utils;

import com.alxsshv.model.User;
/**
 * Интерфейс, описывающий метод
 * вычисления дневной нормы калорий
 * по параметрам пользователя {@link User}.
 * @author Шварёв Алексей
 * @version 1.0
 */
public interface BmrCalculator {
    double calculate(User user);
}
