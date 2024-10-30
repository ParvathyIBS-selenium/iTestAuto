package importmanifest;

/**  TC_03_Verify PALLETS  sighted details sent to CGOSPA at ULD sighting   **/

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.AFLS_Booking;
import screens.AFLS_FlightPlan;
import screens.CGOICSS;
import screens.CGOSPA;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.UldSightingHHT;


public class PublishULDRelocation_TC_3041 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public MaintainFlightSchedule_FLT005 FLT005;
	public AFLS_Booking afls;
	public AFLS_FlightPlan aflsfp;
	public ListMessages_MSG005 MSG005;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public UldSightingHHT uldsighthht;
	public CGOICSS Cgoicss;
	public CGOSPA Cgospa;
	public Cgocxml Cgocxml;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\ImportManifest.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "ImportManifest_SIT";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgoicss = new CGOICSS(driver, excelreadwrite, xls_Read);
		Cgospa = new CGOSPA(driver, excelreadwrite, xls_Read);
		afls = new AFLS_Booking(driver, excelreadwrite, xls_Read);
		aflsfp = new AFLS_FlightPlan(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		uldsighthht = new UldSightingHHT(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_3039")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_3039")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String EndDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String bookingDate = cust.createDateFormat("dd/MMM/YYYY", 0, "DAY", "");
			map.put("BookDate", bookingDate);
			String endDate = cust.createDateFormat("dd/MMM/YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));


			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ConsigneeCountryName",WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));


			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));


			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode", "prop~flightNo", startDate, EndDate, "FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			//libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Login to "CGOICSS"
			String[] cgoicsslogin = libr.getApplicationParams("Cgoicss");
			driver.get(cgoicsslogin[0]); // Enters URL
			cust.loginToCGOICSS(cgoicsslogin[1], cgoicsslogin[2]);

			/** Flight Creation **/
			Cgoicss.clickOnCreateFlight();
			Cgoicss.createNewFlight("carrierCode", "FlightNo", "BookDate", "EndDate");
			Cgoicss.selectDayofOperations();
			Cgoicss.addLeg();
			Cgoicss.enterLegDetails("ATD_Local", "ATA_Local", "Origin", "Destination", "serviceType", "AircraftType","carrierCode");
			Cgoicss.clickSave();
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/*** Login to AFLS_BOOKING **********/
			String[] aflsbooking = libr.getApplicationParams("afls");
			driver.get(aflsbooking[0]);
			cust.loginToAFLS(aflsbooking[1], aflsbooking[2]);

			afls.selectTitleAndSubTitleTab("titleTab", "titleTab");
			afls.enterAWB("CarrierNumericCode", "AWBNo");
			afls.enterAWBOrgAndDest("Origin", "Destination");
			afls.enterBookingOrgAndDest("Origin", "Destination");
			afls.enterBookingDeliveryAndArrivalDate("BookDate", "BookDate");
			afls.enterBookingDeliveryAndArrivalTime("ATD_Local", "ATA_Local");

			afls.selectCommodityCode("CommodityCode");
			afls.selectServiceLevelAndHandlingNeeds("serviceLevel", "handlingNeeds");
			afls.enterCustomerID("AgentCode");
			afls.enterFlightInfo("carrierCode", "FlightNo", "Origin", "Destination", "BookDate");
			afls.enterShipmentDetails("Pieces", "Weight", "Volume");
			afls.enterRateDetails("IATARate");
			afls.selectRouteSearchAndEvaluationSetting("no");
			afls.clickSubmitBooking();
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Login to ALFS_FlightPlan
			cust.loginToAFLS_FlightPlan();

			aflsfp.clickMenu();
			aflsfp.selectMenuOption("val~Flight plan");
			libr.waitForSync(12);
			aflsfp.enterFlightDetails("carrierCode", "FlightNo", "BookDate", "BookDate");
			aflsfp.enterFlightBoardPoint("Origin");
			aflsfp.clickSearch();

			// FBL Trigger
			aflsfp.clickSend();
			aflsfp.selectSendMessages("val~FBL");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/*** Login to cgocxml **********/
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFWB Message loading **/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFFM Message loading **/
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";"
					+ cust.data("Destination") + ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };

			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing1, uld);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/** Switch role to Destination **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/** Mark Flight Movement **/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("prop~flightNo", "StartDate");
			String currtime = cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00", "CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime, currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");


			/*** Launch emulator - uldsighting app **/
			libr.launchUldSightingApp("uldsighting-app");
			// Login in to ULD Sighting App
			String[] hht2 = libr.getApplicationParams("hht");
			cust.loginHHT(hht2[0], hht2[1]);

			uldsighthht.clickDone();
			uldsighthht.enterUldNumber("UldNum");
			uldsighthht.selectFwLocationBeforeSighting("TargetLocation");
			uldsighthht.clickSight();
			uldsighthht.verifySighted("UldNum");
			uldsighthht.clickComplete();
			libr.quitApp();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/*** Login to CGOSPA **********/
			String[] cgospa = libr.getApplicationParams("Cgospa");
			driver.get(cgospa[0]);
			cust.loginToCGOSPA(cgospa[1], cgospa[2]);

			// listing the awb in the search/archives section of CGOSPA
			Cgospa.selectLanguage();
			Cgospa.clickSearchOrArchives();
			String StartDate = cust.createDateFormatWithTimeZone("MM/dd/YYYY", 0, "DAY", "");
			String enddate = cust.createDateFormatWithTimeZone("MM/dd/YYYY", 7, "DAY", "");
			Cgospa.enterStartDate(StartDate);
			Cgospa.enterEndDate(enddate);
			Cgospa.listAWBNo("AWBNo");
			libr.waitForSync(3);

			// verify the awb details in the Research/Archives section
			String pmKey = cust.data("AWBNo");
			int[] verfCols = { 5, 6 };
			String[] actVerfValues = { cust.data("Origin"), cust.data("Destination") };
			Cgospa.verifyAWBDetails(verfCols, actVerfValues, pmKey);
			Cgospa.clickAWBNo();

			// verifying the details in publishULDRelocation message reflected
			// in CGOSPA
			String pmkey = cust.data("UldNum");
			int[] verfCols1 = { 13, 14 };
			String[] actVerfValues1 = { cust.data("UldNum"), cust.data("TargetZone") + cust.data("TargetLocation") };
			Cgospa.verifyFlightDetails(verfCols1, actVerfValues1, pmkey);
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/** Switch role to Destination **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/*** MSG005- Verify PublishULDRelocation details ***/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("ULDWHSRELDTL");
			MSG005.clickReference();
			MSG005.enterReferenceValue("UldNum");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyCGOSPADetails = cust.data("UldNum") + " - " + cust.data("WareHouseCode") + " - "+ cust.data("SourceLocation");
			map.put("pmKeyCGOSPADetail", pmKeyCGOSPADetails);
			int verfColsCGOSPADetails[] = { 9 };
			String[] actVerfValuesverfColsCGOSPADetails = { "Sent" };
			MSG005.verifyMessageDetails(verfColsCGOSPADetails, actVerfValuesverfColsCGOSPADetails, pmKeyCGOSPADetails,
					"val~PublishULDRelocation", false);
			MSG005.clickCheckBox("pmKeyCGOSPADetail");
			MSG005.clickView();
			List<String> msgContents = new ArrayList<String>();
			msgContents.add("val~<ns2:publishEntity>" + "uldRelocationDetails" + "</ns2:publishEntity>");
			msgContents.add("val~<ns2:sourceLocationCode>" + cust.data("SourceLocation") + "</ns2:sourceLocationCode>");
			msgContents.add("val~<ns2:sourceZoneCode>" + cust.data("SourceZone") + "</ns2:sourceZoneCode>");
			msgContents
			.add("val~<ns2:sourceWarehouseCode>" + cust.data("WareHouseCode") + "</ns2:sourceWarehouseCode>");
			msgContents.add("val~<ns2:targetLocationCode>" + cust.data("TargetLocation") + "</ns2:targetLocationCode>");
			msgContents.add("val~<ns2:targetZoneCode>" + cust.data("TargetZone") + "</ns2:targetZoneCode>");
			msgContents
			.add("val~<ns2:targetWarehouseCode>" + cust.data("WareHouseCode") + "</ns2:targetWarehouseCode>");
			MSG005.verifyMessageContent(msgContents, "PublishULDRelocation", true);
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		} finally {
			try {
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
