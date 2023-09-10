package ru.praktikum.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.models.CreateOrderRequest;
import ru.praktikum.models.UserCreationRequest;
import ru.praktikum.models.createOrderResponse.CreateOrderResponse;
import static ru.praktikum.Constants.BASE_URL;
import static ru.praktikum.steps.GetUserOrderListApi.*;
import static ru.praktikum.steps.OrderCreateApi.*;
import static ru.praktikum.steps.UserCreationApi.createUser;
import static ru.praktikum.steps.UserCreationApi.generatedPositiveUser;
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
        UserCreationRequest user = generatedPositiveUser();
        Response responseCreateUser = createUser(user);
        String accessToken = responseCreateUser.getBody().jsonPath().getString("accessToken");
        //Создание заказа
        CreateOrderRequest createOrderRequest = generateOrderWithValidIngredients();
        CreateOrderResponse createOrderResponse = createOrderWithAuthorizationToken(accessToken,createOrderRequest);
        verifyOrderCreationResponseWithToken(createOrderResponse, user);
        //Получение списка заказов
        Response response = getUserOrderListWithAuthorizationToken(accessToken);
        verifyUserGetOrderListResponseWithAuthorizationToken(response,createOrderResponse);
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
