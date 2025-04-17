package com.alxsshv.service;

import com.alxsshv.dto.DayReportDto;
import com.alxsshv.service.validation.IsValidDate;
import com.alxsshv.service.validation.UserIsPresent;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.util.List;

public interface DayReportService {
    DayReportDto getDayReport(
            @UserIsPresent  final long userId,
            @IsValidDate final LocalDate date);
    List<DayReportDto> getHistory(
            @UserIsPresent long userId);
    boolean isGoalAchieved(
            @UserIsPresent  final long userId,
            @IsValidDate final LocalDate date);
}
