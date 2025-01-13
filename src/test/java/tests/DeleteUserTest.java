package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Epic("Работа с данными пользователей")
@Feature("Удаление пользователя")
public class DeleteUserTest {
    private final String BASE_URL = "https://reqres.in/api/users";

    @Description("Удаление пользователя")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testDeleteUser() {
        step("Отправка DELETE-запроса");
        Response response = RestAssured
                .given()
                .when()
                .delete(BASE_URL + "/2")
                .then()
                .statusCode(204) // Проверяем, что статус код 204
                .extract()
                .response();
    }
}
