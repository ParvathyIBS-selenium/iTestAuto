package screens;

import io.appium.java_client.android.AndroidElement;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.touch.TouchActions;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class WeightCaptureHHT  extends CustomFunctions {
	
	String sheetName = "WeightCaptureHHT";
	String screenName = "WeightCaptureHHT";
	

	public WeightCaptureHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht weight capture screen
	 */
	public void invokeWeightCaptureScreen() throws InterruptedException, AWTException {

			try
		{
			
		scrollInMobileDevice("Weight Capture");	
		clickActionInHHT("weightCaphht_menu;xpath",proppathhht,"Weight Capture menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "Weight Capture hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Weight Capture hht screen is not invoked successfully");
		}
	}
    /**
    * @author A-9478 
     * Desc : verifying the shipment details
    */
    public void verifyShipmentDetails(String pcs,String wt) throws IOException, InterruptedException
    {
          /******** PIECES****************/
          String actPcs=getTextAndroid("weightCaphht_txt_pieces;xpath",proppathhht,"Pieces",screenName);

          verifyValueOnPage(actPcs, data(pcs),"Verification of pieces", screenName, "Verification of pieces");

          /******** WEIGHT****************/
          String actWt=getTextAndroid("weightCaphht_txt_Weight;xpath",proppathhht,"Weight",screenName);

          verifyValueOnPage(actWt, data(wt),"Verification of weight", screenName, "Verification of weight");
          
    }
    
    /**
    * @author A-9478 
     * Desc : verifying stated and accepted pieces and weight
    **/
    public void verifyStatedAndAcceptedShipmentDetails(String statedPcs,String statedWt, String acceptedPcs,String acceptedWt) throws IOException, InterruptedException
    {
          /******** Stated PIECES****************/
          String actStatedPcs=getTextAndroid("weightCaphht_txt_StatedPcs;xpath",proppathhht,"Pieces",screenName);
          
          verifyValueOnPage((actStatedPcs.split(" "))[0], data(statedPcs),"Verification of stated pieces", screenName, "Verification of stated pieces");

          /******** Stated WEIGHT****************/
          String actStatedWt=getTextAndroid("weightCaphht_txt_StatedWt;xpath",proppathhht,"Weight",screenName);

          verifyValueOnPage((actStatedWt.split(" "))[0], data(statedWt),"Verification of stated weight", screenName, "Verification of stated weight");
          

          /******** Accepted PIECES****************/
          String actAcceptedPcs=getTextAndroid("weightCaphht_txt_AcceptedPcs;xpath",proppathhht,"Pieces",screenName);

          verifyValueOnPage((actAcceptedPcs.split(" "))[0], data(acceptedPcs),"Verification of accepted pieces", screenName, "Verification of accepted pieces");

          /******** Accepted WEIGHT****************/
          String actAcceptedWt=getTextAndroid("weightCaphht_txt_AcceptedWt;xpath",proppathhht,"Weight",screenName);

          verifyValueOnPage((actAcceptedWt.split(" "))[0], data(acceptedWt),"Verification of accepted weight", screenName, "Verification of accepted weight");
          
    }

	/**
	 * @author A-7271
	 * @param value
	 * Desc : Enter the list value
	 * @throws IOException 
	 */
	public void enterValue(String value) throws IOException
	{
		enterValueInHHT("weightCaphht_inbx_listValue;accessibilityId",proppathhht,data(value),"List Value",screenName);
	    waitForSync(12);
	}
	
	/**
	 * @author A-7271
	 * @param location
	 * Desc : enter location
	 * @throws IOException 
	 */
	public void enterLocation(String location) throws IOException
	{
		enterValueInHHT("weightCaphht_inbx_location;accessibilityId",proppathhht,data(location),"Location",screenName);
	}
	/**
	 * @author A-7271
	 * @param occupancy
	 * Desc : enter occupancy %
	 * @throws IOException 
	 */
	public void enterOccupancy(String occupancy) throws IOException
	{
		enterValueInHHT("weightCaphht_inbx_occupancy;accessibilityId",proppathhht,data(occupancy),"Occupancy %",screenName);
		waitForSync(1);
	}
	/**
	 * @author A-7271
	 * Desc : Enter remarks
	 * @throws IOException 
	 */
	public void enterRemarks() throws IOException
	{
		
		enterValueInHHT("weightCaphht_inbx_remarks;accessibilityId",proppathhht,"Weight Capture","Remarks",screenName);	
		waitForSync(2);
		
	}
	/**
	 * @author A-7271
	 * @param weight
	 * Desc : enter occupancy %
	 * @throws IOException 
	 */
	public void enterActualScaleWt(String weight) throws IOException
	{
		scrollInMobileDevice("Scale Weight");
		enterValueInHHT("weightCaphht_inbx_uldActualWt;accessibilityId",proppathhht,data(weight),"Scale weight",screenName);
	}
	/**
	 * @author A-7271
	 * Desc : Click save button
	 * @throws IOException 
	 */
	public void saveDetails() throws IOException
	{
		clickActionInHHT("weightCaphht_btn_Save;xpath",proppathhht,"Save",screenName);
		waitForSync(10);
		verifyHHTSaveDetails(screenName);
	}
	
	
}
