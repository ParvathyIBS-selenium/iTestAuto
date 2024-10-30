package buildup;

import java.util.Map;

import org.testng.Assert;
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
import screens.Cafeed;
import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;

import screens.SecurityAndScreening_OPR339;

/** Test Id-2968  Verify ULD details sent to CAFEED after flight closure for buildup- Web screen **/



public class UCLS_TC_2968 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	public ExportManifest_OPR344 OPR344;
	public Cafeed cfd;
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainOperationalFlight_FLT003 FLT003;
	public MaintainFlightSchedule_FLT005 FLT005;
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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		cfd= new Cafeed(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);


	}

	@DataProvider(name = "TC_2960")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2960")
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
			map.put("ATA_Local",cust.data("ATA_Local2"));
			System.out.println(cust.data("ATA_Local"));

			cfd.createnewFlightInCafeedwindow("prop~flightNumber","Date","FullFlightNumber","StartDate");
			map.put("FullFlightNo",cust.data("prop~flightNumber"));
			map.put("FlightNo",cust.data("prop~flightNo"));

			//Login to iCargo

			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver = libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);

			cust.loginICargoSTG(iCargo[1], iCargo[2]);


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

			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("ATA_Local",cust.data("ATA_Local1"));


			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("FlightNo", "StartDate");
			FLT003.enterMultiLegFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");


			// Checking AWB is fresh or Not--AWB 1
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo1", cust.data("prop~FullAWBNo"));
			map.put("AWBNo1", cust.data("prop~AWBNo"));


			// Checking AWB is fresh or Not--AWB 2
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo2", cust.data("prop~FullAWBNo"));
			map.put("AWBNo2", cust.data("prop~AWBNo"));


			// Checking AWB is fresh or Not--AWB 2
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo3", cust.data("prop~FullAWBNo"));
			map.put("AWBNo3", cust.data("prop~AWBNo"));

			// Checking AWB is fresh or Not--AWB 2
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo4", cust.data("prop~FullAWBNo"));
			map.put("AWBNo4", cust.data("prop~AWBNo"));


			/*** Login to cgocxml **********/

			/**** XFSU-BKD Message loading AWB1 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo1"));
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "BKD", true);

			/**** XFSU-BKD Message loading AWB1 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo2"));
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "BKD", true);

			map.put("FullAWBNo", cust.data("FullAWBNo4"));
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "BKD", true);

			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("FullAWBNo1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[0],
							cust.data("FullAWBNo2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[1],cust.data("FullAWBNo4") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
											+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[0]};
			String scc[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1],cust.data("SCC").split(",")[0]};

			String routing[] = { cust.data("Origin") + ";" + cust.data("Transit"),cust.data("Origin") + ";" + cust.data("Transit"),cust.data("Origin") + ";" + cust.data("Transit")};
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);

			/**** XFWB Message loading AWB1 ****/
			map.put("FullAWBNo", cust.data("FullAWBNo1"));
			map.put("scc",cust.data("SCC").split(",")[0]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[0]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true); 

			/**** XFWB Message loading AWB2 ****/
			map.put("FullAWBNo", cust.data("FullAWBNo2"));
			map.put("scc",cust.data("SCC").split(",")[1]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true); 

			/**** XFWB Message loading AWB2 ****/
			map.put("FullAWBNo", cust.data("FullAWBNo3"));
			map.put("scc",cust.data("SCC").split(",")[0]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[0]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true); 


			/**** XFWB Message loading AWB2 ****/
			map.put("FullAWBNo", cust.data("FullAWBNo4"));
			map.put("scc",cust.data("SCC").split(",")[0]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[0]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true); 
			cust.closeTab("MSG005", "List Message");


			/**** OPR339 - Security & Screening  AWB1****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo1","CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.switchToFrame("contentFrame","OPR339");


			/**** OPR339 - Security & Screening AWB2 ****/

			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			OPR339.listAWB("AWBNo2","CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.switchToFrame("contentFrame","OPR339");

			/**** OPR339 - Security & Screening AWB2 ****/

			cust.setPropertyValue("AWBNo", cust.data("AWBNo3"),proppath);
			OPR339.listAWB("AWBNo3","CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.switchToFrame("contentFrame","OPR339");


			/**** OPR339 - Security & Screening AWB2 ****/

			cust.setPropertyValue("AWBNo", cust.data("AWBNo4"),proppath);
			OPR339.listAWB("AWBNo3","CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/***** OPR026 - Execute AWB1  ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecute();
			OPR026.clickClearButton();

			/***** OPR026 - Execute AWB2 ****/


			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			OPR026.asIsExecute();
			OPR026.clickClearButton();

			/***** OPR026 - Execute AWB3 ****/


			OPR026.listAWB("AWBNo3", "CarrierNumericCode");
			OPR026.asIsExecute();
			OPR026.clickClearButton();

			/***** OPR026 - Execute AWB4 ****/
			OPR026.listAWB("AWBNo4", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR335 -Goods Acceptance ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.dataload_clear();

			/**** OPR335 -Goods Acceptance ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.dataload_clear();

			/**** OPR335 -Goods Acceptance ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo3"),proppath);
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.dataload_clear();



			/**** OPR335 -Goods Acceptance ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo4"),proppath);
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");



			/*****OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			cust.waitForSync(1);
			OPR344.addNewULDWithAWBAndContour("UldNum","0","CarrierNumericCode","AWBNo1","Pieces","Weight","Contour");
			OPR344.addAWBstoExistingULDwithPcsWeight("UldNum","CarrierNumericCode","AWBNo2","Pieces","Weight");
			String uldNum1=cust.create_uld_number_cart("UldType1");
			map.put("UldNum1", uldNum1);
			OPR344.addBarrow("UldNum1",cust.data("Transit"));
			cust.waitForSync(6);
			OPR344.clickEditULDdetailsByJS("UldNum1");
			OPR344.addAWBDetails("prop~CarrierNumericCode", "AWBNo4","Pieces","Weight");
			OPR344.clickBuildUpComplete("UldNum");
			map.put("ActualWeight", cust.data("actwght"));
			OPR344.clickBuildUpComplete("UldNum1");
			map.put("ActualWeight2", cust.data("actwght"));
			cust.closeTab("OPR344", "Export Manifest");
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			//Login to Cafeed
			String[] cafeed = libr.getApplicationParams("cafeed");
			driver.get(cafeed[0]);

			cust.loginToCafeed(cafeed[1], cafeed[2]);


			cfd.clickSearchFlightLink();
			cfd.listFlightDetails("FullFlightNo", "Date");


			int[] col = {15};
			int[] col1 = {16};
			/***Verifying the pieces and weight of the uld having 2 awbs***/

			String[] awb1pcs= {"20"};
			String[] awb1wght= {cust.data("ActualWeight")};
			String[] awb2pcs= {"10"};
			String[] awb2wght= {cust.data("ActualWeight2")};


			cfd.verifyULDDetails(col,awb1pcs,cust.data("UldNum"));

			cfd.verifyULDDetails(col1,awb1wght,cust.data("UldNum"));
			cfd.verifyULDDetails(col,awb2pcs,cust.data("UldNum1"));

			cfd.verifyULDDetails(col1,awb2wght,cust.data("UldNum1"));
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG

			driver.get(iCargo[0]);

			cust.loginICargoSTG(iCargo[1], iCargo[2]);


			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			/*****OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			map.put("sccVal1",cust.data("SCC").split(",")[0]);
			OPR344.clickEditULDdetails("UldNum");
			OPR344.acceptAlertPopUp("val~The ULD is build-up completed. Do you want to proceed?");
			cust.waitForSync(5);
			OPR344.ClickCloseaddNewULDWithAWB();
			OPR344.expandULDs();
			String pmkey=cust.data("UldNum")+cust.data("CarrierNumericCode")+cust.data("AWBNo1")+cust.data("val~11");
			map.put("pkey", pmkey);
			OPR344.selectAwbNumberByJS("pkey");
			OPR344.offloadAwb("sccVal1");
			cust.waitForSync(5);
			String pmkey1=cust.data("UldNum")+cust.data("CarrierNumericCode")+cust.data("AWBNo2")+cust.data("val~11");
			map.put("pkey1", pmkey1);
			map.put("sccVal1",cust.data("SCC").split(",")[1]);
			OPR344.selectAwbNumberByJS("pkey1");
			OPR344.offloadAwb("sccVal1");
			OPR344.clickLyingList();
			OPR344.applyFilter("prop~CarrierNumericCode","AWBNo3");
			cust.waitForSync(2);
			OPR344.selectAWBcheckboxFromLyingList();
			OPR344.selectULD("UldNum");
			cust.waitForSync(6);
			OPR344.expandULDs();
			OPR344.verifyULDInAssignedShipment("UldNum",true);

			OPR344.clickBuildUpComplete("UldNum");
			map.put("ActualWeight", cust.data("actwght"));
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			//Login to Cafeed

			driver.get(cafeed[0]);

			cust.loginToCafeed(cafeed[1], cafeed[2]);


			cfd.clickSearchFlightLink();
			cfd.listFlightDetails("FullFlightNo", "Date");

			int[] col2 = {15};
			int[] col3 = {16};


			/***Verifying the pieces and weight of the uld having 1 awbs***/
			String[] awb1Pcs1= {"10"};
			String[] awb1wght1= {cust.data("ActualWeight")};

			cfd.verifyULDDetails(col2,awb1Pcs1,cust.data("UldNum"));
			cfd.verifyULDDetails(col3,awb1wght1,cust.data("UldNum"));
			libr.quitBrowser();


		} catch (Exception e) {
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
