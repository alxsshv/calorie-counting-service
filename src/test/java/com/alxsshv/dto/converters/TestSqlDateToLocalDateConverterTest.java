package com.alxsshv.dto.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDate;

@ActiveProfiles("test")
public class TestSqlDateToLocalDateConverterTest {
    private static final String DATE_TEMPLATE = "2025-04-25";
    private final SqlDateToLocalDateConverter converter = new SqlDateToLocalDateConverter();

    @Test
    @DisplayName("Test convertToDatabaseColumn when LocalDate not null"
            + "then return SqlDate")
    public void testConvertToDatabaseColumn_whenLocalDateNotNull_thenReturnSqlDate() {
        LocalDate localDate = LocalDate.parse(DATE_TEMPLATE);
        Date date = converter.convertToDatabaseColumn(localDate);
        Assertions.assertNotNull(date);
        Assertions.assertEquals(DATE_TEMPLATE, date.toString());
    }

    @Test
    @DisplayName("Test convertToDatabaseColumn when LocalDate null"
            + "then return null")
    public void testConvertToDatabaseColumn_whenLocalDateNull_thenReturnNull() {
        Assertions.assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    @DisplayName("Test convertToEntityAttribute when SQLDate not null "
            + "then return LocalDate")
    public void testConvertToEntityAttribute_whenSqlDateNotNull_thenReturnLocalDate() {
        Date sqlDate = Date.valueOf("2025-04-25");
        LocalDate localDate = converter.convertToEntityAttribute(sqlDate);
        Assertions.assertNotNull(localDate);
        Assertions.assertEquals(DATE_TEMPLATE, localDate.toString());
    }

    @Test
    @DisplayName("Test convertToEntityAttribute when SQLDate null "
            + "then return null")
    public void testConvertToEntityAttribute_whenSqlDateNull_thenReturnNull() {
        Assertions.assertNull(converter.convertToEntityAttribute(null));
    }

}
