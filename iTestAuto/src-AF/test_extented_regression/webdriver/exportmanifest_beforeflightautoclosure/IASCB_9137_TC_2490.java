package exportmanifest_beforeflightautoclosure;

/**Automatically flight build up should be closed based on config at transit station**/

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
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.ListMessages_MSG005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;
import screens.ImportManifest_OPR367;
import screens.MarkFlightMovements_FLT006;
import screens.BreakDownScreen_OPR004;


public class IASCB_9137_TC_2490 extends BaseSetup
{
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
	public Cgocxml Cgocxml;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public SecurityAndScreening_OPR339 OPR339;
	public BreakDownScreen_OPR004 OPR004;


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
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_2490")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2490")
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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_US_"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_US_"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_US_"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_US_"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_US_"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_US_"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_US_"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_US_"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_US_"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_US_"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));				
			
			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			map.put("awbNumber1", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum1", cust.data("CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));

			map.put("FullAWBNo", cust.data("awbNumber1"));
			map.put("AWBNo", cust.data("awb1"));
			map.put("AWBNo1", cust.data("awb1"));
			
			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2


			map.put("awbNumber2", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum2", cust.data("CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));

			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo", cust.data("awb2"));
			map.put("AWBNo2", cust.data("awb2"));
			
			/***Flight creation-1**/
			cust.createFlight("FullFlightNumber");

			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum1 = FlightNum1.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum1);
			map.put("FlightNo", FlightNum1.substring(2));

			map.put("route", cust.data("Origin")+"-"+cust.data("Transit"));
			FLT003.enterFlightDetails("route", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeCDG=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			String STD=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",1);
			map.put("STDTime", STD.split(" ")[1]);
			String STA=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",3);
			map.put("STATime", STA.split(" ")[1]);
			map.put("STDDate", STD.split(" ")[0]);
			map.put("STADate", STA.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			cust.closeTab("FLT003", "Maintain Operational Flight");

			/** Flight Creation - 2 **/
			cust.createFlight("FullFlightNumber");

			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flight_code_KL","prop~flightNo", "StartDate","FullFlightNumber");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"),cust.data("prop~flight_code_KL"));
			map.put("FullFlightNo1", FlightNum);
			map.put("FlightNo1", FlightNum.substring(2));

			map.put("route", cust.data("Transit")+"-"+cust.data("Destination"));
			FLT003.enterFlightDetails("route", "scheduleType", "FCTL", "Office", "flightType");
			String STD1=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",2);
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
			libr.quitBrowser();	

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);



			/** XFWB Message loading for AWB1  **/

			map.put("FullAWBNum", cust.data("FullAWBNo"));
			map.put("scc", cust.data("SCC").split(",")[0]);
			map.put("Shipmentdescription",cust.data("ShipmentDesc").split(",")[0]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB-2 **/

			map.put("FullAWBNum", cust.data("FullAWBNo2"));
			map.put("scc", cust.data("SCC").split(",")[1]);
			map.put("Shipmentdescription",cust.data("ShipmentDesc").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/****** XFFM LOADING****/

			/*** MESSAGE - loading and creating XFFM ****/

			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", "Europe/Amsterdam"));

			//ULD Number
			String uldNo=cust.create_uld_number("UldType", "prop~flight_code_KL");
			map.put("UldNum", uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			System.out.println(cust.data("ULDNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[0],
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[1] };
			String routing1[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Transit") + ";"
							+ cust.data("TransitAirport"),
							cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Transit") + ";"
									+ cust.data("TransitAirport")};
			String scc[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1]};

			String uld[] = { cust.data("UldType")+";"+ cust.data("ULDNo")+";"+cust.data("prop~flight_code_KL")};
			int []shipments={2};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing1, uld,shipments);
			// Load XFFM message
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch role
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode","FlightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");

			/** OPR367- Import Manifest **/

			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			String pmkey = cust.data("UldNum");
			OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			String[] Location = { cust.data("BDNlocation"),cust.data("BDNlocation") };
			String[] Pieces = { cust.data("Pieces"), cust.data("Pieces") };
			String[] Weight = { cust.data("Weight"), cust.data("Weight") };
			OPR367.enterBdnDetails_multipleShipments(2, Location, Pieces, Weight);
			OPR004.clickBreakdownComplete();
			OPR367.closeFromOPR004();
			OPR367.closeTab("OPR367", "Import Manifest");

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo1", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.checkSecurityDataReviewed();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.checkSecurityDataReviewed();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code_KL", "FlightNo1", "StartDate");
			String uldNum=cust.create_uld_number("UldType", "prop~flight_code_KL");
			map.put("UldNum", uldNum);
			OPR344.addNewULDWithAWB("UldNum","0","CarrierNumericCode","AWBNo1","Pieces","Weight");
			OPR344.clickBuildUpComplete();
			OPR344.addNewULDWithAWB("val~BULK","0","CarrierNumericCode","AWBNo2","Pieces","Weight");
			OPR344.verifyBDPbuttonStatus("close");
			cust.closeTab("OPR344", "Export manifest");	




		}
		catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
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