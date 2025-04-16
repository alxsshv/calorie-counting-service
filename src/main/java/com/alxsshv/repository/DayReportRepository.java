package com.alxsshv.repository;

import com.alxsshv.model.DayReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DayReportRepository extends JpaRepository<DayReport, LocalDate> {
    @Query(
            value = "select date as \"date\", " +
                    "SUM(amount * calorie_content / 100) as \"day_calorie_sum\", " +
                    "COUNT(ss.id ) as \"food_intakes_count\"\n" +
                    "from serving_sizes ss \n" +
                    "join dishes d on (d.id = ss.dish_id)\n" +
                    "join food_intakes fi on (fi.id = ss.food_intake_id) where user_id = ?1 group by date;",
            nativeQuery = true
    )
    List<DayReport> getDayReportList(long userId);

    @Query(
            value = "select date as \"date\", SUM(amount * calorie_content / 100)" +
                    " as \"day_calorie_sum\", COUNT(ss.id ) as \"food_intakes_count\"\n" +
                    "from serving_sizes ss \n" +
                    "join dishes d on (d.id = ss.dish_id)\n" +
                    "join food_intakes fi on (fi.id = ss.food_intake_id)" +
                    "where (user_id = ?1 and date = ?2) group by date;",
            nativeQuery = true
    )
    DayReport getDayReport(long userId, LocalDate date);

    @Query(
            value = "select SUM(amount * d.calorie_content / 100)\n" +
                    "from serving_sizes ss\n" +
                    "join dishes d on (d.id = ss.dish_id)\n" +
                    "join food_intakes fi on (user_id = ?1 and DATE(date) = ?2); \n",
            nativeQuery = true)
    double getCalorieSum(long userId, LocalDate date);




}
