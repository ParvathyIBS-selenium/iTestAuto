package wp10;

/***TC_01_Filter checks in Transport Order Listing screen****/

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
import screens.AssignFlightLocations_WHS059;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.HandlingAreaSetUpScreen_WHS008;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;
import screens.TransportOrderListing;
import screens.ListMessages_MSG005;


public class IASCB_9162_TC_2788 extends BaseSetup {
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
	public AssignFlightLocations_WHS059 WHSS059;
	public MaintainOperationalFlight_FLT003 FLT003;
	public BuildupPlanning_ADD004 ADD004;
	public SecurityAndScreening_OPR339 OPR339;
	public HandlingAreaSetUpScreen_WHS008 WHS008;
	public ListMessages_MSG005 MSG005;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	public static String haproppath = "\\src\\resources\\HA.properties";
	String sheetName = "wp10";

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
		WHSS059=new AssignFlightLocations_WHS059(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		WHS008= new HandlingAreaSetUpScreen_WHS008(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2788")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2788")
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


			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");		
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Paris"));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Paris"));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", "Europe/Paris"));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "Europe/Paris").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "Europe/Paris");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());


			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
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

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Login to iCargo

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");




			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlightDetails("carrierCode","prop~flightNo" , "StartDate");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeAMS=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			String STD=cust.timeCalculation(currtimeAMS, "dd-MMM-yyyy HH:mm","HOUR",3);
			map.put("STDTime", STD.split(" ")[1]);
			String STA=cust.timeCalculation(currtimeAMS, "dd-MMM-yyyy HH:mm","HOUR",5);
			map.put("STATime", STA.split(" ")[1]);
			map.put("STDDate", STD.split(" ")[0]);
			map.put("STADate", STA.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");		
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");



			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("awbNumber1", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum1", cust.data("CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			map.put("FullAWBNo1", cust.data("awbNumber1"));
			map.put("AWBNo1", cust.data("awb1"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2

			map.put("awbNumber2", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum2", cust.data("CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));

			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo2", cust.data("awb2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("awbNumber1") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc"),libr.data("awbNumber2") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
							+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1] };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"), cust.data("Origin") + ";" + cust.data("Destination")};
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);



			/**** XFWB Message loading AWB1 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo1"));
			map.put("scc", cust.data("SCC").split(",")[0]);
			map.put("Vol", cust.data("Volume"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);

			/**** XFWB Message loading AWB2 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo2"));
			map.put("scc", cust.data("SCC").split(",")[1]);
			map.put("Vol", cust.data("Volume"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);
			cust.closeTab("MSG005", "List Message");


			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo1", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/***** OPR026 - Capture AWB ****/
			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");




			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht2");
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - ACCEPTANCE****/

			gahht.invokeAcceptanceScreen();
			String uldNo = cust.create_uld_number("UldType1", "carrierCode");
			map.put("UldNum", uldNo);
			gahht.enterValue("UldNum");
			map.put("AcceptanceLocation_AMS", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation_AMS"));
			gahht.enterUldAcceptanceDetails("AcceptanceLocation_AMS","awbNum1","Pieces");
			gahht.addULDDetails();
			gahht.saveULDAcceptanceDetails();
			cust.clickBack("Acceptance");

			gahht.invokeAcceptanceScreen();
			gahht.enterValue("UldNum");
			gahht.enterUldAcceptanceDetails("AcceptanceLocation_AMS","awbNum2","Pieces");
			gahht.checkAllPartsReceivedForUldAcceptance();
			gahht.addULDDetails();
			gahht.saveULDAcceptanceDetails();
			libr.quitApp();



			/**** OPR335 -Goods Acceptance for AWB1 ****/
			cust.setPropertyValue("awbNo",cust.data("AWBNo1"),proppath);	
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("awbNo", "CarrierNumericCode", "Goods Acceptance");
			String sccDisplayed1=OPR335.retrieveSCCs();
			System.out.println(sccDisplayed1);	
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			cust.closeTab("OPR335", "Goods Acceptance");


			/**** OPR335 -Goods Acceptance for AWB2 ****/

			cust.setPropertyValue("awbNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("awbNo", "CarrierNumericCode", "Goods Acceptance");
			String sccDisplayed2=OPR335.retrieveSCCs();
			System.out.println(sccDisplayed2);	
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			cust.closeTab("OPR335", "Goods Acceptance");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			String [] hht2=libr.getApplicationParams("hht2");	
			cust.loginTransportOrder(hht2[0], hht2[1]);

			to.searchShipment("UldNum");
			//fetch the src location
			String acceptanceLocation=to.retrieveSrcLocation("UldNum");
			map.put("acceptanceLocation", acceptanceLocation);

			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("UldNum", "AcceptanceLocation_AMS");

			//fetch destination location
			String storageAreaLocation=to.retrieveDestnLocation("UldNum");
			map.put("storageAreaLocation", storageAreaLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("UldNum", "val~Open", "AcceptanceLocation_AMS");
			libr.quitApp();


			/**** WHS008 -HandlingAreaSetUpScreen ****/
			cust.searchScreen("WHS008", "Handling Area Set Up");
			int verfCols [] = {3};

			//Verifying the destination location and zone for uldNum
			map.put("StorageLocationZone_AMS", WebFunctions.getPropertyValue(toproppath, "StorageLocationZone_AMS"));
			String[] actVerfValues2= {cust.data("StorageLocationZone_AMS")};
			//verifying the destination location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("storageAreaLocation", verfCols, actVerfValues2);
			cust.closeTab("WHS008", "Handling Area Set Up");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		
			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);

			//verifying the ULD is listed in the TO apk
			to.searchShipment("UldNum");
			//completing the relocation task
			to.selectTask("storageAreaLocation");
			to.confirmTaskList();
			to.clickRelocationComplete("storageAreaLocation");
			libr.quitApp();




			/*****ADD004 - Build Up planning****/
			cust.searchScreen("ADD004","Buildup Planning");
			libr.waitForSync(10);
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			//Allocate and release
			ADD004.selectULD("AWBNo1");
			ADD004.selectULD("AWBNo2");
			ADD004.clickAllocate();
			ADD004.acceptAlertPopUp("val~This is a BUP shipment, Do you want to continue in BUP mode?");
			ADD004.clickSaveAllocation();
			ADD004.clickRelease();
			cust.closeTab("ADD004","Buildup Planning");	


			/***WHS059 - Assign Flight Locations***/

			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(15);
			WHSS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHSS059.clickList();
			WHSS059.clickMoreOptions("FullFlightNo");
			WHSS059.clickAssignLocation("0");
			map.put("BufferLocation_AMS", WebFunctions.getPropertyValue(toproppath, "BufferLocation_AMS"));
			map.put("Zone", WebFunctions.getPropertyValue(toproppath, "BufferLocationZone_AMS"));
			WHSS059.enterAssignZoneandLocationDetails("Zone","BufferLocation_AMS");		
			WHSS059.clickAssignedLocationTab();
			map.put("currdate",cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			String currtme=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("openTime",cust.timeCalculation(currtme, "HH:mm","MINUTE",2));		
			WHSS059.enterOpenTime("currdate", "openTime");
			cust.closeTab("WHS059", "Assign Flight Locations");
			cust.waitForSync(60);


			/***WHS059 - Assign Flight Locations***/

			//verifying the buffer location is in open status
			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(60);
			WHSS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHSS059.clickList();
			WHSS059.verifyOpenStatus("OPEN");
			cust.closeTab("WHS059", "Assign Flight Locations");





			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO	
			cust.loginTransportOrder(hht[0], hht[1]);

			to.searchForShipments("UldNum");

			//fetch and verify the src location after opening the buffer Location
			to.retrieveAndVerifyOriginLocation("UldNum", "storageAreaLocation");

			//fetch destination location
			String bufferLocation=to.retrieveDestnLocation("UldNum");
			map.put("bufferLocation", bufferLocation);

			libr.quitApp();


			/**** WHS008 -HandlingAreaSetUpScreen ****/

			cust.searchScreen("WHS008", "Handling Area Set Up");

			//Verifying the opened buffer destination location and zone for su
			map.put("BufferLocationZone_AMS", WebFunctions.getPropertyValue(toproppath, "BufferLocationZone_AMS"));
			String[] actVerfValues5= {cust.data("BufferLocationZone_AMS")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("bufferLocation", verfCols, actVerfValues5);
			cust.closeTab("WHS008", "Handling Area Set Up");



			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);

			//verify task based on filter HA
			map.put("HA_Buildup", WebFunctions.getPropertyValue(toproppath, "StorageAreaHA_AMS"));

			to.selectHA(cust.data("HA_Buildup"));
			to.unSelectHA();


			//verify tasks are listed based on filter - HA
			to.selectToFilter(cust.data("HA_Buildup"),cust.data("val~Handling Area"));
			to.VerifyFilterSelected(cust.data("HA_Buildup"),cust.data("val~Handling Area"));
			to.enterShipmentDetails("UldNum");
			to.verifyULDDetails("UldNum", "val~Open", "storageAreaLocation");

			to.clickRefresh();
			to.clearFilterOption();
			to.unSelectHA();

			//verify task based on Filter Destination Location
			to.selectToFilter(cust.data("bufferLocation"),cust.data("val~Destination Location"));
			to.VerifyFilterSelected(cust.data("bufferLocation"),cust.data("val~Destination Location"));
			to.enterShipmentDetails("UldNum");
			to.verifyULDDetails("UldNum", "val~Open", "storageAreaLocation");

			to.clickRefresh();
			to.clearFilterOption();


			//verify task based on  filter flight Num and date
			String flightNum=cust.data("FullFlightNo")+" "+cust.data("StartDate");
			to.selectToFilter(flightNum,cust.data("val~Flight"));
			to.VerifyFilterSelected(flightNum,cust.data("val~Flight"));
			to.enterShipmentDetails("UldNum");
			to.verifyULDDetails("UldNum", "val~Open", "storageAreaLocation");

			to.clickRefresh();
			to.clearFilterOption();
			to.unSelectHA();

			//verify task based on  filter SCC1
			to.selectToFilter(sccDisplayed1,cust.data("val~SCC"));
			to.VerifyFilterSelected(sccDisplayed1,cust.data("val~SCC"));
			to.enterShipmentDetails("UldNum");
			to.verifyULDDetails("UldNum", "val~Open", "storageAreaLocation");

			to.selectTask("bufferLocation");
			//verify  task  mark In progress
			to.verifyULDDetails("UldNum","val~In Progress","storageAreaLocation");

			to.clickRefresh();
			to.clearFilterOption();
			to.unSelectHA();


			//verify task based on  filter SCC2
			to.selectToFilter(sccDisplayed2,cust.data("val~SCC"));
			to.VerifyFilterSelected(sccDisplayed2,cust.data("val~SCC"));
			to.enterShipmentDetails("UldNum");
			to.verifyULDDetails("UldNum", "val~Open", "storageAreaLocation");

			to.selectTask("bufferLocation");
			//verify  task  mark In progress
			to.verifyULDDetails("UldNum","val~In Progress","storageAreaLocation");
			libr.quitApp();



		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
