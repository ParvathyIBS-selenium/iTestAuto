package screens;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class Cgocxml extends CustomFunctions {
	
	String sheetName = "cgocxml_screen";
	String screenName = "cgocxml screen";
	

	public Cgocxml(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	
	public void setMessage(String filePath) throws InterruptedException
	{
		    clearText("cgomon_screen", "txtarea_message;xpath", "Message", screenName);
		    // read text file to HashMap
	         String mapFromFile = HashMapFromTextFile(filePath);
	        setValueInTextbox("cgomon_screen", "txtarea_message;xpath", mapFromFile, "Message", screenName);
	        waitForSync(2);
	}
	/**
	 * @author A-9175
	 * Description : Sending created message by selecting option
	 */
	public void sendMessageCgoCXML(String option) throws InterruptedException, IOException {
		/****clickWebElement(sheetName, "btn_msgLoader;xpath", "Message Loader Button", screenName);
		selectValueInDropdown(sheetName, "select_system;id", option, "End System","VisibleText");
		clearText(sheetName, "txtarea_msg;id", "Message", screenName);
		System.out.println(parameters.get("messageLine"));
		setValueInTextbox(sheetName, "txtarea_msg;id", parameters.get("messageLine"), "Message", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_sendMsg;xpath", "Send Button", screenName);
		waitForSync(5);
		ele = findDynamicXpathElement("txt_sentMsgSuccess;xpath", sheetName, "Message loaded", screenName);
		try {
			String actText = ele.getText();

			String expText = "Message loaded";

			verifyScreenText(" CGOCXML ", actText, expText, " Message sent", " Message sent");

		} catch (Exception e) {
			
				test.log(LogStatus.FAIL, "Could not send message from CGOCXML screen");
				System.out.println("Could not send message from CGOCXML screen");

		}****/
		
        String screen = "cgomon screen";
        String sheet="cgomon_screen";

        if(driver.findElements(By.xpath(xls_Read.getCellValue(sheet, "txtarea_message;xpath"))).size()!=1)
        {      
               clickWebElement(sheet, "btn_menu;xpath", "Menu Button", screen);
               waitForSync(1);     
               if(!driver.findElement(By.xpath(xls_Read.getCellValue(sheet, "txt_msgLoader;xpath"))).isDisplayed()) 
                     clickWebElement(sheet, "txt_cargoSetting;xpath", "Cargo setting Button", screen);
               waitForSync(1);
               clickWebElement(sheet, "txt_msgLoader;xpath", "Message Loader Button", screen);
               waitForSync(1);


               //Selecting the channel
               clickWebElement(sheet, "drpdn_channel;xpath", "Channel DropDown", screen);
               waitForSync(2);
               String locator=xls_Read.getCellValue(sheet, "txt_channel;xpath");
               locator=locator.replace("*", option);
               moveScrollBar(driver.findElement(By.xpath(locator)));
               waitForSync(1);
               driver.findElement(By.xpath(locator)).click();
        }

        clearText(sheet, "txtarea_message;xpath", "Message", screen);
        System.out.println(parameters.get("messageLine"));
        setValueInTextbox(sheet, "txtarea_message;xpath", parameters.get("messageLine"), "Message", screen);
        waitForSync(2);
        clickWebElement(sheet, "btn_sendMsg;xpath", "Send Button", screen);
        waitForSync(5);
        String locator1=xls_Read.getCellValue(sheet, "txt_sentMsgSuccess;xpath");
        try {
               String actText = driver.findElement((By.xpath(locator1))).getText();
               System.out.println(actText);
               String expText = "Message sent";

               verifyScreenText(" CGOMON ", actText, expText, " Message sent", " Message sent");

        } catch (Exception e) {

               test.log(LogStatus.FAIL, "Could not send message from CGOMON screen");
               System.out.println("Could not send message from CGOMON screen");

        }



	}
	/**
	 * @author A-9175
	 * Description : Sending created message by selecting option
	 */
	public void sendMessage(String option) throws InterruptedException, IOException {
		
		
        String screen = "cgomon screen";
        String sheet="cgomon_screen";

        if(driver.findElements(By.xpath(xls_Read.getCellValue(sheet, "txtarea_message;xpath"))).size()!=1)
        {      
               clickWebElement(sheet, "btn_menu;xpath", "Menu Button", screen);
               waitForSync(1);     
               if(!driver.findElement(By.xpath(xls_Read.getCellValue(sheet, "txt_msgLoader;xpath"))).isDisplayed()) 
                     clickWebElement(sheet, "txt_cargoSetting;xpath", "Cargo setting Button", screen);
               waitForSync(1);
               clickWebElement(sheet, "txt_msgLoader;xpath", "Message Loader Button", screen);
               waitForSync(1);


               //Selecting the channel
               clickWebElement(sheet, "drpdn_channel;xpath", "Channel DropDown", screen);
               waitForSync(2);
               String locator=xls_Read.getCellValue(sheet, "txt_channel;xpath");
               locator=locator.replace("*", option);
               moveScrollBar(driver.findElement(By.xpath(locator)));
               waitForSync(1);
               driver.findElement(By.xpath(locator)).click();
        }

     



	}
	
	/**
	 * 
	 * @param filePath
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc - update message in text area
	 */
	public void loadMessage(String filePath) throws InterruptedException, IOException
	{
		 String screen = "cgomon screen";
	        String sheet="cgomon_screen";
	        
	        
		   clearText(sheet, "txtarea_message;xpath", "Message", screen);
	       
	        setMessage(filePath);
	        waitForSync(2);
	        clickWebElement(sheet, "btn_sendMsg;xpath", "Send Button", screen);
	        waitForSync(5);
	        String locator1=xls_Read.getCellValue(sheet, "txt_sentMsgSuccess;xpath");
	        try {
	               String actText = driver.findElement((By.xpath(locator1))).getText();
	               System.out.println(actText);
	               String expText = "Message sent";

	               verifyScreenText(" CGOMON ", actText, expText, " Message sent", " Message sent");

	        } catch (Exception e) {

	               test.log(LogStatus.FAIL, "Could not send message from CGOMON screen");
	               System.out.println("Could not send message from CGOMON screen");

	        }
	}
	
	/**
	 * Desc: click method loader
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickMessageLoader() throws InterruptedException, IOException {
		/***waitForSync(10);
		clickWebElement(sheetName, "btn_msgLoader;xpath", " Message Loader ", screenName);
		waitForSync(1);***/
}

}
