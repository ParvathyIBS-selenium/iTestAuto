package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class Destocking_ADD017 extends CustomFunctions {

	String sheetName = "Destocking_ADD017";
	String GenericSheet = "Generic_Elements";
	String screenName = "Destocking : ADD017";
	String screenId = "ADD017";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";

	public Destocking_ADD017(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}
	/**
	 * @Description : Enter From Date
	 * @author A-9844
	 * @param fromDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void enterFromDate(String fromDate)throws InterruptedException, AWTException, IOException {


		waitTillScreenload(sheetName, "inbx_fromDate;id","From Date", screenName);
		enterValueInTextbox(sheetName, "inbx_fromDate;id", data(fromDate), "From Date", screenName);
		waitForSync(1);
		keyPress("TAB");


	}
	
	/**
	 * @Description : Enter To Date
	 * @author A-9844
	 * @param toDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void enterToDate(String toDate)throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_toDate;id", data(toDate), "To Date", screenName);
		keyPress("TAB");
	}
	
	/**
	 * @Description : Enter Flight details
	 * @author A-9844
	 * @param carrierCode
	 * @param flightNumber
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void enterToDate(String carrierCode,String flightNumber)throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(carrierCode), "carrier code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(flightNumber), "flight number", screenName);
	}
	
	/**
	 * @Description : Enter equipment
	 * @author A-9844
	 * @param equipment
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void enterEquipment(String equipment)throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_equipment;name", data(equipment), "Equipment", screenName);
		
	}
	/**
	 * @Description : Enter Flight details
	 * @author A-9844
	 * @param carrierCode
	 * @param flightNumber
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void enterFlightDetails(String carrierCode,String flightNumber)throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(carrierCode), "carrier code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(flightNumber), "flight number", screenName);
	}
	/**
	 * @author A-9844
	 * @Desc select Vehicle Type
	 * @param equipment,equipmentType
	 */

	public void selectVehicleType(String fullFlightNo,String vehicleType)
	{
		try
		{

			waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
			String vehicleTypeLocator = xls_Read.getCellValue(sheetName, "txt_vehicleType;xpath");
			vehicleTypeLocator=vehicleTypeLocator.replace("FullFlightNo",data(fullFlightNo));
			vehicleTypeLocator=vehicleTypeLocator.replace("VehicleType",data(vehicleType));
			int size=driver.findElements(By.xpath(vehicleTypeLocator)).size();

			if(size!=1){
				clickWebElementByWebDriver(sheetName, "btn_vehicleTypeEditIcon;xpath", "vehicle type edit icon", screenName);
				String locator = xls_Read.getCellValue(sheetName, "btn_vehicleTypeOption;xpath");
				locator=locator.replace("*",data(vehicleType));
				driver.findElement(By.xpath(locator)).click();
				clickWebElementByWebDriver(sheetName, "btn_OK;xpath", "OK button", screenName);
				waitForSync(2);
				writeExtent("Pass", "Selected the vehicle type as "+data(vehicleType)+" on " + screenName);
			}

		}

		catch (Exception e) {
			writeExtent("Fail", "Could not select the vehicle type on " + screenName);
		}


	}
	public void verifyULDdetails(String uldNumber,String equipment,Boolean ulddisplayed)throws InterruptedException, AWTException, IOException {

		try{
			
			waitTillScreenload(sheetName, "txt_Destockinglist;xpath", "Destocking list", screenName);
			String locator = xls_Read.getCellValue(sheetName, "inbx_ULDdetails;xpath");
			locator=locator.replace("ULD",data(uldNumber));
			locator=locator.replace("*",data(equipment));
			int size=driver.findElements(By.xpath(locator)).size();
			if(ulddisplayed==true)
			{
			if(size==1){
				writeExtent("Pass", "Verified the ULD details " + data(uldNumber) + " assigned to  "+ data(equipment) +"on " + screenName);
				onPassUpdate(screenName,  data(uldNumber) + " assigned to  "+ data(equipment) +"on " + screenName, "verifying the ULD details ", "Verify whethe ULDs are present","Verify whethe ULDs are present"); 
						
			}
			else{
				writeExtent("Fail", "Failed to verify ULD details  " + data(uldNumber) + " assigned to "+ data(equipment) +"on " + screenName);
				onFailUpdate(screenName,  data(uldNumber) + " assigned to  "+ data(equipment) +"on " + screenName, "verifying the ULD details ", "Verify whethe ULDs are present","Verify whethe ULDs are present");
			
		}
		}
		else
		{
			if(size==1){
				writeExtent("Fail","Failed to verify the uld"+data(uldNumber) +  "is not removed from "  +screenName);
				onFailUpdate(screenName, data(uldNumber) + "is not removed from " + screenName, "verifying the ULD details not present ", "Verify whethe ULDs are not present","Verify whethe ULDs are not present");
			}
			else{
				writeExtent("Pass", "successfully verified the ULD " + data(uldNumber) + " removed from " + screenName);
				onPassUpdate(screenName, data(uldNumber) + "is removed from " + screenName, "verifying the ULD details not present ", "Verify whethe ULDs are not present","Verify whethe ULDs are not present");
			}
			}
			}
			catch (Exception e) {
				writeExtent("Fail", "Could not verify destocking details on " + screenName);
			}
 

		
	}
	/**
	 * @Description : verify destocking list details
	 * @author A-10690
	 * @param  ULDNUMBER
	 * @param  Equipment 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	
	public void verifyULDdetails(String uldNumber,String equipment)throws InterruptedException, AWTException, IOException {

		try{
			
		String locator = xls_Read.getCellValue(sheetName, "inbx_ULDdetails;xpath");
		locator=locator.replace("ULD",data(uldNumber));
		locator=locator.replace("*",data(equipment));
		int size=driver.findElements(By.xpath(locator)).size();
		
		if(size==1){
			writeExtent("Pass", "Verified the ULD details " + data(uldNumber) + " assigned to  "+ data(equipment) +"on " + screenName);
		}
		else{
			writeExtent("Fail", "Failed to verify ULD details  " + data(uldNumber) + " assigned to "+ data(equipment) +"on " + screenName);
		}
		}
		catch (Exception e) {
			writeExtent("Fail", "Could not verify destocking details on " + screenName);
		}
		
	}

	/**
	 * @Description : click List
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void clickList()throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "btn_List;id", "List", screenName);
		
	}
	
	/**
	 * @Description : select the flight and  click destocking
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void clickDestocking(String flightNumber)throws InterruptedException, AWTException, IOException {

		
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
		String flightNumberRow = xls_Read.getCellValue(sheetName, "chkbox_flightNumberCheckBox;xpath");
		flightNumberRow=flightNumberRow.replace("*", data(flightNumber));
		driver.findElement(By.xpath(flightNumberRow)).click();
		clickWebElement(sheetName, "btn_Destocking;id", "Destocking button", screenName);
		waitForSync(6);
	}
	
	/**
	 * @Description : verify destocking list details
	 * @author A-9844
	 * @param  columnName
	 * @param  expValue
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void verifyDestockingListDetails(String columnName,String expValue)throws InterruptedException, AWTException, IOException {

		try{
		String locator = xls_Read.getCellValue(sheetName, "inbx_destockingDetails;xpath");
		locator=locator.replace("colName", data(columnName));
		locator=locator.replace("value", data(expValue));
		int size=driver.findElements(By.xpath(locator)).size();
		
		if(size==1){
			writeExtent("Pass", "Verified the value of  " + data(columnName) + " as "+ data(expValue) +"on " + screenName);
		}
		else{
			writeExtent("Fail", "Failed to verify the value of  " + data(columnName) + " as "+ data(expValue) +"on " + screenName);
		}
		}
		catch (Exception e) {
			writeExtent("Fail", "Could not verify destocking details on " + screenName);
		}
		
	}
	
	
	
	
	
	

}