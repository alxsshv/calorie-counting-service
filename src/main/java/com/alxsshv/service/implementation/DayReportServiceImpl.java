package com.alxsshv.service.implementation;

import com.alxsshv.dto.DayReportDto;
import com.alxsshv.dto.mappers.DayReportMapper;
import com.alxsshv.model.DayReport;
import com.alxsshv.repository.DayReportRepository;
import com.alxsshv.service.DayReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DayReportServiceImpl implements DayReportService {
    @Autowired
    private DayReportRepository dayReportRepository;
    @Autowired
    private DayReportMapper dayReportMapper;

    @Override
    public DayReportDto getDayReport(long userId, LocalDate date) {
        DayReport dayReport = dayReportRepository.getDayReport(userId, date);
        return dayReportMapper.toDto(dayReport);
    }

    @Override
    public List<DayReportDto> getHistory(long userId) {
        List<DayReport> dayReports = dayReportRepository.getDayReportList(userId);
        return dayReportMapper.toDtoList(dayReports);
    }

    @Override
    public boolean isGoalAchieved(long userId, LocalDate date) {
        return false;
    }
}
