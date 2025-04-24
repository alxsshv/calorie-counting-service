package com.alxsshv.dto.mappers;

import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.model.FoodIntake;
import com.alxsshv.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodIntakeMapper {

    /**Метод преобразования в сущности {@link FoodIntake} в FoodIntakeDto.
     * @param foodIntake - Объект класса FoodIntake (приём пищи).
     * @return возвращает объект передачи данных ({@link FoodIntakeDto})*/
    @Mapping(target = "userId", qualifiedByName = "mapUserId", source = "user")
    FoodIntakeDto toFoodIntakeDto(FoodIntake foodIntake);

    /**Метод преобразования FoodIntakeDto в сущность
     * {@link FoodIntake} (приём пищи).
     * @param foodIntakeDto - объект передачи данных({@link com.alxsshv.dto.DishDto}).
     * @return возвращает объект класса {@link FoodIntake}.*/
    FoodIntake toEntity(FoodIntakeDto foodIntakeDto);

    /**Метод преобразования списка сущностей {@link FoodIntake}(блюдо)
     * в список объектов передачи данных {@link FoodIntakeDto}.
     * @param foodIntakes - список объектов класса ({@link FoodIntake}).
     * @return возвращает список объектов передачи данных {@link FoodIntake}.*/
    List<FoodIntakeDto> toFoodIntakeDtoList(List<FoodIntake> foodIntakes);

    /**Дефолтный метод для получения userId при маппинге
     * foodIntakeDto из класса {@link FoodIntake}.
     * @param user - объект класса {@link User},
     * указанный в свойстве сущности FoodIntake.
     * @return возвращает числовой идентифкатор пользователя (long)*/
    @Named("mapUserId")
    default long mapUserId(User user) {
            return user.getId();
    }
}
