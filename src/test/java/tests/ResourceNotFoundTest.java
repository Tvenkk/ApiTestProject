package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceNotFoundTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";

    @Test
    public void testResourceNotFound() {

        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/777")
                .then()
                .statusCode(404) // Проверяем, что статус код 404 Not Found
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertEquals("{}", responseBody, "Тело ответа не является пустым");
    }
}