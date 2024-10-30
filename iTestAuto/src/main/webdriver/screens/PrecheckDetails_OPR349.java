/**
 * @author A-8468/A-8470
 */
package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class PrecheckDetails_OPR349 extends CustomFunctions {

	Actions a1 = new Actions(driver);

	public PrecheckDetails_OPR349(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "PrecheckDetails_OPR349";
	public String ScreenName = "PreCheck Details / Screen : OPR349";

	/**
	 * Description : To select a shipment from the list displayed after
	 * filtering
	 * 
	 * @param AWBno
	 *            : AWB no for the shipment to be selected
	 */
	public void selectPrecheckShipment(String AWBno) {
		try {
			String div = xls_Read.getCellValue(sheetName, "div_AWBfilterResultPanel;xpath");
			String label = div + "//label[contains(text(),'" + AWBno + "')]";
			driver.findElement(By.xpath(label)).click();

		} catch (Exception e) {

			test.log(LogStatus.FAIL, "Failed to select " + AWBno);
			System.out.println("Failed to select " + AWBno);
			Assert.assertFalse(true, "Element is not found");
		}

	}

	/**
	 * Description : Clicks side pane and then lists the AWB no with the help
	 * filters
	 * 
	 * @param AWBno
	 *            : AWB no for the shipment
	 * @param stationCode
	 *            : Shipment prefix e.g., 020
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void ListAWB(String AWBno, String stationCode) throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "sidepane_AWBlist;xpath", "AWB List Side Pane", ScreenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_AWBprefix;id", data(stationCode), "Satation Code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumber;id", data(AWBno), "AWB Number", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
		waitForSync(2);

		selectPrecheckShipment(data(AWBno));
	}

	/**
	 * Description : Accepts all the highlighted fields(fields with bell
	 * symbol), gets the list of all the tabs where highlight is present, then
	 * provides reason and clicks on Accept button present in that division All
	 * MIP codes should be provided from test data
	 * 
	 * @param FullAWBno
	 *            : Full AWB no for the shipment i.e, shipment prefix + "-" +
	 *            AWBno e.g., 020-12312311
	 * @return List of MIP codes selected on the page for all highlighted
	 *         elements (needed for further validations)
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public List<String> acceptAllHighlightedField(String FullAWBno) throws InterruptedException, AWTException, IOException {
		List<String> selectedMIPreasoncodes = new ArrayList<String>();
		selectedMIPreasoncodes.add(FullAWBno);
		List<String> AllHighlightedElementText = getHighlightedElementText();

		for (String ElementText : AllHighlightedElementText) {
			switch (ElementText) {

			case "Handling Information-SSR":
				// selectValueInDropdown(sheetName,"lst_SSRMipReasonCode;xpath",data("SSRMipReasonCode"),"SSR
				// Mip Reason Code","Value");
				selectOptionInList(sheetName, "btn_SSRMipReasonCode;id", "chk_option;xpath", data("SSRMipReasonCode"),
						"SSR Mip Reason Code");
				selectedMIPreasoncodes.add(data("SSRMipReasonCode"));
				waitForSync(5);
				break;

			case "Handling Information-OSI":

				// selectValueInDropdown(sheetName,"lst_OSIMipReasonCode;xpath",data("OSIMipReasonCode"),"OSI
				// Mip Reason Code","Value");
				selectOptionInList(sheetName, "btn_OSIMipReasonCode;id", "chk_option;xpath", data("OSIMipReasonCode"),
						"OSI Mip Reason Code");
				selectedMIPreasoncodes.add(data("OSIMipReasonCode"));
				waitForSync(5);
				break;

			case "Accounting Information (ACC)":
				// selectValueInDropdown(sheetName,"lst_AccountingInfoMipReasonCode;xpath",data("AccMipReasonCode"),"Accounting
				// Info Mip Reason Code","Value");
				selectOptionInList(sheetName, "btn_ACCMipReasonCode;id", "chk_option;xpath", data("AccMipReasonCode"),
						"Accounting Info Mip Reason Code");
				selectedMIPreasoncodes.add(data("AccMipReasonCode"));
				waitForSync(5);
				break;

			case "Shipment Description (NG)":
				// selectValueInDropdown(sheetName,"lst_ShipmentDescriptionMipReasonCode;xpath",data("ShpDescMipReasonCode"),"Shipment
				// Description Mip Reason Code","Value");
				selectOptionInList(sheetName, "btn_NGMipReasonCode;id", "chk_option;xpath",
						data("ShpDescMipReasonCode"), "Shipment Description Mip Reason Code");
				selectedMIPreasoncodes.add(data("ShpDescMipReasonCode"));
				waitForSync(5);
				break;

			case "Other Customs Information (OCI)":
				// selectValueInDropdown(sheetName,"lst_OCIMipReasonCode;xpath",data("OCIMipReasonCode"),"OCI
				// Mip Reason Code","Value");
				selectOptionInList(sheetName, "btn_OCIMipReasonCode;id", "chk_option;xpath", data("OCIMipReasonCode"),
						"OCI Mip Reason Code");
				selectedMIPreasoncodes.add(data("OCIMipReasonCode"));
				waitForSync(5);
				break;

			case "Security Information":
				// selectValueInDropdown(sheetName,"lst_SecurityInfoMipReasonCode;xpath",data("SecurityInfoMipReasonCode"),"Security
				// Information Mip Reason Code","Value");
				selectOptionInList(sheetName, "btn_SECMipReasonCode;id", "chk_option2;xpath",
						data("SecurityInfoMipReasonCode"), "OCI Mip Reason Code");
				selectedMIPreasoncodes.add(data("SecurityInfoMipReasonCode"));
				waitForSync(5);
				break;

			}
		}

		keyPress("TAB");
		return selectedMIPreasoncodes;

	}

	/**
	 * Description : Gets Headers for all the Divisions on the page, removes all
	 * highlighted elements then clicks on ignore button for non highlighted
	 * Tabs
	 */
	public void ignoreNonHighlightedField() {
		String allTabxpath = xls_Read.getCellValue(sheetName, "tab_allTabs;xpath");
		List<String> AllElementText = getTabHeader(allTabxpath);
		List<String> AllHighlightedElementText = getHighlightedElementText();

		AllElementText.removeAll(AllHighlightedElementText);

		try {

			for (int i = 1; i < AllElementText.size() - 1; i++) {
				String ignoreSwitch = allTabxpath + "[contains(text(),'" + AllElementText.get(i)
						+ "')]/../..//div[@class='on-off-switch']";
				ele = driver.findElement(By.xpath(ignoreSwitch));
				a1.moveToElement(ele).click().build().perform();
			}

		} catch (Exception e) {

			test.log(LogStatus.FAIL, "Failed to ignore non highlighted tabs ");
			System.out.println("Failed to ignore non highlighted tabs ");
			Assert.assertFalse(true, "Element is not found");
		}

	}

	/**
	 * Description : Reads Headers for all highlighted divisions
	 * @return Headers of highlighted Tabs/divisions
	 */
	public List<String> getHighlightedElementText() {
		String highlightedElementxpath = xls_Read.getCellValue(sheetName, "tab_Highlighted;xpath");
		List<String> AllHighlightedElementText = getTabHeader(highlightedElementxpath);

		return AllHighlightedElementText;
	}

	/**
	 * Description : Reads text for all divisions matching to locator given, also removes extra spaces from the text
	 * @param locator : Locator for the tabs for which header is to be read
	 * @return list of division headers
	 */
	public List<String> getTabHeader(String locator) {
		List<String> l1 = new ArrayList<String>();
		try {
			List<WebElement> highlightedElements = driver.findElements(By.xpath(locator));

			for (WebElement element : highlightedElements) {
				String text = "";
				String eletext = element.getText();

				for (int i = 0; i < eletext.length(); i++) {
					if (eletext.charAt(i) != ' ' || (eletext.charAt(i - 1) != ' ' && eletext.charAt(i + 1) != ' ')) {
						text = text + eletext.charAt(i);
					}

				}
				l1.add(text);
			}
		} catch (Exception e) {

			test.log(LogStatus.FAIL, "Failed to ignore non highlighted tabs ");
			System.out.println("Failed to ignore non highlighted tabs ");
			Assert.assertFalse(true, "Element is not found");
		}

		return l1;
	}

	/**
	 * Description : Clicks on save button on OPR349 page
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSave() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_Save;id", "Save Button", ScreenName);
		waitForSync(5);
	}

	/**
	 * Description : Verifies the data in MIP consolidated tab against List of selected MIP codes
	 * @param selectedMIPreasoncodes : List of MIP codes selected against accepted highlighted divisions, 
	 * output of "acceptAllHighlightedField(String FullAWBno)" method
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyMIPconsolidated(List<String> selectedMIPreasoncodes) throws InterruptedException, IOException {

		Thread.sleep(3000);
		clickWebElement(sheetName, "span_MIPconsolidated;xpath", "MIP consolidated and remarks panel", ScreenName);
		String consolidatedMIP = getElementText(sheetName, "txtarea_MIPconsolidated;xpath",
				"MIP consolidated & Remarks", ScreenName);
		if (!(selectedMIPreasoncodes.isEmpty())) {
			for (int i = 0; i < selectedMIPreasoncodes.size(); i++) {
				if (consolidatedMIP.contains(selectedMIPreasoncodes.get(i))) {

					System.out.println("found true for " + selectedMIPreasoncodes.get(i));
					onPassUpdate(ScreenName, selectedMIPreasoncodes.get(i), consolidatedMIP, "MIP verification ",
							"MIP consolidated verification");

				} else {

					onFailUpdate(ScreenName, selectedMIPreasoncodes.get(i), consolidatedMIP, "MIP verification ",
							"MIP consolidated verification");
				}
			}
		}

		else {

			if (consolidatedMIP.equals("")) {
				onPassUpdate(ScreenName, "", consolidatedMIP, "MIP verification ", "MIP consolidated verification");

			} else {

				onFailUpdate(ScreenName, "", consolidatedMIP, "MIP verification ", "MIP consolidated verification");
			}
		}

	}

	/**
	 * Description : Checks security data reviewed checkbox and clicks on precheck validate button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickPrecheckValidate() throws InterruptedException, IOException {
		checkIfUnchecked(sheetName, "chk_securityDataReviewed;xpath", "Security data reviewed checkbox", ScreenName);

		clickWebElement(sheetName, "btn_PrecheckValidate;id", "PreCheck Validate Button", ScreenName);
		waitForSync(8);
	}

	/**
	 * Description : Reads the total issue count from top panel of the page
	 * @return String noOfIssues
	 * @throws InterruptedException
	 */
	public String getNoOfIssues() throws InterruptedException {
		String noOfIssues = getElementText(sheetName, "span_TotalIssueCount;xpath", "Issues", ScreenName);
		waitForSync(2);
		return noOfIssues;
	}

	/**
	 * Description : Reads the total House Airway Bill count associated to AWb listed from top panel of the page
	 * @return String noOfHAWB
	 * @throws InterruptedException
	 */
	public String getNoOfHAWB() throws InterruptedException {
		String noOfHAWB = getElementText(sheetName, "span_HAWBScount;xpath", "HAWBS", ScreenName);
		waitForSync(2);
		return noOfHAWB;
	}

	/**
	 * Description : Verifies precheck status/ block status/ Embargo status/ eData status of the Airway Bill is displayed as expected
	 * @param status : Expected status for the Airway Bill
	 * @throws InterruptedException
	 */
	public void VerifyStatus(String status) throws InterruptedException {

		switch (status) {

		case "Precheck_Loading":
			verifyElementDisplayed(sheetName, "span_Precheck_Loading;xpath", "PreCheck status verification", ScreenName,
					"PreCheck status");
			break;

		case "Precheck_Close":
			verifyElementDisplayed(sheetName, "span_Precheck_close;xpath", "PreCheck status verification", ScreenName,
					"PreCheck status");
			break;

		case "Block_tick":
			verifyElementDisplayed(sheetName, "span_Block_tick;xpath", "Block status verification", ScreenName,
					"PreCheck status");
			break;

		case "Block_error":
			verifyElementDisplayed(sheetName, "span_Block_error;xpath", "Block status verification", ScreenName,
					"PreCheck status");
			break;

		case "Embargo_tick":
			verifyElementDisplayed(sheetName, "span_Embargo_tick;xpath", "Embargo status verification", ScreenName,
					"PreCheck status");
			break;

		case "Embargo_error":
			verifyElementDisplayed(sheetName, "span_Embargo_error;xpath", "Embargo status verification", ScreenName,
					"PreCheck status");
			break;

		case "eDataStatus_green":
			verifyElementDisplayed(sheetName, "span_eDataStatus_green;xpath", "Embargo status verification", ScreenName,
					"PreCheck status");
			break;

		case "eDataStatus_red":
			verifyElementDisplayed(sheetName, "span_eDataStatus_red;xpath", "Embargo status verification", ScreenName,
					"PreCheck status");
			break;

		case "Precheck_Success":
			verifyElementDisplayed(sheetName, "span_Precheck_success;xpath", "PreCheck status verification", ScreenName,
					"PreCheck status");
			break;

		case "Precheck_Hold":
			verifyElementDisplayed(sheetName, "span_Precheck_Hold;xpath", "PreCheck status verification", ScreenName,
					"PreCheck status");
			break;
		}
	}

	/**
	 * Description : To accept highlight for any specific Tab/ Division
	 * @param tabName : Tab / Division name for which highlight/ issue has to be accepted
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void acceptSpecificHighlightedElement(String tabName) throws InterruptedException, AWTException, IOException {
		switch (tabName) {

		case "SSR":
			// selectValueInDropdown(sheetName,"lst_SSRMipReasonCode;xpath",data("SSRMipReasonCode"),"SSR
			// Mip Reason Code","Value");
			selectOptionInList(sheetName, "btn_SSRMipReasonCode;id", "chk_option;xpath", data("SSRMipReasonCode"),
					"SSR Mip Reason Code");
			keyPress("TAB");
			break;

		case "OSI":
			// selectValueInDropdown(sheetName,"lst_OSIMipReasonCode;xpath",data("OSIMipReasonCode"),"OSI
			// Mip Reason Code","Value");
			selectOptionInList(sheetName, "btn_OSIMipReasonCode;id", "chk_option;xpath", data("OSIMipReasonCode"),
					"OSI Mip Reason Code");
			keyPress("TAB");
			break;

		case "ACC":
			// selectValueInDropdown(sheetName,"lst_AccountingInfoMipReasonCode;xpath",data("AccMipReasonCode"),"Accounting
			// Info Mip Reason Code","Value");
			selectOptionInList(sheetName, "btn_ACCMipReasonCode;id", "chk_option;xpath", data("AccMipReasonCode"),
					"Accounting Info Mip Reason Code");
			keyPress("TAB");
			break;

		case "NG":
			// selectValueInDropdown(sheetName,"lst_ShipmentDescriptionMipReasonCode;xpath",data("ShpDescMipReasonCode"),"Shipment
			// Description Mip Reason Code","Value");
			selectOptionInList(sheetName, "btn_NGMipReasonCode;id", "chk_option;xpath", data("ShpDescMipReasonCode"),
					"Shipment Description Mip Reason Code");
			keyPress("TAB");
			break;

		case "OCI":
			// selectValueInDropdown(sheetName,"lst_OCIMipReasonCode;xpath",data("OCIMipReasonCode"),"OCI
			// Mip Reason Code","Value");
			selectOptionInList(sheetName, "btn_OCIMipReasonCode;id", "chk_option;xpath", data("OCIMipReasonCode"),
					"OCI Mip Reason Code");
			keyPress("TAB");
			break;

		case "SCC":
			// selectValueInDropdown(sheetName,"lst_SecurityInfoMipReasonCode;xpath",data("SecurityInfoMipReasonCode"),"Security
			// Information Mip Reason Code","Value");
			clickWebElement(sheetName, "btn_SECMipReasonCode;id", "Security Information Mip Reason Code", sheetName);
			String optionPath = xls_Read.getCellValue(sheetName, "chk_option;xpath").replace("dynVariable",
					data("SecurityInfoMipReasonCode")) + "[2]";
			try {
				ele = driver.findElement(By.xpath(optionPath));
				ele.click();
				keyPress("TAB");
				writeExtent("Pass", "Entered " + data("SecurityInfoMipReasonCode") + " as "
						+ "Security Information Mip Reason Code" + " on " + sheetName + " Screen");
				System.out.println("Entered " + data("SecurityInfoMipReasonCode") + " as "
						+ "Security Information Mip Reason Code" + " on " + sheetName + " Screen");
			} catch (Exception e) {
				e.printStackTrace();
				writeExtent("Fail", "Could not enter " + " as " + data("SecurityInfoMipReasonCode") + " on " + sheetName
						+ " Screen");
				Assert.assertFalse(true, "Could not enter " + " as " + data("SecurityInfoMipReasonCode") + " on "
						+ sheetName + " Screen");
			}

			break;

		}
	}

	/**
	 * Description : To ignore highlight/issue for any specific Tab/ Division
	 * @param tabName : Tab / Division name for which highlight/ issue has to be ignored
	 */
	public void ignoreSpecificHighlightedElement(String tabName) {
		try {

			String allTabxpath = xls_Read.getCellValue(sheetName, "tab_allTabs;xpath");

			String ignoreSwitch = allTabxpath + "[contains(text(),'" + tabName
					+ "')]/../..//div[@class='on-off-switch']";
			driver.findElement(By.xpath(ignoreSwitch)).click();

		} catch (Exception e) {

			test.log(LogStatus.FAIL, "Failed to ignore non highlighted tabs ");
			System.out.println("Failed to ignore non highlighted tabs ");
			Assert.assertFalse(true, "Element is not found");
		}
	}

	/**
	 * Description : To verify proper MIP codes are displayed for precheck close/rejected status
	 * @param AWBno : AWB no for the shipment
	 * @param selectedMIPreasoncodes : MIP reason codes given while accepting issues/ highlights
	 * output of "acceptAllHighlightedField(String FullAWBno)" method
	 * @throws InterruptedException
	 */
	public void verifyPrecheckDetails(String AWBno, List<String> selectedMIPreasoncodes) throws InterruptedException {
		try {
			clickWebElement(sheetName, "span_Precheck_close;xpath", "PreCheck Status", ScreenName);
			waitForSync(4);
			String precheckDetails = xls_Read.getCellValue(sheetName, "div_precheckDetails;xpath");
			String AWBxpath = precheckDetails + "//li/h3";
			String actAWB = driver.findElement(By.xpath(AWBxpath)).getText();

			if (actAWB.contains(AWBno)) {

				System.out.println("found true for " + AWBno);
				onPassUpdate(ScreenName, AWBno, actAWB, "AWBno ", "AWBno verification");

			} else {

				onFailUpdate(ScreenName, AWBno, actAWB, "AWBno ", "AWBno verification");
			}

			boolean flag = false;
			String actMIPreasoncodes = "";
			int i, j = 1;
			List<WebElement> MIPreasoncodes = driver.findElements(By.xpath(precheckDetails + "//li//label"));
			for (i = 1; i < MIPreasoncodes.size(); i++) {
				flag = false;
				for (j = 1; j < selectedMIPreasoncodes.size(); j++) {
					actMIPreasoncodes = MIPreasoncodes.get(i).getText();
					if (actMIPreasoncodes.contains(selectedMIPreasoncodes.get(j))) {
						flag = true;
						System.out.println("found true for " + selectedMIPreasoncodes.get(j));
						onPassUpdate(ScreenName, selectedMIPreasoncodes.get(j), actMIPreasoncodes, "MIP reason codes ",
								"MIP reason codes verification");
						break;

					}
				}

				if (flag == false) {
					onFailUpdate(ScreenName, selectedMIPreasoncodes.get(j), actMIPreasoncodes, "MIP reason codes ",
							"MIP reason codes verification");
				}
			}

		} catch (Exception e) {

			test.log(LogStatus.FAIL, "Failed to verify Precheck details ");
			System.out.println("Failed to verify Precheck details ");
			Assert.assertFalse(true, "Element is not found");
		}
	}

	/**
	 * Description : Verifies details of AWB are listed properly on precheck screen
	 * @param Origin : Origin of AWB/Flight e.g., FRA
	 * @param Destination : Destination of AWB/Flight e.g., MUC
	 * @param SCC : SCC codes selected while doing booking/capture through FWB (capture SCC overrides booking)
	 * @param SlackPcs : slac pieces captured through FWB
	 * @param Pcs : No of pieces in shipment
	 * @param Weight : Weight of the shipment
	 * @param Volume : Volume of the shipment
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyAWBDetails(String Origin, String Destination, String SCC, String SlackPcs, String Pcs,
			String Weight, String Volume) throws InterruptedException, IOException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String javaScript = "document.getElementById('" + "overviewBar" + "').textContent";
		String overviewbar = (String) (js.executeScript("document.getElementById('overviewBar').textContent;"));
		System.out.println("overviewbar is---" + overviewbar);
		System.out.println("Origin is---" + Origin);

		if (overviewbar.contains(Origin)) {
			System.out.println("found true for " + Origin);
			onPassUpdate(ScreenName, Origin, overviewbar, "Origin ", "Origin verification");
		} else {

			onFailUpdate(ScreenName, Origin, overviewbar, "Origin ", "Origin verification");
		}

		if (overviewbar.contains(Destination)) {
			System.out.println("found true for " + Destination);
			onPassUpdate(ScreenName, Destination, overviewbar, "Destination ", "Destination verification");
		} else {

			onFailUpdate(ScreenName, Destination, overviewbar, "Destination ", "Destination verification");
		}

		WebDriver driver = (WebDriver) js;

		String actSCC = getElementText(sheetName, "div_SCC;xpath", "SCC", ScreenName);

		if (actSCC.contains(SCC)) {
			System.out.println("found true for " + SCC);
			onPassUpdate(ScreenName, SCC, actSCC, "SCC ", "SCC verification");
		} else {

			onFailUpdate(ScreenName, SCC, actSCC, "SCC ", "SCC verification");
		}

		clickWebElement(sheetName, "lnk_moreless;xpath", "More/less icon", ScreenName);
		waitForSync(2);

		String actSlackPcs = getElementText(sheetName, "div_slackPcs;xpath", "slackPcs", ScreenName);

		if (actSlackPcs.contains(SlackPcs)) {
			System.out.println("found true for " + SlackPcs);
			onPassUpdate(ScreenName, SlackPcs, actSlackPcs, "Slack Pcs ", "Slack Pcs verification");
		} else {

			onFailUpdate(ScreenName, SlackPcs, actSlackPcs, "Slack Pcs ", "Slack Pcs verification");
		}

		String actPcs = getElementText(sheetName, "div_Pcs;xpath", "Pcs", ScreenName);

		if (actPcs.contains(Pcs)) {
			System.out.println("found true for " + Pcs);
			onPassUpdate(ScreenName, Pcs, actPcs, " Pcs ", "Pcs verification");
		} else {

			onFailUpdate(ScreenName, Pcs, actPcs, " Pcs ", "Pcs verification");
		}

		String actWt = getElementText(sheetName, "div_Wt;xpath", "Weight", ScreenName);

		if (actWt.contains(Weight)) {
			System.out.println("found true for " + Weight);
			onPassUpdate(ScreenName, Pcs, actWt, " Weight ", "Weight verification");
		} else {

			onFailUpdate(ScreenName, Pcs, actWt, " Weight ", "Weight verification");
		}

		String actVol = getElementText(sheetName, "div_Vol;xpath", "Volume", ScreenName);

		if (actVol.contains(Volume)) {
			System.out.println("found true for " + Volume);
			onPassUpdate(ScreenName, Volume, actVol, " Volume ", "Volume verification");
		} else {

			onFailUpdate(ScreenName, Volume, actVol, " Volume ", "Volume verification");
		}

		clickWebElement(sheetName, "lnk_moreless;xpath", "More/less icon", ScreenName);
		waitForSync(2);
	}

	/**
	 * Description : Reads all highlighted fields and clicks on ignore button for all those fields
	 */
	public void ignoreHighlightedField() {
		String allTabxpath = xls_Read.getCellValue(sheetName, "tab_allTabs;xpath");

		List<String> AllHighlightedElementText = getHighlightedElementText();
		try {

			for (String ElementText : AllHighlightedElementText) {
				String ignoreSwitch = allTabxpath + "[contains(text(),'" + ElementText
						+ "')]/../..//div[@class='on-off-switch']";
				driver.findElement(By.xpath(ignoreSwitch)).click();
			}

		} catch (Exception e) {

			test.log(LogStatus.FAIL, "Failed to ignore non highlighted tabs ");
			System.out.println("Failed to ignore non highlighted tabs ");
			Assert.assertFalse(true, "Element is not found");
		}

	}

	/**
	 * Description : Selects security methods used also handles check all & uncheck all 
	 * @param visibletext : check_all / uncheck_all / index (case to be selected)
	 * @param index : Array of indexes for security methods to be chosen, in case check all and uncheck all give an empty array
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void securityInformation(String visibletext, String[] index) throws InterruptedException, AWTException, IOException {
		/*
		 * Give visible text as check_all/uncheck_all if you want to click on
		 * those links else give index if you want to select multiple options
		 * Give visible text as uncheck_all if you don't want to select any
		 * options Define a string [] of indexes that u want to select in the
		 * dropdown starting from 1 If you don't want to select anything from
		 * droppdown define a empty array
		 * 
		 */
		keyPress("SCROLLDOWNMOUSE");
		waitForSync(3);

		checkIfUnchecked(sheetName, "chk_givensecstatusaccepted;id", "Given security status accepted checkbox",
				ScreenName);

		clickWebElement(sheetName, "btn_ScreeningDetails;id", "Screening Details button", ScreenName);
		waitForSync(5);

		switch (visibletext) {

		case "check_all":
			clickWebElement(sheetName, "lnk_CheckAll;xpath", "Check All ", ScreenName);
			break;

		case "uncheck_all":
			clickWebElement(sheetName, "lnk_UnCheckAll;xpath", "UnCheck All ", ScreenName);
			break;

		case "index":
			for (int i = 0; i < index.length; i++) {

				String dynxapth = "//*[@id='ui-multiselect-11-CMP_Operations_Shipment_PreCheckDetails_awbSecurityMipCodes-option-"
						+ index[i] + "']";
				
				clickWebElement(dynxapth, "Screening Details", ScreenName);

			}

		}
		keyPress("TAB");

	}

	/**
	 * Description : To verify the tool tip for issue highlighted
	 * @param expTooltip : Expected tool tip for that issue
	 * @param locator : Locator for the the bell icon in that tab
	 * @throws InterruptedException
	 */
	public void verifyTooltip(String expTooltip, String locator) throws InterruptedException {

		String highlight = xls_Read.getCellValue(sheetName, locator);
		String tooltip = driver.findElement(By.xpath(highlight)).getAttribute("title");
		System.out.println("The tool tip text is -- " + tooltip);

		if (tooltip.replaceAll(" ", "").equalsIgnoreCase((expTooltip).replaceAll(" ", ""))) {
			onPassUpdate(ScreenName, expTooltip, tooltip, " Tool tip ", "Tool tip verification");
		} else {

			onFailUpdate(ScreenName, expTooltip, tooltip, " Tool tip ", "Tool tip verification");
		}

	}

	/**
	 * Description : To verify accept/ignore button is present for the division
	 * @param tabname : Name of tab/division e.g, SSR,OSI,ACC,NG,OCI,SEC
	 * @throws InterruptedException
	 */
	public void verifyAcceptIgnoreBtnPresent(String tabname) throws InterruptedException {
		int index = 1;

		switch (tabname) {

		case "SSR":
			index = 1;
			break;

		case "OSI":
			index = 2;
			break;

		case "ACC":
			index = 3;
			break;

		case "NG":
			index = 5;
			break;

		case "OCI":
			index = 4;
			break;

		case "SEC":
			index = 6;
			break;

		}

		try {
			String Switch = xls_Read.getCellValue(sheetName, "btn_AcceptIgnoreSwitch;xpath");
			String dynxapth = Switch + "[" + index + "]";
			ele = driver.findElement(By.xpath(dynxapth));
			verifyElementDisplayed(ele, "radio button verification", ScreenName, "Accept / Ignore radio button");

			String dynxpath2 = dynxapth + "/div[1]/div";

			String position = driver.findElement(By.xpath(dynxpath2)).getAttribute("style");

			if (position.contains("left: 0px")) {
				customFunction.onPassUpdate(ScreenName, "Accept " + " is Displayed", "Accept " + " is Displayed",
						"Accept " + " is Displayed", "Accept switch Verfication ");

			} else {

				customFunction.onFailUpdate(ScreenName, "Accept " + " is Displayed", "Accept " + " is Not Displayed",
						"Accept " + " is Displayed", "Accept switch Verfication ");

			}

		} catch (Exception e) {

			customFunction.onFailUpdate(ScreenName, "Accept / Ignore radio button " + " is Displayed",
					"Accept / Ignore radio button " + " is Not Displayed",
					"Accept / Ignore radio button " + " is Displayed", "Accept / Ignore radio button Verfication ");

		}
	}

	/**
	 * Description : Verifies no bell icon is displayed on any of the TAB/ division
	 */
	public void verifyBellIconNotDisplayed() throws InterruptedException, AWTException {
		customFunction.keyPress("SCROLLDOWNMOUSE");
		verifyElementNotDisplayed(sheetName, "img_BellIcon;xpath", "Bell icon verification", ScreenName, "Bell icon");

	}

	/**
	 * Description : Verifies after selecting ignore button MIP dropdown is not displayed
	 * @param locator : locator for dropdown to be validated
	 * @param eleName : Name of the tab for which dropdown is been validated
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void verifyMIPreasoncodeDropdown(String locator, String eleName) throws InterruptedException, AWTException, IOException {

		selectOptionInList(sheetName, "btn_SECMipReasonCode;id", "chk_option2;xpath", data("SecurityInfoMipReasonCode"),
				"OCI Mip Reason Code");
		waitForSync(5);
		customFunction.keyPress("TAB");
		waitForSync(5);
		ignoreAll();

		waitForSync(5);

		String span = xls_Read.getCellValue(sheetName, locator) + "/..";

		if ((driver.findElement(By.xpath(span)).getAttribute("style")).contains("none")) {
			Status = false;

			customFunction.onPassUpdate(ScreenName, "Mip Reason Code dropdown" + " is Not Enabled",
					"Mip Reason Code dropdown" + " is Not Enabled", "Mip Reason Code dropdown" + " is Not Enabled",
					"Mip Reason Code dropdown should be hidden ");

		} else {

			customFunction.onFailUpdate(ScreenName, "Mip Reason Code dropdown" + " is Enabled",
					"Mip Reason Code dropdown" + " is Not Enabled", "Mip Reason Code dropdown" + " is Enabled",
					"Mip Reason Code dropdown should be hidden ");
		}

	}

	/**
	 * Description : Clicks on Ignore button for all tabs/ divisions
	 * @throws InterruptedException
	 */
	public void ignoreAll() throws InterruptedException {

		String Switchxpath = xls_Read.getCellValue(sheetName, "btn_AcceptIgnoreSwitch;xpath");
		try {

			Actions a1 = new Actions(driver);

			List<WebElement> allSwitches = driver.findElements(By.xpath(Switchxpath));

			for (WebElement Switch : allSwitches) {
				a1.moveToElement(Switch);
				Switch.click();

			}
			System.out.println("Ignored all the switches on " + ScreenName + " Page");
			writeExtent("Pass", "Ignored all the switches on " + ScreenName + " Page");
		} catch (Exception e) {

			System.out.println("Could not ignore switch on " + ScreenName + " Page");
			writeExtent("Fail", "Could not ignore switch on " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not ignore switch on " + ScreenName + " Page");
		}
	}

	/**
	 * Description : clicks on Side pane and Lists all AWB by giving precheck status, booking status, start date and end date as filter
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void ListAWB() throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "sidepane_AWBlist;xpath", "AWB List Side Pane", ScreenName);
		waitForSync(5);
		selectValueInDropdown(sheetName, "lst_PreCheckStatus;xpath", data("PrecheckStatus"), "Precheck status dropdown",
				"VisibleText");
		enterValueInTextbox(sheetName, "inbx_FromDate;id", data("StartDate"), "Start date", ScreenName);
		enterValueInTextbox(sheetName, "inbx_ToDate;id", data("EndDate"), "End date", ScreenName);
		selectValueInDropdown(sheetName, "lst_BookingStatus;xpath", data("BookingStatus"), "Booking status dropdown",
				"VisibleText");

		waitForSync(2);
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
		waitForSync(2);

	}
	
	/**
	 * Description : Clicks on next AWB button 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickNextAWB() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_NextAWB;id", "Next AWB Button", ScreenName);
		waitForSync(5);
	}

	/**
	 * Description : Verifies AWB no displayed in top panel is correct
	 * @param AWBNo : AWB no for the shipment (master document no)
	 * @throws InterruptedException
	 */
	public void VerifyAWB(String AWBNo) throws InterruptedException {
		String ActualAWB = getElementText(sheetName, "lnk_AWB;xpath", "AWB no", ScreenName);

		if (ActualAWB.contains(AWBNo)) {

			System.out.println("found true for " + AWBNo);
			onPassUpdate(ScreenName, AWBNo, ActualAWB, "AWB verification ", "AWB verification");

		} else {

			onFailUpdate(ScreenName, AWBNo, ActualAWB, "AWB verification ", "AWB verification");
		}
	}

	/**
	 * Description : When AWB is locked by other user for operation, method verifies if the hand symbol is displayed
	 * @param AwbNo : AWB no for the shipment (master document no)
	 * @throws InterruptedException
	 */
	public void verifyHandSymbol(String AwbNo) throws InterruptedException {
		String dynXpath = "//label[contains(.,'" + AwbNo + "')]/../..//i[@class='thumb']";

		if (driver.findElement(By.xpath(dynXpath)).isDisplayed()) {
			customFunction.onPassUpdate(ScreenName, "Hand Symbol " + " is Displayed", "Hand Symbol " + " is Displayed",
					"Hand Symbol " + " is Displayed", "Hand Symbol Verfication ");

		} else {

			customFunction.onFailUpdate(ScreenName, "Hand Symbol " + " is Displayed",
					"Hand Symbol " + " is Not Displayed", "Hand Symbol " + " is Displayed", "Hand Symbol Verfication ");

		}

	}

	/**
	 * Description : To enter remarks in text area field for master AWB
	 * @throws InterruptedException
	 */
	public void enterRemarks() throws InterruptedException {

		enterValueInTextbox(sheetName, "txtarea_otherRemarks;xpath", data("Remarks"), "Remarks", ScreenName);

	}

	/**
	 * Description: To verify change status button is disabled
	 * @throws InterruptedException
	 */
	public void verifyChangeStatusIsDisabled() throws InterruptedException {
		By b = getElement(sheetName, "btn_ChangeStatus;xpath");

		if (driver.findElement(b).isEnabled()) {
			Status = false;
			customFunction.onFailUpdate(ScreenName, "Change status button" + " is Enabled",
					"Change status button" + " is Not Enabled", "Change status button" + " is Enabled",
					"Change status button should be disabled ");
		} else {

			customFunction.onPassUpdate(ScreenName, "Change status button" + " is Enabled",
					"Change status button" + " is Not Enabled", "Change status button" + " is Enabled",
					"Change status button should be disabled ");
		}

	}

	/**
	 * Description : To relist AWb after performing some action on precheck detail screen
	 * @param AWBno : AWB no for the shipment (master document no)
	 * @param stationCode : Shipment prefix e.g, 020
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void reListAWB(String AWBno, String stationCode) throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "sidepane_AWBlist;xpath", "AWB List Side Pane", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName, "lnk_editSearch;xpath", "Edit serch link ", ScreenName);
		enterValueInTextbox(sheetName, "inbx_AWBprefix;id", data(stationCode), "Satation Code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumber;id", data(AWBno), "AWB Number", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
		waitForSync(2);

		selectPrecheckShipment(data(AWBno));
	}

	/**
	 * Description : To click on HAWB tab 
	 * @param HAWB : HAWB (House airway bill no) e.g.,H1, H1T123
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void clickHAWB(String HAWB) throws InterruptedException, AWTException {
		try {

			String id = "houseAWB_" + HAWB;
			waitForSync(2);
			driver.findElement(By.id(id)).click();
			writeExtent("Pass", "Clicked on " + HAWB + " On " + ScreenName + " Page");
			System.out.println("Clicked on " + HAWB + " On " + ScreenName + " Page");

			waitForSync(5);

		} catch (Exception e) {
			System.out.println("Could not click on " + HAWB + " On " + ScreenName + " Page");
			writeExtent("Fail", "Could not click on " + HAWB + " On " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not click on " + HAWB + " On " + ScreenName + " Page");
		}

	}

	/**
	 * Description : To validate precheck is completed for HAWB
	 * @param HAWB : HAWB (House airway bill no) e.g.,H1, H1T123
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void VerifyHAWBcompleted(String HAWB) throws InterruptedException, AWTException {
		try {
			String xpath = "//span[@id='hawbCompleted_" + HAWB + "']/i[contains(@class,'fa-tick-sm')]";
			if (driver.findElement(By.xpath(xpath)).isDisplayed()) {
				customFunction.onPassUpdate(ScreenName, "HAWB precheck completed Symbol " + " is Displayed",
						"HAWB precheck completed Symbol " + " is Displayed",
						"HAWB precheck completed Symbol" + " is Displayed",
						"HAWB precheck completed Symbol Verfication ");

			} else {

				customFunction.onFailUpdate(ScreenName, "HAWB precheck completed Symbol " + " is Displayed",
						"HAWB precheck completed Symbol " + " is Not Displayed",
						"HAWB precheck completed Symbol " + " is Displayed",
						"HAWB precheck completed Symbol Verfication ");

			}

		} catch (Exception e) {
			System.out.println("Could not verify precheck complete for " + HAWB + " On " + ScreenName + " Page");
			writeExtent("Fail", "Could not verify precheck complete for " + HAWB + " On " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not verify precheck complete for " + HAWB + " On " + ScreenName + " Page");
		}

	}

	/**
	 * Description : To click AWB no link in top panel
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickAWB() throws InterruptedException, IOException {

		clickWebElement(sheetName, "lnk_AWB;xpath", "AWB no", ScreenName);
		waitForSync(2);

	}

	/**
	 * Description : To click on More/Less icon to expand or collapse the details
	 * @param More_Less : Provide more to expand, less to collapse
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickMore_Less(String More_Less) throws InterruptedException, IOException {
		switch (More_Less) {
		case "More":
			clickWebElement(sheetName, "lnk_more;xpath", "More icon", ScreenName);
			break;

		case "Less":
			clickWebElement(sheetName, "lnk_less;xpath", "More icon", ScreenName);
			break;
		}
		waitForSync(4);

	}

	/**
	 * Description : To provide Security RA details and select a record from LOV
	 * @param CountryName : Country name for which RA to be listed e.g., DE
	 * @param RA : RA number e.g., DE/RA/00268-19
	 */
	public void securityRAdetail(String CountryName, String RA) {
		try {
			clickWebElement(sheetName, "span_RAdetailsLOV;id", "RA detail LOV", ScreenName);
			switchToFrame("default");
			waitForSync(2);

			enterValueInTextbox(sheetName, "inbx_RAdetailNumber;name", RA, "RA detail Number", ScreenName);
			enterValueInTextbox(sheetName, "inbx_RAdetailCountry;name", CountryName, "Country name", ScreenName);
			clickWebElement(sheetName, "btn_searchRadetail;id", "search RA detail", ScreenName);
			waitForSync(3);

			String rows = xls_Read.getCellValue(sheetName, "tbl_Radetail;xpath");
			String dynxapth = rows + "//label[contains(.,'" + RA + "')]/../..//input[1]";
			driver.findElement(By.xpath(dynxapth)).click();

			clickWebElement(sheetName, "btn_RAdetailOK;id", "RA detail", ScreenName);
			switchToFrame("contentFrame", "OPR349");

		} catch (Exception e) {
			System.out.println("Could not select RA detail from LOV " + ScreenName + " Page");
			writeExtent("Fail", "Could not select RA detail from LOV " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not select RA detail from LOV " + ScreenName + " Page");
		}

	}

	/**
	 * Description : To edit search filters
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void editSearch() throws InterruptedException, IOException {
		clickWebElement(sheetName, "sidepane_AWBlist;xpath", "AWB List Side Pane", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName, "lnk_editSearch;xpath", "Edit serch link ", ScreenName);
	}

	/**
	 * Description : To compare booking details against capture details
	 * @param Agentcode
	 */
	public void compareAWBandBookingAgent(String Agentcode) {

		try {
			String AWBdetails = driver.findElement(By.xpath("(//b[@class='fltSec'])[1]")).getText();
			String BookingDetails = driver.findElement(By.xpath("(//b[@class='fltSec'])[2]")).getText();
			if (AWBdetails.contains(Agentcode) && BookingDetails.contains(Agentcode)) {

				System.out.println("Agent code is same in AWB and Booking details " + ScreenName + " Page");
				writeExtent("Pass", "Agent code is same in AWB and Booking details " + ScreenName + " Page");
			} else {
				System.out.println("Agent code is different in AWB and Booking details " + ScreenName + " Page");
				writeExtent("Fail", "Agent code is different in AWB and Booking details " + ScreenName + " Page");

			}
		} catch (Exception e) {

			onFailUpdate("Precheck Details", "Agent code should be same in both AWB vs Booking",
					"Agent code is different in AWB and Booking details", "AWB vs Booking Comparisons",
					"Agent code verification in AWB vs Booking Comparisons tab");

		}
	}

	/**
	 * Description : To enter RA number in text field
	 * @param RA : RA detail no e.g., DE/RA/00268-19
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterSecurityRA(String RA) throws InterruptedException, AWTException {
		String js = "document.getElementsByName('raDetailsNumber')[0].value='" + RA + "'";
		((JavascriptExecutor) driver).executeScript(js);

	}

	/**
	 * Description : To verify AWB is not listed for precheck
	 * @param AWBno : AWB no for the shipment
	 * @param stationCode : Shipment prefix e.g., 020
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void verifyAWBnotListed(String AWBno, String stationCode) throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "sidepane_AWBlist;xpath", "AWB List Side Pane", ScreenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_AWBprefix;id", data(stationCode), "Satation Code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumber;id", data(AWBno), "AWB Number", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
		waitForSync(2);

		String div = xls_Read.getCellValue(sheetName, "div_AWBfilterResultPanel;xpath");
		String label = div + "//label[contains(text(),'" + data(AWBno) + "')]";
		try {
			if (driver.findElement(By.xpath(label)).isDisplayed()) {
				test.log(LogStatus.FAIL, data(AWBno) + " is listed on the screen ");
				System.out.println(data(AWBno) + " is listed on the OPR349 screen ");
				Assert.assertFalse(true, "Element is found");
			}

		} catch (Exception e) {

			test.log(LogStatus.PASS, data(AWBno) + "is not listed on OPR349 screen");
			System.out.println(data(AWBno) + "is not listed on OPR349 screen");
		}

	}

	/**
	 * Description : To verify custom status
	 * @param expCustomStatus : Expected custom status e.g., X,T1
	 * @throws InterruptedException
	 */
	public void verifyCustomStatus(String expCustomStatus) throws InterruptedException {
		String actCustomStatus = getElementText(sheetName, "div_CustomStatus;xpath", "Custom status", ScreenName);

		if ((actCustomStatus.replace(" ", "")).contains(expCustomStatus.replace(" ", ""))) {
			System.out.println("found true for " + expCustomStatus);
			onPassUpdate(ScreenName, expCustomStatus, actCustomStatus, "Custom Status verification ",
					"Custom Status verification");

		} else {

			onFailUpdate(ScreenName, expCustomStatus, actCustomStatus, "Custom Status verification ",
					"Custom Status verification");
		}

	}

	/**
	 * Description : To click on side pane icon
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSidePane() throws InterruptedException, IOException {
		clickWebElement(sheetName, "sidepane_AWBlist;xpath", "AWB List Side Pane", ScreenName);
		waitForSync(2);

	}

	/**
	 * Description : To select precheck status from the dropdown
	 * @param PrecheckStatus
	 */
	public void selectPrecheckStatus(String PrecheckStatus) {

		selectValueInDropdown(sheetName, "lst_PreCheckStatus;xpath", PrecheckStatus, "Precheck status dropdown",
				"VisibleText");
	}

	/**
	 * Description : To list AWB giving precheck status filter
	 * @param AWBno : AWB no for the shipment
	 * @param stationCode : shipment prefix e.g., 020
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void ListAWBWithPrecheckStatus(String AWBno, String stationCode) throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "sidepane_AWBlist;xpath", "AWB List Side Pane", ScreenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_AWBprefix;id", data(stationCode), "Satation Code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumber;id", data(AWBno), "AWB Number", ScreenName);
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_PreCheckStatus;xpath", data("PrecheckStatus"), "Precheck status dropdown",
				"VisibleText");
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
		waitForSync(2);
		selectPrecheckShipment(data(AWBno));
	}

	/**
	 * Description : To click "Yes" button in the pop up
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickYesButton() throws InterruptedException, IOException {
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", ScreenName);

	}

	/**
	 * Description : To click "No" button in the pop up
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickNoButton() throws InterruptedException, IOException {
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_no;xpath", "no Button", ScreenName);

	}

	/**
	 * Description : To verify whether hand symbol is displayed or not
	 * @param AWBno : AWB no for the shipment
	 * @param displayed_notdisplayed : Whether hand symbol should be displayed or not e.g., Displayed, Not Displayed
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyHandSymbol(String AWBno, String displayed_notdisplayed)
			throws InterruptedException, AWTException {

		String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetail;xpath");
		String dynxpath = xpath + "[contains(.,'" + AWBno + "')]//td[1]//img[contains(@src,'thumb')]";

		switch (displayed_notdisplayed) {

		case "Displayed":
			if (driver.findElement(By.xpath(dynxpath)).isDisplayed()) {
				System.out.println("Hand symbol is displayed");
				writeExtent("Pass", "Hand symbol is displayed");
			} else {
				System.out.println("Hand symbol is not displayed");
				writeExtent("Fail", "Hand symbol is not displayed");
			}

		case "Not Displayed":
			try {
				if (driver.findElement(By.xpath(dynxpath)).isDisplayed()) {
					System.out.println("Hand symbol is displayed");
				} else {
					System.out.println("Hand symbol is not displayed");
					writeExtent("Pass", "Hand symbol is not displayed");
				}

			} catch (Exception e) {

				writeExtent("Fail", "Hand symbol is displayed");
				Assert.assertFalse(true,
						" Hand symbol is displayed " + ScreenName + " Page it should not be displayed");
			}
		}
	}

	/**
	 * Description : To verify whether bell icon is displayed
	 * @param tabName : Name of the tab / division for which bell should be displayed
	 */
	public void verifyBellIconIsDisplayed(String tabName) {

		String dynxpath = "//h2[contains(.,'" + tabName + "')]//i[@class='icon alert-sm']";
		if (driver.findElement(By.xpath(dynxpath)).isDisplayed()) {
			System.out.println("bell icon is displayed");
			writeExtent("Pass", "bell icon is displayed");
		} else {
			System.out.println("bell icon is not displayed");
			writeExtent("Fail", "bell icon is not displayed");
		}


	}

}
