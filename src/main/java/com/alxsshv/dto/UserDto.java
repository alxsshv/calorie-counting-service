package com.alxsshv.dto;

import com.alxsshv.model.Goal;
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
    private String name;
    /**Адрес электронной почты.*/
    private String email;
    /**Возраст пользователя.*/
    private String age;
    /**Вес пользователя в килограммах.*/
    private int weight;
    /**Рост пользователя в сантиметрах.*/
    private int height;
    /**Цель из списка {@link Goal}.*/
    private Goal goal;
}
