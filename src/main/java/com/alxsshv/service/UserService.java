package com.alxsshv.service;

import com.alxsshv.dto.UserDto;
import com.alxsshv.model.User;
import com.alxsshv.service.validation.UserNotExist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;

import java.util.List;

/**Интерфейс, описывающий методы работы
 * с сущностью {@link com.alxsshv.model.User}.
 * @author Шварёв Алексей
 * @version 1.0*/
@Service
public interface UserService {
    /**Метод создание записи о пользователе в БД.
     *@param userDto - DTO для класса {@link com.alxsshv.model.User}.
     * @exception jakarta.validation.ConstraintViolationException
     * должно быть выброшено если пользователь с такми email
     * уже существует.*/
    void createUser(@Valid @UserNotExist UserDto userDto);

    /**Метод поиска записи о пользователе по id.
     * @param id - идентификатор пользователя в формате long.
     * @return возвращает объект класса {@link User}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о пользователе с указанным
     * идентификатором (id) не найдена*/
    User getById(@Min(value = 1,
            message = "Неверный формат id") long id);

    /**Метод поиска записи о пользователе по id.
     * @param id - идентификатор пользователя в формате long.
     * @return возвращает DTO для класса {@link User}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о пользователе с указанным
     * идентификатором (id) не найдена*/
    UserDto findById(@Min(value = 1,
            message = "Неверный формат id") long id);

    /**Метод для получения всех записей о пользователях.
     * @return возвращает список DTO для класса {@link User},
     * если записей нет - возвращается пустой список*/
    List<UserDto> findAll();

    /**Метод изменения данных о пользователе.
     * @param userDto - DTO для класса {@link User}*/
    void update(@Valid UserDto userDto);

    /**Метод удаления записи о пользователе по id.
     * @param id - идентификатор пользователя в формате long
     * (положительное значение больше нуля).*/
    void deleteById(@Min(value = 1,
            message = "Неверный формат id") long id);
}
