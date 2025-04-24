package com.alxsshv.service.implementation;


import com.alxsshv.dto.DishDto;
import com.alxsshv.dto.mappers.DishMapper;
import com.alxsshv.exception.DataProcessingException;
import com.alxsshv.model.Dish;
import com.alxsshv.repository.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DishServiceImplTest {
    @Mock
    private DishRepository dishRepository;
    @Mock
    private DishMapper dishMapper;
    @InjectMocks
    private DishServiceImpl dishService;

    @Test
    @DisplayName("Test create dish when method called "
            + "then called DishRepository and DishMapper")
    public void testCreateDish_whenMethodCalled_thenCreateUser() {
        DishDto dishDto = new DishDto();
        Dish dish = new Dish();
        Mockito.when(dishMapper.toEntity(dishDto)).thenReturn(dish);
        dishService.createDish(dishDto);
        Mockito.verify(dishMapper, Mockito.times(1)).toEntity(dishDto);
        Mockito.verify(dishRepository, Mockito.times(1))
                .save(Mockito.any(Dish.class));
    }

    @Test
    @DisplayName("Test findAll when method called"
            + " then return not empty dto list")
    public void testFindAll_whenMethodCalled_thenReturnNotEmptyList() {
        List<Dish> dishes = List.of( new Dish(), new Dish());
        List<DishDto> expectedDtos = List.of( new DishDto(), new DishDto());
        Mockito.when(dishRepository.findAll()).thenReturn(dishes);
        Mockito.when(dishMapper.toDishDtoList(dishes))
                .thenReturn(expectedDtos);
        List<DishDto> actualDtos = dishService.findAll();
        Assertions.assertEquals(expectedDtos.size(), actualDtos.size());
        Mockito.verify(dishMapper, Mockito.times(1))
                .toDishDtoList(dishes);
        Mockito.verify(dishRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test getById when dish found then return dish")
    public void testGetById_whenDishFound_thenReturnDish() {
        long dishId = 1L;
        Dish dish = new Dish();
        Mockito.when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        Assertions.assertNotNull(dishService.getById(dishId));
        Mockito.verify(dishRepository, Mockito.times(1)).findById(dishId);
    }

    @Test
    @DisplayName("Test getById when dish not found then throw exception")
    public void testGetById_whenDishNotFound_thenTrowException() {
        long dishId = 1L;
        Mockito.when(dishRepository.findById(dishId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> dishService.getById(dishId));
        Mockito.verify(dishRepository, Mockito.times(1)).findById(dishId);
    }

    @Test
    @DisplayName("Test findById when dish found then return DishDto")
    public void testFindById_whenDishFound_thenReturnDishDto() {
        long dishId = 1L;
        Dish dish = new Dish();
        Mockito.when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        Mockito.when(dishMapper.toDishDto(dish)).thenReturn(new DishDto());
        Assertions.assertNotNull(dishService.findById(dishId));
        Assertions.assertInstanceOf(DishDto.class, dishService.findById(dishId));
    }

    @Test
    @DisplayName("Test findById when dish not found then throw exception")
    public void testFindById_whenDishNotFound_thenTrowException() {
        long dishId = 1L;
        Mockito.when(dishRepository.findById(dishId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> dishService.findById(dishId));
        Mockito.verify(dishRepository, Mockito.times(1)).findById(dishId);
    }

    @Test
    @DisplayName("Test updateDish when dish found and title no changed "
            + "then update success")
    public void testUpdateDishWhenSetValidDishDtoAndTitleNoChanged_thenUpdateSuccess() {
        long dishId = 1L;
        String dtoTitle = "newTitle";
        DishDto dishDto = new DishDto();
        dishDto.setId(dishId);
        dishDto.setTitle(dtoTitle);
        Dish dish = new Dish();
        dish.setTitle(dtoTitle);
        Mockito.when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        dishService.updateDish(dishDto);
        Mockito.verify(dishRepository,Mockito.times(1))
                .save(Mockito.any(Dish.class));
        Mockito.verify(dishMapper, Mockito.times(1))
                .updateDishFromDishDto(dish, dishDto);
    }

    @Test
    @DisplayName("Test updateDish when dish found and new title "
            + "not found from database then update success")
    public void testUpdateDishWhenSetValidDishDto_thenUpdateSuccess() {
        long dishId = 1L;
        String dtoTitle = "newTitle";
        DishDto dishDto = new DishDto();
        dishDto.setId(dishId);
        dishDto.setTitle(dtoTitle);
        Dish dish = new Dish();
        dish.setTitle("oldTitle");
        Mockito.when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        Mockito.when(dishRepository.findByTitle(dtoTitle)).thenReturn(Optional.empty());
        dishService.updateDish(dishDto);
        Mockito.verify(dishRepository,Mockito.times(1))
                .save(Mockito.any(Dish.class));
    }

    @Test
    @DisplayName("Test updateDish when dish found and dish with"
            + " new title already exist from database then throw exception")
    public void testUpdateDish_whenDishWithNewTitleAlreadyExist_thenThrowException() {
        long dishId = 1L;
        String dtoTitle = "newTitle";
        DishDto dishDto = new DishDto();
        dishDto.setId(dishId);
        dishDto.setTitle(dtoTitle);
        Dish dish = new Dish();
        dish.setTitle("oldTitle");
        Mockito.when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        Mockito.when(dishRepository.findByTitle(dtoTitle)).thenReturn(Optional.of(new Dish()));
        Assertions.assertThrows(DataProcessingException.class,
                () -> dishService.updateDish(dishDto));
        Mockito.verify(dishRepository,Mockito.never())
                .save(Mockito.any(Dish.class));
    }

    @Test
    @DisplayName("Test deleteById when dish found then delete success")
    public void testDeleteById_whenDishFound_thenDeleteSuccess() {
        long dishId = 1L;
        Mockito.when(dishRepository.findById(dishId)).thenReturn(Optional.of(new Dish()));
        dishService.deleteById(dishId);
        Mockito.verify(dishRepository,Mockito.times(1))
                .delete(Mockito.any(Dish.class));
    }

    @Test
    @DisplayName("Test deleteById when dish not found then throw exception")
    public void testDeleteById_whenDishNotFound_thenThrowException() {
        long dishId = 1L;
        Mockito.when(dishRepository.findById(dishId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> dishService.deleteById(dishId));
        Mockito.verify(dishRepository,Mockito.never())
                .delete(Mockito.any(Dish.class));
    }

}
