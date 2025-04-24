package com.alxsshv.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Классы описывает объект передачи данных
 * (DTO) для сущности Порция {@link com.alxsshv.model.ServingSize}.
 * @author Шварёв Алексей
 * @version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServingSizeDto {
    /**Числовой идентификатор
     *  порции блюда.*/
    private long id;
    /**Блюдо, порцию которого
     * указал пользователь при приёме пищи. Экземпляр класса {@link DishDto}*/
    @NotNull(message = "Пожалуйста выберите блюдо")
    private DishDto dish;
    /**Масса порции съеденного блюда в граммах.*/
    @Min(value = 1, message = "Масса блюда не может быть меньше 1 грамма")
    private int amount;

    /**Метод для преобразования объекта класса
     *  ServingSizeDto в строковое представление.
     *  @return возвращает строку с перечнем
     *  полей и их значений для
     *  экземпляра класса ServingSizeDto.*/
    @Override
    public String toString() {
        return "ServingSizeDto{"
                + "id=" + id
                + ", dish=" + dish
                + ", amount=" + amount
                + '}';
    }
}
