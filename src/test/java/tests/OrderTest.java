package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pageObjects.MainPage;
import pageObjects.OrderPage;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "Иван, Иванов, Москва, Тверская 1, 89001234567",
            "Петр, Петров, Казань, Кремль 2, 89998887766"
    })
    public void testOrderFromTopButton(String name, String surname, String city, String address, String phone) {

        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
        mainPage.clickTopOrderButton();

        OrderPage orderPage = new OrderPage(driver);

        String fullAddress = city + " " + address;

        orderPage.fillFirstForm(name, surname, fullAddress, phone);
        orderPage.clickNext();

        orderPage.fillSecondForm("25.06.2026");

        orderPage.clickOrder();
        orderPage.confirmOrder();

        assertTrue(orderPage.isOrderSuccessVisible());
    }


    @ParameterizedTest
    @CsvSource({
            "Анна, Смирнова, Санкт-Петербург, Невский 10, 89112223344",
            "Олег, Кузнецов, Новосибирск, Ленина 5, 89990001122"
    })
    public void testOrderFromBottomButton(String name, String surname, String city, String address, String phone) {

        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
        mainPage.scrollToFAQ();
        mainPage.clickBottomOrderButton();

        OrderPage orderPage = new OrderPage(driver);

        String fullAddress = city + " " + address;

        orderPage.fillFirstForm(name, surname, fullAddress, phone);
        orderPage.clickNext();

        orderPage.fillSecondForm("26.06.2026");

        orderPage.clickOrder();
        orderPage.confirmOrder();

        assertTrue(orderPage.isOrderSuccessVisible());
    }
}