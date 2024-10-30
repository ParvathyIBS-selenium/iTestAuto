package screens;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AWBHAWBHistory_OPR276 extends CustomFunctions{
	public AWBHAWBHistory_OPR276(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "AWBHAWBHistory_OPR276";
	public String screenName = "AWBHAWBHistory_OPR276";

	/**
	 * Description... verification for AWB Cancelled History
	 * @throws InterruptedException
	 */
	public void verifyAWBCancelledHistory() throws InterruptedException {
		By element = getElement(sheetName, "tbl_AWBHAWBHistory;xpath");
		String actText = driver.findElement(element).getText();
		
		verifyValueOnPage(actText, "CAN", "Cancelled status of AWB verification", sheetName, "Cancelled status of AWB verification");
		
	}
	/**
	 * Description... verification for ADC TC
	 * @throws InterruptedException
	 */
	public void verifyUpdateSource() throws InterruptedException {
		By element = getElement(sheetName, "tbl_AWBHAWBHistory;xpath");
		String actText = driver.findElement(element).getText();
		
		verifyScreenText(sheetName, "AGT", actText,  "Update Source verification ", "Cancelled status of AWB verification");
	}
	
	/**
	   * Description... Verify Details With Same Version
	   * @param verfCols
	   * @param actVerfValues
	   * @param pmKey
	   * @param rowIndex
	   * @throws InterruptedException
	   */
	public void verifyDetailsWithSameVersion(int verfCols[],String actVerfValues[],String pmKey, int rowIndex)
	    throws InterruptedException {
	  
	  String table_row = "("+ xls_Read.getCellValue(sheetName, "tbl_AWBHAWBHistoryDetails;xpath")+"[contains(.,'"+ pmKey +"')])["+ rowIndex +"]";
	      

	   for (int k = 0; k < verfCols.length; k++) {
	    int x = verfCols[k];

	    String td = table_row + "//td" + "[" + x + "]";
	    ele = driver.findElement(By.xpath(td));

	    String actual = ele.getText().toLowerCase().replace(" ", "");
	    String expected = (actVerfValues[k].replace(" ", "").toLowerCase());

	    if (actual.contains(expected)) {
	     System.out.println("found true for " + actVerfValues[k]);

	     onPassUpdate(screenName, expected, actual, "Table verification against " + pmKey + " On ",
	       "Table verification");

	    } else {
	     onFailUpdate(screenName, expected, actual, "Table verification against " + pmKey + " On ",
	       "Table verification");

	    }
	    
	   }
	}
	
	
	/**
	 * Description... verification for Update By
	 * @throws InterruptedException
	 */
	public void verifyUpdateBy() throws InterruptedException {
		By element = getElement(sheetName, "tbl_updatedBY;xpath");
		String actText = driver.findElement(element).getText();
		
		verifyScreenText(sheetName, "EPORTAL", actText,  "Update By verification ", "Cancelled status of AWB verification");
	}
	

}