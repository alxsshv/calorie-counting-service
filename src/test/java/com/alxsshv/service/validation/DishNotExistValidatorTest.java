package com.alxsshv.service.validation;

import com.alxsshv.dto.DishDto;
import com.alxsshv.model.Dish;
import com.alxsshv.repository.DishRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DishNotExistValidatorTest {
    @Mock
    private DishRepository dishRepository;
    @InjectMocks
    private DishNotExistValidator dishNotExistValidator;
    @Autowired
    ConstraintValidatorContext context;

    @Test
    @DisplayName("Test isValid when dish is present then return false")
    public void testIsValid_whenDishIsPresent_thenReturnFalse() {
        String dishTitle = "Блюдо от шефа";
        DishDto dishDto = new DishDto();
        dishDto.setTitle(dishTitle);
        Mockito.when(dishRepository.findByTitle(dishTitle)).thenReturn(Optional.of(new Dish()));
        Assertions.assertFalse(dishNotExistValidator.isValid(dishDto, context));
    }

    @Test
    @DisplayName("Test isValid when dish not exist then return true")
    public void testIsValid_whenDishIsNotExist_thenReturnTrue() {
        String dishTitle = "Блюдо от шефа";
        DishDto dishDto = new DishDto();
        dishDto.setTitle(dishTitle);
        Mockito.when(dishRepository.findByTitle(dishTitle)).thenReturn(Optional.empty());
        Assertions.assertTrue(dishNotExistValidator.isValid(dishDto, context));
    }
}
