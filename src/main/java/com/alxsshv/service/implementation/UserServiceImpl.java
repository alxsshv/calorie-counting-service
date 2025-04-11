package com.alxsshv.service.implementation;

import com.alxsshv.dto.UserDto;
import com.alxsshv.dto.mappers.UserMapper;
import com.alxsshv.exception.DataProcessingException;
import com.alxsshv.model.User;
import com.alxsshv.repository.UserRepository;
import com.alxsshv.service.UserService;
import com.alxsshv.service.utils.BmrCalculator;
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
    @Autowired
    private BmrCalculator bmrCalculator;

    @Transactional
    @Override
    public void createUser(@Valid @UserNotExist final UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        double dailyCalorieNorm = bmrCalculator.calculate(user);
        user.setCalorieNorm(dailyCalorieNorm);
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
        return userMapper.toUserDtoList(users);
    }

    @Transactional
    @Override
    public void update(@Valid final UserDto userDto) {
        User userFromDb = getById(userDto.getId());
        if (!userFromDb.getEmail().equals(userDto.getEmail())){
            Optional<User> userOpt = userRepository.findByEmail(userDto.getEmail());
            if (userOpt.isPresent()) {
                throw new DataProcessingException("Указанный адрес электронной " +
                        "почты привязан в другому пользователю. У двух пользователей " +
                        "не может быть одинаковых адресов электронной почты");
            }
        }
        userMapper.updateUserFromDto(userFromDb, userDto);
        userFromDb.setCalorieNorm(bmrCalculator.calculate(userFromDb));
        userRepository.save(userFromDb);
    }

    @Override
    public void deleteById(@Min(value = 1,
            message = "Неверный формат id") final long id) {
       User userFromDb = getById(id);
       userRepository.delete(userFromDb);
    }
}
