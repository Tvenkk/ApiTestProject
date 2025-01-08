package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.SingleResourceResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetSingleResourceTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetSingleResource() throws Exception {

        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/2")
                .then()
                .statusCode(200) // Проверяем, что статус код 200
                .extract()
                .response();

        SingleResourceResponse SingleResourceResponse = objectMapper.readValue(response.asString(), SingleResourceResponse.class);

        assertEquals(2, SingleResourceResponse.getData().getId(), "ID данного пользователя не совпадает");
    }
}