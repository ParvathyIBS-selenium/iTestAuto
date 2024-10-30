package screens;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.relevantcodes.extentreports.LogStatus;

import common.CommonUtility;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
import controls.ExcelRead;

public class Login extends CustomFunctions {


	String sheetName="Login";
	String screenName="Login";
	
	@FindBy(name="username")
	WebElement username;
	@FindBy(name="j_password")
	WebElement password;
	

	public Login(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		PageFactory.initElements(driver, this);
		
	}
		
	
		
		/**
		 * @author A-7271
		 * @param userName
		 * Desc : enter user name
		 */
		public void enterUserName(String userName)
		{
			//driver.findElement(By.name("username")).sendKeys(userName);
			
			username.sendKeys(userName);
		}
		
		/**
		 * @author A-7271
		 * @param password
		 * Desc : Method to set the password for Login Page
		 */
		public void password(String password2)
		{
			password.sendKeys(password2);
			
		}
		
		/**
		 * @author A-7271
		 * @throws IOException
		 * @throws InterruptedException
		 * Desc : Verify Login
		 */
		public void login() throws IOException, InterruptedException
		{
			try
			{
			Thread.sleep(3000);
		//	driver.findElement(By.xpath("//label[contains(.,'Login with Phone/Email')]")).click();
			WebDriverWait wait = new WebDriverWait(driver,60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Login with Google')]")));
			
		
			
		
			
			driver.findElement(By.xpath("//span[contains(.,'Login with Google')]")).click();
			
			
			
			 //Verify new window is opened or not
	          Assert.assertTrue(waitForNewWindow(driver,30), "New window is not opened");
			
			
	 
			for(String win:driver.getWindowHandles())
			{
				driver.switchTo().window(win);
				Thread.sleep(2000);
				
			}
	     
	         
	          
	      
			driver.findElement(By.xpath("//input[@type='email']")).sendKeys("xxx@gmail.com");
			
			test.log(LogStatus.PASS, "New login window opened");
			}
			
			catch(Exception e)
			{
				test.log(LogStatus.FAIL, "New login window not opened");
			}
					
		
			
		}
		
		/**
		 * @author A-7271
		 * @throws IOException 
		 */
		public void verifyLogin() throws IOException
		{
			System.out.println(driver.getTitle());
			
			if(driver.getTitle().startsWith("MakeMyTrips"))
					test.log(LogStatus.PASS, "URL loaded successfully");
			else
			{
				
				
				//Take screen shot
				
				File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				File dest=new File("D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\screenshots\\image1.png");
				FileUtils.copyFile(src, dest);
				
				test.log(LogStatus.FAIL, "URL loaded successfully");
				test.log(LogStatus.INFO, test.addScreenCapture("D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\screenshots\\image1.png"));
				
			}
			
			
		}
		
		/***
		 * 
		 * @param driver
		 * @param timeout
		 * @return
		 */
		public boolean waitForNewWindow(WebDriver driver, int timeout){
	          boolean flag = false;
	          int counter = 0;
	          while(!flag){
	              try {
	                  Set<String> winId = driver.getWindowHandles();
	                  if(winId.size() > 1){
	                      flag = true;
	                      return flag;
	                  }
	                  Thread.sleep(1000);
	                  counter++;
	                  if(counter > timeout){
	                      return flag;
	                  }
	              } catch (Exception e) {
	                  System.out.println(e.getMessage());
	                  return false;
	              }
	          }
	          return flag;
	      }

	}

