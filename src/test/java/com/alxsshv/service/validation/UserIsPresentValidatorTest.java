package com.alxsshv.service.validation;

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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserIsPresentValidatorTest {
    @Autowired
    private ConstraintValidatorContext context;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserIsPresentValidator userIsPresentValidator;

    @Test
    @DisplayName("Test isValid when user is present then return true")
    public void testIsValid_whenUserIsPresent_thenReturnTrue() {
        long userId = 1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        Assertions.assertTrue(userIsPresentValidator.isValid(userId, context));
    }

    @Test
    @DisplayName("Test isValid when user not exist then return true")
    public void testIsValid_whenUserNotExist_thenReturnTrue() {
        long userId = 1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Assertions.assertFalse(userIsPresentValidator.isValid(userId, context));
    }
}
