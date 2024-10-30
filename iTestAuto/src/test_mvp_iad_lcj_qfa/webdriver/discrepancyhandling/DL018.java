package discrepancyhandling;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import postconditions.CancelFlights;
import screens.CaptureAWB_OPR026;
import screens.CaptureMiscellaneousDiscrepancy_OPR045;
import screens.DeliverCargo_OPR064;
import screens.DeliverNoteEnquiry_OPR034;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListDiscrepancies_OPR050;
import screens.ListFlightDiscrepancy_OPR047;
import screens.ListMessages_MSG005;
import screens.MaintainAndListSystemParameters_SHR048;
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

public class DL018 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
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
	public MaintainOperationalFlight_FLT003 FLT003;
	public ListMessages_MSG005 MSG005;
	public CancelFlights cancelFlights;
	public DeliverCargo_OPR064 OPR064;
	public ImportManifest_OPR367 OPR367;
	public DeliverNoteEnquiry_OPR034 OPR034;
	public MarkFlightMovements_FLT006 FLT006;
	public DeliveryDocumentation_OPR293 OPR293;
	public ListDiscrepancies_OPR050 OPR050;
	public ListFlightDiscrepancy_OPR047 OPR047;
	public CaptureMiscellaneousDiscrepancy_OPR045 OPR045;
	public MaintainAndListSystemParameters_SHR048 SHR048;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="discrepancyhandling";	
	
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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR064 = new DeliverCargo_OPR064(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR034= new DeliverNoteEnquiry_OPR034(driver, excelreadwrite, xls_Read);
		OPR050= new ListDiscrepancies_OPR050(driver, excelreadwrite, xls_Read);
		OPR047 = new ListFlightDiscrepancy_OPR047(driver, excelreadwrite, xls_Read);
		OPR045 = new CaptureMiscellaneousDiscrepancy_OPR045(driver, excelreadwrite, xls_Read);
		SHR048 = new MaintainAndListSystemParameters_SHR048(driver, excelreadwrite, xls_Read);
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
			
			// Switch Role
			cust.switchRole("Origin", "Origin", "RoleGroup");
			
			/**** FLT003 - Create flight****/
			
			cust.createFlight("FlightNo");
		    String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
		    String flightdate1 = cust.createDateFormat("dd-MMM-yyyy", 0, "DAY", "FlightDate");
		    map.put("FlightNo", flightNo);
		    map.put("StartDate", flightdate1);
		    cust.setPropertyValue("FlightNo",flightNo,proppath); 
            cust.setPropertyValue("flightNumber", cust.data("prop~flight_code")+cust.data("prop~flightNo"), proppath);
            excelRead.writeDataInExcel(map, path1, sheetName, testName);
            libr.waitForSync(1);
            cust.searchScreen("FLT003","Maintain Operational Flight");
            FLT003.listNewFlight("prop~flightNo", "StartDate","FlightNo");
            FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
            FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
            FLT003.save("FLT003");
            cust.closeTab("FLT003", "Maintain Operational Flight");
			    
            
            /*********MSG005-loading FBL*********/
            
            String flightdate = cust.createDateFormat("dd-MMM-yy", 0, "DAY", "FlightDate");
            String FBLDate = cust.createDateFormat("ddMMM", 0, "DAY", "FBLDate");
            map.put("StartDate", flightdate);
            map.put("FBLDate", FBLDate);
            map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
            map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
            excelRead.writeDataInExcel(map, path1, sheetName, testName);
            libr.waitForSync(1);
		
           //Checking AWB is fresh or Not
           cust.searchScreen("OPR026","Capture AWB");
	       OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
	       libr.waitForSync(1);
	       
	       String awbNo = cust.data("prop~AWBNo");
	       map.put("AWBNo",awbNo);
	       excelRead.writeDataInExcel(map, path1, sheetName, testName);
	       
	       //Writing the full AWB No to property file
           cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
                
           //Create the message FBL
           cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
           cust.searchScreen("MSG005", "MSG005 - List Messages");
           MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_5");
                
          //Process the message
           MSG005.enterMsgType("FBL");
               MSG005.clickList();
           libr.waitForSync(6);     
           map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
                             +" - "+cust.data("Origin"));
           MSG005.clickCheckBox("pmkey");
           MSG005.clickprocess();
           cust.closeTab("MSG005", "List Message");
      
	           
           /**** OPR026 - Capture AWB****/
           cust.searchScreen("OPR026","Capture AWB");
           OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
           OPR026.updateOrigin("Origin");
           OPR026.updateDestination("Destination");
           OPR026.enterRouting("Destination","prop~flight_code");       
           OPR026.selectSCI("SCI");
           OPR026.enterAgentCode("AgentCode");    
           OPR026.provideShipperCode("shipperCode");
           OPR026.provideConsigneeCode("consigneeCode");
           OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
           OPR026.clickChargesAcc();
           OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");          
           OPR026.asIsExecute();
           cust.closeTab("OPR026", "Capture AWB");

           /****OPR355 - Goods Acceptance****/
           
           //ULD acceptance
           cust.searchScreen("OPR335", "Goods Acceptance");
           cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
           String uldNo=OPR335.create_uld_number("UldType", "prop~flight_code");
           map.put("UldNum", uldNo);
           excelRead.writeDataInExcel(map, path1, sheetName, testName);
           libr.waitForSync(6);
           OPR335.uldShipmentDetails("Pieces","Weight", "Location", "UldNum","");
           OPR335.addULDDetails();
           OPR335.allPartsRecieved();
           OPR335.saveAcceptance();
           cust.closeTab("OPR335", "Goods Acceptance");
           
           /***********FFM Loading**************/
			
           //Create the message FFM
			cust.createTextMessage("MessageExcelAndSheetFFM", "MessageParamFFM");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FFM_1ULD1SHIPMENTS");

			//Process the message
			
			MSG005.enterMsgType("FFM");
			MSG005.clickList();
			libr.waitForSync(6);
			System.out.println(cust.data("prop~flight_code"));
			System.out.println(cust.data("FlightNo"));
			System.out.println(cust.data("Day"));
			System.out.println(cust.data("Month").toUpperCase());
			System.out.println(cust.data("Origin"));
			System.out.println(cust.data("Destination"));
			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("FlightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin")+" - "+cust.data("Destination"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
           
           /*****OPR344 - Export manifest****/
           
           //List and finalize the flight
			cust.searchScreen("OPR344", "Export manifest");
	        OPR344.listFlight("prop~flight_code", "FlightNo","StartDate");
	        OPR344.finalizeFlight();
	        OPR344.verifyFlightStatus("val~Finalized");
	        cust.closeTab("OPR344", "Export Manifest");
           
           /****FLT006 - Mark Flight Movements*****/
           // Switch Role
	       cust.switchRole("Destination", "Origin", "RoleGroup");
           
           cust.searchScreen("FLT006", "Mark Flight Movements");
           FLT006.listFlight("prop~flightNo", "StartDate");
           FLT006.clickFlightMovementArrivalDetailsLink();
           FLT006.clickFlightMovementDepartureDetailsLink();
           FLT006.clickSave();
           FLT006.close("FLT006");
           
           /*****OPR367 - Import Manifest*******/
           //Breakdown partial pieces and weight and verify warning message
           cust.searchScreen("OPR367", "Import Manifest");
           OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
           OPR367.maximizeAllDetails();
           String pmkey = Excel.getCellValue(path1,sheetName, "DL018", "UldNum");
           OPR367.clickCheckBox_ULD(pmkey);
           OPR367.clickBreakDownandBreakdownComplete("Location", "Pieces2","Weight2");
           String expMessage="1 AWBs,"+cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo")+".";
           OPR367.verifyWarningMessage(expMessage);
           OPR367.clickYesButton();  
           OPR367.closeTab("OPR367", "Import Manifest");
           
           
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			libr.quitBrowser();
			Assert.assertFalse(true, "The test step is failed");
		}

	}
}
