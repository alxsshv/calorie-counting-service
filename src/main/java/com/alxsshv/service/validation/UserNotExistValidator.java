package com.alxsshv.service.validation;

import com.alxsshv.dto.UserDto;
import com.alxsshv.model.User;
import com.alxsshv.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserNotExistValidator implements ConstraintValidator<UserNotExist, UserDto> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> userOpt = userRepository.findByEmail(userDto.getEmail());
        return userOpt.isEmpty();
    }
}
