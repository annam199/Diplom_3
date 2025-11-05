package praktikum.config;

import praktikum.core.BrowserType;

/**
 * Конфигурация браузера для запуска автотестов
 */
public class BrowserConfig {

    private final TestConfig testConfig;

    public BrowserConfig() {
        this.testConfig = TestConfig.getInstance();
    }

    /**
     * Получение типа браузера
     * @return тип браузера
     */
    public BrowserType getBrowserType() {
        String browserName = testConfig.getBrowserType();
        return BrowserType.fromString(browserName);
    }

    /**
     * Проверка необходимости запуска в headless режиме
     * @return true, если нужен headless режим
     */
    public boolean isHeadless() {
        return testConfig.isHeadless();
    }

    /**
     * Получение времени неявного ожидания
     * @return время неявного ожидания в секундах
     */
    public int getImplicitWait() {
        return testConfig.getImplicitWait();
    }

    /**
     * Получение тайм-аута загрузки страницы
     * @return тайм-аут загрузки страницы в секундах
     */
    public int getPageLoadTimeout() {
        return testConfig.getPageLoadTimeout();
    }
}