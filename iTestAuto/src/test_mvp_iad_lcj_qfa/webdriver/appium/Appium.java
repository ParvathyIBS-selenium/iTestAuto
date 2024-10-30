package appium;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Appium {
	
	
	
	public static void main(String args[]) throws MalformedURLException, InterruptedException
	{
		  DesiredCapabilities cap = new DesiredCapabilities();
			
			
			cap.setCapability("noReset","true"); 
			
		
			cap.setCapability("deviceName","emulator-5554");
			 
			
			cap.setCapability("platformVersion","8.0"); 
			cap.setCapability("platformName", "Android"); 
			
			cap.setCapability(MobileCapabilityType.APP, "D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\lib\\hht-app-release.apk");
			
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			cap.setCapability("appPackage", "com.icargo");
			cap.setCapability("appActivity", "com.icargo.MainActivity");
			  
			
	        cap.setCapability("newCommandTimeout", 60*15);
	        cap.setCapability("unicodeKeyboard", true);
	        cap.setCapability("resetKeyboard", true);
	        cap.setCapability("autoGrantPermissions", true);
	        cap.setCapability("adbExecTimeout",50000 );
	        
	
	
	

			
			
	        AndroidDriver  androiddriver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), cap);
			Thread.sleep(10000);
			/*androiddriver.findElementByAccessibilityId("Username").sendKeys("GHAADMIN");
			androiddriver.findElementByAccessibilityId("Password").sendKeys("weblogic");
			androiddriver.findElementByAccessibilityId("Login").click();
			
			Thread.sleep(10000);
			androiddriver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='Acceptance']")).click();
			Thread.sleep(1000);
			androiddriver.findElementByAccessibilityId("AWB / ULD / Tracking ID").sendKeys("05790001004");*/
			//androiddriver.quit();
			
			
			//[0,84][1440,2392]
			
		
			/*System.out.println(androiddriver.findElementByAccessibilityId("Username").getLocation().x);
			System.out.println(androiddriver.findElementByAccessibilityId("Username").getLocation().y);
			*/
			
			
			 Dimension size = androiddriver.manage().window().getSize();

			    int startX = 0;
			    int endX = 0;
			    int startY = 0;
			    int endY = 0;
			    startY = (int) (size.height / 2);
	            startX = (int) (size.width * 0.90);
	            endX = (int) (size.width * 0.05);
	            
	            for(int i=0;i<5;i++)
	            {
			    new TouchAction(androiddriver).press(startX, startY).waitAction(1000).release().perform();
               
               
	            }
               
                
}
			
			
			

			
	}


