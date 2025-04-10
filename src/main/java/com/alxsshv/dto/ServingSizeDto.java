package com.alxsshv.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Пожалуйста выберите блюдо")
    private DishDto dish;
    @Min(value = 1, message = "Масса блюда не может быть меньше 1 грамма")
    private int amount;

    @Override
    public String toString() {
        return "ServingSizeDto{" +
                "id=" + id +
                ", dish=" + dish +
                ", amount=" + amount +
                '}';
    }
}
