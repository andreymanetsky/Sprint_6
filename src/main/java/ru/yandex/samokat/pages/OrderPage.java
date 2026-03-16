package ru.yandex.samokat.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;

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
    }

    public void fillFirstForm(String name, String surname, String address, String metro, String phone) {

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(nameField));


        driver.findElement(nameField).sendKeys(name);


        driver.findElement(surnameField).sendKeys(surname);


        driver.findElement(addressField).sendKeys(address);


        driver.findElement(metroField).click();
        driver.findElement(metroField).sendKeys(metro);


        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(firstMetroOption))
                .click();


        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void fillSecondForm(String date, String comment, boolean isBlack, boolean isGrey) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(dateField));

        driver.findElement(dateField).sendKeys(date);
        driver.findElement(dateField).sendKeys(Keys.ENTER);

        driver.findElement(rentalField).click();
        driver.findElement(rentalOption).click();

        if (isBlack) driver.findElement(blackColor).click();
        if (isGrey) driver.findElement(greyColor).click();

        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void confirmOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(confirmButton));
        driver.findElement(confirmButton).click();
    }

    public boolean isOrderSuccess() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true;
        } catch (TimeoutException e) {
            System.out.println("✗ БАГ! Заказ не оформился в Chrome");
            return false;
        }
    }

    public void clickNextWithoutFilling() {
        driver.findElement(nextButton).click();
    }

    // Методы проверки ошибок
    public boolean isNameErrorDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(nameError));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSurnameErrorDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(surnameError));
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public boolean isMetroErrorDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(metroError));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPhoneErrorDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(phoneError));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void debugAllErrors() {
        try {
            System.out.println("ПОДРОБНАЯ ОТЛАДКА ВСЕХ ОШИБОК");
            Thread.sleep(2000);

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