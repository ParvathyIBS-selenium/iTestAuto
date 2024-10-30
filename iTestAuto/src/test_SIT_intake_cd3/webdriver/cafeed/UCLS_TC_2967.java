package cafeed;

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
import screens.CaptureDGDetails_OPR350;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;

/** Test ID : TC_11_Verify ULDdetails sent to CAFEED  after unassignment of DG shipment AWBs from ULD	**/



public class UCLS_TC_2967 extends BaseSetup {

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
	public Cgocxml Cgocxml;
	public ExportManifest_OPR344 OPR344;
	public Cafeed cfd;
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainOperationalFlight_FLT003 FLT003;
	public MaintainFlightSchedule_FLT005 FLT005;
	public CaptureDGDetails_OPR350 OPR350;
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
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		cfd= new Cafeed(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR350 = new CaptureDGDetails_OPR350(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2967")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2967")
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

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "SIN"));

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("ATA_Local",cust.data("ATA_Local1"));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());


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
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			// Checking AWB is fresh or Not--AWB 2
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo2", cust.data("prop~FullAWBNo"));
			map.put("AWBNo2", cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("AWBNo2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Checking AWB is fresh or Not--AWB 3
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo3", cust.data("prop~FullAWBNo"));
			map.put("AWBNo3", cust.data("prop~AWBNo"));
			map.put("awb3", cust.data("AWBNo3"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
							cust.data("FullAWBNo2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
									cust.data("FullAWBNo3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
											+ cust.data("Volume") + ";" + cust.data("ShipmentDesc")};
			String scc[] = {cust.data("SCC"), cust.data("SCC"),cust.data("SCC")};

			String routing[] = { cust.data("Origin") + ";" + cust.data("Transit"),cust.data("Origin") + ";" + cust.data("Transit"),cust.data("Origin") + ";" + cust.data("Destination")};
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFWB Message loading AWB1 ****/
			map.put("FullAWBNum", cust.data("FullAWBNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFWB Message loading AWB2 ****/
			map.put("FullAWBNum", cust.data("FullAWBNo2"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/**** XFWB Message loading AWB3 ****/
			map.put("FullAWBNum", cust.data("FullAWBNo3"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB1", "MessageParamXFWB1");
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

			cust.setPropertyValue("AWBNo", cust.data("awb1"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("awb1", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/

			cust.setPropertyValue("AWBNo", cust.data("awb2"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("awb2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/

			cust.setPropertyValue("AWBNo", cust.data("awb3"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("awb3", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo3", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/***** OPR335 - Goods Acceptance ****/

			cust.setPropertyValue("AWBNo", cust.data("awb1"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");



			/***** OPR335 - Goods Acceptance ****/

			cust.setPropertyValue("AWBNo", cust.data("awb2"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");



			/***** OPR335 - Goods Acceptance ****/

			cust.setPropertyValue("AWBNo", cust.data("awb3"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");


			


			/*****OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addULDWithoutAWB("UldNum", "0");
			libr.waitForSync(3);
			OPR344.verifyShipmentInPlannedSection("AWBNo");
			OPR344.clickShipemntFromPlannedSection("AWBNo");
			OPR344.selectULD("UldNum");
			libr.waitForSync(4);
			OPR344.verifyShipmentInPlannedSection("AWBNo2");
			OPR344.clickShipemntFromPlannedSection("AWBNo2");
			OPR344.selectULD("UldNum");
			libr.waitForSync(4);
			OPR344.verifyShipmentInPlannedSection("AWBNo3");
			OPR344.clickShipemntFromPlannedSection("AWBNo3");
			OPR344.selectULD("UldNum");
			libr.waitForSync(5);
			OPR344.clickBuildUpComplete();
			cust.closeTab("OPR344", "Export Manifest");
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

			String[]awbNumbers={cust.data("FullAWBNo"),cust.data("FullAWBNo2"),cust.data("FullAWBNo3")};

			cfd.verifyAwbDetailsInsideULD("UldNum", awbNumbers);
			cfd.verifyAwbDetailsInsideULD("UldNum", awbNumbers);
			cfd.verifyAwbDetailsInsideULD("UldNum", awbNumbers);

			cfd.clickAWBInsideULD("UldNum","FullAWBNo");
			cfd.verifyDGDetails("UNID","PI","ShippingName");

			cfd.clickAWBInsideULD("UldNum","FullAWBNo2");
			cfd.verifyDGDetails("UNID","PI","ShippingName");

			cfd.clickAWBInsideULD("UldNum","FullAWBNo3");
			cfd.verifyDGDetails("UNID","PI","ShippingName");
			
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
			


			/*****OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			OPR344.expandULDs();
			String pmkey=cust.data("UldNum")+cust.data("CarrierNumericCode")+cust.data("AWBNo2")+cust.data("val~11");
			map.put("pkey", pmkey);
			OPR344.selectAwbNumberByJS("pkey");
			OPR344.offloadAwb("SCC");
			libr.waitForSync(6);
			OPR344.verifyShipmentInPlannedSection("AWBNo2");
			OPR344.clickEditULDdetailsByJS("UldNum");
			OPR344.acceptAlertPopUp("val~The ULD is build-up completed. Do you want to reopen and proceed?");
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight2", "Occupancy");
			OPR344.clickBuildUpComplete();
			cust.closeTab("OPR344", "Export Manifest");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			//Login to Cafeed
			driver.get(cafeed[0]);
			Thread.sleep(2000);
			cust.loginToCafeed(cafeed[1], cafeed[2]);
			Thread.sleep(2000);

			cfd.clickSearchFlightLink();
			cfd.listFlightDetails("FullFlightNo", "Date");

			

			String[]awbNumbersAfterUnassigning={cust.data("FullAWBNo"),cust.data("FullAWBNo3")};

			cfd.verifyAwbDetailsInsideULD("UldNum", awbNumbersAfterUnassigning);
			cfd.verifyAwbDetailsInsideULD("UldNum", awbNumbersAfterUnassigning);

			String[]unassignedAwb={cust.data("FullAWBNo2")};

			cfd.verifyAwbNotPresentInsideULD("UldNum", unassignedAwb);

			libr.quitBrowser();


		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}
