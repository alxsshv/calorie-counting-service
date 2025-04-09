package com.alxsshv.controller;

import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.dto.ServingSizeDto;
import com.alxsshv.dto.mappers.DishMapper;
import com.alxsshv.model.*;
import com.alxsshv.repository.DishRepository;
import com.alxsshv.repository.FoodIntakeRepository;
import com.alxsshv.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FoodIntakeControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FoodIntakeRepository foodIntakeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishMapper dishMapper;

    private static final PostgreSQLContainer<?> POSTGRES
            = new PostgreSQLContainer<>("postgres:17");
    private final RestTemplate template = new TestRestTemplate().getRestTemplate();

    @DynamicPropertySource
    private static void setup(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @BeforeAll
    public static void startDatabase() {
        POSTGRES.start();
    }

    @AfterAll
    public static void stopDatabase() {
        POSTGRES.stop();
    }

    @BeforeEach
    public void fillDatabase() {
        User user = new User();
        user.setName("John");
        user.setAge(33);
        user.setEmail("jhon@world.com");
        user.setWeight(78);
        user.setHeight(178);
        user.setGoal(Goal.KEEPING_FIT);
        userRepository.save(user);

        Dish dish1 = new Dish();
        dish1.setTitle("Блин");
        dish1.setCalorieContent(100);
        dish1.setProteinsAmount(1);
        dish1.setFatsAmount(1);
        dish1.setCarbohydratesAmount(1);
        dishRepository.save(dish1);

        Dish dish2 = new Dish();
        dish2.setTitle("Суп");
        dish2.setCalorieContent(200);
        dish2.setProteinsAmount(2);
        dish2.setFatsAmount(2);
        dish2.setCarbohydratesAmount(2);
        dishRepository.save(dish2);


        ServingSize food1 = new ServingSize();
        food1.setDish(dish1);
        food1.setAmount(400);

        FoodIntake foodIntake = new FoodIntake();
        foodIntake.addServingSize(food1);
        foodIntake.setDate(LocalDate.now());
        foodIntake.setUser(user);
        foodIntakeRepository.save(foodIntake);
    }

    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
        dishRepository.deleteAll();
        foodIntakeRepository.deleteAll();
    }

    @Test
    @DisplayName("Test addFoodIntake when send valid data then get status created (201)")
    public void testAddFoodIntake_whenSendValidData_thenGet201() {
        ServingSizeDto food1 = new ServingSizeDto();
        food1.setDish(dishMapper.toDishDto(dishRepository.findByTitle("Блин").orElseThrow()));
        food1.setAmount(500);

        FoodIntakeDto foodIntakeDto = new FoodIntakeDto();
        foodIntakeDto.setServingSizes(List.of(food1));
        foodIntakeDto.setDate(LocalDate.now());
        foodIntakeDto.setUserId(userRepository.findByEmail("jhon@world.com").orElseThrow().getId());

        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/food", foodIntakeDto, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED));
    }
}
