import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BrowserInfo {
    private WebDriver browser;

    public BrowserInfo(final WebDriver browser) {
        this.browser = browser;
    }

    public void check() {
        browser.manage().window().maximize();
        browser.get("http://it-ebooks.info/");

        browser.findElement(By.cssSelector("input[value='title']")).click();
        browser.findElement(By.id("q")).clear();
        browser.findElement(By.id("q")).sendKeys("Automation");
        browser.findElement(By.cssSelector("input[type='submit']")).click();
        browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        browser.quit();
    }
}
