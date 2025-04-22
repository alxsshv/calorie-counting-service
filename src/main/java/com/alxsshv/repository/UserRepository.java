package com.alxsshv.repository;

import com.alxsshv.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**Интерфейс, описывающий CRUD методы
 * работы с сущностью {@link User}.*/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**Метод для получения пользователя по адресу электронной почты.
     * @param email - адрес электронной почты пользователя (String).
     * @return возвращает Optional, содержащий объект класса {@link User},
     * если пользователь с указанным email найден
     * или пустой Optional если пользователь не найден.*/
    Optional<User> findByEmail(String email);
}
