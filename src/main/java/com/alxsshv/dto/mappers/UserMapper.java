package com.alxsshv.dto.mappers;

import com.alxsshv.dto.UserDto;
import com.alxsshv.model.Goal;
import com.alxsshv.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    /**Метод преобразования UserDto в сущность {@link User}.
     * @param userDto - объект передачи данных
     * для класса User ({@link UserDto}).
     * @return возвращает объект класса User.*/
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toEntity(UserDto userDto);

    /**Метод преобразования в сущности {@link User} в UserDto.
     * @param user - Объект класса User (пользователь сервиса).
     * @return возвращает объект передачи данных
     * для класса User ({@link UserDto}) */
    UserDto toUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDto dto, @MappingTarget User user);

//    @Named("setUserFoal")
//    default Goal setUserGoal(String goal) {
//        return Goal.valueOf(goal);
//    }
}
