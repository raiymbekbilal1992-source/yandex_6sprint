package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pageObjects.MainPage;

import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest extends BaseTest {

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7})
    public void testFAQ(int index) throws InterruptedException {

        MainPage page = new MainPage(driver);

        page.acceptCookies();
        page.scrollToFAQ();   // ✅ ВАЖНО!

        page.clickQuestion(index);

        // ✅ чтобы увидеть как работает
        Thread.sleep(2000);

        assertFalse(page.getAnswer(index).isEmpty());
    }
}