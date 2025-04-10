package com.alxsshv.controller;

import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.service.FoodIntakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/food")
@Slf4j
public class FoodIntakeController {
    @Autowired
    private FoodIntakeService foodIntakeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addFoodIntake(@RequestBody FoodIntakeDto foodIntakeDto) {
        foodIntakeService.createFoodIntake(foodIntakeDto);
        String successMessage = "Прием пищи добавлен";
        log.info(successMessage);
        return successMessage;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FoodIntakeDto> findAllByUserAndDate(
            @RequestParam(value = "user", defaultValue = "0") long userId,
            @RequestParam(value = "date", defaultValue = "-999999999-01-01") LocalDate date) {
        return foodIntakeService.findAllByUserIdAndDate(userId, date);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FoodIntakeDto findById(@PathVariable("id") long id) {
        return foodIntakeService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteById(@PathVariable("id") long id) {
        foodIntakeService.deleteById(id);
        String successMessage = "Запись о приеме пищи успешно удалена";
        log.info(successMessage);
        return successMessage;
    }
}
