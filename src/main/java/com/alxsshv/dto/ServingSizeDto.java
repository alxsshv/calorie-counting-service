package com.alxsshv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для {@link com.alxsshv.model.ServingSize}.
 * @author Шварёв Алексей
 * @version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServingSizeDto {
    private long id;
    private DishDto dish;
    private int amount;
}
