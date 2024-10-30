package sanity_mobility;

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
import rest_sstunitch.JSONBody;
import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.UldSightingHHT;
import screens.WarehouseShipmentEnquiry_WHS011;

public class ULDSighting_Thru extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public MarkFlightMovements_FLT006 FLT006;
	public MaintainOperationalFlight_FLT003 FLT003;
	public WarehouseShipmentEnquiry_WHS011 WHS011;
	public UldSightingHHT uldsighthht;
	public ListMessages_MSG005 MSG005;
	public JSONBody jsonbodys;
	public rest_unitdloc.JSONBody jsonbodyu;
	public ImportManifest_OPR367 OPR367;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ExportManifest_OPR344 OPR344;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String improppath = "\\src\\resources\\IM.properties";
	String sheetName = "uldsighting_thru";

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
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		WHS011 = new WarehouseShipmentEnquiry_WHS011(driver, excelreadwrite, xls_Read);
		uldsighthht = new UldSightingHHT(driver, excelreadwrite, xls_Read);
		jsonbodyu = new rest_unitdloc.JSONBody(driver, excelreadwrite, xls_Read);
		jsonbodys = new JSONBody(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "uldsighting_thru")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "uldsighting_thru")
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

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));

			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_US"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_US"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_US"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_US"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_US"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_US"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_US"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_US"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_US"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_US"));
			map.put("ShipperCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_US"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_US"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_US"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));

			map.put("RegulatedAgentCode",
					WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry"));
			
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "America/New_York");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);

			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			/** Flight-1 Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode", "prop~flightNo", startDate, startDate, "FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber2", cust.data("prop~flight_code") + cust.data("prop~flightNo"),
					proppath);
			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			FlightNum1 = FlightNum1.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum1);
			map.put("FlightNo", FlightNum1.substring(2));

			/** Flight-2 Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode", "prop~flightNo", startDate, startDate, "FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);

			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum2 = FlightNum2.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));

			map.put("FullFlightNo1", FlightNum2);
			map.put("FlightNo1", FlightNum2.substring(2));

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),
					proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));

			/*** MSG005 - SSM Message loading For flight 1 ******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");

			/** Flight - 1 **/
			map.put("FlightNumber", cust.data("FullFlightNo"));
			map.put("Org", cust.data("Origin"));
			map.put("Des", cust.data("Transit"));
			map.put("ATD", cust.data("ATD_Local"));
			map.put("ATA", cust.data("ATA_Local"));

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");

			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Transit", "", "SSM_NEW");

			/** Flight - 2 **/
			map.put("FlightNumber", cust.data("FullFlightNo1"));
			map.put("Org", cust.data("Transit"));
			map.put("Des", cust.data("Destination"));
			map.put("ATD", cust.data("ATD_Local2"));
			map.put("ATA", cust.data("ATA_Local2"));

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Transit", "", "SSM_NEW");

			/****** Loading XFBL for Second Flight ***/
			// Create the message XFBL with AWB1
			map.put("FullFlightNumber", cust.data("FullFlightNo1"));
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
					+ cust.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routings[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routings);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Transit", "", "XFBL_2", true);

			/***** XFWB Loading for AWB -Secured ***/
			// Create XFWB message
			map.put("awbnumber", cust.data("FullAWBNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Transit", "", "XFWB_Transit_WithScreeningInfo", true);

			/** -XFFM Message loading -Secured**/

			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);

			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			// Adding SPX to XFFM
			String scc1[] = { cust.data("SCC") + ";" + cust.data("val~SPX") };
			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";"
					+ cust.data("Destination") + ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc1, routing1, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");

			/** Switch role to Destination **/
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			/** Mark Flight Movement **/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("FlightNo", "StartDate");
			String currtime = cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00", "CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime, currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");

			/**** Import Manifest ***/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.maximizeAllDetails();
			OPR367.verifyBreakdownInstructionsTag("val~Thru unit");
			OPR367.verifyShipment("AWBNo");
			String ULDNo[] = { cust.data("UldNum") };
			// verify uld details
			OPR367.verifyUldDetails(1, ULDNo);
			cust.closeTab("OPR367", "Import Manifest");

			// Creating random 3-digit number for Height everytime
			String height = cust.createRandomNumber("3");
			map.put("Height", height);

			/******* POST REQUEST ****/
			jsonbodys.postRequest(cust.data("EquipmentID"), cust.data("UldNum"), cust.data("ScaleWeight"),
					cust.data("Height"), cust.data("ForwardLinkLen"), cust.data("AfterLinkLen"),
					cust.data("LeftLinkLen"), cust.data("RightLinkLen"));

			/*** Launch emulator - uldsighting app **/

			libr.launchUldSightingApp("uldsighting-app");

			// Login in to ULD Sighting App
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);

			uldsighthht.clickDone();
			uldsighthht.enterUldNumber("UldNum");
			map.put("SightingIMLoc", WebFunctions.getPropertyValue(improppath, "SightingIMLoc"));
			uldsighthht.selectFwLocationBeforeSighting("SightingIMLoc");
			uldsighthht.clickSight();
			uldsighthht.clickGetScaleWeight();
			uldsighthht.verifyScaleWeightAndHeightPopulated("ScaleWeight", "Height");
			uldsighthht.verifyOverhangDetailsPopulated("ForwardLinkLen", "AfterLinkLen", "LeftLinkLen", "RightLinkLen");
			uldsighthht.selectContour("Contour");
			uldsighthht.verifySighted("UldNum");
			uldsighthht.clickComplete();
			libr.quitApp();

			/******* POST REQUEST ****/
			// Trigger RelocateStorageUnit to the exit point of IM with
			// Occupancy status O
			jsonbodyu.postRequest(cust.data("EquipmentID"), cust.data("UldNum"), cust.data("ForwardLocation"),
					cust.data("OccupancyStatus"), cust.data("WareHouse"));

			/*** WHS011 - WAREHOUSE SHIPMENT ENQUIRY ***/
			cust.searchScreen("WHS011", "Warehouse Shipment Enquiry");
			WHS011.enterSU("UldNum");
			WHS011.clickAlsoShowEmptySUCheckbox();
			WHS011.clickList();
			// Verify Location of uld at the entrance of IM
			int verfCol[] = { 4 };
			String[] actVerfVal = { "TE5-" + cust.data("ForwardLocation") };
			WHS011.verifyWarehouseDetailsWithPmKey(verfCol, actVerfVal, "UldNum");
			cust.closeTab("WHS011", "Warehouse Shipment Enquiry");
			
			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code", "FlightNo", "StartDate");
			//Verify auto breakdown happens
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");
			
			/**** OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo1","StartDate");
			OPR344.verifyULDInAssignedShipment("UldNum",true);
			OPR344.verifyBuildUpComplete("UldNum");
			cust.closeTab("OPR344", "Export Manifest");


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		} finally {
			try {
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
