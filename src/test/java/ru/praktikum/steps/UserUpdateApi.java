package ru.praktikum.steps;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.models.UserUpdateRequest;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.*;
import static ru.praktikum.Constants.UPDATE_USER_URL;
public class UserUpdateApi {
    private static final Faker faker = new Faker();
    public static String generateEmail() { return faker.internet().emailAddress(); }
    public static String generateName() {
        return faker.name().firstName();
    }
    public static String generatePassword() {
        return faker.internet().password();
    }

    @Step("Сгенерировать набор данных для редактирования пользователя")
    public static UserUpdateRequest generatedUpdateUser() {
        return new UserUpdateRequest(generateEmail(),generateName(),generatePassword());
    }
    @Step("Отправить запрос на редактирование пользователя с токеном авторизации")
    public static Response updateUser(String accessToken, UserUpdateRequest user) {
        return given()
                .log().all()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .when()
                .body(user)
                .patch(UPDATE_USER_URL);
    }
    @Step("Отправить запрос на редактирование пользователя без токена авторизации")
    public static Response updateUserWithoutToken(UserUpdateRequest user) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .when()
                .body(user)
                .patch(UPDATE_USER_URL);
    }
    @Step("Проверить ответ на запрос редактирования пользователя с токеном авторизации")
    public static void verifyUpdateResponse(Response response, UserUpdateRequest user) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()));
    }
    @Step("Проверить ответ на запрос редактирования пользователя без токена авторизации")
    public static void verifyUpdateResponseWithoutToken(Response response) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }



}