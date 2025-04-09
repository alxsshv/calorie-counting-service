package com.alxsshv.dto.mappers;

import com.alxsshv.dto.ServingSizeDto;
import com.alxsshv.model.ServingSize;

import java.util.List;

public interface ServingSizeMapper {
    ServingSize toEntity(ServingSizeDto servingSizeDto);
    ServingSizeDto toServingSizeDto(ServingSize servingSize);
    List<ServingSizeDto> toServingSizeDtoList(List<ServingSize> servingSizes);
}
