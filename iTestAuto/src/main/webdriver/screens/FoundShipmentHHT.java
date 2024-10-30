package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class FoundShipmentHHT extends CustomFunctions {
	
	String sheetName = "FoundShipmentHHT";
	String screenName = "FoundShipmentHHT";
	

	public FoundShipmentHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoke Found Shipment screen
	 */
	public void invokeFoundShipmentScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Found Shipment");	
		clickActionInHHT("foundShiphht_menu;xpath",proppathhht,"Found Shipment menu",screenName);	
		waitForSync(2);
		writeExtent("Pass", "Found Shipment hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Found Shipment hht screen is not invoked successfully");
		}
	}
	
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Enter Location,Pieces,Weight
	 * @throws IOException 
	 */
	public void enterLocationPiecesWeight(String location,String Pieces,String Weight) throws AWTException, InterruptedException, IOException
	{
			waitForSync(4);
			enterValueInHHT("foundShiphht_inbx_location;accessibilityId",proppathhht,data(location),"Location",screenName);			 
			enterValueInHHT("foundShiphht_inbx_Pieces;accessibilityId",proppathhht,data(Pieces),"Pieces",screenName);
			enterValueInHHT("foundShiphht_inbx_Weight;accessibilityId",proppathhht,data(Weight),"Weight",screenName);
	}
	
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Enter Additional Marking,Remarks
	 * @throws IOException 
	 */
	public void enterAdditionalMarkingRemarks(String AdditionalMarking,String Remarks) throws AWTException, InterruptedException, IOException
	{
			waitForSync(4);
			scrollInMobileDevice("Enter Remarks");	
			enterValueInHHT("foundShiphht_inbx_AdditionalMarking;accessibilityId",proppathhht,data(AdditionalMarking),"Additional Marking",screenName);			 
			enterValueInHHT("foundShiphht_inbx_Remarks;accessibilityId",proppathhht,data(Remarks),"Remarks",screenName);
	}
	
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Click Add Details
	 * @throws IOException 
	 */
	public void clickAddDetails() throws IOException
	{
			waitForSync(4);
			clickActionInHHT("foundShiphht_btn_AddDetails;xpath",proppathhht,"Add Details",screenName);
	}
	
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Click Add
	 * @throws IOException 
	 */
	public void clickAdd() throws IOException
	{
			waitForSync(4);
			clickActionInHHT("foundShiphht_btn_Add;xpath",proppathhht,"Add button",screenName);
			waitForSync(4);
	}
	
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Click Save
	 * @throws IOException 
	 */
	public void clickSave() throws IOException
	{
			waitForSync(4);
			clickActionInHHT("foundShiphht_btn_Save;xpath",proppathhht,"Save button",screenName);
			waitForSync(4);
	}
	
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Click Add Details
	 * @throws IOException 
	 */
	public void clickCreateFSID() throws IOException
	{
			waitForSync(4);
			clickActionInHHT("foundShiphht_btn_CreateFSID;xpath",proppathhht," Create FS ID ",screenName);
			waitForSync(4);
	}
	
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Click Add Details
	 * @throws IOException 
	 */
	public void enterULDNumber(String ULDNum) throws AWTException, InterruptedException, IOException
	{
			waitForSync(4);
			enterValueInHHT("foundShiphht_inbx_ULD;accessibilityId",proppathhht,data(ULDNum),"ULD Number",screenName);			 
	}
	
	
	/**
	 * @author A-9478
	 * @param carrCode
	 * @param flightNo
	 * @param flightDate
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void enterFlightDetails(String carrCode,String flightNo,String flightDate) throws AWTException, InterruptedException, IOException
	{
		
			waitForSync(5);
			enterValueInHHT("foundShiphht_inbx_Carrier;accessibilityId",proppathhht,data(carrCode),"Carrier Code",screenName);
			waitForSync(2);
			enterValueInHHT("foundShiphht_inbx_FlightNo;accessibilityId",proppathhht,data(flightNo),"Flight No",screenName);
			waitForSync(2);
			if(flightDate.equals("currentDay"))
			{
				clickActionInHHT("foundShiphht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
			}

			else if(flightDate.equals("nextDay"))
			{
				clickActionInHHT("foundShiphht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
			}
			waitForSync(2);
	}
	
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Enter Origin,Destination
	 * @throws IOException 
	 */
	public void enterOriginDestination(String Origin,String Destination) throws AWTException, InterruptedException, IOException
	{
			waitForSync(4);
			enterValueInHHT("foundShiphht_inbx_Origin;accessibilityId",proppathhht,data(Origin),"Origin",screenName);			 
			enterValueInHHT("foundShiphht_inbx_Destination;accessibilityId",proppathhht,data(Destination),"Destination",screenName);
	}
	
	/**
	 * @author A-9478
	 * @param screenName
	 * Desc : Verify save details in Found Shipment hht screen
	 * @throws IOException 
	 */
	public String verifyTextAfterSave(String screenName) throws IOException
	{
		String FSID=null;
		try
		{
		waitForSync(5);
		 int size=getSizeOfMobileElement("foundShiphht_txt_FSDataSavedSuccessfully;xpath",proppathhht);
		 String locatorValue=getPropertyValue(proppathhht, "foundShiphht_txt_FSDataSavedSuccessfully;xpath");
		 FSID=androiddriver.findElement(By.xpath(locatorValue)).getText();
			/*** CLOSE CONFIRMATION MESSAGE**/
  	    clickActionInHHT("foundhhtShiphht_btn_CloseConfirmation;xpath",proppathhht,"Close confirmation message",screenName);	
			
			waitForSync(2);
			
			if(size==1)
			{
			 writeExtent("Pass", "Details saved successfully in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				 writeExtent("Fail", "Details not saved successfully in "+screenName);
			}
		}
		
		catch(Exception e)
		{
			 captureScreenShot("Android");
			 writeExtent("Fail", "Details not saved successfully in "+screenName);
		}
		return FSID;
	}
	
	
}
