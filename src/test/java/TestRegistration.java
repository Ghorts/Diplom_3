import api.ApiSpecifications;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.LoginPage;
import test.data.GeneratorTestData;

import static com.codeborne.selenide.Selenide.open;

public class TestRegistration {
    LoginPage loginPage;
    String userMail = GeneratorTestData.getRandomMail();
    String userPassword = GeneratorTestData.getRandomString();
    String userName = GeneratorTestData.getRandomString();

    @Before
    @DisplayName("Открытие браузера")
    public void openBrowser() {
        loginPage = open("https://stellarburgers.nomoreparties.site/", LoginPage.class);
    }

    @After
    @DisplayName("Удаление аккаунта")
    public void delete() {
        ApiSpecifications.assertDelete(loginPage, userMail, userPassword);
    }

    @Test
    @DisplayName("Успешная регистрация клиента")
    public void registrationUserWithValidCredentialsSuccess() {
        loginPage.registration(userName, userMail, userPassword);
        loginPage.assertElement(loginPage.loginText, "Вход");
    }

    @Test
    @DisplayName("Проверка текста ошибки для некорректного пароля меньше 6 символов")
    public void registrationUserWithInvalidPasswordFailed() {
        loginPage.registration(userName, userMail, "12345");
        loginPage.assertElement(loginPage.passwordErrorText, "Некорректный пароль");
    }
}
