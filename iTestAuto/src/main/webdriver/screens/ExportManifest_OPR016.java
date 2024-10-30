package screens;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ExportManifest_OPR016 extends CustomFunctions {
	String sheetName = "ExportManifest_OPR016";
	public CustomFunctions customFuction;
	String screenID = "OPR016";
	public String screenName = "ExportManifest";

	public ExportManifest_OPR016(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelreadwrite, xls_Read);

	}

	/**
	 * Description... Click AWB Button
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	// Click AWB tab after listing flight
	public void clickAWBButton() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "button_AWB;xpath", "AWB Button",
				"ExportManifest");
	}

	/**
	 * Description... Select AWB No
	 * 
	 * @param pmyKey
	 * @param sheetName
	 * @param locatorTableRow
	 * @param locatorEle
	 * @param loopCount
	 * @throws InterruptedException
	 */
	// Select AWB in table. Usage : Here pmyKey is AWBNo in table to be selected
	public void selectAWBNo(String pmyKey, String sheetName,
			String locatorTableRow, String locatorEle, int loopCount)
			throws InterruptedException {
		customFuction.selectTableRecord(pmyKey, sheetName, locatorTableRow,
				locatorEle, loopCount);
		waitForSync(2);

	}

	/**
	 * Description... Close ATD capture Window
	 * 
	 * @throws Exception
	 */
	public void closeATDcaptureWindow() throws Exception {
		waitForSync(2);

		clickWebElement(sheetName, "btn_closeATD;name", "Close ATD button",
				screenName);
		waitForSync(2);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR016");

	}

	/**
	 * Description... Unassign ULD
	 * 
	 * @param pmyKey
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void unassignULD(String pmyKey) throws InterruptedException, IOException {
		selectULDNoFromBuildUp(pmyKey);
		waitForSync(10);
		clickWebElement(sheetName, "btn_Unassign;name", "Unassign Button",
				"Export Manifest");
		waitForSync(5);
		save();
	}

	/**
	 * Description... Assign ULD To OAL Flight
	 * 
	 * @author A-7271
	 * @param uldno
	 * @param POU
	 * @param contour
	 * @param Pieces
	 * @param Weight
	 * @param carrierCode
	 * @param AWBNo
	 * @param location
	 * @throws Exception
	 */
	// Assign AWB to ULD
	public void assignULDToOALFlight(String uldno, String POU, String contour,
			String Pieces, String Weight, String carrierCode, String AWBNo,
			String location) throws Exception {

		switchToWindow("storeParent");

		clickWebElement(sheetName, "btn_AssignULD;name", "Assign ULD Button",
				"ExportManifest");
		waitForSync(6);
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_uldNo;id", data(uldno),
				"ULD Number", "Export Manifest");

		selectValueInDropdown(sheetName, "lst_POU;id", data(POU), "Select POU",
				"Value");
		if (data(uldno).contains("KFW"))
			clickWebElement(sheetName, "chk_barrowCheck;xpath",
					"Barrow Check Box", "ExportManifest");
		waitForSync(3);
		clickWebElement(sheetName, "btn_btnListULD;name", "List ULD Button",
				"ExportManifest");
		waitForSync(5);

		if (!location.equals("")) {
			// location
			enterValueInTextbox(sheetName, "inbx_locationCode;xpath",
					data(location), "location", "Export Manifest");
		}

		selectValueInDropdown(sheetName, "lst_contour;name", data(contour),
				"Select Contour", "Value");
		// code uncommented to enter carrier code other than 020

		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(carrierCode),
				"Carrier Code", "Export Manifest");

		enterValueInTextbox(sheetName, "inbx_awbNo;id", data(AWBNo), "AWB No",
				"Export Manifest");
		clickWebElement(sheetName, "btn_btnListAWB;name", "List AWB Button",
				"ExportManifest");
		waitForSync(3);

		enterValueInTextbox(sheetName, "inbx_shipmentPieces;id", data(Pieces),
				"Pieces", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_AssignULD_wt;name", data(Weight),
				"Weight", "Export Manifest");

		keyPress("TAB");
		waitForSync(2);
		keyRelease("TAB");
		clickWebElement(sheetName, "btn_ULDok;id", "OK Button",
				"ExportManifest");
		waitForSync(3);

		try {
			/**
			 * clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button",
			 * "ExportManifest");
			 **/

			driver.findElement(
					By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
					.click();
		}

		catch (Exception e) {

		}
		waitForSync(3);
		switchToWindow("getParent");
		waitForSync(3);
	}

	/**
	 * Description... ReAssign Full ULD
	 * 
	 * @author A-7271
	 * @description : reAssignFullULD
	 * @param newFlightNumber
	 * @param CarrierCode
	 * @param POU
	 * @param newFlightDate
	 * @throws Exception
	 */
	public void reAssignFullULD(String newFlightNumber, String CarrierCode,
			String POU, String newFlightDate) throws Exception {

		switchToWindow("storeParent");
		clickWebElement("ExportManifest_OPR016", "btn_btnReAssign;xpath",
				"Reassign Button", "Export Manifest");
		waitForSync(5);
		switchToWindow("multipleWindows");

		clickWebElement("ExportManifest_OPR016", "chk_fullULDTransfer;name",
				"Check Full ULD Transfer", "Export Manifest");

		keyPress("SCROLLDOWNMOUSE");
		enterValueInTextbox("ExportManifest_OPR016",
				"inbx_toCarrierCode;xpath", CarrierCode, "Carrier Code",
				"Export Manifest");
		enterValueInTextbox("ExportManifest_OPR016",
				"inbx_toFlightNumber;xpath", newFlightNumber, "Flight Number",
				"Export Manifest");
		enterValueInTextbox("ExportManifest_OPR016", "inbx_toFlightDate;xpath",
				newFlightDate, "Flight Date", "Export Manifest");

		enterValueInTextbox("ExportManifest_OPR016", "inbx_pou;xpath", POU,
				"POU", "Export Manifest");
		save();
		switchToWindow("getParent");
	}

	/**
	 * Description... Enters SCC, POU, enters bulk and uld comment
	 * 
	 * @param optionSCC
	 * @param optionPOU
	 * @param bulkComment
	 * @param uldComment
	 * @throws Exception
	 */
	public void preAdvice(String optionSCC, String optionPOU,
			String bulkComment, String uldComment) throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_preAdvice;name", "PreAdvice Button",
				"PreAdvice Popup Export Manifest");
		switchToWindow("child");
		selectValueInDropdown(sheetName, "lst_preAdvice_scc;name", optionSCC,
				"Preadvice SCC", "Value");
		selectValueInDropdown(sheetName, "lst_pointOfUnlading_scc;name",
				optionPOU, "Preadvice SCC", "Value");
		clickWebElement("Generic_Elements", "btn_list;name", "List Button",
				"PreAdvice Popup Export Manifest");

		clickWebElement(sheetName, "rad_pointOfUnlading_msgTypeFFM;xpath",
				"Message Type Radio Button", "PreAdvice Popup Export Manifest");

		enterValueInTextbox(sheetName, "txtar_pointOfUnlading_remarks1;xpath",
				bulkComment, "Bulk Comment", "PreAdvice Popup Export Manifest");
		enterValueInTextbox(sheetName, "txtar_pointOfUnlading_remarks2;xpath",
				uldComment, "ULD Comment", "PreAdvice Popup Export Manifest");
		clickWebElement(sheetName, "btn_send;name", "Send Button",
				"PreAdvice Popup Export Manifest");

		customFuction.handleAlert("Accept", "PreAdvice Popup Export Manifest");

		clickWebElementByWebDriver("Generic_Elements", "btn_close;name",
				"Close Button", "PreAdvice Popup Export Manifest");

		switchToWindow("getParent");
	}

	/**
	 * Description... Clicks on ULD Expand Link
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickBulkCheckBox() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chk_uldDetails;xpath", "Bulk Check box ",
				screenName);
	}

	/**
	 * Description... Assign To ULD
	 * 
	 * @param ULDno
	 * @throws Exception
	 */
	public void assignToULD(String ULDno) throws Exception {

		selectValueInDropdown(sheetName, "lst_BULK;name", data(ULDno),
				"Select ULD", "Value");
		waitForSync(2);
		clickWebElement(sheetName, "btn_AssigntoULD;name",
				"Assign to ULD Button", "ExportManifest");

	}

	/**
	 * Description... Assign Empty ULD
	 * 
	 * @param uldno
	 * @param POU
	 * @param contour
	 * @throws Exception
	 */
	public void assignEmptyULD(String uldno, String POU, String contour)
			throws Exception {

		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_AssignULD;name", "Assign ULD Button",
				"ExportManifest");
		waitForSync(5);
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_uldNo;id", uldno, "ULD Number",
				"Export Manifest");
		selectValueInDropdown(sheetName, "lst_POU;id", POU, "Select POU",
				"Value");

		clickWebElement(sheetName, "btn_btnListULD;name", "List ULD Button",
				"ExportManifest");
		waitForSync(5);
		selectValueInDropdown(sheetName, "lst_contour;name", data(contour),
				"Select Contour", "Value");
		clickWebElement(sheetName, "btn_ULDok;id", "OK Button",
				"ExportManifest");

		switchToWindow("getParent");
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR016");
		waitForSync(3);

	}

	/**
	 * Description... Clicks the SplitsAssign Button and assigns the required
	 * pieces to any ULD
	 * 
	 * @param Bulk
	 * @param SplitPieces
	 * @param SplitWeight
	 * @throws Exception
	 */
	public void splitAssign(String Bulk, String SplitPieces, String SplitWeight)
			throws Exception {

		selectValueInDropdown(sheetName, "lst_BULK;name", Bulk, "Select ULD",
				"Value");
		waitForSync(2);
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitAssign;name",
				"Split Assign Button", "Export Manifest");
		waitForSync(2);
		switchToWindow("child");
		waitForSync(2);
		keyPress("SCROLLDOWNMOUSE");
		enterValueInTextbox(sheetName, "inbx_splitPieces;name",
				data(SplitPieces), "Split Pieces", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_splitWeight;name",
				data(SplitWeight), "Split Weight", "Export Manifest");
		map.put("SplitVol",
				getAttributeWebElement(sheetName, "txt_splitAssign_vol;xpath",
						"value", "vol", "Export Manifest"));
		clickWebElement("Generic_Elements", "btn_OK;xpath", "Ok Button",
				"Export Manifest");
		switchToWindow("getParent");
	}

	/**
	 * Description... Assign To Flight
	 * 
	 * @throws Exception
	 */
	public void assignToFlight() throws Exception {

		clickWebElement(sheetName, "btn_assignToFlight;xpath",
				"Assign to Flight Button", "ExportManifest");
		waitForSync(5);
	}

	/**
	 * Description... Assign Location To ULD
	 * 
	 * @param location
	 * @throws Exception
	 */
	public void assignLocationToULD(String location) throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_AssignULD;name", "Assign ULD Button",
				"ExportManifest");
		waitForSync(5);
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_locationCode;xpath",
				data(location), "Location Code", "Export Manifest");
		clickWebElement(sheetName, "btn_ULDok;id", "OK Button",
				"ExportManifest");
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR016");
		waitForSync(3);

	}

	/**
	 * Description... Assign ULD
	 * 
	 * @param uldno
	 * @param POU
	 * @param contour
	 * @param Pieces
	 * @param Weight
	 * @param carrierCode
	 * @param AWBNo
	 * @param location
	 * @throws Exception
	 */
	// Assign AWB to ULD
	public void assignULD(String uldno, String POU, String contour,
			String Pieces, String Weight, String carrierCode, String AWBNo,
			String location) throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_AssignULD;name", "Assign ULD Button",
				"ExportManifest");
		waitForSync(6);
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_uldNo;id", data(uldno),
				"ULD Number", "Export Manifest");
		selectValueInDropdown(sheetName, "lst_POU;id", data(POU), "Select POU",
				"Value");
		if (data(uldno).contains("KFW"))
			clickWebElement(sheetName, "chk_barrowCheck;xpath",
					"Barrow Check Box", "ExportManifest");
		waitForSync(3);
		clickWebElement(sheetName, "btn_btnListULD;name", "List ULD Button",
				"ExportManifest");
		waitForSync(5);

		if (!location.equals("")) {
			// location
			enterValueInTextbox(sheetName, "inbx_locationCode;xpath",
					data(location), "location", "Export Manifest");
		}

		selectValueInDropdown(sheetName, "lst_contour;name", data(contour),
				"Select Contour", "Value");
		// code uncommented to enter carrier code other than 020

		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(carrierCode),
				"Carrier Code", "Export Manifest");

		enterValueInTextbox(sheetName, "inbx_awbNo;id", data(AWBNo), "AWB No",
				"Export Manifest");
		clickWebElement(sheetName, "btn_btnListAWB;name", "List AWB Button",
				"ExportManifest");
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_shipmentPieces;id", data(Pieces),
				"Pieces", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_AssignULD_wt;name", data(Weight),
				"Weight", "Export Manifest");

		keyPress("TAB");
		waitForSync(2);
		keyRelease("TAB");
		clickWebElement(sheetName, "btn_ULDok;id", "OK Button",
				"ExportManifest");
		waitForSync(3);
		switchToWindow("getParent");
		waitForSync(3);
	}

	/**
	 * Description... Check All Ulds
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void checkAllUlds() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "chk_allUlds;xpath", "All ULD checkbox",
				"ExportManifest");
	}

	// Assign AWB to ULD
	/**
	 * Description... Assign ULD
	 * 
	 * @param uldno
	 * @param POU
	 * @param contour
	 * @param Pieces
	 * @param Weight
	 * @param AWBNo
	 * @throws Exception
	 */
	public void assignULD(String uldno, String POU, String contour,
			String Pieces, String Weight, String AWBNo) throws Exception {

		switchToWindow("storeParent");
		if (uldno.contains("KFW"))
			clickWebElement(sheetName, "chk_barrowCheck;xpath",
					"Barrow Check Box", "ExportManifest");

		clickWebElement(sheetName, "btn_AssignULD;name", "Assign ULD Button",
				"ExportManifest");
		waitForSync(5);
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_uldNo;id", uldno, "ULD Number",
				"Export Manifest");
		selectValueInDropdown(sheetName, "lst_POU;id", POU, "Select POU",
				"Value");

		clickWebElement(sheetName, "btn_btnListULD;name", "List ULD Button",
				"ExportManifest");
		waitForSync(5);
		selectValueInDropdown(sheetName, "lst_contour;name", data(contour),
				"Select Contour", "Value");
		// code uncommented to enter carrier code other than 020

		enterValueInTextbox(sheetName, "inbx_awbPrefix;id",
				data("carrierCode"), "Carrier Code", "Export Manifest");

		enterValueInTextbox(sheetName, "inbx_awbNo;id", AWBNo, "AWB No",
				"Export Manifest");
		clickWebElement(sheetName, "btn_btnListAWB;name", "List AWB Button",
				"ExportManifest");
		waitForSync(3);
		// to handle error date of journey is different from flight daate
		handleAlert("Accept", "Export Manifest");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_shipmentPieces;id", data(Pieces),
				"Pieces", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_AssignULD_wt;name", data(Weight),
				"Weight", "Export Manifest");
		map.put("Vol",
				getAttributeWebElement(sheetName, "txt_vol;xpath", "value",
						"vol", "Export Manifest"));

		keyPress("TAB");
		waitForSync(2);
		keyRelease("TAB");
		clickWebElement(sheetName, "btn_ULDok;id", "OK Button",
				"ExportManifest");

		switchToWindow("getParent");
	}

	// Assign AWB to Bulk, here ULD value can be 'BULK / MUC' based on the
	// routing
	/**
	 * Description... Assign To Bulk
	 * 
	 * @param Bulk
	 * @throws Exception
	 */
	public void assignToBulk(String Bulk) throws Exception {

		selectValueInDropdown(sheetName, "lst_BULK;name", data(Bulk),
				"Select ULD", "Value");
		waitForSync(2);
		clickWebElement(sheetName, "btn_AssigntoULD;name",
				"Assign to ULD Button", "ExportManifest");

	}

	/**
	 * Description... Click Save Button
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	// Save Option
	public void save() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_Save;name", "Save Button",
				"ExportManifest");
	}

	/**
	 * Description... Click Print Manifest
	 * 
	 * @throws Exception
	 */
	// Print Manifest
	public void printManifest() throws Exception {

		switchToWindow("storeParent");
		waitForSync(10);
		clickWebElement(sheetName, "btn_PrintMft;name",
				"Print Manifest Button", "ExportManifest");
		waitForSync(10);
		switchToWindow("child");

		// clickWebElement(sheetName, "rad_Mawb;name", "MAWB radio",
		// "ExportManifest");
		clickWebElementByWebDriver(sheetName, "btn_Close;name",
				"Close Print Manifest Button", "ExportManifest");
		// clickWebElement(sheetName, "btn_Close;name",
		// "Close Print Manifest Button", "ExportManifest");
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR016");

	}

	/**
	 * Description... Finalize Flight
	 * 
	 * @param flightTime
	 * @param prevDate
	 * @throws Exception
	 */
	// Finalize Flight
	public void finalizeFlight(String flightTime, String prevDate)
			throws Exception {

		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_FinalizeFlight;name",
				"Finalize Flight Button", "ExportManifest");

		waitForSync(2);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_Yes;xpath", "Yes Button",
				"Export Manifest");
		waitForSync(30);
		switchToWindow("child");

		enterValueInTextbox(sheetName, "inbx_FlightTime;name", flightTime,
				"Flight Time", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_Date;xpath", prevDate,
				"Flight Date", "Export Manifest");

		waitForSync(2);
		keyPress("TAB");
		keyRelease("TAB");
		clickWebElement(sheetName, "btn_SaveFinalizeFlt;name",
				"Save Finalize Flight Button", "Export Manifest");
		waitForSync(2);
		switchToWindow("getParent");
		switchToFrame("default");
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button",
				"Export Manifest");
		switchToFrame("contentFrame", "OPR016");

		// Verify Flight status got finalized
		ele = findDynamicXpathElement("txt_Finalized;xpath", sheetName,
				"Text Finalized", screenName);
		String actText = ele.getText();
		customFuction.verifyScreenText(sheetName, "Finalized", actText,
				"Flight Finalization", "Finalize Flight");
		waitForSync(1);

	}

	/**
	 * Description... Finalize Flight
	 * 
	 * @param flightTime
	 * @throws Exception
	 */
	// Finalize Flight
	public void finalizeFlight(String flightTime) throws Exception {

		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_FinalizeFlight;name",
				"Finalize Flight Button", "ExportManifest");

		waitForSync(2);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_Yes;xpath", "Yes Button",
				"Export Manifest");
		waitForSync(2);
		switchToWindow("child");

		enterValueInTextbox(sheetName, "inbx_FlightTime;name", flightTime,
				"Flight Time", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_Date;xpath", "-1", "Flight Date",
				"Export Manifest");

		waitForSync(2);
		waitForSync(2);
		keyPress("TAB");
		keyRelease("TAB");
		clickWebElement(sheetName, "btn_SaveFinalizeFlt;name",
				"Save Finalize Flight Button", "Export Manifest");
		waitForSync(2);
		switchToWindow("getParent");
		waitForSync(2);
		switchToFrame("default");
		waitForSync(2);
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button",
				"Export Manifest");
		waitForSync(2);
		switchToFrame("contentFrame", "OPR016");
		waitForSync(2);

		// Verify Flight status got finalized
		ele = findDynamicXpathElement("txt_Finalized;xpath", sheetName,
				"Text Finalized", screenName);
		String actText = ele.getText();
		customFuction.verifyScreenText(sheetName, "Finalized", actText,
				"Flight Finalization", "Finalize Flight");
		waitForSync(1);

	}

	/**
	 * Description... Assign To Bulk Any Destination
	 * 
	 * @param Bulk
	 * @throws Exception
	 */
	public void assignToBulkAnyDest(String Bulk) throws Exception {

		selectValueInDropdown(sheetName, "lst_BULK;name", Bulk, "Select ULD",
				"Value");
		waitForSync(2);
		clickWebElement(sheetName, "btn_AssigntoULD;name",
				"Assign to ULD Button", "ExportManifest");

	}

	/**
	 * Description... Select ULD No From LPRL
	 * 
	 * @param pmyKey
	 * @throws InterruptedException
	 */
	public void selectULDNoFromLPRL(String pmyKey) throws InterruptedException {

		customFuction.selectTableRecord(pmyKey, sheetName, "table_uldNo;xpath",
				"chk_uldNo;xpath", 5);
		waitForSync(2);

	}

	/**
	 * Description... Select ULD No From BuildUp
	 * 
	 * @param pmyKey
	 * @throws InterruptedException
	 */
	public void selectULDNoFromBuildUp(String pmyKey)
			throws InterruptedException {

		customFuction.selectTableRecord(pmyKey, sheetName,
				"table_manifestDetails;xpath", "chk_uldDetails;xpath", 5);
		waitForSync(2);

	}

	/**
	 * Description... Lying List
	 * 
	 * @param fromDate
	 * @param carrierCode
	 * @param flightNumber
	 * @throws Exception
	 */
	public void lyingList(String fromDate, String carrierCode,
			String flightNumber) throws Exception {
		screenName = "Lying List";
		switchToWindow("storeParent");
		clickButtonSwitchWindow(sheetName, "btn_lyingList;xpath",
				"Lying List Button", "Export Manifest");
		enterToFromDateListFlight(fromDate, fromDate, screenName);

		enterValueInTextbox("Generic_Elements", "inbx_shipmentPrefix;xpath",
				data("ShipmentPrefix"), "Shipment Prefix", screenName);
		enterValueInTextbox("Generic_Elements", "inbx_AWBnumber;xpath",
				data("AWBNo2"), "AWB Number", screenName);

		listFlight("OPR016", carrierCode, flightNumber, fromDate,
				"Generic_Elements");
		checkIfUnchecked(sheetName, "chk_shipmentCheckRow;xpath",
				"Shipment Row Check Box", screenName);
		clickWebElement("Generic_Elements", "btn_childWinOk;xpath",
				"OK Button", screenName);
		handleAlert("Accept", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR016");

	}

	/**
	 * Description... Assign New ULD
	 * 
	 * @param uldno
	 * @param POU
	 * @param contour
	 * @param Pieces
	 * @param Weight
	 * @param AWBNo
	 * @throws Exception
	 */
	public void assignNewULD(String uldno, String POU, String contour,
			String Pieces, String Weight, String AWBNo) throws Exception {

		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_AssignULD;name", "Assign ULD Button",
				"ExportManifest");
		waitForSync(5);
		switchToWindow("child");

		if (uldno.contains("KFW"))
			clickWebElement(sheetName, "chk_barrowCheck;xpath",
					"Barrow Check Box", "ExportManifest");

		enterValueInTextbox(sheetName, "inbx_uldNo;id", uldno, "ULD Number",
				"Export Manifest");
		selectValueInDropdown(sheetName, "lst_POU;id", POU, "Select POU",
				"Value");

		clickWebElement(sheetName, "btn_btnListULD;name", "List ULD Button",
				"ExportManifest");
		waitForSync(5);
		selectValueInDropdown(sheetName, "lst_contour;name", data(contour),
				"Select Contour", "Value");
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id",
				data("carrierCode"), "Carrier Code", "Export Manifest");

		enterValueInTextbox(sheetName, "inbx_awbNo;id", AWBNo, "AWB No",
				"Export Manifest");
		clickWebElement(sheetName, "btn_btnListAWB;name", "List AWB Button",
				"ExportManifest");
		waitForSync(3);
		// to handle error date of journey is different from flight daate
		handleAlert("Accept", "Export Manifest");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_shipmentPieces;id", data(Pieces),
				"Pieces", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_AssignULD_wt;name", data(Weight),
				"Weight", "Export Manifest");
		map.put("Vol",
				getAttributeWebElement(sheetName, "txt_vol;xpath", "value",
						"vol", "Export Manifest"));

		waitForSync(2);

		clickWebElement(sheetName, "btn_ULDok;id", "OK Button",
				"ExportManifest");
		handleAlert("Accept", "Export Manifest");
		switchToWindow("storeFirstChild");
		switchToWindow("secondChild");
		enterValueInTextbox(sheetName, "inbx_transactionPieces;xpath",
				data(Pieces), "Pieces", "Export Manifest");
		clickWebElement("Generic_Elements", "btn_OK;xpath", "OK Button",
				"Export Manifest");

		switchToWindow("getParent");
	}

	/**
	 * Description... ReAssign ULD
	 * 
	 * @param unassignedULDNo
	 * @param newFlightNumber
	 * @param CarrierCode
	 * @param POU
	 * @param newFlightDate
	 * @throws Exception
	 */
	public void reAssignULD(String unassignedULDNo, String newFlightNumber,
			String CarrierCode, String POU, String newFlightDate)
			throws Exception {
		clickWebElement("ExportManifest_OPR016", "btn_btnReAssign;xpath",
				"Reassign Button", "Export Manifest");
		waitForSync(5);
		switchToWindow("child");
		checkIfUnchecked("ExportManifest_OPR016", "inbx_selectedAWBs;xpath",
				"Selected AWB", "Export Manifest");
		keyPress("SCROLLDOWNMOUSE");
		enterValueInTextbox("ExportManifest_OPR016",
				"inbx_toCarrierCode;xpath", CarrierCode, "Carrier Code",
				"Export Manifest");
		enterValueInTextbox("ExportManifest_OPR016",
				"inbx_toFlightNumber;xpath", newFlightNumber, "Flight Number",
				"Export Manifest");
		enterValueInTextbox("ExportManifest_OPR016", "inbx_toFlightDate;xpath",
				newFlightDate, "Flight Date", "Export Manifest");

		enterValueInTextbox("ExportManifest_OPR016", "inbx_toULD;xpath",
				unassignedULDNo, "New ULD nO", "Export Manifest");
		enterValueInTextbox("ExportManifest_OPR016", "inbx_pou;xpath", POU,
				"POU", "Export Manifest");
		save();
		switchToWindow("getParent");
	}

	/**
	 * Description... Offload ULD
	 * 
	 * @throws Exception
	 */
	public void offloadULD() throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_btnOffload;xpath", "Offload Button",
				screenName);
		switchToWindow("child");
		selectValueInDropdown(sheetName, "lst_oflReason;xpath", "1",
				"Offload Reason", "Index");
		clickWebElement(sheetName, "btn_Offload;xpath", "Offload Button",
				"Offload Shipment");
		switchToWindow("getParent");
	}

	/**
	 * Description... Lying List ULD
	 * 
	 * @param uldNo
	 * @throws Exception
	 */
	public void lyingListULD(String uldNo) throws Exception {
		screenName = "Lying List";
		switchToWindow("storeParent");
		clickButtonSwitchWindow(sheetName, "btn_lyingList;xpath",
				"Lying List Button", "Export Manifest");
		waitForSync(5);
		clear("Lying List Pop up");
		enterValueInTextbox(sheetName, "inbx_ULDNo_LyingList;xpath", uldNo,
				"ULD Number", "Lying List Pop up");
		clickWebElement(sheetName, "btn_uldTab_LyingList;xpath",
				"Lying List Button", "Lying List Pop up");
		clickWebElement("Generic_Elements", "btn_List;xpath", "List Button",
				"Lying List Pop up");
		checkIfUnchecked(sheetName, "chk_uld_LyingList;xpath", "ULD Check box",
				"Lying List Pop up");
		clickWebElement("Generic_Elements", "btn_childWinOk;xpath",
				"OK Button", "Lying List Pop up");

		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR016");

	}

	/**
	 * Description... Assign Second ULD
	 * 
	 * @param uldno
	 * @param POU
	 * @param contour
	 * @param Pieces
	 * @param Weight
	 * @param AWBNo
	 * @throws Exception
	 */
	// Assign AWB to ULD
	public void assignSecondULD(String uldno, String POU, String contour,
			String Pieces, String Weight, String AWBNo) throws Exception {

		switchToWindow("storeParent");

		clickWebElement(sheetName, "btn_AssignULD;name", "Assign ULD Button",
				"ExportManifest");
		waitForSync(5);
		switchToWindow("child");

		if (uldno.contains("KFW"))
			clickWebElement(sheetName, "chk_barrowCheck;xpath",
					"Barrow Check Box", "ExportManifest");
		enterValueInTextbox(sheetName, "inbx_uldNo;id", uldno, "ULD Number",
				"Export Manifest");
		selectValueInDropdown(sheetName, "lst_POU;id", POU, "Select POU",
				"Value");

		clickWebElement(sheetName, "btn_btnListULD;name", "List ULD Button",
				"ExportManifest");
		waitForSync(5);
		selectValueInDropdown(sheetName, "lst_contour;name", data(contour),
				"Select Contour", "Value");
		// enterValueInTextbox(sheetName, "inbx_awbPrefix;id",
		// data("carrierCode"), "Carrier Code", "Export Manifest");

		enterValueInTextbox(sheetName, "inbx_awbNo;id", AWBNo, "AWB No",
				"Export Manifest");
		clickWebElement(sheetName, "btn_btnListAWB;name", "List AWB Button",
				"ExportManifest");
		waitForSync(3);
		// to handle error date of journey is different from flight daate
		handleAlert("Accept", "Export Manifest");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_shipmentPieces;id", data(Pieces),
				"Pieces", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_AssignULD_wt;name", data(Weight),
				"Weight", "Export Manifest");
		map.put("Vol",
				getAttributeWebElement(sheetName, "txt_vol;xpath", "value",
						"vol", "Export Manifest"));

		keyPress("TAB");
		waitForSync(2);
		keyRelease("TAB");
		clickWebElement(sheetName, "btn_ULDok;id", "OK Button",
				"ExportManifest");
		waitForSync(10);
		switchToWindow("storeFirstChild");
		switchToWindow("secondChild");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_enterPieces;xpath", data(Pieces),
				"Pieces", "Shipment Location");
		clickWebElement(sheetName, "btn_ok2;xpath", "Ok Button",
				"Shipment Location");
		waitForSync(2);
		switchToWindow("getParent");
	}

	/**
	 * Description... Clicks on ULD Details Button and switch to the child
	 * window
	 * 
	 * @throws Exception
	 */
	public void clickULDDetails() throws Exception {
		clickButtonSwitchWindow(sheetName, "btn_UldDetails;name",
				"ULD Details Button", "Export Manifest");
	}

	/**
	 * Description... Clicks on Confirm Linkage Button and switch to the parent
	 * window
	 * 
	 * @throws Exception
	 */
	public void clickConfirmLinkage() throws Exception {
		clickWebElement(sheetName, "btn_ConfirmLinkage;name",
				"Confirm Linkage Button", screenName);

	}

	/**
	 * Description... Verify ULD Linkage Error Message and
	 * @throws IOException 
	 * 
	 * @throws Exception
	 */
	public void verifyULDLinkageErrorMessage() throws InterruptedException, IOException {

		handleAlert("GetTextAndClose", screenName);
		String actAlertText = getPropertyValue(globalVarPath, "AlertText");
		verifyValueOnPageContains(actAlertText, data("ULDLinkageError"),
				"Verify ULD Linkage Error Message", actAlertText,
				"ULD Linkage Error Message");
	}

	/**
	 * Description... Click ULD Details Close
	 * 
	 * @throws Exception
	 */
	public void clickULDDetailsClose() throws Exception {

		clickButtonSwitchtoParentWindow("Generic_Elements", "btn_close;name",
				"Close Button", "ULD Details Pop Up");

	}

	/**
	 * Description... Assign ULD2
	 * 
	 * @param uldno
	 * @param POU
	 * @param contour
	 * @param carrierCode
	 * @param Pieces
	 * @param Weight
	 * @param AWBNo
	 * @throws Exception
	 */
	public void assignULD2(String uldno, String POU, String contour,
			String carrierCode, String Pieces, String Weight, String AWBNo)
			throws Exception {

		switchToWindow("storeParent");
		if (uldno.contains("KFW"))
			clickWebElement(sheetName, "chk_barrowCheck;xpath",
					"Barrow Check Box", "ExportManifest");

		clickWebElement(sheetName, "btn_AssignULD;name", "Assign ULD Button",
				"ExportManifest");
		waitForSync(5);
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_uldNo;id", uldno, "ULD Number",
				"Export Manifest");
		selectValueInDropdown(sheetName, "lst_POU;id", POU, "Select POU",
				"Value");

		clickWebElement(sheetName, "btn_btnListULD;name", "List ULD Button",
				"ExportManifest");
		waitForSync(5);
		selectValueInDropdown(sheetName, "lst_contour;name", data(contour),
				"Select Contour", "Value");
		// code uncommented to enter carrier code other than 020

		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(carrierCode),
				"Carrier Code", "Export Manifest");

		enterValueInTextbox(sheetName, "inbx_awbNo;id", AWBNo, "AWB No",
				"Export Manifest");
		clickWebElement(sheetName, "btn_btnListAWB;name", "List AWB Button",
				"ExportManifest");
		waitForSync(3);
		// to handle error date of journey is different from flight daate
		handleAlert("Accept", "Export Manifest");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_shipmentPieces;id", data(Pieces),
				"Pieces", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_AssignULD_wt;name", data(Weight),
				"Weight", "Export Manifest");
		map.put("Vol",
				getAttributeWebElement(sheetName, "txt_vol;xpath", "value",
						"vol", "Export Manifest"));

		keyPress("TAB");
		waitForSync(2);
		keyRelease("TAB");
		clickWebElement(sheetName, "btn_ULDok;id", "OK Button",
				"ExportManifest");

		switchToWindow("getParent");
	}

	/**
	 * Description... Verify error Message ATD
	 * 
	 * @throws Exception
	 */
	public void verifyerrorMsgATD() throws Exception {

		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_FinalizeFlight;name",
				"Finalize Flight Button", "ExportManifest");

		waitForSync(2);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_Yes;xpath", "Yes Button",
				"Export Manifest");
		waitForSync(30);
		switchToWindow("child");
		clickWebElement(sheetName, "btn_SaveFinalizeFlt;name",
				"Save Finalize Flight Button", "Export Manifest");

		String expected = "Flight time is mandatory";
		String actual = driver.findElement(
				By.xpath("//*[@class='ic-error-container']")).getText();
		if (expected.equals(actual)) {
			verifyScreenText(screenName, expected, actual,
					"Finalize flight popup Export Manifest", "Finalize Flight");
		} else {
			verifyScreenText(screenName, expected, actual,
					"Finalize flight popup Export Manifest failed",
					"Finalize Flight");
		}

	}

	/**
	 * Description... Clicks on ULD Expand Link
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickULDExpandButton() throws InterruptedException, IOException {
		clickWebElement(sheetName, "lnk_expand_manifestDetailTable;xpath",
				"ULD Expand Link", screenName);
	}

	/**
	 * Description...Selects ULD Booking ID
	 */
	public void selectReferenceNumber() {
		selectValueInDropdown(sheetName,
				"customFunctions.lst_referanceNumber;name",
				data("ULDBookingID"), "ULD Booking ID", "VisibleText");
	}

	/**
	 * Description... Select Second Uld After Assign
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void selectSecondUldAfterAssign() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chk_uld2Details;xpath", "Bulk Check box ",
				screenName);
	}

	/**
	 * Description... Select First Uld After Assign
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void selectFirstUldAfterAssign() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chk_uldDetails;xpath", "Bulk Check box ",
				screenName);
	}

	/**
	 * Description... Click Reopen Flight
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickReopenFlght() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_reopenflt;xpath", "reopens flight ",
				screenName);
	}

	/**
	 * Description... Lying List from Flight Number
	 * 
	 * @param fromDate
	 * @param carrierCode
	 * @param flightNumber
	 * @param i
	 * @throws Exception
	 */
	public void lyingListfromFlghtNmbr(String fromDate, String carrierCode,
			String flightNumber, String i) throws Exception {
		screenName = "Lying List";
		switchToWindow("storeParent");
		clickButtonSwitchWindow(sheetName, "btn_lyingList;xpath",
				"Lying List Button", "Export Manifest");
		enterToFromDateListFlight(fromDate, fromDate, screenName);

		enterValueInTextbox("Generic_Elements", "inbx_shipmentPrefix;xpath",
				data("ShipmentPrefix"), "Shipment Prefix", screenName);

		listFlight("OPR016", carrierCode, flightNumber, fromDate,
				"Generic_Elements");

		String dyxpath = "//input[@name='shipmentCheckRow']" + "[" + i + "]";

		ele = findDynamicXpathElement(dyxpath, "Second Checkbox",
				"Export Manifest");

		clickWebElement(ele, "Shipment Row Check Box", screenName);
		clickWebElement("Generic_Elements", "btn_childWinOk;xpath",
				"OK Button", screenName);
		handleAlert("Accept", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR016");

	}

	/**
	 * Description... Verify Origin And Destination
	 * 
	 * @param Origin
	 * @param Destination
	 */
	public void verifyOriginAndDestination(String Origin, String Destination) {

		ele = findDynamicXpathElement("txt_origin;xpath", sheetName,
				"Text Finalized", screenName);
		String actText = ele.getText();

		customFuction.verifyScreenText(sheetName, Origin, actText,
				"Flight Finalization", "Finalize Flight");
		waitForSync(1);

		ele = findDynamicXpathElement("txt_destination;xpath", sheetName,
				"Text Finalized", screenName);
		String actText1 = ele.getText();

		customFuction.verifyScreenText(sheetName, Destination, actText1,
				"Flight Finalization", "Finalize Flight");
		waitForSync(1);

	}

	/**
	 * Description... Offload ULD with pieces And Weight
	 * 
	 * @param Pieces
	 * @param weight
	 * @throws Exception
	 */
	public void offloadULDwithpiecesAndWeight(String Pieces, String weight)
			throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_btnOffload;xpath", "Offload Button",
				screenName);
		switchToWindow("child");
		selectValueInDropdown(sheetName, "lst_oflReason;xpath", "1",
				"Offload Reason", "Index");

		enterValueInTextbox(sheetName, "inbx_offloadedpieces;xpath", Pieces,
				" ", screenName);
		enterValueInTextbox(sheetName, "inbx_offloadedweight;xpath", weight,
				" ", screenName);

		clickWebElement(sheetName, "btn_Offload;xpath", "Offload Button",
				"Offload Shipment");
		switchToWindow("getParent");
	}

	/**
	 * Description... Get Time
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public String getTime() throws InterruptedException {

		String Text = getElementText(sheetName, "txt_dept_time;xpath",
				"Departure Time", screenName).replace(":", "");

		String Depttime = Text.substring(0, 4);
		return Depttime;

	}

	/**
	 * Description... Select AWB
	 * 
	 * @param AWBNo
	 * @throws InterruptedException
	 */
	public void selectAWB(String AWBNo) throws InterruptedException {
		String xpath = xls_Read.getCellValue(sheetName, "chk_awb;xpath")
				.replace("AWBNo", AWBNo);
		clickWebElement(xpath, "AWB No Check Box", screenName);
	}

	public void verifyActualULDWeight(String actualWeight, String uldNumber,
			String POU) throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_AssignULD;name", "Assign ULD Button",
				"ExportManifest");
		waitForSync(6);
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_uldNo;id", uldNumber,
				"ULD Number", "Export Manifest");
		waitForSync(6);
		selectValueInDropdown(sheetName, "lst_POU;id", data(POU), "Select POU",
				"Value");
		if (uldNumber.contains("KFW"))
			clickWebElement(sheetName, "chk_barrowCheck;xpath",
					"Barrow Check Box", "ExportManifest");
		waitForSync(3);
		clickWebElement(sheetName, "btn_btnListULD;name", "List ULD Button",
				"ExportManifest");
		waitForSync(5);
		String weight = getAttributeWebElement(sheetName, "inbx_actWt;name",
				"actual ULD weight", "value", screenName);
		verifyScreenTextWithExactMatch(sheetName, data(actualWeight), weight,
				"Verification of actual ULD Wt in ULD Tag screen ", screenName);

		clickClose();
		waitForSync(3);
		switchToWindow("getParent");
		waitForSync(3);
	}

	/**
	 * Description... Assigns ULD if awb date and flight date is different
	 * 
	 * @param AWBNo
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickClose() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_close1;name", "close Button",
				"ExportManifest");
	}

	// Assign AWB to ULD
	public void assignULDWithDifferentDate(String uldno, String POU,
			String contour, String Pieces, String Weight, String carrierCode,
			String AWBNo, String location) throws Exception {

		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_AssignULD;name", "Assign ULD Button",
				"ExportManifest");
		waitForSync(6);
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_uldNo;id", data(uldno),
				"ULD Number", "Export Manifest");
		selectValueInDropdown(sheetName, "lst_POU;id", data(POU), "Select POU",
				"Value");
		if (data(uldno).contains("KFW"))
			clickWebElement(sheetName, "chk_barrowCheck;xpath",
					"Barrow Check Box", "ExportManifest");
		waitForSync(3);
		clickWebElement(sheetName, "btn_btnListULD;name", "List ULD Button",
				"ExportManifest");
		waitForSync(5);
		if (!location.equals("")) {
			// location
			enterValueInTextbox(sheetName, "inbx_locationCode;xpath",
					data(location), "location", "Export Manifest");
		}
		selectValueInDropdown(sheetName, "lst_contour;name", data(contour),
				"Select Contour", "Value");
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(carrierCode),
				"Carrier Code", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_awbNo;id", data(AWBNo), "AWB No",
				"Export Manifest");
		clickWebElement(sheetName, "btn_btnListAWB;name", "List AWB Button",
				"ExportManifest");
		waitForSync(3);
		try {
			Thread.sleep(4000);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button",
					screenName);
			Thread.sleep(4000);
		} catch (Exception e) {
		}
		enterValueInTextbox(sheetName, "inbx_shipmentPieces;id", data(Pieces),
				"Pieces", "Export Manifest");
		enterValueInTextbox(sheetName, "inbx_AssignULD_wt;name", data(Weight),
				"Weight", "Export Manifest");
		keyPress("TAB");
		waitForSync(2);
		keyRelease("TAB");
		clickWebElement(sheetName, "btn_ULDok;id", "OK Button",
				"ExportManifest");
		waitForSync(3);
		switchToWindow("getParent");
		waitForSync(3);
	}

	public void clickExpandULDBtn() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_expandULD;xpath", "Expand ULD Button",
				"ExportManifest");

	}

	public void unassignAWB(String awb) throws InterruptedException, IOException {
		WebElement ele = driver.findElement(By.xpath("//div[contains(text(),'"+awb+"')]/../..//td[1]"));
		waitForSync(3);
		ele.click();
		waitForSync(3);
		clickWebElement(sheetName, "btn_unAssign;xpath", "Unassign btn",
				"ExportManifest");
		
	}
	
	
	
	/**
	 * Description... Send FFM / XFFM from OPR016 screen
	 * 
	 * @author A-6978
	 * @param FFMMsgType
	 * @param interfaceSys
	 * @param transMode
	 * @param address
	 * @param receipeintAddress
	 * @param qualifier
	 * @throws Exception
	 */
	
		public void sendFFM(String FFMMsgType, String interfaceSys, String transMode,
			String address,String envelope, String qualifier) throws Exception {
		
		switchToWindow("storeParent");
		waitForSync(10);
		clickWebElement(sheetName, "btn_sendFFM;name",
				"Send FFM Button", "ExportManifest");
		waitForSync(10);
		switchToWindow("childWindow");

		selectValueInDropdown(sheetName, "lst_FFMMsgType;name", FFMMsgType, "FFM Msg Type",
				"Value");
		clickWebElement(sheetName, "btn_add;xpath",
				"Add Button", "ExportManifest");
		selectValueInDropdown(sheetName, "lst_interfaceSys;name", interfaceSys, "Interface System dropdown",
				"Value");
		selectValueInDropdown(sheetName, "lst_transmissionMode;name", transMode, "Transimssion Mode dropdown",
				"VisibleText");
		clickWebElement(sheetName, "btn_address;name",
				"Address Button", "ExportManifest");
		switchToWindow("storeFirstChild");
		
		
		switchToWindow("childWindow2");
		enterValueInTextbox(sheetName, "txt_JNDIName;id", address,
				"JNDI name", "Export Manifest");
		clickWebElement(sheetName, "btn_OK;name",
				"OK Button", "ExportManifest");
		
		switchToWindow("getFirstChild");
		
		enterValueInTextbox(sheetName, "txt_envelope;name", envelope,
				"Envelope", "Export Manifest");
		clickWebElement(sheetName, "btn_envelopeAddress;name",
				"Envelope address Button", "ExportManifest");
		
		switchToWindow("storeFirstChild");
		
		switchToWindow("childWindow2");
		enterValueInTextbox(sheetName, "txt_recAddress;id", interfaceSys,
				"Receipeint Address", "Export Manifest");
		selectValueInDropdown(sheetName, "lst_qualifier;id", qualifier, "Qualifier",
				"Value");
		clickWebElement(sheetName, "btn_OK;name",
				"OK Button", "ExportManifest");
		
		switchToWindow("getFirstChild");
		
		clickWebElement(sheetName, "btn_OK;name",
				"OK Button", "ExportManifest");
		
		switchToWindow("getParent");	
		switchToFrame("contentFrame", "OPR016");
		
		
	}
		
		
		/**
		 * Description... Send FFM / XFFM from OPR016 screen
		 * 
		 * @author A-6978
		 * @param FFMMsgType
		 * @param interfaceSys
		 * @param Envelope address
		 * @param receipeintAddress
		
		 * @throws Exception
		 */
		
			public void sendFFMTTY(String FFMMsgType, String interfaceSys,String transMode,
				String envelopeaddress,String envelope,String recipientAddress) throws Exception {
			
			switchToWindow("storeParent");
			waitForSync(10);
			clickWebElement(sheetName, "btn_sendFFM;name",
					"Send FFM Button", "ExportManifest");
			waitForSync(10);
			switchToWindow("childWindow");

			selectValueInDropdown(sheetName, "lst_FFMMsgType;name", FFMMsgType, "FFM Msg Type",
					"Value");
			clickWebElement(sheetName, "btn_add;xpath",
					"Add Button", "ExportManifest");
			selectValueInDropdown(sheetName, "lst_interfaceSys;name", interfaceSys, "Interface System dropdown",
					"Value");
			selectValueInDropdown(sheetName, "lst_transmissionMode;name", transMode, "Transimssion Mode dropdown",
					"VisibleText");
			clickWebElement(sheetName, "btn_address;name",
					"Address Button", "ExportManifest");
			switchToWindow("storeFirstChild");
			
			
			switchToWindow("childWindow2");
			enterValueInTextbox(sheetName, "txt_JNDIName;id", envelopeaddress,
					"JNDI name", "Export Manifest");
			clickWebElement(sheetName, "btn_OK;name",
					"OK Button", "ExportManifest");
			
			switchToWindow("getFirstChild");
			
			enterValueInTextbox(sheetName, "txt_envelope;name", envelope,
					"Envelope", "Export Manifest");
			clickWebElement(sheetName, "btn_envelopeAddress;name",
					"Envelope address Button", "ExportManifest");
			
			switchToWindow("storeFirstChild");
			
			switchToWindow("childWindow2");
			enterValueInTextbox(sheetName, "txt_recAddressTTY;id", recipientAddress.replace("~", ""),
					"Receipeint Address", "Export Manifest");
			
			clickWebElement(sheetName, "btn_OK;name",
					"OK Button", "ExportManifest");
			
			switchToWindow("getFirstChild");
			
			clickWebElement(sheetName, "btn_OK;name",
					"OK Button", "ExportManifest");
			
			switchToWindow("getParent");	
			switchToFrame("contentFrame", "OPR016");
			
			
		}
			
			
			
			/**
			 * Description... Send FFM / XFFM from OPR016 screen
			 * 
			 * @author A-6978
			 * @param FFMMsgType
			 * @param interfaceSys
			 * @param transMode
			 * @param Envelope address
			 * @param receipeintAddress
			
			 * @throws Exception
			 */
			
				public void sendFFM(String FFMMsgType, String interfaceSys,
					String envelopeaddress,String recipientAddress) throws Exception {
				
				switchToWindow("storeParent");
				waitForSync(10);
				clickWebElement(sheetName, "btn_sendFFM;name",
						"Send FFM Button", "ExportManifest");
				waitForSync(10);
				switchToWindow("childWindow");

				selectValueInDropdown(sheetName, "lst_FFMMsgType;name", FFMMsgType, "FFM Msg Type",
						"Value");
				clickWebElement(sheetName, "btn_add;xpath",
						"Add Button", "ExportManifest");
				selectValueInDropdown(sheetName, "lst_interfaceSys;name", interfaceSys, "Interface System dropdown",
						"Value");
				
				clickWebElement(sheetName, "btn_address;name",
						"Address Button", "ExportManifest");
				switchToWindow("storeFirstChild");
				
				
				switchToWindow("childWindow2");
				enterValueInTextbox(sheetName, "txt_JNDIName;id", envelopeaddress,
						"JNDI name", "Export Manifest");
				clickWebElement(sheetName, "btn_OK;name",
						"OK Button", "ExportManifest");
				
				switchToWindow("getFirstChild");
				
				
				clickWebElement(sheetName, "btn_envelopeAddress;name",
						"Envelope address Button", "ExportManifest");
				
				switchToWindow("storeFirstChild");
				
				switchToWindow("childWindow2");
				enterValueInTextbox(sheetName, "txt_recAddressTTY;id", recipientAddress.replace("~", ""),
						"Receipeint Address", "Export Manifest");
				
				clickWebElement(sheetName, "btn_OK;name",
						"OK Button", "ExportManifest");
				
				switchToWindow("getFirstChild");
				
				clickWebElement(sheetName, "btn_OK;name",
						"OK Button", "ExportManifest");
				
				switchToWindow("getParent");	
				switchToFrame("contentFrame", "OPR016");
				
				
			}
	

}