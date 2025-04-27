package com.alxsshv.controller;

import com.alxsshv.dto.DishDto;
import com.alxsshv.model.Dish;
import com.alxsshv.repository.DishRepository;
import com.alxsshv.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DishControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private DishRepository dishRepository;
    private static final PostgreSQLContainer<?> POSTGRES
            = new PostgreSQLContainer<>("postgres:17");
    private final RestTemplate template = new TestRestTemplate().getRestTemplate();
    public static final double EPSILON = 0.001d;

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    public static void setup(DynamicPropertyRegistry registry) {
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
        Dish dish1 = new Dish();
        dish1.setTitle("Гречневая каша");
        dish1.setCalorieContent(98.7);
        dish1.setProteinsAmount(3.6);
        dish1.setFatsAmount(2.2);
        dish1.setCarbohydratesAmount(17.1);
        dishRepository.save(dish1);

        Dish dish2 = new Dish();
        dish2.setTitle("Винегрет");
        dish2.setCalorieContent(130.1);
        dish2.setProteinsAmount(1.7);
        dish2.setFatsAmount(10.3);
        dish2.setCarbohydratesAmount(8.2);
        dishRepository.save(dish2);
    }

    @AfterEach
    public void clearDatabase() {
        dishRepository.deleteAll();
    }

    @Test
    @DisplayName("Test createDish when send valid data then get status created (201)")
    public void testCreateDish_whenSendValidData_thenGet201() {
        DishDto dishDto = new DishDto();
        dishDto.setTitle("Белый шоколад");
        dishDto.setCalorieContent(554.0);
        dishDto.setProteinsAmount(6.0);
        dishDto.setFatsAmount(34.0);
        dishDto.setCarbohydratesAmount(56.0);
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port  + "/api/v1/dishes",
                        dishDto, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED));
        Assertions.assertTrue(dishRepository.findByTitle("Белый шоколад").isPresent());
    }

    @Test
    @DisplayName("Test createDish when send dishDto title is null" +
            " then get status bad request(400)")
    public void testCreateDish_whenSendDishDtoTitleIsNull_thenGet400() {
        DishDto dishDto = new DishDto();
        dishDto.setCalorieContent(554.0);
        dishDto.setProteinsAmount(6.0);
        dishDto.setFatsAmount(34.0);
        dishDto.setCarbohydratesAmount(56.0);
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port  + "/api/v1/dishes",
                        dishDto, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test createDish when send dishDto title is blank" +
            " then get status bad request(400)")
    public void testCreateDish_whenSendDishDtoTitleIsBlank_thenGet400() {
        DishDto dishDto = new DishDto();
        dishDto.setTitle(" ");
        dishDto.setCalorieContent(554.0);
        dishDto.setProteinsAmount(6.0);
        dishDto.setFatsAmount(34.0);
        dishDto.setCarbohydratesAmount(56.0);
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port  + "/api/v1/dishes",
                        dishDto, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test createDish when send calorieContent exceeds 1000" +
            " then get status bad request(400)")
    public void testCreateDish_whenSendCalorieContentExceeds1000_thenGet400() {
        DishDto dishDto = new DishDto();
        dishDto.setTitle("Белый шоколад");
        dishDto.setCalorieContent(1000.01);
        dishDto.setProteinsAmount(6.0);
        dishDto.setFatsAmount(34.0);
        dishDto.setCarbohydratesAmount(56.0);
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port  + "/api/v1/dishes",
                        dishDto, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test createDish when send proteinsAmount exceeds 100" +
            " then get status bad request(400)")
    public void testCreateDish_whenSendProteinsAmountExceeds100_thenGet400() {
        DishDto dishDto = new DishDto();
        dishDto.setTitle("Белый шоколад");
        dishDto.setCalorieContent(554.01);
        dishDto.setProteinsAmount(100.001);
        dishDto.setFatsAmount(34.0);
        dishDto.setCarbohydratesAmount(56.0);
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port  + "/api/v1/dishes",
                        dishDto, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test createDish when send fatsAmount exceeds 100" +
            " then get status bad request(400)")
    public void testCreateDish_whenSendFatsAmountExceeds100_thenGet400() {
        DishDto dishDto = new DishDto();
        dishDto.setTitle("Белый шоколад");
        dishDto.setCalorieContent(554.01);
        dishDto.setProteinsAmount(10.001);
        dishDto.setFatsAmount(100.00003);
        dishDto.setCarbohydratesAmount(56.0);
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port  + "/api/v1/dishes",
                        dishDto, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }


    @Test
    @DisplayName("Test createDish when send carbohydratesAmount more then 100" +
            " then get status bad request(400)")
    public void testCreateDish_whenSendCarbohydratesAmountExceeds100_thenGet400() {
        DishDto dishDto = new DishDto();
        dishDto.setTitle("Белый шоколад");
        dishDto.setCalorieContent(554.01);
        dishDto.setProteinsAmount(10.001);
        dishDto.setFatsAmount(6.3);
        dishDto.setCarbohydratesAmount(101.0);
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port  + "/api/v1/dishes", dishDto, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test findAll when database is not empty then get 200 and list of dishes")
    public void findAll_whenDatabaseIsNotEmpty_thenGetListOfDishes() {
        int dishesCount = (int)dishRepository.count();
        ResponseEntity<DishDto[]> response = template
                .getForEntity("http://localhost:" + port  + "/api/v1/dishes", DishDto[].class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(dishesCount, response.getBody().length);
    }

    @Test
    @DisplayName("Test findAll when database is empty then get 200 and empty list")
    public void findAll_whenDatabaseIsEmpty_thenGetEmptyList() {
        dishRepository.deleteAll();
        int dishesCount = (int)dishRepository.count();
        ResponseEntity<DishDto[]> response = template
                .getForEntity("http://localhost:" + port  + "/api/v1/dishes", DishDto[].class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(dishesCount, response.getBody().length);
    }

    @Test
    @DisplayName("Test findDishById when id is correct and " +
            "dish found then get dish and status OK (200)")
    public void testFindDishById_whenSendCorrectIdAndDishFound_thenGetDishAnd200() {
        long dishId = dishRepository.findByTitle("Гречневая каша").orElseThrow().getId();
        ResponseEntity<DishDto> response = template
                .getForEntity("http://localhost:" + port  + "/api/v1/dishes/"+ dishId,
                        DishDto.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(dishId, response.getBody().getId());
    }

    @Test
    @DisplayName("Test findDishById when id is correct and " +
            "dish not found then get status not found (404)")
    public void testFindDishById_whenSendCorrectIdAndDishNotFound_thenGet404() {
        long dishId = dishRepository.count()+333;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port  + "/api/v1/dishes/" + dishId, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND));
    }

    @Test
    @DisplayName("Test findDishById when id is not correct " +
            "then get status bad request (400)")
    public void testFindDishById_whenSendNotCorrectId_thenGet400() {
        long dishId = 0;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port  + "/api/v1/dishes/" + dishId, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test updateDish when send valid dishDto then update success")
    public void testUpdateDish_whenSendValidDishDto_thenUpdateSuccess() {
        DishDto dishDto = new DishDto();
        dishDto.setId(dishRepository.findByTitle("Гречневая каша").orElseThrow().getId());
        dishDto.setTitle("Каша гречневая");
        dishDto.setCalorieContent(100.0);
        dishDto.setProteinsAmount(70.4);
        dishDto.setFatsAmount(60.5);
        dishDto.setCarbohydratesAmount(80.6);
        template.put("http://localhost:" + port  + "/api/v1/dishes", dishDto);
        Optional<Dish> dishOpt = dishRepository.findById(dishDto.getId());
        Assertions.assertTrue(dishOpt.isPresent());
        Assertions.assertEquals(dishDto.getTitle(), dishOpt.get().getTitle());
        Assertions.assertTrue(
                Math.abs(dishDto.getCalorieContent() - dishOpt.get().getCalorieContent()) < EPSILON);
        Assertions.assertTrue(
                Math.abs(dishDto.getProteinsAmount() - dishOpt.get().getProteinsAmount()) < EPSILON);
        Assertions.assertTrue(
                Math.abs(dishDto.getFatsAmount() - dishOpt.get().getFatsAmount()) < EPSILON);
        Assertions.assertTrue(
                Math.abs(dishDto.getCarbohydratesAmount() - dishOpt.get().getCarbohydratesAmount()) < EPSILON);
    }

    @Test
    @DisplayName("Test updateDish when send duplicated title then update fails")
    public void testUpdateDish_whenSendDuplicatedTitle_thenUpdateFails() {
        DishDto dishDto = new DishDto();
        dishDto.setId(dishRepository.findByTitle("Гречневая каша").orElseThrow().getId());
        dishDto.setTitle("Винегрет");
        dishDto.setCalorieContent(100.0);
        dishDto.setProteinsAmount(70.4);
        dishDto.setFatsAmount(60.5);
        dishDto.setCarbohydratesAmount(80.6);
        template.put("http://localhost:" + port  + "/api/v1/dishes", dishDto);
        Optional<Dish> dishOpt = dishRepository.findById(dishDto.getId());
        Assertions.assertTrue(dishOpt.isPresent());
        Assertions.assertNotEquals(dishDto.getTitle(), dishOpt.get().getTitle());
        Assertions.assertFalse(
                Math.abs(dishDto.getCalorieContent() - dishOpt.get().getCalorieContent()) < EPSILON);
    }

    @Test
    @DisplayName("Test updateDish when send ProteinsAmount exceeds 100 then update fails")
    public void testUpdateDish_whenProteinsAmountExceeds100_thenUpdateFails() {
        DishDto dishDto = new DishDto();
        dishDto.setId(dishRepository.findByTitle("Гречневая каша").orElseThrow().getId());
        dishDto.setTitle("Каша гречневая");
        dishDto.setCalorieContent(100.0);
        dishDto.setProteinsAmount(100.4);
        dishDto.setFatsAmount(60.5);
        dishDto.setCarbohydratesAmount(80.6);
        template.put("http://localhost:" + port  + "/api/v1/dishes", dishDto);
        Optional<Dish> dishOpt = dishRepository.findById(dishDto.getId());
        Assertions.assertTrue(dishOpt.isPresent());
        Assertions.assertNotEquals(dishDto.getTitle(), dishOpt.get().getTitle());
        Assertions.assertFalse(
                Math.abs(dishDto.getCalorieContent() - dishOpt.get().getCalorieContent()) < EPSILON);
    }

    @Test
    @DisplayName("Test updateDish when send calorieContent less 0 then update fails")
    public void testUpdateDish_whenCalorieContentLess0_thenUpdateFails() {
        DishDto dishDto = new DishDto();
        dishDto.setId(dishRepository.findByTitle("Гречневая каша").orElseThrow().getId());
        dishDto.setTitle("Каша гречневая");
        dishDto.setCalorieContent(-100.0);
        dishDto.setProteinsAmount(8.4);
        dishDto.setFatsAmount(60.5);
        dishDto.setCarbohydratesAmount(80.6);
        template.put("http://localhost:" + port  + "/api/v1/dishes", dishDto);
        Optional<Dish> dishOpt = dishRepository.findById(dishDto.getId());
        Assertions.assertTrue(dishOpt.isPresent());
        Assertions.assertNotEquals(dishDto.getTitle(), dishOpt.get().getTitle());
        Assertions.assertFalse(
                Math.abs(dishDto.getCalorieContent() - dishOpt.get().getCalorieContent()) < EPSILON);
    }

    @Test
    @DisplayName("Test updateDish when send proteins amount less 0 then update fails")
    public void testUpdateDish_whenProteinsAmountLess0_thenUpdateFails() {
        DishDto dishDto = new DishDto();
        dishDto.setId(dishRepository.findByTitle("Гречневая каша").orElseThrow().getId());
        dishDto.setTitle("Каша гречневая");
        dishDto.setCalorieContent(100.0);
        dishDto.setProteinsAmount(-8.4);
        dishDto.setFatsAmount(60.5);
        dishDto.setCarbohydratesAmount(80.6);
        template.put("http://localhost:" + port  + "/api/v1/dishes", dishDto);
        Optional<Dish> dishOpt = dishRepository.findById(dishDto.getId());
        Assertions.assertTrue(dishOpt.isPresent());
        Assertions.assertNotEquals(dishDto.getTitle(), dishOpt.get().getTitle());
        Assertions.assertFalse(
                Math.abs(dishDto.getCalorieContent() - dishOpt.get().getCalorieContent()) < EPSILON);
    }

    @Test
    @DisplayName("Test updateDish when send fats amount less 0 then update fails")
    public void testUpdateDish_whenFatsAmountLess0_thenUpdateFails() {
        DishDto dishDto = new DishDto();
        dishDto.setId(dishRepository.findByTitle("Гречневая каша").orElseThrow().getId());
        dishDto.setTitle("Каша гречневая");
        dishDto.setCalorieContent(100.0);
        dishDto.setProteinsAmount(8.4);
        dishDto.setFatsAmount(-60.5);
        dishDto.setCarbohydratesAmount(80.6);
        template.put("http://localhost:" + port  + "/api/v1/dishes", dishDto);
        Optional<Dish> dishOpt = dishRepository.findById(dishDto.getId());
        Assertions.assertTrue(dishOpt.isPresent());
        Assertions.assertNotEquals(dishDto.getTitle(), dishOpt.get().getTitle());
        Assertions.assertFalse(
                Math.abs(dishDto.getCalorieContent() - dishOpt.get().getCalorieContent()) < EPSILON);
    }

    @Test
    @DisplayName("Test updateDish when send carbohydrates amount less 0 then update fails")
    public void testUpdateDish_whenCarbohydratesAmountLess0_thenUpdateFails() {
        DishDto dishDto = new DishDto();
        dishDto.setId(dishRepository.findByTitle("Гречневая каша").orElseThrow().getId());
        dishDto.setTitle("Каша гречневая");
        dishDto.setCalorieContent(100.0);
        dishDto.setProteinsAmount(8.4);
        dishDto.setFatsAmount(60.5);
        dishDto.setCarbohydratesAmount(-80.6);
        template.put("http://localhost:" + port  + "/api/v1/dishes", dishDto);
        Optional<Dish> dishOpt = dishRepository.findById(dishDto.getId());
        Assertions.assertTrue(dishOpt.isPresent());
        Assertions.assertNotEquals(dishDto.getTitle(), dishOpt.get().getTitle());
        Assertions.assertFalse(
                Math.abs(dishDto.getCalorieContent() - dishOpt.get().getCalorieContent()) < EPSILON);
    }

    @Test
    @DisplayName("Test updateDish when id not found amount less 0 then update fails")
    public void testUpdateDish_whenIdNotFound_thenUpdateFails() {
        DishDto dishDto = new DishDto();
        dishDto.setId(dishRepository.count()+999);
        dishDto.setTitle("Каша гречневая");
        dishDto.setCalorieContent(100.0);
        dishDto.setProteinsAmount(8.4);
        dishDto.setFatsAmount(60.5);
        dishDto.setCarbohydratesAmount(80.6);
        template.put("http://localhost:" + port  + "/api/v1/dishes", dishDto);
        Optional<Dish> dishOpt = dishRepository.findByTitle("Каша гречневая");
        Assertions.assertTrue(dishOpt.isEmpty());
    }

    @Test
    @DisplayName("Test deleteDish_when id found then delete success")
    public void testDeleteDish_whenIdFound_thenDeleteSuccess() {
        int beforeDeleteCount = (int) dishRepository.count();
        long dishId = dishRepository.findByTitle("Винегрет").orElseThrow().getId();
        template.delete("http://localhost:" + port  + "/api/v1/dishes/" + dishId);
        int afterDeleteCount = (int) dishRepository.count();
        Assertions.assertEquals(beforeDeleteCount - 1, afterDeleteCount);
    }

    @Test
    @DisplayName("Test deleteDish_when id not found then delete success")
    public void testDeleteDish_whenIdNotFound_thenDeleteFail() {
        int beforeDeleteCount = (int) dishRepository.count();
        long dishId = dishRepository.count() + 999;
        template.delete("http://localhost:" + port  + "/api/v1/dishes/" + dishId);
        int afterDeleteCount = (int) dishRepository.count();
        Assertions.assertEquals(beforeDeleteCount, afterDeleteCount);
    }

    @Test
    @DisplayName("Test deleteDish when id not found then delete success")
    public void testDeleteDish_whenIdNotCorrect_thenDeleteFail() {
        int beforeDeleteCount = (int) dishRepository.count();
        long dishId = 0;
        template.delete("http://localhost:" + port  + "/api/v1/dishes/" + dishId);
        int afterDeleteCount = (int) dishRepository.count();
        Assertions.assertEquals(beforeDeleteCount, afterDeleteCount);
    }
}
