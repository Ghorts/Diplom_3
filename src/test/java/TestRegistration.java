import api.ApiSettings;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pom.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class TestRegistration {
    LoginPage loginPage;
    String userMail = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
    String userPassword = RandomStringUtils.randomAlphabetic(10);
    String userName = RandomStringUtils.randomAlphabetic(10);

    @Before
    @DisplayName("Открытие браузера")
    public void openBrowser() {
        loginPage = open("https://stellarburgers.nomoreparties.site/", LoginPage.class);
    }

    @After
    @DisplayName("Удаление аккаунта")
    public void delete() {
        if (loginPage.loginText.isDisplayed()) {
            String token = new StringBuilder(ApiSettings.login(userMail, userPassword).statusCode(200).extract().path("accessToken")).delete(0, 6).toString();
            ApiSettings.delete(token).statusCode(202);
        }
    }

    @Test
    @DisplayName("Успешная регистрация клиента")
    public void registrationUserWithValidCredentialsSuccess() {
        LoginPage.registration(userName, userMail, userPassword);
        LoginPage.assertElement(loginPage.loginText, "Вход");
    }

    @Test
    @DisplayName("Проверка текста ошибки для некорректного пароля меньше 6 символов")
    public void registrationUserWithInvalidPasswordFailed() {
        LoginPage.registration(userName, userMail, "12345");
        LoginPage.assertElement(loginPage.passwordErrorText, "Некорректный пароль");
    }
}
