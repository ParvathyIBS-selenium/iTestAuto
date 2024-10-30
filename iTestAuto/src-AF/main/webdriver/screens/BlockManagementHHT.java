package screens;



import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class BlockManagementHHT extends CustomFunctions {
	
	String sheetName = "BlockManagementHHT";
	String screenName = "BlockManagementHHT";
	

	public BlockManagementHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht Block Management screen
	 * @throws IOException 
	 */
	public void invokeBlockManagementScreen() throws InterruptedException, AWTException, IOException {	
		clickActionInHHT("blockhht_menu;xpath",proppathhht,"Block management menu",screenName);
		waitForSync(2);
	}
	
	/**
	 * @author A-9175
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering awb number in hht Screen
	 * @throws IOException 
	 */
	public void enterValue(String value) throws AWTException, InterruptedException, IOException
	{
			enterValueInHHT("blockhht_inbx_Awb;accessibilityId",proppathhht,data(value),"List Value",screenName);
			waitForSync(5);
	}
	
	/**
	 * Desc : Creating Block for AWB
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	
	public void clickNewBlock() throws AWTException, InterruptedException, IOException
	{
			waitForSync(5);
			clickActionInHHT("blockhht_btn_createBlock;xpath",proppathhht,"Create block",screenName);
			waitForSync(2);
		 
	}
	/**
     * Desc : Click on HAWB
     * @author A-9478
     * @throws AWTException
     * @throws InterruptedException
     * @throws IOException 
      */
     
     public void clickOnHAWB(String hawb) throws AWTException, InterruptedException, IOException
     {
           try
           {
                 String locatorValue=getPropertyValue(proppathhht, "blockhht_btn_HAWB;xpath");
                 locatorValue=locatorValue.replace("HAWB", data(hawb));
                 waitForSync(2);
                 androiddriver.findElement(By.xpath(locatorValue)).click();
                 waitForSync(2);
                 writeExtent("Pass", "Successfully clicked on HAWB "+data(hawb)+" In "+screenName);
           }
           catch(Exception e)
           {
                 captureScreenShot("Android");
                 writeExtent("Fail", "Couldn't click on HAWB "+data(hawb)+" In "+screenName);
           }
           
           
     }

	/**
	 * Desc : Selecting a Block type code
	 * @author A-9175
	 * @param blockTypeCode
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	
	public void selectBlockTypeCode(String blockTypeCode) throws InterruptedException, IOException
	{

		waitForSync(5);
		clickActionInHHT("blockhht_btn_blockType;xpath",proppathhht,"Block type",screenName);
		waitForSync(5);
		
		//Fetching the locator value from property file
		String locatorValue=getPropertyValue(proppathhht, "blockhht_btn_blockCode;xpath");
		locatorValue=locatorValue.replace("blockTypeCode", blockTypeCode);
		
		androiddriver.findElement(By.xpath(locatorValue)).click();
		waitForSync(5);
	}
	/**
	 * Desc : Verifying shipment details
	 * @author A-9175
	 * @param awbNo
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyShipment(String awbNo) throws AWTException, InterruptedException, IOException
	{
		
			 String actualAwbNo=getTextAndroid("blockhht_shipment;xpath",proppathhht," AWB NO",screenName);
	         verifyValueOnPage(actualAwbNo, data(awbNo),"Verification of AWBno ", screenName, "Verification of AWB Number");
		
		 
	}
	
	/**
	 * Desc : Updating Flight Details
	 * @author A-9175
	 * @param carrCode
	 * @param flightNo
	 * @param flightDate
 * @throws AWTException
 * @throws InterruptedException
 * @throws IOException
 */

public void updateFlightDetails(String carrCode,String flightNo,String flightDate) throws AWTException, InterruptedException, IOException
{
	
		waitForSync(5);
		enterValueInHHT("blockhht_inbx_carrierCode;accessibilityId",proppathhht,data(carrCode),"Carrier Code",screenName);
		waitForSync(2);
		enterValueInHHT("blockhht_inbx_flightNumber;accessibilityId",proppathhht,data(flightNo),"Flight No",screenName);
		waitForSync(2);
		if(flightDate.equals("currentDay"))
		{
			clickActionInHHT("blockhht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
		}

		else if(flightDate.equals("nextDay"))
		{
			clickActionInHHT("blockhht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
		}
		waitForSync(2);
		clickActionInHHT("blockhht_btn_next2;xpath",proppathhht,"Next",screenName);
		waitForSync(10);
		
		/**Flight Details Updation Confirmation Pop Up and Clicking Yes**/
		
		clickActionInHHT("btn_Yes;xpath",proppathhht,"Yes",screenName);
		waitForSync(12);
		verifyHHTSaveDetails(screenName);


}


	/**
	 * Desc : Entering Block Remarks
	 * @author A-9175
	 * @param remarks
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void enterRemarks(String remarks) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("blockhht_inbx_Remarks;accessibilityId",proppathhht,data(remarks),"Remarks",screenName);
			waitForSync(3);
		 
	}
	
	/**
	 * Desc : Clicking Save
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSave() throws AWTException, InterruptedException, IOException
	{
		
			waitForSync(5);
			clickActionInHHT("btn_Save2;xpath",proppathhht,"Save",screenName);	
			waitForSync(12);
		
	
		 
	}
	
	/**
	 * Desc : Verifying Blocks Existing.
	 * @author A-9175
	 * @param blocks
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	
	public void verifyBlocks(String ...blocks) throws AWTException, InterruptedException
	{
		try
		{
			waitForSync(5);
			for (String block : blocks) 
			{
				
				String blockFound=androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+block+"']")).getText();
				waitForSync(2);
				if(blockFound.equals(block))
				writeExtent("Pass", "Successfully Verified "+block+" In "+screenName);
			}
			
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not Verified Blocks in "+screenName);
		}
		 
	}
	
	/**
	 * Desc : Releasing Block
	 * @author A-9175
	 * @param block
	 * @param Remarks
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void releaseBlock(String block,String Remarks) throws AWTException, InterruptedException
	{
		try
		{
			
				androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+block+"']")).click();
				waitForSync(2);
				enterValueInHHT("blockhht_inbx_Remarks2;accessibilityId",proppathhht,Remarks,"Remarks",screenName);
				waitForSync(3);
				clickActionInHHT("blockhht_btn_release;xpath",proppathhht,"Release block",screenName);	
				waitForSync(12);
				writeExtent("Pass", "Successfully Released "+block+" In "+screenName);
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not Release Blocks in "+screenName);
		}
		 
	}
	
	
	/**
	 * Desc : Verifying Blocks not existing
	 * @author A-9175
	 * @param blocks
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void verifyBlocksNotExist(String ...blocks) throws AWTException, InterruptedException
	{
		try
		{
			waitForSync(5);
			for (String block : blocks) 
			{
				System.out.println(block);
				String txt=androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+block+"']")).getText();
				if(txt!=null)
				writeExtent("Fail", "Block Still Exists for  "+block+" In "+screenName);
			}
			
		}

		catch(Exception e)
		{
			writeExtent("Pass", "Block Not Exists in "+screenName);
		}
		 
	}
	
}
