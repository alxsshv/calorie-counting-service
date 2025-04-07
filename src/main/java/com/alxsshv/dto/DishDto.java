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
    private String title;
    /**Калорийность блюда в килокалориях.*/
    private double calorieContent;
    /**Содержание белков.*/
    private double proteinsAmount;
    /**Содержание жиров.*/
    private double fatsAmount;
    /**Содержание углеводов.*/
    private double carbohydratesAmount;
}
