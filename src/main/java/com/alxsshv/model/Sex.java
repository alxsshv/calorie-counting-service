package com.alxsshv.model;

import com.alxsshv.exception.DataProcessingException;
import lombok.Getter;

/**Перечисление для выбора пола пользователя.
 * @author Шварёв Алексей
 * @version 1.0*/
@Getter
public enum Sex {
    /**Мужской пол.*/
    MAN("Мужчина"),
    /**Женский пол.*/
    WOMAN("Женщина");

    /**Метод позволяет осуществлять маппинг пола по псевдониму.
     * @param pseudonym - псевдоним пола (русскоязычное наименование пола)
     * @return возвращает пол человека, соответствующую указанному псевдониму.
     * @exception DataProcessingException выбрасывается если не найден пол,
     * соответствующий указанному псевдониму*/
    public static Sex valueOfPseudonym(final String pseudonym) {
        for (Sex sex : Sex.values()) {
            if (sex.getPseudonym().equalsIgnoreCase(pseudonym)) {
                return sex;
            }
        }
        throw new DataProcessingException("Неверно указан пол пользователя."
                + " Пожалуйста укажите пол: мужчина или женщина");
    }


    Sex(final String pseudonym) {
        this.pseudonym = pseudonym;
    }

    /**Русскоязычный псевдоним константы
     *  из перечисления.*/
    private final String pseudonym;
}
