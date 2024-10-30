	package importmanifest;

import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import postconditions.CancelFlights;
import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
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

public class IM043 extends BaseSetup {
	
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
	
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367= new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
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
			
			
			// creating flight number

			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = cust.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			System.out.println(FlightNum);
	        excelRead.writeDataInExcel(map, path1, sheetName, testName);
	        		
	        

			/** Flight Creation  FLT003**/
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code")+cust.data("FlightNo"), proppath);
			cust.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate", "FlightNo");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");

			
			/**MSG005-loading FBL***/

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			//Create the message FBL
			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_1");

			//Process the message
			
			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);
			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("FlightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
		
			/***** OPR026 - Execute AWB****/
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","carrierCode");	
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");	
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.saveAWB();	
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			
			//Goods acceptance
			cust.searchScreen("OPR335", "Goods Acceptance");
			OPR335.listAWB(cust.data("AWBNo"), "prop~CarrierNumericCode"); 
            OPR335.looseShipmentDetails("Location", "Pieces","Weight");
            OPR335.addLooseShipment();
            OPR335.allPartsRecieved();
            OPR335.saveAcceptance();
            cust.closeTab("OPR335", "Goods Acceptance");
			
            
            /***FFM Loading***/
			
            //Create the message FFFM
			cust.createTextMessage("MessageExcelAndSheetFFM", "MessageParamFFM");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FFM_1ULD1SHIPMENTS");

			//Process the message
			
			MSG005.enterMsgType("FFM");
			MSG005.clickList();
			libr.waitForSync(6);
			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("FlightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin")+" - "+cust.data("Destination"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
			
			/**Export Manifest**/
            
            //Load the shipment in flight from lying list
            cust.searchScreen("OPR344", "Export manifest");
            OPR344.listFlight("prop~flight_code", "FlightNo","StartDate");
            OPR344.finalizeFlight(true);
            OPR344.verifyFlightStatus("val~Finalized");
            cust.closeTab("OPR344", "Export Manifest");
				
            /****FLT006 - Mark Flight Movements*****/
			
            // Switch Role
			cust.switchRole("Destination", "Origin", "RoleGroup");
            
			cust.searchScreen("FLT006", "Mark Flight Movements");
            FLT006.listFlight("FlightNo", "StartDate");
            FLT006.clickFlightMovementArrivalDetailsLink();
            FLT006.clickFlightMovementDepartureDetailsLink();
            FLT006.clickSave();
            FLT006.close("FLT006");
            
			/** Import Manifest **/
            
			cust.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
            OPR367.maximizeAllDetails();
            OPR367.clickAddSpecialNoteLink();
            OPR367.clickSpecialNoteRadioButtonFromList("val~Others");
            OPR367.SaveDetails();
			OPR367.closeTab("OPR367", "Import Manifest");
			
			/**Relist Import Mnaifest Screen**/
			cust.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
            OPR367.maximizeAllDetails();
            OPR367.verifySpecialNoteAfterAdd("val~Others");
            OPR367.closeTab("OPR367", "Import Manifest");
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

