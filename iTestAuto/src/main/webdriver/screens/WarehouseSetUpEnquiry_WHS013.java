package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;


public class WarehouseSetUpEnquiry_WHS013 extends CustomFunctions {

	String sheetName = "WarehouseSetUpEnquiry_WHS013";
	String screenName = "Warehouse Setup Enquiry: WHS013 ";
	String screenId="WHS013";


	public WarehouseSetUpEnquiry_WHS013(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
/**
 * Description... Enter Location Type, Select Occupancy Status and click on List Button
 * @param locationType
 * @param occupancyStatus
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void lisLocationDetails(String locationType,String occupancyStatus) throws InterruptedException, AWTException, IOException {


		enterValueInTextbox(sheetName, "inbx_locationType;xpath",data(locationType), "Location Type", screenId);
		selectValueInDropdown(sheetName,"lst_occupancyStatus;xpath",occupancyStatus, "Select Occupancy Status", "VisibleText");
		clickWebElement(sheetName, "btn_list;xpath", "List Button",screenId);

	}
	/**Description...get empty location under the corresponding zone
	 * * @param zone
	 * @param occupancyStatus
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void getEmptyLocation(String Zone,String occupancyStatus,String newLocation) throws InterruptedException, IOException
	{

	clickWebElement(sheetName, "lst_zone;xpath", "Zone Button",screenId);
	enterValueInTextbox(sheetName, "lst_zone;xpath",data(Zone), "Zone button", screenId);
	selectValueInDropdown(sheetName,"lst_occupancyStatus;xpath",occupancyStatus, "Select Occupancy Status", "VisibleText");
	clickWebElement(sheetName, "btn_list;xpath", "List Button",screenId);
	waitForSync(5);
	String location=(driver.findElement(By.xpath("(//td[@class='iCargoTableTd']//following-sibling::td)[1]"))).getText();
	map.put(newLocation, location);
	}


	
	/**
	 * Description... Click Clear Button
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void clickClear() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_clear;xpath", "Clear Button",screenId);	
		waitForSync(2);

	}
	/**
	 * Description... Enter Location 
	 * @param location
	 * @throws InterruptedException 
	 */
	public void enterLocation(String location) throws InterruptedException  {

		enterValueInTextbox(sheetName, "inbx_location;xpath",data(location), "Location Type", screenId);	

	}
	/**
	 * Description... Click List Button
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void clickList() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_list;xpath", "List Button",screenId);	
		waitForSync(2);

	}
	/**
	 * Description... Get Zone Code
	 * @return
	 * @throws InterruptedException
	 */
	public String getZoneCode() throws InterruptedException
	{
		String zoneCode=getElementText(sheetName,"inbx_zone;xpath",	"Zone Code", screenId);
		return zoneCode;
	}
	/**
	 * Description... To verify zone 
	 */   
	public void verifyZone(String actZone,String expZone){  

		if (actZone.trim().contains((expZone.trim())))
			writeExtent("Pass", "Verified the zone value for the location on " + screenName);
		else
			writeExtent("Fail", "Zone displayed for the location is incorrect on" + screenName);

	}

/**
 * Description... Get Location Code
 * @return
 * @throws InterruptedException
 */
	public String getLocationCode() throws InterruptedException
	{
		String locationCode=getElementText(sheetName,"htmlDiv_location;xpath",	"Location Code", screenId);
			
		return locationCode;
	}
}
