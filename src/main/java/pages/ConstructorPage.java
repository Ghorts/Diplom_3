package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ConstructorPage {

    //Локатор кнопки "Войти в аккаунт"
    @FindBy(how = How.XPATH, using = ".//div/button[text()= 'Войти в аккаунт']")
    public SelenideElement loginButton;

    //Локатор кнопки "Оформить заказ"
    @FindBy(how = How.XPATH, using = ".//div/button[text()= 'Оформить заказ']")
    public SelenideElement createOrderButton;

    //Локатор надписи "Соберите бургер"
    @FindBy(how = How.XPATH, using = ".//div/main/section/h1[text()='Соберите бургер']")
    public SelenideElement createBurgerText;

    //Локатор раздела "Булки"
    @FindBy(how = How.XPATH, using = ".//div/span[text()='Булки']")
    public SelenideElement bunSection;

    //Локатор раздела "Соусы"
    @FindBy(how = How.XPATH, using = ".//div/span[text()='Соусы']")
    public SelenideElement sauceSection;

    //Локатор раздела "Начинки"
    @FindBy(how = How.XPATH, using = ".//div/span[text()='Начинки']")
    public SelenideElement fillingSection;

    //Локатор названия раздела "Булки"
    @FindBy(how = How.XPATH, using = ".//div/h2[text()='Булки']")
    public SelenideElement bunSectionName;

    //Локатор булки кратерной
    @FindBy(how = How.XPATH, using = ".//p[text()='Краторная булка N-200i']")
    public SelenideElement bunCrater;

    //Локатор названия раздела "Соусы"
    @FindBy(how = How.XPATH, using = ".//div/h2[text()='Соусы']")
    public SelenideElement sauceSectionName;

    //Локатор соуса с шипами
    @FindBy(how = How.XPATH, using = ".//p[text()='Соус с шипами Антарианского плоскоходца']")
    public SelenideElement sauceSpike;

    //Локатор названия раздела "Начинки"
    @FindBy(how = How.XPATH, using = ".//div/h2[text()='Начинки']")
    public SelenideElement fillingSectionName;

    //Локатор начинки биокотлета
    @FindBy(how = How.XPATH, using = ".//p[text()='Биокотлета из марсианской Магнолии']")
    public SelenideElement fillingPatty;

    @Step("Проверка присутствия необходимого элемента")
    public void assertElement(SelenideElement element, String text) {
        element.shouldBe(Condition.visible).shouldHave(Condition.exactText(text));
    }

    @Step("Проверка, что элемент на экране не отображается")
    public void assertVisibleElement(SelenideElement element) {
        element.isDisplayed();
    }

    @Step("Переход между разделами")
    public boolean sectionClick(SelenideElement element, SelenideElement visibleElement) {
        element.click();
        return visibleElement.isDisplayed();
    }
}
