package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ScreeningFacilityDetails_SHR122 extends CustomFunctions {

	public ScreeningFacilityDetails_SHR122(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
	}

	public String sheetName = "ScreeningFacilityDetails_SHR122";
	public String screenName = "ScreeningFacilityDetails";
	String globalVarPath = "\\src\\resources\\GlobalVariable.properties";
	/**
	 * Description... Create New RA
	 * @param Authority
	 * @param RANumber
	 * @param ScreenerFacilityName
	 * @param ExpiryDate
	 * @throws Exception
	 */
	public void createNewRA(String Authority,String RANumber,String ScreenerFacilityName,String ExpiryDate) throws Exception {
		Thread.sleep(2000);
		// Click on Add New button for adding details
		clickWebElement(sheetName, "btn_AddNew;xpath", "Click Add New button", screenName);
		switchToWindow("storeParent");
		switchToWindow("child");
		// Authority
		selectValueInDropdown(sheetName, "lst_authorityType;xpath", data(Authority), "Select Authority Type", "VisibleText");
		// Number
		enterValueInTextbox(sheetName, "inbx_Number;xpath", data(RANumber), "Enter RANumber", screenName);
		// Screener facility Name
		enterValueInTextbox(sheetName, "inbx_screenerFacilityName;xpath", data(ScreenerFacilityName), "Enter Screener Facility Name", screenName);
		Thread.sleep(2000);
		// Expiry
		enterValueInTextbox(sheetName, "inbx_expiryDate;xpath", data(ExpiryDate), "Enter Expiry Date", screenName);
		waitForSync(6);
		keyPress("TAB");
		waitForSync(6);
		// Add
		clickWebElement(sheetName, "btn_Add;xpath", "Add Details", screenName);
		waitForSync(6);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("SHR122");
		// Save
		clickWebElement(sheetName, "btn_Save;xpath", "Save Details", screenName);		
		Thread.sleep(2000);
	}
/**
 * Description... Delete Existing RA
 * @param Authority
 * @param RANumber
 * @param ScreenerFacilityName
 * @param ExpiryDate
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void deleteExistingRA(String Authority,String RANumber,String ScreenerFacilityName,String ExpiryDate) throws InterruptedException, AWTException, IOException {
		Thread.sleep(2000);
		// Authority
		selectValueInDropdown(sheetName, "lst_authorityType;xpath", data(Authority), "Select Authority Type", "VisibleText");
		// Number
	//	enterValueInTextbox(sheetName, "inbx_Number;xpath", data(RANumber), "Enter RANumber", screenName);
		// Screener facility Name
		enterValueInTextbox(sheetName, "inbx_screenerFacilityName;xpath", data(ScreenerFacilityName), "Enter Screener Facility Name", screenName);
		// Expiry
	//	enterValueInTextbox(sheetName, "inbx_validTo;xpath", data(ExpiryDate), "Enter Expiry Date", screenName);
		//keyPress("TAB");
		// List
		clickWebElement(sheetName, "btn_List;xpath", "List Details", screenName);		
		waitForSync(6);
		// Delete
		clickWebElement(sheetName, "btn_Delete;xpath", "Delete Details", screenName);	
	
	}
	

}
