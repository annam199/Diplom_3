package praktikum.core;

import io.qameta.allure.Step;
import praktikum.models.LoginCredentials;
import praktikum.models.User;
import praktikum.utils.ApiHelper;
import praktikum.utils.TestUtils;

/**
 * Утилита для работы с тестовыми пользователями
 */
public class UserTestHelper {

    @Step("Сгенерировать пользователя")
    public static User generateUser() {
        String name = TestUtils.generateName();
        String email = TestUtils.generateEmail();
        String password = TestUtils.generatePassword();
        return new User(email, password, name);
    }

    @Step("Зарегистрировать и авторизовать пользователя")
    public static String registerAndLogin(User user) {
        // Регистрация пользователя
        ApiHelper.registerUser(user).assertThat().statusCode(200);

        // Логин и получение токена
        var loginResponse = ApiHelper.loginUser(
            new LoginCredentials(user.getEmail(), user.getPassword())
        );
        loginResponse.assertThat().statusCode(200);

        return ApiHelper.extractAccessToken(loginResponse);
    }

    @Step("Удалить пользователя")
    public static void deleteUser(String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            try {
                var deleteResponse = ApiHelper.deleteUser("Bearer " + accessToken);
                System.out.println("Удаление пользователя, статус: " + deleteResponse.getStatusCode());
            } catch (Exception e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }
    }
}