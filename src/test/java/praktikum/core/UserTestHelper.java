package praktikum.core;

import praktikum.models.LoginCredentials;
import praktikum.models.User;
import praktikum.utils.ApiHelper;
import praktikum.utils.TestUtils;

/**
 * Утилита для работы с тестовыми пользователями
 */
public class UserTestHelper {

    /**
     * Генерация нового тестового пользователя
     * @return Созданный пользователь
     */
    public static User generateUser() {
        String name = TestUtils.generateName();
        String email = TestUtils.generateEmail();
        String password = TestUtils.generatePassword();
        return new User(email, password, name);
    }

    /**
     * Регистрация пользователя
     * @param user Пользователь для регистрации
     * @return Токен доступа
     */
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

    /**
     * Удаление пользователя по токену
     * @param accessToken Токен доступа пользователя
     */
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