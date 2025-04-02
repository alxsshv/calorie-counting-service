package com.alxsshv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**Класс предназначен для хранения съеденного
 *  за один прием пищи количества еды(блюда)
 *  одного вида.
 *  @author Шварёв Алексей
 *  @version 1.0*/
@Entity
@Table(name = "serving_size")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServingSize {
    /**Идентификатор записи о съеденном блюде.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**Ссылка на запись о блюде в БД.*/
    @ManyToOne(fetch = FetchType.LAZY)
    private Dish dish;
    /**Количество порций одного блюда,
     * съеденного за один приём пищи.*/
    @Column(name = "amount")
    private int amount;
    /**Ссылка на приём пищи к которому относится
     * запись о размере порции.*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_intake_id")
    private FoodIntake foodIntake;
}
