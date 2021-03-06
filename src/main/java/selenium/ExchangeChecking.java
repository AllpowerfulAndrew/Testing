package selenium;

import data.Currency;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import selenium.actions.CommonActions;
import selenium.pages.BNMSite;

import java.util.ArrayList;

public class ExchangeChecking {
    public static final Logger LOG = Logger.getLogger(ExchangeChecking.class);
    private WebDriver browser;
    private BNMSite bnmSite;
    private CommonActions actions;
    private ArrayList<Currency> currencies;

    public ExchangeChecking(final WebDriver browser) {
        this.browser = browser;
        bnmSite = new BNMSite(browser);
        actions = new CommonActions(browser);
    }

    public boolean check(final ArrayList<Currency> expected) {
        LOG.info("Opening browser");
        actions.open("http://bnm.md/ro/content/ratele-de-schimb");
        actions.maximize();
        actions.scrollPage(0, 400);
        bnmSite.exchangeRatesPage().switchDate(31, 2);

        LOG.info("Getting actual content");
        currencies = bnmSite.exchangeRatesPage().getData();

        LOG.info("Closing browser");
        browser.quit();

        return checkData(currencies, expected);
    }

    private boolean checkData(final ArrayList<Currency> current, final ArrayList<Currency> expected) {
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
