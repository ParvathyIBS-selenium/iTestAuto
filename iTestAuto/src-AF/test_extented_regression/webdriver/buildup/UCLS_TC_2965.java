package buildup;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.Cafeed;
import screens.CaptureAWB_OPR026;
import screens.CaptureCheckSheet_CHK002;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.MaintainOperationalFlight_FLT003;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.CaptureDGDetails_OPR350;
import screens.GenerateNOTOC_OPR017;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

/**TC_09_Verify flight details sent to CAFEED after flight closure for part shipment**/

public class UCLS_TC_2965 extends BaseSetup{

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
	public MaintainOperationalFlight_FLT003 FLT003;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public CaptureDGDetails_OPR350 OPR350;
	public CaptureCheckSheet_CHK002 CHK002;
	public GenerateNOTOC_OPR017 OPR017;
	public Cafeed cfd;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;


	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Buildup.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "Buildup_SIT";

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
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);		
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR350 = new CaptureDGDetails_OPR350(driver, excelreadwrite, xls_Read);
		CHK002= new CaptureCheckSheet_CHK002(driver, excelreadwrite, xls_Read);
		cfd= new Cafeed(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		OPR017= new GenerateNOTOC_OPR017(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2965")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2965")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String date=cust.createDateFormat("ddMMMYY", 0, "DAY", "");
			System.out.println(date);
			map.put("StartDate", startDate);
			map.put("Date", date);
			cfd.createnewFlightInCafeedwindow("prop~flightNumber","Date","FullFlightNumber","StartDate");
			map.put("FullFlightNo",cust.data("prop~flightNumber"));
			map.put("FlightNo",cust.data("prop~flightNo"));
			cfd.createnewFlightInCafeedwindow("prop~flightNumber","Date","FullFlightNumber","StartDate");
			map.put("FullFlightNo1",cust.data("prop~flightNumber"));
			map.put("FlightNo1",cust.data("prop~flightNo"));

			String endDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());

			//Login to iCargo

			String [] iCargo=libr.getApplicationParams("iCargoSTG");
			driver = libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("FlightNo", "StartDate");
			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT003.enterLegCapacityDetails("ATD_Local","ATA_Local", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");

			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("FlightNo1", "StartDate");
			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT003.enterLegCapacityDetails("ATD_Local","ATA_Local", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("awbNumber1", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			map.put("FullAWBNo", cust.data("awbNumber1"));

			map.put("AWBNo1", cust.data("awb1"));
			cust.setPropertyValue("AWBNo1", cust.data("AWBNo1"), proppath);

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2
			map.put("awbNumber2", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));
			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo2", cust.data("awb2"));
			cust.setPropertyValue("AWBNo2", cust.data("AWBNo2"), proppath);

			// Checking AWB is fresh or Not (AWBNumber3)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber3
			map.put("awbNumber3", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb3", cust.data("prop~AWBNo"));
			map.put("FullAWBNo3", cust.data("awbNumber3"));
			map.put("AWBNo3", cust.data("awb3"));
			cust.setPropertyValue("AWBNo3", cust.data("AWBNo3"), proppath);
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/***Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFBL Message loading for flight 1 **/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("flightNumber", cust.data("FullFlightNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment1[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces1").split(",")[0]+ ";" + libr.data("Weight1").split(",")[0] + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc").split(",")[0] };
			String scc[] = { cust.data("SCC").split(",")[0] };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment1, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFBL Message loading for flight 2 **/

			map.put("flightNumber", cust.data("FullFlightNo1"));
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment2[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces1").split(",")[1] + ";" + libr.data("Weight1").split(",")[1] + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc").split(",")[0] };

			cust.createXFBLMessage("XFBL_2", shipment2, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 1 **/

			map.put("FullAWBNum", cust.data("awbNumber1"));
			map.put("scc", cust.data("SCC").split(",")[0]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[0]);
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 2 **/

			map.put("FullAWBNum", cust.data("awbNumber2"));
			map.put("scc", cust.data("SCC").split(",")[1]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[1]);
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 3 **/

			map.put("FullAWBNum", cust.data("awbNumber3"));
			map.put("scc", cust.data("SCC").split(",")[1]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[1]);
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
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

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo1", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo3"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/***** OPR026 - Execute AWB ****/
			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/***** OPR026 - Execute AWB ****/

			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/***** OPR026 - Execute AWB ****/

			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo3", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR335 -Goods Acceptance ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");


			/**** OPR335 -Goods Acceptance ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR335 -Goods Acceptance ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo3"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");



			//switch role
			cust.switchRoleToNewRoleGroup("Origin", "FCTL", "RoleGroup1");


			/**** OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			map.put("pcs1", libr.data("Pieces1").split(",")[0]);
			map.put("wt1", libr.data("Weight1").split(",")[0]);
			OPR344.addNewULDWithAWBSplitShipment("UldNum","0","CarrierNumericCode","AWBNo1","pcs1","wt1");
			OPR344.clickEditULDdetailsByJS("UldNum");
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight", "Occupancy");
			cust.waitForSync(5);
			String uldNum1=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNum1);
			OPR344.addNewULDWithAWB("UldNum1","0","CarrierNumericCode","AWBNo2","Pieces","Weight");
			OPR344.clickEditULDdetailsByJS("UldNum1");
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight", "Occupancy");
			cust.waitForSync(2);
			OPR344.clickNOTOC();
			OPR017.clickULDExpand();
			OPR017.performULDAsscWithUNID("UldNum","UNID");
			OPR017.selectUNID();
			String[] Uldnumbers={cust.data("UldNum")};
			OPR017.selectsULDsByJS(Uldnumbers,1);
			String[] AWBs={cust.data("FullAWBNo1")};
			OPR017.generateNOTOCandVerifyReportWithCont("OPR344",AWBs);
			OPR017.clickClose();
			OPR344.clickBuildUpComplete("UldNum");
			cust.closeTab("OPR344", "Export Manifest");
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Login to Cafeed
			String[] cafeed = libr.getApplicationParams("cafeed");
			driver.get(cafeed[0]);
			Thread.sleep(2000);
			cust.loginToCafeed(cafeed[1], cafeed[2]);
			Thread.sleep(2000);

			cfd.clickSearchFlightLink();	
			cfd.listFlightDetails("FullFlightNo", "Date");
			int[] col = {15,16};
			String[] expValue = { cust.data("pcs1"),cust.data("ActualWeight")};
			cfd.verifyULDDetails(col, expValue, cust.data("UldNum"));
			cfd.clickAWBInsideULD("UldNum", "FullAWBNo1");
			cfd.verifyDGDetails("UNID", "PI", "ShippingName");
			libr.quitBrowser();



			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			//switch role
			cust.switchRoleToNewRoleGroup("Origin", "FCTL", "RoleGroup1");



			/**** OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo1","StartDate");
			String uldNum2=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum2", uldNum2);
			map.put("pcs2", libr.data("Pieces1").split(",")[1]);
			map.put("wt2", libr.data("Weight1").split(",")[1]);
			OPR344.addNewULDWithAWBSplitShipment("UldNum2","0","CarrierNumericCode","AWBNo1","pcs2","wt2");
			OPR344.clickEditULDdetailsByJS("UldNum2");
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight", "Occupancy");
			cust.waitForSync(5);
			String uldNum3=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum3", uldNum3);
			OPR344.addNewULDWithAWB("UldNum3","0","CarrierNumericCode","AWBNo3","Pieces","Weight");
			OPR344.clickEditULDdetailsByJS("UldNum3");
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight", "Occupancy");
			cust.waitForSync(2);
			OPR344.clickNOTOC();
			OPR017.clickULDExpand();
			OPR017.performULDAsscWithUNID("UldNum2","UNID");
			OPR017.selectUNID();
			String[] Uldnumbers2={cust.data("UldNum2")};
			OPR017.selectsULDsByJS(Uldnumbers2,1);
			String[] AWBs2={cust.data("FullAWBNo1")};
			OPR017.enterPcsUNIDDetails("val~1");
			OPR017.generateNOTOCandVerifyReportWithCont("OPR344",AWBs2);
			OPR017.clickClose();
			OPR344.clickBuildUpComplete("UldNum2");
			cust.closeTab("OPR344", "Export Manifest");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Login to Cafeed
			driver.get(cafeed[0]);
			Thread.sleep(2000);
			cust.loginToCafeed(cafeed[1], cafeed[2]);
			Thread.sleep(2000);

			/****Cafeed ******/
			cfd.clickSearchFlightLink();
			cfd.listFlightDetails("FullFlightNo1", "Date");
			String[] expValue2 = { cust.data("pcs2"),cust.data("ActualWeight") };
			cfd.verifyULDDetails(col, expValue2, cust.data("UldNum2"));
			cfd.clickAWBInsideULD("UldNum2", "FullAWBNo1");
			cfd.verifyDGDetails("UNID", "PI", "ShippingName");
			libr.quitBrowser();





		}catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}

		finally
		{
			try
			{
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
