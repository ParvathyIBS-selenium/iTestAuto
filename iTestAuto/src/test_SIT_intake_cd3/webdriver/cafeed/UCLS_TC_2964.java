package cafeed;

import java.util.Map;

import org.testng.Assert;
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
import screens.CaptureAWB_OPR026;
import screens.CaptureDGDetails_OPR350;
import screens.Cgocxml;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.Mercury;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;
import screens.AWBClearance_OPR023;
import screens.BuildUpHHT;
import screens.Cafeed;
import screens.ExportManifest_OPR344;




/** testid_2964_TC_08_Verify flight manifest details sent to CAFEED after manual flight closure for build up]  **/



public class UCLS_TC_2964 extends BaseSetup {

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
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainFlightSchedule_FLT005 FLT005;
	public AWBClearance_OPR023 OPR023;
	public ExportManifest_OPR344 OPR344;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public Cafeed cfd;
	public BuildUpHHT buhht;
	public CaptureDGDetails_OPR350 OPR350;
	public MaintainOperationalFlight_FLT003 FLT003;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "cafeed";

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
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR350 = new CaptureDGDetails_OPR350(driver, excelreadwrite, xls_Read);
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
		cfd= new Cafeed(driver, excelreadwrite, xls_Read);
		OPR023= new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_2964")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2964")
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
			map.put("Destination", cust.data("Transit"));
			cfd.createnewFlightInCafeedwindow("prop~flightNumber","Date","FullFlightNumber","StartDate");
			map.put("FullFlightNo",cust.data("prop~flightNumber"));
			map.put("FlightNo",cust.data("prop~flightNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



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

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));


			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath,"SIN"));

			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("EndDate", endDate);

			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM",7, "DAY", ""));
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("Destination", cust.data("Destination1"));




			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("FlightNo", "StartDate");
			FLT003.enterFlightDetails("Route1", "scheduleType", "FCTL", "Office", "flightType");
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
			map.put("AWBNo", cust.data("awb1"));
			map.put("AWBNo1", cust.data("awb1"));
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

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("awbNumber3", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb3", cust.data("prop~AWBNo"));
			map.put("FullAWBNo3", cust.data("awbNumber3"));
			map.put("AWBNo3", cust.data("awb3"));
			map.put("AWBNo3", cust.data("awb3"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			libr.quitBrowser();



			//	Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/** XFBL Message loading  AWBs for first flight**/

			map.put("FullFlightNumber", cust.data("FullFlightNo"));
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" +cust.data("ShipmentDesc").split(",")[0],
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[0],cust.data("awbNumber3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
											+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[1]};
			String scc[] = {  cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1] };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Transit"),cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Transit") };

			cust.createXFBLMessage("XFBL_2",shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 1 **/

			map.put("FullAWBNo", cust.data("awbNumber1"));
			map.put("scc",cust.data("SCC").split(",")[0]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[0]);


			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/*** XFZB LOADING for AWB 3***/
			map.put("FullAWBNo", cust.data("awbNumber1"));
			cust.createXMLMessage("MessageExcelAndSheetXFZB", "MessageParamXFZB");
			Cgocxml.sendMessageCgoCXML("ICARGO");	



			/** XFWB Message loading for AWB 1 THRU ULD **/


			map.put("awbnumber", cust.data("awbNumber2"));
			map.put("scc",cust.data("SCC").split(",")[0]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[0]);

			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB1", "MessageParamXFWB1");
			Cgocxml.sendMessageCgoCXML("ICARGO");



			/**intact ULD***/

			/** XFWB Message loading for AWB 3 **/

			map.put("FullAWBNo", cust.data("awbNumber3"));
			map.put("scc",cust.data("SCC").split(",")[1]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[1]);

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_WERNL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_WERNL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_WERNL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_WERNL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_WERNL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_WERNL"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_WERNL"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_WERNL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_WERNL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_WERNL"));

			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

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
			OPR339.listAWB("AWBNo3", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");


			/***** OPR026 - Execute AWB ****/
			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.clickHAWBDocFinalized();
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
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR335 -Goods Acceptance ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR335 -Goods Acceptance ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo3"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			String uldNo = cust.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR335.uldShipmentDetails("Pieces", "Weight", "Location", "UldNum", "Contour");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			OPR335.captureCheckSheet(true,"leakage");
			cust.closeTab("OPR335", "Goods Acceptance");



			/*****OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			OPR344.assignUldPlanningSection("UldNum");
			String uldNum1=cust.create_uld_number("UldType1", "carrierCode");
			map.put("UldNum1", uldNum1);
			OPR344.addBarrow("UldNum1","0");
			cust.waitForSync(6);
			OPR344.clickEditULDdetailsByJS("UldNum1");
			OPR344.addAWBDetailswithHAWB("prop~CarrierNumericCode", "AWBNo1", true,"Pieces","Weight");
			String uldNum2=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum2", uldNum2);
			OPR344.addNewULDWithAWB("UldNum2","0","CarrierNumericCode","AWBNo2","Pieces","Weight");
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			cust.waitForSync(5);
			OPR344.clickEditULDdetailsByJS("UldNum");
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight", "Occupancy");
			cust.waitForSync(5);
			OPR344.clickEditULDdetailsByJS("UldNum");
			OPR344.clickMoreUldDetails();
			OPR344.addbreakdowninstruction("Intact Unit");
			cust.waitForSync(5);
			OPR344.clickEditULDdetailsByJS("UldNum2");
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight", "Occupancy");
			cust.waitForSync(5);
			OPR344.clickEditULDdetailsByJS("UldNum2");
			OPR344.clickMoreUldDetails();
			OPR344.captureULDBreakdownDetails("Thru unit", "prop~flight_code", "FlightNo", "StartDate", "Destination");
			cust.waitForSync(5);
			OPR344.clickEditULDdetailsByJS("UldNum1");
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight", "Occupancy");
			cust.waitForSync(2);
			OPR344.closeFlight();
			cust.waitForSync(2);
			libr.quitBrowser();





			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			//Login to Cafeed
			String[] cafeed = libr.getApplicationParams("cafeed");
			driver.get(cafeed[0]);
			Thread.sleep(2000);
			cust.loginToCafeed(cafeed[1], cafeed[2]);
			Thread.sleep(2000);
			cfd.clickSearchFlightLink();
			cfd.listFlightDetails("FullFlightNo", "Date");

			int[] col = {21};

			


			String[] expValue= {cust.data("SCC").split(",")[0]};

			

			String s1="CMX"+cust.data("UldNum1").substring(3);
			map.put("uldcafeed", s1);

			cfd.verifyULDDetails(col,expValue,cust.data("UldNum2"));
			cfd.verifyULDDetails(col,expValue,cust.data("uldcafeed"));
		
			cfd.clickAWBInsideULD("UldNum","FullAWBNo3");
			cfd.verifyDGDetails("UNID","PI","ShippingName");




		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}
