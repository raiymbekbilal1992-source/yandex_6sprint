package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By cookieButton = By.id("rcc-confirm-button");
    private By orderButtons = By.xpath("//button[text()='Заказать']");
    private String question = "accordion__heading-";
    private String answer = "accordion__panel-";

    public void acceptCookies() {
        List<WebElement> cookies = driver.findElements(cookieButton);
        if (!cookies.isEmpty()) {
            cookies.get(0).click();
        }
    }

    public void clickTopOrderButton() {
        driver.findElements(orderButtons).get(0).click();
    }

    public void clickBottomOrderButton() {
        driver.findElements(orderButtons).get(1).click();
    }

    public void scrollToFAQ() {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0, 2500)");
    }

    public void clickQuestion(int index) {
        driver.findElement(By.id(question + index)).click();
    }

    public String getAnswer(int index) {
        return driver.findElement(By.id(answer + index)).getText();
    }
}