package wp2;



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
import screens.GeneratePaymentAdvice_CSH007;

import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.WarehouseShipmentEnquiry_WHS011;



/**
 * 
 *  1995: TC_01_Verify column configurator option
 *
 *
 */

public class IASCB_19141_TC_1995 extends BaseSetup {

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
	public MaintainFlightSchedule_FLT005 FLT005;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public WarehouseShipmentEnquiry_WHS011 WHS011;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;

	
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "wp2";

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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		WHS011=new WarehouseShipmentEnquiry_WHS011(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		
	}

	@DataProvider(name = "TC_1995")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_1995")
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
			
			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);	
			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMMM",0, "DAY", ""));
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());

			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ShipperCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

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

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));


			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
		
		/** Maintain Flight Screen (FLT005) . Taking fresh flight**/

		cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
		FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
		cust.closeTab("FLT005", "Maintain Schedule");
		
		//Flight details
		String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
		map.put("FullFlightNo", WebFunctions.getPropertyValue(proppath, "flightNumber"));
		map.put("FlightNo", FlightNum.substring(2));
		excelRead.writeDataInExcel(map, path1, sheetName, testName);
	

		libr.quitBrowser();

		// Relaunch browser
		driver = libr.relaunchBrowser("chrome");

		/****************** MERCURY *********************/

		// Login to "MERCURY"
		String[] mercury = libr.getApplicationParams("mercury");
		driver.get(mercury[0]); // Enters URL
		cust.loginToMercury(mercury[1], mercury[2]);

		map.put("FlightNumber", cust.data("FullFlightNo"));
		cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
		mercuryScreen.clickSendMessage();
		mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
		mercuryScreen.sendMessageInMercury();
		mercuryScreen.verifyMsgStatus("SSM");
		libr.quitBrowser();

		// Relaunch browser
		driver = libr.relaunchBrowser("chrome");

		/*** Login to cgocxml **********/

		String[] cgocxml = libr.getApplicationParams("cgocxml");
		driver.get(cgocxml[0]); // Enters URL
		cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
		
		/**** XFSU-BKD Message loading ****/
		cust.createXMLMessage("MessageExcelAndSheetXFSU", "MessageParamXFSU");
		Cgocxml.clickMessageLoader();
		Cgocxml.sendMessageCgoCXML("ICARGO");
		
		/**** XFBL Message loading ****/
		map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
		cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
		String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
				+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
		String scc[] = { cust.data("SCC") };
		String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
		cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
		Cgocxml.sendMessageCgoCXML("ICARGO");
		
		/**** XFWB Message loading ****/
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
		Thread.sleep(2000);

		// Switch Role
		cust.switchRole("Origin", "FCTL", "RoleGroup");
			
		/**** OPR339 - Security & Screening ****/

			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Screening");
			
			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			

			/**** OPR335 -Goods Acceptance ****/
			
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume", "CommodityCode");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			/** Warehouse Shipment Enquiry **/
			
			cust.searchScreen("WHS011", "Warehouse Shipment Enquiry");
			WHS011.enterFromDate("StartDate");
			WHS011.enterToDate("StartDate");
			WHS011. enterAWBdetails("CarrierNumericCode", "AWBNo");
			WHS011.clickList();
			int[] col = {4};
			String[] Location={cust.data("Location")};
			WHS011.verifyWarehouseDetailsWithPmKey(col, Location,"AWBNo");
			WHS011.clickColumnConfig();
			WHS011.verifyColumnChkboxChecked();
			String[] columns = {"Zone","Loc"};
			WHS011.unselectColumns(columns);
			WHS011.saveColmnConfig();
			WHS011.closeTab("WHS011", "Warehouse Shipment Enquiry");
			libr.quitBrowser();
			
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");
			

			/** Warehouse Shipment Enquiry **/
			
			cust.searchScreen("WHS011", "Warehouse Shipment Enquiry");
			WHS011.enterFromDate("StartDate");
			WHS011.enterToDate("StartDate");
			WHS011. enterAWBdetails("CarrierNumericCode", "AWBNo");
			WHS011.clickList();
			WHS011.verifyColumnNotPresent(columns);
			WHS011.clickColumnConfig();
			WHS011.selectColumns(columns);
			WHS011.saveColmnConfig();
			WHS011.closeTab("WHS011", "Warehouse Shipment Enquiry");
			libr.quitBrowser();
			
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");
			

			/** Warehouse Shipment Enquiry **/
			
			cust.searchScreen("WHS011", "Warehouse Shipment Enquiry");
			WHS011.enterFromDate("StartDate");
			WHS011.enterToDate("StartDate");
			WHS011. enterAWBdetails("CarrierNumericCode", "AWBNo");
			WHS011.clickList();
			WHS011.verifyColumn(columns);
			WHS011.closeTab("WHS011", "Warehouse Shipment Enquiry");
			

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
