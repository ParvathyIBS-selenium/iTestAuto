package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ListFlightDiscrepancy_OPR047 extends CustomFunctions  {

	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName="ListFlightDiscrepancy_OPR047";
	String ScreenName="List Flight Discrepancy";
	String screenId="OPR047";


	public ListFlightDiscrepancy_OPR047(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction=new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
	
	/**
	 * Description... Verify Discrepancy details
	 * @param verfCols
	 * @author A-9478
	 * @param actVerfValues
	 * @throws IOException 
	 */
	public void verifyDiscrepancydetails(int verfCols[], String actVerfValues[]) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_DiscrepancyTable;xpath", "//td", verfCols, data("AWBNo"),
				actVerfValues);
		waitForSync(3);
	}	
	/**
	 * Description : click Add Damage Discrepancy link
	 * @author A-9844
	 * @throws IOException 
	 */
	public void clickAddLink() throws InterruptedException, AWTException, IOException
	{
		waitForSync(3);
		clickWebElement(sheetName, "btn_addLink;xpath", "Add button", ScreenName);
		waitForSync(2);

	}




/**
	 * @author A-9844
	 * @Description... enter damage discrepancy details
	 * @param awbPrefix
	 * @param awbNumber
	 * @param dmgCode
	 * @param dmgPcs
	 * @throws Exception 
	 */
	public void enterDamageDiscrepancyDetails(String awbPrefix,String awbNumber,String dmgCode,String dmgPcs) throws Exception {
		
         switchToWindow("storeParent");
         switchToWindow("child");
         enterValueInTextbox(sheetName, "inbx_awbPrefix;xpath", data(awbPrefix), "awb prefix", ScreenName);
         enterValueInTextbox(sheetName, "inbx_awbNumber;xpath", data(awbNumber), "awb number", ScreenName);
         enterValueInTextbox(sheetName, "inbx_dmgCode;xpath", data(dmgCode), "damage code", ScreenName);
         enterValueInTextbox(sheetName, "inbx_dmgPcs;xpath", data(dmgPcs), "damage pieces", ScreenName);
         clickWebElement(sheetName, "btn_OK;xpath", "OK button", ScreenName);
         waitForSync(5);
         switchToWindow("getParent");
         switchToDefaultAndContentFrame("OPR047");
	}

	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
* Invoking print button
	 */
	public void clickPrint() throws InterruptedException, AWTException, IOException
	{
		clickWebElement(sheetName, "btn_print;id", "Print button", ScreenName);
		waitTillExpectedChildWindowLoad(2);	

	}




/**
	 * Description : click Save Button
	 * @author A-9844
	 * @throws IOException 
	 */
	public void clickSave() throws InterruptedException, AWTException, IOException
	{
		waitForSync(3);
		clickWebElement(sheetName, "btn_save;xpath", "Save button", ScreenName);
		waitForSync(2);

	}





/**
	 * Description : verify damage discrepancy table details
	 * @author A-9844
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmkey
	 * @throws IOException 
	 */
	public void verifyDamageDiscrepancyDetails(int verfCols[], String actVerfValues[],String pmkey) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_AWB_dmgDiscrpncy;xpath", "//td", verfCols, data(pmkey),
				actVerfValues);
		waitForSync(3);
	}





/**
	 * Description... Click AWB Number Check Box
	 * @param awbNo
	 * @throws InterruptedException
	 */
	public void selectAWBRow(String awbNo) throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		try {
			String locator = xls_Read.getCellValue(sheetName, "chk_awbNo;xpath");
			locator = locator.replace("awb", data(awbNo));
			driver.findElement(By.xpath(locator)).click();
			waitForSync(3);
			writeExtent("Pass", "Clicked on AWB checkbox on " + ScreenName);
		} catch (Exception e) {
			writeExtent("Fail", "Could not Click on AWB checkbox on " + ScreenName);
		}

	}




/**
	 * Description : click View/Capture CDS Button
	 * @author A-9844
	 * @throws IOException 
	 */
	public void clickViewOrCaptureCDS() throws InterruptedException, AWTException, IOException
	{
		
		clickWebElement(sheetName, "btn_ViewCaptureCDS;xpath", "View/Capture CDS Button", ScreenName);
		waitForSync(5);

	}





/**
	 * @author A-9844
	 * @Description... enter cargo damage survey details
	 * @param dmgReasonCode
	 * @param dmgPackageCode
	 * @param dmgCodePackaging
	 * @param pointOfNotice
	 * @param msg
	 * @throws Exception 
	 */
	public void enterCargoDamageSurveyDetails(String dmgReasonCode,String dmgPackageCode,String dmgCodePackaging,String pointOfNotice,String msg) throws Exception {

		try{


			switchToWindow("storeParent");
			switchToWindow("child");

			//select damage reason code
			selectValueInDropdown(sheetName, "drp_damageReasonCode;xpath", data(dmgReasonCode), "damage reason code", "VisibleText");
			waitForSync(1);

			//select damage package code
			selectValueInDropdown(sheetName, "drp_packageCode;xpath", data(dmgPackageCode), "damage package code", "VisibleText");
			waitForSync(1);

			//enter damage code packaging
			enterValueInTextbox(sheetName, "inbx_dmgCodePackaging;xpath", data(dmgCodePackaging), "damage code packaging", ScreenName);
			waitForSync(1);

			//select point of notice
			selectValueInDropdown(sheetName, "drp_pointOfNotice;xpath", data(pointOfNotice), "point of notice", "VisibleText");
			waitForSync(1);	

			clickWebElement(sheetName, "btn_surveySave;xpath", "Save button", ScreenName);
			waitForSync(1);
			switchToFrame("default");

			//Accept the confirmation message popup
			String locator = xls_Read.getCellValue("Generic_Elements", "htmlDiv_msgStatus;xpath");
			String actText=driver.findElement(By.xpath(locator)).getText();
			if(actText.contains(data(msg)))
			{

				clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes Button", ScreenName);
				waitForSync(2);
				writeExtent("Pass","Accepted the confirmation message popup with text "+actText+" on "+ScreenName);

			}


			waitForSync(5);
			switchToWindow("getParent");
			switchToDefaultAndContentFrame("OPR047");
		}
		catch (Exception e) {
			writeExtent("Fail","Could not enter the details and accepted the confirmation message popup on "+ScreenName);
		}
	}

	/**
	 * Description : Enter Carrier code, flight no,flight date and click on list
	 * @author A-10690
	 * @throws IOException 
	 */
	public void verifyDiscrepancydetails(int verfCols[], String actVerfValues[],String pmkey) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_AWB_discrepency;xpath", "//td", verfCols, data(pmkey),
				actVerfValues);
		waitForSync(3);
	}

/**
	 * @author A-9847
	 * @Desc To verify Discrepancy details, given the primary key
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmkey
	 * @throws IOException
	 */
	public void verifyDiscrepancydetailsWithPMkey(int verfCols[], String actVerfValues[],String pmkey) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_DiscrepancyTable;xpath", "//td", verfCols, data(pmkey),
				actVerfValues);
		waitForSync(3);
	}	
	
	/**
	 * Description : Enter Carrier code, flight no,flight date and click on list
	 * @author A-9478
	 * @throws IOException 
	 */
	public void listByFlight(String carrierCode,String flightNo,String flightDate) throws InterruptedException, AWTException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_carrierCode;id", data(carrierCode), "Carrier code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_flightNo;id", data(flightNo), "Flight No", ScreenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id", data(flightDate), "Flight Date", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;id", "List button", ScreenName);
		waitForSync(15);

		
		}
	
}