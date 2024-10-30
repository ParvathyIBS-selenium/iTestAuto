package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ListFlightSchedules_FLT004 extends CustomFunctions {


	String sheetName="ListFlightSchedules_FLT004";
	String screenName="ListFlightSchedules : FLT004";

	public ListFlightSchedules_FLT004(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}
	/**
	 * Description... List Flight
	 * @param flightNumber
	 * @param startDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void listFlight(String flightNumber,String startDate) throws InterruptedException, AWTException, IOException
	{

		enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(flightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_startDate;id", startDate, "Flight Start Date", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "btn_list;id", "List", screenName);
		Thread.sleep(3000);		
	}
	/**
	 * @author A-8783
	 * Desc- click details button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickDetails() throws InterruptedException, IOException
	   {
		   clickWebElement(sheetName, "btn_details;name", "details button", screenName);
	   }

	/**  
	 * @author A-9847        	
	* Description... Click Specific Check Box in the Table
	* @param locator
	* @throws InterruptedException
	* @throws IOException
	*/
	public void clickSpecificCheckBox(String pmyKey) throws InterruptedException, IOException
	{
	selectTableRecordJS(data(pmyKey),"clk_chckbx;xpath", sheetName, 1);
	waitForSync(2);
	}
	
	/**
	 * Description... Verify Leg Details
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws IOException 
	 */
	public void verifyLegDetails(int verfCols[],String actVerfValues[],String pmKey) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_flightDetails;xpath", "//td", verfCols, pmKey, actVerfValues);
	}
	
	/**
	 * Description... Click Check Box in the Table
	 * @param locator
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickCheckBox(String locator) throws InterruptedException, IOException
   {
	   clickWebElement(sheetName, locator, "selected row", screenName);
   }
/**
 * Description... Burst Flight
 * @throws InterruptedException
 * @throws IOException 
 */
   public void burstFlight() throws InterruptedException, IOException{
	   clickWebElement(sheetName, "btn_burst;xpath" , "Burst" , screenName);
	   Thread.sleep(8000);	
		handleAlert("Accept", screenName);
		
   }

}
	
	