package com.alxsshv.dto.mappers;

import com.alxsshv.dto.DishDto;
import com.alxsshv.model.Dish;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishMapper {
    /**Метод преобразования DishDto в сущность {@link Dish}(блюдо).
     * @param dishDto - объект передачи данных
     * для класса Dish ({@link DishDto}).
     * @return возвращает объект класса {@link Dish}.*/
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Dish toEntity(DishDto dishDto);

    /**Метод преобразования в сущности {@link Dish} в UserDto.
     * @param dish - Объект класса Dish (блюдо).
     * @return возвращает объект передачи данных
     * для класса Dish ({@link DishDto})*/
    DishDto toDishDto(Dish dish);

    /**Метод преобразования списка сущностей {@link Dish}(блюдо)
     * в список объектов передачи данных {@link DishDto}.
     * @param dishList - список объектов класса Dish ({@link Dish}).
     * @return возвращает список объектов передачи данных {@link DishDto}.*/
    List<DishDto> toDishDtoList(List<Dish> dishList);

    /**Метод преобразования в сущности {@link Dish} в UserDto.
     * @param dish - Объект класса Dish (блюдо).
     * @return возвращает объект передачи данных
     * для класса Dish ({@link DishDto}) */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDishFromDishDto(@MappingTarget Dish dish, DishDto dishDto);


}
