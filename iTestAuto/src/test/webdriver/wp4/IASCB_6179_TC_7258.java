package wp4;

/** FSU-RCF is triggered as part of ULD Sighting for KL  **/

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
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ChecksheetHHT;
import screens.ImportManifest_OPR367;
import screens.ListAuditEnquiry_SHR011;
import screens.ListMessages_MSG005;
import screens.LoadUnloadRFSSST;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;
import screens.ReportingAtDockHHT;
import screens.ServicePointAllocationHHT;
import screens.Servicepointoverview_TGC015;
import screens.UldSightingHHT;

public class IASCB_6179_TC_7258 extends BaseSetup {

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
	public MarkFlightMovements_FLT006 FLT006;
	public ListMessages_MSG005 MSG005;
	public ImportManifest_OPR367 OPR367;
	public Servicepointoverview_TGC015 TGC015;
	public ListAuditEnquiry_SHR011 SHR011;
	public ChecksheetHHT checkhht;
	public LoadUnloadRFSSST ldRfssst;
	public ReportingAtDockHHT reportdockhht;
	public ServicePointAllocationHHT serpointhht;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public UldSightingHHT uldsighthht;

	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String proppathsst = "\\src\\resources\\SSTLocators.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "wp4";

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
		serpointhht = new ServicePointAllocationHHT(driver, excelreadwrite, xls_Read);
		TGC015 = new Servicepointoverview_TGC015(driver, excelreadwrite, xls_Read);
		reportdockhht = new ReportingAtDockHHT(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		SHR011 = new ListAuditEnquiry_SHR011(driver, excelreadwrite, xls_Read);
		uldsighthht = new UldSightingHHT(driver, excelreadwrite, xls_Read);
		ldRfssst = new LoadUnloadRFSSST(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_7258")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_7258")
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

			/** Switch role to Destination **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Amsterdam");
			map.put("StartDate", startDate);
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Amsterdam"));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FWBDate",
					cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "Europe/Amsterdam");
			map.put("XFWBDate", flightdate1);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_ES"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_ES"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_ES"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_ES"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_ES"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_ES"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_ES"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_ES"));
			map.put("ShipperCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_ES"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_ES"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_ES"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_NL3"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_NL3"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_NL3"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_NL3"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_NL3"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_NL3"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_NL3"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_NL3"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_NL3"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_NL3"));


			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_ES"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_ES"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "MAD"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury",
					WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			// Regulated Agent details
			map.put("RegulatedAgentCode",
					WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));
			String currtme1 = cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");
			String currentday = cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Paris");
			String SD = currentday + currtme1;
			map.put("SDtime", SD);
			String screenmethod = cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod", screenmethod);
			map.put("UserName", "T133072");

			/******* OPR026 - Capture AWB *****/
			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No 1
			cust.setPropertyValue("FullAWBNo2", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),
					proppath);
			cust.setPropertyValue("AWBNo2", cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo2", cust.data("prop~FullAWBNo2"));
			map.put("AWBNo2", cust.data("prop~AWBNo2"));

			/******* OPR026 - Capture AWB *****/
			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No 2
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),
					proppath);
			cust.setPropertyValue("AWBNo", cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode", "prop~flightNo", startDate, startDate, "FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			/*** MSG005 - SSM Message loading ******/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");

			/** XFWB Message loading for AWB 1 **/

			map.put("awbNumber", cust.data("FullAWBNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo", true);

			/** XFWB Message loading for AWB 2 **/
			map.put("awbNumber", cust.data("FullAWBNo2"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo", true);

			/** XFFM Message loading **/
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String shipment[] = {
					libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
							+ libr.data("Volume") + ";" + libr.data("ShipmentDesc"),
					libr.data("FullAWBNo2") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
							+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC"), cust.data("SCC") };
			String routing1[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport"),
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			int[] shipments = { 2 };
			
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing1, uld, shipments);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");
			
			/** Switch role to Destination **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");
			
			/**Mark Flight Movement**/
			
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode", "FlightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Amsterdam");
			map.put("ATA", currtime);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","StartDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,startDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");


			/******** Import Manifest *********/
			
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			String ulds[] = { cust.data("UldNum") };
			OPR367.verifyUldDetails(1, ulds);
			OPR367.verifyShipment("AWBNo");
			OPR367.verifyShipment("AWBNo2");
			OPR367.SaveDetails();
			cust.closeTab("OPR367", "Import Manifest");

			/*** Launch emulator - sst **/
			libr.launchSSTApp("sst_smartlox-app", true);
			// Login in to SST
			String[] sst = libr.getApplicationParams("hht2");
			cust.loginSST(sst[0], sst[1], "Bonded", true);

			/*** TOKEN GENERATION IN DROP OFF PICK UP SST SCREEN **/
			ldRfssst.invokeLoadUnloadRFSSSTScreen();
			ldRfssst.addFlightDetails("carrierCode", "FlightNo", "CurrentDate");
			ldRfssst.clickProceed();
			ldRfssst.enterTruckingCompanyName("TruckCompanyCode", "TruckingCompany");
			ldRfssst.enterDriverDetailsWithScroll("StartDate", "Destination");
			ldRfssst.clickProceed();
			ldRfssst.selectVehicletype("VehicleType");
			ldRfssst.clickProceed();
			// verifying token generated
			libr.waitForSync(5);
			ldRfssst.verifyTokenGeneration("TokenId");
			ldRfssst.getAndVerifyCounterServicePointName("ServicePoint");
			libr.quitApp();

			/************ TGC015- SERVICE POINT OVERVIEW *****/
			cust.searchScreen("TGC015", "Servicepointoverview");
			libr.waitForSync(3);
			TGC015.selectWarehouse("servicetype");
			// verifying token generated got displayed
			TGC015.verifyTokenIsDisplayed("TokenId");
			cust.closeTab("TGC015", "Service Point Overview");

			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			String[] hht2 = libr.getApplicationParams("hht2");
			// Login in to HHT
			cust.loginHHT(hht2[0], hht2[1]);

			/*** HHT - SERVICE POINT ALLOCATION ****/
			if (cust.data("tokenInWaitingArea").equals("true")) {
				serpointhht.invokeServicePointAllocationScreen();
				serpointhht.enterToken("TokenId");
				serpointhht.clickselectServicePointDropdown();
				serpointhht.callForward();
				serpointhht.confirmIfCallForwarded();
				cust.clickBack("Service Point Allocation");
			}

			/*** HHT - REPORTING AT DOCK ****/
			reportdockhht.invokeReportingAtDockScreen();
			reportdockhht.enterToken("TokenId");
			reportdockhht.captureCheckSheet();
			reportdockhht.clickSaveCaptureChecksheet();
			reportdockhht.start();
			reportdockhht.complete();
			reportdockhht.releaseDock();
			// libr.quitApp();

			/*** Launch emulator - uldsighting app **/
			libr.launchUldSightingApp("uldsighting-app");
			// Login in to ULD Sighting App
			cust.loginHHT(hht2[0], hht2[1]);

			uldsighthht.clickDone();
			uldsighthht.enterUldNumber("UldNum");
			uldsighthht.clickSight();
			uldsighthht.verifySighted("UldNum");
			uldsighthht.selectFwLocation("ForwardLocation");
			uldsighthht.clickComplete() ;
			libr.quitApp();

			/******* Verify xFSU-RCF message in MSG005 ******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.selectStatus("Sent");
			String pmKey = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo");
			MSG005.clickList();
			MSG005.verifyIfMessageTriggered(pmKey, cust.data("ProfileId"), "XFSU-RCF", true);
			libr.waitForSync(3);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/******* Verify xFSU-RCF message in MSG005 ******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.selectStatus("Sent");
			String pmKey1 = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo2");
			MSG005.clickList();
			MSG005.verifyIfMessageTriggered(pmKey1, cust.data("ProfileId"), "XFSU-RCF", true);
			libr.waitForSync(3);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/** SHR011 - List Audit Enquiry **/
			cust.searchScreen("SHR011", "List Audit Enquiry");
			SHR011.selectModuleName(cust.data("ModuleName"));
			SHR011.selectSubModuleName(cust.data("SubModuleName"));
			SHR011.enterFromDate(startDate);
			SHR011.enterToDate(startDate);
			SHR011.enterOpsULDDetails("UldNum", "carrierCode", "FlightNo", "StartDate");
			SHR011.listDetails();
			map.put("pmKey", "ULD Sighted");
			int verfCols[] = { 1, 5 };
			String actVerfValues[] = { "ULD Sighted",
					"Flight=" + cust.data("carrierCode") + cust.data("FlightNo") + "/" + cust.data("StartDate") };
			SHR011.verifyTransactionDetailsValue(verfCols, actVerfValues, cust.data("pmKey"));
			SHR011.closeTab("SHR011", "List Audit Enquiry");

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

		finally {
			try {
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
