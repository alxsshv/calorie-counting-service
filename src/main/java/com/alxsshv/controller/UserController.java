package com.alxsshv.controller;

import com.alxsshv.dto.UserDto;
import com.alxsshv.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        String successMessage = "Создан пользователь " + userDto.getName();
        log.info(successMessage);
        return successMessage;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserById(@PathVariable("id") final long id) {
        return userService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateUser(@RequestBody final UserDto userDto) {
        userService.update(userDto);
        String successMessage = "Данные пользователя "
                + userDto.getName() + "успешно обновлены";
        log.info(successMessage);
        return successMessage;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable("id") final long id) {
        userService.deleteById(id);
        String successMessage = "Запись о пользователе успешно удалена";
        log.info(successMessage);
        return successMessage;
    }

}
