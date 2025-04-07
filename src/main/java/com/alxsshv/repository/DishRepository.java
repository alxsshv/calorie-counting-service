package com.alxsshv.repository;

import com.alxsshv.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish,Long> {
    Optional<Dish> findByTitle(String title);
}
