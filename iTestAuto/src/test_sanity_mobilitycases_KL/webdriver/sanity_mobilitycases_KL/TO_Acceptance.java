package sanity_mobilitycases_KL;

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
import screens.GoodsAcceptance_OPR335;
import screens.RelocationTaskMonitor_WHS052;
import screens.TransportOrderListing;
import screens.HandlingAreaSetUpScreen_WHS008;
import screens.ListMessages_MSG005;
import screens.GoodsAcceptanceHHT;
import screens.AFLS_Booking;
import screens.CGOICSS;
import screens.MaintainFlightSchedule_FLT005;


public class TO_Acceptance extends BaseSetup {

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
	public RelocationTaskMonitor_WHS052 WHS052;
	public TransportOrderListing to;
	public HandlingAreaSetUpScreen_WHS008 WHS008;
	public ListMessages_MSG005 MSG005;
	public GoodsAcceptanceHHT gahht;
	public AFLS_Booking afls;
	public CGOICSS Cgoicss;
	public MaintainFlightSchedule_FLT005 FLT005;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "to_acceptance";

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
		WHS052=new RelocationTaskMonitor_WHS052(driver, excelreadwrite, xls_Read);
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		WHS008= new HandlingAreaSetUpScreen_WHS008(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		afls=new AFLS_Booking(driver, excelreadwrite, xls_Read);
		Cgoicss = new CGOICSS(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TO_Acceptance")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TO_Acceptance")
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
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", "Europe/Amsterdam"));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			String xfwbdate = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "Europe/Amsterdam");
			map.put("XFWBDate", xfwbdate);


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


			// Checking AWB is fresh or Not--AWB 2
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo2", cust.data("prop~FullAWBNo"));
			map.put("AWBNo2", cust.data("prop~AWBNo"));
			libr.quitBrowser();

			/** Flight Creation **/
			/*** cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, EndDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
            libr.quitBrowser();****/

			//Relaunch browser
			/****driver=libr.relaunchBrowser("chrome");	
			//Login to "CGOICSS"
			String[] cgoicsslogin = libr.getApplicationParams("Cgoicss");
			driver.get(cgoicsslogin[0]); // Enters URL
			cust.loginToCGOICSS(cgoicsslogin[1], cgoicsslogin[2]);***/

			/**Flight Creation **/

			/****Cgoicss.clickOnCreateFlight();
			Cgoicss.createNewFlight("carrierCode","FlightNo", "BookDate", "EndDate");
			Cgoicss.selectDayofOperations();
			Cgoicss.addLeg();
			Cgoicss.enterLegDetails("ATD_Local","ATA_Local", "Origin", "Destination", "serviceType", "AircraftType", "carrierCode");
			Cgoicss.clickSave();
			libr.quitBrowser();***/



			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/*** Login to AFLS_BOOKING **********/
			String[] aflsbooking = libr.getApplicationParams("afls");
			driver.get(aflsbooking[0]);
			cust.loginToAFLS(aflsbooking[1], aflsbooking[2]); 

			afls.selectTitleAndSubTitleTab("titleTab","titleTab");		
			afls.enterAWB("CarrierNumericCode","AWBNo2");
			afls.enterAWBOrgAndDest("Origin", "Destination");
			afls.enterBookingOrgAndDest("Origin", "Destination");
			afls.enterBookingDeliveryAndArrivalDate("BookDate", "BookDate");
			afls.enterBookingDeliveryAndArrivalTime("ATD_Local","ATA_Local");
			map.put("Commoditycode", cust.data("CommodityCode").split(",")[1]);
			afls.selectCommodityCode("Commoditycode");
			afls.selectServiceLevelAndHandlingNeeds("serviceLevel", "handlingNeeds");
			afls.enterCustomerID("AgentCode");
			afls.enterFlightInfo("carrierCode","FlightNo","Origin", "Destination", "BookDate");
			afls.enterShipmentDetails("Pieces", "Weight","Volume");
			afls.enterRateDetails("IATARate");
			afls.selectRouteSearchAndEvaluationSetting("no");
			afls.clickSubmitBooking();
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



			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Amsterdam");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Amsterdam");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);

			//			/** MSG005 - List Messages **/
			//
			//			//XFWB Message loading -awb 1
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			//			map.put("awbnumbers", cust.data("FullAWBNo"));
			//			map.put("scc",cust.data("SCC").split(",")[0]);
			//			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[0]);
			//			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			//			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo", true);

			//XFWB Message loading -awb 2
			map.put("awbnumbers", cust.data("FullAWBNo2"));
			map.put("scc",cust.data("SCC").split(",")[1]);
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo", true);
			cust.closeTab("MSG005", "List Message");


			//			/***** OPR026 - Execute AWB ****/
			//			cust.searchScreen("OPR026", "Capture AWB");
			//			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			//			OPR026.asIsExecute();
			//			cust.closeTab("OPR026", "Capture AWB");	

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


			//uld level TO is Descoped

			//			/*** HHT - ACCEPTANCE****/
			//
			//			//ULD acceptance of AWB1
			//			gahht.invokeAcceptanceScreen();
			//			map.put("awbNumber1", cust.data("CarrierNumericCode")+cust.data("AWBNo"));
			//			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			//			map.put("UldNum", uldNo);
			//			gahht.enterValue("UldNum");
			//			map.put("ULDAcceptanceLocation_AMS", WebFunctions.getPropertyValue(toproppath, "ULDAcceptanceLocation_AMS"));
			//			gahht.enterUldAcceptanceDetail("ULDAcceptanceLocation_AMS","awbNumber1","Pieces");
			//			gahht.addULDDetails();
			//			gahht.saveAcceptanceDetails();
			//			cust.clickBack("Acceptance");


			//SU level acceptance of AWB2

			map.put("awbNumber2", cust.data("CarrierNumericCode")+cust.data("AWBNo2"));
			map.put("SU", cust.data("awbNumber2")+"001");
			gahht.invokeAcceptanceScreen();
			gahht.enterValue("awbNumber2");
			String[] sccs={cust.data("SCC").split(",")[1]};
			gahht.selectMultipleSCC(sccs);
			map.put("looseAcceptanceLocation_AMS", WebFunctions.getPropertyValue(toproppath, "looseAcceptanceLocation_AMS"));
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "looseAcceptanceLocation_AMS");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			libr.quitApp();



			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			String [] hht2=libr.getApplicationParams("hht2");	
			cust.loginTransportOrder(hht2[0], hht2[1]);

			//verifying TO generated for SU level acceptance
			to.searchShipment("SU");
			//fetch the src location
			String acceptanceLocationSU=to.retrieveSrcLocation("SU");
			map.put("acceptanceLocationSU", acceptanceLocationSU);
			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU", "looseAcceptanceLocation_AMS");
			//fetch destination location
			String storageAreaLocationSU=to.retrieveDestnLocation("SU");
			map.put("storageAreaLocationSU", storageAreaLocationSU);
			map.put("LooseAcceptanceZone_AMS", WebFunctions.getPropertyValue(toproppath, "ULDAcceptanceZone_AMS"));
			//verifying zone of the destination location
			to.verifyZone(cust.data("storageAreaLocationSU"), "LooseAcceptanceZone_AMS");
			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "looseAcceptanceLocation_AMS");
			//verifying the pieces/weight 
			to.verifyPcsWt("SU", "Pieces", "Weight");
			libr.quitApp();



			String ColumnNames[]={"Status","Source HA","Dest. HA","Remarks"};



			/**** WHS052 -Relocation Task Monitor****/
			cust.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterULDNumber("SU");
			WHS052.listAwbDetails();
			//Verifying TO details in the table
			String pmKeysu = cust.data("SU");
			map.put("SUNumber", pmKeysu);

			map.put("AcceptanceFromHA_AMS", WebFunctions.getPropertyValue(toproppath, "AcceptanceHA_AMS"));
			map.put("LooseStorageAreaHA_AMS", WebFunctions.getPropertyValue(toproppath, "LooseStorageAreaHA_AMS"));
			String TODetailsSU[]={"Open",cust.data("AcceptanceFromHA_AMS"),cust.data("LooseStorageAreaHA_AMS"),"RELOCATION"};
			WHS052.verifyTODetails(4, ColumnNames, "SUNumber", TODetailsSU);
			WHS052.maximizeAwbDetails("SUNumber");
			WHS052.verifyCurrentLocation("SUNumber", "Current.Loc","Current.Loc"+"\n"+cust.data("acceptanceLocationSU"));
			WHS052.verifyDestinationLocation("SUNumber", "Dest.Loc","Dest.Loc"+"\n"+cust.data("storageAreaLocationSU"));
			cust.closeTab("WHS052", "Relocation Task Monitor");





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

