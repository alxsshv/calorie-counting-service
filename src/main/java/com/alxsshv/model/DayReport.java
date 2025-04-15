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
    @Column(name = "food_intakes_count")
    private long foodIntakesCount;
    @Column(name = "day_calorie_sum")
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
