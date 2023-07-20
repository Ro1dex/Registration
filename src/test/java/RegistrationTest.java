import com.codeborne.selenide.SelenideElement;
import data.GenerateUser;
import data.RegistrationNewUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.RegistrationNewUser.user;
import static data.RegistrationNewUser.userBlocked;


public class RegistrationTest {
    static class AuthTest {

        @Test
        @DisplayName("Should successfully login with active registered user")
        void shouldSuccessfulLoginIfRegisteredActiveUser() {
            RegistrationNewUser.registrationAPI();
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[name=login]").setValue(user.getLogin());
            form.$("[name=password]").setValue(user.getPassword());
            $("button").click();
            $(withText("Личный кабинет")).shouldBe(visible);

        }

        @Test
        @DisplayName("Should get error message if login with not registered user")
        void shouldGetErrorIfNotRegisteredUser() {
            RegistrationNewUser.registrationAPI();
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[name=login]").setValue(GenerateUser.generateLogin());
            form.$("[name=password]").setValue(GenerateUser.generatePassword());
            $("button").click();
            $(withText("Неверно указан логин или пароль")).shouldBe(visible);


        }

        @Test
        @DisplayName("Should get error message if login with blocked registered user")
        void shouldGetErrorIfBlockedUser() {
            RegistrationNewUser.registrationAPI();
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[name=login]").setValue(userBlocked.getLogin());
            form.$("[name=password]").setValue(userBlocked.getPassword());
            $("button").click();
            $(withText("Пользователь заблокирован")).shouldBe(visible);

        }

        @Test
        @DisplayName("Should get error message if login with wrong login")
        void shouldGetErrorIfWrongLogin() {
            RegistrationNewUser.registrationAPI();
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[name=login]").setValue(GenerateUser.generateLogin());
            form.$("[name=password]").setValue(user.getPassword());
            $("button").click();
            $(withText("Неверно указан логин или пароль")).shouldBe(visible);

        }

        @Test
        @DisplayName("Should get error message if login with wrong password")
        void shouldGetErrorIfWrongPassword() {
            RegistrationNewUser.registrationAPI();
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[name=login]").setValue(user.getLogin());
            form.$("[name=password]").setValue(GenerateUser.generatePassword());
            $("button").click();
            $(withText("Неверно указан логин или пароль")).shouldBe(visible);

        }
    }
}
