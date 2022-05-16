package api;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import pages.LoginPage;
import test.data.GeneratorTestData;

import static io.restassured.RestAssured.given;

public class ApiSpecifications {

    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    public static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

    public static RequestSpecification getAuthSpec(String token) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer" + token)
                .setBaseUri(BASE_URL)
                .build();
    }

    @Step("Отправка запроса на создание пользователя")
    public static ValidatableResponse create(String email, String password, String name) {
        return given()
                .spec(getBaseSpec())
                .body("{\"email\": \"" + email + "\","
                        + "\"password\": \"" + password + "\","
                        + "\"name\": \"" + name + "\"}")
                .when()
                .post("/api/auth/register")
                .then();
    }

    @Step("Отправка запроса на удаление пользователя")
    public static ValidatableResponse delete(String auth) {
        return given()
                .spec(getAuthSpec(auth))
                .when()
                .delete("/api/auth/user")
                .then();
    }

    @Step("Отправка запроса на логин")
    public static ValidatableResponse login(String email, String password) {
        return given()
                .spec(getBaseSpec())
                .body("{\"email\":\"" + email + "\"," + "\"password\":\"" + password + "\"}")
                .when()
                .post("/api/auth/login")
                .then();
    }

    @Step("Удаление пользователя (при условии, что он был создан)")
    public static void assertDelete(LoginPage loginPage, String mail, String password) {
        if (loginPage.loginText.isDisplayed()) {
            String token = new StringBuilder(ApiSpecifications.login(mail, password).statusCode(200).extract().path("accessToken")).delete(0, 6).toString();
            ApiSpecifications.delete(token).statusCode(202);
        }
    }
}
