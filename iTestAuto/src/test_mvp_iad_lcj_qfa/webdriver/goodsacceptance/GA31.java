package goodsacceptance;

import java.io.IOException;
import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import postconditions.CancelFlights;
import screens.CaptureAWB_OPR026;
import screens.DeliverCargo_OPR064;
import screens.DeliverNoteEnquiry_OPR034;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainAirportScreen_SHR006;
import screens.MaintainAndListSystemParameters_SHR048;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreening_OPR339;
import screens.TracingReports_TRC006;
import screens.WarehouseShipmentEnquiry_WHS011;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

public class GA31 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions customfunctions;
	public SecurityAndScreening_OPR339 OPR339;
	public CaptureAWB_OPR026 OPR026;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public MaintainOperationalFlight_FLT003 FLT003;
	public ListMessages_MSG005 MSG005;
	public CancelFlights cancelFlights;
	public DeliverCargo_OPR064 OPR064;
	public WarehouseShipmentEnquiry_WHS011 WHS011;
	public ImportManifest_OPR367 OPR367;
	public DeliverNoteEnquiry_OPR034 OPR034;
	public MarkFlightMovements_FLT006 FLT006;
	public DeliveryDocumentation_OPR293 OPR293;
	public TracingReports_TRC006 TRC006;
	public MaintainAirportScreen_SHR006 SHR006;
	public MaintainAndListSystemParameters_SHR048 SHR048;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="goodsacceptance";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		//excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		customfunctions = new CustomFunctions(driver, excelreadwrite, xls_Read);
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
		WHS011 = new WarehouseShipmentEnquiry_WHS011(driver, excelreadwrite, xls_Read);
		TRC006 = new TracingReports_TRC006(driver, excelreadwrite, xls_Read);
		SHR048= new MaintainAndListSystemParameters_SHR048(driver, excelreadwrite, xls_Read);
		SHR006= new MaintainAirportScreen_SHR006(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC_021")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_021")
	public void getTestSuite(Map<Object, Object> map) throws IOException {
		
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
			customfunctions.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
			customfunctions.switchRole("Origin", "Origin", "RoleGroup");
			
			/*******Pre-Condition***********/
			customfunctions.searchScreen("SHR048","Maintain And List System Parameters");
			SHR048.listParameter("warehouse.defaults.storageunit.autocreationenabledsutypes");
			String paramVal=SHR048.getParameterValue();
			map.put("paramVal", paramVal);
			SHR048.enterParametrValue("ULD");
			SHR048.saveDetails();
			customfunctions.closeTab("SHR048", "Maintain And List System Parameters");
			
			//Maintain Airport parameter change
			customfunctions.searchScreen("SHR006","Maintain Airport");
			SHR006.listAirport("Origin");
			SHR006.changeParameterValue("val~To Autogenerate Storage Unit", "N");
			customfunctions.closeTab("SHR006","Maintain Airport");
			
			
			/**** FLT003 - Create flight****/
			
			customfunctions.createFlight("FullFlightNumber");
			String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			String flightdate1 = customfunctions.createDateFormat("dd-MMM-yy", 0, "DAY", "FlightDate");
			map.put("FlightNo", flightNo);
			map.put("StartDate", flightdate1);
	        customfunctions.setPropertyValue("FlightNo",flightNo,proppath); 
	        customfunctions.setPropertyValue("flightNumber", customfunctions.data("carrierCode")+customfunctions.data("prop~flightNo"), proppath);
	        excelRead.writeDataInExcel(map, path1, sheetName, testName);
	        libr.waitForSync(1);
	        customfunctions.searchScreen("FLT003","Maintain Operational Flight");
	        FLT003.enterCarrierCode("carrierCode");
	        FLT003.listNewFlight("prop~flightNo", "StartDate","FlightNo");
	        FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
	        FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
	        FLT003.save("FLT003");
	        customfunctions.closeTab("FLT003", "Maintain Operational Flight");
            
            /*********MSG005-loading FBL*********/
            
				String flightdate = customfunctions.createDateFormat("dd-MMM-yyyy", 0, "DAY", "FlightDate");
	            String FBLDate = customfunctions.createDateFormat("ddMMM", 0, "DAY", "FBLDate");
	            map.put("StartDate", flightdate);
	            map.put("FBLDate", FBLDate);
	            map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
	            map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
			
	         //Checking AWB is fresh or Not
	           customfunctions.searchScreen("OPR026","Capture AWB");
		       OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
		       libr.waitForSync(1);
		       
		       String awbNo = customfunctions.data("prop~AWBNo");
		       map.put("AWBNo",awbNo);
		       
		       //Writing the full AWB No to property file
	           customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
	                
	           //Create the message FBL
	           customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
	           customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
	           MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_1");
	                
	          //Process the message
	         
	           MSG005.enterMsgType("FBL");
	               MSG005.clickList();
	           libr.waitForSync(6);     
	           map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
	                             +" - "+customfunctions.data("Origin"));
	           MSG005.clickCheckBox("pmkey");
	           MSG005.clickprocess();
	           customfunctions.closeTab("MSG005", "List Message");
      
            
	           /**** OPR026 - Capture AWB****/
				customfunctions.searchScreen("OPR026","Capture AWB");
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
				OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
				OPR026.asIsExecute();
				OPR026.closeTab("OPR026", "Capture AWB");


	           
				/****OPR335 - Goods Acceptance****/
                
                //Loose acceptance
				OPR335.searchScreen("OPR335", "Goods Acceptance");
				OPR335.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");  
                OPR335.looseShipmentDetails("Location", "Pieces2","Weight2");
                OPR335.addLooseShipment();
                OPR335.clickSave();
                OPR335.closeTab("OPR335", "Goods Acceptance");
                
                //relist and verify SU in acceptance
                
                OPR335.searchScreen("OPR335", "Goods Acceptance");
				OPR335.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");  
                OPR335.verifySU("");
				OPR335.closeTab("OPR335", "Goods Acceptance");
                	
                
		}	
		catch(Exception e)
		{
			//libr.writeExtent("Fail", "Test case has failed steps");
			libr.captureScreenShot("Web");
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

