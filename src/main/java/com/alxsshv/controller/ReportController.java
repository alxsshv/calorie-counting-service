package com.alxsshv.controller;

import com.alxsshv.dto.DayReport;
import com.alxsshv.service.implementation.FoodIntakeServiceImpl;
import lombok.Getter;
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
    private FoodIntakeServiceImpl service;

    @GetMapping("/sum")
    public double getUserCalorie(
            @RequestParam(value = "user", defaultValue = "0") long userId,
            @RequestParam(value = "date", defaultValue = "-999999999-01-01") LocalDate date) {
        return service.getFoodInCalorieSum(userId, date);
    }

    @GetMapping("/count")
    public double getUserCount(
            @RequestParam(value = "user", defaultValue = "0") long userId,
            @RequestParam(value = "date", defaultValue = "-999999999-01-01") LocalDate date) {
        return service.getFoodInCount(userId, date);
    }

    @GetMapping("/history")
    public List<DayReport> getUserHistory(
            @RequestParam(value = "user", defaultValue = "0") long userId) {
        return service.getHistory(userId);
    }

}
