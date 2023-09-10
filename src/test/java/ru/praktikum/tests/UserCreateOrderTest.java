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
import static ru.praktikum.steps.UserCreateOrderApi.*;
import static ru.praktikum.steps.UserCreateApi.createUser;
import static ru.praktikum.steps.UserCreateApi.generatedPositiveUser;
import static ru.praktikum.steps.UserDeleteApi.deleteUser;
import static ru.praktikum.steps.UserDeleteApi.verifySuccessfulUserDeleteResponse;

@Feature("Создание заказов")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Тесты на создание заказов")
public class UserCreateOrderTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Созданиие заказа с игредиентами авторизованным пользователем")
    public void createOrderWithTokenTest() {
        //Создание пользователя
        UserCreateRequest user = generatedPositiveUser();
        Response responseCreateUser = createUser(user);
        String accessToken = responseCreateUser.getBody().jsonPath().getString("accessToken");
        //Создание заказа
        UserCreateOrderRequest userCreateOrderRequest = generateOrderWithValidIngredients();
        UserCreateOrderResponse response = createOrderWithAuthorizationToken(accessToken, userCreateOrderRequest);
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
        UserCreateOrderRequest userCreateOrderRequest = generateOrderWithValidIngredients();
        Response response = createOrderWithoutAuthorizationToken(userCreateOrderRequest);
        verifyOrderCreationResponseWithoutToken(response);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Созданиие заказа с неверным хешем ингредиентов")
    public void createOrderWithIncorrectHashIngredientsTest() {
        //Создание заказа
        UserCreateOrderRequest userCreateOrderRequest = generateOrderWithInvalidIngredientsHash();
        Response response = createOrderWithoutAuthorizationToken(userCreateOrderRequest);
        verifyOrderCreationResponseWithInvalidIngredientHash(response);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Созданиие заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        //Создание заказа
        UserCreateOrderRequest userCreateOrderRequest = generateOrderWithoutIngredients();
        Response response = createOrderWithoutAuthorizationToken(userCreateOrderRequest);
        verifyOrderCreationResponseWithoutIngredients(response);
    }

}
