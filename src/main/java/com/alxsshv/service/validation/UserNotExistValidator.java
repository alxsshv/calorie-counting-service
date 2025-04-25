package com.alxsshv.service.validation;

import com.alxsshv.dto.UserDto;
import com.alxsshv.model.User;
import com.alxsshv.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Класс, реализующий логику проверки
 * на отсутстввие записи о пользователе в БД.
 * @author Шварёв Алексей
 * @version 1.0
 */
public class UserNotExistValidator
        implements ConstraintValidator<UserNotExist, UserDto> {
    /**Репозиторий для получения сведений
     *  о пользователе из базы данных.*/
    @Autowired
    private UserRepository userRepository;

    /**Метод, реализующий логику проверки на отсутствие пользователя в БД.
     * @param userDto - объект передачи данных {@link UserDto}.
     * @param constraintValidatorContext - контекст валидации.
     * Для поиска пользователя в БД используется свойство объекта userDto -
     * email (адрес электронной почты).
     * @return возвращает true если пользователь на найден в БД или false
     * если запись о пользователе с указанным в свойствае userDto email
     * уже присутствует в базе данных.*/
    @Override
    public boolean isValid(final UserDto userDto,
                           final ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> userOpt = userRepository.findByEmail(userDto.getEmail());
        return userOpt.isEmpty();
    }
}
