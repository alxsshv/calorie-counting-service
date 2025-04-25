package com.alxsshv.service;

import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.model.FoodIntake;
import com.alxsshv.service.validation.IsValidDate;
import com.alxsshv.service.validation.UserIsPresent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.util.List;

/**Интерфейс, описывающий методы работы
 * с сущностью {@link com.alxsshv.model.FoodIntake} (приём пищи).
 * @author Шварёв Алексей
 * @version 1.0*/
public interface FoodIntakeService {

    /**Метод создания записи о приеме пищи.
     * @param foodIntakeDto - объект передачи данных
     * {@link FoodIntakeDto}
     * */
    void createFoodIntake(@Valid FoodIntakeDto foodIntakeDto);

    /**Метод предназначен для получения всех записей
     *  о приёмах пищи выбранного пользователя с указанным id
     *  на выбранную дату.
     * @param userId - идентификатор пользователя (long).
     * @param date - дата, за которую необходимо получить
     * сведения о приёмах пищи.
     * @return возвращает список объектов передачи
     * данных {@link FoodIntakeDto}.*/
    List<FoodIntakeDto> findAllByUserIdAndDate(
            @UserIsPresent long userId,
            @IsValidDate LocalDate date);

    /**Метод поиска записи о приёме пищи по идентификатору.
     * @param foodIntakeId - числовой идентификатор приёма пищи в формате long.
     * @return возвращает объект класса {@link FoodIntake}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о приёме пищи с указанным
     * идентификатором (id) не найдена*/
    FoodIntake getById(@Min(value = 1, message = "Некорректный идентификатор приёма пищи") long foodIntakeId);

    /**Метод поиска записи о приёме пищи по идентификатору (id).
     * @param foodIntakeId - числовой идентификатор приёма пищи в формате long.
     * @return возвращает объект класса {@link FoodIntakeDto}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о приёме пищи с указанным
     * идентификатором (id) не найдена*/
    FoodIntakeDto findById(@Min(value = 1, message = "Некорректный идентификатор приёма пищи") long foodIntakeId);

    /**Метод удаления записи о приёме пищи.
     * @param foodIntakeId - числовой идентификатор
     * удаляемой записи о приёме пищи.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о приёме пищи с указанным
     * идентификатором (id) не найдена*/
    void deleteById(@Min(value = 1, message = "Некорректный идентификатор приёма пищи") long foodIntakeId);
}
