package wp6;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.DeliveryDocumentation_OPR293;
import screens.DropOffPickUpShipmentsSST;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportDocumentation_OPR001;
import screens.ImportManifest_OPR367;
import screens.MaintainFlightSchedule_FLT005;
import screens.ListMessages_MSG005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.Servicepointoverview_TGC015;
import screens.VisitDeclarationEnquiry_TGC010;
import screens.CaptureCheckSheet_CHK002;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
/**Ready for delivery status must not be displayed for the token when shipment has not arrived.**/

public class IASCB_4750_TC_2485 extends BaseSetup{

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ListMessages_MSG005 MSG005;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public ImportManifest_OPR367 OPR367;
	public BreakDownScreen_OPR004 OPR004;
	public ImportDocumentation_OPR001 OPR001;
	public DeliveryDocumentation_OPR293 OPR293;
	public DropOffPickUpShipmentsSST sstDP;
	public VisitDeclarationEnquiry_TGC010 tgc010;
	public Servicepointoverview_TGC015 TGC015;
	public CaptureCheckSheet_CHK002 CHK002;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "wp6";

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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR001 = new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);
		sstDP=new DropOffPickUpShipmentsSST(driver, excelreadwrite, xls_Read);
		tgc010=new VisitDeclarationEnquiry_TGC010(driver, excelreadwrite, xls_Read);
		TGC015 = new Servicepointoverview_TGC015(driver, excelreadwrite, xls_Read);
		CHK002= new CaptureCheckSheet_CHK002(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2485")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2485")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			//Login to iCargo

			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	

			// Switch role
//			cust.switchRole("Origin", "FCTL", "RoleGroup");


			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));

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

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// creating flight number 1
			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam");
			String endDate = cust.createDateFormat("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam");
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormat("ddMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("SSMEndDate", cust.createDateFormat("ddMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", "Europe/Amsterdam"));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "Europe/Amsterdam");
			map.put("XFWBDate", flightdate1);

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			//Maintain Flight Screen (FLT005) . Taking fresh flight

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight 1 details
			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum1 = FlightNum1.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum1);
			map.put("FlightNo", FlightNum1.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum1);



			// creating flight number 2
			cust.createFlight("FullFlightNumber");

			//Maintain Flight Screen (FLT005) . Taking fresh flight

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");
			//Flight 2 details
			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum2 = FlightNum2.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo2", FlightNum2.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum2);



			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("awbNumber1", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			map.put("FullAWBNo", cust.data("awbNumber1"));
			map.put("AWBNo", cust.data("awb1"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2
			map.put("awbNumber2", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));
			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo2", cust.data("awb2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/****************** MERCURY *********************/

			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/**SSM Message loading for flight 1 **/

			map.put("flightNumber", FlightNum1);
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");



			/** SSM Message loading for flight 2 **/
			map.put("flightNumber", FlightNum2);
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.returnTosendMessage();
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");


			/***Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFBL Message loading for flight 1 **/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("flightNumber", FlightNum1);
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment1[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment1, scc, routing);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFBL Message loading for flight 2 **/

			map.put("flightNumber", FlightNum2);
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment2[] = { libr.data("FullAWBNo2") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };

			cust.createXFBLMessage("XFBL_2", shipment2, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 1 **/

			map.put("FullAWBNum", cust.data("awbNumber1"));
			// Create XFWB message	
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 2 **/

			map.put("FullAWBNum", cust.data("awbNumber2"));
			// Create XFWB message
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

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			/**** OPR339 - Security & Screening AWB1****/
			cust.searchScreen("OPR339", "Security and Screening");
			cust.setPropertyValue("AWBNo", cust.data("awb1"),proppath);
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening AWB2****/
			cust.searchScreen("OPR339", "Security and Screening");
			cust.setPropertyValue("AWBNo", cust.data("awb2"),proppath);
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");


			/***** OPR026 - Capture AWB ****/
			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			//Capture check sheet
			OPR026.captureCheckSheet(true,"leakage");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/***** OPR026 - Capture AWB ****/
			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			//Capture check sheet
			OPR026.captureCheckSheet(true,"leakage");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");



			/****OPR355 - Loose Acceptance****/

			cust.searchScreen("OPR335", "Goods Acceptance");
			OPR335.listAWB(cust.data("AWBNo"),"CarrierNumericCode");
			//capture check sheet
			OPR335.captureCheckSheet(true, "leakage");
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/****OPR355 - Loose Acceptance****/

			cust.searchScreen("OPR335", "Goods Acceptance");
			OPR335.listAWB(cust.data("AWBNo2"),"CarrierNumericCode");
			//capture check sheet
			OPR335.captureCheckSheet(true, "leakage");
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			/***** CAPTURE CHECK SHEET***/
            cust.searchScreen("CHK002", "Capture Check Sheet");
            CHK002.listCheckSheetType("AWB");
            cust.setPropertyValue("AWBNo", cust.data("AWBNo"),proppath);
            CHK002.listAWBWithTransaction("AWBNo", "CarrierNumericCode","Manifest");
            CHK002.captureCheckSheet(true, "leakage");
            CHK002.closeTab("CHK002", "Capture Check Sheet");
           
            /***** CAPTURE CHECK SHEET***/
            cust.searchScreen("CHK002", "Capture Check Sheet");
            CHK002.listCheckSheetType("AWB");
            cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
            CHK002.listAWBWithTransaction("AWBNo", "CarrierNumericCode","Manifest");
            CHK002.captureCheckSheet(true, "leakage");
            CHK002.closeTab("CHK002", "Capture Check Sheet");
			
			
             /**** OPR344 - Export manifest****/

			//manifesting and finalizing flight 1
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWB("UldNum","0","prop~CarrierNumericCode","AWBNo","Pieces","Weight");
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			OPR344.finalizeFlight(true);
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export Manifest");


			/**** OPR344 - Export manifest****/

			//manifesting and finalizing flight 2
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo2","StartDate");
			String uldNum2=cust.create_uld_number("UldType", "carrierCode");
			map.put("ULDNum2", uldNum2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWB("ULDNum2","0","prop~CarrierNumericCode","AWBNo2","Pieces","Weight");
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			OPR344.finalizeFlight(true);
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export Manifest");

			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			/****************** MERCURY *********************/

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Login to "MERCURY"
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);


			/**MVT Message Loading mvt_ata**/

			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");


			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);



			// Switch role
			cust.switchRole("Destination", "FCTL", "RoleGroup");
			
			
		    /***** CAPTURE CHECK SHEET for breakdown DGR***/
            cust.searchScreen("CHK002", "Capture Check Sheet");
            CHK002.listCheckSheetType("AWB");
            cust.setPropertyValue("AWBNo", cust.data("AWBNo"),proppath);
            CHK002.listAWBWithTransaction("AWBNo", "CarrierNumericCode","Breakdown");
            CHK002.captureCheckSheetAnswers(true, "leakage");
            CHK002.closeTab("CHK002", "Capture Check Sheet");

			/** OPR367- Import Manifest **/

			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			String pmkey = cust.data("UldNum");
			OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			OPR004.enterBreakdownDetails("BDNLocation","Pieces","Weight");
			OPR004.clickBreakdownComplete();
			OPR367.closeFromOPR004();
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");



			/********* OPR001 Import Documentation ***********/

			cust.searchScreen("OPR001", "Import Documentation: OPR001");
			OPR001.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			OPR001.listAWBDetails("CarrierNumericCode","AWBNo");
			// Clicking AWB Document received checkbox for AWB1
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo"));
			OPR001.clickCaptureHandover();
			OPR001.captureHandoverDetails("ConsigneeCode");
			OPR001.listAWBDetails("CarrierNumericCode","AWBNo");
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo"));
			OPR001.captureCheckSheetForDG(true, "leakage");
			OPR001.clickAWBDocumentReceived(cust.data("AWBNo"));
			OPR001.saveDetails();
			OPR001.closeTab("OPR001", "Import Documentation: OPR001");

		   /***Launch emulator - sst**/
			libr.launchSSTApp("sst-app");

			//Login to sst
			String [] sst=libr.getApplicationParams("hht");	
			cust.loginSST(sst[0], sst[1],"Public");


			/*** PUBLIC SIDE TOKEN GENERATION IN DROP OFF PICK UP SST SCREEN**/
			sstDP.invokeDropOffPickUpShipmentsSSTScreen();
			sstDP.addShipment("CarrierNumericCode", "prop~AWBNo");
			sstDP.addShipment("prop~CarrierNumericCode", "AWBNo2");
			sstDP.clickProceed();
			sstDP.enterDriverDetailsWithScroll("EndDate","Destination");
			sstDP.enterTrailerNo();
			sstDP.clickProceed();
			sstDP.selectVehicletype("VehicleType");
			sstDP.clickProceed();
			libr.waitForSync(2);
			sstDP.verifyTokenGeneration("TokenId");
			libr.quitApp();

			/************TGC010- VISIT DECLARATION ENQUIRY*****/
			cust.searchScreen("TGC010","Visit Declaration Enquiry");
			tgc010.enterToken("TokenId");
			tgc010.clickList();

			//Verify visit declaration details
			int verfCols[]={15}; 
			String[] actVerfValues={"Counter"};
			tgc010.verifyVisitDeclarationDetails(verfCols, actVerfValues, cust.data("TokenId"));
			cust.closeTab("TGC010", "Visit Declaration Enquiry");


			/************TGC015- SERVICE POINT OVERVIEW*****/
			cust.searchScreen("TGC015", "Servicepointoverview");
			libr.waitForSync(3);
			TGC015.selectWarehouse("serviceType");
			//verifying default token color
			TGC015.verifyDefaultTokenColor("TokenId","val~blue");

			TGC015.clickDeliveryPupose("TokenId");
			String awbno[]={cust.data("FullAWBNo"),cust.data("FullAWBNo2")};
			String primarykey[]={cust.data("AWBNo"),cust.data("AWBNo2")};
			//verifiying multiple awbnos for token
			TGC015.verifyMultiple_Awbno(awbno,2,primarykey);
			String statedPieces[]={cust.data("Pieces")+"Pcs"+cust.data("Weight")+" kg",cust.data("Pieces")+"Pcs"+cust.data("Weight")+" kg"};
			//verifiying stated pcs/wgt for awbs of token
			TGC015.verifyStated_Pieces(statedPieces,2,awbno,primarykey);

			String fullAwbNo[]={cust.data("FullAWBNo")};
			String pmkey1[]={cust.data("AWBNo")};

			//verifying shipment1 status for token
			TGC015.verifyDocumentReceivedStatus("received","green",fullAwbNo,1,pmkey1);
			TGC015.verifyReadyForDeliveryStatus("received","green",fullAwbNo,1,pmkey1);
			TGC015.verifyFlightArrivedStatus("received","green",fullAwbNo,1,pmkey1);
			TGC015.verifyBreakDownStatus("received","green",fullAwbNo,1,pmkey1);

			String fullAwbNo1[]={cust.data("FullAWBNo2")};
			String pmkey2[]={cust.data("AWBNo2")};

			//verifying shipment2 status for token
			TGC015.verifyDocumentReceivedStatus("notReceived","red",fullAwbNo1,1,pmkey2);
			TGC015.verifyReadyForDeliveryStatus("notReceived","red",fullAwbNo1,1,pmkey2);
			TGC015.verifyFlightArrivedStatus("notReceived","red",fullAwbNo1,1,pmkey2);
			TGC015.verifyBreakDownStatus("notReceived","red",fullAwbNo1,1,pmkey2);

			TGC015.verifyPopupClosure();
			cust.closeTab("TGC015", "Service Point Overview");

		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}
