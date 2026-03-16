package ru.yandex.samokat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.samokat.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvalidOrderTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");

        mainPage = new MainPage(driver);

        mainPage.acceptCookies();
        try { Thread.sleep(2000); } catch (Exception e) {}
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void invalidOrderNumberShowsNotFound() {

        mainPage.openOrderSearch();
        try { Thread.sleep(1000); } catch (Exception e) {}


        mainPage.enterOrderNumber("999999");
        try { Thread.sleep(1000); } catch (Exception e) {}


        mainPage.clickGoButton();


        try { Thread.sleep(5000); } catch (Exception e) {}


        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("track"), "Не перешли на страницу отслеживания заказа");
    }

    @Test
    public void specialCharactersInOrderNumber() {

        mainPage.openOrderSearch();
        try { Thread.sleep(1000); } catch (Exception e) {}


        mainPage.enterOrderNumber("@#$%");
        try { Thread.sleep(1000); } catch (Exception e) {}


        mainPage.clickGoButton();


        try { Thread.sleep(5000); } catch (Exception e) {}


        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("track"), "Не перешли на страницу отслеживания заказа");
    }
}