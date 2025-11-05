package praktikum.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

/**
 * Фабрика для создания экземпляров WebDriver
 * Поддерживает различные типы браузеров с настройкой опций
 */
public class DriverFactory {

    /**
     * Создание экземпляра WebDriver для указанного типа браузера
     *
     * @param browserType тип браузера
     * @param headless режим без графического интерфейса
     * @return экземпляр WebDriver
     */
    public static WebDriver createDriver(BrowserType browserType, boolean headless) {
        WebDriver driver;

        switch (browserType) {
            case CHROME:
                driver = createChromeDriver(headless);
                break;
            case YANDEX:
                driver = createYandexDriver(headless);
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемый тип браузера: " + browserType);
        }

        configureDriver(driver);
        return driver;
    }

    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        if (headless) {
            options.addArguments("--headless");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createYandexDriver(boolean headless) {
        // Подготовка к работе с Яндекс.Браузером
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        // Специфические опции для Яндекс.Браузера
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");

        // Попытка указать путь к Яндекс.Браузеру (закомментировано, так как путь может отличаться)
        // options.setBinary("/usr/bin/yandex-browser");

        if (headless) {
            options.addArguments("--headless");
        }

        return new ChromeDriver(options);
    }

    private static void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
    }
}