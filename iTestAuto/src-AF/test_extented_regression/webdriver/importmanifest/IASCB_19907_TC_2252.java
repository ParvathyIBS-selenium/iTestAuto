package importmanifest;
import java.util.Date;
/**  Verify LPS time in Import Manifest Screen for Transit Shipment - Single onward flight  **/
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
import screens.Mercury;
import screens.ImportShipmentListing_OPR043;
import screens.ListMessages_MSG005;
import screens.MarkFlightMovements_FLT006;
import screens.MaintainOperationalFlight_FLT003;

public class IASCB_19907_TC_2252 extends BaseSetup {

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
	public MaintainFlightSchedule_FLT005 FLT005;
	public ImportShipmentListing_OPR043 OPR043;
	public MarkFlightMovements_FLT006 FLT006;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public ListMessages_MSG005 MSG005;
	public MaintainOperationalFlight_FLT003 FLT003;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\ImportManifest.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "ImportManifest_FT";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR043= new ImportShipmentListing_OPR043(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
	}


	@DataProvider(name = "TC_2252")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2252")
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

			/**** UPDATING GENERAL DETAILS IN MAP ****/
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 1, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 8, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 1, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 1, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));


			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_PL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_PL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_PL"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_PL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_PL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_PL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_PL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_PL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_PL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_PL"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_PL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_PL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_PL"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName",WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "WRO"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));


			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/** Flight-1 Creation **/
			cust.createFlight("FullFlightNumber");


			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum1 = FlightNum1.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo1", FlightNum1);
			map.put("FlightNo1", FlightNum1.substring(2));

			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeCDG=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 1, "DAY", "Europe/Paris");
			String STD=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",2);
			map.put("STDTime", STD.split(" ")[1]);
			String STA=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",5);
			map.put("STATime", STA.split(" ")[1]);
			map.put("STDDate", STD.split(" ")[0]);
			map.put("STADate", STA.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");		
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");



			/** Flight-2 Creation **/
			cust.createFlight("FullFlightNumber");

			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum2 = FlightNum2.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo2", FlightNum2.substring(2));

			FLT003.enterFlightDetails("Route2", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeCDG2=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 1, "DAY", "Europe/Paris");
			String STD2=cust.timeCalculation(currtimeCDG2, "dd-MMM-yyyy HH:mm","HOUR",5);
			map.put("STDTime", STD2.split(" ")[1]);
			String STA2=cust.timeCalculation(currtimeCDG2, "dd-MMM-yyyy HH:mm","HOUR",8);
			map.put("STATime", STA2.split(" ")[1]);
			map.put("STDDate", STD2.split(" ")[0]);
			map.put("STADate", STA2.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");		
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");



			/** Flight-3 Creation **/
			cust.createFlight("FullFlightNumber");

			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum3 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum3 = FlightNum3.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo3", FlightNum3);
			map.put("FlightNo3", FlightNum3.substring(2));

			FLT003.enterFlightDetails("Route2", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeCDG3=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 1, "DAY", "Europe/Paris");
			String STD3=cust.timeCalculation(currtimeCDG3, "dd-MMM-yyyy HH:mm","HOUR",8);
			map.put("STDTime", STD3.split(" ")[1]);
			String STA3=cust.timeCalculation(currtimeCDG3, "dd-MMM-yyyy HH:mm","HOUR",11);
			map.put("STATime", STA3.split(" ")[1]);
			map.put("STDDate", STD3.split(" ")[0]);
			map.put("STADate", STA3.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");		
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");


			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");


			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo2", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo2", cust.data("prop~FullAWBNo2"));
			map.put("AWBNo2", cust.data("prop~AWBNo"));		
			libr.quitBrowser();



			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/****** Loading XFBL for Second Flight ***/
			// Create the message XFBL with AWB1
			map.put("FullFlightNumber", cust.data("FullFlightNo2"));
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment1[] = { libr.data("FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String sccs[] = { cust.data("SCC")};
			String routings[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment1, sccs, routings);
			//Cgocxml.sendMessageCgoCXML("ICARGO");

			/****** Loading XFBL for Third Flight ***/
			// Create the message XFBL with AWB2
			map.put("FullFlightNumber", cust.data("FullFlightNo3"));
			String shipment2[] = { libr.data("FullAWBNo2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + libr.data("ShipmentDesc") };
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			cust.createXFBLMessage("XFBL_2", shipment2, sccs, routings);
			//Cgocxml.sendMessageCgoCXML("ICARGO");

			/***** XFWB Loading for AWB ***/
			// Create XFWB message
			map.put("awbnumber", cust.data("FullAWBNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			//Cgocxml.sendMessageCgoCXML("ICARGO");

			/***** XFWB Loading for AWB ***/
			// Create XFWB message
			map.put("awbnumber", cust.data("FullAWBNo2"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			//Cgocxml.sendMessageCgoCXML("ICARGO");

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
			+ ";" + cust.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC"),cust.data("SCC")};
			String routing1[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport"),
							cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
									+ cust.data("DestinationAirport")};
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			int []shipments={2};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing1, uld,shipments);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();		

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");


			/** Mark Flight Movement **/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode","FlightNo1","StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 1, "DAY", "Europe/Paris");
			map.put("ATATime", currtime);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","StartDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,startDate);
			String Date=FLT006.getATADate();
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");


			//*****OPR367 - Import Manifest*******/           
			//Verify the AWB details
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code","FlightNo1", "StartDate");	
			String ATA_Date_TimeText=Date+" "+currtime;
			int milestoneCalcTimeForCIQ =Integer.parseInt(cust.data("milestoneCalc").split(",")[0]);	

			//CIQ time for Flight F1
			Date CIQF1=OPR367.CIQCalculation(ATA_Date_TimeText, milestoneCalcTimeForCIQ);
			int milestoneCalcTimeForBuildupClosure =Integer.parseInt(cust.data("milestoneCalc").split(",")[1]);

			//Buildup closure time for Flight F2
			Date buildup_ClosureTimeF2=OPR367.buildupClosureTimeCalculation(STD2, milestoneCalcTimeForBuildupClosure);

			//Buildup closure time for Flight F3
			Date buildup_ClosureTimeF3=OPR367.buildupClosureTimeCalculation(STD3, milestoneCalcTimeForBuildupClosure);

			//verify LPS for Transit Shipment - Single onward flight
			Date lps=OPR367.min(CIQF1,buildup_ClosureTimeF2,buildup_ClosureTimeF3);
			OPR367.verifyLPSTimeForTransitShipment(lps, "UldNum");
			OPR367.closeTab("OPR367", "Import Manifest");

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