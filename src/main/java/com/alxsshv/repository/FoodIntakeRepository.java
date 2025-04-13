package com.alxsshv.repository;

import com.alxsshv.model.FoodIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodIntakeRepository extends JpaRepository<FoodIntake, Long> {
    List<FoodIntake> findByUserIdAndDate(Long userId, LocalDate date);

    @Query(
            value = "select SUM(amount * d.calorie_content)\n" +
                    "from serving_sizes ss\n" +
                    "join dishes d on (d.id = ss.dish_id)\n" +
                    "join food_intakes fi on (user_id = ?1 and DATE(date) = ?2); \n",
            nativeQuery = true)
    double getSum(long userId, LocalDate date);

    @Query(
            value = "select COUNT(id)\n" +
                    "from food_intakes fi where  (user_id = ?1 and DATE(date) = ?2); ",
            nativeQuery = true
    )
    int getCount(long userId, LocalDate date);
}
