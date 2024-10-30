package breakdown;


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
import rest_unitdloc.JSONBody;
import screens.CaptureAWB_OPR026;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.RelocationTaskMonitor_WHS052;
import screens.TransportOrderListing;
import screens.UldSightingHHT;
import screens.WarehouseShipmentEnquiry_WHS011;
import screens.WarehouseSetUpEnquiry_WHS013;


/**
 * TC_13_Verify that TO is generated to storage area for INTACT - CDG
 **/

public class IASCB_45883_TC_8105 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public MaintainOperationalFlight_FLT003 FLT003;
	public TransportOrderListing to;
	public RelocationTaskMonitor_WHS052 WHS052;
	public UldSightingHHT uldsighthht;
	public JSONBody jsonbody;
	public WarehouseShipmentEnquiry_WHS011 WHS011;
	public ListMessages_MSG005 MSG005;
	public WarehouseSetUpEnquiry_WHS013 WHS013;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Breakdown.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "Breakdown_FT";

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
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		WHS052=new RelocationTaskMonitor_WHS052(driver, excelreadwrite, xls_Read);
		uldsighthht=new UldSightingHHT(driver, excelreadwrite, xls_Read);
		jsonbody=new JSONBody(driver, excelreadwrite, xls_Read);
		WHS011=new WarehouseShipmentEnquiry_WHS011(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		WHS013=new WarehouseSetUpEnquiry_WHS013(driver,excelreadwrite,xls_Read);

	}

	@DataProvider(name = "TC_8105")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_8105")
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
			cust.loginICargoSTG(iCargo[1], iCargo[2]);


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


			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));


			/** Flight Creation **/	
			cust.createFlight("FullFlightNumber");

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));


			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));


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
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = {cust.data("SCC")};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);

			/** XFWB Message loading **/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);

			/** -XFFM Message loading **/

			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));

			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum",uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			String uldNo1 = cust.create_uld_number("UldType1", "carrierCode");
			map.put("UldNum1", uldNo1);
			map.put("ULDNo1", cust.data("UldNum1").replaceAll("[^0-9]", ""));
			
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			
			String shipment1[] = {
					cust.data("FullAWBNo") + ";" + cust.data("Pieces1") + ";" + cust.data("Weight1") + ";"
							+ cust.data("Volume1") + ";" + cust.data("ShipmentDesc"),
							cust.data("FullAWBNo") + ";" + cust.data("Pieces2") + ";" + cust.data("Weight2") + ";"
									+ cust.data("Volume2") + ";" + cust.data("ShipmentDesc")};



			String routing1[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport"),
							cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
									+ cust.data("DestinationAirport"),};

			String uld1[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") ,cust.data("UldType1") + ";" + cust.data("ULDNo1") + ";" + cust.data("carrierCode")};
			String scc1[] = { cust.data("SCC"), cust.data("SCC")};
			
			cust.createXFFMMessage("XFFM", shipment1, scc1, routing1, uld1);
			
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");  


			/** Switch role to Destination **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/**Mark Flight Movement**/

			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("FlightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");

			/**** Import Manifest ***/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.verifyBreakdownInstructionsTagforULD("UldNum","val~Intact");
			OPR367.verifyBreakdownInstructionsTagforULD("UldNum1","val~Intact");
			cust.closeTab("OPR367", "Import Manifest");
			

			/***Launch emulator - uld sighting app**/
			libr.launchUldSightingApp("uldsighting-app");

			//Login in to ULD Sighting App
			String [] hht=libr.getApplicationParams("hht");		
			cust.loginHHT(hht[0], hht[1]);	

			uldsighthht.clickDone();			
			uldsighthht.enterUldNumber("UldNum");
			map.put("TEPEntryLocation", WebFunctions.getPropertyValue(toproppath, "TEPEntryLocation"));			
			uldsighthht.selectFwLocationBeforeSighting("TEPEntryLocation");	
			uldsighthht.clickSight();
			uldsighthht.verifySighted("UldNum");
			uldsighthht.clickCaptureWeightBtn();
			uldsighthht.captureActualWeight("Weight");
			uldsighthht.enterOverhangDetails("val~10", "val~10","val~20","val~20");
			uldsighthht.clickPrintTag();
			uldsighthht.clickComplete();
			
			uldsighthht.enterUldNumber("UldNum1");
			map.put("TEPEntryLocation", WebFunctions.getPropertyValue(toproppath, "TEPEntryLocation"));			
			uldsighthht.selectFwLocationBeforeSighting("TEPEntryLocation");	
			uldsighthht.clickSight();
			uldsighthht.verifySighted("UldNum1");
			uldsighthht.clickCaptureWeightBtn();
			uldsighthht.captureActualWeight("Weight");
			uldsighthht.enterOverhangDetails("val~10", "val~10","val~20","val~20");
			uldsighthht.clickPrintTag();
			uldsighthht.clickComplete();
			
			
			libr.quitApp(); 

			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code", "prop~flightNo", "StartDate");
			//Verify auto breakdown happens
			OPR367.verifyBreakdownImageForMultipleUlds("green","Completed",cust.data("UldNum"));
			OPR367.verifyBreakdownImageForMultipleUlds("green","Completed",cust.data("UldNum1"));
			OPR367.closeTab("OPR367", "Import Manifest");

			/******* POST REQUEST****/	
			//Trigger RelocateStorageUnit to the exit point of IM 
			map.put("EquipmentID", WebFunctions.getPropertyValue(toproppath, "EquipmentID"));
			map.put("IMExitTargetLocation", WebFunctions.getPropertyValue(toproppath, "IMExitTargetLocation"));
			jsonbody.postRequest(cust.data("EquipmentID"),cust.data("UldNum"),cust.data("IMExitTargetLocation"),cust.data("OccupancyStatus"),cust.data("WareHouse"));
			libr.waitForSync(8);


			/*** WHS011 - WAREHOUSE SHIPMENT ENQUIRY ***/
			cust.searchScreen("WHS011", "Warehouse Shipment Enquiry");
			WHS011.enterAWBdetails("CarrierNumericCode","AWBNo");
			WHS011.clickList();
			//Verify Location of uld at the entrance of IM
			int verfCol[]={4};  
			map.put("IMExitLocation", WebFunctions.getPropertyValue(toproppath, "IMExitLocation"));
			String[] actVerfVal={cust.data("IMExitLocation")};
			WHS011.verifyWarehouseDetailsWithPmKey(verfCol, actVerfVal,"UldNum");
			cust.closeTab("WHS011", "Warehouse Shipment Enquiry");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);

			to.searchShipment("UldNum");
			//fetch the src location
			String srcIMExitLocation=to.retrieveSrcLocation("UldNum");
			map.put("srcIMExitLocation", srcIMExitLocation);

			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("UldNum", "IMExitLocation");

			//fetch destination location
			String destnStorageAreaLocation=to.retrieveDestnLocation("UldNum");
			map.put("destnStorageAreaLocation", destnStorageAreaLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("UldNum", "val~Open", "IMExitLocation");
			libr.quitApp();



			/**** WHS013 -Warehouse Setup Enquiry ****/

			//verifying zone of the destination location
			cust.searchScreen("WHS013", "Warehouse Setup Enquiry");
			WHS013.enterLocation("destnStorageAreaLocation");
			WHS013.clickList();
			String IMExitZone=WHS013.getZoneCode();
			map.put("StorageAreaZone", WebFunctions.getPropertyValue(toproppath, "StorageAreaZone"));
			WHS013.verifyZone(cust.data("StorageAreaZone"),IMExitZone);
			cust.closeTab("WHS013", "Warehouse Setup Enquiry");



			/**** WHS052 -Relocation Task Monitor****/
			cust.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterULDNumber("UldNum");
			WHS052.listAwbDetails();
			String pmKey = cust.data("UldNum");
			//verifying TO generated for the ULD 
			map.put("UldNumber", pmKey);
			String ColumnNames[]={"Status","Source HA","Dest. HA","Remarks"};
			String TODetails[]={"Open",WebFunctions.getPropertyValue(toproppath, "IMExitHA"),WebFunctions.getPropertyValue(toproppath, "StorageAreaHA")};
			WHS052.verifyTODetails(3, ColumnNames, "UldNumber", TODetails);
			WHS052.maximizeAwbDetails("UldNum");
			WHS052.verifyCurrentLocation("UldNum", "Current.Loc","Current.Loc"+"\n"+cust.data("srcIMExitLocation"));
			WHS052.verifyDestinationLocation("UldNum", "Dest.Loc","Dest.Loc"+"\n"+cust.data("destnStorageAreaLocation"));
			WHS052.verifyVehicleType("UldNum", "Vehicle Type", "Vehicle Type"+"\n"+cust.data("VehicleType"));
			cust.closeTab("WHS052", "Relocation Task Monitor");


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
