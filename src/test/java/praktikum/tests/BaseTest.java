package praktikum.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import praktikum.config.BrowserConfig;
import praktikum.config.TestConfig;
import praktikum.core.BrowserType;
import praktikum.core.DriverFactory;
import praktikum.core.DriverManager;

public class BaseTest {
    protected WebDriver driver;
    protected String baseUrl;

    @Before
    public void setUp() {
        // Загрузка конфигурации
        TestConfig config = TestConfig.getInstance();
        BrowserConfig browserConfig = new BrowserConfig();

        // Получение базового URL
        baseUrl = config.getBaseUrl();

        // Создание драйвера
        BrowserType browserType = browserConfig.getBrowserType();
        boolean headless = browserConfig.isHeadless();

        driver = DriverFactory.createDriver(browserType, headless);

        // Сохранение драйвера в DriverManager
        DriverManager.setDriver(driver);
    }

    @After
    public void tearDown() {
        // Закрытие драйвера через DriverManager
        DriverManager.quitDriver();
    }
}