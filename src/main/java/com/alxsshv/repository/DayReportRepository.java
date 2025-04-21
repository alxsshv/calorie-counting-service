package com.alxsshv.repository;

import com.alxsshv.model.DayReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
/**Интерфейс, описывающий методы получения
 *  отчетов о питании пользователя из БД*/
public interface DayReportRepository
        extends JpaRepository<DayReport, LocalDate> {

    /**Метод получения истории о питании пользователя.
     * @param userId - идентификатор пользователя (long).
     * @return возвращает список (List) объектов класса {@link DayReport}.
     * */
    @Query(
            value = "select fi.\"date\" as \"date\","
                   + "count(ss.id) as \"food_intakes_number\","
                   + "SUM(amount * d.proteins_amount / 100) as \"proteins_sum\","
                   + "SUM(amount * d.fats_amount / 100) as \"fats_sum\","
                   + "SUM(amount * d.carbohydrates_amount / 100) as \"carbohydrates_sum\","
                   + "SUM(amount * d.calorie_content / 100) as \"day_calorie_sum\""
                   + "from serving_sizes ss "
                   + "join dishes d on (d.id = ss.dish_id)"
                   + "join food_intakes fi on (fi.id = ss.food_intake_id) "
                   + "where (user_id = ?1) group by \"date\" ",
            nativeQuery = true
    )
    List<DayReport> getDayReportList(long userId);

    /**Метод получения отчета о питании пользователя за определенную дату.
     * @param userId - числовой идентификатор пользователя (long).
     * @param date - дата за которую необходимо получить
     * отчет о питании пользователя.
     * @return возвращает объект класса {@link DayReport}.*/
    @Query(
            value = "select fi.\"date\" as \"date\","
                   + "count(ss.id) as \"food_intakes_number\","
                   + "SUM(amount * d.proteins_amount / 100) as \"proteins_sum\","
                   + "SUM(amount * d.fats_amount / 100) as \"fats_sum\","
                   + "SUM(amount * d.carbohydrates_amount / 100) as \"carbohydrates_sum\","
                   + "SUM(amount * d.calorie_content / 100) as \"day_calorie_sum\""
                   + "from serving_sizes ss "
                   + "join dishes d on (d.id = ss.dish_id)"
                   + "join food_intakes fi on (fi.id = ss.food_intake_id) "
                   + "where (user_id = ?1 and date = ?2) group by \"date\";",
            nativeQuery = true
    )
    DayReport getDayReport(long userId, LocalDate date);

    /**Метод проверки не превысил ли пользователь в дневную норму калорий.
     * @param userId - числовой идентификатор пользователя (long).
     * @param date - дата для которой необходимо выполнить проверку.
     * @return возвращает true если суммарное количество калорий,
     * полученных пользователем меньше или равно дневной норме
     * калорий пользователя, false если пользователь превысил
     * дневную норму калорий.
     *  */
    @Query(
            value = "select SUM(amount * calorie_content / 100) <= su.calorie_norm "
                   + "from serving_sizes ss "
                   + "join dishes d on (d.id = ss.dish_id)"
                   + "join food_intakes fi on (fi.id = ss.food_intake_id)"
                   + "join service_users su on (user_id = su.id )"
                   + "where (user_id = ?1 and date = ?2) group by su.calorie_norm;",
            nativeQuery = true
    )
    boolean isGoalAchieved(long userId, LocalDate date);

}
