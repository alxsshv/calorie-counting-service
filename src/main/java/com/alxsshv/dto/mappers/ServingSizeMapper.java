package com.alxsshv.dto.mappers;

import com.alxsshv.dto.ServingSizeDto;
import com.alxsshv.model.ServingSize;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServingSizeMapper {
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ServingSize toEntity(ServingSizeDto servingSizeDto);
    ServingSizeDto toServingSizeDto(ServingSize servingSize);
    List<ServingSizeDto> toServingSizeDtoList(List<ServingSize> servingSizes);
}
