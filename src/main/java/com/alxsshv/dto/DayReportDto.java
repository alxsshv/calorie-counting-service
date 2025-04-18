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
    private long foodIntakesNumber;
    private double proteinsSum;
    private double fatsSum;
    private double carbohydratesSum;
    private double dayCalorieSum;

    @Override
    public String toString() {
        return "DayReportDto{" +
                "date=" + date +
                ", foodIntakesNumber=" + foodIntakesNumber +
                ", proteinsSum=" + proteinsSum +
                ", fatsSum=" + fatsSum +
                ", carbohydratesSum=" + carbohydratesSum +
                ", dayCalorieSum=" + dayCalorieSum +
                '}';
    }
}
