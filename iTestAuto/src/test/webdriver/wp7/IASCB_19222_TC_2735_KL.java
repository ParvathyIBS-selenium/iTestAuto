package wp7;

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
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.ExportPlanningProgress_ADD007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;

/**
 * 
 *  TC_10_Verify manifest completion time

 *
 *
 */

public class IASCB_19222_TC_2735_KL extends BaseSetup {

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
	public ExportPlanningProgress_ADD007 ADD007;
	public MaintainFlightSchedule_FLT005 FLT005;
	public BuildupPlanning_ADD004 ADD004;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "wp7";

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
		ADD007=new ExportPlanningProgress_ADD007(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);


	}

	@DataProvider(name = "TC_2735")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2735")
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

//			//Switch Role
//			cust.switchRole("Origin", "FCTL", "RoleGroup");
//
//			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			String nextDate= cust.createDateFormat("dd-MMM-YYYY", 1, "DAY", "");
			map.put("NextDate", nextDate);
				map.put("StartDate", startDate);
				map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM",0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

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
			map.put("ConsigneeCountryName",WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

//			//Checking AWB is fresh or Not 
//			cust.searchScreen("OPR026", "Capture AWB");
//			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
//			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

//			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/
//			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
//			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
//			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
//			libr.quitBrowser();
//
//			//Relaunch browser
//			driver=libr.relaunchBrowser("chrome");
//			/****************** MERCURY *********************/
//			// Login to "MERCURY"
//			String[] mercury = libr.getApplicationParams("mercury");
//			driver.get(mercury[0]); // Enters URL
//			cust.loginToMercury(mercury[1], mercury[2]);
//
//			map.put("flightNo", cust.data("FullFlightNo"));
//			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
//			mercuryScreen.clickSendMessage();
//			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
//			mercuryScreen.sendMessageInMercury();
//			mercuryScreen.verifyMsgStatus("SSM");
//			libr.quitBrowser();
//
//			// Relaunch browser
//			driver = libr.relaunchBrowser("chrome");
//			/*** Login to cgocxml **********/
//			String[] cgocxml = libr.getApplicationParams("cgocxml");
//			driver.get(cgocxml[0]); // Enters URL
//			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
//
//			/** XFBL Message loading **/
//			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
//			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
//			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
//					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
//			String scc[] = { cust.data("SCC") };
//			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
//			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
//			Cgocxml.sendMessageCgoCXML("ICARGO");			
//
//			/** XFWB Message loading  **/
//			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
//			Cgocxml.sendMessageCgoCXML("ICARGO");
//			libr.quitBrowser();
//
//			// Relaunch browser
//			driver = libr.relaunchBrowser("chrome");
//			// Re-Login to iCargo STG
//			driver.get(iCargo[0]);
//			Thread.sleep(2000);
//			cust.loginICargoSTG(iCargo[1], iCargo[2]);
//			Thread.sleep(2000);
//
//			//Switch Role
//			cust.switchRole("Origin", "FCTL", "RoleGroup");

//			/**** OPR339 - Security & Screening ****/
//			cust.searchScreen("OPR339", "Security and Screening");
//			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");
//			OPR339.clickYesButton();
//			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
//			OPR339.saveSecurityDetails();
//			cust.closeTab("OPR339", "Security & Screening");

//			/***** OPR026 - Execute AWB ****/
//			cust.searchScreen("OPR026", "Capture AWB");
//			OPR026.listAWB("AWBNo", "prop~CarrierNumericCodeAMS");
//			OPR026.asIsExecute();
//			cust.closeTab("OPR026", "Capture AWB");
//
//			/**** OPR335 -Goods Acceptance****/
//			cust.searchScreen("OPR335", "Goods Acceptance");
//			cust.listAWB("AWBNo", "prop~CarrierNumericCodeAMS", "Goods Acceptance");
//			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
//			OPR335.verifyAWBDetails(cust.data("SCC"));
//			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
//			OPR335.addLooseShipment();
//			OPR335.allPartsRecieved();
//			OPR335.saveAcceptance();
//			cust.closeTab("OPR335", "Goods Acceptance");
//
//			/*****ADD004 - Build Up planning****/
//			cust.searchScreen("ADD004","Buildup Planning");
//			libr.waitForSync(10);
//			ADD004.listFlight("carrierCode","FlightNo","StartDate");
//			ADD004.verifyShipmentInLoadPlan("prop~AWBNo");
//			ADD004.selectULD("prop~AWBNo");
//			ADD004.clickAllocate();	
//			ADD004.clickSaveAllocation();
//			cust.closeTab("ADD004","Buildup Planning");	
//
//			/**** OPR344 - Export manifest****/
//			cust.searchScreen("OPR344", "Export manifest");
//			OPR344.listFlight("prop~flight_code_KL", "FlightNo","StartDate");
//			String uldNum=cust.create_uld_number("UldType", "carrierCode");
//			map.put("UldNum", uldNum);
//			excelRead.writeDataInExcel(map, path1, sheetName, testName);
//			OPR344.addNewULDWithAWB("UldNum","0","prop~CarrierNumericCodeAMS","AWBNo","Pieces","Weight");
//			OPR344.verifyFlightStatus("val~Built Up");
//			cust.closeTab("OPR344", "Export Manifest");
//
//			/** ADD007 - Export Planning Progress screen **/
//			cust.searchScreen("ADD007", "Export Planning Progress");
//			ADD007.EnterFlightDetailsWithTime("carrierCode","prop~flightNo","CurrDate", "NextDate");
//			ADD007.clickList();
//			ADD007.verifyFlightIsDisplayed("FlightNo");
//			String[] colName={"Manifest Status","Buildup Progress"};
//			ADD007.verifyColumn(colName);
//			ADD007.verifyManifestSatusIsEmpty();
//			cust.closeTab("ADD007", "Export Planning Progress");
//
//			/**** OPR344 - Export manifest****/
//			cust.searchScreen("OPR344", "Export manifest");
//			OPR344.listFlight("prop~flight_code_KL", "FlightNo","StartDate");
//			OPR344.clickManifest();
//			OPR344.printManifestOk();
//			cust.printAndVerifyReport("val~CARGO MANIFEST", "OPR344","Flight Number & Date"+"\n" + cust.data("carrierCode")+" "+cust.data("prop~flightNo"),
//					cust.data("UldNum"));
//			OPR344.printManifestClose();
//			OPR344.verifyFlightStatus("val~Manifested");
//			cust.closeTab("OPR344", "Export Manifest");

			/*** MSG005-Verify XFSU-MAN message  ***/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Manifest Details");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyMAN = cust.data("prop~CarrierNumericCodeAMS") + " - " + cust.data("AWBNo");
			int verfColsMAN[] = { 9 };
			String[] actVerfValuesMAN = { "Sent" };
			MSG005.verifyMessageDetails(verfColsMAN, actVerfValuesMAN, pmKeyMAN, "val~XFSU-MAN", false);
			libr.waitForSync(1);
			String messagetime=MSG005.retrieveMsgSentTime("AWBNo");
			System.out.println(messagetime);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/** ADD007 - Export Planning Progress screen **/
			cust.searchScreen("ADD007", "Export Planning Progress");
			ADD007.EnterFlightDetailsWithTime("carrierCode","prop~flightNo","CurrDate", "NextDate");
			ADD007.clickList();
			ADD007.clickManifestStatusIconAndVerifyTooltip("Completed on "+cust.data("CurrDate").toUpperCase()+","+messagetime.substring(0,5));
			cust.closeTab("ADD007", "Export Planning Progress");


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}