package com.alxsshv.service.validation;

import com.alxsshv.dto.UserDto;
import com.alxsshv.model.User;
import com.alxsshv.repository.UserRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserIsNotExistValidatorTest {
    @Autowired
    private ConstraintValidatorContext context;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserNotExistValidator userNotExistValidator;

    @Test
    @DisplayName("Test isValid when user is not exist then return true")
    public void testIsValid_whenUserIsNotExist_thenReturnTrue() {
        String email = "email@email.com";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        Assertions.assertTrue(userNotExistValidator.isValid(userDto, context));
    }

    @Test
    @DisplayName("Test isValid when user is present then return false")
    public void testIsValid_whenUserIsPresent_thenReturnFalse() {
        String email = "email@email.com";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));
        Assertions.assertFalse(userNotExistValidator.isValid(userDto, context));
    }
}
