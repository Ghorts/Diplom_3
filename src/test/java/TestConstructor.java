import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pom.ConstructorPage;

import static com.codeborne.selenide.Selenide.open;

public class TestConstructor {
    ConstructorPage constructorPage;

    @Before
    @DisplayName("Открытие браузера")
    public void openBrowser() {
        constructorPage = open("https://stellarburgers.nomoreparties.site/", ConstructorPage.class);
    }

    @Test
    @DisplayName("Переход в раздел соусы - успешно")
    public void openSauceSectionSuccess() {
        ConstructorPage.sectionClick(constructorPage.sauceSection, constructorPage.sauceSectionName);
        ConstructorPage.assertVisibleElement(constructorPage.sauceSpike);
    }

    @Test
    @DisplayName("Переход в раздел начинки - успешно")
    public void openFillingSectionSuccess() {
        ConstructorPage.sectionClick(constructorPage.fillingSection, constructorPage.fillingSectionName);
        ConstructorPage.assertVisibleElement(constructorPage.fillingPatty);
    }

    @Test
    @DisplayName("Переход в раздел булки - успешно")
    public void openBunSectionSuccess() {
        ConstructorPage.sectionClick(constructorPage.fillingSection, constructorPage.fillingSectionName);
        constructorPage.fillingPatty.scrollIntoView(true);
        ConstructorPage.sectionClick(constructorPage.bunSection, constructorPage.bunSectionName);
        ConstructorPage.assertVisibleElement(constructorPage.bunCrater);
    }
}
