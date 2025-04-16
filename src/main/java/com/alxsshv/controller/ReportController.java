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

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    private DayReportService dayReportService;

    @GetMapping("/history")
    public List<DayReportDto> getUserHistory(
            @RequestParam(value = "user", defaultValue = "0") long userId) {
        return dayReportService.getHistory(userId);
    }

    @GetMapping("/report")
    public DayReportDto getDayReport(
            @RequestParam(value = "user", defaultValue = "0") long userId,
            @RequestParam(value = "date", defaultValue = "-999999999-01-01") LocalDate date) {
        return dayReportService.getDayReport(userId, date);
    }


}
