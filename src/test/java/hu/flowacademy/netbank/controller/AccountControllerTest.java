package hu.flowacademy.netbank.controller;

import hu.flowacademy.netbank.controller.helpers.AccountHelper;
import hu.flowacademy.netbank.controller.helpers.UserHelper;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static hu.flowacademy.netbank.controller.helpers.AccountHelper.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class AccountControllerTest {

    @LocalServerPort
    private int port;

    private User user;

    @BeforeEach
    public void beforeAll() {
        RestAssured.port = port;
        user = UserHelper.getUserByUsername(UserHelper.signUp());
    }

    @Test
    public void testCreateAccount() {
        create(user);
    }

    @Test
    public void testAddMoneyAccount() {
        Account created = create(user);
        Account account = findAll().stream().filter(a -> a.getOwner().equals(user)
                && a.getCurrency().equals(created.getCurrency())).findFirst().orElseThrow();

        addMoney(account.getId(), account.getCurrency());
    }

}