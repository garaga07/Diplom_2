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
import static ru.praktikum.steps.OrderCreateApi.*;
import static ru.praktikum.steps.UserCreationApi.createUser;
import static ru.praktikum.steps.UserCreationApi.generatedPositiveUser;
import static ru.praktikum.steps.UserDeleteApi.deleteUser;
import static ru.praktikum.steps.UserDeleteApi.verifySuccessfulUserDeleteResponse;

@Feature("Создание заказов")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Тесты на создание заказов")
public class UserOrderCreateTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Созданиие заказа с игредиентами авторизованным пользователем")
    public void createOrderWithTokenTest() {
        //Создание пользователя
        UserCreationRequest user = generatedPositiveUser();
        Response responseCreateUser = createUser(user);
        String accessToken = responseCreateUser.getBody().jsonPath().getString("accessToken");
        //Создание заказа
        CreateOrderRequest createOrderRequest = generateOrderWithValidIngredients();
        CreateOrderResponse response = createOrderWithAuthorizationToken(accessToken,createOrderRequest);
        verifyOrderCreationResponseWithToken(response, user);
        //Удаление пользователя
        Response responseDelete = deleteUser(accessToken);
        verifySuccessfulUserDeleteResponse(responseDelete);
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Созданиие заказа с игредиентами неавторизованным пользователем")
    public void createOrderWithoutTokenTest() {
        //Создание заказа
        CreateOrderRequest createOrderRequest = generateOrderWithValidIngredients();
        Response response = createOrderWithoutAuthorizationToken(createOrderRequest);
        verifyOrderCreationResponseWithoutToken(response);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Созданиие заказа с неверным хешем ингредиентов")
    public void createOrderWithIncorrectHashIngredientsTest() {
        //Создание заказа
        CreateOrderRequest createOrderRequest = generateOrderWithInvalidIngredientsHash();
        Response response = createOrderWithoutAuthorizationToken(createOrderRequest);
        verifyOrderCreationResponseWithInvalidIngredientHash(response);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Созданиие заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        //Создание заказа
        CreateOrderRequest createOrderRequest = generateOrderWithoutIngredients();
        Response response = createOrderWithoutAuthorizationToken(createOrderRequest);
        verifyOrderCreationResponseWithoutIngredients(response);
    }

}
