package hu.flowacademy.netbank.controller.helpers;

import hu.flowacademy.netbank.config.AuthenticationFilter;
import io.restassured.http.Header;

import static io.restassured.RestAssured.*;

public class LoginHelper {

    public static final String API_LOGIN = "/api/login";

    public static String login(String username) {
        return given()
                    .log().all()
                    .body(new AuthenticationFilter.LoginRequest(username, "123password"))
                .when()
                    .post(API_LOGIN)
                .then()
                    .log().all()
                    .extract().body().as(AuthenticationFilter.LoginResponse.class)
                .getToken();
    }

    public static Header buildAuthorization(String token) {
        return new Header("Authorization", "Bearer "+token);
    }
}
