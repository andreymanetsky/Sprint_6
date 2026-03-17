package ru.yandex.samokat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.samokat.pages.MainPage;

import static org.junit.jupiter.api.Assertions.*;

public class NavigationTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
        mainPage.acceptCookies();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void samokatLogoLeadsToMainPage() {
        mainPage.clickSamokatLogo();
        assertEquals("https://qa-scooter.praktikum-services.ru/", driver.getCurrentUrl());
    }
}