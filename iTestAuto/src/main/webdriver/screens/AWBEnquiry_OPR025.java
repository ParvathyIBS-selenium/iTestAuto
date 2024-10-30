package screens;

import org.testng.Assert;
import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AWBEnquiry_OPR025 extends CustomFunctions {
	public AWBEnquiry_OPR025(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "AWBEnquiry_OPR025";
	public String ScreenName = "AWBEnquiry_OPR025";

	/**
	 * Description...  Verify the SCC Code
	 * @param scc
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifySCC(String scc) throws InterruptedException, AWTException {
		By ele = getElement(sheetName, "spn_SCC;xpath");
		String actScc = driver.findElement(ele).getText();
		verifyScreenText(sheetName, scc, actScc, " SCC verification ", ScreenName);
	}
/**
 * Description...  Verify the Shipper AND Consignee
 * @param shipper
 * @param Consignee
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifyShipperANDConsignee(String shipper, String Consignee) throws InterruptedException, AWTException {
		By ele = getElement(sheetName, "spn_Shipper;xpath");
		String actshipper = driver.findElement(ele).getText();
		verifyScreenText(sheetName, shipper, actshipper, " Shipper Name verification ", ScreenName);

		ele = null;
		ele = getElement(sheetName, "spn_consignee;xpath");
		String actConsignee = driver.findElement(ele).getText();
		verifyScreenText(sheetName, Consignee, actConsignee, "Consignee Name verification ", ScreenName);
	}
/**
 * Description...  Verify the Message Contents
 * @param msg
 * @param msgContents
 * @throws Exception
 */
	public void verifyMsgContents(String msg, String msgContents[]) throws Exception {
		switch (msg) {

		case "FSU":

			String fsu[] = { "QLHLOLH~", "6" };
			selectTableRecordWithMultipleKeys(fsu, sheetName, "table_messageDetails;xpath", "chk_message;xpath", 5);

			break;

		case "FOH":
			String foh[] = { "QLHLOLH~", "14" };
			selectTableRecordWithMultipleKeys(foh, sheetName, "table_messageDetails;xpath", "chk_message;xpath", 5);

			break;
		}
		clickWebElement(sheetName, "btn_showContents;xpath", "click show contents", ScreenName);
		waitForSync(2);
		for (int i = 0; i < msgContents.length; i++) {
			ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_messageContent;xpath")));
			String actText = ele.getAttribute("value");
			verifyScreenText(sheetName, msgContents[i], actText, "Msg verification", ScreenName);
		}
		clickButtonSwitchtoParentWindow(sheetName, "btn_closeMsgDetails;xpath", "Close Message button", ScreenName);
		waitForSync(1);
	}
/**
 * Description...  Verify the Shipment AND Route
 * @param shipmnt
 * @param route
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifyShipmntANDRoute(String shipmnt, String route) throws InterruptedException, AWTException {
		By ele = getElement(sheetName, "spn_shipment;xpath");
		String actshipmnt = driver.findElement(ele).getText();
		verifyScreenText(sheetName, shipmnt, actshipmnt, " Shipment Description verification ", ScreenName);

		ele = null;
		ele = getElement(sheetName, "spn_route;xpath");
		String actroute = driver.findElement(ele).getText();
		verifyScreenText(sheetName, route, actroute, "Route verification ", ScreenName);
	}
/**
 * Description...  Verify the Pieces Weight
 * @param pcs
 * @param wgt
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifyPcsWgt(String pcs, String wgt) throws InterruptedException, AWTException {
		By ele = getElement(sheetName, "spn_StatedPcs;xpath");
		String actpcs = driver.findElement(ele).getText();
		verifyScreenText(sheetName, pcs, actpcs, " Stated Pcs verification ", ScreenName);

		ele = null;
		ele = getElement(sheetName, "spn_StatedWgt;xpath");
		String actwgt = driver.findElement(ele).getText();
		verifyScreenText(sheetName, wgt, actwgt, "Stated weight verification ", ScreenName);
	}
/**
 * Description... Verify the Flight Number
 * @param fltNo
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifyFlightNo(String fltNo) throws InterruptedException, AWTException {
		By ele = getElement(sheetName, "td_flightNo;xpath");
		String actfltNo = driver.findElement(ele).getText();
		verifyScreenText(sheetName, fltNo, actfltNo, "Flight Number  verification ", ScreenName);
	}

	
	/**
	 * Description... Verification of Not Ready for carriage
	 * @throws Exception
	 */
	public void verificationOfNotRFCStatusOnOPR025() throws Exception {

		waitForSync(3);
		verifyElementDisplayed(sheetName, "txt_NotRFC;xpath", "5", ScreenName, "Ready For Carriage");

	}

	
	/**
	 * Description... Verification of Ready for carriage
	 * @throws Exception
	 */
	public void verificationOfRFCStatusOnOPR025() throws Exception {

		waitForSync(3);
		verifyElementDisplayed(sheetName, "txt_RFC;xpath", "5", ScreenName, "Ready For Carriage");

	}
/**
 * Description... Click the History Button
 * @throws Exception
 */
	public void clickHistory() throws Exception {
		waitForSync(2);
		clickButtonSwitchWindow(sheetName, "btn_History;xpath", ScreenName, "History button");
		waitForSync(3);

	}
/**
 * Description... Verify History 
 * @param pmy Key
 */
	public void verifyHistory(String pmKey) {

		try {
			String xpathExpression = "//table[@id='ListTable1']//tr[contains(.,'" + pmKey + "')]//td[1]";
			WebElement row = driver.findElement(By.xpath(xpathExpression));
			String status = row.getText();
			System.out.println(status);
			writeExtent("Pass", pmKey + " status is" + status);

		} catch (Exception e) {

			System.out.println(pmKey + " status is not Sent");
			test.log(LogStatus.FAIL, pmKey + " status is not Sent");

		}

	}
/**
 * Description... Close History button
 * @throws Exception
 */
	public void closeHistory() throws Exception {

		clickButtonSwitchtoParentWindow(sheetName, "btn_closeAudit;xpath", "Close button", ScreenName);
		waitForSync(3);

	}
/**
 * Description... Select the Transaction Group
 * @param visibletext
 * @param index
 * @throws InterruptedException
 * @throws IOException 
 */
	public void selectTransactionGroup(String visibletext, String[] index) throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_transactiongroup;id", "Transaction Group button", ScreenName);
		waitForSync(2);

		switch (visibletext) {

		case "check_all":
			clickWebElement(sheetName, "lnk_CheckAll;xpath", "Check All ", ScreenName);
			break;

		case "uncheck_all":
			clickWebElement(sheetName, "lnk_UnCheckAll;xpath", "UnCheck All ", ScreenName);
			break;

		case "index":
			for (int i = 0; i < index.length; i++) {
				String dynxapth = "//ul[@class='ui-multiselect-checkboxes ui-helper-reset']//li[" + index[i]
						+ "]//input";
				driver.findElement(By.xpath(dynxapth)).click();
			}

		}
	}

/**
 * Description... Verify Row Count
 * @param expRowSize, filter, sheetName, locator
 * @throws Interrupted Exception, AWT Exception
 */
	public void verifyRowCount(int expRowSize, String filter, String sheetName, String locator)
			throws InterruptedException, AWTException {
		String dynXpath = xls_Read.getCellValue(sheetName, locator) + "[contains(.,'" + filter + "')]";
		System.out.println("dynXpath is---" + dynXpath);

		List<WebElement> rows = driver.findElements(By.xpath(dynXpath));
		System.out.println("row size is---" + rows.size());
		int actRowSize = rows.size();

		System.out.println("actRowSize ---" + actRowSize);

		if (expRowSize == actRowSize)
			onPassUpdate((sheetName.split("_"))[0], String.valueOf(expRowSize), String.valueOf(actRowSize),
					"Precheck Success should not be stamped after revalidate from  Precheck List screen",
					"Precheck Success should not be stamped after revalidate from Precheck List screen verification");
		else
			onFailUpdate((sheetName.split("_"))[0], String.valueOf(expRowSize), String.valueOf(actRowSize),
					"Precheck Success should not be stamped after revalidate from  Precheck List screen",
					"Precheck Success should not be stamped after revalidate from Precheck List screen verification");

	}
/**
 * Description... Apply the Trigger Point Filter
 * @param Filter
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void applyTriggerPointFilter(String Filter) throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "div_TriggerPointFilter;xpath", "Trigger Point Filter ", ScreenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_Filter;xpath", Filter, "Trigger Point Filter ", ScreenName);
		keyPress("ENTER");
		keyRelease("ENTER");
		waitForSync(5);

	}
/**
 * Description... Apply the Transaction Filter
 * @param Filter
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void applyTransactionFilter(String Filter) throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "div_TransactionFilter;xpath", "Transaction Filter ", ScreenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_Filter;xpath", Filter, "Transaction Filter ", ScreenName);
		keyPress("ENTER");
		keyRelease("ENTER");
		waitForSync(5);

	}
/**
 * Description... Select Panel Value
 * @param visibleText
 */
	public void selectPanelValue(String visibleText) {

		selectValueInDropdown(sheetName, "lst_panelValue;id", visibleText, "Panel value", "VisibleText");
		waitForSync(5);

	}
/**
 * Description... Select Outgoing Message
 * @param MessageType
 */
	public void selectOutgoingMessaging(String MessageType) {

		String xpath = xls_Read.getCellValue(sheetName, "tbl_OutgoingMessages;xpath");
		String dynxpath = xpath + "[contains(.,'" + MessageType + "')]//td[1]";
		try {
			driver.findElement(By.xpath(dynxpath)).click();
		} catch (Exception e) {

			System.out.println("Could not click on checkbox " + MessageType);
			writeExtent("Fail", "Could not click on checkbox " + MessageType);
			Assert.assertFalse(true, "Could not click on checkbox" + MessageType);
		}
	}
/**
 * Description... Click the Message Detail Button
 * @throws Exception
 */
	public void clickMessageDetails() throws Exception {

		clickButtonSwitchWindow(sheetName, "btn_MessageDetail;id", ScreenName, "Message Detail Button ");

	}
/**
 * Description... Select the AWB Info Type
 * @param awbInfo
 * @throws Exception
 */
	public void selectAwbInfoType(String awbInfo) throws Exception {

		selectValueInDropdown(sheetName, "lst_selectAwbInfoType;xpath", data(awbInfo), "Awb Info Type", "VisibleText");
		waitForSync(2);
	}
/**
 * Description... Select Messages
 * @param msgType
 * @throws InterruptedException
 * @throws IOException 
 */
	public void selectMessages(String msgType) throws InterruptedException, IOException {
		switch (msgType) {

		case "FSU":
			clickWebElement(sheetName, "chk_msgType;xpath", "check FSU", ScreenName);
			waitForSync(2);
			break;

		}
	}
/**
 * Description... Verify Message Content
 * @param MessageSubType
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyMessageContent(String MessageSubType) throws InterruptedException, IOException {
		String xpath = xls_Read.getCellValue(sheetName, "tbl_MessageDetail;xpath");

		switch (MessageSubType) {

		case "FSU-RCS":
			String Dynxpath = xpath + "[contains(.,'HDQFMAA~,QLHLOLH~')]//input";
			driver.findElement(By.xpath(Dynxpath)).click();
			clickWebElement(sheetName, "btn_ShowContents;id", "Show Content Button ", ScreenName);
			String actText = getElementText(sheetName, "inbx_MessageContents;name", "Message contents", ScreenName);
			System.out.println(actText);
			if (actText.contains("FSU/6")) {
				customFunction.onPassUpdate(ScreenName, "FSU/6", actText, "Outgoing Message", "FSU-RCS verification");

			} else {
				Status = false;
				customFunction.onFailUpdate(ScreenName, "FSU/6", actText, "Outgoing Message", "FSU-RCS verification");
			}

			break;

		case "FSU-FOH":
			String Dynxpath2 = xpath + "[contains(.,'	QLHLOLH~')]//input";
			driver.findElement(By.xpath(Dynxpath2));
			clickWebElement(sheetName, "btn_ShowContents;id", "Show Content Button ", ScreenName);
			String actText1 = getElementText(sheetName, "inbx_MessageContents;name", "Message contents", ScreenName);
			System.out.println(actText1);
			if (actText1.contains("FSU/14")) {
				customFunction.onPassUpdate(ScreenName, "FSU/14", actText1, "Outgoing Message", "FSU-FOH verification");

			} else {
				Status = false;
				customFunction.onFailUpdate(ScreenName, "FSU/14", actText1, "Outgoing Message", "FSU-FOH verification");
			}

			break;

		}

	}
/**
 * Description... Click the Close message details button
 * @throws Exception
 */
	public void clickCloseMessageDetails() throws Exception {

		clickButtonSwitchtoParentWindow(sheetName, "btn_CloseMessageDetail;id", "Close message details button",
				ScreenName);

	}
/**
 * Description... Verify the SCI
 * @param SCI
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifySCI(String SCI) throws InterruptedException, AWTException {
		By ele = getElement(sheetName, "spn_SCI;xpath");
		String actSCI = driver.findElement(ele).getText();
		verifyScreenText(sheetName, SCI, actSCI, "SCI verification ", ScreenName);
	}
/**
 * Description... Verify the History Details
 * @param verfCols
 * @param actVerfValues
 * @param pmKey
 * @throws IOException 
 */
	public void verifyHistoryDetails(int verfCols[], String actVerfValues[], String pmKey) throws IOException {
		waitForSync(4);
		verify_tbl_records_multiple_cols(sheetName, "table_historyDetails;xpath", "//td", verfCols, pmKey,
				actVerfValues);
	}

	/**
	 * Description... Verify the Discrepancy Details
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws IOException 
	 */
	public void verifyDiscrepancyDetails(int verfCols[], String actVerfValues[], String pmKey) throws IOException {
		waitForSync(4);
		verify_tbl_records_multiple_cols(sheetName, "tbl_Discrepancy;xpath", "//td", verfCols, pmKey, actVerfValues);
	}
	
	public void verifyCustomsDetails(int verfCols[], String actVerfValues[], String pmKey, String expCustomsStatusCode) throws InterruptedException {
		waitForSync(4);
		verify_tbl_records_multiple_cols_contains(sheetName, "tbl_customsDetails;xpath", "//td", verfCols, pmKey,
				actVerfValues);
		String actCustomsCode = getElementText(sheetName, "txt_CustomsStatusCode;xpath", "Customs status code", ScreenName);
		if (actCustomsCode.contains(expCustomsStatusCode)) {
			System.out.println("found true for " + actCustomsCode);

			onPassUpdate(ScreenName, expCustomsStatusCode, actCustomsCode,
					"Customs status code verification against " + pmKey, "Customs status code verification");

		} else {
			onFailUpdate(ScreenName, expCustomsStatusCode, actCustomsCode,
					"Customs status code verification against " + pmKey, "Customs status code verification");

		}
		
	}
	
	public void verifyOtherCustomsInformations(int verfCols[], String actVerfValues[], String pmKey) {
		waitForSync(4);
		verify_tbl_records_multiple_cols_contains(sheetName, "tbl_OCIdetails;xpath", "//td", verfCols, pmKey,
				actVerfValues);
		
	}
	
	

	public void verifyCustomsDetails_multipleCustomStatusCode(int verfCols[], String actVerfValues[], String pmKey, String[] expCustomsStatusCode, int[] row) throws InterruptedException {
		waitForSync(4);
		verify_tbl_records_multiple_cols_contains(sheetName, "tbl_customsDetails;xpath", "//td", verfCols, pmKey,
				actVerfValues);
		
		
		String xpath1 = xls_Read.getCellValue(sheetName, "txt_CustomsStatusCode_multiple;xpath");
		 
		for(int i =0; i< row.length; i++){
			
			String dynXpath = xpath1 + row[i] + "]";
			
			String actCustomsCode = driver.findElement(By.xpath(dynXpath)).getText();
			
		if (actCustomsCode.contains(expCustomsStatusCode[i])) {
			System.out.println("found true for " + actCustomsCode);

			onPassUpdate(ScreenName, expCustomsStatusCode[i], actCustomsCode,
					"Customs status code verification against " + pmKey, "Customs status code verification");

		} else {
			onFailUpdate(ScreenName, expCustomsStatusCode[i], actCustomsCode,
					"Customs status code verification against " + pmKey, "Customs status code verification");

		}
		
		}
		
	}
	
	
	
	public void verifyCustomsCountry(String expCustomsCountry) throws InterruptedException {
				
		String actCustomsCode = getElementText(sheetName, "txt_CustomsCountry;xpath", "Customs country", ScreenName);
						
				
		if (actCustomsCode.replace(" ", "").contains(expCustomsCountry.replace(" ", ""))) {
			System.out.println("found true for " + actCustomsCode);

			onPassUpdate(ScreenName, expCustomsCountry, actCustomsCode,
					"Customs country verification  " , "Customs country verification");

		} else {
			onFailUpdate(ScreenName, expCustomsCountry, actCustomsCode,
					"Customs country verification " , "Customs country verification");

		}
		
	}
	
}