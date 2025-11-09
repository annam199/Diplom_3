package praktikum.core;

/**
 * Перечисление поддерживаемых типов браузеров для автотестов
 */
public enum BrowserType {
    CHROME("chrome"),
    YANDEX("yandex");

    private final String browserName;

    BrowserType(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserName() {
        return browserName;
    }

    /**
     * Получение типа браузера по строковому представлению
     *
     * @param browser строковое название браузера
     * @return тип браузера
     * @throws IllegalArgumentException если браузер не поддерживается
     */
    public static BrowserType fromString(String browser) {
        for (BrowserType type : BrowserType.values()) {
            if (type.browserName.equalsIgnoreCase(browser)) {
                return type;
            }
        }
        // Возвращаем Chrome по умолчанию, если указан неизвестный браузер
        System.err.println("Неподдерживаемый тип браузера: " + browser + ". Будет использован Chrome.");
        return CHROME;
    }
}