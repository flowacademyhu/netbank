package hu.flowacademy.netbank.controller;

import hu.flowacademy.netbank.controller.helpers.LoginHelper;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class AccountControllerTest {

    @LocalServerPort
    private int port;

    private User user;

    @BeforeEach
    public void beforeAll() {
        RestAssured.port = port;
        User signedUp = UserHelper.signUp();
        user = UserHelper.getUserByUsername(LoginHelper.login(signedUp.getEmail(), signedUp.getPassword()), signedUp);
    }

    @Test
    public void testCreateAccount() {
        String token = LoginHelper.login(user.getEmail(), user.getPassword());
        create(token, user);
    }

    @Test
    public void testAddMoneyAccount() {
        String token = LoginHelper.login(user.getEmail(), user.getPassword());
        Account created = create(token, user);
        Account account = findAll(token).stream().filter(a -> a.getOwner().equals(user)
                && a.getCurrency().equals(created.getCurrency())).findFirst().orElseThrow();

        addMoney(token, account.getId(), account.getCurrency());
    }

}