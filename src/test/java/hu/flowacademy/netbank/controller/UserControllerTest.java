package hu.flowacademy.netbank.controller;

import com.github.javafaker.Faker;
import hu.flowacademy.netbank.controller.helpers.LoginHelper;
import hu.flowacademy.netbank.model.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static hu.flowacademy.netbank.controller.helpers.UserHelper.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @LocalServerPort
    private int port;
    private Faker faker = new Faker();

    @BeforeEach
    public void beforeAll() {
        RestAssured.port = port;
    }

    @Test
    public void testUserCreate() {
        signUp();
    }

    @Test
    public void testUserUpdate() {
        User createdUser = signUp();
        String token = LoginHelper.login(createdUser.getEmail());

        User user = getUserByUsername(token, createdUser);

        findOne(token, user.getId());

        User updatedUser = update(token, user.toBuilder()
                .fullName(faker.name().fullName() + UUID.randomUUID())
                .build());

        assertNotEquals(createdUser.getFullName(), updatedUser.getFullName());
    }

    @Test
    public void testDeleteUser() {
        User createdUser = signUp();
        String token = LoginHelper.login(createdUser.getEmail());
        User user = getUserByUsername(token, createdUser);
        delete(token, user.getId());
    }

}