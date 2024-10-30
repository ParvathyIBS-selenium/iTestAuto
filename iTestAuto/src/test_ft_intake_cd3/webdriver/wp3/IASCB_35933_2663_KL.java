package wp3;

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
import screens.BuildUpHHT;
import screens.BuildupPlanning_ADD004;
import screens.Cafeed;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptanceHHT;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
/** Test ID : 2663 - TC_05_Big reference number in scanner screen. **/

/**Test case comments**/
public class IASCB_35933_2663_KL extends BaseSetup {
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
	public GoodsAcceptanceHHT gahht;
	public SecurityAndScreening_OPR339 OPR339;
	public BuildUpHHT buhht;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public ExportManifest_OPR344 OPR344;
	public Cafeed cfd;
	public BuildupPlanning_ADD004 ADD004;
	public MaintainOperationalFlight_FLT003 FLT003;
	public MaintainFlightSchedule_FLT005 FLT005;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "wp3_IASCB_35933";

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
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		cfd= new Cafeed(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
	

	}

	@DataProvider(name = "TC_2663")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2663")
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

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



			
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());


			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("carrierCode","FlightNo", "StartDate","flightType");
			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT003.enterLegCapacityDetails("ATD_Local","ATA_Local", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
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
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/**** XFSU-BKD Message loading AWB1 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo1"));
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFSU-BKD Message loading AWB1 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo2"));
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("FullAWBNo1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
							cust.data("FullAWBNo2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc")};
			String scc[] = { cust.data("SCC"), cust.data("SCC")};

			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination")};
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFWB Message loading AWB1 ****/
			map.put("FullAWBNo", cust.data("FullAWBNo1"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFWB Message loading AWB2 ****/
			map.put("FullAWBNo", cust.data("FullAWBNo2"));
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


			/**** OPR339 - Security & Screening  AWB1****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo1","CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");


			/**** OPR339 - Security & Screening AWB2 ****/

			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo2","CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/***** OPR026 - Execute AWB1  ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/***** OPR026 - Execute AWB2 ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");

			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht2");
			cust.loginHHT(hht[0], hht[1]);


			/*** HHT - ACCEPTANCE AWB1****/

			gahht.invokeAcceptanceScreen();
			gahht.enterValue("FullAWBNo1");
			String BigRefNo1=cust.create_randomAlphaNumericcharacters("BIG",6);
			map.put("BigRefNo1", BigRefNo1);
			gahht.verifyStatedPiecesWeight("Pieces", "Weight");
			//verify flight details
			gahht.verifyFlightDetails("carrierCode","FlightNo","FullAWBNo1");
			map.put("sccVal",cust.data("SCC")+" + 2");
			gahht.verifySCC("FullAWBNo1","sccVal"); 	
			gahht.selectSCCValue("SCC");
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "Location");
			gahht.enterBigReferenceNumber("BigRefNo1");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");

			/*** HHT - ACCEPTANCE AWB2****/

			gahht.invokeAcceptanceScreen();
			gahht.enterValue("FullAWBNo2");
			String BigRefNo2=cust.create_randomAlphaNumericcharacters("BIG",6);
			map.put("BigRefNo2", BigRefNo2);
			gahht.verifyStatedPiecesWeight("Pieces", "Weight");
			//verify flight details
			gahht.verifyFlightDetails("carrierCode", "FlightNo","FullAWBNo2");
			map.put("sccVal",cust.data("SCC")+" + 2");
			gahht.verifySCC("FullAWBNo2","sccVal");   
			gahht.selectSCCValue("SCC");
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "Location");
			gahht.enterBigReferenceNumber("BigRefNo2");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");

			/*** HHT - Build Up AWB1****/

			buhht.invokeBuildUpScreen();
			String uldNo=cust.create_uld_number("UldType", "prop~flight_code_KL");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code_KL", "FlightNo","currentDay");
			buhht.enterAWBDetailsWithoutPcsWgt("FullAWBNo1");
			buhht.verifyBigReferenceNumber("BigRefNo1");
			buhht.enterShipmentDetails("Pieces", "Weight");
			libr.quitApp();

			/*****ADD004 - Build Up planning****/

			cust.searchScreen("ADD004","Buildup Planning");
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			ADD004.verifyShipmentInLoadPlan("AWBNo2");
			ADD004.selectULD("AWBNo2");
			ADD004.clickAllocate();	
			ADD004.clickSaveAllocation();
			cust.closeTab("ADD004","Buildup Planning");

			/**Export manifest screen***/

			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			OPR344.clickShipemntFromPlannedSection("AWBNo2");
			OPR344.selectULD("UldNum");
			OPR344.clickBuildUpComplete();
			OPR344.closeFlight();
			cust.closeTab("OPR344", "Export manifest");

			/*** MSG005-Verify XFSU-RCS message -AWB 1 ***/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCS = cust.data("prop~CarrierNumericCodeAMS") + " - " + cust.data("AWBNo1");		
			int verfColsRCS[] = { 9 };
			String[] actVerfValuesRCS = { "Sent" };
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS, "val~XFSU-RCS", false);
			libr.waitForSync(1);

			MSG005.closeTab("MSG005", "MSG005 - List Messages");


			/*** MSG005-Verify XFSU-RCS message -AWB 2 ***/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCS2 = cust.data("prop~CarrierNumericCodeAMS") + " - " + cust.data("AWBNo2");
			int verfColsRCS2[] = { 9 };
			String[] actVerfValuesRCS2 = { "Sent" };
			MSG005.verifyMessageDetails(verfColsRCS2, actVerfValuesRCS2, pmKeyRCS2, "val~XFSU-RCS", false);
			libr.waitForSync(1);
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
			
			/**Verfying whether only one uld getting displayed for the listed flight***/

			cfd.verifyULDCount(1);
			String[]awbs={cust.data("FullAWBNo1"),cust.data("FullAWBNo2")};
			cfd.verifyAwbDetailsInsideULD("UldNum", awbs);
			cfd.clickAWBInsideULD("UldNum","FullAWBNo1");
		    cfd.verifyBigrefNo("BigRefNo1");
			cfd.clickAWBInsideULD("UldNum","FullAWBNo2");
		    cfd.verifyBigrefNo("BigRefNo2");
			libr.quitBrowser();
			


		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}
