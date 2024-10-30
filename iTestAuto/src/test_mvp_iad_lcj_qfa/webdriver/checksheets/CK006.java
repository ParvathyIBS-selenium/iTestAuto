package checksheets;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AWBClearance_OPR023;
import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.ChecksheetHHT;
import screens.DeliverCargo_OPR064;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListCheckSheetConfig_SHR094;
import screens.ListMessages_MSG005;
import screens.ListTemplates_SHR093;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreeningHHT;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class CK006 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions customfunctions;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005;
	public GoodsAcceptance_OPR335 OPR335;
	public ListCheckSheetConfig_SHR094 SHR094;
	public ListTemplates_SHR093 SHR093;
	public ImportManifest_OPR367 OPR367;
	public ExportManifest_OPR344 OPR344;
	public BreakDownScreen_OPR004 OPR004;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptanceHHT gahht;
	public SecurityAndScreeningHHT sechht;
	public ChecksheetHHT checkhht;
	public MaintainFlightSchedule_FLT005 FLT005;
	public MarkFlightMovements_FLT006 FLT006;
	public AWBClearance_OPR023 OPR023;
	public DeliveryDocumentation_OPR293 OPR293;
	public DeliverCargo_OPR064 OPR064;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName = "checksheets";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		// excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		customfunctions = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		SHR094 = new ListCheckSheetConfig_SHR094(driver, excelreadwrite, xls_Read);
		SHR093 = new ListTemplates_SHR093(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		sechht = new SecurityAndScreeningHHT(driver, excelreadwrite, xls_Read);
		checkhht = new ChecksheetHHT(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR023=new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR064 = new DeliverCargo_OPR064(driver, excelreadwrite, xls_Read);
		
	}

	@DataProvider(name = "HHT07")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "HHT07")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			libr.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			//Login to iCargo
			
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			customfunctions.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			
			
			/******* FLT005 - MAINTAIN FLIGHT ******/

			// creating flight number

			customfunctions.createFlight("FullFlightNumber");
			String startDate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = customfunctions.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = customfunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", customfunctions.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
			System.out.println(FlightNum);

			// Maintain Flight Screen (FLT005)

			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");

			// Entering flight schedule data

			FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");

			FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();

			customfunctions.waitForSync(7);
			customfunctions.closeTab("FLT005", "Maintain Schedule");
			customfunctions.waitForSync(1);





			/******MSG005-loading FBL****/

			//Checking AWB is fresh or Not
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);


			//Writing the full AWB No
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);


			//Create the message FBL
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_1");



			//Process the message
				
			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);


			map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
					+" - "+customfunctions.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			customfunctions.closeTab("MSG005", "List Message");
			
			
			/**** OPR339 - Security & Screening for awbno1****/
            
			customfunctions.searchScreen("OPR339", "OPR339 - Security & Sceening");
            OPR339.listAWB("prop~AWBNo", "prop~CarrierNumericCode", "OPR339 - Security & Sceening");
            OPR339.clickYesButton();
            OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
            OPR339.saveSecurityDetails();
            customfunctions.closeTab("OPR339", "Security & Sceening");	
            
			
			/**** OPR026 - Capture AWB****/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.enterRouting("Destination","prop~flight_code");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.asIsExecute();
			customfunctions.closeTab("OPR026", "Capture AWB");

			/**** OPR355 - Goods Acceptance ****/

			// Goods acceptance
			customfunctions.searchScreen("OPR335", "Goods Acceptance");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			customfunctions.closeTab("OPR335", "Goods Acceptance");
			
			/***** OPR344 - Export manifest ****/

			// Load the shipment in flight from lying list
			customfunctions.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "prop~flightNo", "StartDate");
			String uldNum = OPR344.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWB("UldNum", "0", "prop~CarrierNumericCode", "prop~AWBNo", "Pieces", "Weight");
			OPR344.manifestDetails();
			OPR344.finalizeFlight();
			OPR344.verifyFlightStatus("val~Finalized");
			customfunctions.closeTab("OPR344", "Export Manifest");

			/**** FLT006 - Mark Flight Movements *****/
			// Switch Role
			customfunctions.switchRole("Destination", "Origin", "RoleGroup");

			customfunctions.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("prop~flightNo", "StartDate");
			FLT006.clickFlightMovementArrivalDetailsLink();
			FLT006.clickFlightMovementDepartureDetailsLink();
			FLT006.clickSave();
			FLT006.close("FLT006");

			/** Import Manifest **/

			customfunctions.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code", "prop~flightNo", "StartDate");
			String pmkey = Excel.getCellValue(path1, sheetName, "CK006", "UldNum");
			OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			OPR004.enterBdnLocationDetails1("Location");
			OPR004.saveOPR004();
			customfunctions.verifyErrorMessage("OPR004", "val~The Check Sheet for "+customfunctions.data("prop~CarrierNumericCode")+"-"+customfunctions.data("prop~AWBNo")+" is not complete");
			OPR004.clickCheckBoxAll();
			OPR004.clickCaptureCheckSheet();
			OPR004.checksheetCapture();
			OPR367.closeFromOPR004();
			OPR367.closeTab("OPR367", "Import Manifest");
			
			/**Breakdown Screen**/
			customfunctions.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
            String pmkey1 = Excel.getCellValue(path1, sheetName, "CK006", "UldNum");
			OPR367.clickCheckBox_ULD(pmkey1);
			OPR367.clickBreakdownButton();
			OPR004.enterBdnLocationDetails1("Location");
			OPR367.clickBreakdownComplete();
            OPR367.closeFromOPR004();
            OPR367.closeTab("OPR367", "Import Manifest");
            
            /**Breakdown Screen**/
			customfunctions.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
            String pmkey2 = Excel.getCellValue(path1, sheetName, "CK006", "UldNum");
			OPR367.clickCheckBox_ULD(pmkey2);
			OPR367.clickBreakdownButton();
            OPR004.clickCheckBoxAll();
            OPR004.clickCaptureCheckSheet();
            OPR004.verifyChecksheetCaptured();
            OPR367.closeFromOPR004();
            OPR367.closeTab("OPR367", "Import Manifest");
			
			/** Delivery Documentation **/

			customfunctions.searchScreen("OPR293", "Delivery Documentation");
			OPR293.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
			OPR293.selectAllAWBs();
			OPR293.clickChecksheet();
			OPR293.checksheetCapture();
			OPR293.closeTab("OPR293", "Delivery Documentation");
			
			
			/** Delivery Documentation **/

			customfunctions.searchScreen("OPR293", "Delivery Documentation");
			OPR293.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
			OPR293.selectAllAWBs();
			OPR293.clickChecksheet();
			OPR293.verifyChecksheetCaptured();
			OPR293.enterCaptureHandOverDetails();
			OPR293.enterCustomer("CustomerName");
			OPR293.generateDeliveryID3();
			customfunctions.closeTab("OPR293", "Delivery Documentation");
	

			/**********OPR293-Deliver Cargo*********************/
			customfunctions.searchScreen("OPR064", "Deliver Cargo");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Deliver Cargo");
			OPR064.enterDeliveredTo("Test");
			OPR064.clickSave();
			customfunctions.closeTab("OPR064", "Deliver Cargo");
			

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
