package screens;
import java.util.List;
import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class MaintainULDBooking_CAP110 extends CustomFunctions {

	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "MaintainULDBooking_CAP110";
	String ScreenName = "Maintain ULD Booking";
	String screenId = "CAP110";

	public MaintainULDBooking_CAP110(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
/**
 * Description... Select ULD Booking Type
 * @param uldBookingType
 */
	public void selectULDBookingType(String uldBookingType) {
		switchToFrame("contentFrame", "CAP110");
		selectValueInDropdown(sheetName, "lst_uldBookingType;name", data(uldBookingType), "Uld Booking Type", "Value");

	}
/**
 * Description... Click List Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickListButton() throws InterruptedException, IOException {
		clickWebElement("Generic_Elements", "btn_importList;name", "List Button", ScreenName);
		waitForSync(4);
		customFuction.handleAlert("Accept", ScreenName);
	}
/**
 * Description... Click Add Link
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickAddLink() throws InterruptedException, IOException {
		clickWebElement(sheetName, "lnk_add;xpath", "Add Link", ScreenName);
	}
/**
 * Description... Add ULD Details
 * @param origin
 * @param destination
 * @param uldType
 * @param noOfULD
 * @param countour
 * @throws InterruptedException
 * @throws IOException 
 */
	public void addULDDetails(String origin, String destination, String uldType, String noOfULD, String countour)
			throws InterruptedException, IOException {

		enterValueInTextbox(sheetName, "inbx_origin;name", origin, "Origin", ScreenName);
		enterValueInTextbox(sheetName, "inbx_destination;name", destination, "Destination", ScreenName);
		clickWebElement(sheetName, "lnk_add;xpath", "Add Link", ScreenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_ULDtypefield;xpath", uldType, "Uld Type", ScreenName);
		enterValueInTextbox(sheetName, "inbx_numberOfULD;name", noOfULD, "No Of ULDs", ScreenName);
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_contour;name", countour, "contour", "Value");

	}
/**
 * Description... List ULD Booking
 * @param uldbkgid
 * @throws InterruptedException
 * @throws IOException 
 */
	public void listULDBooking(String uldbkgid) throws InterruptedException, IOException {
		enterValueInTextbox(sheetName, "txt_uldBookingID;id", data(uldbkgid), "ULD BookingId", ScreenName);
		clickWebElement("Generic_Elements", "btn_list2;name", "List Button", ScreenName);
		waitForSync(4);
	}
/**
 * Description... Add ULD Details1
 * @param uldType
 * @param noOfULD
 * @param countour
 * @throws Exception
 */
public void addULDDetails1(String uldType, String noOfULD, String countour) throws Exception {
        waitForSync(5);
        clickWebElement(sheetName, "lnk_add;xpath", "Add ULD details button", ScreenName);
        waitForSync(5);
        enterValueInTextbox(sheetName, "inbx_uldType;name", data(uldType), "Uld Type", ScreenName);
        waitForSync(5);
        enterValueInTextbox(sheetName, "inbx_numberOfULD;name", data(noOfULD), "No Of ULDs", ScreenName);
        waitForSync(5);
        keyPress("TAB");
        waitForSync(5);
        waitForSync(5);
        selectValueInDropdown(sheetName, "lst_contour;id", data(countour), "contour", "Value");
        waitForSync(5);
}

/**
 * Description... Click Get Shipment Details
 * @param awbNo
 * @param startDate
 * @param endDate
 * @throws Exception
 */

	public void clickGetShipmentDetails(String awbNo, String startDate,String endDate) throws Exception{
	      waitForSync(3);		
		clickWebElement(sheetName, "btn_getShipment;name", "Get Shipment Button", ScreenName);
		waitForSync(5);		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iCargoContentFrameCAP110");
		driver.switchTo().frame("popupContainerFrame");
		waitForSync(3);	
		enterValueInTextbox(sheetName, "inbx_awbNumber;name", data(awbNo), "awb No", ScreenName);
		enterValueInTextbox(sheetName, "inbx_startDate;xpath", data(startDate), "Start Date", ScreenName);		
		enterValueInTextbox(sheetName, "inbx_endDate;xpath", data(endDate), "End Date", ScreenName);
clearText(sheetName, "inbx_origin;name", "Origin", ScreenName);

		clickWebElement(sheetName, "btn_list;name", "List Button", ScreenName);		
		waitForSync(5);
		checkIfUnchecked(sheetName, "chk_listBooking;name", "Booking list Check Box", ScreenName);
		clickWebElement(sheetName, "btn_ok;name", "Ok Button", ScreenName);
		waitForSync(5);
	   
	   }

/**
 * Description... Validate ULD
 * @throws InterruptedException
 * @throws IOException 
 */
	public void validateULD() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_validate;name", "Validate Button", ScreenName);
		waitForSync(8);

	}
/**
 * Description... Cancel ULD Booking
 * @throws InterruptedException
 * @throws IOException 
 */
	public void cancelULDBooking() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_cancel;id", "Cancel Button", ScreenName);
		waitForSync(4);
		customFuction.handleAlert("Accept", ScreenName);

	}
/**
 * Description... Save
 * @throws InterruptedException
 * @throws IOException 
 */
	public void save() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_save;id", "Save Button", ScreenName);
		waitForSync(4);
	}
/**
 * Description... Close Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void closeButton() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_closeScreen;xpath", "Close Button", ScreenName);
		waitForSync(4);
	}
/**
 * Description... Store ULD Boooking Id
 * @throws InterruptedException
 */
	public void storeULDBoookingId() throws InterruptedException {
		By element = getElement(sheetName, "txt_uldBookingID;id");
		String uldBkgID = driver.findElement(element).getAttribute("value");
		map.put("ULD_BKG_ID", uldBkgID);

	}
/**
 * Description... Verify ULD Bkg Status Confirmed
 * @throws InterruptedException
 */
	public void verifyULDBkgStatusConfirmed() throws InterruptedException {
		By element = getElement(sheetName, "txt_uldBookingStatus;id");
		String status = driver.findElement(element).getAttribute("value");
		map.put("ULD_BKG_Status", status);

		if (status.equals("Confirmed")) {
			verifyScreenText(sheetName, "Confirmed", "Confirmed", "ULD Booking status is conirmed",
					"MaintainULDBooking");

		} else {
			verifyScreenText(sheetName, "Confirmed", status, "ULD Booking status is Nonconirmed", "MaintainULDBooking");
		}

	}
/**
 * Description... Verify ULD Bkg ID
 * @param ULDBkgId
 * @throws InterruptedException
 */
	public void verifyULDBkgID(String ULDBkgId) throws InterruptedException {
		By element = getElement(sheetName, "txt_uldBookingID;id");
		String uldBkgID = driver.findElement(element).getAttribute("value");

		if (uldBkgID.equals(data(ULDBkgId))) {
			verifyScreenText(sheetName, data(ULDBkgId), uldBkgID, "ULD Booking Id is present", "MaintainULDBooking");

		} else {
			verifyScreenText(sheetName, data(ULDBkgId), uldBkgID, "ULD Booking Id is Not present",
					"MaintainULDBooking");
		}

	}
/**
 * Description... Verify ULD Bkg Status Cancelled
 * @throws InterruptedException
 */
	public void verifyULDBkgStatusCancelled() throws InterruptedException {
		switchToFrame("contentFrame", "CAP110");
		waitForSync(5);
		By element = getElement(sheetName, "txt_uldBookingStatus;id");
		String status = driver.findElement(element).getAttribute("value");
		map.put("ULD_BKG_Status", status);

		if (status.equals("Cancelled")) {
			verifyScreenText(sheetName, "Cancelled", "Cancelled", "ULD Booking status is conirmed",
					"MaintainULDBooking");

		} else {
			verifyScreenText(sheetName, "Cancelled", status, "ULD Booking status is Nonconirmed", "MaintainULDBooking");
		}

	}
/**
 * Description... Enter Origin Destination
 * @param origin
 * @param destination
 * @throws InterruptedException
 */
	public void enterOriginDest(String origin, String destination) throws InterruptedException {

		enterValueInTextbox(sheetName, "inbx_origin;name", data(origin), "origin", ScreenName);
		enterValueInTextbox(sheetName, "inbx_destination;name", data(destination), "destination", ScreenName);

	}
/**
 * Description... Enter Own Weight Volume
 * @throws InterruptedException
 */
	public void enterOwnWtVol() throws InterruptedException {

		enterValueInTextbox(sheetName, "inbx_ownWeight;name", data("OwnWt"), "Own Weight", ScreenName);
		enterValueInTextbox(sheetName, "inbx_ownVolume;name", data("OwnVol"), "Own Volume", ScreenName);

	}
/**
 * Description... Allow Fill Up Yes Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void allowFillUp() throws InterruptedException, IOException {
		clickWebElement(sheetName, "rad_FillupYes;xpath", "FillupYes Button", ScreenName);
		waitForSync(2);

	}
/**
 * Description... Booking Free Sale
 * @throws InterruptedException
 * @throws IOException 
 */
	public void bookingFreeSale() throws InterruptedException, IOException {
		clickWebElement(sheetName, "rad_FreeSale;xpath", "FreeSale Button", ScreenName);
		waitForSync(2);

	}
/**
 * Description... Select Flight
 * @throws Exception
 */
	public void selectFlight() throws Exception {
		clickButtonSwitchWindow(sheetName, "btn_selectFlight;xpath", "Select Flight Button", ScreenName);
		waitForSync(10);

	}
/**
 * Description... Add Link
 * @throws Exception
 */
	public void addLink() throws Exception {
		clickWebElement("Generic_Elements", "lnk_add;id", "Add ULD details button", ScreenName);
	}
/**
 * Description... Enter Flight1 Details
 * @param Origin
 * @param Destination
 * @param FlightNo
 * @param FlightDate
 * @throws InterruptedException
 * @throws AWTException
 */
	public void enterFlight1Details(String Origin, String Destination, String FlightNo, String FlightDate)
			throws InterruptedException, AWTException {

		enterValueInTextbox(sheetName, "inbx_selectedFlightOrigin;xpath", data(Origin), "Origin", ScreenName);
		enterValueInTextbox(sheetName, "inbx_selectedFlightDestination;xpath", data("Destination"), "Destination",
				ScreenName);

		enterValueInTextbox(sheetName, "inbx_selectedFlightNumber;xpath", data(FlightNo), "FullFlightNo", ScreenName);
		enterValueInTextbox(sheetName, "inbx_selectedFlightDate;xpath", data(FlightDate), "FlightDate", ScreenName);
		keyPress("TAB");
	}
/**
 * Description... Select Flight Ok Button
 * @throws Exception
 */
	public void SelectFlightOkBtn() throws Exception {
		Thread.sleep(4000);
		clickWebElement("Generic_Elements", "btn_ok2;name", "ok button", ScreenName);
		waitForSync(5);

	}
	/**
	 * Description... Click Optimize button
	 * @throws IOException 
	 */
// To click on Optimize button
	public void clickOptimize() throws IOException{
		waitForSync(5);
		try {
			clickWebElement(sheetName, "button_optimize;xpath", "optimize button", ScreenName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Description... Update SCC
	 * @param SCCUpdate
	 * @throws InterruptedException
	 */
	// To update scc
	public void updateSCC(String SCCUpdate) throws InterruptedException{
		waitForSync(10);
		enterValueInTextbox(sheetName, "inbx_sccUpdate;xpath", data(SCCUpdate), "SCC", ScreenName);
	}
	// To select Particular flight details and delete that and after that add one row
	/**
	 * Description... Operation In Select Flight PopUp
	 * @param Origin
	 * @param Destination
	 * @param FlightNo
	 * @param FlightDate
	 * @throws Exception
	 */
	public void operationInSelectFlightPopUp(String Origin, String Destination, String FlightNo,String FlightDate) throws Exception{
		waitForSync(10);
		try {
			clickWebElement(sheetName, "chkbx_selectFlightPopUp;xpath", "CheckBox", ScreenName);
			waitForSync(5);
			clickWebElement(sheetName, "button_delFlight;xpath", "Delete Button", ScreenName);
			waitForSync(5);
			clickWebElement(sheetName, "button_AddRow;xpath", "Add Button", ScreenName);
			waitForSync(5);
			enterValueInTextbox(sheetName, "inbx_enterOriginInpopUp;xpath", data(Origin), "Origin", ScreenName);
			waitForSync(5);
			enterValueInTextbox(sheetName, "inbx_enterDestinationInpopUp;xpath", data(Destination), "Destination",
					ScreenName);
			waitForSync(5);
			enterValueInTextbox(sheetName, "inbx_EnterFlightInPopUp;xpath", data(FlightNo), "FullFlightNo", ScreenName);
			waitForSync(5);
			enterValueInTextbox(sheetName, "inbx_EnterFlightDateInPopUp;xpath", data(FlightDate), "FlightDate", ScreenName);
			keyPress("TAB");
			waitForSync(10);
			clickButtonSwitchtoParentWindow(sheetName, "button_okButtonInPopUp;xpath", "OK button", ScreenName);
			driver.switchTo().defaultContent();
			driver.switchTo().frame("iCargoContentFrameCAP110");
			waitForSync(5);
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * Description... Capture Irregularity
	 * @param irregularityValue
	 * @param Remarks
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	// To capture Irregularity
	public void captureIrregularity(String irregularityValue, String Remarks) throws InterruptedException, IOException{
		waitForSync(10);
		/*driver.switchTo().defaultContent();
		driver.switchTo().frame("iCargoContentFrameCAP110");*/
		try {	
			driver.switchTo().frame("popupContainerFrame");
			waitForSync(5);
			enterValueInTextbox(sheetName, "drpdown_irregularity;xpath", data(irregularityValue), "irregularityValue", "Value");
			keyPress("TAB");
			waitForSync(5);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String javaSript = "document.getElementById('CMP_Operations_Shipment_Cto_CaptureIrregularity_Remarks0').value='automation test'";
			waitForSync(5);
			js.executeScript(javaSript);
			WebDriver driver = (WebDriver) js;
			waitForSync(5);
			clickWebElement(sheetName, "button_irregularityOKButton;xpath", "OK Button", ScreenName);
			driver.switchTo().defaultContent();
			driver.switchTo().frame("iCargoContentFrameCAP110");
			waitForSync(3);
		} catch (AWTException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * Description... Add ULD Details 2
	 * @param uldType
	 * @param noOfULD
	 * @param countour
	 * @throws Exception
	 */
	public void addULDDetails2(String uldType, String noOfULD, String countour) throws Exception {
		waitForSync(5);
		
		clickWebElement(sheetName, "lnk_add;xpath", "Add ULD details button", ScreenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_UldType2;id", data(uldType), "Uld Type", ScreenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_NoOfUldType2;id", data(noOfULD), "No Of ULDs", ScreenName);
		keyPress("TAB");
		selectValueInDropdown(sheetName, "inbx_contour2;id", data(countour), "contour", "Value");

	} 
/**
 * Description... 	Add More ULD Details
 * @param origin
 * @param destination
 * @param uldType
 * @param noOfULD
 * @param countour
 * @throws InterruptedException
 * @throws IOException 
 */
	public void addMoreULDDetails(String origin, String destination, String uldType, String noOfULD, String countour)
			throws InterruptedException, IOException {

		enterValueInTextbox(sheetName, "inbx_origin;name", origin, "Origin", ScreenName);
		enterValueInTextbox(sheetName, "inbx_destination;name", destination, "Destination", ScreenName);
		clickWebElement(sheetName, "lnk_add;xpath", "Add Link", ScreenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_ULDtypefield2;xpath", uldType, "Uld Type", ScreenName);
		enterValueInTextbox(sheetName, "inbx_numberOfULD;xpath", noOfULD, "No Of ULDs", ScreenName);
		waitForSync(3);
		selectValueInDropdown(sheetName, "lst_contour;xpath", countour, "contour", "Value");

	}
/**
 * Description... Click Get Shipment
 * @throws Exception
 */
public void clickGetShipment() throws Exception{
             waitForSync(3);            
              clickWebElement(sheetName, "btn_getShipment;name", "Get Shipment Button", ScreenName);
              waitForSync(5);     
       }
 /**
  * Description...   Click Get Shipment Details With Flight Number    
  * @param flightNo
  * @param startDate
  * @param endDate
  * @throws Exception
  */
       public void clickGetShipmentDetailsWithFlightNumber(String flightNo, String startDate,String endDate) throws Exception{
             waitForSync(3);            
              clickWebElement(sheetName, "btn_getShipment;name", "Get Shipment Button", ScreenName);
              waitForSync(5);            
              driver.switchTo().defaultContent();
              driver.switchTo().frame("iCargoContentFrameCAP110");
              driver.switchTo().frame("popupContainerFrame");
              waitForSync(3);     
              enterValueInTextbox(sheetName, "inbx_FlightNo;xpath", data(flightNo), "Flight No", ScreenName);
              enterValueInTextbox(sheetName, "inbx_startDate;xpath", data(startDate), "Start Date", ScreenName);        
              enterValueInTextbox(sheetName, "inbx_endDate;xpath", data(endDate), "End Date", ScreenName);
              performKeyActions(sheetName, "inbx_endDate;xpath", "TAB", "endDate", ScreenName);
              clickWebElement(sheetName, "btn_list;name", "List Button", ScreenName);        
              waitForSync(5);
              checkIfUnchecked(sheetName, "chk_listBooking;name", "Booking list Check Box", ScreenName);
              clickWebElement(sheetName, "btn_ok;name", "Ok Button", ScreenName);
              waitForSync(5);
          
          }
 /**
  * Description... Validate And Popup      
  * @throws Exception
  */
       public void validateAndPopup() throws Exception {
              ScreenName="Maintain ULD Booking";
              switchToFrame("default");
              switchToFrame("contentFrame", "CAP110");
              switchToWindow("storeParent");
              clickWebElement(sheetName, "btn_validate;name", "Validate Button", ScreenName);
              waitForSync( 10);
              switchToFrame("default");

              try {

                     while (driver.findElement(
                                  By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
                                  .isDisplayed()) {
                           clickWebElement("Generic_Elements", "btn_yes;xpath",
                                         "yes Button", ScreenName);
                           Thread.sleep(5000);
                     }
                     
              } catch (Exception e) {
              }
       }
/**
 * Description... Update ULD Details
 * @param origin
 * @param destination
 * @param uldType
 * @param noOfULD
 * @param countour
 * @throws InterruptedException
 */
       public void updateULDDetails(String origin, String destination, String uldType, String noOfULD, String countour)
                     throws InterruptedException {

              enterValueInTextbox(sheetName, "inbx_origin;name", origin, "Origin", ScreenName);
              enterValueInTextbox(sheetName, "inbx_destination;name", destination, "Destination", ScreenName);
              enterValueInTextbox(sheetName, "inbx_ULDtypefield;xpath", uldType, "Uld Type", ScreenName);
              enterValueInTextbox(sheetName, "inbx_numberOfULD;name", noOfULD, "No Of ULDs", ScreenName);
              waitForSync(2);
              selectValueInDropdown(sheetName, "lst_contour;name", countour, "contour", "Value");

       }
/**
 * Description... Delete ULD Details
 * @throws InterruptedException
 * @throws IOException 
 */
       public void deleteULDDetails()
                     throws InterruptedException, IOException {

              clickWebElement(sheetName, "chkbx_ULDDetails;xpath", "ULD details checkbox", ScreenName);
              waitForSync(3);
              clickWebElement(sheetName, "lnk_delete;xpath", "Delete Link", ScreenName);
              waitForSync(3);
              
              
              

       }
     /**
      * Description... Add multiple ULD Details
      * @param origin
      * @param destination
      * @param uldType
      * @param noOfULD
      * @param countour
      * @param count
      * @throws InterruptedException
     * @throws IOException 
      */
       public void addmultipleULDDetails(String origin, String destination, String uldType, String noOfULD, String countour,String count)
                     throws InterruptedException, IOException {

              enterValueInTextbox(sheetName, "inbx_origin;name", origin, "Origin", ScreenName);
              enterValueInTextbox(sheetName, "inbx_destination;name", destination, "Destination", ScreenName);
              clickWebElement(sheetName, "lnk_add;xpath", "Add Link", ScreenName);
              waitForSync(6);
              String locator = (xls_Read.getCellValue(sheetName, "inbx_ULDtypefield;xpath")) +"["+count+"]";
              enterValueInTextbox(locator,uldType, "ULD type", ScreenName);
              String locator1 =( xls_Read.getCellValue(sheetName, "inbx_numberOfULD;xpath")) +"["+count+"]";
              enterValueInTextbox(locator1, noOfULD, "no of uld", ScreenName);
              String locator2 =( xls_Read.getCellValue(sheetName, "lst_contour;xpath")) +"["+count+"]";
              selectValueInDropdownWthXpath(locator2, countour, "counter", "Value");
              waitForSync(2);
       
       }
    /**
     * Description...  Verify Inner CBM  
     * @param InnerCBM
     * @param count
     * @throws InterruptedException
     */
       public void verifyInnerCBM(String InnerCBM,String count)
                     throws InterruptedException {
       
              String dynXpath= "//*[@id='FlightDetailsBody']/tr["+count+"]/td[14]";
              System.out.println(dynXpath);
              String innerCBM = driver
                     .findElement(By.xpath("//*[@id='FlightDetailsBody']/tr[1]/td[14]")).getText();
       if (innerCBM.equals(data(InnerCBM))) {
              writeExtent("Pass", "Inner CBM verified");
       } else {
              writeExtent("Fail", "Inner CBM not verified ; Expected : " + data(InnerCBM) + " Actual : " + innerCBM);
       }
	
}
/**
        * Description... Enter ULD Booking ID
        * @throws InterruptedException
        */
       public void enterULDBookingID() throws InterruptedException{
    	   enterValueInTextbox(sheetName, "txt_uldBookingID;xpath", data("ULDBookingID"), "ULD Booking ID", ScreenName);
       }
       /**
        * Description...  Enter ULD Number
        * @throws InterruptedException
        */
       public void enterULDNumber() throws InterruptedException{
    	  enterValueInTextbox(sheetName, "inbx_numberOfULD;name", data("ChangeULDNumber"), "No Of ULD", ScreenName); 
       
}
       /**
        * Description... Update Contour
        * @param contour
        * @throws Exception
        */
public void updateContour(String contour) throws Exception{
              selectValueInDropdown(sheetName, "lst_contour;id", data(contour), "contour", "Value");
               waitForSync(5);
       }
   /**
    * Description... Verify OSL    
    * @throws Exception
    */
       public void verifyOSL() throws Exception{
              String exp="0";
              String actual=driver.findElement(By.xpath("//*[@class='iCargoTableDataTd']")).getText();
              if(exp.equals(actual)){
                     customFuction.verifyScreenText(sheetName,exp,actual,"OSL value verified",ScreenName);
              }else{
                     customFuction.verifyScreenText(sheetName,exp,actual,"OSL value verified failed",ScreenName);
              }
              
               
        }
/**
 * Description... Verify Critical Flag as Y
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyCriticalFlag() throws InterruptedException, IOException {
		String actCriticalFlag = getAttributeWebElement(sheetName, "inbx_criticalFlag;name", "Critical Flag", "value",
				ScreenName);
		verifyValueOnPageContains(actCriticalFlag.toUpperCase(), "Y", "Verify Critical Flag", ScreenName,
				"Critical Flag");
	}
/**
 * Description... Verify Reason Code for Critical Flag
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyReasonCode() throws InterruptedException, IOException {
		String actCriticalFlag = getAttributeWebElement(sheetName, "inbx_displayCriticalityReason;name", "Reason Code",
				"value", ScreenName);
		verifyValueOnPageContains(actCriticalFlag.toUpperCase(), data("ULDLinkageError").toUpperCase(),
				"Verify Reason Code", ScreenName, "Reason Code");

	}

	/**
	 * Description... Verify ULD Status Confirmed/Non Confirmed
	 * 
	 * @param actStatus
	 * @param expStatus
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyULDStatus(String actStatus, String expStatus) throws InterruptedException, IOException {
		verifyValueOnPageContains(actStatus, expStatus, "Verify ULD Status is " + expStatus, ScreenName, "ULD Status");
	}

	/**
	 * Description... Verify Flight status for 3 leg shipment
	 * 
	 * @throws InterruptedException
	 *             *
	 */
	public void verifyFlightStatus3Leg(String expStatus1, String expStatus2, String expStatus3)
			throws InterruptedException {
		String actStatus1 = getElementText(sheetName, "tab_fltStatus_Row1;xpath", "Flight Status Segment 1",
				ScreenName);
		String actStatus2 = getElementText(sheetName, "tab_fltStatus_Row2;xpath", "Flight Status Segment 2",
				ScreenName);
		hover(sheetName, "tab_fltStatus_Row3;xpath");
		String actStatus3 = getElementText(sheetName, "tab_fltStatus_Row3;xpath", "Flight Status Segment 3",
				ScreenName);
		verifyValueOnPage(actStatus1, expStatus1, "Verify Flight Status Segment 1", ScreenName,
				"Flight Status Segment 1");
		verifyValueOnPage(actStatus2, expStatus2, "Verify Flight Status Segment 2", ScreenName,
				"Flight Status Segment 2");
		verifyValueOnPage(actStatus3, expStatus3, "Verify Flight Status Segment 3", ScreenName,
				"Flight Status Segment 3");
	}

	/**
	 * Description... Verify Flight status for 2 leg shipment
	 * 
	 * @throws InterruptedException
	 *             *
	 */
	public void verifyFlightStatus2Leg(String expStatus1, String expStatus2) throws InterruptedException {
		hover(sheetName,"tab_fltStatus_Row1;xpath");
		String actStatus1 = getElementText(sheetName, "tab_fltStatus_Row1;xpath", "Flight Status Segment 1",
				ScreenName);
		String actStatus2 = getElementText(sheetName, "tab_fltStatus_Row2;xpath", "Flight Status Segment 2",
				ScreenName);

		verifyValueOnPage(actStatus1, expStatus1, "Verify Flight Status Segment 1", ScreenName,
				"Flight Status Segment 1");
		verifyValueOnPage(actStatus2, expStatus2, "Verify Flight Status Segment 2", ScreenName,
				"Flight Status Segment 2");

	}

	/**
	 * Description... Changing Destination by clicking on LOV         * 
      * @throws Exception 
       */
      public void changeDestination(String destNew) throws Exception {
            clickWebElement(sheetName, "lnk_destinationLOV;id", "Destination LOV button", ScreenName);
            waitForSync(5);
            switchToWindow("storeParent");
            switchToWindow("child");
            enterValueInTextbox(sheetName, "inbx_airportCode;xpath", destNew, "Changed Destination", ScreenName);
            waitForSync(5);
            clickWebElement(sheetName, "btn_ListButtonLOV;id", "List button", ScreenName);
            waitForSync(5);
            clickWebElement(sheetName, "chkbx_airportCodeLOV;name", "Clicking Checkbox", ScreenName);
            waitForSync(5);
            clickWebElement(sheetName, "btn_OKButtonLOV;name", "Clicking OK in LOV", ScreenName);
            waitForSync(5);
            switchToWindow("getParent");
            switchToDefaultAndContentFrame("CAP110");
            
      }

/**
 * Description... Verify Segment 3 removed
 */
	public void verifySegment3Removed() {
		List noOfrowsEle = returnListOfElements("MaintainULDBooking_CAP110", "tab_flrStatus_noOfRow;xpath");

		int listSize = returnListSize(noOfrowsEle);
		if (listSize == 2)
			onPassUpdate(ScreenName, "2", "2", "Segment 3 removed",
					"1. Change the destination to transit2 \n 2. Verify destination segment removed");
		else
			onFailUpdate(ScreenName, "No of Segments is 2", "No of Segments is not 2", "Segment 3 removed",
					"1. Change the destination to transit2 \n 2. Verify destination segment removed");

	}
	/**
	 * Description... Verify ULDBooking Type DropDown Or Not
	 */
	public void verifyULDBookingTypeDropDownOrNot() {
		boolean dropDownPresent = driver.findElement(By.xpath("//select[@name='uldBookingType']")).isDisplayed();
		if(dropDownPresent==true) {
			
		}
		else {
			
		}
 		
	}
	/**
	 * Description... Add ULD Details 3
	 * @param uldType
	 * @param noOfULD
	 * @param countour
	 * @throws Exception
	 */
	public void addULDDetails3(String uldType, String noOfULD, String countour) throws Exception {
		waitForSync(5);	
		

		clickWebElement(sheetName, "lnk_add;xpath", "Add ULD details button", ScreenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_UldType3;id", data(uldType), "Uld Type", ScreenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_NoOfUldType3;id", data(noOfULD), "No Of ULDs", ScreenName);
		keyPress("TAB");
		selectValueInDropdown(sheetName, "inbx_contour3;id", data(countour), "contour", "Value");

	}
	/**
	 * Description... Verify Own Weight
	 * @throws Exception
	 */
public void verifyOwnWeight() throws Exception{
          String exp="1500";
          String act= driver.findElement(By.xpath("//*[@class='iCargoTableDataTd'][6]")).getText();
          if(exp.equals(act)){
                 customFuction.verifyScreenText(sheetName,exp,act,"Own Weight value verified",ScreenName);
          }else{
                 customFuction.verifyScreenText(sheetName,exp,act,"Own Weight value verified failed",ScreenName);
          }
          
       }
     /**
      * Description...   Check Stowage Loss
      * @throws Exception
      */
       public void checkStowageLoss()throws Exception{
          String stowage= driver.findElement(By.xpath("//*[@class='iCargoTableDataTd'][20]")).getText();
       }
       /**
        * Description... Verify Overall and Segment Status
        * @throws Exception
        */
       public void verifyOverallandSegmentStatus() throws Exception{
          By element = getElement(sheetName, "txt_uldBookingStatus;id");
              String status = driver.findElement(element).getAttribute("value");
              map.put("ULD_BKG_Status", status);

              if (status.equals("Confirmed")) {
                    verifyScreenText(sheetName, "Confirmed", "Confirmed", "ULD Booking status is conirmed",
                                  "MaintainULDBooking");

              } else {
                    verifyScreenText(sheetName, "Confirmed", status, "ULD Booking status is Nonconirmed", "MaintainULDBooking");
              }

       String actFirstSegstatus= driver.findElement(By.xpath("//*[@class='iCargoTableDataTd'][14]")).getText();
       String expFirstSegstatus="Auto-Confirmed";
       if(expFirstSegstatus.equals(actFirstSegstatus)){
              verifyScreenText(sheetName, "Confirmed", actFirstSegstatus, "ULD Booking status is conirmed",
                           "MaintainULDBooking");

       } else {
              verifyScreenText(sheetName, "Confirmed", actFirstSegstatus, "ULD Booking status is Nonconirmed", "MaintainULDBooking");
       }  
       
       String actSecondSegstatus= driver.findElement(By.xpath("//*[@class='iCargoTableDataTd'][37]")).getText();
       String expSecondSegstatus="Not-Confirmed";
       if(expSecondSegstatus.equals(actSecondSegstatus)){
              verifyScreenText(sheetName, "Confirmed", actSecondSegstatus, "ULD Booking status is conirmed",
                           "MaintainULDBooking");

       } else {
              verifyScreenText(sheetName, "Confirmed", actSecondSegstatus, "ULD Booking status is Nonconirmed", "MaintainULDBooking");
       }  
              
       }
/**
 * Description... Verify Updated SCC
 * @param SCCUpdate
 * @throws Exception
 */
 public void verifyUpdatedSCC(String SCCUpdate)throws Exception{
    	   String expected= data(SCCUpdate);
    	   String actual= driver.findElement(By.xpath("//*[@name='scc']")).getAttribute("value");
    	   if(expected.contains(actual)){
    		   customFuction.verifyScreenText(sheetName,expected,actual,"SCC Update verified",ScreenName);
    	   }
    	   else{
    		   customFuction.verifyScreenText(sheetName,expected,actual,"SCC Update verified failed",ScreenName);
    	   }
       }
/**
 * Description... Enter Own Weight Volume
 * @param OwnWt
 * @param OwnVol
 * @throws InterruptedException
 */
public void enterOwnWtVol(String OwnWt,String OwnVol) throws InterruptedException {

         enterValueInTextbox(sheetName, "inbx_ownWeight;name", data("OwnWt"), "Own Weight", ScreenName);
         enterValueInTextbox(sheetName, "inbx_ownVolume;name", data("OwnVol"), "Own Volume", ScreenName);

  }
/**
 * Description... Update Own Weight
 * @param OwnWt2
 * @throws Exception
 */
public void updateOwnWeight(String OwnWt2) throws Exception{
          enterValueInTextbox(sheetName, "inbx_ownWeight;name", data("OwnWt"), "Own Weight", ScreenName);
}

}