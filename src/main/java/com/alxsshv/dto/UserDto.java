package com.alxsshv.dto;

import com.alxsshv.service.validation.UserViolationsConstants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для {@link com.alxsshv.model.User}.
 * @author Шварёв Алексей
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    /**Идентификатор пользователя.*/
    private long id;

    /**Имя пользователя.*/
    @NotNull(message = "Имя пользователя не может быть пустым")
    @Size(min = UserViolationsConstants.MIN_USER_NAME_LENGTH,
            max = UserViolationsConstants.MAX_USER_NAME_LENGTH,
            message = "Неверный формат имени пользователя")
    private String name;

    /**Адрес электронной почты.*/
    @NotNull(message = "Адрес электронной почты не может быть пустым")
    @Pattern(regexp = UserViolationsConstants.EMAIL_VALIDATION_TEMPLATE,
            message = "Неверный формат адреса электронной почты")
    private String email;

    /**Возраст пользователя.*/
    @Min(value = UserViolationsConstants.MIN_USER_AGE,
            message = "Некорректно указан возраст пользователя")
    @Max(value = UserViolationsConstants.MAX_USER_AGE,
            message = "Некорректно указан возраст пользователя")
    private int age;

    /**Вес пользователя в килограммах.*/
    @Min(value = UserViolationsConstants.MIN_USER_WEIGHT,
            message = "Некорректно указан вес пользователя. "
                    + "Вес должен быть указан в килограммах")
    @Max(value = UserViolationsConstants.MAX_USER_WEIGHT,
            message = "Некорректно указан вес пользователя. "
                    + "Вес должен быть указан в килограммах")
    private int weight;

    /**Рост пользователя в сантиметрах.*/
    @Min(value = UserViolationsConstants.MIN_USER_HEIGHT,
            message = "Некорректно указан"
            + " рост пользователя. Рост должен быть указан в сантиметрах")
    @Max(value = UserViolationsConstants.MAX_USER_HEIGHT,
            message = "Некорректно указан"
            + " рост пользователя. Рост должен быть указан в сантиметрах")
    private int height;

    /**Псевдоим цели использования сервиса
     * из перечисления {@link com.alxsshv.model.Goal}.*/
    @NotNull(message = "Пожалуйста укажите цель подсчета калорий")
    @NotEmpty(message = "Пожалуйста выбрите цель из возможных вариантов:"
            + " похудение, поддержание, набор массы")
    private String goal;
    /**Пол пользователя из перечисления {@link com.alxsshv.model.Sex}.*/
    @NotNull(message = "Пожалуйста укажите пол пользователя")
    @NotEmpty(message = "Пожалуйста укажите пол пользователя:"
            + " мужчина или женщина")
    private String sex;
    /**Дневная норма калорий в килокалориях.*/
    private double calorieNorm;

    /**Метод преобразования экземпляра класса UserDto в строку.
     * @return строковое представление экземпляра класса UserDto,
     * содержащее наименование его полей и их значения*/
    @Override
    public String toString() {
        return "UserDto{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + ", age=" + age
                + ", weight=" + weight
                + ", height=" + height
                + ", goal='" + goal + '\''
                + '}';
    }
}
