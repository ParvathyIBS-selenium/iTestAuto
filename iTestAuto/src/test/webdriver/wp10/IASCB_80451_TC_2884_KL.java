package wp10;

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
import screens.AFLS_Booking;
import screens.CGOICSS;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.RelocationTaskMonitor_WHS052;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.HandlingAreaSetUpScreen_WHS008;
import screens.ListMessages_MSG005;
import screens.WarehouseSetUpEnquiry_WHS013;
import screens.SecurityAndScreening_OPR339;
import screens.TransportOrderListing;
import screens.MaintainFlightSchedule_FLT005;

/** TC_02_Verify TO generation during acceptance loose acceptance  **/


 

public class IASCB_80451_TC_2884_KL extends BaseSetup {

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
	public ListMessages_MSG005 MSG005;
	public GoodsAcceptanceHHT gahht;
	public AFLS_Booking afls;
	public CGOICSS Cgoicss;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;
	public RelocationTaskMonitor_WHS052 WHS052;
	public MaintainFlightSchedule_FLT005 FLT005;
	public SecurityAndScreening_OPR339 OPR339;
	public HandlingAreaSetUpScreen_WHS008 WHS008;
	public WarehouseSetUpEnquiry_WHS013 WHS013;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
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
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		afls=new AFLS_Booking(driver, excelreadwrite, xls_Read);
		Cgoicss = new CGOICSS(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		WHS008= new HandlingAreaSetUpScreen_WHS008(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		WHS052=new RelocationTaskMonitor_WHS052(driver, excelreadwrite, xls_Read);
		WHS013=new WarehouseSetUpEnquiry_WHS013(driver,excelreadwrite,xls_Read); 

	}

	@DataProvider(name = "TC_2884")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2884")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
			
			String startDate = cust.createDateFormat("dd-MMM-YYYY",0, "DAY", "");
			String EndDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String bookingDate =cust.createDateFormat("dd/MMM/YYYY",0, "DAY", "");
			map.put("BookDate", bookingDate);
			String endDate = cust.createDateFormat("dd/MMM/YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", "Europe/Amsterdam"));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "Europe/Amsterdam");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());

			String currtme1=cust.createDateFormat("HHmm", 0, "DAY", "Europe/Amsterdam");
			String currentday=cust.createDateFormat("ddMMYY", 0, "DAY", "");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);

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

			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));
			

			// Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			
			/** Flight Creation **/
		/**	cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, EndDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();**/
			
			//Relaunch browser
		/**	driver=libr.relaunchBrowser("chrome");	
			//Login to "CGOICSS"
			String[] cgoicsslogin = libr.getApplicationParams("Cgoicss");
			driver.get(cgoicsslogin[0]); // Enters URL
			cust.loginToCGOICSS(cgoicsslogin[1], cgoicsslogin[2]);**/

			/** Flight Creation **/
		/**	Cgoicss.clickOnCreateFlight();
			Cgoicss.createNewFlight("carrierCode","FlightNo", "BookDate", "EndDate");
			Cgoicss.selectDayofOperations();
			Cgoicss.addLeg();
			Cgoicss.enterLegDetails("ATD_Local","ATA_Local", "Origin", "Destination", "serviceType", "AircraftType", "carrierCode");
			Cgoicss.clickSave();
			libr.quitBrowser(); **/

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
			afls.enterBookingDeliveryAndArrivalDate("BookDate", "BookDate");
			afls.enterBookingDeliveryAndArrivalTime("ATD_Local","ATA_Local");
			afls.selectCommodityCode("CommodityCode");
			afls.selectServiceLevelAndHandlingNeeds("serviceLevel", "handlingNeeds");
			map.put("SCCselected",cust.data("SCC").split(",")[0]);
			afls.selectConditionalSCC("SCCselected");
			afls.enterCustomerID("AgentCode");
			afls.enterFlightInfo("carrierCode","FlightNo","Origin", "Destination", "BookDate");
			afls.enterShipmentDetails("Pieces", "Weight","Volume");
			afls.enterRateDetails("IATARate");
			afls.selectRouteSearchAndEvaluationSetting("no");
			afls.clickSubmitBooking();
			libr.quitBrowser();

			

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/***Login to cgocxml **********/
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/*** MESSAGE - loading XFWB **********/
			// Create XFWB message
			map.put("SCC1",cust.data("SCC").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
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

			/***** OPR026 - Execute AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");	

			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht2");
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - ACCEPTANCE****/

			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			gahht.enterValue("awbNumber");
			gahht.selectSCCValue("SCC1");
			gahht.clickSCCOK();
			map.put("AcceptanceLocation", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation_AMS"));
			gahht.LooseAcceptanceDetailsWithoutStoragePosition("Pieces1", "Weight1", "AcceptanceLocation");
			gahht.CaptureStoragePosition("storagePOS");
			gahht.saveAcceptanceDetailsAndVerifyCheckSheets();
			
			
			gahht.selectSCCValue("SCC1");
			gahht.clickSCCOK();
			map.put("AcceptanceLocation", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation_AMS"));
			gahht.LooseAcceptanceDetailsWithoutStoragePosition("Pieces2", "Weight2", "AcceptanceLocation");
			gahht.CaptureStoragePosition("storagePOS");
			gahht.save(); 
			
			
			
			
			
			libr.quitApp();
			
			
			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);

			map.put("SU1", cust.data("CarrierNumericCode")+cust.data("AWBNo")+"001");
			map.put("SU2", cust.data("CarrierNumericCode")+cust.data("AWBNo")+"002");
			to.searchShipment("SU1");
			
			
			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU1", "AcceptanceLocation");

			//fetch destination location
			String storageAreaLocation=to.retrieveDestnLocation("SU1");
			map.put("storageAreaLocation", storageAreaLocation);

			
			map.put("StorageZone_AMS", WebFunctions.getPropertyValue(toproppath, "StorageareaZone_AMS"));
			//verifying zone of the destination location
			to.verifyZone(cust.data("storageAreaLocation"), "StorageZone_AMS");
			
			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU1", "val~Open", "AcceptanceLocation");
		
			
			to.clickRefresh();
			to.searchShipment("SU2");
			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU2", "AcceptanceLocation");

			//fetch destination location
			String storageAreaLocation1=to.retrieveDestnLocation("SU2");
			map.put("storageAreaLocation1", storageAreaLocation1);
			
			map.put("StorageZone_AMS", WebFunctions.getPropertyValue(toproppath, "StorageareaZone_AMS"));
			//verifying zone of the destination location
			to.verifyZone(cust.data("storageAreaLocation1"), "StorageZone_AMS");

			to.verifyShipmentDetails("SU2", "val~Open", "AcceptanceLocation");
			
			
			/**** WHS052 -Relocation Task Monitor****/
			cust.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterULDNumber("SU1");
			WHS052.selectUncheckAll();

			WHS052.listAwbDetails();
			String pmKey = cust.data("SU1");
			map.put("awbNo", pmKey);

			//Verifying TO details in the table
			String ColumnNames[]={"Status","Source HA","Dest. HA","Remarks"};
			String TODetails[]={"Open",WebFunctions.getPropertyValue(toproppath, "AcceptanceHA_AMS"),WebFunctions.getPropertyValue(toproppath, "StorageAreaHA_AMS"),"RELOCATION"};
			WHS052.verifyTODetails(4, ColumnNames, "awbNo", TODetails);
			WHS052.maximizeAwbDetails("AWBNo");
			WHS052.verifyCurrentLocation("AWBNo", "Current.Loc","Current.Loc"+"\n"+cust.data("AcceptanceLocation_AMS"));
			WHS052.verifyDestinationLocation("AWBNo", "Dest.Loc","Dest.Loc"+"\n"+cust.data("storageAreaLocation"));
			cust.closeTab("WHS052", "Relocation Task Monitor");
			
			/**** WHS052 -Relocation Task Monitor****/
			cust.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterULDNumber("SU2");
			WHS052.selectUncheckAll();

			WHS052.listAwbDetails();
			String pmKey1 = cust.data("SU2");
			map.put("awbNo", pmKey1);

			//Verifying TO details in the table
			
			String TODetails1[]={"Open",WebFunctions.getPropertyValue(toproppath, "AcceptanceHA_AMS"),WebFunctions.getPropertyValue(toproppath, "StorageAreaHA_AMS"),"RELOCATION"};
			WHS052.verifyTODetails(4, ColumnNames, "awbNo", TODetails1);
			WHS052.maximizeAwbDetails("AWBNo");
			WHS052.verifyCurrentLocation("AWBNo", "Current.Loc","Current.Loc"+"\n"+cust.data("AcceptanceLocation_AMS"));
			WHS052.verifyDestinationLocation("AWBNo", "Dest.Loc","Dest.Loc"+"\n"+cust.data("storageAreaLocation1"));
			cust.closeTab("WHS052", "Relocation Task Monitor");

			
			// WHS008 steps are not needed since TO is generated to zone
			
//			/**** WHS008 -HandlingAreaSetUpScreen ****/
//			cust.searchScreen("WHS008", "Handling Area Set Up");
//			int verfCols [] = {3};
//
//			//Verifying storage area destination location and zone 
//			String[] actVerfValues2= {WebFunctions.getPropertyValue(toproppath, "StorageLocationZone_AMS")};
//			//verifying the location displayed is in the correct Zone as per the configuration
//			WHS008.verifyLocationAndCorrespondingZone("storageAreaLocation", verfCols, actVerfValues2);
//			cust.closeTab("WHS008", "Handling Area Set Up");
//
//
			
			
			//WHS013 -Warehouse Setup Enquiry  -Checking for the empty location 
			cust.searchScreen("WHS013", "Warehouse Setup Enquiry");
			WHS013.getEmptyLocation("Zone","Empty","newLocation");
			cust.closeTab("WHS013", "Warehouse Setup Enquiry");
			
			
		
			
			to.clickRefresh();
			to.searchShipment("SU1");
			//completing the relocation task for SU1
			to.selectTask("storageAreaLocation");
			to.confirmTaskList();
			to.enterEmptyLocation("newLocation");
		  
			/**** WHS013 -Warehouse Setup Enquiry  -Checking for the empty location ****/
			cust.searchScreen("WHS013", "Warehouse Setup Enquiry");
			WHS013.getEmptyLocation("Zone","Empty","newLocation");
			cust.closeTab("WHS013", "Warehouse Setup Enquiry");
			
			
		
			
			to.clickRefresh();
			to.searchShipment("SU2");
			//completing the relocation task for SU1
			to.selectTask("storageAreaLocation1");
			to.confirmTaskList();
			to.enterEmptyLocation("newLocation");	
			libr.quitApp();
			

			/***  OPR335 -Goods Acceptance ***/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			//verifying the details in GoodsAcceptance screen
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			/*******Verify XFSU-RCS message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCS=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsRCS[]={9};
			String[] actVerfValuesRCS={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-RCS",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");


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