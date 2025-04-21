package com.alxsshv.dto;

import com.alxsshv.service.validation.DishViolationsConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для {@link com.alxsshv.model.Dish}.
 * @author Шварёв Алексей
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishDto {

    /**Идентификатор блюда.*/
    private long id;

    /**Название блюда.*/
    @NotEmpty(message = "Название блюда не может быть пустым")
    @NotBlank(message = "Название блюда не может быть пустым")
    private String title;

    /**Калорийность блюда в килокалориях на 100 грамм продукта.*/
    @Max(value = DishViolationsConstants.UPPER_CALORIE_LIMIT,
            message = "Слишком большое количество килокалорий "
                    + "в 100 граммах продукта")
    @Min(value = DishViolationsConstants.LOWER_CALORIE_LIMIT,
            message = "Значение калорийности не может быть отрицательным")
    private double calorieContent;

    /**Содержание белков на 100 грамм продукта.*/
    @Max(value = DishViolationsConstants.UPPER_PROTEINS_LIMIT,
            message = "В 100 граммах продукта"
                    + " не может быть более 100 граммов белков")
    @Min(value = DishViolationsConstants.LOWER_PROTEINS_LIMIT,
            message = "Значение содержания белков"
                    + " не может быть отрицательным")
    private double proteinsAmount;

    /**Содержание жиров на 100 грамм продукта.*/
    @Max(value = DishViolationsConstants.UPPER_FATS_LIMIT,
            message = "В 100 граммах продукта"
                    + " не может быть более 100 граммов жиров")
    @Min(value = DishViolationsConstants.LOWER_FATS_LIMIT,
            message = "Значение содержания жиров"
                    + " не может быть отрицательным")
    private double fatsAmount;

    /**Содержание углеводов на 100 грамм продукта.*/
    @Max(value = DishViolationsConstants.UPPER_CARBOHYDRATES_LIMIT,
            message = "В 100 граммах продукта"
                    + " не может быть более 100 граммов углеводов")
    @Min(value = DishViolationsConstants.LOWER_CARBOHYDRATES_LIMIT,
            message = "Значение содержания углеводов"
                    + " не может быть отрицательным")
    private double carbohydratesAmount;

    /**Метод преобразования экземпляра класса DishDto в строку.
     * @return строковое представление экземпляра класса DishDto,
     * содержащее наименование его полей и их значения*/
    @Override
    public String toString() {
        return "DishDto{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", calorieContent=" + calorieContent
                + ", proteinsAmount=" + proteinsAmount
                + ", fatsAmount=" + fatsAmount
                + ", carbohydratesAmount=" + carbohydratesAmount
                + '}';
    }
}
