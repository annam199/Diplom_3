package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import praktikum.core.UserTestHelper;
import praktikum.models.User;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;

import static org.junit.Assert.assertTrue;

@DisplayName("Тесты заказа")
public class OrderTests extends BaseTest {
    private String accessTokenForCleanup;
    private User testUser;

    @Before
    public void setUp() {
        super.setUp();

        // Создание и регистрация пользователя через UserTestHelper
        testUser = UserTestHelper.generateUser();
        accessTokenForCleanup = UserTestHelper.registerAndLogin(testUser);

        // Авторизация через веб-интерфейс
        driver.get(baseUrl + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageToLoad();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        MainPage mainPage = new MainPage(driver);
        mainPage.waitForPageToLoad();
        assertTrue("Не удалось войти в аккаунт для создания заказа", mainPage.isLoggedIn());
    }

    @Test
    @Description("Проверка создания заказа авторизованным пользователем")
    public void testCreateOrder() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsSection();
        assertTrue("Раздел 'Начинки' не стал активным", mainPage.isFillingsSectionActive());
        mainPage.addIngredientToBurger(mainPage.getIngredientBun());
        mainPage.addIngredientToBurger(mainPage.getIngredientSauce());
        mainPage.addIngredientToBurger(mainPage.getIngredientFilling());
        mainPage.clickOrderButton();
        assertTrue("Сообщение 'Ваш заказ начали готовить' не отображается",
                mainPage.isOrderInProgressDisplayed());
    }

    @Test
    @Description("Проверка входа и последующего выхода из аккаунта")
    public void testSuccessfulLoginAndLogout() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccountButton();
        mainPage.clickLogoutButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageToLoad();
        assertTrue("Не произошел переход на страницу входа после выхода",
                loginPage.isLoginFormDisplayed());
    }

    @After
    public void tearDown() {
        UserTestHelper.deleteUser(accessTokenForCleanup);
        super.tearDown();
    }
}