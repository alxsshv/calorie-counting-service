package com.alxsshv.service.validation;

import com.alxsshv.model.User;
import com.alxsshv.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public class UserIsPresentValidator implements ConstraintValidator<UserIsPresent, Long> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.isPresent();
    }
}
