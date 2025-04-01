package com.alxsshv.model;

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
    /**Рускоязычный псевдоним константы.*/
    private final String pseudonym;
}
