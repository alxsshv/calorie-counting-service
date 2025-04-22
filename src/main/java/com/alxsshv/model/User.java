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
    /**Пол пользователя.*/
    @Column(name = "sex")
    private Sex sex;
    /**Дневная норма калорий в килокалориях.*/
    @Column(name = "calorie_norm")
    private double calorieNorm;

    /**Метод описывает алгоритм вычисления hash-кода объекта класса User.
     * Для вычисления хеш-кода используются все свойства объекта класса User.
     * @return возвращает целочисленное значение хеш-кода экземпляра класса User
     * в диапазоне от -2147483648 до 2147483647*/
    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && age == user.age
                && weight == user.weight && height == user.height
                && Objects.equals(name, user.name)
                && Objects.equals(email, user.email)
                && goal == user.goal && sex == user.sex;
    }

    /**Метод описывает алгоритм вычисления hash-кода объекта класса User.
     * Для вычисления хеш-кода используются все свойства объекта класса User.
     * @return возвращает целочисленное значение хеш-кода экземпляра класса User
     * в диапазоне от -2147483648 до 2147483647*/
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, weight, height, goal, sex);
    }

    /**
     * Метод преобразования экземпляра класса User в строку.
     * @return строковое представление экземпляра класса User,
     * содержащее наименование его полей и их значения
     */
    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + ", age=" + age
                + ", weight=" + weight
                + ", height=" + height
                + ", goal=" + goal
                + ", sex=" + sex
                + '}';
    }

}
