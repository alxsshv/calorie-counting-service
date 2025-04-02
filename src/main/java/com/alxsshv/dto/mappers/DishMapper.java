package com.alxsshv.dto.mappers;

import com.alxsshv.dto.DishDto;
import com.alxsshv.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishMapper {
    /**Метод преобразования DishDto в сущность {@link Dish}(блюдо).
     * @param dishDto - объект передачи данных
     * для класса Dish ({@link DishDto}).
     * @return возвращает объект класса {@link Dish}.*/
    Dish toEntity(DishDto dishDto);

    /**Метод преобразования в сущности {@link Dish} в UserDto.
     * @param dish - Объект класса Dish (блюдо).
     * @return возвращает объект передачи данных
     * для класса Dish ({@link DishDto}) */
    DishDto toDishDto(Dish dish);
}
