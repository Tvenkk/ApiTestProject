package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.UserCredetials;
import models.UserModelResponse;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Epic("Работа с данными пользователей")
@Feature("Создание пользователя")
public class CreateUserTest {
    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Description("Создание пользователя только с именем")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testCreateUserWithoutName() throws JsonProcessingException {
        UserCredetials user = new UserCredetials("morpheus");

        step("Отправляем POST-запрос");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201) // Проверяем, что статус код 201
                .extract()
                .response();

        step("Десериализация JSON-ответа в объект UserModelResponse");
        UserModelResponse userModelResponse = objectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем, что в ответе присутствуют id и createdAt");
        assertNotNull(userModelResponse.getId(), "ID не должен быть null");
        assertNotNull(userModelResponse.getCreatedAt(), "createdAt не должен быть null");

        step("Проверяем, что имя и работа совпадают с теми, что были отправлены");
        assertEquals(user.getName(), userModelResponse.getName(), "Имя пользователя не совпадает");
        assertEquals(user.getJob(), userModelResponse.getJob(), "Профессия пользователя не совпадает");
    }


    @Description("Создание пользователя с именем и работой")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testCreateUserWithNameJob() throws JsonProcessingException {
        UserCredetials user = new UserCredetials("morpheus", "leader");

        step("Отправляем POST-запрос");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201) // Проверяем, что статус код 201
                .extract()
                .response();

        step("Десериализация JSON-ответа в объект UserModelResponse");
        UserModelResponse userModelResponse = objectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем, что в ответе присутствуют id и createdAt");
        assertNotNull(userModelResponse.getId(), "ID не должен быть null");
        assertNotNull(userModelResponse.getCreatedAt(), "createdAt не должен быть null");

        step("Проверяем, что имя и работа совпадают с теми, что были отправлены");
        assertEquals(user.getName(), userModelResponse.getName(), "Имя пользователя не совпадает");
        assertEquals(user.getJob(), userModelResponse.getJob(), "Профессия пользователя не совпадает");
    }
}
