package com.alxsshv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**Класс описывает сущность "Пользователь", содержит
 * описание полей записей о пользователях сервиса.
 * @author Шварёв Алексей
 * @version 1.0*/
@Entity
@Table(name = "service_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**Числовой идентификатор записи о пользователе в БД.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**Имя пользователя.*/
    @Column(name = "name")
    private String name;
    /**Адрес электронной почты пользователя.*/
    @Column(name = "email")
    private String email;
    /**Возраст пользователя.*/
    @Column(name = "age")
    private int age;
    /**Вес пользователя в килограммах.*/
    @Column(name = "weight")
    private int weight;
    /**Рост пользователя в сантиметрах.*/
    @Column(name = "height")
    private int height;
    /**Цель использования:
     * WEIGHT_LOSS("Похудение"),
     * KEEPING_FIT("Поддержание"),
     * WEIGHT_GAIN("Набор массы").*/
    @Column(name = "goal")
    private Goal goal;

    /**Метод описывает логику сравнения экземпляров класса User.
     * @return возвращает false если объект с которым сравнивается
     * экземпляр класса User равен null, если это объект
     * другого класса, а так же если оба объекта относятся к классу User,
     * но имеется отличие в значениях свойств объектов.
     * Возвращает true если оба объекта не равны null, относятся к одному
     * и тому же классу и имеют одинаковые значения по каждому из свойств.*/
    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && weight == user.weight
                && height == user.height && Objects.equals(name, user.name)
                && Objects.equals(email, user.email)
                && Objects.equals(age, user.age) && goal == user.goal;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                ", goal='" + goal + '\'' +
                '}';
    }

    /**Метод описывает алгоритм вычисления hash-кода экземпляра класса User.
     * Для вычисления хеш-кода используются все поля экземпляра класса User.
     * @return возвращает целочисленное значение хеш-кода экземпляра класса User
     * в диапазоне от -2147483648 до 2147483647*/
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, weight, height, goal);

    }
}
