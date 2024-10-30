package iascb_149878;

/** TC_31_Verify Updation of short captured AWB details based on XFFM or FFM before XFWB process or AWB save when config is set  **/

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
import screens.ImportManifest_OPR367;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;

public class IASCB_149878_TC_10245  extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public MaintainFlightSchedule_FLT005 FLT005;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "IASCB_149878";

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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_10245")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_10245")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			//Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);

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
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));
			
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			//Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			//Creating flight number
			cust.createFlight("FullFlightNumber");
			//Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			
			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			/****************** MERCURY *********************/
			//Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** SSM Message loading for the Flight with CDG-AMS route **/
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			//Switch role to Destination
			cust.switchRole("Destination", "FCTL", "RoleGroup");
			
			/** Mark Flight Movement **/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode","FlightNo","StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Amsterdam");
			map.put("ATA", currtime);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","StartDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,startDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
			
			/** Import Manifest **/
			//Short Capturing AWB from OPR367 (CDG-AMS route)
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			String uldNo=cust.create_uld_number("UldType", "carrierCode");
		    map.put("UldNum", uldNo);
		    excelRead.writeDataInExcel(map, path1, sheetName, testName); 
		    OPR367.addNewULDInfoWithSCC("UldNum", "CarrierNumericCode", "AWBNo", "Pieces", "Weight", "Origin", "Destination", "Pieces", "Weight",2,"SCC");
			OPR367.SaveDetails();
			OPR367.uncheckManuallyUpdatedCheckbox();
			OPR367.SaveDetails();
			cust.closeTab("OPR367", "Import Manifest");
			libr.quitBrowser();
					
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			//Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); 
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
			
			/*** XFFM Message loading Before XFWB - With Updated Origin, Destination, Pieces, Weight, Volume and SCC ***/		
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));
			
			map.put("OrgAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("DestAirport", WebFunctions.getPropertyValue(custproppath, "JFK"));
			map.put("Org","IAD");
			map.put("Dest","JFK");
			
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
		
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pcs") + ";" + libr.data("Weight1") + ";"+ libr.data("Vol") + ";" + libr.data("ShipmentDesc")};
			String scc[] = { cust.data("SCC1") };
			String routing[] = { cust.data("Org") + ";" + cust.data("OrgAirport") + ";" + cust.data("Dest")+ ";" + cust.data("DestAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };

			//Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing, uld);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();
			
			driver=libr.relaunchBrowser("chrome");
			//Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			//Switch role to Destination
			cust.switchRole("Destination", "FCTL", "RoleGroup");
			
			/** Import Manifest - Verifying Short Captured Data overwritten with XFFM Data **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			String[]sccNotPresent={cust.data("SCC")};
			String[]sccPresent={cust.data("SCC1")};
			OPR367.verifySCCsAddedInULD("UldNum",sccPresent);
			OPR367.verifySCCsNotPresentInULD("UldNum", sccNotPresent);
			OPR367.verifyAWBOriginAndDestinationInsideULD("UldNum","AWBNo","Org","Dest");
			OPR367.verifyManifestedDetails("Pcs","Weight1","Pcs","Weight1");
			cust.closeTab("OPR367", "Import Manifest");
				
			/***** Capture AWB OPR026- Verifying Short Captured Data overwritten with XFFM Data*****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.verifySCCCodes("VerifySCCExists", cust.data("SCC1"));
			OPR026.verifySCCCodes("VerifySCCNotExists", cust.data("SCC"));
			OPR026.verifyOrigin("Org");
			OPR026.verifyDestination("Dest");
			OPR026.verifyStatedPieces("Pcs");
			OPR026.verifyStatedWeight("Weight1");
			cust.closeTab("OPR026", "Capture AWB");
			libr.quitBrowser();
					
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			//Login to "CGOCXML"
			driver.get(cgocxml[0]); 
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
					
			/**** XFWB Message loading with Updated Origin, Destination (CDG-AMS) Pieces, Weight, Volume and SCC  ****/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();
			
			driver=libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			//Switch role to Destination
			cust.switchRole("Destination", "FCTL", "RoleGroup");		
			
			/*** Import Manifest - Verifying XFFM Data overwritten with XFWB Data ***/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			String[]sccPresent1={cust.data("SCC")};
			String[]sccNotPresent1={cust.data("SCC1")};
			OPR367.verifySCCsAddedInULD("UldNum",sccPresent1);
			OPR367.verifySCCsNotPresentInULD("UldNum", sccNotPresent1);
			OPR367.verifyManifestedDetails("Pcs","Weight1","Pieces","Weight");
			OPR367.verifyAWBOriginAndDestinationInsideULD("UldNum","AWBNo","Origin","Destination");
			cust.closeTab("OPR367", "Import Manifest");
			
			/***** Capture AWB OPR026 - Verifying XFFM Data overwritten with XFWB Data *****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.verifySCCCodes("VerifySCCExists", cust.data("SCC"));
			OPR026.verifySCCCodes("VerifySCCNotExists", cust.data("SCC1"));
			OPR026.verifyOrigin("Origin");
			OPR026.verifyDestination("Destination");
			OPR026.verifyStatedPieces("Pieces");
			OPR026.verifyStatedWeight("Weight");
			cust.closeTab("OPR026", "Capture AWB");
			
		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

