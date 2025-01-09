package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Работа с данными ресурсов")
@Feature("Получает несуществующий ресурс")
public class ResourceNotFoundTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";

    @Story("Получение несуществующего ресурса")
    @Description("Получение ошибки 404")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testResourceNotFound() {
        step("Отправка GET-запроса");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/777")
                .then()
                .statusCode(404) // Проверяем, что статус код 404 Not Found
                .extract()
                .response();

        step("Проверка, что тело ответа пустое");
        String responseBody = response.getBody().asString();
        assertEquals("{}", responseBody, "Тело ответа не является пустым");
    }
}
