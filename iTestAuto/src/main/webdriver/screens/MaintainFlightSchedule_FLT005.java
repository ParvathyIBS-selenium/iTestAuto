/**
 * Author : A-7037
 * Date Created/ Modified : 20/02/2019
 * Description : To perform operations on Maintain Flight Schedule (FLT005) Screen
 */

package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.relevantcodes.extentreports.LogStatus;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class MaintainFlightSchedule_FLT005 extends CustomFunctions {

	String sheetName = "MaintainFlightSchedule_FLT005";
	String screenName = "MaintainFlightSchedule : FLT005";

	public MaintainFlightSchedule_FLT005(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	/**
	 * Description... List Flight
	 * 
	 * @param FlightNumber
	 * @param flightStartDate
	 * @param flightEndDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listFlight(String FlightNumber, String flightStartDate, String flightEndDate)
			throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightStartDate;xpath", flightStartDate, "Flight StartDate", screenName);
		performKeyActions(sheetName, "inbx_flightStartDate;xpath", "TAB", "Flight StartDate", screenName);
		enterValueInTextbox(sheetName, "inbx_flightEndDate;xpath", flightEndDate, "Flight EndDate", screenName);
		performKeyActions(sheetName, "inbx_flightEndDate;xpath", "TAB", "Flight EndDate", screenName);
		clickWebElement(sheetName, "btn_flightListButton;xpath", "List", screenName);
		waitForSync(2);

	}

	/**
	 * Description... Relist Flight
	 * 
	 * @param FlightNumber
	 * @param flightStartDate
	 * @param flightEndDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void relistFlight(String FlightNumber, String flightStartDate, String flightEndDate)
			throws InterruptedException, AWTException, IOException {

		waitForSync(5);
		switchToFrame("contentFrame", "FLT005");
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightStartDate;xpath", flightStartDate, "Flight StartDate", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_flightEndDate;xpath", flightEndDate, "Flight EndDate", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "btn_flightListButton;xpath", "List", screenName);
		waitForSync(3);

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

		switchToFrame("contentFrame", "FLT005");
		enterValueInTextbox(sheetName, "inbx_flightRoute;xpath", data(route), "Flight route", screenName);
		checkIfUnchecked(sheetName, "chk_flightFrequency;xpath", "Flight Frequency", screenName);
		selectValueInDropdown(sheetName, "lst_flightScheduleType;xpath", data(schduleType), "schduleType",
				"VisibleText");
		enterValueInTextbox(sheetName, "inbx_controlStation;xpath", data(FCTLStation), "FCTL Station", screenName);
		/**
		 * enterValueInTextbox(sheetName, "inbx_controlOffice;xpath",
		 * data(FCTLOffice), "FCTL office", screenName);
		 **/
		selectValueInDropdown(sheetName, "lst_flightType;xpath", data(flightType), "Flight Type", "VisibleText");

		String creationDate = getAttributeWebElement(sheetName, "inbx_creationDate;name", "Creation date", "value",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightEffectiveDate;xpath", creationDate, "Effective Date", screenName);
		waitForSync(2);
		performKeyActions(sheetName, "inbx_flightEffectiveDate;xpath", "TAB", "Effective Date", screenName);
		waitForSync(2);
		clickButtonSwitchWindow(sheetName, "lnk_flightUpdateCapacity;xpath", "Update Capacity", screenName);

	}

	/**
	 * Description... Enter Leg Capacity Details
	 * 
	 * @param departureTime
	 * @param arrivalTime
	 * @param aircraftType
	 * @param capacityConfig
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterLegCapacityDetails(String departureTime, String arrivalTime, String aircraftType,
			String capacityConfig) throws InterruptedException, IOException {

		enterValueInTextbox(sheetName, "inbx_flightDepartureTime;xpath", data(departureTime), "Departure Time",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightArrivalTime;xpath", data(arrivalTime), "Arrival Time", screenName);
		enterValueInTextbox(sheetName, "inbx_aircraftType;xpath", data(aircraftType), "Aircraft type", screenName);
		clickWebElement(sheetName, "btn_viewCapacity;xpath", "view capcity", screenName);
		waitForSync(4);
		/*
		 * selectValueInDropdown(sheetName, "lst_capacityConfig;xpath",
		 * data(capacityConfig), "capacity Config", "Value");
		 */
	}

	/**
	 * Description... Click Leg Capacity Ok Button
	 * 
	 * @throws Exception
	 */
	public void legCapacityOkButton() throws Exception {

		clickWebElement("Generic_Elements", "btn_ok2;name", "leg capcity ok btn", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "FLT005");
		waitForSync(4);
	}

	/**
	 * @author A-8783 desc- Select agreement type from the drop down
	 * @param agreementType
	 */
	public void selectAgreementType(String agreementType) {

		selectValueInDropdown(sheetName, "lst_agreementType;name", data(agreementType), "Agreement Type",
				"VisibleText");

	}

	/**
	 * Description... click Save button and Handle Alert
	 * 
	 * @throws Exception
	 */
	public void save() throws Exception {

		clickWebElement(sheetName, "btn_save;xpath", "Save button", screenName);
		waitForSync(2);
		switchToFrame("default");
		handleAlert("Accept", screenName);
		waitForSync(6);
		handleAlert("Accept", screenName);
		waitForSync(3);
		keyPress("ENTER");
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
	 * Description... Click Update Capacity
	 * 
	 * @throws Exception
	 */
	public void clickUpdateCapacity() throws Exception {
		clickButtonSwitchWindow(sheetName, "lnk_flightUpdateCapacity;xpath", "Update Capacity", screenName);

	}

	/**
	 * @author A-9847 Description... Click Specific Check Box in the Table
	 * @param locator
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickSpecificCheckBox(String pmyKey) throws InterruptedException, IOException {
		selectTableRecordJS(data(pmyKey), "clk_chckbx;xpath", sheetName, 1);
		waitForSync(2);
	}

	/**
	 * @author A-8783
	 * @param weight
	 * @param volume
	 * @throws Exception
	 */
	public void verifyFlightCapacity(String weight, String volume) throws Exception {
		String actualWt = getAttributeWebElement(sheetName, "inbx_weight;xpath", "Flight weight", "value", screenName)
				.toUpperCase();
		verifyScreenTextWithExactMatch(screenName, data(weight), actualWt, "Flight weight",
				"Verification of flight weight");

		String actualVol = getAttributeWebElement(sheetName, "inbx_volume;name", "Flight volume", "value", screenName)
				.toUpperCase();
		verifyScreenTextWithExactMatch(screenName, data(volume), actualVol, "Flight volume",
				"Verification of flight volume");

	}

	/**
	 * Description... Click Leg Capacity Ok Button from FLT004
	 * 
	 * @throws Exception
	 */
	public void legCapacityOkButtonFromFLT004() throws Exception {

		clickWebElement("Generic_Elements", "btn_ok2;name", "leg capcity ok btn", screenName);

		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "FLT004");
		waitForSync(4);
	}

	/**
	 * Description... Enter over booking weight and over booking volume
	 * 
	 * @param OVBWeight
	 * @param OVBVolume
	 * @throws InterruptedException
	 */
	public void enterOVBWeightAndOVBVolume(String OVBWeight, String OVBVolume) throws InterruptedException {

		enterValueInTextbox(sheetName, "inbx_OVBWeight;xpath", OVBWeight, "OVB Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_OVBVolume;xpath", OVBVolume, "OVB Volume", screenName);
	}

	/**
	 * @author A-7271
	 * @param route
	 *            Desc : check the leg based on the route
	 */
	public void checkLeg(String route) {
		String locator = xls_Read.getCellValue(sheetName, "chkBox_leg;xpath");
		locator = locator.replace("route", data(route));

		driver.findElement(By.xpath(locator)).click();
		waitForSync(1);

	}

	/**
	 * Description... Verify Leg Capacity
	 * 
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws Exception
	 */

	public void verifyLegCapacity(int verfCols[], String actVerfValues[], String pmKey) throws Exception {
		try {
			ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_capacityConfig;xpath")));
			Select sel = new Select(ele);
			sel.selectByVisibleText(pmKey);

			String table_row = xls_Read.getCellValue(sheetName, "table_legCapacity;xpath");
			List<WebElement> rows = driver.findElements(By.xpath(table_row));
			for (int i = 1; i <= rows.size(); i++) {
				System.out.println("i= " + i);
				String dynXpath = table_row + "[" + i + "]";

				for (int k = 0; k < verfCols.length; k++) {
					int x = verfCols[k];

					String td = dynXpath + "//td" + "[" + x + "]";
					ele = driver.findElement(By.xpath(td));

					String actual = ele.getText().toLowerCase().replace(" ", "");
					String expected = (actVerfValues[k].replace(" ", "").toLowerCase());

					if (actual.equals(expected)) {
						System.out.println("found true for " + actVerfValues[k]);

						onPassUpdate(screenName, expected, actual, "Table verification against " + pmKey + " On ",
								"Table verification");

					} else {
						onFailUpdate(screenName, expected, actual, "Table verification against " + pmKey + " On ",
								"Table verification");

					}
				}

			}
		} catch (Exception e) {

			retryCount = retryCount + 1;

			if (retryCount <= 3) {
				verifyLegCapacity(verfCols, actVerfValues, pmKey);
			}

			else {

				test.log(LogStatus.FAIL, "Could not perform table record verification");
				System.out.println("Table contents are not verified or verification failed");
			}

		}
		clickButtonSwitchtoParentWindow(sheetName, "btn_legCapacityCloseBtn;xpath", "leg capcity close btn",
				screenName);

		Thread.sleep(5000);

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
	 * Description... Enter Day Change
	 * 
	 * @param dayChange
	 * @throws InterruptedException
	 */
	public void enterDayChange(String dayChange) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_dayChangeAtDest;xpath", data(dayChange), "Day Change", screenName);
	}

	/**
	 * Description... Verify Flight Schedule
	 * 
	 * @throws Exception
	 */
	public void verifyFlightSchedule() throws Exception {
		switchToWindow("child");

		System.out.println("entered in to flight schedule window");

		int a = 0;
		int[] arr = { 5, 6, 7, 7, 8, 15 };

		int verfCols[] = { 3, 5 };

		waitForSync(5);

		for (int i = 1; i <= 3; i++) {
			String Firstdate = createDateFormat("dd-MMM-YYYY", arr[a], "DAY", "");
			a++;
			System.out.println(Firstdate);
			String Lastdate = createDateFormat("dd-MMM-YYYY", arr[a], "DAY", "");
			a++;
			System.out.println(Lastdate);
			String pmKey = Firstdate;
			String[] actVerfValues = { data("Route"), Lastdate };

			verify_tbl_records_multiple_cols(sheetName, "table_flightschedule;xpath", "//td", verfCols, pmKey,
					actVerfValues);
		}

		waitForSync(2);
		clickButtonSwitchtoParentWindow(sheetName, "btn_flightScheduleOk;xpath", "flight schedule ok btn ", screenName);
		waitForSync(5);

	}

	/**
	 * Description... List New Flight
	 * 
	 * @param FlightNumber
	 * @param flightStartDate
	 * @param flightEndDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listNewFlight(String FlightNumber, String flightStartDate, String flightEndDate)
			throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightStartDate;xpath", flightStartDate, "Flight StartDate", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_flightEndDate;xpath", flightEndDate, "Flight EndDate", screenName);
		keyPress("TAB");
		waitForSync(2);
		clickWebElement(sheetName, "btn_flightListButton;xpath", "List", screenName);
		waitForSync(8);
		handleAlert("Accept", screenName);

	}

	/**
	 * Description... Create New Flight
	 * 
	 * @param FlightNumber
	 * @param flightStartDate
	 * @param flightEndDate
	 * @return
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public boolean createNewFlight(String FlightNumber, String flightStartDate, String flightEndDate)
			throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightStartDate;xpath", data(flightStartDate), "Flight StartDate",
				screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_flightEndDate;xpath", data(flightEndDate), "Flight EndDate", screenName);
		keyPress("TAB");
		waitForSync(3);
		clickWebElement(sheetName, "btn_flightListButton;xpath", "List", screenName);
		waitForSync(8);

		boolean isFlightExists = handleAlert("Accept", screenName);

		return isFlightExists;

	}

	/**
	 * Description... Provide SCL weight and volume, enter MDP, LDP and LDC
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

		enterValueInTextbox(sheetName, "inbx_SCLWeight;name", data(SCLweight), "SCL Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_SCLVolume;name", data(SCLvolume), "SCL Volume", screenName);
		enterValueInTextbox("(//input[@name='specificSclCapacityulds'])[1]", data(LDC), "LDC", screenName);
		enterValueInTextbox("(//input[@name='specificSclCapacityulds'])[2]", data(LDP), "LDP", screenName);
		enterValueInTextbox("(//input[@name='specificSclCapacityulds'])[3]", data(MDP), "MDP", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Provide GSC weight and volume, enter MDP, LDP and LDC
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
		waitForSync(2);
		clickButtonSwitchWindow(sheetName, "lnk_flightUpdateSegCapacity;xpath", "Seg details ", screenName);
		enterValueInTextbox(sheetName, "inbx_gscweight;name", data(GSCweight), "SCL Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_gscvolume;name", data(GSCvolume), "SCL Volume", screenName);
		enterValueInTextbox("(//input[@name='specificGscCapacityulds'])[1]", data(LDC), "LDC", screenName);
		enterValueInTextbox("(//input[@name='specificGscCapacityulds'])[2]", data(LDP), "LDP", screenName);
		enterValueInTextbox("(//input[@name='specificGscCapacityulds'])[3]", data(MDP), "MDP", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Click Segment
	 * 
	 * @throws Exception
	 */
	public void clickSegment() throws Exception {

		clickWebElement(sheetName, "btn_Segment;xpath", "Segment", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Select Flight Frequency
	 * 
	 * @throws Exception
	 */
	public void selectFrequency() throws Exception {

		checkIfUnchecked(sheetName, "chk_segmentFrequency;xpath", "Flight Frequency", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Click Change Capacity Button
	 * 
	 * @throws Exception
	 */
	public void clickChangeCapacity() throws Exception {

		clickWebElement(sheetName, "btn_Changecapacity;name", "Change capacity", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Save Without Bursting
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveWithoutBursting() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_save;xpath", "Save button", screenName);
		waitForSync(2);

		waitForSync(8);
		handleAlert("Accept", screenName);
		handleAlert("Dismiss", screenName);
		waitForSync(3);

	}

	/**
	 * Description... Verify Status
	 * 
	 * @throws Exception
	 */
	public void verifyStatus() throws Exception {
		String status = getElementText(sheetName, "inbx_status;xpath", "status", screenName);
		String exp = "PUBLISHED";

		if (status.contains(exp)) {
			System.out.println("found true for " + status);
			onPassUpdate(screenName, "PUBLISHED", status, "Satus ", "Flight schedule");
		}

	}

	/**
	 * Description... Enter Leg Capacity Details
	 * 
	 * @param departureTime
	 * @param arrivalTime
	 * @param aircraftType
	 * @param capacityConfig
	 * @param LDC
	 * @param weight
	 * @throws InterruptedException
	 * @throws IOException
	 */
	// Overloaded method to enter the Leg Capacity Details
	public void enterLegCapacityDetails(String departureTime, String arrivalTime, String aircraftType,
			String capacityConfig, String LDC, String weight) throws InterruptedException, IOException {

		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_flightDepartureTime;xpath", data(departureTime), "Departure Time",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightArrivalTime;xpath", data(arrivalTime), "Arrival Time", screenName);
		enterValueInTextbox(sheetName, "inbx_aircraftType;xpath", data(aircraftType), "Aircraft type", screenName);
		clickWebElement(sheetName, "btn_viewCapacity;xpath", "view capcity", screenName);
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_capacityConfig;xpath", data(capacityConfig), "capacity Config", "Value");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_weight;xpath", data(weight), "Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_LDC;xpath", data(LDC), "LDP", screenName);

	}

	/**
	 * Description... Click Leg Capacity Ok Button For PPS
	 * 
	 * @throws Exception
	 */
	public void legCapacityOkButtonForPPS() throws Exception {

		clickWebElement("Generic_Elements", "btn_ok2;name", "leg capcity ok btn", screenName);
		waitForSync(4);
		clickWebElement("Generic_Elements", "btn_yes;xpath", "leg capcity yes btn", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "FLT005");
		waitForSync(4);

	}

	/**
	 * Description... Enter Leg Capacity Details
	 * 
	 * @param departureTime
	 * @param arrivalTime
	 * @param aircraftType
	 * @param weight
	 * @param volume
	 * @param LDC
	 * @param LDP
	 * @param MDP
	 * @param Q
	 * @throws InterruptedException
	 */
	public void enterLegCapacityDetails(String departureTime, String arrivalTime, String aircraftType, String weight,
			String volume, String LDC, String LDP, String MDP, String Q) throws InterruptedException {

		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_flightDepartureTime;xpath", data(departureTime), "Departure Time",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightArrivalTime;xpath", data(arrivalTime), "Arrival Time", screenName);
		enterValueInTextbox(sheetName, "inbx_aircraftType;xpath", data(aircraftType), "Aircraft type", screenName);
		enterValueInTextbox(sheetName, "inbx_weight;xpath", data(weight), "Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_volume;name", data(volume), "volume", screenName);
		enterValueInTextbox(sheetName, "inbx_LDC;xpath", data(LDC), "LDC value", screenName);
		enterValueInTextbox(sheetName, "inbx_LDP;xpath", data(LDP), "LDP value", screenName);
		enterValueInTextbox(sheetName, "inbx_MDP;xpath", data(MDP), "MDP value", screenName);
		enterValueInTextbox(sheetName, "inbx_Q;xpath", data(Q), "Q value", screenName);
		waitForSync(2);

	}

	/**
	 * Description... Click Leg Capacity Ok Button
	 * 
	 * @throws Exception
	 */
	// for handle alerts
	public void legCapacityOk() throws Exception {

		clickWebElement("Generic_Elements", "btn_ok2;name", "leg capcity ok btn", screenName);
		waitForSync(2);
		handleAlert("Accept", "MaintainFlightSchedule : FLT005");
		switchToWindow("getParent");
		switchToFrame("contentFrame", "FLT005");
		waitForSync(5);
	}

	// TC 16043
	/**
	 * Description... Leg Capacity Ok Button No Frame Switch
	 * 
	 * @throws Exception
	 */
	public void legCapacityOkNoFrameSwitch() throws Exception {

		clickWebElement("Generic_Elements", "btn_ok2;name", "leg capcity ok btn", screenName);

		switchToWindow("getParent");
		waitForSync(4);
	}

	/**
	 * Description... Edit MDP Value
	 * 
	 * @param value
	 * @throws InterruptedException
	 */

	public void editMDP(String value) throws InterruptedException {
		waitForSync(4);
		enterValueInTextbox(sheetName, "inbx_MDP;xpath", value, "MDP Value", "MaintainFlightSchedule_FLT005");
	}

	/**
	 * Description... Edit LDC Value
	 * 
	 * @param value
	 * @throws InterruptedException
	 */
	public void editLDC(String value) throws InterruptedException {
		waitForSync(4);
		enterValueInTextbox(sheetName, "inbx_LDC;xpath", value, "LDC Value", "MaintainFlightSchedule_FLT005");
	}

	/**
	 * Description... Edit LDP Value
	 * 
	 * @param value
	 * @throws InterruptedException
	 */
	public void editLDP(String value) throws InterruptedException {
		waitForSync(4);
		enterValueInTextbox(sheetName, "inbx_LDP;xpath", value, "LDP Value", "MaintainFlightSchedule_FLT005");
	}

	/**
	 * Description... Edit LDC LDP MDP
	 * 
	 * @param ldc
	 * @param ldp
	 * @param mdp
	 * @throws InterruptedException
	 */
	public void editLDCLDPMDP(String ldc, String ldp, String mdp) throws InterruptedException {
		editLDC(ldc);
		editLDP(ldp);
		editMDP(mdp);

	}

	/**
	 * Description... List New Flight with different carrierCode
	 * 
	 * @param FlightNumber
	 * @param flightStartDate
	 * @param flightEndDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listNewFlightWithDiffCode(String carrierCode, String FlightNumber, String flightStartDate,
			String flightEndDate) throws InterruptedException, AWTException, IOException {
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", carrierCode, "Carrier code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightStartDate;xpath", flightStartDate, "Flight StartDate", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_flightEndDate;xpath", flightEndDate, "Flight EndDate", screenName);
		keyPress("TAB");
		waitForSync(3);
		clickWebElement(sheetName, "btn_flightListButton;xpath", "List", screenName);
		waitForSync(8);
		handleAlert("Accept", screenName);

	}

	/**
	 * Description... Overloaded method to Enter Flight Details
	 * 
	 * @param route
	 * @param schduleType
	 * @param FCTLStation
	 * @param FCTLOffice
	 * @param flightType
	 * @throws Exception
	 */
	public void enterFlightDetails(String route, String schduleType, String FCTLStation, String FCTLOffice,
			String flightType, String flightOwner) throws Exception {

		switchToFrame("contentFrame", "FLT005");
		enterValueInTextbox(sheetName, "inbx_flightRoute;xpath", data(route), "Flight route", screenName);
		enterValueInTextbox(sheetName, "inbx_FlightOwner;name", data(flightOwner), "Flight Owner", screenName);
		checkIfUnchecked(sheetName, "chk_flightFrequency;xpath", "Flight Frequency", screenName);
		selectValueInDropdown(sheetName, "lst_flightScheduleType;xpath", data(schduleType), "schduleType",
				"VisibleText");
		enterValueInTextbox(sheetName, "inbx_controlStation;xpath", data(FCTLStation), "FCTL Station", screenName);
		enterValueInTextbox(sheetName, "inbx_controlOffice;xpath", data(FCTLOffice), "FCTL office", screenName);
		selectValueInDropdown(sheetName, "lst_flightType;xpath", data(flightType), "Flight Type", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_flightEffectiveDate;xpath", ".", "Effective Date", screenName);
		waitForSync(2);
		performKeyActions(sheetName, "inbx_flightEffectiveDate;xpath", "TAB", "Effective Date", screenName);
		waitForSync(3);
		clickButtonSwitchWindow(sheetName, "lnk_flightUpdateCapacity;xpath", "Update Capacity", screenName);

	}

	public void enterOperatingReference(String a, String b) throws Exception {
		clickButtonSwitchWindow(sheetName, "lnk_flightUpdateCapacity;xpath", "Update Capacity", screenName);
		waitForSync(5);
		scrollBars("bottom", 5);
		clickWebElement(sheetName, "inbx_operatingReference_LOV;id", "LOV btn", screenName);
		waitForSync(4);
		enterValueInTextbox(sheetName, "inbx_operatingReference_1;xpath", a, "Operating reference 1", screenName);
		enterValueInTextbox(sheetName, "inbx_operatingReference_2;xpath", b, "Operating reference 2", screenName);
		clickWebElement(sheetName, "btn_opRef_ok;name", "OK btn", screenName);
		waitForSync(6);

	}

	/**
	 * A-8705 Lists the new flight
	 * 
	 * @param CarrierCode
	 * @param FlightNumber
	 * @param flightStartDate
	 * @param flightEndDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void listNewFlight(String CarrierCode, String FlightNumber, String flightStartDate, String flightEndDate)
			throws InterruptedException, AWTException, IOException {
		System.out.println();
		clearText(sheetName, "inbx_carrierCode;name", "CarrierCode", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(CarrierCode), "CarrierCode", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightStartDate;xpath", flightStartDate, "Flight StartDate", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_flightEndDate;xpath", flightEndDate, "Flight EndDate", screenName);
		keyPress("TAB");
		waitForSync(3);
		clickWebElement(sheetName, "btn_flightListButton;xpath", "List", screenName);
		waitForSync(8);
		handleAlert("Accept", screenName);

	}

	public void listFlight(String CarrierCode, String FlightNumber, String flightStartDate, String flightEndDate)
			throws InterruptedException, IOException

	{
		clearText(sheetName, "inbx_carrierCode;name", "CarrierCode", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(CarrierCode), "CarrierCode", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightStartDate;xpath", flightStartDate, "Flight StartDate", screenName);
		enterValueInTextbox(sheetName, "inbx_flightEndDate;xpath", flightEndDate, "Flight EndDate", screenName);
		clickWebElement(sheetName, "btn_flightListButton;xpath", "List", screenName);
		waitForSync(5);
	}

	/**
	 * @author A-7271
	 * @param expStatus
	 *            Desc : verify flight status
	 */
	public void verifyFlightStatus(String expStatus) {
		String flightStatus = getAttributeWebElement(sheetName, "inbx_flightStatus;name", "Flight Status", "value",
				screenName).toUpperCase();
		verifyScreenTextWithExactMatch(screenName, expStatus, flightStatus, "Flight Status",
				"Verification of flight status");

	}

	/**
	 * @author A-6260 Desc..verify flight route
	 * @param expMsg
	 */
	public void verifyFlightRoute(String expMsg) {
		String flightRoute = getAttributeWebElement(sheetName, "inbx_flightRoute;xpath", "Flight Route", "value",
				screenName);
		verifyScreenTextWithExactMatch(screenName, expMsg, flightRoute, "Flight Route", "Verification of flight route");

	}

	/**
	 * @author A-7271
	 * @param frequency
	 *            Desc : verify flight frequency
	 * @throws Exception
	 */
	public void checkFrequency(String frequency) throws Exception {
		if (!frequency.equals("All")) {
			String day = createDateFormat("EEE", Integer.parseInt(frequency), "DAY", "");

			if (day.equals("Mon")) {
				frequency = "Monday";
			} else if (day.equals("Tue")) {
				frequency = "Tuesday";
			} else if (day.equals("Wed")) {
				frequency = "Wednesday";
			} else if (day.equals("Thu")) {
				frequency = "Thursday";
			} else if (day.equals("Fri")) {
				frequency = "Friday";
			} else if (day.equals("Sat")) {
				frequency = "Saturday";
			} else if (day.equals("Sun")) {
				frequency = "Sunday";
			}
		}

		switch (frequency) {
		case "All": {
			String locator = xls_Read.getCellValue(sheetName, "chk_flightFrequency;xpath");
			if (driver.findElement(By.xpath(locator)).isSelected()) {
				writeExtent("Pass", "Frequency of the flight matches with 1234567");
			} else {
				writeExtent("Fail", "Frequency of the flight does not match with 1234567");
			}
			break;
		}
		case "Monday": {
			String locator = xls_Read.getCellValue(sheetName, "chk_flightFrequency_mon;xpath");
			if (driver.findElement(By.xpath(locator)).isSelected()) {
				writeExtent("Pass", "Frequency of the flight matches with monday");
				map.put("frequency", "1");
			} else {
				writeExtent("Fail", "Frequency of the flight does not match with monday");
			}
			break;
		}
		case "Tuesday": {
			String locator = xls_Read.getCellValue(sheetName, "chk_flightFrequency_tue;xpath");
			if (driver.findElement(By.xpath(locator)).isSelected()) {
				writeExtent("Pass", "Frequency of the flight matches with tuesday");
				map.put("frequency", "2");
			} else {
				writeExtent("Fail", "Frequency of the flight does not match with tuesday");
			}
			break;
		}
		case "Wednesday": {
			String locator = xls_Read.getCellValue(sheetName, "chk_flightFrequency_wed;xpath");
			if (driver.findElement(By.xpath(locator)).isSelected()) {
				writeExtent("Pass", "Frequency of the flight matches with wednesday");
				map.put("frequency", "3");
			} else {
				writeExtent("Fail", "Frequency of the flight does not match with wednesday");
			}
			break;
		}
		case "Thursday": {
			String locator = xls_Read.getCellValue(sheetName, "chk_flightFrequency_thu;xpath");
			if (driver.findElement(By.xpath(locator)).isSelected()) {
				writeExtent("Pass", "Frequency of the flight matches with thursday");
				map.put("frequency", "4");
			} else {
				writeExtent("Fail", "Frequency of the flight does not match with thursday");
			}
			break;
		}
		case "Friday": {
			String locator = xls_Read.getCellValue(sheetName, "chk_flightFrequency_fri;xpath");
			if (driver.findElement(By.xpath(locator)).isSelected()) {
				writeExtent("Pass", "Frequency of the flight matches with friday");
				map.put("frequency", "5");
			} else {
				writeExtent("Fail", "Frequency of the flight does not match with friday");
			}
			break;
		}
		case "Saturday": {
			String locator = xls_Read.getCellValue(sheetName, "chk_flightFrequency_sat;xpath");
			if (driver.findElement(By.xpath(locator)).isSelected()) {
				writeExtent("Pass", "Frequency of the flight matches with saturday");
				map.put("frequency", "6");
			} else {
				writeExtent("Fail", "Frequency of the flight does not match with saturday");
			}
			break;
		}
		case "Sunday": {
			String locator = xls_Read.getCellValue(sheetName, "chk_flightFrequency_sun;xpath");
			if (driver.findElement(By.xpath(locator)).isSelected()) {
				writeExtent("Pass", "Frequency of the flight matches with sunday");
				map.put("frequency", "7");
			} else {
				writeExtent("Fail", "Frequency of the flight does not match with sunday");
			}
			break;
		}
		}
	}

	/**
	 * A-7271 Lists the new flight
	 * 
	 * @param CarrierCode
	 * @param FlightNumber
	 * @param flightStartDate
	 * @param flightEndDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void listNewFlight(String CarrierCode, String FlightNumber, String flightStartDate, String flightEndDate,
			String flightType) throws InterruptedException, AWTException, IOException {
		System.out.println();
		clearText(sheetName, "inbx_carrierCode;name", "CarrierCode", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(CarrierCode), "CarrierCode", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightStartDate;xpath", flightStartDate, "Flight StartDate", screenName);
		enterValueInTextbox(sheetName, "inbx_flightEndDate;xpath", flightEndDate, "Flight EndDate", screenName);
		clickWebElement(sheetName, "btn_flightListButton;xpath", "List", screenName);
		waitForSync(5);
		switchToFrame("default");

		try {

			int size = driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).size();
			System.out.println(size);
			while (size != 1) {
				switchToFrame("contentFrame", "FLT005");
				driver.findElement(By.id(xls_Read.getCellValue(sheetName, "btn_clear;id"))).click();
				createFlight(flightType);
				waitForSync(2);

				String fltcode = getAttributeWebElement(sheetName, "inbx_carrierCode;name", "Carrier Code", "value",
						screenName);
				while (!fltcode.equals("")) {
					waitForSync(2);
					fltcode = "";
					fltcode = getAttributeWebElement(sheetName, "inbx_carrierCode;name", "Carrier Code", "value",
							screenName);
				}

				listFlight(CarrierCode, FlightNumber, flightStartDate, flightEndDate);
				switchToFrame("default");
				size = driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).size();
			}

			handleAlert("Accept", screenName);

		}

		catch (Exception e) {

		}
	}
}
