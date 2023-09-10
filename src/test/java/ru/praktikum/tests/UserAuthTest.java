package ru.praktikum.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.models.UserAuthorizationRequest;
import ru.praktikum.models.UserCreateRequest;
import static ru.praktikum.Constants.BASE_URL;
import static ru.praktikum.steps.UserAuthApi.*;
import static ru.praktikum.steps.UserCreateApi.createUser;
import static ru.praktikum.steps.UserCreateApi.generatedPositiveUser;
import static ru.praktikum.steps.UserDeleteApi.deleteUser;
import static ru.praktikum.steps.UserDeleteApi.verifySuccessfulUserDeleteResponse;

@Feature("Авторизация пользователя")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Тесты на авторизацию пользователя")
public class UserAuthTest {
    private String accessToken;
    private UserCreateRequest user;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        //Создание пользователя
        user = generatedPositiveUser();
        Response responseCreateUser = createUser(user);
        accessToken = responseCreateUser.getBody().jsonPath().getString("accessToken");
    }

    @After
    public void tearDown() {
        //Удаление пользователя
        Response responseDelete = deleteUser(accessToken);
        verifySuccessfulUserDeleteResponse(responseDelete);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Авторизация пользователя в системе с существующей парой логин/пароль")
    public void validCredentialsUserAuthorizationTest() {
        UserAuthorizationRequest userAuthorizationRequest = getUserAuthorizationCredentialsAfterCreate(user);
        Response responseAuthorizationUser = authorizationUser(userAuthorizationRequest);
        verifySuccessfulAuthorizationAfterUserCreation(responseAuthorizationUser, user);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Авторизация пользователя в системе с несуществующей парой логин/пароль")
    public void invalidCredentialsUserAuthorizationTest() {
        UserAuthorizationRequest userAuthorizationRequest = generateUserAuthorizationRequestWithIncorrectPassword(user);
        Response responseAuthorizationUser = authorizationUser(userAuthorizationRequest);
        verifyUnauthorizedUserAuthorizationResponse(responseAuthorizationUser);
    }
}


