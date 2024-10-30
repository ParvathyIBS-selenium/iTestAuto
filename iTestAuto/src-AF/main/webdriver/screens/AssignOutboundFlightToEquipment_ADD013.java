package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AssignOutboundFlightToEquipment_ADD013 extends CustomFunctions{
	String sheetName = "AssignOutboundFlight_ADD013";
	String screenName = "AssignOutboundFlightToEquipment";
	String screenId = "ADD013";
	
	public AssignOutboundFlightToEquipment_ADD013(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) 
	{
		super(driver, excelReadWrite, xls_Read2);
	}
	
	/**
	 * @author A-10330
	 * @Desc To enter the from date
	 * @param toDate
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	
	public void enterFromDate(String fromdate) throws InterruptedException, AWTException
	{
		waitTillScreenload(sheetName, "inbx_fromdate;id","From date field", screenName);
		
		enterValueInTextbox(sheetName, "inbx_fromdate;id",data(fromdate), "fromdate", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		
	}
	
	/**
	 * @author A-10330
	 * @Desc To enter the to date
	 * @param toDate
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	
	public void enterToDate(String toDate) throws InterruptedException, AWTException
	{
		
		enterValueInTextbox(sheetName, "inbx_todate;id",data(toDate), "fromdate", screenName);
		waitForSync(2);
		keyPress("TAB");
		keyRelease("TAB");
		
	}
	
	/**
	 * @author A-10330
	 * @Desc To enter the flightCode
	 * @throws InterruptedException
	 */
	
	public void enterFlightCode(String flightCode) throws InterruptedException
	{
		
		enterValueInTextbox(sheetName, "inbx_carriercode;id",data(flightCode), "flightCode", screenName);
	}
	
	/**
	 * @author A-10330
	 * @Desc To enter the flightNum
	 * @param flightNum
	 * @throws InterruptedException
	*/
	
	public void enterFlightNum(String flightNum) throws InterruptedException
	{
		
		enterValueInTextbox(sheetName, "inbx_flighnum;id",data(flightNum), "flightCode", screenName);
	}
	
	/**
	 * @author A-10330
	 * @Desc click on list button
	 * @throws InterruptedException
	*/
	
	public void clickList() throws InterruptedException
	{
		clickWebElementByWebDriver(sheetName, "btn_list;id", "List Button", screenName);
		waitForSync(1);
	}
	
	/**
	 * @author A-10330
	 * @Desc select Equipment Type
	 * @param equipment,equipmentType
	 */
	
	public void selectEquipmentType(String equipment,String equipmentType)
	{
		try
		{
			
			waitTillScreenload(sheetName, "anchor_equipmentlink;xpath","ADD equipment link", screenName);
			
			clickWebElementByWebDriver(sheetName, "anchor_equipmentlink;xpath", "ADD equipment link", screenName);
			
			waitForSync(1);
			
		if(equipment.equals("TOP"))
		{
			String locator = xls_Read.getCellValue(sheetName, "inputcheckbox_equipmenttype;xpath");
			locator=locator.replace("*",data(equipmentType));
			
			WebElement e1=driver.findElement(By.xpath(locator));
			
			moveScrollBar(e1);
			
			driver.findElement(By.xpath(locator)).click();
			
			clickWebElementByWebDriver(sheetName, "btn_chooseequipmenttype;id", "OK equipment button", screenName);
		 }
		else if(equipment.equals("RFS"))
		{
			String locator = xls_Read.getCellValue(sheetName, "inputcheckbox_equipmenttype1;xpath");
			locator=locator.replace("*",data(equipmentType));
            WebElement e1=driver.findElement(By.xpath(locator));
			
            moveScrollBar(e1);
			
			driver.findElement(By.xpath(locator)).click();
			
			clickWebElementByWebDriver(sheetName, "btn_chooseequipmenttype;id", "OK equipment button", screenName);
	    }
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Could not Assign equipment" + equipment + " to outbound flight  On"
					+ screenName + " Page");
		}
	}
	
	/**
	 * @author A-10330
	 * @Desc verifyEquipmentType
	 * @param equipmentType
	 */
	
	public void  verifyEquipmentTypeAssigned(String equipmentType) throws InterruptedException
	{
		waitForSync(2);
		verifyElementDisplayed(sheetName, "div_equipmenttype;xpath", "equipment type selected", screenName, " equipment Type"+data(equipmentType)+ "selected");
	}
	

}
