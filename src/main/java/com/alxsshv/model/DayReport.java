package com.alxsshv.model;

import com.alxsshv.dto.converters.SqlDateToLocalDateConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class DayReport {
    @Id
    @Convert(attributeName = "date", converter = SqlDateToLocalDateConverter.class)
    private LocalDate date;
    @Column(name = "food_intakes_number")
    private long foodIntakesNumber;
    @Column(name = "proteins_sum")
    private double proteinsSum;
    @Column(name = "fats_sum")
    private double fatsSum;
    @Column(name = "carbohydrates_sum")
    private double carbohydratesSum;
    @Column(name = "day_calorie_sum")
    private double dayCalorieSum;

    @Override
    public String toString() {
        return "DayReport{" +
                "date=" + date +
                ", foodIntakesNumber=" + foodIntakesNumber +
                ", proteinsSum=" + proteinsSum +
                ", fatsSum=" + fatsSum +
                ", carbohydratesSum=" + carbohydratesSum +
                ", dayCalorieSum=" + dayCalorieSum +
                '}';
    }
}
