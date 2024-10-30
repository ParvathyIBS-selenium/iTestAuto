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

public class GA02 extends BaseSetup {
	
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
	public DeliveryDocumentation_OPR293 OPR293;
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
		SHR048 = new MaintainAndListSystemParameters_SHR048(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) throws InterruptedException, IOException {
		
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
			
			/*******Pre-Condition***********//*
			customfunctions.searchScreen("SHR048","Maintain And List System Parameters");
			SHR048.listParameter("operations.shipment.aprcheckrequiredforacceptancefinalisation");
			String paramVal=SHR048.getParameterValue();
			map.put("paramVal", paramVal);
			SHR048.enterParametrValue("Y");
			SHR048.saveDetails();
			customfunctions.closeTab("SHR048", "Maintain And List System Parameters");*/
			
			
			/**** FLT003 - Create flight****/
			
			   customfunctions.createFlight("FlightNo");
			    String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			    String flightdate1 = customfunctions.createDateFormat("dd-MMM-yyyy", 0, "DAY", "FlightDate");
			    map.put("FlightNo", flightNo);
			    map.put("StartDate", flightdate1);
	            customfunctions.setPropertyValue("FlightNo",flightNo,proppath); 
	            customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
	            excelRead.writeDataInExcel(map, path1, sheetName, testName);
	            libr.waitForSync(1);
	            customfunctions.searchScreen("FLT003","Maintain Operational Flight");
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
	            excelRead.writeDataInExcel(map, path1, sheetName, testName);
	            libr.waitForSync(1);
			
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
      
	
            /**** OPR339 - Security & Screening****/
            
	            customfunctions.searchScreen("OPR339", "Security and Screening");
	            OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
	            OPR339.clickYesButton();
	            OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
	            OPR339.saveSecurityDetails();
	            customfunctions.closeTab("OPR339", "Security & Sceening");

            
           /**** OPR026 - Capture AWB****/
				customfunctions.searchScreen("OPR026","Capture AWB");
				OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
				OPR026.updateOrigin("Origin");
				OPR026.updateDestination("Destination");
				OPR026.enterRouting("Destination","prop~flight_code");       
				OPR026.selectSCI("SCI");
				OPR026.enterSCC("DGR");
				OPR026.enterAgentCode("AgentCode");    
				OPR026.provideShipperCode("shipperCode");
				OPR026.provideConsigneeCode("consigneeCode");
				OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
				OPR026.clickChargesAcc();
				OPR026.	provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
				OPR026.saveAWB();
				OPR026.clickOkButton();
				OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
				//Capture DGR details
				OPR026.enterSCC("DGR");
				OPR026.clickDGRGoods();
				OPR026.captureDGRDetails("UNID", "ProperShippingName", "Pieces", "Pieces", "PerPkgUnit","PI",false);
				OPR026.asIsExecute();
				customfunctions.closeTab("OPR026", "Capture AWB");

				/****OPR355 - Goods Acceptance****/
                
                //Loose acceptance
				customfunctions.searchScreen("OPR335", "Goods Acceptance");
				customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");  
                //Accept Partial pieces for Location 1
				OPR335.looseShipmentDetails("Location", "Pieces2","Weight2");
                OPR335.addLooseShipment();
                OPR335.clickSave();
                OPR335.verifyAcceptanceFinalized("not finalised",false);
                OPR335.verificationOfNotRFCStatus();
                customfunctions.closeTab("OPR335", "Goods Acceptance");
                //Accept Remaining pieces for Location 2
				customfunctions.searchScreen("OPR335", "Goods Acceptance");
				customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance"); 
				//Capture check sheet
               OPR335.captureChecksheet();
                OPR335.looseAcceptanceDetails("Location2", "Pieces2","Weight2");
                OPR335.addLooseShipment();
                OPR335.allPartsRecieved();              
                OPR335.saveAcceptance();
                customfunctions.closeTab("OPR335", "Goods Acceptance");
     
               /*******Verify FSU-FOH message in MSG005******/
				
	            customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
	            
	            MSG005.enterMsgType("FSU");
	            MSG005.selectMsgSubType("Freight On Hand");
	            MSG005.clickReference();
	            MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
	            MSG005.selectStatus("Sent");
	            MSG005.clickList();
	            MSG005.verifyMessageTriggered("AWBNo", "FSU");
	            libr.waitForSync(6); 
	            MSG005.closeTab("MSG005", "MSG005 - List Messages");
                
                /*******Verify FSU-RCS message in MSG005******/
				
	            customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
	            
	            MSG005.enterMsgType("FSU");
	            MSG005.selectMsgSubType("Acceptance");
	            MSG005.clickReference();
	            MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
	            MSG005.selectStatus("Sent");
	            MSG005.clickList();
	            MSG005.verifyMessageTriggered("AWBNo", "FSU");
	            libr.waitForSync(6); 
	            MSG005.closeTab("MSG005", "MSG005 - List Messages");
	            
	           /*******WHS011 - Warehouse Shipment Enquiry***********/
				//Verify warehouse location, pieces and weight
	            customfunctions.searchScreen("WHS011", "WHS011 - Warehouse Shipment Enquiry");
	            WHS011.enterAWBdetails();
	            WHS011.clickList();
	            int[] col = {4,9,10};
	            String[] values={customfunctions.data("Location"),customfunctions.data("Pieces2"),customfunctions.data("Weight2")};
	            String[] values1={customfunctions.data("Location2"),customfunctions.data("Pieces2"),customfunctions.data("Weight2")};
	            WHS011.verifyWarehouseDetailsWithPmKey(col, values,"prop~AWBNo");
	            WHS011.verifyWarehouseDetailsWithPmKey(col, values1,"prop~AWBNo");
	            customfunctions.closeTab("WHS011", "Warehouse Enquiry");
	            
	        
		}	
		catch(Exception e)
		{
			//libr.writeExtent("Fail", "Test case has failed steps");
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		
		finally
		{
			/*customfunctions.closeTab();
			  *//*******Post-Condition***********//*
			customfunctions.searchScreen("SHR048","Maintain And List System Parameters");
			SHR048.listParameter("operations.shipment.aprcheckrequiredforacceptancefinalisation");
			SHR048.enterParametrValue(customfunctions.data("paramVal"));
			SHR048.saveDetails();
			customfunctions.closeTab("SHR048", "Maintain And List System Parameters");*/
		
		}

	}
}
