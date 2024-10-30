package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;


public class AFLS_Booking extends CustomFunctions

{
	String SheetName ="AFLS_Booking";
	String ScreenName ="AFLS_screen";


	public AFLS_Booking(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}


	/**
	 * @author A-9847
	 * @Desc To select the given Title and the corresponding Sub-title Tab
	 * @param titleTab
	 * @param subTitleTab
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */
	public void selectTitleAndSubTitleTab(String titleTab,String subTitleTab) throws InterruptedException, IOException, AWTException
	{	
		try{		
			String title = xls_Read.getCellValue(SheetName, "lst_selectTitle;xpath").replace("*", data(titleTab));
			driver.findElement(By.xpath(title)).click();
			waitForSync(2);
			String subtitle = xls_Read.getCellValue(SheetName, "lst_selectSubTitle;xpath").replace("*", data(subTitleTab));
			driver.findElement(By.xpath(subtitle)).click();
			waitForSync(6);	
			writeExtent("Pass", "Successfully selected the Title Tab "+data(titleTab) +" and SubTitle Tab "+data(subTitleTab)+" on "+ScreenName);

		}	
		catch(Exception e){

			writeExtent("Fail", "Failed to select Title and SubTitle Tab on "+ScreenName);
		}		
	}	

	/**
	 * @author A-9847
	 * @Desc To enter the AWB prefix and Number
	 * @param awbPrefix
	 * @param awbNumber
	 * @throws InterruptedException
	 */
	public void enterAWB(String awbPrefix,String awbNumber) throws InterruptedException{

		enterValueInTextbox(SheetName, "inbx_awbPrefix;id", data(awbPrefix), "AWB Prefix", ScreenName);
		enterValueInTextbox(SheetName, "inbx_awbNumber;id", data(awbNumber), "AWB Number", ScreenName);
		waitForSync(2);	

	}

	/**
	 * @author A-9847
	 * @Desc To enter the AWB Origin and destination
	 * @param awbOrg
	 * @param awbDest
	 * @throws InterruptedException
	 */
	public void enterAWBOrgAndDest(String awbOrg,String awbDest) throws InterruptedException{

		enterValueInTextbox(SheetName, "inbx_awbOrigin;id", data(awbOrg), "AWB Origin", ScreenName);
		enterValueInTextbox(SheetName, "inbx_awbDest;id", data(awbDest), "AWB Destination", ScreenName);
		waitForSync(2);			
	}

	/**
	 * @author A-9847
	 * @Desc To enter the Booking Origin and Destination
	 * @param bookingOrg
	 * @param bookingDest
	 * @throws InterruptedException
	 */
	public void enterBookingOrgAndDest(String bookingOrg,String bookingDest) throws InterruptedException{

		enterValueInTextbox(SheetName, "inbx_bookingOrg;id", data(bookingOrg), "Booking Origin", ScreenName);
		enterValueInTextbox(SheetName, "inbx_bookingDest;id", data(bookingDest), "Booking Destination", ScreenName);
		waitForSync(2);			
	}

	/**
	 * @author A-9847
	 * @Desc To enter the Booking Delivery and Arrival Date
	 * @param deliveryDate
	 * @param ArrivalDate
	 * @throws InterruptedException
	 */
	public void enterBookingDeliveryAndArrivalDate(String deliveryDate,String ArrivalDate) throws InterruptedException{

		enterValueInTextbox(SheetName, "inbx_bookingDeliveryDate;id", data(deliveryDate), "Booking Delivery Date", ScreenName);
		waitForSync(2);
		enterValueInTextbox(SheetName, "inbx_bookingArrivalDate;id", data(ArrivalDate), "Booking Arrival Date", ScreenName);
		waitForSync(3);
	}


	/**
	 * @author A-9847
	 * @Desc To enter the Booking Delivery and Arrival Time
	 * @param deliveryDate
	 * @param ArrivalDate
	 * @throws InterruptedException
	 */
	public void enterBookingDeliveryAndArrivalTime(String deliveryTime,String ArrivalTime) throws InterruptedException{

		enterValueInTextbox(SheetName, "inbx_bookingDeliveryTime;name", data(deliveryTime), "Booking Delivery Time", ScreenName);
		waitForSync(1);
		enterValueInTextbox(SheetName, "inbx_bookingArrivalTime;name", data(ArrivalTime), "Booking Arrival Time", ScreenName);
		waitForSync(2);
	}

	/**
	 * @author A-9847
	 * @Desc To select the given commodity Code from dropdown
	 * @param commodityCode
	 */
	public void selectCommodityCode(String commodityCode){		

		selectValueInDropdown(SheetName, "drpdn_commodityCode;id", data(commodityCode), "Select Commodity Code", "VisibleText");
		waitForSync(2);
	}

	/**
	 * @author A-9847
	 * @Desc To select the given ServiceLevel and corresponding Handling Needs
	 * @param serviceLevel
	 * @param handlingNeeds
	 */
	public void selectServiceLevelAndHandlingNeeds(String serviceLevel,String handlingNeeds){		

		selectValueInDropdown(SheetName, "drpdn_serviceLevel;name", data(serviceLevel), "Select Service Level", "VisibleText");
		waitForSync(1);
		selectValueInDropdown(SheetName, "drpdn_handlingNeeds;name", data(handlingNeeds), "Select Handling Needs", "VisibleText");
		waitForSync(2);
	}


	/**
	 * @author A-9847
	 * @Desc To select the Optional SCCs checkbox
	 * @param scc
	 */

	public void selectOptionalSCC(String scc){

		try{
			driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "chk_optionalScc;xpath").replace("*", data(scc)))).click();
			writeExtent("Pass", "Selected SCC ad "+data(scc)+" on "+ScreenName);

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to select the SCC on "+ScreenName);
		}

	}

	/**
	 * @author A-9847
	 * @Desc To select the Conditional SCCs checkbox
	 * @param scc
	 */
	public void selectConditionalSCC(String scc){

		try{
			driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "chk_conditionalScc;xpath").replace("*", data(scc)))).click();
			writeExtent("Pass", "Selected SCC ad "+data(scc)+" on "+ScreenName);

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to select the SCC on "+ScreenName);
		}

	}

	/**
	 * @author A-9847
	 * @Desc To enter the Agent Code
	 * @param AgentCode
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */
	public void enterCustomerID(String AgentCode) throws InterruptedException, IOException, AWTException{

		try{
			if(!driver.findElement(By.id("booking_accountNbr")).isDisplayed()) {
				waitForSync(1);
				clickWebElement(SheetName, "div_customerTab;id", "Customer details Tab", ScreenName);
			}	

			waitForSync(3);
			enterValueInTextbox(SheetName, "inbx_bookingAgentCode;name", data(AgentCode), "Agent Code", ScreenName);
			keyPress("TAB");	

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to enter the Agent Code on "+ScreenName);
		}	
	}

	/**
	 * @author A-10690
	 * @Desc To select the given ServiceLevel,product group and corresponding Handling Needs
	 * @param serviceLevel
	 * @param handlingNeeds
	 * @param product group
	 */
	public void selectServiceLevelDetails(String serviceLevel,String productGroup,String handlingNeeds){		

		selectValueInDropdown(SheetName, "drpdn_serviceLevel;name", data(serviceLevel), "Select Service Level", "VisibleText");
		selectValueInDropdown(SheetName, "drpdn_productgroup;name", data(productGroup), "Select Service Level", "VisibleText");
		waitForSync(1);
		selectValueInDropdown(SheetName, "drpdn_handlingNeeds;name", data(handlingNeeds), "Select Handling Needs", "VisibleText");
		waitForSync(2);
	
	}
	
	/**
	 * @author A-10690
	 * @Desc To select the checklist when DGR scc is selected
	 * @param checklist answer1
	 * @param checklist answer2
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void selectCheckList(String answer1,String answer2) throws InterruptedException, IOException{	
		
		clickWebElement(SheetName, "btn_checklist;id", "Checklist Tab", ScreenName);
		String checklist = xls_Read.getCellValue(SheetName, "drpdn_checklistCode1;xpath");
		while(driver.findElements(By.xpath(checklist)).size()==1)
		{
			waitForSync(1);
		}
		selectValueInDropdown(SheetName, "drpdn_checklistCode1;xpath", data(answer1), "Select checklist answer", "VisibleText");
		waitForSync(2);
		selectValueInDropdown(SheetName, "drpdn_checklistCode2;xpath", data(answer2), "Select checklist answer", "VisibleText");
		
	}


	/**
	 * @author A-10690
	 * @Desc To click optional SHC button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickOptionalSHC() throws InterruptedException, IOException{

		
		clickWebElement(SheetName, "btn_moreoptionalsgc;xpath", "optional SHC", ScreenName);
			
		}
	/**
	 * @author A-9847
	 * @Desc To enter the Flight Info
	 * @param carrierCode
	 * @param fltNo
	 * @param fltOrg
	 * @param fltDest
	 * @param fltDepDate
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */
	public void enterFlightInfo(String carrierCode,String fltNo,String fltOrg,String fltDest,String fltDepDate) throws InterruptedException, IOException, AWTException{

		try{
			if(!driver.findElement(By.name("segmentEnforceCapacityCheckbox")).isDisplayed()) {
				clickWebElement(SheetName, "div_flightInfoTab;id", "Flight Info Tab", ScreenName);
			}	

			waitForSync(3);
			enterValueInTextbox(SheetName, "inbx_carrierCode;id", data(carrierCode), "Carrier Code", ScreenName);
			enterValueInTextbox(SheetName, "inbx_flightNo;id", data(fltNo), "Flight Number", ScreenName);
			keyPress("TAB");
			enterValueInTextbox(SheetName, "inbx_flightOrg;id", data(fltOrg), "Flight Origin", ScreenName);
			enterValueInTextbox(SheetName, "inbx_flightDest;id", data(fltDest), "Flight Destination", ScreenName);
			enterValueInTextbox(SheetName, "inbx_flightDepDate;id", data(fltDepDate), "Flight Depature Date", ScreenName);

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to enter the Flight Info on "+ScreenName);
		}	
	}
	/**
	 * @author A-10690
	 * @Desc To click modifybooking button
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void clickmodifyBooking() throws InterruptedException, IOException{

		waitForSync(1);
		clickWebElement(SheetName, "btn_modifybooking;xpath", "modify booking", ScreenName);
		waitForSync(3);

	}


	/**
	 * @author A-9847
	 * @Desc To enter the Shipment Details
	 * @param pcs -pieces
	 * @param wgt - Weight
	 * @param vol - Volume
	 */
	public void enterShipmentDetails(String pcs,String wgt,String vol){

		try{
			if(!driver.findElement(By.name("booking.isInformational")).isDisplayed()) {
				clickWebElement(SheetName, "div_ShipmentDetailsTab;id", "Shipment Details Tab", ScreenName);
			}	

			waitForSync(3);
			enterValueInTextbox(SheetName, "inbx_pieces;id", data(pcs), "Pieces", ScreenName);
			enterValueInTextbox(SheetName, "inbx_weight;id", data(wgt), "Weight", ScreenName);
			enterValueInTextbox(SheetName, "inbx_volume;id", data(vol), "Volume", ScreenName);

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to enter the Shipment Details on "+ScreenName);
		}	
	}


	/**
	 * @author A-9847
	 * @Desc To enter the rate after clicking the File Spot Checkbox
	 * @param rate
	 */
	public void enterRateDetails(String rate){

		try{
			if(!driver.findElement(By.id("booking_revenueInfo_revenueCode")).isDisplayed()) {
				clickWebElement(SheetName, "div_RateDetailsTab;id", "Rate & Charge Details Tab", ScreenName);
			}	

			waitForSync(3);
			clickWebElement(SheetName, "chk_fileSpot;id", "File Spot Checkbox", ScreenName);
			waitForSync(2);
			enterValueInTextbox(SheetName, "inbx_rate;id", data(rate), "Rate", ScreenName);

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to enter the Rate & Charge Details on "+ScreenName);
		}	
	}


	/**
	 * @author A-9847
	 * @Desc To select File Spot Checkbox
	 * @param rate
	 */

	public void clickFileSpotCheckBox() throws InterruptedException, IOException{

		waitForSync(1);
		clickWebElement(SheetName, "chk_fileSpot;id", "File Spot Checkbox", ScreenName);

	}

	/**
	 * @author A-9847
	 * @Desc To make all the RouteSearch and Evaluation settings either to Yes/No
	 * @param option - yes/No
	 */
	public void selectRouteSearchAndEvaluationSetting(String option){


		try{
			if(!driver.findElement(By.id("searchOptionsButton")).isDisplayed()) {
				clickWebElement(SheetName, "div_routeSearchTab;id", "Route Search & Evaluation Settings Tab", ScreenName);
			}	

			waitForSync(3);

			if(option.equals("yes"))
			{
				driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "btn_chkRestriction;xpath").replace("*", "true"))).click();
				driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "btn_chkCapacity;xpath").replace("*", "true"))).click();
				driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "btn_chkRate;xpath").replace("*", "true"))).click();
				driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "btn_chkAllotment;xpath").replace("*", "true"))).click();
				driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "btn_chkLoopUpPrice;xpath").replace("*", "true"))).click();
			}
			else{
				driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "btn_chkRestriction;xpath").replace("*", "false"))).click();
				driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "btn_chkCapacity;xpath").replace("*", "false"))).click();
				driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "btn_chkRate;xpath").replace("*", "false"))).click();
				driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "btn_chkAllotment;xpath").replace("*", "false"))).click();
				driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "btn_chkLoopUpPrice;xpath").replace("*", "false"))).click();
			}

			waitForSync(2);
			writeExtent("Pass", "Successfully selected the Route Search & Evaluation Settings on "+ScreenName);


		}
		catch(Exception e){
			writeExtent("Fail", "Failed to select the Route Search & Evaluation Settings on "+ScreenName);
		}	
	}

	/**
	 * @author A-9847
	 * @Desc To click and verify Submit Booking
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickSubmitBooking() throws InterruptedException, IOException{

		try{
			clickWebElement(SheetName, "div_submitBooking;xpath", "Submit Booking", ScreenName);
			waitTillScreenload(SheetName, "div_bookingConfirm;xpath","Booking Information", ScreenName);  
			String bookingConfirmation = xls_Read.getCellValue(SheetName, "div_bookingConfirm;xpath");
			System.out.println(driver.findElements(By.xpath(bookingConfirmation)).size());
			if(driver.findElements(By.xpath(bookingConfirmation)).size()==1)
				writeExtent("Pass", "Successfully verified Booking Confirmation on "+ScreenName); 
			else
				writeExtent("Fail", "Failed to verify Booking Confirmation on "+ScreenName); 

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify Booking Confirmation on "+ScreenName); 
		}


	}




}































