package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Работа с данными пользователей")
@Feature("Получает несуществующего пользователя")
public class UserNotFoundTest {
    private final String BASE_URL = "https://reqres.in/api/users";

    @Story("Получение несуществующего пользователя")
    @Description("Получение ошибки 404")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testUserNotFound() {
        step("Отправка GET-запроса");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/9999")
                .then()
                .statusCode(404) // Проверяем, что статус код 404 Not Found
                .extract()
                .response();

        step("Проверка, что тело ответа пустое");
        String responseBody = response.getBody().asString();
        assertEquals("{}", responseBody, "Тело ответа не является пустым");
    }
}
