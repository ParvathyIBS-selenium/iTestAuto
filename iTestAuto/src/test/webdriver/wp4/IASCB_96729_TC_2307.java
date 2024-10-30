package wp4;
/**    Breakdown status filter in Breakdown List HHT screen - Intact ULD  **/
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
import screens.BreakdownListHHT;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ImportManifest_OPR367;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;

public class IASCB_96729_TC_2307 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public ImportManifest_OPR367 OPR367;
	public MarkFlightMovements_FLT006 FLT006;
	public MaintainFlightSchedule_FLT005 FLT005;
	public BreakdownListHHT bdlsthht;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "wp4";

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
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		bdlsthht = new BreakdownListHHT(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "IASCB_96729_TC_2307")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "IASCB_96729_TC_2307")
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

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));

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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR1"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR1"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR1"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR1"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR1"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR1"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR1"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR1"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR1"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR1"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));
			
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			 /** Switch role to Origin **/
			//cust.switchRole("Origin", "FCTL", "RoleGroup");

//			// Checking AWB is fresh or Not (AWBNumber1)
//			cust.searchScreen("OPR026", "Capture AWB");
//			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
//			libr.waitForSync(1);
//			// AWBNumber1
			map.put("awbNumber1",cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			//map.put("awb1",cust.data("prop~AWBNo"));
			map.put("awb1",cust.data("16491171"));
			
            
//			// Checking AWB is fresh or Not (AWBNumber2)
//			cust.searchScreen("OPR026", "Capture AWB");
//			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
//			libr.waitForSync(1);
			// AWBNumber2
			map.put("awbNumber2", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
		//	map.put("awb2",cust.data("prop~AWBNo"));
			map.put("awb2",cust.data("16491182"));

//			/** Flight Creation **/
//			cust.createFlight("FullFlightNumber");
//            // Maintain Flight Screen (FLT005) . Taking fresh flight
//            cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
//			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
//			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			//libr.quitBrowser();
			
//			//Relaunch browser
//			driver=libr.relaunchBrowser("chrome");
//			//Login to "MERCURY"
//			String[] mercury = libr.getApplicationParams("mercury");
//			driver.get(mercury[0]); // Enters URL
//			cust.loginToMercury(mercury[1], mercury[2]);
//			
//			/** SSM Message loading **/
//			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
//			mercuryScreen.clickSendMessage();
//			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
//			mercuryScreen.sendMessageInMercury();
//			mercuryScreen.verifyMsgStatus("SSM");
//			libr.quitBrowser();
//
//			// Relaunch browser
//			driver = libr.relaunchBrowser("chrome");
//
//			/*** Login to cgocxml **********/
//            String[] cgocxml = libr.getApplicationParams("cgocxml");
//			driver.get(cgocxml[0]); // Enters URL
//			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

		    String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc")};
			String scc[] = { cust.data("SCC"), cust.data("SCC")};

//			/** XFWB Message loading for AWB 1 **/
//			map.put("awbNumber", cust.data("awbNumber1"));
//			// Create XFWB message
//			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
//			Cgocxml.sendMessageCgoCXML("ICARGO");
//
//			/** XFWB Message loading for AWB 2 **/
//			map.put("awbNumber", cust.data("awbNumber2"));
//			// Create XFWB message
//			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
//			Cgocxml.sendMessageCgoCXML("ICARGO");
//			
//			/** XFFM Message loading **/
//			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
//			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
//			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));
//
//			// ULD Number
//			String uldNo = cust.create_uld_number("UldType", "carrierCode");
//			map.put("UldNum", uldNo);
//			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
//
//			String uldNo1 = cust.create_uld_number("UldType", "carrierCode");
//			map.put("UldNum1", uldNo1);
//			map.put("ULDNo1", cust.data("UldNum1").replaceAll("[^0-9]", ""));
//
//			excelRead.writeDataInExcel(map, path1, sheetName, testName);
//			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
//
//			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")+ ";" + cust.data("DestinationAirport"),cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
//			+ ";" + cust.data("DestinationAirport")};
//			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode"),cust.data("UldType") + ";" + cust.data("ULDNo1") + ";" + cust.data("carrierCode") };
//
//			// Create XFFM message
//			cust.createXFFMMessage("XFFM", shipment, scc, routing1, uld);
//			Cgocxml.sendMessageCgoCXML("ICARGO");
//			libr.quitBrowser();
//
//			// Relaunch browser
//			driver = libr.relaunchBrowser("chrome");
//            // Re-Login to iCargo STG
//             driver.get(iCargo[0]);
//			Thread.sleep(2000);
//			cust.loginICargoSTG(iCargo[1], iCargo[2]);
//			Thread.sleep(2000);
			
			/**Switch role to Destination**/
			cust.switchRole("Destination", "Origin", "RoleGroup");
			
			/** Mark Flight Movement **/
            cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode","FlightNo","StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","StartDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,startDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
			
			/******** Import Manifest *********/
            cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode","FlightNo", "StartDate");
			OPR367.maximizeAllDetails();
			OPR367.verifyShipment("awb1");
			OPR367.verifyShipment("awb2");
			//String uldss[]={cust.data("UldNum"),cust.data("UldNum1")};
			String uldss[]={"AKE41366AF","AKE66431AF"};
			OPR367.verifyUldDetails(2,uldss);			
			OPR367.SaveDetails();
			cust.closeTab("OPR367", "Import Manifest");		
			
			/******** Import Manifest *********/
            cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode","FlightNo", "StartDate");			
			
			//Completed ULD
			String uldNo="AKE41366AF";
			OPR367.clickCheckBox_ULD(uldNo);
			OPR367.clickBreakdownButton();
			OPR367.clickBreakdownComplete();		
	    	OPR367.closeFromOPR004();			
		
	    	cust.closeTab("OPR367", "Import Manifest");			
			libr.quitBrowser();
			
//			/***Launch emulator - hht**/
//			libr.launchApp("hht-app-release");		
//
//			//Login in to HHT
//			String [] hht=libr.getApplicationParams("hht");	
//			cust.loginHHT(hht[0], hht[1]);
//
//			/*** HHT - LIST BREAKDOWN ****/
//			bdlsthht.invokeBreakdownListHHTScreen();
//			bdlsthht.selectBDInstruction("val~Intact Unit");
			//bdlsthht.unselectHandlingArea();
//			bdlsthht.clickNext();
//			
//			bdlsthht.clickBreakdownListFilter();				
//			bdlsthht.verifyBreakDownStatusFilter();			
//			map.put("FlightNum", cust.data("carrierCode") + "-" + cust.data("FlightNo"));
//			bdlsthht.selectFlight("FlightNum");
//							
//			//Verifying Breakdown Status filter - Completed
//			bdlsthht.selectBDStatus("val~Completed",true);
//			bdlsthht.clickOK();
//			bdlsthht.verifyStatusFilters(true,"UldNum");
//			bdlsthht.verifyStatusFilters(false,"UldNum1");
//		
//			//Verifying Breakdown Status filter - Not Started
//			bdlsthht.clickBreakdownListFilter();	
//			bdlsthht.selectBDStatus("val~Completed",false);
//			bdlsthht.selectBDStatus("val~Not Started",true);
//			bdlsthht.clickOK();
//			bdlsthht.verifyStatusFilters(false,"UldNum");
//			bdlsthht.verifyStatusFilters(true,"UldNum1");				
//			libr.quitApp();
			
				
		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
} 
			