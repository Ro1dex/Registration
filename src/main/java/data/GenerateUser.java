package data;

import com.github.javafaker.Faker;
import lombok.Value;

public class GenerateUser {

    private GenerateUser() {

    }

    public static String generateLogin() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }
    public static String generatePassword(){
        Faker faker = new Faker();
        return faker.name().lastName() + (faker.number().digits(3));
    }
    public static GenerateUser.UserInfo registration(){
        String status = "active";
        String login = generateLogin();
        String password = generatePassword();
        return new UserInfo(login, password, status);
    }
    public static GenerateUser.UserInfo registrationBlocked(){
        String status = "blocked";
        String login = generateLogin();
        String password = generatePassword();
        return new UserInfo(login, password, status);
    }



    @Value
    public static class UserInfo {
        String login;
        String password;
        String status;
    }

}
