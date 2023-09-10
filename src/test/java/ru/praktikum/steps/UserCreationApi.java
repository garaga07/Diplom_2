package ru.praktikum.steps;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.models.UserCreationRequest;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;
import static ru.praktikum.Constants.CREATE_USER_URL;

public class UserCreationApi {

    private static final Faker faker = new Faker();
    public static String generatePassword() {
        return faker.internet().password();
    }

    public static String generateName() {
        return faker.name().firstName();
    }

    public static String generateEmail() { return faker.internet().emailAddress(); }

    @Step("Сформировать тело запроса для создания пользователя")
    public static UserCreationRequest generatedPositiveUser() {
        return new UserCreationRequest(generateEmail(),generateName(),generatePassword());
    }

    @Step("Сформировать тело запроса для создания пользователя с недостаточным набором данных - отсутствует email")
    public static UserCreationRequest generatedUserWithMissingEmail() {
        return new UserCreationRequest(null, generateName(), generatePassword());
    }

    @Step("Сформировать тело запроса для создания пользователя с недостаточным набором данных - отсутствует name")
    public static UserCreationRequest generatedUserWithMissingName() {
        return new UserCreationRequest(generateEmail(), null, generatePassword());
    }

    @Step("Сформировать тело запроса для создания пользователя с недостаточным набором данных - отсутствует password")
    public static UserCreationRequest generatedUserWithMissingPassword() {
        return new UserCreationRequest(generateEmail(), generateName(), null);
    }

    @Step("Отправить запрос для создания пользователя")
    public static Response createUser(UserCreationRequest user) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(CREATE_USER_URL);
    }

    @Step("Проверить ответ на запрос создания пользователя")
    public static void verifyPositiveCreationResponse(Response response, UserCreationRequest expectedUser) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(expectedUser.getEmail()))
                .body("user.name", equalTo(expectedUser.getName()))
                .body("accessToken", containsString("Bearer "))
                .body("refreshToken", notNullValue());
    }

    @Step("Проверить ответ на запрос создания пользователя с недостаточным набором данных")
    public static void verifyMissingDataCreationResponse(Response response) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Проверить ответ на запрос создания пользователя c повторяющимся email")
    public static void verifyDuplicateEmailCreationResponse(Response response) {
        response.then()
                .log().all()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }



}
