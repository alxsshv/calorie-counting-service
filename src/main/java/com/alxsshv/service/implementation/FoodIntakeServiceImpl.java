package com.alxsshv.service.implementation;

import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.dto.mappers.FoodIntakeMapper;
import com.alxsshv.model.FoodIntake;
import com.alxsshv.repository.FoodIntakeRepository;
import com.alxsshv.service.FoodIntakeService;
import com.alxsshv.service.validation.IsValidFoodIntakeDate;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class FoodIntakeServiceImpl implements FoodIntakeService {
    @Autowired
    private FoodIntakeRepository foodIntakeRepository;
    @Autowired
    private FoodIntakeMapper foodIntakeMapper;

    @Override
    public void createFoodIntake(@Valid final FoodIntakeDto foodIntakeDto) {
        FoodIntake foodIntake = foodIntakeMapper.toEntity(foodIntakeDto);
        foodIntakeRepository.save(foodIntake);
    }

    @Override
    public List<FoodIntakeDto> findAllByUserIdAndDate(
            @Min(value = 1, message = "Некорректный идентификатор пользователя") final long userId,
            @IsValidFoodIntakeDate final LocalDate date) {
        List<FoodIntake> foodIntakeList = foodIntakeRepository
                .findByUserIdAndDate(userId, date);
        return foodIntakeMapper.toFoodIntakeDtoList(foodIntakeList);
    }

    @Override
    public FoodIntake getById(@Min(value = 1,
            message = "Некорректный идентификатор приёма пищи") final long FoodIntakeId) {
        Optional<FoodIntake> foodIntakeOpt = foodIntakeRepository.findById(FoodIntakeId);
        if (foodIntakeOpt.isEmpty()) {
            String errorMessage = "Запись о приёме пищи не найдена";
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }
        return foodIntakeOpt.get();
    }

    @Override
    public FoodIntakeDto findById(@Min(value = 1,
            message = "Некорректный идентификатор приёма пищи") long FoodIntakeId) {
       FoodIntake foodIntake = getById(FoodIntakeId);
       return foodIntakeMapper.toFoodIntakeDto(foodIntake);
    }

    @Override
    public void deleteById(@Min(value = 1,
            message = "Некорректный идентификатор приёма пищи") long FoodIntakeId) {
        FoodIntake foodIntake = getById(FoodIntakeId);
        foodIntakeRepository.delete(foodIntake);
    }

    public int getFoodInCount(long userId, LocalDate date) {
        return foodIntakeRepository.getCount(userId, date);
    }

    public double getFoodInCalorieSum(long userId, LocalDate date) {
        return foodIntakeRepository.getSum(userId, date);
    }
}
