package iascb_212839;

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
import screens.MaintainFlightSchedule_FLT005;
import screens.CaptureAWB_OPR026;
import screens.ListMessages_MSG005;
import screens.ImportManifest_OPR367;
import screens.TransportOrderListing;
import screens.UldSightingHHT;
import screens.MarkFlightMovements_FLT006;
import screens.HandlingAreaSetUpScreen_WHS008;
import screens.WarehouseShipmentEnquiry_WHS011;

//TC_09 _Verify TO is generated from staging location to avaiable location after ULD sighting is  completed for an intact ULD



public class IASCB_212839_TC_14666 extends BaseSetup {


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
	public ListMessages_MSG005 MSG005;
	public ImportManifest_OPR367 OPR367;
	public TransportOrderListing to;
	public UldSightingHHT uldsighthht;
	public MarkFlightMovements_FLT006 FLT006;
	public HandlingAreaSetUpScreen_WHS008 WHS008;
	public WarehouseShipmentEnquiry_WHS011 WHS011;



	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "iascb_212839";


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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		uldsighthht=new UldSightingHHT(driver, excelreadwrite, xls_Read);
		WHS008= new HandlingAreaSetUpScreen_WHS008(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		WHS011=new WarehouseShipmentEnquiry_WHS011(driver,excelreadwrite,xls_Read); 

	}

	@DataProvider(name = "TC_14666")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_14666")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
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
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			// creating flight number
			cust.createFlight("FullFlightNumber");

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMMM",0, "DAY", ""));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());

			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");

			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);      			


			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code")+cust.data("prop~flightNo"),proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			/** MSG005 - List Messages **/

			//SSM Message loading
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");


			//XFWB Message loading
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);


			//XFFM Message loading
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));

			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			//	Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing1, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			MSG005.closeTab("MSG005", "List Messages");



			/**Switch role to Destination**/
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode", "FlightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","StartDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,startDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");


			/***Launch emulator - uldsighting app**/       
			libr.launchUldSightingApp("uldsighting-app");
			//Login in to ULD Sighting App
			String [] hht=libr.getApplicationParams("hht");		
			cust.loginHHT(hht[0], hht[1]);		

			uldsighthht.clickDone();					
			uldsighthht.enterUldNumber("UldNum");
			//Entering the sighting location
			map.put("SightingLocation_CDG", WebFunctions.getPropertyValue(toproppath, "SightingLocation_CDG"));		
			uldsighthht.selectSightingLocation("SightingLocation_CDG");
			uldsighthht.clickSight();
			//Entering the FWD zone
			map.put("SightingZone", WebFunctions.getPropertyValue(toproppath, "SightingZone"));		
			uldsighthht.selectFwLocationAfterSighting("SightingZone");	
			uldsighthht.clickComplete() ;	
			libr.quitApp();



			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);


			//Verifying TO is triggered to a location in the Forward Zone 
			to.searchShipment("UldNum");
			//fetch the src location
			String srcLocation=to.retrieveSrcLocation("UldNum");
			map.put("srcLocation", srcLocation);

			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("UldNum", "SightingLocation_CDG");

			//fetch destination location
			String destnLocation=to.retrieveDestnLocation("UldNum");
			map.put("destnLocation", destnLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("UldNum", "val~Open", "SightingLocation_CDG");

			libr.quitApp();
	

			/**** WHS008 -HandlingAreaSetUpScreen ****/
			cust.searchScreen("WHS008", "Handling Area Set Up");
			int verfCols [] = {3};
			//Verifying destination location and zone for ULD
			String[] actVerfValues= {WebFunctions.getPropertyValue(toproppath, "SightingZone")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("destnLocation", verfCols, actVerfValues);
			cust.closeTab("WHS008", "Handling Area Set Up");


			/** WAREHOUSE shipment Enquiry screen  - WHS009 **/
			cust.searchScreen("WHS011", "Warehouse shipment Enquiry");
			WHS011.enterSU("UldNum");
			WHS011.clickList();
			WHS011.clickAWBcheckBox();
			WHS011.clickSURelocation();
			//Relocated done
			map.put("ExitSightingLocation_CDG", WebFunctions.getPropertyValue(toproppath, "ExitSightingLocation_CDG"));		
			WHS011.SURelocationDetails("ExitSightingLocation_CDG");
			cust.closeTab("WHS011", "Warehouse shipment Enquiry");

			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);


			//Verifying TO is triggered to a location in the Forward Zone 
			to.searchShipment("UldNum");
			//fetch the src location
			String srcLocation2=to.retrieveSrcLocation("UldNum");
			map.put("srcLocation2", srcLocation2);

			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("UldNum", "ExitSightingLocation_CDG");

			//fetch destination location
			String destnLocation2=to.retrieveDestnLocation("UldNum");
			map.put("destnLocation2", destnLocation2);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("UldNum", "val~Open", "ExitSightingLocation_CDG");

			libr.quitApp();

			/**** WHS008 -HandlingAreaSetUpScreen ****/
			cust.searchScreen("WHS008", "Handling Area Set Up");
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("destnLocation", verfCols, actVerfValues);
			cust.closeTab("WHS008", "Handling Area Set Up");



		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}		

}

