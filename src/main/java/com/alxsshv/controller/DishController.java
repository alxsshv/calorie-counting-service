package com.alxsshv.controller;

import com.alxsshv.dto.DishDto;
import com.alxsshv.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**Клас, предназначенный для обработки запросов
 * на добавление, изменение, удавление и получение
 * информации о блюдах (продуктах питания).
 * @author Шварёв Алексей
 * @version 1.0*/
@RestController
@RequestMapping("/api/v1/dishes")
@Slf4j
public class DishController {
    /**Сервисный слой, реализующий логику обрабокт запросов
     * в части записей о блюдах.*/
    @Autowired
    private DishService dishService;

    /**Метод добавления блюда.
     *@param dishDto - объект передачи данных {@link DishDto}.
     * Поля объекта dishDto должны соответстовать требованиям:
     * названия блюда не может быть пустым,
     * калорийность блюда на 100 грам продукта не может превышать 1000 ккал.
     *@return возвращает строку с сообщением об
     * успешном добавлении блюда.*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createDish(@RequestBody final DishDto dishDto) {
        dishService.createDish(dishDto);
        String successMessage = "Добавлено новое блюдо: " + dishDto.getTitle();
        log.info(successMessage);
        return successMessage;
    }

    /**Метод возвращает сведения о всех, внесенных в базу данных, блюдах.
     * @return возвращает список (массив) объектов {@link DishDto}*/
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DishDto> findAllDishes() {
        return dishService.findAll();
    }

    /**Метод возвращает информацию о блюде с указанным id.
     * @param id - числовой идентификатор блюда (long).
     * @return возвращает объект {@link DishDto}*/
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DishDto findDishById(@PathVariable("id") final long id) {
        return dishService.findById(id);
    }

    /**Метод обработки запросов на изменение сведений о блюде.
     *@param dishDto - объект передачи данных {@link DishDto}.
     * Поля объекта dishDto должны соответстовать требованиям:
     * названия блюда не может быть пустым,
     * калорийность блюда на 100 грам продукта не может превышать 1000 ккал.
     *@return возвращает строку с сообщением об
     * успешном изменении блюда.*/
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateDish(@RequestBody final DishDto dishDto) {
        dishService.updateDish(dishDto);
        String successMessage = "Сведения о блюде: " + dishDto.getTitle()
                + " успешно обновлены";
        log.info(successMessage);
        return successMessage;
    }

    /**Метод обработки запросов на удаление сведений о блююе.
     * @param id - числовой идентификатор блюда (long).
     * @return возвращает сообщение об успешном удалении блюда.*/
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteDish(@PathVariable("id") final long id) {
        dishService.deleteById(id);
        String successMessage = "Запись о блюде успешно удалена";
        log.info(successMessage);
        return successMessage;
    }

}
