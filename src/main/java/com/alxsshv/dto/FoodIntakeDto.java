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
 * Классы описывает объект передачи данных
 * (DTO) для сущности Приём пищи {@link com.alxsshv.model.FoodIntake}.
 * @author Шварёв Алексей
 * @version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodIntakeDto {
    /**
     * Числовой идентификатор записи
     *  о приёме пищи (long).
     *  */
    private long id;
    /**
     * Числовой идентификатор пользователя,
     * который добавил запись о приёме пищи.
     * */
    @Min(value = 1, message = "Неверное указан идентификатор пользователя")
    @UserIsPresent
    private long userId;
    /**Дата приёма пищи.*/
    private LocalDate date;
    /**
     * Список порций блюд, съеденных
     * при приёме пищи. Список содержит объекты
     * класса {@link ServingSizeDto}
     * */
    @NotEmpty(message = "Необходимо добавить хотябы одно блюдо")
    @Valid
    private List<ServingSizeDto> servingSizes;

    /**Метод для преобразования объекта класса
     *  FoodIntakeDto в строковое представление.
     *  @return возвращает строку с перечнем
     *  полей и их значений для
     *  экземпляра класса FoodIntakeDto.*/
    @Override
    public String toString() {
        return "FoodIntakeDto{"
                + "id=" + id
                + ", userId=" + userId
                + ", date=" + date
                + ", servingSizes=" + servingSizes
                + '}';
    }
}
