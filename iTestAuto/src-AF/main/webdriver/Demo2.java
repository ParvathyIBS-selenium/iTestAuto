import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class Demo2 {

	public static void main(String[] args) throws MalformedURLException {
		
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("pCloudy_Username", "parvathy.geetha@ibsplc.com");
		capabilities.setCapability("pCloudy_ApiKey", "5rzyv7kptn9x4zr9j8jpxn4y");
		capabilities.setCapability("pCloudy_DurationInMinutes", 60);
		capabilities.setCapability("newCommandTimeout", 600);
		capabilities.setCapability("launchTimeout", 90000);
		capabilities.setCapability("pCloudy_DeviceFullName", "SAMSUNG_GalaxyS9_Android_10.0.0_374f4");
		capabilities.setCapability("platformVersion", "12.0.0");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("automationName", "uiautomator2");
		capabilities.setCapability("pCloudy_ApplicationName", "HHT.apk");
		capabilities.setCapability("appPackage", "com.icargo");
		capabilities.setCapability("appActivity", "com.icargo.MainActivity");
		capabilities.setCapability("pCloudy_WildNet", "true");
		capabilities.setCapability("pCloudy_EnableVideo", "false");
		capabilities.setCapability("pCloudy_EnablePerformanceData", "false");
		capabilities.setCapability("pCloudy_EnableDeviceLogs", "false");
		capabilities.setCapability("appiumVersion", "1.21.0");
		AndroidDriver androiddriver = new AndroidDriver<WebElement>(new URL("https://ibs-itq.pcloudy.com/appiumcloud/wd/hub"), capabilities);
	   
		androiddriver.rotate(ScreenOrientation.LANDSCAPE);
}
}
