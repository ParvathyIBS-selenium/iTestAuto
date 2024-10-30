package mvp_cr_iascb_31368;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AWBClearance_OPR023;
import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.ExportManifest_OPR344;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.TracingReports_TRC006;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

/**Capture Damage Discrepancy for Transit Shipment via Truck and Verify Auto Block Should be applied for AWB **/


public class IASCB_31368_TC10 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public TracingReports_TRC006 TRC006;
	public AWBClearance_OPR023 OPR023;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;
	public Mercury mercuryScreen;
	public ExportManifest_OPR344 OPR344;
	public ImportManifest_OPR367 OPR367;
	public BreakDownScreen_OPR004 OPR004;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName="mvp_cr_iascb_31368";	

	@BeforeClass
	public void setup() {

		testName = getTestName();
		//excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		TRC006 = new TracingReports_TRC006(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		Cgocxml=new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon=new Cgomon(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
	}



	@DataProvider(name = "IASCB_31368")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}


	@Test(dataProvider = "IASCB_31368")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map=map;		
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
			Thread.sleep(2000);	


			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR"));
			
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US3"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US3"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US3"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US3"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US3"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US3"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US3"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US3"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US3"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US3"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "ORD"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_FR"));


			/******** TELEX ADDRESS****/
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));


			// creating flight number
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			FlightNum=FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			System.out.println(FlightNum);


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

			/*** Loading ASM ***/
			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			//Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			map.put("Aircraft", cust.data("AircraftType").split(",")[1]);

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");
			mercuryScreen.returnTosendMessage();

			/***MESSAGE - loading ASM for flight 2**/

			cust.createFlight("FullFlightNumber2");

			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			FlightNum2=FlightNum2.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo2", FlightNum2);	
			map.put("FlightNo2", FlightNum2.substring(2));
			
			map.put("Aircraft", cust.data("AircraftType").split(",")[0]);

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM2");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");
			libr.quitBrowser();


			/************ LOADING MESSAGE VIA CGOCXML***/
			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			
			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			//Create XFBL message for 2nd flight

			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipments[] = { libr.data("prop~FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String sccs[] = {cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1] };
			String routings[] = { cust.data("Origin") + ";" + cust.data("Destination") };

			cust.createXFBLMessage("XFBL_2", shipments, sccs, routings);

			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			map.put("OtherCarrier", cust.data("carrierCode"));
			/***MESSAGE - loading XFWB **/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			cust.createXFWBMessageWithSCCs("XFWB_Transit_MultipleSCCs", sccs);
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/*** MESSAGE - loading and creating XFFM ****/

			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			System.out.println(cust.data("ULDNo"));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { cust.data("prop~FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC").split(",")[0]+";"+ cust.data("SCC").split(",")[1] };
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("prop~flight_code") };
			
			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing, uld);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			libr.quitBrowser();


			//Login to "MERCURY"
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);


			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");
			mercuryScreen.returnTosendMessage();

			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");

			libr.quitBrowser();


			/***** RELOGIN TO ICARGO***/
			driver=libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			/** Import Manifest **/
			
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code", "FlightNo", "StartDate");
			OPR367.verifyBreakdownInstructionsTag("val~Thru unit");
			String pmkey = cust.data("UldNum");
			OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			OPR004.verifyThruCheckbox();
			OPR004.clickBreakdownComplete();
			OPR367.closeFromOPR004();
			OPR367.closeTab("OPR367", "Import Manifest");

			/**** TRC006 - Tracing Reports ******/

			cust.searchScreen("TRC006", "Tracing Reports");
			TRC006.addReportType("Damage Report");
			TRC006.listReportWithAWB("AWBNo", "CarrierNumericCode");
			TRC006.verifyStatedPiecesAndWeight("Pieces", "Weight","ShipmentDesc");
			TRC006.addDamageDetails("DmgCode", "Pieces", "DamageDetails");
			TRC006.printDamage();
			TRC006.closeTab("TRC006", "Tracing Reports");

			/*****OPR023 - AWB Clearance *******/            
			//Verify that block shouldnt be created
			cust.searchScreen("OPR023", "AWB Clearance");
			OPR023.listAWB("prop~CarrierNumericCode","prop~AWBNo");
			OPR023.verifyBlockReleased(cust.data("BlockType"),cust.data("FullAWBNo"),"AWBClearance_OPR023");
			OPR023.closeTab("OPR023", "AWB Clearance");

			/**** OPR344 - Export manifest****/
			//verify build up allowed for part 2 shipment
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo2","StartDate");
			OPR344.verifyULDInPlannedSection("UldNum");
			OPR344.assignUldPlanningSection("UldNum");
			OPR344.verifyULDInAssignedShipment("UldNum", true);
			OPR344.clickBuildUpComplete();
			OPR344.verifyBuildUpComplete("UldNum");
			cust.closeTab("OPR344", "Export manifest");




		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}

}


