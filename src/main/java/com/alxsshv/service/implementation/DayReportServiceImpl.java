package com.alxsshv.service.implementation;

import com.alxsshv.dto.DayReportDto;
import com.alxsshv.dto.mappers.DayReportMapper;
import com.alxsshv.model.DayReport;
import com.alxsshv.repository.DayReportRepository;
import com.alxsshv.service.DayReportService;
import com.alxsshv.service.validation.IsValidDate;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@Validated
public class DayReportServiceImpl implements DayReportService {
    @Autowired
    private DayReportRepository dayReportRepository;
    @Autowired
    private DayReportMapper dayReportMapper;

    @Override
    public DayReportDto getDayReport(
            @Min(value = 1, message = "Некорректный идентификатор пользователя") final long userId,
            @IsValidDate final LocalDate date) {
        DayReport dayReport = dayReportRepository.getDayReport(userId, date);
        return dayReportMapper.toDto(dayReport);
    }

    @Override
    public List<DayReportDto> getHistory(
            @Min(value = 1, message = "Некорректный идентификатор пользователя") final long userId) {
        List<DayReport> dayReports = dayReportRepository.getDayReportList(userId);
        return dayReportMapper.toDtoList(dayReports);
    }

    @Override
    public boolean isGoalAchieved(
            @Min(value = 1, message = "Некорректный идентификатор пользователя") final long userId,
            @IsValidDate final LocalDate date) {
        return false;
    }
}
