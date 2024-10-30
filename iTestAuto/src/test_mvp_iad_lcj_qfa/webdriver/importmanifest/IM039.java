package importmanifest;

import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import postconditions.CancelFlights;
import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

public class IM039 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public CustomFunctions cust;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CaptureAWB_OPR026 OPR026;
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainOperationalFlight_FLT003 FLT003;
	public ListMessages_MSG005 MSG005;
	public SecurityAndScreening_OPR339 OPR339;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public BreakDownScreen_OPR004 OPR004;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="importmanifest";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		//excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367= new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR004=new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		cust.setPropertyValue("module", sheetName, globalVarPath);
		cust.setPropertyValue("showStopper", "false", globalVarPath);
		cust.setPropertyValue("isTcFailed", "false", globalVarPath);
		cust.setPropertyValue("executionType", "Regression", globalVarPath);	
	}
	
	
	
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
					
			//Login to iCargo
		
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
		//Flight Creation
			cust.createFlight("FlightNo");
			String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			String flightCode=WebFunctions.getPropertyValue(proppath,"flight_code");
			cust.setPropertyValue("flightNumber",flightCode+flightNo,proppath); 
			String startDate = cust.createDateFormat("dd-MMM-yyyy", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-yyyy", 7, "DAY", "");
			String FlightNum = cust.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum);
								
			/*******FLT005 - Flight Creation*******/
			
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("FlightNo", startDate, endDate);
			// Entering flight schedule data
			FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();
			cust.closeTab("FLT005", "Maintain Schedule");

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			//Writing the AWB in property file
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			map.put("AWBNo", cust.data("prop~AWBNo"));
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
			
			//Create the message FBL for AWB
			
			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_5");

			//Process the message
			
			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);

			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			
			cust.closeTab("MSG005", "List Message");

			/***** OPR026 - Execute AWB****/
			
			//Execute AWB
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination", "carrierCode");
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");	
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","val~GEN", "ShipmentDesc");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			
			/******OPR355 - Goods Acceptance*********/
			//Loose acceptance for AWB
			cust.searchScreen("OPR335", "Goods Acceptance");
			OPR335.listAWB(cust.data("prop~AWBNo"), "prop~CarrierNumericCode"); 
            OPR335.looseShipmentDetails("Location", "Pieces","Weight");
            OPR335.addLooseShipment();
            OPR335.allPartsRecieved();
            OPR335.saveAcceptance();
            cust.closeTab("OPR335", "Goods Acceptance");
			
            /*******MSG005 - List Messages*******/
            
            //Create the message FFM
            cust.createTextMessage("MessageExcelAndSheetFFM", "MessageParamFFM");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FFM_NIL");

			//Process the message			
			MSG005.enterMsgType("FFM");
			MSG005.clickList();
			libr.waitForSync(6);
			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("FlightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin")+" - "+cust.data("Transit"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
			
            /****FLT006 - Mark Flight Movements*****/
			
            // Switch Role
			cust.switchRole("Destination", "Origin", "RoleGroup");
            
			cust.searchScreen("FLT006", "Mark Flight Movements");
            FLT006.listFlight("FlightNo", "StartDate");
            FLT006.clickFlightMovementArrivalDetailsLink();
            FLT006.clickFlightMovementDepartureDetailsLink();
            FLT006.clickSave();
            cust.closeTab("FLT006", "Mark Flight Movements");
            
			/*****OPR367-Import Manifest*******/
	
			OPR367.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
            OPR367.enterShipmentDetailsInSearchBox("val~BULK");
            OPR367.maximizeAllDetails();
            OPR367.verifyIfInfoListed(false);
            OPR367.closeTab("OPR367", "Import Manifest");
            
            OPR004.searchScreen("OPR004", "Breakdown");
            OPR004.listFlightwithShipment("BULK","prop~flight_code","prop~flightNo", "StartDate");
            OPR004.verifyNoBulkULDManifestedPopUp();
            OPR004.closeTab("OPR004","Breakdown");
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

