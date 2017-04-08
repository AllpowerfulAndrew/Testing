package selenium.actions;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonActions {
    private WebDriver browser;
    private WebDriverWait wait;

    public CommonActions(final WebDriver browser) {
        this.browser = browser;
        wait = new WebDriverWait(browser, 30);
    }

    public void scrollPage(final int x, final int y) {
        ((JavascriptExecutor) browser).executeScript("scroll(" + x + "," + y + ")");
    }

    public void maximize() {
        if (!(browser instanceof AndroidDriver))
            browser.manage().window().maximize();
    }

    public void open(final String url) {
        browser.get(url);
    }
}
