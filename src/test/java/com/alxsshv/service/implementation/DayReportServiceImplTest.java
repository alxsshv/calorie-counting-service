package com.alxsshv.service.implementation;

import com.alxsshv.dto.DayReportDto;
import com.alxsshv.dto.mappers.DayReportMapper;
import com.alxsshv.model.DayReport;
import com.alxsshv.repository.DayReportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DayReportServiceImplTest {
    @Mock
    private DayReportRepository reportRepository;
    @Mock
    private DayReportMapper dayReportMapper;
    @InjectMocks
    private DayReportServiceImpl  dayReportService;

    @Test
    @DisplayName("Test getDayReport when method called then return instance of DayReportDto.class")
    public void testGetDayReport_whenMethodCalled_thenReturnInstanceOfDayReportDto() {
        final long userId = 1L;
        final LocalDate date = LocalDate.now();
        final DayReport dayReport = new DayReport();
        Mockito.when(reportRepository.getDayReport(userId, date)).thenReturn(dayReport);
        Mockito.when(dayReportMapper.toDto(dayReport)).thenReturn(new DayReportDto());
        Assertions.assertNotNull(dayReportService.getDayReport(userId,date));
        Assertions.assertInstanceOf(DayReportDto.class, dayReportService.getDayReport(userId,date));
    }

    @Test
    @DisplayName("Test getHistory when method called then return not empty list")
    public void testGetHistory_whenMethodCalled_thenReturnNotEmptyList() {
        final long userId = 1L;
        final int expectedListSize = 2;
        final List<DayReport> reports = List.of(new DayReport(), new DayReport());
        Mockito.when(reportRepository.getDayReportList(userId))
                .thenReturn(reports);
        Mockito.when(dayReportMapper.toDtoList(reports))
                .thenReturn(List.of(new DayReportDto(), new DayReportDto()));
        Assertions.assertNotNull(dayReportService.getHistory(userId));
        Assertions.assertEquals(expectedListSize,
                dayReportService.getHistory(userId).size());
    }

    @Test
    @DisplayName("Test isGoalAchieved when repository return true then return true")
    public void testIsGoalAchieved_whenRepositoryReturnTrue_thenReturnTrue() {
        final long userId = 1L;
        final LocalDate date = LocalDate.now();
        Mockito.when(reportRepository.isGoalAchieved(userId, date)).thenReturn(true);
        Assertions.assertTrue(dayReportService.isGoalAchieved(userId, date));
    }

    @Test
    @DisplayName("Test isGoalAchieved when repository return false then return false")
    public void testIsGoalAchieved_whenRepositoryReturnFalse_thenReturnFalse() {
        final long userId = 1L;
        final LocalDate date = LocalDate.now();
        Mockito.when(reportRepository.isGoalAchieved(userId, date)).thenReturn(false);
        Assertions.assertFalse(dayReportService.isGoalAchieved(userId, date));
    }
}
