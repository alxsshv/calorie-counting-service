package com.alxsshv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

/**Класс предназначен для хранения информации
 * об одном приёме пищи пользователем.
 * @author Шварёв Алексей
 * @version 1.0*/
@Entity
@Table(name = "food_intake")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodIntake {
    /**Идентификатор записи о приеме пищи в БД.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**Информация о пользователе.*/
    @Column(name = "user")
    private User user;
    /**Дата приема пищи пользователем.
     * аннотации Timestamp не используются, чтобы
     * пользователь мог расчитвать калории на любой день.*/
    @Column(name = "date")
    private LocalDate date;
    /**Map содержит id каждого блюда (ключ), употребляемого в
     * процессе приема пищи и его количество.*/
    @Column(name = "dishes")
    private Map<Long, Integer> dishes;

}
