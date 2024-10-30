package sanity_mobilitycases_AF;


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
import screens.DropOffPickUpShipmentsSST;
import screens.SecurityAndScreening_OPR339;
import screens.ListMessages_MSG005;
import screens.ServicePointAllocationHHT;
import screens.ReportingAtDockHHT;
import screens.TokenShipmentListingHHT;
import screens.GoodsAcceptanceHHT;
import screens.Servicepointoverview_TGC015;

public class SST_CustomerTruck_Export extends BaseSetup {

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
	public DropOffPickUpShipmentsSST sstDP;
	public ListMessages_MSG005 MSG005;
	public ServicePointAllocationHHT serpointhht;
	public ReportingAtDockHHT reportdockhht;
	public TokenShipmentListingHHT tokenshipmentlistinghht;
	public GoodsAcceptanceHHT gahht;
	public Servicepointoverview_TGC015 TGC015;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "sst_customerTruck_Export";

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
		sstDP=new DropOffPickUpShipmentsSST(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		serpointhht=new ServicePointAllocationHHT(driver, excelreadwrite, xls_Read);
		reportdockhht=new ReportingAtDockHHT(driver, excelreadwrite, xls_Read);
		tokenshipmentlistinghht=new TokenShipmentListingHHT(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		TGC015 = new Servicepointoverview_TGC015(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "SST_CustomerTruck_Export")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "SST_CustomerTruck_Export")
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

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);			
			excelRead.writeDataInExcel(map, path1, sheetName, testName);			
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));


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


			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


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

			/** MSG005 - List Messages **/

			//XFWB Message loading

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);
			cust.closeTab("MSG005", "List Message");


			/**** OPR339 - Security & Screening ****/

			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR026 - Capture AWB ****/

			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			
			/***Launch emulator - sst**/

			libr.launchSSTApp("sst-app");
			//Login in to SST
			String [] sst=libr.getApplicationParams("hht");    
			cust.loginSST(sst[0], sst[1],"Public");

			/*** PUBLIC SIDE TOKEN GENERATION IN DROP OFF PICK UP SST SCREEN**/
			sstDP.invokeDropOffPickUpShipmentsSSTScreen();
			sstDP.addShipment("CarrierNumericCode", "AWBNo");
			sstDP.clickProceed();
			sstDP.enterDriverDetailsWithScroll("StartDate");
			sstDP.clickProceed();
			sstDP.selectVehicletype("VehicleType");
			sstDP.clickProceed();
			libr.waitForSync(2);
			sstDP.verifyTokenGeneration("TokenID");
			sstDP.getAndVerifyServicePointName("ServicePoint");
			libr.quitApp();	


			/************TGC015- SERVICE POINT OVERVIEW*****/
			cust.searchScreen("TGC015", "Servicepointoverview");
			libr.waitForSync(3);
			TGC015.selectWarehouse("servicetype");
			//verifying token generated got displayed
			TGC015.verifyTokenIsDisplayed("TokenID");
			TGC015.freeCounterToken(cust.data("TokenID"));
			cust.closeTab("TGC015", "Service Point Overview");



			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");
			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);


			if(cust.data("tokenInWaitingArea").equals("true"))
			{

				/*** HHT - SERVICE POINT ALLOCATION****/

				//Call forwarding the token to dock
				serpointhht.invokeServicePointAllocationScreen();
				serpointhht.enterToken("TokenID");
				serpointhht.clickselectServicePointDropdown();
				serpointhht.callForward();
				serpointhht.confirmIfCallForwarded();
				cust.clickBack("Service Point Allocation");

			}

			/*** HHT - REPORTING AT DOCK****/

			map.put("TokenID", "0180");
			reportdockhht.invokeReportingAtDockScreen();
			reportdockhht.enterToken("TokenID");
			reportdockhht.clickReportDockMandatory();
			reportdockhht.captureCheckSheetIfMandatory();
			reportdockhht.clickSaveCaptureChecksheet();
			reportdockhht.start();

			tokenshipmentlistinghht.clickSelectAWBIcon();
			tokenshipmentlistinghht.clickPendingIcon();
			tokenshipmentlistinghht.clickNext();

			//Smart Navigation to Goods Acceptance screen
			gahht.captureCheckSheetCDGPHYCHCK();
			gahht.selectSCCValue("SCC");
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "Location");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			libr.quitApp();


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
