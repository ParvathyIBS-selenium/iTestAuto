package iascb_121133;

import java.util.Arrays;

/** TC_06_Verify breakdown of split SCC shipment with additional split info data **/

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
import screens.BreakDownScreen_OPR004;
import screens.BuildUpHHT;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.TransportOrderListing;

public class IASCB_121133_TC_10226 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public BreakDownScreen_OPR004 OPR004;
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainFlightSchedule_FLT005 FLT005;
	public GoodsAcceptance_OPR335 OPR335;
	public BuildUpHHT buhht;
	public ExportManifest_OPR344 OPR344;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public ListMessages_MSG005 MSG005;
	public TransportOrderListing to;
	public MaintainOperationalFlight_FLT003 FLT003;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "IASCB_121133";

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
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004=new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		buhht = new BuildUpHHT(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "IASCB_121133_TC_10226")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "IASCB_121133_TC_10226")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));


			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Login to iCargo
			
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// creating flight number
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/** Switch role to Origin **/
			cust.switchRole("Origin", "Origin", "RoleGroup"); 
			
			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/
			
			cust.createFlight("FullFlightNumber");
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlightDetails("prop~flight_code","prop~flightNo", "StartDate");
			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeAMS=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			System.out.println(currtimeAMS);
			String STD=cust.timeCalculation(currtimeAMS, "dd-MMM-yyyy HH:mm","HOUR",3);
			System.out.println(STD.split(" ")[1]);
			map.put("STDTime", STD.split(" ")[1]);
			String STA=cust.timeCalculation(currtimeAMS, "dd-MMM-yyyy HH:mm","HOUR",5);
			System.out.println(STA.split(" ")[1]);
			map.put("STATime", STA.split(" ")[1]);
			FLT003.enterLegCapacityDetails("STDTime","STATime", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");


			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/*** MESSAGE - loading XFWB **********/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			String sccs[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1] };
			
			// Create XFWB message
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs", sccs);
			
			// Load XFWB
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_MultipleSCCs", true);
			
			/****** XFFM LOADING****/
		
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			//ULD Number
			String uldNo=OPR335.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));	
			System.out.println(cust.data("ULDNo"));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { cust.data("FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
					+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc")};
			String scc[] = {cust.data("SCC").split(",")[0] + ";" +cust.data("SCC").split(",")[1]};
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport")};
			String uld[] = { cust.data("UldType")+";"+ cust.data("ULDNo")+";"+cust.data("carrierCode")};

			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");
			

			/**** OPR026 - Capture AWB ****/
			
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			String pcs[] = { libr.data("SplitPcs").split(",")[0], libr.data("SplitPcs").split(",")[1] };
			String wgt[] = { libr.data("Weight1"), libr.data("Weight2") };
			OPR026.splitShipmentWithSCC(libr.data("SCC"), pcs,wgt);
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");
			

			/** Switch role to Destination **/
			
			cust.switchRole("Destination", "Origin", "RoleGroup");

			/******** Mark Flight Movement ******/

			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode", "prop~flightNo", "StartDate");
			FLT006.clickFlightMovementArrivalDetailsLink();
			FLT006.clickFlightMovementDepartureDetailsLink();
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");

			/******** Import Manifest *********/

			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.verifyShipment("AWBNo");
			OPR367.SaveDetails();
			OPR367.closeTab("OPR367", "Import Manifest");

			/** Breakdown Screen **/
			
			cust.searchScreen("OPR004", "Breakdown");
			OPR004.listFlightwithShipment(cust.data("UldNum"), "carrierCode", "FlightNo", "StartDate");
			System.out.println(cust.data("BDNlocation").split(",")[0]);
			String[] Location = { cust.data("BDNlocation").split(",")[0], cust.data("BDNlocation").split(",")[1] };
			String[] Pieces = { cust.data("RcvdPcs").split(",")[0], cust.data("RcvdPcs").split(",")[1] };
			String[] Weight = { cust.data("RcvdWt").split(",")[0], cust.data("RcvdWt").split(",")[1] };
			String scs[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1] };
			
			//Verification of Split scc Summary Table
			
			OPR004.switchInToSplitWindow();
			
			int[] verfPieces0={1};
			String[] actVerfValues2={Pieces[0]};
			OPR004.verifySplitSccSummaryDetails(verfPieces0,actVerfValues2,scs[0]);
			
			int[] verfPieces1={1};
			String[] actVerfValues3={Pieces[1]};
			OPR004.verifySplitSccSummaryDetails(verfPieces1,actVerfValues3,scs[1]);
			
			OPR004.switchOutToSplitWindow();
			
			//Split Breakdown
			OPR004.captureSplitBreakdownandVerifySccColumn(2, Pieces, Weight, Location, scs);
			
			//Capture check sheet		
			OPR004.clickCheckBoxAll();
			OPR004.clickCaptureCheckSheet();
			OPR004.captureChecksheet(true);
			
			OPR004.saveOPR004Alert();
			String[] SU=OPR004.getSplitSUNumber("AWBNo");
			System.out.println(Arrays.asList(SU));
			OPR004.clickBreakdownComplete(); 
			OPR004.closeTab("OPR004", "Breakdown");
			

			/***LAUNCH EMULATOR - TRANSPORT ORDER**/
			
			libr.launchTransportOrder("TO-app");	
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);

			//Login in to HHT

			to.searchShipment("val~"+SU[0]);
			to.verifyShipmentIsListed("val~"+SU[0]);
			to.clearShipment();
			to.searchShipment("val~"+SU[1]);
			to.verifyShipmentIsListed("val~"+SU[1]);
			libr.quitApp();
			
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			/** Breakdown Screen **/
			
			cust.searchScreen("OPR004", "Breakdown");
			OPR004.listFlightwithShipment(cust.data("UldNum"), "carrierCode", "FlightNo", "StartDate");
			System.out.println(cust.data("BDNlocation").split(",")[0]);
			String[] updatedLocation = { cust.data("BDNlocation").split(",")[0], cust.data("BDNlocation").split(",")[1],cust.data("BDNlocation").split(",")[2] };
			String[] updatedPieces = { cust.data("UpdatedRcvdPcs").split(",")[0], cust.data("UpdatedRcvdPcs").split(",")[1],cust.data("UpdatedRcvdPcs").split(",")[2] };
			String[] updatedWeight = { cust.data("UpdatedRcvdWt").split(",")[0], cust.data("UpdatedRcvdWt").split(",")[1],cust.data("UpdatedRcvdWt").split(",")[2] };
			String updatedsccs[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1],cust.data("SCC").split(",")[0] };
			
			
			//Clear Split shipment details
			
			OPR004.switchInToSplitWindow();
			
			//Update Split count and details
			OPR004.updateSplitBreakdown(3, updatedPieces, updatedWeight, updatedLocation, updatedsccs);
			OPR004.switchOutToSplitWindow();
			OPR004.saveOPR004Alert();
			OPR004.closeTab("OPR004", "Breakdown");

			/** Import Manifest **/
			
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");
			
			/*******Verify FSU-RCF message in MSG005******/	


			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "prop~flightNo", "prop~AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyMessageTriggered("prop~AWBNo", "XFSU-RCF");
			libr.waitForSync(6);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
