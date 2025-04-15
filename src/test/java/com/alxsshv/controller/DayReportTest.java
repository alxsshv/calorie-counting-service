package com.alxsshv.controller;

import com.alxsshv.dto.DayReportDto;
import com.alxsshv.model.*;
import com.alxsshv.repository.DishRepository;
import com.alxsshv.repository.FoodIntakeRepository;
import com.alxsshv.repository.ServingSizeRepository;
import com.alxsshv.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DayReportTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FoodIntakeRepository foodIntakeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private ServingSizeRepository servingSizeRepository;

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
        User user1 = new User();
        user1.setName("John");
        user1.setAge(33);
        user1.setEmail("jhon@world.com");
        user1.setWeight(78);
        user1.setHeight(178);
        user1.setGoal(Goal.KEEPING_FIT);
        user1.setSex(Sex.MAN);
        user1.setCalorieNorm(1620);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("jane");
        user2.setAge(22);
        user2.setEmail("jane@world.com");
        user2.setWeight(78);
        user2.setHeight(178);
        user2.setGoal(Goal.KEEPING_FIT);
        user2.setSex(Sex.WOMAN);
        user2.setCalorieNorm(1310);
        userRepository.save(user2);

        Dish dish1 = new Dish();
        dish1.setTitle("Блин");
        dish1.setCalorieContent(200);
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
        food1.setAmount(100);

        FoodIntake foodIntake1 = new FoodIntake();
        foodIntake1.addServingSize(food1);
        foodIntake1.setDate(LocalDate.now());
        foodIntake1.setUser(user1);
        foodIntakeRepository.save(foodIntake1);

        ServingSize food2 = new ServingSize();
        food1.setDish(dish2);
        food1.setAmount(600);
        FoodIntake foodIntake2 = new FoodIntake();
        foodIntake2.addServingSize(food2);
        foodIntake2.setDate(LocalDate.now());
        foodIntake2.setUser(user1);
        foodIntakeRepository.save(foodIntake2);

        ServingSize food3 = new ServingSize();
        food1.setDish(dish1);
        food1.setAmount(300);
        FoodIntake foodIntake3 = new FoodIntake();
        foodIntake3.addServingSize(food3);
        foodIntake3.setDate(LocalDate.now().minusDays(1));
        foodIntake3.setUser(user1);
        foodIntakeRepository.save(foodIntake3);
    }

    @AfterEach
    public void clearDatabase() {
        foodIntakeRepository.deleteAll();
        servingSizeRepository.deleteAll();
        userRepository.deleteAll();
        dishRepository.deleteAll();
    }

    @Test
    public void testHistory() {
        long userId = userRepository.findByEmail("jhon@world.com").orElseThrow().getId();
        ResponseEntity<DayReportDto[]> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/report/history?user=" + userId, DayReportDto[].class);
        System.out.println(response.getBody());
        System.out.println(response.getBody().length);
        Arrays.stream(response.getBody()).peek(System.out::println).toList();
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
