package ru.yandex.samokat.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // ПЕРВАЯ ФОРМА
    private final By nameField = By.xpath(".//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    private final By firstMetroOption = By.xpath(".//div[contains(@class, 'select-search__select')]//button[1]");

    // ВТОРАЯ ФОРМА
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalField = By.xpath(".//div[text()='* Срок аренды']");
    private final By rentalOption = By.xpath(".//div[text()='сутки']");
    private final By blackColor = By.id("black");
    private final By greyColor = By.id("grey");
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath(".//button[text()='Заказать' and contains(@class, 'Button_Middle__1CSJM')]");

    // ПОДТВЕРЖДЕНИЕ
    private final By confirmButton = By.xpath(".//button[text()='Да']");
    private final By successMessage = By.xpath(".//div[text()='Заказ оформлен']");

    // СООБЩЕНИЯ ОБ ОШИБКАХ
    private final By nameError = By.xpath("//div[text()='Введите корректное имя']");
    private final By surnameError = By.xpath("//div[text()='Введите корректную фамилию']");
    private final By addressError = By.xpath("//div[text()='Введите корректный адрес']");
    private final By metroError = By.xpath("//div[text()='Выберите станцию']");
    private final By phoneError = By.xpath("//div[text()='Введите корректный номер']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillFirstForm(String name, String surname, String address, String metro, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));

        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);

        driver.findElement(metroField).click();
        driver.findElement(metroField).sendKeys(metro);

        wait.until(ExpectedConditions.elementToBeClickable(firstMetroOption)).click();

        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    public void fillSecondForm(String date, String comment, boolean isBlack, boolean isGrey) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));

        driver.findElement(dateField).sendKeys(date);
        driver.findElement(dateField).sendKeys(Keys.ENTER);

        driver.findElement(rentalField).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(rentalOption)).click();

        if (isBlack) driver.findElement(blackColor).click();
        if (isGrey) driver.findElement(greyColor).click();

        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }

    public boolean isOrderSuccess() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true;
        } catch (TimeoutException e) {
            System.out.println("✗ БАГ! Заказ не оформился в Chrome");
            return false;
        }
    }

    public void clickNextWithoutFilling() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    // Методы проверки ошибок
    public boolean isNameErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(nameError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSurnameErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(surnameError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddressErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(addressError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMetroErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(metroError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPhoneErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(phoneError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void debugAllErrors() {
        try {
            System.out.println("ПОДРОБНАЯ ОТЛАДКА ВСЕХ ОШИБОК");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'Input_ErrorMessage')]")));

            var errorMessages = driver.findElements(By.xpath("//div[contains(@class, 'Input_ErrorMessage')]"));
            System.out.println("1. Найдено элементов с классом Input_ErrorMessage: " + errorMessages.size());

            for (int i = 0; i < errorMessages.size(); i++) {
                var element = errorMessages.get(i);
                System.out.println("   Ошибка #" + (i+1) + ":");
                System.out.println("      Текст: '" + element.getText() + "'");
                System.out.println("      Видим: " + element.isDisplayed());
                System.out.println("      HTML: " + element.getAttribute("outerHTML"));
            }

        } catch (Exception e) {
            System.out.println("Ошибка отладки: " + e.getMessage());
        }
    }
}