package wp10;

/***verify To task based on To filters scc,flightnum date,Location and HA of multileg flight with DG scc****/

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
import rest_multiple_sfmi.Jsonbody;
import rest_pawbs.JSONBody;
import screens.AssignFlightLocations_WHS059;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.ChecksheetHHT;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.HandlingAreaSetUpScreen_WHS008;
import screens.ListCheckSheetConfig_SHR094;
import screens.ListTemplates_SHR093;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;
import screens.TransportOrderListing;
import screens.WarehouseRelocation_WHS009;

public class IASCB_9162_TC_2789 extends BaseSetup {
	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public GoodsAcceptance_OPR335 OPR335;
	public TransportOrderListing to;
	public GoodsAcceptanceHHT gahht;
	public AssignFlightLocations_WHS059 WHS059;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;
	public MaintainOperationalFlight_FLT003 FLT003;
	public BuildupPlanning_ADD004 ADD004;
	public WarehouseRelocation_WHS009 WHS009;
	public SecurityAndScreening_OPR339 OPR339;
	public HandlingAreaSetUpScreen_WHS008 WHS008;
	public ChecksheetHHT checkhht;
	public ListCheckSheetConfig_SHR094 SHR094;
	public ListTemplates_SHR093 SHR093;
	public Jsonbody jsonbody1;
	public JSONBody jsonbody;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";

	String sheetName = "wp10";

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
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		WHS059=new AssignFlightLocations_WHS059(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		WHS009=new WarehouseRelocation_WHS009(driver, excelreadwrite, xls_Read);
		jsonbody1=new Jsonbody(driver, excelreadwrite, xls_Read);
		WHS008= new HandlingAreaSetUpScreen_WHS008(driver, excelreadwrite, xls_Read);
		jsonbody=new JSONBody(driver, excelreadwrite, xls_Read);
		checkhht=new ChecksheetHHT(driver, excelreadwrite, xls_Read);
		SHR094 = new ListCheckSheetConfig_SHR094(driver, excelreadwrite, xls_Read);
		SHR093 = new ListTemplates_SHR093(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_2657")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2657")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// creating flight number
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNumber", cust.data("carrierCode")+cust.data("prop~flightNo"),proppath);

			String timeStamp = cust.createDateFormat("dd-MMM-yyyy hh:mm:ss", 0, "DAY", "Europe/Paris");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");		
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", "Europe/Paris"));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", "Europe/Paris"));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", "Europe/Paris"));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "Europe/Paris").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "Europe/Paris");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerId_US"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerName_US"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerpostCode_US"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerstreetName_US"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercityName_US"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountryId_US"));
			map.put("ConsigneeCountryName",WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountryName_US"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountrySubdivision_US"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "paycargoCustomertelephoneNo_US"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "paycargoCustomeremail_US"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));

			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Login to iCargo

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT003.enterLegCapacityDetails("StartDate","EndDate","ATD_Local","ATA_Local", "AircraftType", "");			
			FLT003.clickSecondCheckbox();
			FLT003.clickLegCapacity();
			FLT003.enterLegCapacityDetails("StartDate","EndDate","ATD_Local1","ATA_Local1", "AircraftType", "");
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
			map.put("FullAWBNum", cust.data("CarrierNumericCode")+ cust.data("prop~AWBNo"));
			map.put("FullAWBNumber", cust.data("prop~CarrierNumericCode") + cust.data("prop~AWBNo")+"001");
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");


			/***Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MESSAGE - loading XFWB **********/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

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


			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");



			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);


			/*** HHT - ACCEPTANCE****/
			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			gahht.enterValue("awbNumber");
			gahht.selectSCCValue("SCC");
			map.put("AcceptanceLocation", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation"));
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "AcceptanceLocation");
			gahht.checkAllPartsReceived();
			gahht.clickSaveOnly();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");
			libr.quitApp();

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
			String destnControlLocation=to.retrieveDestnLocation("SU");
			map.put("destnControlLocation", destnControlLocation);


			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "AcceptanceLocation");
			libr.quitApp();


			/**** WHS008 -HandlingAreaSetUpScreen ****/

			cust.searchScreen("WHS008", "Handling Area Set Up");
			int verfCols [] = {3};

			//Verifying destination location and zone for uldNum
			String[] actVerfValues2= {WebFunctions.getPropertyValue(toproppath, "ControlLocationZone_RCMscc_CDG")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("destnControlLocation", verfCols, actVerfValues2);
			cust.closeTab("WHS008", "Handling Area Set Up");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO

			cust.loginTransportOrder(hht[0], hht[1]);

			//completing the relocation task
			to.searchShipment("SU");
			to.selectTask("destnControlLocation");
			to.confirmTaskList();
			to.clickRelocationComplete("destnControlLocation");
			libr.quitApp();


			/** WAREHOUSE RELOCATION  - WHS009 **/

			//Manual relocation from the Control Location to the Rapix Entry Point
			cust.searchScreen("WHS009", "Warehouse Relocation");

			WHS009.enterSU("SU");
			WHS009.listAwbDetails();
			//Full Relocation
			WHS009.markCheckbox();
			WHS009.clickFullRelocationButton();
			map.put("RapixEntryLoc", WebFunctions.getPropertyValue(toproppath, "RapixEntryLocation"));
			WHS009.enterDestinationLocAndSU("RapixEntryLoc","SU");
			cust.switchToMainScreen("WHS009");
			WHS009.clickSaveButton();
			cust.closeTab("WHS009", "Warehouse Relocation");


			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);


			/******* PAWBS POST REQUEST for SU ****/	
			jsonbody.postRequest(cust.data("CarrierNumericCode"), cust.data("AWBNo"), timeStamp,cust.data("ScreeningResult").split(",")[0],screenmethod,cust.data("RapixEntryLoc"),cust.data("ScreenerName"),cust.data("SU"));
			libr.waitForSync(8);

			/******* SFMI POST REQUEST SU ****/		
			jsonbody1.postRequest(cust.data("AWB"),cust.data("Weight"),cust.data("Volume"),cust.data("val~10"),cust.data("val~10"),cust.data("val~10"),"001"+cust.data("AWB"));
			libr.waitForSync(8);


			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.verifyScreeningMethodAutopopulated("screenmethod");
			OPR339.verifyScreeningResultAndSUnumber(cust.data("ScreeningResult").split(",")[1],cust.data("AWB")+"001");			
			OPR339.verifyScreenerDetails("ScreenerName",timeStamp.split(" ")[0]);
			OPR339.verifyScreenedPiecesAndWeight("Pieces", "Weight");
			String Sccnotpresent[]={"NSC"};
			OPR339.verifySccNotPresent(Sccnotpresent);
			String Sccpresent[]={"SPX"};
			OPR339.verifyScc(Sccpresent);
			cust.closeTab("OPR339", "Security & Sceening"); 
			
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			map.put("VPPWeight",cust.data("Weight"));
		      OPR335.clickSave("OPR335");
			cust.closeTab("OPR335", "Goods Acceptance");

			
			

			/**** OPR335 -Goods Acceptance****/

			//verifying acceptance is finalised
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");


			/** WAREHOUSE RELOCATION  - WHS009 **/

			//Manual relocation from the Rapix entry point to the Rapix exit Point
			cust.searchScreen("WHS009", "Warehouse Relocation");

			WHS009.enterSU("SU");
			WHS009.listAwbDetails();
			//Full Relocation
			WHS009.markCheckbox();
			WHS009.clickFullRelocationButton();
			map.put("RapixExitLocation", WebFunctions.getPropertyValue(toproppath, "RapixExitLocation"));
			WHS009.enterDestinationLocAndSU("RapixExitLocation","SU");
			cust.switchToMainScreen("WHS009");
			WHS009.clickSaveButton();
			cust.closeTab("WHS009", "Warehouse Relocation");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO	
			cust.loginTransportOrder(hht[0], hht[1]);			

			to.searchShipment("SU");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU", "RapixExitLocation");

			//fetch destination location
			String destnStorageLocation=to.retrieveDestnLocation("SU");
			map.put("destnStorageLocation", destnStorageLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "RapixExitLocation");
			libr.quitApp();


			/**** WHS008 -HandlingAreaSetUpScreen ****/

			cust.searchScreen("WHS008", "Handling Area Set Up");

			//Verifying destination storage area location and zone for su
			String[] actVerfValues4= {WebFunctions.getPropertyValue(toproppath, "StorageAreaZone_CDG")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("destnStorageLocation", verfCols, actVerfValues4);
			cust.closeTab("WHS008", "Handling Area Set Up");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);


			to.searchShipment("SU");
			//completing the relocation task
			to.selectTask("destnStorageLocation");
			to.confirmTaskList();
			to.clickRelocationComplete("destnStorageLocation");
			libr.quitApp();



			/*****ADD004 - Build Up planning****/
			cust.searchScreen("ADD004","Buildup Planning");
			libr.waitForSync(10);
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			ADD004.verifyShipmentInLoadPlan("prop~AWBNo");
			//Allocate and release
			ADD004.selectULD("AWBNo");
			ADD004.clickAllocate();
			ADD004.selectAllocationType("ULD");
			ADD004.enterSegmentAndUldDetails("UldType1","1",cust.data("Route1"));
			ADD004.clickSaveAllocation();
			ADD004.clickRelease();
			cust.closeTab("ADD004","Buildup Planning");	



			/*** WHS059 - Assign Flight locations ***/

			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(15);
			WHS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHS059.clickList();
			WHS059.clickMoreOptions("FullFlightNo");
			WHS059.clickAssignLocation("0");
			map.put("PITLocation_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG"));
			map.put("PITLocationZone_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocationZone_CDG"));
			WHS059.enterAssignZoneandLocation("PITLocationZone_CDG","PITLocation_CDG");		
			WHS059.clickAssignedLocationTab();
			map.put("currdate",cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			String currtme=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("openTime",cust.timeCalculation(currtme, "HH:mm","MINUTE",2));		
			WHS059.enterOpenTime("currdate", "openTime");
			cust.closeTab("WHS059", "Assign Flight Locations");
			cust.waitForSync(60);


			/*** WHS059 - Assign Flight locations ***/

			//verifying the PIT location is in open status
			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(60);
			WHS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHS059.clickList();
			WHS059.verifyOpenStatus("OPEN");
			cust.closeTab("WHS059", "Assign Flight Locations");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);

			String flightNum=cust.data("FullFlightNo")+" "+cust.data("StartDate");

			//verifying the TO is generated from storage area to the opened PIT location from Export build up app
			to.searchShipment("SU");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU", "destnStorageLocation");

			//fetch destination location
			String descPITLocationOpened=to.retrieveDestnLocation("SU");
			map.put("descPITLocationOpened", descPITLocationOpened);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "destnStorageLocation");
			libr.quitApp();



			/**** WHS008 -HandlingAreaSetUpScreen ****/

			cust.searchScreen("WHS008", "Handling Area Set Up");

			//Verifying the opened PIT destination location and zone for su
			String[] actVerfValues5= {WebFunctions.getPropertyValue(toproppath, "PITLocationZone_CDG")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("descPITLocationOpened", verfCols, actVerfValues5);
			cust.closeTab("WHS008", "Handling Area Set Up");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);

			//verify task based on filter HA
			map.put("HA", WebFunctions.getPropertyValue(toproppath, "StorageAreaHA_CDG"));
			to.selectToFilter(cust.data("HA"),cust.data("val~Handling Area"));
			to.VerifyFilterSelected(cust.data("HA"),cust.data("val~Handling Area"));
			to.searchShipment("SU");
			to.verifyShipmentDetails("SU", "val~Open", "destnStorageLocation");
			to.clearShipment();
			to.clickRefresh();
			to.clearFilterOption();
			//verify task based on Filter Location
			to.selectToFilter(cust.data("descPITLocationOpened"),cust.data("val~Destination Location"));
			to.VerifyFilterSelected(cust.data("descPITLocationOpened"),cust.data("val~Destination Location"));
			to.verifyShipmentDetails("SU", "val~Open", "destnStorageLocation");
			to.clearFilterOption();
			//verify task based on  filter flight Num and date
			to.selectToFilter(flightNum,cust.data("val~Flight"));
			to.VerifyFilterSelected(flightNum,cust.data("val~Flight"));
			to.verifyShipmentDetails("SU", "val~Open", "destnStorageLocation");
			to.clearFilterOption();
			//verify task based on  filter SCC
			to.selectToFilter(cust.data("SCC"),cust.data("val~SCC"));
			to.VerifyFilterSelected(cust.data("SCC"),cust.data("val~SCC"));
			to.verifyShipmentDetails("SU", "val~Open", "destnStorageLocation");
			to.clearFilterOption();
			to.searchShipment("SU");
			to.selectTask("descPITLocationOpened");
			//verify  task  mark In progress
			to.verifyShipmentDetails("SU","val~In Progress","destnStorageLocation");
			libr.quitApp();



		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
