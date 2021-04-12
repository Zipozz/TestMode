package ru.netology.test;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;


class DataGenerator {

    private static Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    public static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void postUser(UserInfo registration) {
        RestAssured.given()
                .spec(requestSpecification)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }


    public static UserInfo getNewUser(String status) {

        String login = faker.name().fullName();
        String password = faker.internet().password();
        UserInfo registration = new UserInfo(login, password, status);
        postUser(registration);
        return registration;
    }


    public static String getNewPassword() {

        return faker.internet().password();
    }

    public static String getNewLogin() {

        return faker.name().fullName();
    }



}