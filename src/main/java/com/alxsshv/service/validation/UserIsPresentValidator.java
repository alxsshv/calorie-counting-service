package com.alxsshv.service.validation;

import com.alxsshv.model.User;
import com.alxsshv.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
/**
 * Класс, реализующий логику проверки
 * на существование записи о пользователе
 * с указанным id в БД.
 * @author Шварёв Алексей
 * @version 1.0
 */

@Validated
public class UserIsPresentValidator
        implements ConstraintValidator<UserIsPresent, Long> {
    /**Репозиторий для получения сведений
     *  о пользователе из базы данных.*/
    @Autowired
    private UserRepository userRepository;

    /**Метод проверки на существование пользователя с указанным id.
     * @param userId - идентификатор (id) пользователя в формате long.
     * @param constraintValidatorContext - контекст валидации.
     * @return возрващает true, если запись о пользователе
     * с указанным id имеется в базе данных или возващает false,
     * если запись о пользователе с указанным id не найдена. */
    @Override
    public boolean isValid(final Long userId,
                           final ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.isPresent();
    }
}
