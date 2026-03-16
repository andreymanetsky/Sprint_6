package ru.yandex.samokat.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class OrderStatusPage {
    private final WebDriver driver;

    // Сообщение "Заказ не найден"
    private final By notFoundMessage = By.xpath(".//div[text()='Заказ не найден']");

    // Картинка с ошибкой
    private final By errorImage = By.xpath(".//img[@alt='Not found']");

    public OrderStatusPage(WebDriver driver) {
        this.driver = driver;
    }

    // Проверяем, что заказ не найден
    public boolean isOrderNotFound() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(notFoundMessage));
        return driver.findElement(notFoundMessage).isDisplayed();
    }

    // Получаем текст ошибки
    public String getNotFoundMessage() {
        return driver.findElement(notFoundMessage).getText();
    }

    // Проверяем, что картинка ошибки есть
    public boolean isErrorImageDisplayed() {
        return driver.findElement(errorImage).isDisplayed();
    }
}