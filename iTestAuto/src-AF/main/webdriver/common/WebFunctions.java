package common;

import io.appium.java_client.MobileElement;

/*****Author name: A-7290 A-7626 and A-7688
 * Description: Common methods for Web Elements
 Date of creation: 29-05-18******/

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.openqa.selenium.Keys;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.Assert;

import bsh.ParseException;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import controls.ExcelRead;


public class WebFunctions extends DriverSetup{
	public String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public int RANDOMSTRINGLENGTH = 10;
	public static WebDriver driver;
	ExcelReadWrite excelReadWrite;
	public String excelfilename = null;
	public ExcelReadWrite excelreadwrite;
	public ExcelRead excelRead;
	public static ExtentTest test;
	Object[][] retObjArr = null;
	// String currentTestName;
	public Actions action;
	public CommonUtility commonUtility;
	public Xls_Read xls_Read;

	public int counter = 0;
	public Actions actions;
	public Alert alert;
	public String alertText;
	public WebElement ele;
	public boolean Status = true;
	static String windowHandle;
	static String referenceVar;
	public static String childWindow;
	public static String parentWindow;
	public static String firstChildWindow;
	public static Map<Object, Object> map;
	public static List<Object> list;
	
	public static CustomFunctions customFunction;
	public String projDir = System.getProperty("user.dir");
	public String globalVarPath = "\\src\\resources\\GlobalVariable.properties";
	public String filePathDownload = System.getProperty("user.dir")
			+ "\\src\\resources\\Downloads\\";
	public String screenshotPath = System.getProperty("user.dir")
			+ "\\screenshots\\";
	public String autoItPath = projDir + "\\autoIt\\File.exe";
	JavascriptExecutor js = (JavascriptExecutor) driver;
	public Wait wait;
	// Winium driver initialisation

	public static WiniumDriver windriver;
	
	//Android driver
	public static AndroidDriver androiddriver;

	// winium driver path

	public static String winium_driver_path = System.getProperty("user.dir")
			+ "\\lib\\Winium.Desktop.Driver.exe";

	public WebFunctions(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2)

	// Initializing variables
	{

		this.driver = driver;
		this.excelreadwrite = excelReadWrite;
		commonUtility = new CommonUtility();
		this.xls_Read = xls_Read2;
		excelRead = new ExcelRead();
		excelfilename = this.getClass().getSimpleName();
		actions = new Actions(driver);
		customFunction = new CustomFunctions(driver, excelReadWrite, xls_Read2);
	}

	WebElement element;
	String testSteps, pageName, eleName;

	// Zero Parameter constructor
	public WebFunctions() {
	}

	public ExtentTest getExtentTestInstance() {
		return this.test;
	}

	public void setExtentTestInstance(ExtentTest test) {
		this.test = test;
	}

	/**
	 * Description... Takes Object Map keys, returns the value of key
	 * 
	 * @param keyName
	 * @return Map values
	 */
	/*
	 * Author: A-7626 Date Modified :17-05-2018
	 */
	public String data(String keyName) {

		try {

			if (keyName.contains("prop~"))

			{

				String keyVal = keyName.split("~")[1].toString();

				return (getPropertyValue(globalVarPath, keyVal));

			}

			else if (keyName.contains("val~")) {

				return (keyName.split("~")[1].toString());
			} else {
				return (String) map.get(keyName);
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not fetch correct data value");
			System.out.println(e);
		}
		return null;
	}
	/*****
	 * 
	 * @param screenName
	 * @param expText
	 * @param actText
	 * @param functinalityName
	 * @param testSteps
	 * @param isAssertionReq
	 * Desc : To handle assertion
	 */
	public void onFailUpdate(String screenName, String expText, String actText,
			String functinalityName, String testSteps,boolean isAssertionReq) {
		counter = counter + 1;
		excelreadwrite.insertFailedData(
				DriverSetup.testName,
				commonUtility.getcurrentDateTime() + "_"
						+ String.valueOf(counter), "Verify the functionality "
						+ functinalityName + " On " + screenName + " Screen",
				testSteps, "Expected Value is : " + expText
						+ " \nActual value is : " + actText, false, "",
				actText,

				expText);
		test.log(LogStatus.FAIL, "Failed to Verify " + expText);
		System.out.println("Failed to Verify " + expText);
		
		if(isAssertionReq)
		{
		Assert.assertFalse(true, "Element is not found");
		}
	}
	/*
	 * A-8705 Selects Multiple Checkboxes
	 */
	public void selectMultipleCheckboxes(String sheetName, String locator) {
		List<WebElement> checkboxes = driver.findElements(By.xpath(xls_Read
				.getCellValue(sheetName, locator)));
		for (WebElement c : checkboxes) {
			if (!c.isSelected()) {
				c.click();
			}
		}

	}

	/*
	 * A-8705 Deselects Checkboxes
	 */

	public void deselectCheckboxes(String sheetName, String locator, int i) {
		List<WebElement> checkboxes = driver.findElements(By.xpath(xls_Read
				.getCellValue(sheetName, locator)));
		for (int j = 0; j < i; j++) {
			checkboxes.get(j).click();
		}
	}

	/*
	 * A-8705 Deselects Multiple Checkboxes and handle alert
	 */
	public String deselectMultipleCheckboxesandHandleAlert(String sheetName,
			String locator) {
		String actualMsg = "";
		List<WebElement> checkboxes = driver.findElements(By.xpath(xls_Read
				.getCellValue(sheetName, locator)));
		for (WebElement c : checkboxes) {
			try {
				if (c.isSelected()) {
					c.click();
				}
			} catch (Exception e) {
				actualMsg = customFunction.handleAlertAndReturnText();

			}

		}
		return actualMsg;
	}

	/*** String operations ****/
	public void stringOperations(String ops, String expText[], String actText) {

		switch (ops) {

		case "compare":
			for (int i = 0; i < expText.length; i++) {
				if (actText.contains(expText[i])) {
					onPassUpdate("", expText[i], actText, "", "");
				} else {
					onFailUpdate("", expText[i], actText, "", "");
				}
			}
			break;
		}
	}

	/*
	 * Author : A-7271 ; Scroll bars
	 */
	public void scrollBars(String scroll, int coordinate)
			throws InterruptedException {
		try {
			switch (scroll) {
			case "down":
				js.executeScript("window.scrollBy(0," + coordinate + ")", "");
				break;
			case "up":
				js.executeScript("window.scrollBy(0,-" + coordinate + ")", "");
				break;
			case "bottom":
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				break;
			case "top":
				js.executeScript("window.scrollTo(0,0)");
				break;
			case "right":
				js.executeScript("window.scrollBy(" + coordinate + ",0)", "");
				break;

			case "left":
				js.executeScript("window.scrollBy(-" + coordinate + ",0)", "");
				break;
			}
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Could not perform javascript scroll" + e);
		}
	}
	
/**
 * @author A-7271
 * Desc : To scroll the android screen till page down.
 */
public void androidScrolllTillPageDown()
{
	try{
	waitForSync(10);
    Dimension dim = androiddriver.manage().window().getSize();
    int height = dim.getHeight();
    int width = dim.getWidth();
    int x = width/2;
    int top_y = (int)(height*0.80);
    int bottom_y = (int)(height*0.20);
    System.out.println("coordinates :" + x + "  "+ top_y + " "+ bottom_y);
    TouchAction ts = new TouchAction(androiddriver);
    ts.longPress(x, top_y).moveTo(x, bottom_y).release().perform();
    writeExtent("Pass","Scrolled till page down");
	}
	
	catch(Exception e)
	{
		 writeExtent("Fail","Could not scroll till page down");
	}

}

/**
 * 
 * @param proppath
 * @param locator
 * @Desc : Wait till android element is visible
 */
public void waitTillMobileElementDisplay(String proppath,String locator,String locatorType)
{
	try
	{
		String element=getPropertyValue(proppath, locator);

		int i=0;

		if(locatorType.equals("xpath"))
		{
			while(androiddriver.findElements(By.xpath(element)).size()!=1)
			{
				waitForSync(1);
				i++;
				if(i==60)
					break;
			}
		}

		else
		{
			while(androiddriver.findElementsByAccessibilityId(element).size()!=1)
			{
				waitForSync(1);
				i++;
				if(i==60)
					break;
			}
		}

	}

	catch(Exception e)
	{

	}
}
/**
 * 
 * @param proppath
 * @param locator
 * @Desc : Wait till android element is visible
 */
public void waitTillMobileElementDisplay(String proppath,String locator,String locatorType,int wait)
{
	try
	{
		String element=getPropertyValue(proppath, locator);

		int i=0;

		if(locatorType.equals("xpath"))
		{
			while(androiddriver.findElements(By.xpath(element)).size()!=1)
			{
				waitForSync(1);
				i++;
				if(i==wait)
					break;
			}
		}

		else
		{
			while(androiddriver.findElementsByAccessibilityId(element).size()!=1)
			{
				waitForSync(1);
				i++;
				if(i==wait)
					break;
			}
		}

	}

	catch(Exception e)
	{

	}
}

/**
 * @Description : Method for verifying contents are present and not present based on the boolean value passed
 * @author A-9844
 * @param reportHeading
 * @param screenId
 * @param VP
 * @throws Exception
 */
public void printAndVerifyReport(String reportHeading,String screenId,boolean isPresent,String...VP ) throws Exception
{
	try
	{

		//Verification if report got generated

		switchToWindow("storeParent");

		switchToWindow("multipleWindows");

		int windowSize=getWindowSize();



		if(windowSize==2)
		{
			switchToFrame("frameName","ReportContainerFrame");

			//Verifying heading of the report

			String locatorHeading=xls_Read.getCellValue("Generic_Elements", "htmlDiv_reportHeading;xpath");
			locatorHeading=locatorHeading.replace("ReportHeading", data(reportHeading));
			try {
				if(driver.findElement(By.xpath(locatorHeading)).isDisplayed())
				{
					onPassUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is  getting generated", "Verify whether the report is generated","Verify whether the report is generated");
				}
				else
				{
					onFailUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is Not getting generated", "Verify whether the report is Not generated","Verify whether the report is not generated",false);
				}

			} catch (Exception e) {
				onFailUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is Not getting generated", "Verify whether the report is Not generated","Verify whether the report is not generated",false);
			}

			//Verifying Report Contents

			String locatorContent=xls_Read.getCellValue("Generic_Elements", "htmlContents_report;xpath");
			String contents=driver.findElement(By.xpath(locatorContent)).getText();
			System.out.println(contents);

			waitForSync(5);
			for(String s:VP)
			{

				if(isPresent){
					waitForSync(5);

					if(contents.contains(s))
					{
						writeExtent("Pass", "Sucessfully Verified on report : " +s + " on " + screenId);
                        contents=contents.replaceFirst(s,"");
                        System.out.println(contents);

					}
					else
					{ 
						writeExtent("Fail", "Not Verified on report : " + s + " on " + screenId);
					}
				}


				else{


					waitForSync(2);

					if(!contents.contains(s))
					{
						writeExtent("Pass", "Sucessfully Verified the content is not present on the report : " +s + " on " + screenId);
					}
					else
					{ 
						writeExtent("Fail", "Content is present on the report : " + s + " on " + screenId);
					}
				}
			}
		}
	}




	catch(Exception e)
	{
		writeExtent("Fail", "Report is not getting generated"+" on " + screenId);
	}
	finally
	{
		closeBrowser();
		waitForSync(2);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame",screenId);
	}

}


	/**
	 * @Description : Verifying Print Contents 
	 * @author A-9175
	 * @param reportHeading
	 * @param screenId
	 * @param VP
	 * @throws Exception
	 */
	public void printAndVerifyReport(String reportHeading,String screenId,String...VP ) throws Exception
    {
       try
       {
        
       //Verification if report got generated
    	 
       switchToWindow("storeParent");
       
       switchToWindow("multipleWindows");

       int windowSize=getWindowSize();
       

       if(windowSize==2)
       {
             switchToFrame("frameName","ReportContainerFrame");
            
             //Verifying heading of the report
             
             String locatorHeading=xls_Read.getCellValue("Generic_Elements", "htmlDiv_reportHeading;xpath");
             locatorHeading=locatorHeading.replace("ReportHeading", data(reportHeading));
             try {
            	 if(driver.findElement(By.xpath(locatorHeading)).isDisplayed())
                 {
                       onPassUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is  getting generated", "Verify whether the report is generated","Verify whether the report is generated");
                 }
                 else
                 {
                       onFailUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is Not getting generated", "Verify whether the report is Not generated","Verify whether the report is not generated",false);
                 }
                  
			} catch (Exception e) {
				 onFailUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is Not getting generated", "Verify whether the report is Not generated","Verify whether the report is not generated",false);
			}
             
             //Verifying Report Contents
             
             String locatorContent=xls_Read.getCellValue("Generic_Elements", "htmlContents_report;xpath");
             String contents=driver.findElement(By.xpath(locatorContent)).getText();
             System.out.println(contents);
             for(String s:VP)
             {
            	 waitForSync(2);
            	
            	 if(contents.contains(s))
            	 {
            		 writeExtent("Pass", "Sucessfully Verified on report : " +s + " on " + screenId);
            		 contents=contents.replaceFirst(s,"");
                     System.out.println(contents);
            	 }
            	 else
            	 { 
            		 writeExtent("Fail", "Not Verified on report : " + s + " on " + screenId);
            	 }
             }
       }
       }
       catch(Exception e)
       {
    	   writeExtent("Fail", "Report is not getting generated"+" on " + screenId);
       }
       finally
       {
    	   closeBrowser();
           waitForSync(2);
           switchToWindow("getParent");
           switchToFrame("default");
           switchToFrame("contentFrame",screenId);
       }
                   
    }


	/**
	 * @author A-7271
	 * @param sheetName
	 * @param locator
	 * @param actions
	 * @throws InterruptedException
	 * Description : Perform mouse actions using Actions Class
	 */
	public void performMouseActions(String sheetName,String locator,String actions)
			throws InterruptedException {
		
		WebElement ele=driver.findElement(By.xpath(xls_Read
				.getCellValue(sheetName, locator)));
		
		Actions act=new Actions(driver);
		try {
			switch (actions) {
			case "click":
				act.moveToElement(ele).click().perform();
				break;
			case "doubleClick":
				act.moveToElement(ele).doubleClick().perform();
				break;
			}
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Could not perform mouse actions using Actions class");
		}
	}
	/*****
	 * 
	 * @param screenName
	 * @param expText
	 * @param actText
	 * @param functinalityName
	 * @param testSteps
	 * @param isAssertionReq
	 * Desc : To handle assertion
	 */
	public void onInfoUpdate(String screenName, String expText, String actText,
			String functinalityName, String testSteps,boolean isAssertionReq) {
		counter = counter + 1;
		excelreadwrite.insertFailedData(
				DriverSetup.testName,
				commonUtility.getcurrentDateTime() + "_"
						+ String.valueOf(counter), "Verify the functionality "
						+ functinalityName + " On " + screenName + " Screen",
				testSteps, "Expected Value is : " + expText
						+ " \nActual value is : " + actText, false, "",
				actText,

				expText);
		test.log(LogStatus.INFO, "Failed to Verify " + expText);
		System.out.println("Failed to Verify " + expText);
		
		
	}
	/*
	 * Author: A-7271 Date Modified :30-05-2018
	 */

	public WebDriver relaunchBrowser() {

		try {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			String sc2 = System.getProperty("user.dir");
			String pathc = sc2 + "\\lib\\chromedriver.exe";

			System.setProperty("webdriver.chrome.driver", pathc);
			ChromeOptions options = new ChromeOptions();// Added for checking
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", sc2
					+ "\\src\\resources\\Downloads\\");
			options.setExperimentalOption("prefs", chromePrefs);
			 options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
			options.addArguments("--test-type");
			options.addArguments("start-maximized", "disable-popup-blocking");

			Proxy proxy = new Proxy();// Added for checking for proxy settings
			proxy.setProxyType(Proxy.ProxyType.SYSTEM);// Added for checking for
			// proxy settings
			capabilities.setBrowserName("chrome");
			capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			capabilities.setCapability("proxy", proxy);// Added for checking for
			// proxy settings
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);// Added
			// for
			// checking
			capabilities.setCapability("chrome.switches",
					Arrays.asList("--start-maximized"));
			driver = new ChromeDriver(capabilities);
			driver.manage().window().maximize();

			return driver;
		}

		catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not relaunch browser" + e);
			return null;
		}

	}
	/**
	 * 
	 * @param apk
	 * @return
	 * Desc : launchTransportOrderApp
	 */
	public AndroidDriver launchTransportOrder(String apk) {

		try {
			    DesiredCapabilities cap = new DesiredCapabilities();
			    cap.setCapability("noReset","true"); 
				cap.setCapability("deviceName","emulator-5554");
				cap.setCapability("platformVersion","8.0"); 
				cap.setCapability("platformName", "Android"); 
				cap.setCapability(MobileCapabilityType.APP, projDir+"\\lib\\"+apk+".apk");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
				cap.setCapability("appPackage", "com.ibsplc.icargo.transportorderlisting");
				cap.setCapability("appActivity", "com.icargo.MainActivity");
		        cap.setCapability("newCommandTimeout", 60*15);
		        cap.setCapability("unicodeKeyboard", true);
		        cap.setCapability("resetKeyboard", true);
		        cap.setCapability("autoGrantPermissions", true);
		        cap.setCapability("adbExecTimeout",50000 );
		        map.put("SelectedApk","Transport Order Listing");
		      androiddriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
               waitForSync(5);
               
               
			 return androiddriver;
		}

		catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not launch app" + e);
			return null;
		}
	}


	/**
	 * 
	 * @param apk
	 * @return
	 * Desc : Launch HHT APP
	 */
	public AndroidDriver launchHHT(String apk) {
		

		try {
			    DesiredCapabilities cap = new DesiredCapabilities();
			    cap.setCapability("noReset","true"); 
				cap.setCapability("deviceName","emulator-5554");
				cap.setCapability("platformVersion","9.0"); 
				cap.setCapability("platformName", "Android"); 
				cap.setCapability(MobileCapabilityType.APP, projDir+"\\lib\\"+apk+".apk");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
				cap.setCapability("appPackage", "com.icargo");
				cap.setCapability("appActivity", "com.icargo.MainActivity");
		        cap.setCapability("newCommandTimeout", 60*15);
		        cap.setCapability("unicodeKeyboard", true);
		        cap.setCapability("resetKeyboard", true);
		        cap.setCapability("autoGrantPermissions", true);
		        cap.setCapability("adbExecTimeout",50000 );
		        map.put("SelectedApk", "iCargo");
		      androiddriver = new AndroidDriver(new URL("http://127.0.0.1:4734/wd/hub"), cap);
               waitForSync(20);
			 return androiddriver;
		}

		catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not launch app" + e);
			return null;
		}

	}
	/**
	 * 
	 * @param apk
	 * @return
	 * Desc : Launch HHT APP
	 */
	public AndroidDriver launchApp(String apk) {
		

		try {
			    DesiredCapabilities cap = new DesiredCapabilities();
			    cap.setCapability("noReset","true"); 
				cap.setCapability("deviceName","emulator-5554");
				cap.setCapability("platformVersion","8.0"); 
				cap.setCapability("platformName", "Android"); 
				cap.setCapability(MobileCapabilityType.APP, projDir+"\\lib\\"+apk+".apk");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
				cap.setCapability("appPackage", "com.icargo");
				cap.setCapability("appActivity", "com.icargo.MainActivity");
		        cap.setCapability("newCommandTimeout", 60*15);
		        cap.setCapability("unicodeKeyboard", true);
		        cap.setCapability("resetKeyboard", true);
		        cap.setCapability("autoGrantPermissions", true);
		        cap.setCapability("adbExecTimeout",50000 );
		        map.put("SelectedApk","iCargo");
		      androiddriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		   
               waitForSync(20);
               
               
			 return androiddriver;
		}

		catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not launch app" + e);
			return null;
		}

	}
	
	/**
	 * 
	 * @param apk
	 * @return
	 * Desc : Launch HHT APP
	 * @throws MalformedURLException 
	 */
	public AndroidDriver launchAppInpCloudy(String apk) throws MalformedURLException {
		

		//try {
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
			androiddriver = new AndroidDriver<WebElement>(new URL("https://ibs-itq.pcloudy.com/appiumcloud/wd/hub"), capabilities);
		   
               waitForSync(20);
               
               
			 return androiddriver;
//		}
//
//		catch (Exception e) {
//			test.log(LogStatus.FAIL, "Could not launch app" + e);
//			return null;
//		}

	}
	/**
	 * 
	 * @param apk
	 * @return
	 * Desc : ExportBuildUpApp
	 * @throws MalformedURLException 
	 */
	public AndroidDriver launchExportBuildUpApp(String apk) throws MalformedURLException {

		try {
			    DesiredCapabilities cap = new DesiredCapabilities();
			    cap.setCapability("noReset","true"); 
				cap.setCapability("deviceName","emulator-5554");
				cap.setCapability("platformVersion","8.0"); 
				cap.setCapability("platformName", "Android"); 
				//cap.setCapability(MobileCapabilityType.APP, projDir+"\\lib\\"+apk+".apk");
				cap.setCapability(MobileCapabilityType.APP, "C:\\Users\\A-7271\\Desktop\\APKs\\exportbuildup-app-release.apk");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
				cap.setCapability("appPackage", "com.ibsplc.icargo.exportbuildup");
				cap.setCapability("appActivity", "com.icargo.MainActivity");
		        cap.setCapability("newCommandTimeout", 60*15);
		        cap.setCapability("unicodeKeyboard", true);
		        cap.setCapability("resetKeyboard", true);
		        cap.setCapability("autoGrantPermissions", true);
		        cap.setCapability("adbExecTimeout",50000 );
		        map.put("SelectedApk","Export Buildup");
		      androiddriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
               waitForSync(5);
               
               
			 return androiddriver;
		}

		catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not launch app" + e);
			return null;
		}

	}
	
	
	/**
	 * 
	 * @param apk
	 * @return
	 * Desc : Launch SST APP
	 */
	public AndroidDriver launchSSTApp(String apk) {

		try {
			    DesiredCapabilities cap = new DesiredCapabilities();
			    cap.setCapability("noReset","false"); 
				cap.setCapability("deviceName","emulator-5554");
				cap.setCapability("platformVersion","8.0"); 
				cap.setCapability("platformName", "Android"); 
				cap.setCapability(MobileCapabilityType.APP, projDir+"\\lib\\"+apk+".apk");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
				cap.setCapability("appPackage", "com.ibsplc.icargo.sst");
				cap.setCapability("appActivity", "com.icargo.MainActivity");
		        cap.setCapability("newCommandTimeout", 60*15);
		        cap.setCapability("unicodeKeyboard", true);
		        cap.setCapability("resetKeyboard", true);
		        cap.setCapability("autoGrantPermissions", true);
		        cap.setCapability("adbExecTimeout",50000 );
		        map.put("SelectedApk","SST");
		      androiddriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
               waitForSync(5);
               
               
			 return androiddriver;
		}

		catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not launch app" + e);
			return null;
		}

	}
	/**
	 * 
	 * @param apk
	 * @return
	 * Desc : Launch SST APP
	 */
	public AndroidDriver launchSSTApp(String apk,boolean reset) {

		try {
			    DesiredCapabilities cap = new DesiredCapabilities();
			    if(reset)
			    {
			    cap.setCapability("noReset","false"); 
			    }
			    else
			    {
			    	cap.setCapability("noReset","true"); 	
			    }
				cap.setCapability("deviceName","emulator-5554");
				cap.setCapability("platformVersion","8.0"); 
				cap.setCapability("platformName", "Android"); 
				cap.setCapability(MobileCapabilityType.APP, projDir+"\\lib\\"+apk+".apk");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
				cap.setCapability("appPackage", "com.ibsplc.icargo.sst");
				cap.setCapability("appActivity", "com.icargo.MainActivity");
		        cap.setCapability("newCommandTimeout", 60*15);
		        cap.setCapability("unicodeKeyboard", true);
		        cap.setCapability("resetKeyboard", true);
		        cap.setCapability("autoGrantPermissions", true);
		        cap.setCapability("adbExecTimeout",50000 );
		        map.put("SelectedApk","SST");
		      androiddriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
               waitForSync(5);
               
               
			 return androiddriver;
		}

		catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not launch app" + e);
			return null;
		}

	}
	/**
	 * 
	 * @param apk
	 * @return
	 * Desc : Launch ULD SIGHTING APP
	 */
	public AndroidDriver launchUldSightingApp(String apk) {

		try {
			    DesiredCapabilities cap = new DesiredCapabilities();
			    cap.setCapability("noReset","true"); 
				cap.setCapability("deviceName","emulator-5554");
				cap.setCapability("platformVersion","8.0"); 
				cap.setCapability("platformName", "Android"); 
				cap.setCapability(MobileCapabilityType.APP, projDir+"\\lib\\"+apk+".apk");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
				cap.setCapability("appPackage", "com.ibsplc.icargo.uldsighting");
				cap.setCapability("appActivity", "com.icargo.MainActivity");
		        cap.setCapability("newCommandTimeout", 60*15);
		        cap.setCapability("unicodeKeyboard", true);
		        cap.setCapability("resetKeyboard", true);
		        cap.setCapability("autoGrantPermissions", true);
		        cap.setCapability("adbExecTimeout",50000 );
		        map.put("SelectedApk", "ULD Sighting");
		      androiddriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
               waitForSync(5);
               
               
			 return androiddriver;
		}

		catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not launch app" + e);
			return null;
		}

	}
	/*
	 * Author: A-7271 Date Modified :30-05-2018
	 */
	public enum applications {
		iCargo, BE, Patriarch, iMail, hht,hht2,hht3, cgomon,cgocxml,mercury,iCargoSTG,cafeed,vccustoms,Cgoicss,afls,afls_flightPlan,Cgospa,hhtWro
	}

	/**
	 * Description... Takes environment details and returns username, password
	 * and URL from Global Varialble properties file
	 * 
	 * @param keyName
	 * @return Map values
	 */
	public String[] getApplicationParams(String appln) {
		String[] params = new String[3];
		switch (applications.valueOf(appln)) {

		case iCargo:
			params[0] = getPropertyValue(globalVarPath, "iCargoURL");
			params[1] = getPropertyValue(globalVarPath, "iCargoUN");
			params[2] = getPropertyValue(globalVarPath, "iCargoPWD");
			return params;

		case BE:
			params[0] = getPropertyValue(globalVarPath, "BEURL");
			params[1] = getPropertyValue(globalVarPath, "BEUN");
			params[2] = getPropertyValue(globalVarPath, "BEPWD");
			return params;

		case Patriarch:
			params[0] = getPropertyValue(globalVarPath, "patriarchURL");
			params[1] = getPropertyValue(globalVarPath, "patriarchUN");
			params[2] = getPropertyValue(globalVarPath, "patriarchPWD");
			return params;

		case iMail:
			params[0] = getPropertyValue(globalVarPath, "iMailURL");
			params[1] = getPropertyValue(globalVarPath, "iMailUN");
			params[2] = getPropertyValue(globalVarPath, "iMailPWD");
			return params;


		case hht:
			params[0] = getPropertyValue(globalVarPath, "hhtUN");
			params[1] = getPropertyValue(globalVarPath, "hhtPWD");
			map.put("sstCredentials", "sst");
			
			return params;
			

		case hht2:
			params[0] = getPropertyValue(globalVarPath, "hhtUN2");
			params[1] = getPropertyValue(globalVarPath, "hhtPWD2");
			map.put("sstCredentials", "sst2");
		
			return params;
			
		case hht3:
			params[0] = getPropertyValue(globalVarPath, "hhtUN3");
			params[1] = getPropertyValue(globalVarPath, "hhtPWD3");
			return params;

		

		case cgomon:
			params[0] = getPropertyValue(globalVarPath, "cgomonURL");
			params[1] = getPropertyValue(globalVarPath, "cgomonUN");
			params[2] = getPropertyValue(globalVarPath, "cgomonPWD");
			return params;
			
		case cgocxml:
			/***params[0] = getPropertyValue(globalVarPath, "cgocxmlURL");***/
			params[0] = getPropertyValue(globalVarPath, "cgomonURL");
			params[1] = getPropertyValue(globalVarPath, "cgocxmlUN");
			params[2] = getPropertyValue(globalVarPath, "cgocxmlPWD");
			return params;
			
		case iCargoSTG:
			params[0] = getPropertyValue(globalVarPath, "iCargoSTGURL");
			params[1] = getPropertyValue(globalVarPath, "iCargoSTGUN");
			params[2] = getPropertyValue(globalVarPath, "iCargoSTGPWD");
			return params;

		case mercury:
			params[0] = getPropertyValue(globalVarPath, "mercuryURL");
			params[1] = getPropertyValue(globalVarPath, "mercuryUN");
			params[2] = getPropertyValue(globalVarPath, "mercuryPWD");
			return params;
			
		case cafeed:
			params[0] = getPropertyValue(globalVarPath, "cafeedURL");
			params[1] = getPropertyValue(globalVarPath, "cafeedUN");
			params[2] = getPropertyValue(globalVarPath, "cafeedPWD");
			return params;
			
		case vccustoms:
			params[0] = getPropertyValue(globalVarPath, "vccustomsURL");
			params[1] = getPropertyValue(globalVarPath, "vccustomsUN");
			params[2] = getPropertyValue(globalVarPath, "vccustomsPWD");
            return params;
            
		case Cgoicss:
			params[0] = getPropertyValue(globalVarPath, "cgoicssURL");
			params[1] = getPropertyValue(globalVarPath, "cgoicssUN");
			params[2] = getPropertyValue(globalVarPath, "cgoicssPWD");
			return params;  
			
		case afls:
			   params[0] = getPropertyValue(globalVarPath, "aflsURL");
			   params[1] = getPropertyValue(globalVarPath, "aflsUN");
			   params[2] = getPropertyValue(globalVarPath, "aflsPWD");
			   return params; 
			   
		case afls_flightPlan:

			 params[0] = getPropertyValue(globalVarPath, "aflsFPURL");
			 params[1] = getPropertyValue(globalVarPath, "aflsFPUN");
			 params[2] = getPropertyValue(globalVarPath, "aflsFPPWD");
			 return params; 
			 
		case Cgospa:
			params[0] = getPropertyValue(globalVarPath, "cgospaURL");
			params[1] = getPropertyValue(globalVarPath, "cgospaUN");
			params[2] = getPropertyValue(globalVarPath, "cgospaPWD");
			 return params; 
			 
		case hhtWro:
			params[0] = getPropertyValue(globalVarPath, "hhtWROUN");
			params[1] = getPropertyValue(globalVarPath, "hhtWROPWD");
			return params;



		}
		return params;

	}
	/**
	 * 
	 * @param src
	 * @param dest
	 * @throws IOException
	 * Desc : Copy file
	 */
	public static void copy(File src, File dest) throws IOException { 
		InputStream is = null; 
		OutputStream os = null; 
		try { 
		is = new FileInputStream(src);
		 os = new FileOutputStream(dest); 
		// buffer size 1K 
		byte[] buf = new byte[1024]; 
		int bytesRead; 
		while ((bytesRead = is.read(buf)) > 0) 
		{
		 os.write(buf, 0, bytesRead); 
		}
		 } finally 
		{ 
		is.close();
		 os.close();
		 } }
	
	/*****Capture Screenhot
	 * @throws IOException ****/
	public String captureScreenShot(String drivr) throws IOException
	{
		/******* CALENDAR****/
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__HH_mm_ss");
		
		 TakesScreenshot scrShot=null;
		if(drivr.equals("Web"))
		{
		  scrShot =((TakesScreenshot)driver);
		}
		
		else if(drivr.equals("Android"))
		{
			 scrShot =((TakesScreenshot)androiddriver);
		}
		 //Call getScreenshotAs method to create image file

        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
      //Copy file at destination
        String destination=screenshotPath+testName+"_"+dateFormat.format(date)+".png";
        File DestFile = new File(screenshotPath+testName+"_"+dateFormat.format(date)+".png");
        System.out.println(destination);
        FileUtils.copyFile(SrcFile, DestFile);
        setPropertyValue("screenShotPath", destination, globalVarPath);
        return destination;
       
	}
	/*****Capture Screenhot
	 * @throws IOException ****/
	public String captureScreenShot(String drivr,String testdata) throws IOException
	{
		/******* CALENDAR****/
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__HH_mm_ss");
		
		 TakesScreenshot scrShot=null;
		if(drivr.equals("Web"))
		{
		  scrShot =((TakesScreenshot)driver);
		}
		
		else if(drivr.equals("Android"))
		{
			 scrShot =((TakesScreenshot)androiddriver);
		}
		 //Call getScreenshotAs method to create image file

        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
      //Copy file at destination
        String destination=screenshotPath+testdata+"_"+dateFormat.format(date)+".png";
        File DestFile = new File(screenshotPath+testdata+"_"+dateFormat.format(date)+".png");
        System.out.println(destination);
        FileUtils.copyFile(SrcFile, DestFile);
        setPropertyValue("screenShotPath", destination, globalVarPath);
        return destination;
       
	}
	// for switch to default frame argument is "default"
	// for switch to a particular frame arguments are "frameLocator", sheetName
	// name
	// for switch to content frame arguments are "contentFrame" and ScreenId ex
	// "OPR016"

	/**
	 * Description... for switch to default frame argument is "default" for
	 * switch to a particular frame arguments are "frameLocator", sheetName name
	 * for switch to content frame arguments are "contentFrame" and ScreenId ex
	 * "OPR016"
	 * 
	 * @param frameName
	 *            as var args
	 */
	public void switchToFrame(String... frameName) {
		wait = new WebDriverWait(driver, 60);
		//waitForSync(2);
		String fName="";
		try {
			
			if (frameName[0].equalsIgnoreCase("frameLocator")) {
				fName = xls_Read
						.getCellValue(frameName[1], "frame_Screen;name");
				wait.until(ExpectedConditions
						.frameToBeAvailableAndSwitchToIt(fName));
			} else if (frameName[0].equalsIgnoreCase("default"))
				driver.switchTo().defaultContent();
			else if (frameName[0].equalsIgnoreCase("contentFrame")) {
				fName = "iCargoContentFrame" + frameName[1];
				wait.until(ExpectedConditions
						.frameToBeAvailableAndSwitchToIt(fName));
			} else if (frameName[0].equalsIgnoreCase("frameName")) {
				fName = frameName[1];
				wait.until(ExpectedConditions
						.frameToBeAvailableAndSwitchToIt(fName));
			}

			else if (frameName[0].equalsIgnoreCase("toFrame")) {
				System.out.println("enttrehm");

				fName = xls_Read.getCellValue(frameName[1].split("~")[0],
						frameName[1].split("~")[1]);

				wait.until(ExpectedConditions
						.frameToBeAvailableAndSwitchToIt(fName));
			}

		} catch (Exception e) {
			System.out.println("Could not Switch To Frame " + fName);
			writeExtent("Fail","Could not Switch To Frame " + fName);
			
			Assert.assertFalse(true, "Could not Switch To Frame "
					+ fName);
		}

	}

	/**
	 * Description... Clicks errorMsg image flow
	 * 
	 * @param sheetName
	 *            Element Xpath sheetname
	 * @param locator
	 *            Element name in Xpath Sheet
	 * @throws InterruptedException
	 * @throws IOException 
	 * @throws Exception
	 */

	public void clickErrorMsg(String screenName) throws InterruptedException, IOException {
		clickWebElement("Generic_Elements", "img_errorMsg;xpath",
				"ErrorMessage", screenName);
	}

	/*
	 * Author : A-8468 Date Modified : 26/1/2019 Purpose : Common Method to
	 * Verify Element enabled in UI page or not
	 */
	public boolean verifyElementEnabled(String sheetName, String locator,
			String testSteps, String screenName, String

			eleName) throws InterruptedException {

		By b = getElement(sheetName, locator);
		if (driver.findElement(b).isEnabled()) {
			customFunction
					.onPassUpdate(screenName, eleName + " is Enabled", eleName
							+ " is Enabled", eleName + " is Enabled", testSteps);
			return true;

		} else {
			Status = false;
			customFunction.onFailUpdate(screenName, eleName + " is Enabled",
					eleName + " is Not Enabled", eleName + " is Enabled",
					testSteps);
			return false;
		}

	}

	/**
	 * Description... Checks whether an element is not enabled and logs the
	 * result in custom report
	 * 
	 * @param sheetName
	 * @param locator
	 * @param testSteps
	 * @param screenName
	 * @param eleName
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-8468 Date Modified : 26/1/2019 Purpose : Common Method to
	 * Verify Element enabled in UI page or not
	 */
	public void verifyElementNotEnabled(String sheetName, String locator,
			String testSteps, String screenName, String eleName)
			throws InterruptedException {
		By b = getElement(sheetName, locator);
		try {
			driver.findElement(b).isEnabled();
			Status = false;
			customFunction.onFailUpdate(screenName, eleName + " is Displayed",
					eleName + " is Not Displayed", eleName + " is Displayed",
					testSteps);

		} catch (Exception e) {

			customFunction.onPassUpdate(screenName, eleName
					+ " is Not Displayed", eleName + " is Not Displayed",
					eleName + " is Not Displayed", testSteps);
		}
	}

	public enum alertOps {
		Accept, Dismiss, GetText, CompareText
	}

	/**
	 * Description... Accepts/Dismiss or return Alert Text
	 * 
	 * @param AlertOperations
	 *            Accept/Dismiss/GetText
	 * @param ScreenName
	 *            ScreenName from application
	 * @return Alert Text
	 * @throws IOException
	 */
	// handle javascript alerts and perform operations as Accept, Dismiss,Get
	// Text and store in property file
	public String switchToAlert(String alertOperations, String ScreenName)
			throws IOException {
		alertText = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.alertIsPresent());
			if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {

				Alert alert = driver.switchTo().alert();
				alertText = alert.getText();
				switch (alertOps.valueOf(alertOperations)) {
				case Accept:
					alertText = alert.getText();
					alert.accept();
					writeExtent("Pass", "Accepted Alert with text " + alertText
							+ " on " + ScreenName + " Screen");
					return null;
				case Dismiss:
					alertText = alert.getText();
					alert.dismiss();
					writeExtent("Pass", "Dismissed Alert with text "
							+ alertText + " on " + ScreenName + " Screen");
					return null;

				case GetText:
					alertText = alert.getText();
					alert.accept();
					writeExtent("Pass", "Alert Text is " + alertText + " On "
							+ ScreenName + " Screen");
					return alertText;

				}
			}
		} catch (Exception e) {

			writeExtent("Info", "No Alert Found on " + ScreenName + " Screen");

		}
		return alertText;

	}

	/*
	 * Author : A-7271 Date Modified : 11/8/2017 Purpose : Enters Value in a
	 * WebElement, takes Xpath SheetName, Locator and element name as argument.
	 * Xpath must end with "_LocatorName"
	 */
	public enum keyValues {
		TAB, ENTER, DELETE, DOWN
	}

	public void performKeyActions(String sheetName, String locator, String key,
			String eleName, String ScreenName) throws InterruptedException {
		try {
			By element = getElement(sheetName, locator);

			waitForSync(1);

			switch (keyValues.valueOf(key)) {

			case TAB:
				driver.findElement(element).sendKeys(Keys.TAB);
				break;

			case ENTER:
				driver.findElement(element).sendKeys(Keys.ENTER);
				break;
			case DELETE:
				driver.findElement(element).sendKeys(Keys.DELETE);
				break;
			case DOWN:
				driver.findElement(element).sendKeys(Keys.DOWN);
				break;

			}
			writeExtent("Pass", "Performed the key action " + key);

		} catch (Exception e) {

			writeExtent("Fail", "Could not perform the key action " + key);
			Assert.assertFalse(true, "Could not perform the key action " + key);
		}

	}

	public void performKeyActions(WebElement element, String key,
			String eleName, String ScreenName) throws InterruptedException {
		try {

			
			

			switch (keyValues.valueOf(key)) {

			case TAB:
				element.sendKeys(Keys.TAB);
				break;

			case ENTER:
				element.sendKeys(Keys.ENTER);
				break;
			case DELETE:
				element.sendKeys(Keys.DELETE);
				break;
			case DOWN:
				element.sendKeys(Keys.DOWN);
				break;

			}
			writeExtent("Pass", "Performed the key action " + key);

		} catch (Exception e) {

			writeExtent("Fail", "Could not perform the key action " + key);
			Assert.assertFalse(true, "Could not perform the key action " + key);
		}

	}

	/**
	 * Description... Selects any option from the dropdown
	 * 
	 * @param sheetName
	 *            xpath sheetName
	 * @param locator
	 *            locator in xpath sheet
	 * @param option
	 *            option to be selected in dropdown
	 * @param eleName
	 *            element name for reporting
	 * @param selectBy
	 *            value/index/visible text
	 * @param index
	 *            index as integer 0/1/2...
	 */
	// select the option in a dropdown if it is not selected
	public void selectValueInDropdown(String sheetName, String locator,
			String option, String eleName, String selectBy) {
		try {
			By ele = getElement(sheetName, locator);
			WebElement ele1 = driver.findElement(ele);
			moveScrollBar(ele1);
			Select select = new Select(ele1);

			switch (selectBy) {
			case "Value": {
				String actopt = select.getFirstSelectedOption().getText();
				if (!actopt.equalsIgnoreCase(option))
					select.selectByValue(option);

			}
				break;
			case "VisibleText": {
				String actopt = select.getFirstSelectedOption().getText();
				if (!actopt.equalsIgnoreCase(option))
					select.selectByVisibleText(option);

			}
				break;
			case "Index": {
				int index = Integer.parseInt(option);
				String actopt = select.getFirstSelectedOption().getText();
				if (!actopt.equalsIgnoreCase(option))
					select.selectByIndex(index);

			}
				break;

			}
			writeExtent("Pass", "Entered " + option + " as " + eleName + " on "
					+ sheetName.split("_")[0] + " Screen");
			System.out.println("Entered " + option + " as " + eleName + " on "
					+ sheetName.split("_")[0] + " Screen");

		} catch (Exception e) {
			/*
			 * System.out.println( "Could not enter " + option + " as " +
			 * eleName + " on " + sheetName.split("_")[0] + " Screen");
			 */
			e.printStackTrace();
			writeExtent("Fail", "Could not enter " + " as " + eleName + " on "
					+ sheetName.split("_")[0] + " Screen");
			Assert.assertFalse(true, "Could not enter " + " as " + eleName
					+ " on " + sheetName.split("_")[0] + " Screen");

		}
	}

	/**
	 * Description... selectOptionInList -> for Tailboard dropdown
	 * @throws IOException 
	 * 
	 * 
	 * 
	 */

	public void selectOptionInList(String sheetName, String triXpath,
			String optionXpath, String option, String eleName)
			throws InterruptedException, IOException {

		clickWebElement(sheetName, triXpath, eleName, sheetName);
		String optionPath = xls_Read.getCellValue(sheetName, optionXpath)
				.replace("dynVariable", option);
		try {
			ele = driver.findElement(By.xpath(optionPath));
			ele.click();
			writeExtent("Pass", "Entered " + option + " as " + eleName + " on "
					+ sheetName + " Screen");
			System.out.println("Entered " + option + " as " + eleName + " on "
					+ sheetName + " Screen");
		} catch (Exception e) {
			e.printStackTrace();
			writeExtent("Fail", "Could not enter " + " as " + option + " on "
					+ sheetName + " Screen");
			Assert.assertFalse(true, "Could not enter " + " as " + option
					+ " on " + sheetName + " Screen");
		}
	}

	public void selectOptionInList(String sheetName, String triXpath,
			String optionXpath, String option, String

			eleName, String ScreenName) throws InterruptedException, IOException {
		// String ScreenName="Adhoc Notification Pop up eTracking";
		clickWebElement(sheetName, triXpath, eleName, ScreenName);
		String optionPath = xls_Read.getCellValue(sheetName, optionXpath)
				.replace("dynVariable", option);
		try {
			ele = driver.findElement(By.xpath(optionPath));
			ele.click();
			writeExtent("Pass", "Entered " + option + " as " + eleName + " on "
					+ sheetName + " Screen");
			System.out.println("Entered " + option + " as " + eleName + " on "
					+ sheetName + " Screen");
		} catch (Exception e) {
			e.printStackTrace();
			writeExtent("Fail", "Could not enter " + " as " + option + " on "
					+ sheetName + " Screen");
			Assert.assertFalse(true, "Could not enter " + " as " + option
					+ " on " + sheetName + " Screen");
		}
	}

	/**
	 * Description... Defines case options for switchToAlert
	 * 
	 * @author A-7688
	 * 
	 */

	/**
	 * Description... Sets Property value in the project path
	 * 
	 * @param Key
	 * @param Value
	 * @param s3
	 *            Relative path in the project
	 */
	/*
	 * Author : A-7688,A-7290,A-7626 Date Modified : 7/6/2017 Purpose : Set
	 * value for Key in any property file whose path is given as s3 under
	 * project folder
	 */
	public void setPropertyValue(String key, String value, String s3) {

		Properties prop = new Properties();
		String s2 = System.getProperty("user.dir");
		String path = s2 + s3;
		FileOutputStream output;
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(path);
			prop.load(fileIn);
			output = new FileOutputStream(path);
			prop.setProperty(key, value);
			prop.store(output, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * * Description... Gets the property value for the key from the property
	 * file
	 * 
	 * @param s3
	 * @param Key
	 * @return Value of the key
	 */
	/*
	 * Author : A-7688,A-7290,A-7626 Date Modified : 7/6/2017 Purpose :Takes
	 * value for Key from the file path in S3 and returns it as a string
	 */
	public static String getPropertyValue(String s3, String Key) {
		Properties prop = new Properties();
		String s2 = System.getProperty("user.dir");
		String path = s2 + s3;
		try {
			prop.load(new FileInputStream(path));
		} catch (Exception e) {

		}
		String value = prop.getProperty(Key);
		return value;
	}

	/**
	 * * Description... Explicitly waits for a WebElement for the wait time
	 * mentioned in GlobalVariable.properties
	 * 
	 * @param locator
	 */
	/*
	 * Author : A-7290 Date Modified : 7/6/2017 Purpose :Waits till the element
	 * is visible on page (Timeout is given in GlobalVariable.properties file as
	 * "waitTime") takes Locator as Argument
	 */

	public void waitTillOverlayDisappear(By locator) {
		int i = 0;

		String waitTime = Excel.getPropertyValue("waitTime");
		int waitint = Integer.parseInt(waitTime);
		while (i < waitint) {
			try {

				WebElement element = driver.findElement(locator);
				if (element != null) {

					System.out.println("waited for " + i);
					break;
				}
				waitForSync(1);
				i++;

			} catch (Exception e) {

				waitForSync(1);
				i++;
				System.out.println("waited for " + i);
			}
		}
	}

	/**
	 * Description... Explicitly waits for a WebElement for the wait time
	 * mentioned in GlobalVariable.properties
	 * 
	 * @param ele
	 *            WebElement
	 */
	/*
	 * Author : A-7688 Date Modified : 7/6/2017 Purpose : Waits till the element
	 * is visible on page (Timeout is given in GlobalVariable.properties file as
	 * "waitTime") takes WebElement as Argument
	 */

	public void waitTillOverlayDisappear(WebElement ele) {
		try {
			String waitTime = Excel.getPropertyValue("waitTime");
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(waitTime));
			wait.until(ExpectedConditions.elementToBeClickable(ele));

		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Description...Called by getRandomString for Generating random string of
	 * length 10
	 * 
	 * @return
	 */
	

	public int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;

		}

	}

	/**
	 * Description...Generates a random String of length 10
	 * 
	 * @return
	 */
	/*
	 * Author : Raghothma Date Modified : 7/6/2017 Purpose : returns random
	 * string of length returned by getRandomNumber
	 */

	public String getRandomString() {
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOMSTRINGLENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();

	}

	/*
	 * Description... Selects the check box in a table in the required row where
	 * depending on the composite keys.
	 * 
	 * @author A-8705
	 */
	public void selectCheckBoxinTableRecord(String referenceVar,
			String sheetName, String locator, String locatorEle, int loopCount) {

		try {
			boolean flag = false;
			int row = 0;
			List<WebElement> rows = driver.findElements(By.xpath(xls_Read
					.getCellValue(sheetName, locator)));
			locatorEle = xls_Read.getCellValue(sheetName, locatorEle);

			{
				for (int i = 0; i <= rows.size(); i++) {
					System.out.println("i= " + i);

					if (rows.get(i)
							.getText()
							.toLowerCase()
							.replace(" ", "")
							.contains(
									referenceVar.toLowerCase().replace(" ", ""))) {

						flag = true;

					}

					if (flag) {
						row = i;
						break;
					}
				}
				int j = row;
				int checkBoxNum = j + 1;
				String dynXpath = locatorEle + "[" + checkBoxNum + "]";
				try {
					new Robot().mouseWheel(2);
					waitForSync(1);
					driver.findElement(By.xpath(dynXpath)).click();
					waitForSync(5);
				} catch (ElementNotVisibleException e) {
					waitForSync(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Description... Selects the check box in a table in the required row where
	 * depending on the primary key.
	 * 
	 * @param referenceVar
	 * @param locator
	 * @param sheetName
	 * @param loopCount
	 */

	public void selectTableRec(String referenceVar, String sheetName,
			String locator, String locatorEle, int loopCount) {

		try {
			boolean flag = false;
			int row = 0;
			List<WebElement> rows = driver.findElements(By.xpath(xls_Read
					.getCellValue(sheetName, locator)));
			locatorEle = xls_Read.getCellValue(sheetName, locatorEle);

			{
				for (int i = 0; i <= rows.size(); i++) {
					System.out.println("i= " + i);

					if (rows.get(i)
							.getText()
							.toLowerCase()
							.replace(" ", "")
							.contains(
									referenceVar.toLowerCase().replace(" ", ""))) {

						flag = true;

					}

					if (flag) {
						row = i;
						break;
					}
				}

				for (int i = 0; i < loopCount; i++) {
					String dynXpath = "(" + locatorEle + ")[" + row + "]";
					try {
						new Robot().mouseWheel(2);
						waitForSync(1);
						driver.findElement(By.xpath(dynXpath)).click();
						waitForSync(1);
						if (!driver.findElement(By.xpath(dynXpath))
								.isSelected())
							driver.findElement(By.xpath(dynXpath)).click();
						break;
					}

					catch (ElementNotVisibleException e) {
						new Robot().mouseWheel(2);
						System.out.println("found at " + (i + 1) + "times");
						waitForSync(1);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Description... Closes all instances opened by Selenium
	 */
	/*
	 * Author : A-7626 Date Modified : 7/6/2017 Purpose :Close all open browser
	 * instances opened by Selenium and end the session
	 */
	public void quitBrowser() {
		try {
			driver.quit();
		} catch (Exception e) {
			driver.quit();

		}
	}
	
	/**
	 * @author A-7271
	 * Description : quit app
	 */
	public void quitApp() {
		try {
			customFunction.logoutApp();
			waitForSync(10);

			androiddriver.quit();
		} catch (Exception e) {
			androiddriver.quit();

		}
	}
	/**
	 * @author A-7271
	 * Description : Close browser
	 */
	public void closeBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
			driver.close();

		}
	}
	
	/**
	 * @author A-7271
	 * @param screenId
	 * @return
	 * Desc : get station name of loggedin station
	 */
	public String getLoggedInStation(String screenId)
	{
		try
		{
		driver.switchTo().defaultContent();
		//String station=driver.findElement(By.xpath("(//span[contains(.,'At')])[2]//b")).getText();
		//String station=driver.findElement(By.xpath("(//span[contains(.,'At')])//b")).getText();
		String station=driver.findElement(By.xpath("//b[@id='ic-user-stationcode']")).getText();
		return station;
		}
		
		catch(Exception e)
		{
			return "";
		}
		finally
		{
			String frameName = "iCargoContentFrame" + screenId;
			driver.switchTo().frame(frameName);
		}
		
		
	}
	/**
	 * Description... Clicks links, button, radio button, check box
	 * 
	 * @param sheetName
	 *            Xpath Sheetname
	 * @param locator
	 *            Xpath Locator name
	 * @param eleName
	 *            used for reporting purpose. example OK Button
	 * @param ScreenName
	 *            used for reporting purpose. example Login Page
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	// click a webelement link, check box, button, radio button
	public void clickWebElement(String sheetName, String locator,
			String eleName, String ScreenName) throws InterruptedException, IOException {

		String browserName = DriverSetup.browser;

		Actions actionDriver = new Actions(driver);
		By element = getElement(sheetName, locator);
		WebElement elementtobeClicked = driver.findElement(element);
		actionDriver.moveToElement(elementtobeClicked).perform();

		if (browserName.equals("firefox")) {

			try {

				clickWebElementByWebDriver(sheetName, locator, eleName,
						ScreenName);
				waitForSync(3);
			}

			catch (Exception e) {
				System.out.println("Could not click on " + eleName + " On "
						+ ScreenName + " Page");
				writeExtent("Fail", "Could not click on " + eleName + " On "
						+ ScreenName + " Page");
				Assert.assertFalse(true, "Could not click on " + eleName
						+ " On " + ScreenName + " Page");
			}
		} else {
			try {

				javaScriptToclickElement(element, eleName, ScreenName);
			} catch (Exception e) {
				System.out.println("Could not click on " + eleName + " On "
						+ ScreenName + " Page");
				captureScreenShot("Web");
				writeExtent("Fail", "Could not click on " + eleName + " On "
						+ ScreenName + " Page");
				Assert.assertFalse(true, "Could not click on " + eleName
						+ " On " + ScreenName + " Page");
			}
		}
	}

	public By getElement(String sheetName, String object) {

		try {

			By element = null;
			String locatorType = null;
			String locatorName = null;

			locatorType = object.split(";")[1].toString();
			locatorName = xls_Read.getCellValue(sheetName, object);

			String locator = xls_Read.getCellValue(sheetName, object,
					"Locators");

			System.out.println("locatorType" + locatorType);
			System.out.println("locatorName" + locatorName);
			System.out.println("locatorName" + locator);
			// Finding the element

			switch (locator) {
			case "xpath":
				element = By.xpath(locatorName);
				break;
			case "name":
				element = By.name(locatorName);
				break;
			case "id":
				element = By.id(locatorName);
				break;
			case "linkText":
				element = By.linkText(locatorName);
				break;
			case "partialLinkText":
				element = By.partialLinkText(locatorName);
				break;
			case "tagname":
				element = By.tagName(locatorName);
				break;
			case "cssSelector":
				element = By.cssSelector(locatorName);
				break;
			case "className":
				element = By.className(locatorName);
				break;

			}
			/***** waitTillOverlayDisappear(element, driver); ***/

			waitTillOverlayDisappear(element);
			return element;
		}

		catch (Exception e) {
			return null;
		}

	}
	/**
	 * 
	 * @param sheetName
	 * @param object
	 * @return
	 */
	public By getElement(String sheetName, String object,boolean wait) {

		try {

			By element = null;
			String locatorType = null;
			String locatorName = null;

			locatorType = object.split(";")[1].toString();
			locatorName = xls_Read.getCellValue(sheetName, object);

			String locator = xls_Read.getCellValue(sheetName, object,
					"Locators");

			System.out.println("locatorType" + locatorType);
			System.out.println("locatorName" + locatorName);
			System.out.println("locatorName" + locator);
			// Finding the element

			switch (locator) {
			case "xpath":
				element = By.xpath(locatorName);
				break;
			case "name":
				element = By.name(locatorName);
				break;
			case "id":
				element = By.id(locatorName);
				break;
			case "linkText":
				element = By.linkText(locatorName);
				break;
			case "partialLinkText":
				element = By.partialLinkText(locatorName);
				break;
			case "tagname":
				element = By.tagName(locatorName);
				break;
			case "cssSelector":
				element = By.cssSelector(locatorName);
				break;
			case "className":
				element = By.className(locatorName);
				break;

			}
			if(wait)
			waitTillOverlayDisappear(element);
			
		    
			return element;
		}

		catch (Exception e) {
			return null;
		}

	}
	/**
	 * Description...Multiplies SyncTime from GlobalVariable.properties to the
	 * number of seconds sent as argument
	 * 
	 * @param i
	 *            seconds to wait
	 */
	// wait for sync
	public void waitForSync(int i) {
		try {
			String path = customFunction.proppath;
			int syncTime = Integer.parseInt(getPropertyValue(path, "SyncTime"));
			int j = i * 1000 * syncTime;
			Thread.sleep(j);
			System.out.println("Waited for " + (i * syncTime) + " seconds...");
		} catch (Exception e) {

		}
	}
	/**
	 * @author A-7271
	 * @return
	 * Description : Return the number of windows
	 */
public int getWindowSize()

{
	try{
		
		int windowSize=driver.getWindowHandles().size();
		return windowSize;
		
	}
	
	catch(Exception e)
	{
		return 0;
	}
}
/*
 * @author A-7271
 * @param window
 * @throws Exception
 * Desc : switch to a specified window
 */
public void switchToSpecifiedWindow(String window) throws Exception {
	
	
	try
	{
		driver.switchTo().window(window);
		waitForSync(2);
	}
	
	catch(Exception e)
	
	{
		
	}
}
public static ExpectedCondition<Boolean> waitForAjaxCalls() {
    return new ExpectedCondition<Boolean>() {
        @Override
        public Boolean apply(WebDriver driver) {
            return Boolean.valueOf(((JavascriptExecutor) driver).executeScript("return (window.angular !== undefined) && (angular.element(document).injector() !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)").toString());
        }
    };
}
/**
 * @author A-7271
 * @param numberOfWindows
 * Desc : explicit conditions to load the window
 */

public void waitTillChildWindowLoad(int numberOfWindows)
{
	try
	{
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
		int winSize=driver.getWindowHandles().size();
		System.out.println(winSize);

		Set<String>windows=driver.getWindowHandles();
		Iterator<String> itr= windows.iterator();
		String parentWindow=itr.next();
		String childWindow=itr.next();
		driver.switchTo().window(childWindow);
       	waitForSync(2);
    	waitTillScreenloadWithOutAssertion("Generic_Elements", "htmlSpan_homePage;xpath",
				"HomePage", "HomePage");
    	
    	
    	
		/****driver.switchTo().frame("iCargoContentFrame");
		waitTillScreenloadWithOutAssertion("Generic_Elements", "htmlDiv_homePageDiv;xpath",
				"HomePage", "HomePage");
		System.out.println("camee");
		driver.switchTo().defaultContent();***/

	}

	catch(Exception e)
	{
		writeExtent("Fail","Failed to load the icargo home page with in expected time");
	}
}
	/**
	 * Description...switch to parent/child window. Or stores the window
	 * depending on the argumnet passed
	 * 
	 * @param storeParent
	 *            /child/getParent
	 */
	// stores/switch to a window depending on the argument passed
	public void switchToWindow(String window) throws Exception {
	
		try
		{
		waitForSync(2);
		Set<String> winHandle = null;
		if (window.equals("storeParent")) {
			String doubleWindow = driver.getWindowHandle();
			parentWindow = doubleWindow;
		} 
		else if (window.contains("storeParent;")) {
			String doubleWindow = driver.getWindowHandle();
			map.put(window.split(";")[1], doubleWindow);
			
		}else if (window.equals("multipleWindows")) {
			waitForSync(8);
			for (String handle : driver.getWindowHandles()) {
			

				driver.switchTo().window(handle);
			}
			
			
		} else if (window.equals("child")) {

			for (int i = 0; i < 60; i++) {
				winHandle = driver.getWindowHandles();
				if (winHandle.size() == 2)
					break;
				else
					waitForSync(1);

			}
			winHandle.remove(parentWindow);
			String winHandleNew = winHandle.toString();
			String winHandleFinal = winHandleNew.replaceAll("\\[", "")
					.replaceAll("\\]", "");
			driver.switchTo().window(winHandleFinal);
			// customFunction.clickCertificateError();

		} else if (window.equals("child_BE")) {

			Set<String> windowhandle = driver.getWindowHandles();

			if (windowhandle.size() > 1) {
				Iterator it = windowhandle.iterator();
				String parentWindow = (String) it.next();
				driver.switchTo().window(parentWindow).close();
				String childWindow = (String) it.next();

				driver.switchTo().window(childWindow);
			} else {
				Iterator it = windowhandle.iterator();
				String parentWindow = (String) it.next();
				driver.switchTo().window(parentWindow);
				waitForSync(2);
			}

		} else if (window.equals("getParent"))
			driver.switchTo().window(parentWindow);

		else if (window.equals("closeParent"))
			driver.switchTo().window(parentWindow).close();
		

		else if (window.equals("closeChild")) {
			String doubleWindow = driver.getWindowHandle();
			childWindow = doubleWindow;
			System.out.println("childWindow is---" + childWindow);
			driver.switchTo().window(childWindow).close();
		} else if (window.equals("childWindow")) {
			waitForSync(8);

			System.out.println(driver.getWindowHandles().size());

			if (!window.equals(parentWindow)) {
				for (String handle : driver.getWindowHandles()) {

					driver.switchTo().window(handle);
					firstChildWindow = handle;

				}
			}
		}
			else if (window.equals("switchToChildWindow")) {
				waitForSync(2);

				
					for (String handle : driver.getWindowHandles()) {
						
						
					if(!handle.equals(parentWindow))
					{
						driver.switchTo().window(handle);
					}

					
				}
		} else if (window.equals("childWindow2")) {
			waitForSync(8);

			System.out.println(driver.getWindowHandles().size());

			if (!window.equals(parentWindow)) {
				if (!window.equals(firstChildWindow)) {
					for (String handle : driver.getWindowHandles()) {

						driver.switchTo().window(handle);

					}

				}
			}
			
		} else if (window.equals("storeFirstChild")) {
			String tripleWin = driver.getWindowHandle();
			firstChildWindow = tripleWin;
		} else if (window.equals("getFirstChild")) {
			driver.switchTo().window(firstChildWindow);
		} else if (window.equals("secondChild")) {
			Set<String> allWindows = driver.getWindowHandles();
			Iterator<String> windows = allWindows.iterator();
			String Parent = windows.next();
			String child1 = windows.next();
			String child2 = windows.next();
			System.out.println("childWindow is---" + child2);
			driver.switchTo().window(child2);
		}
		}
		
		catch(Exception e)
		{
			writeExtent("Fail","Failed to switch to the window");
		}
		}

	/**
	 * @author A-9175
	 * @param numberOfWindows
	 * @Desc : waitTillExpectedChildWindowLoad
	 */
	public void waitTillExpectedChildWindowLoad(int numberOfWindows)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
			int winSize=driver.getWindowHandles().size();
			System.out.println(winSize);
			if(winSize==numberOfWindows)
			{
				writeExtent("Pass","Successfully Verified Child Window");
			}
		}
		catch(Exception e)
		{	
			writeExtent("Fail","Failed to verify expected child windows with in expected time");
		}
	}


	/**
	 * Description... Clicks the element if its available else doesn't break the
	 * flow
	 * 
	 * @param sheetName
	 *            Element Xpath sheetname
	 * @param locator
	 *            Element name in Xpath Sheet
	 * @throws Exception
	 */
	// click the element if it is displayed, doesn't through an exception
	public void clickIfDisplayed(String sheetName, String locator)
			throws Exception {
		try {

			driver.findElement(
					By.xpath(xls_Read.getCellValue(sheetName, locator)))
					.click();

		} catch (Exception e) {

			System.out.println("Not clicked on the object with locator "
					+ locator + " in sheet " + sheetName);

		}
	}

	/**
	 * Description... enter text in a text box/ text area
	 * 
	 * @param sheetName
	 *            Xpath Sheetname
	 * @param locator
	 *            Xpath Locator name
	 * @param eleName
	 *            used for reporting purpose. example OK Button
	 * @param ScreenName
	 *            used for reporting purpose. example Login Page
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7688 Date Modified : 11/8/2017 Purpose : Enters Value in a
	 * WebElement, takes Xpath SheetName, Locator and element name as argument.
	 * Xpath must end with "_LocatorName"
	 */
	public void enterValueInTextbox(String sheetName, String locator,
			String value, String eleName, String ScreenName)
			throws InterruptedException {
		try {
			// Actions actionDriver = new Actions(driver);
			By element = getElement(sheetName, locator);
			WebElement elementInFocus = driver.findElement(element);
			// actionDriver.moveToElement(elementInFocus).perform();
			moveScrollBar(elementInFocus);

			driver.findElement(element).click();
			driver.findElement(element).clear();
			waitForSync(1);
			driver.findElement(element).sendKeys(value);
			
			if(eleName.equalsIgnoreCase("Password"))
			{
				value="******";
			}
			
			writeExtent("Pass", "Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");
			
			System.out.println("Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");

		} catch (Exception e) {
			if(eleName.equalsIgnoreCase("Password"))
			{
				value="******";
			}
			System.out.println("Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");
			
			writeExtent("Fail", "Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not enter " + value + " as "
					+ eleName + " on " + ScreenName + " Page");
		}

	}
	/**
	 * @author A-9847
	 * @Desc To enter value in TextBox without scroll
	 * @param sheetName
	 * @param locator
	 * @param value
	 * @param eleName
	 * @param ScreenName
	 * @throws InterruptedException
	 */
	public void enterValueInTextboxWithoutScroll(String sheetName, String locator,
			String value, String eleName, String ScreenName)
			throws InterruptedException {
		try {
			
			By element = getElement(sheetName, locator);

			driver.findElement(element).click();
			driver.findElement(element).clear();
			waitForSync(1);
			driver.findElement(element).sendKeys(value);
			
			if(eleName.equalsIgnoreCase("Password"))
			{
				value="******";
			}
			
			writeExtent("Pass", "Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");
			
			System.out.println("Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");

		} catch (Exception e) {
			if(eleName.equalsIgnoreCase("Password"))
			{
				value="******";
			}
			System.out.println("Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");
			
			writeExtent("Fail", "Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not enter " + value + " as "
					+ eleName + " on " + ScreenName + " Page");
		}

	}




	/**@author A-10328
	* Description - Enter text without clear 
	* @param sheetName
	* @param locator
* @param value
* @param eleName
* @param ScreenName
* @throws InterruptedException
*/

public void enterTextWithoutClear(String sheetName, String locator,
String value, String eleName, String ScreenName)
throws InterruptedException 
{
	try 

	{

	By element = getElement(sheetName, locator);
	WebElement elementInFocus = driver.findElement(element);
	moveScrollBar(elementInFocus);

	driver.findElement(element).click();
	waitForSync(1);
	driver.findElement(element).sendKeys(value);

	if(eleName.equalsIgnoreCase("Password"))

	{
	value="******";

	}
	writeExtent("Pass", "Entered " + value + " as " + eleName + " on "
			+ ScreenName + " Page");

			System.out.println("Entered " + value + " as " + eleName + " on "
			+ ScreenName + " Page");

			}


			 catch (Exception e) 
	{
				 if(eleName.equalsIgnoreCase("Password"))
				 {
				 value="******";
				 }
				 System.out.println("Could not enter " + value + " as " + eleName
				 + " on " + ScreenName + " Page");

				 writeExtent("Fail", "Could not enter " + value + " as " + eleName
						 + " on " + ScreenName + " Page");
						 Assert.assertFalse(true, "Could not enter " + value + " as "
						 + eleName + " on " + ScreenName + " Page");



						 }
}
	/**
	 * @author A-7271
	 * @param sheetName
	 * @param locator
	 * @param value
	 * @param eleName
	 * @param ScreenName
	 * @throws InterruptedException
	 * Desc : set value in text box
	 */
	public void setValueInTextbox(String sheetName, String locator,
			String value, String eleName, String ScreenName)
			throws InterruptedException {
		try {
			
			By element = getElement(sheetName, locator);
			WebElement elementInFocus = driver.findElement(element);
			
			moveScrollBar(elementInFocus);

			driver.findElement(element).click();
			driver.findElement(element).clear();
		
			/****** COPY AND PASTE THE VALUE IN THE TEXT FIELD***/
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection stringSelection = new StringSelection(value);
		    clipboard.setContents(stringSelection, null);
		    elementInFocus.sendKeys(Keys.SHIFT, Keys.INSERT);
		   
		    
		   if(elementInFocus.getAttribute("value").equals(""))
		    {
		    	waitForSync(2);
		    	Robot r=new Robot();
		    	r.keyPress(KeyEvent.VK_CONTROL);
		    	r.keyPress(KeyEvent.VK_V);
		    	r.keyRelease(KeyEvent.VK_CONTROL);
		    	r.keyRelease(KeyEvent.VK_V);
			   
			 /****  Actions builder=new Actions(driver);
			   
			   Action seriesOfActions=builder.moveToElement(elementInFocus).click()
					                  .keyDown(elementInFocus,Keys.CONTROL)
					                 .sendKeys(elementInFocus, "v")
					                 .keyUp(elementInFocus, Keys.CONTROL).build();
			   seriesOfActions.perform();***/
			 
		    }
		  
			writeExtent("Pass", "Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");
			System.out.println("Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");

		} catch (Exception e) {
			System.out.println("Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");
			writeExtent("Fail", "Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not enter " + value + " as "
					+ eleName + " on " + ScreenName + " Page");
		}

	}
	/**
	 * @author A-7271
	 * @param sheetName
	 * @param locator
	 * @param value
	 * @param eleName
	 * @param ScreenName
	 * @throws InterruptedException
	 * Description : Enter the values in textbox by JS
	 */
	public void enterValueInTextboxByJS(String sheetName, String locator,
			String value, String eleName, String ScreenName)
			throws InterruptedException {
		try {
			// Actions actionDriver = new Actions(driver);
			By element = getElement(sheetName, locator);
			WebElement txtValue = driver. findElement(element);
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].value='"+value+"'", txtValue);
			
			writeExtent("Pass", "Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");
			System.out.println("Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");

		} catch (Exception e) {
			System.out.println("Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");
			writeExtent("Fail", "Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not enter " + value + " as "
					+ eleName + " on " + ScreenName + " Page");
		}

	}

	/**
	 * A-7271 Run Security Warning using Auto it
	 * 
	 * @throws IOException
	 */
	public void runSecurityWarning() throws IOException {
		Runtime.getRuntime().exec(autoItPath);
	}

	/**
	 * Description... Compares 2 boolean values and log the result in the
	 * report.
	 * 
	 * @param expValue
	 * @param actValue
	 * @param testSteps
	 * @param pageName
	 * @param ValueName
	 */
	/*
	 * Author : A-7688 Date Modified : 11/8/2017 Purpose : Verifies a text on a
	 * page . Argument needs to be sent from the Calling method are expValue,
	 * actValue, testSteps, pageName, ValueName
	 */

	public void verifyValueOnPage(boolean expValue, boolean actValue,
			String testSteps, String pageName, String ValueName) {

		if (actValue == expValue) {

			counter = counter + 1;
			excelreadwrite.insertData(DriverSetup.testName,

			commonUtility.getcurrentDateTime() + "_" + String.valueOf(counter),
					"Verify the Value " + ValueName + " On " + pageName
							+ " Page ", "Expected Value is : " + expValue
							+ "\nActual Value is : " + actValue, testSteps,
					true,

					"Yes", "Value " + ValueName + " on Page " + pageName
							+ " Sucessfully Verified ", "Value" + ValueName
							+ "On Page" + pageName + " Sucessfully Verified");
			writeExtent("Pass", "Value " + ValueName + " On " + pageName
					+ " Page Sucessfully Verified");

		} else {

			Status = false;
			counter = counter + 1;
			excelreadwrite.insertFailedData(
					DriverSetup.testName,
					commonUtility.getcurrentDateTime() + "_"
							+ String.valueOf(counter), "Verify the Value "
							+ ValueName + " On " + pageName + " page ",
					"Expected Value is : " + expValue + " \nActual Value is : "
							+ actValue, testSteps,

					false, "", "Failed to Verify " + ValueName + " On "
							+ pageName + " Page ", "Value On " + pageName
							+ " Page Sucessfully Verified");

			writeExtent("Fail", "Failed to Verify " + ValueName + " On "
					+ pageName + " Page ");

		}

	}
	
	/**
	 * @author A-7271
	 * @param value
	 * Desc : Verify data from list of web elemnets
	 */
	public void verifyDataFromListOfWebElements(String expValue,String locatorValue,String elemnt,String sheetName,String screenName)
	{
		try
		{
		boolean verifyData=false;
		String locator=xls_Read.getCellValue(sheetName, locatorValue);
	     List <WebElement> actEle=driver.findElements(By.xpath(locator));
	     
	     for(WebElement ele:actEle )
	     {
	    	 if(ele.getText().contains(expValue))
	    	 {
	    		 writeExtent("Pass", "Verified " + expValue +" as "+elemnt+ " On "
	 					+ screenName); 
	    		 verifyData=true;
	    		 break;
	    	 }
	     }
	     
	     if(!verifyData)
	     writeExtent("Fail", "Failed to verify " + expValue +" as "+elemnt+ " On "
					+ screenName+" Page "); 
		}
		
		catch(Exception e)
		{
			 writeExtent("Fail", "Failed to verify " + expValue +" as "+elemnt+ " On "
						+ screenName+" Page "); 
		}
		
		
	}

	/*
	 * Author : A-7688 Date Modified : 11/8/2017 Purpose : Verifies a text on a
	 * page . Argument needs to be sent from the Calling method are expValue,
	 * actValue, testSteps, pageName, functinalityName
	 */
	public void verifyValueOnPageContains(String actValue, String expValue,
			String testSteps, String screenName, String functinalityName)
			throws InterruptedException, IOException {
		waitForSync(2);
		System.out.println("expected is : " + expValue + "\nactual is : "
				+ actValue);
		if (actValue.contains(expValue)) {
			customFunction.onPassUpdate(screenName, expValue, actValue,
					functinalityName, testSteps);

		} else {
			Status = false;
			captureScreenShot("Web");
			customFunction.onFailUpdate(screenName, expValue, actValue,
					functinalityName, testSteps);
		}
	}

	/**
	 * Description...Compares actValue and expValue using equals method and log
	 * the result
	 * 
	 * @param actValue
	 * @param expValue
	 * @param testSteps
	 * @param screenName
	 * @param functinalityName
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7688 Date Modified : 11/8/2017 Purpose : Verifies a text on a
	 * page . Argument needs to be sent from the Calling method are expValue,
	 * actValue, testSteps, pageName, functinalityName
	 */
	public void verifyValueOnPage(String actValue, String expValue,
			String testSteps, String screenName, String functinalityName)
			throws InterruptedException {
		waitForSync(2);
		System.out.println("expected is : " + expValue + "\nactual is : "
				+ actValue);
		if (actValue.equals(expValue)) {
			customFunction.onPassUpdate(screenName, expValue, actValue,
					functinalityName, testSteps);

		} else {
			Status = false;
			customFunction.onFailUpdate(screenName, expValue, actValue,
					functinalityName, testSteps);
		}
	}

	public void pageLoadTest() {

		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals

				("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(pageLoadCondition);
	}

	/**
	 * Description... Click an element using javascript. This method is
	 * overloaded.
	 * 
	 * @param sheetName
	 *            xpath sheetname
	 * @param xpath
	 *            element xpath name in xpath sheet
	 * @param elename
	 *            Element Name
	 * @param ScreenName
	 *            Screen Name
	 */
	// click an element using javascript
	public void javaScriptToclickElement(String sheetName, String locator,
			String elename, String ScreenName) {

		try {
			waitForSync(2);
			By b = getElement(sheetName, locator);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(b));
			wait.until(ExpectedConditions.elementToBeClickable(b));
			ele = driver.findElement(b);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele);
			writeExtent("Pass", "Clicked on " + elename + " On " + ScreenName
					+ " Page");
			System.out.println("Clicked on " + elename + " On " + ScreenName
					+ " Page");

		} catch (Exception e) {
			System.out.println("Could not click on element " + elename);
			writeExtent("Fail", "Could not click on " + elename + " On "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not click on " + elename + " On "
					+ ScreenName + " Page");
		}
	}

	/**
	 * Description... Click an element using javascript. This method is
	 * overloaded. Element has to be sent from the calling method
	 * 
	 * @param ele
	 * @param elename
	 * @param ScreenName
	 */
	// overloaded method to click an element using javascript
	public void javaScriptToclickElement(By ele, String elename,
			String ScreenName) {
		try {
			waitForLoad(driver);
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			WebElement element = driver.findElement(ele);
			waitTillOverlayDisappear(element);

			waitForSync(2);
			JavascriptExecutor executor = (JavascriptExecutor) driver;

			executor.executeScript("arguments[0].click();", element);
			writeExtent("Pass", "Clicked on " + elename + " On " + ScreenName
					+ " Page");
			System.out.println("Clicked on " + elename + " On " + ScreenName
					+ " Page");

		} catch (Exception e) {
			System.out.println("Could not click on element " + elename);
			writeExtent("Fail", "Could not click on " + elename + " On "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not click on " + elename + " On "
					+ ScreenName + " Page");
		}

	}

	public static void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(pageLoadCondition);
	}

	/**
	 * Description... Changes the date format from dd/MM/yy to dd-MMM-yyyy
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	// change the date format
	public String changeDateFormat(String date) throws ParseException,
			java.text.ParseException {
		String newDateString;
		SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date startDate;
		startDate = df.parse(date);
		newDateString = df1.format(startDate);
		return newDateString;

	}

	/**
	 * Description... This method is used to tell from which file and sheet Test
	 * data should be read. It is configured in test case file.
	 * 
	 * @param table
	 * @param sheet
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public String[] getTestDataFileDetails(String table, String sheet)
			throws IOException, InvalidFormatException {
		String tcFileName = System.getProperty("user.dir")
				+ commonUtility.getPropertiesConfigValue("testCaseFilePath");
		int startNm = startRowNum(tcFileName, table, sheet);

		String fileName = Excel.getCellValue(tcFileName, sheet, startNm, 1);
		String sheetName = Excel.getCellValue(tcFileName, sheet, startNm, 2);
		String TagName = Excel.getCellValue(tcFileName, sheet, startNm, 3);
		String[] fileDetails = { fileName, sheetName, TagName };
		return fileDetails;
	}

	/**
	 * Description... Used by buildMapFromXls method for getting start row
	 * number for a test case from the datasheet
	 * 
	 * @param tcFileName
	 *            Test case file name
	 * @param str
	 *            Test case name
	 * @param sheetName
	 *            Data sheet name
	 * @return start row number
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static int startRowNum(String tcFileName, String str,
			String sheetName) throws IOException, InvalidFormatException {
		String path = tcFileName;
		FileInputStream fileInputStream = new FileInputStream(path);
		Workbook wb = WorkbookFactory.create(fileInputStream);
		Sheet sheet = wb.getSheet(sheetName);
		Iterator<Row> rows = sheet.rowIterator();

		int rowIndex = -1;

		Row row = null;

		ArrayList<Integer> rowNum = new ArrayList<Integer>();

		String rowNums = "";
		while (rows.hasNext()) {
			row = rows.next();
			for (Cell cell : row) {

				if (cell.getRichStringCellValue().toString().equals(str)) {

					rowIndex = row.getRowNum();
					rowNum.add(rowIndex);
				}
			}

		}

		// extracting

		rowNums = rowNum.get(0).toString();

		int startrow = Integer.parseInt(rowNums);

		return startrow;

	}

	/**
	 * Description... commonly used for reading xpaths from the xpath sheetname
	 * 
	 * @param cell
	 *            the cell value as object
	 * @return
	 */
	private static Object getCellValue(Cell cell) {
		HSSFCell hssfCell = (HSSFCell) cell;
		if (XSSFCell.CELL_TYPE_NUMERIC == hssfCell.getCellType()) {
			DecimalFormat df = new DecimalFormat("#.");
			String numeric = df.format(cell.getNumericCellValue());
			numeric = numeric + "";
			String[] strArray = (numeric.replace(".", "-")).split("-");
			if (strArray.length > 1) {
				if ((strArray[1].replace("0", "")).trim().length() == 0) {
					numeric = strArray[0];
				}
			}
			return numeric;

		} else if (XSSFCell.CELL_TYPE_STRING == hssfCell.getCellType()) {
			return hssfCell.getStringCellValue();
		} else if (XSSFCell.CELL_TYPE_NUMERIC == hssfCell.getCellType()) {
			return hssfCell.getNumericCellValue();
		} else if (XSSFCell.CELL_TYPE_BOOLEAN == hssfCell.getCellType()) {
			return hssfCell.getBooleanCellValue();
		} else if (XSSFCell.CELL_TYPE_BLANK == hssfCell.getCellType()) {
			return "";
		}
		return "";
	}

	/**
	 * Description... Checks whether an element is displayed and logs the result
	 * in custom report
	 * 
	 * @param sheetName
	 * @param eleXpath
	 * @param testSteps
	 * @param screenName
	 * @param eleName
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7290 Date Modified : 18/8/2017 Purpose : Common Method to
	 * Verify Element displayed in UI page or not
	 */
	public boolean verifyElementDisplayed(String sheetName, String eleXpath,
			String testSteps, String screenName, String eleName)
			throws InterruptedException {

		By b = getElement(sheetName, eleXpath);
		if (driver.findElement(b).isDisplayed()) {
			customFunction.onPassUpdate(screenName, eleName,
					eleName, eleName,
					testSteps);
			return true;

		} else {
			Status = false;
			customFunction.onFailUpdate(screenName, eleName,
					eleName, eleName,
					testSteps);
			return false;
		}

	}

	/**
	 * Description... Collects the element text which is defined as innertext in
	 * HTML Tree
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param pageName
	 * @return Text of an element
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7688 Date Modified : 29/08/2017 Purpose : Generic method to
	 * get text from a webelement
	 */
	public String getElementText(String sheetName, String locator,
			String eleName, String pageName) throws InterruptedException {

		waitForSync(2);
		try {
			By b = getElement(sheetName, locator);
			String text = driver.findElement(b).getText();
			writeExtent("Pass", "Returned text of element " + eleName + " : "
					+ text);

			return text;
		} catch (Exception e) {
			System.out.println("Could not return text from element " + eleName
					+ " on " + pageName);
			writeExtent("Fail", "Could not return text from element " + eleName
					+ " on " + pageName);
			return "";
		}

	}

	/**
	 * Description... Collects the element text using Javascript
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param ScreenName
	 * @return Text of an element
	 */
	/*
	 * Author : A-7688 Date Modified : 29/08/2017 Purpose : Generic method to
	 * get text from a webelement using JavaScript
	 */
	public String getTextUsingJavascript(String sheetName, String locator,
			String eleName, String ScreenName) {
		String actValue = "";

		try {
			By b = getElement(sheetName, locator);
			ele = driver.findElement(b);
			actValue = (String) ((JavascriptExecutor) driver).executeScript(
					"return arguments[0].value;", ele);

			System.out.println("Returned text of element " + eleName + " : "
					+ actValue);
			writeExtent("Pass", "Returned text of element " + eleName + " : "
					+ actValue);
			return actValue;
		}

		catch (Exception e) {

			e.printStackTrace();
			System.out.println("Could not get text of element " + eleName
					+ " on " + ScreenName + "Screen");
			writeExtent("Fail", "Could not get text of element " + eleName
					+ " on " + ScreenName + "Screen");
			return actValue;
		}

	}

	public String getTextUsingJavascript(WebElement ele, String ScreenName) {
		String actValue = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(ele));
			actValue = (String) ((JavascriptExecutor) driver).executeScript(
					"return arguments[0].value;", ele);

			System.out.println("Returned text of element " + eleName + " : "
					+ actValue);
			writeExtent("Pass", "Returned text of element " + eleName + " : "
					+ actValue);
			return actValue;
		}

		catch (Exception e) {

			e.printStackTrace();
			System.out.println("Could not get text of element " + eleName
					+ " on " + ScreenName + "Screen");
			writeExtent("Fail", "Could not get text of element " + eleName
					+ " on " + ScreenName + "Screen");
			return actValue;
		}

	}

	/**
	 * Description... Collects any attribute from a webelement and returns its
	 * value as String
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param atrbName
	 *            Attribute Name whose value needs to be collected
	 * @param pageName
	 * @return Attribute Value
	 */
	/*
	 * Author : A-7688 Date Modified : 29/08/2017 Purpose : Generic method to
	 * get attribute from a webelement
	 */
	public String getAttributeWebElement(String sheetName, String locator,
			String eleName, String atrbName, String pageName) {
		try {

			if (atrbName.equals("title")) {
				String title = driver.getTitle();
				return title;
			} else {
				By b = getElement(sheetName, locator);

				String text = driver.findElement(b).getAttribute(atrbName);
				return text;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not return text from element " + eleName
					+ " on " + pageName);
			writeExtent("Fail", "Could not return text from element " + eleName
					+ " on " + pageName);
			return "";
		}

	}

	/**
	 * Description... Finds the element on the xpath sent from the calling
	 * method
	 * 
	 * @param xpath
	 * @param eleName
	 * @param ScreenName
	 * @return
	 */
	/*
	 * Author : A-7688 Date Modified : 6/09/2017 Purpose : Generic method to
	 * find element using dynamic xpath
	 */

	public WebElement findDynamicXpathElement(String xpath, String eleName,
			String ScreenName) {

		try {
			waitForSync(2);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath(xpath)));
			ele = driver.findElement(By.xpath(xpath));

			System.out.println("Returned " + eleName + " Sucessfully  On "
					+ ScreenName + " Page");
			return ele;

		} catch (Exception e) {

			System.out.println("Failed to find element " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Failed to find element " + eleName + " On "
					+ ScreenName + " Page");
			return ele;
		}
	}

	/**
	 * Description... Finds the element on the xpath sent from the calling
	 * method
	 * 
	 * @param xpath
	 * @param eleName
	 * @param ScreenName
	 * @return
	 */
	public WebElement findDynamicXpathElement(String xpath, String sheetName,
			String eleName, String ScreenName) {

		try {
			waitForSync(2);
			ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,
					xpath)));
			test.log(LogStatus.PASS, "Element returned sucessfully " + eleName
					+ " On " + ScreenName + " Page");
			System.out.println("Element returned sucessfully " + eleName
					+ " On " + ScreenName + " Page");
			return ele;

		} catch (Exception e) {

			System.out.println("Could not find element " + eleName + " On "
					+ ScreenName + " Page");
			test.log(LogStatus.FAIL, "Could not find element " + eleName
					+ " On " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not find element " + eleName
					+ " On " + ScreenName + " Page");
			return ele;
		}
	}

	/**
	 * Description... If an element xpath results in more than one
	 * element,collects the list of all such element and click the element which
	 * is displayed
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param Screenname
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7626 Y Date Modified : 08/9/2017 Purpose : Clicks on a
	 * WebElement based on the availabity of the element(Is displayed), takes
	 * Xpath SheetName, Locator and element name as argument. Xpath must end
	 * with "_LocatorName"
	 */
	public void checkAndClickAvailablEle(String sheetName, String locator,
			String eleName, String Screenname) throws InterruptedException

	{

		try {
			waitForSync(2);
			String[] myLocator = locator.split(";");
			String templocator = myLocator[myLocator.length - 1];

			switch (templocator) {
			case "xpath":
				clickOnAvailableEle(driver.findElements(By.xpath(xls_Read
						.getCellValue(sheetName, locator))));
				break;
			case "id":
				clickOnAvailableEle(driver.findElements(By.id(xls_Read
						.getCellValue(sheetName, locator))));
				break;
			case "name":
				clickOnAvailableEle(driver.findElements(By.name(xls_Read
						.getCellValue(sheetName, locator))));
				break;
			case "linkText":
				clickOnAvailableEle(driver.findElements(By.linkText(xls_Read
						.getCellValue(sheetName,

						locator))));
				break;
			case "cssSelector":
				clickOnAvailableEle(driver.findElements(By.cssSelector(xls_Read
						.getCellValue(sheetName,

						locator))));
				break;
			case "tagName":
				clickOnAvailableEle(driver.findElements(By.tagName(xls_Read
						.getCellValue(sheetName, locator))));
				break;
			case "className":
				clickOnAvailableEle(driver.findElements(By.className(xls_Read
						.getCellValue(sheetName,

						locator))));
				break;
			case "partialLinkText":
				clickOnAvailableEle(driver.findElements(By
						.partialLinkText(xls_Read.getCellValue(sheetName,

						locator))));
				break;

			}
			writeExtent("Pass", "Clicked on element " + eleName);

		} catch (Exception e) {

			System.out.println("Could not click on element " + eleName);
			writeExtent("Fail", "Could not click on element " + eleName);
			Assert.assertFalse(true, "Could not click on element" + eleName);

		}
	}

	/**
	 * Description... called from checkAndClickAvailablEle method. clicks if the
	 * element is available
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param Screenname
	 * @throws InterruptedException
	 */
	// called from checkAndClickAvailablEle method. clicks if the element is
	// available
	public void clickOnAvailableEle(List<WebElement> eles) {
		if (eles.size() > 0) {
			for (WebElement ele : eles) {

				if (ele.isDisplayed()) {
					ele.click();
					break;
				}
			}
		} else {
			writeExtent("Fail", "No Such element " + ele);
		}
	}

	/**
	 * Description... Checks whether an element is displayed and logs the result
	 * in custom report
	 * 
	 * @param sheetName
	 * @param eleXpath
	 * @param testSteps
	 * @param screenName
	 * @param eleName
	 * @throws InterruptedException
	 */
	// verify element is displayed overloaded method. takes
	// webelement,testSteps,screenName,eleName as argument
	public void verifyElementDisplayed(WebElement ele, String testSteps,
			String screenName, String eleName) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(ele));
		if (ele.isDisplayed()) {
			customFunction.onPassUpdate(screenName, eleName + " is Displayed",
					eleName + " is Displayed", eleName + " is Displayed",
					testSteps);
		} else {
			Status = false;
			customFunction.onFailUpdate(screenName, eleName + " is Displayed",
					eleName + " is Not Displayed", eleName + " is Displayed",
					testSteps);
		}

	}

	public void onPassUpdate(String screenName, String expText, String actText,
			String functinalityName, String testSteps) {
		try {
			counter = counter + 1;
			excelreadwrite.insertData(DriverSetup.testName,

			commonUtility.getcurrentDateTime() + "_" + String.valueOf(counter),
					"Verify the functionality " + functinalityName + " On "
							+ screenName + " Screen",

					testSteps, "Expected Value is : " + expText
							+ " \nActual value is : " + actText, true, "No",

					actText, expText);
			test.log(LogStatus.PASS, "Successfully Verified " + expText
					+ " On " + screenName +" .Verification point is "+functinalityName);
			System.out.println("Successfully Verified " + expText + " On "
					+ screenName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onFailUpdate(String screenName, String expText, String actText,
			String functinalityName, String testSteps) {
		counter = counter + 1;
		excelreadwrite.insertFailedData(
				DriverSetup.testName,
				commonUtility.getcurrentDateTime() + "_"
						+ String.valueOf(counter), "Verify the functionality "
						+ functinalityName + " On " + screenName + " Screen",
				testSteps, "Expected Value is : " + expText
						+ " \nActual value is : " + actText, false, "",
				actText,

				expText);
		
		test.log(LogStatus.FAIL, "Failed to Verify " + expText+" Where actual Text found as :"+actText+ ".Verification point is "+functinalityName);
		//test.log(LogStatus.INFO, test.addScreenCapture(getPropertyValue(globalVarPath,"screenShotPath")));
		System.out.println("Failed to Verify " + expText);
		
		if(!customFunction.getPropertyValue(globalVarPath, "isClubbedTC").equals("Yes"))
		{
		Assert.assertFalse(true, "Element is not found");
		}
	}
	public void hover(String locator) {

		try {
			By element = By.xpath(locator);
			waitTillOverlayDisappear(element);
			WebElement ele = driver.findElement(By.xpath(locator));
			(new Actions(driver)).moveToElement(ele).perform();
		}

		catch (Exception e) {
			System.out.println("Not hovered on the object with locator : "
					+ locator);
		}
	}
	/**
	* Desc : get user details
	* @author A-10690
	* @throws InterruptedException
	* @throws IOException 
	 */
	public void getUser(String user) throws InterruptedException{
	    
		String locator =xls_Read.getCellValue("Generic_Elements","txt_loginuser;xpath");
		String s1=driver.findElement(By.xpath(locator)).getText();
		map.put(user,s1);
			
		}

	/****
	 * @author A-7271
	 * @param message
	 * Desc : To handle the reporting in the catch block of the test case
	 */
	public void onFailUpdate(String message)
			 {
		 counter = counter + 1;
		test.log(LogStatus.FAIL, message);
		//test.log(LogStatus.INFO, test.addScreenCapture(getPropertyValue(globalVarPath,"screenShotPath")));
		
		if(!customFunction.getPropertyValue(globalVarPath, "isClubbedTC").equals("Yes"))
		{
		Assert.assertFalse(true, "Test case has failed steps");
		}
	}

	// click a webelement link, check box, button, radio button
	public void clickWebElement(WebElement ele, String eleName,
			String ScreenName) throws InterruptedException {
		waitForSync(1);
		waitTillOverlayDisappear(ele);
		try {
			ele.click();
			writeExtent("Pass", "Clicked on " + eleName + " On " + ScreenName
					+ " Page");
			System.out.println("Clicked on " + eleName + " On " + ScreenName
					+ " Page");

		} catch (Exception e) {
			System.out.println("Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			
			if(!customFunction.getPropertyValue(globalVarPath, "isClubbedTC").equals("Yes"))
			{
			Assert.assertFalse(true, "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			}

		}
	}

	public void doubleClickTableRecord(String referenceVar, String locator,
			String sheetName, int loopCount) {

		try {
			Actions action = new Actions(driver);
			String dynXpath = xls_Read.getCellValue(sheetName, locator)
					+ referenceVar + "')]";

			System.out.println(dynXpath);
			WebElement element = driver.findElement(By.xpath(dynXpath));

			for (int i = 0; i < loopCount; i++) {
				try {
					new Robot().mouseWheel(2);
					waitForSync(1);
					action.doubleClick(element).perform();
					waitForSync(1);
					if (!element.isSelected())
						action.doubleClick(element).perform();
					break;
				}

				catch (ElementNotVisibleException e) {
					new Robot().mouseWheel(2);
					System.out.println("found at " + (i + 1) + "times");
					waitForSync(1);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	// doubleclick a webelement link, check box, button, radio button

	public void doubleclickWebElement(String sheetName, String locator,
			String eleName, String ScreenName) throws InterruptedException {
		Actions action = new Actions(driver);
		By ele = getElement(sheetName, locator);
		WebElement element = driver.findElement(ele);

		try {

			action.doubleClick(element).perform();

		} catch (Exception e) {
			System.out.println("Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
		}
	}

	/**
	 * This method will set any parameter string to the system's clipboard.
	 * 
	 * @throws InterruptedException
	 */
	public void setClipboardData(String string) throws InterruptedException {
		// StringSelection is a class that can be used for copy and paste
		// operations.
		waitForSync(2);
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(stringSelection, null);
	}

	public enum keyActions {
		TAB, ENTER, SCROLLDOWNMOUSE, DELETE, DOWN, SCROLLUPMOUSE, F4, ESCAPE, CONTROL, BACK_SPACE, V, N, ALT
	}

	/**
	 * Description...Performs any keyboard press key operations
	 * 
	 * @param key
	 *            as described in keyActions
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	// perform keyboard operations for pressing the keys
	public void keyPress(String key) throws AWTException, InterruptedException {
		waitForSync(1);
		String downfieldValue = "10";
		String upfieldValue = "-10";
		Robot robot = new Robot();

		switch (keyActions.valueOf(key)) {

		case TAB:
			robot.keyPress(KeyEvent.VK_TAB);
			System.out.println("pressed tab");
			break;
		case ENTER:
			robot.keyPress(KeyEvent.VK_ENTER);
			break;
		case DELETE:
			robot.keyPress(KeyEvent.VK_DELETE);
			break;
		case DOWN:
			robot.keyPress(KeyEvent.VK_DOWN);
			break;
		case SCROLLDOWNMOUSE:
			robot.mouseWheel(Integer.parseInt(downfieldValue));
			break;
		case SCROLLUPMOUSE:
			robot.mouseWheel(Integer.parseInt(upfieldValue));
			break;

		case F4:
			robot.keyPress(KeyEvent.VK_F4);
			break;
		case ESCAPE:
			robot.keyPress(KeyEvent.VK_ESCAPE);
			break;
		case BACK_SPACE:
			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			break;
		case CONTROL:
			robot.keyPress(KeyEvent.VK_CONTROL);
			break;
		case V:
			robot.keyPress(KeyEvent.VK_V);
			break;
		case ALT:
			robot.keyPress(KeyEvent.VK_ALT);
			break;
		case N:
			robot.keyPress(KeyEvent.VK_N);
			break;
		}

	}

	/**
	 * Description...Perform keyboard operations for releasing the keys. Should
	 * be called after keyPress method
	 * 
	 * @param key
	 *            as described in keyActions
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	// perform keyboard operations for releasing the keys
	public void keyRelease(String key) throws AWTException,
			InterruptedException {
		waitForSync(1);
		String fieldValue = "";
		Robot robot = new Robot();

		switch (keyActions.valueOf(key)) {
		case TAB:
			robot.keyRelease(KeyEvent.VK_TAB);
			break;

		case ENTER:
			robot.keyRelease(KeyEvent.VK_ENTER);

			break;
		case DELETE:
			robot.keyRelease(KeyEvent.VK_DELETE);
			break;

		case DOWN:
			robot.keyRelease(KeyEvent.VK_DOWN);
			break;
		}

	}

	/**
	 * Description... Uploads the file. For Handling windows file upload pop up.
	 * 
	 * @param fileLocation
	 */
	// uploads file
	public void uploadFile(String fileLocation) {
		try {
			// Setting clipboard with file location
			setClipboardData(fileLocation);
			// native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			waitForSync(1);
			robot.keyPress(KeyEvent.VK_CONTROL);
			waitForSync(1);
			robot.keyPress(KeyEvent.VK_V);
			waitForSync(1);
			robot.keyRelease(KeyEvent.VK_V);
			waitForSync(1);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			waitForSync(1);
			robot.keyPress(KeyEvent.VK_ENTER);
			waitForSync(1);
			robot.keyRelease(KeyEvent.VK_ENTER);
			waitForSync(1);
		} catch (Exception exp) {
			System.out.println("Failed to Upload File " + fileLocation);
			writeExtent("Fail", "Failed to Upload File " + fileLocation);
			Assert.assertFalse(true, "Failed to Upload File " + fileLocation);
		}
	}

	/*
	 * Author : A-7688 Date Modified : 24/08/2017 * Purpose : Generic Method to
	 * check And Return Available Element
	 */
	public WebElement checkAndReturnAvailablEle(String sheetName,
			String locator, String eleName, String Screenname)
			throws InterruptedException

	{

		try {

			ele = returnAvailableEle(driver.findElements(By.xpath(xls_Read
					.getCellValue(sheetName, locator))));
			writeExtent("Pass", "returned element " + eleName);

		} catch (Exception e) {

			System.out.println("Could return element " + eleName);
			writeExtent("Fail", "Failed to return element " + eleName);
			Assert.assertFalse(true, "Failed to return element " + eleName);

		}
		return ele;
	}

	/*
	 * Author : A-7688 Date Modified : 24/08/2017 Flow : Open SetUP
	 * Page>>Login>>Go to B2BUI>>Load Profile>>Enter Flight and Passenger
	 * Details>>Click Search Button Purpose : Click Add to cart Button on Flight
	 * Search Page
	 */
	public WebElement returnAvailableEle(List<WebElement> eles) {
		if (eles.size() > 0) {
			for (WebElement ele : eles) {

				if (ele.isDisplayed()) {
					return ele;

				}
			}
		} else {
			writeExtent("Fail", "No Such element " + ele);
		}
		return null;
	}
	/**
	 * @Desc To verify the pieces, weight in the report contents by passing the pmkey
	 * @param reportHeading
	 * @param screenId
	 * @param pmKey
	 * @param elementstoVerify  - pcs, wgt (any elements to be verified)
	 * @param elementsIndexfromPmKey - index position of the given elements from the pmkey in the report
	 * @throws Exception
	 */
	public void verifyNumericElementsInReport(String reportHeading,String screenId,String pmKey,String elementstoVerify[], int elementsIndexfromPmKey[]) throws Exception
	{
	     
		try
		{
			//Verification if report got generated
			switchToWindow("storeParent");
			switchToWindow("multipleWindows");
			int windowSize=getWindowSize();

			if(windowSize==2)
			{
				switchToFrame("frameName","ReportContainerFrame");

				//Verifying heading of the report
				String locatorHeading=xls_Read.getCellValue("Generic_Elements", "htmlDiv_reportHeading;xpath");
				locatorHeading=locatorHeading.replace("ReportHeading", data(reportHeading));
				try 
				{
					if(driver.findElement(By.xpath(locatorHeading)).isDisplayed())				
						onPassUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is  getting generated", "Verify whether the report is generated","Verify whether the report is generated");			
					else				
						onFailUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is Not getting generated", "Verify whether the report is Not generated","Verify whether the report is not generated",false);			
				} catch (Exception e) {
					onFailUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is Not getting generated", "Verify whether the report is Not generated","Verify whether the report is not generated",false);
				}
				//Extracting the Report Contents
				String locatorContent=xls_Read.getCellValue("Generic_Elements", "htmlContents_report;xpath");
				String contents=driver.findElement(By.xpath(locatorContent)).getText();
				System.out.println(contents);		

				//Getting the index position of the given primary key from the extracted report contents
				int index=getPmKeyIndex(contents,pmKey);
				System.out.println(index);

				for(int i=0;i<elementstoVerify.length;i++)
				{

					if(elementstoVerify[i].equals(contents.split("\\n")[index+elementsIndexfromPmKey[i]]))	
						writeExtent("Pass",  "Sucessfully Verified in the report : " +elementstoVerify[i] + " on " + screenId);
					else
						writeExtent("Fail",  "Failed to verify in the report : " +elementstoVerify[i] + " on " + screenId);

				}

			}
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Report is not getting generated"+" on " + screenId);
		}
		finally
		{
			closeBrowser();
			waitForSync(2);
			switchToWindow("getParent");
			switchToFrame("default");
			switchToFrame("contentFrame",screenId);
		}

	}

	


	

	/**
	 * @Desc To return the index position of the given pmkey in the report contents
	 * @param s
	 * @param awbNo
	 * @return
	 */

	public static int getPmKeyIndex(String s,String pmkey)
	{
		int i=0;
		for(String value : s.split("\\n"))
		{
			System.out.println(value);
			if(value.equals(pmkey))
				break;	
			else 
				i++;		
		}
		return i;
	}
	/**
	 * Description... Handles and Verifies the alert text
	 * 
	 * @param expAlertText
	 * @param testSteps
	 * @param pageName
	 * @param AlertText
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7688 Date Modified : 17/08/2017 Flow : Open URL>> Navigate as
	 * per test case flow>> if alert is present get Alert text and verify it
	 * Purpose : Generic Method to Verify Alert Text
	 */

	public void verifyAlertText(String expAlertText, String testSteps,
			String pageName) throws InterruptedException {

		try {

			Alert alert = driver.switchTo().alert();
			String actAlertText = alert.getText();
			alert.accept();
			verifyValueOnPage(expAlertText, actAlertText, testSteps, pageName,
					"Alert Text");
		} catch (Exception e) {
			System.out.println("Could not find  " + expAlertText + " "
					+ "Alert Text");
			writeExtent("Fail", "Could not find  " + expAlertText + " "
					+ "Alert Text");
			Assert.assertFalse(true, "Could not find  " + expAlertText + " "
					+ "Alert Text");
		}

	}

	/**
	 * Description... Verifies the file is downloaded successfully in the
	 * downloads folder
	 * 
	 * @param filePath
	 * @param fileName
	 * @param pageName
	 */
	/*
	 * Author : A-7688 Date Modified : 9/11/2017 flow : Download file on a Page,
	 * Verify file download purpose : Verify a file is downloaded
	 */
	public static boolean verifyFileDownload(String filePath, String fileName,
			String pageName) {

		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();
		boolean fileFound = false;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				if (listOfFiles[i].toString().contains(fileName)) {
					fileFound = true;
					System.out.println("File found");
					break;
				}
			}

		}
		if (fileFound == false) {
			System.err.println("File not found");
		}
		return fileFound;
	}

	/**
	 * Description...Delete file if present in a folder.
	 * 
	 * @param filePath
	 * @param fileName
	 * @author A-7688
	 */
	/*
	 * Author : A-7688 Date Modified : 9/11/2017 purpose : Delete file if
	 * present on a page
	 */
	public void deleteFileIfPresent(String filePath, String fileName) {
		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

				if (listOfFiles[i].toString().contains(fileName)) {
					writeExtent("Info", "Deleting file " + fileName + " from  "
							+ filePath);
					System.out.println("Deleting file " + fileName + " from  "
							+ filePath);
					listOfFiles[i].delete();
					break;
				}
			}
		}
	}

	/**
	 * Description... Performs mouse hover operation
	 * 
	 * @param sheetName
	 * @param xpath
	 */
	// to hover over an element
	public void hover(String sheetName, String locator) {

		try {
			By element = getElement(sheetName, locator);
			waitTillOverlayDisappear(element);
			WebElement ele = driver.findElement(By.xpath(xls_Read.getCellValue(
					sheetName, locator)));
			(new Actions(driver)).moveToElement(ele).perform();
		}

		catch (Exception e) {
			System.out.println("Not hovered on the object with locator : "
					+ locator);
		}
	}

	public enum Cookieops {
		Get, Add, Delete
	};

	/**
	 * Description... perform cookie related operations depending on parameter
	 * passed. All cookie operations should be given in Cookieops
	 * 
	 * @param parameter
	 *            Cookieops
	 * @throws InterruptedException
	 */
	// perform cookie related operations
	public void ManageCookies(String parameter) throws InterruptedException {
		Set<Cookie> cookies = driver.manage().getCookies();

		switch (Cookieops.valueOf(parameter)) {
		case Get:
			cookies = driver.manage().getCookies();
			setPropertyValue("Cookie", cookies.toString(),
					customFunction.proppath);
			break;
		case Add:
			for (Cookie a : cookies) {
				driver.manage().addCookie(a);
			}
			break;
		case Delete:
			driver.manage().deleteAllCookies();
			break;
		}

	}

	public enum Browserops {
		Refresh, Backwarkd, Forward, NavigateTo, Maximize
	};

	/**
	 * Description... perfrom browser related operations depending on parameter
	 * passed. All browser operations should be given in Browserops
	 * 
	 * @param parameter
	 *            Browserops
	 * @throws InterruptedException
	 */
	// perfrom browser related operations
	public void browserOperations(String... parameter)
			throws InterruptedException {

		switch (Browserops.valueOf(parameter[0])) {
		case Refresh:
			driver.navigate().refresh();
			break;
		case Backwarkd:
			driver.navigate().back();
			break;
		case Forward:
			driver.navigate().forward();
			break;
		case NavigateTo:
			driver.navigate().to(parameter[1]);
			break;
		case Maximize:
			driver.manage().window().maximize();
			break;
		}
	}

	/*
	 * Author : A-8705 Date Modified : 24/7/2019 Purpose : Common Method to
	 * Verify Element is not enabled in UI page
	 */
	public void verifyElementDisabled(WebElement b, String sheetName,
			String testSteps, String screenName, String

			eleName) throws InterruptedException {
		boolean s = b.isEnabled();
		if (s == false) {
			customFunction.onPassUpdate(screenName, eleName + " is disabled",
					eleName + " is disabled", eleName + " is disabled",
					testSteps);
		} else {
			customFunction
					.onFailUpdate(screenName, eleName + " is enabled", eleName
							+ " is enabled", eleName + " is enabled", testSteps);
		}
	}

	/**
	 * 
	 * Description... Checks for DetailedReport key in Global Variable
	 * properties. If DetailedReport=Yes then logs all the operations otherwise
	 * logs what is logged using test.log
	 * 
	 * @param Status
	 *            Pass/Fail
	 * @param Details
	 */
	public void writeExtent(String Status, String Details) {
		String reportDetails = getPropertyValue(customFunction.proppath,
				"DetailedReport");
		try {
			if (reportDetails.equalsIgnoreCase("Yes")) {
				if (Status.equals("Pass"))
				{
					test.log(LogStatus.PASS, Details);
				}
				else if (Status.equals("Fail"))
				{
					test.log(LogStatus.FAIL, Details);
					//test.log(LogStatus.INFO, test.addScreenCapture(getPropertyValue(globalVarPath,"screenShotPath")));
				}
				else if (Status.equals("Info"))
				{
					test.log(LogStatus.INFO, Details);
					//test.log(LogStatus.INFO, test.addScreenCapture(getPropertyValue(globalVarPath,"screenShotPath")));

				}
				else if(Status.equals("AddScreenShot"))
				{
					test.log(LogStatus.INFO, test.addScreenCapture(Details));
				}
			}
		} catch (Exception e) {
			System.out.println("Failed in creating Extent Object");
			System.out.println(e);
		}
	}

	
	public void selectTableRecord(String referenceVar, String locator,
			String sheetName, int loopCount) {

		try {

			String xpart1 = xls_Read.getCellValue("Generic_Elements",
					"table_selectEle;xpath") + referenceVar +

			"')]";
			String xpart2 = xls_Read.getCellValue(sheetName, locator);
			String dynXpath = xpart1 + xpart2;
			System.out.println(dynXpath);

			for (int i = 0; i < loopCount; i++) {
				try {
					new Robot().mouseWheel(2);
					waitForSync(1);
					driver.findElement(By.xpath(dynXpath)).click();
					waitForSync(1);
					if (!driver.findElement(By.xpath(dynXpath)).isSelected())
						driver.findElement(By.xpath(dynXpath)).click();
					
					writeExtent("Pass","The reference value "+referenceVar+" is present ");
					/****WebElement element = driver.findElement(By.xpath(dynXpath));
					waitTillOverlayDisappear(element);
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", element);*****/
					
					break;
				}

				catch (ElementNotVisibleException e) {
					new Robot().mouseWheel(2);
					System.out.println("found at " + (i + 1) + "times");
					waitForSync(1);
					writeExtent("Fail","The reference value "+referenceVar+" is not present ");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	/**
	 * @author A-10690
	 * @desc:Launching a new url in a new tab the same chrome instance
	 * @param url
	 * @throws Exception 
	 */
	public void launchUrlInTab(String url)
	{
		try
		{
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		js.executeScript("window.open("+url+");");
		
		}
		
		catch(Exception e)
		{
			writeExtent("Fail","Failed to launch"+url+"in new tab");
			System.out.println("Failed to launch"+url+"in new tab");
		}
	}
	
	/**
	 * @author A-10690
	 * @desc:switching to the tab having the expected url
	 * @param url
	 */
	public void switchToTab(String expectedrurl)

	{
   
	/*	Set<String> handles=driver.getWindowHandles();
		List<String>hl=new ArrayList(handles);*/
		
		try
		{
		for(String tab:driver.getWindowHandles())
		{

			String url=	driver.switchTo().window(tab).getCurrentUrl();
			if(url.contains(expectedrurl))
			{
				
			 waitForSync(1);
			 break;
				
				
			}
		}
		
		}
		
		catch(Exception e)
		{
			
		}
}
	/**
	 * @Description : wait Till Screen load
	 * @author A-9175
	 * @param sheetName
	 * @param locator
	 * @param elename
	 * @param ScreenName
	 */
	public void waitTillScreenload(String sheetName, String locator,
			String elename, String ScreenName) {

		try {
			By b = getElement(sheetName, locator,false);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.presenceOfElementLocated(b));
			wait.until(ExpectedConditions.elementToBeClickable(b));
			
		    /*******ele = driver.findElement(b);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele);****/
			
			writeExtent("Pass", elename + "visible on " + ScreenName
					+ " Page");
			

		} catch (Exception e) {
			
			writeExtent("Fail", elename + "not visible on " + ScreenName
					+ " Page");
			Assert.assertFalse(true, elename + "not visible on " + ScreenName
					+ " Page");
		}
	}
	/**
	 * @Description : wait Till Screen load
	 * @author A-9175
	 * @param sheetName
	 * @param locator
	 * @param elename
	 * @param ScreenName
	 */
	public void waitTillScreenloadWithOutAssertion(String sheetName, String locator,
			String elename, String ScreenName) {

		try {
			By b = getElement(sheetName, locator,false);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.presenceOfElementLocated(b));
			wait.until(ExpectedConditions.elementToBeClickable(b));
			

		} catch (Exception e) {
			
			
		}
	}
	/**
	 * @Description : wait Till Screen load
	 * @author A-9175
	 * @param sheetName
	 * @param locator
	 * @param elename
	 * @param ScreenName
	 */
	public void waitTillScreenloadWithOutAssertion(String sheetName, String locator,
			String elename, String ScreenName,int waitTime) {

		try {
			By b = getElement(sheetName, locator,false);
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.presenceOfElementLocated(b));
			wait.until(ExpectedConditions.elementToBeClickable(b));
			

		} catch (Exception e) {
			
			
		}
	}
	public void selectTableRecordJS(String referenceVar, String locator,
			String sheetName, int loopCount) {

		try {

			String xpart1 = xls_Read.getCellValue("Generic_Elements",
					"table_selectEle;xpath") + referenceVar +

			"')]";
			String xpart2 = xls_Read.getCellValue(sheetName, locator);
			String dynXpath = xpart1 + xpart2;
			System.out.println(dynXpath);

			for (int i = 0; i < loopCount; i++) {
				try {
					new Robot().mouseWheel(2);
					waitForSync(1);
					WebElement element = driver.findElement(By.xpath(dynXpath));
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", element);
					writeExtent("Pass","The reference value "+referenceVar+" is present ");
					
					break;
				}

				catch (ElementNotVisibleException e) {
					new Robot().mouseWheel(2);
					System.out.println("found at " + (i + 1) + "times");
					waitForSync(1);
					writeExtent("Fail","The reference value "+referenceVar+" is not present ");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	/**
	 * Description... Selects the check box in a table in the required row where
	 * depending on the primary key.
	 * 
	 * @param referenceVar
	 * @param locator
	 * @param sheetName
	 * @param loopCount
	 */

	public void selectTableRecord(String referenceVar, String sheetName,
			String locator, String locatorEle, int loopCount) {

		try {
			boolean flag = false;
			int row = 0;
			List<WebElement> rows = driver.findElements(By.xpath(xls_Read
					.getCellValue(sheetName, locator)));
			locatorEle = xls_Read.getCellValue(sheetName, locatorEle);

			{
				for (int i = 0; i <= rows.size(); i++) {
					System.out.println("i= " + i);

					if (rows.get(i)
							.getText()
							.toLowerCase()
							.replace(" ", "")
							.contains(
									referenceVar.toLowerCase().replace(" ", ""))) {

						flag = true;

					}

					if (flag) {
						row = i;
						break;
					}
				}

				String dynXpath = "(" + locatorEle + ")[" + row + "]";
				for (int i = 0; i < loopCount; i++) {
					try {
						new Robot().mouseWheel(2);
						waitForSync(1);
						driver.findElement(By.xpath(dynXpath)).click();
						waitForSync(1);
						if (!driver.findElement(By.xpath(dynXpath))
								.isSelected())
							driver.findElement(By.xpath(dynXpath)).click();
						break;
					}

					catch (ElementNotVisibleException e) {
						new Robot().mouseWheel(2);
						System.out.println("found at " + (i + 1) + "times");
						waitForSync(1);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Description... Selects the check box in a table in the required row where
	 * depending on the composite keys.
	 * 
	 * @author A-7271
	 * @param referenceVar
	 * @param locator
	 * @param sheetName
	 * @param loopCount
	 */

	public void selectTableRecordWithMultipleKeys(String referenceVar[],
			String sheetName, String locator, String

			locatorEle, int loopCount) {

		try {
			boolean flag = false;
			int row = 0;
			List<WebElement> rows = driver.findElements(By.xpath(xls_Read
					.getCellValue(sheetName, locator)));
			locatorEle = xls_Read.getCellValue(sheetName, locatorEle);

			{
				for (int i = 0; i <= rows.size(); i++) {
					System.out.println("i= " + i);

					for (int j = 0; j < referenceVar.length; j++) {
						if (rows.get(i)
								.getText()
								.toLowerCase()
								.replace(" ", "")
								.contains(
										referenceVar[j].toLowerCase().replace(
												" ", "")))

						{

							flag = true;

						}

						else {
							flag = false;
							break;
						}
					}

					if (flag) {
						row = i;
						break;
					}
				}

				String dynXpath = "(" + locatorEle + ")[" + row + "]";
				for (int i = 0; i < loopCount; i++) {
					try {
						new Robot().mouseWheel(2);
						waitForSync(1);
						driver.findElement(By.xpath(dynXpath)).click();
						waitForSync(1);
						if (!driver.findElement(By.xpath(dynXpath))
								.isSelected())
							driver.findElement(By.xpath(dynXpath)).click();
						break;
					}

					catch (ElementNotVisibleException e) {
						new Robot().mouseWheel(2);
						System.out.println("found at " + (i + 1) + "times");
						waitForSync(1);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Description... Clears the text in textbox/ textarea
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param ScreenName
	 */
	public void clearText(String sheetName, String locator, String eleName,
			String ScreenName) {
		try {

			By element = getElement(sheetName, locator);
			driver.findElement(element).clear();

			waitForSync(1);
			writeExtent("Pass", "Cleared value" + " in " + eleName + " on "
					+ ScreenName + " Page");
			System.out.println("Cleared value" + " in " + eleName + " on "
					+ ScreenName + " Page");

		} catch (Exception e) {
			System.out.println("Could not clear value" + " in " + eleName
					+ " on " + ScreenName + " Page");
			writeExtent("Fail", "Could not clear value" + " in " + eleName
					+ " on " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not clear value" + " in " + eleName
					+ " on " + ScreenName + " Page");
		}

	}

	/**
	 * Description... Check a check box/radio button if not checked
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param ScreenName
	 */
	public void checkIfUnchecked(String sheetName, String locator,
			String eleName, String ScreenName) {
		try {

			By b = getElement(sheetName, locator);
			moveScrollBar(driver.findElement(b));
			boolean checked = driver.findElement(b).isSelected();
			if (!checked)
				javaScriptToclickElement(sheetName, locator, eleName,
						ScreenName);
		} catch (Exception e) {
			System.out.println("Could not check " + eleName + " on "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not check " + eleName + " on "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not check " + " as " + eleName
					+ " on " + ScreenName + " Page");
		}
	}

	/**
	 * Description... For selecting multiple options in a multiple select
	 * dropdown
	 * 
	 * @param multipleVals
	 * @param ele
	 * @param selectBy
	 * @param option
	 */
	public void selectMultipleValuesInDropdown(String multipleVals,
			WebElement ele, String selectBy, String option) {
		try {
			String multipleSel[] = multipleVals.split(",");
			switch (selectBy) {
			case "Value":
				for (String valueToBeSelected : multipleSel) {
					new Select(ele).selectByValue(valueToBeSelected);
				}
			case "VisibleText":
				for (String valueToBeSelected : multipleSel) {
					new Select(ele).selectByVisibleText(valueToBeSelected);
				}

			}
		} catch (Exception e) {
			System.out.println("Could not enter " + option);
			writeExtent("Fail", "Could not enter " + option);
			Assert.assertFalse(true, "Could not enter " + option);
		}
	}

	/**
	 * This Method return all of options available in DropDown Box
	 * 
	 */
	public String[] getAllOptions(WebElement ele) {
		List<String> listTemp = new ArrayList<String>();
		try {

			// List<WebElement> options =
			// dropDownListBox.findElements(By.tagName("option"));
			Select options = new Select(ele);
			for (WebElement option : options.getOptions()) {
				listTemp.add(option.getText());
			}
			String[] allOptions = listTemp.toArray(new String[0]);
			return allOptions;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Description... Store the parent window handle, clicks on the Add Button
	 * and switches to the child window
	 * 
	 * @throws Exception
	 */
	public void clickButtonSwitchWindow(String sheetName, String locator,
			String screenName, String eleName) throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, locator, eleName, screenName);
		waitForSync(8);
		switchToWindow("child");
	}

	/**
	 * Description... Checks whether an element is not displayed and logs the
	 * result in custom report
	 * 
	 * @param sheetName
	 * @param eleXpath
	 * @param testSteps
	 * @param screenName
	 * @param eleName
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7290 Date Modified : 18/8/2017 Purpose : Common Method to
	 * Verify Element displayed in UI page or not
	 */
	public void verifyElementNotDisplayed(String sheetName, String eleXpath,
			String testSteps, String screenName, String eleName)
			throws InterruptedException {
		By b = getElement(sheetName, eleXpath);
		try {
			driver.findElement(b).isDisplayed();
			Status = false;
			customFunction.onFailUpdate(screenName, eleName + " is Displayed",
					eleName + " is Not Displayed", eleName + " is Displayed",
					testSteps);

		} catch (Exception e) {

			customFunction.onPassUpdate(screenName, eleName
					+ " is Not Displayed", eleName + " is Not Displayed",
					eleName + " is Not Displayed", testSteps);
		}
	}

	/**
	 * Description... Store the parent window handle, clicks on the Add Button
	 * and switches to the child window
	 * 
	 * @throws Exception
	 */
	public void clickButtonSwitchtoParentWindow(String sheetName,
			String locator, String eleName, String screenName) throws Exception {

		clickWebElement(sheetName, locator, eleName, screenName);
		switchToWindow("getParent");
	}

	public void waitTillSpinnerDisappear() {

		int i = 1;
		boolean isEleDispld = true;
		try {

			while (isEleDispld && i < 30) {
				isEleDispld = driver
						.findElement(
								By.xpath("//div[@class='ic-header-items-sub-content ic-loading']"))
						.isDisplayed();
				waitForSync(i);
				i++;
			}

		}

		catch (Exception e) {
		}
	}
	/**
	 * @author A-9847
	 * @Desc Wait till the loader disappear
	 * @param sheetName
	 * @param locator
	 */
	public void waitTillSpinnerDisappear(String sheetName,String locator) {

		int i = 1;
		boolean isEleDispld = true;
		By b = getElement(sheetName,locator,false);	
		try {

			while (isEleDispld && i < 30) {
				isEleDispld = driver.findElement(b).isDisplayed();
				waitForSync(i);
				System.out.println(i);
				i++;
			}

		}

		catch (Exception e) {
		}

	}
	/**
	 * Description... Returns the list of web elements with the xpath mentioned
	 * in the sheetname
	 * 
	 * @param sheetName
	 * @param locator
	 * @return returns the list of web elements with the xpath mentioned in the
	 *         sheetname
	 */
	// returns the list of web elements with the xpath mentioned in the
	// sheetname
	public List returnListOfElements(String sheetName, String locator) {
		By b = getElement(sheetName, locator);
		List<WebElement> list = driver.findElements(b);
		return list;
	}

	/**
	 * Description... Returns the list of text of the list of web elements which
	 * is sent as an argument
	 * 
	 * @param list
	 * @return Returns the list of text of the list of web elements which is
	 *         sent as an argument
	 */
	// returns the list of text of the list of web elements which is sent as an
	// argument
	public List<String> returnTextListOfElements(List<WebElement> list) {
		List<String> stringList = new ArrayList();
		for (WebElement ele : list)
			stringList.add(ele.getText());
		return stringList;

	}

	/**
	 * Description... Clicks the list of web elements which is sent as an
	 * argument
	 * 
	 * @param list
	 * @param eleName
	 * @param ScreenName
	 */
	// clicks the list of web elements which is sent as an argument
	public void clickListOfElements(List<WebElement> list, String eleName,
			String ScreenName) {
		try {
			if (!list.isEmpty()) {
				for (WebElement ele : list) {
					ele.click();
					waitForSync(1);
				}
			}
		} catch (Exception e) {
			System.out.println("Could not check " + eleName + " on "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not check " + eleName + " on "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not check " + " as " + eleName
					+ " on " + ScreenName + " Page");
		}
	}

	/**
	 * Description... Checks the list of check boxes which is sent as argument
	 * 
	 * @param list
	 * @param eleName
	 * @param ScreenName
	 */
	// checks the list of check boxes which is sent as argument
	public void checkIfUncheckedList(List<WebElement> list, String eleName,
			String ScreenName) {
		try {
			if (!list.isEmpty()) {
				for (WebElement ele : list) {
					boolean checked = ele.isSelected();
					if (!checked)

						ele.click();
					waitForSync(1);
				}
			}
		} catch (Exception e) {
			System.out.println("Could not check " + eleName + " on "
					+ ScreenName + " Page");
			writeExtent("Info", "Could not check " + eleName + " on "
					+ ScreenName + " Page");

		}
	}

	public void switchToDefaultAndContentFrame(String screenID) {
		switchToFrame("default");
		switchToFrame("contentFrame", screenID);
	}

	/**
	 * Description... enter text in a text box/ text area if displayed
	 * 
	 * @param sheetName
	 *            Xpath Sheetname
	 * @param locator
	 *            Xpath Locator name
	 * @param eleName
	 *            used for reporting purpose. example OK Button
	 * @param ScreenName
	 *            used for reporting purpose. example Login Page
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7688 Date Modified : 11/8/2017 Purpose : Enters Value in a
	 * WebElement if displayed, takes Xpath SheetName, Locator and element name
	 * as argument. Xpath must end with "_LocatorName"
	 */
	public void enterValueIfDisplayed(String sheetName, String locator,
			String value, String eleName, String ScreenName)
			throws InterruptedException {
		try {
			By element = getElement(sheetName, locator);

			driver.findElement(element).clear();
			waitForSync(1);
			driver.findElement(element).sendKeys(value);
			writeExtent("Pass", "Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");
			System.out.println("Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");

		} catch (Exception e) {
			System.out.println("Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");

		}

	}

	public WebDriver relaunchBrowser(String browser) {
		try {
			switch (browser) {
			case "firefox": {
				DesiredCapabilities capabilities = DesiredCapabilities
						.firefox();
				String sFF = System.getProperty("user.dir");
				String pathFF = sFF + "\\lib\\geckodriver.exe";
				System.setProperty("webdriver.gecko.driver", pathFF);
				capabilities = DesiredCapabilities.firefox();
				capabilities.setBrowserName("firefox");
				// capabilities.setVersion(browserversion);
				capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
						true);
				capabilities.setCapability("marionette", true);
				driver = new FirefoxDriver(capabilities);
				driver.manage().window().maximize();
				return driver;
			}
			case "chrome": {
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				String sc2 = System.getProperty("user.dir");
				String pathc = sc2 + "\\lib\\chromedriver.exe";

				System.setProperty("webdriver.chrome.driver", pathc);
				ChromeOptions options = new ChromeOptions();// Added for
															// checking
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("download.default_directory", sc2+"\\src\\resources\\Downloads\\");
			    options.setExperimentalOption("prefs",chromePrefs);
				 options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
				 options.addArguments("--incognito");
				Proxy proxy = new Proxy();// Added for checking for proxy
											// settings
				proxy.setProxyType(Proxy.ProxyType.SYSTEM);// Added for checking
															// for
				// proxy settings
				capabilities.setBrowserName("chrome");
				capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				capabilities.setCapability("proxy", proxy);// Added for checking
															// for
				// proxy settings
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);// Added
				// for
				// checking
				capabilities.setCapability("chrome.switches",
						Arrays.asList("--start-maximized"));
				driver = new ChromeDriver(capabilities);
				driver.manage().window().maximize();
				return driver;
			}
			case "headless": {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			String pathchrome = System.getProperty("user.dir") + "\\lib\\chromedriver.exe";

			System.setProperty("webdriver.chrome.driver", pathchrome);
			ChromeOptions options2 = new ChromeOptions();
			options2.addArguments("--window-size=1980,1080");
			options2.addArguments("--disable-gpu");
			options2.addArguments("--disable-extensions");
			options2.setExperimentalOption("useAutomationExtension", false);
			options2.addArguments("--proxy-server='direct://'");
			options2.addArguments("--proxy-bypass-list=*");
			options2.addArguments("--start-maximized");
			options2.addArguments("--headless");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options2);
			driver = new ChromeDriver(capabilities);
			driver.manage().window().maximize();
			return driver;
			
			}
			case "chrome-debug": {
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				String sc2 = System.getProperty("user.dir");
				String pathc = sc2 + "\\lib\\chromedriver.exe";

				System.setProperty("webdriver.chrome.driver", pathc);
				ChromeOptions options = new ChromeOptions();// Added for
															// checking
				options.setExperimentalOption("excludeSwitches",
						new String[] { "enable-automation" });
				Proxy proxy = new Proxy();// Added for checking for proxy
											// settings
				proxy.setProxyType(Proxy.ProxyType.SYSTEM);// Added for checking
															// for
				// proxy settings
				capabilities.setBrowserName("chrome");
				capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				capabilities.setCapability("proxy", proxy);// Added for checking
															// for
				// proxy settings
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);// Added
				// for
				// checking
				capabilities.setCapability("chrome.switches",
						Arrays.asList("--start-maximized"));
				driver = new ChromeDriver(capabilities);
				driver.manage().window().maximize();
				return driver;
			}
			case "ie": {
				DesiredCapabilities capabilities = DesiredCapabilities
						.internetExplorer();
				capabilities = DesiredCapabilities.internetExplorer();

				String s2 = System.getProperty("user.dir");
				String path = s2 + "\\lib\\IEDriverServer.exe";

				System.out.println("@getCapabilities() - ie driver path :"
						+ path);

				System.setProperty("webdriver.ie.driver", path);
				capabilities.setBrowserName("iexplore");

				capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);

				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
						true);

				capabilities
						.setCapability(
								InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
								true);
				capabilities.setCapability(
						InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
				/*
				 * capabilities.setCapability("initialBrowserUrl",
				 * "https://icargo-icapsit.lcag.fra.dlh.de/icargo/");
				 */

				driver = new InternetExplorerDriver(capabilities);
				driver.manage().window().maximize();
				return driver;
			}
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not relaunch browser" + e);
			return null;
		}
		return driver;
	}

	/**
	 * Description... Returns the elements size... (length and height )
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param ScreenName
	 * @return Dimensions of a web element
	 */
	public Dimension getDimension(String sheetName, String locator,
			String eleName, String ScreenName) {
		Dimension d = null;
		try {
			By b = getElement(sheetName, locator);
			d = driver.findElement(b).getSize();

		} catch (Exception e) {
			System.out.println("Could not find size of " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not find size of " + eleName + " On "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not find size of " + eleName
					+ " On " + ScreenName + " Page");
		}
		return d;
	}

	/**
	 * Description... Returns the time zone depending on the
	 * 
	 * @param String
	 *            fieldValue-Contains the URL of the application *
	 * @return time zones formatted time
	 */
	public enum time_zones {
		addTime
	};

	public String changeTimeZone(String timeZones, String dateFormat,
			String timeAddFormat, String timetoaddformat) {
		String ZonedDateTime = "";
		try {

			switch (time_zones.valueOf(timeZones)) {

			case addTime:

				SimpleDateFormat format = new SimpleDateFormat(dateFormat);
				format.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date1 = format.parse(timeAddFormat);
				Date date2 = format.parse(timetoaddformat);
				long sum = date1.getTime() + date2.getTime();
				ZonedDateTime = format.format(new Date(sum));

				break;

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return ZonedDateTime;

	}
	
	/**
	 * Description... Verify if the radio button/check box is selected or not
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param ScreenName
	 * @return
	 */
	public boolean verifyElementSelected(String sheetName, String locator,
			String eleName, String ScreenName) {
		boolean isSelected = false;
		try {
			By b = getElement(sheetName, locator);
			isSelected = driver.findElement(b).isSelected();

			if (isSelected)
				verifyValueOnPage(true, true, ScreenName, eleName, " Checked ");
			else
				verifyValueOnPage(true, false, ScreenName, eleName, " Checked ");
		} catch (Exception e) {

			Assert.assertFalse(true, "Could not find " + eleName + " On "
					+ ScreenName + " Page");
		}
		return isSelected;
	}

	public void selectValueInDropdownWthXpath(String xpath, String option,
			String eleName, String selectBy) {
		try {

			WebElement ele1 = driver.findElement(By.xpath(xpath));
			Select select = new Select(ele1);

			switch (selectBy) {
			case "Value": {
				String actopt = select.getFirstSelectedOption().getText();
				if (!actopt.equalsIgnoreCase(option))
					select.selectByValue(option);

			}
				break;
			case "VisibleText": {
				String actopt = select.getFirstSelectedOption().getText();
				if (!actopt.equalsIgnoreCase(option))
					select.selectByVisibleText(option);

			}
				break;
			case "Index": {
				int index = Integer.parseInt(option);
				String actopt = select.getFirstSelectedOption().getText();
				if (!actopt.equalsIgnoreCase(option))
					select.selectByIndex(index);

			}
				break;

			}
			writeExtent("Pass", "Entered " + option + " as " + eleName

			);
			System.out.println("Pass Entered " + option + " as " + eleName);

		} catch (Exception e) {
			/*
			 * System.out.println( "Could not enter " + option + " as " +
			 * eleName + " on " + sheetName.split("_")[0] + " Screen");
			 */
			// e.printStackTrace();
			writeExtent("Fail", "Entered " + option + " as " + eleName);
			/*
			 * Assert.assertFalse(true, "Could not enter " + " as " + eleName );
			 */

		}
	}

	/**
	 * Description... Verifies if the date format is valid or not
	 * 
	 * @param format
	 * @param value
	 * @param pageName
	 * @return
	 * @throws Exception
	 */
	public String verifyDateFormat(String format, String value, String pageName)
			throws Exception {
		String dateForm = "";
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		// To make strict date format validation
		formatter.setLenient(false);
		Date parsedDate = null;

		try {
			parsedDate = formatter.parse(value);
			dateForm = formatter.format(parsedDate).toString();
			verifyValueOnPage(dateForm, value, "Verify Date Format", pageName,
					"Date Format");

		} catch (Exception e) {
			verifyValueOnPage(dateForm, value, "Verify Date Format", pageName,
					"Date Format");
		}
		return dateForm;
	}

	/**
	 * Description... Returns the elements Location... (x and y coordinate from
	 * screen )
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param ScreenName
	 * @return Location of a web element
	 */
	public int[] getLocation(String sheetName, String locator, String eleName,
			String ScreenName) {
		int d[] = new int[2];
		try {
			By b = getElement(sheetName, locator);

			int d1 = driver.findElement(b).getLocation().getX();
			int d2 = driver.findElement(b).getLocation().getY();
			d[0] = d1;
			d[1] = d2;
		} catch (Exception e) {
			System.out.println("Could not find Location of " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not find Location of " + eleName
					+ " On " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not find Location of " + eleName
					+ " On " + ScreenName + " Page");
		}
		return d;
	}

	/**
	 * Description... Returns the elements size... (length and height )
	 * 
	 * @param (WebElement
	 * 
	 * @param eleName
	 * @param ScreenName
	 * @return Dimensions of a web element
	 */
	public int[] getLocation(WebElement ele, String eleName, String ScreenName) {
		int d[] = new int[2];
		try {

			int d1 = ele.getLocation().getX();
			int d2 = ele.getLocation().getY();
			d[0] = d1;
			d[1] = d2;
		} catch (Exception e) {
			System.out.println("Could not find Location of " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not find Location of " + eleName
					+ " On " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not find Location of " + eleName
					+ " On " + ScreenName +

					" Page");
		}
		return d;
	}

	public void javaScriptToEnterValueInTextBox(String sheetName,
			String locator, String value, String elename, String ScreenName) {

		try {
			waitForSync(2);
			By b = getElement(sheetName, locator);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(b));
			wait.until(ExpectedConditions.elementToBeClickable(b));
			ele = driver.findElement(b);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].value='" + value + "'", ele);
			writeExtent("Pass", "Clicked on " + elename + " On " + ScreenName
					+ " Page");
			System.out.println("Clicked on " + elename + " On " + ScreenName
					+ " Page");

		} catch (Exception e) {
			System.out.println("Could not click on element " + elename);
			writeExtent("Fail", "Could not click on " + elename + " On "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not click on " + elename + " On "
					+ ScreenName + " Page");
		}
	}

	/**
	 * Description... Returns the
	 * 
	 * @param sheetName
	 * @param locator
	 * @param cssProp
	 * @param eleName
	 * @param ScreenName
	 * @return
	 */
	public String getCssValue(String sheetName, String locator, String cssProp,
			String eleName, String ScreenName) {
		String cssValue = "";
		try {
			By b = getElement(sheetName, locator);
			cssValue = driver.findElement(b).getCssValue(cssProp);
		} catch (Exception e) {
			System.out.println("Could not return css value of " + eleName
					+ " On " + ScreenName + " Page");
			writeExtent("Fail", "Could not return css value of " + eleName
					+ " On " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not return css value of " + eleName
					+ " On " +

					ScreenName + " Page");
		}
		return cssValue;
	}

	/**
	 * Description... Returns the list of attribute values of the list of web
	 * elements which is sent as an argument
	 * 
	 * @param list
	 * @return Returns the list of attribute values of the list of web elements
	 *         which is sent as an argument
	 */

	public List<String> returnAttributeValueListOfElements(
			List<WebElement> list, String valueName) {
		List<String> stringList = new ArrayList();
		for (WebElement ele : list)
			stringList.add(ele.getAttribute(valueName));
		return stringList;

	}

	public int randomNumberInList(int min, int max) {
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;

	}

	public String selectRandomValueFromDropdown(String sheetName, String locator) {

		List<WebElement> listOfValues = driver.findElements(By.xpath(xls_Read
				.getCellValue(sheetName, locator)));
		WebElement element = listOfValues.get(randomNumberInList(1,
				listOfValues.size() - 1));
		element.click();
		return element.getText().toString();

	}

	/**
	 * Description... Check a check box/radio button if not checked
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param ScreenName
	 */
	public void uncheckIfChecked(String sheetName, String locator,
			String eleName, String ScreenName) {
		try {

			By b = getElement(sheetName, locator);
			boolean checked = driver.findElement(b).isSelected();
			if (checked)
				javaScriptToclickElement(sheetName, locator, eleName,
						ScreenName);
		} catch (Exception e) {
			System.out.println("Could not check " + eleName + " on "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not check " + eleName + " on "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not check " + " as " + eleName
					+ " on " + ScreenName + " Page");
		}
	}

	/**
	 * Description... Collects the element text which is defined as innertext in
	 * HTML Tree
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param pageName
	 * @return Text of an element
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7688 Date Modified : 29/08/2017 Purpose : Generic method to
	 * get text from a webelement
	 */
	public String getElementText(WebElement ele, String eleName, String pageName)
			throws InterruptedException {

		waitForSync(2);
		try {

			String text = ele.getText();
			writeExtent("Pass", "Returned text of element " + eleName + " : "
					+ text);

			return text;
		} catch (Exception e) {
			System.out.println("Could not return text from element " + eleName
					+ " on " + pageName);
			writeExtent("Fail", "Could not return text from element " + eleName
					+ " on " + pageName);
			return "";
		}

	}

	/**
	 * Description... Collects any attribute from a webelement and returns its
	 * value as String
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param atrbName
	 *            Attribute Name whose value needs to be collected
	 * @param pageName
	 * @return Attribute Value
	 */
	/*
	 * Author : A-7688 Date Modified : 29/08/2017 Purpose : Generic method to
	 * get attribute from a webelement
	 */
	public String getAttributeWebElement(WebElement ele, String eleName,
			String atrbName, String pageName) {
		try {

			String text = ele.getAttribute(atrbName);
			return text;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not return text from element " + eleName
					+ " on " + pageName);
			writeExtent("Fail", "Could not return text from element " + eleName
					+ " on " + pageName);
			return "";
		}

	}

	public void moveScrollBar(WebElement ele) {
		try {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView()", ele);
		} catch (Exception e) {
			System.out.println("Failed to move horizontal Scroll Bar");
		}
	}

	/*
	 * Author : A-7688 Date Modified : 29/08/2017 Purpose : Generic method to
	 * get text from a webelement using JavaScript
	 */
	public String getAttributeUsingJavascript(String sheetName, String locator,
			String eleName, String ScreenName, String attributeName) {
		String act = "";

		try {
			By b = getElement(sheetName, locator);
			ele = driver.findElement(b);
			act = ((JavascriptExecutor) driver).executeScript(
					"return arguments[0]." + attributeName + ";", ele)
					.toString();

			System.out.println("Returned text of element " + eleName + " : "
					+ act);
			writeExtent("Pass", "Returned text of element " + eleName + " : "
					+ act);
			return act;
		}

		catch (Exception e) {

			e.printStackTrace();
			System.out.println("Could not get text of element " + eleName
					+ " on " + ScreenName + "Screen");
			writeExtent("Fail", "Could not get text of element " + eleName
					+ " on " + ScreenName + "Screen");
		}
		return act;

	}

	/**
	 * Description... Checks whether an element is not displayed and logs the
	 * result in custom report
	 * 
	 * @param sheetName
	 * @param eleXpath
	 * @param testSteps
	 * @param screenName
	 * @param eleName
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7290 Date Modified : 18/8/2017 Purpose : Common Method to
	 * Verify Element displayed in UI page or not
	 */
	public void verifyElementNotDisplayed(String xpath, String testSteps,
			String screenName, String eleName) throws InterruptedException {

		try {
			driver.findElement(By.xpath(xpath)).isDisplayed();
			Status = false;
			customFunction.onFailUpdate(screenName, eleName + " is Displayed",
					eleName + " is Not Displayed", eleName + " is Displayed",
					testSteps);

		} catch (Exception e) {

			customFunction.onPassUpdate(screenName, eleName
					+ " is Not Displayed", eleName + " is Not Displayed",
					eleName + " is Not Displayed", testSteps);
		}
	}

	/*
	 * Author : A-7688 Date Modified : 29/08/2017 Purpose : Generic method to
	 * get text from a webelement using JavaScript
	 */
	public String getAttributeUsingJavascript(WebElement ele, String eleName,
			String ScreenName, String attributeName) {
		String actValue = "";

		try {

			String act = ((JavascriptExecutor) driver).executeScript(
					"return arguments[0]." + attributeName + ";", ele)
					.toString();

			System.out.println("Returned text of element " + eleName + " : "
					+ act);
			writeExtent("Pass", "Returned text of element " + eleName + " : "
					+ act);
			return act;
		}

		catch (Exception e) {

			e.printStackTrace();
			System.out.println("Could not get text of element " + eleName
					+ " on " + ScreenName + "Screen");
			writeExtent("Fail", "Could not get text of element " + eleName
					+ " on " + ScreenName + "Screen");
		}
		return actValue;

	}

	/*
	 * Author : A-7688 Date Modified : 11/8/2017 Purpose : Enters Value in a
	 * WebElement, takes Xpath SheetName, Locator and element name as argument.
	 * Xpath must end with "_LocatorName"
	 */
	public void enterValueInTextbox(String xpath, String value, String eleName,
			String ScreenName) throws InterruptedException {
		try {
			driver.findElement(By.xpath(xpath)).clear();
			waitForSync(1);
			driver.findElement(By.xpath(xpath)).sendKeys(value);
			writeExtent("Pass", "Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");
			System.out.println("Entered " + value + " as " + eleName + " on "
					+ ScreenName + " Page");

		} catch (Exception e) {
			System.out.println("Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");
			writeExtent("Fail", "Could not enter " + value + " as " + eleName
					+ " on " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not enter " + value + " as "
					+ eleName + " on " + ScreenName + " Page");
		}

	}

	public void clickWebElement(String xpath, String eleName, String ScreenName)
			throws InterruptedException {

		try {

			driver.findElement(By.xpath(xpath)).click();
		} catch (Exception e) {
			System.out.println("Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			// Assert.assertFalse(true, "Could not click on " + eleName + " On "
			// + ScreenName + " Page");
		}
	}

	/**
	 * Description... Returns Selected Option from the Dropdown
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @return Selected Option from the Dropdown
	 */
	public String getFirstSelectedOptionDropdown(String sheetName,
			String locator, String eleName) {
		String actopt = "";
		try {
			By ele = getElement(sheetName, locator);
			WebElement ele1 = driver.findElement(ele);
			Select select = new Select(ele1);

			actopt = select.getFirstSelectedOption().getText();

			writeExtent("Pass", "Returned " + actopt + " as " + eleName
					+ " on " + sheetName + " Screen");
			System.out.println("Entered " + actopt + " as " + eleName
					+ " Text on " + sheetName + " Screen");
			return actopt;
		} catch (Exception e) {

			System.out.println("Could not enter " + actopt + " as " + eleName
					+ " on " + sheetName + " Screen");

			// e.printStackTrace();
			writeExtent("Fail", "Could not enter " + " as " + eleName
					+ " Text on " + sheetName + " Screen");
			Assert.assertFalse(true, "Could not enter " + " as " + eleName
					+ " Text on " + sheetName + " Screen");

		}
		return actopt;
	}

	/**
	 * Description... Returns the size of the list Returns the
	 * 
	 * @param list
	 * @return
	 */
	public int returnListSize(List<WebElement> list) {
		if (!list.isEmpty())
			return list.size();
		else
			return 0;
	}

	/**
	 * Description... Verifies Element Is Enabled or not
	 * 
	 * @param sheetName
	 * @param locator
	 * @param testSteps
	 * @param screenName
	 * @param eleName
	 * @return true if the element is enabled, false otherwise
	 * @throws InterruptedException
	 */
	public boolean verifyElementIsEnabled(String sheetName, String locator,
			String testSteps, String screenName, String eleName,
			String condition) throws InterruptedException {
		WebElement ele1 = null;
		boolean enabled = false;
		try {
			By ele = getElement(sheetName, locator);
			ele1 = driver.findElement(ele);

		} catch (Exception e) {
			System.out.println("Could not find " + eleName + " on " + sheetName
					+ " Screen");

			// e.printStackTrace();
			writeExtent("Fail", "Could not find " + eleName + " on "
					+ sheetName + " Screen");
			Assert.assertFalse(true, "Could not find " + eleName + " on "
					+ sheetName + " Screen");

		}

		switch (condition) {

		case "enable":

			if (ele1.isEnabled()) {
				customFunction.onPassUpdate(screenName,
						eleName + " is Enabled", eleName + " is Enabled",
						eleName + " is Enabled", testSteps);
				enabled = true;

			}

			else {
				customFunction.onFailUpdate(screenName, eleName
						+ "is Not Enabled", eleName + "is Not Displayed",
						eleName + " is Not Enabled", testSteps);

				enabled = false;
			}

			break;

		case "disable":

			if (!ele1.isEnabled()) {
				customFunction.onPassUpdate(screenName, eleName
						+ " is disabled", eleName + " is disabled", eleName
						+ " is disabled", testSteps);
				enabled = false;

			}

			else {
				customFunction.onFailUpdate(screenName, eleName
						+ " is Not Enabled", eleName + " is  Enabled ", eleName
						+ " is Enabled", testSteps);

				enabled = true;

			}

		}

		return enabled;

	}

	/**
	 * Description... Verifies that the values sent as parameter is not Null
	 * 
	 * @param actValue
	 * @param functionalityname
	 * @throws InterruptedException
	 */
	public void verifyValueNotNull(String actValue, String functionalityname)
			throws InterruptedException {
		waitForSync(2);

		if (!(actValue == null)) {
			test.log(LogStatus.INFO, "Value found for " + functionalityname
					+ " is " + actValue);

		} else {
			test.log(LogStatus.INFO, "Null values are found for "
					+ functionalityname);
		}
	}

	/**
	 * 
	 * @param sheetName
	 * @param locator
	 * @param testSteps
	 * @param screenName
	 * @param eleName
	 * @return true if the element is enabled, false otherwise
	 * @throws InterruptedException
	 */
	public boolean verifyElementIsEnabled(String sheetName, String locator,
			String testSteps, String screenName, String eleName)
			throws InterruptedException {
		WebElement ele1 = null;
		boolean enabled = false;
		try {

			By ele = getElement(sheetName, locator);
			ele1 = driver.findElement(ele);
			if (ele1.isEnabled()) {
				customFunction.onPassUpdate(screenName, eleName
						+ " is Not Displayed", eleName + " is Enabled", eleName
						+ " is Displayed", testSteps);
				enabled = true;

			}

			else
				customFunction.onFailUpdate(screenName, eleName
						+ " is Not Enabled", eleName + " is Not Displayed",
						eleName + " is Not Enabled", testSteps);
			enabled = false;

		} catch (Exception e) {
			System.out.println("Could not find " + eleName + " on " + sheetName
					+ " Screen");

			// e.printStackTrace();
			writeExtent("Fail", "Could not find " + eleName + " on "
					+ sheetName + " Screen");
			Assert.assertFalse(true, "Could not find " + eleName + " on "
					+ sheetName + " Screen");

		}
		return enabled;

	}

	/**
	 * 
	 * Description... Finds the innertext of an element and passes if it is null
	 * and fails otherwise
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param ScreenName
	 * @param testSteps
	 * @throws InterruptedException
	 */
	public void verifyNullValues(String sheetName, String locator,
			String eleName, String ScreenName, String testSteps) throws

	InterruptedException {
		String actValue = getElementText(sheetName, locator, eleName,
				ScreenName);
		if (actValue.replace(" ", "").equals(""))

			onPassUpdate(ScreenName, "No Value", "No Value",
					"No Value in the field", testSteps);

		else

			onFailUpdate(ScreenName, "No Value", "No Value",
					"No Value in the field", testSteps);

	}

	/**
	 * Description... Gets the innertext of an element and verifies
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param ScreenName
	 * @param testSteps
	 * @param expText
	 * @param option
	 * @throws InterruptedException
	 */
	public void getTextAndVerify(String sheetName, String locator,
			String eleName, String ScreenName, String testSteps, String

			expText, String option) throws InterruptedException {

		String actText = getElementText(sheetName, locator, eleName, ScreenName);

		switch (option) {

		case "equals":
			if (actText.equals(expText))
				onPassUpdate(ScreenName, expText, actText, eleName + "Text",
						testSteps);
			else
				onFailUpdate(ScreenName, expText, actText, eleName + "Text",
						testSteps);
			break;

		case "contains":
			if (actText.contains(expText))
				onPassUpdate(ScreenName, expText, actText, eleName + "Text",
						testSteps);
			else
				onFailUpdate(ScreenName, expText, actText, eleName + "Text",
						testSteps);
			break;
		}
	}

	/**
	 * Description... Checks whether an element is displayed and logs the result
	 * in custom report
	 * 
	 * @param sheetName
	 * @param eleXpath
	 * @param testSteps
	 * @param screenName
	 * @param eleName
	 * @throws InterruptedException
	 */
	/*
	 * Author : A-7290 Date Modified : 18/8/2017 Purpose : Common Method to
	 * Verify Element displayed in UI page or not
	 */
	public boolean verifyElementDisplayed(String xpath, String testSteps,
			String screenName, String eleName) throws InterruptedException {
		
		try
		{

		if (driver.findElement(By.xpath(xpath)).isDisplayed()) {
			customFunction.onPassUpdate(screenName, eleName + " is Displayed",
					eleName + " is Displayed", eleName + " is Displayed",
					testSteps);
			return true;

		} else {
			Status = false;
			customFunction.onFailUpdate(screenName, eleName + " is Displayed",
					eleName + " is Not Displayed", eleName + " is Displayed",
					testSteps);
			return false;
		}
		}
		
		catch(Exception e)
		{
			Status = false;
			customFunction.onFailUpdate(screenName, eleName + " is Displayed",
					eleName + " is Not Displayed", eleName + " is Displayed",
					testSteps);
			return false;
		}

	}

	public void javaScriptToEnterValueInTextBoxWithTAB(String sheetName,
			String locator, String value, String elename, String ScreenName) {

		try {
			waitForSync(2);
			By b = getElement(sheetName, locator);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(b));
			wait.until(ExpectedConditions.elementToBeClickable(b));
			ele = driver.findElement(b);

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].value='" + value + "'", ele);
			writeExtent("Pass", "Entered " + value + " On " + elename
					+ " On Page " + ScreenName);
			System.out.println("Entered " + value + " On " + elename
					+ " On Page " + ScreenName);
			ele.sendKeys(Keys.TAB);

		} catch (Exception e) {
			System.out.println("Could not Enter " + value + " On " + elename
					+ " On Page " + ScreenName);
			writeExtent("Fail", "Could not Enter " + value + " On " + elename
					+ " On Page " + ScreenName);
			Assert.assertFalse(true, "Could not Enter " + value + " On "
					+ elename + " On Page " + ScreenName);
		}
	}

	// get element text without switch to report container frame
	public String getElementTextnoFrameSwitch(String sheetName, String locator,
			String eleName, String pageName) throws InterruptedException {

		waitForSync(2);
		try {

			By b = getElement(sheetName, locator);
			String text = driver.findElement(b).getText();
			writeExtent("Pass", "Returned text of element " + eleName + " : "
					+ text);

			return text;

		} catch (Exception e) {
			System.out.println("Could not return text from element " + eleName
					+ " on " + pageName);
			writeExtent("Fail", "Could not return text from element " + eleName
					+ " on " + pageName);
			return "";
		}

	}
	/**
	 * @author A-9847
	 * @Desc To verify whether the given element is enabled or not
	 * @param sheetName
	 * @param eleXpath
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyElementEnabled(String sheetName, String eleXpath)throws InterruptedException {

		try{		
	    By b = getElement(sheetName, eleXpath,false);
	    if(driver.findElement(b).isEnabled())
		return true;
	    else
	    return false;
		}	    
	    catch(Exception e){
		    return false;	
		}
		
	}

	public void clickWebElementByWebDriver(String sheetName, String locator,
			String eleName, String ScreenName) throws InterruptedException {

		try {

			By element = getElement(sheetName, locator);
			driver.findElement(element).click();
			writeExtent("Pass", "Clicked on " + eleName + " On "
					+ ScreenName + " Page");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
		}
	}

	public int getWidth(String xpath, String eleName, String ScreenName) {
		int d = 0;
		try {

			d = driver.findElement(By.xpath(xpath)).getSize().getWidth();

		} catch (Exception e) {
			System.out.println("Could not find size of " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not find size of " + eleName + " On "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not find size of " + eleName
					+ " On " + ScreenName + " Page");
		}
		return d;
	}

	/***
	 * Description : This method can be used to get the height of any
	 * element(field/button etc) on screen
	 * 
	 * @param xpath
	 *            = xpath of the element for which we need to find width
	 * @param eleNmae
	 *            = name of the element
	 * @param screenName
	 *            = name of the screen
	 * @return it returns height of the element in integer
	 * 
	 * 
	 * @author A-8468 on 18/02/2019
	 * 
	 ***/
	public int getHeight(String xpath, String eleName, String ScreenName) {
		int d = 0;
		try {

			d = driver.findElement(By.xpath(xpath)).getSize().getHeight();

		} catch (Exception e) {
			System.out.println("Could not find size of " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not find size of " + eleName + " On "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not find size of " + eleName
					+ " On " + ScreenName + " Page");
		}
		return d;
	}

	public void verifyUrl(String expectedUrl, String screenName) {
		try {

			String actualUrl = driver.getCurrentUrl();
			if (actualUrl.contains(expectedUrl)) {

				System.out.println("Sucessfully verified " + expectedUrl
						+ " on " + screenName + " Page");
				writeExtent("Fail", "Sucessfully verified " + expectedUrl
						+ " on " + screenName + " Page");

			} else {

				System.out.println("Expected Url is " + expectedUrl + " on "
						+ screenName + " Page");
				System.out.println("Actual Url is " + actualUrl + " on "
						+ screenName + " Page");
				writeExtent("Fail", "Could not verify " + expectedUrl + " on "
						+ screenName + " Page");
				Assert.assertFalse(true, "Could not verify " + expectedUrl
						+ " on " + screenName + " Page");
			}

		} catch (Exception e) {

			System.out.println("Could not verify " + expectedUrl + " on "
					+ screenName + " Page");
			writeExtent("Fail", "Could not verify " + expectedUrl + " on "
					+ screenName + " Page");
			Assert.assertFalse(true, "Could not verify " + expectedUrl + " on "
					+ screenName + " Page");

		}
	}

	/**** Click web element by action class ***/

	public void clickWebElementByActionClass(String sheetName, String locator,
			String eleName, String ScreenName) throws InterruptedException {

		try {
			Actions actionDriver = new Actions(driver);
			By element = getElement(sheetName, locator);
			WebElement elementtobeClicked = driver.findElement(element);

			actionDriver.moveToElement(elementtobeClicked).click().perform();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
		}
	}

	public void verifyTitle(String expectedTitle, String screenName) {
		try {

			String actualTitle = driver.getTitle();
			if (actualTitle.contains(expectedTitle)) {

				System.out.println("Sucessfully verified " + expectedTitle
						+ " on " + screenName + " Page");
				writeExtent("Fail", "Sucessfully verified " + expectedTitle
						+ " on " + screenName + " Page");

			} else {

				System.out.println("Expected Title is " + expectedTitle
						+ " on " + screenName + " Page");
				System.out.println("Actual Title is " + actualTitle + " on "
						+ screenName + " Page");
				writeExtent("Fail", "Could not verify " + expectedTitle
						+ " on " + screenName + " Page");
				Assert.assertFalse(true, "Could not verify " + expectedTitle
						+ " on " + screenName + " Page");
			}

		} catch (Exception e) {

			System.out.println("Could not verify " + expectedTitle + " on "
					+ screenName + " Page");
			writeExtent("Fail", "Could not verify " + expectedTitle + " on "
					+ screenName + " Page");
			Assert.assertFalse(true, "Could not verify " + expectedTitle
					+ " on " + screenName + " Page");

		}
	}

	public String getFirstSelectedOptionDropdown(String xpath, String eleName) {
		String actopt = "";
		try {

			WebElement ele1 = driver.findElement(By.xpath(xpath));
			Select select = new Select(ele1);

			actopt = select.getFirstSelectedOption().getText();

			// writeExtent("Pass", "Returned " + actopt + " as " + eleName + "
			// on " + sheetName + " Screen");
			// System.out.println("Entered " + actopt + " as " + eleName + "
			// Text on " + sheetName + " Screen");
			return actopt;
		} catch (Exception e) {

			// System.out.println("Could not enter " + actopt + " as " + eleName
			// + " on " + sheetName + " Screen");

			// e.printStackTrace();
			// writeExtent("Fail", "Could not enter " + " as " + eleName + "
			// Text on " + sheetName + " Screen");
			// Assert.assertFalse(true, "Could not enter " + " as " + eleName +
			// " Text on " + sheetName + " Screen");

		}
		return actopt;
	}

	public void verifyNullValues(WebElement ele, String eleName,
			String ScreenName) throws InterruptedException {

		String actValue = getElementText(ele, eleName, pageName);
		if (actValue.replace(" ", "").equals(""))

			onPassUpdate(ScreenName, "No Value", "No Value",
					"No Value in the field", testSteps);

		else

			onFailUpdate(ScreenName, "No Value", "No Value",
					"No Value in the field", testSteps);

	}

	/**
	 * Description... Clicks webelement by Actions class for dynamic xpath
	 * 
	 * @throws InterruptedException
	 */
	public void clickWebElementByActionClass(String xpath, String eleName,
			String ScreenName) throws InterruptedException {

		try {
			Actions actionDriver = new Actions(driver);

			WebElement elementtobeClicked = driver.findElement(By.xpath(xpath));

			actionDriver.moveToElement(elementtobeClicked).click().perform();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			writeExtent("Fail", "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
			Assert.assertFalse(true, "Could not click on " + eleName + " On "
					+ ScreenName + " Page");
		}
	}

	/**
	 * Description... Returns the arraylist objects when string parameters are
	 * passed
	 * 
	 * @param entry
	 * @return
	 */
	public ArrayList getStringList(String... entry) {
		ArrayList<String> list = new ArrayList();
		for (String lst : entry)
			list.add(lst);
		return list;
	}

	/**
	 * Description... Selects value from the list of dropdowns
	 * 
	 * @param sheetName
	 * @param locator
	 * @param option
	 * @param selectBy
	 * @param eleName
	 */
	public void selectValueInListOfDropdown(String sheetName, String locator,
			List<String> option, List<String> selectBy, String

			eleName) {
		List<WebElement> list = returnListOfElements(sheetName, locator);
		for (int i = 0; i < list.size(); i++) {
			try {
				if (list.get(i).isDisplayed())
					selectValueInDropdownWebElement(list.get(i), option.get(i),
							eleName, selectBy.get(i));
			} catch (Exception e) {

			}
		}

	}

	/**
	 * Description... Selects value in dropdown taking a webelement
	 * 
	 * @param ele
	 * @param option
	 * @param eleName
	 * @param selectBy
	 */
	public void selectValueInDropdownWebElement(WebElement ele, String option,
			String eleName, String selectBy) {
		try {

			Select select = new Select(ele);

			switch (selectBy) {
			case "Value": {
				String actopt = select.getFirstSelectedOption().getText();
				if (!actopt.equalsIgnoreCase(option))
					select.selectByValue(option);

			}
				break;
			case "VisibleText": {
				String actopt = select.getFirstSelectedOption().getText();
				if (!actopt.equalsIgnoreCase(option))
					select.selectByVisibleText(option);

			}
				break;
			case "Index": {
				int index = Integer.parseInt(option);
				String actopt = select.getFirstSelectedOption().getText();
				if (!actopt.equalsIgnoreCase(option))
					select.selectByIndex(index);

			}
				break;

			}
			writeExtent("Pass", "Entered " + option + " as " + eleName);
			System.out.println("Pass Entered " + option + " as " + eleName);

		} catch (Exception e) {

			e.printStackTrace();
			writeExtent("Fail", "Could not enter " + option + " as " + eleName);
			Assert.assertFalse(true, "Could not enter " + option + " as "
					+ eleName);

		}
	}

	public String getFirstSelectedOptionDropdown(WebElement ele,
			String screenName) {
		String actopt = "";
		try {

			Select select = new Select(ele);

			actopt = select.getFirstSelectedOption().getText();

			writeExtent("Pass", "Returned " + actopt + " as " + eleName
					+ " on " + screenName + " Screen");
			System.out.println("Entered " + actopt + " as " + eleName
					+ " Text on " + screenName + " Screen");
			return actopt;
		} catch (Exception e) {

			System.out.println("Could not enter " + actopt + " as " + eleName
					+ " on " + screenName + " Screen");

			e.printStackTrace();
			writeExtent("Fail", "Could not enter " + " as " + eleName
					+ " Text on " + screenName + " Screen");
			Assert.assertFalse(true, "Could not enter " + " as " + eleName
					+ " Text on " + screenName + " Screen");

		}
		return actopt;
	}

	/*
	 * Author : A-7688 Date Modified : 11/8/2017 Purpose : Enters Value in a
	 * WebElement, takes Xpath SheetName, Locator and element name as argument.
	 * Xpath must end with "_LocatorName"
	 */

	public void verifyMapEqual(Map expMap, Map actMap, String valueName,
			String ScreenName) {
		if (expMap.equals(actMap)) {
			System.out.println("Successfully verified " + valueName + " on "
					+ ScreenName);
			writeExtent("Pass", "Successfully verified " + valueName + " on "
					+ ScreenName);

		} else {
			System.out.println("Failed to verify " + valueName + " on "
					+ ScreenName);
			writeExtent("Fail", "Failed to verify " + valueName + " on "
					+ ScreenName);
			Assert.assertFalse(true, "Failed to verify " + valueName + " on "
					+ ScreenName);

		}
	}

	/**
	 * Description... Store the parent window handle, clicks on the Add Button
	 * and switches to the child window
	 * 
	 * @throws Exception
	 */
	public void clickButtonSwitchToSecondWindow(String sheetName,
			String locator, String screenName, String eleName) throws Exception {

		switchToWindow("storeFirstChild");
		clickWebElement(sheetName, locator, eleName, screenName);

		switchToWindow("childWindow2");
	}

	public List<String> returnTextScrollListOfElements(List<WebElement> list,
			String ElementName, String ScreenName) throws InterruptedException {
		List<String> stringList = new ArrayList();
		for (WebElement ele : list)

		{
			stringList.add(ele.getText());
			performKeyActions(ele, "DOWN", ElementName, ScreenName);

		}

		return stringList;

	}

	public HashMap returnAttributeListOfElements(List<WebElement> listGEN,
			String subList[], String attrName) {
		ArrayList<WebElement> list = new ArrayList(listGEN);
		HashMap map2 = new HashMap();

		for (int i = 0; i < subList.length; i++) {

			map2.put(subList[i], listGEN.get(i).getAttribute(attrName));
		}
		return map2;

	}

	/**
	 * Description... Returns the Value attribute for the list of elements
	 * 
	 * @param list
	 * @param attributeName
	 * @return
	 */
	public List<String> returnValueListOfElements(List<WebElement> list,
			String attributeName) {
		List<String> stringList = new ArrayList();
		for (WebElement ele : list)
			stringList.add(ele.getAttribute(attributeName));
		return stringList;

	}

	/**
	 * Description... Perform the Keyboard actions
	 * 
	 * @param key
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void robotKey(String key) throws AWTException, InterruptedException {
		keyPress(key);
		keyRelease(key);
		waitForSync(3);
	}

	/**
	 * Description... Perform the Keyboard actions
	 * 
	 * @param key1
	 * @param key2
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void robotKey2(String key1, String key2) throws AWTException,
			InterruptedException {
		keyPress(key1);
		keyPress(key2);
		keyRelease(key2);
		keyRelease(key1);
		waitForSync(3);
	}
}