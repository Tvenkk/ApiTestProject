package tests;

import io.restassured.RestAssured;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import models.UserData;
import models.UsersResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetListUsersTest {
    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetListUsers() throws Exception {
        //Отправка GET-запроса
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200) // Проверяем, что статус код 200
                .extract()
                .response();

        // Десериализация JSON-ответа в объект UsersResponse
        UsersResponse usersResponse = objectMapper.readValue(response.asString(), UsersResponse.class);

        // Проверяем, что количество пользователей равно 6 (согласно документации Reqres)
        assertEquals(6, usersResponse.getData().size(), "Количество пользователей не совпадает");
        assertEquals(1, usersResponse.getPage(), "Неверная страница");
        assertEquals(2, usersResponse.getTotal_pages(), "Неверное общее количество страница");
        assertEquals(12, usersResponse.getTotal(), "Неверное количество страниц");

        // Проверяем, что email каждого пользователя оканчивается на @reqres.in
        for (UserData user : usersResponse.getData()) {
            assertTrue(user.getEmail().endsWith("@reqres.in"), "Email пользователя " + user.getId() + " не оканчивается на reqres.in");
        }

    }
}
