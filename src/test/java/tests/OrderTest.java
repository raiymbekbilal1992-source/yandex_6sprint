package tests;

import io.qameta.allure.Step;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pageObjects.MainPage;
import pageObjects.OrderPage;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "Иван, Иванов, Комсомольская площадь 5, Черкизовская, 89001234567, 25.06.2026, black, Позвонить за час",
            "Петр, Петров, Арбат 10, Парк культуры, 89998887766, 26.06.2026, grey, Оставить у двери"
    })
    public void testOrderFromTopButton(
            String name, String surname, String address,
            String metro, String phone,
            String date, String color, String comment) {

        MainPage mainPage = openMainPage();
        clickTopOrder(mainPage);

        OrderPage orderPage = new OrderPage(driver);
        fillFirst(orderPage, name.trim(), surname.trim(), address.trim(), metro.trim(), phone.trim());
        goNext(orderPage);
        fillSecond(orderPage, date.trim(), color.trim(), comment.trim());
        submitOrder(orderPage);
        checkSuccess(orderPage);
    }

    @ParameterizedTest
    @CsvSource({
            "Анна, Смирнова, Тверская 15, Тверская, 89112223344, 27.06.2026, black, Без комментариев",
            "Олег, Кузнецов, Арбат 20, Арбатская, 89990001122, 28.06.2026, grey, Позвонить заранее"
    })
    public void testOrderFromBottomButton(
            String name, String surname, String address,
            String metro, String phone,
            String date, String color, String comment) {

        MainPage mainPage = openMainPage();
        clickBottomOrder(mainPage);

        OrderPage orderPage = new OrderPage(driver);
        fillFirst(orderPage, name.trim(), surname.trim(), address.trim(), metro.trim(), phone.trim());
        goNext(orderPage);
        fillSecond(orderPage, date.trim(), color.trim(), comment.trim());
        submitOrder(orderPage);
        checkSuccess(orderPage);
    }

    @Step("Открыть главную страницу и принять cookies")
    private MainPage openMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
        return mainPage;
    }

    @Step("Нажать верхнюю кнопку «Заказать»")
    private void clickTopOrder(MainPage mainPage) {
        mainPage.clickTopOrderButton();
    }

    @Step("Нажать нижнюю кнопку «Заказать»")
    private void clickBottomOrder(MainPage mainPage) {
        mainPage.scrollToBottomOrderButton();
        mainPage.clickBottomOrderButton();
    }

    @Step("Заполнить первую форму: {name} {surname}, адрес: {address}, метро: {metro}")
    private void fillFirst(OrderPage orderPage, String name, String surname,
                           String address, String metro, String phone) {
        orderPage.fillFirstForm(name, surname, address, metro, phone);
    }

    @Step("Нажать «Далее»")
    private void goNext(OrderPage orderPage) {
        orderPage.clickNext();
    }

    @Step("Заполнить вторую форму: дата {date}, цвет {color}")
    private void fillSecond(OrderPage orderPage, String date, String color, String comment) {
        orderPage.fillSecondForm(date, color, comment);
    }

    @Step("Нажать «Заказать» и подтвердить")
    private void submitOrder(OrderPage orderPage) {
        orderPage.clickOrder();
        orderPage.confirmOrder();
    }

    @Step("Проверить: попап об успешном заказе появился")
    private void checkSuccess(OrderPage orderPage) {
        assertTrue(orderPage.isOrderSuccessVisible(),
                "Попап об успешном заказе не появился");
    }
}