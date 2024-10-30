package screens;

import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ImportFlightEnquiry_OPR008 extends CustomFunctions {
	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "ImportFlightEnquiry_OPR008";
	String screenName = "Import Flight Enquiry";
	String screenId = "OPR008";

	public ImportFlightEnquiry_OPR008(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
	
	/**
	 * @author A-9175
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
		clickWebElementByWebDriver(sheetName, "btn_list;id", "List Button", screenId);
		waitForSync(5);
	}
	
	
	/**
	 * @author A-9175
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * Description : verify flight details
	 */
	public void verifyFlightDetails(int verfCols[], String actVerfValues[],
			String pmKey)
	{
		verify_tbl_records_multiple_cols_and_select(sheetName, "table_importFlightEnquiry;xpath",
				"//td", verfCols, pmKey, actVerfValues);
	}
	
	public void verifyBULKShipmentSummary() throws InterruptedException
	{
		waitForSync(2);
		getElementText(sheetName,"txt_AWBsManifested;xpath", "AWB Manifested Count For BULK", screenName);
		waitForSync(5);
	}
	
	
	public void verifyULDShipmentSummary() throws InterruptedException
	{
		waitForSync(2);
		getElementText(sheetName,"txt_ULDsManifested;xpath", "AWB Manifested Count for ULD ", screenName);
		waitForSync(5);
	}
	

	
}
