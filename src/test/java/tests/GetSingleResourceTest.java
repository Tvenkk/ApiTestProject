package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.SingleResourceResponse;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Работа с данными ресурсов")
@Feature("Получает информацию о конкретном ресурсе")
public class GetSingleResourceTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Story("Получение информации о конкретном ресурсе")
    @Description("Получение данных конкретного ресурса")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testGetSingleResource() throws Exception {
        step("Отправка GET-запроса");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/2")
                .then()
                .statusCode(200) // Проверяем, что статус код 200
                .extract()
                .response();

        step("Десериализация JSON-ответа в объект SingleResourceResponse");
        SingleResourceResponse SingleResourceResponse = objectMapper.readValue(response.asString(), SingleResourceResponse.class);

        step("Проверяем, что ID данного ресурса совпадает");
        assertEquals(2, SingleResourceResponse.getData().getId(), "ID данного ресурса не совпадает");
    }
}
