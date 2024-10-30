package screens;

import org.testng.Assert;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class StorageUnitListing_WHS006 extends CustomFunctions {
	public StorageUnitListing_WHS006(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "StorageUnitListing_WHS006";
	public String ScreenName = "StorageUnitListing_WHS006";

	/**
 * @author A-9478
 * Desc : Enter SU Type
 * @throws InterruptedException 
 * @throws IOException 
 */
	public void enterSUType(String SUType) throws InterruptedException, IOException
	{
		
		enterValueInTextbox(sheetName, "inbx_SUType;id", data(SUType), "SU Type",
				ScreenName);		
	}
	
	/**
	 * @author A-9478
	 * Desc : click list
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
		public void clickList() throws InterruptedException, IOException
		{
						
			clickWebElement(sheetName, "btn_list;id", "List Button", ScreenName);
			waitForSync(4);

		}
	
	/**
	 * @author A-9478
	 * Desc : Select Occupany Status
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
		public void selectOccupancyStatus(String Status) throws InterruptedException, IOException
		{
			
			selectValueInDropdown(sheetName, "lst_OccupancyStatus;id",
					Status, "Occupancy Status", "VisibleText");			
		}
		
	/**
	 * @author A-9478
	 * Desc: Return SU 
	 * 
	 */
	public String getStorageUnit()
	{
		String attValue="";
		try
		{
			String locator = xls_Read.getCellValue(sheetName, "txt_getStorageUnit;xpath");
			attValue = driver.findElement(By.xpath(locator)).getAttribute("value");
			writeExtent("Pass", "Fetched storage unit "+attValue+" In "+ScreenName);			
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Unable to fetch storage unit value in "+ScreenName);
		}
		return attValue;
	}
		
		
	

}