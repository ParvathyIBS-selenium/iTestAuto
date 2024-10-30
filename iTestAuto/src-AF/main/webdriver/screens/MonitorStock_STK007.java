package screens;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class MonitorStock_STK007 extends CustomFunctions {

	String sheetName = "MonitorStock_STK007";
	public CustomFunctions customFuction;
	String screenID = "STK007";
	public String screenName = "MonitorStock";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";


	public MonitorStock_STK007(WebDriver driver,ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}


	/**
	 * 
	 * @param holderType
	 * @param holderCode
	 * @param docType
	 * @param subType
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : enter stock holder details
	 */
	public void enterStockHolderDetails(String holderType, String holderCode, String docType, String subType) throws InterruptedException, IOException {

		//H=HQ R=REGION S=STATION A=AGENT(Holder Type)
		//Listing with mandatory fields - stockHolderType, stockHolderCode, DocType, SubType
		selectValueInDropdown(sheetName, "drpdn_stkHldType;id", data(holderType), "Stock Holder Type", "Value");
		enterValueInTextbox(sheetName, "inbx_stkHldCode;id", data(holderCode), "Stock Holder Code", screenName);
		selectValueInDropdown(sheetName, "drpdn_docType;id", data(docType), "Doc Type", "Value");
		selectValueInDropdown(sheetName, "drpdn_subType;id", data(subType), "Sub Type", "Value");
		clickWebElement(sheetName, "btn_list;id", "List Button", screenName);

	}

	/**
	 * 
	 * @param pmyKey
	 * @param tbltag
	 * @param verfCols
	 * @param actVerfValues
	 * @throws IOException
	 * Desc : verify stock holder details
	 */
	public void verifyStockHolderDetails(String pmyKey, String tbltag, int verfCols[], String actVerfValues[]) throws IOException{

		//To Verify Stock Holder details from the table
		verify_tbl_records_multiple_cols(sheetName, "tble_stkHolder;xpath", tbltag,verfCols, data(pmyKey), actVerfValues);

	}

	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click create stock
	 */
	public void clickCreateStock() throws InterruptedException, IOException{

		//To click the Create Stock Button
		clickWebElement(sheetName, "btn_createStock;id", "Create Stock Button", screenName);
		waitForSync(3);
	}

	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click view range
	 */
	public void clickViewRange() throws InterruptedException, IOException{

		//To click the View Range Button
		clickWebElement(sheetName, "btn_viewRange;id", "View Range Button", screenName);
		waitForSync(3);

	}

	/**
	 * 
	 * @param pmyKey
	 * Desc : click check box
	 */
	public void clickCheckBox(String pmyKey){

		// Clicking a particular row's check-box by pmyKey
		selectTableRecordJS(data(pmyKey),"clk_chckbx;xpath", sheetName, 1);
		waitForSync(2);

	}

	/**
	 * 
	 * @param pmyKey
	 * @param tbltag
	 * @param verfCols
	 * @param actVerfValues
	 * @throws Exception
	 * Desc : verify view range
	 */
	public void verifyViewRange(String pmyKey, String tbltag, int verfCols[], String actVerfValues[]) throws Exception{

		//To verify the specified stock range in stock range table(pmyKey can be Stock range from or to)
		switchToWindow("storeParent");
		switchToWindow("child");
		verify_tbl_records_multiple_cols(sheetName, "tble_viewRange;xpath", tbltag,verfCols, data(pmyKey), actVerfValues);
		clickWebElement(sheetName, "btn_viewRangeClose;xpath", "Close Button on View Range Pop-up Window",screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "STK007");

	}




}
