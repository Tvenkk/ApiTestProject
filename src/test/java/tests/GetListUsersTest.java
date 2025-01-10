package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import models.UserData;
import models.UsersResponse;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Работа с данными пользователей")
@Feature("Получает список пользователей")
public class GetListUsersTest {
    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Story("Получение списка пользователей")
    @Description("Получение данных о пользователях из списка")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testGetListUsers() throws Exception {
        step("Отправка GET-запроса");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200) // Проверяем, что статус код 200
                .extract()
                .response();

        step("Десериализация JSON-ответа в объект UsersResponse");
        UsersResponse usersResponse = objectMapper.readValue(response.asString(), UsersResponse.class);


        step("Проверяем, что количество пользователей равно 6 (согласно документации Reqres)");
        assertEquals(6, usersResponse.getData().size(), "Количество пользователей не совпадает");
        step("Проверяем номер страницы");
        assertEquals(1, usersResponse.getPage(), "Неверная страница");
        step("Проверяем общее количество страниц");
        assertEquals(2, usersResponse.getTotal_pages(), "Неверное общее количество страница");
        step("Проверяем общее количество пользователей");
        assertEquals(12, usersResponse.getTotal(), "Неверное общее количество пользователей");

        step("Проверяем, что email каждого пользователя оканчивается на @reqres.in");
        for (UserData user : usersResponse.getData()) {
            assertTrue(user.getEmail().endsWith("@reqres.in"), "Email пользователя " + user.getId() + " не оканчивается на reqres.in");
        }

    }
}
