import api.ApiSpecifications;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.ConstructorPage;
import pages.LoginPage;
import test.data.GeneratorTestData;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class TestLogin {
    ValidatableResponse response;
    LoginPage loginPage;
    String token;
    ConstructorPage homePage;
    String userMail = GeneratorTestData.getRandomMail();
    String userPassword = GeneratorTestData.getRandomString();
    String userName = GeneratorTestData.getRandomString();

    @Before
    @DisplayName("Создаём пользователя, открываем браузер")
    public void createUser() {
        response = ApiSpecifications.create(userMail, userPassword, userName).statusCode(200);
        token = new StringBuilder(response.extract().path("accessToken")).delete(0, 6).toString();
        homePage = open("https://stellarburgers.nomoreparties.site/", ConstructorPage.class);
        loginPage = page(LoginPage.class);
    }

    @After
    @DisplayName("Удаляем созданного пользователя")
    public void deleteUser() {
        ApiSpecifications.delete(token).statusCode(202);
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной - успешно")
    public void loginViaHomePageSuccess() {
        homePage.loginButton.click();
        loginPage.login(userMail, userPassword);
        homePage.assertElement(homePage.createOrderButton, "Оформить заказ");
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет» - успешно")
    public void loginViaHeaderLoginButtonSuccess() {
        loginPage.loginButton.click();
        loginPage.login(userMail, userPassword);
        homePage.assertElement(homePage.createOrderButton, "Оформить заказ");
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации - успешно")
    public void loginViaRegistrationFormSuccess() {
        loginPage.loginButton.click();
        loginPage.signUpButton.click();
        loginPage.signInButton.click();
        loginPage.login(userMail, userPassword);
        homePage.assertElement(homePage.createOrderButton, "Оформить заказ");
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановленяи пароля - успешно")
    public void loginViaRecoveryFormSuccess() {
        loginPage.loginButton.click();
        loginPage.recoveryPasswordButton.click();
        loginPage.signInButton.click();
        loginPage.login(userMail, userPassword);
        homePage.assertElement(homePage.createOrderButton, "Оформить заказ");
    }
}
