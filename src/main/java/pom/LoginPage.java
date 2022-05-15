package pom;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Selenide.page;

public class LoginPage {

    //Локатор кнопки "Личный кабинет"
    @FindBy(how = How.CSS, using = "#root > div > header > nav > a")
    public static SelenideElement loginButton;
    //Локатор кнопки "Зарегистрироваться"

    @FindBy(how = How.CLASS_NAME, using = "Auth_link__1fOlj")
    public static SelenideElement signUpButton;

    //Локатор полей окна регистрации
    @FindBy(how = How.NAME, using = "name")
    public static ElementsCollection registrationField;

    //Локатор поля пароль на окне регистрации
    @FindBy(how = How.NAME, using = "Пароль")
    public static SelenideElement passwordField;

    //Локатор кнопки "Зарегистрироваться"
    @FindBy(how = How.XPATH, using = ".//div/form/button")
    public static SelenideElement registrationButton;

    //Локатор текста ошибки "Некорректный пароль"
    @FindBy(how = How.XPATH, using = ".//fieldset/div/p[text()='Некорректный пароль']")
    public SelenideElement passwordErrorText;

    //Локатор для надписи "Вход"
    @FindBy(how = How.XPATH, using = ".//div/h2[text()='Вход']")
    public SelenideElement loginText;

    //Локатор для кнопки "Войти"
    @FindBy(how = How.XPATH, using = ".//div/form/button[text()='Войти']")
    public static SelenideElement getLoginButton;

    //Локатор поля email на странице входа
    @FindBy(how = How.NAME, using = "name")
    public static SelenideElement emailField;

    //Локатор кнопки Войти на форме регистрации
    @FindBy(how = How.XPATH, using = ".//div/p/a[text()= 'Войти']")
    public static SelenideElement signInButton;

    //Локатор кнопки Восстановить пароль на форме входа
    @FindBy(how = How.XPATH, using = ".//div/p/a[text()= 'Восстановить пароль']")
    public static SelenideElement recoveryPasswordButton;

    @Step("Регистрация клиента")
    public static void registration(String name, String email, String password) {
        loginButton.click();
        signUpButton.click();
        registrationButton.click();
        registrationField.get(0).setValue(name);
        registrationField.get(1).setValue(email);
        passwordField.setValue(password);
        registrationButton.click();
    }

    @Step("Авторизация клиента")
    public static LoginPage login(String email, String password) {
        emailField.setValue(email);
        passwordField.setValue(password);
        getLoginButton.click();
        return page(LoginPage.class);
    }

    @Step("Проверка присутствия необходимого элемента")
    public static void assertElement(SelenideElement element, String text) {
        element.shouldBe(Condition.visible).shouldHave(Condition.exactText(text));
    }
}
