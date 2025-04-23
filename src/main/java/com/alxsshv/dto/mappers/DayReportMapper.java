package com.alxsshv.dto.mappers;

import com.alxsshv.dto.DayReportDto;
import com.alxsshv.model.DayReport;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DayReportMapper {
    /**Метод преобразования DayReportDto в сущность {@link DayReport}.
     * @param dayReportDto - объект передачи данных
     * для класса DayReport.
     * @return возвращает объект класса DayReport.*/
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DayReport toEntity(DayReportDto dayReportDto);

    /**Метод преобразования в сущности {@link DayReport} в DayReportDto.
     * @param dayReport - Объект класса DayReport (пользователь сервиса).
     * @return возвращает объект передачи данных
     * для класса DyaReport ({@link DayReportDto}) */
    DayReportDto toDto(DayReport dayReport);

    /**Метод преобразования списка (массива) сущностей {@link DayReport}
     * в список объектов класса DayReportDto.
     * @param dayReports - список (List) объектов класса DayReport (пользователь сервиса).
     * @return возвращает список (List) объектов передачи данных ({@link DayReportDto}) */
    List<DayReportDto> toDtoList(List<DayReport> dayReports);
}
