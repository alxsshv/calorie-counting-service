package com.alxsshv.controller;

import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.service.FoodIntakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**Клас, предназначенный для обработки запросов на добавление,
 * удаление и получение информации о приёме пищи.
 * @author Шварёв Алексей
 * @version 1.0*/
@RestController
@RequestMapping("/api/v1/food")
@Slf4j
public class FoodIntakeController {
    @Autowired
    private FoodIntakeService foodIntakeService;

    /**Метод добавления приема пищи.
     *@param foodIntakeDto - объект передачи данных {@link FoodIntakeDto}.
     * объект foodIntakeDto должен содержать хотябы один объект
     * описывающий порцию блюда, съеденного при приёме пищи,
     * в списке объектов {@link com.alxsshv.dto.ServingSizeDto}.
     *@return возвращает строку с сообщением об
     * успешном добавлении приёма пищи.*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addFoodIntake(@RequestBody FoodIntakeDto foodIntakeDto) {
        foodIntakeService.createFoodIntake(foodIntakeDto);
        String successMessage = "Прием пищи добавлен";
        log.info(successMessage);
        return successMessage;
    }

    /**Метод получения информации о приёмах пищи
     * указанного пользователя в указанную дату.
     * @param userId - числовой идентификатор пользователя (long).
     * @param date  - дата для которой необходимо вернуть сведения о приёмах пищи.
     * @return возвращает список объектов {@link FoodIntakeDto}*/
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FoodIntakeDto> findAllByUserAndDate(
            @RequestParam(value = "user", defaultValue = "0") long userId,
            @RequestParam(value = "date", defaultValue = "-999999999-01-01") LocalDate date) {
        return foodIntakeService.findAllByUserIdAndDate(userId, date);
    }

    /**Метод получения информации о приёме пищи по его идентификатору.
     * @param id - числовой идентификатор приёма пищи (long).
     * @return возвращает объект {@link FoodIntakeDto}*/
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FoodIntakeDto findById(@PathVariable("id") long id) {
        return foodIntakeService.findById(id);
    }

    /**Метод удаления сведений о приёме пищи по идентиифкатору.
     *  @param id - числовой идентификатор приёма пищи (long).
     *  @return возвращает сообщение об спешном удалении
     *  сведений о приёме пищи.*/
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteById(@PathVariable("id") long id) {
        foodIntakeService.deleteById(id);
        String successMessage = "Запись о приеме пищи успешно удалена";
        log.info(successMessage);
        return successMessage;
    }
}
