package com.alxsshv.dto.mappers;

import com.alxsshv.dto.UserDto;
import com.alxsshv.model.Goal;
import com.alxsshv.model.Sex;
import com.alxsshv.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    /**Метод преобразования UserDto в сущность {@link User}.
     * @param userDto - объект передачи данных
     * для класса User ({@link UserDto}).
     * @return возвращает объект класса User.*/
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "goal", qualifiedByName = "setUserGoal", source = "goal")
    @Mapping(target = "sex", qualifiedByName = "setUserSex", source = "sex")
    User toEntity(UserDto userDto);

    /**Метод преобразования в сущности {@link User} в UserDto.
     * @param user - Объект класса User (пользователь сервиса).
     * @return возвращает объект передачи данных
     * для класса User ({@link UserDto}) */
    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> userList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "goal", qualifiedByName = "setUserGoal", source = "goal")
    @Mapping(target = "sex", qualifiedByName = "setUserSex", source = "sex")
    void updateUserFromDto(@MappingTarget User user, UserDto dto);

    /**Дефолтный метод для преобразования указанного строкового
     * псеводнима цели, цель из перечисления {@link Goal}*/
    @Named("setUserGoal")
    default Goal setUserGoal(String goal) {
        try {
            return Goal.valueOf(goal);
        } catch (IllegalArgumentException ex) {
            return Goal.valueOfPseudonym(goal);
        }
    }

    /**Дефолтный метод для преобразования указанного строкового
     * псеводнима пола, пол выбирается из перечисления {@link Sex}*/
    @Named("setUserSex")
    default Sex setUserSex(String sex) {
        try {
            return Sex.valueOf(sex);
        } catch (IllegalArgumentException ex) {
            return Sex.valueOfPseudonym(sex);
        }
    }
}
