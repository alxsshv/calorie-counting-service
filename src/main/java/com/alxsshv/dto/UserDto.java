package com.alxsshv.dto;

import com.alxsshv.model.Goal;
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
    @Size(min = 3, max = 80,
            message = "Неверный формат имени пользователя")
    private String name;
    /**Адрес электронной почты.*/
    @NotNull(message = "Адрес электронной почты не может быть пустым")
    @Pattern(regexp = "[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}",
            message = "Неверный формат адреса электронной почты")
    private String email;
    /**Возраст пользователя.*/
    @Min(value = 1, message = "Некорректно указан возраст пользователя")
    @Max(value = 120, message = "Некорректно указан возраст пользователя")
    private int age;
    /**Вес пользователя в килограммах.*/
    @Min(value = 1, message = "Некорректно указан вес пользователя. " +
            "Вес должен быть указан в килограммах")
    @Max(value = 200, message = "Некорректно указан вес пользователя. " +
            "Вес должен быть указан в килограммах")
    private int weight;
    /**Рост пользователя в сантиметрах.*/
    @Min(value = 50, message = "Некорректно указан" +
            " рост пользователя. Рост должен быть указан в сантиметрах")
    @Max(value = 280, message = "Некорректно указан" +
            " рост пользователя. Рост должен быть указан в сантиметрах")
    private int height;
    /**Псевдоим цели использования сервиса
     * из перечисления {@link Goal}.*/
    @NotNull(message = "Пожалуйста укажите цель подсчета калорий")
    @NotEmpty(message = "Пожалуйста выбрите цель из возможных вариантов: похудение, поддержание, набор массы")
    private String goal;
    /**Пол пользователя из перечисления {@link com.alxsshv.model.Sex}.*/
    @NotNull(message = "Пожалуйста укажите пол пользователя")
    @NotEmpty(message = "Пожалуйста укажите пол пользователя: мужчина или женщина")
    private String sex;
    /**Дневная норма калорий*/
    private double calorieNorm;


    /**Метод преобразования экземпляра класса UserDto в строку.
     * @return строковое представление экземпляра класса UserDto,
     * содержащее наименование его полей и их значения*/
    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                ", goal='" + goal + '\'' +
                '}';
    }
}
