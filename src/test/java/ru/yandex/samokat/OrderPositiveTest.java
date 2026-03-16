package ru.yandex.samokat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.samokat.pages.MainPage;
import ru.yandex.samokat.pages.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderPositiveTest {
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
    }

    @AfterEach
    public void tearDown() {

        driver.quit();
    }


    private static Stream<Arguments> getTestData() {
        return Stream.of(

                Arguments.of(
                        "top",
                        "Иван",
                        "Петров",
                        "Москва, Ленина 1",
                        "Черкизовская",
                        "89991234567",
                        "25.12.2024",
                        "Позвоните за час",
                        true,
                        false
                ),


                Arguments.of(
                        "bottom",
                        "Мария",
                        "Сидорова",
                        "СПб, Невский 10",
                        "Сокольники",
                        "89161234567",
                        "26.12.2024",
                        "Оставить у двери",
                        false,
                        true
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void orderScooterTest(
            String orderButtonType,
            String name,
            String surname,
            String address,
            String metro,
            String phone,
            String date,
            String comment,
            boolean isBlack,
            boolean isGrey) {

        System.out.println("\n ТЕСТ ЗАКАЗА ДЛЯ " + name + " " + surname + " ===");


        if (orderButtonType.equals("top")) {
            mainPage.clickTopOrderButton();
            System.out.println(" Нажали верхнюю кнопку");
        } else {
            mainPage.clickBottomOrderButton();
            System.out.println(" Нажали нижнюю кнопку");
        }


        orderPage.fillFirstForm(name, surname, address, metro, phone);
        System.out.println(" Заполнили первую форму");
        orderPage.clickNextButton();
        System.out.println(" Нажали 'Далее'");


        orderPage.fillSecondForm(date, comment, isBlack, isGrey);
        System.out.println(" Заполнили вторую форму");
        orderPage.clickOrderButton();
        System.out.println(" Нажали 'Заказать'");


        orderPage.confirmOrder();
        System.out.println(" Подтвердили заказ");


        System.out.println("\n ПРОВЕРКА РЕЗУЛЬТАТА:");
        boolean success = orderPage.isOrderSuccess();


        if (!success) {
            System.out.println(" БАГ: Заказ не оформляется в Chrome");
            System.out.println("   Ожидалось: окно 'Заказ оформлен'");
            System.out.println("   Фактически: ничего не произошло");
        }


        assertTrue(success, "БАГ: Заказ не оформляется в Chrome");
    }
}