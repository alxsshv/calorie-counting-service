package com.alxsshv.repository;

import com.alxsshv.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**Интерфейс описывает методы работы с сущностью блюдо {@link Dish}) */
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    /**Метод для получения блюда по названию.
     * @param title - строка с названием искомого блюда.
     * @return возвращает Optional, содержащий объект класса Dish,
     * если блюдо с указанным названием найдено
     * или пустой Optional если блюдо не найдено.*/
    Optional<Dish> findByTitle(String title);
}
