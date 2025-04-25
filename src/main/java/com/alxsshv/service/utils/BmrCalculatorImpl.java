package com.alxsshv.service.utils;

import com.alxsshv.model.Sex;
import com.alxsshv.model.User;
import org.springframework.stereotype.Component;

/**Класс предназначен для рассчета дневной нормы каллорий пользователя.
 * Рассчет выполняется на основе уравнений Харриса-Бенедикта
 * (пересмотренных Миффлином и Сент-Джором)*/
@Component
public class BmrCalculatorImpl implements BmrCalculator {

    /**Метод рассчета дневной нормы каллорий.
     * @param user - пользователь, для которого
     * выполняется рассчет.
     * @return возвращает дневную норму каллорий
     * в формате double в киллокаллориях*/
    @Override
    public double calculate(final User user) {
        double weightMultiplier = 10.0d;
        double heightMultiplier = 6.25d;
        double ageMultiplier = 5.0d;
        double sexVariable = user.getSex().equals(Sex.MAN) ? 5 : -161;
        return (weightMultiplier * user.getWeight()) + (heightMultiplier * user.getHeight())
                - (ageMultiplier * user.getAge()) + sexVariable;
    }
}
