package com.alxsshv.model;

import jakarta.persistence.*;

/**Класс, описывает блюда, которые пользователь
 *  может употреблять в течение дня.
 * @author Шварёв Алексей
 * @version 1.0
 *  */
@Entity
@Table(name = "dishes")
public class Dish {
    /**Идентификатор блюда.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**Название блюда.*/
    @Column(name = "title")
    private int title;
    /**Калорийность блюда в килокалориях.*/
    @Column(name = "calorie_content")
    private int calorieContent;
    /**Содержание белков.*/
    @Column(name = "proteins_amount")
    private int proteinsAmount;
    /**Содержание жиров.*/
    @Column(name = "fats_amount")
    private int fatsAmount;
    /**Содержание углеводов.*/
    @Column(name = "carbohydrates_amount")
    private int carbohydratesAmount;


}
