package com.alxsshv.dto.mappers;

import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.model.FoodIntake;
import com.alxsshv.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodIntakeMapper {
    @Mapping(target = "userId", qualifiedByName = "mapUserId", source = "user")
    FoodIntakeDto toFoodIntakeDto(FoodIntake foodIntake);
    FoodIntake toEntity(FoodIntakeDto foodIntakeDto);
    List<FoodIntakeDto> toFoodIntakeDtoList(List<FoodIntake> foodIntakes);

    /**Дефолтный метод для получения userId при маппинге
     * foodIntakeDto из класса {@link User}*/
    @Named("mapUserId")
    default long mapUserId(User user) {
            return user.getId();
    }
}
