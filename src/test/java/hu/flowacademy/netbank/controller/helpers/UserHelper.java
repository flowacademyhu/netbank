package hu.flowacademy.netbank.controller.helpers;

import com.github.javafaker.Faker;
import hu.flowacademy.netbank.model.Role;
import hu.flowacademy.netbank.model.User;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.List;

import static io.restassured.RestAssured.*;

public class UserHelper {

    public static final String API_USERS = "/api/users";
    private static Faker faker = new Faker();

    public static User signUp() {
        User user = User.builder()
                .email(faker.internet().emailAddress())
                .password("123password")
                .fullName(faker.name().fullName())
                .build();
        given()
                .log().all()
                .body(user)
                .contentType(ContentType.JSON)
        .when()
                .post(API_USERS)
        .then()
                .log().all()
                .statusCode(201);
        return user;
    }

    public static List<User> getAll() {
        return given()
                .log().all()
            .when()
                .get(API_USERS)
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().as(new TypeRef<>() {});
    }

    public static User getUserByUsername(User createdUser) {
        return getAll().stream().filter(u -> u.getEmail().equals(createdUser.getEmail()))
                .findFirst().orElseThrow();
    }

    public static User update(User user) {
        return given()
                .log().all()
                .body(user)
                .contentType(ContentType.JSON)
            .when()
                .put(API_USERS+"/"+user.getId())
            .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(User.class);
    }

    public static User findOne(String id) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
            .when()
                .get(API_USERS+"/"+id)
            .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(User.class);
    }

    public static void delete(String id) {
        given()
            .log().all()
            .contentType(ContentType.JSON)
        .when()
            .delete(API_USERS+"/"+id)
        .then()
            .log().all()
            .statusCode(204);
    }

}
