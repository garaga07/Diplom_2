package ru.praktikum.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.models.UserCreateOrderRequest;
import ru.praktikum.models.UserCreateRequest;
import ru.praktikum.models.userCreateOrderResponse.UserCreateOrderResponse;
import static ru.praktikum.Constants.BASE_URL;
import static ru.praktikum.steps.UserGetOrderListApi.*;
import static ru.praktikum.steps.UserCreateOrderApi.*;
import static ru.praktikum.steps.UserCreateApi.createUser;
import static ru.praktikum.steps.UserCreateApi.generatedPositiveUser;
import static ru.praktikum.steps.UserDeleteApi.deleteUser;
import static ru.praktikum.steps.UserDeleteApi.verifySuccessfulUserDeleteResponse;

@Feature("Получения списка заказов пользователем")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Тесты на получение списка заказов пользователем")
public class UserGetOrderListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение списка заказов авторизованным пользователем")
    public void usersGetOrderListWithAuthorizationTokenTest() {
        //Создание пользователя
        UserCreateRequest user = generatedPositiveUser();
        Response responseCreateUser = createUser(user);
        String accessToken = responseCreateUser.getBody().jsonPath().getString("accessToken");
        //Создание заказа
        UserCreateOrderRequest userCreateOrderRequest = generateOrderWithValidIngredients();
        UserCreateOrderResponse userCreateOrderResponse = createOrderWithAuthorizationToken(accessToken, userCreateOrderRequest);
        verifyOrderCreationResponseWithToken(userCreateOrderResponse, user);
        //Получение списка заказов
        Response response = getUserOrderListWithAuthorizationToken(accessToken);
        verifyUserGetOrderListResponseWithAuthorizationToken(response, userCreateOrderResponse);
        //Удаление пользователя
        Response responseDelete = deleteUser(accessToken);
        verifySuccessfulUserDeleteResponse(responseDelete);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение списка заказов неавторизованным пользователем")
    public void usersGetOrderListWithoutAuthorizationTokenTest() {
        Response response = getUserOrderListWithoutAuthorizationToken();
        verifyUserGetOrderListResponseWithoutAuthorizationToken(response);
    }
}
