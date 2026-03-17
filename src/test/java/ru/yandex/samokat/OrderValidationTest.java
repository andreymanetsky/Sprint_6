package ru.yandex.samokat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.samokat.pages.MainPage;
import ru.yandex.samokat.pages.OrderPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderValidationTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
        mainPage.acceptCookies();
        mainPage.clickTopOrderButton();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void allRequiredFieldsShowErrorsWhenEmpty() {

        orderPage.clickNextWithoutFilling();

        assertTrue(orderPage.isNameErrorDisplayed(), "Должна быть ошибка под полем Имя");
        assertTrue(orderPage.isSurnameErrorDisplayed(), "Должна быть ошибка под полем Фамилия");
        assertTrue(orderPage.isMetroErrorDisplayed(), "Должна быть ошибка под полем Метро");
        assertTrue(orderPage.isPhoneErrorDisplayed(), "Должна быть ошибка под полем Телефон");
    }
}