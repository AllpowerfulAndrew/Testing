import data.Currency;
import data.XMLParser;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenium.ExchangeChecking;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import static org.testng.Assert.assertEquals;

public class Testing {
    public static final Logger LOG = Logger.getLogger(Testing.class);
    private ExchangeChecking info;
    private DesiredCapabilities cap;
    private ArrayList<Currency> expected;

    // TODO Don't forget to set your personal full path to driver and
    private String chromeDriverPath;
    private URL url;

    @BeforeClass
    public void getExpectedInfo() {
        expected = (ArrayList<Currency>) new XMLParser().getCurrencies();
    }

    @BeforeClass
    public void setProperties() {
        LOG.info("Setting system properties");
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/test/resources/testing.properties"));

            chromeDriverPath = properties.getProperty("chromeDriver.fullPath");
            url = new URL(properties.getProperty("appium.server"));

            System.setProperty("webdriver.ie.driver", properties.getProperty("ieDriver.path"));
            System.setProperty("webdriver.chrome.driver", properties.getProperty("chromeDriver.path"));
            System.setProperty("webdriver.gecko.driver", properties.getProperty("firefoxDriver.path"));
        } catch (IOException e) {
            LOG.error("Fail with properties: ", e);
        }
    }

    @BeforeClass
    public void setCapability() {
        LOG.info("Setting android capabilities");
        cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "JVYTCEPRQOSONVFI");
        cap.setCapability(MobileCapabilityType.VERSION, "4.4.2");
    }

    @DataProvider
    public Object[][] getWebDrivers() {
        Object[][] drivers = {
                {"Start testing on IE", new InternetExplorerDriver()},
                {"Start testing on Chrome", new ChromeDriver()},
                {"Start testing on Firefox", new FirefoxDriver()}
        };

        return drivers;
    }

    @Test(dataProvider = "getWebDrivers")
    public void checkDesktopBrowser(String message, WebDriver driver) {
        LOG.info(message);
        info = new ExchangeChecking(driver);
        assertEquals(true, info.check(expected));
    }

    @Test
    public void checkAndroidBrowser() {
        cap.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
        cap.setCapability("chromedriverExecutable", chromeDriverPath);

        LOG.info("Start testing on Android - Chrome");
        info = new ExchangeChecking(new AndroidDriver(url, cap));
        assertEquals(true, info.check(expected));
    }

    @Test
    public void check() {
        WebDriver browser = new FirefoxDriver();
        browser.get("http://vk.com");
        browser.close();
    }
}