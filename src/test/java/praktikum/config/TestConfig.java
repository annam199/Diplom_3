package praktikum.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Менеджер конфигурации для тестового окружения
 * Загрузка настроек для Chrome
 */
public class TestConfig {

    private static final String DEFAULT_CONFIG = "application.properties";
    private static volatile TestConfig instance;

    private final Properties properties;

    private TestConfig() {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Получение singleton-экземпляра конфигурации
     * @return экземпляр TestConfig
     */
    public static TestConfig getInstance() {
        if (instance == null) {
            synchronized (TestConfig.class) {
                if (instance == null) {
                    instance = new TestConfig();
                }
            }
        }
        return instance;
    }

    /**
     * Загрузка конфигурации
     */
    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIG)) {
            if (input != null) {
                properties.load(input);
                System.out.println("Загружена конфигурация по умолчанию");
            } else {
                System.err.println("Не найден файл конфигурации по умолчанию");
                // Установка значений по умолчанию, если файл не найден
                setDefaultValues();
            }
        } catch (IOException ex) {
            System.err.println("Ошибка загрузки конфигурации: " + ex.getMessage());
            setDefaultValues();
        }
    }

    /**
     * Установка значений по умолчанию
     */
    private void setDefaultValues() {
        properties.setProperty("base.url", "https://stellarburgers.education-services.ru/");
        properties.setProperty("browser.type", "chrome");
        properties.setProperty("headless", "false");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "10");
        properties.setProperty("page.load.timeout", "30");
    }

    /**
     * Получение базового URL приложения
     * @return базовый URL
     */
    public String getBaseUrl() {
        return properties.getProperty("base.url", "https://stellarburgers.education-services.ru");
    }

    /**
     * Получение типа браузера
     * @return тип браузера
     */
    public String getBrowserType() {
        return "chrome";
    }

    /**
     * Проверка включения режима без графического интерфейса
     * @return true, если включен headless режим
     */
    public boolean isHeadless() {
        return Boolean.parseBoolean(
            System.getProperty("headless",
                properties.getProperty("headless", "false"))
        );
    }

    /**
     * Получение времени неявного ожидания
     * @return время неявного ожидания в секундах
     */
    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    /**
     * Получение времени явного ожидания
     * @return время явного ожидания в секундах
     */
    public int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "10"));
    }

    /**
     * Получение тайм-аута загрузки страницы
     * @return тайм-аут загрузки страницы в секундах
     */
    public int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "30"));
    }
}