import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testing.WebBrowser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.testng.Assert.assertEquals;

public class Testing {
    private WebBrowser info;

    @BeforeClass
    public void setProperties() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/testing.properties"));

            System.setProperty("webdriver.ie.driver", properties.getProperty("ieDriver.path"));
            System.setProperty("webdriver.chrome.driver", properties.getProperty("chromeDriver.path"));
            System.setProperty("webdriver.gecko.driver", properties.getProperty("firefoxDriver.path"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIeInfo() {
        info = new WebBrowser(new InternetExplorerDriver());
        info.openPage();
        assertEquals(true, info.checkData());
    }

    @Test
    public void checkChromeInfo() {
        info = new WebBrowser(new ChromeDriver());
        info.openPage();
        assertEquals(true, info.checkData());
    }

    @Test
    public void checkFirefoxInfo() {
        info = new WebBrowser(new FirefoxDriver());
        info.openPage();
        assertEquals(true, info.checkData());
    }
}