package screens;

import java.awt.AWTException;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class MaintainOperationalFlight_FLT003 extends CustomFunctions {

	String sheetName = "MaintainOperationFlight_FLT003";
	String screenName = "Maintain Operational Flight : FLT003";

	public MaintainOperationalFlight_FLT003(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	/**
	 * Description... List Flight
	 * 
	 * @param fltNumber
	 * @param fltDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listFlight(String fltNumber, String fltDate) throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_CarrierCode;xpath", data("prop~flight_code"), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(fltNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id", data(fltDate), "Flight Date", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "btn_listFlight;id", "List", screenName);
		Thread.sleep(3000);
	}

	/**
	 * @author A-8783 Description... Enter Leg Capacity Details
	 * @param departureDate
	 * @param arrivalDate
	 * @param departureTime
	 * @param arrivalTime
	 * @param aircraftType
	 * @param capacityConfig
	 * @throws Exception
	 */
	public void enterLegCapacityDetails(String departureDate, String arrivalDate, String departureTime,
			String arrivalTime, String aircraftType, String capacityConfig) throws Exception {
		enterValueInTextbox(sheetName, "inbx_departureDate;name", data(departureDate), "Departure Date", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDepartureTime;xpath", data(departureTime), "Departure Time",
				screenName);
		enterValueInTextbox(sheetName, "inbx_arrivalDate;name", data(arrivalDate), "Arrival Date", screenName);
		enterValueInTextbox(sheetName, "inbx_flightArrivalTime;xpath", data(arrivalTime), "Arrival Time", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_aircraftType;id", data(aircraftType), "Aircraft type", screenName);
		clickWebElement(sheetName, "btn_viewCapacity;id", "view capcity", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_legDetailsOK;id", "view capcity Ok Button", screenName);

	}

	/**
	 * @author A-10690
	 * @throws IOException
	 *             Desc : Verifying the seal numbers in FLT003 screen
	 */
	public void verifySealNumbers(String sealNumber1, String sealNumber2) throws Exception {

		String exptext = data(sealNumber1) + "," + data(sealNumber2);
		waitForSync(1);

		By element = getElement(sheetName, "txt_Sealno;id");
		String actText = driver.findElement(element).getAttribute("value");
		if (exptext.equals(actText)) {
			writeExtent("Pass", "Captured seal numbers" + exptext + "are getting displayed in customs seal no field");
		} else {
			writeExtent("Fail",
					"Captured seal numbers" + exptext + "are not getting displayed in customs seal no field");
		}
	}

	/**
	 * Description... List Flight
	 * 
	 * @param fltNumber
	 * @param fltDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listFlight(String carrierCode, String fltNumber, String fltDate)
			throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_CarrierCode;xpath", data(carrierCode), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(fltNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id", data(fltDate), "Flight Date", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "btn_listFlight;id", "List", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Click checkbox for second leg
	 * 
	 * @param locator
	 * @throws Exception
	 */
	public void clickSecondCheckbox() throws Exception {
		switchToWindow("getParent");
		switchToFrame("contentFrame", "FLT003");
		clickWebElement(sheetName, "chkbox_secondRoute;xpath", "Leg", screenName);

	}

	/**
	 * Description... Update In Capacity Table
	 * 
	 * @param OVBWeight
	 * @param OVBVolume
	 * @param Weight
	 * @param Volume
	 * @param LDC
	 * @param LDP
	 * @param MDP
	 * @throws Exception
	 */
	public void updateInCapacityTable(String OVBWeight, String OVBVolume, String Weight, String Volume, String LDC,
			String LDP, String MDP) throws Exception {

		switchToWindow("child");
		enterValueInTextbox(sheetName, "ovb_weight;xpath", data(OVBWeight), "OVBWeight", screenName);
		enterValueInTextbox(sheetName, "ovb_volume;xpath", data(OVBVolume), "OVBVolume", screenName);
		enterValueInTextbox(sheetName, "inbx_weight;xpath", data(Weight), "Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_volume;xpath", data(Volume), "Volume", screenName);
		enterValueInTextbox(sheetName, "inbx_ldc;xpath", data(LDC), "LDC", screenName);
		enterValueInTextbox(sheetName, "inbx_ldp;xpath", data(LDP), "LDP", screenName);
		enterValueInTextbox(sheetName, "inbx_mdp;xpath", data(MDP), "MDP", screenName);

	}
	/**
     * Description... Extract Departure time from FLT003
* 
* @throws Exception
*/
	public String  getFlightDepartureTime() throws Exception {
		switchToFrame("contentFrame", "FLT003");

		waitForSync(1);
		String  Departuretime ="";
		try
		{

			By element = getElement(sheetName, "table_flightDeparturetext;xpath");
			 Departuretime = driver.findElement(element).getText();
			
			
			
			}
			catch(Exception e)
			{
				writeExtent("Fail","Flight departure time is Not Extracted");
			}
			return Departuretime;
		
		}

	/**
	 * Description... Save Update
	 * 
	 * @throws Exception
	 */
	public void saveUpdate() throws Exception {
		clickWebElement("Generic_Elements", "btn_ok2;name", "leg capcity ok btn", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "FLT003");
		clickWebElement(sheetName, "btn_Save;id", "Save", screenName);

	}

	/**
	 * Description... List Flight
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listFlight() throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "flight_number;xpath", data("FlightNumber"), "Flight No", screenName);
		enterValueInTextbox(sheetName, "flight_date;xpath", data("flightDate"), "Flight Date", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "flightList_Button;xpath", "List", screenName);
		Thread.sleep(3000);
	}

	/**
	 * Description... Change Control Office
	 * 
	 * @throws InterruptedException
	 */
	public void changeControlOffice() throws InterruptedException {

		enterValueInTextbox(sheetName, "controlStation_field;xpath", data("controlStation"), "Station", screenName);
		enterValueInTextbox(sheetName, "controlOffice_Field;xpath", data("controlOffice"), "Office", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Click Leg Capacity
	 * 
	 * @throws Exception
	 */
	public void clickLegCapacity() throws Exception {
		waitForSync(2);
		switchToWindow("storeParent");
		clickButtonSwitchWindow(sheetName, "btn_legCapacity;id", "Leg details ", screenName);

	}

	/**
	 * Description... Update Aircraft Type
	 * 
	 * @param aircraftType
	 * @throws Exception
	 */
	public void updateAircraftType(String aircraftType) throws Exception {
		waitForSync(1);
		switchToWindow("child");
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_aircraftType;id", data(aircraftType), "Aircraft Type", screenName);
		clickWebElement(sheetName, "btn_legDetailsOK;id", "Leg Details OK", screenName);
		waitForSync(3);
		switchToFrame("default");
		waitForSync(10);
		try {
			while (driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
				waitForSync(10);
			}
		}

		catch (Exception e) 
		{
		}

		waitForSync(1);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "FLT003");

	}

	/**
	 * Description... List new flight Flight
	 * 
	 * @param fltNumber
	 * @param fltDate
	 * @param fltcode
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listnewFlight(String fltcode, String fltNumber, String fltDate)
			throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_CarrierCode;xpath", data(fltcode), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(fltNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id", data(fltDate), "Flight Date", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "btn_listFlight;id", "List", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Click Save
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickSave() throws InterruptedException, IOException {
		waitForSync(1);
		clickWebElement(sheetName, "btn_Save;id", "Save", screenName);

	}

	/**
	 * Description... Swiched to parent window and clicks on save button
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 */
	public void save(String ScreenName) throws Exception {
		switchToWindow("getParent");
		switchToFrame("contentFrame", "FLT003");
		clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", ScreenName);
		waitForSync(4);
	}

	/**
	 * Description... Enter carrier code
	 * 
	 * @param carrierCode
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void enterCarrierCode(String carrierCode) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_CarrierCode;xpath", data(carrierCode), "Carrier Code", screenName);
		Thread.sleep(3000);
	}

	/**
	 * @author A-8783 Description... Enter multileg Flight Details
	 * @param route
	 * @param schduleType
	 * @param FCTLStation
	 * @param FCTLOffice
	 * @param flightType
	 * @throws Exception
	 */
	public void enterMultiLegFlightDetails(String route, String schduleType, String FCTLStation, String FCTLOffice,
			String flightType) throws Exception {

		switchToFrame("contentFrame", "FLT003");
		enterValueInTextbox(sheetName, "inbx_flightRoute;xpath", data(route), "Flight route", screenName);
		selectValueInDropdown(sheetName, "lst_flightScheduleType;xpath", data(schduleType), "schduleType",
				"VisibleText");
		By element = getElement(sheetName, "txt_chargeParty;name");
		String chargeParty = driver.findElement(element).getAttribute("value");
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_flightOwner;name", chargeParty, "Flight owner", screenName);
		selectValueInDropdown(sheetName, "lst_flightType;xpath", data(flightType), "Flight Type", "VisibleText");
		enterValueInTextbox(sheetName, "controlStation_field;xpath", data(FCTLStation), "FCTL Station", screenName);
		enterValueInTextbox(sheetName, "controlOffice_Field;xpath", data(FCTLOffice), "FCTL office", screenName);
		int size = driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "btn_leg;xpath"))).size();
		clickButtonSwitchWindow(sheetName, "btn_legCapacity;id", "Leg details ", screenName);

		enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "");
		switchToWindow("getParent");
		switchToFrame("contentFrame", "FLT003");
		for (int i = 2; i <= size; i++) {
			String s1 = "//table[@id='flightLegTable']//tbody/tr[";
			String s2 = "]/td//input[@type='checkbox']";
			String locator = s1 + i + s2;

			driver.findElement(By.xpath(locator)).click();
			clickButtonSwitchWindow(sheetName, "btn_legCapacity;id", "Leg details ", screenName);
			enterLegCapacityDetails("ATD_Local2", "ATA_Local2", "AircraftType", "");
			switchToWindow("getParent");
			switchToFrame("contentFrame", "FLT003");

		}

	}

	/**
	 * Description... Verify Leg Details
	 * 
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws IOException
	 */
	public void verifyLegDetails(int verfCols[], String actVerfValues[], String pmKey) throws IOException {
		verify_tbl_records_multiple_cols(sheetName, "table_legDetails;xpath", "//td", verfCols, pmKey, actVerfValues);
	}

	/**
	 * @author A-9844 Description... List New Flight
	 * @param carrierCode
	 * @param fltNumber
	 * @param fltDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listNewFlightDetails(String carrierCode, String fltNumber, String fltDate)
			throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_CarrierCode;xpath", data(carrierCode), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(fltNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id", data(fltDate), "Flight Date", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		waitForSync(1);
		clickWebElement(sheetName, "btn_listFlight;id", "List", screenName);
		waitForSync(5);
		handleAlert("Accept", screenName);
	}

	/**
	 * Description... List New Flight
	 * 
	 * @param fltNumber
	 * @param fltDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listNewFlight(String fltNumber, String fltDate) throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_CarrierCode;xpath", data("prop~flight_code"), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(fltNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id", data(fltDate), "Flight Date", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		waitForSync(1);
		clickWebElement(sheetName, "btn_listFlight;id", "List", screenName);
		waitForSync(5);
		handleAlert("Accept", screenName);
	}

	/**
	 * Description... click yes button
	 * 
	 * @throws Exception
	 */
	public void clickYesButton() throws Exception {
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);
		waitForSync(2);
		switchToFrame("contentFrame", "FLT003");
	}

	/**
	 * Description... List New Flight
	 * 
	 * @param fltNumber
	 * @param fltDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listNewFlight(String fltNumber, String fltDate, String flightType)
			throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_CarrierCode;xpath", data("prop~flight_code"), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(fltNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id", data(fltDate), "Flight Date", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		waitForSync(1);
		clickWebElement(sheetName, "btn_listFlight;id", "List", screenName);
		waitForSync(5);
		switchToFrame("default");

		try {

			int size = driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).size();
			System.out.println(size);
			while (size != 1) {
				switchToFrame("contentFrame", "FLT003");
				driver.findElement(By.id(xls_Read.getCellValue(sheetName, "btn_clear;id"))).click();
				createFlight(flightType);
				waitForSync(2);
				listFlight(fltNumber, fltDate);
				switchToFrame("default");
				size = driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).size();
			}

			handleAlert("Accept", screenName);

		}

		catch (Exception e) {

		}
	}

	/**
	 * Description... List New Flight
	 * 
	 * @param fltNumber
	 * @param fltDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listNewFlight(String carrierCode, String fltNumber, String fltDate, String flightType)
			throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_CarrierCode;xpath", data(carrierCode), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(fltNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id", data(fltDate), "Flight Date", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		waitForSync(1);
		clickWebElement(sheetName, "btn_listFlight;id", "List", screenName);
		waitForSync(5);
		switchToFrame("default");

		try {

			int size = driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).size();
			System.out.println(size);
			while (size != 1) {
				switchToFrame("contentFrame", "FLT003");
				driver.findElement(By.id(xls_Read.getCellValue(sheetName, "btn_clear;id"))).click();
				createFlight(flightType);
				waitForSync(2);
				listFlight(carrierCode, fltNumber, fltDate);
				switchToFrame("default");
				size = driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).size();
			}

			handleAlert("Accept", screenName);

		}

		catch (Exception e) {

		}
	}

	/**
	 * Description... Save leg Capacity
	 * 
	 * @throws Exception
	 */
	public void savelegCapacity() throws Exception {

		clickWebElement("Generic_Elements", "btn_ok2;name", "leg capcity ok btn", screenName);
		handleAlert("Accept", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "FLT003");
		waitForSync(4);
	}

	/**
	 * Description... Edit Leg Details
	 * 
	 * @param OVBWeight
	 * @param OVBVolume
	 * @param Weight
	 * @param Volume
	 * @param LDC
	 * @param LDP
	 * @param MDP
	 * @throws Exception
	 */
	public void editLegDetails(String OVBWeight, String OVBVolume, String Weight, String Volume, String LDC, String LDP,
			String MDP) throws Exception {

		clickButtonSwitchWindow(sheetName, "btn_legCapacity;id", "Leg details ", screenName);
		enterValueInTextbox(sheetName, "inbx_OVBweight;xpath", data(OVBWeight), "OVBWeight", screenName);
		enterValueInTextbox(sheetName, "inbx_OVBvolume;xpath", data(OVBVolume), "OVBVolume", screenName);
		enterValueInTextbox(sheetName, "inbx_weight;xpath", data(Weight), "Weight1", screenName);
		enterValueInTextbox(sheetName, "inbx_volume;xpath", data(Volume), "Volume1", screenName);
		enterValueInTextbox(sheetName, "inbx_LDC;xpath", data(LDC), "LDC", screenName);
		enterValueInTextbox(sheetName, "inbx_LDP;xpath", data(LDP), "LDP", screenName);
		enterValueInTextbox(sheetName, "inbx_MDP;xpath", data(MDP), "MDP", screenName);
	}

	/**
	 * Description... Click Retain capacity
	 * 
	 * @throws Exception
	 */
	public void clickRetaincapa() throws Exception {
		clickWebElement(sheetName, "inbx_retainCapa;name", "Retain capacity", screenName);

	}

	/**
	 * Description... Click Leg Checkbox
	 * 
	 * @param locator
	 * @throws Exception
	 */
	public void clickCheckbox(String locator) throws Exception {
		clickWebElement(sheetName, locator, "Leg", screenName);

	}

	/**
	 * Description... Enter Flight Details
	 * 
	 * @param route
	 * @param schduleType
	 * @param FCTLStation
	 * @param FCTLOffice
	 * @param flightType
	 * @throws Exception
	 */
	public void enterFlightDetails(String route, String schduleType, String FCTLStation, String FCTLOffice,
			String flightType) throws Exception {

		switchToFrame("contentFrame", "FLT003");
		enterValueInTextbox(sheetName, "inbx_flightRoute;xpath", data(route), "Flight route", screenName);
		selectValueInDropdown(sheetName, "lst_flightScheduleType;xpath", data(schduleType), "schduleType",
				"VisibleText");
		By element = getElement(sheetName, "txt_chargeParty;name");
		String chargeParty = driver.findElement(element).getAttribute("value");
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_flightOwner;name", chargeParty, "Flight owner", screenName);
		selectValueInDropdown(sheetName, "lst_flightType;xpath", data(flightType), "Flight Type", "VisibleText");
		enterValueInTextbox(sheetName, "controlStation_field;xpath", data(FCTLStation), "FCTL Station", screenName);
		enterValueInTextbox(sheetName, "controlOffice_Field;xpath", data(FCTLOffice), "FCTL office", screenName);
		clickButtonSwitchWindow(sheetName, "btn_legCapacity;id", "Leg details ", screenName);

	}

	/**
	 * Description... Enter Leg Capacity Details with configuration details
	 * 
	 * @param departureTime
	 * @param arrivalTime
	 * @param aircraftType
	 * @param capacityConfig
	 * @throws Exception
	 * @author A-9175
	 */
	public void enterLegCapacityDetailsWithConfiguration(String departureTime, String arrivalTime, String aircraftType,
			String capacityConfig) throws Exception {

		enterValueInTextbox(sheetName, "inbx_flightDepartureTime;xpath", data(departureTime), "Departure Time",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightArrivalTime;xpath", data(arrivalTime), "Arrival Time", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_aircraftType;id", data(aircraftType), "Aircraft type", screenName);
		clickWebElement(sheetName, "btn_viewCapacity;id", "view capcity", screenName);
		waitForSync(3);
		selectValueInDropdown(sheetName, "lst_capacityConfig;id", data(capacityConfig), "capacity Config", "Value");
		clickWebElement(sheetName, "btn_legDetailsOK;id", "view capcity Ok Button", screenName);

	}

	/**
	 * Description... Enter Leg Capacity Details
	 * 
	 * @param departureTime
	 * @param arrivalTime
	 * @param aircraftType
	 * @param capacityConfig
	 * @throws Exception
	 */
	public void enterLegCapacityDetails(String departureTime, String arrivalTime, String aircraftType,
			String capacityConfig) throws Exception {

		enterValueInTextbox(sheetName, "inbx_flightDepartureTime;xpath", data(departureTime), "Departure Time",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightArrivalTime;xpath", data(arrivalTime), "Arrival Time", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_aircraftType;id", data(aircraftType), "Aircraft type", screenName);
		clickWebElement(sheetName, "btn_viewCapacity;id", "view capcity", screenName);
		waitForSync(3);
		// selectValueInDropdown(sheetName, "lst_capacityConfig;id",
		// data(capacityConfig), "capacity Config", "Value");
		clickWebElement(sheetName, "btn_legDetailsOK;id", "view capcity Ok Button", screenName);

	}

	/**
	 * Description... Modify Aircraft Type
	 * 
	 * @param aircraftType
	 * @param capacityConfig
	 * @throws Exception
	 */
	public void modifyAircraftType(String aircraftType, String capacityConfig) throws Exception {

		clickButtonSwitchWindow(sheetName, "btn_legCapacity;id", "Leg details ", screenName);

		enterValueInTextbox(sheetName, "inbx_aircraftType;id", data(aircraftType), "Aircraft type", screenName);
		clickWebElement(sheetName, "btn_viewCapacity;id", "view capcity", screenName);
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_capacityConfig;id", data(capacityConfig), "capacity Config", "Value");

	}

	/**
	 * Description... Leg Capacity Ok Button
	 * 
	 * @throws Exception
	 */
	public void legCapacityOkButton() throws Exception {

		clickWebElement("Generic_Elements", "btn_ok2;name", "leg capcity ok btn", screenName);

		switchToWindow("getParent");
		switchToFrame("contentFrame", "FLT003");
		waitForSync(4);
	}

	/**
	 * Description... Click Alert
	 * 
	 * @throws Exception
	 */
	public void ClickAlert() throws Exception {

		handleAlert("Accept", screenName);
		switchToFrame("contentFrame", "FLT003");
		waitForSync(2);
	}

	/**
	 * Description... Set Station And Office
	 * 
	 * @param Station
	 * @param Office
	 * @throws InterruptedException
	 */
	// To provide station and office(Above method is very specific, can not use
	// for versatile requirements)
	public void setStationAndOffice(String Station, String Office) throws InterruptedException {
		waitForSync(5);
		enterValueInTextbox(sheetName, "controlStation_field;xpath", data(Station), "Station", screenName);
		enterValueInTextbox(sheetName, "controlOffice_Field;xpath", data(Office), "Office", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Fetch Value from First Record
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String fetchValuefromFirstRecord() throws InterruptedException {
		String ldc = getElementText("MaintainOperationFlight_FLT003", "ldc_value;xpath", "values in box", screenName);
		String ldp = getElementText("MaintainOperationFlight_FLT003", "ldp_value;xpath", "values in box", screenName);

		String mdp = getElementText("MaintainOperationFlight_FLT003", "mdp_value;xpath", "values in box", screenName);
		String q7 = getElementText("MaintainOperationFlight_FLT003", "q7_value;xpath", "values in box", screenName);

		String weight = getElementText("MaintainOperationFlight_FLT003", "weight1_value;xpath", "values in box",
				screenName);
		String volume = getElementText("MaintainOperationFlight_FLT003", "volume1_value;xpath", "values in box",
				screenName);
		return (ldc + "/" + ldp + "/" + mdp + "/" + q7 + "/" + weight + "/" + volume);
	}

	/**
	 * Description... Fetch Value from Second Record
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String fetchValuefromSecondRecord() throws InterruptedException {

		String ldc = getElementText("MaintainOperationFlight_FLT003", "ldc2_value;xpath", "values in box", screenName);
		String ldp = getElementText("MaintainOperationFlight_FLT003", "ldp2_value;xpath", "values in box", screenName);

		String mdp = getElementText("MaintainOperationFlight_FLT003", "mdp2_value;xpath", "values in box", screenName);
		String q7 = getElementText("MaintainOperationFlight_FLT003", "q7_value2;xpath", "values in box", screenName);
		String weight = getElementText("MaintainOperationFlight_FLT003", "weight2_value;xpath", "values in box",
				screenName);
		String volume = getElementText("MaintainOperationFlight_FLT003", "volume2_value;xpath", "values in box",
				screenName);
		return (ldc + "/" + ldp + "/" + mdp + "/" + q7 + "/" + weight + "/" + volume);

	}

	/**
	 * Description... compare Strings
	 * 
	 * @param exp
	 * @param act
	 * @throws InterruptedException
	 */
	public void compare(String exp, String act) throws InterruptedException {

		verifyValueOnPage(act, exp, "Verify " + exp, "Maintain Operational Flight", exp);
	}

	/**
	 * Description... Click Segment
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickSeg() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_segment;xpath", "segment", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Click segment Capacity
	 * 
	 * @throws Exception
	 */
	public void clickSegCapacity() throws Exception {

		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_segmentCapacity;xpath", "segment capacity", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Provide SCL value
	 * 
	 * @param SCLweight
	 * @param SCLvolume
	 * @param MDP
	 * @param LDP
	 * @param LDC
	 * @throws Exception
	 */
	public void provideSCLvalue(String SCLweight, String SCLvolume, String MDP, String LDP, String LDC)
			throws Exception {
		waitForSync(1);
		switchToWindow("child");
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_sclweight;name", data(SCLweight), "SCL Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_sclvolume;name", data(SCLvolume), "SCL Volume", screenName);
		enterValueInTextbox(sheetName, "inbx_sclLDC;xpath", data(LDC), "LDC", screenName);
		enterValueInTextbox(sheetName, "inbx_sclLDP;xpath", data(LDP), "LDP", screenName);
		enterValueInTextbox(sheetName, "inbx_sclMDP;xpath", data(MDP), "MDP", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Provide GSC value
	 * 
	 * @param GSCweight
	 * @param GSCvolume
	 * @param MDP
	 * @param LDP
	 * @param LDC
	 * @throws Exception
	 */
	public void provideGSCvalue(String GSCweight, String GSCvolume, String MDP, String LDP, String LDC)
			throws Exception {
		waitForSync(1);
		switchToWindow("child");
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_gscweight;name", data(GSCweight), "SCL Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_gscvolume;name", data(GSCvolume), "SCL Volume", screenName);
		enterValueInTextbox(sheetName, "inbx_gscLDC;xpath", data(LDC), "LDC", screenName);
		enterValueInTextbox(sheetName, "inbx_gscLDP;xpath", data(LDP), "LDP", screenName);
		enterValueInTextbox(sheetName, "inbx_gscMDP;xpath", data(MDP), "MDP", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Click segment capacity ok
	 * 
	 * @throws Exception
	 */
	public void ClickSegCapOk() throws Exception {

		clickWebElement(sheetName, "inbx_SegCapOk;name", "segment capacity ok", screenName);
		waitForSync(1);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "FLT003");
	}

}
