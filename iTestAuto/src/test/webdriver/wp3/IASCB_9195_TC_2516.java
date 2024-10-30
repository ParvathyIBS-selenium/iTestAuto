package wp3;

/***Remaining build up time indication in colour code against the build up task while < 120  mins and it should be in red color***/

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
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;
import screens.TasksListExportBuildUp;


@Test
public class IASCB_9195_TC_2516 extends BaseSetup
{
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
	public GoodsAcceptanceHHT gahht;
	public Cgocxml Cgocxml;
	public BuildupPlanning_ADD004 ADD004;
	public TasksListExportBuildUp expbuildup;
	public MaintainOperationalFlight_FLT003 FLT003;
	public Cgomon Cgomon;
	
	
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "wp3";


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
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		expbuildup= new TasksListExportBuildUp(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_2516")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2516")
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
			Thread.sleep(2300);	

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
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			// creating flight number
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNumber", cust.data("carrierCode")+cust.data("prop~flightNo"),proppath);
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			System.out.println(startDate);
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");		
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMMM",0, "DAY", ""));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());



			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			map.put("awbNumber1", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			map.put("FullAWBNumber1", cust.data("prop~CarrierNumericCode") + cust.data("prop~AWBNo")+"001");
			map.put("FullAWBNo1", cust.data("awbNumber1"));
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
			map.put("FullAWBNumber2", cust.data("prop~CarrierNumericCode") + cust.data("prop~AWBNo")+"001");
			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo", cust.data("awb2"));
			map.put("AWBNo2", cust.data("awb2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeCDG=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			System.out.println(currtimeCDG);
			String STD=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","MINUTE",120);
			String closuretime=cust.timeCalculation(STD.split(" ")[1], "HH:mm","MINUTE",-30);
			
			System.out.println(closuretime);
			map.put("ClosureTime",closuretime);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(STD.split(" ")[1]);
			map.put("STDTime", STD.split(" ")[1]);
			String STA=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","MINUTE",180);
			System.out.println(STA.split(" ")[1]);
			map.put("STATime", STA.split(" ")[1]);
			map.put("STDDate", STD.split(" ")[0]);
			map.put("STADate", STA.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");			FLT003.clickSecondCheckbox();

			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");


			/*** Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFSU-BKD - awb 1**/
			map.put("awbNumber", cust.data("awbNumber1"));
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFSU-BKD - awb2**/
			map.put("awbNumber", cust.data("awbNumber2"));
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.sendMessageCgoCXML("ICARGO");



			/** XFBL Message loading  AWBs**/

			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
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
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 2 **/

			map.put("FullAWBNum", cust.data("awbNumber2"));

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
			Thread.sleep(2300);

			cust.switchRole("Origin", "Origin", "RoleGroup");


			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo1", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.checkSecurityDataReviewed();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.checkSecurityDataReviewed();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/***** OPR026 - Capture AWB ****/
			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			libr.quitBrowser();
			
			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			//Login to "CGOMON"
			String[] cgomon = libr.getApplicationParams("cgomon");
			driver.get(cgomon[0]); // Enters URL
			cust.loginToCgomon(cgomon[1], cgomon[2]);

			//Verifying Inbound Message
			Cgomon.clickInboundMessage();
			map.put("awbNumber", cust.data("prop~CarrierNumericCode")+"-"+cust.data("AWBNo1"));
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFWB", "ICARGO");

			//Verifying Outbound Message
			Cgomon.clickOutboundMessage();
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFWB", "PELICAN");
			
			
			//Verifying Inbound Message
			Cgomon.clickInboundMessage();
			Cgomon.cleanDetails();
			map.put("awbNumber", cust.data("prop~CarrierNumericCode")+"-"+cust.data("AWBNo2"));
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFWB", "ICARGO");

			//Verifying Outbound Message
			Cgomon.clickOutboundMessage();
			Cgomon.cleanDetails();
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFWB", "PELICAN");
			libr.quitBrowser();


			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");



			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);



			/*** HHT - ACCEPTANCE****/

			gahht.selectHandlingAreaAndClickDone();
			gahht.invokeAcceptanceScreen();

			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo1"));
			gahht.enterValue("awbNumber");
			gahht.verifyStatedPiecesWeight("Pieces", "Weight");
			gahht.verifyFlightDetails("carrierCode", "prop~flightNo");
			gahht.verifySCC("FullAWBNo1","SCC"); 
			gahht.selectSCCValue("SCC");
			gahht.clickSCCOK();
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "Location");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");


			/*** HHT - ACCEPTANCE****/

			gahht.invokeAcceptanceScreen();

			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo2"));
			gahht.enterValue("awbNumber");
			gahht.verifyStatedPiecesWeight("Pieces", "Weight");
			gahht.verifyFlightDetails("carrierCode", "prop~flightNo");
			gahht.verifySCC("FullAWBNo2","SCC"); 
			gahht.selectSCCValue("SCC");
			gahht.clickSCCOK();
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "Location");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");
			libr.quitApp();
			

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

            // switch role
			cust.switchRole("Origin", "Origin", "RoleGroup");
			
			
			/*******Verify FSU-RCS message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCS=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo1");
			int verfColsRCS[]={9};
			String[] actVerfValuesRCS={"Sent"};
			String pmKeyRCS1=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo2");
			int verfColsRCS1[]={9};
			String[] actVerfValuesRCS1={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-RCS",false);
			MSG005.verifyMessageDetails(verfColsRCS1, actVerfValuesRCS1, pmKeyRCS1,"val~XFSU-RCS",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
		

			
			//AWB1
			/****OPR355 - Loose Acceptance****/

			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"), proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces","Weight","Volume","CommodityCode");
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			//AWB2
			/****OPR355 - Loose Acceptance****/

			cust.setPropertyValue("AWBNo2", cust.data("AWBNo2"), proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo2", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces","Weight","Volume","CommodityCode");
			OPR335.verificationOfRFCStatus();
			libr.waitForSync(20);
			cust.closeTab("OPR335", "Goods Acceptance");

			/*****ADD004 - Build Up planning****/			
			cust.searchScreen("ADD004", "Buildup Planning");
			ADD004.listFlight("carrierCode","FlightNo","StartDate");

			ADD004.verifyShipmentInLoadPlan("AWBNo1");
			ADD004.selectULD("AWBNo1");
			ADD004.clickAllocate();
			ADD004.clickSaveAllocation();
			cust.closeTab("ADD004", "Buildup Planning");

			/*****ADD004 - Build Up planning****/	
			cust.searchScreen("ADD004", "Buildup Planning");
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			ADD004.verifyShipmentInLoadPlan("AWBNo");
			ADD004.selectULD("AWBNo2");
			ADD004.clickAllocate();	
			ADD004.selectAllocationType("Specific");
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			ADD004.enterULDNum("UldNum");
			ADD004.clickSaveAllocation();
			cust.closeTab("ADD004", "Buildup Planning");
			libr.quitBrowser();

			/***Launch emulator -Export Build Up **/
			libr.launchExportBuildUpApp("exportbuildup-app-release");		

			//Login in to Export BuildUp App
			String [] expbuild=libr.getApplicationParams("hht");	
			cust.loginExportBuildUp(expbuild[0], expbuild[1]);
			map.put("FlightNum", cust.data("carrierCode") + " " + cust.data("FlightNo"));
		
          /***Verifying the color and remaining time for the flight closure for the AWB1***/
			
			expbuildup.verifytaskCreatedwrtAWB("AWBNo1","FlightNum");
			String currtimeCDG1=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			System.out.println(currtimeCDG1);
			map.put("curenttime", currtimeCDG1.split(" ")[1]);
		    expbuildup.verifytime(cust.data("curenttime"),cust.data("ClosureTime"),"FlightNo");
			expbuildup.verifyColour("Red","FlightNo");
			
			  /***Verifying the color and remaining time for the flight closure for the AWB2***/
		
			expbuildup.verifytaskCreatedwrtAWB("AWBNo2","FlightNum");
		    String currtimeCDG2=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			System.out.println(currtimeCDG2);
			map.put("curenttime", currtimeCDG2.split(" ")[1]);
		    expbuildup.verifytime(cust.data("curenttime"),cust.data("ClosureTime"),"FlightNo");
			expbuildup.verifyColour("Red","FlightNo");
			libr.quitApp();


			

		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}

	
	

