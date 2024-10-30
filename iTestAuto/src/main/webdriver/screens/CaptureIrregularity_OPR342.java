package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.WebFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CaptureIrregularity_OPR342 extends CustomFunctions {

	public CaptureIrregularity_OPR342(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "CaptureIrregularity_OPR342";
	public String screenName = "CaptureIrregularity_OPR342";

/**
 * Description... List AWB
 * @author A-9175
 * @param awbNo
 * @param ShipmentPrefix
 * @throws InterruptedException
 * @throws IOException 
 */
	public void listAWB(String awbNo,String ShipmentPrefix) throws InterruptedException, IOException {
		
		awbNo = getPropertyValue(proppath, "AWBNo");

		System.out.println("AWBnumber is ---" + awbNo);
		waitForSync(2);
		enterValueInTextbox(sheetName, "awbPrefix;id", data(ShipmentPrefix), "Shipment Prefix", screenName);
		enterValueInTextbox(sheetName, "awbNum;id", awbNo, "AWB No", screenName);
		clickWebElement(sheetName, "btnList;id", "List Button", screenName);
		waitForSync(4);
		
	}
	/**
	 * @author A-8783
	 * Desc : Clicking add/save irregularity Details and handle alert
	 * @throws InterruptedException
	 * @throws IOException 
	 */
		public void clickSaveHandleAlert() throws InterruptedException, IOException {
			clickWebElement(sheetName, "btnSave;id", "Link Irregularity", screenName);
			waitForSync(2);
			switchToFrame("default");
			
			String expectedMsg="Do you wish to capture irregularity against all the AWBs associated to the token?";

			String actualMsg=getElementText("Generic_Elements", "htmlDiv_confirmMsg;xpath","Confirmation Message", screenName);

			verifyScreenText(sheetName, expectedMsg, actualMsg, "Confirmation Msg", screenName);

			clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
			waitForSync(2);
			
		}
		/**
		 * @author A-8783
		 * Desc - Handle error
		 * @throws InterruptedException
		 * @throws IOException
		 */
public void handleError() throws InterruptedException, IOException {
	
	try {
		switchToFrame("default");
		clickWebElement(sheetName, "btn_errorIcon;xpath", "Error Message", screenName);
		switchToFrame("contentFrame", "OPR342");
	}
	catch(Exception e)
	{
		writeExtent("Info","No error getting on listing token in OPR342"); 
	}


}

	/**
	 * @author A-9175
	 * Desc : Selecting Operation
	 * @param Operation
	 * @throws Exception
	 */
	
	public void selectOperation(String Operation) throws Exception {
	    selectValueInDropdown(sheetName,"dropDownTransactions;id",Operation,"Operation","VisibleText");
	    }
	/**
	 * @author A-8783
	 * Desc- List with token number
	 * @param tokenID
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void listToken(String tokenID) throws InterruptedException, IOException {
		enterValueInTextbox(sheetName, "inbx_tokenNo;xpath", data(tokenID), "Token number", screenName);
		clickWebElement(sheetName, "btnList;id", "List Button", screenName);
		waitForSync(2);
	}
	/**
	 * @author A-8783
	 * Desc- verify that the token number fiels is present
	 * @throws InterruptedException
	 */
	public void verifyTokenfield() throws InterruptedException {
		verifyElementDisplayed(sheetName, "inbx_tokenNo;xpath", "Token number field", screenName, "Token number field");
	}

	/**
	 * @author A-9175
	 * Desc : Selecting Irregularity Code
	 * @param irregularityCode
	 * @throws InterruptedException
	 */
	
		public void clickIrregularitySelect(String irregularityCode) throws InterruptedException {
			enterValueInTextbox(sheetName, "inbxIrregularityCode;id", irregularityCode, "irregularityCode", screenName);
			waitForSync(4);
		}
		
		/**
		 * @author A-9175
		 * Desc : Entering Remarks
		 * @param remarks
		 * @throws InterruptedException
		 */
		
		public void enterRemarks(String remarks) throws InterruptedException {
			
			enterValueInTextbox(sheetName, "inbxIrregularityRemarks;id", remarks, "remarks", screenName);
			waitForSync(4);
			
		}
	
	/**
	 * @author A-9175
	 * Desc : Clicking add/save irregularity Details
	 * @throws InterruptedException
	 * @throws IOException 
	 */
		public void clickSave() throws InterruptedException, IOException {
			clickWebElement(sheetName, "btnSave;id", "Link Irregularity", screenName);
			waitForSync(4);
			
		}
		/**
		 * Desc : Verifying Irregularity Details
		 * @author A-9175
		 * @throws IOException 
		 * @throws InterruptedException
		 */
		
			public void verifyIrregularityDetails(int verfCols[],String actVerfValues[],String pmKey) throws IOException
			{
				waitForSync(4);
				verify_tbl_records_multiple_cols(sheetName, "table_Irregularitydetails;xpath", "//td", verfCols, pmKey, actVerfValues);
			}
		


}