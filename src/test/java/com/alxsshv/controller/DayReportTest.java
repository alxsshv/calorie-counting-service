package com.alxsshv.controller;

import com.alxsshv.model.Goal;
import com.alxsshv.model.Sex;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;

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
        user1.setCalorieNorm(5020);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("jane");
        user2.setAge(22);
        user2.setEmail("jane@world.com");
        user2.setWeight(78);
        user2.setHeight(178);
        user2.setGoal(Goal.KEEPING_FIT);
        user2.setSex(Sex.WOMAN);
        user2.setCalorieNorm(10);
        userRepository.save(user2);

        Dish dish1 = new Dish();
        dish1.setTitle("Блин");
        dish1.setCalorieContent(200);
        dish1.setProteinsAmount(10);
        dish1.setFatsAmount(20);
        dish1.setCarbohydratesAmount(40);
        dishRepository.save(dish1);

        Dish dish2 = new Dish();
        dish2.setTitle("Суп");
        dish2.setCalorieContent(400);
        dish2.setProteinsAmount(20);
        dish2.setFatsAmount(40);
        dish2.setCarbohydratesAmount(80);
        dishRepository.save(dish2);

        ServingSize food1 = new ServingSize();
        food1.setDish(dish2);
        food1.setAmount(600);

        FoodIntake foodIntake1 = new FoodIntake();
        foodIntake1.addServingSize(food1);
        foodIntake1.setDate(LocalDate.now());
        foodIntake1.setUser(user1);
        foodIntakeRepository.save(foodIntake1);

        ServingSize food2 = new ServingSize();
        food2.setDish(dish2);
        food2.setAmount(400);

        FoodIntake foodIntake2 = new FoodIntake();
        foodIntake2.addServingSize(food2);
        foodIntake2.setDate(LocalDate.now());
        foodIntake2.setUser(user1);
        foodIntakeRepository.save(foodIntake2);

        ServingSize food3 = new ServingSize();
        food3.setDish(dish1);
        food3.setAmount(400);

        FoodIntake foodIntake3 = new FoodIntake();
        foodIntake3.addServingSize(food3);
        foodIntake3.setDate(LocalDate.now().minusDays(1));
        foodIntake3.setUser(user1);
        foodIntakeRepository.save(foodIntake3);

        ServingSize food4 = new ServingSize();
        food4.setDish(dish2);
        food4.setAmount(400);
        FoodIntake foodIntake4 = new FoodIntake();
        foodIntake4.addServingSize(food4);
        foodIntake4.setDate(LocalDate.now().minusDays(2));
        foodIntake4.setUser(user2);
        foodIntakeRepository.save(foodIntake4);

        ServingSize food5 = new ServingSize();
        food5.setDish(dish2);
        food5.setAmount(400);
        FoodIntake foodIntake5 = new FoodIntake();
        foodIntake5.addServingSize(food5);
        foodIntake5.setDate(LocalDate.now());
        foodIntake5.setUser(user2);
        foodIntakeRepository.save(foodIntake5);
    }

    @AfterEach
    public void clearDatabase() {
        foodIntakeRepository.deleteAll();
        servingSizeRepository.deleteAll();
        userRepository.deleteAll();
        dishRepository.deleteAll();
    }

    @Test
    @DisplayName("Test getNutritionHistory when send valid userId " +
            " then get status success and actual dayReportList")
    public void testGetNutritionHistory_whenSendValidUser_thenGet200() {
        int expectedHistoryLength = 2;
        long userId = userRepository.findByEmail("jhon@world.com").orElseThrow().getId();
        ResponseEntity<DayReportDto[]> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports/history?user=" + userId,
                        DayReportDto[].class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedHistoryLength, response.getBody().length);
    }

    @Test
    @DisplayName("Test getNutritionHistory when send incorrect userId then get status bad request (400)")
    public void testNutritionHistory_whenSendIncorrectUserId_thenGetStatus404() {
        long userId = userRepository.count() + 999L;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports/history?user=" + userId,
                        String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test getDayReport when send valid userId and date then get status 200 and get dayReport")
    public void testGetDayReport_whenSendValidUserIdAndDate_ThenGetStatus200() {
        double expectedCalorieSum = 4000;
        double expectedProteinsSum = 200;
        double expectedFatsSum = 400;
        double expectedCarbohydratesSum = 800;
        int expectedFoodIntakesNumber = 2;
        long userId = userRepository.findByEmail("jhon@world.com").orElseThrow().getId();
        ResponseEntity<DayReportDto> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports" +
                        "?user=" + userId + "&date=" + LocalDate.now(), DayReportDto.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedCalorieSum, response.getBody().getDayCalorieSum());
        Assertions.assertEquals(expectedProteinsSum, response.getBody().getProteinsSum());
        Assertions.assertEquals(expectedFatsSum, response.getBody().getFatsSum());
        Assertions.assertEquals(expectedCarbohydratesSum, response.getBody().getCarbohydratesSum());
        Assertions.assertEquals(expectedFoodIntakesNumber, response.getBody().getFoodIntakesNumber());
    }

    @Test
    @DisplayName("Test getDayReport when send valid userId and yesterday date " +
            "then get status 200 and get dayReport")
    public void testGetDayReport_whenSendValidUserIdAndYesterdayDate_ThenGetStatus200() {
        double expectedCalorieSum = 800;
        double expectedProteinsSum = 40;
        double expectedFatsSum = 80;
        double expectedCarbohydratesSum = 160;
        long userId = userRepository.findByEmail("jhon@world.com").orElseThrow().getId();
        ResponseEntity<DayReportDto> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports" +
                        "?user=" + userId + "&date=" + LocalDate.now().minusDays(1), DayReportDto.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedCalorieSum, response.getBody().getDayCalorieSum());
        Assertions.assertEquals(expectedProteinsSum, response.getBody().getProteinsSum());
        Assertions.assertEquals(expectedFatsSum, response.getBody().getFatsSum());
        Assertions.assertEquals(expectedCarbohydratesSum, response.getBody().getCarbohydratesSum());
    }

    @Test
    @DisplayName("Test getDayReport when send incorrect userId and valid date" +
            " then get status 400 (bad request)")
        public void testGetDayReport_whenSendIncorrectUserIdAndValidDate_thenGet400() {
        long userId = -1L;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports" +
                        "?user=" + userId + "&date=" + LocalDate.now(), String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test getDayReport when user not found" +
            " then get status 400 (bad request)")
    public void testGetDayReport_whenUserNotFound_thenGet400() {
        long userId = userRepository.count() + 999L;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports" +
                        "?user=" + userId + "&date=" + LocalDate.now(), String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test getDayReport when send request without userId" +
            " then get status 400 (bad request)")
    public void testGetDayReport_whenSendRequestWithoutUserId_thenGet400() {
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports" +
                        "?date=" + LocalDate.now(), String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test getDayReport when send request without date" +
            " then get status 400 (bad request)")
    public void testGetDayReport_whenSendRequestWithoutDate_thenGet400() {
        long userId = userRepository.findByEmail("jhon@world.com").orElseThrow().getId();
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports" +
                        "?date=" + userId, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test isGoalAchieved when send valid userId and date " +
            "user days calorie sum less than user calorie norm " +
            "then get status success (200) and return true")
    public void testIsGoalAchieved_whenSendValidUserIdAndDate_ThenGet200AndTrue() {
        long userId = userRepository.findByEmail("jhon@world.com").orElseThrow().getId();
        ResponseEntity<Boolean> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports/results" +
                        "?user=" + userId + "&date=" + LocalDate.now(), Boolean.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody());
    }

    @Test
    @DisplayName("Test isGoalAchieved when send valid userId and date and " +
            "user days calorie sum more than user calorie norm " +
            "then get status success (200) and return false")
    public void testIsGoalAchieved_whenSendValidUserIdAndDate_ThenGet200andFalse() {
        long userId = userRepository.findByEmail("jane@world.com").orElseThrow().getId();
        ResponseEntity<Boolean> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports/results" +
                        "?user=" + userId + "&date=" + LocalDate.now(), Boolean.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertFalse(response.getBody());
    }

    @Test
    @DisplayName("Test isGoalAchieved when send incorrect userId and valid date" +
            " then get status 400 (bad request)")
    public void testIsGoalAchieved_whenSendIncorrectUserIdAndValidDate_thenGet400() {
        long userId = -222L;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports/results" +
                        "?user=" + userId + "&date=" + LocalDate.now(), String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test isGoalAchieved when user not found" +
            " then get status 400 (bad request)")
    public void testIsGoalAchieved_whenUserNotFound_thenGet400() {
        long userId = userRepository.count() + 999L;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports/results" +
                        "?user=" + userId + "&date=" + LocalDate.now(), String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test isGoalAchieved when send request without userid" +
            " then get status 400 (bad request)")
    public void testIsGoalAchieved_whenSendRequestWithoutUserid_thenGet400() {
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports/results" +
                        "?date=" + LocalDate.now(), String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test isGoalAchieved when send request without date" +
            " then get status 400 (bad request)")
    public void testIsGoalAchieved_whenSendRequestWithoutDate_thenGet400() {
        long userId = userRepository.findByEmail("jane@world.com").orElseThrow().getId();
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/reports/results" +
                        "?user=" + userId, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }
}
