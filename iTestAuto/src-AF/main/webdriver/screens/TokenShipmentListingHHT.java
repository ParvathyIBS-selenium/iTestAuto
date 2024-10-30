package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class TokenShipmentListingHHT extends CustomFunctions {
	
	String sheetName = "TokenShipmentListingHHT";
	String screenName = "TokenShipmentListingHHT";
	

	public TokenShipmentListingHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht Token Shipment Listing screen
	 */
	public void invokeTokenShipmentListingScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Token Shipment Listing");	
		clickActionInHHT("tokenshipmentlisting;xpath",proppathhht,"Token Shipment Listing menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "Token Shipment Listing hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Token Shipment Listing hht screen is not invoked successfully");
		}
	}
	/**
	 * @author A-9175
	 * @throws IOException
	 */
	public void clickSelectULDIcon() throws IOException {
		waitTillMobileElementDisplay(proppathhht, "tokenshipmentlisting_selectULDIcon;xpath", "xpath");
		clickActionInHHT("tokenshipmentlisting_selectULDIcon;xpath", proppathhht, "Select ULD Icon", screenName);
		waitForSync(2);

	}

/**
	 * @author A-9175 Description : select ULD pending icon
	 * @throws IOException
 */
	public void clickPendingULDIcon() throws IOException {

		clickActionInHHT("tokenshipmentlisting_selectULDPendingIcon;xpath", proppathhht, "Pending Icon", screenName);
		waitForSync(3);
	}
	/**
	 * @desc : next
	 * @author A-9175
	 * @throws IOException
	 */
	public void Next() throws IOException {
		clickActionInHHT("tokenshipmentlisting_NextButton;xpath", proppathhht, "Next", screenName);

	}

	/**
	 * @author A-9844
	 * Description : click the pending AWB icon
	 * @throws IOException 
	 */
	public void clickSelectAWBIcon() throws IOException 
	
	
	{
		
		waitTillMobileElementDisplay(proppathhht,"tokenshipmentlisting_selectAWBIcon;xpath","xpath");
		clickActionInHHT("tokenshipmentlisting_selectAWBIcon;xpath",proppathhht,"Select AWB Icon",screenName);
		waitForSync(2);

	}


	/**
	 * @author A-9844
	 * Description : select AWB
	 * @throws IOException 
	 */
	public void clickPendingIcon() throws IOException 
	{
		

		clickActionInHHT("tokenshipmentlisting_selectPendingIcon;xpath",proppathhht,"Pending Icon",screenName);
		waitForSync(3);
		

	}
	/**
	 * @author A-9844
	 * Description : click Next Button
	 * @throws IOException 
	 */
	public void clickNext() throws IOException 
	{
		clickActionInHHT("tokenshipmentlisting_NextButton;xpath",proppathhht,"Next",screenName);
		waitTillMobileElementDisplay(proppathhht,"gahht_txt_checksheet;xpath","xpath");

	}
	
	/**
	 * @author A-9175
	 * @param token
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb number in hht
	 * @throws IOException 
	 */
	public void enterToken(String token) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("tokenList;accessibilityId",proppathhht,data(token),"Token Number",screenName);
			waitForSync(12);
	}
	
	
	/**
	 * @author A-9175
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb number in hht
	 * @throws IOException 
	 */
	public void enterAwbNumber(String awbNumber) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("tokenList_txt_AWBNumber;accessibilityId",proppathhht,data(awbNumber),"Awb Number",screenName);
			waitForSync(12);
	}

}
