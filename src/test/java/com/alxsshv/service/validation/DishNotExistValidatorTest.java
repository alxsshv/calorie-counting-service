package com.alxsshv.service.validation;

import com.alxsshv.dto.DishDto;
import com.alxsshv.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DishNotExistValidatorTest {
    @Mock
    private DishRepository dishRepository;
    @InjectMocks
    private DishNotExistValidator dishNotExistValidator;

    @Test
    public void testIsValid_whenDishIsPresent_thenGetFalse() {
        String dishTitle = "Блюдо от шефа";
        DishDto dishDto = new DishDto();
        dishDto.setId(1L);
        dishDto.setTitle(dishTitle);
//        Mockito.when()
//        dishNotExistValidator.isValid()
    }
}
