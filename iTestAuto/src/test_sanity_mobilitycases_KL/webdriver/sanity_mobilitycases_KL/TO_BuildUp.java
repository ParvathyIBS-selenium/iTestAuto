package sanity_mobilitycases_KL;


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
import screens.AssignFlightLocations_WHS059;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.MaintainOperationalFlight_FLT003;
import screens.GoodsAcceptanceHHT;
import screens.ListMessages_MSG005;
import screens.SecurityAndScreening_OPR339;
import screens.TransportOrderListing;
import screens.GoodsAcceptance_OPR335;
import screens.HandlingAreaSetUpScreen_WHS008;
import screens.RelocationTaskMonitor_WHS052;
import screens.AFLS_Booking;
import screens.CGOICSS;
import screens.AFLS_FlightPlan;


public class TO_BuildUp extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public TransportOrderListing to;
	public ListMessages_MSG005 MSG005;
	public GoodsAcceptanceHHT gahht;
	public AssignFlightLocations_WHS059 WHSS059;
	public AFLS_Booking afls;
	public AFLS_FlightPlan aflsfp;
	public CGOICSS Cgoicss;
	public MaintainOperationalFlight_FLT003 FLT003;
	public BuildupPlanning_ADD004 ADD004;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public HandlingAreaSetUpScreen_WHS008 WHS008;
	public RelocationTaskMonitor_WHS052 WHS052;


	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	public static String haproppath = "\\src\\resources\\HA.properties";
	String sheetName = "to_buildup";

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
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		WHSS059=new AssignFlightLocations_WHS059(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		afls=new AFLS_Booking(driver, excelreadwrite, xls_Read);
		aflsfp=new AFLS_FlightPlan(driver, excelreadwrite, xls_Read);
		Cgoicss = new CGOICSS(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		WHS008= new HandlingAreaSetUpScreen_WHS008(driver, excelreadwrite, xls_Read);
		WHS052=new RelocationTaskMonitor_WHS052(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TO_BuildUp")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TO_BuildUp")
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

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");



			String startDate = cust.createDateFormat("dd-MMM-YYYY",0, "DAY", "");
			String EndDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String bookingDate =cust.createDateFormat("dd/MMM/YYYY",0, "DAY", "");
			map.put("BookDate", bookingDate);
			String endDate = cust.createDateFormat("dd/MMM/YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", "Europe/Amsterdam"));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "Europe/Amsterdam");
			map.put("XFWBDate", flightdate1);

			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Amsterdam");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Amsterdam");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
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

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));




			/** Flight Creation **/

			// Maintain Operational Screen (FLT003)
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlightDetails("carrierCode","FlightNo", "StartDate");
			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT003.enterLegCapacityDetails("BookDate","BookDate","ATD_Local","ATA_Local", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");

			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			libr.quitBrowser();

			//Relaunch browser
			/***driver=libr.relaunchBrowser("chrome");	
			//Login to "CGOICSS"
			String[] cgoicsslogin = libr.getApplicationParams("Cgoicss");
			driver.get(cgoicsslogin[0]); // Enters URL
			cust.loginToCGOICSS(cgoicsslogin[1], cgoicsslogin[2]);

           /** Flight Creation **/
			/****Cgoicss.clickOnCreateFlight();
			Cgoicss.createNewFlight("carrierCode","FlightNo", "BookDate", "EndDate");
			Cgoicss.selectDayofOperations();
			Cgoicss.addLeg();
			Cgoicss.enterLegDetails("ATD_Local","ATA_Local", "Origin", "Destination", "serviceType", "AircraftType", "carrierCode");
			Cgoicss.clickSave();
			libr.quitBrowser();
			 ******/

			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/*** Login to AFLS_BOOKING **********/
			String[] aflsbooking = libr.getApplicationParams("afls");
			driver.get(aflsbooking[0]);
			cust.loginToAFLS(aflsbooking[1], aflsbooking[2]); 

			afls.selectTitleAndSubTitleTab("titleTab","titleTab");		
			afls.enterAWB("CarrierNumericCode","AWBNo");
			afls.enterAWBOrgAndDest("Origin", "Destination");
			afls.enterBookingOrgAndDest("Origin", "Destination");
			afls.enterBookingDeliveryAndArrivalDate("BookDate", "BookDate");
			afls.enterBookingDeliveryAndArrivalTime("ATD_Local","ATA_Local");
			afls.selectCommodityCode("CommodityCode");
			afls.selectServiceLevelAndHandlingNeeds("serviceLevel", "handlingNeeds");
			afls.enterCustomerID("AgentCode");
			afls.enterFlightInfo("carrierCode","FlightNo","Origin", "Destination", "BookDate");
			afls.enterShipmentDetails("Pieces", "Weight","Volume");
			afls.enterRateDetails("IATARate");
			afls.selectRouteSearchAndEvaluationSetting("no");
			afls.clickSubmitBooking();
			libr.quitBrowser();

			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			//Login to ALFS_FlightPlan		 
			cust.loginToAFLS_FlightPlan();

			aflsfp.clickMenu();
			aflsfp.selectMenuOption("val~Flight plan");
			aflsfp.enterFlightDetails("carrierCode", "FlightNo", "BookDate","BookDate");
			aflsfp.clickSearch();

			//FBL Trigger
			aflsfp.clickSend();
			aflsfp.selectSendMessages("val~FBL");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			/** MSG005 - List Messages **/

			//XFWB Message loading
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo", true);
			cust.closeTab("MSG005", "List Message");




			/***** OPR026 - Execute AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");





			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht2");
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - ACCEPTANCE****/
			//map.put("HandlingArea", WebFunctions.getPropertyValue(toproppath, "HandlingArea_AMS"));
			//gahht.selectHandlingAreaAndClickDone();
			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			gahht.enterValue("awbNumber");
			//verify Stated pieces and Stated weight,
			gahht.verifyStatedPiecesWeight("Pieces", "Weight");
			String[] sccs={cust.data("SCC")};
			gahht.selectMultipleSCC(sccs);
			map.put("AcceptanceLocation", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation_AMS"));
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "AcceptanceLocation");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetailsAndVerifyCheckSheets();
			libr.quitApp();


			/**** OPR335 -Goods Acceptance****/

			//verifying acceptance is finalised
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");



			map.put("AWB", cust.data("CarrierNumericCode") + cust.data("AWBNo"));
			map.put("SU", cust.data("AWB")+"001");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);


			to.searchShipment("SU"); 


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU", "AcceptanceLocation");

			//fetch destination location
			String storageLocation=to.retrieveDestnLocation("SU");
			map.put("destnStorageLocation", storageLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "AcceptanceLocation");
			map.put("LooseAcceptanceZone", WebFunctions.getPropertyValue(toproppath, "ULDAcceptanceZone_AMS"));
			//verifying zone of the destination location
			to.verifyZone(cust.data("destnStorageLocation"), "LooseAcceptanceZone");
			libr.quitApp();



			//	/**** WHS008 -HandlingAreaSetUpScreen ****/
			//
			//  cust.searchScreen("WHS008", "Handling Area Set Up");
			//	int verfCols [] = {3};
			//
			//	//Verifying the destination location and zone for SU
			//	map.put("StorageLocationZone_AMS", WebFunctions.getPropertyValue(toproppath, "StorageLocationZone_AMS"));
			//	String[] actVerfValues2= {cust.data("StorageLocationZone_AMS")};
			//	//verifying the location displayed is in the correct Zone as per the configuration
			//	WHS008.verifyLocationAndCorrespondingZone("destnStorageLocation", verfCols, actVerfValues2);
			//	cust.closeTab("WHS008", "Handling Area Set Up");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);

			//completing the relocation task
			to.searchShipment("SU");
			map.put("DesLocation_AMS", WebFunctions.getPropertyValue(toproppath, "BuildupLocation_AMS"));

			to.selectTask("destnStorageLocation");
			to.confirmTaskList();
			to.enterDestLocation("DesLocation_AMS");
			libr.quitApp();

			//
			//	/*****ADD004 - Build Up planning****/
			//	cust.searchScreen("ADD004","Buildup Planning");
			//	libr.waitForSync(10);
			//	ADD004.listFlight("carrierCode","FlightNo","StartDate");
			//	ADD004.verifyShipmentInLoadPlan("AWBNo");
			//	//Allocate and release
			//	ADD004.selectULD("AWBNo");
			//	ADD004.clickAllocate();
			//	ADD004.selectAllocationType("ULD");
			//	ADD004.enterUldDetails("UldType", "1");
			//	ADD004.clickSaveAllocation();
			//	ADD004.clickRelease();
			//	cust.closeTab("ADD004","Buildup Planning");	



			/***WHS059 - Assign Flight Locations***/

			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(15);
			WHSS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHSS059.clickList();
			WHSS059.clickMoreOptions("FullFlightNo");
			WHSS059.clickAssignLocation("0");
			map.put("BufferLocation_AMS", WebFunctions.getPropertyValue(toproppath, "BufferLocation_AMS"));
			map.put("Zone", WebFunctions.getPropertyValue(toproppath, "BufferLocationZone_AMS"));
			WHSS059.enterAssignZoneandLocationDetails("Zone","BufferLocation_AMS");		
			WHSS059.clickAssignedLocationTab();
			map.put("currdate",cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			String currtme=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("openTime",cust.timeCalculation(currtme, "HH:mm","MINUTE",2));	
			map.put("closeTime",cust.timeCalculation(currtme, "HH:mm","MINUTE",20));	
			WHSS059.enterOpenTime("currdate", "openTime");
			WHSS059.enterCloseTime("currdate", "closeTime");
			cust.closeTab("WHS059", "Assign Flight Locations");
			cust.waitForSync(60);


			/***WHS059 - Assign Flight Locations***/

			//verifying the buffer location is in open status
			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(60);
			WHSS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHSS059.clickList();
			WHSS059.verifyOpenStatus("OPEN");
			cust.closeTab("WHS059", "Assign Flight Locations");




			/**** WHS052 -Relocation Task Monitor****/
			cust.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterULDNumber("SU");
			WHS052.VerifyBuildupTOTriggered();
			
			map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_AMS"));
			map.put("LooseStorageAreaHA_AMS", WebFunctions.getPropertyValue(toproppath, "LooseStorageAreaHA_AMS"));
			String TODetailsSU[]={"Open",cust.data("LooseStorageAreaHA_AMS"),cust.data("HA_Buildup"),"RELOCATION TASK"};
			String ColumnNames[]={"Status","Source HA","Dest. HA","Remarks"};
			WHS052.verifyTODetails(1, ColumnNames, "SU", TODetailsSU);
			WHS052.maximizeAwbDetails("SU");
			WHS052.verifyCurrentLocation("SU", "Current.Loc","Current.Loc"+"\n"+cust.data("DesLocation_AMS"));
			WHS052.verifyDestinationLocation("SU", "Dest.Loc","Dest.Loc"+"\n"+cust.data("BufferLocation_AMS"));
			cust.closeTab("WHS052", "Relocation Task Monitor");



			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO	
			cust.loginTransportOrder(hht[0], hht[1]);

			to.searchShipment("SU");

			//fetch and verify the src location after opening the buffer Location
			to.retrieveAndVerifyOriginLocation("SU", "DesLocation_AMS");

			//fetch destination location
			String bufferLocation=to.retrieveDestnLocation("SU");
			map.put("bufferLocation", bufferLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "DesLocation_AMS");
			libr.quitApp();


			//			/**** WHS008 -HandlingAreaSetUpScreen ****/
			//
			//			cust.searchScreen("WHS008", "Handling Area Set Up");
			//
			//			//Verifying the opened buffer destination location and zone for su
			//			map.put("BufferLocationZone_AMS", WebFunctions.getPropertyValue(toproppath, "BufferLocationZone_AMS"));
			//			String[] actVerfValues5= {cust.data("BufferLocationZone_AMS")};
			//			//verifying the location displayed is in the correct Zone as per the configuration
			//			WHS008.verifyLocationAndCorrespondingZone("bufferLocation", verfCols, actVerfValues5);
			//			cust.closeTab("WHS008", "Handling Area Set Up");



		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		finally {
			try {
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
