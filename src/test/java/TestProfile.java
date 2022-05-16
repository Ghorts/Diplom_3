import api.ApiSpecifications;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.ConstructorPage;
import pages.LoginPage;
import pages.ProfilePage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class TestProfile {
    ValidatableResponse response;
    LoginPage loginPage;
    ProfilePage profilePage;
    String token;
    ConstructorPage homePage;
    String userMail = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
    String userPassword = RandomStringUtils.randomAlphabetic(10);
    String userName = RandomStringUtils.randomAlphabetic(10);

    @Before
    @DisplayName("Создаём пользователя, открываем браузер, логинимся")
    public void createUser() {
        response = ApiSpecifications.create(userMail, userPassword, userName).statusCode(200);
        token = new StringBuilder(response.extract().path("accessToken")).delete(0, 6).toString();
        homePage = open("https://stellarburgers.nomoreparties.site/", ConstructorPage.class);
        loginPage = page(LoginPage.class);
        homePage.loginButton.click();
        loginPage.login(userMail, userPassword);
        loginPage.loginButton.click();
        profilePage = page(ProfilePage.class);
    }

    @After
    @DisplayName("Удаляем созданного пользователя")
    public void deleteUser() {
        ApiSpecifications.delete(token).statusCode(202);
    }

    @Test
    @DisplayName("Переходим в личный кабинет по клику на кнопку в шапке сайта - успешно")
    public void goToProfileViaHeaderButtonSuccess() {
        profilePage.assertElement(profilePage.profileButton, "Профиль");
        profilePage.assertElement(profilePage.personalDataText, "В этом разделе вы можете изменить свои персональные данные");
    }

    @Test
    @DisplayName("Переходим из личного кабинета в конструктор по клику на кнопку в шапке сайта - успешно")
    public void goToConstructorViaHeaderButtonSuccess() {
        profilePage.constructorButton.click();
        homePage.assertElement(homePage.createBurgerText, "Соберите бургер");
        homePage.assertElement(homePage.createOrderButton, "Оформить заказ");
    }

    @Test
    @DisplayName("Выход из личного кабинета по кнопке Выйти - успешно")
    public void logOutViaLogOutButtonSuccess() {
        profilePage.logOutButton.click();
        loginPage.assertElement(loginPage.loginText, "Вход");
        loginPage.assertElement(loginPage.getLoginButton, "Войти");
    }
}
