package screens;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CheckInCheckOutHistory_WHS022 extends CustomFunctions {
	
	String sheetName = "CheckInCheckOutHistory_WHS022";
	String screenName = "CheckInCheckOutHistory_WHS022";
	String screenId="WHS022";	

	public CheckInCheckOutHistory_WHS022(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
	
/**
 * Description... Enter Carrier Code and AWB Number
 * @param carrierCode
 * @param awbNumber
 * @throws InterruptedException
 */
public void enterAWB(String carrierCode,String awbNumber) throws InterruptedException
	{
		//Enter carrier code
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(carrierCode), "Carrier Code", screenName);
		
		//Enter awb number
		enterValueInTextbox(sheetName, "inbx_awbNumber;name", data(awbNumber), "Awb Number", screenName);
		
	}
/**
 * Description...	Click List Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void listAwbDetails() throws InterruptedException, IOException
	{
		//List button
		clickWebElement(sheetName, "btn_list;name", "List Button", screenName);
		waitForSync(3);	
	}	
	
	
	/**@author A-7271
	* Description... Verify checkin check Details
	* @param verfCols
	* @param actVerfValues
	* @throws InterruptedException
	 * @throws IOException 
	*/
	public void verifyChkinChkOutDetails(String pmKey,int verfCols[], String actVerfValues[]) throws InterruptedException, IOException {
	      
	      verify_tbl_records_multiple_cols(sheetName, "tbl_awbDetails;xpath", "//td", verfCols, data(pmKey),
	                  actVerfValues);   
	      
	   }
}
		