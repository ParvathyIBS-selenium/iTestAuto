package buildup;

/**TC_18_Capture floating information for a pallet without capturing a physical or commercial link **/

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
import screens.Cgocxml;
import screens.DeadloadStatement_OPR063;
import screens.ListMessages_MSG005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;
import screens.BuildUpHHT;
import screens.Cafeed;
import screens.ExportManifest_OPR344;
import screens.GenerateNOTOC_OPR017;
import screens.GoodsAcceptanceHHT;

public class IASCB_29929_TC_7244 extends BaseSetup 

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
	public ExportManifest_OPR344 OPR344;
	public MaintainOperationalFlight_FLT003 FLT003;
	public DeadloadStatement_OPR063 OPR063;
	public Cgocxml Cgocxml;
	public GenerateNOTOC_OPR017 OPR017;
	public BuildUpHHT buhht;
	public GoodsAcceptanceHHT gahht;
	public Cafeed cfd;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Buildup.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		cfd= new Cafeed(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR017 = new GenerateNOTOC_OPR017(driver, excelreadwrite, xls_Read);
		OPR063 = new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_7244")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_7244")
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
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

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

			// AWBNumber1
			map.put("awbNumber1", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			map.put("FullAWBNo", cust.data("awbNumber1"));
			map.put("AWBNo1", cust.data("awb1"));

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2
			map.put("awbNumber2", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));
			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo2", cust.data("awb2"));

			libr.quitBrowser();



			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/** XFBL Message loading  AWBs**/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc")};
			String scc[] = { cust.data("SCC"), cust.data("SCC")};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination")};
			cust.createXFBLMessage("XFBL_2",shipment, scc, routing);
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
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
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


			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");



			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);



			/*** HHT - ACCEPTANCE****/

			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo1"));
			gahht.enterValue("awbNumber");
			gahht.verifyStatedPiecesWeight("Pieces", "Weight");
			gahht.verifyFlightDetails("carrierCode", "prop~flightNo");
			gahht.verifySCC("FullAWBNo","SCC"); 
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

			/*** HHT - BUILD UP****/

			buhht.invokeBuildUpScreen();
			String uldNo=cust.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code", "prop~flightNo","nextDay");
			map.put("BuildupLoc", WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG"));
			buhht.enterBuildupLocation("BuildupLoc");
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo1"));
			buhht.enterAWBDetailsWithoutPcsWgt("awbNumber");
			buhht.enterPiecesAndSCC("Pieces","Weight","SCC");
			buhht.clickMoreOptions();
			buhht.clickUpdateULDHeightContour();
			buhht.selectContourAndSave("Contour");
			cust.waitForSync(3);
			buhht.clicksave();
			cust.waitForSync(2);
			buhht.clickMoreOptions();
			buhht.clickCaptureULDWeigh();
			buhht.enterULDActualweight("Location", "ActualWeight", "val~10", "val~W1");
			cust.waitForSync(10);
			cust.clickBack("Build Up");
			cust.clickBack("Build Up");
			cust.clickBack("Build Up");

			buhht.invokeBuildUpScreen();
			String uldNo1=cust.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum1", uldNo1);
			buhht.enterValue("UldNum1");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code", "prop~flightNo","nextDay");
			buhht.enterBuildupLocation("BuildupLoc");
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo2"));
			buhht.enterAWBDetailsWithoutPcsWgt("awbNumber");
			buhht.enterPiecesAndSCC("Pieces","Weight","SCC");
			buhht.clickMoreOptions();
			buhht.clickUpdateULDHeightContour();
			buhht.selectContourAndSave("Contour");
			cust.waitForSync(3);
			buhht.clicksave();
			cust.waitForSync(2);
			buhht.clickMoreOptions();
			buhht.clickCaptureULDWeigh();
			buhht.enterULDActualweight("Location", "ActualWeight", "val~10", "val~W1");
			cust.waitForSync(10);
			cust.clickBack("Build Up");
			cust.clickBack("Build Up");
			cust.clickBack("Build Up");

			buhht.invokeBuildUpScreen();
			buhht.enterValue("UldNum");	
			cust.waitForSync(2);
			buhht.clickMoreOptions();
			buhht.clickCaptureLinkageAndFloatingPallets();
			buhht.checkFloatingPallet();
			buhht.enterNoofPosition("position");
			buhht.clicksave();
			cust.clickBack("Build Up");
			cust.clickBack("Build Up");

			//switch role
			cust.switchRoleToNewRoleGroup("Origin", "FCTL", "RoleGroup1");


			/**** OPR017 - Generate NOTOC****/
			cust.searchScreen("OPR017", "Generate NOTOC");
			OPR017.listFlight("carrierCode", "FlightNo","StartDate");
			OPR017.clickGenerateNOTOC("OPR017");
			cust.closeTab("OPR017", "Generate NOTOC");

			/*** HHT - BUILD UP****/

			buhht.invokeBuildUpScreen();
			buhht.enterValue("UldNum");
			cust.waitForSync(2);
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteBtn();
			buhht.clickSaveCaptureChecksheet();
			buhht.clickTopUpNoOption();
			buhht.clickSaveForContour();
			cust.waitForSync(2);
			cust.clickBack("Build Up");
			cust.clickBack("Build Up");

			buhht.invokeBuildUpScreen();
			buhht.enterValue("UldNum1");	
			cust.waitForSync(2);
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteBtn();
			buhht.clickSaveCaptureChecksheet();
			cust.waitForSync(2);
			buhht.clickTopUpNoOption();
			cust.waitForSync(2);
			cust.clickBack("Build Up");
			cust.clickBack("Build Up");
			libr.quitApp();


			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/** DEAD LOAD STATEMENT - OPR063 **/

			cust.searchScreen("OPR063", "Dead load statement");
			OPR063.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			OPR063.selectULD(cust.data("UldNum"));	
			OPR063.clickULDLoadingInstuctor();
			OPR063.verifyFloatingPallet("Yes");
			OPR063.verifynoofpositions(4);
			OPR063.ULDLoadingInstructionOK();
			cust.closeTab("OPR063", "Dead load statement");

			/*******Verify FSU-RCS message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo1");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCS=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo1");
			int verfColsRCS[]={9};
			String[] actVerfValuesRCS={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-RCS",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/*******Verify FSU-RCS message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo2");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCS1=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo2");
			int verfColsRCS1[]={9};
			String[] actVerfValuesRCS1={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS1, actVerfValuesRCS1, pmKeyRCS1,"val~XFSU-RCS",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/*******Verify FSU-PRE message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Prepared for loading");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo1");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyPRE=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo1");
			int verfColsPRE[]={9};
			String[] actVerfValuesPRE={"Sent"};
			MSG005.verifyMessageDetails(verfColsPRE, actVerfValuesPRE, pmKeyPRE,"val~XFSU-PRE",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");


			/*******Verify FSU-PRE message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Prepared for loading");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo2");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyPRE1=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo2");
			int verfColsPRE1[]={9};
			String[] actVerfValuesPRE1={"Sent"};
			MSG005.verifyMessageDetails(verfColsPRE1, actVerfValuesPRE1, pmKeyPRE1,"val~XFSU-PRE",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");



			/*******Verify FSU-FOH message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Freight On Hand");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo1");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFOH=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo1");
			int verfColsFOH[]={9};
			String[] actVerfValuesFOH={"Sent"};
			MSG005.verifyMessageDetails(verfColsFOH, actVerfValuesFOH, pmKeyFOH,"val~XFSU-FOH",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");


			/*******Verify FSU-FOH message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Freight On Hand");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo2");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFOH1=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo2");
			int verfColsFOH1[]={9};
			String[] actVerfValuesFOH1={"Sent"};
			MSG005.verifyMessageDetails(verfColsFOH1, actVerfValuesFOH1, pmKeyFOH1,"val~XFSU-FOH",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/** CHECKING XFUM TRIGGERED FOR AWB **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFUM");
			MSG005.clickList();
			String pmKeyFUM=cust.data("Origin")+" - "+cust.data("UldNum").substring(3,8);
			MSG005.verifyIfMessageTriggered(pmKeyFUM,cust.data("ProfileId"),"XFUM",true);
			String pmKeyFUM1=cust.data("Origin")+" - "+cust.data("UldNum1").substring(3,8);
			MSG005.verifyIfMessageTriggered(pmKeyFUM1,cust.data("ProfileId"),"XFUM",true);
			libr.waitForSync(1); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");


			//launch browser 
			driver = libr.relaunchBrowser("chrome");


			//Login to Cafeed
			String[] cafeed = libr.getApplicationParams("cafeed");
			driver.get(cafeed[0]);
			Thread.sleep(2000);
			cust.loginToCafeed(cafeed[1], cafeed[2]);
			Thread.sleep(2000);

			cfd.clickSearchFlightLink();
			cfd.listFlightDetails("FullFlightNo", "Date");
			cfd.clickULDDetails("UldNum");
			cfd. verifyFloatingPalletIsChecked();
			cfd.verifynoofpositions("expposition");
			cfd.verifyCommericalLinkageisEmpty();
			libr.quitBrowser();




		}
		catch (Exception e) {
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