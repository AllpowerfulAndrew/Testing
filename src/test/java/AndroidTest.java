//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.remote.MobileCapabilityType;
//import org.openqa.selenium.Platform;
//import org.openqa.selenium.remote.BrowserType;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class AndroidTest {
//    DesiredCapabilities cap;
//
//    @BeforeClass
//    public void setup() {
//        cap = new DesiredCapabilities();
//        cap.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
//        cap.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
//        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
//        cap.setCapability(MobileCapabilityType.APP_PACKAGE, "");
//        cap.setCapability(MobileCapabilityType.APP_ACTIVITY, "");
//        cap.setCapability(MobileCapabilityType.VERSION, "4.4.2");
//    }
//
//    @Test
//    public void start() {
//        try {
//            AppiumDriver android = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
//            android.get("http://vk.com");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }
//}
