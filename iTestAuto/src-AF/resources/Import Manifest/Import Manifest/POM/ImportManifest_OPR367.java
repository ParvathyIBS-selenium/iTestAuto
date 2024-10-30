package screens;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ImportManifest_OPR367 extends CustomFunctions {

	String sheetName = "ImportManifest_OPR367";
	String sheetName2="BreakDown_OPR004";
	String GenericSheet="Generic_Elements";
	String screenName = "Import Manifest : OPR367";
	String screenId = "OPR367";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public ImportManifest_OPR367(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
	super(driver, excelReadWrite, xls_Read2);

	}
	/**
	 * Description : Used to List Flight
	 * @author A-9175
	 * @param carrCode
	 * @param FlightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	
		public void listFlight(String carrCode,String FlightNumber, String flightDate) throws InterruptedException, AWTException {
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_carrierCode;xpath", data(carrCode), "Flight Carrier code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;xpath", data(flightDate), "Flight Date", screenName);
		performKeyActions(sheetName, "inbx_flightDate;xpath", "TAB", "Flight Date", screenName);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		Thread.sleep(3000);
		}
		
		/**
		 * @Description : Used to see all shipment Details
		 * @author A-9175
		 * @throws InterruptedException
		 * @throws AWTException
		 */
		
		public void maximizeAllDetails() throws InterruptedException, AWTException {
			waitForSync(5);
			clickWebElement(sheetName, "btn_maximizeAllDetails;xpath", "Maximize Shipment Details", screenName);
			waitForSync(5);
			}
		
		/**
		 *Description : Used to Select specified shipment
		 * @author A-9175
		 * @param pmyKey
		 * @throws InterruptedException
		 */
		
		public void clickCheckBox(String pmyKey) throws InterruptedException {

			selectTableRecord(data(pmyKey), "chk_selectShipment;xpath", sheetName, 1);
			waitForSync(1);

		}
		
		/**
		 * Description : Used to click and breakdown and and enter breakdown details
		 * @author A-9175
		 * @param breakDownLoc
		 * @param rcvdPcs
		 * @param rcvdWt
		 * @throws InterruptedException
		 * @throws AWTException
		 */
		public void clickBreakDownandBreakdownComplete(String breakDownLoc,String rcvdPcs,String rcvdWt) throws InterruptedException, AWTException {
			waitForSync(5);
			clickWebElement(sheetName, "btn_breakDown;xpath", "BreakDown Button", screenName);
			waitForSync(5);
			enterValueInTextbox(sheetName, "inbx_breakdownLocationCode;name", data(breakDownLoc), "BreakDown Location", screenName);
			enterValueInTextbox(sheetName, "inbx_recievedPcs;name", data(rcvdPcs), "Recieved pcs", screenName);
			enterValueInTextbox(sheetName, "inbx_recievedWgt;name", data(rcvdWt), "Recieved weight", screenName);
			waitForSync(2);
			clickWebElement(sheetName, "btn_breakDownComplete;name", "BreakDown Button", screenName);
			waitForSync(5);
			
			}
		/**
		 * Desccription : Used to close flight
		 * @author A-9175
		 * @throws InterruptedException
		 * @throws AWTException
		 */
		public void closeFlight() throws InterruptedException, AWTException {
			waitForSync(5);
			clickWebElement(sheetName, "btn_closeFlight;id", "Close Flight", screenName);
			waitForSync(5);
			clickWebElement(GenericSheet, "btn_OK;xpath", "Ok Button", screenName);
			waitForSync(2);
			try
			 {
				clickWebElement(GenericSheet, "btn_OK;xpath", "Ok Button", screenName);
				 waitForSync(2);
			 }
			 
			 catch(Exception e)
			 {
				 
			 }
			}
		
		public void clickCheckBox_ULD(String pmyKey) throws InterruptedException {

            selectTableRecord(pmyKey, "chk_selectShipment;xpath", sheetName, 1);
            waitForSync(1);

      }
		/**
		 * @author A-9175
		 * @throws InterruptedException
		 */
		public void clickYesButton() throws InterruptedException
		{
			switchToFrame("default");
			clickWebElement(GenericSheet, "btn_Yes;xpath", "Yes Button", screenName);
			 waitForSync(1);
			switchToFrame("contentFrame", "OPR367");
		}
		/**
		 * @author A-9175
		 * @throws InterruptedException
		 * @throws AWTException
		 */
		public void closeFromOPR004() throws InterruptedException, AWTException {
			waitForSync(5);
			clickWebElement(sheetName2, "btn_close;name", "BreakDown Button", screenName);
			waitForSync(2);
			}
		
		/**
		 * @author A-9175
		 * @param ULDno
		 * @param awbPre
		 * @param AwbNo
		 * @param manPcs
		 * @param manWgt
		 * @param Origin
		 * @param Destination
		 * @param statedPcs
		 * @param statedWgt
		 * @throws InterruptedException
		 * @throws AWTException
		 * Description : Adding new ULD from Import manifest screen
		 */
		
		public void addNewULD(String ULDno,String awbPre,String AwbNo,String manPcs,String manWgt,String Origin,String Destination,String statedPcs,String statedWgt) throws InterruptedException, AWTException {
			waitForSync(5);
			clickWebElement(sheetName, "btn_addUld;id", "Add New ULD Button", screenName);
			waitForSync(5);
			enterValueInTextbox(sheetName, "inbx_newULD;xpath", data(ULDno), " ULD Number ", screenName);
			clickWebElement(sheetName, "btn_newULDAdd;id","Add New ULD Button", screenName);
			enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(awbPre), " AWB Prefixr ", screenName);
			enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(AwbNo), " AWB Number ", screenName);
			waitForSync(5);
			try
			 {clickWebElement(GenericSheet, "btn_OK;xpath", "Ok Button", screenName);
				 waitForSync(2);
			 }catch(Exception e){}
			enterValueInTextbox(sheetName, "inbx_manifestedPcs;id", data(manPcs), " Manifested Pieces ", screenName);
			enterValueInTextbox(sheetName, "inbx_manifestedWgt;id", data(manWgt), " Manifested weight ", screenName);
			enterValueInTextbox(sheetName, "inbx_Origin;xpath", data(Origin), " Origin ", screenName);
			enterValueInTextbox(sheetName, "inbx_Destination;xpath", data(Destination), " Destination ", screenName);
			waitForSync(5);

		      Actions act = new Actions(driver);
		      act.moveToElement(driver.findElement(By.xpath("//div[@id='react-select-3--value']")));
		      act.click().build().perform();
		      act.sendKeys(Keys.ARROW_DOWN).build().perform();
		      act.sendKeys(Keys.ENTER).build().perform();
		     enterValueInTextbox(sheetName, "inbx_statedPcs;id", data(statedPcs), " Stated Pieces ", screenName);
			 enterValueInTextbox(sheetName, "inbx_statedWgt;id", data(statedWgt), " Stated Weight ", screenName);
		     waitForSync(5);
		     clickWebElement(sheetName, "btn_addAWB;id","Add New ULD Button", screenName);
		     waitForSync(5);
		     clickWebElement(sheetName, "btn_popUpOK;id","OK Button", screenName);
			}
		
		/**
		 * @author A-9175
		 * @throws InterruptedException
		 * @throws AWTException
		 * Description : To save details
		 */
		
		public void SaveDetails() throws InterruptedException, AWTException {
			waitForSync(5);
			clickWebElement(sheetName, "btn_Save;id", "Save Button", screenName);
			waitForSync(2);
			}
		
		/**
		 * @author A-9175
		 * @param status
		 * @throws InterruptedException
		 * Description : Verifying Nill Manifest details
		 */
		public void verifyNilDetails(String status) throws InterruptedException
		{		
			 getTextAndVerify(sheetName, "txt_nilManifest;xpath", "Nil Manifest", screenName, "Nil Manifest Details",
						data(status), "equals");
		}
		
		/**
		 * @author A-9175
		 * @throws InterruptedException
		 * @throws AWTException
		 * Description : Checking Nil Check box
		 */
		public void checkNil() throws InterruptedException, AWTException {
			waitForSync(5);
			selectMultipleCheckboxes(sheetName,"chk_nilManifest;xpath");
			waitForSync(2);
			}
		
		/**
		 * @author A-9175
		 * @throws InterruptedException
		 * @throws AWTException
		 * Description : Deleting Selected ULD 
		 */
		public void deleteULD() throws InterruptedException, AWTException {
			waitForSync(5);
			clickWebElement(sheetName, "btn_deleteUld;id", "Delete ULD ", screenName);
			waitForSync(5);
			clickWebElement(GenericSheet, "btn_OK;xpath", "Ok Button", screenName);
			waitForSync(2);
			}
		/**
		 * @author A-9175
		 * @param uld
		 * @throws InterruptedException
		 * @throws AWTException
		 * Description : Verifying Break down green tick mark
		 */
		public void verifyBreakdownSuccessfullImage() throws InterruptedException, AWTException {
			verifyElementDisplayed(sheetName, "img_BDN;xpath", " BDN Success ", screenName, " BreakDown Successfull image ");
			waitForSync(2);
			}
		/**
		 * @author A-9175
		 * @param mpcs
		 * @param mWgt
		 * @param rpcs
		 * @param rwgt
		 * @param spcs
		 * @param swgt
		 * @throws InterruptedException
		 * @throws AWTException
		 * Description : Verifying Manifested Details in Import Screen
		 */
		
		public void verifyManifestedDetails(String mpcs,String mWgt,String spcs,String swgt) throws InterruptedException, AWTException {
			waitForSync(2);
			getTextAndVerify(sheetName, "txt_ManifestedPcsCount;xpath", "Manifested Pieces", screenName, "Manifested Pieces",data(mpcs), "equals");
			waitForSync(2);
			getTextAndVerify(sheetName, "txt_ManifestedWgtCount;xpath", "Manifested Weight", screenName, "Manifested Weight",data(mWgt), "equals");
			waitForSync(2);
			getTextAndVerify(sheetName, "txt_statedPcsCount;xpath", "Stated Pieces", screenName, "Stated Pieces",data(spcs), "equals");
			waitForSync(2);
			getTextAndVerify(sheetName, "txt_statedWgtCount;xpath", "Stated Weight", screenName, "Stated Weight",data(swgt), "equals");
			}
		
		/**
		 * @author A-9175
		 * @param uld
		 * @throws InterruptedException
		 * @throws AWTException
		 * Description : enter shipment number ie,Bulk or ULD num ber in search text box
		 */
		public void enterShipmentDetailsInSearchBox(String uld) throws InterruptedException, AWTException {
			enterValueInTextbox(sheetName, "inbx_searchBox;xpath", data(uld), "Search Elemenet ", screenName);
			waitForSync(5);
			}
		
}
