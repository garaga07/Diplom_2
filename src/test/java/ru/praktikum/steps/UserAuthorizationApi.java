package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.models.UserAuthorizationRequest;
import ru.praktikum.models.UserCreationRequest;
import ru.praktikum.models.UserUpdateRequest;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;
import static ru.praktikum.Constants.AUTHORIZATION_USER_URL;

public class UserAuthorizationApi {
    @Step("Получить данные для входа в систему после создания пользователя")
    public static UserAuthorizationRequest getUserAuthorizationCredentialsAfterCreate(UserCreationRequest userCreationRequest) {
        return new UserAuthorizationRequest(userCreationRequest.getEmail(),userCreationRequest.getPassword());
    }
    @Step("Получить данные для входа в систему после редактирования пользователя")
    public static UserAuthorizationRequest getUserAuthorizationCredentialsAfterUpdate(UserUpdateRequest userUpdateRequest) {
        return new UserAuthorizationRequest(userUpdateRequest.getEmail(),userUpdateRequest.getPassword());
    }
    @Step("Получить данные для входа в систему с неверным паролем пользователя")
    public static UserAuthorizationRequest generateUserAuthorizationRequestWithIncorrectPassword(UserCreationRequest userCreationRequest) {
        return new UserAuthorizationRequest (userCreationRequest.getEmail(),"123");
    }

    @Step("Отправить запрос на авторизацию пользователя в системе")
    public static Response authorizationUser(UserAuthorizationRequest user) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(AUTHORIZATION_USER_URL);
    }

    @Step("Проверить ответ на запрос авторизации пользователя после создания")
    public static void verifySuccessfulAuthorizationAfterUserCreation(Response response, UserCreationRequest expectedUser) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", containsString("Bearer "))
                .body("refreshToken", notNullValue())
                .body("user.email", equalTo(expectedUser.getEmail()))
                .body("user.name", equalTo(expectedUser.getName()));
    }

    @Step("Проверить ответ на запрос авторизации пользователя после редактирования")
    public static void verifySuccessfulAuthorizationAfterUserUpdate(Response response, UserUpdateRequest expectedUser) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", containsString("Bearer "))
                .body("refreshToken", notNullValue())
                .body("user.email", equalTo(expectedUser.getEmail()))
                .body("user.name", equalTo(expectedUser.getName()));
    }

    @Step("Проверить ответ на запрос авторизации пользователя в систему с несуществующей парой логин/пароль")
    public static void verifyUnauthorizedUserAuthorizationResponse(Response response) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
