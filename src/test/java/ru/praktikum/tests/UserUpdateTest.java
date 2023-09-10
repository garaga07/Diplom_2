package ru.praktikum.tests;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.models.UserAuthorizationRequest;
import ru.praktikum.models.UserCreationRequest;
import ru.praktikum.models.UserUpdateRequest;
import static ru.praktikum.Constants.BASE_URL;
import static ru.praktikum.steps.UserAuthorizationApi.*;
import static ru.praktikum.steps.UserCreationApi.*;
import static ru.praktikum.steps.UserDeleteApi.deleteUser;
import static ru.praktikum.steps.UserDeleteApi.verifySuccessfulUserDeleteResponse;
import static ru.praktikum.steps.UserUpdateApi.*;
@Feature("Редактирование пользователя")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Тесты на редактирование пользователя")
public class UserUpdateTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Редактирование пользователя")
    public void userUpdateTest() {
        //Создание пользователя
        UserCreationRequest userCreationRequest = generatedPositiveUser();
        Response responseCreateUser = createUser(userCreationRequest);
        verifyPositiveCreationResponse(responseCreateUser,userCreationRequest);
        String accessTokenAfterCreateUser = responseCreateUser.getBody().jsonPath().getString("accessToken");
        //Редактирование пользователя
        UserUpdateRequest userUpdateRequest = generatedUpdateUser();
        Response responseUpdateUser = updateUser(accessTokenAfterCreateUser,userUpdateRequest);
        verifyUpdateResponse(responseUpdateUser,userUpdateRequest);
        //Авторизация пользователя после редактирования данных
        UserAuthorizationRequest userAuthorizationRequest = getUserAuthorizationCredentialsAfterUpdate(userUpdateRequest);
        Response responseAuthorizationUser = authorizationUser(userAuthorizationRequest);
        verifySuccessfulAuthorizationAfterUserUpdate(responseAuthorizationUser,userUpdateRequest);
        String accessTokenAfterAuthorizationUser = responseAuthorizationUser.getBody().jsonPath().getString("accessToken");
        //Удаление пользователя
        Response responseDelete = deleteUser(accessTokenAfterAuthorizationUser);
        verifySuccessfulUserDeleteResponse(responseDelete);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Редактирование пользователя без токена авторизации")
    public void userUpdateWithoutToken(){
        //Редактирование пользователя
        UserUpdateRequest userUpdateRequest = generatedUpdateUser();
        Response responseUpdateUser = updateUserWithoutToken(userUpdateRequest);
        verifyUpdateResponseWithoutToken(responseUpdateUser);
    }


}
