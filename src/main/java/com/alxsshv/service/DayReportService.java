package com.alxsshv.service;

import com.alxsshv.dto.DayReportDto;

import java.time.LocalDate;
import java.util.List;

public interface DayReportService {
    DayReportDto getDayReport(long userId, LocalDate date);
    List<DayReportDto> getHistory(long user);
    boolean isGoalAchieved(long userId, LocalDate date);
}
