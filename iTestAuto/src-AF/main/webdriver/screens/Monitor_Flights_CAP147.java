package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class Monitor_Flights_CAP147 extends CustomFunctions {

	String sheetName="Monitor_Flights_CAP147";
	String screenName="Monitor_Flights : CAP147";

	public Monitor_Flights_CAP147(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}
	/**
	 * @author A-9175
	 * Description... List Flight
	 * @param FlightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void listFlight(String carrCode,String FlightNumber, String flightDate)throws InterruptedException, AWTException, IOException {
		enterValueInTextbox(sheetName, "inbx_carrCode;name",data(carrCode), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name",data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;name",data(flightDate), "Flight Date", screenName);
		clickWebElementByWebDriver(sheetName, "btn_List;id", "List", screenName);
		waitForSync(3);

	}
	/**
	 * @author A-9847
	 * Desc-Verify total consumed capacity
	 * @param shipmentWgt
	 * @param shipmentVol
	 * @throws InterruptedException
	 */
	public void verifyTotalConsumedCapacity(String shipmentWgt,String shipmentVol) throws InterruptedException{

		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		int totalWgt= Integer.parseInt(shipmentWgt);
		int totalVol= Integer.parseInt(shipmentVol);
		getTextAndVerify(sheetName, "lbl_totalConsumedCapacityWgt;xpath", "Total Consumed Capacity Weight", screenName, "Verification of Total Consumed Capacity Weight",
		Integer.toString(totalWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_totalConsumedCapacityVol;xpath", "Total Consumed Capacity Volume", screenName, "Verification of Total Consumed Capacity Volume",
		Integer.toString(totalVol)+"CBM", "equals");

		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");

		}
	/**
	 * @author A-8783
	 * @param awbNo
	 */
	public void selectAWBFromConfirmedBkg(String awbNo) {
		System.out.println(data(awbNo));
		String locator = xls_Read.getCellValue(sheetName, "chk_selectCheckBox;xpath");
		locator=locator.replace("awbno", data(awbNo));
		try {
		driver.findElement(By.xpath(locator)).click();
		 writeExtent("Pass", "Selected checkbox from "+ screenName + " Page with awb no: " +data(awbNo));

		}
		catch (Exception e) {
			writeExtent("Fail","Could not select check box");
		}
		
	}
	/**
	 * @author A-8783
	 * @param option
	 * @throws InterruptedException
	 * @throws IOException
	 */
		public void selectOptions(String option) throws InterruptedException, IOException {
			selectOptionInList(sheetName, "btn_dropdown;xpath", "lst_selectOption;xpath", option,
					"Select option");
			switchToFrame("frameName","popupContainerFrame");
			waitForSync(2);
		}
		/**
		 * @author A-8783
		 * @param origin
		 * @param destination
		 * @param shippingDate
		 * @param aircraft
		 * @param flightcode
		 * @param i
		 * @param checkAll
		 * @throws InterruptedException
		 * @throws IOException
		 */
		public void selectFlightfromPopup(String origin, String destination, String shippingDate, String aircraft, String flightcode,String index, boolean checkAll) throws InterruptedException, IOException {
			try {
				waitForSync(2);
				clickWebElement(sheetName, "btn_editIcon;xpath", "Edit icon", screenName);
				waitForSync(2);
				enterValueInTextbox(sheetName,"inbx_flightPopupOrigin;id", data(origin), "Enter origin", screenName);
				enterValueInTextbox(sheetName,"inbx_flightPopupDestination;id", data(destination), "Enter destination", screenName);
				enterValueInTextbox(sheetName,"inbx_flightPopupShippingDate;id", data(shippingDate), "Enter shipping date", screenName);
				clickWebElement(sheetName, "btn_selectAircraft;id", "Select dropdown", screenName);
				waitForSync(2);
			
				if(checkAll) {
				clickWebElement(sheetName, "chk_checkAll;xpath", "Select dropdown", screenName);
				writeExtent("Pass", "Checked all as aircraftType on "
						+ screenName + " Page");
			}
			else {
				String locator=xls_Read.getCellValue(sheetName, "chk_aircraftType;xpath");
				locator=locator.replace("aircraftType", data(aircraft));
				driver.findElement(By.xpath(locator)).click();
				writeExtent("Pass", "Entered " + data(aircraft) + " as aircraftType on "
						+ screenName + " Page");
			}
				
				//Enter flight code as keyword
				enterValueInTextbox(sheetName,"inbx_SearchFlightNumber;xpath", flightcode, "Enter keyword", screenName);
				waitForSync(3);
				
				//Select flight based on index
				String locator1=xls_Read.getCellValue(sheetName, "btn_selectFirstFlightPopup;xpath").replace("value", index);
				 driver.findElement(By.xpath(locator1)).click();
				 writeExtent("Pass", "Selected Flight from "+ screenName + " Page");
						
				clickWebElement(sheetName, "btn_selectFlightpopupOK;xpath", "OK", screenName);
				
			}
			catch (Exception e) {
				writeExtent("Fail","Could not select flight from the select flight pop up");
			}
			}

	
	/**
	 * @Description : Total capacity Sales with OVB Details
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param OVBwgt
	 * @param AircraftVol
	 * @param OVBvol
	 * @throws InterruptedException
	 */
	public void totalCapacitySales(String Aircraftwgt, String OVBwgt, String AircraftVol, String OVBvol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		int totalCapacitySalesWgt= Integer.parseInt(Aircraftwgt)+Integer.parseInt(OVBwgt);
		int totalCapacitySalesVol= Integer.parseInt(AircraftVol)+Integer.parseInt(OVBvol);

		
		getTextAndVerify(sheetName, "lbl_totalCapacitySalesWgt;xpath", "Total Capacity Sales Weight", screenName, "Total Capacity Sales Weight",Integer.toString(totalCapacitySalesWgt)+" "+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_totalCapacitySalesVol;xpath", "Total Capacity Sales Volume", screenName, "Total Capacity Sales Volume",Integer.toString(totalCapacitySalesVol)+" "+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	/** @Description : Total Capacity Handling with OVB Details
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param AircraftVol
	 * @throws InterruptedException
	 */
	public void totalCapacityHandling(String Aircraftwgt, String AircraftVol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		
		getTextAndVerify(sheetName, "lbl_totalCapacityHandlingWgt;xpath", "Total Capacity Handling Weight", screenName, "Total Capacity Handling Weight",Aircraftwgt+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_totalCapacityHandlingVol;xpath", "Total Capacity Handling Volume", screenName, "Total Capacity Handling Volume",AircraftVol+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
	}
	/**
	 * @Description : Total Allotment Details
	 * @author A-9175
	 * @param AllotmentWgt
	 * @param AllotmentVol
	 * @throws InterruptedException
	 */
	
	public void totalAllotment(String AllotmentWgt, String AllotmentVol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		
		getTextAndVerify(sheetName, "lbl_totalAllotmentWgt;xpath", "Total Allotment Weight", screenName, "Total Allotment Weight",AllotmentWgt+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_totalAllotmentVol;xpath", "Total Allotment Volume", screenName, "Total Allotment Volume",AllotmentVol+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
}
	/** @Description :Remaining Capacity FS Sales details with ALLOTMENT
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param OVBwgt
	 * @param AircraftVol
	 * @param OVBvol
	 * @param AllotmentWgt
	 * @param AllotmentVol
	 * @throws InterruptedException
	 */
	public void remainingCapacityFSSalesAllotment(String Aircraftwgt, String OVBwgt, String AircraftVol, String OVBvol,String AllotmentWgt,String AllotmentVol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		int remainingCapacitySalesFSWgt= Integer.parseInt(Aircraftwgt)+Integer.parseInt(OVBwgt)-Integer.parseInt(AllotmentWgt);
		int remainingCapacitySalesFSVol= Integer.parseInt(AircraftVol)+Integer.parseInt(OVBvol)-Integer.parseInt(AllotmentVol);

		
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesWgt;xpath", "Remaining FS Capacity - Sales Weight", screenName, "Remaining FS Capacity - Sales Weight",Integer.toString(remainingCapacitySalesFSWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesVol;xpath", "Remaining FS Capacity - Sales Volume", screenName, "Remaining FS Capacity - Sales Volume",Integer.toString(remainingCapacitySalesFSVol)+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	

	/**
	 * @Description : Remaining Capacity FS Handling details with ALLOTMENT
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param AircraftVol
	 * @param AllotmentWgt
	 * @param AllotmentVol
	 * @throws InterruptedException
	 */
	public void remainingcapacityFSHandlingAllotment(String Aircraftwgt, String AircraftVol, String AllotmentWgt,String AllotmentVol) throws InterruptedException {
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		int remainingCapacityHandlingFSWgt= Integer.parseInt(Aircraftwgt)-Integer.parseInt(AllotmentWgt);
		int remainingCapacityHandlingFSVol= Integer.parseInt(AircraftVol)-Integer.parseInt(AllotmentVol);

		
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingWgt;xpath", "Remaining FS Capacity - Handling Weight", screenName, "Remaining FS Capacity - Handling Weight",Integer.toString(remainingCapacityHandlingFSWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingVol;xpath", "Remaining FS Capacity - Handling Volume", screenName, "Remaining FS Capacity - Handling Volume",Integer.toString(remainingCapacityHandlingFSVol)+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	/**
	 * @Description: Remaining ALLOTMENT capacity deatils
	 * @author A-9175
	 * @param AllotmentWgt
	 * @param AllotmentVol
	 * @param bookedAllotmentWgt
	 * @param bookedAllotmentVol
	 * @throws InterruptedException
	 */
	public void remainingAllotmentCapacity(String AllotmentWgt, String AllotmentVol, String bookedAllotmentWgt,String bookedAllotmentVol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		int remainingAllotmentCapacityWgt= Integer.parseInt(AllotmentWgt)-Integer.parseInt(bookedAllotmentWgt);
		int remainingAllotmentCapacityVol= Integer.parseInt(AllotmentVol)-Integer.parseInt(bookedAllotmentVol);

		
		getTextAndVerify(sheetName, "lbl_remainingAllotmentCapacityWgt;xpath", "Remaining Allotment Capacity Weight", screenName, "Remaining FS Capacity - Handling Weight",Integer.toString(remainingAllotmentCapacityWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingAllotmentCapacityVol;xpath", "Remaining Allotment Capacity Volume", screenName, "Remaining FS Capacity - Handling Volume",Integer.toString(remainingAllotmentCapacityVol)+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	/**
	 * @Description : Remaining Capacity FS Handling details without ALLOTMENT and with OVB deatils
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param OVBwgt
	 * @param AircraftVol
	 * @param OVBvol
	 * @param BookedWgt
	 * @param BookedVol
	 * @throws InterruptedException
	 */
	public void remainingCapacityFSSaleswithoutAllotment(String Aircraftwgt, String OVBwgt, String AircraftVol, String OVBvol,String BookedWgt,String BookedVol) throws InterruptedException {
	
	switchToFrame("frameName","popupContainerFrame");
	waitForSync(2);
	
	int remainingCapacitySalesFSWgt= Integer.parseInt(Aircraftwgt)+Integer.parseInt(OVBwgt)-Integer.parseInt(BookedWgt);
	int remainingCapacitySalesFSVol= Integer.parseInt(AircraftVol)+Integer.parseInt(OVBvol)-Integer.parseInt(BookedVol);

	
	getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesWgt;xpath", "Remaining FS Capacity - Sales Weight", screenName, "Remaining FS Capacity - Sales Weight",Integer.toString(remainingCapacitySalesFSWgt)+"kg", "equals");
	getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesVol;xpath", "Remaining FS Capacity - Sales Volume", screenName, "Remaining FS Capacity - Sales Volume",Integer.toString(remainingCapacitySalesFSVol)+"CBM", "equals");

	
	waitForSync(2);
	switchToFrame("default");
	switchToFrame("contentFrame","CAP147");
	
}
	/**
	 * @Description : Remaining Capacity FS Handling details without ALLOTMENT
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param AircraftVol
	 * @param BookedWgt
	 * @param BookedVol
	 * @throws InterruptedException
	 */
	public void remainingcapacityFSHandlingwithoutAllotment(String Aircraftwgt, String AircraftVol, String BookedWgt,String BookedVol) throws InterruptedException {
	
	switchToFrame("frameName","popupContainerFrame");
	waitForSync(2);
	
	int remainingCapacityHandlingFSWgt= Integer.parseInt(Aircraftwgt)-Integer.parseInt(BookedWgt);
	int remainingCapacityHandlingFSVol= Integer.parseInt(AircraftVol)-Integer.parseInt(BookedVol);

	
	getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingWgt;xpath", "Remaining FS Capacity - Handling Weight", screenName, "Remaining FS Capacity - Handling Weight",Integer.toString(remainingCapacityHandlingFSWgt)+"kg", "equals");
	getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingVol;xpath", "Remaining FS Capacity - Handling Volume", screenName, "Remaining FS Capacity - Handling Volume",Integer.toString(remainingCapacityHandlingFSVol)+"CBM", "equals");

	
	waitForSync(2);
	switchToFrame("default");
	switchToFrame("contentFrame","CAP147");
	
}

	
	/**
	 * @author A-9844
	 * Desc-To verify error message
	 * @param expErrorMessage
	 * @throws InterruptedException
	 */
public void verifyErrorMessage(String expErrorMessage) throws InterruptedException{
		
		String locator = xls_Read.getCellValue("Generic_Elements", "htmlDiv_errorMsg;xpath");
		if((driver.findElements(By.xpath(locator)).size()>0)){
        	String actErrorMessage =getElementText("Generic_Elements", "htmlDiv_errorMsg;xpath", "Error Message", screenName);
    		
    		if(actErrorMessage.equals(data(expErrorMessage))){
    			
    			writeExtent("Pass","Successfully verified Error message '"+ data(expErrorMessage)+"' on "+screenName+" Page");
    			System.out.println("Error message "+ expErrorMessage+" shown on "+screenName);
    		}
    		else
    		{
    			writeExtent("Fail","Error message '"+ data(expErrorMessage)+"'not displayed on "+screenName+" Page");
    		}	
	   }
	}

/**
* To click Confirmed Booking Link
* @throws InterruptedException
* @throws IOException
*/

public void clickConfirmedBookingLink() throws InterruptedException, IOException{
clickWebElement(sheetName, "btn_confirmedLink;xpath", "Confirmed Link", screenName);
waitForSync(1);
}

	/**
	 * @Description : Verifying depletion details
	 * @author A-9175
	 * @param AircraftWgt
	 * @param AircraftVol
	 * @param shipmentWgt
	 * @param shipmentVol
	 * @throws InterruptedException
	 */

	public void verifyDepletionAfterFSBooking(String AircraftWgt, String AircraftVol,String shipmentWgt,String shipmentVol) throws InterruptedException 
	{
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		int remainingWgt= Integer.parseInt(AircraftWgt)-Integer.parseInt(shipmentWgt);
		double remainingVol= Integer.parseInt(AircraftVol)-Double.parseDouble(shipmentVol);
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesWgt;xpath", "Remaining FS Capacity Sales Weight", screenName, "Verification of Remaining FS Capacity Sales Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesVol;xpath", "Remaining FS Capacity Sales Volume", screenName, "Verification of Remaining FS Capacity Sales Volume",
				Double.toString(remainingVol)+"CBM", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingWgt;xpath", "Remaining FS Capacity Handling Weight", screenName, "Verification of Remaining FS Capacity Handling Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingVol;xpath", "Remaining FS Capacity Handling Volume", screenName, "Verification of Remaining FS Capacity Handling Volume",
				Double.toString(remainingVol)+"CBM", "equals");
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	
	/**
	 * To click Allotments
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void clickAllotments() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_allotments;xpath", "Allotments", screenName);
		waitForSync(2);
	}
	/**
	 * @author A-9175
	 * Description... Click view capacity button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickViewCapacitySummary() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_capacitySummary;id", "Capacity Summary", screenName);
		waitForSync(2);
		
	}
    /**
   * To click close button on View Capacity summary Popup
   * @throws InterruptedException
   * @throws IOException
   */
                         public void clickCloseOnViewCapacitySummary() throws InterruptedException, IOException{
         
         switchToFrame("frameName","popupContainerFrame");
         clickWebElement(sheetName, "btn_viewCapSummaryClose;id", "Close on View Capacity Summary", screenName);
         waitForSync(1);
         switchToFrame("default");
         switchToFrame("contentFrame","CAP147");
         
   }

	
	/**
	 * To release a specific Allotment by passing the Allotment ID
	 * @param allotmentId
	 * @throws InterruptedException
	 * @throws IOException
	 */

  public void releaseAllotments(String allotmentId) throws InterruptedException, IOException{
	  
	  String locator= xls_Read.getCellValue(sheetName, "btn_threedots;xpath");
	  locator=locator.replace("*",data(allotmentId));
          driver.findElement(By.xpath(locator)).click();
	  waitForSync(1);
	  clickWebElement(sheetName, "btn_release;id", "Release", screenName);
	  waitForSync(2);
	}
  /**
   * To verify flight status by passing the corresponding expected Status
   * @param Status
   * @throws InterruptedException
   */
  
 public void verifyFlightStatus(String status) throws InterruptedException{
	   
	   getTextAndVerify(sheetName, "lbl_flightStatus;xpath", "Flight Status", screenName, "Verification of Flight Status",data(status), "equals");  
	   
   }
	
	/**
	 * @author A-9175
	 * Description... verify Depletion Details
	 * @param AircraftWgt
	 * @param AircraftVol
	 * @param shipmentWgt
	 * @param shipmentVol
	 * @throws InterruptedException
	 */
	public void verifyDepletionDetailsAfterFSBooking(String AircraftWgt, String AircraftVol,String shipmentWgt,String shipmentVol) throws InterruptedException 
	{
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		int remainingWgt= Integer.parseInt(AircraftWgt)-Integer.parseInt(shipmentWgt);
		int remainingVol= Integer.parseInt(AircraftVol)-Integer.parseInt(shipmentVol);
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesWgt;xpath", "Remaining FS Capacity Sales Weight", screenName, "Verification of Remaining FS Capacity Sales Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesVol;xpath", "Remaining FS Capacity Sales Volume", screenName, "Verification of Remaining FS Capacity Sales Volume",
				Integer.toString(remainingVol)+"CBM", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingWgt;xpath", "Remaining FS Capacity Handling Weight", screenName, "Verification of Remaining FS Capacity Handling Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingVol;xpath", "Remaining FS Capacity Handling Volume", screenName, "Verification of Remaining FS Capacity Handling Volume",
				Integer.toString(remainingVol)+"CBM", "equals");
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}

	/**
	 * @author A-9175
	 * Description... verify Depletion Details for  flight after booking with allotment
	 * @param AircraftWgt
	 * @param AircraftVol
	 * @param shipmentWgt
	 * @param shipmentVol
	 * @throws InterruptedException
	 */
	public void verifyDepletionDetailsAfterAllotmentBooking(String AircraftWgt, String AircraftVol,
			String FlightAltWgt,String shipmentWgt,String FlightAltVol,String shipmentVol) throws InterruptedException 
	{
		
		
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		int remainingWgt= Integer.parseInt(AircraftWgt)-Integer.parseInt(FlightAltWgt);
		int remainingVol= Integer.parseInt(AircraftVol)-Integer.parseInt(FlightAltVol);
		int remainingAltWgt=Integer.parseInt(FlightAltWgt)-Integer.parseInt(shipmentWgt);
		int remainingAltVol=Integer.parseInt(FlightAltVol)-Integer.parseInt(shipmentVol);
		
		getTextAndVerify(sheetName, "lbl_totalAllotmentWgt;xpath", "Total Allotment Weight", screenName, "Total Allotment Weight",FlightAltWgt+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_totalAllotmentVol;xpath", "Total Allotment Volume", screenName, "Total Allotment Volume",FlightAltVol+"CBM", "equals");

		
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesWgt;xpath", "Remaining FS Capacity Sales Weight", screenName, "Verification of Remaining FS Capacity Sales Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesVol;xpath", "Remaining FS Capacity Sales Volume", screenName, "Verification of Remaining FS Capacity Sales Volume",
				Integer.toString(remainingVol)+"CBM", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingWgt;xpath", "Remaining FS Capacity Handling Weight", screenName, "Verification of Remaining FS Capacity Handling Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingVol;xpath", "Remaining FS Capacity Handling Volume", screenName, "Verification of Remaining FS Capacity Handling Volume",
				Integer.toString(remainingVol)+"CBM", "equals");
		getTextAndVerify(sheetName, "lbl_remainingAllotmentCapacityWgt;xpath", "Remaining Allotment Capacity  Weight", screenName, "Verification of Remaining Allotment Capacity Weight",
				Integer.toString(remainingAltWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingAllotmentCapacityVol;xpath", "Remaining Allotment Capacity Volume", screenName, "Verification of Remaining Allotment Capacity  Volume",
				Integer.toString(remainingAltVol)+"CBM", "equals");
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}

	
	

}
