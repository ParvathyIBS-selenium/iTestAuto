package sanity_mobilitycases_KL;

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
import screens.CreateVisitDeclaration_TGC013;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.ReportingAtDockHHT;
import screens.SecurityAndScreening_OPR339;
import screens.ServicePointAllocationHHT;
import screens.TokenShipmentListingHHT;
import screens.CGOICSS;
import screens.AFLS_Booking;
import screens.AFLS_FlightPlan;
import screens.DropOffPickUpShipmentsSST;


public class HHT_BUP_Acceptance extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainFlightSchedule_FLT005 FLT005;
	public GoodsAcceptanceHHT gahht;
	public CreateVisitDeclaration_TGC013 TGC013;
	public ServicePointAllocationHHT serpointhht;
	public ReportingAtDockHHT reportdockhht;
	public TokenShipmentListingHHT tokenshipmentlistinghht;
	public GoodsAcceptance_OPR335 OPR335;
	public AFLS_Booking afls;
	public AFLS_FlightPlan aflsfp;
	public CGOICSS Cgoicss;
	public DropOffPickUpShipmentsSST sstDP;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "hht_bup_acceptance";

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
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		TGC013=new CreateVisitDeclaration_TGC013(driver, excelreadwrite, xls_Read);
		serpointhht=new ServicePointAllocationHHT(driver, excelreadwrite, xls_Read);
		reportdockhht=new ReportingAtDockHHT(driver, excelreadwrite, xls_Read);
		tokenshipmentlistinghht=new TokenShipmentListingHHT(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		Cgoicss = new CGOICSS(driver, excelreadwrite, xls_Read);
		afls=new AFLS_Booking(driver, excelreadwrite, xls_Read);
		aflsfp=new AFLS_FlightPlan(driver, excelreadwrite, xls_Read);
		sstDP=new DropOffPickUpShipmentsSST(driver, excelreadwrite, xls_Read);


	}

	@DataProvider(name = "HHT_BUP_Acceptance")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "HHT_BUP_Acceptance")
	public void getTestSuite(Map<Object, Object> map) throws Exception {

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


			String startDate = cust.createDateFormat("dd-MMM-YYYY",0, "DAY", "");
			String bookingDate =cust.createDateFormat("dd/MMM/YYYY",0, "DAY", "");
			map.put("BookDate", bookingDate);
			String endDate = cust.createDateFormat("dd/MMM/YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Amsterdam");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Amsterdam");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);


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
			afls.enterBookingDeliveryAndArrivalDate("BookDate", "BookDate");
			afls.enterBookingDeliveryAndArrivalTime("ATD_Local","ATA_Local");
			map.put("Commodity", cust.data("CommodityCode").split(",")[1]);
			afls.selectCommodityCode("Commodity");
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

			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			/*** MESSAGE - loading XFWB **********/	
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			String sccs[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1],cust.data("SCC").split(",")[2]};
			// Create XFWB message
			cust.createXFWBMessageWithSCCs("XFWB_WithScreeningInfo_Multsccs", sccs);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo_Multsccs", true);
			cust.closeTab("MSG005", "List Message");
			
			/** Now screening is not needed at AMS ***/
//
//			/**** OPR339 - Security & Screening ****/
//			cust.searchScreen("OPR339", "Security and Screening");
//			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
//			OPR339.clickYesButton();
//			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
//			OPR339.saveSecurityDetails();
//			cust.closeTab("OPR339", "Security & Sceening");
//
			/***** OPR026 - Execute AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");



			/***Launch emulator - sst**/
			libr.launchSSTApp("sst_smartlox-app", true);

			//Login to sst
			String [] sst=libr.getApplicationParams("hht2");	
			cust.loginSSTWithCardNumber(sst[0], sst[1],"Public",true,cust.data("DeviceID"),cust.data("CardNumber"));

			/*** PUBLIC SIDE TOKEN GENERATION IN DROP OFF PICK UP SST SCREEN**/
			
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			sstDP.clickAddAWB();
			sstDP.enterAWBNo("awbNumber");
			sstDP.clickAddButton();
			sstDP.clickProceed();
			sstDP.checkDisclaimerBox();
			sstDP.clickProceed();
			sstDP.selectVehicletype("VehicleType");
			sstDP.clickProceed();
			libr.waitForSync(2);
			sstDP.verifyTokenGeneration("TokenID");
			libr.quitApp();	

			
			

			/**** TGC013- CREATE VISIT DECLARATION****/


			cust.searchScreen("TGC013","Create Visit Declaration");
			TGC013.enterTokenNo("TokenID");
			TGC013.clickList();
			TGC013.clickMoreOptions("AWBNo");
			TGC013.clickEdit();
			String uldNo=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			TGC013.captureULDinfo("UldNum");
			TGC013.saveDetails();
			cust.closeTab("TGC013", "Create Visit Declaration");
			
			
			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			String [] hht2=libr.getApplicationParams("hht2");
			cust.loginHHT(hht2[0], hht2[1]);

			//Call forwarding the token to dock
			serpointhht.invokeServicePointAllocationScreen();
			serpointhht.enterToken("TokenID");
			serpointhht.enterServicePoint("ServicepointVal"); 
			serpointhht.callForward();
			cust.clickBack("Service Point Allocation");




			/*** HHT - REPORTING AT DOCK****/

			reportdockhht.invokeReportingAtDockScreen();
			reportdockhht.enterToken("TokenID");
			reportdockhht.start();
			tokenshipmentlistinghht.clickSelectULDIcon();
			tokenshipmentlistinghht.clickPendingULDIcon();
			tokenshipmentlistinghht.Next();

			//Smart Navigation to Goods Acceptance screen without entering ULD number

			gahht.verifyDefaultAcceptanceLocation("ServicepointVal");
			gahht.enterContour("Contour");
			gahht.captureUldHeight("Height");
			gahht.clickWeightCapture();
			gahht.enterScaleWeightID("scaleID");
			gahht.enterScaleWeightValue("ScaleWgt");
			gahht.reEnterScaleWeight("ScaleWgt");
			gahht.clickOkWeightCapture();			
			map.put("awbNumber1", cust.data("CarrierNumericCode")+cust.data("AWBNo"));
			gahht.enterUldAcceptanceDetailWithPieces("awbNumber1","Pieces");
			gahht.saveAcceptanceDetailsAndVerifyCheckSheets();
			gahht.clickPendingStatus();
			gahht.captureCheckSheetCDGPHYCHCK();
			libr.quitApp();

			/****OPR355 - Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");  
			OPR335.verifyAcceptanceFinalized("finalised",false);
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");
			libr.quitBrowser();

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			cust.loginHHT(hht2[0], hht2[1]);

			/*** HHT - REPORTING AT DOCK****/

			reportdockhht.invokeReportingAtDockScreen();
			reportdockhht.enterToken("TokenID");
			reportdockhht.complete();
			reportdockhht.releaseDock();
			libr.quitApp();

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
