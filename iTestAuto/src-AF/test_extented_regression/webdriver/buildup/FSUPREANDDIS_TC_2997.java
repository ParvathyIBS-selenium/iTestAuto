package buildup;

/*TC_06_Verify status change in CGOSPA when an AWB is assigned to a ULD from icargo -Export build-up tablet app**/

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
import screens.AFLS_FlightPlan;
import screens.CGOICSS;
import screens.CGOSPA;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;
import screens.BuildupPlanning_ADD004;
import screens.BuildUpHHT;
import screens.BuildUpListHHT;


public class FSUPREANDDIS_TC_2997 extends BaseSetup


{

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainFlightSchedule_FLT005 FLT005;
	public GoodsAcceptance_OPR335 OPR335;
	public AFLS_Booking afls;
	public AFLS_FlightPlan aflsfp;
	public ListMessages_MSG005 MSG005;
	public BuildupPlanning_ADD004 ADD004;
	public BuildUpListHHT bulhht;
	public BuildUpHHT buhht;
	public CGOICSS Cgoicss;
	public CGOSPA Cgospa;
	public Cgocxml Cgocxml;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Buildup.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "Buildup_SIT";

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
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgoicss = new CGOICSS(driver, excelreadwrite, xls_Read);
		Cgospa = new CGOSPA(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		afls=new AFLS_Booking(driver, excelreadwrite, xls_Read);
		aflsfp=new AFLS_FlightPlan(driver, excelreadwrite, xls_Read);
		bulhht = new BuildUpListHHT(driver, excelreadwrite, xls_Read);
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2997")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2997")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			//Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			String startDate = cust.createDateFormat("dd-MMM-YYYY",0, "DAY", "");
			String EndDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
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


			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_FR2"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_FR2"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR2"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR2"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR2"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR2"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR2"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR2"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR2"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR2"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR2"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));



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

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, EndDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");	

			//Login to "CGOICSS"
			String[] cgoicsslogin = libr.getApplicationParams("Cgoicss");
			driver.get(cgoicsslogin[0]); // Enters URL
			cust.loginToCGOICSS(cgoicsslogin[1], cgoicsslogin[2]);

			/** Flight Creation **/
			Cgoicss.clickOnCreateFlight();
			Cgoicss.createNewFlight("carrierCode","FlightNo", "BookDate", "EndDate");
			Cgoicss.selectDayofOperations();
			Cgoicss.addLeg();
			Cgoicss.enterLegDetails("ATD_Local","ATA_Local", "Origin", "Destination", "serviceType", "AircraftType", "carrierCode");
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
			afls.enterBookingDeliveryAndArrivalDate("BookDate", "BookDate");
			afls.enterBookingDeliveryAndArrivalTime("ATD_Local","ATA_Local");

			afls.selectCommodityCode("CommodityCode");
			afls.selectServiceLevelAndHandlingNeeds("serviceLevel", "handlingNeeds");
			afls.selectConditionalSCC("SCC");
			afls.enterCustomerID("AgentCode");
			afls.enterFlightInfo("carrierCode","FlightNo","Origin", "Destination", "BookDate");
			afls.enterShipmentDetails("Pieces", "Weight","Volume");
			afls.enterRateDetails("IATARate");
			afls.selectRouteSearchAndEvaluationSetting("no");
			afls.clickSubmitBooking();
			libr.quitBrowser();

			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			//Login to ALFS_FlightPlan		 
			cust.loginToAFLS_FlightPlan();

			aflsfp.clickMenu();
			aflsfp.selectMenuOption("val~Flight plan");
			libr.waitForSync(12);
			aflsfp.enterFlightDetails("carrierCode", "FlightNo", "BookDate","BookDate");
			aflsfp.clickSearch();

			//FBL Trigger
			aflsfp.clickSend();
			aflsfp.selectSendMessages("val~FBL");
			libr.quitBrowser();

			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/*** Login to cgocxml **********/
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]); 

			/** XFWB Message loading **/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
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

			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR026 - Capture AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/**Goods Acceptance**/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			String uldNo = cust.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);

			OPR335.uldShipmentDetails("Pieces", "Weight", "Location", "UldNum", "Contour");
			OPR335.addULDDetails();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");

			/*****ADD004 - Build Up planning****/	
			cust.searchScreen("ADD004", "Buildup Planning");
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			ADD004.verifyShipmentInLoadPlan("AWBNo");
			ADD004.selectULD("AWBNo");
			ADD004.clickAllocate();	
			ADD004.acceptAlertPopUp("val~This is a BUP shipment, Do you want to continue in BUP mode?");
			ADD004.clickSaveAllocation();
			cust.closeTab("ADD004", "Buildup Planning");


			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");		

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);


			/***  Build-Up List HHT ***/
			bulhht.invokeBuildUpListScreen();  
			map.put("FlightNumber", cust.data("carrierCode")+"-"+cust.data("FlightNo"));
			bulhht.listByFlightDetails("carrierCode", "FlightNo", "nextDay");
			map.put("Uld",cust.data("UldType")+" - 1");
			bulhht.selectULD("Uld");
			bulhht.clickBuildUp();		

			/*** Navigated to HHT - Build Up ***/           

			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code_KL", "prop~flightNo","currentDay");
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteBtn();
			buhht.clickSaveCaptureChecksheet();   
			buhht.clickTopUpNoOption();
			buhht.selectContourAndSave("Contour");
			cust.clickBack("Build Up");




			/*******Verify FSU-PRE message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Prepared for loading");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyPRE=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsPRE[]={9};
			String[] actVerfValuesPRE={"Sent"};
			MSG005.getNumberOfRecordsPresent(cust.data("AWBNo"),1);
			MSG005.verifyMessageDetails(verfColsPRE, actVerfValuesPRE, pmKeyPRE,"val~XFSU-PRE",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to CGOSPA **********/
			String[] cgospa = libr.getApplicationParams("Cgospa");
			driver.get(cgospa[0]);
			cust.loginToCGOSPA(cgospa[1], cgospa[2]); 

			//listing the awb in the search/archives section of CGOSPA
			Cgospa.selectLanguage();
			Cgospa.clickSearchOrArchives();
			String StartDate = cust.createDateFormatWithTimeZone("MM/dd/YYYY", 0, "DAY", "");
			String enddate = cust.createDateFormatWithTimeZone("MM/dd/YYYY", 7, "DAY", "");
			Cgospa.enterStartDate(StartDate);
			Cgospa.enterEndDate(enddate);
			Cgospa.listAWBNo("AWBNo");
			libr.waitForSync(3);

			//verify the awb details in the Research/Archives section
			String pmKey =cust.data("AWBNo");
			int[] verfCols={5,6};
			String[] actVerfValues ={cust.data("Origin"),cust.data("Destination")};
			Cgospa.verifyAWBDetails(verfCols, actVerfValues,pmKey);
			Cgospa.clickAWBNo();

			//verifying scc details for the ULD 
			Cgospa.verifySCCDetails("UldNum", cust.data("ExpectedSCCs"), cust.data("SCC"));

			//verifying the details in publishSipmentRelocation message reflected in CGOSPA
			String uld =cust.data("UldNum");
			int[] verfCols1={2,10,11,14};
			String[] actVerfValues1 ={cust.data("status"),cust.data("Pieces"),cust.data("Weight"),cust.data("HA")+cust.data("Location")};
			Cgospa.verifyFlightDetails(verfCols1, actVerfValues1,uld);
			libr.quitBrowser();



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


