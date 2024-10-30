package screens;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class TransactionBlockSetUp_OPR041 extends CustomFunctions {
	public TransactionBlockSetUp_OPR041(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "TransactionBlockSetUp_OPR041";
	public String ScreenName = "Transaction Block SetUp : OPR041";


	/**
	 * @author A-6260
	 * Desc..Click List
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickList() throws InterruptedException, IOException
	{                
		clickWebElement(sheetName, "btn_list;name", "List button", ScreenName);
		waitForSync(3); 

	}

	/**
	 * @author A-6260
	 * Description... select block type
	 * @throws Exception 
	 */
	public void selectBlockType(String BlockType) throws Exception
	{                
		selectValueInDropdown(sheetName, "lst_blockType;name", 
				data(BlockType), "Select Block Type", 
				"VisibleText");
		waitForSync(3); 

	}
	/**
	 * Description... Verify transaction block details
	 * @author A-6260
	 * @throws IOException 
	 */
	public void verifyTransactionBlockdetails(String pmKeyCol,String pmKey,int[] colVal,int[] colVal2,String[] actVal,String[]actVal2) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_TransactionBlockDetails;xpath", pmKeyCol,pmKey,colVal,colVal2,actVal,actVal2,ScreenName);
		waitForSync(3);
	}

	/**
	 * @author A-6260
	 * Description... select transaction
	 * @throws Exception 
	 */
	public void selectTransaction(String transaction) throws Exception
	{                
		selectValueInDropdown(sheetName, "lst_blockedTransaction;name", 
				data(transaction), "Select blocked transaction ", 
				"VisibleText");
		waitForSync(3); 

	}

	public void selectCheckType(String checktype) throws Exception
	{                
		selectValueInDropdown(sheetName, "lst_CheckType;name", 
				data(checktype), "Select check type ", 
				"VisibleText");
		waitForSync(3); 

	}

	public void selectBlockTypeAndTransaction(String BlockType,String transaction) throws Exception
	{                
		selectValueInDropdown(sheetName, "lst_blockType;name", 
				data(BlockType), "Select Block Type", 
				"VisibleText");
		selectValueInDropdown(sheetName, "lst_blockedTransaction;name", 
				data(transaction), "Select blocked transaction ", 
				"VisibleText");
		waitForSync(3); 
	}



}