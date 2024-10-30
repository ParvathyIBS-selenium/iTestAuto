package wp1;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;

import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListCheckSheetConfig_SHR094;
import screens.ListTemplates_SHR093;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;

/** checksheet configuration at import station TC_1807 **/

public class IASCB_19028_TC_1807 extends BaseSetup {
	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public SecurityAndScreening_OPR339 OPR339;
	public CaptureAWB_OPR026 OPR026;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ImportManifest_OPR367 OPR367;
	public BreakDownScreen_OPR004 OPR004;
	public ListCheckSheetConfig_SHR094 SHR094;
	public ListTemplates_SHR093 SHR093;
	public Mercury mercuryScreen;
	public Cgocxml cgocxml;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String proppathsst = "\\src\\resources\\SSTLocators.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "wp1";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
	    FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
	    mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		OPR004=new BreakDownScreen_OPR004(driver,excelreadwrite,xls_Read);
		SHR094=new ListCheckSheetConfig_SHR094(driver,excelreadwrite,xls_Read);
		SHR093 = new ListTemplates_SHR093(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_1807")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_1807")
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

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

		    cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String currentDate = cust. createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Amsterdam");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("CurrentDate", currentDate);
			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM",0, "DAY", ""));
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



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
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			

		
                    /******* OPR026 - Capture AWB *****/
			
			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
		   OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));

			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/

		    cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
		    cust.closeTab("FLT005", "Maintain Schedule");
			
			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);			
			System.out.println(FlightNum);

			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

	/****************** MERCURY *********************/
			
			//	 Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** SSM Message loading **/

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			// Login to "CGOCXML"
			String[] Cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(Cgocxml[0]); // Enters URL
			cust.loginToCgocxml(Cgocxml[1], Cgocxml[2]);

			/** XFSU-BKD Message loading **/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("FlightNumber", cust.data("FullFlightNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFSU", "MessageParamXFSU");
			cgocxml.clickMessageLoader();
			cgocxml.sendMessageCgoCXML("ICARGO");
			
			/** XFBL Message loading **/			
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading **/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			cgocxml.sendMessageCgoCXML("ICARGO");
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

			

			/**** OPR026 - Capture AWB ****/
		
			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			//Capture check sheet
			
			OPR026.captureCheckSheet(true);
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			/*************create uld*******/
			
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
		

			/**** OPR335 -Goods Acceptance ****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
		    OPR335.uldShipmentDetails("Pieces","Weight","Location","UldNum","Contour");
		    OPR335.allPartsRecieved();
			OPR335.clickSave();
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.captureChecksheet(true);
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");


			
			/**** OPR344 - Export manifest****/

			//manifesting and finalizing flight 
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo","StartDate");
			OPR344.assignUldPlanningSection("UldNum");
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");	
			OPR344.finalizeFlight(true);
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export Manifest");

			libr.quitBrowser();

			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			//Login to "MERCURY"
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);


			//***  MVT ATA loading ****/

		
			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

		      /** Switch role to Destination **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");
         
			/***** list checksheet configuration**/
			
			cust.searchScreen("SHR094", "List Check SheetConfig");
			SHR094.selectCheckSheetType("val~AWB");
			SHR094.selectTransaction("Breakdown");
			SHR094.enterScc("val~COL");
			SHR094.enterAirport("Destination");
			SHR094.selectStatus("Active");
			SHR094.listDetails();
			String currntDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			SHR094.verifyFromAndToDate(currntDate);
			String templateId=SHR094.getTemplateID();
			map.put("templateId", templateId);
			System.out.println(templateId);
			cust.closeTab("SHR094", "List Check Sheet Configuration");
			
			/**List Template SHR093**/			
                        cust.searchScreen("SHR093", "List Templates");
                        SHR093.enterTemplateId(templateId);
                        SHR093.listDetails();
			String templateName=SHR093.getTemplateName();
			templateName=templateName.trim();
			map.put("templateName", templateName);
			cust.closeTab("SHR093", "List Templates");
			
			/*****OPR367 - Import Manifest*******/ 
			
			
			//Verify the AWB details
			cust.searchScreen("OPR367", "Import Manifest");
			
			OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
			
			OPR367.maximizeAllDetails();
			String[] uldno= {cust.data("UldNum")};
			OPR367.verifyUldDetails(1,uldno);
	                OPR367.verifyShipment("AWBNo");
			OPR367.SaveDetails();
			OPR367.closeTab("OPR367", "Import Manifest");
			
                        /*******OPR004-BREAKDOWN SCREEN******/
			
			cust.searchScreen("OPR004", "BreakDown");
			
			OPR004.listFlightAndULD( "UldNum", "carrierCode",  "FlightNo", "StartDate");
			int verfCols[]= {3};
			String actVerfValues[]= {cust.data("AWBNo")};
			OPR004.verifyBreakdownDetails(verfCols, actVerfValues);
			OPR004.enterBdnDetails("BDNlocation","Pieces","Weight");
			OPR004.verifyCheckSheetErrorMessage("The Check Sheet for awbno is not complete","FullAWBNo" );
			OPR004.clickBreakdownComplete();
			OPR004.clickCheckBoxAll();
			OPR004.clickCaptureCheckSheet();
			OPR004.captureChecksheet(true);
                        OPR004.clickBreakdownComplete();
			OPR004.listFlightAndULD( "UldNum", "carrierCode",  "FlightNo", "StartDate");
			OPR004.breakDownStatus("Breakdown Completed");
			OPR004.closeTab("OPR004", "BreakDownScreen");

			
			} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

