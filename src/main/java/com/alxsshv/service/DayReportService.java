package com.alxsshv.service;

import com.alxsshv.dto.DayReportDto;
import com.alxsshv.service.validation.IsValidDate;
import com.alxsshv.service.validation.UserIsPresent;

import java.time.LocalDate;
import java.util.List;

/**Интерфейс, описывающий методы работы
 * с сущностью {@link com.alxsshv.model.DayReport}
 * (Отчет о питании за день).
 * @author Шварёв Алексей
 * @version 1.0*/
public interface DayReportService {

    /**Метод поиска записи о пользователе
     *  по идентификатору пользователя и дате.
     * @param userId - идентификатор пользователя,
     * для которого формируется отчет за день, в формате long.
     * @param date -  дата на которую формируется
     * отчет о питании пользователя.
     * @return возвращает объект класса {@link DayReportDto}.
     */
    DayReportDto getDayReport(
            @UserIsPresent long userId,
            @IsValidDate LocalDate date);

    /**Метод для получения истории питания пользователя по датам.
     * @param userId - числовой идентификатор пользователя,
     * для которого необходимо получить историю питания
     * @return возвращает список (массив) объектов {@link DayReportDto},
     * если записей нет - возвращается пустой список*/
    List<DayReportDto> getHistory(
            @UserIsPresent long userId);

    /**Метод для проверки уложился ли пользователь в дневную норму калорий.
     * @param userId - числовой идентификатор пользователя,
     * для которого необходимо выполнить проверку.
     * @param date - дата на которую необходимо выполнить проверку.
     * @return возвращает true, если пользователь уложился
     * в дневную норму калорий (количество потребленных калорий
     * меньше или равно дневной нормы калорий,
     * рассчитанной для пользователя),
     * возвращает false, если пользователь потребил больше калорий,
     * чем его дневная норма. */
    boolean isGoalAchieved(
            @UserIsPresent long userId,
            @IsValidDate LocalDate date);
}
