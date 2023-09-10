package ru.praktikum.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.models.UserCreationRequest;
import static ru.praktikum.Constants.BASE_URL;
import static ru.praktikum.steps.UserCreationApi.*;
import static ru.praktikum.steps.UserDeleteApi.deleteUser;
import static ru.praktikum.steps.UserDeleteApi.verifySuccessfulUserDeleteResponse;

@Feature("Создание пользователя")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Тесты на создание пользователя")
public class UserCreationTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание пользователя - позитивный сценарий")
    public void positiveUserCreationTest() {
        //Создание пользователя
        UserCreationRequest user = generatedPositiveUser();
        Response responseCreateUser = createUser(user);
        verifyPositiveCreationResponse(responseCreateUser,user);
        String accessToken = responseCreateUser.getBody().jsonPath().getString("accessToken");
        //Удаление пользователя
        Response responseDelete = deleteUser(accessToken);
        verifySuccessfulUserDeleteResponse(responseDelete);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание пользователя с повторяющимся email")
    public void duplicateEmailUserCreationTest() {
        //Создание пользователя
        UserCreationRequest user = generatedPositiveUser();
        Response responseCreateUser = createUser(user);
        String accessToken = responseCreateUser.getBody().jsonPath().getString("accessToken");
        //Создание пользователя c повторяющимся email
        Response response = createUser(user);
        verifyDuplicateEmailCreationResponse(response);
        //Удаление пользователя
        Response responseDelete = deleteUser(accessToken);
        verifySuccessfulUserDeleteResponse(responseDelete);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка валидации при создании пользователя - отсутствует email")
    public void missingEmailUserCreationTest() {
        UserCreationRequest user = generatedUserWithMissingEmail();
        Response response = createUser(user);
        verifyMissingDataCreationResponse(response);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка валидации при создании пользователя - отсутствует password")
    public void missingPasswordUserCreationTest() {
        UserCreationRequest user = generatedUserWithMissingPassword();
        Response response = createUser(user);
        verifyMissingDataCreationResponse(response);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка валидации при создании пользователя - отсутствует name")
    public void missingNameUserCreationTest() {
        UserCreationRequest user = generatedUserWithMissingName();
        Response response = createUser(user);
        verifyMissingDataCreationResponse(response);
    }


}
