package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.models.userCreateOrderResponse.UserCreateOrderResponse;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.*;
import static ru.praktikum.Constants.GET_USER_ORDER_LIST_URL;

public class UserGetOrderListApi {
    @Step("Отправить запрос на получение списка заказов авторизованным пользователем")
    public static Response getUserOrderListWithAuthorizationToken(String accessToken) {
        return given()
                .log().all()
                .header("Authorization", accessToken)
                .when()
                .get(GET_USER_ORDER_LIST_URL);
    }

    @Step("Отправить запрос на получение списка заказов неавторизованным пользователем")
    public static Response getUserOrderListWithoutAuthorizationToken() {
        return given()
                .log().all()
                .when()
                .get(GET_USER_ORDER_LIST_URL);
    }

    @Step("Проверить ответ на получение списка заказов авторизованным пользователем")
    public static void verifyUserGetOrderListResponseWithAuthorizationToken(Response response, UserCreateOrderResponse userCreateOrderResponse) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("orders[0]._id", equalTo(userCreateOrderResponse.getOrder().getId()))
                .body("orders[0].ingredients[0]", equalTo(userCreateOrderResponse.getOrder().getIngredients().get(0).getId()))
                .body("orders[0].status", equalTo(userCreateOrderResponse.getOrder().getStatus()))
                .body("orders[0].name", equalTo(userCreateOrderResponse.getOrder().getName()))
                .body("orders[0].createdAt", equalTo(userCreateOrderResponse.getOrder().getCreatedAt()))
                .body("orders[0].updatedAt", equalTo(userCreateOrderResponse.getOrder().getUpdatedAt()))
                .body("total", equalTo(userCreateOrderResponse.getOrder().getNumber()))
                .body("totalToday", greaterThan(0));
    }

    @Step("Проверить ответ на получение списка заказов неавторизованным пользователем")
    public static void verifyUserGetOrderListResponseWithoutAuthorizationToken(Response response) {
        response.then()
                .log().all()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
