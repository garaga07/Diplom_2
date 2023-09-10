package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.Constants.DELETE_USER_URL;

public class UserDeleteApi {
    @Step("Отправить запрос на удаление пользователя")
    public static Response deleteUser(String accessToken) {
        return given()
                .log().all()
                .header("Authorization", accessToken)
                .when()
                .delete(DELETE_USER_URL);
    }

    @Step("Проверить ответ на запрос удаления пользователя")
    public static void verifySuccessfulUserDeleteResponse(Response response) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_ACCEPTED)
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"));
    }

}
