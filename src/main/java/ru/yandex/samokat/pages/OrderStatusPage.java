package ru.yandex.samokat.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class OrderStatusPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Сообщение "Заказ не найден"
    private final By notFoundMessage = By.xpath(".//div[contains(text(), 'Заказ не найден')]");

    // Картинка с ошибкой
    private final By errorImage = By.xpath(".//img[@alt='Not found']");

    // Индикатор загрузки страницы (любой элемент, который появляется только на странице статуса)
    private final By pageIndicator = By.xpath(".//div[contains(@class, 'Track_OrderStatus') or contains(text(), 'Статус заказа')]");

    public OrderStatusPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Ждём загрузки страницы статуса заказа
    public void waitForPageLoad() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(notFoundMessage),
                    ExpectedConditions.visibilityOfElementLocated(errorImage),
                    ExpectedConditions.visibilityOfElementLocated(pageIndicator)
            ));
            System.out.println("Страница статуса заказа загружена");
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке страницы статуса: " + e.getMessage());
        }
    }

    // Проверяем, что заказ не найден
    public boolean isOrderNotFound() {
        try {
            waitForPageLoad(); // Сначала ждём загрузки страницы
            return wait.until(ExpectedConditions.visibilityOfElementLocated(notFoundMessage)).isDisplayed();
        } catch (Exception e) {
            System.out.println("Сообщение 'Заказ не найден' не появилось: " + e.getMessage());
            return false;
        }
    }

    // Получаем текст ошибки
    public String getNotFoundMessage() {
        waitForPageLoad();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(notFoundMessage)).getText();
    }

    // Проверяем, что картинка ошибки есть
    public boolean isErrorImageDisplayed() {
        try {
            waitForPageLoad();
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorImage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}