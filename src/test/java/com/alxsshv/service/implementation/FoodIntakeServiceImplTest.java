package com.alxsshv.service.implementation;

import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.dto.mappers.FoodIntakeMapper;
import com.alxsshv.model.FoodIntake;
import com.alxsshv.repository.FoodIntakeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class FoodIntakeServiceImplTest {
    @Mock
    private FoodIntakeRepository foodIntakeRepository;
    @Mock
    private FoodIntakeMapper foodIntakeMapper;
    @InjectMocks
    private FoodIntakeServiceImpl foodIntakeService;

    @Test
    @DisplayName("Test createFoodIntake when method called then creation success")
    public void createFoodIntake_whenMethodCalled_thenCreationSuccess() {
        FoodIntakeDto foodIntakeDto = new FoodIntakeDto();
        when(foodIntakeMapper.toEntity(foodIntakeDto))
                .thenReturn(new FoodIntake());
        foodIntakeService.createFoodIntake(foodIntakeDto);
        verify(foodIntakeRepository, times(1))
                .save(any(FoodIntake.class));
    }

    @Test
    @DisplayName("Test findAllByUserIdAndDate when method called then return not empty foodIntake list")
    public void testFindAllByUserIdAndDate_whenMethodCalled_thenReturnNotEmptyList() {
        long userId = 1L;
        LocalDate date = LocalDate.now();
        List<FoodIntake> foodIntakeList = List.of(new FoodIntake(), new FoodIntake());
        List<FoodIntakeDto> expectedDtos = List.of(new FoodIntakeDto(), new FoodIntakeDto());
        when(foodIntakeRepository.findByUserIdAndDate(userId,date)).thenReturn(foodIntakeList);
        when(foodIntakeMapper.toFoodIntakeDtoList(foodIntakeList)).thenReturn(expectedDtos);
        List<FoodIntakeDto> actualDtos = foodIntakeService.findAllByUserIdAndDate(userId, date);
        Assertions.assertEquals(expectedDtos.size(), actualDtos.size());
        verify(foodIntakeRepository, times(1))
                .findByUserIdAndDate(userId, date);
    }

    @Test
    @DisplayName("Test getById when foodIntake found then return foodIntake")
    public void testGetById_whenFoodIntakeFound_thenReturnFoodIntake() {
        long foodIntakeId = 1L;
        when(foodIntakeRepository.findById(foodIntakeId))
                .thenReturn(Optional.of(new FoodIntake()));
        Assertions.assertNotNull(foodIntakeService.getById(foodIntakeId));
        Assertions.assertInstanceOf(FoodIntake.class, foodIntakeService.getById(foodIntakeId));
    }

    @Test
    @DisplayName("Test getById when foodIntake not found then throw exception")
    public void testGetById_whenFoodIntakeNotFound_thenThrowException() {
        long foodIntakeId = 1L;
        when(foodIntakeRepository.findById(foodIntakeId))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> foodIntakeService.getById(foodIntakeId));
    }

    @Test
    @DisplayName("Test findById when foodIntake found then return foodIntakeDto")
    public void testFindById_whenFoodIntakeFound_thenReturnFoodIntakeDto() {
        long foodIntakeId = 1L;
        when(foodIntakeRepository.findById(foodIntakeId))
                .thenReturn(Optional.of(new FoodIntake()));
        when(foodIntakeMapper.toFoodIntakeDto(any(FoodIntake.class)))
                .thenReturn(new FoodIntakeDto());
        Assertions.assertNotNull(foodIntakeService.findById(foodIntakeId));
        Assertions.assertInstanceOf(FoodIntakeDto.class, foodIntakeService.findById(foodIntakeId));
    }

    @Test
    @DisplayName("Test findById when foodIntake not found then throw exception")
    public void testFindById_whenFoodIntakeNotFound_thenThrowException() {
        long foodIntakeId = 1L;
        when(foodIntakeRepository.findById(foodIntakeId))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> foodIntakeService.findById(foodIntakeId));
    }

    @Test
    @DisplayName("Test findById when foodIntake found then delete success")
    public void testFindById_whenFoodIntakeFound_thenDeleteSuccess() {
        long foodIntakeId = 1L;
        when(foodIntakeRepository.findById(foodIntakeId))
                .thenReturn(Optional.of(new FoodIntake()));
        foodIntakeService.deleteById(foodIntakeId);
        verify(foodIntakeRepository, times(1)).delete(any(FoodIntake.class));
    }

    @Test
    @DisplayName("Test deleteById when foodIntake not found then throw exception")
    public void testDeleteById_whenFoodIntakeNotFound_thenThrowException() {
        long foodIntakeId = 1L;
        when(foodIntakeRepository.findById(foodIntakeId))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> foodIntakeService.deleteById(foodIntakeId));
        verify(foodIntakeRepository, never()).delete(any(FoodIntake.class));
    }


}
