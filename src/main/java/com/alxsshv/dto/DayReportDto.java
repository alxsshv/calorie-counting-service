package com.alxsshv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DayReportDto {
    private LocalDate date;
    private long foodIntakesCount;
    private double dayCalorieSum;

    @Override
    public String toString() {
        return "DayReport{" +
                "date=" + date +
                ", foodIntakesCount=" + foodIntakesCount +
                ", dayCalorieSum=" + dayCalorieSum +
                '}';
    }
}
