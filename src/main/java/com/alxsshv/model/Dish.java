package com.alxsshv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**Класс, описывает блюда, которые пользователь
 *  может употреблять в течение дня.
 * @author Шварёв Алексей
 * @version 1.0*/
@Entity
@Table(name = "dishes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    /**Идентификатор блюда.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**Название блюда.*/
    @Column(name = "title")
    private String title;
    /**Калорийность блюда в килокалориях
     * в 100 граммах продукта.*/
    @Column(name = "calorie_content")
    private double calorieContent;
    /**Содержание белков в 100 граммах продукта.*/
    @Column(name = "proteins_amount")
    private double proteinsAmount;
    /**Содержание жиров в 100 граммах продукта.*/
    @Column(name = "fats_amount")
    private double fatsAmount;
    /**Содержание углеводов в 100 граммах продукта.*/
    @Column(name = "carbohydrates_amount")
    private double carbohydratesAmount;

    /**Метод описывает логику сравнения объектов класса Dish.
     * @return возвращает false если объект с которым сравнивается
     * экземпляр класса Dish равен null, если это объект
     * другого класса, а так же если оба объекта относятся к классу Dish,
     * но имеется отличие в значениях свойств объектов.
     * Возвращает true если оба объекта не равны null, относятся к одному
     * и тому же классу Dish и имеют одинаковые значения
     * по каждому из свойств.*/
    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dish dish = (Dish) o;
        return id == dish.id && title.equals(dish.title)
                && calorieContent == dish.calorieContent
                && proteinsAmount == dish.proteinsAmount
                && fatsAmount == dish.fatsAmount
                && carbohydratesAmount == dish.carbohydratesAmount;
    }

    /**Метод описывает алгоритм вычисления hash-кода объекта класса Dish.
     * Для вычисления хеш-кода используются все свойства объекта класса Dish.
     * @return возвращает целочисленное значение хеш-кода экземпляра класса Dish
     * в диапазоне от -2147483648 до 2147483647*/
    @Override
    public int hashCode() {
        return Objects.hash(id, title, calorieContent,
                proteinsAmount, fatsAmount, carbohydratesAmount);
    }

    /**Метод преобразования экземпляра класса Dish в строку.
     * @return строковое представление экземпляра класса Dish,
     * содержащее наименование его полей и их значения*/
    @Override
    public String toString() {
        return "Dish{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", calorieContent=" + calorieContent
                + ", proteinsAmount=" + proteinsAmount
                + ", fatsAmount=" + fatsAmount
                + ", carbohydratesAmount=" + carbohydratesAmount
                + '}';
    }
}
