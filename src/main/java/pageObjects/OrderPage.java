package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(7));
    }

    private By name = By.xpath("//input[@placeholder='* Имя']");
    private By surname = By.xpath("//input[@placeholder='* Фамилия']");
    private By address = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metro = By.className("select-search__input");
    private By metroOptions = By.xpath("//div[contains(@class,'select-search__option')]");
    private By phone = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[text()='Далее']");

    private By date = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rent = By.className("Dropdown-control");
    private By rentOption = By.xpath("//div[contains(@class,'Dropdown-option')]");
    private By orderButton = By.xpath("(//button[text()='Заказать'])[1]");
    private By confirmButton = By.xpath("//button[text()='Да']");

    private By blackColor = By.id("black");
    private By comment = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private By successPopup = By.xpath("//div[contains(text(),'Заказ оформлен')]");

    public void fillFirstForm(String n, String s, String a, String p) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(name)).sendKeys(n);
        driver.findElement(surname).sendKeys(s);
        driver.findElement(address).sendKeys(a);

        // --- метро (фикс только на нестабильный случай) ---
        driver.findElement(metro).click();
        driver.findElement(metro).sendKeys("Черкизовская");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(metroOptions));
            List<WebElement> options = driver.findElements(metroOptions);
            options.get(0).click();
        } catch (Exception e) {
            // fallback только если список не появился (например у Петра)
            driver.findElement(metro).clear();
            driver.findElement(metro).sendKeys("Черкизовская");
            wait.until(ExpectedConditions.visibilityOfElementLocated(metroOptions));
            driver.findElements(metroOptions).get(0).click();
        }

        driver.findElement(phone).sendKeys(p);
    }

    public void clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(date));
    }

    public void fillSecondForm(String dateValue) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(date)).click();
        driver.findElement(date).sendKeys(dateValue);
        driver.findElement(date).sendKeys("\n");

        wait.until(ExpectedConditions.elementToBeClickable(rent)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(rentOption)).click();

        // добавленные поля
        driver.findElement(blackColor).click();
        driver.findElement(comment).sendKeys("Позвонить за час");
    }

    public void clickOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }

    public boolean isOrderSuccessVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup)).isDisplayed();
    }
}
