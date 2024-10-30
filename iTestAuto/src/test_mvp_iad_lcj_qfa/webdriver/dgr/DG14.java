package dgr;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import postconditions.CancelFlights;
import screens.CaptureAWB_OPR026;
import screens.DeliverCargo_OPR064;
import screens.DeliverNoteEnquiry_OPR034;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.GenerateNOTOC_OPR017;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainAndListSystemParameters_SHR048;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreening_OPR339;
import screens.WarehouseShipmentEnquiry_WHS011;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class DG14 extends BaseSetup {
	
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
	public MaintainAndListSystemParameters_SHR048 SHR048;
	public ListMessages_MSG005 MSG005;
	public CancelFlights cancelFlights;
	public DeliverCargo_OPR064 OPR064;
	public WarehouseShipmentEnquiry_WHS011 WHS011;
	public ImportManifest_OPR367 OPR367;
	public DeliverNoteEnquiry_OPR034 OPR034;
	public MarkFlightMovements_FLT006 FLT006;
	public GenerateNOTOC_OPR017 OPR017;
	public DeliveryDocumentation_OPR293 OPR293;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="dgr";	
	
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
		OPR064 = new DeliverCargo_OPR064(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR017=new GenerateNOTOC_OPR017(driver, excelreadwrite, xls_Read);
		
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
			customfunctions.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
			customfunctions.switchRole("Origin", "Origin", "RoleGroup");
			
			 /**** FLT003 - Create flight****/
			
			  customfunctions.createFlight("FlightNo");
			  customfunctions.createFlight("FlightNo2");
			   
	            customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
	            String flightdate1 = customfunctions.createDateFormat("dd-MMM-yyyy", 0, "DAY", "FlightDate");
	            map.put("StartDate", flightdate1);
	            libr.waitForSync(1);
	            
	          /*************************FLIGHT 1*****************/
	            customfunctions.searchScreen("FLT003","Maintain Operational Flight");
	            FLT003.listNewFlight("prop~flightNo", "StartDate","FlightNo");
	            FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
	            FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
	            FLT003.save("FLT003");
	            customfunctions.closeTab("FLT003", "Maintain Operational Flight");
	            String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			 
	            map.put("FlightNo", flightNo);
	            
	            
	            /*************************FLIGHT 2*****************/
	           customfunctions.searchScreen("FLT003","Maintain Operational Flight");
	            FLT003.listNewFlight("prop~flightNo2", "StartDate","FlightNo2");
	            FLT003.enterFlightDetails("Route2", "scheduleType", "Origin", "FCTL", "flightType");
	            FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
	            FLT003.save("FLT003");
	            customfunctions.closeTab("FLT003", "Maintain Operational Flight");
	            String flightNo2 = WebFunctions.getPropertyValue(proppath,"flightNo2");
			 
	            map.put("FlightNo2", flightNo2);
			  
            
            /*********MSG005-loading FBL*********/
            
				
	            String FBLDate = customfunctions.createDateFormat("ddMMM", 0, "DAY", "FBLDate");
	            map.put("FBLDate", FBLDate);
	            map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
	            map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
	           
			
	        //Checking AWB is fresh or Not
	            	           customfunctions.searchScreen("OPR026","Capture AWB");
		       OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
		       libr.waitForSync(1);
		       
		       String awbNo = customfunctions.data("prop~AWBNo");
		       map.put("AWBNo",awbNo);
		       excelRead.writeDataInExcel(map, path1, sheetName, testName);
		       
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
				OPR026.enterRouting("Transit","prop~flight_code");   
				OPR026.enterSecondRouting("Destination","prop~flight_code");
				OPR026.selectSCI("SCI");
				OPR026.enterSCC(customfunctions.data("SCC"));
				OPR026.enterAgentCode("AgentCode");    
				OPR026.provideShipperCode("shipperCode");
				OPR026.provideConsigneeCode("consigneeCode");
				OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
				OPR026.clickChargesAcc();
				OPR026.	provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
				OPR026.saveAWBDGR();
				OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
				//Capture DGR details
				OPR026.enterSCC(customfunctions.data("SCC"));
				OPR026.clickDGRGoods();
				OPR026.captureDGRDetails("UNID", "ProperShippingName", "Pieces", "Pieces", "PerPkgUnit","PI",false);
				OPR026.asIsExecute();
				customfunctions.closeTab("OPR026", "Capture AWB");
				
				/**************** GOODS ACCEPTANCE*****************/
				//Goods acceptance
				customfunctions.searchScreen("OPR335", "Goods Acceptance");
				customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
				
			
				OPR335.looseShipmentDetails("Location", "Pieces","Weight");
				OPR335.addLooseShipment();
				OPR335.allPartsRecieved();
				OPR335.saveAcceptance();
				customfunctions.closeTab("OPR335", "Goods Acceptance");
				
				
				/***************EXPORT MANIFEST************/
	            
	        	
	        	
				customfunctions.searchScreen("OPR344", "Export manifest");
				String ULDNo=customfunctions.create_uld_number("val~AKE", "carrierCode");
				map.put("ULDNo",ULDNo);
				OPR344.listFlight("prop~flight_code", "prop~flightNo","StartDate");
				OPR344.addNewULDWithAWB("ULDNo","0","prop~CarrierNumericCode","prop~AWBNo","Pieces","Weight");
				OPR344.clickNOTOC();
				OPR017.generateNOTOCandVerifyReport("OPR344");
				OPR017.clickClose();
				OPR344.manifestDetails();
				OPR344.finalizeFlight();
				customfunctions.closeTab("OPR344", "Export manifest");
				
		/**************** MARK FLIGHT MOVEMENT*************/
				
				customfunctions.searchScreen("FLT006", "Mark Flight Movements");
				FLT006.listFlight("prop~flightNo", "StartDate");
				FLT006.clickFlightMovementArrivalDetailsLink();
				FLT006.clickFlightMovementDepartureDetailsLink();
				FLT006.clickSave();
				FLT006.closeTab("FLT006", "Mark Flight Movements");
				
				/**Switch role to Destination**/
				customfunctions.switchRole("Transit", "Origin", "RoleGroup");
	           
				/** Import Manifest **/
	          
				customfunctions.searchScreen("OPR367", "Import Manifest");
				OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
				OPR367.clickCheckBox_ULD(customfunctions.data("ULDNo"));
				OPR367.clickBreakdownButton();
				OPR367.selectThruCheckbox();
				OPR367.SaveDetailsInOPR004();
				OPR367.enterDetailsInViolations("val~Size of goods","val~Breakdown done");
				OPR367.clickYesButton();
				OPR367.closeTab("OPR367", "Import Manifest");
				
			   /*****Export manifest***/
				
				customfunctions.searchScreen("OPR344", "Export manifest");
				OPR344.listFlight("prop~flight_code", "prop~flightNo2","StartDate");
				OPR344.clickNOTOC();
				OPR017.clickULDExpand();
				OPR017.verifyULDAsscWithUNID("ULDNo","UNID");
				OPR017.generateNOTOCandVerifyReport("OPR344");
				OPR017.clickClose();
				customfunctions.closeTab("OPR344", "Export manifest");
				
		}	
		catch(Exception e)
		{
			
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
		}

	}
}


