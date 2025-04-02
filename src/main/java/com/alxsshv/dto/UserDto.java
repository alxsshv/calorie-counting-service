package com.alxsshv.dto;

import com.alxsshv.model.Goal;
import lombok.Value;

/**
 * DTO for {@link com.alxsshv.model.User}.
 * @author Шварёв Алексей
 * @version 1.0
 */
@Value
public class UserDto {
    /**Идентификатор пользователя.*/
    long id;
    /**Имя пользователя.*/
    String name;
    /**Адрес электронной почты.*/
    String email;
    String age;
    int weight;
    int height;
    Goal goal;
}