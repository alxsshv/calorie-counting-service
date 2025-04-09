package com.alxsshv.dto;

import com.alxsshv.model.ServingSize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO для {@link com.alxsshv.model.FoodIntake}.
 * @author Шварёв Алексей
 * @version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodIntakeDto {
    private long id;
    @Min(value = 1, message = "Неверное указан идентификатор пользователя")
    private long userId;
    @NotEmpty(message = "Дата приема пищи не может быть пустой")
    private LocalDate date;
    @NotEmpty(message = "Необходимо добавить хотябы одно блюдо")
    private List<ServingSizeDto> servingSizes;
}
