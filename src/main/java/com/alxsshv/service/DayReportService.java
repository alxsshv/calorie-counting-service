package com.alxsshv.service;

import com.alxsshv.dto.DayReportDto;
import com.alxsshv.service.validation.IsValidDate;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.util.List;

public interface DayReportService {
    DayReportDto getDayReport(
            @Min(value = 1, message = "Некорректный идентификатор пользователя") final long userId,
            @IsValidDate final LocalDate date);
    List<DayReportDto> getHistory(
            @Min(value = 1, message = "Некорректный идентификатор пользователя") long userId);
    boolean isGoalAchieved(
            @Min(value = 1, message = "Некорректный идентификатор пользователя") final long userId,
            @IsValidDate final LocalDate date);
}
