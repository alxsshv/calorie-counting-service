package com.alxsshv.model;

import com.alxsshv.dto.converters.SqlDateToLocalDateConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**Класс описывает сущность "Отчет за день", содержит
 * описание полей отчета о питании пользователя.
 * @author Шварёв Алексей
 * @version 1.0*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class DayReport {
    /**Дата на которую сформирован отчет. */
    @Id
    @Convert(attributeName = "date", converter = SqlDateToLocalDateConverter.class)
    private LocalDate date;
    /**Количество приёмов пищи.*/
    @Column(name = "food_intakes_number")
    private long foodIntakesNumber;
    /**Количество белков за день
     * в граммах.*/
    @Column(name = "proteins_sum")
    private double proteinsSum;
    /**Количество жиров за день
     * в граммах.*/
    @Column(name = "fats_sum")
    private double fatsSum;
    /**Количество углеводов за день
     * в граммах.*/
    @Column(name = "carbohydrates_sum")
    private double carbohydratesSum;
    /**Количество калорий за день
     *  в килокалориях.*/
    @Column(name = "day_calorie_sum")
    private double dayCalorieSum;

    /**Метод для преобразования объекта класса
     *  DayReport в строковое представление.
     *  @return возвращает строку с перечнем
     *  полей и их значений для
     *  экземпляра класса DayReport.*/
    @Override
    public String toString() {
        return "DayReport{"
                + "date=" + date
                + ", foodIntakesNumber=" + foodIntakesNumber
                + ", proteinsSum=" + proteinsSum
                + ", fatsSum=" + fatsSum
                + ", carbohydratesSum=" + carbohydratesSum
                + ", dayCalorieSum=" + dayCalorieSum
                + '}';
    }
}
