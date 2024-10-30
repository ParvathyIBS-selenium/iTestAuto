package screens;

import org.openqa.selenium.WebDriver;

import java.io.IOException;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ListActiveAllotments_CAP030 extends CustomFunctions{
	public ListActiveAllotments_CAP030(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName="ListActiveAllotments_CAP030";
	public String ScreenName="List Active Allotments";
	String GenericSheet = "Generic_Elements";


	/**
	 * @author A-9844
	 * Desc..Enter Allotment ID
	 * @param allotmentID
	 * @throws InterruptedException
	 */
	public void enterAllotment(String allotmentID ) throws InterruptedException{

		enterValueInTextbox(sheetName, "inbx_allotmentID;xpath", data(allotmentID), "Allotment ID", ScreenName);
	}

	
	/**
	 * @author A-9844
	 * Desc..Enter date range
	 * @param fromdate
	 * @param todate
	 * @throws InterruptedException
	 */
	public void enterDateRange(String fromdate,String toDate ) throws InterruptedException{

		enterValueInTextbox(sheetName, "inbx_fromDate;xpath", data(fromdate), "from date", ScreenName);
		enterValueInTextbox(sheetName, "inbx_toDate;xpath", data(toDate), "to date", ScreenName);
	}
	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : list allotments
	 */
	public void clicklistallotments() throws InterruptedException, IOException{

		clickWebElement(sheetName, "list_allotments;xpath","list allotments", ScreenName);
		waitForSync(1);
	}
	/**
	 * @author A-9844
	 * Desc..Verify allotment details
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyAllotmentDetails(int verfCols[],String actVerfValues[],String pmKey ) throws InterruptedException, IOException{

		verify_tbl_records_multiple_cols(sheetName, "table_allotmentDetails;xpath", "//td", verfCols, pmKey, actVerfValues);
	}


	
}

