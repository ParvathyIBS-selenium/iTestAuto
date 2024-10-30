	package screens;

	import java.awt.AWTException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

	import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

	import com.relevantcodes.extentreports.LogStatus;

	import common.CustomFunctions;
import common.WebFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

	public class CaptureMiscellaneousDiscrepancy_OPR045 extends CustomFunctions {

		public CaptureMiscellaneousDiscrepancy_OPR045(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
			super(driver, excelReadWrite, xls_Read2);
		}

		public String sheetName = "CaptureMiscDiscrepancy_OPR045";
		public String screenName = "CaptureMiscellaneousDiscrepancy";
		String globalVarPath = "\\src\\resources\\GlobalVariable.properties";
		
		public void selectDiscType(String DiscType) throws InterruptedException {
			Thread.sleep(2000);
			selectValueInDropdown(sheetName, "lst_DiscrepancyType;name", DiscType, "DiscType", "VisibleText");
			Thread.sleep(2000);
		}
		
		public void listAWB(String awbNo, String ShipmentPrefix, String ScreenName) throws InterruptedException, IOException {
			waitForSync(2);
			enterValueInTextbox("Generic_Elements", "inbx_shipmentPrefix;xpath", data(ShipmentPrefix), "Shipment Prefix",
					ScreenName);
			enterValueInTextbox("Generic_Elements", "inbx_AWBnumber;xpath", awbNo, "AWB No", ScreenName);
			clickWebElement("Generic_Elements", "btn_List;xpath", "List Button", ScreenName);
			waitForSync(4);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath","yes Button", screenName);
			switchToFrame("contentFrame", "OPR045");
			Thread.sleep(2000);

		}
		
		public void selectDiscCode(String DiscCode) throws InterruptedException {
			Thread.sleep(2000);
			selectValueInDropdown(sheetName, "lst_Discrepancycode;name", DiscCode, "DiscCode", "VisibleText");
			Thread.sleep(2000);
		}
		public void enterDiscDetails(String Pieces, String Weight) throws InterruptedException, AWTException {
			Thread.sleep(2000);
			enterValueInTextbox(sheetName, "inbx_DiscrepancyPieces;name", data(Pieces), "Pieces", screenName);
			keyPress("TAB");
			waitForSync(2);
			keyRelease("TAB");
			enterValueInTextbox(sheetName, "inbx_DiscrepancyWeight;name", data(Weight), "Weight", screenName);
			keyPress("TAB");
			waitForSync(2);
			keyRelease("TAB");
		}

		/**
			 * @author A-9847
			 * @Desc To enter flight details
			 * @param carrierCode
			 * @param fltNum
			 * @param fltDate
			 * @throws InterruptedException
			 */
			public void enterFlightDetails(String carrierCode,String fltNum,String fltDate) throws InterruptedException{
				enterValueInTextbox(sheetName, "inbx_carrierCode;id", data(carrierCode), "Carrier Code",screenName);
				enterValueInTextbox(sheetName, "inbx_flightNum;id", data(fltNum), "Flight Number", screenName);		
				enterValueInTextbox(sheetName, "inbx_flightDate;id", data(fltDate), "Flight Date",screenName);	
				waitForSync(2);
				
			}
		public void enterRemarks(String remarks) throws InterruptedException, AWTException {
			Thread.sleep(2000);
			enterValueInTextbox(sheetName, "inbx_Discrepancyremarks;name", remarks , "Remarks", screenName);
			keyPress("TAB");
			waitForSync(2);
			keyRelease("TAB");
		}
		/**@author A-9847
		 * @Des To click on List Button 
		 * @throws InterruptedException
		 * @throws IOException
		 */
		public void clickList() throws InterruptedException, IOException{
			clickWebElement("Generic_Elements", "btn_List;xpath", "List Button", screenName);
			waitForSync(5);

		}
		/**
		 * @author A-9847
		 * @Desc To click 'yes' or 'no' on the warning message pop-up
		 * @param opt
		 * @throws InterruptedException
		 * @throws IOException
		 */

		public void clickYesNo(String opt) throws InterruptedException, IOException{

			switchToFrame("default");
			if(opt.equals("yes"))
				clickWebElement("Generic_Elements", "btn_yes;xpath","Yes Button", screenName);
			else
				clickWebElement("Generic_Elements", "btn_no;xpath","No Button", screenName);
			switchToFrame("contentFrame", "OPR045");
		}

		/**
		 * @Desc To enter the AWB Number
		 * @param awbNo
		 * @param ShipmentPrefix
		 * @throws InterruptedException
		 */
		public void enterAWB(String awbNo, String ShipmentPrefix) throws InterruptedException{

			enterValueInTextbox("Generic_Elements", "inbx_shipmentPrefix;xpath", data(ShipmentPrefix), "Shipment Prefix",screenName);
			enterValueInTextbox("Generic_Elements", "inbx_AWBnumber;xpath", data(awbNo), "AWB No", screenName);

		}

		/**
		 * To verify the EAWB indicator
		 */

		public void verifyEawbIndicator(){

			try{
				String loc = xls_Read.getCellValue(sheetName, "btn_eawbIndicator;xpath");
				if(driver.findElement(By.xpath(loc)).isDisplayed())
					writeExtent("Pass", "Successfully Verified Eawb Indicator on "+ screenName);
			} catch (Exception e) {
				writeExtent("Fail", "Could not Verify Eawb Indicator on " + screenName);
			}

		}

		/**
		 * To verify the Warning Message displayed on the pop-up
		 * @param expText
		 */
		public void verifyWarningMsg(String expText){

			try{
				switchToFrame("default");
				String loc = xls_Read.getCellValue("Generic_Elements", "txt_AlertText;xpath");
				String actText=driver.findElement(By.xpath(loc)).getText();
				verifyScreenTextWithExactMatch(sheetName, expText, actText, "Warning message","Warning message");
				switchToFrame("contentFrame", "OPR045");
			}
			catch(Exception e) {
				writeExtent("Fail", " Failed to verify the Warning message "+expText+ " on " + screenName);
			}

		}

		/**
		 * @author A-9847
		 * @Desc To verify the Discrepancy code and remarks
		 * @param disCode
		 * @param remarks
		 */
		public void verifyDiscDetails(String disCode,String remarks){

			try{
				String loc = xls_Read.getCellValue(sheetName, "inbx_Discrepancyremarks;name");
				String actText=driver.findElement(By.name(loc)).getText();
				verifyScreenTextWithExactMatch(sheetName, data(remarks), actText, "Remarks","Remarks");
				String loc1 = xls_Read.getCellValue(sheetName,"txt_disCode;xpath").replace("*",data(disCode));
				String txt=driver.findElement(By.xpath(loc1)).getAttribute("selected");
				if(txt.equals("true"))
					writeExtent("Pass", "Successfully Verified Discrepancy Code as "+ data(disCode)+ " on "+ screenName);
				else
					writeExtent("Fail", "Failed to verify Discrepancy Code as "+ data(disCode)+ " on "+ screenName);
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to verify Discrepancy Details on "+ screenName); 
			}
		}
		/**
		 * To click on create Discrepancy button
		 * @throws InterruptedException
		 * @throws AWTException
		 * @throws IOException
		 */
		public void clickCreateDisc() throws InterruptedException, AWTException, IOException {

			clickWebElement(sheetName, "btn_Save;name", "Create Discrepancy Button", screenName);
			waitForSync(2);

		}
		/**
		 * @desc To verify whether the Remarks field is enabled
		 */
		public void verifyRemarksFieldEnabled(){
			try{
				String loc = xls_Read.getCellValue(sheetName, "inbx_Discrepancyremarks;name");
				boolean val=driver.findElement(By.name(loc)).isEnabled();
				if(val)
					writeExtent("Pass", "Verified Remarks Field is enabled on "+ screenName);
				else
					writeExtent("Fail", "Failed to verify Remarks Field is enabled on "+ screenName);

			}catch(Exception e){
				writeExtent("Fail", "Failed to verify Remarks Field is enabled on "+ screenName); 
			}

		} 

		/**
		 * @desc To verify whether the Discrepancy code field is enabled
		 */
		public void  verifyDisCodeFieldEnabled(){

			try{
				String loc = xls_Read.getCellValue(sheetName, "lst_Discrepancycode;name");
				boolean val=driver.findElement(By.name(loc)).isEnabled();
				if(val)
					writeExtent("Pass", "Verified Discrepancy Code field is enabled on "+ screenName);
				else
					writeExtent("Fail", "Failed to verify Discrepancy Code field is enabled on "+ screenName);

			} 
			catch(Exception e){
				writeExtent("Fail", "Failed to verify Discrepancy Code field is enabled on "+ screenName); 
			}

		}

		/**
		 * @Des To verify whether the AWB field got cleared
		 */

		public void verifyAwbFieldCleared(){

			try{

				String loc = xls_Read.getCellValue("Generic_Elements", "inbx_AWBnumber;xpath");
				String val=driver.findElement(By.xpath(loc)).getAttribute("value");

				String loc1 = xls_Read.getCellValue("Generic_Elements", "inbx_shipmentPrefix;xpath");
				String val1=driver.findElement(By.xpath(loc1)).getAttribute("value");

				if(val.equals("") && val1.equals(""))
					writeExtent("Pass", "Verified AWB field got cleared on "+ screenName);
				else
					writeExtent("Fail", "Failed to verify AWB field got cleared on "+ screenName);	

			}catch(Exception e){
				writeExtent("Fail", "Failed to verify AWB field got cleared on "+ screenName); 
			}

		}
		
		
		public void createDisc() throws InterruptedException, AWTException, IOException {
		
			clickWebElement(sheetName, "btn_Save;name", "Create Discrepancy Button", screenName);
			Thread.sleep(2000);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath","yes Button", screenName);
			switchToFrame("contentFrame", "OPR045");
			Thread.sleep(2000);
		}
		/**
         * @author A-9478
         * Desc: Enter Discrepancy pieces and weight
         * @param Pieces
         * @param Weight
         * @throws InterruptedException
         * @throws AWTException
         */
         public void enterDiscPcsAndWgt(String Pieces, String Weight) throws InterruptedException, AWTException {                  
               enterValueInTextbox(sheetName, "inbx_DiscPcs;id", data(Pieces), "Pieces", screenName);              
               enterValueInTextbox(sheetName, "inbx_DiscWgt;id", data(Weight), "Weight", screenName);              
               waitForSync(2);
         }

		public void closeDisc(String Msg) throws InterruptedException, AWTException, IOException {
			
			clickWebElement(sheetName, "btn_CloseDisc;name", "Close Discrepancy Button", screenName);
			Thread.sleep(2000);
			switchToFrame("default");
	   		//*[@id='ic-sd-msgc']
	   		String actText = driver.findElement(By.xpath("//*[@id='ic-sd-msgc']")).getText();

	   		String expText=Msg;
	   		if (actText.contains(expText)) {
	   			verifyScreenText(sheetName, expText, actText, "Discrepancy closed", screenName);

	   		} else {
	   			verifyScreenText(sheetName, expText, actText, "Discrepancy is not closed",
	   					screenName);
	   		}
	   		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes Button", screenName);
	   		Thread.sleep(4000);

		}


	}
	

