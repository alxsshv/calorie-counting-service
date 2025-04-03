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
    @Size(min = 3, max = 150,
            message = "Неверный формат имени пользователя")
    private String name;
    /**Адрес электронной почты.*/
    @NotNull(message = "Адрес электронной почты не может быть пустым")
    @Pattern(regexp = "[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}",
            message = "Неверный формат адреса электронной почты")
    private String email;
    /**Возраст пользователя.*/
    @Size(min = 1, max=120, message = "Некорректно указан возраст пользователя")
    private int age;
    /**Вес пользователя в килограммах.*/
    @Size(min = 1, max=200, message = "Некорректно указан вес пользователя. " +
            "Вес должен быть указан в килограммах")
    private int weight;
    /**Рост пользователя в сантиметрах.*/
    @Size(min = 50, max = 280, message = "Некоректно указан" +
            " возраст пользователя. Рост должен быть указан в сантиметрах")
    private int height;
    /**Цель из списка {@link Goal}.*/
    @NotNull(message = "Пожалуйста укажите цель подсчета калорий")
    private String goal;
}
