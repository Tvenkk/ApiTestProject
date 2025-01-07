package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.ResourceResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetListResourceTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetListResource() throws Exception {

        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200) // Проверяем, что статус код 200
                .extract()
                .response();


        ResourceResponse resourceResponse = objectMapper.readValue(response.asString(), ResourceResponse.class);

        assertEquals(6, resourceResponse.getData().size(), "Количество пользователей не совпадает");
        assertEquals(1, resourceResponse.getPage(), "Неверная страница");
        assertEquals(2, resourceResponse.getTotal_pages(), "Неверное общее количество страниц");
        assertEquals(12, resourceResponse.getTotal(), "Неверное количество страниц");
        assertEquals(6, resourceResponse.getPer_page(), "Неверное количество за страницу");

    }
}