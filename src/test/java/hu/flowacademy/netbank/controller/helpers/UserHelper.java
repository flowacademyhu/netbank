package hu.flowacademy.netbank.controller.helpers;

import com.github.javafaker.Faker;
import hu.flowacademy.netbank.model.User;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

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

    public static List<User> getAll(String token) {
        return given()
                .header(LoginHelper.buildAuthorization(token))
                .log().all()
            .when()
                .get(API_USERS)
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().as(new TypeRef<>() {});
    }

    public static User getUserByUsername(String token, User createdUser) {
        return getAll(token).stream().filter(u -> u.getEmail().equals(createdUser.getEmail()))
                .findFirst().orElseThrow();
    }

    public static User update(String token, User user) {
        return given()
                .log().all()
                .header(LoginHelper.buildAuthorization(token))
                .body(user)
                .contentType(ContentType.JSON)
            .when()
                .put(API_USERS+"/"+user.getId())
            .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(User.class);
    }

    public static User findOne(String token, String id) {
        return given()
                .log().all()
                .header(LoginHelper.buildAuthorization(token))
                .contentType(ContentType.JSON)
            .when()
                .get(API_USERS+"/"+id)
            .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(User.class);
    }

    public static void delete(String token, String id) {
        given()
            .log().all()
            .header(LoginHelper.buildAuthorization(token))
            .contentType(ContentType.JSON)
        .when()
            .delete(API_USERS+"/"+id)
        .then()
            .log().all()
            .statusCode(204);
    }

}
