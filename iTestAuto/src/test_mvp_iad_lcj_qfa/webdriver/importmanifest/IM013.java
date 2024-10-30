package importmanifest;

import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import postconditions.CancelFlights;
import screens.BreakDownEnquiry_OPR005;
import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.DeliverCargo_OPR064;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ImportShipmentListing_OPR043;
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

public class IM013 extends BaseSetup {
	
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
	public BreakDownEnquiry_OPR005 OPR005;
	public BreakDownScreen_OPR004 OPR004;
	public ImportShipmentListing_OPR043 OPR043;
	public DeliveryDocumentation_OPR293 OPR293;
	public DeliverCargo_OPR064 OPR064;
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
		customfunctions = new CustomFunctions(driver, excelreadwrite, xls_Read);
	
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367= new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR043=new ImportShipmentListing_OPR043(driver, excelreadwrite, xls_Read);
		OPR005=new BreakDownEnquiry_OPR005(driver, excelreadwrite, xls_Read);
		OPR004=new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR293=new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR064=new DeliverCargo_OPR064(driver, excelreadwrite, xls_Read);
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
		

			//create AWB

			String flightdate = customfunctions.createDateFormat("ddMMMyy", 0, "DAY", "FlightDate");
			String FBLDate = customfunctions.createDateFormat("ddMMM", 0, "DAY", "FBLDate");
			String flightStartdate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("flightStartdate",flightStartdate);
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
			
			//Checking AWB1 is fresh or Not
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo1 = WebFunctions.getPropertyValue(proppath,"AWBNo");
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
			String FullAWBNo1 = WebFunctions.getPropertyValue(proppath,"FullAWBNo");
			map.put("AWBNo", AWBNo1);
			map.put("FullAWBNo", FullAWBNo1);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			
			/** Create the message FBL for AWB 1 **/
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_1");
           	
           	/** Process the message 1 **/
           
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
            
            /**FWB 1**/
            
            /** Create the message FBL for AWB 1 **/
			customfunctions.createTextMessage("MessageExcelAndSheet2", "MessageParam2");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FWB_LOAD");
           	customfunctions.closeTab("MSG005", "List Message");
           	
            
          
			
			/** Capture AWB Details for awbno1 **/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.asIsExecute();
			OPR026.close("OPR026");
			
			
			/****OPR355 - Goods Acceptance 1****/
            
	          
			customfunctions.searchScreen("OPR335", "Goods Acceptance");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
            OPR335.looseShipmentDetails("Location", "Pieces","Weight");
            OPR335.addLooseShipment();
            OPR335.allPartsRecieved();
            OPR335.saveAcceptance();
            customfunctions.closeTab("OPR335", "Goods Acceptance");
            
			
            /** Checking AWB2 is fresh or Not **/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo2 = WebFunctions.getPropertyValue(proppath,"AWBNo");
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
			String FullAWBNo2 = WebFunctions.getPropertyValue(proppath,"FullAWBNo");
			map.put("AWBNo2", AWBNo2);
			map.put("FullAWBNo2", FullAWBNo2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
            
            
           /** Create the message FBL for AWB 2 **/
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_1");
                  

            /** Process the message 2 **/
            
            MSG005.enterMsgType("FBL");
            MSG005.clickList();
            libr.waitForSync(6);   
            map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()+" - "+customfunctions.data("Origin"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickprocess();
            customfunctions.closeTab("MSG005", "List Message");
			
			
			
			
			/**** OPR339 - Security & Screening for awbno2****/
            
			customfunctions.searchScreen("OPR339", "OPR339 - Security & Sceening");
            OPR339.listAWB("prop~AWBNo", "prop~CarrierNumericCode", "OPR339 - Security & Sceening");
            OPR339.clickYesButton();
            OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
            OPR339.saveSecurityDetails();
            customfunctions.closeTab("OPR339", "Security & Sceening");
            
            

            /**FWB 2**/
            
			customfunctions.createTextMessage("MessageExcelAndSheet2", "MessageParam2");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FWB_LOAD");
           	customfunctions.closeTab("MSG005", "List Message");
           	
			
			/** Capture AWB Details for awb no 2**/
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo2", "prop~CarrierNumericCode");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.asIsExecute();
			OPR026.close("OPR026");

            
            /** OPR355 - Goods Acceptance 2**/
            
			customfunctions.searchScreen("OPR335", "Goods Acceptance");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			String uldNo=OPR335.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			OPR335.uldShipmentDetails("Pieces","Weight", "Location", "UldNum","");
            OPR335.addULDDetails();
            OPR335.allPartsRecieved();
            OPR335.saveAcceptance();
            customfunctions.closeTab("OPR335", "Goods Acceptance");
            
            
            /** Create the message FFM ULD BultUp **/
            
			customfunctions.createTextMessage("MessageExcelAndSheet1", "MessageParam1");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FFM_1LOOSE_1ULD");
                  
           /** Process the message **/
          
            MSG005.enterMsgType("FFM");
            MSG005.clickList();
            libr.waitForSync(6);      
            map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
                              +" - 1600 - "+customfunctions.data("Origin")+" - "+customfunctions.data("Destination"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickprocess();
            libr.waitForSync(5);
	        MSG005.verifyMessageTriggered("pmkey", "FFM");    
            customfunctions.closeTab("MSG005", "List Message");
            
			 
            /**Export Manifest**/
           
			
            customfunctions.searchScreen("OPR344", "Export manifest");
            OPR344.listFlight("prop~flight_code", "FlightNo","flightStartdate");
            OPR344.manifestDetails();
            OPR344.finalizeFlight();
            OPR344.verifyFlightStatus("val~Finalized");
            OPR344.closeTab("OPR344", "Export manifest");

			
			/**Switch role to Destination**/
			customfunctions.switchRole("Destination", "Origin", "RoleGroup");
			
			/**Mark Flight Movement**/
            customfunctions.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("prop~flightNo", "flightStartdate");
			FLT006.clickFlightMovementArrivalDetailsLink();
			FLT006.clickFlightMovementDepartureDetailsLink();
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
            
			/** Import shipment Listing : Checking Delivery Status as Pending Arrival Before Breakdown**/
           
			customfunctions.searchScreen("OPR043", "Import Shipment Listing");
			OPR043.listAWB(customfunctions.data("prop~CarrierNumericCode"),customfunctions.data("AWBNo2"),customfunctions.data("Destination"));
			OPR043.VerifyShipmentStatus("Pending Arrival","AWBNo2");
			OPR043.closeTab("OPR043", "Import Shipment Listing");
			
			/**Breakdown Screen**/
			customfunctions.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("prop~flight_code","prop~flightNo", "flightStartdate");
            OPR367.maximizeAllDetails();
            String pmKey=customfunctions.data("UldNum");
            OPR367.clickCheckBox_ULD(pmKey);
            OPR367.clickBreakDownandBreakdownComplete("Location","Pieces", "Weight");
            OPR367.closeFromOPR004();
            OPR367.closeTab("OPR367", "Import Manifest");
            
            
            /** Import shipment Listing : Checking Delivery Status as Pending Delivery After Breakdown**/
            
			customfunctions.searchScreen("OPR043", "Import Shipment Listing");
			OPR043.listAWB(customfunctions.data("prop~CarrierNumericCode"), customfunctions.data("prop~AWBNo2"), customfunctions.data("Destination"));
			OPR043.VerifyShipmentStatus("Pending Delivery", "AWBNo2");
			OPR043.closeTab("OPR043", "Import Shipment Listing");
            
            
            /**Delivery Documentaion Screen OPR293**/
            
			customfunctions.searchScreen("OPR293", "Delivery Documentation");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
			OPR293.selectAllAWBs();
			OPR293.enterCaptureHandOverDetails();
			OPR293.enterCustomer("AgentCode");
			OPR293.generateDeliveryID3();
			customfunctions.closeTab("OPR293", "Delivery Documentation");
			
			
			/**Deliver Cargo**/
			
			/**********OPR293-Deliver Cargo***************/
			customfunctions.searchScreen("OPR064", "Deliver Cargo");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Deliver Cargo");
			OPR064.enterDeliveredTo(customfunctions.data("val~Test"));
			OPR064.clickSave();
			customfunctions.closeTab("OPR064", "Deliver Cargo");

			/** Import shipment Listing : Checking Delivery Status as Delivered After Delivery**/
            
			customfunctions.searchScreen("OPR043", "Import Shipment Listing");
			OPR043.listAWB(customfunctions.data("prop~CarrierNumericCode"), customfunctions.data("AWBNo2"), customfunctions.data("Destination"));
			OPR043.VerifyShipmentStatus("Delivered", "AWBNo2");
			OPR043.closeTab("OPR043", "Import Shipment Listing");
            
			
			
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
	
}

