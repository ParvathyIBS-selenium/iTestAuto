import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;


public class WebMail {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		WebDriver wd;
		String sc2 = System.getProperty("user.dir");
		String pathc = sc2 + "\\lib\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", pathc);
		 wd=new ChromeDriver();
		wd.manage().window().maximize();
		
		wd.get("https://outlook.office.com/mail");
		
		wd.findElement(By.name("loginfmt")).sendKeys("A-7271@ibsplc.com");
		Thread.sleep(2000);
		wd.findElement(By.id("idSIButton9")).click();
		Thread.sleep(2000);
		wd.findElement(By.name("passwd")).sendKeys("Qwertyuiop60");
		Thread.sleep(2000);
		wd.findElement(By.id("idSIButton9")).click();
		
		Thread.sleep(2000);
		wd.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys("057 - 67897421");
		Thread.sleep(3000);
	
		wd.findElement(By.xpath("//i[@data-icon-name='Search']")).click();
		Thread.sleep(5000);
		WebElement ele=wd.findElement(By.xpath("//span[contains(.,'Damage Identified for the shipment -057 - 67897421')]"));
		
		
		Actions act=new Actions (wd);
		act.moveToElement(ele).doubleClick().perform();
		Thread.sleep(5000);
		
		for(String win:wd.getWindowHandles())
		{
			wd.switchTo().window(win);
			Thread.sleep(3000);
		}
		
		String actualText=wd.findElement(By.xpath("//span[contains(.,'Hi Team,')]/..")).getText();
		
		
		String expText="AWB Number :057 - 67897421"+System.lineSeparator()+"Airport Code :IAD"+System.lineSeparator()+
				"Damage Pieces :1"+System.lineSeparator()+"Damage Code :OTH"+System.lineSeparator()+"Damage Reason :IMPLOD"+
				System.lineSeparator()+"Remarks :Import test data";
				
				
				
	
		
		if(actualText.contains("AWB Number :057 - 67897421"))
		{
			System.out.println("matched");
		}
		
		
		

		
		

	}

}
