package com.alxsshv.model;

import com.alxsshv.exception.DataProcessingException;
import lombok.Getter;

@Getter
public enum Sex {
    MAN ("Мужчина"),
    WOMAN ("Женщина");

    public static Sex valueOfPseudonym(String pseudonym) {
        for (Sex sex : Sex.values()) {
            if (sex.getPseudonym().equalsIgnoreCase(pseudonym)) {
                return sex;
            }
        }
        throw new DataProcessingException("Неверно указан пол пользователя." +
                " Пожалуйста укажите пол: мужчина или женщина");
    }

    Sex( final String pseudonym) {
        this.pseudonym = pseudonym;
    }

    private final String pseudonym;
}
