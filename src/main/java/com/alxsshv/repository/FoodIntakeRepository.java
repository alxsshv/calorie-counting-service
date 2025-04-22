package com.alxsshv.repository;

import com.alxsshv.model.FoodIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**Интерфейс описывает CRUD методы для
 * сущности прием пищи {@link FoodIntake}).*/
@Repository
public interface FoodIntakeRepository extends JpaRepository<FoodIntake, Long> {
    /**Метод для получения приемов пищи {@link FoodIntake}
     * по дате и идентификатору пользователя.
     * @param userId - числовой идентификатор пользователя (long).
     * @param date - дата приёма пищи.
     * @return возвращает список (List) объектов класса {@link FoodIntake},
     * если на данную дату пользователь добавлял информацию о приемах пищи,
     * или пустой список (List) если приемы пищи на указанную дату,
     * выбранным пользователем не добавлялись.*/
    List<FoodIntake> findByUserIdAndDate(Long userId, LocalDate date);
}
