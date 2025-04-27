package com.alxsshv.controller;

import com.alxsshv.dto.UserDto;
import com.alxsshv.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**Клас, предназначен для обработки запросов на добаление, удаление,
 * изменение и получение информации о пользователях.
 * @author Шварёв Алексей
 * @version 1.0*/
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    /**Сервис, реализующей бизнес логику обработки запросов
     * в части информации о пользователях.*/
    @Autowired
    private UserService userService;

    /**Метод добавления пользователя.
     *@param userDto - объект передачи данных {@link UserDto}.
     * Поля userDto должны соответстовать требованиям:
     * Имя пользователя не может быть пустым,
     * адрес электронной почты должен быть уникальным.
     * Цель должна соответствовать значениям перечисления {@link com.alxsshv.model.Goal}.
     *@return возвращает строку с сообщением об
     * успешном добавлении пользователя.*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody final UserDto userDto) {
        userService.createUser(userDto);
        String successMessage = "Создан пользователь " + userDto.getName();
        log.info(successMessage);
        return successMessage;
    }

    /**Метод возвращает сведения о всех пользователях.
     *@return возвращает список объектов {@link UserDto}.*/
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAllUsers() {
        return userService.findAll();
    }

    /**Метод возвращает информацию о пользователе с указанным id.
     * @param id - уникальный идентификатор пользователя (long).
     * @return возвращает объект класса {@link UserDto}*/
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserById(@PathVariable("id") final long id) {
        return userService.findById(id);
    }

    /**Метод изменения данных о пользователе.
     *@param userDto - объект передачи данных {@link UserDto}.
     * Поля userDto должны соответстовать требованиям:
     * Имя пользователя не может быть пустым,
     * адрес электронной почты должен быть уникальным.
     * Цель должна соответствовать значениям перечисления {@link com.alxsshv.model.Goal}.
     *@return возвращает строку с сообщением об
     * успешном изменени данных пользователя.*/
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateUser(@RequestBody final UserDto userDto) {
        userService.update(userDto);
        String successMessage = "Данные пользователя "
                + userDto.getName() + " успешно обновлены";
        log.info(successMessage);
        return successMessage;
    }

    /**Метод добавления пользователя.
     *@param id - уникальный идентификатор пользователя (long).
     *@return возвращает строку с сообщением об
     * успешном удалении данных пользователя.*/
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable("id") final long id) {
        userService.deleteById(id);
        String successMessage = "Запись о пользователе успешно удалена";
        log.info(successMessage);
        return successMessage;
    }

}
