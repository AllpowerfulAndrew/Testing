package selenium.pages;

import data.Currency;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesPage {
    public static final Logger LOG = Logger.getLogger(ExchangeRatesPage.class);
    private WebDriver browser;
    private ArrayList<Currency> currencies;

    @FindBy(id = "edit-date-table")
    WebElement dateTable;

    @FindBy(css = ".ui-datepicker-month")
    WebElement monthMenu;

    @FindBy(css = ".rates-table")
    WebElement ratesTable;

    public ExchangeRatesPage(final WebDriver browser) {
        this.browser = browser;
        currencies = new ArrayList<>();
        PageFactory.initElements(browser, this);
    }

    public void switchDate(final int day, final int month) {
        ratesTable.click();
        dateTable.click();
        monthMenu.click();
        monthMenu.findElement(By.cssSelector("option[value='" + month + "']")).click();
        browser.findElement(By.linkText("" + day)).click();
    }

    public ArrayList getData() {
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

        return currencies;
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
}
