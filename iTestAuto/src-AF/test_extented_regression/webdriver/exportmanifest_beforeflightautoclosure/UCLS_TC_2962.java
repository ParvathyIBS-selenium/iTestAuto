package exportmanifest_beforeflightautoclosure;

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
import screens.Cafeed;
import screens.CaptureAWB_OPR026;
import screens.CaptureCheckSheet_CHK002;
import screens.CaptureDGDetails_OPR350;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;

/**TC_05_Verify ULD details sent to CAFEED after build up completion of the ULD at origin and transit with both origin and transit being hub stations- Android**/



public class UCLS_TC_2962 extends BaseSetup {

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
	public Cgocxml Cgocxml;
	public ExportManifest_OPR344 OPR344;
	public Cafeed cfd;
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainOperationalFlight_FLT003 FLT003;
	public MaintainFlightSchedule_FLT005 FLT005;
	public CaptureCheckSheet_CHK002 CHK002;
	public CaptureDGDetails_OPR350 OPR350;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "UCLSAutoFlightClosure";

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
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		cfd= new Cafeed(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		CHK002= new CaptureCheckSheet_CHK002(driver, excelreadwrite, xls_Read);
		OPR350 = new CaptureDGDetails_OPR350(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2967")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2967")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			String currtimeCDG=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");

			String STD=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",2);

			map.put("STDTime", STD.split(" ")[1]);
			String ATD=STD.split(" ")[1];
			String ATDLocal=ATD.split(":")[0]+ATD.split(":")[1];
			map.put("ATD_Local",ATDLocal);
			String STA=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",5);
			map.put("STATime", STA.split(" ")[1]);
			String ATA=STA.split(" ")[1];
			String ATALocal=ATA.split(":")[0]+ATA.split(":")[1];
			map.put("STDDate", STD.split(" ")[0]);
			map.put("STADate", STA.split(" ")[0]);
			String date1=cust.createDateFormatWithTimeZone("ddMMMYY", 0, "DAY", "");
			map.put("Date1", date1);
			map.put("ATA_Local",ATALocal);
			/***Assigning the value of origin to origin***/
			String origin1=cust.data("Origin2");
			map.put("Origin",cust.data("Origin2"));
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			map.put("Destination", cust.data("Transit"));
			cfd.createnewFlightInCafeedwindow("prop~flightNumber","Date1","FullFlightNumber","StartDate");
			map.put("FullFlightNo",cust.data("prop~flightNumber"));
			map.put("FlightNo",cust.data("prop~flightNo"));
			

			String date=cust.createDateFormat("ddMMMYY", 0, "DAY", "");
			map.put("Date", date);
			map.put("ATA_Local",cust.data("ATA_Local2"));
			map.put("ATD_Local",cust.data("ATD_Local2"));
			String startDate1 = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate1", startDate1);
			map.put("Origin",cust.data("Transit"));
			map.put("Destination",cust.data("Destination1"));
			cfd.createnewFlightInCafeedwindow("prop~flightNumber","Date","FullFlightNumber","StartDate1");
			map.put("FullFlightNo1",cust.data("prop~flightNumber"));
			map.put("FlightNo1",cust.data("prop~flightNo"));
			
			//Login to iCargo

			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver = libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	




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

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "SIN"));

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));
			
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "");
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("ATD_Local",ATDLocal);
			map.put("ATA_Local",ATALocal);
			map.put("Origin",origin1);



			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			
			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/


			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("FlightNo", "StartDate");
			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");


			// Checking AWB is fresh or Not--AWB 1
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("AWBNo"));
			
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc")};
			String scc[] = {cust.data("SCC")};

			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination")};
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFWB Message loading AWB1 ****/
			map.put("FullAWBNum", cust.data("FullAWBNo"));
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
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			/**** OPR339 - Security & Screening ****/


			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");



			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
			//Capture check sheet
			OPR026.captureCheckSheet(true,"leakage");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			



			/***** OPR335 - Goods Acceptance ****/


			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.captureCheckSheet(true, "leakage");
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");





			/**CAPTURE DG details****/

			cust.searchScreen("OPR350", "Capture DG Details");
			String shippingnName=cust.data("ShippingName");	
			OPR350.listAWB("AWBNo","prop~CarrierNumericCode");
			OPR350.captureUnidandProperShippingname("UNID",shippingnName);
			OPR350.capturePI("PI");
			OPR350.capturePackages("10","val~1","Unit");
			OPR350.clickAddButton();
			OPR350.clickSaveButton();
			cust.closeTab("OPR350", "Capture DG Details");
			
			


			/***** CAPTURE CHECK SHEET***/
			cust.searchScreen("CHK002", "Capture Check Sheet");
			CHK002.listCheckSheetType("AWB");
			CHK002.listAWBWithTransaction("AWBNo", "CarrierNumericCode","Manifest");
			CHK002.captureCheckSheet(true, "leakage");
			CHK002.closeTab("CHK002", "Capture Check Sheet");	

			/*****OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			OPR344.addNewULDWithAWB("UldNum","0","CarrierNumericCode","prop~AWBNo","Pieces","Weight");
			libr.waitForSync(5);
			OPR344.clickEditULDdetailsByJS("UldNum");
			libr.waitForSync(3);
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight", "Occupancy");
			cust.closeTab("OPR344", "Export Manifest");
			libr.quitBrowser();


		} catch (Exception e) {
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
