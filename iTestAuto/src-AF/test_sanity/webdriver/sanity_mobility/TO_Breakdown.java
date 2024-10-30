package sanity_mobility;


import java.util.Map;

import org.testng.Assert;
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
import screens.ExportManifest_OPR344;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.TransportOrderListing;
import screens.UldSightingHHT;
import screens.SecurityAndScreening_OPR339;
import screens.GoodsAcceptance_OPR335;
import screens.CaptureCheckSheet_CHK002;
import screens.WarehouseSetUpEnquiry_WHS013;
import screens.BreakdownHHT;


public class TO_Breakdown extends BaseSetup {

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
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public ExportManifest_OPR344 OPR344;
	public MaintainOperationalFlight_FLT003 FLT003;
	public GoodsAcceptance_OPR335 OPR335;
	public SecurityAndScreening_OPR339 OPR339;
	public TransportOrderListing to;
	public Cgocxml Cgocxml;
	public CaptureCheckSheet_CHK002 CHK002;
	public WarehouseSetUpEnquiry_WHS013 WHS013;
	public BreakdownHHT bdhht;
	public ListMessages_MSG005 MSG005;
	
	public UldSightingHHT uldsighthht;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	public static String improppath = "\\src\\resources\\IM.properties";
	String sheetName = "to_breakdown";

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
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		CHK002= new CaptureCheckSheet_CHK002(driver, excelreadwrite, xls_Read);
		WHS013=new WarehouseSetUpEnquiry_WHS013(driver,excelreadwrite,xls_Read); 
		bdhht = new BreakdownHHT(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		uldsighthht=new UldSightingHHT(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "to_breakdown")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "to_breakdown")
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

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);

			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());

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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeCDG=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			System.out.println(currtimeCDG);
			String STD=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",3);
			System.out.println(STD.split(" ")[1]);
			map.put("STDTime", STD.split(" ")[1]);
			String STA=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",5);
			System.out.println(STA.split(" ")[1]);
			map.put("STATime", STA.split(" ")[1]);
			map.put("STDDate", STD.split(" ")[0]);
			map.put("STADate", STA.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");		
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");

			/** XFBL Message loading **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = {cust.data("SCC")};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);

			/** XFWB Message loading **/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);
			
			/**XFFM Message Loading with ULD **/
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

		String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };

			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing1, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");  

			/**Switch role to Destination**/
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("prop~flightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
           

			/***Launch emulator - uldsighting app**/       
			libr.launchUldSightingApp("uldsighting-app");
			//Login in to ULD Sighting App
			String [] hht=libr.getApplicationParams("hht");		
			cust.loginHHT(hht[0], hht[1]);		
	
			uldsighthht.clickDone();					
			uldsighthht.enterUldNumber("UldNum");
			
			map.put("SightingIMLoc", WebFunctions.getPropertyValue(improppath, "SightingIMLoc"));
			uldsighthht.selectFwLocationBeforeSighting("SightingIMLoc");	
			uldsighthht.clickSight();
			uldsighthht.verifySighted("UldNum");
		    uldsighthht.clickComplete() ;	
			libr.quitApp();

			map.put("AWB", cust.data("CarrierNumericCode") + cust.data("AWBNo"));
			map.put("SU", cust.data("AWB")+"001");
	        

			/**** Import Manifest ***/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.selectBDInstructionforULD("UldNum","BDInstruction");
			OPR367.maximizeAllDetails();
			OPR367.verifyShipment("AWBNo");
			String ULDNo[]={cust.data("UldNum")};
			//verify uld details
			OPR367.verifyUldDetails(1,ULDNo);
			OPR367.SaveDetails();
			cust.closeTab("OPR367", "Import Manifest");

			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			// Login in to HHT
		
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - BREAKDOWN ***/
			bdhht.invokeBreakdownHHTScreen();
			bdhht.enterValue("UldNum");
			bdhht.selectAwb("FullAWBNo");
			map.put("Breakdown_Location", WebFunctions.getPropertyValue(toproppath, "Breakdown_Location"));
			bdhht.enterLocation("Breakdown_Location");
			bdhht.selectMultipleSCC(scc);
			bdhht.addPcs("Pieces");	
			bdhht.clickSave();
			cust.clickBack("Breakdown");

			//Marking BreakdownComplete
			bdhht.enterValue("UldNum");
			bdhht.clickMoreOptions();
			bdhht.clickBreakdownCompleteBtn();
			cust.clickBack("Breakdown");
			libr.quitApp();

			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);
         	to.searchShipment("SU");
			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU", "Breakdown_Location");

			//fetch destination location
			String storageAreaLocation=to.retrieveDestnLocation("SU");
			map.put("storageAreaLocation", storageAreaLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "Breakdown_Location");

			
			
			
			/**** WHS013 -Warehouse Setup Enquiry ****/

			//verifying zone of the destination location
			cust.searchScreen("WHS013", "Warehouse Setup Enquiry");
			WHS013.enterLocation("storageAreaLocation");
			WHS013.clickList();
			String StorageZone=WHS013.getZoneCode();
			map.put("Breakdown_StorageAreaZone_Import_CDG", WebFunctions.getPropertyValue(toproppath, "Breakdown_StorageAreaZone_Import_CDG"));
			WHS013.verifyZone(cust.data("Breakdown_StorageAreaZone_Import_CDG"),StorageZone);
			cust.closeTab("WHS013", "Warehouse Setup Enquiry");

			
			/*** TRANSPORT ORDER  ***/

			//Verifying TO generated in the TO app
			to.clickRefresh();
			to.searchShipment("SU");
			//completing the relocation task for SU
			to.selectTask("storageAreaLocation");
			to.confirmTaskList();
			to.clickRelocationComplete("storageAreaLocation");
			libr.quitApp();


		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}
