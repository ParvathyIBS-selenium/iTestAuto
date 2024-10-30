package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ListIrregularity_OPR341 extends CustomFunctions{
	public ListIrregularity_OPR341(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	
	
	public String sheetName="ListIrregularity_OPR341";
	public String ScreenName="ListIrregularity";

	/**
	 * Description... Verify Irregularity Details Value
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 */
  public void verifyIrregularityDetailsValue(int verfCols[],String actVerfValues[],String pmKey)
			 throws InterruptedException {
	
		
			waitForSync(1);
			verify_tbl_records_multiple_cols_contains(sheetName, "table_ListIrregularity;xpath", "//td", verfCols, pmKey, actVerfValues);	
	}
  /**
	 * @author A-10690
	 * Desc- List with token number
	 * @param tokenID
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void listToken(String tokenID) throws InterruptedException, IOException {
		enterValueInTextbox(sheetName, "btn_tokenno;name", data(tokenID), "Token number", ScreenName);
		clickWebElement(sheetName, "btn_list;name", "List Button", ScreenName);
		waitForSync(2);
	}
	/**
	 * @author A-9844
	 * Description... to select the transaction code
	 * @param 
	 * @throws InterruptedException
	 */
	public void selectSubModuleName(String transaction) throws InterruptedException{
		waitForSync(2);

		selectValueInDropdown(sheetName, "lst_submoduleName;name", transaction, "Transction Code", "VisibleText");		
	}


	/**
	 * @author A-8783
	 * Desc - Scroll till text is found
	 * @param irregCode
	 */
public void scrollTillText(String irregCode){
	String irrgCodeLoc=xls_Read.getCellValue(sheetName, "txt_irregCode;xpath");
	irrgCodeLoc=irrgCodeLoc.replace("*", irregCode);
	moveScrollBar(driver.findElement(By.xpath(irrgCodeLoc)));
	waitForSync(1);
}

	/**
	 * @author A-8783
	 * Desc- click print button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void Print() throws InterruptedException, IOException {
		
		clickWebElement(sheetName, "btn_print;name", "List Button", ScreenName);
		waitForSync(4);
	}



}
