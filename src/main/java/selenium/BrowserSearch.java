package selenium;

import data.Currency;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class BrowserSearch {
    public static final Logger LOG = Logger.getLogger(BrowserSearch.class);
    private WebDriver browser;
    List<Currency> currencies = new ArrayList<>();

    public BrowserSearch(final WebDriver browser) {
        this.browser = browser;
    }

    public void openPage() {
        LOG.info("Opening browser");

        if (!(browser instanceof AndroidDriver))
            browser.manage().window().maximize();

        browser.get("http://bnm.md/ro/content/ratele-de-schimb");

        ((JavascriptExecutor)browser).executeScript("scroll(0,400)");
        browser.findElement(By.cssSelector(".rates-table")).click();
        browser.findElement(By.id("edit-date-table")).click();
        browser.findElement(By.cssSelector(".ui-datepicker-month")).click();
        browser.findElement(By.cssSelector(".ui-datepicker-month")).findElement(By.cssSelector("option[value='2']")).click();
        browser.findElement(By.linkText("31")).click();

        LOG.info("Getting actual content");
        getData();

        LOG.info("Closing browser");
        browser.quit();
    }

    private void getData() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOG.error("Thread error: ", e);
        }

        List<WebElement> tables = browser.findElement(By.cssSelector(".rates-table")).findElements(By.cssSelector(".rates"));

        for (int i = 0; i < tables.size(); i++) {
            List<WebElement> tr = tables.get(i).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

            savingDataFromPage(tr);
        }
    }

    private void savingDataFromPage(List<WebElement> tr) {
        String name = null;
        int numCode = 0;
        String charCode = null;
        int nominal = 0;
        double value = 0;
        int num = 0;

        for (int j = 0; j < tr.size(); j++) {
            List<WebElement> list = tr.get(j).findElements(By.tagName("td"));

            if (num == 5) num = 0;

            for (int k = 0; k < list.size(); k++) {
                WebElement element = list.get(k);

                switch (num) {
                    case 0:
                        name = element.getText();
                        break;
                    case 1:
                        numCode = Integer.parseInt(element.getText());
                        break;
                    case 2:
                        charCode = element.getText();
                        break;
                    case 3:
                        nominal = Integer.parseInt(element.getText());
                        break;
                    case 4:
                        value = Double.parseDouble(element.getText());
                        break;
                }

                num++;
            }

            currencies.add(new Currency(name, numCode, charCode, nominal, value));
        }
    }

    public boolean checkData(ArrayList<Currency> expected) {
        LOG.info("Start checking actual content with expected content");

        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).getName().equals(currencies.get(i).getName())) return false;
            if (!expected.get(i).getCharCode().equals(currencies.get(i).getCharCode())) return false;
            if (!(expected.get(i).getNominal() == currencies.get(i).getNominal())) return false;
            if (!(expected.get(i).getNumCode() == currencies.get(i).getNumCode())) return false;
            if (!(expected.get(i).getValue() == currencies.get(i).getValue())) return false;
        }

        LOG.info("Complete checking actual content with expected content");
        LOG.info("Return test results");
        return true;
    }
}
