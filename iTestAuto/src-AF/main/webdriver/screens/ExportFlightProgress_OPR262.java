package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ExportFlightProgress_OPR262 extends CustomFunctions {
	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "ExportFlightProgress_OPR262";
	String screenName = "Export Flight Progress";
	String screenId = "OPR262";

	public ExportFlightProgress_OPR262(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
/**
 * Description... Select Flight Check Box
 * @throws InterruptedException
 * @throws IOException 
 */
	// selects flight check box
	public void selectFlightCheckBox() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chk_selectFlight;xpath",
				"Flight Select Check Box", screenName);

	}
/**
 * Description... Click Auto Load Plan
 * @throws InterruptedException
 * @throws IOException 
 */
	// clicks auto load plan button
	public void clickAutoLoadPlan() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_AutoLoadPlan;xpath",
				"Auto Load Plan Button", screenName);
waitForSync(5);
	}
/**
 * Description... Click Load Plan
 * @throws InterruptedException
 * @throws IOException 
 */
	// clicks load plan button
	public void clickLoadPlan() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_LoadPlan;xpath", "Load Plan Button",
				screenName);
	}
	/**
	 * Description... Change To Date
	 * @param toDate
	 * @throws InterruptedException
	 */
	public void changeToDate(String toDate) throws InterruptedException {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_todate;xpath", toDate, "To Date", screenName);	
				
	}
	/**
	 * @author A-10690
	 * Desc - Verify export print status column colour 
	 * @param expected colour
	 * @param flight number
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void verifyExportPrintStatusColour(String colour,String flightNumber) throws InterruptedException, AWTException{
		
		
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "txt_printmanifestcolumnnum;xpath");
		String columnnumber = driver.findElement(By.xpath(locator)).getAttribute("data-ic-csid");
		String locator1=xls_Read.getCellValue(sheetName, "txt_printmanifestcolour;xpath");
		locator1=locator1.replace("flgt",data(flightNumber));
		locator1=locator1.replace("*",columnnumber);
		locator1=locator1.replace("clr",colour);

		if(driver.findElements(By.xpath(locator1)).size()==1)
		{
			writeExtent("Pass","Successfully verified  export print status colour on "+screenName);
		}
		else{
			writeExtent("Fail","Failed to verify export print status colour "+screenName);
		}

		
		
	}	
/**
 * Description... Change From Date
 * @param fromDate
 * @throws InterruptedException
 */
public void changeFromDate(String fromDate) throws InterruptedException {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_fromdate;xpath",fromDate, "From Date", screenName);	
				
	}
	
/**
 * Description... Click Booked Flight CheckBox
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickBookedFlightCheckBox() throws InterruptedException, IOException
	{
		waitForSync(3);
		clickWebElement(sheetName, "chk_bookedFlight;xpath", "check box of booked flights",
				screenName);
	}
	
/**
 * Description... Enter Destination
 * @param destination
 * @throws InterruptedException
 */
	public void enterDestination(String destination) throws InterruptedException
	{
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_destination;xpath",data(destination), "From Date", screenName);	
		
		
	}
	
/**
 * Description... Click List Button	
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickList() throws InterruptedException, IOException
	{
		clickWebElement("Generic_Elements", "btn_List;xpath", "List Button",screenName);
		waitForSync(5);
	}
	
/**
 * Description... Select flight	
 * @param flight
 * @throws InterruptedException
 */
	public void selectflight(String flight) throws InterruptedException
	{
		String  xpath=xls_Read.getCellValue("ExportFlightProgress_OPR262","chk_selectFlght;xpath").replace("flight",flight);
		waitForSync(5);
		clickWebElement(xpath,"checkbox","Export Flight Progress");
		
		
	}
	
	
	
}