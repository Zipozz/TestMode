package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestModeTest{


    @BeforeEach
    void Setup() {
        open("http://localhost:9999"); }


    @Test
    void shouldActiveUser() {
        UserInfo userInfo = DataGenerator.getNewUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(userInfo.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(userInfo.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldBlockedUser() {
        UserInfo userInfo = DataGenerator.getNewUser("blocked");
        $("[data-test-id=login] [class = input__control]").setValue(userInfo.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(userInfo.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Пользователь заблокирован")).shouldBe(visible,Duration.ofSeconds(5));

    }

    @Test
    void shouldIncorrectPassword() {
        UserInfo userInfo = DataGenerator.getNewUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(userInfo.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(DataGenerator.getNewPassword());
        $(byText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(visible,Duration.ofSeconds(5));

    }

    @Test
    void shouldIncorrectLogin() {
        UserInfo userInfo = DataGenerator.getNewUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(DataGenerator.getNewLogin());
        $("[data-test-id=password] [class = input__control]").setValue(userInfo.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(5));

    }


}
