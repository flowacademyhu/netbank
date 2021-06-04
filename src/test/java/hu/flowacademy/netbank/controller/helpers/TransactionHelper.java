package hu.flowacademy.netbank.controller.helpers;

import com.github.javafaker.Faker;
import hu.flowacademy.netbank.dto.TransactionCreateDTO;
import hu.flowacademy.netbank.model.Account;
import hu.flowacademy.netbank.model.Transaction;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.*;

public class TransactionHelper {

    public static final String API_TRANSACTIONS = "/api/transactions";

    private static Faker faker = new Faker();

    public static TransactionCreateDTO create(String token, String sender, String receiver) {
        TransactionCreateDTO transaction = TransactionCreateDTO.builder()
                .amount(BigDecimal.valueOf(faker.number().numberBetween(100, 10000)))
                .senderAccountId(sender)
                .receiverAccountId(receiver)
                .build();

        given()
                .log().all()
                .body(transaction)
                .contentType(ContentType.JSON)
                .header(LoginHelper.buildAuthorization(token))
        .when()
                .post(API_TRANSACTIONS)
        .then()
                .log().all()
                .statusCode(204);

        return transaction;
    }

    public static void revert(String token, String id) {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(LoginHelper.buildAuthorization(token))
        .when()
                .post(API_TRANSACTIONS+"/"+id+"/revert")
        .then()
                .log().all()
                .statusCode(204);
    }

    public static List<Transaction> findAll(String token) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(LoginHelper.buildAuthorization(token))
        .when()
                .post(API_TRANSACTIONS)
        .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(new TypeRef<>() {});
    }

    public static Transaction findOne(String token, String id) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(LoginHelper.buildAuthorization(token))
        .when()
                .post(API_TRANSACTIONS+"/"+id)
        .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(Transaction.class);
    }
}
