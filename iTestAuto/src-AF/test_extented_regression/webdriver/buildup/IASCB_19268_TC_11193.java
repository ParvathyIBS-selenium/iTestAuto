package buildup;

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
import screens.BuildUpHHT;

import screens.Cafeed;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.DeadloadStatement_OPR063;

import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;

import screens.MaintainOperationalFlight_FLT003;

import screens.Mercury;
import screens.OffloadHHT;
import screens.SecurityAndScreening_OPR339;

/** TC_20_Verify Recalculation of Tall rigid to neutral loability status for rigid ratio update in built up volume - scanner**/


public class IASCB_19268_TC_11193 extends BaseSetup {

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
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public DeadloadStatement_OPR063 OPR063;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public BuildUpHHT buhht;
	public MaintainOperationalFlight_FLT003 FLT003;
	public Cafeed cfd;
	public OffloadHHT offloadhht;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Buildup.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "Buildup_FT";

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
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR063 = new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
		cfd= new Cafeed(driver, excelreadwrite, xls_Read);
		offloadhht = new OffloadHHT(driver, excelreadwrite, xls_Read);



	}

	@DataProvider(name = "TC_11193")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_11193")
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

			//Login to iCargo

			String [] iCargo=libr.getApplicationParams("iCargoSTG");
			driver = libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	

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


			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");

			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("FlightNo", "StartDate");
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




			map.put("awbNumber1", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum1", cust.data("CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));

			map.put("FullAWBNo1", cust.data("awbNumber1"));
			map.put("AWBNo1", cust.data("awb1"));

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2


			map.put("awbNumber2", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum2", cust.data("CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));

			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo2", cust.data("awb2"));

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber3


			map.put("awbNumber3", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum3", cust.data("CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb3", cust.data("prop~AWBNo"));

			map.put("FullAWBNo3", cust.data("awbNumber3"));
			map.put("AWBNo3", cust.data("awb3"));
			libr.quitBrowser();



			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/**** XFSU-BKD Message loading ****/

			map.put("FullAWBNo", cust.data("FullAWBNo1"));
			map.put("Vol", cust.data("Volume").split(",")[0]);
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFSU-BKD Message loading ****/

			map.put("FullAWBNo", cust.data("FullAWBNo2"));
			map.put("Vol", cust.data("Volume").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFSU-BKD Message loading ****/

			map.put("FullAWBNo", cust.data("FullAWBNo3"));
			map.put("Vol", cust.data("Volume").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume").split(",")[0] + ";" + cust.data("ShipmentDesc").split(",")[0],
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume").split(",")[1] + ";" + cust.data("ShipmentDesc").split(",")[1],cust.data("awbNumber3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
											+ cust.data("Volume").split(",")[2] + ";" + cust.data("ShipmentDesc").split(",")[2]};
			String scc[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1],cust.data("SCC").split(",")[2] };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFWB Message loading AWB1 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo1"));
			map.put("scc", cust.data("SCC").split(",")[0]);
			map.put("Shipmentdesc1",cust.data("ShipmentDesc").split(",")[0]);
			map.put("Vol", cust.data("Volume").split(",")[0]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFWB Message loading AWB2 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo2"));
			map.put("scc", cust.data("SCC").split(",")[1]);
			map.put("Shipmentdesc1",cust.data("ShipmentDesc").split(",")[1]);
			map.put("Vol", cust.data("Volume").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/**** XFWB Message loading AWB3 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo3"));
			map.put("scc", cust.data("SCC").split(",")[2]);
			map.put("Shipmentdesc1",cust.data("ShipmentDesc").split(",")[2]);
			map.put("Vol", cust.data("Volume").split(",")[2]);
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
			cust.switchToFrame("contentFrame","OPR339");

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			OPR339.listAWB("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.switchToFrame("contentFrame","OPR339");

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo3"),proppath);
			OPR339.listAWB("AWBNo3", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();


			/***** OPR026 - Capture AWB ****/
			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecute();
			OPR026.clickClearButton();

			/***** OPR026 - Execute AWB ****/
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			OPR026.asIsExecute();
			OPR026.clickClearButton();

			/***** OPR026 - Capture AWB ****/
			//Execute AWB
			OPR026.listAWB("AWBNo3", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/**** OPR335 -Goods Acceptance for AWB1 ****/


			cust.setPropertyValue("awbNo",cust.data("AWBNo1"),proppath);	

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("awbNo", "CarrierNumericCode", "Goods Acceptance");
			map.put("Vol", cust.data("Volume").split(",")[0]);
			OPR335.verifyAWBDetails("Pieces", "Weight", "Vol");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.verificationOfRFCStatus();
			OPR335.dataload_clear();

			/**** OPR335 -Goods Acceptance for AWB2 ****/

			cust.setPropertyValue("awbNo", cust.data("AWBNo2"),proppath);
			cust.listAWB("awbNo", "CarrierNumericCode", "Goods Acceptance");
			map.put("Vol", cust.data("Volume").split(",")[1]);
			OPR335.verifyAWBDetails("Pieces", "Weight", "Vol");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.verificationOfRFCStatus();
			OPR335.dataload_clear();

			/**** OPR335 -Goods Acceptance for AWB3 ****/

			cust.setPropertyValue("awbNo", cust.data("AWBNo3"),proppath);
			cust.listAWB("awbNo", "CarrierNumericCode", "Goods Acceptance");
			map.put("Vol", cust.data("Volume").split(",")[1]);
			OPR335.verifyAWBDetails("Pieces", "Weight", "Vol");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");



			/*****OPR344 - Export manifest****/

			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNo=cust.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			OPR344.addNewULDWithAWBAndContour("UldNum","0","CarrierNumericCode","AWBNo1","Pieces","Weight","Contour");
			cust.waitForSync(1);
			OPR344.clickEditULDdetailsByJS("UldNum");
			OPR344.addAWBstoExistingULDwithPcsWeight("UldNum","CarrierNumericCode","AWBNo2","Pieces","Weight");
			cust.waitForSync(2);
			OPR344.clickEditULDdetailsByJS("UldNum");
			OPR344.addAWBstoExistingULDwithPcsWeight("UldNum","CarrierNumericCode","AWBNo3","Pieces","Weight");
			cust.closeTab("OPR344", "Export Manifest");

			/** DEAD LOAD STATEMENT - OPR063 **/
			//Verify loadability status getting displayed as  Tall rigid

			cust.searchScreen("OPR063", "Dead load statement");
			OPR063.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			OPR063.selectULD(cust.data("UldNum"));
			OPR063.clickULDLoadingInstuctor();
			OPR063.verifyLoadabilityStatus(cust.data("LoadabilityStatus"));
			OPR063.ULDLoadingInstructionOK();
			cust.closeTab("OPR063", "Dead load statement");

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");		



			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - OFFLOAD****/

			offloadhht.invokeOffloadHHTScreen();
			map.put("awbNumber", cust.data("prop~CarrierNumericCode")+cust.data("AWBNo1"));
			offloadhht.enterValue("awbNumber");
			offloadhht.enterPieces("Pieces");
			offloadhht.selectOffloadReasons("Due Payload BXRB07");
			offloadhht.clickSave();
			cust.clickBack("Offload");
			cust.clickBack("Offload");


			/*** HHT - BUILDUP****/

			buhht.invokeBuildUpScreen();
			buhht.enterValue("UldNum");
			map.put("BuildupLoc", WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG"));
			buhht.enterBuildupLocation("BuildupLoc");
			map.put("LoadabilityStatus",cust.data("NewLoadabilityStatus").split(",")[0]);
			buhht.verifyLoadabilityStatus("LoadabilityStatus");
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteBtn();
			buhht.clickSaveCaptureChecksheet();
			buhht.clickTopUpNoOption();
			buhht.clickSaveForContour();
			libr.quitApp();


			/** DEAD LOAD STATEMENT - OPR063 **/
			//Verify loadability status getting displayed as  Neutral

			cust.searchScreen("OPR063", "Dead load statement");
			OPR063.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			OPR063.selectULD(cust.data("UldNum"));
			OPR063.clickULDLoadingInstuctor();
			OPR063.verifyLoadabilityStatus(cust.data("NewLoadabilityStatus").split(",")[0]);
			OPR063.ULDLoadingInstructionOK();
			cust.closeTab("OPR063", "Dead load statement");
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



			//Verify loadability status getting displayed as Neutral in cafeed
			int[] col = {20};


			String[] expValue= {cust.data("NewLoadabilityStatus").split(",")[1]};

			cfd.verifyULDDetails(col,expValue,cust.data("UldNum"));
			libr.quitBrowser();



		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		finally {
			try {
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
} 