package screens;


import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ImportFlightProgress_OPR337 extends CustomFunctions {
	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "ImportFlightProgress_OPR337";
	String screenName = "Import Flight Progress";
	String screenId="OPR263";

	public ImportFlightProgress_OPR337(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
	
	/**A-8705
	 * Description... List Flight
	 * 
	 * @param ScreenID
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listFlight(String ScreenID, String carrierCode,
			String flightNumber, String flightDate, String sheetName)
			throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName,"chk_NIL_Flights;xpath", "NIL Checkbox",ScreenID);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_todateFilter;name",flightDate,
				"to Date Filter", screenName);
		waitForSync(3);
		clickWebElement(sheetName,"lnk_advance_search_options;xpath", "Advanced search options link",ScreenID);
		waitForSync(3);
		enterValueInTextbox("Generic_Elements", "inbx_carrierCode;xpath",
				carrierCode, "Carrier Code", ScreenID);
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", flightNumber,
				"Flight Number", ScreenID);
		enterValueInTextbox("Generic_Elements", "inbx_flightDate;xpath",
				flightDate, "Flight Date", ScreenID);	
		keyPress("TAB");
		keyRelease("TAB");	
		keyPress("TAB");
		keyRelease("TAB");
		clickWebElement("Generic_Elements", "btn_list;name", "List Button",
				ScreenID);
		waitForSync(3);

	}
	
	/**
	 * Desc : Entering flight details by check or without check  nill Flight Checkbox
	 * @author A-9175
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * @param nillFlightCheckbox
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listFlightBasedOnNilFlightCheckbox(String carrierCode,String flightNumber, String flightDate,boolean nillFlightCheckbox)throws InterruptedException, AWTException, IOException {
		if(nillFlightCheckbox)
		{
			clickWebElement(sheetName,"chk_NIL_Flights;xpath", "NIL Checkbox",screenName);
			waitForSync(3);
			enterValueInTextbox(sheetName, "inbx_todateFilter;name",data(flightDate),"to Date Filter", screenName);
			waitForSync(3);
			clickWebElement(sheetName,"lnk_advance_search_options;xpath", "Advanced search options link",screenName);
			waitForSync(3);
			enterValueInTextbox("Generic_Elements", "inbx_carrierCode;xpath",data(carrierCode), "Carrier Code", screenName);
			enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(flightNumber),"Flight Number", screenName);
			enterValueInTextbox("Generic_Elements", "inbx_flightDate;xpath",data(flightDate), "Flight Date", screenName);
			waitForSync(3);
			performKeyActions("Generic_Elements","inbx_flightDate;xpath", "TAB","Flight Number", screenName);
			waitForSync(1);
			clickWebElement("Generic_Elements", "btn_list;name", "List Button",screenName);
			waitForSync(3);
		}
		else
		{
			
			enterValueInTextbox(sheetName, "inbx_todateFilter;name",data(flightDate),"to Date Filter", screenName);
			waitForSync(3);
			clickWebElement(sheetName,"lnk_advance_search_options;xpath", "Advanced search options link",screenName);
			waitForSync(3);
			enterValueInTextbox("Generic_Elements", "inbx_carrierCode;xpath",data(carrierCode), "Carrier Code", screenName);
			enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(flightNumber),"Flight Number", screenName);
			enterValueInTextbox("Generic_Elements", "inbx_flightDate;xpath",data(flightDate), "Flight Date", screenName);
			waitForSync(3);
			waitForSync(1);
			performKeyActions("Generic_Elements","inbx_flightDate;xpath", "TAB","Flight Number", screenName);
			clickWebElement("Generic_Elements", "btn_list;name", "List Button",screenName);
			waitForSync(3);
		}

	}
	/**
	 * Desc : Verifying flight listed or not
	 * @author A-9175
	 * @param flightNo
	 * @param verfCols
	 * @param actVerfValues
	 * @throws Exception
	 */
public void verifyFlightDetails(String flightNo, int[] verfCols, String[] actVerfValues) throws Exception {
			verify_tbl_records_multiple_cols_contains(sheetName, "tab_flightDetails;xpath", "//td", verfCols, flightNo,
					actVerfValues);}


/**
 * Desc : verifying flight is not found
 * @author A-9175
 * @param flightNo
 * @throws Exception
 */
public void verifyFlightDetailsNotFound(String flightNo) throws Exception {
	try
	{
		String locator = xls_Read.getCellValue("Generic_Elements", "txt_errorMessage;xpath");
		boolean errorPopUp=driver.findElement(By.xpath(locator)).isDisplayed();
		if(errorPopUp)
		writeExtent("Pass", "Flight Details Not Found for "+ data(flightNo)+" on " + screenName + " Page");
		else
		writeExtent("Fail", "Flight Details  Found for "+ flightNo+" on " + screenName + " Page");
	}
	catch (Exception e) {
		writeExtent("Fail", "Flight Details  Found for "+ flightNo+" on " + screenName + " Page");
	}
	}

	/**A-8705
	 * Description... Click Flight Enquiry
	 * 
	 * @param 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickFlightEnquiry() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_MoreInformation;xpath", "Flight Check Box", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_flightEnquiry;xpath", "Flight Enquiry Button", screenName);
		waitForSync(5);
	}
	
	/**A-8705
	 * Description... Verifies CLS is red
	 * 
	 * @param ScreenID
	 * @throws InterruptedException
	 */
	public void checkCLSred() throws Exception
	{
		waitForSync(3);
		verifyElementDisplayed("//table[@id='importProgressDataTable']//tbody//tr[1]//td[10]//*[@class='icon ico-minus-red-round']", "check if red mark is displayed", screenName, "CLS");
	}
	
	
	/**A-8705
	 * Description... Verifies CLS is green
	 * 
	 * @param ScreenID
	 * @throws InterruptedException
	 */
	public void checkCLSgreen() throws Exception
	{
		waitForSync(3);
		verifyElementDisplayed("//table[@id='importProgressDataTable']//tbody//tr[1]//td[10]//*[@class='icon ico-ok-green']", "check if green mark is displayed", screenName, "CLS");
	}
	
	
	
}
