package praktikum.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public class TestUtils {
    private static final Properties properties = new Properties();
    private static final Faker faker = new Faker(Locale.ENGLISH);

    static {
        try (InputStream input = TestUtils.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Нет файла с конфигурацией");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url", "https://stellarburgers.education-services.ru");
    }

    public static String getBrowserType() {
        return System.getProperty("browser.type", properties.getProperty("browser.type", "chrome"));
    }

    public static WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        return new ChromeDriver(options);
    }

    public static String generateName() {
        return faker.name().firstName();
    }
    public static String generateEmail() {
        return faker.internet().emailAddress();
    }
    public static String generatePassword() {
        return faker.internet().password(8, 15, true, true);
    }
}