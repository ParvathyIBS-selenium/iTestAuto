package screens;

import java.util.Set;
import java.util.Random;
import java.awt.AWTException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FoundShipment_OPR037 extends CustomFunctions {
	public FoundShipment_OPR037(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "FoundShipment_OPR037";
	public String screenName = "FoundShipment";
	String screenId = "OPR037";

	/**
	 * @Description... List FS Id
	 * 
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listByFSID(String FSID) throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_FSID;id", data(FSID), "FSID", screenId);
		waitForSync(2);
		clickWebElement(sheetName, "btn_Listbtn;id", " List button ", screenName);
		waitForSync(4);
	}

	/**
	 * @Description... Verify Location Details
	 * 
	 * @param verfCols
	 * @param actVerfValues
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyLocationDetailsWithPmKey(int verfCols[], String actVerfValues[], String pmKey)
			throws InterruptedException, IOException {

		verify_tbl_records_multiple_cols(sheetName, "table_locationDetails;xpath", "//td", verfCols, data(pmKey),
				actVerfValues);

	}

	/**
	 * @Description... Check Location Details checkbox
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickCheckAllLocationDetails() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "chbx_LocationDetails;name", " Location details ", screenName);
		waitForSync(3);
	}

	/**
	 * @Description... Click Modify button in Location details
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickModifyButton() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_modifyLocation;id", " Modify button ", screenName);
		waitForSync(3);
	}

	/**
	 * @Description... Enter pieces and weight in Location Details window
	 * @throws Exception
	 */
	public void enterPiecesAndWeight(String Pieces, String Weight) throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_modifyPieces;id", data(Pieces), "Pieces", screenId);
		enterValueInTextbox(sheetName, "inbx_modifyWeight;id", data(Weight), "Weight", screenId);
		waitForSync(3);
		clickWebElement(sheetName, "btn_OkButton;id", " Ok button ", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR037");
	}
	
	/**
	 * @Description : Click FSNO Generate button
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickGenerateButton() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_generate;id", " Generate button ", screenName);
		waitForSync(3);
	}

	/**
	 * @Description : Returning FSNo generated
	 * @author A-9175
	 * @return
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public String getFSNo() throws InterruptedException, AWTException, IOException {
		waitForSync(3);
		String fsn = getAttributeWebElement(sheetName, "inbx_FSNnumber;name", " FSN Number ", "value", screenName);
		waitForSync(3);
		return fsn;
	}

	/**
	 * @Description : Capture warehouse location details
	 * @author A-9175
	 * @param Pieces
	 * @param Weight
	 * @param Loc
	 * @throws Exception
	 */
	public void captureLocationDetails(String Pieces, String Weight,String su, String Loc) throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_SU;id", data(su), "SU", screenId);
		enterValueInTextbox(sheetName, "inbx_modifyPieces;id", data(Pieces), "Pieces", screenId);
		enterValueInTextbox(sheetName, "inbx_modifyWeight;id", data(Weight), "Weight", screenId);
		enterValueInTextbox(sheetName, "inbx_loc;name", data(Loc), "Location", screenId);
		waitForSync(3);
		clickWebElement(sheetName, "btn_OkButton;id", " Ok button ", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR037");
	}

	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * Desc : verify save details
	 */
	public void verifySaveDetails() throws InterruptedException
	{
		 verifyElementDisplayed(sheetName,"htmlDiv_save;xpath", " Save", screenName, "Save toast message");
	}
	/**
	 * @Description : Capture Pieces and Weight Information
	 * @author A-9175
	 * @param Pieces
	 * @param Weight
	 * @throws Exception
	 */
	public void capturePiecesAndWeight(String Pieces, String Weight) throws Exception {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_pcs;id", data(Pieces), "Pieces", screenId);
		enterValueInTextbox(sheetName, "inbx_wgt;name", data(Weight), "Weight", screenId);

	}

	/**
	 * @Description : Adding Remarks
	 * @author A-9175
	 * @param addrem
	 * @param rem
	 * @throws Exception
	 */
	public void captureRemarks(String addrem, String rem) throws Exception {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_addlMarking;id", data(addrem), "Additional Remarks", screenId);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_remarks;id", data(rem), "Remarks", screenId);
	}

	/**
	 * @Description : Clicking add location button
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickAddLoc() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_addLocation;id", " Add Loc button ", screenName);
		waitForSync(3);
	}
}