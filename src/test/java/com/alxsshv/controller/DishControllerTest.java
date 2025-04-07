package com.alxsshv.controller;

import com.alxsshv.model.Dish;
import com.alxsshv.repository.DishRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DishControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private DishRepository dishRepository;
    private static final PostgreSQLContainer<?> POSTGRES
            = new PostgreSQLContainer<>("postgres:17");
    private final RestTemplate template = new TestRestTemplate().getRestTemplate();

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
    public void beforeAll() {
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





}
