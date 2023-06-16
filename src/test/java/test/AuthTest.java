package test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.DataGenerator.Registration.getRegisteredUser;
import static data.DataGenerator.Registration.getUser;
import static data.DataGenerator.getRandomLogin;
import static data.DataGenerator.getRandomPassword;

public class AuthTest {

    @BeforeEach
        //Выполняется перед каждым тестом.
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }


    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
        $("[data-test-id=\"login\"] input").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(registeredUser.getPassword());
        $("[data-test-id=\"action-login\"]").click();

        $("[id=\"root\"]").shouldHave(Condition.text("Личный кабинет"), Duration.ofSeconds(15));

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        $("[data-test-id=\"login\"] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=\"action-login\"]").click();

        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        $("[data-test-id=\"login\"] input").setValue(blockedUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(blockedUser.getPassword());
        $("[data-test-id=\"action-login\"]").click();

        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        $("[data-test-id=\"login\"] input").setValue(wrongLogin);
        $("[data-test-id=\"password\"] input").setValue(registeredUser.getPassword());
        $("[data-test-id=\"action-login\"]").click();

        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
        $("[data-test-id=\"login\"] input").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(wrongPassword);
        $("[data-test-id=\"action-login\"]").click();

        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}