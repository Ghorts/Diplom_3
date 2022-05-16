import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pages.ConstructorPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

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
        constructorPage.sectionClick(constructorPage.sauceSection, constructorPage.sauceSectionName);
        constructorPage.assertVisibleElement(constructorPage.sauceSpike);
    }

    @Test
    @DisplayName("Переход в раздел начинки - успешно")
    public void openFillingSectionSuccess() {
        constructorPage.sectionClick(constructorPage.fillingSection, constructorPage.fillingSectionName);
        constructorPage.assertVisibleElement(constructorPage.fillingPatty);
    }

    @Test
    @DisplayName("Переход в раздел булки - успешно")
    public void openBunSectionSuccess() {
        assertTrue(constructorPage.sectionClick(constructorPage.fillingSection, constructorPage.fillingSectionName));
        constructorPage.fillingPatty.scrollIntoView(true);
        assertTrue(constructorPage.sectionClick(constructorPage.bunSection, constructorPage.bunSectionName));
        constructorPage.assertVisibleElement(constructorPage.bunCrater);
    }
}
