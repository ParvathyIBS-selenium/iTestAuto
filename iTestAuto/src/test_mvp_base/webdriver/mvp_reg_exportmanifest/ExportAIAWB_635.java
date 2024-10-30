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
import screens.CaptureHAWB_OPR029;

/**
 * Export of consolidated AWB for which FWB and FHL have been sent by the
 * customers FBL received for an AI flight
 **/
public class ExportAIAWB_635 extends BaseSetup {

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
	public CaptureHAWB_OPR029 OPR029;
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
		OPR029 = new CaptureHAWB_OPR029(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "ManifestIAD_1")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "ManifestIAD_1")
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

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_US"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_US"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_US"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_US"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_US"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_US"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_US"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_US"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_US"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_US"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_US"));


			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_IN"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_IN"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_IN"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_IN"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_IN"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_IN"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_IN"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_IN"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_IN"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_IN"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "DXB"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "DEL"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_US"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_US"));



			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

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

			/*** incoming flight - ek**/
			map.put("FullFlightNo", cust.data("prop~otherAirline") +cust.data("prop~flightNo"));

			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);


			// Create ASM message
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");


			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Login to iCargo

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/** XFBL LOADING FLIGHT 2 **/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("FlightNumber", cust.data("FullFlightNo1"));
			map.put("Org", cust.data("Transit"));
			map.put("Des", cust.data("Destination"));

			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();



			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Login to iCargo

			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");


			/**** OPR026 - Capture AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.clickConsoleButton();
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Transit", "prop~otherAirline");
			OPR026.enterSecondRouting("Destination", "carrierCode");     
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.clickSave();
			OPR026.clickYesButton();
			// Click 'Add/Update' HAWB button
			OPR029.clickAddUpdateHAWBBtn();
			// Capture HAWB details
			OPR026.addHAWBDetails("HAWB", "ShipperCode", "ConsigneeCode", "Origin", "Destination", "Pieces", "Weight");
			OPR029.clickHAWBSaveBtn();
			OPR026.close("OPR029");
			cust.waitForSync(3);
			OPR026.handleShipmentStatusPopUp();
			// Click HAWB Doc Finalized checkbox
			OPR026.clickHAWBDocFinalized();
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.enterSCC("val~SPX");
			OPR339.chkSecurityDataReviewed();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");


			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			// Click As Is Execute button
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/** ULDAcceptance **/

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			String uldNo = OPR335.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			OPR335.uldShipmentDetails("Pieces", "Weight", "Location", "UldNum", "");
			OPR335.addULDDetails();
			OPR335.provideCTMdetails("prop~otherAirline", "StartDate");
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
			OPR063.verifyULDActualWeight("UldNum","ActualWeight");
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

			libr.quitBrowser();


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
