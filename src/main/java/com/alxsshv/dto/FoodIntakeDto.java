package com.alxsshv.dto;

import com.alxsshv.service.validation.UserIsPresent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
    @UserIsPresent
    private long userId;
    private LocalDate date;
    @NotEmpty(message = "Необходимо добавить хотябы одно блюдо")
    @Valid
    private List<ServingSizeDto> servingSizes;

    @Override
    public String toString() {
        return "FoodIntakeDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", servingSizes=" + servingSizes +
                '}';
    }
}
