package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ProfilePage {

    //Локатор кнопки "Профиль
    @FindBy(how = How.XPATH, using = ".//nav/ul/li/a[text()='Профиль']")
    public SelenideElement profileButton;

    //Локатор надписи "В этом разделе вы можете изменить свои персональные данные"
    @FindBy(how = How.XPATH, using = ".//nav/p")
    public SelenideElement personalDataText;

    //Локатор кнопки "Конструктор" в шапке сайта
    @FindBy(how = How.XPATH, using = ".//ul/li/a/p[text()='Конструктор']")
    public SelenideElement constructorButton;

    //Локатор кнопки "Выход"
    @FindBy(how = How.XPATH, using = ".//button[text()='Выход']")
    public SelenideElement logOutButton;

    @Step("Проверка присутствия необходимого элемента")
    public void assertElement(SelenideElement element, String text) {
        element.shouldBe(Condition.visible).shouldHave(Condition.exactText(text));
    }
}
