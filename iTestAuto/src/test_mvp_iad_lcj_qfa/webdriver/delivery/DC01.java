package delivery;

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

public class DC01 extends BaseSetup {
	
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
	public ImportManifest_OPR367 OPR367;
	public DeliverNoteEnquiry_OPR034 OPR034;
	public MarkFlightMovements_FLT006 FLT006;
	public DeliveryDocumentation_OPR293 OPR293;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="delivery";	
	
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
		customfunctions.setPropertyValue("module", sheetName, globalVarPath);
		customfunctions.setPropertyValue("showStopper", "false", globalVarPath);
		customfunctions.setPropertyValue("isTcFailed", "false", globalVarPath);
		customfunctions.setPropertyValue("executionType", "Regression", globalVarPath);
		OPR034= new DeliverNoteEnquiry_OPR034(driver, excelreadwrite, xls_Read);
		
		
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
			
			/*// Switch Role
			customfunctions.switchRole("Origin", "Origin", "RoleGroup");
			*/
			 /**** FLT003 - Create flight****/
			
			   // customfunctions.createFlight("FlightNo");
			    String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			    String flightdate1 = customfunctions.createDateFormat("dd-MMM-yyyy", 0, "DAY", "FlightDate");
			    map.put("FlightNo", flightNo);
			    map.put("StartDate", flightdate1);
	            customfunctions.setPropertyValue("FlightNo",flightNo,proppath); 
	            customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
	            excelRead.writeDataInExcel(map, path1, sheetName, testName);
	            libr.waitForSync(1);
	          /* customfunctions.searchScreen("FLT003","Maintain Operational Flight");
	            FLT003.listNewFlight("prop~flightNo", "StartDate","FlightNo");
	            FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
	            FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
	            FLT003.save("FLT003");
	            customfunctions.closeTab("FLT003", "Maintain Operational Flight");
            */
            /*********MSG005-loading FBL*********//*
            
				String flightdate = customfunctions.createDateFormat("dd-MMM-yyyy", 0, "DAY", "FlightDate");
	            String FBLDate = customfunctions.createDateFormat("ddMMM", 0, "DAY", "FBLDate");
	            map.put("StartDate", flightdate);
	            map.put("FBLDate", FBLDate);
	            map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
	            map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
	            excelRead.writeDataInExcel(map, path1, sheetName, testName);
	            libr.waitForSync(1);
			
	         //Checking AWB is fresh or Not
	           customfunctions.searchScreen("OPR026","Capture AWB");
		       OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
		       libr.waitForSync(1);*/
		       
		       String awbNo = customfunctions.data("prop~AWBNo");
		       map.put("AWBNo",awbNo);
		       excelRead.writeDataInExcel(map, path1, sheetName, testName);
		       
		       //Writing the full AWB No to property file
	           customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
	                
	         /*  //Create the message FBL
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
      

            *//**** OPR339 - Security & Screening****//*
            
	            customfunctions.searchScreen("OPR339", "Security and Screening");
	            OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
	            OPR339.clickYesButton();
	            OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
	            OPR339.saveSecurityDetails();
	            customfunctions.closeTab("OPR339", "Security & Sceening");

            
            *//**** OPR026 - Capture AWB****//*
				customfunctions.searchScreen("OPR026","Capture AWB");
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
				OPR026.saveAWB();   
				OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
				OPR026.asIsExecute();
				customfunctions.closeTab("OPR026", "Capture AWB");*/

		/****OPR355 - Goods Acceptance****/
                
                //Goods acceptance
				customfunctions.searchScreen("OPR335", "Goods Acceptance");
				customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");  
                OPR335.looseShipmentDetails("Location", "Pieces","Weight");
                OPR335.addLooseShipment();
                OPR335.allPartsRecieved();
                OPR335.saveAcceptance();
                customfunctions.closeTab("OPR335", "Goods Acceptance");
                
                /*****OPR344 - Export manifest****/
                
               //Load the shipment in flight from lying list
                customfunctions.searchScreen("OPR344", "Export manifest");
                	OPR344.listFlight("prop~flight_code", "FlightNo","StartDate");
                String uldNum=OPR344.create_uld_number("UldType", "carrierCode");
                map.put("UldNum", uldNum);
                excelRead.writeDataInExcel(map, path1, sheetName, testName);
                OPR344.addNewULDWithAWB("UldNum","0","prop~CarrierNumericCode","prop~AWBNo","Pieces","Weight");
                OPR344.manifestDetails();
                OPR344.finalizeFlight();
                OPR344.verifyFlightStatus("val~Finalized");
                customfunctions.closeTab("OPR344", "Export Manifest");
                
                /****FLT006 - Mark Flight Movements*****/
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
	            OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
	            OPR367.maximizeAllDetails();
	            String pmkey = Excel.getCellValue(path1,sheetName, "DC01", "UldNum");
	            OPR367.clickCheckBox_ULD(pmkey);
	            OPR367.clickBreakDownandBreakdownComplete("Location", "Pieces","Weight");
	            OPR367.closeTab("OPR367", "Import Manifest");
	            
	          /*  *//*******Verify FSU-RCF message in MSG005******//*
			
	            customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
	            MSG005.enterMsgType("FSU");
	            MSG005.clickReference();
	            MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
	            MSG005.selectMsgSubType("Breakdown");
	            MSG005.selectStatus("Sent");
	            MSG005.clickList();
	            MSG005.verifyMessageTriggered("AWBNo", "FSU-RCF");
	            libr.waitForSync(6); 
	            MSG005.closeTab("MSG005", "MSG005 - List Messages");*/
		
				/**********OPR293-Delivery Documentation**********/
								
				//Capture handover details and generate delivery id
				customfunctions.searchScreen("OPR293", "Delivery Documentation");
				customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
				OPR293.selectAllAWBs();
				OPR293.enterCaptureHandOverDetails();
				OPR293.enterCustomer("CustomerName");
				OPR293.generateDeliveryID3();
				customfunctions.closeTab("OPR293", "Delivery Documentation");
			
				/**********OPR034-Deliver Note Enquiry**********/
				
				//Verify DN Status
				customfunctions.searchScreen("OPR034", "Deliver Note Enquiry");
				OPR034.listByAWB("CarrierNumericCode", "AWBNo");
				OPR034.verifyDNStatus("Issued");
				customfunctions.closeTab("OPR034", "Deliver Note Enquiry");
			
				
				
				/**********OPR293-Deliver Cargo*********************/
				customfunctions.searchScreen("OPR064", "Deliver Cargo");
				customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Deliver Cargo");
				OPR064.enterDeliveredTo("Test");
				OPR064.clickSave();
				customfunctions.closeTab("OPR064", "Deliver Cargo");
				
				 /*******Verify FSU-AWD message in MSG005******/
			/*	
	            customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
	            MSG005.enterMsgType("FSU");
	            MSG005.clickReference();
	            MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
	            MSG005.selectMsgSubType("AWB Document Delivered");	           
	            MSG005.selectStatus("Sent");
	            MSG005.clickList();
	            MSG005.verifyMessageTriggered("AWBNo", "FSU-AWD");
	            libr.waitForSync(6); 
	            MSG005.closeTab("MSG005", "MSG005 - List Messages");*/
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

