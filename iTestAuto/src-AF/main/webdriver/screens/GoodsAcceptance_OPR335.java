package screens;

import java.util.Random;
import java.awt.AWTException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import rest_sfmi.JSONBody;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.relevantcodes.extentreports.LogStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class GoodsAcceptance_OPR335 extends CustomFunctions {
	public GoodsAcceptance_OPR335(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "GoodsAcceptance_OPR335";
	public String screenName = "GoodsAcceptance";
	CustomFunctions comm = new CustomFunctions(driver, excelreadwrite, xls_Read);
	JSONBody jsonbody = new JSONBody(driver, excelreadwrite, xls_Read);
	
	
	

	/**
	 * Description: Enter loose Shipment Details
	 * 
	 * @param ShipmentAcceptanceLocation
	 * @param ShipmentPieces
	 * @param ShipmentWeight
	 * @throws Exception
	 */
	public void looseShipmentDetails(String ShipmentAcceptanceLocation, String ShipmentPieces, String ShipmentWeight)
			throws Exception {
		Thread.sleep(3000);

		clickWebElement(sheetName, "div_LooseAcceptance;xpath", "Loose acceptance tab open", screenName);
		waitForSync(4);

		enterValueInTextbox(sheetName, "inbx_LooseShipmentPcs;name", data(ShipmentPieces), "ShipmentLocation",
				screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentWt;name", data(ShipmentWeight), "ShipmentWeight", screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentLoc;name", data(ShipmentAcceptanceLocation),
				"ShipmentLocation", screenName);
		map.put("VPPWeight", data(ShipmentWeight));
		map.put("VPPVolume", getAttributeWebElement(sheetName, "inbx_volume;xpath", "Volume", "value", screenName));
		map.put("VPPType", "loose");

		/******************* Select SCC *****************/

		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
		waitForSync(1);
		selectSCCs();
		
		/*****clickWebElement(sheetName, "span_checkAllSCCs;xpath", "Check SCC", screenName);
		waitForSync(1);
		clickWebElement(sheetName, "span_closeCheckSCCs;xpath", "Button Close SCC", screenName);
		waitForSync(1);*****/

	}
	/**
	 * Description... Edit first ULD Acceptance
	 * 
	 * @param Pieces
	 * @param Weight
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */
	public void editFirstULDAcceptance(String location) throws InterruptedException, IOException, AWTException {

		clickWebElement(sheetName, "btn_Uldaccepatance;xpath", "ULDAcceptance", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "icon_editULDShp;xpath", "Edit ULD shipment icon", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_uldacceptanceLoc;xpath", data(location), "Location", screenName);	
		clickWebElement(sheetName, "btn_ULDokButton;xpath", "OK Button", screenName);
		waitForSync(2);
		
		
		

	}
	/**
	 * Description... Edit  second ULD Acceptance
	 * 
	 * @param Pieces
	 * @param Weight
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */
	public void editSecondULDAcceptance(String location) throws InterruptedException, IOException, AWTException {

		
		clickWebElement(sheetName, "icon_editSecondULDShpDetails;xpath", "Edit ULD shipment icon", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_uldacceptanceLoc;xpath", data(location), "Location", screenName);
		clickWebElement(sheetName, "btn_ULDokButton;xpath", "OK Button", screenName);
		waitForSync(2);
		

	}

/**
	 * @author A-9844
	 * @Desc To Capture DG Details Information for DG Goods
	 * @throws Exception
	 */
	public void CaptureDGDetails(String[] ULDNo,String[] pieces,int count) throws Exception
	{

	
		driver.switchTo().frame("iCargoContentFrameOPR335");
		driver.switchTo().frame("popupContainerFrame");
		for(int i=0;i<count;i++){

			String locator = xls_Read.getCellValue(sheetName, "txt_DGuldNo;xpath");
			locator=locator.replace("uld", ULDNo[i]);

			driver.findElement(By.xpath(locator)).click();
			waitForSync(2);

			String pcslocator = xls_Read.getCellValue(sheetName, "inbx_pcsInULD;xpath");

			pcslocator=pcslocator.replace("uld", ULDNo[i]);
			driver.findElement(By.xpath(pcslocator)).sendKeys(pieces[i]);
			waitForSync(2);
			
		}

		clickWebElement(sheetName, "btn_DGSave;xpath", "Save Button", screenName);
		waitForSync(5);
		
	

		

	}
	public void selectSCCs()
	{
		
		try
		{
			int checkSCCs=driver.findElements(By.xpath("//span[contains(.,'Check all')]")).size(); 
			int closeCheckSCCs=driver.findElements(By.xpath("//span[@class='ui-icon ui-icon-circle-close']")).size();


			while(!driver.findElement(By.xpath("(//span[contains(.,'Check all')])["+checkSCCs+"]")).isDisplayed())
			{
				checkSCCs=checkSCCs-1;


			}
			driver.findElement(By.xpath("(//span[contains(.,'Check all')])["+checkSCCs+"]")).click();

			while(!driver.findElement(By.xpath("(//span[@class='ui-icon ui-icon-circle-close'])["+closeCheckSCCs+"]")).isDisplayed())
			{
				closeCheckSCCs=closeCheckSCCs-1;


			}
			driver.findElement(By.xpath("(//span[@class='ui-icon ui-icon-circle-close'])["+closeCheckSCCs+"]")).click();
		}
		catch(Exception e)
		{

		}

		}
	
	/**
	 * @author A-9175
	 * @Description : Returning SU number generated
	 * @param pos
	 * @return
	 */
	public String getSUNumber(int pos)
    {
		String SUnumber="";
		try 
		{
			String locatorSU = xls_Read.getCellValue(sheetName, "txt_Sunumber;xpath");
			locatorSU = locatorSU.replace("*", Integer.toString(pos));
			SUnumber = driver.findElement(By.xpath(locatorSU)).getText();
			System.out.println(SUnumber);
			writeExtent("Pass", "SU number generated as :"+SUnumber+"on "+screenName);
		} catch (Exception e) {
			writeExtent("Fail", "SU number Not generated on "+screenName);
		}
		return SUnumber;
		
            
    }
	/**
	 * @author A-9844
	 *  Description...fetch SCC value displayed
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String retrieveSCCs() throws InterruptedException, IOException {

		String scc = new String();

		String locator = xls_Read.getCellValue(sheetName, "txt_sccs;xpath");
		String sccs = locator.replace("value", "2");

		waitTillScreenload(sheetName, "btn_editIcon;xpath", "Edit Icon", screenName);
		/***************/
		if (!driver.findElement(By.xpath(sccs)).isDisplayed()) {
			clickWebElement(sheetName, "tab_AWBdetails;id", "AWB details", screenName);
			waitForSync(3);
		}
		/***************/
		scc = driver.findElement(By.xpath(sccs)).getText();
		return scc;


	}




	/**
	 * @author A-9175
	 * @Description : Verification for split shipment icon
	 */
	public void verifyInfoiconforSplitShipment() {
		try {
			verifyElementDisplayed(sheetName, "img_info;xpath", " Split Shipment  Info Icon ", screenName,
					"Split Shipment Info Icon");
		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify Split shipment Icon in " + screenName);
		}
	}

	/**
	 * @author A-9175
	 * @throws IOException
	 * @throws InterruptedException
	 * @Description : click split shipment icon
	 */
	public void clickInfoiconforSplitShipment() throws InterruptedException, IOException {
		clickWebElement(sheetName, "img_info;xpath", "Info Icon", screenName);
	}

	/**
	 * @author A-9175
	 * @Description : Verification for info tab icon for split information
	 * @param scc
	 * @param pcs
	 */
	public void verifysplitshipmentInfoFromInfoIcon(String scc, String pcs) {
		try {
			String locatorSCC = xls_Read.getCellValue(sheetName, "txt_infoSplitscc;xpath");
			locatorSCC = locatorSCC.replace("scc", scc);
			String locatorPCS = xls_Read.getCellValue(sheetName, "txt_infoSplitsccPcs;xpath");
			locatorPCS = locatorPCS.replace("pcs", pcs);
			if (driver.findElement(By.xpath(locatorSCC)).isDisplayed()
					&& driver.findElement(By.xpath(locatorPCS)).isDisplayed()) {
				writeExtent("Pass", "Sucessfully Verified Split info for SCC : " + scc + " as " + pcs + " in info icon "
						+ screenName);
			}
		} catch (Exception e) {
			writeExtent("Fail",
					"Not Verified Split info for SCC : " + scc + " as " + pcs + " in info icon " + screenName);
		}
	}

	/**
	 * @author A-9847
	 * @Desc To verify the ChkWeight and ChkVolume
	 * @param suWgt
	 * @param suVol
	 */

	public void verifySUWeightAndVolume(String suWgt, String suVol) {

		try {

			String actSUWgt = getAttributeWebElement(sheetName, "inbx_suWgt;xpath", "SU Weight", "value", screenName);
			verifyScreenTextWithExactMatch(sheetName, data(suWgt), actSUWgt, "SU Weight", screenName);

			String actSUVol = getAttributeWebElement(sheetName, "inbx_suVol;xpath", "SU Volume", "value", screenName);
			verifyScreenTextWithExactMatch(sheetName, data(suVol), actSUVol, "SU Volume", screenName);

		}

		catch (Exception e) {
			writeExtent("Fail", "Failed to verify the SU Weight and Volume on " + screenName);

		}

	}

	/**
	 * @author A-9847
	 * @Desc To verify the dimension details(length,Width,Height), Weight and
	 *       Volume in dimension section
	 * @param length
	 * @param width
	 * @param height
	 * @param SUWgt
	 * @param SUVol
	 * @throws InterruptedException
	 */

	public void verifyDimensionDetails(String length, String width, String height, String SUWgt, String SUVol)
			throws InterruptedException {

		try {

			String actdimensions = getElementText(sheetName, "txt_dimensionDetails;xpath", "Dimension Details",
					"GoodsAcceptance").replace(" ", "");
			System.out.println(actdimensions);

			String actlength = actdimensions.split(",")[0];
			String actwidth = actdimensions.split(",")[1];
			String actheight = actdimensions.split(",")[2].split("\\(")[0];

			verifyScreenTextWithExactMatch(sheetName, data(length), actlength, "Length", screenName);
			verifyScreenTextWithExactMatch(sheetName, data(width), actwidth, "Width", screenName);
			verifyScreenTextWithExactMatch(sheetName, data(height), actheight, "Height", screenName);

			String actSUWeight = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "div_dimWgt;xpath")))
					.getText();
			String actSUVol = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "div_dimVol;xpath")))
					.getText();

			verifyScreenTextWithExactMatch(sheetName, data(SUWgt), actSUWeight, "Total SU Weight", screenName);
			verifyScreenTextWithExactMatch(sheetName, data(SUVol), actSUVol, "Total SU Volume", screenName);

		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify the Dimension details on " + screenName);

		}

	}

	/**
	 * @author A-9847
	 * @Desc To click save button only
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void clickSaveOnly() throws InterruptedException, IOException {

		waitForSync(3);
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		try {
			Thread.sleep(8000);
			switchToFrame("default");
			waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
					screenName, 20);
			while (driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).isDisplayed()) {
				String msgText = getElementText(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "warning",
						screenName);
				clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
				Thread.sleep(8000);
				if (!msgText.contains("successfully saved"))
					waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath",
							"Warning Pop-Up", screenName, 20);
						}

					} catch (Exception e) {
					}

					switchToFrame("contentFrame", "OPR335");
				}

	

	/**
	 * @author A-9847
	 * @Desc To verify multiple errror messages
	 * @param screen
	 * @param errMessage
	 */
	public void verifyMultipleErrorMessages(String screen, String... errMessage)

	{
		String xpath = xls_Read.getCellValue("Generic_Elements", "htmlDiv_errorText;xpath");
		waitForSync(3);

		List<WebElement> ele = driver.findElements(By.xpath(xpath));
		System.out.println(ele.size());
		System.out.println(errMessage.length);

		try {

			for (int i = 0; i < errMessage.length; i++) {
				boolean msgFound = false;
				for (WebElement errMsg : ele) {
					System.out.println(errMsg.getText());
					System.out.println(errMessage[i]);
					if (errMsg.getText().equals(errMessage[i])) {
						msgFound = true;
					}

				}

				if (msgFound) {
					writeExtent("Pass", "Error message '" + errMessage[i] + "' shown on " + screen);
					System.out.println("Error message " + errMessage[i] + " shown on " + screen);
				} else {
					writeExtent("Fail", "Error message '" + errMessage[i] + "' not shown on " + screen);
					System.out.println("Error message " + errMessage[i] + " not shown on " + screen);
				}
			}
		}

		catch (Exception e) {
			writeExtent("Fail", "Expected Error message  not shown on " + screen);
			System.out.println("Expected Error message  not shown on " + screen);
		}

	}

	/**
	 * @author A-9844
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 *             Description : entered awb number in hht and accept the pop up
	 * @throws IOException
	 */
	public void enterAWBNumber(String value) throws AWTException, InterruptedException, IOException {
		enterValueInHHT("gahht_inbx_enterValue;accessibilityId", proppathhht, data(value), "List Value", screenName);
		waitForSync(7);

		/** AWB does not exist pop up and Clicking Yes **/
		String locatorYes = getPropertyValue(proppathhht, "btn_Yes;xpath");
		if (androiddriver.findElements(By.xpath(locatorYes)).size() == 1) {
			androiddriver.findElement(By.xpath(locatorYes)).click();
			waitForSync(5);
		}
	}

	/**
	 * @author A-10328 Description... Security And Screening in OPR335
	 * @throws Exception
	 */
	public void clicksecurityAndScreening() throws Exception

	{
		screenName = "Security and Screening Details";
		clickWebElement(sheetName, "btn_Secexpand;xpath", "Security & Screening details", screenName);
		waitForSync(2);
		switchToFrame("default");
		waitForSync(2);
		switchToFrame("frameName", "iCargoContentFrameOPR335");
		waitForSync(2);

	}

	/**
	 * @author A-9844
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 *             Description : capture awb details
	 * @throws IOException
	 */
	public void captureAWBDetails(String origin, String destination, String pieces, String weight, String scc)
			throws AWTException, InterruptedException, IOException {
		enterValueInHHT("gahht_txt_origin;xpath", proppathhht, data(origin), "Origin", screenName);
		enterValueInHHT("gahht_txt_destination;xpath", proppathhht, data(destination), "Destination", screenName);
		enterValueInHHT("gahht_txt_pieces;xpath", proppathhht, data(pieces), "Pieces", screenName);
		enterValueInHHT("gahht_txt_weight;xpath", proppathhht, data(weight), "Weight", screenName);
		enterValueInHHT("gahht_txt_scc;xpath", proppathhht, data(scc), "SCC", screenName);
		clickActionInHHT("gahht_txt_captureAWBSave;xpath", proppathhht, "Save", screenName);
		waitForSync(8);

	}

	/**
	 * @Description : Capture Big Reference number
	 * @author A-10690
	 * @param Bigreferenceno
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterBigReferenceNumber(String value) throws AWTException, InterruptedException, IOException {

		enterValueInTextbox(sheetName, "inbx_BigRefNo;name", data(value), "Big reference number", screenName);
		waitForSync(2);

	}

	/**
	 * @Description : verify Big Reference number
	 * @author A-10690
	 * @param Bigreferenceno
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void verifyBigReferenceNumber(String value) throws AWTException, InterruptedException, IOException {
		String locator = xls_Read.getCellValue(sheetName, "inbx_BigRefNo;name");
		String actText = driver.findElement(By.name(locator)).getAttribute("value");

		if (actText.equals(data(value))) {
			System.out.println("Successfully  verified the updated bigreferenceno " + data(value));
			writeExtent("Pass", "Successfully  verified the updated bigreferenceno " + data(value));

		} else {
			System.out.println("updated bigreferenceno " + data(value) + "not verified");
			writeExtent("Fail", "updated bigreferenceno " + data(value) + "not verified");
		}

	}

	/**
	 * @author A-10330 Desc.. Provide CTM details
	 * @param carrierCode
	 * @param FlightDate
	 * @param Flightno
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void provideCTMdetails(String carrierCode, String flightNo, String FlightDate)
			throws InterruptedException, IOException {

		waitForSync(1);

		if (!driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_fromCarrier;xpath"))).isDisplayed()) {
			clickWebElement(sheetName, "btn_CtmDetails;xpath", "CTM Details", screenName);
		}

		waitForSync(2);
		enterValueInTextbox(sheetName, "enterfrm_carrier;xpath", data(carrierCode), "From Carrier", screenName);
		enterValueInTextbox(sheetName, "inbx_CTMflightNo;id", data(flightNo), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_CTMflightDate;id", data(FlightDate), "Flight Date", screenName);

	}

	/**
	 * Description... Save Acceptance With Block Exists
	 * 
	 * @param expblockmsg
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveAcceptanceWithBlockExists(String expblockmsg) throws InterruptedException, IOException {
		waitForSync(6);
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		waitForSync(2);

		switchToFrame("default");
		String expText = "is incompatible with";
		waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
				screenName, 20);
		String actText = getElementText(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "incompatible SCC warning",
				screenName);
		while (actText.contains(expText)) {
			clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);
			actText = getElementText(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "incompatible SCC warning",
					screenName);
		}

		String actText2 = getElementText(sheetName, "htmlDiv_blockedForScreeningMsg;xpath", "Confirmation msg",
				screenName);

		verifyScreenText(sheetName, expblockmsg, actText2, "Acceptance with screening", screenName);

		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);

	}

	/**
	 * @author A-10330 Description:checksheet capture for AVI
	 * @param chkSheetRequired
	 * @param date
	 * @param time
	 */
	public void captureChecksheetLiveanimals(boolean chkSheetRequired, String date, String time) {
		if(getLoggedInStation("OPR335").equals("AMS"))
		{

		boolean checkSheetExists = true;
		try {
			clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
			waitForSync(3);

			driver.switchTo().frame("popupContainerFrame");
			waitForSync(3);

			List<WebElement> questions = driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			if (questions.size() == 0) {
				checkSheetExists = false;
			}

			for (WebElement ele : questions) {
				Select select = new Select(ele);
				select.selectByVisibleText("Yes");
			}
			enterValueInTextbox(sheetName, "inbx_answerDate;name", date, "Date when animal was fed", screenName);
			enterValueInTextbox(sheetName, "inbx_answerDateTime;name", time, "Time when animal was fed", screenName);
			if (chkSheetRequired) {
				if (checkSheetExists) {
					writeExtent("Pass", "Check sheet details selected on " + screenName);
				}

				else {
					writeExtent("Fail", "No check sheet details configured on " + screenName);
				}
			}

			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
			waitForSync(3);

			if (chkSheetRequired) {
				if (checkSheetExists) {
					writeExtent("Pass", "Check sheet details saved on " + screenName);
				}
			}
		}

		catch (Exception e) {
			writeExtent("Fail", "Could not save check sheet details on " + screenName);
		}
		
		}
		else
			switchToFrame("default");

	}

	/**
	 * @author A-8783
	 * @param SCC
	 * @throws InterruptedException
	 */
	public void verifySCC(String SCC) throws InterruptedException {
		String actual = getElementText(sheetName, "txt_scc;xpath", "SCC", screenName);
		verifyScreenText(sheetName, data(SCC), actual, "SCC", screenName);
	}

	/**
	 * @author A-9844
	 * @Desc To enter the Obligatory answer of checksheet as YES/NO based on
	 *       questions
	 * @param chkSheetRequired
	 * @param answer
	 */
	public void captureCheckSheet(boolean chkSheetRequired, String answer) {
		if(getLoggedInStation("OPR335").equals("AMS"))
		{

		boolean checkSheetExists = true;
		try {
			clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
			driver.switchTo().frame("popupContainerFrame");
			waitForSync(3);

			List<WebElement> questions = driver.findElements(By.xpath("//p[@style='display:inline']"));
			if (questions.size() == 0) {
				checkSheetExists = false;
			}
			int i = 0;
			for (WebElement ele : questions) {
				System.out.println(ele.getText());
				if (ele.getText().contains(answer)) {
					selectValueInDropdownWthXpath("//select[@name='questionwithAnswer[" + i + "].templateAnswer']",
							"No", ele.getText(), "VisibleText");
					i++;
				} else {
					selectValueInDropdownWthXpath("//select[@name='questionwithAnswer[" + i + "].templateAnswer']",
							"Yes", ele.getText(), "VisibleText");
					i++;
				}

			}
			if (chkSheetRequired) {
				if (checkSheetExists) {
					writeExtent("Pass", "Check sheet details selected on " + screenName);
				}

				else {
					writeExtent("Fail", "No check sheet details configured on " + screenName);
				}
			}

			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
			waitForSync(3);

			if (chkSheetRequired) {
				if (checkSheetExists) {
					writeExtent("Pass", "Check sheet details saved on " + screenName);
				}
			}
		} catch (Exception e) {
			writeExtent("Fail", "Could not save check sheet details on " + screenName);
		}
		}
		
		else
			switchToFrame("default");
	}

	/**
	 * @author A-9847 To select non-obligatory answers as "NO" for specific
	 *         question given
	 * @param chkSheetRequired
	 * @param answers
	 * @throws Exception
	 */
	public void captureChecksheetWithMultiFormat(boolean chkSheetRequired, String answers[]) throws Exception {
		if(getLoggedInStation("OPR335").equals("AMS")||getLoggedInStation("OPR335").equals("CDG"))
		{

		

		boolean checkSheetExists = true;
		String startDate = createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
		try {

			clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
			waitForSync(3);

			driver.switchTo().frame("popupContainerFrame");
			waitForSync(3);

			List<WebElement> questions = driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			List<WebElement> questions2 = driver.findElements(By.xpath("//input[@title='Date']"));
			List<WebElement> questions3 = driver.findElements(By.xpath("//input[@title='Time']"));
			List<WebElement> questions4 = driver.findElements(By.xpath("//textarea[@class='iCargoTextAreaMedium']"));
			List<WebElement> questions5 = driver.findElements(By.xpath("//button[contains(@id,'CMP_Checksheet') and @class='ui-multiselect ui-widget ui-state-default ui-corner-all']"));

			List<WebElement> quests = driver.findElements(By.xpath("//p[@style='display:inline']"));

			if (questions.size() == 0 && questions2.size() == 0 && questions3.size() == 0 && questions4.size() == 0 && questions5.size() == 0) {
				checkSheetExists = false;
			}

			/** Date Fields **/
			for (WebElement ele : questions2)
			{
				ele.sendKeys(startDate);
			}

			/** Time Fields **/
			for (WebElement ele : questions3)
			{
				ele.sendKeys("00:00");
			}

		     /** TextAreas **/
			for (WebElement ele : questions4)
			{
				ele.sendKeys("Yes");
				keyPress("TAB");
			}

		   
			
			/** Select first option from DropDowns other than Yes/No/NA **/
			for (WebElement ele : questions5) {
			moveScrollBar(ele);
			String selectedOrNot=ele.getText();

			if(selectedOrNot.equals("Select"))
			{

				//Opening the options dialog box
				ele.click();
				int i = questions5.indexOf(ele);

				//Selecting the first option from dialog box
				String dynamicXpath="(//input[contains(@id,'ui-multiselect-"+(i+1)+"-CMP_Checksheet_Defaults_CaptureCheckSheet')])[1]";
				driver.findElement(By.xpath(dynamicXpath)).click();
				//Closing the options dialog box
				driver.findElement(By.xpath(dynamicXpath+"/../../../..//a//span[@class='ui-icon ui-icon-circle-close']")).click();
			}
			}

			
			
			/** Yes/No DropDowns **/
			for (WebElement ele : questions)
			{		
				new Select(ele).selectByVisibleText("Yes");
				keyPress("TAB");
				waitForSync(2);
				/****  Handling Any Obligatory questions - No  ***/
				if(driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "warning_symbol;xpath"))).size()==1)
				{
					new Select(ele).selectByVisibleText("No");
					waitForSync(2);

				}
			}
			

			for (WebElement nonobg : quests) {
				for (int i = 0; i < answers.length; i++) {
					System.out.println(nonobg.getText());
					if (nonobg.getText().contains(answers[i]))
						selectValueInDropdownWthXpath(
								"//p[contains(text(),'" + nonobg.getText()
										+ "')]/../../following-sibling::div[1]//select",
										"No", nonobg.getText(), "VisibleText");
				}
			}

			if (chkSheetRequired) {
				if (checkSheetExists) {
					writeExtent("Pass", "Check sheet details selected on " + screenName);
				}

				else {
					writeExtent("Fail", "No check sheet details configured on " + screenName);
				}
			}
			waitForSync(2);
			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
			waitForSync(3);

			if (chkSheetRequired) {
				if (checkSheetExists) {
					writeExtent("Pass", "Check sheet details saved on " + screenName);
				}
			}
		}

		catch (Exception e) {
			writeExtent("Fail", "Could not save check sheet details on " + screenName);
		}
		}
		else
			switchToFrame("default");

	}


	

	/**
	 * @author A-6260 Desc--add awb details
	 * @param destination
	 * @param shipmentDesc
	 * @param stdPcs
	 * @param stdWt
	 * @param stdVol
	 * @param scc
	 * @param shipperCode
	 * @throws InterruptedException
	 */
	public void addAWBDetails(String destination, String shipmentDesc, String stdPcs, String stdWt, String stdVol,
			String scc, String shipperCode) throws InterruptedException {
		scc = "SCC";
		enterValueInTextbox(sheetName, "inbx_awbDestination;name", data(destination), "Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_shipmentDescription;name", data(shipmentDesc), "Shipment Description",
				screenName);
		enterValueInTextbox(sheetName, "inbx_statedPcs;name", data(stdPcs), "Stated Pcs", screenName);
		enterValueInTextbox(sheetName, "inbx_statedWt;name", data(stdWt), "Stated Wt", screenName);
		enterValueInTextbox(sheetName, "inbx_statedVol;name", data(stdVol), "Stated Vol", screenName);
		enterValueInTextbox(sheetName, "inbx_scc;name", data(scc), "SCC", screenName);

		enterValueInTextbox(sheetName, "txt_Shippercode;id", data(shipperCode), "shipperCode", screenName);
		performKeyActions(sheetName, "txt_Shippercode;id", "TAB", "Shipper Code", screenName);
	}

	/**
	 * @author A-6260 Desc..enter loose shipment details
	 * @param ShipmentAcceptanceLocation
	 * @param ShipmentPieces
	 * @param ShipmentWeight
	 * @throws Exception
	 */
	public void enterLooseShipmentDetails(String ShipmentAcceptanceLocation, String ShipmentPieces,
			String ShipmentWeight) throws Exception {

		String locator = xls_Read.getCellValue(sheetName, "inbx_LooseShipmentPcs;name");
		if (!driver.findElement(By.name(locator)).isDisplayed()) {
			clickWebElement(sheetName, "div_LooseAcceptance;xpath", "Loose acceptance tab open", screenName);

		}

		waitForSync(4);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentPcs;name", data(ShipmentPieces), "ShipmentPieces",
				screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentWt;name", data(ShipmentWeight), "ShipmentWeight", screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentLoc;name", data(ShipmentAcceptanceLocation),
				"ShipmentLocation", screenName);
		map.put("VPPWeight", data(ShipmentWeight));
		map.put("VPPVolume", getAttributeWebElement(sheetName, "inbx_volume;xpath", "Volume", "value", screenName));
		map.put("VPPType", "loose");

	}

	/**
	 * @author A-6260 Description..verify Checksheet alert and accept
	 * @param expText
	 * @throws InterruptedException
	 */
	public void verifyChecksheetAlertAndAccept(String expText) throws InterruptedException {

		switchToFrame("default");

		String alertText = getElementText(sheetName, "txt_alertChecksheet;xpath", "Check sheet alert text", screenName);
		if (alertText.contains("1. Acceptance check sheet not complete")) {
			System.out.println("Alert text is " + alertText + screenName + " Page");
			writeExtent("Pass", "Alert text is " + alertText + screenName + " Page");
		} else {
			System.out.println("Alert text is " + alertText + screenName + " Page");
			writeExtent("Fail", "Alert text is " + alertText + screenName + " Page");
		}

		handleAlert("Accept", screenName);

		waitForSync(3);
		switchToFrame("contentFrame", "OPR335");

	}

	/**
	 * 
	 * @param templates
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyCheckSheetTemplate(String[] templates) throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
		waitForSync(3);
		driver.switchTo().frame("popupContainerFrame");
		waitForSync(3);
		try {
			for (String t : templates) {
				String locator = xls_Read.getCellValue(sheetName, "txt_template;xpath");
				locator = locator.replace("*", t);
				if (driver.findElement(By.xpath(locator)).isDisplayed()) {
					onPassUpdate(screenName, "template " + t, "template " + t, "Verification of checksheet template",
							"Verification of checksheet template");
				} else {
					onFailUpdate(screenName, "template " + t, "template " + t, "Verification of checksheet template",
							"Verification of checksheet template");
				}
			}

			// Close button
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", driver.findElement(By.name("btnClose")));
			}

			catch (Exception e) {

			}

			waitForSync(2);
			switchToFrame("contentFrame", "OPR335");

		} catch (Exception e) {
			writeExtent("Fail", "Couldnt verify template name in " + screenName);
		}
	}

	/**
	 * @author A-6260 Desc.. Provide CTM details
	 * @param carrierCode
	 * @param FlightDate
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void provideCTMdetails(String carrierCode, String FlightDate) throws InterruptedException, IOException {

		waitForSync(1);

		if (!driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_fromCarrier;xpath"))).isDisplayed()) {
			clickWebElement(sheetName, "btn_CtmDetails;xpath", "CTM Details", screenName);
		}

		waitForSync(2);
		enterValueInTextbox(sheetName, "enterfrm_carrier;xpath", data(carrierCode), "From Carrier", screenName);
		enterValueInTextbox(sheetName, "inbx_CTMflightDate;id", data(FlightDate), "Flight Date", screenName);

	}

	/**
	 * @author A-6260 Desc: add awb details
	 * @param destination
	 * @param shipmentDesc
	 * @param stdPcs
	 * @param stdWt
	 * @param stdVol
	 * @param scc
	 * @throws InterruptedException
	 */
	public void addAWBDetails(String destination, String shipmentDesc, String stdPcs, String stdWt, String stdVol,
			String scc) throws InterruptedException {
		scc = "SCC";
		enterValueInTextbox(sheetName, "inbx_awbDestination;name", data(destination), "Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_shipmentDescription;name", data(shipmentDesc), "Shipment Description",
				screenName);
		enterValueInTextbox(sheetName, "inbx_statedPcs;name", data(stdPcs), "Stated Pcs", screenName);
		enterValueInTextbox(sheetName, "inbx_statedWt;name", data(stdWt), "Stated Wt", screenName);
		enterValueInTextbox(sheetName, "inbx_statedVol;name", data(stdVol), "Stated Vol", screenName);
		enterValueInTextbox(sheetName, "inbx_scc;name", data(scc), "SCC", screenName);
	}

	/**
	 * @Description : Adding Shipment Details
	 * @author A-9175
	 * @param pcs
	 * @param scc
	 * @param location
	 * @throws Exception
	 */
	public void addLooseShipmentDetails(String pcs, String scc, String location) throws Exception {
		Thread.sleep(3000);
		try {
			enterValueInTextbox(sheetName, "inbx_LooseShipmentPcs;name", pcs, "Shipment Pieces", screenName);
			waitForSync(3);
			performKeyActions(sheetName, "inbx_LooseShipmentPcs;name", "TAB", "Shipment Pieces", screenName);
			enterValueInTextbox(sheetName, "inbx_LooseShipmentLoc;name", location, "ShipmentLocation", screenName);
			waitForSync(3);

			map.put("VPPWeight", getAttributeWebElement(sheetName, "inbx_weight;xpath", "Weight", "value", screenName));
			map.put("VPPVolume", getAttributeWebElement(sheetName, "inbx_volume;xpath", "Volume", "value", screenName));
			map.put("VPPType", "loose");

			writeExtent("Pass", "Addition of Shipment Details Sucessfull");

		} catch (Exception e) {
			writeExtent("Fail", "Addition of shipment details Failed");
		}

	}
public static String checksheetpath= "\\src\\resources\\Checksheet.properties";
	
	/**@author A-9847
	 * @Desc To capture checksheet of Multiple formats
	 * @param chkSheetRequired
	 * @throws Exception
	 */
	public void captureChecksheetWithMultiFormats(boolean chkSheetRequired) throws Exception {
		boolean checkSheetExists = true;
		String startDate = createDateFormat("dd-MMM-YYYY", 0, "DAY", "");

		try {
			clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
			waitForSync(3);	
			driver.switchTo().frame("popupContainerFrame");		
			waitTillScreenloadWithOutAssertion("Generic_Elements", "btn_save;xpath", "CheckSheet Save Button", screenName);

			List<WebElement> questions = driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			List<WebElement> questions2 = driver.findElements(By.xpath("//input[@title='Date']"));
			List<WebElement> questions3 = driver.findElements(By.xpath("//input[@title='Time']"));
			List<WebElement> questions4 = driver.findElements(By.xpath("//textarea[@class='iCargoTextAreaMedium']"));
			List<WebElement> questions5 = driver.findElements(By.xpath("//button[contains(@id,'CMP_Checksheet') and @class='ui-multiselect ui-widget ui-state-default ui-corner-all']//span[2]"));


			if (questions.size() == 0 && questions2.size() == 0 && questions3.size() == 0 && questions4.size() == 0 && questions5.size() == 0)
				checkSheetExists = false;

			/** Date Fields **/
			for (WebElement ele : questions2)
			{
				ele.sendKeys(startDate);
			}

			/** Time Fields **/
			for (WebElement ele : questions3)
			{
				ele.sendKeys("00:00");
			}

			/** TextAreas **/
			for (WebElement ele : questions4)
			{
				ele.sendKeys("Yes");
				keyPress("TAB");
			}

			/** Select first option from DropDowns other than Yes/No/NA **/
			for (WebElement ele : questions5) {	
				moveScrollBar(ele);
				String selectedOrNot=ele.getText();

				if(selectedOrNot.equals("Select"))
				{

					//Opening the options dialog box
					ele.click();	
					int i = questions5.indexOf(ele);

					//Selecting the first option from dialog box
					String dynamicXpath="(//input[contains(@id,'ui-multiselect-"+(i+1)+"-CMP_Checksheet_Defaults_CaptureCheckSheet')])[1]";
					driver.findElement(By.xpath(dynamicXpath)).click();
					//Closing the options dialog box
					driver.findElement(By.xpath(dynamicXpath+"/../../../..//a//span[@class='ui-icon ui-icon-circle-close']")).click();		
				}
			}
			/** Yes/No DropDowns **/
			for (WebElement ele : questions)
			{		
				WebElement selectedOption = new Select(ele).getFirstSelectedOption();
				if(selectedOption.getText().equals(""))
				{
					moveScrollBar(ele);
					new Select(ele).selectByVisibleText("Yes");
					keyPress("TAB");
					waitForSync(2);



					/****  Handling Any Obligatory questions - No  ***/
					if(driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "warning_symbol;xpath"))).size()==1)
					{
						new Select(ele).selectByVisibleText("No");
						waitForSync(2);

					}
				}
			}

			/****  Handling Any Obligatory questions - No  ***/			
			/**One Way** String ObgQuest= WebFunctions.getPropertyValue(checksheetpath, "ObligatoryQuestions");
			List<WebElement> checkSheetQuestions = driver.findElements(By.xpath("//p[@style='display:inline']"));				
			for (WebElement ele : checkSheetQuestions) {
				int i=checkSheetQuestions.indexOf(ele);	
	if (ObgQuest.contains(ele.getText()))
					selectValueInDropdownWthXpath("(//p[@style='display:inline']/../../..//select)["+(i+1)+"]","No", ele.getText(), "VisibleText");		
			}
			 **************************************/


			if (chkSheetRequired) {
				if (checkSheetExists)
					writeExtent("Pass", "Check sheet details are Saved on " + screenName);
				else 
					writeExtent("Fail", "No check sheet details configured on " + screenName);	
			}


			String locator=xls_Read.getCellValue(sheetName, "btn_checkSheetOK;xpath");
			System.out.println(locator);

			WebElement element=driver.findElement(By.xpath(locator));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);

			waitForSync(3);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
			waitForSync(3);
		}

		catch (Exception e) {
			writeExtent("Fail", "Could not save check sheet details on " + screenName);
		}
	}
	
	/**
	 * Description : Click Shipper Return Button
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void clickShipperReturn() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_shipperReturn;xpath", "Click Shipper return button", screenName);
		waitForSync(2);
	}

	/**
	 * Description: Enter loose Shipment Details
	 * 
	 * @param ShipmentAcceptanceLocation
	 * @param ShipmentPieces
	 * @param ShipmentWeight
	 * @throws Exception
	 */
	public void looseShipmentDetails(String ShipmentAcceptanceLocation, String ShipmentPieces, String ShipmentWeight,
			boolean sccReq) throws Exception {
		Thread.sleep(3000);

		clickWebElement(sheetName, "div_LooseAcceptance;xpath", "Loose acceptance tab open", screenName);
		waitForSync(4);

		enterValueInTextbox(sheetName, "inbx_LooseShipmentPcs;name", data(ShipmentPieces), "ShipmentLocation",
				screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentWt;name", data(ShipmentWeight), "ShipmentWeight", screenName);

		enterValueInTextbox(sheetName, "inbx_LooseShipmentLoc;name", "01", "ShipmentLocation", screenName);

		/******************* Select SCC *****************/

		if (sccReq) {
			clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
			waitForSync(1);

			clickWebElement(sheetName, "chkBox_SCC;id", "Checkbox SCC", screenName);
			waitForSync(1);

			driver.findElement(By.xpath("(//span[contains(.,'Check all')])[2]")).click();
			waitForSync(1);
			clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
			waitForSync(1);
		}

	}

	/**
	 * @author A-6260 Description... Verify AWB details in the goods acceptance
	 *         screen
	 * @param Pieces
	 * @param Weight
	 * @param Volume
	 * @param CommodityCode
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyAWBDetails(String Pieces, String Weight, String Volume, String CommodityCode)
			throws InterruptedException, IOException {
		CommodityCode = "SCC";
		String locator = xls_Read.getCellValue(sheetName, "inbx_AWBDetails;xpath");
		String pieces = locator.replace("value", "1");
		String weight = locator.replace("value", "2");
		String volume = locator.replace("value", "3");
		waitTillScreenloadWithOutAssertion(sheetName, "btn_editIcon;xpath", "Edit Icon", screenName);
		/***************/
		if (!driver.findElement(By.xpath(pieces)).isDisplayed()) {
			clickWebElement(sheetName, "tab_AWBdetails;id", "AWB details", screenName);
		}
		/***************/
		waitForSync(3);
		String actText_pieces = driver.findElement(By.xpath(pieces)).getText();
		String actText_weight = driver.findElement(By.xpath(weight)).getText();
		String actText_volume = driver.findElement(By.xpath(volume)).getText();
		String CommodityCodeLocator = xls_Read.getCellValue(sheetName, "inbx_commodityDetails;xpath");
		String actText_commodityCode = driver.findElement(By.xpath(CommodityCodeLocator)).getText();

		comm.verifyScreenText(sheetName, data(Weight), actText_weight, actText_weight, screenName);
		comm.verifyScreenText(sheetName, data(Volume), actText_volume, actText_volume, screenName);
		comm.verifyScreenText(sheetName, data(Pieces), actText_pieces, actText_pieces, screenName);
		comm.verifyScreenText(sheetName, data(CommodityCode), actText_commodityCode, actText_commodityCode, screenName);

	}

	/**
	 * @author A-7271
	 * @param SCC
	 *            Desc : verify AWB details
	 */
	public void verifyAWBDetails(String SCC) {
		try {
			String sccLocator = xls_Read.getCellValue(sheetName, "inbx_commodityDetails;xpath");

			/***************/
			if (!driver.findElement(By.xpath(sccLocator)).isDisplayed()) {
				clickWebElement(sheetName, "tab_AWBdetails;id", "AWB details", screenName);
			}
			/***************/
			String sccCodeActText = driver.findElement(By.xpath(sccLocator)).getText();
			boolean sccExists = true;
			String[] arrSCCExp = new String[20];
			List<String> listSCC = new ArrayList<String>();

			// Storing the SCC retreived in arraylist

			for (int i = 0; i < sccCodeActText.split(",").length; i++) {
				listSCC.add(sccCodeActText.split(",")[i].trim());
			}

			// Storing expected values in array

			for (int i = 0; i < SCC.split(",").length; i++) {
				arrSCCExp[i] = SCC.split(",")[i].trim();
			}

			// Verifying if expected SCC contains in the actual SCC list

			for (int i = 0; i < SCC.split(",").length; i++) {
				if (!listSCC.contains(arrSCCExp[i])) {
					writeExtent("Fail", "SCC " + arrSCCExp[i] + " is missing in the SCC field on " + screenName);
					sccExists = false;
					break;
				}
			}
			if (sccExists)
				writeExtent("Pass", "SCC field matches on " + screenName);
			else
				writeExtent("Fail", "SCC field does not match on " + screenName);

		}

		catch (Exception e) {
			writeExtent("Fail", "SCC field does not match on " + screenName);
		}

	}

	/**
	 * @author A-6260 Description... Verify AWB details in the goods acceptance
	 *         screen
	 * @param Pieces
	 * @param Weight
	 * @param Volume
	 * @param CommodityCode
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyAWBDetails(String Pieces, String Weight, String Volume) throws InterruptedException, IOException {

		String locator = xls_Read.getCellValue(sheetName, "inbx_AWBDetails;xpath");
		String pieces = locator.replace("value", "1");
		String weight = locator.replace("value", "2");
		String volume = locator.replace("value", "3");
		waitTillScreenload(sheetName, "btn_editIcon;xpath", "Edit Icon", screenName);
		/***************/
		if (!driver.findElement(By.xpath(pieces)).isDisplayed()) {
			clickWebElement(sheetName, "tab_AWBdetails;id", "AWB details", screenName);
			waitForSync(3);
		}
		/***************/
		String actText_pieces = driver.findElement(By.xpath(pieces)).getText();
		String actText_weight = driver.findElement(By.xpath(weight)).getText();
		String actText_volume = driver.findElement(By.xpath(volume)).getText();

		// Verifications

		comm.verifyScreenText(sheetName, data(Weight), actText_weight, actText_weight, screenName);
		comm.verifyScreenText(sheetName, data(Volume), actText_volume, actText_volume, screenName);
		comm.verifyScreenText(sheetName, data(Pieces), actText_pieces, actText_pieces, screenName);

	}

	/**
	 * @author A-6260 Description.. Verify the certificate number in the TSA
	 *         information section
	 * @param certificateNumber
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyTsaInformation(String certificateNumber) throws InterruptedException, IOException {
		String expText = data(certificateNumber).split(",")[0];
		clickWebElement("GoodsAcceptance_OPR335", "btn_TSAinfo;xpath", "TSA info Button", screenName);
		waitForSync(3);
		String actText = driver.findElement(By.xpath("//input[@name='ccsf']")).getAttribute("value");
		if (actText.equals(expText)) {
			comm.verifyScreenText(sheetName, expText, actText, "CCSF number", screenName);

		} else {
			comm.verifyScreenText(sheetName, expText, actText, "CCSF number", screenName);

		}
		clickWebElement("GoodsAcceptance_OPR335", "btn_TSAinfo;xpath", "TSA info Button", screenName);
		waitForSync(3);
	}

	/**
	 * @author A-6260 Description: To add or update certificate number in the
	 *         TSA information section
	 * @param certificateNumber
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */
	public void provideTsaDetails(String certificateNumber) throws InterruptedException, IOException, AWTException {
		clickWebElement("GoodsAcceptance_OPR335", "btn_TSAinfo;xpath", "TSA info Button", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_CCSF;xpath", certificateNumber, "CCSF number", screenName);
		keyPress("TAB");
		waitForSync(3);
	}

	/**
	 * @author A-6260 Description: Save acceptance with invalid certificate
	 *         number
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveAcceptancewithInvalidCertificate() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
				screenName, 20);
		String expText = "CCSF Number specified is invalid. Do you want to continue?";
		String actText = getElementText(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "invalid certificate msg",
				screenName);
		verifyScreenText(sheetName, expText, actText, "Invalid certificate number", screenName);
		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);
		waitForSync(5);
		while (driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size() > 0) {
			clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);
			waitForSync(3);
		}
	}

	/**
	 * @author A-8783 Description... Add HAWB details
	 * @param HAWBnumber
	 * @param ShipmentPieces
	 * @param ShipmentWeight
	 * @throws Exception
	 */
	public void addHAWBdetailsGoodsAcceptance(String HAWBnumber, String ShipmentPieces, String ShipmentWeight)
			throws Exception {
		try {

			clickWebElement(sheetName, "btn_AddHAWBdetails;xpath", "HAWB Details icon", screenName);
			Thread.sleep(2000);
			switchToFrame("default");

			clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
			waitForSync(3);

			Thread.sleep(2000);
			driver.switchTo().frame("iCargoContentFrameOPR335");
			enterValueInTextbox(sheetName, "txt_HAWBnumber;id", data(HAWBnumber), "HAWB number", screenName);
			keyPress("TAB");
			enterValueInTextbox(sheetName, "txt_HAWBStatedPieces;xpath", data(ShipmentPieces), "Shipment pieces",
					screenName);
			keyPress("TAB");
			Thread.sleep(2000);
			enterValueInTextbox(sheetName, "txt_HAWBStatedWeight;xpath", data(ShipmentWeight), "shipment weight",
					screenName);
			keyPress("TAB");
			Thread.sleep(2000);
			clickWebElement(sheetName, "btn_HAWBadd;id", "Add HAWB ", screenName);
			waitForSync(3);
			clickWebElement(sheetName, "btn_closeHAWBpopUp;xpath", "close HAWB Details pop up", screenName);
			waitForSync(3);
		} catch (Exception e) {
			writeExtent("Fail", "Could not enter HAWB details on " + screenName);
		}

	}

	/**
	 * @author A-6260 Description: Verifying whether the certificate number is
	 *         valid or invalid
	 * @param certificate_valid_invalid
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void verifyCertificateNumber(String certificate_valid_invalid) throws InterruptedException, IOException {
		clickWebElement("GoodsAcceptance_OPR335", "btn_TSAinfo;xpath", "TSA info Button", screenName);
		waitForSync(3);
		switch (certificate_valid_invalid) {

		case "validCertificate":
			if (driver.findElement(By.xpath("//input[@name='ccsf']")).getAttribute("style").contains("red")) {
				writeExtent("Fail", "The certificate number is invalid");
			} else {
				writeExtent("Pass", "Verified Certificate number");
			}
			break;

		case "invalidCertificate":
			if (driver.findElement(By.xpath("//input[@name='ccsf']")).getAttribute("style").contains("red")) {
				writeExtent("Pass", "Verified Invalid Certificate number");
			} else {
				writeExtent("Fail", "Failed to verify invalid certificate number");
			}
			break;
		}
	}

	/**
	 * @author A-7271 Description : click dgr button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickDGRButton() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_dgrGoods;id", "DGR Goods Button", screenName);
		waitForSync(2);
	}

	/**
	 * Description :Capture DGR details
	 * 
	 * @param UNIDNo
	 * @param properShippingName
	 * @param netQuantityperPackage
	 * @param noOfPackage
	 * @param PerPackageUnit
	 * @param PI
	 * @param radioActive
	 * @throws Exception
	 */
	public void captureDGRDetails(String UNIDNo, String properShippingName, String netQuantityperPackage,
			String noOfPackage, String PerPackageUnit, String PI, boolean radioActive) throws Exception {
		waitForSync(3);
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR335");
		driver.switchTo().frame("popupContainerFrame");

		enterValueInTextbox("CaptureAWB_OPR026", "inbx_UNIDNumber;xpath", UNIDNo, "UNID No", screenName);

		performKeyActions("CaptureAWB_OPR026", "inbx_UNIDNumber;xpath", "TAB", "DGR Shipment", screenName);

		keyPress("TAB");
		keyRelease("TAB");

		selectValueInDropdownWithoutFail("CaptureAWB_OPR026", "lst_properShipName;xpath", properShippingName,
				"Proper Shipping Name", "Value");

		waitForSync(1);
		if (radioActive) {
			selectValueInDropdown("CaptureAWB_OPR026", "lst_RMC;id", "1", "RMC dropdown", "Index");
			enterValueInTextbox("CaptureAWB_OPR026", "inbx_TI;id", "1", "Transport Index", screenName);
		}
		if (!radioActive) {
			enterValueInTextbox("CaptureAWB_OPR026", "inbx_netQuantityPerPackage;xpath", netQuantityperPackage,
					"Net Quantity Per Package", screenName);
			selectValueInDropdown("CaptureAWB_OPR026", "lst_netQuantityPerPackageUnit;xpath", PerPackageUnit,
					"Net Quantity Per Package Unit", "VisibleText");
		}
		enterValueInTextbox("CaptureAWB_OPR026", "inbx_noOfPackages;xpath", noOfPackage, "No Of Package", screenName);

		enterValueInTextbox("CaptureAWB_OPR026", "inbx_PI;xpath", PI, "PI", screenName);

		clickWebElement("CaptureAWB_OPR026", "btn_add;xpath", "Add Button", screenName);

		waitForSync(6);
		
		/** Check the DG verified Checkbox **/
		checkDGVerifiedCheckbox();

		clickWebElement("CaptureAWB_OPR026", "btn_Dgrok;xpath", "Ok Button", screenName);

	}

	/**
	 * @author A-7271 Description : Capture check sheet
	 * @param chkSheetRequired
	 */
	public void captureChecksheet(boolean chkSheetRequired) {
		boolean checkSheetExists = true;
		try {

			clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
			waitForSync(3);

			driver.switchTo().frame("popupContainerFrame");
			waitForSync(3);

			List<WebElement> questions = driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			if (questions.size() == 0) {
				checkSheetExists = false;
			}

			for (WebElement ele : questions) {
				Select select = new Select(ele);
				select.selectByVisibleText("Yes");
			}
			if (chkSheetRequired) {
				if (checkSheetExists) {
					writeExtent("Pass", "Check sheet details selected on " + screenName);
				}

				else {
					writeExtent("Fail", "No check sheet details configured on " + screenName);
				}
			}

			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
			waitForSync(3);

			if (chkSheetRequired) {
				if (checkSheetExists) {
					writeExtent("Pass", "Check sheet details saved on " + screenName);
				}
			}
		}

		catch (Exception e) {
			writeExtent("Fail", "Could not save check sheet details on " + screenName);
		}
	}

	/**
	 * @author A-7271 Description : Capture check sheet with drop down and text
	 *         fields
	 * @param chkSheetRequired
	 * @throws Exception
	 */
	public void captureChecksheetWithMultiFormat(boolean chkSheetRequired) throws Exception {
		boolean checkSheetExists = true;
		String startDate = createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
		try {

			clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
			waitForSync(3);

			driver.switchTo().frame("popupContainerFrame");
			waitForSync(3);

			List<WebElement> questions = driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			List<WebElement> questions2 = driver.findElements(By.xpath("//input[@title='Date']"));
			List<WebElement> questions3 = driver.findElements(By.xpath("//input[@title='Time']"));
			List<WebElement> questions4 = driver.findElements(By.xpath("//textarea[@class='iCargoTextAreaMedium']"));

			if (questions.size() == 0 && questions2.size() == 0 && questions3.size() == 0 && questions4.size() == 0) {
				checkSheetExists = false;
			}

			questions2.parallelStream().forEach(ele -> ele.sendKeys(startDate));

			questions3.parallelStream().forEach(ele -> ele.sendKeys("00:00"));

			questions4.parallelStream().forEach(ele -> ele.sendKeys("Yes"));

			questions.parallelStream().forEach(ele -> new Select(ele).selectByVisibleText("Yes"));

			if (chkSheetRequired) {
				if (checkSheetExists) {
					writeExtent("Pass", "Check sheet details selected on " + screenName);
				}

				else {
					writeExtent("Fail", "No check sheet details configured on " + screenName);
				}
			}
			waitForSync(2);
			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
			waitForSync(3);

			if (chkSheetRequired) {
				if (checkSheetExists) {
					writeExtent("Pass", "Check sheet details saved on " + screenName);
				}
			}
		}

		catch (Exception e) {
			writeExtent("Fail", "Could not save check sheet details on " + screenName);
		}
	}

	/**
	 * @author A-8783
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectHAWB() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_AddHAWBdetails;xpath", "HAWB Details icon", screenName);
		clickWebElement(sheetName, "btn_hawbCheckAll;xpath", "Select All ", screenName);
		clickWebElement(sheetName, "btn_hawbOK;name", "HAWB OK", screenName);
		clickWebElement(sheetName, "btn_closeHAWBpopUp;xpath", "close HAWB Details pop up", screenName);
	}

	/**
	 * @author A-9478 Description... Add Storage Unit
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void addStorageUnit(String SU) throws InterruptedException, IOException {

		enterValueInTextbox(sheetName, "inbx_StorageUnit;id", data(SU), "Storage Unit", screenName);
	}

	/**
	 * Description : Capture check sheet in a generic way
	 * 
	 * @author A-9175
	 * @throws Exception
	 */

	public void checksheetCapture() throws Exception {

		switchToWindow("storeParent");

		waitForSync(3);
		try {
			if (driver.findElement(By.xpath("//i[@class='icon fa-tick']")).isDisplayed()) {
				System.out.println("No check sheet REQUIRED");
			}
		} catch (Exception e) {
			clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
			waitForSync(3);
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR335");
			driver.switchTo().frame("popupContainerFrame");
			String locator = xls_Read.getCellValue(sheetName, "btn_Yesbutton;xpath");
			List<WebElement> elements = driver.findElements(By.xpath(locator));
			for (WebElement elemnt : elements) {
				elemnt.click();
				waitForSync(3);
			}

			clickWebElement("GoodsAcceptance_OPR335", "btn_Save;name", "Ok Button", screenName);
			waitForSync(2);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn;xpath", "OK Button", screenName);
			Thread.sleep(2000);
			switchToFrame("contentFrame", "OPR335");
			driver.switchTo().frame("popupContainerFrame");
		}

		finally {
			waitForSync(2);

			switchToWindow("getParent");
			driver.switchTo().defaultContent();
			driver.switchTo().frame("iCargoContentFrameOPR335");
			waitForSync(5);
		}

	}

	/**
	 * Description: Verifies whether Storage capacity value is displayed or not
	 * 
	 * @param Location
	 * @param checkIsPresent
	 */
	public void verifyStorageCapacity(String Location, boolean checkIsPresent) {
		String locator = xls_Read.getCellValue(sheetName, "txt_StorageCapacityNumber;xpath");
		locator = locator.replace("SpecifySpecialNote", data(Location));
		String SU_number = driver.findElement(By.xpath(locator)).getText();
		if (checkIsPresent == true) {
			if (!(SU_number.equalsIgnoreCase(""))) {
				System.out.println("Storage Capacity:" + SU_number + " is stamped in " + screenName + " Page");
				writeExtent("Pass", "Storage Capacity:" + SU_number + " is stamped in " + screenName + " Page");
			} else {
				System.out.println("Storage Capacity is  not stamped in " + screenName + " Page");
				writeExtent("Fail", "Storage Capacity is  not stamped in " + screenName + " Page");
			}
		} else {
			if (SU_number.equalsIgnoreCase("")) {
				System.out.println("Storage Capacity is  not stamped in " + screenName + " Page");
				writeExtent("Pass", "Storage Capacity is  not stamped in " + screenName + " Page");
			} else {
				System.out.println("Storage Capacity:" + SU_number + " is stamped in " + screenName + " Page");
				writeExtent("Fail", "Storage Capacity:" + SU_number + " is stamped in " + screenName + " Page");
			}
		}

	}

	/**
	 * @author A-7271 Description..verify SU
	 * @param su
	 * @throws InterruptedException
	 */
	public void verifySU(String su) throws InterruptedException {
		getTextAndVerify(sheetName, "inbx_StorageUnit;id", "Storage unit", screenName, "Verification of storage unit",
				su, "equals");

	}

	/**
	 * Description... Verify Acceptance Finalized in the same frame
	 * 
	 * @param Acceptance_finalised_notfinalised
	 * @throws InterruptedException
	 */
	public void verifyAcceptanceFinalizedinSameFrame(String Acceptance_finalised_notfinalised)
			throws InterruptedException {

		switch (Acceptance_finalised_notfinalised) {

		case "finalised":
			String actText = driver.findElement(By.xpath("//label[contains(text(),'Acceptance finalised')]")).getText();

			System.out.println("Actual text is--" + actText);
			String expText = "Acceptance finalised";
			if (actText.equals(expText)) {
				System.out.println("Acceptance finalised");
				writeExtent("Pass", "Acceptance finalised");

			} else {
				System.out.println("Acceptance not finalised");
				writeExtent("Fail", "Acceptance not finalised");
			}
			break;

		case "not finalised":
			String actText1 = driver.findElement(By.xpath("//label[contains(text(),' Acceptance not finalised')]"))
					.getText();

			System.out.println("Actual text is--" + actText1);
			String expText1 = "Acceptance not finalised";
			if (actText1.equals(expText1)) {
				System.out.println("Acceptance not finalised");
				writeExtent("Pass", "Acceptance not finalised");

			} else {

				System.out.println("Acceptance finalised");
				writeExtent("Fail", "Acceptance finalised");
			}

		}

		Thread.sleep(2000);
	}
	/**@author A-10328
	* Description - Verify the splitted weight in Accepted shipment
	* @param scc
	* @param exptext
	*/

	public void verifySplitWeight(String[] scc,String [] exptext)
	{
		int j=0;
		for(int i=1;i<=scc.length;i++)
		{
			String locatorValue = xls_Read.getCellValue(sheetName, "table_weight;xpath");
			locatorValue = locatorValue.replace("sccval", scc[j]);
			String locator=locatorValue.replace("*", Integer.toString(i));
			String actText = driver.findElement(By.xpath(locator)).getAttribute("value");
			if(exptext[j].contains(actText))
			{
				writeExtent("Pass", "Sucessfully Verified weight for SCC : " + scc[j] + " as " + exptext[j] + " in Accepted shipment "
						+ screenName);
			}
			else
			{
				writeExtent("Fail", "Not Verified weight for SCC : " + scc[j] + " as " + exptext[j] + " in Accepted shipment "
						+ screenName);
			}

			j++;
		}
	}

	/**
	 * Description... Enter loose Shipment Details
	 * 
	 * @param ShipmentAcceptanceLocation
	 * @param ShipmentPieces
	 * @param ShipmentWeight
	 * @throws Exception
	 */
	public void looseAcceptanceDetails(String ShipmentAcceptanceLocation, String ShipmentPieces, String ShipmentWeight)
			throws Exception {
		Thread.sleep(3000);
		String locator = xls_Read.getCellValue(sheetName, "inbx_LooseShipmentPcs;name");

		if (!driver.findElement(By.name(locator)).isDisplayed()) {
			clickWebElement(sheetName, "div_LooseAcceptance;xpath", "Loose acceptance tab open", screenName);
			waitForSync(4);
		}
		enterValueInTextbox(sheetName, "inbx_LooseShipmentPcs;name", data(ShipmentPieces), "ShipmentLocation",
				screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentWt;name", data(ShipmentWeight), "ShipmentWeight", screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentLoc;name", data(ShipmentAcceptanceLocation),
				"ShipmentLocation", screenName);

		map.put("VPPWeight", data(ShipmentWeight));
		map.put("VPPVolume", getAttributeWebElement(sheetName, "inbx_volume;xpath", "Volume", "value", screenName));
		map.put("VPPType", "loose");

		/******************* Select SCC *****************/

		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
		waitForSync(1);
		clickWebElement(sheetName, "span_checkAllSCCs_2;xpath", "Check SCC", screenName);
		waitForSync(1);
		clickWebElement(sheetName, "span_closeCheckSCCs_2;xpath", "Button Close SCC", screenName);
		waitForSync(1);

	}
    /**
	 * Description: Selecting SCC codes * 
	 * @author A-10330
	 * @param SCC values
	 */
public void  selectSCC(String scc)
	
	{
		
	int i=1;
	try
	{
		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
		String locator =  xls_Read.getCellValue(sheetName,"selectSccvalue;xpath");
		waitForSync(1);
		locator=locator.replace("*", scc);

		List<WebElement> el= driver.findElements(By.xpath(locator));

		for(int j=0;j<el.size();j++)
		{

			if(driver.findElement(By.xpath(locator+"["+i+"]")).isDisplayed())
				break;


			if(!driver.findElement(By.xpath(locator+"["+i+"]")).isDisplayed())
			{
				i++;


			}


		}

		if(driver.findElement(By.xpath(locator+"["+i+"]")).isDisplayed())
		{
			writeExtent("Pass", "successfully verified the scc checkbox element"+scc+ "dispayed");


		}

		driver.findElement(By.xpath(locator+"["+i+"]")).click();

		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);


	}catch(Exception e)
	{
		writeExtent("Fail", "could not select the scc value"+scc+ "");
	}
	}
	/**
	 * @author A-9478 Description... Verify dimensions
	 * @param replacementValue
	 * @throws InterruptedException
	 */
	public void verifyDimensionDetails(String dimension) throws InterruptedException {
		String description = getElementText(sheetName, "txt_dimensionDetails;xpath", "Dimension Details",
				"GoodsAcceptance");
		if (dimension.split(",")[0].equals(description.split(",")[0].replace(" ", ""))
				&& dimension.split(",")[1].equals(description.split(",")[1].replace(" ", ""))
				&& (dimension.split(",")[2] + "(LBH)").equals(description.split(",")[2].replace(" ", ""))) {
			writeExtent("Pass", "Verified dimension details " + dimension + " in " + screenName);
		} else {
			writeExtent("Fail", "Failed to Verify dimension details " + dimension + " in " + screenName);
		}

	}

	/**
	 * @author A-9847
	 * @Desc To verify the Weight Check Status - Completed or Not Completed
	 * @param WeightCheck_completed_notCompleted
	 * @param switchToFrame
	 * @throws InterruptedException
	 */
	public void verifyWeightCheckStatus(String WeightCheck_completed_notCompleted, boolean switchToFrame)
			throws InterruptedException {

		if (switchToFrame) {

			switchToFrame("contentFrame", "OPR335");
		}

		try {

			switch (WeightCheck_completed_notCompleted) {

			case "completed":

				boolean answer = driver
						.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_weightcheckCompleted;xpath")))
						.isDisplayed();
				if (answer) {
					writeExtent("Pass", "Weight Check Completed");

				} else {
					writeExtent("Fail", "Weight Check Not Completed");
				}
				break;
			case "not completed":
				boolean status = driver
						.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_weightcheckNotCompleted;xpath")))
						.isDisplayed();
				if (status) {
					writeExtent("Pass", "Weight Check Not Completed");

				} else {
					writeExtent("Fail", "Weight Check Completed");
				}

			}

		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify the Weight Check Status");
		}
	}

	/**
	 * @author A-9847
	 * @Desc To verify the total dimension Pieces
	 * @param pcs
	 */
	public void verifyDimensionPieces(String pcs) {

		try {
			String actDimmedPcs = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "div_dimPcs;xpath")))
					.getText();
			System.out.println(actDimmedPcs);

			verifyScreenTextWithExactMatch(sheetName, data(pcs), actDimmedPcs, "Total Dimension Pieces", screenName);
		} catch (Exception e) {

			writeExtent("Fail", "Failed to verify the Dimension Pieces on " + screenName);
		}

	}

	/**
	 * @author A-9478 Description... Verify weight
	 * @param replacementValue
	 * @throws InterruptedException
	 */
	public void verifyWeight(String expectedWeight) throws InterruptedException {
		float f = Float.parseFloat(expectedWeight);
		String actualWeight = getElementText(sheetName, "txt_weightDetails;xpath", " Weight ", "GoodsAcceptance");
		if (Float.toString(f).equals(actualWeight)) {
			writeExtent("Pass", "Verified weight " + expectedWeight + " in " + screenName);
		} else {
			writeExtent("Fail", "Failed to Verify weight " + expectedWeight + " in " + screenName);
		}

	}

	/**
	 * @author A-7271 Description..select SCCs
	 * @param totalSCCs
	 * @param sccsToBeSelected
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectSCCs(int totalSCCs, int sccsToBeSelected) throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
		waitForSync(1);

		// Selecting the SCCs
		for (int i = 0; i < sccsToBeSelected; i++)

		{
			String locator = "ui-multiselect-" + totalSCCs
					+ "-CMP_Operations_Shipment_UX_GoodsAcceptance_SU_SCC-option-" + i;
			driver.findElement(By.id(locator)).click();
		}
		waitForSync(1);
		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
		waitForSync(1);

	}

	/**
	 * Description... Verify Save with Warning popUp
	 * 
	 * @throws Exception
	 */
	public void verifySavewithWarning() throws Exception {
		switchToFrame("default");
		Thread.sleep(2000);
		String expected = "Acceptance Finalisation restricted due to following reasons Flight loadability check failed do you want to continue?";

		String actual = getElementText(sheetName, "html_confirmSave;xpath", "Save with block", screenName);
		verifyScreenText(sheetName, expected, actual, "Save with restriction", screenName);

		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);
		switchToFrame("contentFrame", "OPR335");
	}

	/**
	 * Description..Click print
	 * 
	 * @throws Exception
	 */
	public void print() throws Exception {
		switchToWindow("storeParent");
		clickWebElementByWebDriver("GoodsAcceptance_OPR335", "btn_Print;xpath", "Print button",
				"GoodsAcceptance_OPR335");
		waitForSync(5);
		int windowSize = driver.getWindowHandles().size();

		try {
			if (windowSize > 1) {
				switchToWindow("child");
				driver.close();
				switchToWindow("getParent");
				switchToFrame("default");
				switchToFrame("contentFrame", "OPR335");
				onPassUpdate(screenName, "Print functionality in acceptance screen",
						"Print functionality is  working in acceptance screen", "Print window",
						"Verify print functionality");
			}

			else {
				captureScreenShot("Web");
				onFailUpdate(screenName, "Print functionality in acceptance screen",
						"Print functionality is not working in acceptance screen", "Print window",
						"Verify print functionality");
			}
		} catch (Exception e) {
			captureScreenShot("Web");
			onFailUpdate(screenName, "Print functionality in acceptance screen",
					"Print functionality is not working in acceptance screen", "Print window",
					"Verify print functionality");
			Assert.assertFalse(true, "Goods Acceptance print window is not opened");
		}

	}

	/**
	 * @author A-9847
	 * @Desc To check and add the missing SCC split Info after referring to the
	 *       info panel on OPR335
	 */

	public void compareAndAddtheMissingSplitInfo() {

		try {

			ArrayList<String> Expsccs = new ArrayList<String>();
			ArrayList<String> Actsccs = new ArrayList<String>();

			String locator = xls_Read.getCellValue(sheetName, "txt_splitSCCInfo;xpath");
			List<WebElement> Expscctext = driver.findElements(By.xpath(locator));
			for (int i = 0; i < Expscctext.size(); i++) {
				Expsccs.add(Expscctext.get(i).getText());
				System.out.println(Expscctext.get(i).getText());
			}

			String locator1 = xls_Read.getCellValue(sheetName, "txt_actSplitSCC;xpath");
			List<WebElement> Actscctext = driver.findElements(By.xpath(locator1));
			for (int i = 0; i < Actscctext.size(); i++) {
				Actsccs.add(Actscctext.get(i).getText());
				System.out.println(Actscctext.get(i).getText());
			}

			Expsccs.removeAll(Actsccs);
			System.out.println(Expsccs);

			String missingSCC = Expsccs.toString().replace("[", "").replace("]", "").trim();
			System.out.println(missingSCC);

			String missingSCCpcs = driver
					.findElement(By.xpath(
							xls_Read.getCellValue(sheetName, "txt_missingSCCPcs;xpath").replace("*", missingSCC)))
					.getText();
			System.out.println(missingSCCpcs);

			/**
			 * If the missing SCC is BUP, it will be added under the Uld
			 * Acceptance Section else under loose Acceptance Section
			 **/
			if (Expsccs.contains("BUP")) {
				clickWebElement(sheetName, "btn_Uldaccepatance;xpath", "ULDAcceptance", screenName);
				waitForSync(3);
				enterValueInTextbox(sheetName, "inb_uldshipmentPieces;xpath", missingSCCpcs, "Pieces", screenName);
				waitForSync(2);
				keyPress("TAB");
				enterValueInTextbox(sheetName, "inbx_uldacceptanceLoc;xpath", data("Location"), "Location", screenName);
				waitForSync(2);
				enterValueInTextbox(sheetName, "inbx_ULDnumber;xpath", data("UldNum"), "ULDNumber", screenName);
				waitForSync(3);
				keyPress("TAB");
				clickULDWarningPopUp();
				waitForSync(1);
				addULDDetails();
				waitForSync(2);
			}

			else

			{
				enterValueInTextbox(sheetName, "inbx_LooseShipmentPcs;name", missingSCCpcs, "ShipmentLocation",
						screenName);
				waitForSync(2);
				keyPress("TAB");
				enterValueInTextbox(sheetName, "inbx_LooseShipmentLoc;name", data("Location"), "ShipmentLocation",
						screenName);
				waitForSync(2);
				selectMissingSCC(missingSCC);
				addLooseShipment();
				waitForSync(2);
			}

			writeExtent("Pass", "Successfully Added the missed SCC Split Info with SCC as " + missingSCC
					+ " and Pieces as " + missingSCCpcs + " on " + screenName);

		}

		catch (Exception e) {
			writeExtent("Fail", "Failed to Compare and Add the Missing Split Scc Info on " + screenName);
		}
	}

	/**
	 * @author A-9847 Description... Verify loose accepted pieces
	 * @param replacementValue
	 * @throws InterruptedException
	 */
	public void verifyLooseAcceptanceDetails(String pcs, String wgt, String vol) {
		try {

			String accepteddetails = getAttributeWebElement(sheetName, "htmlDiv_looseAcceptanceDetails;xpath",
					"Loose Accepted Details", "innerText", screenName);
			String actpcs = accepteddetails.split("\\s+")[1].replaceAll("[^0-9]", "");
			String actwgt = accepteddetails.split("\\s+")[3].split("\\.")[0];
			String actvol = accepteddetails.split("\\s+")[4].split("\\.")[0];

			verifyScreenTextWithExactMatch(sheetName, data(pcs), actpcs, "AWB Accepted Pieces", screenName);
			verifyScreenTextWithExactMatch(sheetName, data(wgt), actwgt, "AWB Accepted Weight", screenName);
			verifyScreenTextWithExactMatch(sheetName, data(vol), actvol, "AWB Accepted Volume", screenName);
		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify the accepted AWB details on " + screenName);
		}
	}

	/**
	 * Description : Deleting selected shipment
	 * 
	 * @author A-10690
	 * @param scc
	 * @throws Exception
	 */
	public void deleteShipment(String scc) throws Exception {

		try {
			waitForSync(1);
			String locatorValue = xls_Read.getCellValue(sheetName, "lbl_deleteShipment;xpath");

			locatorValue = locatorValue.replace("SCCValue", scc);
			driver.findElement(By.xpath(locatorValue)).click();
			writeExtent("Pass", "Shipment Deleted sucessfully");

		} catch (Exception e) {
			writeExtent("Fail", "Deletion Failed");
		}
	}

	/**
	 * Description: Selecting the specific scc
	 * 
	 * @author A-10690
	 * @param sccsToBeSelected
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void selectMissingSCC(String scc) throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
		waitForSync(1);

		// Selecting the SCCs
		String locatorSCC = xls_Read.getCellValue(sheetName, "txt_missingscc;xpath");
		locatorSCC = locatorSCC.replace("missedscc", scc);
		driver.findElement(By.xpath(locatorSCC)).click();
		waitForSync(1);
		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
		waitForSync(1);

	}

	/**
	 * Description..verify Checksheet alert
	 * 
	 * @param expText
	 * @throws InterruptedException
	 */
	public void verifyChecksheetAlert(String expText) throws InterruptedException {

		switchToFrame("default");

		String alertText = getElementText(sheetName, "txt_alertChecksheet;xpath", "Check sheet alert text", screenName);
		if (alertText.contains("1. Acceptance check sheet not complete")) {
			System.out.println("Alert text is " + alertText + screenName + " Page");
			writeExtent("Pass", "Alert text is " + alertText + screenName + " Page");
		} else {
			System.out.println("Alert text is " + alertText + screenName + " Page");
			writeExtent("Fail", "Alert text is " + alertText + screenName + " Page");
		}

		handleAlert("Dismiss", screenName);

		waitForSync(3);
		switchToFrame("contentFrame", "OPR335");

	}

	/**
	 * Description... Capture Checksheet Goods Acceptance
	 * 
	 * @throws Exception
	 */
	public void captureChecksheet() throws Exception {

		switchToWindow("storeParent");

		waitForSync(3);
		try {

			if (driver.findElement(By.xpath("//i[@class='icon fa-tick']")).isDisplayed()) {

				System.out.println("No check sheet REQUIRED");

			}
		} catch (Exception e) {
			clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
			waitForSync(3);
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR335");
			driver.switchTo().frame("popupContainerFrame");

			selectValueInDropdown(sheetName, "lst_checksheetdropdown;xpath", "Y", "Manifest Checksheet dropdown",
					"Value");
			clickWebElement("GoodsAcceptance_OPR335", "btn_Save;name", "Ok Button", screenName);
			waitForSync(2);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn;xpath", "OK Button", screenName);
			Thread.sleep(2000);
			switchToFrame("contentFrame", "OPR335");
			driver.switchTo().frame("popupContainerFrame");
		}

		waitForSync(2);

		switchToWindow("getParent");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iCargoContentFrameOPR335");
	}

	/**
	 * Description... Click ULD Acceptance Tab
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickULDAccptTab() throws InterruptedException, IOException {
		clickWebElement("GoodsAcceptance_OPR335", "btn_Uldaccepatance;xpath", "ULDAcceptance", "GoodsAcceptance");
		waitForSync(5);
	}

	/**
	 * Description...Click Loose Loose acceptance tab open
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickLooseAccptTab() throws InterruptedException, IOException {
		clickWebElement("GoodsAcceptance_OPR335", "div_LooseAcceptance;xpath", "Loose acceptance tab open",
				"GoodsAcceptance");
		waitForSync(5);
	}

	/**
	 * Description... Verify Acceptance Status
	 * 
	 * @param status
	 * @throws InterruptedException
	 */
	public void verifyAcceptanceStatus(String status) throws InterruptedException {

		String actText = driver.findElement(By.xpath("(//*[@id='messagePane']//label)[2]")).getText();

		if (actText.contains(data(status))) {
			comm.verifyScreenText(sheetName, data(status), actText, actText, screenName);

		} else {
			comm.verifyScreenText(sheetName, data(status), actText, actText, screenName);
		}
		Thread.sleep(2000);
	}

	/**
	 * Description : Editing Shipment details
	 * 
	 * @author A-9175
	 * @param pcc
	 * @param scc
	 * @param location
	 * @throws Exception
	 */
	public void editShipmentLocation(String pcc, String scc, String location) throws Exception {

		try {
			String locatorValue = xls_Read.getCellValue(sheetName, "lbl_editLooseShipment;xpath");
			locatorValue = locatorValue.replace("SCCValue", scc);

			/*** EDITING THE SHIPMENT DETAILS ***/
			WebElement element = driver.findElement(By.xpath(locatorValue));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);

			waitForSync(3);
			enterValueInTextbox(sheetName, "inbx_LooseShipmentLoc;name", location, "ShipmentLocation", screenName);
			waitForSync(3);
			clickWebElement(sheetName, "btn_AddShipment;id", "Add Loose Shipment Button", screenName);
			waitForSync(3);
			writeExtent("Pass", "Updation of Shipment Details Sucessfull");

		} catch (Exception e) {
			writeExtent("Fail", "Updation of shipment details Failed");
		}
	}

	/**
	 * @author A-8783 Description..Verifying SU is not null
	 * @param scc
	 * @throws InterruptedException
	 */
	public void verifySUNotNull(String scc) throws InterruptedException {
		try {
			// Select the shipment
			String locatorValue = xls_Read.getCellValue(sheetName, "lbl_editLooseShipment;xpath");
			locatorValue = locatorValue.replace("SCCValue", scc);
			WebElement element = driver.findElement(By.xpath(locatorValue));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
			waitForSync(3);
			// verifying su null or not
			String storageUnit = getAttributeWebElement(sheetName, "inbx_StorageUnit;id", "Storage unit", "value",
					screenName);
			if (storageUnit != null) {
				writeExtent("Pass", "Found Value for SU :" + storageUnit);
			} else {
				writeExtent("Fail", "Found No Values for SU");
			}
		} catch (Exception e) {
			writeExtent("Fail", "Not able to select shipment");
		}
	}

	/**
	 * Description... check All Parts Received Checkbox
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void allPartsRecieved() throws InterruptedException, IOException {

		clickWebElement("GoodsAcceptance_OPR335", "chk_AllPartsRcvd;name", "AllParts recieved Checkbox",
				"GoodsAcceptance_OPR335");
	}

	/**
	 * Description... Save Acceptance With Block Exists
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveAcceptanceWithBlockExists() throws InterruptedException, IOException {
		Thread.sleep(6000);
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		Thread.sleep(2000);

		switchToFrame("default");

		// Need to add while loop here

		String expText = "Blocked for Screening";

		String actText = getElementText(sheetName, "htmlDiv_blockedForScreeningMsg;xpath", "Confirmation msg",
				screenName);

		verifyScreenText(sheetName, expText, actText, "Acceptance with screening", screenName);

		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);

	}

	/**
	 * @author A-9847
	 * @Desc To verify the AWB Origin and Destination
	 * @param org
	 * @param dest
	 */

	public void verifyAWBOriginDestination(String org, String dest) {

		try {

			String locator = xls_Read.getCellValue(sheetName, "inbx_AWBDetails;xpath");
			String pieces = locator.replace("value", "1");

			if (!driver.findElement(By.xpath(pieces)).isDisplayed()) {
				clickWebElement(sheetName, "tab_AWBdetails;id", "AWB details", screenName);
				waitForSync(3);
			}
			String actOrgDest = driver
					.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_OriginDestination;xpath"))).getText();
			System.out.println(actOrgDest);
			verifyScreenText(sheetName, data(org), actOrgDest.split(" ")[0], "AWB Origin", screenName);
			verifyScreenText(sheetName, data(dest), actOrgDest.split(" ")[1], "AWB Destination", screenName);

		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify AWB Origin and Destination on " + screenName);

		}

	}

	/**
	 * @author A-10328 Description- Enter volume
	 * @param ShipmentVolume
	 * @throws InterruptedException
	 */
	public void enterVolume(String ShipmentVolume) throws InterruptedException

	{

		enterValueInTextbox(sheetName, "inbx_volume;xpath", data(ShipmentVolume), "ShipmentVolume", screenName);
		waitForSync(1);

	}

	/**
	 * Description... Verify Acceptance Finalized
	 * 
	 * @param Acceptance_finalised_notfinalised
	 * @throws InterruptedException
	 */
	public void verifyautoAcceptanceRCT(String Acceptance_finalised_notfinalised) throws InterruptedException {

		switch (Acceptance_finalised_notfinalised) {

		case "finalised":
			String actText = driver.findElement(By.xpath("//label[contains(text(),'Acceptance finalised')]")).getText();

			System.out.println("Actual text is--" + actText);
			String expText = "Acceptance finalised";
			if (actText.equals(expText)) {
				System.out.println("Acceptance finalised");
				writeExtent("Pass", "Acceptance finalised");

			} else {
				System.out.println("Acceptance not finalised");
				writeExtent("Fail", "Acceptance not finalised");
			}
			break;

		case "not finalised":
			String actText1 = driver.findElement(By.xpath("//label[contains(text(),' Acceptance not finalised')]"))
					.getText();

			System.out.println("Actual text is--" + actText1);
			String expText1 = "Acceptance not finalised";
			if (actText1.equals(expText1)) {
				System.out.println("Acceptance not finalised");
				writeExtent("Pass", "Acceptance not finalised");

			} else {

				System.out.println("Acceptance finalised");
				writeExtent("Fail", "Acceptance finalised");
			}

		}

		Thread.sleep(2000);

	}

	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 *             Desc : getVPPFeed
	 */
	public void getVPPFeed() throws InterruptedException, IOException {
		/***** WEIGHT RECEPTION FROM VPP ****/
		String station = getLoggedInStation("OPR335");
		/****
		 * String checkWeight= getAttributeWebElement(sheetName,
		 * "inbx_suWgt;xpath", "Check Weight", "value", screenName);
		 ***/
		if (station.equals("CDG")) {
			/** if(checkWeight.equals("0.0")) ***/
			if (driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "lbl_weightcheckNotCompleted;xpath")))
					.size() == 1) {

				System.out.println(data("VPPAwb"));
				System.out.println(data("VPPWeight"));
				System.out.println(data("VPPVolume"));
				
				
				

				/******* POST REQUEST ****/
				if (data("VPPType").equals("loose"))
				{
					String suNumber=getSUNumber(1).split(data("VPPAwb"))[1]+data("VPPAwb");
					jsonbody.postRequest(data("VPPAwb"), data("VPPWeight"), data("VPPVolume"), "100", "100", "100",suNumber);
				}

				else

					jsonbody.postRequest(data("VPPAwb"), data("VPPWeight"), data("VPPVolume"), "100", "100", "100",
							data("VPPULDNumber"));

				clickWebElement(sheetName, "btn_editIcon;xpath", "Edit Icon", screenName);
				clickWebElement("Generic_Elements", "btn_List;xpath", "List Button", screenName);
				waitForSync(2);
			}
		}
	}

	/**
	 * A-7271 Desc : capture checksheet for CDGPHYCHCK
	 * @throws Exception 
	 */
	public void captureCheckSheetCDGPHYCHCK() throws Exception {
		String locator = xls_Read.getCellValue(sheetName, "lbl_pendingchecksheet;xpath");

		if (driver.findElements(By.xpath(locator)).size() == 1)
		{
		captureChecksheetWithMultiFormats(true);
		switchToFrame("contentFrame", "OPR335");
		waitForSync(1);
		}
	}

	/**
	 * Description... Save Acceptance
	 * @throws Exception 
	 */
	public void saveAcceptance() throws Exception {
		
		/*** To capture the DG Details and check the DG Goods Verified Checkbox as part of NEW Change for DGR SCCs **/
		CaptureDGVerificationInfo();
		/**************************************************/

		Thread.sleep(6000);
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		try {
			Thread.sleep(8000);
			switchToFrame("default");
			waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
					screenName, 20);
			while (driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).isDisplayed()) {
				String msgText = getElementText(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "warning",
						screenName);
				clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
				Thread.sleep(8000);
				if (!msgText.contains("successfully saved"))
					waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath",
							"Warning Pop-Up", screenName, 20);
			}

		} catch (Exception e) {
		}

		switchToFrame("contentFrame", "OPR335");

		/***** CAPTURE CHECKSHEET FOR SPX ****/

		/***
		 * if(getPropertyValue(proppath, "testEnv").equals("RCT")) {
		 * if(data("Origin").equals("IAD")||data("Origin").equals("BEG")||data("Origin").equals("WRO")||data("Origin").equals("DXB"))
		 * { captureChecksheet(true); switchToFrame("contentFrame", "OPR335");
		 * waitForSync(1); } }
		 ****/
		/**********************************************/

		/**** CAPTURE CHECKSHEET FOR CDGPHYCHCK ****/

		String station = getLoggedInStation("OPR335");
		if ((station.equals("CDG"))|(station.equals("AMS"))) {
			
				captureCheckSheetCDGPHYCHCK();

		}

		/**********************************************/

		/***** WEIGHT RECEPTION FROM VPP ****/

		getVPPFeed();

		/**********************************************/
		

		
		waitTillScreenload(sheetName, "btn_Save;name", "Acceptance Save Button", screenName);
		waitTillScreenload(sheetName, "btn_editIcon;xpath", "Edit Icon", screenName);
		verifyAcceptanceFinalized("finalised", false);

	}

	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 *             Desc : relist AWB and verify if acceptance status is
	 *             finalized
	 */
	public void relistAWBAndVerifyAcceptanceStatus() throws InterruptedException, IOException {
		waitTillScreenload(sheetName, "btn_editIcon;xpath", "Edit Icon", screenName);
		clickWebElement(sheetName, "btn_editIcon;xpath", "Edit Icon", screenName);
		clickWebElement("Generic_Elements", "btn_List;xpath", "List Button", screenName);
		waitForSync(2);

		/**********************************************/
		waitTillScreenload(sheetName, "btn_Save;name", "Acceptance Save Button", screenName);
		waitTillScreenload(sheetName, "txt_goodsAcceptance Status;xpath", "Acceptance status", screenName);
		String actText = driver.findElement(By.xpath(".//*[@id='messagePane']/label[2]")).getText();
		System.out.println("Actual text is--" + actText);
		String expText = "Acceptance finalised";
		if (actText.equals(expText)) {
			comm.verifyScreenText(sheetName, "Finalized", "Finalized", "AcceptanceFinalized", screenName);

		} else {
			comm.verifyScreenText(sheetName, "Acceptance Finalized", "Acceptance Not Finalized", "AcceptanceFinalized",
					screenName);
		}
		Thread.sleep(2000);
	}

	/**
	 * Description... Add Dimension Details
	 * 
	 * @param dimensions
	 * @param pcs
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void addDimensionDetails(String dimensions, String pcs) throws InterruptedException, IOException {
		clickWebElement(sheetName, "htmlDiv_dimension;xpath", "Dimension button", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_dimLength;xpath", data(dimensions).split(",")[0], "Dimension length",
				screenName);
		enterValueInTextbox(sheetName, "inbx_dimWidth;xpath", data(dimensions).split(",")[1], "Dimension width",
				screenName);
		enterValueInTextbox(sheetName, "inbx_dimHeight;xpath", data(dimensions).split(",")[2], "Dimension height",
				screenName);
		enterValueInTextbox(sheetName, "inbx_dimPcs;xpath", data(pcs), "Dimension pieces", screenName);
		clickWebElement(sheetName, "btn_dimAdd;xpath", "Add Button", screenName);
		waitForSync(2);
	}

	/**
	 * Description... Security And Screening
	 * 
	 * @param secSCC
	 * @throws Exception
	 */
	public void securityAndScreeing1(String secSCC) throws Exception {
		screenName = "Security and Screening Pop up";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		waitTillSpinnerDisappear();
		switchToFrame("default");
		Thread.sleep(4000);
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR026");
		driver.switchTo().frame("popupContainerFrame");

		clickWebElement(sheetName, "btn_shipDetails;xpath", "Shipment Details Panel", screenName);
		clickWebElement(sheetName, "btn_editSCC;xpath", "Edit SCC Button", screenName);

		Thread.sleep(2000);
		ele = driver.findElement(By.xpath("//input[@name='newScc']"));
		ele.click();
		Thread.sleep(1000);
		enterValueInTextbox(sheetName, "inbx_newSCC;xpath", data(secSCC), "SecSCC", screenName);
		clickWebElement(sheetName, "btn_updateSCCok;name", "OK Button ", screenName);
		Thread.sleep(2000);
		checkIfUnchecked(sheetName, "chk_dataRcvd;name", "Data Received Check Box", screenName);
		Thread.sleep(2000);
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);

	}

	/**
	 * Description... Save Acceptance
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveAcceptance1() throws InterruptedException, IOException {
		Thread.sleep(6000);
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		try {
			Thread.sleep(4000);
			switchToFrame("default");
			waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
					screenName, 20);
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			Thread.sleep(4000);
		} catch (Exception e) {
		}
		switchToFrame("contentFrame", "OPR335");

		String actText = driver
				.findElement(By.xpath("//*[@class='popover-icon message-short error m-t-5 pull-left block']"))
				.getText();

		System.out.println("Actual text is--" + actText);
		String expText = "Acceptance finalised";
		if (actText.equals(expText)) {
			comm.verifyScreenText(sheetName, "Acceptance finalised", "Acceptance finalised", "AcceptanceFinalized",
					screenName);

		} else {
			comm.verifyScreenText(sheetName, "Acceptance not finalised", "Acceptance not finalised",
					"Acceptancenotfinalised", screenName);
		}
		Thread.sleep(2000);
	}
	String grouping = "\\src\\resources\\Grouping.properties";
	String DG = "\\src\\resources\\DG.properties";
	
	
	/**
	 * @author A-9847
	 * @Desc To Capture DG Details Information for DG Goods
	 * @throws Exception
	 */
	public void CaptureDGVerificationInfo() throws Exception
	{

		String unid=getPropertyValue(DG, "Unid");
		String shippingName=getPropertyValue(DG, "ShippingName");
		String unit=getPropertyValue(DG, "PackageUnit");
		String pi=getPropertyValue(DG, "PI");
		String quantityPerPackage= Integer.toString(Integer.parseInt(data("VPPWeight"))/10);
		String testEnv=getPropertyValue(globalVarPath, "testEnv");

		By ele = getElement(sheetName, "btn_dgrGoods;id");
		String IsDGInfoAlreadyCaptured = driver.findElement(ele).getAttribute("style");

		if(IsDGInfoAlreadyCaptured.equals(""))
		{
			if(testEnv.equals("RC4") && verifyIsDGR())
			{			
				clickDGRButton();
				captureDGRDetails(unid,shippingName,quantityPerPackage,"10",unit, pi, false);
				switchToMainScreen("OPR335");

			}
		}
	}

	/**
	 * @author A-9847
	 * @Desc To verify if the SCCs present contains DG SCCs
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyIsDGR() throws InterruptedException{

		try{

			String sccLocator = xls_Read.getCellValue(sheetName, "inbx_commodityDetails;xpath");
			/***************/
			if (!driver.findElement(By.xpath(sccLocator)).isDisplayed()) {
				clickWebElement(sheetName, "tab_AWBdetails;id", "AWB details", screenName);
			
		    waitTillScreenloadWithOutAssertion(sheetName, "inbx_commodityDetails;xpath", "Commodity Details", screenName);
			}		

			/***************/

			//Getting SCCs from AWB Details Section of OPR335
			String sccCodeActText = driver.findElement(By.xpath(sccLocator)).getText();
			String dgrSCCs=getPropertyValue(grouping, "DGR");	
			for(int i=0;i<sccCodeActText.split(",").length;i++)
			{
				if(dgrSCCs.contains(sccCodeActText.split(",")[i]))
					return true;
			}
			return false;
		}
		catch (Exception e) {
			writeExtent("Fail", "Check the  SCC for the AWB");
			return false;
		}

	}




/**
	 * @author A-9847
	 * @Desc To check the DG Verified Checkbox
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void checkDGVerifiedCheckbox() throws InterruptedException, IOException{
		
		clickWebElement(sheetName, "chk_dgVerified;id", "DG Verified Checkbox", screenName);
		
	}

	/**
	 * Description... Verify Acceptance Finalized from OPR030 screen
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyAcceptanceFinalized1() throws InterruptedException, IOException {

		clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
		Thread.sleep(4000);

		driver.switchTo().frame("iCargoContentFrameOPR030");

		String actText = driver.findElement(By.xpath(".//*[@id='messagePane']/label[2]")).getText();

		System.out.println("Actual text is--" + actText);
		String expText = "Acceptance finalised";
		if (actText.equals(expText)) {
			comm.verifyScreenText(sheetName, "Finalized", "Finalized", "AcceptanceFinalized", screenName);

		} else {
			comm.verifyScreenText(sheetName, "Acceptance not finalised", "Acceptance not finalised",
					"Acceptancenotfinalised", screenName);
		}
		Thread.sleep(2000);
	}

	/**
	 * Description.. Click close in OPR335.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickClose() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_clickclose;xpath", "Close button", screenName);
	}

	/**
	 * @author A-9844 Description... handle the ULD warning popup message
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickULDWarningPopUp() throws InterruptedException, IOException {
		try {
			switchToFrame("default");
			String locator = xls_Read.getCellValue("Generic_Elements", "htmlDiv_msgStatus;xpath");
			String actText = driver.findElement(By.xpath(locator)).getText();
			if (actText.contains("doesnot exists in the system, Do you want to create it?")) {
				writeExtent("Info", "Sucessfully verified the text as: " + actText + " on " + screenName);
			} else {
				writeExtent("Fail",
						"Failed to verify the pop ip.Pop up is coming as: " + actText + " on " + screenName);
			}
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			switchToFrame("contentFrame", "OPR335");

		} catch (Exception e) {
			writeExtent("Info", "No popup is displayed " + " on " + screenName);
			switchToFrame("contentFrame", "OPR335");
		}
	}

	/**
	 * Description... Save Acceptance Without Add Sec Details
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveAcceptanceWithoutAddSecDetails() throws InterruptedException, IOException {
		Thread.sleep(6000);
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		try {
			Thread.sleep(4000);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			Thread.sleep(8000);
		} catch (Exception e) {
		}
		switchToFrame("contentFrame", "OPR335");

		String actText = driver.findElement(By.xpath("(//*[@id='messagePane']//label)[2]")).getText();

		System.out.println("Actual text is--" + actText);
		String expText = "Acceptance not finalised";
		if (actText.contains(expText)) {
			comm.verifyScreenText(sheetName, expText, actText, "Acceptance Not Finalized", screenName);

		} else {
			comm.verifyScreenText(sheetName, expText, actText, "Acceptance Finalized", screenName);
		}
		Thread.sleep(2000);
	}

	/**
	 * Description: Verifying the total number of shipments added in Loose
	 * Acceptance Tab
	 * 
	 * @author A-9175
	 * @param pcs
	 * @throws Exception
	 */
	public void verifyLooseShipmentDetails(String pcs[]) throws Exception {

		try {
			int addedShipmentCount = 0;
			for (int i = 0; i < pcs.length; i++) {
				String locatorValue = xls_Read.getCellValue(sheetName, "lbl_shipments;xpath");
				locatorValue = locatorValue.replace("index", Integer.toString(i));
				if (driver.findElement(By.xpath(locatorValue)).isDisplayed())
					addedShipmentCount++;
				waitForSync(3);

			}
			if (pcs.length == addedShipmentCount)
				writeExtent("Pass", "Total of " + addedShipmentCount + "Shipments is added in Loose Shipment Tab");
		} catch (Exception e) {
			writeExtent("Fail", "Failed to add  of shipments in Loose Shipment Tab");
		}
	}

	/**
	 * Description : Deleting selected shipment
	 * 
	 * @author A-9175
	 * @param scc
	 * @throws Exception
	 */
	public void deleteShipmentLocation(String scc) throws Exception {

		try {
			String locatorValue = xls_Read.getCellValue(sheetName, "lbl_deleteLooseShipment;xpath");
			locatorValue = locatorValue.replace("SCCValue", scc);
			driver.findElement(By.xpath(locatorValue)).click();
			writeExtent("Pass", "Shipment Deleted sucessfully");

		} catch (Exception e) {
			writeExtent("Fail", "Deletion Failed");
		}
	}

	/**
	 * Description : adding loose shipment details
	 * 
	 * @author A-9175
	 * @param pcs
	 * @param wgt
	 * @param loc
	 * @throws Exception
	 */
	public void addLooseShipment(String pcs, String wgt, String loc) throws Exception {

		waitForSync(4);

		enterValueInTextbox(sheetName, "inbx_LooseShipmentPcs;name", pcs, "ShipmentLocation", screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentWt;name", wgt, "ShipmentWeight", screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentLoc;name", loc, "ShipmentLocation", screenName);
		map.put("VPPWeight", wgt);
		map.put("VPPVolume", getAttributeWebElement(sheetName, "inbx_volume;xpath", "Volume", "value", screenName));
		map.put("VPPType", "loose");

	}

	/**
	 * Description: Selecting SCC codes
	 * 
	 * @author A-9175
	 * @param totalSCCs
	 * @param sccsToBeSelected
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void selectSCC(int totalSCCs, int sccsToBeSelected) throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
		waitForSync(1);

		// Selecting the SCCs
		String locator = "//label//input[@id='ui-multiselect-" + totalSCCs
				+ "-CMP_Operations_Shipment_UX_GoodsAcceptance_SU_SCC-option-" + sccsToBeSelected + "']";
		driver.findElement(By.xpath(locator)).click();

		waitForSync(1);
		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
		waitForSync(1);

	}

	/**
	 * Description... Verify Security And Screening Details
	 * 
	 * @param SecSCC
	 * @throws Exception
	 */
	public void verifySecurityAndScreeningDetails(String SecSCC) throws Exception {
		screenName = "Security and Screening Details";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_Secexpand;xpath", "Sec&Screening details", screenName);
		Thread.sleep(2000);

		switchToFrame("default");
		Thread.sleep(4000);
		driver.switchTo().frame("iCargoContentFrameOPR335");
		driver.switchTo().frame("if11");
		clickWebElement(sheetName, "btn_shipDetails;xpath", "Shipment Details Panel", screenName);
		String sccActual = getElementText("SecurityAndScreening_OPR339", "htmlDiv_shipmentDesc;xpath",
				"Shipment description", screenName);

		verifyScreenText("SecurityAndScreening_OPR339", data(SecSCC), sccActual, "Shipment Description", screenName);

		clickWebElement(sheetName, "btn_close;xpath", "Close button", screenName);
	}

	/**
	 * Description... Add Loose Shipment
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void addLooseShipment() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_AddShipment;name", "Add Loose Shipment Button", screenName);
		waitForSync(3);

	}

	public void storeSU(int rowVal,int colVal) throws IOException
	{
		try
		{
		int size=driver.findElements(By.xpath("//td[contains(.,'CDGWHS')]")).size();
		System.out.println(size);
		String su=driver.findElement(By.xpath("(//td[contains(.,'CDGWHS')])["+size+"]/preceding-sibling::td[1]")).getText();
		System.out.println(su);
		setCellValue(rowVal, colVal, su);
		
		}
		
		catch(Exception e)
		{
			
		}
	}
	/**
	 * @Description : Verifying Shipment Details
	 * @author A-9175
	 * @param scc
	 * @throws Exception
	 */
	public void verifySplitShipment(String scc) throws Exception {

		try {
			waitForSync(3);
			String locatorValue = xls_Read.getCellValue(sheetName, "lbl_editLooseShipment;xpath");
			locatorValue = locatorValue.replace("SCCValue", scc);

			/*** EDITING THE SHIPMENT DETAILS ***/
			WebElement element = driver.findElement(By.xpath(locatorValue));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
			waitForSync(3);
			writeExtent("Pass", "Sucessfully found Shipment Details for SCC : " + scc);

		} catch (Exception e) {
			writeExtent("Fail", "Not found Shipment Details for SCC : " + scc);
		}
	}

	/**
	 * Description.. Verify popup and handle
	 * 
	 * @param expected
	 * @param handlebtnname
	 * @throws Exception
	 */
	public void verifyPopUpAndHandle(String expected, String handlebtnname) throws Exception {
		switchToFrame("default");
		Thread.sleep(2000);
		try {
			String actual = driver
					.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "txt_AlertText;xpath"))).getText();
			verifyScreenText(sheetName, data(expected), actual, "Found alert Text is :" + actual, screenName);
			if (handlebtnname.equals("Yes"))
				clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);
			else
				clickWebElement("Generic_Elements", "btn_no;xpath", "No button", screenName);

		} finally {
			switchToFrame("contentFrame", "OPR335");
		}
	}

	/**
	 * @author A-6260 Description... Add HAWB details
	 * @param HAWBnumber
	 * @param ShipmentPieces
	 * @param ShipmentWeight
	 * @throws Exception
	 */
	public void addHAWBdetails(String HAWBnumber, String ShipmentPieces, String ShipmentWeight) throws Exception {
		try {

			clickWebElement(sheetName, "btn_AddHAWBdetails;xpath", "HAWB Details icon", screenName);
			Thread.sleep(2000);
			enterValueInTextbox(sheetName, "txt_HAWBnumber;id", data(HAWBnumber), "HAWB number", screenName);
			keyPress("TAB");
			enterValueInTextbox(sheetName, "txt_HAWBpieces;id", data(ShipmentPieces), "Shipment pieces", screenName);
			keyPress("TAB");
			enterValueInTextbox(sheetName, "txt_HAWBweight;id", data(ShipmentWeight), "shipment weight", screenName);
			clickWebElement(sheetName, "btn_HAWBadd;id", "Add HAWB ", screenName);
			waitForSync(3);
			clickWebElement(sheetName, "btn_closeHAWBpopUp;xpath", "close HAWB Details pop up", screenName);
			waitForSync(3);
		} catch (Exception e) {
			writeExtent("Fail", "Could not enter HAWB details on " + screenName);
		}

	}

	/**
	 * Description... Add ULD Details
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void addULDDetails() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_AddULDacceptance;name", "Add ULD Details Button", screenName);
	}

	/*
	 * public void uld1Details(String ULDNumber1, String Contour1, String
	 * Location, String Pieces, String Weight) throws Exception {
	 * Thread.sleep(3000);
	 * 
	 * 
	 * enterValueInTextbox(String sheetName, String locator, String value,
	 * String eleName, String ScreenName)
	 * 
	 * enterValueInTextbox(sheetName, "inbx_ULD1Name;xpath", ULDNumber1,
	 * "ULDNumber1", screenName);
	 * 
	 * enterValueInTextbox("GoodsAcceptance_OPR335", "lst_ULD1Contour;xpath",
	 * Contour1, "Contour1", "GoodsAcceptance_OPR335");
	 * 
	 * enterValueInTextbox(sheetName, "inbx_ULD1Location;xpath", Location,
	 * "Location", screenName); enterValueInTextbox(sheetName,
	 * "inbx_ULD1Pieces;xpath", Location, "Pieces", screenName);
	 * enterValueInTextbox(sheetName, "inbx_ULD1ShipmentWeight;xpath", Location,
	 * "Weight", screenName); }
	 */

	/**
	 * Description... Click Booking Details
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickBookingDetails() throws InterruptedException, IOException {

		clickWebElement(sheetName, "lnk_bkgDetails;xpath", "Booking Details Link", screenName);
	}
	/**
	 * @author A-9844 To select non-obligatory answers as "YES" for specific
	 *         question given
	 * @param chkSheetRequired
	 * @param answers
	 * @throws Exception
	 */
	public void captureChecksheetsWithMultiFormat(boolean chkSheetRequired, String answers[]) throws Exception {
		if(getLoggedInStation("OPR335").equals("AMS")||getLoggedInStation("OPR335").equals("CDG"))
		{



			boolean checkSheetExists = true;
			String startDate = createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			try {

				clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
				waitForSync(3);

				driver.switchTo().frame("popupContainerFrame");
				waitForSync(3);

				List<WebElement> questions = driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
				List<WebElement> questions2 = driver.findElements(By.xpath("//input[@title='Date']"));
				List<WebElement> questions3 = driver.findElements(By.xpath("//input[@title='Time']"));
				List<WebElement> questions4 = driver.findElements(By.xpath("//textarea[@class='iCargoTextAreaMedium']"));
				List<WebElement> questions5 = driver.findElements(By.xpath("//button[contains(@id,'CMP_Checksheet') and @class='ui-multiselect ui-widget ui-state-default ui-corner-all']"));

				List<WebElement> quests = driver.findElements(By.xpath("//p[@style='display:inline']"));

				if (questions.size() == 0 && questions2.size() == 0 && questions3.size() == 0 && questions4.size() == 0 && questions5.size() == 0) {
					checkSheetExists = false;
				}

				/** Date Fields **/
				for (WebElement ele : questions2)
				{
					ele.sendKeys(startDate);
				}

				/** Time Fields **/
				for (WebElement ele : questions3)
				{
					ele.sendKeys("00:00");
				}

				/** TextAreas **/
				for (WebElement ele : questions4)
				{
					ele.sendKeys("Yes");
					keyPress("TAB");
				}



				/** Select first option from DropDowns other than Yes/No/NA **/
				for (WebElement ele : questions5) {
					moveScrollBar(ele);
					String selectedOrNot=ele.getText();

					if(selectedOrNot.equals("Select"))
					{

						//Opening the options dialog box
						ele.click();
						int i = questions5.indexOf(ele);

						//Selecting the first option from dialog box
						String dynamicXpath="(//input[contains(@id,'ui-multiselect-"+(i+1)+"-CMP_Checksheet_Defaults_CaptureCheckSheet')])[1]";
						driver.findElement(By.xpath(dynamicXpath)).click();
						//Closing the options dialog box
						driver.findElement(By.xpath(dynamicXpath+"/../../../..//a//span[@class='ui-icon ui-icon-circle-close']")).click();
					}
				}



				/** Yes/No DropDowns **/
				for (WebElement ele : questions)
				{		
					new Select(ele).selectByVisibleText("Yes");
					keyPress("TAB");
					waitForSync(2);
					/****  Handling Any Obligatory questions - No  ***/
					if(driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "warning_symbol;xpath"))).size()==1)
					{
						new Select(ele).selectByVisibleText("No");
						waitForSync(2);

					}
				}


				for (WebElement nonobg : quests) {
					for (int i = 0; i < answers.length; i++) {
						System.out.println(nonobg.getText());
						if (nonobg.getText().contains(answers[i]))
							selectValueInDropdownWthXpath("//p[contains(text(),'" + nonobg.getText()+ "')]/../../following-sibling::div[1]//select","Yes", nonobg.getText(), "VisibleText");
						waitForSync(2);
					}
				}

				if (chkSheetRequired) {
					if (checkSheetExists) {
						writeExtent("Pass", "Check sheet details selected on " + screenName);
					}

					else {
						writeExtent("Fail", "No check sheet details configured on " + screenName);
					}
				}
				waitForSync(2);
				clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
				switchToFrame("default");
				clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
				waitForSync(3);

				if (chkSheetRequired) {
					if (checkSheetExists) {
						writeExtent("Pass", "Check sheet details saved on " + screenName);
					}
				}
			}

			catch (Exception e) {
				writeExtent("Fail", "Could not save check sheet details on " + screenName);
			}
		}
		else
			switchToFrame("default");

	}


	/**
	 * Description... Adding ULD Shipment Details
	 * 
	 * @param Pieces
	 * @param Weight
	 * @param Location
	 * @param ULDNumber
	 * @param Contour
	 * @throws Exception
	 */
	public void uldShipmentDetails(String Pieces, String Weight, String Location, String ULDNumber, String Contour)
			throws Exception {
		waitForSync(3);
		clickWebElement(sheetName, "btn_Uldaccepatance;xpath", "ULDAcceptance", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inb_uldshipmentPieces;xpath", data(Pieces), "Pieces", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_uldshipmentWeight;xpath", data(Weight), "Weight", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_uldacceptanceLoc;xpath", data(Location), "Location", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_ULDnumber;xpath", data(ULDNumber), "ULDNumber1", screenName);
		waitForSync(3);
		keyPress("TAB");
		clickULDWarningPopUp();
		waitForSync(1);

		map.put("VPPType", "uld");
		map.put("VPPULDNumber", data(ULDNumber));
		map.put("VPPWeight", data(Weight));
		/****
		 * map.put("VPPVolume", getAttributeWebElement(sheetName,
		 * "htmlDiv_uldAcceptanceDetails;xpath", "Volume", "innerText",
		 * screenName).split(" ")[2].split("CBM")[0]);
		 ****/
		map.put("VPPVolume",
				getAttributeWebElement(sheetName, "inbx_uldshipmentVolume;xpath", "Volume", "value", screenName));

	}

	/**
	 * Description... Security And Screening pop up in OPR335
	 * 
	 * @param SecSCC
	 * @throws Exception
	 */
	public void securityAndScreeingInOPR335(String SecSCC) throws Exception {
		screenName = "Security and Screening Details";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_Secexpand;xpath", "Sec&Screening details", screenName);
		Thread.sleep(2000);

		switchToFrame("default");
		Thread.sleep(4000);
		driver.switchTo().frame("iCargoContentFrameOPR335");
		driver.switchTo().frame("if11");
		clickWebElement(sheetName, "btn_shipDetails;xpath", "Shipment Details Panel", screenName);
		clickWebElement(sheetName, "btn_editSCC;xpath", "Edit SCC Button", screenName);
		Thread.sleep(2000);
		ele = driver.findElement(By.xpath("//input[@name='newScc']"));
		ele.click();
		Thread.sleep(1000);
		enterValueInTextbox(sheetName, "inbx_newSCC;xpath", data(SecSCC), "SecSCC", screenName);
		clickWebElement(sheetName, "btn_updateSCCok;name", "OK Button ", screenName);
		Thread.sleep(2000);
		Thread.sleep(2000);
		checkIfUnchecked(sheetName, "chk_dataRcvd;name", "Data Received Check Box", screenName);
		Thread.sleep(2000);
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR335");

	}

	/**
	 * Description... Save Acceptance where acceptance is finalised but RCS is
	 * not stamped
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveAcceptanceNotRCS() throws InterruptedException, IOException {
		Thread.sleep(6000);
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		try {
			Thread.sleep(4000);
			switchToFrame("default");
			waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
					screenName, 20);
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			Thread.sleep(4000);
		} catch (Exception e) {
		}
		switchToFrame("contentFrame", "OPR335");

		String actText = driver.findElement(By.xpath("//*[contains(text(),'Acceptance finalised')]")).getText();

		System.out.println("Actual text is--" + actText);
		String expText = "Acceptance finalised";
		if (actText.equals(expText)) {
			comm.verifyScreenText(sheetName, "Finalized", "Finalized", "AcceptanceFinalized", screenName);

		} else {
			comm.verifyScreenText(sheetName, "Acceptance Finalized", "Acceptance Not Finalized", "AcceptanceFinalized",
					screenName);
		}
		Thread.sleep(2000);
	}

	/**
	 * Description... Verification Of Not RFC Status
	 * 
	 * @throws Exception
	 */
	public void verificationOfNotRFCStatus() throws Exception {

		waitForSync(3);
		String actText="";
		
		try
		{
		 actText = driver.findElement(By.xpath("//*[contains(text(),'Not ready for carriage')]")).getText();
		System.out.println("Actual text is--" + actText);
		String expText = "Not ready for carriage";
		if (actText.equals(expText)) {
//			comm.verifyScreenText(sheetName, "Not ready for carriage", "Not ready for carriage",
//					"Not ready for carriage", screenName);
			
			writeExtent("Pass", "Successfully verified the 'Not Ready For Carriage' status on "+screenName);

		} else {
//			comm.verifyScreenText(sheetName, "Ready For Carriage", "Ready For Carriage", "Ready For Carriage",
//					screenName);
			
			writeExtent("Fail", "Failed to verify the 'Not Ready For Carriage' status on "+screenName);
		}
		}
		
		catch(Exception e)
		{
//			comm.verifyScreenText(sheetName, "Ready For Carriage", "Ready For Carriage", "Ready For Carriage",
//					screenName);
			
			writeExtent("Fail", "Failed to verify the 'Not Ready For Carriage' status on "+screenName);
		}

	}

	/**
	 * @author A-7271 Description: Verify not finalized reason
	 * @param reason
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyNotFinalizedReason(String reason) throws InterruptedException, IOException {
		clickWebElement(sheetName, "label_AcceptanceNotFinalized;xpath", "Acceptance Not Finalized Label", screenName);
		waitForSync(2);
		String notFinalizedReason = getElementText(sheetName, "table_ReasonsForNotFinalized;xpath",
				"Acceptance not finalized reason", screenName);
		comm.verifyScreenText(sheetName, data(reason), notFinalizedReason, "Reason for Acceptance not finalized",
				screenName);

	}

	/**
	 * Description: Click yes button
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickYesButton() throws InterruptedException, IOException {
		switchToFrame("default");
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR339");
	}

	/**
	 * Description... Check Temporary Storage Only checkbox is checked or not
	 * 
	 * @throws Exception
	 */
	public void verifyTemporaryStorageOnly(boolean temporaryStorage) throws Exception {
		String locator = xls_Read.getCellValue(sheetName, "chk_temporaryStorageOnly;name");
		if (temporaryStorage) {
			if (driver.findElement(By.name(locator)).isSelected()) {
				customFunction.onPassUpdate(screenName, "Temporary storage only checkbox",
						"Temporary storage only checkbox is checked", "Temporary Storage Only checbox",
						"Verify Temporary Storage Only checkbox");
			} else {
				customFunction.onFailUpdate(screenName, "Temporary storage only checkbox",
						"Temporary storage only checkbox is not checked", "Temporary Storage Only checbox",
						"Verify Temporary Storage Only checkbox");
			}
		} else {
			if (!driver.findElement(By.name(locator)).isSelected()) {
				customFunction.onPassUpdate(screenName, "Temporary storage only checkbox",
						"Temporary storage only checkbox is not checked", "Temporary Storage Only checbox",
						"Verify Temporary Storage Only checkbox");
			} else {
				customFunction.onFailUpdate(screenName, "Temporary storage only checkbox",
						"Temporary storage only checkbox is checked", "Temporary Storage Only checbox",
						"Verify Temporary Storage Only checkbox");
			}
		}

		Thread.sleep(2000);

	}

	/**
	 * @author A-7271 Description : Verify Title
	 */
	public void verifyTitle() {
		String title = getAttributeWebElement(sheetName, "", "OPR335Title", "title", screenName);
		System.out.println(title);

		if (title.equals("Goods Acceptance Screen")) {
			customFunction.onPassUpdate(screenName, "Title verification", "Title should be Goods Acceptance Screen",
					"Title is " + title, "Title verification");
		} else {
			customFunction.onFailUpdate(screenName, "Title verification", "Title should be Goods Acceptance Screen",
					"Title is " + title, "Title verification");
		}

	}

	/**
	 * Description... Verification Of RFC Status
	 * 
	 * @throws Exception
	 */
	public void verificationOfRFCStatus() throws Exception {

		waitForSync(3);
		String actText="";

		/**** ADDED AS PART OF SEALCHECK CHECKSHEET FOR SCC–SPX AT CDG ****/
		String station = getLoggedInStation("OPR335");
		if ((station.equals("CDG"))) {		
			captureCheckSheetCDGPHYCHCK();
		}

		try
		{
			actText = driver.findElement(By.xpath("//*[contains(text(),'Ready for carriage')]")).getText();
			System.out.println("Actual text is--" + actText);
			String expText = "Ready for carriage";
			if (actText.equals(expText)) {
//				comm.verifyScreenText(sheetName, "Ready for carriage", "Ready for carriage", "Ready for carriage",
//						screenName);
				
				writeExtent("Pass", "Successfully verified the 'Ready For Carriage' status on "+screenName);

			} else {
//				comm.verifyScreenText(sheetName, "Not Ready for carriage", "Not Ready for carriage",
//						"Not Ready for carriage", screenName);
				
				writeExtent("Fail", "Failed to verify the 'Ready For Carriage' status on "+screenName);
			}
		}

		catch(Exception e)
		{
//			comm.verifyScreenText(sheetName, "Not Ready for carriage", "Not Ready for carriage",
//					"Not Ready for carriage", screenName);
			
			writeExtent("Fail", "Failed to verify the 'Ready For Carriage' status on "+screenName);	
		}

	}

	public void verifySUSequence(int verfCols[], String actVerfValues[], String pmyKey)
			throws InterruptedException, IOException {
		String locator = xls_Read.getCellValue(sheetName, "tbl_Location;xpath");

		WebElement entry = driver.findElement(By.xpath(locator));
		moveScrollBar(entry);

		verify_tbl_records_multiple_cols(sheetName, "tbl_Sunumber;xpath", "//td", verfCols, pmyKey, actVerfValues);

	}

	/**
	 * @author A-7271
	 * @param pcs
	 * @throws Exception
	 *             Description : verify the accepted pcs in the dashboard
	 */
	public void checkAcceptedPcs(String pcs) throws Exception {

		clickWebElement(sheetName, "lnk_awbNumber;xpath", "AWB Number Link", screenName);
		waitForSync(6);

		switchToFrame("frameName", "popupContainerFrame");

		getTextAndVerify(sheetName, "span_acceptedPcs;xpath", "Accepted pcs", screenName,
				"Verification of accepted pcs", data(pcs) + " Pcs Accepted", "equals");

		switchToFrame("default");
		switchToFrame("contentFrame", "OPR335");
		clickWebElement(sheetName, "btn_closeDashboard;xpath", "Close button", screenName);
		waitForSync(1);

	}

	/**
	 * Description... Enter From Carrier Code
	 * 
	 * @param carrierCode
	 * @throws InterruptedException
	 */
	public void enterFromCarrierCode(String carrierCode) throws InterruptedException {

		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "enterfrm_carrier;xpath", data(carrierCode), "From Carrier", screenName);
		Thread.sleep(1000);

	}

	/**
	 * @author A-7271 Description : AWB capture damage
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCaptureAWBDamage() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_captureAWBDamage;name", "AWB Damage Capture", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Click Uld Acceptance
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickUldAcceptance() throws InterruptedException, IOException {
		waitForSync(3);
		clickWebElement(sheetName, "btn_Uldaccepatance;xpath", "ULDAcceptance", screenName);

	}

	/**
	 * Description... Enter Mul Uld Acceptance Details
	 * 
	 * @param Pieces
	 * @param Weight
	 * @param Location
	 * @param ULDNumber
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void enterMulUldAcceptanceDetails(String Pieces, String Weight, String Location, String ULDNumber)
			throws InterruptedException, AWTException, IOException {

		for (int i = 1; i <= 8; i++) {
			waitForSync(3);
			Random ran = new Random();
			int ranNum = ran.nextInt(10000);
			System.out.println(ranNum);
			enterValueInTextbox(sheetName, "inb_uldshipmentPieces;xpath", data(Pieces), "Pieces", screenName);
			waitForSync(3);
			enterValueInTextbox(sheetName, "inbx_uldshipmentWeight;xpath", data(Weight), "Weight", screenName);
			waitForSync(3);
			enterValueInTextbox(sheetName, "inbx_uldacceptanceLoc;xpath", data(Location), "Location", screenName);
			waitForSync(3);
			enterValueInTextbox(sheetName, "inbx_ULDnumber;xpath", data(ULDNumber) + ranNum + "LH", "ULDNumber" + i,
					screenName);
			keyPress("ENTER");
			keyRelease("ENTER");
			clickULDWarningPopUp();
			waitForSync(3);
			clickWebElement(sheetName, "btn_AddULDacceptance;name", "Add ULD Details Button", screenName);
		}

		map.put("VPPType", "uld");

	}

	/**
	 * Description... Delete Acceptance Information
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void deleteAcceptanceInformation() throws InterruptedException, IOException {
		waitForSync(3);
		clickWebElement(sheetName, "btn_delete;xpath", "Delete Button", screenName);

	}

	/**
	 * Description... As Is Execute After Capture
	 * 
	 * @throws Exception
	 */
	public void asIsExecuteAfterCapture() throws Exception {
		screenName = "Capture AWB";
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR335");
		switchToWindow("storeParent");

		clickWebElement("CaptureAWB_OPR026", "btn_AsIsExecute;xpath", "AsIsExecute Button", "CaptureAWB");
		waitForSync(20);
		switchToFrame("default");

		try {

			while (driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
				Thread.sleep(10000);
			}
		} catch (Exception e) {
		}

		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR335");
		Thread.sleep(10000);

	}

	/**
	 * Description... Verify Shipment Description
	 * 
	 * @param replacementValue
	 * @throws InterruptedException
	 */
	public void verifyShipmentDescription(String replacementValue) throws InterruptedException {
		String description = getElementText(sheetName, "txt_ShipmentDesc;xpath", "Shipment Description",
				"GoodsAcceptance");

		if (replacementValue.equals(description)) {
			writeExtent("Pass", "Verified Shipment Description is of 250 characters");
		}

	}

	/**
	 * Description... Compare LAT with Acceptance Time
	 * 
	 * @throws Exception
	 */
	public void compareLATwithAccptncTime() throws Exception {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		Date dateobj = new Date();
		System.out.println(sdf1.format(dateobj));

		String LAtdate = getElementText(sheetName, "txt_LATDate;xpath", "LAT Date", "GoodsAcceptance");
		String LATtime = getElementText(sheetName, "txt_LATtime;xpath", "LAT Date", "GoodsAcceptance");

		System.out.println(LAtdate);
		System.out.println(LATtime);

		String LAT = LAtdate.concat(" " + LATtime);

		System.out.println(LAT);
		System.out.println(LAT);

		if (sdf1.parse(sdf1.format(dateobj)).before(sdf1.parse(LAT)))
			writeExtent("Pass", "LAT is more than Acceptance Time");

		else
			writeExtent("Fail", "LAT is less than Acceptance Time");

	}

	/**
	 * Description... Security And Screening for Redirected Use
	 * 
	 * @param SecSCC
	 * @param parentContentFrame
	 * @throws Exception
	 */
	public void securityAndScreeingforRedirectedUse(String SecSCC, String parentContentFrame) throws Exception {
		screenName = "Security and Screening Pop up";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		waitTillSpinnerDisappear();
		switchToFrame("default");
		Thread.sleep(4000);
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		driver.switchTo().frame(parentContentFrame);
		driver.switchTo().frame("popupContainerFrame");

		clickWebElement(sheetName, "btn_shipDetails;xpath", "Shipment Details Panel", screenName);
		clickWebElement(sheetName, "btn_editSCC;xpath", "Edit SCC Button", screenName);
		Thread.sleep(2000);
		ele = driver.findElement(By.xpath("//input[@name='newScc']"));
		ele.click();
		Thread.sleep(1000);
		enterValueInTextbox(sheetName, "inbx_newSCC;xpath", data(SecSCC), "SecSCC", screenName);
		clickWebElement(sheetName, "btn_updateSCCok;name", "OK Button ", screenName);
		Thread.sleep(2000);
		checkIfUnchecked(sheetName, "chk_dataRcvd;name", "Data Received Check Box", screenName);
		Thread.sleep(2000);
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);

	}

	/**
	 * Description... Click Save Button
	 * @throws Exception 
	 */
	public void clickSave() throws Exception {

		/*** To capture the DG Details and check the DG Goods Verified Checkbox as part of NEW Change for DGR SCCs **/
		CaptureDGVerificationInfo();
		/**************************************************/
		
		Thread.sleep(6000);

	
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		try {
			Thread.sleep(4000);
			switchToFrame("default");
			String expText = "is incompatible with";
			waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
					screenName, 20);
			String actText = getElementText(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
					screenName);
			while (actText.contains(expText)) {
				clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);
				waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
						screenName, 20);
				actText = getElementText(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
						screenName);
			}

			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			Thread.sleep(4000);
			switchToFrame("contentFrame", "OPR335");
			
			/**** CAPTURE CHECKSHEET FOR CDGPHYCHCK ****/

			String station = getLoggedInStation("OPR335");
			if ((station.equals("CDG"))|(station.equals("AMS"))) {
			
					captureCheckSheetCDGPHYCHCK();

			}

			/**********************************************/

			/**** WEIGHT RECEPTION FROM VPP ****/
			getVPPFeed();

		} catch (Exception e) {
		}
	}

	/**
	 * Description... Click Save Button
	 * @throws Exception 
	 */
	public void clickSave(String screenId) throws Exception {

		/*** To capture the DG Details and check the DG Goods Verified Checkbox as part of NEW Change for DGR SCCs **/
		CaptureDGVerificationInfo();
		/**************************************************/
		
		Thread.sleep(6000);

	
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		try {
			Thread.sleep(4000);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			Thread.sleep(4000);
			switchToFrame("contentFrame", screenId);
		} catch (Exception e) {
		}
		
		/**** CAPTURE CHECKSHEET FOR CDGPHYCHCK ****/

		String station = getLoggedInStation("OPR335");
		if ((station.equals("CDG"))|(station.equals("AMS"))) {
		
				captureCheckSheetCDGPHYCHCK();

		}

		/**********************************************/
	}

	/**
	 * Description... Handle Shipment Violets LAT popup
	 * 
	 * @param globalVarPath
	 * @throws InterruptedException
	 */
	public void handleShipmentVioletsLATpopup(String globalVarPath) throws InterruptedException {

		switchToFrame("default");

		String alertText = getPropertyValue(globalVarPath, "AlertText");
		if (alertText.equals("Shipment violates LAT. Can shipment make the booked flight?")) {
			System.out.println("Alert text is " + alertText + screenName + " Page");
			writeExtent("Pass", "Alert text is " + alertText + screenName + " Page");
		} else {
			System.out.println("Alert text is " + alertText + screenName + " Page");
			writeExtent("Fail", "Alert text is " + alertText + screenName + " Page");
		}

		handleAlert("Dismiss", screenName);

		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR335");

	}

	/**
	 * Description... Handle Late Delivery By Customer Popup
	 * 
	 * @param globalVarPath
	 * @throws InterruptedException
	 */
	public void handleLateDeliveryByCustomerPopup(String globalVarPath) throws InterruptedException {

		switchToFrame("default");
		String dynXpath = "(" + xls_Read.getCellValue("Generic_Elements", "txt_AlertText;xpath") + ")[3]";
		String alertText = driver.findElement(By.xpath(dynXpath)).getText();
		System.out.println(alertText);
		if (alertText.contains("Late delivery by customer")) {
			System.out.println("Alert text is " + alertText + screenName + " Page");
			writeExtent("Pass", "Alert text is " + alertText + screenName + " Page");
		} else {
			System.out.println("Alert text is " + alertText + screenName + " Page");
			writeExtent("Fail", "Alert text is " + alertText + screenName + " Page");
		}

		handleAlert("Accept", screenName);

		Thread.sleep(2000);

	}

	/**
	 * @author A-9478 Description: Enter Big Ref No
	 * @param value
	 * @throws InterruptedException
	 */
	public void enterBigRefNoField(String value) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_BigRefNo;id", data(value), "Big Ref No", screenName);
		waitForSync(3);
	}

	/**
	 * @author A-9478 Description: Verify Big Ref No field
	 * @param value
	 * @throws InterruptedException
	 */
	public void verifyBigRefNoField() {
		try {
			String actualValue = getAttributeWebElement(sheetName, "inbx_BigRefNo;id", "Big Ref No", "value",
					screenName);
			if (actualValue.length() <= 25) {
				writeExtent("Pass", "Successfully verified length of Big Ref No field in " + screenName + " Page");
			} else {
				writeExtent("Fail", "Could not verify length of Big Ref No field in " + screenName + " Page");
			}

		} catch (Exception e) {
			writeExtent("Fail", "Could not verify length of Big Ref No field in " + screenName + " Page");
		}
	}

	/**
	 * @author A-9478 Description: Verify Big Ref No field
	 * @param value
	 * @throws InterruptedException
	 */
	public void verifyBigRefNoField(int actLength) {
		try {
			int expLength = 0;
			String actualValue = getAttributeWebElement(sheetName, "inbx_BigRefNo;id", "Big Ref No", "value",
					screenName);

			if (actLength > 25) {
				expLength = 25;
			} else {
				expLength = actLength;
			}

			if (actualValue.length() == expLength) {
				writeExtent("Pass",
						"big Ref No Field Length matches with the expected value . Expected value is " + expLength);
			} else {
				writeExtent("Fail",
						"big Ref No Field Length does not match with the expected value . Expected value is "
								+ expLength + " and " + "actual value is " + actualValue);
			}

		}

		catch (Exception e) {

		}
	}

	/**
	 * Description... Verify Weight and Volume
	 * 
	 * @param status
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyAWBDetails(String Weight, String Volume) throws InterruptedException, IOException {

		String locator = xls_Read.getCellValue(sheetName, "inbx_AWBDetails;xpath");
		String weight = locator.replace("value", "2");
		String volume = locator.replace("value", "3");
		/***************/
		if (!driver.findElement(By.xpath(weight)).isDisplayed()) {
			clickWebElement(sheetName, "tab_AWBdetails;id", "AWB details", screenName);
		}
		/***************/
		String actText_weight = driver.findElement(By.xpath(weight)).getText();
		String actText_volume = driver.findElement(By.xpath(volume)).getText();

		if (actText_weight.contains(data(Weight)) && actText_volume.contains(data(Volume))) {
			comm.verifyScreenText(sheetName, data(Weight), actText_weight, actText_weight, screenName);
			comm.verifyScreenText(sheetName, data(Volume), actText_volume, actText_volume, screenName);
		} else {
			comm.verifyScreenText(sheetName, data(Weight), actText_weight, actText_weight, screenName);
			comm.verifyScreenText(sheetName, data(Volume), actText_volume, actText_volume, screenName);
		}

	}

	/**
	 * @author A-9478 Description... Verify All Parts Received Checkbox
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyAllPartsReceived(boolean allPartsSelected) throws InterruptedException, IOException {
		try {
			String locator = xls_Read.getCellValue(sheetName, "chk_AllPartsRcvd;name");
			boolean result = driver.findElement(By.name(locator)).isSelected();
			if (allPartsSelected) {
				if (result) {
					writeExtent("Pass", "All Parts Received checkbox is checked in " + screenName);
				} else {
					writeExtent("Fail", "All Parts Received checkbox is not checked in " + screenName);
				}
			} else {
				if (!result) {
					writeExtent("Pass", "All Parts Received checkbox is not checked in " + screenName);
				} else {
					writeExtent("Fail", "All Parts Received checkbox is checked in " + screenName);
				}
			}
		} catch (Exception e) {
			writeExtent("Fail", "Couldn't verify All Parts Received checkbox in " + screenName);
		}
	}

	/**
	 * Description... Verify Acceptance Finalized
	 * 
	 * @param Acceptance_finalised_notfinalised
	 * @throws InterruptedException
	 */
	public void verifyAcceptanceFinalized(String Acceptance_finalised_notfinalised) throws InterruptedException {

		switchToFrame("contentFrame", "OPR335");

		switch (Acceptance_finalised_notfinalised) {

		case "finalised":
			
			String actText="";
			try
			{
			 actText = driver.findElement(By.xpath("//label[contains(text(),'Acceptance finalised')]")).getText();

			System.out.println("Actual text is--" + actText);
			String expText = "Acceptance finalised";
			if (actText.equals(expText)) {
				System.out.println("Acceptance finalised");
				writeExtent("Pass", "Acceptance finalised");

			} else {
				System.out.println("Acceptance not finalised");
				writeExtent("Fail", "Acceptance not finalised");
			}
			
			}
			
			catch(Exception e)
			{
				writeExtent("Fail", "Acceptance not finalised");
			}
			break;
		case "not finalised":
			
			String actText1="";

			try
			{
				actText1 = driver.findElement(By.xpath("//label[contains(text(),' Acceptance not finalised')]"))
						.getText();

				System.out.println("Actual text is--" + actText1);
				String expText1 = "Acceptance not finalised";
				if (actText1.equals(expText1)) {
					System.out.println("Acceptance not finalised");
					writeExtent("Pass", "Acceptance not finalised");

				} else {

					System.out.println("Acceptance finalised");
					writeExtent("Fail", "Acceptance finalised");
				}
			}

			catch(Exception e)
			{
				writeExtent("Fail", "Acceptance finalised");
			}

		}

		Thread.sleep(2000);

	}

	/**
	 * Description... Verify Acceptance Finalized
	 * 
	 * @param Acceptance_finalised_notfinalised
	 * @throws Exception 
	 */
	public void verifyAcceptanceFinalized(String Acceptance_finalised_notfinalised, boolean switchToFrame)
			throws Exception {

		if (switchToFrame) {

			switchToFrame("contentFrame", "OPR335");
		}

		switch (Acceptance_finalised_notfinalised) {

		case "finalised":

			/***** CAPTURE CHECKSHEET FOR SPX ****/

			/****
			 * if(getPropertyValue(proppath, "testEnv").equals("RCT")) {
			 * if(data("Origin").equals("IAD")||data("Origin").equals("BEG")||data("Origin").equals("WRO")||data("Transit").equals("IAD"))
			 * { captureChecksheet(true); switchToFrame("contentFrame",
			 * "OPR335"); waitForSync(1); } }
			 ****/

			/**** CAPTURE CHECKSHEET FOR CDGPHYCHCK ****/

			String station = getLoggedInStation("OPR335");
			if ((station.equals("CDG"))|(station.equals("AMS"))) {
				
					captureCheckSheetCDGPHYCHCK();

			}

			/**********************************************/

			/***** WEIGHT RECEPTION FROM VPP ****/
			getVPPFeed();
			/**********************************************/
			String actText="";
			try
			{

				actText = driver.findElement(By.xpath("//label[contains(text(),'Acceptance finalised')]")).getText();

				System.out.println("Actual text is--" + actText);
				String expText = "Acceptance finalised";
				if (actText.equals(expText)) {
					System.out.println("Acceptance finalised");
					writeExtent("Pass", "Acceptance finalised");

				} else {
					System.out.println("Acceptance not finalised");
					writeExtent("Fail", "Acceptance not finalised");
				}
				

			}

			catch(Exception e)
			{
				writeExtent("Fail", "Acceptance not finalised");
			}
			break;
		case "not finalised":
			waitTillScreenload(sheetName, "btn_Save;name", "Acceptance Save Button", screenName);

			String actText1="";
			try
			{
				actText1 = driver.findElement(By.xpath("//label[contains(text(),' Acceptance not finalised')]"))
						.getText();

				System.out.println("Actual text is--" + actText1);
				String expText1 = "Acceptance not finalised";
				if (actText1.equals(expText1)) {
					System.out.println("Acceptance not finalised");
					writeExtent("Pass", "Acceptance not finalised");

				} else {

					System.out.println("Acceptance finalised");
					writeExtent("Fail", "Acceptance finalised");
				}
			}

			catch(Exception e)
			{
				writeExtent("Fail", "Acceptance finalised");
			}

		}

		Thread.sleep(2000);

	}

	/**
	 * Description... List AWB
	 * 
	 * @param awbNo
	 * @param ShipmentPrefix
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void listAWB(String awbNo, String ShipmentPrefix) throws InterruptedException, IOException {

		String sheetName = "Generic_Elements";

		System.out.println("AWBnumber is ---" + awbNo);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath", data(ShipmentPrefix), "Shipment Prefix",
				screenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumber;xpath", awbNo, "AWB No", screenName);
		clickWebElement(sheetName, "btn_List;xpath", "List Button", screenName);
		map.put("VPPAwb", data(ShipmentPrefix) + awbNo);

		waitForSync(4);

	}

	/**
	 * Description... Click Capture AWB
	 * 
	 * @throws Exception
	 */
	public void clickCaptureAWB() throws Exception {

		clickWebElement(sheetName, "btn_CaptureAWB;id", "Capture AWB Button", screenName);
		Thread.sleep(2000);

	}

	/**
	 * @author A-9844
	 * @param SCC
	 *            Desc : verify SCC details are not present in the scc field
	 */
	public void verifySCCDetailsNotUpdated(String SCC) {
		try {
			String sccLocator = xls_Read.getCellValue(sheetName, "inbx_commodityDetails;xpath");

			/***************/
			if (!driver.findElement(By.xpath(sccLocator)).isDisplayed()) {
				clickWebElement(sheetName, "tab_AWBdetails;id", "AWB details", screenName);
			}
			/***************/
			String sccCodeActText = driver.findElement(By.xpath(sccLocator)).getText();
			boolean sccExists = true;
			String[] arrSCCExp = new String[20];
			List<String> listSCC = new ArrayList<String>();

			// Storing the SCC retreived in arraylist

			for (int i = 0; i < sccCodeActText.split(",").length; i++) {
				listSCC.add(sccCodeActText.split(",")[i].trim());
			}

			// Storing expected values in array

			for (int i = 0; i < SCC.split(",").length; i++) {
				arrSCCExp[i] = SCC.split(",")[i].trim();
			}

			// Verifying if expected SCC is not present in the actual SCC list

			for (int i = 0; i < SCC.split(",").length; i++) {
				if (!listSCC.contains(arrSCCExp[i])) {
					writeExtent("Pass", "SCC " + arrSCCExp[i] + " is not present in the SCC field on " + screenName);
					sccExists = false;
					break;
				}
			}
			if (sccExists)
				writeExtent("Fail", "SCC is updated in the field on " + screenName);
			else
				writeExtent("Pass", "SCC is not updated in the field on " + screenName);

		}

		catch (Exception e) {
			writeExtent("Fail", "SCC field does not match on " + screenName);
		}

	}

	/**
	 * Description... Click Temporary Storage Only
	 * 
	 * @throws Exception
	 */
	public void clickTemporaryStorageOnly() throws Exception {

		clickWebElement(sheetName, "chk_temporaryStorageOnly;name", "Temporary storage checkbox", screenName);
		Thread.sleep(2000);

	}

	/**
	 * Description... Click Transhipment
	 * 
	 * @throws Exception
	 */
	public void clickTranshipment() throws Exception {

		clickWebElement(sheetName, "chk_Transhipment;name", "Transhipment checkbox", screenName);
		Thread.sleep(2000);

	}

	/**
	 * Description... Verify Transhipment
	 * 
	 * @throws Exception
	 */

	public void verifyTranshipment() throws Exception {

		verifyElementDisplayed(sheetName, "img_TranshipmentTick;xpath", "Transhipment image verification ", screenName,
				"Transhipment image");

	}

	/**
	 * Description... Click Dimensions Tab
	 * 
	 * @throws Exception
	 */
	public void clickDimensionsTab() throws Exception {

		clickWebElement(sheetName, "div_Dimensions;xpath", "Dimensions tab", screenName);
		Thread.sleep(2000);

	}

	/**
	 * Description... Verify No Of Dimensions
	 * 
	 * @param no_of_dimensions
	 * @throws Exception
	 */
	public void verifyNoOfDimensions(String no_of_dimensions) throws Exception {

		String xpath = xls_Read.getCellValue(sheetName, "tbl_DimensionTable;xpath");

		List<WebElement> AllRows = driver.findElements(By.xpath(xpath));
		String Dynxpath = xls_Read.getCellValue(sheetName, "tbl_DimensionTable;xpath") + "[@class='result']";

		AllRows.remove(driver.findElement(By.xpath(Dynxpath)));
		String RowSize = Integer.toString(AllRows.size());

		// Comparing required no of rows to actual no of rows
		if (RowSize.equals(no_of_dimensions)) {

			onPassUpdate(screenName, no_of_dimensions, RowSize, " Goods acceptance of transhipment ",
					" Verification of no of dimensions");

		} else {

			onFailUpdate(screenName, no_of_dimensions, RowSize, " Goods acceptance of transhipment ",
					" Verification of no of dimensions");
		}
	}

	/**
	 * Description... Edit Loose Acceptance
	 * 
	 * @param ShipmentPieces
	 * @param ShipmentWeight
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void editLooseAcceptance(String ShipmentPieces, String ShipmentWeight)
			throws InterruptedException, IOException {

		clickWebElement(sheetName, "icon_editLooseShp;xpath", "Edit loose shipment icon", screenName);
		waitForSync(4);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentPcs;name", data(ShipmentPieces), "ShipmentLocation",
				screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentWt;name", data(ShipmentWeight), "ShipmentWeight", screenName);

	}

	/**
	 * Description... Add New Loose Acceptance
	 * 
	 * @param ShipmentAcceptanceLocation
	 * @param ShipmentPieces
	 * @param ShipmentWeight
	 * @throws InterruptedException
	 */
	public void addNewLooseAcceptance(String ShipmentAcceptanceLocation, String ShipmentPieces, String ShipmentWeight)
			throws InterruptedException {

		waitForSync(4);

		enterValueInTextbox(sheetName, "inbx_LooseShipmentPcs;name", data(ShipmentPieces), "ShipmentLocation",
				screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentWt;name", data(ShipmentWeight), "ShipmentWeight", screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentLoc;name", data(ShipmentAcceptanceLocation),
				"ShipmentLocation", screenName);
	}

	/**
	 * @author A-7271 Description : Add awb details
	 * @param destination
	 * @param shipmentDesc
	 * @param stdPcs
	 * @param stdWt
	 * @param scc
	 * @throws InterruptedException
	 */
	public void addAWBDetails(String destination, String shipmentDesc, String stdPcs, String stdWt, String scc)
			throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_awbDestination;name", data(destination), "Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_shipmentDescription;name", data(shipmentDesc), "Shipment Description",
				screenName);
		enterValueInTextbox(sheetName, "inbx_statedPcs;name", data(stdPcs), "Stated Pcs", screenName);
		enterValueInTextbox(sheetName, "inbx_statedWt;name", data(stdWt), "Stated Wt", screenName);
		enterValueInTextbox(sheetName, "inbx_scc;name", data(scc), "SCC", screenName);
	}

	/**
	 * Description... Edit ULD Acceptance
	 * 
	 * @param Pieces
	 * @param Weight
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */
	public void editULDAcceptance(String Pieces, String Weight) throws InterruptedException, IOException, AWTException {
		waitForSync(4);
		clickWebElement(sheetName, "icon_editULDShp;xpath", "Edit loose shipment icon", screenName);
		waitForSync(4);
		enterValueInTextbox(sheetName, "inb_uldshipmentPieces;xpath", data(Pieces), "Pieces", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_uldshipmentWeight;xpath", data(Weight), "Weight", screenName);
		waitForSync(3);

	}

	/**
	 * Description... Select Contour
	 * 
	 * @param contour
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void selectContour(String contour) throws InterruptedException, IOException, AWTException {
		waitForSync(2);
		/*********
		 * selectValueInDropdown(sheetName, "lst_contour;id", data(contour),
		 * "Contour", "VisibleText");
		 *********/
		selectValueInDropdown(sheetName, "lst_contour;id", "1", "Contour", "Index");
	}

	/**
	 * Description... Select Contour
	 * 
	 * @param contour
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void selectContourByText(String contour) throws InterruptedException, IOException, AWTException {
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_contour;id", data(contour), "Contour", "VisibleText");

	}

	/**
	 * Description... Edit AWB
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void editAWB() throws InterruptedException, IOException {
		clickWebElement(sheetName, "icon_editAWB;xpath", "Edit AWB icon", screenName);
		waitForSync(5);
	}

	/**
	 * Description... Handle Warehouse Checkout Popup:Function to enter number
	 * of pieces moved out of WHS.
	 * 
	 * @param parentFrameName
	 * @param locatorTextBox
	 * @param PcsOutofWHS
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void popUpWHS(String parentFrameName, String[] locatorTextBox, int[] PcsOutofWHS)
			throws InterruptedException, IOException {
		waitForSync(8);

		switchToFrame("default");
		driver.switchTo().frame(parentFrameName);
		driver.switchTo().frame("popupContainerFrame");
		String[] pcsMovedOutWHS = new String[50];

		for (int i = 0; i < PcsOutofWHS.length; i++) {
			System.out.println(pcsMovedOutWHS[i]);
			System.out.println(PcsOutofWHS[i]);
			pcsMovedOutWHS[i] = Integer.toString(PcsOutofWHS[i]);
		}

		for (int i = 0; i < locatorTextBox.length; i++) {

			enterValueInTextbox(sheetName, locatorTextBox[i], pcsMovedOutWHS[i], "Warehouse Checkout", screenName);

		}

		clickWebElement(sheetName, "btn_yesWhsCheckOut;id", "Yes Button WHS CheckOut", screenName);
		waitForSync(5);
	}

	/**
	 * Description... Click OK After Editing ULD Details
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickOKAfterEditingULDDetails() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_AddULDacceptance;id", "Add ULD Details Button", screenName);
	}

	/**
	 * Description... Click OK After Editing Loose SHPipper Details
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickOKAfterEditingLooseSHPDetails() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_AddShipment;id", "Add Loose Shipment Button", screenName);
	}

	/**
	 * Description... Provide dimension Details
	 * 
	 * @param FullFlightNo
	 * @param FlightDate
	 * @throws InterruptedException
	 */
	public void providedimensionDetails(String FullFlightNo, String FlightDate) throws InterruptedException {

		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_CTMcarrier;id", data(FullFlightNo).substring(0, 2), "Carrier Code",
				screenName);
		enterValueInTextbox(sheetName, "inbx_CTMflightNo;id", data(FullFlightNo).substring(2), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_CTMflightDate;id", data(FlightDate), "Flight Date", screenName);

	}

	/**
	 * Description... Click Checksheet Goods Acceptance
	 * 
	 * @throws Exception
	 */
	public void clickChecksheetGoodsAcceptance() throws Exception {

		switchToWindow("storeParent");

		waitForSync(3);
		try {

			if (driver.findElement(By.xpath("//i[@class='icon fa-tick']")).isDisplayed()) {

				System.out.println("No check sheet REQUIRED");

			}
		} catch (Exception e) {
			clickWebElement(sheetName, "btn_checkSheetGoodsAcceptance;xpath", "Checksheet", screenName);
			waitForSync(3);
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR335");
			driver.switchTo().frame("popupContainerFrame");

			selectValueInDropdown(sheetName, "lst_animalAcceptanceCheck;xpath", data("AnimalAcceptance"),
					"Data Capture Complete", "Value");
			selectValueInDropdown(sheetName, "lst_shippersCertificates;xpath", data("ShippersCertificate"),
					"DG Details Capture Complete", "Value");
			clickWebElement("GoodsAcceptance_OPR335", "btn_saveChecksheet;xpath", "Save Button", screenName);
			handleAlert("Accept", "OPR335");
			waitForSync(2);
			switchToFrame("contentFrame", "OPR335");
			driver.switchTo().frame("popupContainerFrame");

			driver.findElement(By.xpath("//button[@name='btnClose']")).click();
		}

		waitForSync(2);
		switchToWindow("getParent");

	}

	/**
	 * Description... Get Login Station
	 * 
	 * @return
	 */
	public String getLoginStation() {

		String expected = driver.findElement(By.xpath("//span[contains(.,'At:')]/b")).getText();
		return expected;

	}

	/**
	 * Description... Verify Security And Screening Details Without Close
	 * 
	 * @param SecSCC
	 * @throws Exception
	 */
	public void verifySecurityAndScreeningDetailsWithoutClose(String SecSCC) throws Exception {
		screenName = "Security and Screening Details";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_Secexpand;xpath", "Sec&Screening details", screenName);
		Thread.sleep(2000);

		switchToFrame("default");
		Thread.sleep(4000);
		driver.switchTo().frame("iCargoContentFrameOPR335");
		driver.switchTo().frame("if11");
		clickWebElement(sheetName, "btn_shipDetails;xpath", "Shipment Details Panel", screenName);
		String sccActual = getElementText("SecurityAndScreening_OPR339", "htmlDiv_shipmentDesc;xpath",
				"Shipment description", screenName);

		verifyScreenText("SecurityAndScreening_OPR339", data(SecSCC), sccActual, "Shipment Description", screenName);

	}

	/**
	 * Description... Verify SCC field Editable
	 * 
	 * @param SCIAdd
	 * @throws Exception
	 */
	public void verifySCCfieldEditable(String SCIAdd) throws Exception {
		clickWebElement(sheetName, "btn_edit;xpath", "Edit Button", screenName);
		Thread.sleep(4000);
		enterValueInTextbox(sheetName, "inbx_sci;xpath", data(SCIAdd), "New SCI", screenName);
		Thread.sleep(4000);
		clickWebElement(sheetName, "btn_addSCC;xpath", "OK Button", screenName);
		Thread.sleep(4000);

	}

	/**
	 * Description... Add Agent after expanding the security and screening pop
	 * up
	 * 
	 * @param agntType
	 * @param isoCC
	 * @param agentID
	 * @param expiry
	 * @throws Exception
	 */
	public void addAgent(String agntType, String isoCC, String agentID, String expiry) throws Exception {
		switchToFrame("default");

		driver.switchTo().frame("iCargoContentFrameOPR335");
		driver.switchTo().frame("if11");
		clickWebElement(sheetName, "btn_add;xpath", "AddAgent", screenName);
		Thread.sleep(4000);
		selectValueInDropdown(sheetName, "lst_agntType;xpath", data(agntType), "Agent Type", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_isoCC;xpath", data(isoCC), "ISO Country Code", screenName);
		enterValueInTextbox(sheetName, "inbx_agentID;xpath", data(agentID), "Agent ID", screenName);
		enterValueInTextbox(sheetName, "inbx_expiry;xpath", data(expiry), "Expiry", screenName);
		clickWebElement(sheetName, "btn_addagntInfo;xpath", "Add Agent", screenName);
	}

	/**
	 * Description... Verify Airport in screening same as logged in
	 * 
	 * @param expected
	 * @throws Exception
	 */
	public void verifyAirport(String expected) throws Exception {

		switchToFrame("default");
		switchToFrame("contentFrame", "OPR335");
		driver.switchTo().frame("if11");
		String actual = driver.findElement(By.xpath("//input[@id='airport']")).getAttribute("value");
		System.out.println("Actual text" + actual);

		if (actual.equals(expected)) {
			comm.verifyScreenText(sheetName, expected, actual, "Airport should be same as logged in station",
					screenName);
		} else {
			comm.verifyScreenText(sheetName, expected, actual, "Airport not same as logged in station", screenName);
		}
	}

	/**
	 * Description... Screening Details Expand AOM
	 * 
	 * @param methods
	 * @param aom
	 * @param StndPcs
	 * @param StndWt
	 * @param Result
	 * @throws Exception
	 */
	// after expanding the security and screening pop up
	public void screeningDetailsExpandAOM(String methods, String aom, String StndPcs, String StndWt, String Result)
			throws Exception {

		waitForSync(5);
		selectValueInDropdown(sheetName, "lst_method;xpath", data(methods), "Method of Screening", "VisibleText");
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_AOM;xpath", data(aom), "Pieces", screenName);
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_stndrdPcs;xpath", data(StndPcs), "Pieces", screenName);
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_stndrdWts;xpath", data(StndWt), "Weight", screenName);
		Thread.sleep(2000);
		selectValueInDropdown(sheetName, "lst_result;xpath", data(Result), "Result", "VisibleText");
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_screeningadd;xpath", "Add screening details", screenName);

	}

	/**
	 * Description... Screening Details Expand
	 * 
	 * @param methods
	 * @param StndPcs
	 * @param StndWt
	 * @param Result
	 * @throws Exception
	 */
	public void screeningDetailsExpand(String methods, String StndPcs, String StndWt, String Result) throws Exception {
		switchToFrame("default");
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR335");
		driver.switchTo().frame("if11");
		Thread.sleep(2000);
		waitForSync(5);
		selectValueInDropdown(sheetName, "lst_method;xpath", data(methods), "Method of Screening", "VisibleText");
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_stndrdPcs;xpath", data(StndPcs), "Pieces", screenName);
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_stndrdWts;xpath", data(StndWt), "Weight", screenName);
		Thread.sleep(2000);
		selectValueInDropdown(sheetName, "lst_result;xpath", data(Result), "Result", "VisibleText");
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_screeningadd;xpath", "Add screening details", screenName);
		waitForSync(4);

	}

	/**
	 * Description... Screening Remarks
	 * 
	 * @param remarks
	 * @throws Exception
	 */
	public void screeningRemarks(String remarks) throws Exception {
		switchToFrame("default");
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR335");
		driver.switchTo().frame("if11");
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_remarks;xpath", data(remarks), "Screening Remarks", screenName);

	}

	/**
	 * Description... Verify Save with Error popUp
	 * 
	 * @throws Exception
	 */
	public void verifySavewithErrorpopUp() throws Exception {
		switchToFrame("default");
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR335");
		driver.switchTo().frame("if11");
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_SSCsave;xpath", "Security and screening save", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		Thread.sleep(2000);
		String expected = "Do you want to release the block on the AWB?";

		String actual = getElementText(sheetName, "html_confirmSave;xpath", "Save with block", screenName);
		verifyScreenText(sheetName, expected, actual, "Save with block", screenName);

		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);
		switchToFrame("contentFrame", "OPR335");
	}

	/**
	 * Description... Verify Acceptance Finalized after Error
	 * 
	 * @param Acceptance_finalised_notfinalised
	 * @throws InterruptedException
	 */
	public void verifyAcceptanceFinalizedafterError(String Acceptance_finalised_notfinalised)
			throws InterruptedException {

		switchToFrame("contentFrame", "OPR335");

		switch (Acceptance_finalised_notfinalised) {

		case "finalised":
			String actText = driver.findElement(By.xpath(".//*[@id='messagePane']/label")).getText();

			System.out.println("Actual text is--" + actText);
			String expText = "Acceptance finalised";
			if (actText.equals(expText)) {
				System.out.println("Acceptance finalised");
				writeExtent("Pass", "Acceptance finalised");

			} else {
				System.out.println("Acceptance not finalised");
				writeExtent("Fail", "Acceptance not finalised");
			}

		case "not finalised":
			String actText1 = driver.findElement(By.xpath(".//*[@id='messagePane']/label")).getText();

			System.out.println("Actual text is--" + actText1);
			String expText1 = " Acceptance not finalised";
			if (actText1.equals(expText1)) {
				System.out.println("Acceptance not finalised");
				writeExtent("Pass", "Acceptance not finalised");

			} else {

				System.out.println("Acceptance finalised");
				writeExtent("Fail", "Acceptance finalised");
			}
		}

	}

	/**
	 * Description... Check SSC Boxes
	 * 
	 * @throws Exception
	 */
	public void checkSSCBoxes() throws Exception {
		clickWebElement(sheetName, "btn_Secexpand;xpath", "Sec&Screening details", screenName);
		Thread.sleep(2000);

		switchToFrame("default");
		Thread.sleep(4000);
		driver.switchTo().frame("iCargoContentFrameOPR335");
		driver.switchTo().frame("if11");
		checkIfUnchecked(sheetName, "chbx_securityreview;xpath", "Security data reviewed", screenName);
		Thread.sleep(4000);
		checkIfUnchecked(sheetName, "chbx_newSecuritystatus;xpath", "New security status given", screenName);

	}

	/**
	 * Description... Verify Agent
	 * 
	 * @throws Exception
	 */
	public void verifyAgent() throws Exception {
		switchToFrame("default");

		driver.switchTo().frame("iCargoContentFrameOPR335");
		driver.switchTo().frame("if11");
		clickWebElement(sheetName, "tab_agentDetails;xpath", "Agent Details", screenName);
		Thread.sleep(3000);

		String expected = "DE";
		String actual = driver.findElement(By.xpath("//td[contains(.,'Reg Agent Issuing')]/following-sibling::td[1]"))
				.getText();
		System.out.println("Actual" + actual);
		if (actual.equals(expected)) {
			comm.verifyScreenText(sheetName, expected, actual, "Agent code verified", screenName);
		} else {
			comm.verifyScreenText(sheetName, expected, actual, "Agent code verified", screenName);
		}

	}

	/**
	 * Description... Add New ULD Acceptance
	 * 
	 * @param Pieces
	 * @param Weight
	 * @param Location
	 * @param ULDNumber
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addNewULDAcceptance(String Pieces, String Weight, String Location, String ULDNumber)
			throws InterruptedException, AWTException, IOException {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inb_uldshipmentPieces;xpath", data(Pieces), "Pieces", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_uldshipmentWeight;xpath", data(Weight), "Weight", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_uldacceptanceLoc;xpath", data(Location), "Location", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_ULDnumber;xpath", data(ULDNumber), "ULDNumber1", screenName);
		waitForSync(3);
		keyPress("TAB");
		clickULDWarningPopUp();
		map.put("VPPType", "uld");
		map.put("VPPULDNumber", data(ULDNumber));
		map.put("VPPWeight", data(Weight));
		map.put("VPPVolume",
				getAttributeWebElement(sheetName, "inbx_uldshipmentVolume;xpath", "Volume", "value", screenName));
	}

	/**
	 * Description... Add Loose Shipment New
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void addLooseShipmentNew() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_AddShipment;id", "Add Loose Shipment Button", screenName);
	}

	/**
	 * Description... Add ULD Details New
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void addULDDetailsNew() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_AddULDacceptance;id", "Add ULD Details Button", screenName);
	}

	/**
	 * Description... Edit ULD Acceptance
	 * 
	 * @param Pieces
	 * @param Weight
	 * @param ULDNumber
	 * @param Contour
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void editULDAcceptance(String Pieces, String Weight, String ULDNumber, String Contour)
			throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_Uldaccepatance;xpath", "ULDAcceptance", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "icon_editULDShp;xpath", "Edit loose shipment icon", screenName);
		waitForSync(4);
		enterValueInTextbox(sheetName, "inb_uldshipmentPieces;xpath", data(Pieces), "Pieces", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_uldshipmentWeight;xpath", data(Weight), "Weight", screenName);
		waitForSync(3);

	}

	/**
	 * Description... Add New ULD Acceptance
	 * 
	 * @param Pieces
	 * @param Weight
	 * @param Location
	 * @param ULDNumber
	 * @param Contour
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void addNewULDAcceptance(String Pieces, String Weight, String Location, String ULDNumber, String Contour)
			throws InterruptedException, AWTException {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inb_uldshipmentPieces;xpath", data(Pieces), "Pieces", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_uldshipmentWeight;xpath", data(Weight), "Weight", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_uldacceptanceLoc;xpath", data(Location), "Location", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_ULDnumber;xpath", data(ULDNumber), "ULDNumber1", screenName);
		waitForSync(3);
		keyPress("TAB");
		waitForSync(1);
		map.put("VPPType", "uld");
		map.put("VPPULDNumber", data(ULDNumber));
		map.put("VPPWeight", data(Weight));
		map.put("VPPVolume",
				getAttributeWebElement(sheetName, "inbx_uldshipmentVolume;xpath", "Volume", "value", screenName));
	}

	/**
	 * Description... Click Save When Weight Not Match
	 * 
	 * @throws Exception
	 */
	public void clickSaveWhenWeightNotMatch() throws Exception {
		Thread.sleep(6000);
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		try {
			Thread.sleep(4000);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			Thread.sleep(4000);
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
		} catch (Exception e) {
		}

	}

	/**
	 * Description... Verify if the awb is new
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyIfAwbIsNew() throws InterruptedException, IOException {
		switchToFrame("default");
		boolean newAwb = verifyElementDisplayed("Generic_Elements", "btn_yes;xpath", "Verifying whether the awb is new",
				screenName, "yes Button");
		if (newAwb) {
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			waitForSync(2);
		}
		switchToFrame("contentFrame", "OPR335");
		waitForSync(1);

	}

	/**
	 * Description... Verify Acceptance Not Finalized
	 * 
	 * @param Acceptance_finalised_notfinalised
	 * @throws InterruptedException
	 */
	public void verifyAcceptanceNotFinalized(String Acceptance_finalised_notfinalised) throws InterruptedException {

		switch (Acceptance_finalised_notfinalised) {

		case "finalised":
			String actText = driver.findElement(By.xpath(".//*[@id='messagePane']/label[2]")).getText();

			System.out.println("Actual text is--" + actText);
			String expText = "Acceptance finalised";
			if (actText.equals(expText)) {
				System.out.println("Acceptance finalised");
				writeExtent("Pass", "Acceptance finalised");

			} else {
				System.out.println("Acceptance not finalised");
				writeExtent("Fail", "Acceptance not finalised");
			}

		case "not finalised":
			String actText1 = driver.findElement(By.xpath(".//*[@id='messagePane']/label")).getText();

			System.out.println("Actual text is--" + actText1);
			String expText1 = "Acceptance not finalised";
			if (actText1.contains(expText1)) {
				System.out.println("Acceptance not finalised");
				writeExtent("Pass", "Acceptance not finalised");

			} else {

				System.out.println("Acceptance finalised");
				writeExtent("Fail", "Acceptance finalised");
			}

		}

		Thread.sleep(2000);
	}

	/**
	 * Description... Click If Unchecked
	 * 
	 * @param sheetName
	 * @param locator
	 * @throws Exception
	 */
	public void clickIfUnchecked(String sheetName, String locator) throws Exception {
		try {
			if ((driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, locator))).isSelected()))
				driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, locator))).click();

		} catch (Exception e) {

			System.out.println("Not clicked on the object with locator " + locator + " in sheet " + sheetName);

		}
	}
	
	
	
	
	/*************** DATALOAD************/
	
	/**
	 * Description... List an AWB No on any Screen
	 * 
	 * @param awbNo
	 * @param ShipmentPrefix
	 * @param ScreenName
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void dataload_listAWB(String awbNo, String ScreenName) throws InterruptedException, IOException {

		String sheetName = "Generic_Elements";
		
		String locator = xls_Read.getCellValue(sheetName, "inbx_shipmentPrefix;xpath");
		
		
	try
	{
		if(driver.findElement(By.xpath(locator)).isDisplayed())
		{
		//waitForSync(2);
		waitTillScreenload(sheetName, "inbx_shipmentPrefix;xpath","Shipment Prefix",ScreenName);	
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath", awbNo.substring(0,3), "Shipment Prefix",ScreenName);
				
		enterValueInTextbox(sheetName, "inbx_AWBnumber;xpath", awbNo.substring(3), "AWB No", ScreenName);
		
		clickWebElement(sheetName, "btn_List;xpath", "List Button", ScreenName);
		waitTillScreenloadWithOutAssertion("GoodsAcceptance_OPR335", "btn_editIcon;xpath", "Edit Icon",screenName,10);
		}
		
	}
	
	catch(Exception e)
	{
		
	}
		


	}
	/**
	 * Description: Enter loose Shipment Details
	 * 
	 * @param ShipmentAcceptanceLocation
	 * @param ShipmentPieces
	 * @param ShipmentWeight
	 * @throws Exception
	 */
	public void dataload_looseShipmentDetails(String pieces, String weight, String volume,String location)
			throws Exception {
		//Thread.sleep(3000);

		String locator = xls_Read.getCellValue(sheetName, "inbx_LooseShipmentPcs;name");
		if (!driver.findElement(By.name(locator)).isDisplayed()) {
			clickWebElement(sheetName, "div_LooseAcceptance;xpath", "Loose acceptance tab open", screenName);

		}

		enterValueInTextbox(sheetName, "inbx_LooseShipmentPcs;name", pieces, "ShipmentLocation",
				screenName);
		 //keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_LooseShipmentWt;name", weight, "ShipmentWeight", screenName);
		enterValueInTextbox(sheetName, "inbx_looseAcceptanceVol;name", volume, "ShipmentVolume", screenName);
		enterValueInTextbox(sheetName, "inbx_LooseShipmentLoc;name", location,"ShipmentLocation", screenName);
			
		

		/******************* Select SCC *****************/
		keyPress("TAB");
		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", screenName);
		waitForSync(1);
		dataload_selectSCCs();
	/**	clickWebElement(sheetName, "span_checkAllSCCs;xpath", "Check SCC", screenName);
		waitForSync(1);
		clickWebElement(sheetName, "span_closeCheckSCCs;xpath", "Button Close SCC", screenName);
		waitForSync(1);***/

	}
	
	public void dataload_selectSCCs()
	{
		try
		{
			int checkSCCs=driver.findElements(By.xpath("//span[contains(.,'Check all')]")).size();
			System.out.println(checkSCCs);
			int closeCheckSCCs=driver.findElements(By.xpath("//span[@class='ui-icon ui-icon-circle-close']")).size();
			System.out.println(closeCheckSCCs);

			while(!driver.findElement(By.xpath("(//span[contains(.,'Check all')])["+checkSCCs+"]")).isDisplayed())
			{
				checkSCCs=checkSCCs-1;

				
			}
			driver.findElement(By.xpath("(//span[contains(.,'Check all')])["+checkSCCs+"]")).click();

			while(!driver.findElement(By.xpath("(//span[@class='ui-icon ui-icon-circle-close'])["+closeCheckSCCs+"]")).isDisplayed())
			{
				closeCheckSCCs=closeCheckSCCs-1;


			}
			driver.findElement(By.xpath("(//span[@class='ui-icon ui-icon-circle-close'])["+closeCheckSCCs+"]")).click();
		}
		catch(Exception e)
		{

		}

	}
	/**
	 * Description... Adding ULD Shipment Details
	 * 
	 * @param Pieces
	 * @param Weight
	 * @param Location
	 * @param ULDNumber
	 * @param Contour
	 * @throws Exception
	 */
	public void dataload_uldShipmentDetails(String Pieces, String Weight, String volume,String Location, String ULDNumber)
			throws Exception {
	
		String locator = xls_Read.getCellValue(sheetName, "inb_uldshipmentPieces;xpath");
		if (!driver.findElement(By.xpath(locator)).isDisplayed()) {
			clickWebElement(sheetName, "btn_Uldaccepatance;xpath", "ULDAcceptance", screenName);
			waitForSync(1);
		}
		enterValueInTextbox(sheetName, "inb_uldshipmentPieces;xpath", Pieces, "Pieces", screenName);
		
		enterValueInTextbox(sheetName, "inbx_uldshipmentWeight;xpath", Weight, "Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_uldshipmentVolume;xpath", volume, "Volume", screenName);
		
		
		enterValueInTextbox(sheetName, "inbx_uldacceptanceLoc;xpath", Location, "Location", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_ULDnumber;xpath", ULDNumber, "ULDNumber1", screenName);
		keyPress("TAB");
		clickULDWarningPopUp();
		selectValueInDropdown(sheetName, "lst_contour;id", "1", "Contour", "Index");
		keyPress("TAB");
		waitForSync(1);
		
		/******************* Select SCC *****************/

		clickWebElement(sheetName, "btn_addSCCsULD;id", "Button SCC", screenName);
		waitForSync(1);
		dataload_selectSCCs();


	}

	/**
	 * @author A-10330 Desc.. Provide CTM details
	 * @param carrierCode
	 * @param FlightDate
	 * @param Flightno
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void dataload_provideCTMdetails(String carrierCode)
			throws InterruptedException, IOException {

		//waitForSync(1);

		if (!driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_fromCarrier;xpath"))).isDisplayed()) {
			clickWebElement(sheetName, "btn_CtmDetails;xpath", "CTM Details", screenName);
			
		}
		waitForSync(2);
		String locator = xls_Read.getCellValue(sheetName, "enterfrm_carrier;xpath");
		
		String x=driver.findElement(By.xpath(locator)).getAttribute("value");
		System.out.println(x);
		
		if(driver.findElement(By.xpath(locator)).getAttribute("value").equals(""))
		{
			enterValueInTextbox(sheetName, "enterfrm_carrier;xpath", carrierCode, "From Carrier", screenName);
		}

		

	}
	
	/**
	 * Description... Save Acceptance
	 * @throws Exception 
	 */
	public void dataload_saveAcceptance(String awb,int rowVal,int colVal) throws Exception {
		//Thread.sleep(6000);
		clickWebElement(sheetName, "btn_Save;name", "AcceptanceSaveButton", screenName);
		Thread.sleep(4000);
		boolean isErrorMsgDisplayed=dataload_verifyErrorMessage(awb,rowVal,colVal);
		
		if(!isErrorMsgDisplayed)
		{
		try {
			
			switchToFrame("default");
			waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "Warning Pop-Up",
					screenName, 20);
			while (driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).isDisplayed()) {
				String msgText = getElementText(sheetName, "htmlDiv_invalidCertificateMsg;xpath", "warning",
						screenName);
				clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
				//Thread.sleep(8000);
				if (!msgText.contains("successfully saved"))
					waitTillScreenloadWithOutAssertion(sheetName, "htmlDiv_invalidCertificateMsg;xpath",
							"Warning Pop-Up", screenName, 20);
			}

		} catch (Exception e) {
			
			
		}

		switchToFrame("contentFrame", "OPR335");

	
		/**********************************************/
		waitTillScreenload(sheetName, "btn_Save;name", "Acceptance Save Button", screenName);
		waitTillScreenload(sheetName, "btn_editIcon;xpath", "Edit Icon", screenName);
		dataload_verifyAcceptanceFinalized(awb,rowVal,colVal);
		
		
		}
		//scrollBars("down",250);
		moveScrollBar(driver.findElement(By.xpath("//h2[contains(.,'Dimensions')]")));
		captureScreenShot("Web",awb);
		test.log(LogStatus.INFO, test.addScreenCapture(getPropertyValue(globalVarPath,"screenShotPath")));

	}
	/**
	 * Description..Click print
	 * 
	 * @throws Exception
	 */
	public void dataload_print(String awb) throws Exception {
		switchToWindow("storeParent");
		clickWebElementByWebDriver("GoodsAcceptance_OPR335", "btn_Print;xpath", "Print button",
				"GoodsAcceptance_OPR335");
		waitForSync(5);
		int windowSize = driver.getWindowHandles().size();
		


		try {
			if (windowSize > 1) {
				switchToWindow("child");
				captureScreenShot("Web");
				test.log(LogStatus.INFO, test.addScreenCapture(getPropertyValue(globalVarPath,"screenShotPath")));
				driver.close();
				switchToWindow("getParent");
				switchToFrame("default");
				switchToFrame("contentFrame", "OPR335");
				writeExtent("Pass","Print preview opened for  "+awb);
			}

			else {
				writeExtent("Fail","Print preview not opened for  "+awb);
			}
		} catch (Exception e) {
			writeExtent("Fail","Print preview not opened for  "+awb);
		}
		
		

	}
	
	public void dataload_clear() throws InterruptedException, IOException
	{
		waitTillScreenloadWithOutAssertion(sheetName, "btn_editIcon;xpath","awbEdit icon",screenName,7);	
		clickWebElement(sheetName, "btn_editIcon;xpath", "Edit Icon", screenName);
		clickWebElement(sheetName, "btn_clear;name", "Clear", screenName);
		waitTillScreenloadWithOutAssertion("Generic_Elements", "inbx_shipmentPrefix;xpath","Shipment Prefix",screenName,10);	
		//waitForSync(2);

	}
	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : verifying if the AWB is new
	 */
	public boolean isAWBCaptured(int rowVal,int colVal,String awb) throws InterruptedException, IOException {

		switchToFrame("default");
		boolean awbCaptured=true;

		int size =driver.findElements(By.xpath("//div[@class='alert-messages-detail']")).size();	
		
		if (size!=0)
		{
			String textMsg=driver.findElement(By.xpath("//div[@class='alert-messages-detail']")).getText();
			captureScreenShot("Web",awb);	
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			waitForSync(2);
			if(textMsg.contains("AWB does not exist"))
			setCellValue(rowVal, colVal, "FAILED - AWB not captured");
			else
			setCellValue(rowVal, colVal, "FAILED - "+textMsg);
			awbCaptured=false;	
		}

		switchToFrame("contentFrame", "OPR335");
		return awbCaptured;

	}
	/**
	 * @author A-9847
	 * Check for a New AWB or Already Accepted data on OPR335
	 * @param rowval
	 * @param colval
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	public boolean isAWBNotAccepted(int rowval, int colval,String awb) throws IOException, InterruptedException{

		boolean awbNotAccepted=true;
		int size =driver.findElements(By.xpath("//label[contains(text(),'Acceptance not finalised')]//i[@class='icon rejected']")).size();	
		int size1 =driver.findElements(By.xpath("//label[contains(text(),'Acceptance finalised')]//i[@class='icon done-alt']")).size();

	
		
		if(size !=0 || size1 !=0){
			
			moveScrollBar(driver.findElement(By.xpath("//h2[contains(.,'Dimensions')]")));
			captureScreenShot("Web",awb);	
			setCellValue(rowval, colval, "PASSED - ALREADY ACCEPTED - TBC");
			awbNotAccepted=false;
		}

		return awbNotAccepted;	

	}

	/**
	 * Description... Verify Acceptance Finalized
	 * 
	 * @param Acceptance_finalised_notfinalised
	 * @throws Exception 
	 */
	public void dataload_verifyAcceptanceFinalized(String awb,int rowVal,int colVal)
			throws Exception {



		String actTextFinalized="";
		String actTextRFC="";
		String expTextFinalized="Acceptance finalised";
		String expTextRFC="Ready for carriage";
		boolean finalized=false;
		boolean readyForCarriage=false;

		try
		{
			actTextFinalized = driver.findElement(By.xpath("//label[contains(text(),'Acceptance finalised')]")).getText();
			System.out.println(actTextFinalized);
			if (actTextFinalized.equals(expTextFinalized)) {
				writeExtent("Pass", "Acceptance finalised");
				finalized=true;
			

			} else {

				writeExtent("Fail", "Acceptance not finalised");
				
			}

		}
		catch(Exception e)
		{
			writeExtent("Fail", "Acceptance not finalised");
			
		}


		try
		{
			actTextRFC = driver.findElement(By.xpath("//*[contains(text(),'Ready for carriage')]")).getText();
			


			if (actTextRFC.equals(expTextRFC)) {
				writeExtent("Pass", "Shipment stamped ready for carriage");
				readyForCarriage=true;
				
			} else {
				writeExtent("Fail", "Shipment not stamped ready for carriage");
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Shipment not stamped ready for carriage");
		}
		
		
		if(finalized==true && readyForCarriage==true)
		{
			//setCellValue(rowVal, colVal, "Acceptance finalized and RFC stamped for the AWB - Status OK "+awb);
			setCellValue(rowVal, colVal, "PASSED");
		}
		
		else if(finalized==false && readyForCarriage==false )
		{
			//setCellValue(rowVal, colVal, "Acceptance not finalized and RFC not stamped for the AWB "+awb);
			setCellValue(rowVal, colVal, "PASSED - TBC");
		}
		else if(finalized==true && readyForCarriage==false )
		{
			//setCellValue(rowVal, colVal, "Acceptance  finalized and RFC not stamped for the AWB "+awb);
			setCellValue(rowVal, colVal, "PASSED but RFC not stamped");
		}
	}
	/**
	 * Description... Add Loose Shipment
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void dataload_addLooseShipment(int rowVal,int colVal) throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_AddShipment;name", "Add Loose Shipment Button", screenName);
		waitForSync(3);
		
		
		storeSU(rowVal,colVal);

	}

	public boolean dataload_verifyErrorMessage(String awbNumber , int rowVal, int colVal) throws InterruptedException, IOException{


		String errorMessage = xls_Read.getCellValue("Generic_Elements", "htmlDiv_errorMessages;xpath");

		try
		{
			if(driver.findElement(By.xpath(errorMessage)).isDisplayed()){
				
				String errorMessageText=driver.findElement(By.xpath(errorMessage)).getText();
				//setCellValue(rowVal, colVal, "Error message is coming on accepting the AWB "+awbNumber+" Error message is "+errorMessageText);
				setCellValue(rowVal, colVal, "FAILED with error - "+errorMessageText);
				writeExtent("Fail","Error message is coming on accepting the AWB "+awbNumber+" Error message is "+errorMessageText);
				
				return true;
				
			}
			else
			{
				return false;
			}
			
		

			
		}
		
		catch(Exception e)
		{
			
			return false;
		}
   }




	public String getNotFinalizedReason() throws InterruptedException, IOException {
        
        clickWebElement(sheetName, "label_AcceptanceNotFinalized;xpath", "Acceptance Not Finalized Label", screenName);
        waitForSync(2);
        String notFinalizedReason = getElementText(sheetName, "table_ReasonsForNotFinalized;xpath","Acceptance not finalized reason", screenName);
 
        return notFinalizedReason;

 }



	

	}
