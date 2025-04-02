package com.alxsshv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для {@link com.alxsshv.model.Dish}.
 * @author Шварёв Алексей
 * @version 1.0
 */
@AllArgsConstructor
@Getter
@Setter
public class DishDto {
    /**Идентификатор блюда.*/
    private int id;
    /**Название блюда.*/
    private int title;
    /**Калорийность блюда в килокалориях.*/
    private int calorieContent;
    /**Содержание белков.*/
    private int proteinsAmount;
    /**Содержание жиров.*/
    private int fatsAmount;
    /**Содержание углеводов.*/
    private int carbohydratesAmount;
}
