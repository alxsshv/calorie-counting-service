package com.alxsshv.controller;

import com.alxsshv.dto.DayReportDto;
import com.alxsshv.service.DayReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**Клас, предназначенный для обработки запросов на выдачу
 * отчётов о питании пользователя.
 * @author Шварёв Алексей
 * @version 1.0*/
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    /**Сервисный слой, реализующий логику обработки запросов
     *  в части формирования отчетов.*/
    @Autowired
    private DayReportService dayReportService;

    /**Метод для получения истории питания пользователя по датам.
     * @param userId - id пользователя (long).
     * @return возвращает список (массив) объектов передачи
     * данных {@link DayReportDto}*/
    @GetMapping("/history")
    public List<DayReportDto> getNutritionHistory(
            @RequestParam(value = "user", defaultValue = "0") final long userId) {
        return dayReportService.getHistory(userId);
    }

    /**Метод для обработки запросов на получение отчета о питании
     * пользователя на указанную дату.
     * @param userId - id пользователя (long).
     * @param date  - дата для которой необходимо вернуть отчет о питании.
     * @return объект передачи данных {@link DayReportDto}*/
    @GetMapping
    public DayReportDto getDayReport(
            @RequestParam(value = "user", defaultValue = "0") final long userId,
            @RequestParam(value = "date", defaultValue = "-999999999-01-01") final LocalDate date) {
        return dayReportService.getDayReport(userId, date);
    }

    /**Метод для обработк запросов на выполнение проверки
     *уложился ли пользователь в дневную норму калорий.
     * @param userId - id пользователя (long), если значение не указано в запросе
     * используется дефолтное значение 0.
     * @param date  - дата для которой необходимо вернуть
     * отчет о питании (в формете ГГГГ-ММ-ДД).
     * если значение не указано в запросе, используется значение
     * по умолчанию -999999999-01-01.
     * @return булевое значение: true - если пользователь уложился
     * в дневную норму каллорий (цель достигнута), false - если цель не достигнута.*/
    @GetMapping("/results")
    public boolean isGoalAchieved(
            @RequestParam(value = "user", defaultValue = "0") final long userId,
            @RequestParam(value = "date", defaultValue = "-999999999-01-01") final LocalDate date) {
        return dayReportService.isGoalAchieved(userId, date);
    }
}
