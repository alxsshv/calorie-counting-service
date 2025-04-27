package com.alxsshv.service.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@ActiveProfiles("test")
public class DateValidatorTest {
    @Autowired
    private ConstraintValidatorContext context;
    private final DateValidator dateValidator = new DateValidator();

    @Test
    @DisplayName("Test isValid when date after LocalDate.EPOCH then return true")
    public void testIsValid_whenDateAfterDateEpoch_thenReturnTrue() {
        LocalDate testDate = LocalDate.EPOCH.plusDays(1);
        Assertions.assertTrue(dateValidator.isValid(testDate, context));
    }

    @Test
    @DisplayName("Test isValid when date equals LocalDate.EPOCH then return false")
    public void testIsValid_whenDateEqualsDateEpoch_thenReturnTrue() {
        LocalDate testDate = LocalDate.EPOCH;
        Assertions.assertFalse(dateValidator.isValid(testDate, context));
    }

    @Test
    @DisplayName("Test isValid when date before LocalDate.EPOCH then return false")
    public void testIsValid_whenDateBeforeDateEpoch_thenReturnTrue() {
        LocalDate testDate = LocalDate.EPOCH.minusDays(1);
        Assertions.assertFalse(dateValidator.isValid(testDate, context));
    }


}
