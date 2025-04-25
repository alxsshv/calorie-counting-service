package com.alxsshv.service.implementation;

import com.alxsshv.dto.DishDto;
import com.alxsshv.dto.UserDto;
import com.alxsshv.dto.mappers.UserMapper;
import com.alxsshv.exception.DataProcessingException;
import com.alxsshv.model.Dish;
import com.alxsshv.model.User;
import com.alxsshv.repository.UserRepository;
import com.alxsshv.service.UserService;
import com.alxsshv.service.utils.BmrCalculator;
import com.alxsshv.service.validation.UserNotExist;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**Класс, описывающий логику работы
 * с сущностью {@link com.alxsshv.model.User}.
 * @author Шварёв Алексей
 * @version 1.0*/
@Service
@Validated
@Slf4j
public class UserServiceImpl implements UserService {
    /**Репозиторий для получения сведений
     * о пользователях из базы данных.*/
    @Autowired
    private UserRepository userRepository;
    /**Интерфеейс для двухстороннего преобразования
     * сущности {@link Dish} в DTO {@link DishDto}.
     */
    @Autowired
    private UserMapper userMapper;
    /**Интерфейс для вычисления
     * дневной нормы калорий пользователя.*/
    @Autowired
    private BmrCalculator bmrCalculator;

    /**Метод создания записи о пользователе в БД.
     * @param userDto - DTO для класса {@link com.alxsshv.model.User}.*/
    @Transactional
    @Override
    public void createUser(@Valid @UserNotExist final UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        double dailyCalorieNorm = bmrCalculator.calculate(user);
        user.setCalorieNorm(dailyCalorieNorm);
        userRepository.save(user);
    }

    /**Метод поиска записи о пользователе по id.
     * @param id - идентификатор пользователя в формате long.
     * @return возвращает объект класса {@link User}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о пользователе с указанным
     * идентификатором (id) не найдена*/
    @Override
    public User getById(final long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            String errorMessage = "Пользователь не найден";
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }
        return userOpt.get();
    }

    /**Метод поиска записи о пользователе по id.
     * @param id - идентификатор пользователя в формате long.
     * @return возвращает DTO для класса {@link User}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о пользователе с указанным
     * идентификатором (id) не найдена*/
    @Override
    public UserDto findById(final long id) {
        User user = getById(id);
        return userMapper.toUserDto(user);
    }

    /**Метод для получения всех записей о пользователях.
     * @return возвращает список DTO для класса {@link User},
     * если записей нет - возвращается пустой список*/
    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserDtoList(users);
    }

    /**Метод изменения данных о пользователе.
     * @param userDto - DTO для класса {@link User}*/
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

    /**Метод удаления записи о пользователе по id.
     * @param id - идентификатор пользователя в формате long
     * (положительное значение больше нуля).*/
    @Override
    public void deleteById(final long id) {
       User userFromDb = getById(id);
       userRepository.delete(userFromDb);
    }
}
