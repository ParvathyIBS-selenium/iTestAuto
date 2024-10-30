package wp10;

/** TO must be generated during acceptance save- ULD shipment.**/
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
import screens.Cgocxml;
import screens.GoodsAcceptance_OPR335;
import screens.RelocationTaskMonitor_WHS052;
import screens.TransportOrderListing;
import screens.ListMessages_MSG005;
import screens.Cgomon;
import screens.SecurityAndScreening_OPR339;
import screens.AssignFlightLocations_WHS059;
import screens.BuildupPlanning_ADD004;
import screens.MaintainOperationalFlight_FLT003;
import screens.HandlingAreaSetUpScreen_WHS008;



public class IASCB_80451_TC_2888 extends BaseSetup
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
	public GoodsAcceptance_OPR335 OPR335;
	public RelocationTaskMonitor_WHS052 WHS052;
	public TransportOrderListing to;
	public ListMessages_MSG005 MSG005;
	public AssignFlightLocations_WHS059 WHSS059;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;
	public MaintainOperationalFlight_FLT003 FLT003;
	public BuildupPlanning_ADD004 ADD004;
	public SecurityAndScreening_OPR339 OPR339;
	public HandlingAreaSetUpScreen_WHS008 WHS008;

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
		WHS052=new RelocationTaskMonitor_WHS052(driver, excelreadwrite, xls_Read);
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		WHSS059=new AssignFlightLocations_WHS059(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		WHS008= new HandlingAreaSetUpScreen_WHS008(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2888")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2888")
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


			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB"));


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



			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();

			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Paris");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");


			/***Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MESSAGE - loading XFWB **********/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
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

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			//verifying the details in GoodsAcceptance screen
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			//Creating ULD number
			String uldNo=OPR335.create_uld_number("UldType1", "prop~flight_code");
			map.put("UldNum", uldNo);
			libr.waitForSync(6);
			map.put("AcceptanceLocation", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation"));
			OPR335.uldShipmentDetails("Pieces","Weight", "AcceptanceLocation", "UldNum","Contour");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");



			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			String[] hht = libr.getApplicationParams("hht");	
			cust.loginTransportOrder(hht[0], hht[1]);

			to.searchShipment("UldNum");

			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("UldNum", "AcceptanceLocation");


			//fetch destination location
			String destnControlLocation=to.retrieveDestnLocation("UldNum");
			map.put("destnControlLocation", destnControlLocation);


			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("UldNum", "val~Open", "AcceptanceLocation");
			libr.quitApp();


			/**** WHS008 -HandlingAreaSetUpScreen ****/

			cust.searchScreen("WHS008", "Handling Area Set Up");
			int verfCols [] = {3};

			//Verifying destination location and zone for uldNum
			String[] actVerfValues2= {WebFunctions.getPropertyValue(toproppath, "ControlLocationZone_COLscc_CDG")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("destnControlLocation", verfCols, actVerfValues2);
			cust.closeTab("WHS008", "Handling Area Set Up");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);

			//completing the relocation task
			to.searchShipment("UldNum");
			to.selectTask("destnControlLocation");
			to.confirmTaskList();
			to.clickRelocationComplete("destnControlLocation");
			libr.quitApp();


			/*****ADD004 - Build Up planning****/
			cust.searchScreen("ADD004","Buildup Planning");
			libr.waitForSync(10);
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			ADD004.verifyShipmentInLoadPlan("prop~AWBNo");
			ADD004.selectULD("prop~AWBNo");
			//Allocating and releasing 
			ADD004.clickAllocate();	
			ADD004.acceptAlertPopUp("val~This is a BUP shipment, Do you want to continue in BUP mode?");
			ADD004.clickSaveAllocation();
			ADD004.clickRelease();
			cust.closeTab("ADD004","Buildup Planning");	


			/*** WHS059 - Assign Flight locations ***/

			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(15);
			WHSS059.enterFlightDetails("carrierCode","FlightNo","StartDate");
			WHSS059.clickList();
			WHSS059.clickMoreOptions("FullFlightNo");
			WHSS059.clickAssignLocation("0");
			map.put("PITLocation_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG"));
			map.put("PITLocationZone_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocationZone_CDG"));
			WHSS059.enterAssignZoneandLocation("PITLocationZone_CDG","PITLocation_CDG");		
			WHSS059.clickAssignedLocationTab();
			map.put("currdate",cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			String currtme=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("openTime",cust.timeCalculation(currtme, "HH:mm","MINUTE",2));		
			WHSS059.enterOpenTime("currdate", "openTime");
			cust.closeTab("WHS059", "Assign Flight Locations");
			cust.waitForSync(60);


			/*** WHS059 - Assign Flight locations ***/

			//verifying the PIT location is in open status
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


			to.searchShipment("UldNum");
			//fetch the src location after opening the PIT Location
			String srcControlLocation=to.retrieveSrcLocation("UldNum");
			map.put("srcControlLocation", srcControlLocation);

			//fetch and verify the src location after opening the PIT Location
			to.retrieveAndVerifyOriginLocation("UldNum", "destnControlLocation");

			//fetch destination location
			String PITLocation=to.retrieveDestnLocation("UldNum");
			map.put("PITLocation", PITLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("UldNum", "val~Open", "destnControlLocation");
			libr.quitApp();



			/**** WHS008 -HandlingAreaSetUpScreen ****/
			cust.searchScreen("WHS008", "Handling Area Set Up");

			//Verifying opened PIT destination location and zone for uldNum
			String[] actVerfValues4= {WebFunctions.getPropertyValue(toproppath, "PITLocationZone_CDG")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("PITLocation", verfCols, actVerfValues4);
			cust.closeTab("WHS008", "Handling Area Set Up");



			/**** WHS052 -Relocation Task Monitor****/
			cust.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterULDNumber("UldNum");
			WHS052.listAwbDetails();
			String pmKey = cust.data("UldNum");
			map.put("UldNumber", pmKey);
			String ColumnNames[]={"Status","Source HA","Dest. HA","Flight","Remarks"};
			String TODetails[]={"Open",WebFunctions.getPropertyValue(toproppath, "ControlLocationHA_COLscc_CDG"),WebFunctions.getPropertyValue(toproppath, "PITLocationHA_CDG"),cust.data("carrierCode")+"-"+cust.data("FlightNo")+" "+cust.data("StartDate")+"(E)","RELOCATION TASK"};
			WHS052.verifyTODetails(5, ColumnNames, "UldNumber", TODetails);
			WHS052.maximizeULDDetails("UldNum");
			WHS052.verifyCurrentLoc("UldNum", "Current.Loc","Current.Loc"+"\n"+cust.data("srcControlLocation"));
			WHS052.verifyDestLocation("UldNum", "Dest.Loc","Dest.Loc"+"\n"+cust.data("PITLocation"));
			cust.closeTab("WHS052", "Relocation Task Monitor");
			libr.quitBrowser();


		}

		catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
