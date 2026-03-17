package ru.yandex.samokat.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class YandexPage {
    private final WebDriver driver;

    // Поле поиска на Яндексе
    private final By searchField = By.xpath(".//input[@name='text']");

    public YandexPage(WebDriver driver) {
        this.driver = driver;
    }

    // Проверяем, что открылась главная Яндекса
    public boolean isMainYandexPageOpened() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(searchField));
        return driver.findElement(searchField).isDisplayed();
    }

    // Получаем текущий URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}