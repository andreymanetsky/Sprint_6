package ru.yandex.samokat.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.ArrayList;

public class MainPage {
    private final WebDriver driver;

    // Логотип Самоката (слева вверху)
    private final By samokatLogo = By.className("Header_LogoScooter__3lsAR");

    // Логотип Яндекса (справа вверху)
    private final By yandexLogo = By.className("Header_LogoYandex__3TSOI");

    // Кнопка "Заказать" в шапке страницы (верхняя)
    private final By topOrderButton = By.className("Button_Button__ra12g");

    // Кнопка "Заказать" внизу страницы (нужно скроллить)
    private final By bottomOrderButton = By.xpath(".//button[text()='Заказать' and contains(@class, 'Button_Middle__1CSJM')]");

    // Кнопка "Да все привыкли" для принятия куки
    private final By cookieButton = By.id("rcc-confirm-button");

    // Поле для ввода номера заказа
    private final By orderInput = By.xpath(".//input[@placeholder='Введите номер заказа']");

    // Кнопка Go! для поиска заказа
    private final By goButton = By.xpath(".//button[text()='Go!']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Принимаем куки
    public void acceptCookies() {
        driver.findElement(cookieButton).click();
    }

    // Кликаем на лого Самоката
    public void clickSamokatLogo() {
        driver.findElement(samokatLogo).click();
    }

    // Кликаем на лого Яндекса
    public void clickYandexLogo() {
        driver.findElement(yandexLogo).click();
    }

    // Кликаем верхнюю кнопку "Заказать"
    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    // Кликаем нижнюю кнопку "Заказать" (со скроллом)
    public void clickBottomOrderButton() {
        WebElement element = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    // Кликаем на вопрос по его номеру (0-7)
    public void clickQuestion(int index) {
        WebElement question = driver.findElement(By.id("accordion__heading-" + index));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);
        question.click();
    }

    // Получаем текст ответа на вопрос
    public String getAnswerText(int index) {
        By answerLocator = By.xpath(".//div[@id='accordion__panel-" + index + "']/p");
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return driver.findElement(answerLocator).getText();
    }

    // Вводим номер заказа
    public void enterOrderNumber(String number) {
        try {
            // Сначала кликаем на поле, чтобы оно стало активным
            WebElement input = driver.findElement(orderInput);
            input.click();
            Thread.sleep(500);
            input.clear();
            input.sendKeys(number);
            System.out.println("Ввели номер: " + number);
        } catch (Exception e) {
            System.out.println("Ошибка при вводе номера: " + e.getMessage());
        }
    }

    // Жмём Go!
    public void clickGoButton() {
        try {
            WebElement button = driver.findElement(goButton);
            button.click();
            System.out.println("Нажали кнопку Go!");
        } catch (Exception e) {
            System.out.println("Ошибка при нажатии Go: " + e.getMessage());
        }
    }

    // Получаем текущий URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Переключаемся на новое окно (для Яндекса)
    public void switchToNewWindow() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    // Закрываем текущее окно
    public void closeCurrentWindow() {
        driver.close();
    }

    // Возвращаемся в главное окно
    public void switchToMainWindow() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    // Открыть форму поиска заказа (нажать на "Статус заказа")
    public void openOrderSearch() {
        try {
            driver.findElement(By.xpath("//button[text()='Статус заказа']")).click();
            Thread.sleep(1000);
            System.out.println("Открыли форму поиска заказа");
        } catch (Exception e) {
            System.out.println("Ошибка при открытии формы поиска: " + e.getMessage());
        }
    }


}