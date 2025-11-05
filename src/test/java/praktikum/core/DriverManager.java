package praktikum.core;

import org.openqa.selenium.WebDriver;

/**
 * Потокобезопасный менеджер WebDriver
 * Позволяет управлять драйвером в многопоточной среде
 */
public class DriverManager {

    // ThreadLocal для изоляции драйвера в каждом потоке
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        // Приватный конструктор для предотвращения создания экземпляров
    }

    /**
     * Установка драйвера для текущего потока
     * @param driver экземпляр WebDriver
     */
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    /**
     * Получение драйвера для текущего потока
     * @return экземпляр WebDriver
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Закрытие и освобождение драйвера для текущего потока
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}