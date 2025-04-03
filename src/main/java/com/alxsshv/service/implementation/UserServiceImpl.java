package com.alxsshv.service.implementation;

import com.alxsshv.dto.UserDto;
import com.alxsshv.dto.mappers.UserMapper;
import com.alxsshv.model.User;
import com.alxsshv.repository.UserRepository;
import com.alxsshv.service.UserService;
import com.alxsshv.service.validation.UserNotExist;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public void createUser(@Valid @UserNotExist final UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
    }

    @Override
    public User getById(@Min(value = 1,
            message = "Неверный формат id") final long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            String errorMessage = "Пользователь с указанным id не найден";
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }
        return userOpt.get();
    }

    @Override
    public UserDto findById(@Min(value = 1,
            message = "Неверный формат id") final long id) {
        User user = getById(id);
        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> userMapper.toUserDto(user))
                .toList();
    }

    @Transactional
    @Override
    public void update(@Valid final UserDto userDto) {
        User userFromDB = getById(userDto.getId());
        userMapper.updateUserFromDto(userDto, userFromDB);
        userRepository.save(userFromDB);
    }

    @Override
    public void deleteById(@Min(value = 1,
            message = "Неверный формат id") final long id) {
       User userFromDb = getById(id);
       userRepository.delete(userFromDb);
    }
}
