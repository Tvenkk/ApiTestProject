package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.LoginRequest;
import models.LoginResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginUserTest {
    private final String BASE_URL = "https://reqres.in/api/login";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest
    @CsvSource({
            "'eve.holt@reqres.in', 'cityslicka', true",
            "'tobias.funke@reqres.in', 'cityslicka', true",
            "'byron.fields@reqres.in', 'cityslicka', true",
            "'peter@klaven', '', false",
            "'kek@klaven', '', false"
    })
    public void testLoginUser(String email, String password, boolean isSuccess) throws JsonProcessingException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        step("Отправляем POST-запрос на регистрацию");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post(BASE_URL)
                .then()
                .extract()
                .response();

        if (isSuccess) {
            step("Проверяем успешную авторизацию, статус код 200");
            assertEquals(200, response.getStatusCode(), "Статус код не соответствует 200");

            LoginResponse loginResponse = objectMapper.readValue(response.asString(), LoginResponse.class);

            step("Проверяем, что в ответе присутствует token");
            assertNotNull(loginResponse.getToken(), "Token не должен быть null");

        } else {
            step("Проверяем неуспешную авторизацию, статус код 400");
            assertEquals(400, response.getStatusCode(), "Статус код не соответствует 400");

            LoginResponse loginResponse = objectMapper.readValue(response.asString(), LoginResponse.class);

            String errorMessage = loginResponse.getError();
            step("Проверяем сообщение об ошибке");
            assertNotNull(errorMessage, "Сообщение об ошибке не должно быть null");
            assertEquals("Missing password", errorMessage, "Сообщение об ошибке не совпадает");
        }
    }
}
