package selenium.pages;

import org.openqa.selenium.WebDriver;

public class BNMSite {
    WebDriver driver;

    public BNMSite(WebDriver driver) {
        this.driver = driver;
    }

    public ExchangeRatesPage exchangeRatesPage() {
        return new ExchangeRatesPage(driver);
    }
}
