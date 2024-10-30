package iascb_45883;

import java.util.Map;

import org.testng.Assert;
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
import screens.BreakdownHHT;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ImportDocumentation_OPR001;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;

/**
 * TC_01_Verify that ULD is marked as INTACT during breakdown - clearing agent.
 **/

public class IASCB_45883_TC_8093_KL extends BaseSetup {

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
	public MaintainFlightSchedule_FLT005 FLT005;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public Mercury mercuryScreen;
	public BreakdownHHT bdhht;
	public Cgocxml Cgocxml;
	public ImportDocumentation_OPR001 OPR001;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "iascb_45883";

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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		bdhht = new BreakdownHHT(driver, excelreadwrite, xls_Read);
		OPR001 = new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_8093")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_8093")
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

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_NL2"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_NL2"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_NL2"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_NL2"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_NL2"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_NL2"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_NL2"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_NL2"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_NL2"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_NL2"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);

			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM", 7, "DAY", ""));
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			
			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Paris");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", "T133072");

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");

			/** Maintain Flight Screen (FLT005) . Taking fresh flight **/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode", "prop~flightNo", startDate, endDate, "FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));

			// Flight details

			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FlightNumber", cust.data("FullFlightNo"));
			

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("FullAWBNo", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"));
			map.put("AWBNo1", cust.data("prop~AWBNo"));
			

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2
			map.put("FullAWBNo2", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"));
			map.put("AWBNo2", cust.data("prop~AWBNo"));
			

			// Checking AWB is fresh or Not (AWBNumber3)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber3
			map.put("FullAWBNo3", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"));
			map.put("AWBNo3", cust.data("prop~AWBNo"));


			/** SSM Message loading **/

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "","SSM_NEW");

			/** XFWB Message loading for AWB 1 **/

			map.put("FullAWBNum", cust.data("FullAWBNo"));
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "","XFWB_WithScreeningInfo", true);

			/** XFWB Message loading for AWB 2 **/

			map.put("FullAWBNum", cust.data("FullAWBNo2"));
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "","XFWB_WithScreeningInfo", true);

			/** XFWB Message loading for AWB 3 **/

			map.put("FullAWBNum", cust.data("FullAWBNo3"));
			
			//Consignee with loose delivery preference unchecked
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));
			
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "","XFWB_WithScreeningInfo", true);
			
			/** XFFM Message loading **/
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { libr.data("FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + libr.data("ShipmentDesc"),libr.data("FullAWBNo2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + libr.data("ShipmentDesc"),libr.data("FullAWBNo3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC"),cust.data("SCC"),cust.data("SCC")};
			String routing1[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport"),
							cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
									+ cust.data("DestinationAirport"),cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
											+ cust.data("DestinationAirport")};
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			int []shipments={3};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing1, uld,shipments);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");


			/** Switch role to Destination **/
			cust.switchRole("Destination", "Origin", "RoleGroup");

			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode", "FlightNo", "StartDate");
			FLT006.clickFlightMovementDepartureDetailsLink();
			FLT006.clickFlightMovementArrivalDetailsLink();
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");

			/**** Import Manifest ***/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "prop~flightNo", "StartDate");
			OPR367.maximizeAllDetails();
			OPR367.verifyShipment("AWBNo1");
			OPR367.verifyShipment("AWBNo2");
			OPR367.verifyShipment("AWBNo3");
		    cust.closeTab("OPR367", "Import Manifest");

			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			// Login in to HHT
			String[] hht2 = libr.getApplicationParams("hht2");
			cust.loginHHT(hht2[0], hht2[1]);

			/*** HHT - BREAKDOWN ****/

			bdhht.invokeBreakdownHHTScreen();
			bdhht.enterValue("UldNum");
			bdhht.selectAwb("FullAWBNo3");
			String scc1[] = { cust.data("SCC") };
			bdhht.selectMultipleSCC(scc1);
			bdhht.addPcs("Pieces");
			bdhht.clickSave();
			cust.clickBack("Breakdown");

			// selecting unitized button in hht breakdown screen
			bdhht.enterValue("UldNum");
			bdhht.clickUnitizedYes();
			bdhht.clickSave();
			cust.clickBack("Breakdown");
			cust.clickBack("Breakdown");
			libr.quitApp();

			/**** Import Manifest ***/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "prop~flightNo", "StartDate");
			OPR367.maximizeAllDetails();
			OPR367.clickCheckBox_ULD(cust.data("UldNum"));	
			OPR367.clickBreakdownButton();
			OPR367.verifyIntactCheck(true);
			
			
			/********* OPR001 Import Documentation ***********/

			cust.searchScreen("OPR001", "Import Documentation: OPR001");
			OPR001.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo2"));
			OPR001.clickUpdatePrenominationBtn();
			OPR001.updateClearingAgentDetails("ConsigneeCode");
			OPR001.saveDetails();
			OPR001.closeTab("OPR001", "Import Documentation: OPR001");	
			
			
			/**** Import Manifest ***/
			
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "prop~flightNo", "StartDate");
			OPR367.maximizeAllDetails();
			OPR367.verifyClearingAgent("AWBNo2","ConsigneeName");
			cust.closeTab("OPR367", "Import Manifest");
			libr.quitBrowser();

		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
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
