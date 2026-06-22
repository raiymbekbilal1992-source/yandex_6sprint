package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    // Первая форма
    private By nameField    = By.xpath("//input[@placeholder='* Имя']");
    private By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroInput   = By.xpath("//input[@placeholder='* Станция метро']");
    private By metroOptions = By.xpath("//button[contains(@class,'select-search__option')]");
    private By phoneField   = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton   = By.xpath("//button[text()='Далее']");

    // Вторая форма
    private By dateField    = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentDropdown = By.className("Dropdown-control");
    private By rentOptions  = By.xpath("//div[contains(@class,'Dropdown-option')]");
    private By blackColor   = By.id("black");
    private By greyColor    = By.id("grey");
    private By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопки подтверждения
    private By orderButton   = By.xpath("(//button[text()='Заказать'])[1]");
    private By confirmButton = By.xpath("//button[text()='Да']");

    // Попап успеха
    private By successPopup = By.xpath("//*[contains(text(),'Заказ оформлен')]");

    public void fillFirstForm(String n, String s, String a, String metroStation, String p) {
        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        name.click();
        name.sendKeys(n);

        WebElement surname = driver.findElement(surnameField);
        surname.click();
        surname.sendKeys(s);

        WebElement address = driver.findElement(addressField);
        address.click();
        address.sendKeys(a);

        // Метро
        WebElement metro = wait.until(ExpectedConditions.elementToBeClickable(metroInput));
        metro.click();
        metro.sendKeys(metroStation);

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(metroOptions, 0));
        try { Thread.sleep(700); } catch (InterruptedException ignored) {}
        js.executeScript("arguments[0].click();", driver.findElements(metroOptions).get(0));

        // Пауза вместо ожидания закрытия — список остаётся в DOM но уже не мешает
        try { Thread.sleep(700); } catch (InterruptedException ignored) {}

        WebElement phone = driver.findElement(phoneField);
        phone.click();
        phone.sendKeys(p);
    }

    public void clickNext() {
        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        js.executeScript("arguments[0].click();", next);
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
    }

    public void fillSecondForm(String dateValue, String color, String commentText) {
        WebElement date = wait.until(ExpectedConditions.elementToBeClickable(dateField));
        date.click();
        date.sendKeys(dateValue);
        date.sendKeys("\n");

        wait.until(ExpectedConditions.elementToBeClickable(rentDropdown)).click();
        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(rentOptions)
        );
        js.executeScript("arguments[0].click();", options.get(0));

        if ("black".equalsIgnoreCase(color)) {
            js.executeScript("arguments[0].click();", driver.findElement(blackColor));
        } else if ("grey".equalsIgnoreCase(color)) {
            js.executeScript("arguments[0].click();", driver.findElement(greyColor));
        }

        driver.findElement(commentField).sendKeys(commentText);
    }

    public void clickOrder() {
        WebElement order = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        js.executeScript("arguments[0].click();", order);
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }

    public boolean isOrderSuccessVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successPopup)
        ).isDisplayed();
    }
}