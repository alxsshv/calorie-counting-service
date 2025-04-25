package com.alxsshv.service.utils;

import com.alxsshv.model.User;
/**
 * Интерфейс, описывающий логику
 * вычисления дневной нормы калорий
 * по параметрам пользователя {@link User}.
 * @author Шварёв Алексей
 * @version 1.0
 */
public interface BmrCalculator {
    /**Метод рассчета дневной нормы каллорий.
     * @param user - пользователь, для которого
     * выполняется рассчет.
     * @return возвращает дневную норму каллорий
     * в формате double в киллокаллориях*/
    double calculate(User user);
}
