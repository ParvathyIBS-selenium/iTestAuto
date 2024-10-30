package iascb_56347;
/**TC_04_Verify cancelling Pallet movement Request with source as Automatic (A) triggered to forklift**/
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
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.HandlingAreaSetUpScreen_WHS008;
import screens.ListMessages_MSG005;
import screens.MaintainOperationalFlight_FLT003;
import screens.RelocationTaskMonitor_WHS052;
import screens.RequestPalletMovement_ADD015;
import screens.SecurityAndScreening_OPR339;
import screens.TransportOrderListing;


public class IASCB_56347_TC_10502 extends BaseSetup {

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
	public MaintainOperationalFlight_FLT003 FLT003;
	public SecurityAndScreening_OPR339 OPR339;
	public HandlingAreaSetUpScreen_WHS008 WHS008;
	public RequestPalletMovement_ADD015 ADD015;
	public ListMessages_MSG005 MSG005;
	public RelocationTaskMonitor_WHS052 WHS052;
	public ExportManifest_OPR344 OPR344;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";

	String sheetName = "iascb_56347";

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
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		WHS052=new RelocationTaskMonitor_WHS052(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		ADD015 = new RequestPalletMovement_ADD015(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		WHS008= new HandlingAreaSetUpScreen_WHS008(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "to_buildup")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "to_buildup")
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

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			String currDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");		
			map.put("StartDate", startDate);
			map.put("currDate", currDate);
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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName",WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
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
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			cust.closeTab("FLT003", "FLT003 - Maintain Operational Flight");

			
			
			/*** MSG005 - SSM Message loading******/
			map.put("flightNo", cust.data("FullFlightNo"));
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", "").toUpperCase());
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetSSM","MessageParamSSM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");
			cust.closeTab("MSG005","MSG005 - List Messages");



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
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			
			/**** XFBL Message loading ****/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);

			/*** MESSAGE - loading XFWB **********/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);
			cust.closeTab("MSG005","MSG005 - List Messages");


			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");


			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/**** OPR335 -Goods Acceptance****/
			//Loose acceptance
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			map.put("AcceptanceLocation", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation"));
			OPR335.looseShipmentDetails("AcceptanceLocation", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR344 - Export manifest****/

			//Assigning the buildup location
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
		OPR344.addNewULDandPOU("UldNum", "0");
			OPR344.addCountour("0");
			OPR344.clickMoreUldDetails();
			map.put("BuildupcompleteLocation_ForkLift", WebFunctions.getPropertyValue(toproppath, "BuildupcompleteLocation_ForkLift"));
			OPR344.captureLocationAndAWBDetails("BuildupcompleteLocation_ForkLift", "CarrierNumericCode", "AWBNo", "Pieces", "Weight", "Volume");
			OPR344.clickBuildUpComplete();
	       cust.closeTab("OPR344", "Export Manifest");

			
			
			
			
			

			/**** WHS052 -Relocation Task Monitor****/
			cust.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterULDNumber("UldNum");
			WHS052.listAwbDetails();
		
			//verifying TO generated for the ULD 

			
		
			String ColumnNames[]={"Status","Source HA","Dest. HA","Remarks"};
			map.put("Source HA", WebFunctions.getPropertyValue(toproppath, "BuildupcompletesourceHA_CDG"));
			map.put("Dest. HA", WebFunctions.getPropertyValue(toproppath, "BuildupcompletedestHA_CDG"));
			String TODetails[]={"Open",cust.data("Source HA"),cust.data("Dest. HA")};
			WHS052.verifyTODetails(3, ColumnNames, "UldNum", TODetails);
			cust.closeTab("WHS052", "Relocation Task Monitor");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO	
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginTransportOrder(hht[0], hht[1]);

			//Fetch the TO destination location 
			to.searchShipment("UldNum");
			
			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("UldNum", "BuildupcompleteLocation_ForkLift");
			

			/**** WHS008 Request pallet movement ****/

			cust.searchScreen("ADD015", "Request Pallet Movement");
			ADD015.enterDateDetails("currDate", "currDate");
			ADD015.enterULDNumber("UldNum");
			ADD015.list();
			
			String[]actvalues={"BuildupcompleteLocation_ForkLift","vehicletype","Source","TOStatus"};
			ADD015.verifyTODetails("UldNum", actvalues);
			ADD015.selectULD("UldNum");
			ADD015.clickCancelTO();
			cust.closeTab("ADD015", "Request Pallet Movement");
			


			/**** WHS052 -Relocation Task Monitor****/
			cust.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterULDNumber("UldNum");
			WHS052.listAwbDetails();
			WHS052.verifyULDRemoved("UldNum");
			cust.closeTab("WHS052", "Relocation Task Monitor");
			

		//	verifying the TO is generated 
			to.clickRefresh();
			to.verifyTOIsRemoved("UldNum");
			libr.quitApp();



		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}