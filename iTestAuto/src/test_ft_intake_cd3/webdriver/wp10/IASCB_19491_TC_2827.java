package wp10;

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
import screens.AdhocTaskCreationHHT;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.MaintainOperationalFlight_FLT003;
import screens.RelocationTaskMonitor_WHS052;
import screens.SecurityAndScreening_OPR339;
import screens.TransportOrderListing;
import screens.WarehouseRelocation_WHS009;
import screens.BuildupPlanning_ADD004;
import screens.HandlingAreaSetUpScreen_WHS008;
import screens.AssignFlightLocations_WHS059;


/**
 * 
 * TC_03_Verify TO generation based on flight number from android screen
 *
 *
 */

public class IASCB_19491_TC_2827 extends BaseSetup {

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
	public RelocationTaskMonitor_WHS052 WHS052;
	public TransportOrderListing to;
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainOperationalFlight_FLT003 FLT003;
	public ExportManifest_OPR344 OPR344;
	public AdhocTaskCreationHHT adhoctc;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;
	public WarehouseRelocation_WHS009 WHS009;
	public BuildupPlanning_ADD004 ADD004;
	public HandlingAreaSetUpScreen_WHS008 WHS008;
	public Jsonbody jsonbody1;
	public JSONBody jsonbody;
	public AssignFlightLocations_WHS059 WHSS059;

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
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);		
		WHS052=new RelocationTaskMonitor_WHS052(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);
		adhoctc = new AdhocTaskCreationHHT(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		WHS009=new WarehouseRelocation_WHS009(driver, excelreadwrite, xls_Read);
		WHS008= new HandlingAreaSetUpScreen_WHS008(driver, excelreadwrite, xls_Read);
		jsonbody1=new Jsonbody(driver, excelreadwrite, xls_Read);
		jsonbody=new JSONBody(driver, excelreadwrite, xls_Read);
		WHSS059=new AssignFlightLocations_WHS059(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2827")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2827")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			cust.createFlight("FullFlightNumber");
			String timeStamp = cust.createDateFormatWithTimeZone("dd-MMM-yyyy hh:mm:ss", 0, "DAY", "Europe/Paris");
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName",WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
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

			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeCDG=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			System.out.println(currtimeCDG);
			String STD=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",3);
			System.out.println(STD.split(" ")[1]);
			map.put("STDTime", STD.split(" ")[1]);
			String STA=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",5);
			System.out.println(STA.split(" ")[1]);
			map.put("STATime", STA.split(" ")[1]);
			map.put("STDDate", STD.split(" ")[0]);
			map.put("STADate", STA.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");

			// Checking AWB1 is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));

			// Checking AWB2 is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo1", cust.data("prop~FullAWBNo"));
			map.put("AWBNo1", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			cust.setPropertyValue("AWBNo", cust.data("AWBNo"), proppath);
			cust.setPropertyValue("AWBNo1", cust.data("AWBNo1"), proppath);
			System.out.println(cust.data("FullAWBNo"));
			System.out.println(cust.data("FullAWBNo1"));

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/***Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** MSG005 -XFBL Message loading **/

			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") , libr.data("FullAWBNo1") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
							+ libr.data("Volume") + ";" + libr.data("ShipmentDesc")};
			String scc[] = { cust.data("SCC"),cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"), cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** MSG005 -XFWB Message loading **/
			//AWB1
			map.put("awbNumber", cust.data("FullAWBNo"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			//AWB1
			map.put("awbNumber", cust.data("FullAWBNo1"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
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
			//AWBNo1
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/***** OPR026 - Execute AWB ****/
			//AWBNo2
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/** OPR335 - Loose Acceptance **/
			//AWBNo1
			cust.setPropertyValue("AWBNo", cust.data("AWBNo"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			map.put("AcceptanceLocation", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation"));
			OPR335.looseShipmentDetails("AcceptanceLocation", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptanceWithBlockExists();
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");


			/** OPR335 - Loose Acceptance **/
			//AWBNo2
			cust.setPropertyValue("AWBNo1", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo1", "CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("AcceptanceLocation", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptanceWithBlockExists();
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");



			map.put("AWB1", cust.data("CarrierNumericCode") + cust.data("AWBNo"));
			map.put("SU1", cust.data("AWB1")+"001");

			map.put("AWB2", cust.data("CarrierNumericCode") + cust.data("AWBNo1"));
			map.put("SU2", cust.data("AWB2")+"001");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginTransportOrder(hht[0], hht[1]);

			//search first SU 
			to.searchShipment("SU1");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU1", "AcceptanceLocation");


			//fetch destination location
			String destnControlLocationSU1=to.retrieveDestnLocation("SU1");
			map.put("destnControlLocationSU1", destnControlLocationSU1);


			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU1", "val~Open", "AcceptanceLocation");

			to.clickRefresh();

			//search second SU 
			to.searchShipment("SU2");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU2", "AcceptanceLocation");


			//fetch destination location
			String destnControlLocationSU2=to.retrieveDestnLocation("SU2");
			map.put("destnControlLocationSU2", destnControlLocationSU2);


			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU2", "val~Open", "AcceptanceLocation");
			libr.quitApp();


			/**** WHS008 -HandlingAreaSetUpScreen ****/

			cust.searchScreen("WHS008", "Handling Area Set Up");
			int verfCols [] = {3};

			//Verifying destination control location and zone for SU1
			String[] actVerfValues2= {WebFunctions.getPropertyValue(toproppath, "ControlLocationZone_CDG")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("destnControlLocationSU1", verfCols, actVerfValues2);

			WHS008.clickClear();


			//Verifying destination control location and zone for SU2
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("destnControlLocationSU2", verfCols, actVerfValues2);
			cust.closeTab("WHS008", "Handling Area Set Up");



			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);

			to.searchShipment("SU1");
			//completing the relocation task for SU1
			to.selectTask("destnControlLocationSU1");
			to.confirmTaskList();
			to.clickRelocationComplete("destnControlLocationSU1");
			to.clickRefresh();

			to.searchShipment("SU2");
			//completing the relocation task for SU2
			to.selectTask("destnControlLocationSU2");
			to.confirmTaskList();
			to.clickRelocationComplete("destnControlLocationSU2");
			libr.quitApp();


			/** WAREHOUSE RELOCATION  - WHS009 **/

			//Manual relocation from the Control Location to the Rapix Entry Point of the first SU
			cust.searchScreen("WHS009", "Warehouse Relocation");
			WHS009.enterSU("SU1");
			WHS009.listAwbDetails();
			//Full Relocation
			WHS009.markCheckbox();
			WHS009.clickFullRelocationButton();
			map.put("RapixEntryLocation", WebFunctions.getPropertyValue(toproppath, "RapixEntryLocation"));
			WHS009.enterDestinationLocAndSU("RapixEntryLocation","SU1");
			cust.switchToMainScreen("WHS009");
			WHS009.clickSaveButton();
			cust.closeTab("WHS009", "Warehouse Relocation");


			/** WAREHOUSE RELOCATION  - WHS009 **/

			//Manual relocation from the Control Location to the Rapix Entry Point of the second SU
			cust.searchScreen("WHS009", "Warehouse Relocation");
			WHS009.enterSU("SU2");
			WHS009.listAwbDetails();
			//Full Relocation
			WHS009.markCheckbox();
			WHS009.clickFullRelocationButton();
			WHS009.enterDestinationLocAndSU("RapixEntryLocation","SU2");
			cust.switchToMainScreen("WHS009");
			WHS009.clickSaveButton();
			cust.closeTab("WHS009", "Warehouse Relocation");


			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);


			/******* SFMI POST REQUEST SU1 ****/		
			jsonbody1.postRequest(cust.data("AWB1"),cust.data("Weight"),cust.data("Volume"),cust.data("val~10"),cust.data("val~10"),cust.data("val~10"),"001"+cust.data("AWB1"));
			libr.waitForSync(8);

			/******* SFMI POST REQUEST SU2 ****/		
			jsonbody1.postRequest(cust.data("AWB2"),cust.data("Weight"),cust.data("Volume"),cust.data("val~10"),cust.data("val~10"),cust.data("val~10"),"001"+cust.data("AWB2"));
			libr.waitForSync(8);

			/******* PAWBS POST REQUEST for SU1 ****/	
			jsonbody.postRequest(cust.data("CarrierNumericCode"), cust.data("AWBNo"), timeStamp,cust.data("ScreeningResult").split(",")[0],screenmethod,cust.data("RapixEntryPoint"),cust.data("ScreenerName"),cust.data("SU1"));	
			libr.waitForSync(8);

			/******* PAWBS POST REQUEST for SU2 ****/	
			jsonbody.postRequest(cust.data("CarrierNumericCode"), cust.data("AWBNo1"), timeStamp,cust.data("ScreeningResult").split(",")[0],screenmethod,cust.data("RapixEntryPoint"),cust.data("ScreenerName"),cust.data("SU2"));	
			libr.waitForSync(8);


			//AWB1
			/****OPR355 - Loose Acceptance****/

			cust.setPropertyValue("AWBNo", cust.data("AWBNo"), proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces","Weight","Volume","CommodityCode");
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			//AWB2
			/****OPR355 - Loose Acceptance****/

			cust.setPropertyValue("AWBNo1", cust.data("AWBNo1"), proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo1", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces","Weight","Volume","CommodityCode");
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");


			/** WAREHOUSE RELOCATION  - WHS009 **/

			//Manual relocation from Rapix entry point to the Rapix exit Point for the first SU
			cust.searchScreen("WHS009", "Warehouse Relocation");
			WHS009.enterSU("SU1");
			WHS009.listAwbDetails();
			//Full Relocation
			WHS009.markCheckbox();
			WHS009.clickFullRelocationButton();
			map.put("RapixExitLocation", WebFunctions.getPropertyValue(toproppath, "RapixExitLocation"));
			WHS009.enterDestinationLocAndSU("RapixExitLocation","SU1");
			cust.switchToMainScreen("WHS009");
			WHS009.clickSaveButton();
			cust.closeTab("WHS009", "Warehouse Relocation");


			/** WAREHOUSE RELOCATION  - WHS009 **/

			//Manual relocation from Rapix entry point to the Rapix exit Point for the second SU
			cust.searchScreen("WHS009", "Warehouse Relocation");
			WHS009.enterSU("SU2");
			WHS009.listAwbDetails();
			//Full Relocation
			WHS009.markCheckbox();
			WHS009.clickFullRelocationButton();
			WHS009.enterDestinationLocAndSU("RapixExitLocation","SU2");
			cust.switchToMainScreen("WHS009");
			WHS009.clickSaveButton();
			cust.closeTab("WHS009", "Warehouse Relocation");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO	
			cust.loginTransportOrder(hht[0], hht[1]);

			//search first SU
			to.searchShipment("SU1");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU1", "RapixExitLocation");

			//fetch destination location
			String destnStorageLocationSU1=to.retrieveDestnLocation("SU1");
			map.put("destnStorageLocationSU1", destnStorageLocationSU1);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU1", "val~Open", "RapixExitLocation");


			to.clickRefresh();

			//search second shipment
			to.searchShipment("SU2");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU2", "RapixExitLocation");

			//fetch destination location
			String destnStorageLocationSU2=to.retrieveDestnLocation("SU2");
			map.put("destnStorageLocationSU2", destnStorageLocationSU2);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU2", "val~Open", "RapixExitLocation");
			libr.quitApp();


			/**** WHS008 -HandlingAreaSetUpScreen ****/

			cust.searchScreen("WHS008", "Handling Area Set Up");

			//Verifying storage destination location and zone for SU1
			//Verifying storage area destination location and zone for SU1
			String[] actVerfValues4= {WebFunctions.getPropertyValue(toproppath, "StorageAreaZone_CDG")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("destnStorageLocationSU1", verfCols, actVerfValues4);

			WHS008.clickClear();


			//Verifying storage destn location and zone for SU2
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("destnStorageLocationSU2", verfCols, actVerfValues4);
			cust.closeTab("WHS008", "Handling Area Set Up");



			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);


			to.searchShipment("SU1");
			//completing the relocation task for SU1
			to.selectTask("destnStorageLocationSU1");
			to.confirmTaskList();
			to.clickRelocationComplete("destnStorageLocationSU1");
			to.clickRefresh();

			to.searchShipment("SU2");
			//completing the relocation task for SU2
			to.selectTask("destnStorageLocationSU2");
			to.confirmTaskList();
			to.clickRelocationComplete("destnStorageLocationSU2");
			libr.quitApp();



			/*****ADD004 - Build Up planning****/
			cust.searchScreen("ADD004","Buildup Planning");
			libr.waitForSync(10);
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			ADD004.verifyShipmentInLoadPlan("AWBNo");
			//Allocate and release
			ADD004.selectULD("AWBNo");
			ADD004.clickAllocate();
			ADD004.selectAllocationType("ULD");
			ADD004.enterUldDetails("UldType1", "1");
			ADD004.clickSaveAllocation();
			ADD004.clickRelease();
			cust.closeTab("ADD004","Buildup Planning");	


			/*****ADD004 - Build Up planning****/
			cust.searchScreen("ADD004","Buildup Planning");
			libr.waitForSync(10);
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			ADD004.verifyShipmentInLoadPlan("AWBNo1");
			//Allocate and release
			ADD004.selectULD("AWBNo1");
			ADD004.clickAllocate();
			ADD004.selectAllocationType("ULD");
			ADD004.enterUldDetails("UldType1", "1");
			ADD004.clickSaveAllocation();
			ADD004.clickRelease();
			cust.closeTab("ADD004","Buildup Planning");	

			
			/***WHS059 - Assign Flight Locations***/
			
			//open PIT location
			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(15);
			WHSS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHSS059.clickList();
			WHSS059.clickMoreOptions("FullFlightNo");
			WHSS059.clickAssignLocation("0");
			map.put("PITLocationZone_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocationZone_CDG"));
			map.put("PITLocation_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG"));
			WHSS059.enterAssignZoneandLocation("PITLocationZone_CDG","PITLocation_CDG");		
			WHSS059.clickAssignedLocationTab();
			map.put("currdate",cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			String currtme=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("openTime",cust.timeCalculation(currtme, "HH:mm","MINUTE",2));		
			WHSS059.enterOpenTime("currdate", "openTime");
			cust.closeTab("WHS059", "Assign Flight Locations");
			cust.waitForSync(60);

			
			/***Assign flight Locations*/
			//verifying the PIT location is in open status
			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(60);
			WHSS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHSS059.clickList();
			WHSS059.verifyOpenStatus("OPEN");
			cust.closeTab("WHS059", "Assign Flight Locations");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO	
			cust.loginTransportOrder(hht[0], hht[1]);

			//search first SU
			to.searchShipment("SU1");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU1", "destnStorageLocationSU1");

			//fetch destination location
			String descPITLocationOpenedSU1=to.retrieveDestnLocation("SU1");
			map.put("descPITLocationOpenedSU1", descPITLocationOpenedSU1);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU1", "val~Open", "destnStorageLocationSU1");

			to.clickRefresh();

			//search first SU
			to.searchShipment("SU2");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU2", "destnStorageLocationSU2");

			//fetch destination location
			String descPITLocationOpenedSU2=to.retrieveDestnLocation("SU2");
			map.put("descPITLocationOpenedSU2", descPITLocationOpenedSU2);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU2", "val~Open", "destnStorageLocationSU2");
			libr.quitApp();


			/**** WHS008 -HandlingAreaSetUpScreen ****/

			cust.searchScreen("WHS008", "Handling Area Set Up");


			//Verifying the opened PIT destination location and zone for SU1
			String[] actVerfValues5= {WebFunctions.getPropertyValue(toproppath, "PITLocationZone_CDG")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("descPITLocationOpenedSU1", verfCols, actVerfValues5);

			WHS008.clickClear();

			//Verifying the opened PIT destination location and zone for SU2
			WHS008.verifyLocationAndCorrespondingZone("descPITLocationOpenedSU2", verfCols, actVerfValues5);
			cust.closeTab("WHS008", "Handling Area Set Up");



			/***Launch emulator - ransport Order**/
			libr.launchTransportOrder("TO-app");		

			//Login in to TO

			cust.loginTransportOrder(hht[0], hht[1]);

			//verifying TO is generated from storage area to the opened PIT Location

			to.searchShipment("SU1");
			//completing the relocation task for SU1
			to.selectTask("descPITLocationOpenedSU1");
			to.confirmTaskList();
			to.clickRelocationComplete("descPITLocationOpenedSU1");
			to.clickRefresh();

			to.searchShipment("SU2");
			//completing the relocation task for SU2
			to.selectTask("descPITLocationOpenedSU2");
			to.confirmTaskList();
			to.clickRelocationComplete("descPITLocationOpenedSU2");
			libr.quitApp();



			/**** OPR344 - Export manifest****/

			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNo = OPR335.create_uld_number("UldType1", "prop~flight_code");
			map.put("UldNum", uldNo);
			String uldNo1 = OPR335.create_uld_number("UldType1", "prop~flight_code");
			map.put("UldNum1", uldNo1);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWBAndContour("UldNum","0","CarrierNumericCode","AWBNo","Pieces","Weight","Contour");
			OPR344.addNewULDWithAWBAndContour("UldNum1","0","CarrierNumericCode","AWBNo1","Pieces","Weight","Contour");
			OPR344.clickBuildUpComplete();
			OPR344.clickBuildUpComplete();
			cust.closeTab("OPR344", "Export Manifest");
			libr.quitBrowser();


			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");		

			//Login in to HHT
			cust.loginHHT(hht[0], hht[1]);

			/** Adhoc Task Creation **/

			//Relocating the selected ULD
			adhoctc.invokeAdhocTaskCreationScreen();
			adhoctc.clickRelocationTask();	
			adhoctc.verifyFlightField();
			adhoctc.updateFlightDetails("carrierCode", "FlightNo","currentDay");
			adhoctc.verifyULDPreselected();
			adhoctc.selectOrUnselectULD("UldNum");
			adhoctc.clickNext();
			map.put("Relocate_To_BuildupLocation_CDG", WebFunctions.getPropertyValue(toproppath, "BuildupLocation_CDG"));
			adhoctc.enterLocationInSearchField("Relocate_To_BuildupLocation_CDG");
			adhoctc.verifyTaskCreated();



			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			//Login in to HHT
			cust.loginTransportOrder(hht[0], hht[1]);

			//verifying the selected ULD is relocated to the location given in the Adhoc Task Creation screen
			to.searchShipment("UldNum1");
			to.verifyShipmentStatus("val~Open");
			to.verifyAssigneddBuildupLocationinTOlistingscreen("UldNum1", "BuildupLocation_CDG");
			libr.quitApp();


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}