package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class OffloadEnquiry_OPR011 extends CustomFunctions  {

	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName="OffloadEnquiry_OPR011";
	String ScreenName="Offload Enquiry";
	String screenId="OPR011";


	public OffloadEnquiry_OPR011(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction=new CustomFunctions(driver, excelReadWrite, xls_Read2);
	}
	
	/**
	 * Description... Verify offload details
	 * @param verfCols
	 * @param actVerfValues
	 * @throws IOException 
	 */
	public void verifyOffloadDetails(int verfCols[], String actVerfValues[]) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_ListOffload;xpath", "//td", verfCols, data("prop~AWBNo"),
				actVerfValues);
		waitForSync(3);
	}
	
	/**
	 * Description... List by flight
	 * @throws InterruptedException
	 * @throws IOException 
	 */
		public void listByFlight(String carrierCode, String flightno) throws InterruptedException, IOException
		{
			enterValueInTextbox(sheetName, "inbx_carrierCode;id", data(carrierCode), "Carrier Code", "OPR011");
			enterValueInTextbox(sheetName, "inbx_flightNo;id", data(flightno), "Flight number", "OPR011");
			clickWebElement(sheetName, "btn_list;id", "List Button", ScreenName);
			waitForSync(5);
		}

	
}