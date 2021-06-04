package hu.flowacademy.netbank.controller.helpers;

import hu.flowacademy.netbank.dto.LoanRequestDTO;
import hu.flowacademy.netbank.model.Currency;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.math.BigDecimal;

import static io.restassured.RestAssured.*;

public class LoanHelper {

    public static final String API_LOANS = "/api/loans";

    public static void requestLoan(String token, String accountId, Currency currency, BigDecimal amount) {
        given()
            .log().all()
            .header(LoginHelper.buildAuthorization(token))
            .body(LoanRequestDTO.builder()
                    .accountId(accountId)
                    .amount(amount)
                    .build())
            .contentType(ContentType.JSON)
        .when()
            .patch(API_LOANS+"/"+currency)
        .then()
            .log().all()
            .statusCode(204);
    }

}
