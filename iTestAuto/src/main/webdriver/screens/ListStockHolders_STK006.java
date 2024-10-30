package screens;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;



public class ListStockHolders_STK006 extends CustomFunctions{
	
	String sheetName="ListStockHolders_STK006";
	String ScreenName="List Stock Holders";
	String ScreenID="STK006";
	
	public ListStockHolders_STK006(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
	}
	
	
	/**
	 * @author A-8783
	 * Decription - List Stock holder details
	 * @param stockHolderType
	 * @param stockHolderCode
	 * @param docType
	 * @param subType
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void listStockHolderDetails (String stockHolderType, String stockHolderCode, String docType, String docSubType) throws InterruptedException, IOException {
		 selectValueInDropdown(sheetName, "lst_stockHolderType;name",  data(stockHolderType), "Select Stock Holder Type dropdown","VisibleText"); 
         enterValueInTextbox(sheetName, "inbx_stockHolderCode;name", data(stockHolderCode), "Stock Holder Code", ScreenName);
		 selectValueInDropdown(sheetName, "lst_docType;name",  data(docType), "Select Stock Holder Type dropdown","Value"); 
		 selectValueInDropdown(sheetName, "lst_docSubType;name",  data(docSubType), "Select Stock Holder Type dropdown","Value"); 
		 waitForSync(2);
		 clickWebElement(sheetName, "btn_list;id", "List button", ScreenName);
		 waitForSync(5);
	}
	
	/**
	 * Description... Verify stock holder details
	 * @param verfCols
	 * @author A-8783
	 * @param actVerfValues
	 * @throws IOException 
	 */
	public void verifyStockHolderDetails(int verfCols[], String actVerfValues[], String stockHolderCode) throws IOException
	{



		verify_tbl_records_multiple_cols(sheetName, "table_stockHolderTable;xpath", "//td", verfCols, data(stockHolderCode),
				actVerfValues);
		waitForSync(1);
	}	
	
	
 }

