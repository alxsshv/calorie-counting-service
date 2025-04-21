package com.alxsshv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**Класс описывает объекта передачи данных (DTO)
 *  для сущности "Отчет за день"
 *  {@link com.alxsshv.model.DayReport}, содержит
 * описание полей отчета, передаваемых по REST-протоколу.
 * @author Шварёв Алексей
 * @version 1.0*/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DayReportDto {
    /**Дата на которую сформирован отчет. */
    private LocalDate date;
    /**Количество приёмов пищи.*/
    private long foodIntakesNumber;
    /**Количество белков, поглащённых за день
     * в граммах.*/
    private double proteinsSum;
    /**Количество жиров, поглащённых за день
     * в граммах.*/
    private double fatsSum;
    /**Количество углеводов, поглащённых за день
     * в граммах.*/
    private double carbohydratesSum;
    /**Количество калорий, поглащённых за день
     *  в килокалориях.*/
    private double dayCalorieSum;

    /**Метод для преобразования объекта класса
     *  DayReportDto в строковое представление.
     *  @return возвращает строку с перечнем
     *  полей и их значений для
     *  экземпляра класса DayReportDto.*/
    @Override
    public String toString() {
        return "DayReportDto{"
                + "date=" + date
                + ", foodIntakesNumber=" + foodIntakesNumber
                + ", proteinsSum=" + proteinsSum
                + ", fatsSum=" + fatsSum
                + ", carbohydratesSum=" + carbohydratesSum
                + ", dayCalorieSum=" + dayCalorieSum
                + '}';
    }
}
