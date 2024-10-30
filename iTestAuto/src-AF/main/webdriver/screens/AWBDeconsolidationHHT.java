package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AWBDeconsolidationHHT extends CustomFunctions {
	
	String sheetName = "AWBDeconsolidationHHT";
	String screenName = "AWBDeconsolidationHHT";
	

	public AWBDeconsolidationHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht AWBDeconsolidationHHT screen
	 * @throws IOException 
	 */
	public void invokeAWBDeconsolidationHHTScreen() throws InterruptedException, AWTException, IOException {
	
		
		clickActionInHHT("awbdeconsolidation_menu;xpath",proppathhht,"Awb deconsolidation menu",screenName);
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
		enterValueInHHT("awbdeconsol_inbx_Awb;accessibilityId",proppathhht,data(awbNumber),"Awb Number",screenName);
		waitForSync(10); 
	}
	
	/**
	 * @author A-7271
	 * @param location
	 * Desc : enter location
	 * @throws IOException 
	 */
	public void enterLocation(String location) throws IOException
	{
		enterValueInHHT("awbdeconsol_inbx_location;accessibilityId",proppathhht,data(location),"location",screenName);
		
	}
	/**
	 * @author A-7271
	 * @param Storage Unit
	 * Desc : Enter storage unit
	 * @throws IOException 
	 */
	public void enterSU(String storageUnit) throws IOException
	{
		enterValueInHHT("awbdeconsol_inbx_su;accessibilityId",proppathhht,data(storageUnit),"Storage Unit",screenName);
		
	}
	/**
	 * @author A-7271
	 * @param hawb
	 * Desc : Enter HAWB
	 * @throws IOException 
	 */
	public void enterHAWB(String hawb) throws IOException
	{
		enterValueInHHT("awbdeconsol_inbx_hawb;accessibilityId",proppathhht,data(hawb),"hawb",screenName);
		waitForSync(2); 
	}
	/**
	 * @author A-7271
	 * @param pieces
	 * Desc : Enter pieces
	 * @throws IOException 
	 */
	public void enterPieces(String pieces) throws IOException
	{
		enterValueInHHT("awbdeconsol_inbx_pcs;accessibilityId",proppathhht,data(pieces),"Pieces",screenName);
		waitForSync(1); 
	}
	
	/**
	 * @author A-7271
	 * Desc : Save details
	 * @throws IOException 
	 */
	public void saveDetails() throws IOException
	{
		clickActionInHHT("awbdeconsol_btn_Save;xpath",proppathhht,"Save",screenName);
		waitForSync(10);
	}
	
	/***
	 * @author A-7271
	 * Desc : Click more options
	 * @throws IOException 
	 */
	public void clickMoreOptions() throws IOException
	{
		clickActionInHHT("awbdeconsol_btn_moreOptions;xpath",proppathhht,"More Options",screenName);
		waitForSync(5);
	}
	/**
	 * @author A-7271
	 * Desc : deconsolidation completed
	 * @throws IOException 
	 */
	public void clickDeconsolidationComplete() throws IOException
	{
		clickActionInHHT("awbdeconsol_btn_deconsolidationCompleted;xpath",proppathhht,"Deconsolidation completed",screenName);
		waitForSync(5);
	}
	/**
	 * Select SCC
	 * @throws IOException 
	 */
	public void selectSCC(String SCC) throws IOException
	{
		clickActionInHHT("awbdeconsol_btn_SCC;xpath",proppathhht,"Select SCC",screenName);
		waitForSync(4);
		
		String locator=getPropertyValue(proppathhht, "awbdeconsol_lst_SCC;xpath");
        locator=locator.replace("SCC", data(SCC));
		androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(4);
	}
	/**
	 * @author A-7271
	 * @param pcs
	 * Desc : enter pcs after scroll
	 * @throws IOException 
	 */
	public void enterPcsAfterScroll(String pcs) throws IOException
	{
		scrollInMobileDevice("Pieces");
		enterValueInHHT("awbdeconsol_inbx_pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
		waitForSync(1); 
	}
	/**
	 * @author A-7271
	 * @param pcs
	 * Desc : verify available pcs
	 * @throws IOException 
	 */
	public void verifyAvailablePcs(String pcs) throws IOException
	{
		String locator=getPropertyValue(proppathhht, "awbdeconsol_inbx_availablePcs;xpath");
        locator=locator.replace("avilablePcs", data(pcs));
        
        int size=androiddriver.findElements(By.xpath(locator)).size();
        
      
        	if(size==2)
			{
			 writeExtent("Pass", "Available pieces are "+data(pcs)+" in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "Available pieces mismatch in "+screenName);
			}
        
	}
}
