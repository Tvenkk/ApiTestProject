package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.ResourceResponse;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Работа с данными ресурсов")
@Feature("Получает список ресурсов")
public class GetListResourceTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Story("Получение списка ресурсов")
    @Description("Получение данных о ресурсах из списка")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testGetListResource() throws Exception {
        step("Отправка GET-запроса");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200) // Проверяем, что статус код 200
                .extract()
                .response();

        step("Десериализация JSON-ответа в объект ResourceResponse");
        ResourceResponse resourceResponse = objectMapper.readValue(response.asString(), ResourceResponse.class);


        step("Проверяем, что количество ресурсов равно 6 (согласно документации Reqres)");
        assertEquals(6, resourceResponse.getData().size(), "Количество ресурсов не совпадает");
        step("Проверяем номер страницы");
        assertEquals(1, resourceResponse.getPage(), "Неверная страница");
        step("Проверяем общее количество страниц");
        assertEquals(2, resourceResponse.getTotal_pages(), "Неверное общее количество страниц");
        step("Проверяем общее количество ресурсов");
        assertEquals(12, resourceResponse.getTotal(), "Неверное количество ресурсов");
        step("Проверяем количество ресурсов на странице");
        assertEquals(6, resourceResponse.getPer_page(), "Неверное количество ресурсов на странице");

    }
}
