package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.models.CreateOrderRequest;
import ru.praktikum.models.UserCreationRequest;
import ru.praktikum.models.createOrderResponse.CreateOrderResponse;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static ru.praktikum.Constants.*;

public class OrderCreateApi {
    @Step("Сформировать тело запроса для создания заказа с ингредиентами")
    public static CreateOrderRequest generateOrderWithValidIngredients() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(INGREDIENT_HASH_1);
        ingredients.add(INGREDIENT_HASH_2);
        ingredients.add(INGREDIENT_HASH_3);
        return new CreateOrderRequest(ingredients);
    }

    @Step("Сформировать тело запроса для создания заказа без ингредиентов")
    public static CreateOrderRequest generateOrderWithoutIngredients() {
        List<String> ingredients = new ArrayList<>();
        return new CreateOrderRequest(ingredients);
    }

    @Step("Сформировать тело запроса для создания заказа с неверным хешем ингредиента")
    public static CreateOrderRequest generateOrderWithInvalidIngredientsHash() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(INVALID_INGREDIENT_HASH);
        return new CreateOrderRequest(ingredients);
    }

    @Step("Отправить запрос для создания заказа с токеном авторизации")
    public static CreateOrderResponse createOrderWithAuthorizationToken(String accessToken, CreateOrderRequest order) {
        return given()
                .log().all()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER_URL)
                .then()
                .extract()
                .body()
                .as(CreateOrderResponse.class);
    }

    @Step("Отправить запрос для создания заказа без токена авторизации")
    public static Response createOrderWithoutAuthorizationToken(CreateOrderRequest order) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER_URL);
    }

    @Step("Проверить ответ на создание заказа с токеном авторизации")
    public static void verifyOrderCreationResponseWithToken(CreateOrderResponse response, UserCreationRequest user) {
        assertTrue(response.isSuccess());
        assertNotNull(response.getName());
        assertNotNull(response.getOrder().getIngredients());
        assertNotNull(response.getOrder().getId());
        assertEquals(user.getName(), response.getOrder().getOwner().getName());
        assertEquals(user.getEmail(), response.getOrder().getOwner().getEmail());
        assertNotNull(response.getOrder().getOwner().getCreatedAt());
        assertNotNull(response.getOrder().getOwner().getUpdatedAt());
        assertEquals("done", response.getOrder().getStatus());
        assertNotNull(response.getOrder().getName());
        assertNotNull(response.getOrder().getCreatedAt());
        assertNotNull(response.getOrder().getUpdatedAt());
        assertTrue(response.getOrder().getNumber() > 0);
        assertTrue(response.getOrder().getPrice() > 0);
    }

    @Step("Проверить ответ на создание заказа без токена авторизации")
    public static void verifyOrderCreationResponseWithoutToken(Response response) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }

    @Step("Проверить ответ на создание заказа с неверным хешем ингредиентов")
    public static void verifyOrderCreationResponseWithInvalidIngredientHash(Response response) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("One or more ids provided are incorrect"));
    }

    @Step("Проверить ответ на создание заказа без ингредиентов")
    public static void verifyOrderCreationResponseWithoutIngredients(Response response) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }
}
