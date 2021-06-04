package hu.flowacademy.netbank.controller.helpers;

import com.github.javafaker.Faker;
import hu.flowacademy.netbank.dto.AddMoneyDTO;
import hu.flowacademy.netbank.dto.TransactionCreateDTO;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Currency;
import hu.flowacademy.netbank.model.User;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class AccountHelper {

    public static final String API_ACCOUNTS = "/api/accounts";
    public static Faker faker = new Faker();

    public static Account create(User owner) {
        Account account = Account.builder()
                .amount(BigDecimal.valueOf(faker.number().numberBetween(10000, 1000000)))
                .currency(getRandomCurrency())
                .owner(owner)
                .build();

        given()
                .log().all()
                .body(account)
                .contentType(ContentType.JSON)
        .when()
                .post(API_ACCOUNTS)
        .then()
                .log().all()
                .statusCode(201);

        return account;
    }

    public static void addMoney(String id) {
        AddMoneyDTO account = AddMoneyDTO.builder()
                .amount(BigDecimal.valueOf(faker.number().numberBetween(10000, 1000000)))
                .currency(getRandomCurrency())
                .build();

        given()
                .log().all()
                .body(account)
                .contentType(ContentType.JSON)
        .when()
                .put(API_ACCOUNTS+"/"+id)
        .then()
                .log().all()
                .statusCode(204);
    }

    public static Account findOne(String id) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
        .when()
                .get(API_ACCOUNTS+"/"+id)
        .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(Account.class);
    }

    public static List<Account> findAll() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
        .when()
                .get(API_ACCOUNTS)
        .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(new TypeRef<>() {});
    }

    private static Currency getRandomCurrency() {
        return Currency.values()[new Random().nextInt(Currency.values().length)];
    }

}
