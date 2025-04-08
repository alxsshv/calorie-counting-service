package com.alxsshv.controller;

import com.alxsshv.dto.UserDto;
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

import java.util.Optional;

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
        User user1 = new User();
        user1.setName("Иван");
        user1.setEmail("ivan@world.com");
        user1.setAge(20);
        user1.setWeight(75);
        user1.setHeight(185);
        user1.setGoal(Goal.KEEPING_FIT);

        User user2 = new User();
        user2.setName("Сергей");
        user2.setEmail("sergey@world.com");
        user2.setAge(20);
        user2.setWeight(75);
        user2.setHeight(185);
        user2.setGoal(Goal.WEIGHT_LOSS);

        userRepository.save(user1);
        userRepository.save(user2);
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
        userDto.setGoal("Похудение");
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/users",
                        userDto,String.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(userRepository.findByEmail(email).isPresent());
    }

    @Test
    @DisplayName("test addUser when send email field is incorrectly" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenEmailFiledIsIncorrectly_thenReturn400() {
        String email = "senyaworld.com";
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

    @Test
    @DisplayName("test addUser when send email field is null" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenEmailFiledIsNull_thenReturn400() {
        UserDto userDto = new UserDto();
        userDto.setName("Сеня");
        userDto.setAge(22);
        userDto.setWeight(92);
        userDto.setHeight(180);
        userDto.setGoal(Goal.WEIGHT_LOSS.getPseudonym());
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/users",
                        userDto,String.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    @DisplayName("test addUser when send email field is empty" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenEmailFiledIsEmpty_thenReturn400() {
        String email = "";
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
    }

    @Test
    @DisplayName("test addUser when send user name is empty" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenNameIsEmpty_thenReturn400() {
        String email = "senya@world.ru";
        UserDto userDto = new UserDto();
        userDto.setName("");
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

    @Test
    @DisplayName("test addUser when send user name is null" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenNameIsNull_thenReturn400() {
        String email = "senya@world.ru";
        UserDto userDto = new UserDto();
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

    @Test
    @DisplayName("test addUser when send user name length less 3 characters" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenNameLengthLess3_thenReturn400() {
        String email = "senya@world.ru";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setName("По");
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

    @Test
    @DisplayName("test addUser when send user name length is more 150 characters" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenNameMore150Chars_thenReturn400() {
        String email = "senya@world.ru";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setName("Александрина_Аполлинариевна_" +
                "Семипополовигероверсалофедираковская-Христорождественская");
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

    @Test
    @DisplayName("test addUser when user age less then 1 year" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenAgeLessThen1_thenReturn400() {
        String email = "senya@world.com";
        UserDto userDto = new UserDto();
        userDto.setName("Сеня");
        userDto.setEmail(email);
        userDto.setAge(0);
        userDto.setWeight(92);
        userDto.setHeight(180);
        userDto.setGoal(Goal.WEIGHT_LOSS.getPseudonym());
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/users",
                        userDto,String.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertTrue(userRepository.findByEmail(email).isEmpty());
    }

    @Test
    @DisplayName("test addUser when user age more then 120 years" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenAgeMoreThen120_thenReturn400() {
        String email = "senya@world.com";
        UserDto userDto = new UserDto();
        userDto.setName("Сеня");
        userDto.setEmail(email);
        userDto.setAge(121);
        userDto.setWeight(92);
        userDto.setHeight(180);
        userDto.setGoal(Goal.WEIGHT_LOSS.getPseudonym());
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/users",
                        userDto,String.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertTrue(userRepository.findByEmail(email).isEmpty());
    }

    @Test
    @DisplayName("test addUser when user weight less then 1 kg" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenWeightLessThen1kg_thenReturn400() {
        String email = "senya@world.com";
        UserDto userDto = new UserDto();
        userDto.setName("Сеня");
        userDto.setEmail(email);
        userDto.setAge(35);
        userDto.setWeight(0);
        userDto.setHeight(180);
        userDto.setGoal(Goal.WEIGHT_LOSS.getPseudonym());
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/users",
                        userDto,String.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertTrue(userRepository.findByEmail(email).isEmpty());
    }

    @Test
    @DisplayName("test addUser when user weight more then 200 kg" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenWeightMoreThen200kg_thenReturn400() {
        String email = "senya@world.com";
        UserDto userDto = new UserDto();
        userDto.setName("Сеня");
        userDto.setEmail(email);
        userDto.setAge(35);
        userDto.setWeight(201);
        userDto.setHeight(200);
        userDto.setGoal(Goal.WEIGHT_LOSS.getPseudonym());
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/users",
                        userDto,String.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertTrue(userRepository.findByEmail(email).isEmpty());
    }

    @Test
    @DisplayName("test addUser when user height less then 50 cm" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenHeightLessThen50cm_thenReturn400() {
        String email = "senya@world.com";
        UserDto userDto = new UserDto();
        userDto.setName("Сеня");
        userDto.setEmail(email);
        userDto.setAge(35);
        userDto.setWeight(100);
        userDto.setHeight(49);
        userDto.setGoal(Goal.WEIGHT_LOSS.getPseudonym());
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/users",
                        userDto,String.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertTrue(userRepository.findByEmail(email).isEmpty());
    }

    @Test
    @DisplayName("test addUser when user height more then 280 cm" +
            "then return status 400 (BAD_REQUEST)")
    public void testAddUser_whenHeightMoreThen280cm_thenReturn400() {
        String email = "senya@world.com";
        UserDto userDto = new UserDto();
        userDto.setName("Сеня");
        userDto.setEmail(email);
        userDto.setAge(35);
        userDto.setWeight(100);
        userDto.setHeight(281);
        userDto.setGoal(Goal.WEIGHT_LOSS.getPseudonym());
        ResponseEntity<String> response = template
                .postForEntity("http://localhost:" + port + "/api/v1/users",
                        userDto,String.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertTrue(userRepository.findByEmail(email).isEmpty());
    }

    @Test
    @DisplayName("Test findAll when database stores one user then get" +
            " status 200 (success) and one record in users list")
    public void testFindAll_whenDatabaseStoresOneUser_thenGet200() {
        ResponseEntity<User[]> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/users", User[].class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        int usersNumberInDatabase = (int) userRepository.count();
        Assertions.assertEquals(usersNumberInDatabase, response.getBody().length);
    }

    @Test
    @DisplayName("Test findAll when in database no user " +
            "then get status 200 (success) and empty users list")
    public void testFindAll_whenInDatabaseNoUsers_thenGet200() {
        userRepository.deleteAll();
        ResponseEntity<UserDto[]> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/users", UserDto[].class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        int usersNumberInDatabase = (int) userRepository.count();
        Assertions.assertEquals(usersNumberInDatabase, response.getBody().length);
    }

    @Test
    @DisplayName("Test findUserById when send valid id and user found then get status 200 (success)")
    public void testFindUserById_whenSendValidIdAndUserFound_thenGet200() {
        long userId = userRepository.findByEmail("ivan@world.com").orElseThrow().getId();
        ResponseEntity<UserDto> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/users/" + userId, UserDto.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(userId, response.getBody().getId());
    }

    @Test
    @DisplayName("Test findUserById when send valid id and user not found then get status 404 (not found)")
    public void testFindUserById_whenSendValidIdAndUserNotFound_thenGet404() {
        long userId = userRepository.count() + 999;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/users/" + userId, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND));
    }

    @Test
    @DisplayName("Test findUserById when send not valid id then get status 400 (bad request)")
    public void testFindUserById_whenSendNotValidId_thenGet400() {
        long userId = 0;
        ResponseEntity<String> response = template
                .getForEntity("http://localhost:" + port + "/api/v1/users/" + userId, String.class);
        Assertions.assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Test updateUser when send valid id and user found then update success")
    public void testUpdateUserById_whenSendValidIdAndUserFound_thenUpdatingSuccess() {
        String updateEmail = "ivan1@world.com";
        long userId = userRepository.findByEmail("ivan@world.com").orElseThrow().getId();
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("Иван Иванов");
        userDto.setEmail(updateEmail);
        userDto.setAge(22);
        userDto.setWeight(77);
        userDto.setHeight(188);
        userDto.setGoal(Goal.WEIGHT_GAIN.getPseudonym());
        template.put("http://localhost:" + port + "/api/v1/users", userDto);
        Optional<User> userOpt = userRepository.findByEmail(updateEmail);
        Assertions.assertTrue(userOpt.isPresent());
        Assertions.assertEquals(userId, userOpt.get().getId());
        Assertions.assertEquals(userDto.getName(), userOpt.get().getName());
        Assertions.assertEquals(userDto.getAge(), userOpt.get().getAge());
        Assertions.assertEquals(userDto.getWeight(), userOpt.get().getWeight());
        Assertions.assertEquals(userDto.getHeight(), userOpt.get().getHeight());
        Assertions.assertTrue(userDto.getGoal()
                .equalsIgnoreCase(userOpt.get().getGoal().getPseudonym()));
    }

    @Test
    @DisplayName("Test updateUser when send valid id but email already exist then update fails")
    public void testUpdateUserById_whenSendValidIdAndEmailAlreadyExist_thenUpdatingFail() {
        String emailForUpdate = "sergey@world.com";
        long userId = userRepository.findByEmail("ivan@world.com").orElseThrow().getId();
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("Иван Иванов");
        userDto.setEmail(emailForUpdate);
        userDto.setAge(22);
        userDto.setWeight(77);
        userDto.setHeight(188);
        userDto.setGoal(Goal.WEIGHT_GAIN.getPseudonym());
        template.put("http://localhost:" + port + "/api/v1/users", userDto);
        User updatableUser = userRepository.findById(userId).orElseThrow();
        User emailForUpdateOwner = userRepository.findByEmail(emailForUpdate).orElseThrow();
        Assertions.assertNotEquals(updatableUser.getId(), emailForUpdateOwner.getId());
        Assertions.assertNotEquals(userDto.getName(), updatableUser.getName());
        Assertions.assertEquals("Иван", updatableUser.getName());
    }

    @Test
    @DisplayName("Test updateUser when send not valid email then update fails")
    public void testUpdateUserById_whenSendNotValidEmail_thenUpdatingFail() {
        long userId = userRepository.findByEmail("ivan@world.com").orElseThrow().getId();
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("Иван Иванов");
        userDto.setEmail("ivan1@worldcom");
        userDto.setAge(22);
        userDto.setWeight(77);
        userDto.setHeight(188);
        userDto.setGoal(Goal.WEIGHT_GAIN.getPseudonym());
        template.put("http://localhost:" + port + "/api/v1/users", userDto);
        User userFromDb = userRepository.findById(userId).orElseThrow();
        Assertions.assertEquals("ivan@world.com", userFromDb.getEmail());
    }

    @Test
    @DisplayName("test deleteUserById when send valid id then deleting success")
    public void testDeleteUserById_whenSendValidId_thenDeletingSuccess() {
        long userId = 1;
        template.delete("http://localhost:" + port + "/api/v1/users/" + userId);
        Optional<User> userOpt = userRepository.findById(userId);
        Assertions.assertTrue(userOpt.isEmpty());
    }

}
