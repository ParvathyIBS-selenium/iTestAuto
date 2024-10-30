package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CTM_OPR002 extends CustomFunctions {

	public CTM_OPR002(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	
	}

	

	public String sheetName = "CTM_OPR002";
	public String ScreenName = "CTM";
	String globalVarPath = "\\src\\resources\\GlobalVariable.properties";
	
	
	
	/**
	 * Description... Click list Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clicklist() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_List;xpath", "List Button", ScreenName);
		waitForSync(4);

	}
	/**
	 * Description... Click Retrieve Shipments
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickRetrieveShipments() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_RetrieveShipments;xpath", "RetrieveShipments Button", ScreenName);
		waitForSync(4);

	}
	/**
	 * Desc : Clicking on Print button
	 * @author A-9175
	 * @throws Exception
	 */
	public void clickPrint() throws Exception {
		waitForSync(2);
		try{
			clickWebElement(sheetName, "printTransferManifest;id", "Print Button", ScreenName);
			waitForSync(5);
			switchToWindow("storeParent");
			switchToWindow("child");
			driver.close();
			waitForSync(2);
			writeExtent("Pass", " Transfer Manifest Generated Sucessfully " + ScreenName + " Page");
			switchToWindow("getParent");
			waitForSync(2);	
			
			}catch(Exception e){
			 
			writeExtent("Fail", " Could not Generate Transfer Manifest " + ScreenName + " Page");
		 }
		finally{
			switchToDefaultAndContentFrame("OPR002");
		}

	}
	/**
	 * @author A-10328
	 * Description... To generate CTM number 
 * @return  CTM number
 */

public String create_CTMRef_number() 

{

	String randStr = "";

	try 

{
		String randomNum_length = "5";
		String alpha="CTM";
		int digit = Integer.parseInt(randomNum_length);
		long value1 = 1;
		long value2 = 9;

		for (int i = 1; i < digit; i++) 

{
			value1 = value1 * 10;
			value2 = value2 * 10;
			

	}

	Long randomlong = (long) (value1 + Math.random() * value2);

	randStr = randomlong.toString();

	randStr = alpha + randStr ;
	writeExtent("Pass", "CTM number is generated " + randStr);
	System.out.println("CTM number is generated " + randStr);

		
	}

		catch (Exception e) 

	{
			System.out.println("CTM number could not be generated");
			test.log(LogStatus.FAIL, "CTM number could not be generated");

		
	}
		
	return randStr;
	}

	/**
	 * @author A-9175
	 * Desc : Capture from flight details
	 * @param carrCode
	 * @param flightNo
	 * @param Date
	 * @throws Exception
	 */
	
	 public void CaptureFromFlightDetails(String carrCode,String flightNo, String Date) throws Exception {
			
			enterValueInTextbox(sheetName, "incomingFlightCarrCode;id", data(carrCode)," From Carrier Code ", ScreenName);
			waitForSync(1);
			enterValueInTextbox(sheetName, "incommingFlightNo;id", data(flightNo)," From Flight Number ", ScreenName);
			waitForSync(1);
			enterValueInTextbox(sheetName, "incommingFlightDate;id", data(Date)," Date ", ScreenName);
			waitForSync(1);
		}


/**
	  * Desc : click Retrive button
	  * @author A-9175
	  * @param carrCode
	  * @param flightNo
	  * @param Date
	  * @throws Exception
	  */
  
  public void clickRetriveShipmentButton(String carrCode,String flightNo, String Date) throws Exception {
		
			waitForSync(2);
			clickWebElement(sheetName, "retrieveShipment;id", "RetrieveShipments List Button", ScreenName);
			waitForSync(4);
		}

	/**
	 * Description... List With Date
	 * @param fromDate
	 * @param fromTime
	 * @param toDate
	 * @param toTime
	 * @throws Exception
	 */
	public void ListWithDate(String fromDate,String fromTime, String toDate,String toTime) throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		enterValueInTextbox(sheetName, "fromDate_field;xpath", fromDate,
				"From Date", ScreenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "fromTime_field;xpath", fromTime,
				"From Time", ScreenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "toDate_field;xpath", toDate,
				"To Date", ScreenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "toTime_field;xpath", toTime,
				"To Time", ScreenName);
		waitForSync(1);
		
		clickWebElement(sheetName, "btn_listRetrieveShipment;xpath", "RetrieveShipments List Button", ScreenName);
		
		waitForSync(4);

	}
	/**
	 * Description... Select Airline And Click Retrieve
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void selectAirlineAndClickRetrieve() throws InterruptedException, IOException {

		clickWebElement(sheetName, "chk_airlinePair;xpath", "PendingAirlinesPair Checkbox", ScreenName);
		waitForSync(2);
		
		clickWebElement(sheetName, "btn_Retrieve;xpath", "RetrieveShipments List Button", ScreenName);
		waitForSync(2);

	}
	
	/**
	 * Description... Select AWB
	 * @param AWBno
	 * @throws InterruptedException
	 */
	// inside window
	public void selectAWB(String AWBno) throws InterruptedException {

			String dynxpath ="(//input[@value='"+ AWBno +"']/../..//input)[1]";
			try{
			driver.findElement(By.xpath(dynxpath)).click();			
			
			}catch(Exception e){
			 
			System.out.println("Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
			writeExtent("Fail", "Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
			 
		 }
		
		
	}
	/**
	 * Description... Click AWB Checkbox 
	 * @throws Exception
	 */
	public void clickOK() throws Exception {

		clickWebElement(sheetName, "btn_Ok;xpath", "AWB Checkbox", ScreenName);
		waitForSync(2);
		switchToWindow("getParent");
		switchToFrame("contentFrame","OPR002");

	}
	/**
     * @author A-7271
     * @param pcs
     * @param wtUnit
     * Desc: add shipment
     * @throws Exception 
      */
     public void addShipment(String awbPrefix,String awbNumber,String pcs,String wt,String wtUnit) throws Exception
     {
           switchToWindow("storeParent");
           clickWebElement(sheetName, "lnk_addShipment;id", "Add shipment link", ScreenName);
           waitForSync(2);
           switchToWindow("multipleWindows");
           enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(awbPrefix),"Awb Prefix", ScreenName);
           enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(awbNumber),"Awb Number", ScreenName);
           clickWebElement(sheetName, "btn_listShipment;id", "List shipment", ScreenName);
           waitForSync(2);
           
           enterValueInTextbox(sheetName, "inbx_actualPcs;id", data(pcs),"Pieces", ScreenName);
           performKeyActions(sheetName, "inbx_actualPcs;id", "TAB", "Pieces", ScreenName);
           enterValueInTextbox(sheetName, "inbx_actualWt;name", data(wt),"Weight", ScreenName);
           selectValueInDropdown(sheetName, "lst_wtUnit;name", data(wtUnit), "Weight Unit",
                       "VisibleText");
           waitForSync(2);
           clickWebElement(sheetName, "btn_CTMdetailsOk;id", "OK button", ScreenName);
           waitForSync(3);
           switchToWindow("getParent");
           switchToFrame("default");
           switchToFrame("contentFrame","OPR002");
     }

/**
                * Desc : enter from flight details
                * @author A-7037
                * @param carrCode
                * @param flightNo
                * @param Date
                * @throws Exception
                */
                public void enterFromFlightDetails(String carrCode,String flightNo, String Date) throws Exception {
                                
                                enterValueInTextbox(sheetName, "incomingFlightCarrCode;id", data(carrCode)," From Carrier Code ", ScreenName);
                                waitForSync(1);
                                enterValueInTextbox(sheetName, "incommingFlightNo;id", data(flightNo)," From Flight Number ", ScreenName);
                                waitForSync(1);
                                enterValueInTextbox(sheetName, "incommingFlightDate;id", data(Date)," Date ", ScreenName);
                                waitForSync(1);
                                
                }

	/**
     * Description... select inbound CTM
     * @param AWBno
     * @throws InterruptedException
     * @throws AWTException
     */
     public void selectinboundCTM(String CTMRef) throws InterruptedException, AWTException 
     {             
            
                   selectValueInDropdown(sheetName, "lst_CTMtype;xpath", "Inbound", "CTM type", "VisibleText");
                   waitForSync(2);
                   enterValueInTextbox(sheetName, "CTMRef;id", CTMRef ," CTM Refrence id", ScreenName); 
                   waitForSync(2);
     }

	/**
	 * Description... Click Save Button
	 * @throws Exception
	 */
	public void clickSaveButton() throws Exception {
		waitForSync(2);
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", ScreenName);
		waitForSync(2);
		switchToAlert("Accept", "Booking Confirmation");
	}
	
	/**
	 * Description... Click AWB checkbox
	 * @param AWBno
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void clickAWBcheckbox(String AWBno) throws InterruptedException, AWTException 
	{		
	
		String dynxpath ="//tr[contains(.,'" + AWBno + "')]//input[@type='checkbox']";
		
		try{
			
			driver.findElement(By.xpath(dynxpath)).click();			
			
		 }catch(Exception e){
			 
			System.out.println("Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
			writeExtent("Fail", "Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
			Assert.assertFalse(true, "Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
			 
		 }
		
		      
	}
	
	/**
	 * Description... Verify Leg
	 * @param destination
	 * @throws Exception
	 */
	public void verifyLeg(String destination) throws Exception
	{
		String xpath="//td[contains(text(),'"+destination+"')]";
		verifyElementDisplayed(xpath, "Verifed that that the particular destination"+destination+" is present", ScreenName, "destination");
	}

public void clickSave() throws Exception {
		waitForSync(2);
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", ScreenName);
		waitForSync(3);
		
	}
	
	public void closeCTMreport() throws Exception {
		waitForSync(2);
		switchToWindow("storeParent");
		switchToWindow("child");
		driver.close();
		switchToWindow("getParent");
		waitForSync(2);
	}
	
	public void clickTransferEnd() throws Exception {
		waitForSync(2);
		clickWebElement(sheetName, "btn_TransferEnd;id", "Transfer End Button", ScreenName);
		waitForSync(5);
		switchToWindow("storeParent");
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_actualDate;id", createDateFormat("dd-MMM-YYYY", 0, "DAY", ""), "Actual Date", ScreenName);
		waitForSync(5);
		clickWebElement(sheetName, "inbx_actualTime;name","From Time", ScreenName);
		enterValueInTextbox(sheetName, "inbx_actualTime;name", "00:00", "From Time", ScreenName);
		clickWebElement(sheetName, "saveATT;id"," Save ", ScreenName);
		switchToWindow("getParent");
		waitForSync(2);

		
	}
	/**
	 * Desc : Entering outgoing Carrier Code
	 * @author A-9175
	 * @param carrCode
	 * @throws Exception
	 */
	public void enterOutGoingCarrierCode(String carrCode) throws Exception {
			
			enterValueInTextbox(sheetName, "outgoingFlightNo;id", data(carrCode)," From Carrier Code ", ScreenName);
			waitForSync(4);
		}

	/**
		 * Desc : enter from flight details
		 * @author A-9175
		 * @param carrCode
		 * @param flightNo
		 * @param Date
		 * @throws Exception
		 */
		public void ListwithFromFlightDetails(String carrCode,String flightNo, String Date) throws Exception {
			
			enterValueInTextbox(sheetName, "incomingFlightCarrCode;id", data(carrCode)," From Carrier Code ", ScreenName);
			waitForSync(1);
			enterValueInTextbox(sheetName, "incommingFlightNo;id", data(flightNo)," From Flight Number ", ScreenName);
			waitForSync(1);
			enterValueInTextbox(sheetName, "incommingFlightDate;id", data(Date)," Date ", ScreenName);
			waitForSync(1);
			clickWebElement(sheetName, "retrieveShipment;id", "RetrieveShipments List Button", ScreenName);
			waitForSync(4);
		}


		/**
		 * Desc : select shipment from Shipment section
		 * @author A-9175
		 * @param pmyKey
		 * @throws Exception
		 */
		
		public void selectAirlinefromShipmentSection(String pmyKey) throws Exception {

			switchToWindow("storeParent");
			switchToWindow("child");
			 try{
	         	String locator = xls_Read.getCellValue(sheetName, "chk_selectAWB;xpath");
	             locator=locator.replace("AWBNo",data(pmyKey));
	         		driver.findElement(By.xpath(locator)).click();
					waitForSync(2);
					writeExtent("Pass", "Successfully Selected "+data(pmyKey)+" in shipment section on "+ScreenName);}
				 catch(Exception e){
					 writeExtent("Fail", "Could not Select "+data(pmyKey)+" in shipment section on "+ScreenName);
				 }
			waitForSync(4);
		}

	/**
	 * Desc : Retreiving CMTRef no from alert
	 * @author A-9175
	 * @param alertText
	 * @return
	 */
	public String getCTMRefNumber(String alertText) {
		String actualAlertText = alertText; 
		String[] AlertTextContents = actualAlertText.split(" "); 
		return AlertTextContents[1];

	}

	/**
	 * Desc : list with CMT Ref number
	 * @author A-9175
	 * @param ctmRefNo
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void ListWithCTMREFno(String ctmRefNo) throws InterruptedException, IOException {
		enterValueInTextbox(sheetName, "CTMRef;id", ctmRefNo," CTM Ref Number ", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_List;xpath", "List Button", ScreenName);
		waitForSync(4);
		
	}

	/**
	 * Desc : Verifying Table details
	 * @author A-9175
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void verifyTableDetails(int verfCols[], String actVerfValues[],
			String pmKey) throws InterruptedException, IOException {
		waitForSync(2);
		verify_tbl_records_multiple_cols(sheetName, "table_listCTM;xpath","//td", verfCols, pmKey, actVerfValues);
	}


	/**
	 * Desc : Select the shipment from CMT002 screen
	 * @author A-9175
	 * @param AWBno
	 * @throws InterruptedException
	 */
	public void selectShipment(String AWBno) throws InterruptedException {

		String dynxpath ="(//input[@value='"+ AWBno +"']/../..//input)[2]";
		try{
		driver.findElement(By.xpath(dynxpath)).click();			
		
		}catch(Exception e){
		 
		System.out.println("Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
		writeExtent("Fail", "Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
		Assert.assertFalse(true, "Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
		 
	 }
	}
	public void verifyErrorMessage(String expErrorMsg) throws Exception {
		switchToFrame("default");
		String actErrorMsg = getElementText(sheetName, "div_errorDialogue;xpath", "Error msg", ScreenName);

		if (actErrorMsg.contains(expErrorMsg)) {
			System.out.println("found true for " + actErrorMsg);

			onPassUpdate(ScreenName, expErrorMsg, actErrorMsg, "Error message verification",
					"Error message verification");

		} else {
			onFailUpdate(ScreenName, expErrorMsg, actErrorMsg, "Error message verification",
					"Error message verification");

		}

	}
}
