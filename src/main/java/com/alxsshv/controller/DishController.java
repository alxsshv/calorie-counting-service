package com.alxsshv.controller;

import com.alxsshv.dto.DishDto;
import com.alxsshv.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createDish(@RequestBody DishDto dishDto) {
        dishService.createDish(dishDto);
        String successMessage = "Добавлено новое блюдо: " + dishDto.getTitle();
        log.info(successMessage);
        return successMessage;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DishDto> findAllDishes() {
        return dishService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DishDto findDishById(@PathVariable("id") long id) {
        return dishService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateDish(@RequestBody DishDto dishDto) {
        dishService.updateDish(dishDto);
        String successMessage = "Данные о блюде: " + dishDto.getTitle()
                + " успешно обновлены";
        log.info(successMessage);
        return successMessage;
    }
}
