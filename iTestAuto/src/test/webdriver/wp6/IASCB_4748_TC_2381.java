package wp6;


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
import screens.CaptureAWB_OPR026;
import screens.CaptureCheckSheet_CHK002;
import screens.Cgocxml;
import screens.ChecksheetHHT;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportDocumentation_OPR001;
import screens.ListMessages_MSG005;
import screens.ListTemplates_SHR093;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.ImportManifest_OPR367;
import screens.ListCheckSheetConfig_SHR094;
import screens.GoodsAcceptanceHHT;
import screens.BreakdownHHT;
import screens.DeliveryDocumentation_OPR293;
import screens.DropOffPickUpShipmentsSST;
import screens.ReportingAtDockHHT;
import screens.DeliveryHHT;
import screens.ServicePointAllocationHHT;
import screens.UldSightingHHT;
import screens.VisitDeclarationEnquiry_TGC010;
import screens.DeliverySlip_OPR038;
import screens.MarkFlightMovements_FLT006;
import screens.Servicepointoverview_TGC015;



/**
 *  TC_02_ Delivery of selected  shipment from Token from bonded area
 *  
 * 
 */



public class IASCB_4748_TC_2381 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public SecurityAndScreening_OPR339 OPR339;
	public CaptureAWB_OPR026 OPR026;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ImportManifest_OPR367 OPR367;
	public GoodsAcceptanceHHT gahht;
	public BreakdownHHT bdhht;
	public DeliveryDocumentation_OPR293 OPR293;
	public DropOffPickUpShipmentsSST dpsst;
	public ReportingAtDockHHT reportdockhht;
	public DeliveryHHT deliveryhht;
	public ServicePointAllocationHHT serpointhht;
	public VisitDeclarationEnquiry_TGC010 tgc010;
	public DeliverySlip_OPR038 OPR038;
	public CaptureCheckSheet_CHK002 CHK002;
	public ListCheckSheetConfig_SHR094 SHR094;
	public UldSightingHHT uldsighthht;
	public ListTemplates_SHR093 SHR093;
	public ImportDocumentation_OPR001 OPR001;
	public ChecksheetHHT checkhht;
	public Mercury mercuryScreen;
	public MarkFlightMovements_FLT006 FLT006;
	public Servicepointoverview_TGC015 TGC015;
	public Cgocxml Cgocxml;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String proppathsst = "\\src\\resources\\SSTLocators.properties";
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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		CHK002 = new CaptureCheckSheet_CHK002(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		bdhht=new BreakdownHHT(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		dpsst=new DropOffPickUpShipmentsSST(driver, excelreadwrite, xls_Read);
		reportdockhht=new ReportingAtDockHHT(driver, excelreadwrite, xls_Read);
		deliveryhht = new DeliveryHHT(driver, excelreadwrite, xls_Read);
		serpointhht=new ServicePointAllocationHHT(driver, excelreadwrite, xls_Read);
		tgc010=new VisitDeclarationEnquiry_TGC010(driver, excelreadwrite, xls_Read);
		OPR038=new DeliverySlip_OPR038(driver, excelreadwrite, xls_Read);
		uldsighthht=new UldSightingHHT(driver, excelreadwrite, xls_Read);
		checkhht=new ChecksheetHHT(driver, excelreadwrite, xls_Read);
		SHR094 = new ListCheckSheetConfig_SHR094(driver, excelreadwrite, xls_Read);
		SHR093 = new ListTemplates_SHR093(driver, excelreadwrite, xls_Read);
		OPR001 = new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		TGC015 = new Servicepointoverview_TGC015(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_2380")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2380")
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

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());

			excelRead.writeDataInExcel(map, path1, sheetName, testName);



			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

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

			// Checking AWB1 is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			map.put("awbNo1", cust.data("AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo2", cust.data("prop~FullAWBNo"));
			map.put("AWBNo2", cust.data("prop~AWBNo"));
			map.put("awbNo2", cust.data("AWBNo2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			cust.setPropertyValue("AWBNo", cust.data("AWBNo"), proppath);
			cust.setPropertyValue("AWBNo2", cust.data("AWBNo2"), proppath);
			System.out.println(cust.data("FullAWBNo"));
			System.out.println(cust.data("FullAWBNo2"));


			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum);
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			/****************** MERCURY *********************/

			//	 Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** SSM Message loading **/

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");

			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/** MSG005-XFSU-BKD Message loading **/

			//AWB1
			map.put("awbNumber", cust.data("FullAWBNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFSU", "MessageParamXFSU");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			//AWB2
			map.put("awbNumber", cust.data("FullAWBNo2"));
			cust.createXMLMessage("MessageExcelAndSheetXFSU", "MessageParamXFSU");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** MSG005 -XFBL Message loading **/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") , libr.data("FullAWBNo2") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
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
			map.put("awbNumber", cust.data("FullAWBNo2"));
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

			//AWB1
			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			//Capture check sheet
			OPR026.captureCheckSheet(true,"leakage");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			//AWB2
			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/***** OPR026 - Execute AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			//Capture check sheet
			OPR026.captureCheckSheet(true,"leakage");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/**List Check Sheet Configurations _SHR094**/

			cust.searchScreen("SHR094", "List Check Sheet Configuration");
			SHR094.selectCheckSheetType("val~AWB");
			SHR094.selectTransaction("Acceptance");
			SHR094.enterSccGrp("val~DGR");
			SHR094.enterAirportGrp("val~HUBS");
			SHR094.selectStatus("Active");
			SHR094.listDetails();
			String templateId=SHR094.getTemplateID();
			map.put("templateId", templateId);
			cust.closeTab("SHR094", "List Check Sheet Configuration");

			/**List Template SHR093**/

			cust.searchScreen("SHR093", "List Templates");
			SHR093.enterTemplateId(templateId);
			SHR093.listDetails();
			String templateName=SHR093.getTemplateName();
			templateName=templateName.trim();
			map.put("templateName", templateName);
			cust.closeTab("SHR093", "List Templates");

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");		

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht2");	
			cust.loginHHT(hht[0], hht[1]);


			/*** HHT - ACCEPTANCE****/

			gahht.invokeAcceptanceScreen();

			//Acceptance for AWB1
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo"));
			gahht.enterValue("awbNumber");
			//capture check sheet
			checkhht.clickChecksheetTemplate(templateName);
			checkhht.captureCheckSheet("leakage");
			checkhht.clickSave();
			// Select SCC button
			gahht.selectSCCValue("SCC");
			gahht.clickSCCOK();
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "Location");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			cust.clickBack("Acceptance");
			libr.waitForSync(8);
			//Acceptance for AWB2
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo2"));
			gahht.enterValue("awbNumber");
			//capture check sheet
			checkhht.clickChecksheetTemplate(templateName);
			checkhht.captureCheckSheet("leakage");
			checkhht.clickSave();
			// Select SCC button
			gahht.selectSCCValue("SCC");
			gahht.clickSCCOK();
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "Location");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");
			libr.quitApp();

			//AWB1
			/****OPR355 - Loose Acceptance****/

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAcceptanceFinalized("finalised", false);
			cust.closeTab("OPR335", "Goods Acceptance");

			//AWB2
			/****OPR355 - Loose Acceptance****/

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo2", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAcceptanceFinalized("finalised", false);
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

			//manifesting and finalizing flight 
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			System.out.println(uldNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWB("UldNum","0","CarrierNumericCode","AWBNo","Pieces","Weight");
			OPR344.clickEditULDdetails("UldNum");
			OPR344.addAWBtoExistingULDwithPcsWeight("UldNum","CarrierNumericCode","AWBNo2","Pieces","Weight");
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			OPR344.finalizeFlight(true);
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export Manifest");



			/** Switch role to Destination **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/** Mark Flight Movement **/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode","FlightNo","StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Amsterdam");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Amsterdam");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementArrivalDetails(currtime,currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");


			/*****OPR367 - Import Manifest*******/           
			//Verify the AWB details
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode","prop~flightNo", "StartDate");
			OPR367.maximizeAllDetails();
			OPR367.SaveDetails();
			OPR367.closeTab("OPR367", "Import Manifest");


			/***Launch emulator - uldsighting app**/
			libr.launchUldSightingApp("uldsighting-app");
			//Login in to ULD Sighting App
			String [] hht2=libr.getApplicationParams("hht");	
			cust.loginHHT(hht2[0], hht2[1]);

			uldsighthht.clickDone();
			uldsighthht.enterUldNumber("UldNum");
			uldsighthht.clickSight();
			uldsighthht.verifySighted("UldNum");
			//Select forward location
			uldsighthht.selectFwLocation("ForwardLocation");
			uldsighthht.clickComplete();
			libr.quitApp();


			cust.searchScreen("CHK002", "Capture Check Sheet");
			CHK002.listCheckSheetType("AWB");
			cust.setPropertyValue("AWBNo", cust.data("AWBNo"),proppath);
			CHK002.listAWBWithTransaction("AWBNo", "CarrierNumericCode","Breakdown");
			CHK002.captureCheckSheetAnswers(true, "leakage");
			CHK002.closeTab("CHK002", "Capture Check Sheet");


			cust.searchScreen("CHK002", "Capture Check Sheet");
			CHK002.listCheckSheetType("AWB");
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			CHK002.listAWBWithTransaction("AWBNo", "CarrierNumericCode","Breakdown");
			CHK002.captureCheckSheetAnswers(true, "leakage");
			CHK002.closeTab("CHK002", "Capture Check Sheet");


			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			cust.loginHHT(hht2[0], hht2[1]);

			/*** HHT - BREAKDOWN ****/

			bdhht.invokeBreakdownHHTScreen();
			bdhht.enterValue("UldNum");

			//AWB1
			bdhht.selectAwb("FullAWBNo");
			bdhht.clickSaveCaptureChecksheet();
			bdhht.enterLocation("BDNLocation");
			String scc1[] = { cust.data("SCC")};
			bdhht.selectMultipleSCC(scc1);
			bdhht.addPcs("Pieces");	
			bdhht.clickSave();
			libr.waitForSync(6);

			//AWB2
			bdhht.clickSaveCaptureChecksheet();
			bdhht.selectAwb("FullAWBNo2");
			bdhht.clickSaveCaptureChecksheet();
			bdhht.enterLocation("BDNLocation");
			bdhht.selectMultipleSCC(scc1);
			bdhht.addPcs("Pieces");	
			bdhht.clickSave();
			libr.waitForSync(6);
			bdhht.clickSaveCaptureChecksheet();
			//Marking BreakdownComplete
			bdhht.clickMoreOptions();
			bdhht.clickBreakdownCompleteBtn();
			cust.clickBack("Breakdown");
			libr.quitApp();

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
			String DNInfo2=OPR293.retrieveDeliveryDocumentationDetails("AWBNo2", "12");
			map.put("DNinfo2",DNInfo2);
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

			/*** TOKEN GENERATION IN DROP OFF PICK UP SST SCREEN**/

			dpsst.invokeDropOffPickUpShipmentsSSTScreen();
			dpsst.addShipment("CarrierNumericCode", "AWBNo");
			dpsst.addShipment("CarrierNumericCode", "AWBNo2");
			dpsst.clickProceed();
			dpsst.enterDriverDetailsForBondedSide("StartDate", "Destination");
			dpsst.clickProceed();
			dpsst.selectVehicletype("VehicleType");
			dpsst.clickProceed();
			libr.waitForSync(5);
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
			cust.loginHHT(hht2[0], hht2[1]);

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


			//SMART NAVIGATION
			/** Delivery HHT **/
			libr.waitForSync(8);
			map.put("awbno1",cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo")+" ");
			deliveryhht.verifyAWBNumber("awbno1");
			map.put("awbno2",cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo2")+" ");
			deliveryhht.verifyAWBNumber("awbno2");

			deliveryhht.clickSelectOptionIcon("awbno2");
			deliveryhht.clickDeliverButton();
			deliveryhht.selectShipment();
			deliveryhht.clickSelectAll();
			deliveryhht.clickNext();
			deliveryhht.enterDeliverRemarks("val~Delivered");
			deliveryhht.enterCustomsReferenceNumberIfNotAutopopulated("customRefNo");
			deliveryhht.clickNext();
			deliveryhht.verifyDeliveredStatusAgainstAWB("awbno2","val~DELIVERED");
			deliveryhht.clickDeliveryComplete();
			deliveryhht.enterDeliveredTo("consigneeCode");
			deliveryhht.enterVehicleInfo("VehicleInfo");
			deliveryhht.enterContactNumber("ContactNumber");
			deliveryhht.clickNext();
			deliveryhht.captureSignature();
			deliveryhht.enterRemarks("val~Delivery complete");
			deliveryhht.clickPrintPOD();

			/*** HHT - REPORTING AT DOCK****/
			libr.waitForSync(7);
			reportdockhht.complete();
			reportdockhht.releaseDock();
			libr.quitApp();


			/*******  Delivery Slip*******/ 

			cust.searchScreen("OPR038", "OPR038- Delivery Slip");
			OPR038.listByAWB("CarrierNumericCode", "AWBNo2");
			String pmKey = cust.data("ConsigneeCode");
			int verfCols1 [] = { 3,4,9};
			String[] actVerfValues1= { cust.data("Pieces"),cust.data("Weight"),"DELIVERY COMPLETE" };
			OPR038.verifyTableRecords( verfCols1, actVerfValues1,pmKey);
			OPR038.selectCheckbox();
			OPR038.clickReprint();
			//Steps to verify the delivery slip contents 
			String DNdetails=cust.data("DNinfo2").substring(0, 5)+"( "+cust.data("Pieces")+"/"+cust.data("Weight")+"kg,"+"\n"+"Customs reference "+"number: "+cust.data("customRefNo")+" )";
			cust.printAndVerifyReport("val~DELIVERY SLIP", "OPR038",cust.data("Destination"),cust.data("FullAWBNo2"),cust.data("Origin"),cust.data("ShipmentDesc"),cust.data("Pieces"),cust.data("Weight1"),cust.data("ConsigneeName"),DNdetails,"DELIVERY COMPLETE");
			OPR038.closeTab("OPR038", "Delivery Slip");



			/*******Verify FSU-DLV message in MSG005******/

			//AWB2

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Delivery");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyDLV=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo2");
			int verfColsDLV[]={9};
			String[] actVerfValuesDLV={"Sent"};
			MSG005.verifyMessageDetails(verfColsDLV, actVerfValuesDLV, pmKeyDLV,"val~XFSU-DLV",false);
			libr.waitForSync(1);


			/*** VERIFY THE MESSAGE CONTENTS***/
			map.put("pmkey", pmKeyDLV);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			msgContents.add("val~<Content>"+cust.data("customRefNo")+"</Content>");
			MSG005.verifyMessageContent(msgContents,"XFSU");
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");




		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}