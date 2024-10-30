package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
import io.appium.java_client.MobileElement;

public class AWBEnquiryHHT extends CustomFunctions {
	
	String sheetName = "AWBEnquiryHHT";
	String screenName = "AWBEnquiryHHT";
	

	public AWBEnquiryHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht AWBEnquiryHHT screen
	 * @throws IOException 
	 */
	public void invokeAWBEnquiryHHTScreen() throws InterruptedException, AWTException, IOException {
	
		
		clickActionInHHT("awbenquiryhht_menu;xpath",proppathhht,"Awb Enquiry menu",screenName);
		waitForSync(5);
	}
	
	/**
	 * @author A-7271
	 * @param awbNumber
	 * Desc : Enter awb number
	 * @throws IOException 
	 */
	
	public void enterAWBNumber(String awbNumber) throws IOException
	{
		enterValueInHHT("awbenquiryhht_inbx_Awb;accessibilityId",proppathhht,data(awbNumber),"Awb Number",screenName);
		waitForSync(10); 
	}
	
	/**
	 * @author A-7271
	 * @throws IOException
	 * Desc : click warehouse button
	 */
	public void clickWareHouseBtn() throws IOException
	{
		
		clickActionInHHT("awbenquiryhht_btn_warehouse;xpath",proppathhht,"Warehouse Section",screenName);
		/** Double Click Required **/
		String locator=getPropertyValue(proppathhht, "awbenquiryhht_btn_warehouse;xpath");
		androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(4); 
	}
	/**
	 * @author A-9847
	 * @Desc To click the Print SU Label
	 * @throws IOException
	 */
	public void printSULabel() throws IOException{
		
		clickActionInHHT("awbenquiryhht_txt_printSULabel;xpath",proppathhht,"Print SU Label",screenName);
		waitForSync(3); 
	}
	
	
	/**
	 * @author A-9847
	 * @Desc To check all the checkboxes of SUs in Warehouse Section
	 */
	public void checkAllCheckboxesInWHSection(){

		try{	
			List<MobileElement>checkboxes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "awbenquiryhht_inbx_checkboxes;xpath")));

			for(MobileElement checkbox:checkboxes)
			{
				checkbox.click();
				waitForSync(1);
				writeExtent("Pass", "Successfully checked the checkbox on "+screenName);
			}			
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to check the checkboxes on "+screenName);	
		}
	}

	/**
	 * @author A-9478
	 * @throws IOException
	 * Desc : click Import button
	 */
	public void clickImportBtn() throws IOException
	{
		
		clickActionInHHT("awbenquiryhht_btn_Import;xpath",proppathhht,"Import",screenName);
		waitForSync(4); 
	}
	
	/**
	 * @author A-9478
	 * @throws IOException
	 * Desc : click Export button
	 */
	public void clickExportBtn() throws IOException
	{
		
		clickActionInHHT("awbenquiryhht_btn_Export;xpath",proppathhht,"Export",screenName);
		waitForSync(4); 
	}
	/**
     * @author A-9478
     * @throws IOException
     * Desc : click Damage button
     */
     public void clickDamageBtn() throws IOException
     {
           scrollInMobileDevice("Damage");
          clickActionInHHT("awbenquiryhht_btn_Damage;xpath",proppathhht,"Damage",screenName);
           waitForSync(4); 
     }

/**
     * @author A-9478 
      * Desc : verifying the Damage details
     */
     public void verifyDamageDetails(String pcs) throws IOException, InterruptedException
     {
           /******** PIECES****************/
           String actPcs=getTextAndroid("awbenquiryhht_txt_DamagePcs;xpath",proppathhht," Damage Pieces",screenName);

           verifyValueOnPage(actPcs, data(pcs),"Verification of pieces", screenName, "Verification of Damage pieces");   
     }

/**
     * @author A-9478
     * @param awbNumber
     * Desc : Enter hawb number
     * @throws IOException 
      */
     
     public void enterHAWBNumber(String HAWB) throws IOException
     {
       enterValueInHHT("awbenquiryhht_inbx_HAWB;accessibilityId",proppathhht,data(HAWB),"HAWB Number",screenName);
           waitForSync(10); 
     }

	/**
	 * @author A-7271
	 * @param Storage Unit
	 * Desc : Enter storage unit
	 * @throws IOException 
	 * @throws InterruptedException 
	 * Desc : verifying the warehouse details
	 */
	public void verifyWarehouseDetails(String pcs,String wt,String ULD) throws IOException, InterruptedException
	{
		/******** PIECES****************/
		String actPcs=getTextAndroid("awbenquiryhht_inbx_warehousePcs;xpath",proppathhht,"Pieces",screenName);

		verifyValueOnPage(actPcs, data(pcs),"Verification of pieces", screenName, "Verification of pieces");

		/******** WEIGHT****************/
		String actWt=getTextAndroid("awbenquiryhht_inbx_warehouseWt;xpath",proppathhht,"Weight",screenName);

		verifyValueOnPage(actWt, data(wt),"Verification of weight", screenName, "Verification of weight");

		/*****ULD*****/
		String locator=getPropertyValue(proppathhht, "awbenquiryhht_inbx_warehouseUld;xpath");
		locator=locator.replace("ULD", data(ULD));	
		int size=androiddriver.findElements(By.xpath(locator)).size();

		if(size==1)
		{
			writeExtent("Pass", "ULD "+data(ULD)+" is displaying in the warehouse details"+" on "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			writeExtent("Fail", "ULD "+data(ULD)+" is not available in the warehouse details "+" on "+screenName);
		}
	}
	
	/**
	 * @author A-9478	
	 * @throws IOException 
	 * @throws InterruptedException 
	 * Desc : verifying the warehouse details
	 */
	public void verifyWarehouseDetails(String pcs,String wt) throws IOException, InterruptedException
	{
		/******** PIECES****************/
		String actPcs=getTextAndroid("awbenquiryhht_inbx_warehousePcs;xpath",proppathhht,"Pieces",screenName);

		verifyValueOnPage(actPcs, data(pcs),"Verification of pieces", screenName, "Verification of pieces");

		/******** WEIGHT****************/
		String actWt=getTextAndroid("awbenquiryhht_inbx_warehouseWt;xpath",proppathhht,"Weight",screenName);

		verifyValueOnPage(actWt, data(wt),"Verification of weight", screenName, "Verification of weight");
		
	}

	/**
	 * @author A-9478
	 * @param Storage Unit
	 * Desc : Enter storage unit
	 * @throws IOException 
	 * @throws InterruptedException 
	 * Desc : verifying the warehouse details
	 */
	public void verifyImportDetails(String pcs,String wt,String ULD,String FlightNo) throws IOException, InterruptedException
	{
		/******** PIECES****************/
		String actPcs=getTextAndroid("awbenquiryhht_txt_importPcs;xpath",proppathhht,"Pieces",screenName);

		verifyValueOnPage(actPcs, data(pcs),"Verification of pieces", screenName, "Verification of pieces");

		/******** WEIGHT****************/
		String actWt=getTextAndroid("awbenquiryhht_txt_importWt;xpath",proppathhht,"Weight",screenName);

		verifyValueOnPage(actWt, data(wt),"Verification of weight", screenName, "Verification of weight");

		/*****ULD*****/
		String locator=getPropertyValue(proppathhht, "awbenquiryhht_inbx_warehouseUld;xpath");
		locator=locator.replace("ULD", data(ULD));	
		int size=androiddriver.findElements(By.xpath(locator)).size();

		if(size==1)
		{
			writeExtent("Pass", "ULD "+data(ULD)+" is displaying in the Import details"+" on "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			writeExtent("Fail", "ULD "+data(ULD)+" is not available in the Import details "+" on "+screenName);
		}
		
		/******Flight No*********/
		String locator1=getPropertyValue(proppathhht, "awbenquiryhht_txt_importFlightNo;xpath");
		locator1=locator1.replace("Flight", data(FlightNo));	
		int size1=androiddriver.findElements(By.xpath(locator1)).size();

		if(size1==1)
		{
			writeExtent("Pass", "Flight number "+data(FlightNo)+" is displaying in the Import details"+" on "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Flight number "+data(FlightNo)+" is not available in the Import details "+" on "+screenName);
		}
	}
	
	/**
	 * @author A-9478
	 * @param Storage Unit
	 * Desc : Enter storage unit
	 * @throws IOException 
	 * @throws InterruptedException 
	 * Desc : verifying the warehouse details
	 */
	public void verifyExportDetails(String pcs,String wt,String ULD,String FlightNo) throws IOException, InterruptedException
	{
		/******** PIECES****************/
		String actPcs=getTextAndroid("awbenquiryhht_inbx_ExportPcs;xpath",proppathhht,"Pieces",screenName);

		verifyValueOnPage(actPcs, data(pcs),"Verification of pieces", screenName, "Verification of pieces");

		/******** WEIGHT****************/
		String actWt=getTextAndroid("awbenquiryhht_inbx_ExportWt;xpath",proppathhht,"Weight",screenName);

		verifyValueOnPage(actWt, data(wt),"Verification of weight", screenName, "Verification of weight");

		/*****ULD*****/
		String locator=getPropertyValue(proppathhht, "awbenquiryhht_inbx_warehouseUld;xpath");
		locator=locator.replace("ULD", data(ULD));	
		int size=androiddriver.findElements(By.xpath(locator)).size();

		if(size==1)
		{
			writeExtent("Pass", "ULD "+data(ULD)+" is displaying in the Export details"+" on "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			writeExtent("Fail", "ULD "+data(ULD)+" is not available in the Export details "+" on "+screenName);
		}
		
		/******Flight No*********/
		String locator1=getPropertyValue(proppathhht, "awbenquiryhht_txt_importFlightNo;xpath");
		locator1=locator1.replace("Flight", data(FlightNo));	
		int size1=androiddriver.findElements(By.xpath(locator1)).size();

		if(size1==1)
		{
			writeExtent("Pass", "Flight number "+data(FlightNo)+" is displaying in the export details"+" on "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Flight number "+data(FlightNo)+" is not available in the export details "+" on "+screenName);
		}
	}

}
