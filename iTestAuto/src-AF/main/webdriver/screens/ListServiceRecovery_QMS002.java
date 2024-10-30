/**
 * Author : A-7724 
 * Date Created/ Modified : 20/02/2019
 * Description : To perform operations on ListServiceRecovery_QMS002
 */

package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.LogStatus;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ListServiceRecovery_QMS002 extends CustomFunctions{
	public ListServiceRecovery_QMS002(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "ListServiceRecovery_QMS002";
	public String ScreenName = "List Service Recovery : QMS002";
	CustomFunctions comm = new CustomFunctions(driver, excelreadwrite, xls_Read);

	/**
	 * Description... Selects OperationType from the type drop down
	 * @param OperationType
	 * @throws Exception
	 */
	public void selectOperationType(String type) throws Exception {
		selectValueInDropdown(sheetName, "list_OperationType;name", type,
				"Type of Operation", "Value");
		waitForSync(1);

	}

	/**
	 * Description... Enters Carrier Code, Flight Number and Flight data in List
	 * Service Recovery Screen
	 * @param CarrierCode          
	 * @param FlightNumber           
	 * @param Flightdata         
	 * @throws InterruptedException  , AWTException
         
	 */
	public void listFlight(String carrierCode, String flightNumber,
			String flightDate) throws InterruptedException, AWTException {
		try {
			enterValueInTextbox(sheetName, "inbx_carrierCode;name",
					carrierCode, "Carrier Code", ScreenName);
			enterValueInTextbox(sheetName, "inbx_flightNumber;name",
					flightNumber, "Flight Number", ScreenName);
			enterValueInTextbox(sheetName, "inbx_flightDate;name", flightDate,
					"Flight Date", ScreenName);
			waitForSync(2);
			keyPress("TAB");
			keyRelease("TAB");

		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not perform list flight operations");

		}
	}

	/**
	 * Description... Enters Shipment prefix, AWB Number and Click on List in
	 * List Service Recovery Screen
	 * @param Shipmentprefix        
	 * @param AWBNumber        
	 * @param ListButton      
	 * @throws InterruptedException , AWTException           
	 * @throws IOException 
	 */

	public void listAWB(String awbNo, String ShipmentPrefix)
			throws InterruptedException, IOException {

		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_shipPrefixFil;name",
				data(ShipmentPrefix), "Shipment Prefix", ScreenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumberFil;xpath", data(awbNo),
				"AWB No", ScreenName);
		clickWebElement(sheetName, "btn_List;xpath", "List Button", ScreenName);
		waitForSync(4);

	}

	/**
	 * Description... Verifies Alert text and click on close button
	 * @param Alert text
	 * @param CloseButton  
	 * @throws Exception
	 */
	public void errorMessageVerification(String errorMessage) throws Exception {
		try {
			String actualAlertText = driver
					.findElement(
							By.xpath(xls_Read.getCellValue(
									"ListServiceRecovery_QMS002",
									"txt_errorMsg;xpath"))).getText();

			String expectedAlertText = data(errorMessage);
			comm.verifyScreenText(
					ScreenName,
					expectedAlertText,
					actualAlertText,
					"Alert text",
					"1.Login to iCapsit \n ,2.Invoke QMS002 screen \n ,3.Enter the Booking details \n , 4.verify the error Message \n");

			clickWebElement("ListServiceRecovery_QMS002", "btn_Close;xpath",
					"Close Button", ScreenName);

		} catch (Exception e) {

		}
	}

	/**
	 * Description... Enters Shipment prefix, AWB Number , Select service
	 * Recovery Reason, Clicks on Add to list and Click on yes button in List
	 * Service Recovery Screen
	 * @param Shipment prefix       
	 * @param AWBNumber
	 * @param Service Recovery Reason   
	 * @param Addto list  
	 * @param YesButton       
	 * @throws InterruptedException
	 */
	public void listAWBForServiceRecovery(String awbNo, String ShipmentPrefix,
			String SRReason) throws InterruptedException {

		try {
			Thread.sleep(5000);
			enterValueInTextbox(sheetName, "inbx_shipPrefix;name",
					data(ShipmentPrefix), "Shipment Prefix", ScreenName);
			enterValueInTextbox(sheetName, "inbx_AWBnumber;name", data(awbNo),
					"AWB No", ScreenName);
			selectValueInDropdown(sheetName, "list_serviceRecReason;name",
					data(SRReason), "SR Reason", "VisibleText");
			clickWebElement(sheetName, "btn_addToList;name",
					"Add to List Button", ScreenName);
			Thread.sleep(2000);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath", "Ok Button",
					ScreenName);
			String frameName = "iCargoContentFrame" + "QMS002";
			driver.switchTo().frame(frameName);

		} catch (Exception e) {

		}
	}

	/**
	 * Description... Selects manualAction from action type drop down
	 * 
	 * @param manualAction
	 * @throws InterruptedException
	 */
	public void selectManualAction(String manualAction)
			throws InterruptedException {

		try {

			waitForSync(2);
			WebElement ele1 = findDynamicXpathElement(
					"list_manualActType;xpath", sheetName,
					"Manual Action type", ScreenName);
			waitForSync(1);
			moveScrollBar(ele1);
			selectValueInDropdown(sheetName, "list_manualActType;xpath",
					manualAction, "Manual Action type", "VisibleText");

		} catch (Exception e) {

		}
	}

	/**
	 * Description... Selects srStatus from SR Status type drop down
	 * 
	 * @param manualAction
	 * @throws InterruptedException
	 */
	public void selectSRStatus(String srStatus) throws InterruptedException {

		try {

			waitForSync(2);
			WebElement ele1 = findDynamicXpathElement("list_SRStatus;xpath",
					sheetName, "SR Status", ScreenName);
			waitForSync(1);
			moveScrollBar(ele1);
			selectValueInDropdown(sheetName, "list_SRStatus;xpath", srStatus,
					"SR Status", "VisibleText");

		} catch (Exception e) {

		}
	}

	/**
	 * Description... Click Save Button and Yes button in List Service Recovery Screen
	 * @param Save Button       
	 * @param Yes Button      
	 * @throws InterruptedException
	 */
	public void clickSave() throws InterruptedException {
		try {
			clickWebElement(sheetName, "btn_save;name", "Save Button",
					ScreenName);
			Thread.sleep(2000);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath", "Ok Button",
					ScreenName);
			String frameName = "iCargoContentFrame" + "QMS002";
			driver.switchTo().frame(frameName);
		} catch (Exception e) {

		}
	}

	/**
	 * Description... Click Save Button , Yes button and Yes button in List Service Recovery Screen
	 * @param Save Button       
	 * @param Yes Button  
	 * @param Yes Button 
	 * @throws InterruptedException
	 */
	public void clickServiceRecoverySave() throws InterruptedException {
		try {
			clickWebElement(sheetName, "btn_save;name", "Save Button",
					ScreenName);
			Thread.sleep(2000);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath", "Ok Button",
					ScreenName);
			clickWebElement("Generic_Elements", "btn_yes;xpath", "Ok Button",
					ScreenName);
			String frameName = "iCargoContentFrame" + "QMS002";
			driver.switchTo().frame(frameName);
		} catch (Exception e) {

		}
	}

	/**
	 * Description... Click check box row and Replan button in List Service Recovery Screen
	 * @param check box      
	 * @param Replan button        
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickReplan() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chk_row;xpath", "Select ROW", ScreenName);
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_rePlan;name", "Replan Button",
				ScreenName);

	}
}