package com.alxsshv.service.implementation;

import com.alxsshv.dto.DishDto;
import com.alxsshv.dto.mappers.DishMapper;
import com.alxsshv.exception.DataProcessingException;
import com.alxsshv.model.Dish;
import com.alxsshv.repository.DishRepository;
import com.alxsshv.service.DishService;
import com.alxsshv.service.validation.DishNotExist;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class DishServiceImpl implements DishService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishMapper dishMapper;

    @Override
    @Transactional
    public void createDish(@Valid @DishNotExist DishDto dishDto) {
      Dish dish = dishMapper.toEntity(dishDto);
      dishRepository.save(dish);
    }

    @Override
    public List<DishDto> findAll() {
        List<Dish> dishes = dishRepository.findAll();
        return dishMapper.toDishDtoList(dishes);
    }

    @Override
    public Dish getById(@Min(value = 1,
            message = "Неверный формат id") long id) {
        Optional<Dish> dishOpt = dishRepository.findById(id);
        if (dishOpt.isEmpty()) {
            throw new EntityNotFoundException("Блюдо не найдено");
        }
        return dishOpt.get();
    }

    @Override
    public DishDto findById(@Min(value = 1,
            message = "Неверный формат id") long id) {
        Dish dish = getById(id);
        return dishMapper.toDishDto(dish);
    }

    @Override
    @Transactional
    public void updateDish(@Valid DishDto dishDto) {
        Dish dishFromDb = getById(dishDto.getId());
        if (!dishFromDb.getTitle().equals(dishDto.getTitle())) {
            Optional<Dish> dishOpt = dishRepository.findByTitle(dishDto.getTitle());
            if (dishOpt.isPresent()) {
                String errorMessage = "Блюдо с таким названием уже существует." +
                        " У двух блюд не может быть одинаковых названий";
                log.error(errorMessage);
                throw new DataProcessingException(errorMessage);
            }
        }
        dishMapper.updateDishFromDishDto(dishFromDb, dishDto);
        dishRepository.save(dishFromDb);
    }

    @Override
    public void deleteById(@Min(value = 1,
            message = "Неверный формат id") long id) {
        Dish dish = getById(id);
        dishRepository.delete(dish);
    }
}
