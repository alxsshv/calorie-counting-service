package com.alxsshv.repository;

import com.alxsshv.model.DayReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DayReportRepository extends JpaRepository<DayReport, LocalDate> {
    @Query(
            value = "select a.\"date\", a.\"food_intakes_count\" , b.\"day_calorie_sum\" from  \n" +
                    "(select fi.date as \"date\", COUNT(id) as \"food_intakes_count\"\n" +
                    "from food_intakes fi where (user_id = ?1)\n" +
                    "group by date) as a \n" +
                    "join\n" +
                    "(select date, SUM(amount * d.calorie_content / 100) as \"day_calorie_sum\"\n" +
                    "from serving_sizes ss\n" +
                    "join dishes d on (d.id = ss.dish_id)\n" +
                    "join food_intakes fi on (user_id = ?1)\n" +
                    "group by date) as b\n" +
                    "on b.date = a.date;",
            nativeQuery = true
    )
    List<DayReport> getDayReportList(long userId);

    @Query(
            value = "select a.\"date\", a.\"food_intakes_count\" , b.\"day_calorie_sum\" from  \n" +
                    "(select fi.date as \"date\", COUNT(id) as \"food_intakes_count\"\n" +
                    "from food_intakes fi where (user_id = ?1 and date = ?2)) as a \n" +
                    "join\n" +
                    "(select date, SUM(amount * d.calorie_content / 100) as \"day_calorie_sum\"\n" +
                    "from serving_sizes ss\n" +
                    "join dishes d on (d.id = ss.dish_id)\n" +
                    "join food_intakes fi on (user_id = ?1 and date = ?2)) as b\n" +
                    "on b.date = a.date;",
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
