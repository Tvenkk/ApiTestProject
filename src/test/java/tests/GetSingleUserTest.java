package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.SingleUserResponse;
import models.UserData;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Работа с данными пользователей")
@Feature("Получает информацию о конкретном пользователе")
public class GetSingleUserTest {
    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Story("Получение информации о конкретном пользователе")
    @Description("Получение данных конкретного пользователя")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testGetSingleUser() throws Exception {
        step("Отправка GET-запроса");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/2")
                .then()
                .statusCode(200) // Проверяем, что статус код 200
                .extract()
                .response();

        step("Десериализация JSON-ответа в объект SingleUserResponse");
        SingleUserResponse SingleUserResponse = objectMapper.readValue(response.asString(), SingleUserResponse.class);

        step("Проверяем, что email пользователя оканчивается на @reqres.in");
        UserData user = SingleUserResponse.getData();
        assertTrue(user.getEmail().endsWith("@reqres.in"), "Email пользователя " + user.getId() + " не оканчивается на reqres.in");
    }
}
