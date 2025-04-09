package com.alxsshv.dto.mappers;

import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.model.FoodIntake;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodIntakeMapper {
    FoodIntakeDto toFoodIntakeDto(FoodIntake foodIntake);
    FoodIntake toEntity(FoodIntakeDto foodIntakeDto);
    List<FoodIntakeDto> toFoodIntakeDtoList(List<FoodIntake> foodIntakes);
}
