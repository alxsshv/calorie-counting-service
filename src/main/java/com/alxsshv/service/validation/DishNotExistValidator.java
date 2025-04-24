package com.alxsshv.service.validation;

import com.alxsshv.dto.DishDto;
import com.alxsshv.model.Dish;
import com.alxsshv.repository.DishRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DishNotExistValidator implements ConstraintValidator<DishNotExist, DishDto> {
    @Autowired
    private DishRepository dishRepository;

    @Override
    public boolean isValid(final DishDto dishDto, final ConstraintValidatorContext constraintValidatorContext) {
        final Optional<Dish> dishOpt = dishRepository.findByTitle(dishDto.getTitle());
        return dishOpt.isEmpty();
    }
}
