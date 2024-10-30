package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.openqa.selenium.support.ui.Select;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ScanAWBDocumentintoSatchel_OPR086 extends CustomFunctions {

	public ScanAWBDocumentintoSatchel_OPR086(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	public String sheetName = "ScanAWBDocument_OPR086";
	public String screenName = "Scan AWB Document into Satchel";


/**
 * @author A-10690
 * Description... list satchel
 * @param satchel
 * @throws InterruptedException
 * @throws IOException 
 */
public void listSatchel(String satchel) throws InterruptedException, IOException {
	enterValueInTextbox(sheetName, "inbx_satchel;name", data(satchel), "satchel", screenName);
	clickWebElement(sheetName, "btn_List;id","List Button", screenName);
	waitForSync(2);
}

/**
 * @author A-9844
 * Description... Verify satchel status font color
 * @param awbNo
 */
public void verifySatchelStatusFontColor(String awbNo){
	try{	
		String locator=xls_Read.getCellValue(sheetName, "txt_satchelStatusColor;xpath");
		locator=locator.replace("*", data(awbNo));
		String clr=driver.findElement(By.xpath(locator)).getAttribute("color");  
		System.out.println(clr);
		if(clr.equals("#FF0000"))
			writeExtent("Pass", "Successfully Verified Red Color for Satchel status for "+ data(awbNo) +" on "+ screenName + " Page");
		else
			writeExtent("Fail","Not Verified Ref Color for Satchel status for "+ data(awbNo) +" on "+ screenName + " Page");


	}
	catch (Exception e) {
		writeExtent("Fail","Could not verify satchel status font color on "+ screenName + " Page");
	}

}
/**
 * @author A-9847
 * @desc To verify the warning message and perform the required action(yes/no)
 * @param expText
 * @param action
 */		

public void verifyAndHandleWarning(String expText,String action){

	try{
		switchToFrame("default");
		String actText = getElementText("Generic_Elements", "htmlDiv_confirmMsg;xpath", "Confirmation Message",screenName);
		verifyScreenTextWithExactMatch(sheetName, expText, actText, "Warning message","Warning message");
		if(action.equals("yes"))
			clickWebElement("Generic_Elements", "btn_yes;xpath","Yes Button", screenName);
		else
			clickWebElement("Generic_Elements", "btn_no;xpath","No Button", screenName);
		switchToFrame("contentFrame", "OPR086");
	}
	catch(Exception e) {
		writeExtent("Fail", " Failed to verify the Warning message "+expText+ " on " + screenName);
	}

}
/**
 * @author A-9844
 * Description... click save
 * @throws InterruptedException
 * @throws IOException 
 */
public void clickSave() throws InterruptedException, IOException {
	clickWebElement(sheetName, "btn_save;name","Save Button", screenName);
	waitForSync(4);
}
/**
 * @author A-9844
 * Desc - Verify and handle alert
 * @param expectedMsg
 * @param expectedTextYes
 * @param expectedTextNo
 * @throws InterruptedException
 * @throws IOException
 */
public void verifyAndHandleAlert(String expectedMsg,String expectedTextYes,String expectedTextNo) throws InterruptedException, IOException {

	switchToFrame("default");


	String actualMsg = getElementText("Generic_Elements", "htmlDiv_confirmMsg;xpath", "Confirmation Message",screenName);

	String textYes = getElementText("Generic_Elements", "btn_yes;xpath", "yes button",screenName);
	String textNo = getElementText("Generic_Elements", "btn_no;xpath", "no button",screenName);

	verifyScreenText(sheetName, data(expectedMsg), actualMsg, "Confirmation Msg", screenName);
	verifyScreenText(sheetName, data(expectedTextYes), textYes, " Yes Text", screenName);
	verifyScreenText(sheetName, data(expectedTextNo), textNo, " No Text", screenName);


	clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes Button", screenName);
	waitForSync(4);
	switchToFrame("contentFrame", "OPR086");
	waitForSync(4);

}

/**
	 * @author A-9844
	 * Description... click Edit
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickEdit() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_edit;xpath","Edit Button", screenName);
		waitForSync(4);
	}
/**
 *  @author A-10690
 * Description... verify Flight Details
 * @param carrierCode
 * @param fltNo
 * @param fltDate
 * @throws InterruptedException
 * @throws IOException 
 */
	public void  verifyFlightDetails(String flightNum,String flghtDate) throws InterruptedException, IOException
	{
		String actFlightNum = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_flightNumber;xpath"))).getText();
		String expFlightNum = data(flightNum);
		verifyScreenText(sheetName, expFlightNum, actFlightNum, "Flight details verification", "OPR086");
		String actFlghttDate = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_flightDate;xpath"))).getText();
		String expFlghttDate = data(flghtDate);
		verifyScreenText(sheetName, expFlghttDate, actFlghttDate, "Flight date verification ", "OPR086");
		waitForSync(4);
				
	}
	
	/**
	 * @author A-10690
	 * Description... enter AWB
	 * @param awb
	 * @throws InterruptedException
	 * @throws IOException 
	 * @throws AWTException 
	 */
	public void enterAWB(String AWB) throws InterruptedException, IOException, AWTException {
		String locator = xls_Read.getCellValue(sheetName, "inbx_enterAWB;name");
		enterValueInTextbox(sheetName, "inbx_enterAWB;name", data(AWB), "AWB", screenName);
		waitForSync(5);
		performKeyActions(sheetName,"inbx_enterAWB;name", "TAB","AWB no", screenName);
		 waitForSync(7);
		
	}


	/**
	 * @author A-10690
	 * Description... Click awb and clicked save button
	 * @param AWB
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void saveAWB(String AWB) throws InterruptedException, IOException {
		String locator = xls_Read.getCellValue(sheetName, "table_selectcheckbox;xpath");
		locator=locator.replace("*",data(AWB));
		driver.findElement(By.xpath(locator)).click();
		clickWebElement(sheetName,"btn_save;name", "save button", screenName);
		
				waitForSync(10);
				switchToFrame("default");
				waitForSync(10);     
				try {

				while (driver.findElement(
				By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
				.isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_Yes;xpath",
				"OK Button", screenName);
				Thread.sleep(10000);
				}
				} catch (Exception e) {
				}

				Thread.sleep(12000);
				switchToFrame("contentFrame", "OPR086");
				Thread.sleep(12000);
		
	}
	
	/**
	 * Description... Verify awbDetails Details Value
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 */
  public void verifyAWBDetails(int verfCols[],String actVerfValues[],String pmKey)
			 throws InterruptedException {
	
		
			waitForSync(4);
			verify_tbl_records_multiple_cols_contains(sheetName, "table_AWBDetails;xpath", "//td", verfCols, pmKey, actVerfValues);	
	}
  
}