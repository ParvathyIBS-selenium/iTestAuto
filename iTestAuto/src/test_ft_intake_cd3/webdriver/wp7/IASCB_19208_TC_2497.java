package wp7;


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
import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;
import screens.ImportManifest_OPR367;
import screens.BreakDownScreen_OPR004;
import screens.ExportShipmentListing_OPR030;
import screens.GeneratePaymentAdvice_CSH007;
import screens.Cgocxml;
import screens.Mercury;

/**
 *  Verify the provision to view breakdown information for transit shipment arriving in multiple flight
 */


public class IASCB_19208_TC_2497 extends BaseSetup {

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
	public MaintainFlightSchedule_FLT005 FLT005;
	public ImportManifest_OPR367 OPR367;
	public BreakDownScreen_OPR004 OPR004;
	public ExportShipmentListing_OPR030 OPR030;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public Cgocxml Cgocxml;
	public Mercury mercuryScreen;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
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
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);

		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR030 = new ExportShipmentListing_OPR030(driver, excelreadwrite, xls_Read);

		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2497")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2497")
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

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			// creating flight number 1
			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM",0, "DAY", ""));
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code_KL") + cust.data("prop~flightNo"), proppath);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			//Maintain Flight Screen (FLT005) . Taking fresh flight

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight 1 details
			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum1 = FlightNum1.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum1);
			map.put("FlightNo", FlightNum1.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			

			// creating flight number 2
			cust.createFlight("FullFlightNumber");
			
			//Maintain Flight Screen (FLT005) . Taking fresh flight

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");
			
			//Flight 2 details
			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum2 = FlightNum2.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo2", FlightNum2.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

		
			/***Storing Values to Map***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR1"));
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR1"));
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));

			
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			/****************** MERCURY *********************/

			//	 Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/**SSM Message loading for flight 1 **/

			map.put("flightNumber", FlightNum1);
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");



			/** SSM Message loading for flight 2 **/
			map.put("flightNumber", FlightNum2);
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.returnTosendMessage();
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");


			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFBL Message loading for flight 1 **/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("flightNumber", FlightNum1);
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment1[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces1") + ";" + libr.data("Weight1") + ";"
					+ libr.data("Volume1") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment1, scc, routing);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFBL Message loading for flight 2 **/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("flightNumber", FlightNum2);
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment2[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces2") + ";" + libr.data("Weight2") + ";"
					+ libr.data("Volume2") + ";" + libr.data("ShipmentDesc") };

			cust.createXFBLMessage("XFBL_2", shipment2, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			libr.quitBrowser();


			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			/**** OPR026 - Capture AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCodeAMS");
			// Enter shipment details
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Transit", "prop~flight_code_KL");
			OPR026. enterSecondRouting("Destination", "prop~flight_code_KL");
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");



			/**** OPR339 - Security & Screening ****/

			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Screening");

			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCodeAMS");
			OPR026.asIsExecuteOnly();

			// Generate Payment Advice Screen
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			cust.closeTab("OPR026", "Capture AWB");




			/****OPR355 - Loose Acceptance****/

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCodeAMS", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");


			/**** OPR344 - Export manifest****/

			//manifesting and finalizing flight 1
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code_KL", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWBSplitShipment("UldNum","0","prop~CarrierNumericCodeAMS","AWBNo","Pieces1","Weight1");
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			OPR344.finalizeFlight(true);
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export Manifest");

			/**** OPR344 - Export manifest****/

			//manifesting and finalizing flight 2
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code_KL", "FlightNo2","StartDate");
			String uldNum2=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum2", uldNum2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWBSplitShipment("UldNum2","0","prop~CarrierNumericCodeAMS","AWBNo","Pieces2","Weight2");
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			OPR344.finalizeFlight(true);
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export Manifest");
			
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Login to "MERCURY"
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** Loading MVT : ARRIVAL  for Flight 1**/

			map.put("flightNumber", FlightNum1);
			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");


			/** Loading MVT : ARRIVAL for flight 2**/

			map.put("flightNumber", FlightNum2);
			mercuryScreen.returnTosendMessage();
			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");

			libr.quitBrowser();


			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			
			// Switch role
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			/** OPR030-Export Shipment Listing **/

			cust.searchScreen("OPR030", "Export Shipment Listing");
			OPR030.enterAWB("AWBNo", "prop~CarrierNumericCodeAMS");
			OPR030.clearOrigin();
			OPR030.selectFilterMode("val~Journey Date Mode");
			OPR030.clickList();

			//verifying existing column Accepted Pcs/Wgt/Vol column is renamed to Breakdown/Accepted Pcs/Wgt/Vol

			String[] colName={"Accepted / Breakdown Pcs/Wgt/Vol (Kg/CBM)"};
			OPR030.verifyColumn(colName);

		
			//verifying Accepted pcs/wgt/vol of the shipment before breakdown at the transit
			
			int verfCols [] = {16};
			String[] actVerfValues = { "0"+" / "+"0"+" / "+"0" };
			OPR030.verifyTableRecords(verfCols, actVerfValues, "FullAWBNo");
			cust.closeTab("OPR030", "Export Shipment Listing");



			/** Import Manifest **/

			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code_KL", "FlightNo", "StartDate");
			String pmkey1 = Excel.getCellValue(path1,sheetName, "IASCB_19208_TC_2497", "UldNum");
			OPR367.clickCheckBox_ULD(pmkey1);
			OPR367.clickBreakDownandBreakdownComplete("Location2", "Pieces1", "Weight1");
			OPR367.closeFromOPR004();
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");

			/** OPR030-Export Shipment Listing **/

			//Verifying broken down pcs/wgt/vol of Flight 1 for the transit shipmnet
			cust.searchScreen("OPR030", "Export Shipment Listing");
			OPR030.enterAWB("AWBNo", "prop~CarrierNumericCodeAMS");
			OPR030.clearOrigin();
			OPR030.selectFilterMode("val~Journey Date Mode");
			OPR030.clickList();
			int verfCols2 [] = {16};
			String[] actVerfValues2 = { cust.data("Pieces1")+" / "+cust.data("Weight1")+" / "+cust.data("Volume1") };
			OPR030.verifyTableRecords(verfCols2, actVerfValues2, "FullAWBNo");
			cust.closeTab("OPR030", "Export Shipment Listing");

			/** Import Manifest **/

			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code_KL", "FlightNo2", "StartDate");
			String pmkey2 = Excel.getCellValue(path1,sheetName, "IASCB_19208_TC_2497", "UldNum2");
			OPR367.clickCheckBox_ULD(pmkey2);
			OPR367.clickBreakDownandBreakdownComplete("Location2", "Pieces2", "Weight2");
			OPR367.closeFromOPR004();
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");

			/** OPR030-Export Shipment Listing **/

			//Verifying total broken down pcs/wgt/vol for the transit shipmnet
			cust.searchScreen("OPR030", "Export Shipment Listing");
			OPR030.enterAWB("AWBNo", "prop~CarrierNumericCodeAMS");
			OPR030.clearOrigin();
			OPR030.selectFilterMode("val~Journey Date Mode");
			OPR030.clickList();
			int verfCols3 [] = {16};
			String[] actVerfValues3 = { cust.data("Pieces")+" / "+cust.data("Weight")+" / "+cust.data("Volume") };
			OPR030.verifyTableRecords(verfCols3, actVerfValues3, "FullAWBNo");
			cust.closeTab("OPR030", "Export Shipment Listing");




		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}