package exportmanifest;


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
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreening_OPR339;
import screens.ImportManifest_OPR367;
import screens.AFLS_Booking;
import screens.AFLS_FlightPlan;
import screens.BreakDownScreen_OPR004;
import screens.CGOICSS;
import screens.ExportShipmentListing_OPR030;

/**
 *  Verify the provision to view breakdown information for transit shipment arriving in multiple flight
 */


public class IASCB_19208_TC_2497_AF extends BaseSetup {

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
	public MaintainOperationalFlight_FLT003 FLT003;
	public ImportManifest_OPR367 OPR367;
	public BreakDownScreen_OPR004 OPR004;
	public ExportShipmentListing_OPR030 OPR030;
	public AFLS_Booking afls;
	public AFLS_FlightPlan aflsfp;
	public CGOICSS Cgoicss;
	public MarkFlightMovements_FLT006 FLT006;
	
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\ExportManifest.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "ExportManifest_FT";

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
		Cgoicss = new CGOICSS(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR030 = new ExportShipmentListing_OPR030(driver, excelreadwrite, xls_Read);
		afls=new AFLS_Booking(driver, excelreadwrite, xls_Read);
		aflsfp=new AFLS_FlightPlan(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);

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

			String startDate = cust.createDateFormat("dd-MMM-YYYY",1, "DAY", "");
			String EndDate = cust.createDateFormat("dd-MMM-YYYY", 8, "DAY", "");
			String expSTDtime="";
			String bookingDate = cust.createDateFormat("dd/MMM/YYYY",1, "DAY", "");
			map.put("BookDate", bookingDate);
			String endDate =cust.createDateFormat("dd/MMM/YYYY", 8, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 1, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 1, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 1, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 1, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 1, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 1, "DAY", ""));
			
			
			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerId_US"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerName_US"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerpostCode_US"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerstreetName_US"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercityName_US"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountryId_US"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountryName_US"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountrySubdivision_US"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "paycargoCustomertelephoneNo_US"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "paycargoCustomeremail_US"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));

			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury",WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			
			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			
			/** Flight Creation 1 **/
			
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, EndDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum1 = FlightNum1.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo1", FlightNum1);
			map.put("FlightNo1", FlightNum1.substring(2));

			
			/** Flight Creation 2 **/
			
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo1", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber1", cust.data("carrierCode") + cust.data("prop~flightNo1"), proppath);
			
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo1", startDate, EndDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber1");
			FlightNum2 = FlightNum2.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo2", FlightNum2.substring(2));
			
			/** Flight Creation 3 **/
			
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo2", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber2", cust.data("carrierCode") + cust.data("prop~flightNo2"), proppath);
			
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo2", startDate, EndDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum3 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			FlightNum3 = FlightNum3.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo3", FlightNum3);
			map.put("FlightNo3", FlightNum3.substring(2));
			
			libr.quitBrowser();
			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");	

			//Login to "CGOICSS"
			String[] cgoicsslogin = libr.getApplicationParams("Cgoicss");
			driver.get(cgoicsslogin[0]); // Enters URL
			cust.loginToCGOICSS(cgoicsslogin[1], cgoicsslogin[2]);

			/** Flight Creation 1 **/
			Cgoicss.clickOnCreateFlight();
			Cgoicss.createNewFlight("carrierCode","FlightNo1", "BookDate", "EndDate");
			Cgoicss.selectDayofOperations();
			Cgoicss.addLeg();
			Cgoicss.enterLegDetails("ATD_Local","ATA_Local", "Origin", "Transit", "serviceType", "AircraftType", "carrierCode");
			Cgoicss.clickSave();
			
			
			/** Flight Creation 2 **/	
			Cgoicss.clickOnCreateFlight();
			Cgoicss.createNewFlight("carrierCode","FlightNo2", "BookDate", "EndDate");
			Cgoicss.selectDayofOperations();
			Cgoicss.addLeg();
			Cgoicss.enterLegDetails("ATD_Local","ATA_Local", "Origin", "Transit", "serviceType", "AircraftType", "carrierCode");
			Cgoicss.clickSave();
			
			/** Flight Creation 3 **/
		
			String bookingDate1 =cust.createDateFormat("dd/MMM/YYYY",2, "DAY", "");
			map.put("BookDate1", bookingDate1);
			String endDate1 = cust.createDateFormat("dd/MMM/YYYY", 9, "DAY", "");
			map.put("EndDate1", endDate1);
			
			String startDate1 = cust.createDateFormat("dd-MMM-YYYY",2, "DAY", "");
			map.put("StartDate1", startDate1);
			map.put("EndDate1", endDate1);

			Cgoicss.clickOnCreateFlight();
			Cgoicss.createNewFlight("carrierCode","FlightNo3", "BookDate1", "EndDate1");
			Cgoicss.selectDayofOperations();
			Cgoicss.addLeg();
			Cgoicss.enterLegDetails("ATD_Local2","ATA_Local2", "Transit", "Destination", "serviceType", "AircraftType", "carrierCode");
			Cgoicss.clickSave();
			
			libr.quitBrowser();
			
			
			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to AFLS_BOOKING **********/

			String[] aflsbooking = libr.getApplicationParams("afls");
			driver.get(aflsbooking[0]);
			cust.loginToAFLS(aflsbooking[1], aflsbooking[2]);

			afls.selectTitleAndSubTitleTab("titleTab","titleTab");
			afls.enterAWB("CarrierNumericCode","AWBNo");
			afls.enterAWBOrgAndDest("Origin", "Destination");
			afls.enterBookingOrgAndDest("Origin", "Destination");
			afls.enterBookingDeliveryAndArrivalDate("BookDate", "BookDate1");
			afls.enterBookingDeliveryAndArrivalTime("ATD_Local","ATA_Local2");
			afls.selectCommodityCode("CommodityCode");
			afls.selectServiceLevelAndHandlingNeeds("serviceLevel", "handlingNeeds");
			afls.selectConditionalSCC("SCC");
			afls.enterCustomerID("AgentCode");
			afls.enterFlightInfo("carrierCode","FlightNo1","Origin", "Transit", "BookDate");
			afls.clickAddNew();
			afls.enterFlightSecondInfo("carrierCode","FlightNo3","Transit", "Destination", "BookDate1");
			afls.enterShipmentDetails("Pieces","Weight","Volume");
			afls.enterRateDetails("IATARate");
			afls.selectRouteSearchAndEvaluationSetting("no");
			afls.clickSubmitBooking();
			
			//Part Booking of shipment to another flight
			
			afls.clickPartBooking();
			afls.enterPartshipmentAWBDetails("CarrierNumericCode","AWBNo");
			afls.clickSearch();
			afls.clickEditButtonIcon();
			afls.clickFlightInfoTab();
			afls.editPcsWgtInfo("Pieces1","Weight1","Volume1");
			afls.clickSave();
			
			afls.clickPartBooking();
			afls.enterPartshipmentAWBDetails("CarrierNumericCode","AWBNo");
			afls.clickSearch();
			afls.enterAwbSuffix("A");
			afls.clickCreatePartBooking();
			afls.clickFlightInfoTab();
			afls.enterFlightInfoWithShipmentDetails("carrierCode","FlightNo2","Origin", "Transit", "BookDate","Pieces2","Weight2","Volume2");
			afls.clickSave();
			
			afls.clickPartBooking();
			afls.enterPartshipmentAWBDetails("CarrierNumericCode","AWBNo");
			afls.clickSearch();
			afls.clickPartSubmitBooking();
			libr.quitBrowser();

			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			//Login to ALFS_FlightPlan		 
			cust.loginToAFLS_FlightPlan();

			aflsfp.clickMenu();
			aflsfp.selectMenuOption("val~Flight plan");
			aflsfp.enterFlightDetails("carrierCode", "FlightNo3", "BookDate1","BookDate1");
			aflsfp.enterFlightBoardPoint("Transit");
			aflsfp.clickSearch();

			//FBL Trigger
			aflsfp.clickSend();
			aflsfp.selectSendMessages("val~FBL");
		
			libr.quitBrowser();
			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/** XFWB Message loading **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_Transit_NoFlight", true);	
		
			/****** XFFM LOADING PART 1****/
			
			map.put("FullFlightNumber", cust.data("FullFlightNo1"));
			
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));
			
			//ULD Number
			String uldNo1=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1",uldNo1);
			map.put("ULDNo", cust.data("UldNum1").replaceAll("[^0-9]", ""));
			System.out.println(cust.data("UldNum1"));
			
			map.put("Pcs", cust.data("Pieces1"));
			map.put("Wgt", cust.data("Weight1"));
			map.put("Vol", cust.data("Volume1"));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String ship1[] = { cust.data("FullAWBNo") + ";" + cust.data("Pieces1")+ ";" + cust.data("Weight1")
					+ ";" + cust.data("Volume1") + ";" + cust.data("ShipmentDesc")};
			String scc1[] = { cust.data("SCC") + ";" + cust.data("val~SPX") };
			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport")};
			String uld1[] = { cust.data("UldType")+";"+ cust.data("ULDNo")+";"+cust.data("carrierCode")};

			// Create XFFM message
			cust.createXFFMMessage("XFFM", ship1, scc1, routing1, uld1);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			
			/****** XFFM LOADING PART2****/
			
			map.put("FullFlightNumber", cust.data("FullFlightNo2"));
			
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			//ULD Number
			String uldNo2=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum2", uldNo2);
			map.put("ULDNo", cust.data("UldNum2").replaceAll("[^0-9]", ""));
			
			map.put("Pcs", cust.data("Pieces2"));
			map.put("Wgt", cust.data("Weight2"));
			map.put("Vol", cust.data("Volume2"));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String ship2[] = { cust.data("FullAWBNo") + ";" + cust.data("Pieces2") + ";" + cust.data("Weight2")
					+ ";" + cust.data("Volume2") + ";" + cust.data("ShipmentDesc")};
			String scc2[] = { cust.data("SCC") + ";" + cust.data("val~SPX") };
			String routing2[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport")};
			String uld2[] = { cust.data("UldType")+";"+ cust.data("ULDNo")+";"+cust.data("carrierCode")};

			// Create XFFM message
			cust.createXFFMMessage("XFFM", ship2, scc2, routing2, uld2);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");
			
			// Switch role
			cust.switchRole("Transit", "FCTL", "RoleGroup");
						
			
			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode","FlightNo1","StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
			
			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode","FlightNo2","StartDate");
			String currtime1=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime1);
			String currDate1=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime1,currDate1);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
						
			
			/** OPR030-Export Shipment Listing **/
			
			cust.searchScreen("OPR030", "Export Shipment Listing");
			OPR030.enterAWB("AWBNo", "prop~CarrierNumericCode");
			OPR030.clearOrigin();
			OPR030.selectFilterMode("val~Journey Date Mode");
			OPR030.clickFromToDate(cust.data("StartDate1"));
			OPR030.clickList();

			//verifying existing column Accepted Pcs/Wgt/Vol column is renamed to Breakdown/Accepted Pcs/Wgt/Vol
			String[] colName={"Accepted / Breakdown Pcs/Wgt/Vol (kg/CBM)"};
			OPR030.verifyColumn(colName);			
			//verifying Accepted pcs/wgt/vol of the shipment before breakdown at the transit			
			int verfCols [] = {16};
			String[] actVerfValues = { "0"+" / "+"0"+" / "+"0" };
			OPR030.verifyTableRecords(verfCols, actVerfValues, "FullAWBNo");
			cust.closeTab("OPR030", "Export Shipment Listing");

			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code", "FlightNo1", "StartDate");
			OPR367.clickCheckBox_ULD(cust.data("UldNum1"));
			OPR367.clickBreakdownButton();
			OPR367.clickBreakdownComplete();	
			OPR367.ClickYesAlert();
			OPR367.closeFromOPR004();
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");

			/** OPR030-Export Shipment Listing **/
			//Verifying broken down pcs/wgt/vol of Flight 1 for the transit shipmnet
			cust.searchScreen("OPR030", "Export Shipment Listing");
			OPR030.enterAWB("AWBNo", "prop~CarrierNumericCode");
			OPR030.clearOrigin();
			OPR030.selectFilterMode("val~Journey Date Mode");
			OPR030.clickFromToDate(cust.data("StartDate1"));
			OPR030.clickList();
			int verfCols2 [] = {16};
			String[] actVerfValues2 = { cust.data("Pieces1")+" / "+cust.data("Weight1")+" / "+cust.data("Volume1") };
			OPR030.verifyTableRecords(verfCols2, actVerfValues2, "FullAWBNo");
			cust.closeTab("OPR030", "Export Shipment Listing");

			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code", "FlightNo2", "StartDate");
			OPR367.clickCheckBox_ULD(cust.data("UldNum2"));
			OPR367.clickBreakdownButton();
			OPR367.clickBreakdownComplete();
			OPR367.ClickYesAlert();
			OPR367.closeFromOPR004();
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");
			
			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("FlightNo3", "StartDate1");
			
			//extract Flight departure time
			
		    expSTDtime =FLT003.getFlightDepartureTime();
		    
		    cust.closeTab("FLT003", "Mainain opertaional Flight");

			
		    /** OPR030-Export Shipment Listing **/
			//Verifying total broken down pcs/wgt/vol for the transit shipmnet
			cust.searchScreen("OPR030", "Export Shipment Listing");
			OPR030.enterAWB("AWBNo", "prop~CarrierNumericCode");
			OPR030.clearOrigin();
			OPR030.selectFilterMode("val~Journey Date Mode");
			OPR030.clickFromToDate(cust.data("StartDate1"));
			OPR030.clickList();
			int verfCols3 [] = {16};
			String[] actVerfValues3 = { cust.data("Pieces")+" / "+cust.data("Weight")+" / "+cust.data("Volume") };
			OPR030.verifyTableRecords(verfCols3, actVerfValues3, "FullAWBNo");
            OPR030.verifyPlannedFlight(cust.data("FullFlightNo3"), cust.data("StartDate1")+" "+expSTDtime+"(S)"); 
			cust.closeTab("OPR030", "Export Shipment Listing");


		} catch (Exception e) {
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