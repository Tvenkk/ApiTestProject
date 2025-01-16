package tests;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RegisterUserTest {
    private final String BASE_URL = "https://reqres.in/api/register";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest
    @CsvSourсe({
            "'eve.holt@reqres.in', 'pistol', true",

    })
}
