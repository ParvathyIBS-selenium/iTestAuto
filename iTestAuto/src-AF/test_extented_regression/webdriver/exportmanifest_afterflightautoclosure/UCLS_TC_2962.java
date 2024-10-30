package exportmanifest_afterflightautoclosure;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.BuildUpHHT;
import screens.Cafeed;
import screens.CaptureAWB_OPR026;
import screens.CaptureCheckSheet_CHK002;
import screens.CaptureDGDetails_OPR350;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreening_OPR339;

/** TC_05_Verify ULD details sent to CAFEED after build up completion of the ULD at origin and transit with both origin and transit being hub stations- Android	**/



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
	public BuildUpHHT buhht;
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainOperationalFlight_FLT003 FLT003;
	public MaintainFlightSchedule_FLT005 FLT005;
	public CaptureDGDetails_OPR350 OPR350;
	public MarkFlightMovements_FLT006 FLT006;
	public CaptureCheckSheet_CHK002 CHK002;
	public ImportManifest_OPR367 OPR367;
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
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
		OPR350 = new CaptureDGDetails_OPR350(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		CHK002= new CaptureCheckSheet_CHK002(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2962")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2962")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
			map.put("StartDate1",cust.data("StartDate"));
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String date=cust.createDateFormat("ddMMMYY", 0, "DAY", "");
			String date1=cust.createDateFormatWithTimeZone("ddMMMYY", 0, "DAY", "");
			System.out.println(date);
			map.put("StartDate2", startDate);
			map.put("Date", date);
			map.put("Date1", date1);
			map.put("ATA_Local",cust.data("ATA_Local2"));
			map.put("ATD_Local",cust.data("ATD_Local2"));
			System.out.println(cust.data("ATA_Local"));
			
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			//Login to Cafeed
			String[] cafeed = libr.getApplicationParams("cafeed");
			driver.get(cafeed[0]);
			Thread.sleep(2000);
			cust.loginToCafeed(cafeed[1], cafeed[2]);
			Thread.sleep(2000);

			cfd.clickSearchFlightLink();
			cfd.listFlightDetails("FullFlightNo", "Date1");


			int[] col = {21};


			String[] awbScc= {cust.data("SCC")};


			cfd.verifyULDDetails(col,awbScc,cust.data("UldNum"));

			String[]awbNumbers={cust.data("FullAWBNo")};

			cfd.verifyAwbDetailsInsideULD("UldNum", awbNumbers);


			cfd.clickAWBInsideULD("UldNum","FullAWBNo");
			cfd.verifyDGDetails("UNID","PI","ShippingName");



			libr.quitBrowser();


			//Login to iCargo

			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver = libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	

			// Switch role
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			
			


			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("ATA_Local",cust.data("ATA_Local1"));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());


			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("FlightNo1", "StartDate2");
			FLT003.enterFlightDetails("Route1", "scheduleType", "FCTL", "Office", "flightType");
			FLT003.enterLegCapacityDetails("ATD_Local2","ATA_Local2", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");



			cust.switchRole("Origin", "FCTL", "RoleGroup");




			/*****OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate1");
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			libr.waitForSync(3);
			OPR344.finalizeFlight();
			cust.closeTab("OPR344", "Export Manifest");


			cust.switchRole("Transit", "FCTL", "RoleGroup");


			/** Mark Flight Movement **/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode","FlightNo","StartDate1");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Amsterdam");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Amsterdam");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementArrivalDetails(currtime,currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
			
			/***** CAPTURE CHECK SHEET***/
			cust.searchScreen("CHK002", "Capture Check Sheet");
			CHK002.listCheckSheetType("AWB");
			CHK002.listAWBWithTransaction("AWBNo", "CarrierNumericCode","Breakdown");
			CHK002.captureCheckSheetAnswers(true,"leakage");
			CHK002.closeTab("CHK002", "Capture Check Sheet");

			/**** Import Manifest ***/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode","FlightNo", "StartDate1");
			String pmkey = Excel.getCellValue(path1, sheetName, "UCLS_TC_2962", "UldNum");
			OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			OPR367.enterBdnDetailsforAWB(cust.data("BDNlocation"), cust.data("Pieces"), cust.data("Weight"),"AWBNo");
			OPR367.SaveDetailsInOPR004();
			OPR367.clickYesButton();
			OPR367.closeFromOPR004();	 			
			cust.closeTab("OPR367", "Import Manifest");	
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/**** XFBL Message loading ****/
			map.put("Origin",cust.data("Transit") );
			map.put("FullFlightNo", cust.data("FullFlightNo1"));
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc")};
			String scc[] = {cust.data("SCC")};

			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination")};
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
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



			/*****OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo1","StartDate2");
			String uldNum1=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNum1);

			OPR344.addNewULDWithAWB("UldNum1","0","CarrierNumericCode","prop~AWBNo","Pieces","Weight");
			libr.waitForSync(7);
			OPR344.clickEditULDdetailsByJS("UldNum1");
			libr.waitForSync(5);
			OPR344.clickMoreUldDetails();
			OPR344.captureMoreUldDetails("ActualWeight", "Occupancy");
			cust.closeTab("OPR344", "Export Manifest");
			libr.quitBrowser();


			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");

			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht2");
			cust.loginHHT(hht[0], hht[1]);

			/**build up complete AWB1 **/

			buhht.invokeBuildUpScreen();
			buhht.enterValue("UldNum1");
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteBtn();
			buhht.clickSaveCaptureChecksheet();   
			buhht.clickTopUpNoOption();
			cust.waitForSync(3);
			buhht.selectContourAndSave("Contour");
			cust.waitForSync(3);
			cust.clickBack("Build Up");	

			libr.quitApp();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			//Login to Cafeed

			driver.get(cafeed[0]);
			Thread.sleep(2000);
			cust.loginToCafeed(cafeed[1], cafeed[2]);
			Thread.sleep(2000);

			cfd.clickSearchFlightLink();
			cfd.listFlightDetails("FullFlightNo1", "Date");
			cfd.verifyULDDetails(col,awbScc,cust.data("UldNum1"));
			cfd.verifyAwbDetailsInsideULD("UldNum1", awbNumbers);
			cfd.clickAWBInsideULD("UldNum1","FullAWBNo");
			cfd.verifyDGDetails("UNID","PI","ShippingName");
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