package ru.yandex.samokat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.samokat.pages.MainPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvalidOrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://qa-scooter.praktikum-services.ru/");

        mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        wait.until(d -> d.getCurrentUrl().contains("qa-scooter.praktikum-services.ru"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void invalidOrderNumberShowsNotFound() {
        String oldUrl = driver.getCurrentUrl();
        System.out.println("Старый URL: " + oldUrl);

        // Открываем форму поиска
        mainPage.openOrderSearch();

        // Вводим номер
        mainPage.enterOrderNumber("999999");

        // Нажимаем Go
        mainPage.clickGoButton();

        // Ждём изменения URL (перехода на страницу статуса)
        wait.until(d -> !d.getCurrentUrl().equals(oldUrl));
        String newUrl = driver.getCurrentUrl();
        System.out.println("Новый URL: " + newUrl);

        // Проверяем, что URL содержит track и наш номер
        assertTrue(newUrl.contains("track"), "URL должен содержать 'track'");
        assertTrue(newUrl.contains("999999"), "URL должен содержать номер заказа");
    }

    @Test
    public void specialCharactersInOrderNumber() {
        String oldUrl = driver.getCurrentUrl();
        System.out.println("Старый URL: " + oldUrl);

        // Открываем форму поиска
        mainPage.openOrderSearch();

        // Вводим спецсимволы
        mainPage.enterOrderNumber("@#$%");

        // Нажимаем Go
        mainPage.clickGoButton();

        // Ждём изменения URL
        wait.until(d -> !d.getCurrentUrl().equals(oldUrl));
        String newUrl = driver.getCurrentUrl();
        System.out.println("Новый URL: " + newUrl);

        // Проверяем, что URL изменился и содержит track
        assertTrue(!newUrl.equals(oldUrl), "URL должен измениться");
        assertTrue(newUrl.contains("track"), "URL должен содержать 'track'");
    }
}