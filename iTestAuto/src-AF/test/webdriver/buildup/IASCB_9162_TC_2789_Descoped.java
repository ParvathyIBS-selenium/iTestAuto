package buildup;

/***verify To task based on To filters scc,flightnum date,Location and HA of multileg flight with DG scc****/

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
import rest_multiple_sfmi.Jsonbody;
import rest_pawbs.JSONBody;
import screens.AssignFlightLocations_WHS059;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;
import screens.TransportOrderListing;
import screens.WarehouseShipmentEnquiry_WHS011;
import screens.ListMessages_MSG005;
import screens.WarehouseSetUpEnquiry_WHS013;

public class IASCB_9162_TC_2789_Descoped extends BaseSetup {
	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public GoodsAcceptance_OPR335 OPR335;
	public TransportOrderListing to;
	public GoodsAcceptanceHHT gahht;
	public AssignFlightLocations_WHS059 WHS059;
	public MaintainOperationalFlight_FLT003 FLT003;
	public BuildupPlanning_ADD004 ADD004;
	public SecurityAndScreening_OPR339 OPR339;
	public ListMessages_MSG005 MSG005;
	public WarehouseShipmentEnquiry_WHS011 WHS011;
	public WarehouseSetUpEnquiry_WHS013 WHS013;
	public Jsonbody jsonbody1;
	public JSONBody jsonbody;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Buildup.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";

	String sheetName = "Buildup_FT";

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
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		WHS059=new AssignFlightLocations_WHS059(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		jsonbody1=new Jsonbody(driver, excelreadwrite, xls_Read);
		jsonbody=new JSONBody(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		WHS011=new WarehouseShipmentEnquiry_WHS011(driver,excelreadwrite,xls_Read); 
		WHS013=new WarehouseSetUpEnquiry_WHS013(driver,excelreadwrite,xls_Read); 
	}

	@DataProvider(name = "TC_2657")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2657")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// creating flight number
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNumber", cust.data("carrierCode")+cust.data("prop~flightNo"),proppath);

			String timeStamp = cust.createDateFormat("dd-MMM-yyyy hh:mm:ss", 0, "DAY", "Europe/Paris");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");		
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", "Europe/Paris"));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", "Europe/Paris"));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", "Europe/Paris"));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "Europe/Paris").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "Europe/Paris");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());

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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerId_US"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerName_US"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerpostCode_US"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerstreetName_US"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercityName_US"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountryId_US"));
			map.put("ConsigneeCountryName",WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountryName_US"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountrySubdivision_US"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "paycargoCustomertelephoneNo_US"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "paycargoCustomeremail_US"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));

			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));


			// Login to iCargo

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT003.enterLegCapacityDetails("StartDate","EndDate","ATD_Local","ATA_Local", "AircraftType", "");			
			FLT003.clickSecondCheckbox();
			FLT003.clickLegCapacity();
			FLT003.enterLegCapacityDetails("StartDate","EndDate","ATD_Local1","ATA_Local1", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");



			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("FullAWBNum", cust.data("CarrierNumericCode")+ cust.data("prop~AWBNo"));
			map.put("FullAWBNumber", cust.data("prop~CarrierNumericCode") + cust.data("prop~AWBNo")+"001");
			map.put("AWBNo", cust.data("prop~AWBNo"));

			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);


			/*** MESSAGE - loading XFWB **********/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_Transit_MSG", true);
			cust.closeTab("MSG005", "List Message");


			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");



			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);


			/*** HHT - ACCEPTANCE****/
			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			gahht.enterValue("awbNumber");
			gahht.selectSCCValue("SCC");
			map.put("AcceptanceLocation", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation"));
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "AcceptanceLocation");
			gahht.checkAllPartsReceived();
			gahht.clickSaveOnly();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");
			libr.quitApp();

			map.put("AWB", cust.data("CarrierNumericCode") + cust.data("AWBNo"));
			map.put("SU", cust.data("AWB")+"001");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO

			cust.loginTransportOrder(hht[0], hht[1]);

			to.searchShipment("SU");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU", "AcceptanceLocation");


			//fetch destination location
			String destnControlLocation=to.retrieveDestnLocation("SU");
			map.put("destnControlLocation", destnControlLocation);


			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "AcceptanceLocation");


			/**** WHS013 -Warehouse Setup Enquiry ****/

			//verifying zone of the destination location
			cust.searchScreen("WHS013", "Warehouse Setup Enquiry");
			WHS013.enterLocation("destnControlLocation");
			WHS013.clickList();
			String CTXZone=WHS013.getZoneCode();
			map.put("ControlLocationZone_CDG", WebFunctions.getPropertyValue(toproppath, "ControlLocationZone_CDG"));
			WHS013.verifyZone(cust.data("ControlLocationZone_CDG"),CTXZone);
			cust.closeTab("WHS013", "Warehouse Setup Enquiry");


			/*** TRANSPORT ORDER  ***/

			//Verifying TO generated in the TO app
			to.clickRefresh();
			//completing the relocation task
			to.searchShipment("SU");
			to.selectTask("destnControlLocation");
			to.confirmTaskList();
			to.clickRelocationComplete("destnControlLocation");



			/** WAREHOUSE RELOCATION  - WHS011 for AWB 1 **/

			//Manual relocation from the destination Control Location to the Rapix Entry Point
			cust.searchScreen("WHS011", "Warehouse Relocation");
			WHS011.enterAWBdetails("CarrierNumericCode","prop~AWBNo");
			WHS011.clickList();
			WHS011.clickAWBcheckBox();
			WHS011.clickSURelocation();
			map.put("RapixEntryLocation", WebFunctions.getPropertyValue(toproppath, "RapixEntryLocation_second"));
			WHS011.SURelocationDetails("RapixEntryLocation");
			cust.closeTab("WHS011", "Warehouse Relocation");


			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);


			/******* PAWBS POST REQUEST for SU ****/	
			jsonbody.postRequest(cust.data("CarrierNumericCode"), cust.data("AWBNo"), timeStamp,cust.data("ScreeningResult").split(",")[0],screenmethod,cust.data("RapixEntryLocation"),cust.data("ScreenerName"),cust.data("SU"));
			libr.waitForSync(8);

			/******* SFMI POST REQUEST SU ****/		
			jsonbody1.postRequest(cust.data("AWB"),cust.data("Weight"),cust.data("Volume"),cust.data("val~10"),cust.data("val~10"),cust.data("val~10"),"001"+cust.data("AWB"));
			libr.waitForSync(8);


			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.verifyScreeningMethodAutopopulated("screenmethod");
			OPR339.verifyScreeningResultAndSUnumber(cust.data("ScreeningResult").split(",")[1],cust.data("AWB")+"001");			
			OPR339.verifyScreenedPiecesAndWeight("Pieces", "Weight");
			String Sccnotpresent[]={"NSC"};
			OPR339.verifySccNotPresent(Sccnotpresent);
			String Sccpresent[]={"SPX"};
			OPR339.verifyScc(Sccpresent);
			cust.closeTab("OPR339", "Security & Sceening"); 


			/**** OPR335 -Goods Acceptance ****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");	
			map.put("VPPWeight",cust.data("Weight"));
			OPR335.clickSave("OPR335");
			OPR335.dataload_clear();

			//verifying acceptance is finalised
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			/** WAREHOUSE RELOCATION  - WHS011 for AWB 1 **/

			//Manual relocation from the destination Control Location to the Rapix Entry Point
			cust.searchScreen("WHS011", "Warehouse Relocation");
			WHS011.enterAWBdetails("CarrierNumericCode","prop~AWBNo");
			WHS011.clickList();
			WHS011.clickAWBcheckBox();
			WHS011.clickSURelocation();
			map.put("RapixExitLocation", WebFunctions.getPropertyValue(toproppath, "RapixExitLocation"));
			WHS011.SURelocationDetails("RapixExitLocation");
			cust.closeTab("WHS011", "Warehouse Relocation");


			/*** TRANSPORT ORDER  ***/

			//Verifying TO generated in the TO app
			to.clickRefresh();
			to.searchShipment("SU");

			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU", "RapixExitLocation");

			//fetch destination location
			String destnStorageLocation=to.retrieveDestnLocation("SU");
			map.put("destnStorageLocation", destnStorageLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "RapixExitLocation");


			/**** WHS013 -Warehouse Setup Enquiry ****/

			//verifying zone of the destination location
			cust.searchScreen("WHS013", "Warehouse Setup Enquiry");
			WHS013.enterLocation("destnStorageLocation");
			WHS013.clickList();
			String StorageAreaZone=WHS013.getZoneCode();
			map.put("ControlLocationZone_RCMscc_CDG", WebFunctions.getPropertyValue(toproppath, "ControlLocationZone_RCMscc_CDG"));
			WHS013.verifyZone(cust.data("ControlLocationZone_RCMscc_CDG"),StorageAreaZone);
			cust.closeTab("WHS013", "Warehouse Setup Enquiry");




			/*** TRANSPORT ORDER  ***/

			//Verifying TO generated in the TO app
			to.clickRefresh();
			to.searchShipment("SU");
			//completing the relocation task
			to.selectTask("destnStorageLocation");
			to.confirmTaskList();
			to.clickRelocationComplete("destnStorageLocation");



			/*****ADD004 - Build Up planning****/
			cust.searchScreen("ADD004","Buildup Planning");
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			ADD004.verifyShipmentInLoadPlan("prop~AWBNo");
			//Allocate and release
			ADD004.selectULD("AWBNo");
			ADD004.clickAllocate();
			ADD004.selectAllocationType("ULD");
			ADD004.enterSegmentAndUldDetails("UldType1","1",cust.data("Route1"));
			ADD004.clickSaveAllocation();
			ADD004.selectTask("AWBNo");
			ADD004.clickRelease();
			cust.closeTab("ADD004","Buildup Planning");	


			/**** Commented as WHS059 is not needed for AF 

			// WHS059 - Assign Flight locations 
			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(15);
			WHS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHS059.clickList();
			WHS059.clickMoreOptions("FullFlightNo");
			WHS059.clickAssignLocation("0");
			map.put("PITLocation_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG"));
			map.put("PITLocationZone_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocationZone_CDG"));
			WHS059.enterAssignZoneandLocation("PITLocationZone_CDG","PITLocation_CDG");		
			WHS059.clickAssignedLocationTab();
			map.put("currdate",cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			String currtme=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("openTime",cust.timeCalculation(currtme, "HH:mm","MINUTE",2));		
			WHS059.enterOpenTime("currdate", "openTime");
			cust.closeTab("WHS059", "Assign Flight Locations");
			cust.waitForSync(60);


			//WHS059 - Assign Flight locations

			//verifying the PIT location is in open status
			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(60);
			WHS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHS059.clickList();
			WHS059.verifyOpenStatus("OPEN");
			cust.closeTab("WHS059", "Assign Flight Locations");

			 ***/


			/*** TRANSPORT ORDER  ***/

			//Verifying TO generated in the TO app
			to.clickRefresh();

			String flightNum=cust.data("FullFlightNo")+" "+cust.data("StartDate");

			//verifying the TO is generated from storage area to the opened PIT location from Export build up app
			to.searchShipment("SU");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU", "destnStorageLocation");

			//fetch destination location
			String descPITLocationOpened=to.retrieveDestnLocation("SU");
			map.put("descPITLocationOpened", descPITLocationOpened);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "destnStorageLocation");



			/**** WHS013 -Warehouse Setup Enquiry ****/

			//verifying zone of the destination location
			cust.searchScreen("WHS013", "Warehouse Setup Enquiry");
			WHS013.enterLocation("descPITLocationOpened");
			WHS013.clickList();
			String PITZone=WHS013.getZoneCode();
			map.put("PITLocationZone_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocationZone_CDG"));
			WHS013.verifyZone(cust.data("PITLocationZone_CDG"),PITZone);
			cust.closeTab("WHS013", "Warehouse Setup Enquiry");


			/*** TRANSPORT ORDER  ***/

			//Verifying TO generated in the TO app
			to.clickRefresh();
			//verify task based on filter HA
			map.put("HA", WebFunctions.getPropertyValue(toproppath, "PITLocationHA_CDG"));
			to.unSelectHA();

			to.selectToFilter(cust.data("HA"),cust.data("val~Handling Area"));
			to.VerifyFilterSelected(cust.data("HA"),cust.data("val~Handling Area"));
			to.enterShipmentDetails("SU");
			to.verifyULDDetails("SU", "val~Open", "destnStorageLocation");
			to.clickRefresh();
			to.clearFilterOption();


			//verify task based on Filter Location
			to.selectToFilter(cust.data("descPITLocationOpened"),cust.data("val~Destination Location"));
			to.VerifyFilterSelected(cust.data("descPITLocationOpened"),cust.data("val~Destination Location"));
			to.verifyULDDetails("SU", "val~Open", "destnStorageLocation");
			to.clickRefresh();
			to.clearFilterOption();

			//verify task based on  filter flight Num and date
			to.selectToFilter(flightNum,cust.data("val~Flight"));
			to.VerifyFilterSelected(flightNum,cust.data("val~Flight"));
			to.verifyULDDetails("SU", "val~Open", "destnStorageLocation");
			to.clickRefresh();
			to.clearFilterOption();


			//verify task based on  filter SCC
			to.selectToFilter(cust.data("SCC"),cust.data("val~SCC"));
			to.VerifyFilterSelected(cust.data("SCC"),cust.data("val~SCC"));
			to.verifyULDDetails("SU", "val~Open", "destnStorageLocation");
			to.clickRefresh();
			to.clearFilterOption();
			to.searchShipment("SU");
			to.selectTask("descPITLocationOpened");
			//verify  task  mark In progress
			to.verifyShipmentDetails("SU","val~In Progress","destnStorageLocation");
			to.confirmTaskList();
			to.clickRelocationComplete("descPITLocationOpened");
			libr.quitApp();




		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

		finally
		{
			try
			{
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

	}
}
