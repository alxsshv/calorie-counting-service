package com.alxsshv.service;

import com.alxsshv.dto.DishDto;
import com.alxsshv.model.Dish;
import com.alxsshv.service.validation.DishNotExist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

/**Интерфейс, описывающий методы работы
 * с сущностью {@link com.alxsshv.model.Dish}.
 * @author Шварёв Алексей
 * @version 1.0*/
public interface DishService {

    /**Метод создания записи о блюде.
     * @param dishDto - объект передачи данных
     * для {@link com.alxsshv.model.Dish}
     */
    void createDish(@Valid @DishNotExist DishDto dishDto);

    /**Метод предназначен для получения всех записей о блюдах.
     * @return возвращает список объектов передачи
     * данных для класса {@link Dish}*/
    List<DishDto> findAll();

    /**Метод поиска записи о блюде по id.
     * @param id - числовой идентификатор блюда в формате long.
     * @return возвращает объект класса {@link Dish}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о блюде с указанным
     * идентификатором (id) не найдена*/
    Dish getById(@Min(value = 1,
            message = "Неверный формат id") long id);

    /**Метод поиска записи о блюде по id.
     * @param id - числовой идентификатор блюда в формате long.
     * @return возвращает объект класса {@link DishDto}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о блюде с указанным
     * идентификатором (id) не найдена*/
    DishDto findById(@Min(value = 1,
            message = "Неверный формат id") long id);

    /**Метод изменения данных в записи о блюде.
     * @param dishDto - объект передачи данных
     * для {@link com.alxsshv.model.Dish}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о блюде с указанным в dishDto
     * идентификатором (id) не найдена*/
    void updateDish(@Valid DishDto dishDto);

    /**Метод удаления записи о блюде по id.
     * @param id - числовой идентификатор пользователя в формате long..
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о блюде с указанным
     * идентификатором (id) не найдена*/
    void deleteById(@Min(value = 1,
            message = "Неверный формат id") long id);
}
