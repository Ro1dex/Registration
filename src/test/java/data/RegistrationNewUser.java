package data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RegistrationNewUser  {

    //этот метод будет регистрировать одновременно 1 активного и 1 заблокированного пользователя
    public static GenerateUser.UserInfo user = GenerateUser.registration("active");
    public static GenerateUser.UserInfo userBlocked = GenerateUser.registration("blocked");
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    public static void registrationAPI() {

        // сам запрос
        given()
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

}
