package com.alxsshv.service.validation;

/**
 * Класс для описания значений постоянных величин,
 * используемых при валидации пользователей.
 * @author Шварёв Алексей
 * @version 1.0
 */
public final class UserViolationsConstants {
    /**Минимальный рост пользователя в сантиметрах.*/
    public static final int MIN_USER_HEIGHT = 50;
    /**Максимальный рост пользователя в сантиметрах.*/
    public static final int MAX_USER_HEIGHT = 280;
    /**Минимальный вес пользователя в килограммах.*/
    public static final int MIN_USER_WEIGHT = 1;
    /**Максимальный вес пользователя в килограммах.*/
    public static final int MAX_USER_WEIGHT = 200;
    /**Минимальный возраст пользователя в годах.*/
    public static final int MIN_USER_AGE = 1;
    /**Максимальный возраст пользователя в годах.*/
    public static final int MAX_USER_AGE = 120;
    /**Минимальная длина имени пользователя.*/
    public static final int MIN_USER_NAME_LENGTH = 3;
    /**Максимальная длина имени пользователя.*/
    public static final int MAX_USER_NAME_LENGTH = 80;
    /**Шаблон для проверки адреса электронной почты.*/
    public static final String EMAIL_VALIDATION_TEMPLATE
            = "[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}";
}
