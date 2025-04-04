package com.alxsshv.controller;

import com.alxsshv.dto.UserDto;
import com.alxsshv.dto.mappers.UserMapper;
import com.alxsshv.model.Goal;
import com.alxsshv.model.User;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private UserRepository userRepository;
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:17");
    private final RestTemplate template = new TestRestTemplate().getRestTemplate();

    @DynamicPropertySource
    private static void setup(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @BeforeAll
    public static void startPostgres() {
        POSTGRES.start();
    }

    @AfterAll
    public static void stopPostgres() {
        POSTGRES.stop();
    }

    @BeforeEach
    public void fillDatabase() {
        User user = new User();
        user.setName("Иван");
        user.setEmail("ivan@world.com");
        user.setAge(20);
        user.setWeight(75);
        user.setHeight(185);
        user.setGoal(Goal.KEEPING_FIT.getPseudonym());
        userRepository.save(user);
    }

    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("test addUser when send valid user data" +
            "then return status 201 (Created)")
    public void testAddUser_whenSendValidUserData_thenReturn201() {
        String email = "senya@world.com";
        UserDto userDto = new UserDto();
        userDto.setName("Сеня");
        userDto.setEmail(email);
        userDto.setAge(22);
        userDto.setWeight(92);
        userDto.setHeight(180);
        userDto.setGoal(Goal.WEIGHT_LOSS.getPseudonym());
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/users",
                        userDto,String.class);
        System.out.println(response.getBody());
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(userRepository.findByEmail(email).isPresent());
    }

    @Test
    @DisplayName("test addUser when send email field is incorrectly" +
            "then return status 400 (Created)")
    public void testAddUser_whenEmailFiledIsIncorrectly_thenReturn500() {
        String email = "senya@world.com";
        UserDto userDto = new UserDto();
        userDto.setName("Сеня");
        userDto.setEmail(email);
        userDto.setAge(22);
        userDto.setWeight(92);
        userDto.setHeight(180);
        userDto.setGoal(Goal.WEIGHT_LOSS.getPseudonym());
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/users",
                        userDto,String.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertTrue(userRepository.findByEmail(email).isEmpty());
    }



}
