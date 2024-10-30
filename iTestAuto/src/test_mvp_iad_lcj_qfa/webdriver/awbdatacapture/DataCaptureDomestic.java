package awbdatacapture;

import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import postconditions.CancelFlights;
import screens.CaptureAWB_OPR026;
import screens.CaptureDGDetails_OPR350;
import screens.CaptureHAWB_OPR029;
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

public class DataCaptureDomestic extends BaseSetup {
	
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
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainOperationalFlight_FLT003 FLT003;
	public ListMessages_MSG005 MSG005;
	public SecurityAndScreening_OPR339 OPR339;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public ExportManifest_OPR344 OPR344;
	public CaptureDGDetails_OPR350 OPR350;
	public CaptureHAWB_OPR029 OPR029;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="awbdatacapture";	
	
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
	
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367= new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR350=new CaptureDGDetails_OPR350(driver, excelreadwrite, xls_Read);
		OPR029=new CaptureHAWB_OPR029(driver, excelreadwrite, xls_Read);
	
		customfunctions.setPropertyValue("module", sheetName, globalVarPath);
		customfunctions.setPropertyValue("showStopper", "false", globalVarPath);
		customfunctions.setPropertyValue("isTcFailed", "false", globalVarPath);
		customfunctions.setPropertyValue("executionType", "Regression", globalVarPath);
	
		
		
		
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
		/**
		 * 
		 * CASE 1 : executeDomAWBAndCheckFWBTriggered
		 * CASE 2 : voidAwb
		 * CASE 3 : captureDGShipmentWithDataCapture
		 * CASE 4 : captureDGShipmentfromDGDetailsScreen
		 * CASE 5 : captureAWBWithHouseDetails
		 * CASE 6 : updateHouseDetails
		 * 
		 * **/
			
			
			/******* 	case 1   *******/

			//create AWB
			String flightStartdate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("flightStartdate",flightStartdate);
			String flightdate = customfunctions.createDateFormat("ddMMMyy", 0, "DAY", "FlightDate");
			String FBLDate = customfunctions.createDateFormat("ddMMM", 0, "DAY", "FBLDate");
			map.put("StartDate", flightdate);
			map.put("FBLDate", FBLDate);
			map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
            map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			
			//Login to iCargo
		
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			customfunctions.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
			customfunctions.switchRole("Origin", "Origin", "RoleGroup");
			
			/** Flight Creation **/
			
			customfunctions.createFlight("FlightNo");
			String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			map.put("FlightNo", flightNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
			customfunctions.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "flightStartdate", "FlightNo");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");
			
			//Checking AWB is fresh or Not
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo = WebFunctions.getPropertyValue(proppath,"AWBNo");
			map.put("AWBNo", AWBNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			
			
            //Writing the full AWB No to property file
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
            
			
			
            /** Create the message FBL **/
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_1");
                  
           /** Process the message **/
            
            MSG005.enterMsgType("FBL");
            MSG005.clickList();
            libr.waitForSync(6);      
            map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
                              +" - "+customfunctions.data("Origin"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickprocess();
            customfunctions.closeTab("MSG005", "List Message");

			
			/** Capture AWB Details **/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
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
			OPR026.sendFWB();
			OPR026.enterSendFwbDetails("EMAIL OUT");
			OPR026.asIsExecute();
			OPR026.close("OPR026");
			
			
			
			/**** OPR339 - Security & Screening****/
            
			customfunctions.searchScreen("OPR339", "OPR339 - Security & Sceening");
            OPR339.listAWB("AWBNo", "prop~CarrierNumericCode", "OPR339 - Security & Sceening");
            OPR339.clickYesButton();
            OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
            OPR339.saveSecurityDetails();
            customfunctions.closeTab("OPR339", "Security & Sceening");
			
			/**Checking FWB is triggered**/
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			
			MSG005.enterMsgType("FWB");
			MSG005.clickList();
			libr.waitForSync(6);      
			map.put("pmkey", customfunctions.data("prop~CarrierNumericCode")+" - "+customfunctions.data("prop~AWBNo")+" - "+customfunctions.data("Origin")+" - "+customfunctions.data("Destination"));
			MSG005.clickCheckBox("pmkey");
			MSG005.verifyMessageTriggered("pmkey", "FWB");        
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
			
			
			/*****************************Case 2*****************************/
			
			
			/** Flight Creation **/
			
			customfunctions.createFlight("FlightNo");
			String flightNo1= WebFunctions.getPropertyValue(proppath,"flightNo");
			map.put("FlightNo", flightNo1);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
			customfunctions.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "flightStartdate", "FlightNo");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");
			
			//Checking AWB is fresh or Not
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo1 = WebFunctions.getPropertyValue(proppath,"AWBNo");
			map.put("AWBNo1", AWBNo1);
			libr.waitForSync(1);
			
			
            //Writing the full AWB No to property file
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
            
			
			
            /** Create the message FBL **/
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_1");
                  
           /** Process the message **/
            
            MSG005.enterMsgType("FBL");
            MSG005.clickList();
            libr.waitForSync(6);      
            map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
                              +" - "+customfunctions.data("Origin"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickprocess();
            customfunctions.closeTab("MSG005", "List Message");

			
			/** Capture AWB Details **/
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
			OPR026.clickDeleteAWB();
			customfunctions.verifyErrorMessage("Capture AWB", "val~is voided");
			OPR026.close("OPR026");
			
			
			
			
			/******************************CASE 3 **************************************/
			
			
			/** Flight Creation **/
			
			customfunctions.createFlight("FlightNo");
			String flightNo2 = WebFunctions.getPropertyValue(proppath,"flightNo");
			map.put("FlightNo2", flightNo2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
			customfunctions.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "flightStartdate", "FlightNo");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");
			
			//Checking AWB is fresh or Not
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo2 = WebFunctions.getPropertyValue(proppath,"AWBNo");
			map.put("AWBNo2", AWBNo2);
			System.out.println(map.get("AWBNo2"));
			libr.waitForSync(1);
			
			
            //Writing the full AWB No to property file
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
            
			
			
            /** Create the message FBL **/
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam2");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_1");
                  
           /** Process the message **/
            
            MSG005.enterMsgType("FBL");
            MSG005.clickList();
            libr.waitForSync(6);      
            map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
                              +" - "+customfunctions.data("Origin"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickprocess();
            customfunctions.closeTab("MSG005", "List Message");

			
			/** Capture AWB Details **/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","carrierCode");	
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");	
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","commodityCode2", "shipmentDes2");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.clickSave();
			customfunctions.handleAlert("Dismiss", "OPR026");
			OPR026.captureDGRDetails("UNIDNo", "DgGoodsName", "netQuantityperPackage", "noOfPackage", "PerPackageUnit","PI");
			OPR026.saveAWB();	
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.close("OPR026");
			
			
			
			
			
			/******************************CASE 4 **************************************/
			
			
			/** Flight Creation **/
			
			customfunctions.createFlight("FlightNo");
			String flightNo3 = WebFunctions.getPropertyValue(proppath,"flightNo");
			map.put("FlightNo3", flightNo3);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
			customfunctions.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "flightStartdate", "FlightNo");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");
			
			//Checking AWB is fresh or Not
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo3 = WebFunctions.getPropertyValue(proppath,"AWBNo");
			map.put("AWBNo3", AWBNo3);
			System.out.println(map.get("AWBNo3"));
			libr.waitForSync(1);
			
			
            //Writing the full AWB No to property file
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
            
			
			/** Capture AWB Details **/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","carrierCode");	
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");	
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","commodityCode2", "shipmentDes2");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.saveAWBDGR();	
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			OPR026.closeTab("OPR026","Capture AWB");
			
			
			/**Capturing DG Details**/
			customfunctions.searchScreen("OPR350","Capture DG Details");
			OPR350.listAWB("AWBNo", "prop~CarrierNumericCode", "Capture DG Details");
			OPR350.captureDGRDetails("UNIDNo", "DgGoodsName", "netQuantityperPackage", "noOfPackage", "PerPackageUnit","PI");
			OPR350.save("OPR350");
			OPR350.closeTab("OPR350","Capture DG Details");
			
			
			
			
			
			/*****************************Case 5*****************************/
			
			
			/** Flight Creation **/
			
			customfunctions.createFlight("FlightNo");
			String flightNo4 = WebFunctions.getPropertyValue(proppath,"flightNo");
			map.put("FlightNo4", flightNo4);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
			customfunctions.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "flightStartdate", "FlightNo");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");
			
			//Checking AWB is fresh or Not
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo4 = WebFunctions.getPropertyValue(proppath,"AWBNo");
			map.put("AWBNo4", AWBNo4);
			libr.waitForSync(1);
			
			
            //Writing the full AWB No to property file
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
            
			
			
            /** Create the message FBL **/
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_1");
                  
           /** Process the message **/
            
            MSG005.enterMsgType("FBL");
            MSG005.clickList();
            libr.waitForSync(6);      
            map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
                              +" - "+customfunctions.data("Origin"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickprocess();
            customfunctions.closeTab("MSG005", "List Message");

			
			/** Capture AWB Details **/
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
			OPR026.close("OPR026");
			
			
			/**Capture House Details**/
			customfunctions.searchScreen("OPR029","Capture House AWB");
			OPR029.listAWB("AWBNo", "prop~CarrierNumericCode", "OPR029");
			customfunctions.handleAlert("Accept", "OPR029");
			customfunctions.switchToMainScreen("OPR029");
			OPR029.clickAddUpdateHAWBBtn();
			OPR029.addHAWBDetails("HAWB", "shipperCode", "consigneeCode", "Origin", "Destination", "Pieces", "Weight");
			OPR029.save("OPR029");
			OPR026.close("OPR029");
				
				
			/**List and Execute AWB in OPR026**/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			OPR026.closeTab("OPR026","Capture AWB");
				
				
				
			/*****************************Case 6*****************************/
			
			
			/** Flight Creation **/
			
			customfunctions.createFlight("FlightNo");
			String flightNo5 = WebFunctions.getPropertyValue(proppath,"flightNo");
			map.put("FlightNo5", flightNo5);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
			customfunctions.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "flightStartdate", "FlightNo");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");
			
			//Checking AWB is fresh or Not
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo5 = WebFunctions.getPropertyValue(proppath,"AWBNo");
			map.put("AWBNo5", AWBNo5);
			libr.waitForSync(1);
			
			
            //Writing the full AWB No to property file
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
        
			
			/** Capture AWB Details **/
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
			OPR026.close("OPR026");
			
			
			/**Capture House Details**/
			customfunctions.searchScreen("OPR029","Capture House AWB");
			OPR029.listAWB("AWBNo", "prop~CarrierNumericCode", "OPR029");
			customfunctions.handleAlert("Accept", "OPR029");
			customfunctions.switchToMainScreen("OPR029");
			OPR029.clickAddUpdateHAWBBtn();
			OPR029.addHAWBDetails("HAWB", "shipperCode", "consigneeCode", "Origin", "Destination", "Pieces", "Weight");
			customfunctions.switchToMainScreen("OPR029");
			OPR029.save("OPR029");
			OPR026.close("OPR029");
			
			/**Updating HAWB Details**/
			
			customfunctions.searchScreen("OPR029","Capture House AWB");
			OPR029.listAWB("AWBNo", "prop~CarrierNumericCode", "OPR029");
			map.put("pmkey", customfunctions.data("HAWB"));
			OPR029.clickCheckBox("pmkey");
			OPR029.clickAddUpdateHAWBBtn();
			OPR029.updateHAWBPiecesWeightSlac("pcs1", "wgt1", "pcs1");
			customfunctions.switchToMainScreen("OPR029");
			OPR029.save("OPR029");
			customfunctions.handleAlert("Accept", "OPR029");
			OPR026.close("OPR029");
			
			
			/**List and Execute AWB in OPR026**/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.clickHAWBDocFinalized();
			OPR026.asIsExecute();
			OPR026.closeTab("OPR026","Capture AWB");
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace(	);
		}

	}
	
}

