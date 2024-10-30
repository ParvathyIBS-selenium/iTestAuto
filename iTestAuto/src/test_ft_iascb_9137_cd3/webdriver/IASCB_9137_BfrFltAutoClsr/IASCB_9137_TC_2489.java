package IASCB_9137_BfrFltAutoClsr;
/**  Autoclosure of buildup at airline transfer  **/

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
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainOperationalFlight_FLT003;

public class IASCB_9137_TC_2489 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public MaintainOperationalFlight_FLT003 FLT003;
	public ExportManifest_OPR344 OPR344;
	public CaptureAWB_OPR026 OPR026;
	public GoodsAcceptance_OPR335 OPR335;	
	public Cgocxml Cgocxml;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "BeforeFlightAutoClosure";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);	
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_2489")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2489")
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

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/		
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));

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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US"));
		
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			
			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB"));
			
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			// Switch role
			cust.switchRole("Transit", "FCTL", "RoleGroup");
		
            /******* OPR026 - Capture AWB *****/		
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the Full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/*** Incoming flight OAL - AA**/
			cust.createFlight("FullFlightNumber");
			
			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("otherAirline","prop~flightNo", "StartDate","FullFlightNumber");

			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum1 = FlightNum1.replace(cust.data("prop~flight_code"),cust.data("otherAirline"));
			map.put("FullFlightNo", FlightNum1);
			map.put("FlightNo", FlightNum1.substring(2));
			cust.closeTab("FLT003", "Maintain Operational Flight");
					
			/** Flight Creation - OWN **/
			cust.createFlight("FullFlightNumber");
			
			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo1", FlightNum);
			map.put("FlightNo1", FlightNum.substring(2));

			map.put("route", cust.data("Transit")+"-"+cust.data("Destination"));
			String currtimeCDG=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			FLT003.enterFlightDetails("route", "scheduleType", "FCTL", "Office", "flightType");
			String STD1=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",3);
			map.put("STDTime", STD1.split(" ")[1]);
			String STA1=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",6);
			map.put("STATime", STA1.split(" ")[1]);
			map.put("STDDate", STD1.split(" ")[0]);
			map.put("STADate", STA1.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();		
			
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Paris");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);
			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/****** Loading XFBL for Second Flight ***/
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")+ ";" + cust.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String sccs[] = {cust.data("SCC")};
			String routings[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, sccs, routings);
			Cgocxml.sendMessageCgoCXML("ICARGO");					
			
			/** XFWB Message loading **/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch role
			cust.switchRole("Transit", "FCTL", "RoleGroup");
			
			/**** OPR026 - Capture AWB ****/			
			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/****OPR355 - ULD Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");

			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR335.uldShipmentDetails("Pieces", "Weight", "Location", "UldNum", "");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.provideCTMdetails("otherAirline","FlightNo", "StartDate");
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			/*** MSG005-Verify XFSU-RCT message ***/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Inbound CTM");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCT = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo");
			int verfColsRCT[] = { 9 };
			String[] actVerfValuesRCT = { "Sent" };
			MSG005.verifyMessageDetails(verfColsRCT, actVerfValuesRCT, pmKeyRCT, "val~XFSU-RCT", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");	
			

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo1", "StartDate");
			OPR344.verifyULDInPlannedSection("UldNum");
			OPR344.assignUldPlanningSection("UldNum");
			OPR344.verifyULDInAssignedShipment("UldNum", true);
			OPR344.clickBuildUpComplete();
			OPR344.verifyBDPbuttonStatus("close");
			cust.closeTab("OPR344", "Export manifest");		
			
		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

