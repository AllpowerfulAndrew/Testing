import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Testing {
    private BrowserInfo info;

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
        info = new BrowserInfo(new InternetExplorerDriver());
        info.check();
    }

    @Test
    public void checkChromeInfo() {
        info = new BrowserInfo(new ChromeDriver());
        info.check();
    }

    @Test
    public void checkFirefoxInfo() {
        info = new BrowserInfo(new FirefoxDriver());
        info.check();
    }

    @Test
    public void out() {
        new Excel().create();
    }
}