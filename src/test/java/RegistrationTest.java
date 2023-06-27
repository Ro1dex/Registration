import com.codeborne.selenide.SelenideElement;
import data.GenerateUser;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;


public class RegistrationTest {
    static class AuthTest {
        static GenerateUser.UserInfo user = GenerateUser.registration();
        static GenerateUser.UserInfo userBlocked = GenerateUser.registrationBlocked();

        private static final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        @BeforeAll
        static void setUpAll() {

            // сам запрос
            given() // "дано"
                    .spec(requestSpec)
                    .body(user)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
            given()
                    .spec(requestSpec)
                    .body(userBlocked)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);

        }

        @Test
        @DisplayName("Should successfully login with active registered user")
        void shouldSuccessfulLoginIfRegisteredActiveUser() {
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
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[name=login]").setValue(user.getLogin() + "Fu");
            form.$("[name=password]").setValue(user.getPassword() + "Fu");
            $("button").click();
            $(withText("Неверно указан логин или пароль")).shouldBe(visible);


        }

        @Test
        @DisplayName("Should get error message if login with blocked registered user")
        void shouldGetErrorIfBlockedUser() {
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
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[name=login]").setValue(user.getLogin() + "Fu");
            form.$("[name=password]").setValue(user.getPassword());
            $("button").click();
            $(withText("Неверно указан логин или пароль")).shouldBe(visible);

        }

        @Test
        @DisplayName("Should get error message if login with wrong password")
        void shouldGetErrorIfWrongPassword() {
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[name=login]").setValue(user.getLogin());
            form.$("[name=password]").setValue(user.getPassword() + "Fu");
            $("button").click();
            $(withText("Неверно указан логин или пароль")).shouldBe(visible);

        }
    }
}
