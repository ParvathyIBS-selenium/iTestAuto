package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CaptureIrregularityHHT extends CustomFunctions {
	
	String sheetName = "CaptureIrregularityHHT";
	String screenName = "CaptureIrregularityHHT";
	

	public CaptureIrregularityHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht acceptance screen
	 */
	public void invokeCaptureIrregularityScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Capture Irregularity");	
		clickActionInHHT("capIrrhht_menu;xpath",proppathhht,"Capture Irregularity menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "Capture Irregularity hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Capture Irregularity hht screen is not invoked successfully");
		}
	}
	/**
     * @author A-9478
     * @param awbNumber
     * @throws AWTException
     * @throws InterruptedException
     * Description : entered hawb number in hht
     * @throws IOException 
      */
     public void enterHAWBNumber(String value) throws AWTException, InterruptedException, IOException
     {
           
                 enterValueInHHT("capIrrhht_inbx_HAWB;accessibilityId",proppathhht,data(value)," Enter HAWB ",screenName);
                 waitForSync(4);         
     }

/**
     * @author A-9478
     * @Description: Click Add details button
     */
     public void clickAddDetails() throws AWTException, InterruptedException, IOException
     {
           
                 clickActionInHHT("capIrrhht_btn_AddDetails;xpath",proppathhht,"Add Details",screenName);   
                 waitForSync(3);               
     }
     
     /**
     * @author A-9478
     * @Description: enter flight details
     */
     public void updateFlightDetails(String carrCode,String flightNo,String flightDate) throws AWTException, InterruptedException, IOException
     {
           
                 waitForSync(5);
           enterValueInHHT("capIrrhht_btn_carrierCode;accessibilityId",proppathhht,data(carrCode),"Carrier Code",screenName);
                 waitForSync(2);
           enterValueInHHT("capIrrhht_btn_flightNumber;accessibilityId",proppathhht,data(flightNo),"Flight No",screenName);
                 waitForSync(2);
                 if(flightDate.equals("currentDay"))
                 {
                       clickActionInHHT("capIrrhht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
                 }

                 else if(flightDate.equals("nextDay"))
                 {
                       clickActionInHHT("capIrrhht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
                 }
                 waitForSync(2);
                 clickActionInHHT("capIrrhht_btn_AddButton;xpath",proppathhht,"Add",screenName);
                 waitForSync(5);
     }
     /**
 	 * @author A-8783
 	 * Desc- To verify that the token number field is present
 	 */
 	public void verifyTokenNumberField() {
 		int size;
    	try {
    		 size=getSizeOfMobileElement("capIrrhht_inbx_Token;xpath",proppathhht);
    		
    		if( size==1) {
    			writeExtent("Pass", "Verified that the field token number is displayed in " +screenName);
    		}
    	}
    	catch(Exception e) {
    		writeExtent("Fail","Failed to verify token number field in" + screenName);
    	}

      }

/**
     * @author A-9478 
      * Description : Close please select transaction pop up
     * @throws IOException 
      */
     public void closePleaseSelectTransation() throws AWTException, InterruptedException, IOException
     {           
       clickActionInHHT("capIrrhht_btn_closePleaseSelectTransaction;xpath",proppathhht,"Close Please Select Transaction pop up",screenName);
           waitForSync(5); 
     }

	/* @author A-9478
    * @param Pieces
    * @throws AWTException
    * @throws InterruptedException
    * Description : entered remarks in hht
    * @throws IOException 
     */
    public void enterRemarks(String Remarks) throws AWTException, InterruptedException, IOException
    {
          try
          {
          enterValueInHHT("capIrrhht_inbx_Remarks;accessibilityId",proppathhht,data(Remarks),"Remarks",screenName);
                writeExtent("Pass", "Value "+data(Remarks)+" entered in Capture Irregularity hht screen");
          }
          
          catch(Exception e)
          {
                captureScreenShot("Android");
                writeExtent("Fail", "Value "+data(Remarks)+" not entered in "+screenName);
          }
          
    }

	
	/**
	 * @author A-9478
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Select Transaction
	 */
    public void selectTransaction(String transaction) throws AWTException, InterruptedException
	{
		try
		{
			
			clickActionInHHT("capIrrhht_btn_transaction;xpath", proppathhht, "Transaction", screenName);
			waitForSync(2);
			String locator = getPropertyValue(proppathhht, "capIrrhht_txt_transaction");
			locator = locator.replace("transaction", data(transaction));
			scrollInMobileDevice(data(transaction));
			androiddriver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", "Selected Transaction value as " + data(transaction) + " in " + screenName);
			waitForSync(5);
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Transaction value "+data(transaction)+" not selected in "+screenName);
		}
		 
	}

	/**
     * Desc : Entering Token number
     * @author A-9175
     * @param value
     * @throws AWTException
     * @throws InterruptedException
     * @throws IOException
     */
     public void enterTokenNumber(String token) throws AWTException, InterruptedException, IOException
     {
           
             enterValueInHHT("capIrrhht_inbx_Token;xpath",proppathhht,data(token),"Enter Value",screenName);
                  waitForSync(4);
                  
           
     }


	
	/**
	 * @author A-9478
	 * @param Deviation code
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Select Deviation
	 */
     public void selectDeviationCode(String deviationCode) throws AWTException, InterruptedException
 	{
         try
         {
               clickActionInHHT("capIrrhht_btn_deviationCode;xpath",proppathhht,"Deviation Code",screenName);
               waitForSync(5);
               String locator = getPropertyValue(proppathhht, "capIrrhht_txt_deviationCode;xpath");
   			locator=locator.replace("deviation", data(deviationCode));
   				scrollInMobileDevice(data(deviationCode));
   				 androiddriver.findElement(By.xpath(locator)).click();
               writeExtent("Pass", "Selected Deviation Code as "+data(deviationCode)+" in "+screenName);
               waitForSync(5);
         }
         
         catch(Exception e)
         {
               writeExtent("Fail", "Deviation Code "+data(deviationCode)+" not selected in "+screenName);
         }
         

 	}

	
	/**
	 * @author A-9478
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb number in hht
	 * @throws IOException 
	 */
	public void enterAwbNumber(String value) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("capIrrhht_inbx_Awb;accessibilityId",proppathhht,data(value),"Enter Value",screenName);
			waitForSync(4);
			
		 
	}
	
	/**
	 * @author A-9478
	 * @param Pieces,Remarks
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered Pieces,Remarks in hht
	 */
	public void enterPiecesAndRemarks(String Pieces,String Remarks) throws AWTException, InterruptedException
	{
		try
		{
			enterValueInHHT("capIrrhht_inbx_Pcs;accessibilityId",proppathhht,data(Pieces),"Pieces",screenName);
			waitForSync(2);
			enterValueInHHT("capIrrhht_inbx_Remarks;accessibilityId",proppathhht,data(Remarks),"Remarks",screenName);
			writeExtent("Pass", "Value "+ data(Pieces)+" and "+data(Remarks)+" entered in Capture Irregularity hht screen");
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Value "+ data(Pieces)+" and "+data(Remarks)+" not entered in "+screenName);
		}
		 
	}
	
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSave() throws AWTException, InterruptedException, IOException
	{
		
			clickActionInHHT("capIrrhht_btn_Save;xpath",proppathhht,"Save",screenName);	
			waitForSync(6);
			verifyHHTSaveDetails(screenName);
	}
}
