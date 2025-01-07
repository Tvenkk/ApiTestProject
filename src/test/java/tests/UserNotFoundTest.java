package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserNotFoundTest {
    private final String BASE_URL = "https://reqres.in/api/users";

    @Test
    public void testUserNotFound() {
        // //Отправка GET-запроса на получение несуществующего пользователя (например, ID 9999)
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/9999")
                .then()
                .statusCode(404) // Проверяем, что статус код 404 Not Found
                .extract()
                .response();

        // Проверка, что тело ответа пустое
        String responseBody = response.getBody().asString();
        assertEquals("{}", responseBody, "Тело ответа не является пустым");
    }
}