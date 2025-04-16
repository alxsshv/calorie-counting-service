package com.alxsshv.service;

import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.model.FoodIntake;
import com.alxsshv.service.validation.IsValidDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.util.List;

public interface FoodIntakeService {

    void createFoodIntake(@Valid FoodIntakeDto foodIntakeDto);

    List<FoodIntakeDto> findAllByUserIdAndDate(
            @Min(value = 1, message = "Некорректный идентификатор пользователя") long userId,
            @IsValidDate LocalDate date);

    FoodIntake getById(@Min(value = 1, message = "Некорректный идентификатор приёма пищи") long FoodIntakeId);

    FoodIntakeDto findById(@Min(value = 1, message = "Некорректный идентификатор приёма пищи") long FoodIntakeId);

    void deleteById(@Min(value = 1, message = "Некорректный идентификатор приёма пищи") long FoodIntakeId);
}
