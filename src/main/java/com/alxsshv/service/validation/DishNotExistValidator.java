package com.alxsshv.service.validation;

import com.alxsshv.dto.DishDto;
import com.alxsshv.model.Dish;
import com.alxsshv.repository.DishRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Класс, реализующий логику проверки
 * на существование записи о блюде
 * с указанным id в БД.
 * @author Шварёв Алексей
 * @version 1.0
 */
public class DishNotExistValidator implements ConstraintValidator<DishNotExist, DishDto> {
    /**Репозиторий для получения сведений
     *  о блюде из базы данных.*/
    @Autowired
    private DishRepository dishRepository;

    /**Метод проверки на существование блюда с указанным id.
     * @param dishDto - объект передачи данных {@link DishDto}.
     * @return возрващает true, если запись о блюде
     * с указанным в dishDto id не найдена или возващает false,
     * если запись о блюде имеется в базе данных. */
    @Override
    public boolean isValid(final DishDto dishDto, final ConstraintValidatorContext constraintValidatorContext) {
        final Optional<Dish> dishOpt = dishRepository.findByTitle(dishDto.getTitle());
        return dishOpt.isEmpty();
    }
}
