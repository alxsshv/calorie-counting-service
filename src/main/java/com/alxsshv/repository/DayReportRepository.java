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
                    "COUNT(ss.id ) as \"food_intakes_count\"" +
                    "from serving_sizes ss " +
                    "join dishes d on (d.id = ss.dish_id)" +
                    "join food_intakes fi on (fi.id = ss.food_intake_id) " +
                    "where user_id = ?1 group by date;",
            nativeQuery = true
    )
    List<DayReport> getDayReportList(long userId);

    @Query(
            value = "select  SUM(amount * calorie_content / 100) <= su.calorie_norm" +
                    "from serving_sizes ss" +
                    "join dishes d on (d.id = ss.dish_id)" +
                    "join food_intakes fi on (fi.id = ss.food_intake_id)" +
                    "join service_users su on (user_id = su.id )" +
                    "where (user_id = ?1 and date = ?2) group by su.calorie_norm; ",
            nativeQuery = true
    )
    DayReport getDayReport(long userId, LocalDate date);

    @Query(
            value = "select date as \"date\", SUM(amount * calorie_content / 100)" +
                    " as \"day_calorie_sum\", COUNT(ss.id ) as \"food_intakes_count\"" +
                    "from serving_sizes ss " +
                    "join dishes d on (d.id = ss.dish_id)" +
                    "join food_intakes fi on (fi.id = ss.food_intake_id)" +
                    "where (user_id = ?1 and date = ?2) group by date;",
            nativeQuery = true
    )
    boolean isGoalAchieved(long userId, LocalDate date);

}
