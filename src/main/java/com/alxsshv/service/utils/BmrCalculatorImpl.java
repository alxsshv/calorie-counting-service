package com.alxsshv.service.utils;

import com.alxsshv.model.Sex;
import com.alxsshv.model.User;
import org.springframework.stereotype.Component;

/**Класс предназначен для рассчета дневной нормы каллорий пользователя.
 * Рассчет выполняется на основе уравнений Харриса-Беннета
 * (пересмотренных Миффлином и Сент-Джором)*/
@Component
public class BmrCalculatorImpl implements BmrCalculator {

    /**Метод рассчета дневной нормы каллорий.
     * @param user - пользователь, для которого
     * выполняется рассчет.
     * @return возвращает дневную норму каллорий
     * в формате double в киллокаллориях*/
    @Override
    public double calculate(User user) {
        int sexVariable = user.getSex().equals(Sex.MAN) ? 5 : -161;
        return (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * user.getAge()) + sexVariable;
    }
}
