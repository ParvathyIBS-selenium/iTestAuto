package wp6;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.ImportManifest_OPR367;
import screens.DropOffPickUpShipmentsSST;
import screens.ServicePointAllocationHHT;
import screens.ReportingAtDockHHT;
import screens.DeliveryHHT;
import screens.DeliverySlip_OPR038;
import screens.MaintainFlightSchedule_FLT005;
import screens.DeliveryDocumentation_OPR293;
import screens.SecurityAndScreening_OPR339;
import screens.GoodsAcceptance_OPR335;
import screens.ExportManifest_OPR344;
import screens.ImportDocumentation_OPR001;
import screens.Servicepointoverview_TGC015;
import screens.VisitDeclarationEnquiry_TGC010;
import screens.CaptureCheckSheet_CHK002;
import screens.Mercury;
import screens.Cgocxml;


/**
 * 
 * TC_01_Verify delivery of multiple AWBs at a time through the HHT delivery screen at CDG
 *
 *
 */



public class IASCB_25087_TC_2472 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ImportManifest_OPR367 OPR367;
	public CaptureAWB_OPR026 OPR026;
	public BreakDownScreen_OPR004 OPR004;
	public DropOffPickUpShipmentsSST dpsst;
	public ReportingAtDockHHT reportdockhht;
	public DeliveryHHT deliveryhht;
	public DeliverySlip_OPR038 OPR038;
	public ImportDocumentation_OPR001 OPR001;
	public MaintainFlightSchedule_FLT005 FLT005;
	public DeliveryDocumentation_OPR293 OPR293;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public ServicePointAllocationHHT serpointhht;
	public Servicepointoverview_TGC015 TGC015;
	public VisitDeclarationEnquiry_TGC010 tgc010;
	public CaptureCheckSheet_CHK002 CHK002;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
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
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		dpsst=new DropOffPickUpShipmentsSST(driver, excelreadwrite, xls_Read);
		reportdockhht=new ReportingAtDockHHT(driver, excelreadwrite, xls_Read);
		deliveryhht = new DeliveryHHT(driver, excelreadwrite, xls_Read);
		serpointhht=new ServicePointAllocationHHT(driver, excelreadwrite, xls_Read);
		OPR038=new DeliverySlip_OPR038(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR001 = new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		TGC015 = new Servicepointoverview_TGC015(driver, excelreadwrite, xls_Read);
		tgc010=new VisitDeclarationEnquiry_TGC010(driver, excelreadwrite, xls_Read);
		CHK002= new CaptureCheckSheet_CHK002(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_2472")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2472")
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
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));

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

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("awbNumber1", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			map.put("FullAWBNo", cust.data("awbNumber1"));
			map.put("AWBNo", cust.data("awb1"));
			map.put("awbNo1", cust.data("AWBNo"));
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
			map.put("awbNo2", cust.data("AWBNo2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"),proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");


			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight details
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum);


			libr.quitBrowser();

			/****************** MERCURY *********************/

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");


			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFSU-BKD Message loading  AWB1**/

			map.put("FullAWBNum", cust.data("awbNumber1"));
			cust.createXMLMessage("MessageExcelAndSheetXFSU", "MessageParamXFSU");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFSU-BKD Message loading  AWB2**/

			map.put("FullAWBNum", cust.data("awbNumber2"));
			cust.createXMLMessage("MessageExcelAndSheetXFSU", "MessageParamXFSU");
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/** XFBL Message loading  AWBs**/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC"), cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
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

			/** OPR335 - ULD Acceptance **/

			cust.searchScreen("OPR335", "Goods Acceptance");
			OPR335.listAWB(cust.data("AWBNo"),"CarrierNumericCode");
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR335.uldShipmentDetails("Pieces", "Weight", "Location", "UldNum", "");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.captureCheckSheet(true, "leakage");
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");

			/** OPR335 - ULD Acceptance **/


			cust.searchScreen("OPR335", "Goods Acceptance");
			OPR335.listAWB(cust.data("AWBNo2"),"CarrierNumericCode");
			String uldNo2 = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum2", uldNo2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR335.uldShipmentDetails("Pieces", "Weight", "Location", "UldNum2", "");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.captureCheckSheet(true, "leakage");
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.clickSave();
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
			
            /**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR344.assignUldPlanningSection("UldNum");
			OPR344.verifyULDInAssignedShipment("UldNum", true);
			libr.waitForSync(8);
			OPR344.assignUldPlanningSection("UldNum2");
			libr.waitForSync(5);
			OPR344.verifyULDInAssignedShipment("UldNum2", true);
			libr.waitForSync(4);
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			OPR344.finalizeFlight(true);
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export manifest");

			libr.quitBrowser();

			/****************** MERCURY *********************/

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Login to "MERCURY"
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** Loading MVT : ARRIVAL  **/

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
           
            
            /***** CAPTURE CHECK SHEET for breakdown DGR***/
            cust.searchScreen("CHK002", "Capture Check Sheet");
            CHK002.listCheckSheetType("AWB");
            cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
            CHK002.listAWBWithTransaction("AWBNo", "CarrierNumericCode","Breakdown");
            CHK002.captureCheckSheetAnswers(true, "leakage");
            CHK002.closeTab("CHK002", "Capture Check Sheet");

			/** OPR367- Import Manifest **/

			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code", "FlightNo", "StartDate");
			String pmkey = Excel.getCellValue(path1, sheetName, "IASCB_25087_TC_2472", "UldNum");
			OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			String[] Location = { cust.data("BDNLocation"), cust.data("BDNLocation") };
			String[] Pieces = { cust.data("Pieces"), cust.data("Pieces") };
			String[] Weight = { cust.data("Weight"), cust.data("Weight") };
			OPR367.enterBdnDetails_multipleShipments(1, Location, Pieces, Weight);
			OPR004.clickBreakdownComplete();
			OPR367.closeFromOPR004();


			String pmkey1 = Excel.getCellValue(path1, sheetName, "IASCB_25087_TC_2472", "UldNum2");
			OPR367.clickCheckBox_ULD(pmkey1);
			OPR367.clickBreakdownButton();
			OPR367.enterBdnDetails_multipleShipments(1,Location,Pieces,Weight);
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

			/********* OPR001 Import Documentation ***********/

			cust.searchScreen("OPR001", "Import Documentation: OPR001");
			OPR001.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			OPR001.listAWBDetails("CarrierNumericCode", "AWBNo2");
			// Clicking AWB Document received checkbox for AWB2
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo2"));
			OPR001.clickCaptureHandover();
			OPR001.captureHandoverDetails("ConsigneeCode");
			OPR001.listAWBDetails("CarrierNumericCode","AWBNo2");
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo2"));
			OPR001.captureCheckSheetForDG(true, "leakage");	
			OPR001.clickAWBDocumentReceived(cust.data("AWBNo2"));
			OPR001.saveDetails();
			OPR001.closeTab("OPR001", "Import Documentation: OPR001");

			/********** OPR293-Delivery Documentation **********/

			// Generate delivery id
			cust.searchScreen("OPR293", "Delivery Documentation");
			OPR293.listWithFlightNumber("prop~flight_code", "FlightNo","StartDate");
			OPR293.selectAllAWBs();
			OPR293.generateDeliveryID3();
			OPR293.verifyDNStatus("Paid");
			map.put("DeliveryID", OPR293.getDeliveryID());
			OPR293.closeTab("OPR293", "Delivery Documentation");


			/***** CAPTURE CHECK SHEET***/
			//capture check sheet for AWB1
			cust.setPropertyValue("AWBNo", cust.data("AWBNo"),proppath);
			cust.searchScreen("CHK002", "Capture Check Sheet");
			CHK002.listCheckSheetType("AWB");
			CHK002.listAWBWithTransaction("AWBNo", "prop~CarrierNumericCode","Lodgement");
			CHK002.enterCheckSheetAns();
			CHK002.save();
			CHK002.closeTab("CHK002", "Capture Check Sheet");

			/***** CAPTURE CHECK SHEET***/
			//capture check sheet for AWB2
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("CHK002", "Capture Check Sheet");
			CHK002.listCheckSheetType("AWB");
			CHK002.listAWBWithTransaction("AWBNo", "prop~CarrierNumericCode","Lodgement");
			CHK002.enterCheckSheetAns();
			CHK002.save();
			CHK002.closeTab("CHK002", "Capture Check Sheet");



			/***Launch emulator - sst**/
			libr.launchSSTApp("sst-app");

			//Login in to SST
			String [] sst=libr.getApplicationParams("hht");	
			cust.loginSST(sst[0], sst[1],"Bonded");


			/*** TOKEN GENERATION IN DROP OFF PICK UP SST SCREEN FROM BONDED SIDE**/
			dpsst.invokeDropOffPickUpShipmentsSSTScreen();
			dpsst.addShipment("prop~CarrierNumericCode", "AWBNo");
			dpsst.addShipment("prop~CarrierNumericCode", "AWBNo2");
			dpsst.clickProceed();
			dpsst.enterDriverDetailsForBondedSide("StartDate", "Destination");
			dpsst.clickProceed();
			dpsst.selectVehicletype("VehicleType");
			dpsst.clickProceed();
			libr.waitForSync(2);
			dpsst.verifyTokenGeneration("TokenId");
			dpsst.getDockServicePointName("servicepoint");
			map.put("ServicePoint",cust.data("servicepoint"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			/************TGC010- VISIT DECLARATION ENQUIRY*****/
			cust.searchScreen("TGC010","Visit Declaration Enquiry");
			tgc010.enterToken("TokenId");
			tgc010.clickList();

			//Verify visit declaration details
			int verfCols[]={15,35}; 
			String[] actVerfValues={"Truck Dock","Assigned"};
			tgc010.verifyVisitDeclarationDetails(verfCols, actVerfValues, cust.data("TokenId"));
			cust.closeTab("TGC010", "Visit Declaration Enquiry");


			/************TGC015- SERVICE POINT OVERVIEW*****/
			cust.searchScreen("TGC015", "Servicepointoverview");
			libr.waitForSync(3);
			TGC015.selectWarehouse("serviceType");
			//verifying token generated got displayed
			TGC015.verifyTokenIsDisplayed("TokenId");
			cust.closeTab("TGC015", "Service Point Overview");

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");		

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);


			/*** HHT - SERVICE POINT ALLOCATION****/

			if(cust.data("tokenInWaitingArea").equals("true"))
			{
				serpointhht.invokeServicePointAllocationScreen();
				serpointhht.enterToken("TokenId");
				serpointhht.enterServicePoint("ServicePoint");
				serpointhht.callForward();
				serpointhht.confirmIfCallForwarded();
				cust.clickBack("Service Point Allocation");
			}

			/*** HHT - REPORTING AT DOCK****/

			reportdockhht.invokeReportingAtDockScreen();
			reportdockhht.enterToken("TokenId");
			reportdockhht.clickReportDock();
			reportdockhht.captureCheckSheet();
			reportdockhht.clickSaveCaptureChecksheet();
			reportdockhht.enterCurrentDock("ServicePoint");
			reportdockhht.start();


			//** Delivery HHT -SMART NAVIGATION **/

			libr.waitForSync(3);
			//verify Select ALL Button
			deliveryhht.verifySelectAllButton("val~Select All");
			//verifying check box against each awb
			map.put("awbno1",cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo")+" ");
			deliveryhht.verifyCheckBoxAgainstAWB("awbno1");

			map.put("awbno2",cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo2")+" ");
			deliveryhht.verifyCheckBoxAgainstAWB("awbno2");


			deliveryhht.clickSelectAll();
			deliveryhht.clickDeliverButton();
			deliveryhht.clickNext();
			deliveryhht.enterDeliverRemarks("val~Delivered");
			deliveryhht.enterCustomsReferenceNumber("customRefNo");
			deliveryhht.clickNext();
			deliveryhht.verifyDeliveredStatusAgainstAWB("awbno1","val~DELIVERED");
			deliveryhht.verifyDeliveredStatusAgainstAWB("awbno2","val~DELIVERED");
			deliveryhht.clickDeliveryComplete();
			deliveryhht.enterDeliveredTo("consigneeCode");
			deliveryhht.enterVehicleInfo("VehicleNo");
			deliveryhht.enterContactNumber("ContactNumber");
			deliveryhht.clickNext();
			deliveryhht.captureSignature();
			deliveryhht.enterRemarks("val~Delivery complete");
			deliveryhht.clickPrintPOD();
			libr.waitForSync(15);


			/*** HHT - REPORTING AT DOCK****/

			reportdockhht.complete();
			reportdockhht.releaseDock();
			libr.quitApp();



			/*****OPR038 - Delivery Slip*******/ 
			cust.searchScreen("OPR038", "OPR038- Delivery Slip");
			OPR038.listByAWB("prop~CarrierNumericCode", "AWBNo");
			String pmKey = cust.data("ConsigneeCode");
			int verfCols1 [] = { 3,4,9};
			String[] actVerfValues1= { cust.data("Pieces"),cust.data("Weight"),"DELIVERY COMPLETE" };
			OPR038.verifyTableRecords( verfCols1, actVerfValues1,pmKey);
			OPR038.selectCheckbox();
			OPR038.clickReprint();
			cust.printAndVerifyReport("val~DELIVERY SLIP","OPR038",cust.data("FullAWBNo"));
			libr.waitForSync(3);
			OPR038.clickClear();
			OPR038.listByAWB("prop~CarrierNumericCode", "AWBNo2");
			OPR038.verifyTableRecords( verfCols1, actVerfValues1,pmKey);
			OPR038.selectCheckbox();
			OPR038.clickReprint();
			cust.printAndVerifyReport("val~DELIVERY SLIP","OPR038",cust.data("FullAWBNo2"));
			OPR038.closeTab("OPR038", "Delivery Slip");



		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}