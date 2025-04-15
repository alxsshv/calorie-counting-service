package com.alxsshv.dto.mappers;

import com.alxsshv.dto.DayReportDto;
import com.alxsshv.dto.UserDto;
import com.alxsshv.model.DayReport;
import com.alxsshv.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DayReportMapper {
    /**Метод преобразования DayReportDto в сущность {@link DayReport}.
     * @param dayReportDto - объект передачи данных
     * для класса DayReport.
     * @return возвращает объект класса DayReport.*/
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DayReport toEntity(DayReportDto dayReportDto);

    /**Метод преобразования в сущности {@link DayReport} в DayReportDto.
     * @param dayReport - Объект класса DayReport (пользователь сервиса).
     * @return возвращает объект передачи данных
     * для класса DyaReport ({@link DayReportDto}) */
    DayReportDto toDto(DayReport dayReport);

    List<DayReportDto> toDtoList(List<DayReport> dayReports);
}
