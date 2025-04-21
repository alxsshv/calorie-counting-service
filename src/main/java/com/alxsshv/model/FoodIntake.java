package com.alxsshv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**Класс предназначен для хранения информации
 * об одном приёме пищи пользователем.
 * @author Шварёв Алексей
 * @version 1.0*/
@Entity
@Table(name = "food_intakes")
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    /**Дата приема пищи пользователем.
     * Аннотации Timestamp не используются, чтобы
     * пользователь мог рассчитывать калории на любой день.*/
    @Column(name = "date")
    private LocalDate date;
    /**Список содержит объекты {@link ServingSize}.
     *  В списке хранятся съеденные за один
     *  приём пищи блюда и их количество*/
    @OneToMany(mappedBy = "foodIntake",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "serving_sizes")
    private List<ServingSize> servingSizes = new ArrayList<>();

    /**Метод добавления количества съеденного блюда \
     * в список servingSizes.
     * @param servingSize - размер порции (блюдо и его количество).
     * Данная реализация метода обеспечивает согласованность
     * двустороннего отношения OneToMany - ManyToOne.*/
     public void addServingSize(final ServingSize servingSize) {
         servingSizes.add(servingSize);
         servingSize.setFoodIntake(this);
     }

    /**Метод для преобразования объекта класса
     *  FoodIntake в строковое представление.
     *  @return возвращает строку с
     *  полями и их значениями для
     *  экземпляра класса FoodIntake.*/
    @Override
    public String toString() {
        return "FoodIntake{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", servingSizes=" + servingSizes +
                '}';
    }
}
