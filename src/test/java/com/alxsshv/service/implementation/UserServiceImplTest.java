package com.alxsshv.service.implementation;

import com.alxsshv.dto.UserDto;
import com.alxsshv.dto.mappers.UserMapper;
import com.alxsshv.exception.DataProcessingException;
import com.alxsshv.model.User;
import com.alxsshv.repository.UserRepository;
import com.alxsshv.service.utils.BmrCalculator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BmrCalculator bmrCalculator;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Test createUser when method called then user created")
    public void testCreateUser_whenMethodCalled_thenUserCreated() {
        double expectedCalorieNorm = 1200.0d;
        final UserDto userDto = new UserDto();
        final User user = new User();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(bmrCalculator.calculate(user)).thenReturn(expectedCalorieNorm);
        userService.createUser(userDto);
        Assertions.assertEquals(expectedCalorieNorm, user.getCalorieNorm(), 0.0001);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Test getById when user found then return user")
    public void testGetById_whenUserFound_thenReturn() {
        final long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        final User user = userService.getById(userId);
        Assertions.assertNotNull(user);
    }

    @Test
    @DisplayName("Test getById when user not found then throw exception")
    public void testGetById_whenUserNotFound_thenThrow() {
        final long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    @DisplayName("Test findById when user found then return userDto")
    public void testFindById_whenUserFound_thenReturnUserDto() {
        final long userId = 1L;
        final User user = new User();
        final UserDto userDto = new UserDto();
        userDto.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        final UserDto result = userService.findById(userId);
        Assertions.assertEquals(userId, result.getId());
    }

    @Test
    @DisplayName("Test findById when user not found then throw exception")
    public void testFindById_whenUserNotFound_thenThrow() {
        final long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    @DisplayName("Test findAll when callMethod then return userDto list")
    public void testFindAll_whenCallMethod_thenReturnUserDtoList() {
        final List<User> users = List.of(new User(), new User());
        final List<UserDto> expectedUserDtoList = List.of(new UserDto(), new UserDto());
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toUserDtoList(users)).thenReturn(expectedUserDtoList);
        final List<UserDto> actualUserDtoList = userService.findAll();
        Assertions.assertEquals(expectedUserDtoList.size(), actualUserDtoList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test update when user found and email no changed then update success")
    public void testUpdate_whenUserFoundAndEmailNoChanged_thenUpdateSuccess() {
        final String email = "email@world.com";
        final long userId = 1L;
        final UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setEmail(email);
        final User user = new User();
        user.setEmail(email);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.update(userDto);
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).updateUserFromDto(user, userDto);
    }

    @Test
    @DisplayName("Test update when user found and email changed"
            + "but email no match from db then update success")
    public void testUpdate_whenUserFoundAndEmailChangedButEmailNoMatchFromDb_thenUpdateSuccess() {
        final long userId = 1L;
        final UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setEmail("newemail@mail.com");
        final User user = new User();
        user.setEmail("oldemail@mail.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        userService.update(userDto);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
        verify(userMapper, times(1)).updateUserFromDto(user, userDto);
    }

    @Test
    @DisplayName("Test update when user found and email changed"
            + "but email match with another user from db thenThrowException")
    public void testUpdate_whenUserFoundAndEmailChangedButEmailMatchWithAnotherUser_thenThrowException() {
        final long userId = 1L;
        final UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setEmail("newemail@mail.com");
        final User user = new User();
        user.setEmail("oldemail@mail.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(new User()));
        Assertions.assertThrows(DataProcessingException.class, () -> userService.update(userDto));
        verify(userMapper, never()).updateUserFromDto(user, userDto);
    }

    @Test
    @DisplayName("Test deleteById when user found then delete success")
    public void testDeleteById_whenUserFound_thenDeleteSuccess() {
        final long userId = 1L;
        final User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.deleteById(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Test deleteById when user not found then throw exception")
    public void testDeleteById_whenUserNotFound_thenThrow() {
        final long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.deleteById(userId));
    }
}
