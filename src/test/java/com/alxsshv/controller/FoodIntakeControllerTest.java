package com.alxsshv.controller;

import com.alxsshv.model.Goal;
import com.alxsshv.model.Sex;
import com.alxsshv.dto.FoodIntakeDto;
import com.alxsshv.dto.ServingSizeDto;
import com.alxsshv.dto.mappers.DishMapper;
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
import java.util.ArrayList;
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
    private ServingSizeRepository servingSizeRepository;
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
    }

    @AfterEach
    public void clearDatabase() {
        foodIntakeRepository.deleteAll();
        servingSizeRepository.deleteAll();
        userRepository.deleteAll();
        dishRepository.deleteAll();
    }

    @Test
    @DisplayName("Test addFoodIntake when send valid data then get status created (201)")
    public void testAddFoodIntake_whenSendValidData_thenGet201() {
        long foodIntakeBeforeCount = foodIntakeRepository.count();
        ServingSizeDto food1 = new ServingSizeDto();
        food1.setDish(dishMapper.toDishDto(dishRepository.findByTitle("Блин").orElseThrow()));
        food1.setAmount(500);

        FoodIntakeDto foodIntakeDto = new FoodIntakeDto();
        foodIntakeDto.setServingSizes(List.of(food1));
        foodIntakeDto.setDate(LocalDate.now());
        foodIntakeDto.setUserId(userRepository.findByEmail("jhon@world.com").orElseThrow().getId());

        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/food", foodIntakeDto, String.class);
        long foodIntakeAfterCount = foodIntakeRepository.count();
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED));
        Assertions.assertEquals(foodIntakeBeforeCount + 1, foodIntakeAfterCount);
    }

    @Test
    @DisplayName("Test addFoodIntake when userId equals 0 then get status bad request (400)")
    public void testAddFoodIntake_whenUserEquals0_thenGet400() {
        long foodIntakeBeforeCount = foodIntakeRepository.count();
        ServingSizeDto food1 = new ServingSizeDto();
        food1.setDish(dishMapper.toDishDto(dishRepository.findByTitle("Блин").orElseThrow()));
        food1.setAmount(500);

        FoodIntakeDto foodIntakeDto = new FoodIntakeDto();
        foodIntakeDto.setServingSizes(List.of(food1));
        foodIntakeDto.setDate(LocalDate.now());

        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/food", foodIntakeDto, String.class);
        long foodIntakeAfterCount = foodIntakeRepository.count();
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
        Assertions.assertEquals(foodIntakeBeforeCount, foodIntakeAfterCount);
    }

    @Test
    @DisplayName("Test addFoodIntake when user not found then get status bad request (400)")
    public void testAddFoodIntake_whenUserNotFound_thenGet400() {
        long foodIntakeBeforeCount = foodIntakeRepository.count();
        ServingSizeDto food1 = new ServingSizeDto();
        food1.setDish(dishMapper.toDishDto(dishRepository.findByTitle("Блин").orElseThrow()));
        food1.setAmount(500);

        FoodIntakeDto foodIntakeDto = new FoodIntakeDto();
        foodIntakeDto.setServingSizes(List.of(food1));
        foodIntakeDto.setDate(LocalDate.now());
        foodIntakeDto.setUserId(userRepository.count() + 999);

        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/food", foodIntakeDto, String.class);
        long foodIntakeAfterCount = foodIntakeRepository.count();
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
        Assertions.assertEquals(foodIntakeBeforeCount, foodIntakeAfterCount);
    }

    @Test
    @DisplayName("Test addFoodIntake when servingSizes is null then get status bad request (400)")
    public void testAddFoodIntake_whenServingSizesIsNull_thenGet400() {
        long foodIntakeBeforeCount = foodIntakeRepository.count();
        FoodIntakeDto foodIntakeDto = new FoodIntakeDto();
        foodIntakeDto.setDate(LocalDate.now());
        foodIntakeDto.setUserId(userRepository.findByEmail("jhon@world.com").orElseThrow().getId());

        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/food", foodIntakeDto, String.class);
        long foodIntakeAfterCount = foodIntakeRepository.count();
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
        Assertions.assertEquals(foodIntakeBeforeCount, foodIntakeAfterCount);
    }

    @Test
    @DisplayName("Test addFoodIntake when servingSizes is empty then get status bad request (400)")
    public void testAddFoodIntake_whenServingSizesIsEmpty_thenGet400() {
        long foodIntakeBeforeCount = foodIntakeRepository.count();
        FoodIntakeDto foodIntakeDto = new FoodIntakeDto();
        foodIntakeDto.setDate(LocalDate.now());
        foodIntakeDto.setUserId(userRepository.findByEmail("jhon@world.com").orElseThrow().getId());
        foodIntakeDto.setServingSizes(new ArrayList<>());

        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/food", foodIntakeDto, String.class);
        long foodIntakeAfterCount = foodIntakeRepository.count();
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
        Assertions.assertEquals(foodIntakeBeforeCount, foodIntakeAfterCount);
    }

    @Test
    @DisplayName("Test addFoodIntake when servingSizes amount is less than 1 then get status bad request (400)")
    public void testAddFoodIntake_whenServingSizeAmountLessThan1_thenGet400() {
        long foodIntakeBeforeCount = foodIntakeRepository.count();
        ServingSizeDto food1 = new ServingSizeDto();
        food1.setDish(dishMapper.toDishDto(dishRepository.findByTitle("Блин").orElseThrow()));
        food1.setAmount(0);
        FoodIntakeDto foodIntakeDto = new FoodIntakeDto();
        foodIntakeDto.setServingSizes(List.of(food1));
        foodIntakeDto.setDate(LocalDate.now());
        foodIntakeDto.setUserId(userRepository.findByEmail("jane@world.com").orElseThrow().getId());

        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/food", foodIntakeDto, String.class);
        long foodIntakeAfterCount = foodIntakeRepository.count();
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
        Assertions.assertEquals(foodIntakeBeforeCount, foodIntakeAfterCount);
    }

    @Test
    @DisplayName("Test addFoodIntake when servingSizes dish is null then get status bad request (400)")
    public void testAddFoodIntake_whenServingSizeDishIsNull_thenGet400() {
        long foodIntakeBeforeCount = foodIntakeRepository.count();
        ServingSizeDto food1 = new ServingSizeDto();
        food1.setAmount(300);
        FoodIntakeDto foodIntakeDto = new FoodIntakeDto();
        foodIntakeDto.setServingSizes(List.of(food1));
        foodIntakeDto.setDate(LocalDate.now());
        foodIntakeDto.setUserId(userRepository.findByEmail("jane@world.com").orElseThrow().getId());

        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/food", foodIntakeDto, String.class);
        long foodIntakeAfterCount = foodIntakeRepository.count();
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
        Assertions.assertEquals(foodIntakeBeforeCount, foodIntakeAfterCount);
    }

    @Test
    @DisplayName("Test findAllByUserIdAndDate when send valid userId and yesterday's date then get empty food intake list")
    public void testFindAllByUserIdAndDate_whenSendValidUserIdAndYesterdayDate_thenGetEmptyFoodIntakeList() {
        User user = userRepository.findByEmail("jane@world.com").orElseThrow();
        ServingSize food2 = new ServingSize();
        food2.setDish(dishRepository.findByTitle("Суп").orElseThrow());
        food2.setAmount(500);
        FoodIntake foodIntake = new FoodIntake();
        foodIntake.addServingSize(food2);
        foodIntake.setDate(LocalDate.now());
        foodIntake.setUser(user);
        foodIntakeRepository.save(foodIntake);

        ResponseEntity<FoodIntakeDto[]> response = template
                .getForEntity("http://localhost:" + port +
                        "/api/v1/food?user=" + user.getId() + "&date=" + LocalDate.now().minusDays(1),
                        FoodIntakeDto[].class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(0, response.getBody().length);
    }

    @Test
    @DisplayName("Test findAllByUserIdAndDate when date is not defined then get status bad request 400")
    public void testFindAllByUserIdAndDate_whenDateIsNotDefined_thenGet400() {
        User user = userRepository.findByEmail("jane@world.com").orElseThrow();
        ServingSize food2 = new ServingSize();
        food2.setDish(dishRepository.findByTitle("Суп").orElseThrow());
        food2.setAmount(500);
        FoodIntake foodIntake = new FoodIntake();
        foodIntake.addServingSize(food2);
        foodIntake.setDate(LocalDate.now());
        foodIntake.setUser(user);
        foodIntakeRepository.save(foodIntake);

        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/food?user=" + user.getId(),
                        String.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    @DisplayName("Test findById when foodIntake found then get status OK and foodIntake")
    public void testFindById_whenFoodIntakeFound_thenGet200() {
        long foodIntakeId = foodIntakeRepository.findAll().getFirst().getId();
        ResponseEntity<FoodIntakeDto> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/food/" + foodIntakeId, FoodIntakeDto.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(foodIntakeId, response.getBody().getId());
    }

    @Test
    @DisplayName("Test findById when foodIntake not found then get status not found (404)")
    public void testFindById_whenFoodIntakeNotFound_thenGet404() {
        long foodIntakeId = foodIntakeRepository.count() + 999;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/food/" + foodIntakeId, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND));
    }

    @Test
    @DisplayName("Test findById when send incorrect id  then get status bad request (400)")
    public void testFindById_whenSendIncorrectId_thenGet404() {
        long foodIntakeId = 0;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/food/" + foodIntakeId, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test deleteById when foodIntake found  then delete success")
    public void testDeleteById_whenFoodIntakeFound_thenDeleteSuccess() {
        long countBeforeDeleting = foodIntakeRepository.count();
        long foodIntakeId = 1L;
        template.delete("http://localhost:" + port + "/api/v1/food/" + foodIntakeId);
        long countAfterDeleting = foodIntakeRepository.count();
        Assertions.assertEquals(countBeforeDeleting - 1, countAfterDeleting);
    }

    @Test
    @DisplayName("Test deleteById when foodIntake not found  then delete fail")
    public void testDeleteById_whenFoodIntakeNotFound_thenDeleteFail() {
        long countBeforeDeleting = foodIntakeRepository.count();
        long foodIntakeId = countBeforeDeleting + 999;
        template.delete("http://localhost:" + port + "/api/v1/food/" + foodIntakeId);
        long countAfterDeleting = foodIntakeRepository.count();
        Assertions.assertEquals(countBeforeDeleting, countAfterDeleting);
    }

}
