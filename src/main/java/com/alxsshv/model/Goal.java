package com.alxsshv.model;

import com.alxsshv.exception.DataProcessingException;
import lombok.Getter;

/**Список возможных целей использования приложения пользователем.
 * @author Шварёв Алексей
 * @version 1.0*/
@Getter
public enum Goal {
    /**Похудение.*/
    WEIGHT_LOSS("Похудение"),
    /**Поддержание формы.*/
    KEEPING_FIT("Поддержание"),
    /**Набор массы.*/
    WEIGHT_GAIN("Набор массы");

    Goal(final String pseudonym) {
        this.pseudonym = pseudonym;
    }

    /**Метод позволяет осуществлять поиск целей по псевдониму.
     * @param pseudonym - псевдоним цели (кириллическое наименование цели)
     * @return возвращает цель, соответствующую указанному псевдониму.
     * @exception IllegalArgumentException выбрасывается если не найдена цель,
     * соответствующая указанному псевдониму*/
    public static Goal valueOfPseudonym(final String pseudonym) {
        for (Goal goal : Goal.values()) {
            if (goal.getPseudonym().equalsIgnoreCase(pseudonym)) {
                return goal;
            }
        }
        throw new DataProcessingException("Указанная цель не доступна."
                + " Доступные цели: Похудение, Поддержание, Набор массы");
    }

    /**Русскоязычный псевдоним константы
     *  из перечисления.*/
    private final String pseudonym;
}
