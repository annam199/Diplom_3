package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import praktikum.core.UserTestHelper;
import praktikum.models.User;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import static org.junit.Assert.assertTrue;

@DisplayName("Тесты конструктора")
public class ConstructorTests extends BaseTest {
    private String accessToken;
    private User testUser;

    @Before
    public void setUp() {
        super.setUp();

        // Создание и регистрация пользователя через UserTestHelper
        testUser = UserTestHelper.generateUser();
        accessToken = UserTestHelper.registerAndLogin(testUser);

        // Авторизация через веб-интерфейс
        driver.get(baseUrl + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageToLoad();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        MainPage mainPage = new MainPage(driver);
        mainPage.waitForPageToLoad();
        assertTrue(mainPage.isLoggedIn());
    }

    @Test
    @Description("Проверка перехода к разделу булок в конструкторе")
    public void testNavigateToBuns() {
        MainPage mainPage = new MainPage(driver);
        assertTrue("Раздел 'Булки' не активен по умолчанию", mainPage.isBunsSectionActive());
        mainPage.clickFillingsSection();
        assertTrue("Раздел 'Начинки' не стал активным", mainPage.isFillingsSectionActive());
        mainPage.clickBunsSection();

        assertTrue("Раздел 'Булки' не стал активным", mainPage.isBunsSectionActive());
    }

    @Test
    @Description("Проверка перехода к разделу соусов в конструкторе")
    public void testNavigateToSauces() {
        MainPage mainPage = new MainPage(driver);
        assertTrue("Раздел 'Булки' не активен по умолчанию", mainPage.isBunsSectionActive());

        mainPage.clickFillingsSection();
        assertTrue("Раздел 'Начинки' не стал активным", mainPage.isFillingsSectionActive());
        assertTrue("Заголовок 'Начинки' не виден", mainPage.isFillingsTitleDisplayed());

        mainPage.clickSaucesSection();

        assertTrue("Раздел 'Соусы' не стал активным", mainPage.isSaucesSectionActive());
        assertTrue("Заголовок 'Соусы' не виден", mainPage.isSaucesTitleDisplayed());
    }

    @Test
    @Description("Проверка перехода к разделу начинок в конструкторе")
    public void testNavigateToFilling() {
        MainPage mainPage = new MainPage(driver);
        assertTrue("Раздел 'Булки' не активен по умолчанию", mainPage.isBunsSectionActive());

        mainPage.clickFillingsSection();

        assertTrue("Раздел 'Начинки' не стал активным", mainPage.isFillingsSectionActive());
        assertTrue("Заголовок 'Начинки' не виден", mainPage.isFillingsTitleDisplayed());
    }

    @After
    public void tearDown() {
        UserTestHelper.deleteUser(accessToken);
        super.tearDown();
    }
}