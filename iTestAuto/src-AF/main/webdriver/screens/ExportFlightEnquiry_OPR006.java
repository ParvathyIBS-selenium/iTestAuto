package screens;

import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ExportFlightEnquiry_OPR006 extends CustomFunctions {
	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "ExportFlightEnquiry_OPR006";
	String screenName = "Export Flight Enquiry";
	String screenId = "OPR006";

	public ExportFlightEnquiry_OPR006(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
	
	/**
	 * @author A-7271
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * Description : list flight
	 */
	public void listFlight(String carrierCode,String flightNumber,String flightDate) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(carrierCode), "Carrier Code", screenId);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(flightNumber), "Flight Number", screenId);
		waitForSync(1);
		  performKeyActions(sheetName,"inbx_flightNumber;name", "TAB","Flight Number", screenId);
		  waitForSync(1);
		enterValueInTextboxByJS(sheetName, "inbx_flightDate;name", data(flightDate), "Flight Date", screenId);
		clickWebElementByWebDriver(sheetName, "btn_list;name", "List Button", screenId);
		waitForSync(5);
	}
	
	public void printReport() throws Exception
	{
		switchToWindow("storeParent");
		clickWebElementByWebDriver(sheetName, "btn_view;name", "View Button", screenId);
		waitForSync(3);
		switchToWindow("multipleWindows");
		
		int windowSize=getWindowSize();
	
		if(windowSize==2)
		{
			onPassUpdate(screenId, "window size should be 2 ", "window size is "+windowSize, "Verify whether the report is generated",
					"Verify whether the report is generated");
		}
		else
		{
			onFailUpdate(screenId, "window size should be 2 ", "window size is "+windowSize, "Verify whether the report is generated",
					"Verify whether the report is generated");
		}
		closeBrowser();
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame","OPR006");
	}
	
	/**
	 * @author A-7271
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * Description : verify flight details
	 */
	public void verifyFlightDetails(int verfCols[], String actVerfValues[],
			String pmKey)
	{
		verify_tbl_records_multiple_cols_and_select(sheetName, "table_exportFlightEnquiry;xpath",
				"//td", verfCols, pmKey, actVerfValues);
	}
	
}
