import api.ApiSettings;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pom.ConstructorPage;
import pom.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class TestLogin {
    ValidatableResponse response;
    LoginPage loginPage;
    String token;
    ConstructorPage homePage;
    String userMail = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
    String userPassword = RandomStringUtils.randomAlphabetic(10);
    String userName = RandomStringUtils.randomAlphabetic(10);

    @Before
    @DisplayName("Создаём пользователя, открываем браузер")
    public void createUser() {
        response = ApiSettings.create(userMail, userPassword, userName).statusCode(200);
        token = new StringBuilder(response.extract().path("accessToken")).delete(0, 6).toString();
        homePage = open("https://stellarburgers.nomoreparties.site/", ConstructorPage.class);
        loginPage = page(LoginPage.class);
    }

    @After
    @DisplayName("Удаляем созданного пользователя")
    public void deleteUser() {
        ApiSettings.delete(token).statusCode(202);
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной - успешно")
    public void loginViaHomePageSuccess() {
        homePage.loginButton.click();
        LoginPage.login(userMail, userPassword);
        ConstructorPage.assertElement(homePage.createOrderButton, "Оформить заказ");
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет» - успешно")
    public void loginViaHeaderLoginButtonSuccess() {
        LoginPage.loginButton.click();
        LoginPage.login(userMail, userPassword);
        ConstructorPage.assertElement(homePage.createOrderButton, "Оформить заказ");
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации - успешно")
    public void loginViaRegistrationFormSuccess() {
        LoginPage.loginButton.click();
        LoginPage.signUpButton.click();
        LoginPage.signInButton.click();
        LoginPage.login(userMail, userPassword);
        ConstructorPage.assertElement(homePage.createOrderButton, "Оформить заказ");
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановленяи пароля - успешно")
    public void loginViaRecoveryFormSuccess() {
        LoginPage.loginButton.click();
        LoginPage.recoveryPasswordButton.click();
        LoginPage.signInButton.click();
        LoginPage.login(userMail, userPassword);
        ConstructorPage.assertElement(homePage.createOrderButton, "Оформить заказ");
    }
}
