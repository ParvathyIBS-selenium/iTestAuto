package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class MaintainStationAllotment_CAP012 extends CustomFunctions{

	public MaintainStationAllotment_CAP012(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		// TODO Auto-generated constructor stub
	}
	
	public String sheetName="MaintainStationAllotment_CAP012";
	public String ScreenName="MaintainStationAllotment";
	String GenericSheet = "Generic_Elements";
	
	
	/**
	 * @author A-8783
	 * Desc..enter station code
	 * @param station
	 * @throws InterruptedException
	 */
	public void enterStationCode(String station) throws InterruptedException{

		enterValueInTextbox(sheetName, "inbx_station;name", data(station), "station code", ScreenName);
		
	}
	
	/**
	 * @author A-8783
	 * Desc..enter flight details
	 * @param carrierCode
	 * @param flightNo
	 * @param origin
	 * @param destination
	 * @throws InterruptedException
	 *
	 */
	public void enterFlightDetails(String carrierCode, String flightNo, String origin, String destination) throws InterruptedException{

		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(carrierCode), "Carrier code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(flightNo), "Flight No", ScreenName);
		enterValueInTextbox(sheetName, "inbx_origin;name", data(origin), "Origin", ScreenName);
		enterValueInTextbox(sheetName, "inbx_destination;name", data(destination), "Destination", ScreenName);
		
	}
	
	/**
	 * @author A-8783
	 * Desc..enter flight start and end date
	 * @param startDate
	 * @param endDate
	 * @throws InterruptedException
	 * 
	 */
	public void enterAllotmentRange(String startDate, String endDate) throws InterruptedException{

		enterValueInTextbox(sheetName, "inbx_startDate;name", startDate, "Flight start date", ScreenName);
		enterValueInTextbox(sheetName, "inbx_endDate;name", endDate, "Flight end date", ScreenName);
		
		
	}
	
	/**
	 * @author A-8783
	 * Desc..enter frequency of flight
	 * @throws InterruptedException
	 * @throws IOException 
	 * 
	 */
	public void enterFrequency() throws InterruptedException, IOException{

		clickWebElement(sheetName, "chbx_freqAll;name", "Check frequency", ScreenName);
		
		
	}

	/**
	 * @author A-8783
	 * Desc..enter category code
	 * @param category
	 * @throws InterruptedException
	 * 
	 */
	public void enterCategory(String category) throws InterruptedException{

		enterValueInTextbox(sheetName, "inbx_category;name", data(category), "Category code", ScreenName);
		
		
	}
	
	/**
	 * @author A-8783
	 * Desc..enter capacity details
	 * @param startDate
	 * @param endDate
	 * @throws InterruptedException
	 * 
	 */
	public void enterCapacity(String weight, String volume) throws InterruptedException{

		enterValueInTextbox(sheetName, "inbx_weight;name", data(weight), "Weight", ScreenName);
		enterValueInTextbox(sheetName, "inbx_volume;name", data(volume), "Volume", ScreenName);
		
		
	}
	/**
	 * @author A-8783
	 * @param AllotmentID
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void saveDetails(String AllotmentID) throws InterruptedException, AWTException, IOException {
		
		clickWebElement(sheetName, "btn_save;name", "Save Button", ScreenName);
		Thread.sleep(2000);
		switchToFrame("default");
		By ele =  getElement(sheetName,"saveInfo;xpath");
		String actText = driver.findElement(ele).getText();
		verifyScreenText(ScreenName, "created", actText,"Save details", "Verify station allotment is created");

		clickWebElement("Generic_Elements", "btn_yes;xpath", "Ok Button", ScreenName);
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameCAP012");
		
		String locator=xls_Read.getCellValue(sheetName, "inbx_allotmentId;name");
		WebElement ele1=driver.findElement(By.name(locator));
		
		String allotmentID = getAttributeWebElement(ele1, "Allotment ID", "value", ScreenName);
		map.put(AllotmentID, allotmentID);
		Thread.sleep(2000);



	}
	
}
