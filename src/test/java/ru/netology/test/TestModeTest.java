package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestModeTest {


    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
        faker = new Faker(new Locale("eng"));
        user = new User();
    }

    @Test
    void shouldLoginActiveUser() {
        User dataOrderCard= DataGenerator.Registration.generateActiveUser();
        DataGenerator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue(dataOrderCard.getLogin());
        $("[name=password]").setValue(dataOrderCard.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginBlockedUser() {
        User dataOrderCard= DataGenerator.Registration.generateBlockedUser();
        DataGenerator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue(dataOrderCard.getLogin());
        $("[name=password]").setValue(dataOrderCard.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginFunctionInvalidLogin() {
        User dataOrderCard= DataGenerator.Registration.generateActiveUser();
        DataGenerator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue("vasiliy");
        $("[name=password]").setValue(dataOrderCard.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginFunctionInvalidPassword() {
        User dataOrderCard= DataGenerator.Registration.generateActiveUser();
        DataGenerator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue(dataOrderCard.getLogin());
        $("[name=password]").setValue("qwerty");
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}
