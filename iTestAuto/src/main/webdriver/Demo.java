import java.awt.AWTException;
import java.awt.Robot;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;






public class Demo {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, AWTException, IOException {
		
		
		
		
//		Robot r=new Robot();
//		
//		
//		String pathc = System.getProperty("user.dir")+ "\\lib\\chromedriver.exe";
//		System.setProperty("webdriver.chrome.driver", pathc);
//		
//		WebDriver driver=new ChromeDriver();
//		
//		driver.manage().window().maximize();
//		driver.get("http://192.168.145.137:8000/icargo/");
//		
//		Actions act=new Actions(driver);
//		
//		WebElement ele=driver.findElement(By.name("username"));
//		WebElement ele2=driver.findElement(By.name("btOk"));
//		WebElement ele3=driver.findElement(By.name("j_password"));
//		
//		List<WebElement>ele4=driver.findElements(By.name("username"));
		
//		for(WebElement ele7:ele4)
//		{
//			if(ele7.getText().equals("GHAADMIN"))
//			{
//				System.out.println("matched");
//			}
//		}
		
	
		//ele4.forEach(i -> System.out.println("i"));
		
		
//		
//
//		
//		JavascriptExecutor js=(JavascriptExecutor) driver;
//		js.executeScript("arguments[0].value='GHAADMIN'", ele);
//		r.keyPress(KeyEvent.VK_TAB);
//		r.keyRelease(KeyEvent.VK_TAB);
//		act.sendKeys("weblogic").perform();
//		
//		
//		//js.executeScript("arguments[0].value='weblogic'", ele3);
//		//js.executeScript("arguments[0].click();", ele2);
//		
//		
//		
//		
//		js.executeScript("arguments[0].click();", ele2);
		
		
		//Actions act=new Actions(driver);
		
		
//		act.moveToElement(ele).sendKeys("GHAADMIN").perform();
//		//driver.findElement(By.name("username")).sendKeys("GHAADMIN");
//		driver.findElement(By.name("j_password")).sendKeys("weblogic");
//		//driver.findElement(By.name("btOk")).click();
//		act.moveToElement(ele2).sendKeys(Keys.ENTER).perform();
		
		
//		Action builder=act.moveToElement(ele).sendKeys("GHAADMIN")
//				          .sendKeys(Keys.TAB)
//				          .sendKeys("weblogic")
//				          .sendKeys(Keys.TAB)
//				          .sendKeys(Keys.TAB)
//				          .sendKeys(Keys.ENTER).build();
//		
//		
//		builder.perform();
//		
//		
		
		
		
		
//		wd.findElement(By.name("username")).sendKeys("GHAADMIN");
//		wd.findElement(By.name("j_password")).sendKeys("weblogic");
//		wd.findElement(By.name("btOk")).click();
//	wd.quit();
		
		//Actions act=new Actions(wd);
		
//		wd.findElement(By.name("username")).sendKeys("GHAADMIN");
//		
//		
//		wd.findElement(By.name("j_password")).sendKeys("weblogic");
//		
//      	WebElement btnOK=wd.findElement(By.name("btOk"));
//    	WebElement usrName=wd.findElement(By.name("username"));
//    	WebElement password=wd.findElement(By.name("j_password"));
//    	wd.findElement(By.name("username")).sendKeys("GHAADMIN");
//    	//wd.findElement(By.name("j_password")).sendKeys("weblogic");
//    	JavascriptExecutor js=(JavascriptExecutor)wd;
//    	js.executeScript("arguments[0].value='weblogic'", password);
//    	js.executeScript("arguments[0].click();", btnOK);
//		
//		act.moveToElement(usrName).sendKeys("GHAADMIN").perform();
//		act.sendKeys(Keys.TAB).perform();
//		act.sendKeys("weblogic").perform();
//		btnOK.click();
//		
//		
//		Action builder=act.moveToElement(usrName).sendKeys("GHAADMIN")
//				.sendKeys(Keys.TAB)
//				.sendKeys("weblogic").build();
//		
//		builder.perform();
//		btnOK.click();	
		
//		wd.manage().window().maximize();
//		
//		wd.get("https://www.google.co.in/");
//		
//		
//		
//		
//		Actions act=new Actions(wd);
//		WebElement ele=wd.findElement(By.xpath("//a[contains(.,'Sign in')]"));
//		act.moveToElement(ele).click().build().perform();
//		
//		Thread.sleep(1000);
		
	
		
		/*wd.findElement(By.name("username")).sendKeys("GHAADMIN");
		Thread.sleep(2000);
		
		Robot robot=new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		
		Thread.sleep(2000);
		wd.findElement(By.name("j_password")).sendKeys("weblogic");
		//wd.findElement(By.name("btOk")).click();
		
		
		
		Thread.sleep(6000);
		wd.close();
		wd.findElement(By.name("btOk")).click();
		
		*/
		
	
		



		
		
		
		
/*
		WebDriver driver=new ChromeDriver();

		Actions act=new Actions(driver);
		
		WebElement ele=driver.findElement(By.xpath(""));
		
		act.moveToElement(ele).click().perform();
		
		act.moveToElement(ele).keyDown(Keys.TAB);
		
		*//**** ACTION***//*
		WebElement txtUsername=driver.findElement(By.xpath(""));
		Actions builder = new Actions(driver);
		Action seriesOfActions = builder
			.moveToElement(txtUsername)
			.click()
			.keyDown(txtUsername, Keys.SHIFT)
			.sendKeys(txtUsername, "hello")
			.keyUp(txtUsername, Keys.SHIFT)
			.doubleClick(txtUsername)
			.contextClick()
			.build();
			
		seriesOfActions.perform() ;
		
		
		HashMap<String,String> hm=new HashMap<String,String>();*/
		
		int i=0;
		
		while(i!=5)
		{
			i++;
			System.out.println(i);
			if(i==4)
				break;
			
		}
		
		
	}
}
