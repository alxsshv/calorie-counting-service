package com.alxsshv.service.validation;

/**
 * Класс для описания значений постоянных величин,
 * используемых при валидации блюд.
 * @author Шварёв Алексей
 * @version 1.0
 */
public final class DishViolationsConstants {
    /**Максимальное количество калорий,
     *  содержащихся в 100 граммах блюда
     *  в килокаллриях.*/
    public static final int UPPER_CALORIE_LIMIT = 1000;
    /**Минимальное количество калорий
     * на 100 грамм блюда в килокалриях.*/
    public static final int LOWER_CALORIE_LIMIT = 0;
    /**Максимальное количество белков
     * на 100 грамм продукта.*/
    public static final int UPPER_PROTEINS_LIMIT = 100;
    /**Минимальное количество белков
     * на 100 грамм продукта.*/
    public static final int LOWER_PROTEINS_LIMIT = 0;
    /**Максимальное количество жиров
     * на 100 грамм продукта.*/
    public static final int UPPER_FATS_LIMIT = 100;
    /**Минимальное количество жиров
     * на 100 грамм продукта.*/
    public static final int LOWER_FATS_LIMIT = 0;
    /**Максимальное количество углеводов
     * на 100 грамм продукта.*/
    public static final int UPPER_CARBOHYDRATES_LIMIT = 100;
    /**Минимальное количество углеводов
     * на 100 грамм продукта.*/
    public static final int LOWER_CARBOHYDRATES_LIMIT = 0;

}
