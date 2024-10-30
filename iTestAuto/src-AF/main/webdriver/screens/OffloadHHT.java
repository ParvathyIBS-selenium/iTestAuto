package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class OffloadHHT extends CustomFunctions {
	
	String sheetName = "OffloadHHT";
	String screenName = "OffloadHHT";
	

	public OffloadHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the Offload hht screen
	 */
	public void invokeOffloadHHTScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Offload");	
		clickActionInHHT("offload_menu;xpath",proppathhht,"Offload menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "Offload hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Offload hht screen is not invoked successfully");
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
	public void enterAWBNumber(String awbno) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("offload_inbx_awbNo;accessibilityId",proppathhht,data(awbno),"AWB Number",screenName);
			waitForSync(5);
		 
	}
	/**
	 * @author A-9844
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : click arrow
	 * @throws IOException 
	 */
	public void clickArrow() throws AWTException, InterruptedException, IOException
	{
		
		clickActionInHHT("offload_btn_arrow;xpath",proppathhht,"arrow",screenName);
		waitForSync(3);
		 
	}


	/**
	 * @author A-9844
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : click on awb
	 * @throws IOException 
	 */
	public void clickOnAWBNo(String AWBNo) throws AWTException, InterruptedException, IOException
	{
		
		
		try{
		
		String locator = WebFunctions.getPropertyValue(proppathhht,"offload_btn_clikAWBNo;xpath");
		locator=locator.replace("*", data(AWBNo));  
        androiddriver.findElement(By.xpath(locator)).click();
        waitForSync(3);
		writeExtent("Pass", "clicked on "+data(AWBNo)+" in "+screenName);
	}
	catch(Exception e)
	{
		writeExtent("Fail", "Couldn't click on "+data(AWBNo)+" in "+screenName);
	}
		 
	}

	/**
	 * @author A-9478
	 * Description : Click Unitized yes
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void clickUnitizedYes() throws InterruptedException, IOException
	{
		clickActionInHHT("offload_btn_UnitizedYes;xpath",proppathhht,"Unitized yes button",screenName);
		waitForSync(2);
	}
	/**
	 * @author A-9478
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb/ULD number in hht
	 * @throws IOException 
	 */
	public void enterValue(String value) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("offload_inbx_awbNo;accessibilityId",proppathhht,data(value),"AWB Number",screenName);
			waitForSync(8);
		 
	}

	/**
	 * Desc : Click selectZON
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickFWDZON() throws InterruptedException, IOException
	{
		clickActionInHHT("offload_btn_selectZON;xpath",proppathhht,"select ZON",screenName);
		waitForSync(2);
	}

/**
	 * @author A-9844
	 * Desc- verify error message 
	 * @throws IOException 
	 */
		public void verifyFWDZONErrorMessage() throws IOException {
			
			String locatorValue=getPropertyValue(proppathhht, "offload_btn_errMsg;xpath");

            locatorValue=locatorValue.replace("*", "User does not have privilege to modify forward zone.");
			
				int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();
				
				if(eleSize==1)
				{
					writeExtent("Pass","Verified the error message: User does not have privilege to modify forward zone.");
					clickActionInHHT("offload_btn_closeMsg;xpath",proppathhht,"FWDZON Error message",screenName);
					waitForSync(1);
				}
				else
				{
					writeExtent("Fail","Could not verify the error message: User does not have privilege to modify forward zone.");
				}
			
		}
	/**
	 * @author A-9478
	 * @param location
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered location
	 * @throws IOException 
	 */
	public void enterLocation(String Location) throws AWTException, InterruptedException, IOException
	{
			waitForSync(2);
			enterValueInHHT("offload_inbx_location;xpath",proppathhht,data(Location),"Location",screenName);
		 
	}
	
	/**
	 * @author A-9478
	 * @param location
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Enter Pieces,weight
	 * @throws IOException 
	 */
	public void enterPiecesAndWeight(String Pieces,String Weight) throws AWTException, InterruptedException, IOException
	{	
			waitForSync(2);
			enterValueInHHT("offload_inbx_pieces;accessibilityId",proppathhht,data(Pieces),"Pieces",screenName);
			scrollInMobileDevice("Weight(kg)");
			waitForSync(5);
			enterValueInHHT("offload_inbx_weight;accessibilityId",proppathhht,data(Weight),"Weight",screenName);
	}
	/**
     * @author A-9478
     * @param awbNumber
     * @throws AWTException
     * @throws InterruptedException
     * Description : entered awb number in hht
	 * @throws IOException 
     */
     public void enterStorageUnit(String SU) throws AWTException, InterruptedException, IOException
     {
           
                 enterValueInHHT("offload_inbx_SU;accessibilityId",proppathhht,data(SU),"Storage Unit",screenName);
                 waitForSync(5);
     }



	/**
	 * @author A-9478
	 * Description : Select offload reasons
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void selectOffloadReasons(String offloadReason) throws InterruptedException, IOException
	{
		
		try
		{
		scrollInMobileDevice("Offload Reason");
		clickActionInHHT("offload_lst_offloadReason;xpath",proppathhht,"Offload Reason",screenName);
		waitForSync(5);
		String locator = WebFunctions.getPropertyValue(proppathhht,"offload_lst_offloadReasonValue;xpath");
        locator=locator.replace("OffloadReason", offloadReason);
        scrollInMobileDevice(offloadReason);
        androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(2);
		writeExtent("Pass", "Offload Reason "+offloadReason+" is selected "+" in "+screenName);
	}
		
		catch(Exception e)
		{
			 captureScreenShot("Android");
			 writeExtent("Fail", "Failed to select Offload Reason "+offloadReason+" in "+screenName);
		}

		
	}
	
	/**
	 * @author A-9478
	 * Description : Click Save
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void clickSave() throws InterruptedException, IOException
	{
		clickActionInHHT("offload_btn_save;xpath",proppathhht,"Save button",screenName);
		waitForSync(8);
	}
	/**
	 * Desc : Click ok to CON002 popup
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickCON002() throws InterruptedException, IOException
	{
		clickActionInHHT("offload_btn_CON002ok;xpath",proppathhht,"CON002 button",screenName);
		waitForSync(2);
	}
	
	/**
	 * @author A-9478
	 * @param location
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Enter Pieces
	 * @throws IOException 
	 */
	public void enterPieces(String Pieces) throws AWTException, InterruptedException, IOException
	{	
		String locator = WebFunctions.getPropertyValue(proppathhht,"offload_inbx_pieces;accessibilityId");
		if(androiddriver.findElements(By.xpath(locator)).size()!=1)
		{
			waitForSync(1);
		}
		enterValueInHHT("offload_inbx_pieces;accessibilityId",proppathhht,data(Pieces),"Pieces",screenName);
		waitForSync(5);
	}
	
}
