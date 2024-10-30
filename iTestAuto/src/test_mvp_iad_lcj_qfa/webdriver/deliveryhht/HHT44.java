package deliveryhht;

import java.util.Map;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.DeliveryDocumentation_OPR293;
import screens.DeliveryHHT;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreening_OPR339;
import screens.AWBClearance_OPR023;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class HHT44 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005; 
	public ExportManifest_OPR344 OPR344;
	public GoodsAcceptance_OPR335 OPR335;
	public ImportManifest_OPR367 OPR367;
	public DeliveryDocumentation_OPR293 OPR293;
	public MaintainOperationalFlight_FLT003 FLT003;
	public MarkFlightMovements_FLT006 FLT006;
	public SecurityAndScreening_OPR339 OPR339;
	public BreakDownScreen_OPR004 OPR004;
	public AWBClearance_OPR023 OPR023;
	public DeliveryHHT deliveryhht;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="deliveryhht";	
	
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
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		FLT006=new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR004= new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		deliveryhht=new DeliveryHHT(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "HHT08")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "HHT08")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			 
			// Login to "ICARGO"
			String[] iCargo = libr.getApplicationParams("iCargo");
			driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);
			
			 
			// creating flight number

			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = cust.getPropertyValue(proppath, "flightNumber");
			String flightStartdate = cust.createDateFormat("dd-MMM-yyyy", 0, "DAY", "");
			map.put("flightStartdate", flightStartdate);
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
				

			 /** Creating Flight of Schedule type & Flight type : Own & Combination **/
						 
			cust.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flight_code","prop~flightNo","flightStartdate","FullFlightNumber");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("departureTime","arrivalTime","aircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");
						
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);


			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
						

			/**** OPR026 - Capture AWB****/
			//Capture AWB details
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
			OPR026.saveAWB();   
			cust.closeTab("OPR026", "Capture AWB");
						
						
			/******OPR339 - Security and Screening****/
			//Capture security and screening details
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
			OPR339.saveSecurityDetail();
			cust.closeTab("OPR339", "Security & Sceening");
			            
			
			/********OPR026 - Capture AWB**********/
			//Execute the AWB
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			 
			//Switch role to Origin station
			cust.switchRole("Origin", "Destination", "val~ADMIN");
			
			/****OPR355 - Goods Acceptance****/
			
			String uldNo=OPR335.create_uld_number("UldType", "prop~flight_code");
			System.out.println(uldNo);
            map.put("UldNum", uldNo);
            excelRead.writeDataInExcel(map, path1, sheetName, testName);
            //uld acceptance
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo","prop~CarrierNumericCode","Goods Acceptance");
			libr.waitForSync(6);
			System.out.println(cust.data("UldNum"));
			OPR335.uldShipmentDetails("Pieces", "Weight","Location","UldNum", "");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			//Release block for AWB
			cust.searchScreen("OPR023", "AWB Clearance");
			libr.waitForSync(4);
			OPR023.listAWB("prop~CarrierNumericCode", "prop~AWBNo");
			OPR023.releaseBlock("val~Block Released");
			cust.closeTab("OPR023", "AWB Clearance");
			
			/**** OPR344 - Export manifest****/
			//Perform Build up
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo","flightStartdate");
			OPR344.addNewULD("UldNum","0");
			cust.closeTab("OPR344", "Export manifest");

			/***********FFM Loading**************/
			//Switch role
			cust.switchRole("Destination", "Destination", "val~ADMIN");
			
            //Create the message FFM
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
				
			
			/****FLT006 - Mark Flight Movements*****/
			//Capture ATA and ATD
            cust.searchScreen("FLT006", "Mark Flight Movements");
            FLT006.listFlight("prop~flightNo", "StartDate");
            FLT006.clickFlightMovementArrivalDetailsLink();
            FLT006.clickFlightMovementDepartureDetailsLink();
            FLT006.clickSave();
            FLT006.close("FLT006");
		 
			
            /** Import Manifest **/
            //Perform intact breakdown
            cust.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
            OPR367.maximizeAllDetails();
            String pmkey = Excel.getCellValue(path1,sheetName, "HHT44", "UldNum");
            OPR367.clickCheckBox_ULD(pmkey);
            OPR367.clickBreakDownandBreakdownComplete("Location2", "Pieces","Weight");
            OPR367.SaveDetailsInOPR004();
            OPR367.clickYesButton();
            OPR367.closeTab("OPR367", "Import Manifest");
	        
	     
	     	/**********OPR293-Delivery Documentation**********/
				
			//Capture handover details and generate delivery id
			cust.searchScreen("OPR293", "Delivery Documentation");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
			OPR293.selectAllAWBs();
			OPR293.enterCaptureHandOverDetails();
			OPR293.enterCustomer("CreditCustomer");
			OPR293.generateDeliveryID3();
			cust.closeTab("OPR293", "Delivery Documentation");
			
			//Check the DN status
			cust.searchScreen("OPR293", "Delivery Documentation");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
			OPR293.verifyDNStatus("Paid");
			cust.closeTab("OPR293", "Delivery Documentation");
			
			
			
			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");

			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);
			
			/** Delivery HHT**/
			//Perform delivery in HHT by listing with ULD Number
			deliveryhht.invokeDeliveryHHTScreen();
			deliveryhht.enterAWBULDNum("UldNum");
			deliveryhht.clickNext();
			deliveryhht.clickPendingButton();
			deliveryhht.clickNext();
			deliveryhht.enterDeliverRemarks("val~Delivered");
			deliveryhht.enterCustomsReferenceNumber("customRefNo");
			deliveryhht.clickNext();
			deliveryhht.deliveryStatusVerify("val~DELIVERED");
			deliveryhht.clickDeliveryComplete();
			deliveryhht.enterDeliveredTo("consigneeCode");
			deliveryhht.enterVehicleInfo("VehicleInfo");
			deliveryhht.enterContactNumber("ContactNumber");
			deliveryhht.clickNext();
			deliveryhht.enterRemarks("val~Delivery complete");
			deliveryhht.clickPrintPOD();
			
				
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
		}

	}
}


