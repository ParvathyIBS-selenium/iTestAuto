package appium;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class test {
	public static void main(String args[]) throws MalformedURLException, InterruptedException
	{
		String sc2 = System.getProperty("user.dir");
		String pathc = sc2 + "\\lib\\chromedriver.exe";

		System.setProperty("webdriver.chrome.driver", pathc);
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://demo.travelfusion.com/user/mirror");
		
		Thread.sleep(10000);
		
		WebElement ele=driver.findElement(By.xpath("(//div[@class='seat seat_select W'])[1]"));
		
		Actions act=new Actions(driver);
		
		act.moveToElement(ele).build().perform();
		
		Thread.sleep(5000);
		
		//System.out.println(driver.getPageSource());
		
		String val=driver.findElement(By.xpath("//div[@id='powerTip']")).getText();
		System.out.println(val);
		
		List<WebElement> elements=driver.findElements(By.xpath("//div[@class='seat seat_select W']"));
		
		for(WebElement elemnt:elements)
		{
			
			
			act.moveToElement(elemnt).build().perform();
			
			if(driver.findElement(By.xpath("//div[@id='powerTip']")).getText().contains("0 GBP"))
			{
				elemnt.click();
			}
		}
		
		
	}
}
