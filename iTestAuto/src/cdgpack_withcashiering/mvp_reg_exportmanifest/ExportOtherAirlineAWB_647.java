package mvp_reg_exportmanifest;

import java.util.Map;

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
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.DeadloadStatement_OPR063;
import screens.ExportManifest_OPR344;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;

/**
 * Export of consolidated AWB for which FWB and FHL have been sent by the
 * customers FBL received for an "other airline" flight
 **/
public class ExportOtherAirlineAWB_647 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public BuildupPlanning_ADD004 ADD004;
	public ListMessages_MSG005 MSG005;
	public DeadloadStatement_OPR063 OPR063;
	public Cgomon Cgomon;
	public Cgocxml Cgocxml;
	public Mercury mercuryScreen;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_reg_exportmanifest";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		// excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		ADD004 = new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		OPR063 = new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "ExportOtherAirlineAWB_647")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "ExportOtherAirlineAWB_647")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));

			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR2"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR2"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR2"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR2"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR2"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR2"));
			map.put("ShipperCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR2"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR2"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR2"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_IN"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_IN"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_IN"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_IN"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_IN"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_IN"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_IN"));
			map.put("ConsigneeCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_IN"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_IN"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_IN"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "DEL"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_FR2"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_FR2"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "DEL"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury",
					WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** CREATE FLIGHT 1 **/

			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo2", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber2", cust.data("prop~flight_code") + cust.data("prop~flightNo2"),
					proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			map.put("FlightNumber", cust.data("FullFlightNo"));
			map.put("ASMdep", cust.data("ASMdeparture1"));
			map.put("ASMarr", cust.data("ASMarrival1"));
			map.put("Org", cust.data("Origin"));
			map.put("Des", cust.data("Transit"));

			/***** ASM Message loading 1 - MERCURY *****/

			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			// Create ASM message
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");

			/** CREATE FLIGHT 2 **/

			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNumber", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			System.out.println(FlightNum2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo1", FlightNum2);
			map.put("FlightNo1", FlightNum2.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			map.put("ASMdep", cust.data("ASMdeparture2"));
			map.put("ASMarr", cust.data("ASMarrival2"));
			map.put("Org", cust.data("Transit"));
			map.put("Des", cust.data("Destination"));
			map.put("FlightNumber", cust.data("FullFlightNo1"));

			/***** ASM Message loading 2 - MERCURY *****/

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.returnTosendMessage();
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");

			libr.quitBrowser();

			/**** OPR026 - CHECKING AWB IS FRESH OR NOT ****/

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Login to iCargo

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),
					proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			libr.quitBrowser();

			/*** LOADING XFBL - CGOCXML ***/

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/** XFBL LOADING FLIGHT 2 **/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("FlightNumber", cust.data("FullFlightNo1"));
			map.put("Org", cust.data("Transit"));
			map.put("Des", cust.data("Destination"));

			// Create XFBL message
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");

			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			// Cgocxml.clickMessageLoader();
			// Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFWB LOADING ****/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** XFZB LOADING ***/
			// Create XFZB message
			cust.createXMLMessage("MessageExcelAndSheetXFZB", "MessageParamXFZB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Login to iCargo

			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/**** OPR339 - Security & Screening ****/

			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.enterSCC("val~SPX");
			OPR339.chkSecurityDataReviewed();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.clickHAWBDocFinalized();
			// Click As Is Execute button
			OPR026.asIsExecuteOnly();

			//Generate Payment Advice Screen
			CSH007.verifyServiceCode("val~AWBI");
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");	
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			cust.closeTab("OPR026", "Capture AWB");

			// Switch role
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			/** ULDAcceptance **/

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			String uldNo = OPR335.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			OPR335.uldShipmentDetails("Pieces", "Weight", "Location", "UldNum", "");
			OPR335.addULDDetails();
			OPR335.providedimensionDetails("FullFlightNo", "StartDate");
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo1", "StartDate");
			OPR344.verifyULDInPlannedSection("UldNum");
			OPR344.assignUldPlanningSection("UldNum");
			OPR344.verifyULDInAssignedShipment("UldNum", true);
			OPR344.clickBuildUpComplete();
			OPR344.clickEditULDdetails("UldNum");
			OPR344.acceptAlertPopUp("val~The ULD is build-up completed. Do you want to reopen and proceed?");
			OPR344.verifyULDValues();
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight", "Occupancy");
			OPR344.clickEditULDdetails("UldNum");
			OPR344.addAWBDetails("CarrierNumericCode", "prop~AWBNo", true);
			cust.closeTab("OPR344", "Export manifest");

			/** DEAD LOAD STATEMENT - OPR063 **/
			cust.searchScreen("OPR063", "Dead load statement");
			OPR063.listFlightDetails("carrierCode", "FlightNo1", "StartDate");
			OPR063.verifyFlightDetails();
			OPR063.selectULD(cust.data("UldNum"));
			OPR063.clickSendProvisional();
			cust.closeTab("OPR063", "Dead load statement");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo1", "StartDate");
			OPR344.clickBuildUpComplete();
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			OPR344.finalizeFlight(true);
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export manifest");

			/** Messages Triggered **/

			// ********** CHECKING IF XFSU-RCS GOT TRIGGERD ****/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.clickList();
			String pmKeyRCS = cust.data("CarrierNumericCode") + " - " + cust.data("prop~AWBNo");
			int verfColsRCS[] = { 9 };
			String[] actVerfValuesRCS = { "Sent" };
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS, "val~XFSU-RCS", false);
			libr.waitForSync(2);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** CHECKING IF XFSU-FOH GOT TRIGGERD ****/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Freight On Hand");
			MSG005.clickList();
			String pmKeyFOH = cust.data("CarrierNumericCode") + " - " + cust.data("prop~AWBNo");
			int verfColsFOH[] = { 9 };
			String[] actVerfValuesFOH = { "Sent" };
			MSG005.verifyMessageDetails(verfColsFOH, actVerfValuesFOH, pmKeyFOH, "val~XFSU-FOH", false);
			libr.waitForSync(2);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** CHECKING IF FSU-PRE GOT TRIGGERD ****/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Prepared for loading");
			MSG005.clickList();
			String pmKeyPRE = cust.data("CarrierNumericCode") + " - " + cust.data("prop~AWBNo");
			int verfColsPRE[] = { 9 };
			String[] actVerfValuesPRE = { "Sent" };
			MSG005.verifyMessageDetails(verfColsPRE, actVerfValuesPRE, pmKeyPRE, "val~XFSU-PRE", false);
			libr.waitForSync(2);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/** CHECKING XFUM TRIGGERED FOR AWB **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFUM");
			MSG005.clickList();
			String pmKeyFUM=cust.data("Transit")+" - "+cust.data("UldNum").substring(3,8);
			int verfColsFUM[] = { 9 };
			String[] actVerfValuesFUM = { "Sent" };
			MSG005.verifyMessageDetails(verfColsFUM, actVerfValuesFUM, pmKeyFUM, "val~xFUM", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/********** CHECKING IF XFSU-MAN GOT TRIGGERD ****/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Manifest Details");
			MSG005.clickList();
			String pmKeyMAN = cust.data("CarrierNumericCode") + " - " + cust.data("prop~AWBNo");
			int verfColsMAN[] = { 9 };
			String[] actVerfValuesMAN = { "Sent" };
			MSG005.verifyMessageDetails(verfColsMAN, actVerfValuesMAN, pmKeyMAN, "val~XFSU-MAN", false);
			libr.waitForSync(2);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/** CHECKING xFZB TRIGGERED FOR AWB **/
			 
			cust.searchScreen("MSG005","MSG005 - List Messages");
			MSG005.enterMsgType("XFZB");
			MSG005.clickList();
			String pmKeyXFZB=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo")+" - "+cust.data("HAWB");
			int verfColsXFZB[]={9};
			String[] actVerfValuesXFZB={"Sent"}; 
			MSG005. verifyMessageDetails(verfColsXFZB,actVerfValuesXFZB, pmKeyXFZB,"val~xFZB", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005","MSG005 - List Messages");
			
			/** CHECKING XFWB TRIGGERED FOR AWB **/
			
			 cust.searchScreen("MSG005", "MSG005 - List Messages");
			 MSG005.enterMsgType("XFWB"); MSG005.selectStatus("Sent");
			 MSG005.clickList(); 
			 String pmKeyXFWB=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination"); 
			 int verfColsXFWB[]={9}; 
			 String[]actVerfValuesXFWB={"Sent"};
			 MSG005.verifyMessageDetails(verfColsXFWB, actVerfValuesXFWB,pmKeyXFWB,"val~XFWB",false); 
			 libr.waitForSync(1);
			 MSG005.closeTab("MSG005", "MSG005 - List Messages");
			 
			 /** CHECKING XFFM TRIGGERED FOR FLIGHT **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFFM");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFFM=cust.data("carrierCode")+" - "+cust.data("FlightNo1")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Transit");
			int verfColsXFFM[]={9};
			String[] actVerfValuesXFFM={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFFM, actVerfValuesXFFM, pmKeyXFFM,"val~XFFM",false);
			libr.waitForSync(1); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			libr.quitBrowser();
			

		

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
