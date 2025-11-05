package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import praktikum.core.UserTestHelper;
import praktikum.models.User;
import praktikum.pages.ForgotPasswordPage;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegistrationPage;
import static org.junit.Assert.assertTrue;

@DisplayName("Авторизация")
public class LoginTests extends BaseTest {
    private String accessToken;
    private User testUser;

    @Before
    public void setUp() {
        super.setUp();
        testUser = UserTestHelper.generateUser();
        accessToken = UserTestHelper.registerAndLogin(testUser);
    }

    @Test
    @Description("Проверка входа через кнопку на главной странице")
    public void testLoginViaMainPageButton() {
        driver.get(baseUrl);
        MainPage mainPage = new MainPage(driver);
        mainPage.waitForPageToLoad();
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageToLoad();
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        mainPage.waitForPageToLoad();
        assertTrue("Конструктор не отображается после входа", mainPage.isLoggedIn());
    }

    @Test
    @Description("Проверка входа через личный кабинет")
    public void testLoginViaPersonalAccount() {
        driver.get(baseUrl);
        MainPage mainPage = new MainPage(driver);
        mainPage.waitForPageToLoad();
        mainPage.clickPersonalAccountButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageToLoad();
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        mainPage.waitForPageToLoad();
        assertTrue("Конструктор не отображается после входа", mainPage.isLoggedIn());
    }

    @Test
    @Description("Проверка входа через ссылку в форме регистрации")
    public void testLoginViaRegistrationForm() {
        driver.get(baseUrl + "/register");
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.waitForPageToLoad();
        registrationPage.clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageToLoad();
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        MainPage mainPage = new MainPage(driver);
        mainPage.waitForPageToLoad();
        assertTrue("Конструктор не отображается после входа", mainPage.isLoggedIn());
    }

    @Test
    @Description("Проверка входа через ссылку в форме восстановления пароля")
    public void testLoginViaForgotPasswordForm() {
        driver.get(baseUrl + "/forgot-password");
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageToLoad();
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        MainPage mainPage = new MainPage(driver);
        mainPage.waitForPageToLoad();
        assertTrue("Конструктор не отображается после входа", mainPage.isLoggedIn());
    }

    @After
    public void tearDown() {
        UserTestHelper.deleteUser(accessToken);
        super.tearDown();
    }
}