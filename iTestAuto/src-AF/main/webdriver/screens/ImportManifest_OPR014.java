package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ImportManifest_OPR014 extends CustomFunctions {

	String sheetName = "ImportManifest_OPR014";
	String screenName = "Import Manifest : OPR014";
	String screenId = "OPR014";
public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public ImportManifest_OPR014(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	// click the ULD check Box
/**
 * Description... click ULD CheckBox
 * @param pmyKey
 * @throws InterruptedException
 */
	public void clickULDCheckBox(String pmyKey) throws InterruptedException {
		selectTableRecord(data(pmyKey), "chk_selectULD;xpath", sheetName, 3);
		waitForSync(2);
	}

	// Select Breakdown Instruction
/**
 * Description... Select Breakdown Instruction
 * @param pmyKey
 * @param Value
 */
	public void selectBreakdownInstrction(String pmyKey, String Value) {

		try {
			boolean flag = false;
			int row = 0;
			// get the required row
			String tableBody = xls_Read.getCellValue(sheetName, "tbl_importmanifest;xpath");
			List<WebElement> rows = driver
					.findElements(By.xpath(xls_Read.getCellValue(sheetName, "tbl_importmanifest;xpath")));

			System.out.println("row size  " + rows.size());

			{

				rows = driver.findElements(By.xpath(tableBody));

				{
					for (int i = 0; i <= rows.size(); i++) {
						System.out.println("i= " + i);

						if (rows.get(i).getText().toLowerCase().replace(" ", "")
								.contains(pmyKey.toLowerCase().replace(" ", ""))) {

							flag = true;

						}

						if (flag) {
							row = i + 1;
							break;
						}
					}

					System.out.println("row = " + row);

					int id = 0;
					if (row == 1) {
						id = 0;
					} else {
						id = row - 4;
					}

					String dynXpath = "//*[@id='" + id + "']/td[28]//select[@name='ULDBreakdownInstruction']";

					clickWebElement(sheetName, "btn_scroll;xpath", "Scroll", screenName);
					waitForSync(1);
					clickWebElement(sheetName, "btn_scroll;xpath", "Scroll", screenName);
					waitForSync(2);
					WebElement ele = null;
					ele = driver.findElement(By.xpath(dynXpath));
					Select select = new Select(ele);
					String actopt = select.getFirstSelectedOption().getText();
					if (!actopt.equalsIgnoreCase(Value))
						select.selectByVisibleText(Value);
					waitForSync(2);

					System.out.println("Breakdown instruction " + Value + " is Selected");

				}
			}

		}

		catch (Exception e) {

			System.out.println("Breakdown instruction is not selected");

		}
	}

	// Save Manifest Details
/**
 * Description... Save Manifest Details
 * @throws InterruptedException
 * @throws IOException 
 */
	public void saveManifestDetails() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_Save;xpath", "Save", screenName);
		waitForSync(4);
	}
/**
 * Description... Click Flight Close Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickFlightCloseButton() throws InterruptedException, IOException {
		Thread.sleep(1000);
		clickWebElement(sheetName, "butn_close;name", "Close Button", screenName);
		Thread.sleep(3000);
		handleAlert("Accept", screenName);

	}

	/*
	 * public void verifyBreakdownComplete() { try{
	 * 
	 * waitForSync(2);
	 * ele=findDynamicXpathElement("img_breakdownComplete;xpath", sheetName,
	 * "Breakdown Complete", screenName); boolean image=ele.isDisplayed();
	 * System.out.println("Image verified"); test.log(LogStatus.PASS,
	 * "Breakdown Completed image is Verified " + image);
	 * 
	 * } catch(Exception e) { System.out.println("Image not verified");
	 * test.log(LogStatus.FAIL, "Failed to Verify Image");
	 * Assert.assertFalse(true, "Element is not found"); } }
	 * 
	 * public void verifyBreakdownInComplete() { try{
	 * 
	 * waitForSync(2);
	 * 
	 * ele=findDynamicXpathElement("img_breakdownIncomplete;xpath", sheetName,
	 * "Breakdown InComplete", screenName); boolean image=ele.isDisplayed();
	 * System.out.println("Image verified"); test.log(LogStatus.PASS,
	 * "Breakdown inComplete image is Verified " + image);
	 * 
	 * } catch(Exception e) { System.out.println("Image not verified");
	 * test.log(LogStatus.FAIL, "Failed to Verify Image");
	 * Assert.assertFalse(true, "Element is not found"); } }
	 */
/**
 * Description... Verify Breakdown Image
 * @param pmyKey
 * @param imagelocator
 * @param imageName
 */
	public void verifyBreakdownImage(String pmyKey, String imagelocator, String imageName) {
		try {
			boolean flag = false;
			int row = 0;
			// String ScreenName = sheetName.split("_")[0];
			// get the required row
			String tableBody = xls_Read.getCellValue(sheetName, "tbl_importmanifest;xpath");
			List<WebElement> rows = driver
					.findElements(By.xpath(xls_Read.getCellValue(sheetName, "tbl_importmanifest;xpath")));
			String image = xls_Read.getCellValue(sheetName, imagelocator);

			System.out.println("row size  " + rows.size());

			{

				rows = driver.findElements(By.xpath(tableBody));

				{
					for (int i = 0; i <= rows.size(); i++) {
						System.out.println("i= " + i);

						if (rows.get(i).getText().toLowerCase().replace(" ", "")
								.contains(pmyKey.toLowerCase().replace(" ", ""))) {

							flag = true;

						}

						if (flag) {
							row = i + 1;
							break;
						}
					}

					System.out.println("row = " + row);
					int id = 0;
					if (row == 1) {
						id = 0;
					} else {
						id = row - 4;
					}

					String dynXpath = "//*[@id='" + id + "']/td[4]/div" + image;
					WebElement ele = null;

					ele = driver.findElement(By.xpath(dynXpath));
					Thread.sleep(2000);
					boolean verifyimage = ele.isDisplayed();
					System.out.println("Image verified");
					test.log(LogStatus.PASS, imageName + " image is Verified ");

				}
			}

		}

		catch (Exception e) {

			test.log(LogStatus.FAIL, "Could not perform image verification");
			System.out.println("Image is not verified or verification failed");

		}
	}



/**
 * Description... Link Add Details
 * @param uldNo
 * @param POL
 * @param flightCode
 * @param awbNo
 * @param ownerCode
 * @param ShipmentPieces
 * @param ShipmentWeight
 * @param origin
 * @param destination
 * @param SCC
 * @throws Exception
 */

	// click on the BreakDown
public void linkAddDetails(String uldNo, String POL, String flightCode,
			String awbNo, String ownerCode, String ShipmentPieces,
			String ShipmentWeight, String origin, String destination, String SCC)
			throws Exception {
		screenName = "Link Add Pop Up";
		enterValueInTextbox(sheetName, "inbx_lnkadd_ULDno;xpath", uldNo,
				"ULD No", screenName);
		selectValueInDropdown(sheetName, "lst_lnkadd_POL;xpath", POL, "POL",
				"Value");
		clickWebElement(sheetName, "btn_lnkadd_list;xpath", "List Button",
				screenName);
		enterValueInTextbox(sheetName, "inbx_lnkadd_flightCode;xpath",
				ownerCode, "Flight Code", screenName);
		
		setPropertyValue("AWBNo", awbNo, proppath);
		
		enterValueInTextbox(sheetName, "inbx_lnkadd_awbNo;xpath", awbNo,
				"AWB No", screenName);
		enterValueInTextbox(sheetName, "inbx_lnkadd_ownerCode;xpath",
				flightCode, "Owner Code", screenName);
		clickWebElement(sheetName, "btn_lnkadd_btnListTwo;xpath",
				"AWB List Button", screenName);
		waitForSync(5);

		customFunction.handleAlert("Accept", "OPR014");
		enterValueInTextbox(sheetName, "inbx_lnkadd_MftPcs;xpath",
				ShipmentPieces, "Shipment Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_lnkadd_MftWt;xpath",
				ShipmentWeight, "Shipment Weight", screenName);

		enterValueInTextbox(sheetName, "inbx_statedPieces;name",
				ShipmentPieces, "Stated Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_statedWeight;name",
				ShipmentWeight, "Stated Weight", screenName);

		enterValueInTextbox(sheetName, "inbx_origin;name", origin, "Origin",
				screenName);
		enterValueInTextbox(sheetName, "inbx_destination;name", destination,
				"Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_SCC;name", data("SecSCC"), "SCC", screenName);
		clickWebElement(sheetName, "chk_awbRcvd;xpath", "AWB Rcvd Check Box",
				screenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_lnkadd_SCI;name", "SCI Link",
				screenName);

		
        
		
		selectValueInDropdown(sheetName, "lst_lnkadd_SCI;name", SCC, "SCI",
				"VisibleText");
		waitForSync(3);
		clickWebElement(sheetName, "btn_lnkadd_Ok;xpath", "Ok Button",
				screenName);

		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", screenId);

	}
	
	
/**
 * Description... Click BreakDown
 * @throws InterruptedException
 * @throws IOException 
 */
	/* click on the BreakDown */

	public void clickBreakDown() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_breakdown;name", "Break Down", screenName);
		waitForSync(5);
		

		 handleAlert("Accept", screenName);

	}

/**
	 * Description... Get the first AWB Number from the screen
	 * @return
	 * @throws InterruptedException
 * @throws IOException 
	 */
	public String getFirstAWBNo() throws InterruptedException, IOException{
		clickWebElement(sheetName, "lnk_expandAWB;xpath", "Expand Button", screenName);
		try{
			driver.findElement(By.xpath("((//table[@id='importmanifest']//tr)[3]//td)[12]")).isDisplayed();
		}
		catch(Exception e)
		{
			System.out.println("Failed in flight creation");
			writeExtent("Fail", "Failed in flight creation");
			Assert.assertFalse(true, "Failed in flight creation");
		}
		return getElementText(sheetName, "txt_awbNo1;xpath", "AWB Number", screenName).trim();
	}
	/**
	 * Description... Get the second AWB Number from the screen
	 * @return
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public String getSecondAWBNo() throws InterruptedException, IOException{
		clickWebElement(sheetName, "lnk_expandAWB;xpath", "Expand Button", screenName);
		return getElementText(sheetName, "txt_awbNo2;xpath", "AWB Number", screenName).trim();
	}


/**
	 * Description... Selects BreakdownInstruction Method
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public void selectBreakdownInstruction(String selectBreakdownInstructionMethod) throws InterruptedException {

		selectValueInDropdown(sheetName, "lst_breakdownInstruction;name", selectBreakdownInstructionMethod,
				"Breakdown Instruction", "VisibleText");

	}

/**
	 * Description... Click RampTransfer Button	 * 
	 * @throws InterruptedException
 * @throws IOException 
	 */
	/* click on the Ramp Transfer button */
	public void clickRampTransfer() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_RampTransfer;name", "Ramp Transfer", screenName);
		waitForSync(8);

	}

	/**
	 * Description... verify AWB received document is checked
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public void verifyAWBDocumentReceivedCheck() throws InterruptedException {

		By b = getElement(sheetName, "chk_awrReceivedFlag;name");
		boolean checked = driver.findElement(b).isSelected();

		if (checked) {

			writeExtent("Pass", "AWB received doc is checked ");

		} else
			writeExtent("Fail", "AWB received doc is NOT checked ");

	}

	
	/**
	 * Description... Verify ULD details are correct
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	
	public void verifyULDDetails(int verfCols[], String actVerfValues[])
			throws Exception {
		
		clickWebElement(sheetName, "lnk_expandFlightDetails;xpath", "Expand Flight Details", screenName);
		verify_tbl_records_multiple_cols(sheetName, "tbl_importmanifest;xpath", "//td", verfCols, data("AWBNo"),
				actVerfValues);
		

	}
	
	/**
	 * Description... Verify Breakdown status
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	
	public void verifyBDNStatus(String BDNStatus, String pmkey)
			throws Exception {

		String xpath = xls_Read.getCellValue(sheetName, "tbl_importmanifest;xpath");
		String dynxpath = xpath + "[contains(.,'" + pmkey  + "')]//td[4]//img";
		WebElement img = driver.findElement(By.xpath(dynxpath));
		String status = img.getAttribute("tooltip_info");

		switch (BDNStatus) {
		case "Completed":

			if (status.equals("Breakdown Completed")) {
				System.out.println("Breakdown status is " + BDNStatus);
				writeExtent("Pass", "Breakdown status is " + BDNStatus);
			} else {
				System.out.println("Breakdown status is not " + BDNStatus);
				writeExtent("Fail", "Breakdown status is not " + BDNStatus);
			}
			break;

		}

	}


    /***
	 * A-8705
	 * Verifies Discrepancy message
	 * @param Discrepancy Msg
	 * @throws InterruptedException 
     * @throws IOException 
	 *            
	 */
	public void verifyDiscrepancyMsg(String msg) throws InterruptedException, IOException {	
		switchToFrame("default");
		waitForSync(4);
		WebElement ele = driver.findElement(By.xpath(xls_Read.getCellValue("BreakDown_OPR004", "popup_msg;xpath")));	
		String expectedText = ele.getText();
		String e=expectedText.replaceAll(" ", "");
		String m =msg.replaceAll(" ", "");
		verifyScreenText(sheetName, e, m,
				"Error Message_partial Breakdown",
				"//1. Login to iCargo \n , 2.Complete Partial breakdown\n");
		clickWebElement("Generic_Elements", "btn_yes;xpath", "BreakDown Yes",
				screenName);
	

	}



}
