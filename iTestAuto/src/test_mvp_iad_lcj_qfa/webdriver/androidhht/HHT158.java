package androidhht;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import postconditions.CancelFlights;
import screens.BreakDownScreen_OPR004;
import screens.BreakdownHHT;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.ChecksheetHHT;
import screens.DamageCaptureHHT;
import screens.DeliverCargo_OPR064;
import screens.DeliverNoteEnquiry_OPR034;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.FlightLoadPlan_OPR015;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListCheckSheetConfig_SHR094;
import screens.ListIrregularity_OPR341;
import screens.ListMessages_MSG005;
import screens.ListTemplates_SHR093;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.OffloadEnquiry_OPR011;
import screens.OffloadHHT;
import screens.SecurityAndScreeningHHT;
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

public class HHT158 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public MaintainFlightSchedule_FLT005 FLT005;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005; 
	public GoodsAcceptance_OPR335 OPR335;
	public DamageCaptureHHT dchht;
	public ListIrregularity_OPR341 OPR341;
	public ExportManifest_OPR344 OPR344;
	public MarkFlightMovements_FLT006 FLT006;
	public SecurityAndScreening_OPR339 OPR339;
	public ImportManifest_OPR367 OPR367;
	public ListCheckSheetConfig_SHR094 SHR094;
	public ListTemplates_SHR093 SHR093;
	public BreakDownScreen_OPR004 OPR004;
	public DeliveryDocumentation_OPR293 OPR293;
	public BreakdownHHT bdhht;
	public SecurityAndScreeningHHT sechht;
	public OffloadEnquiry_OPR011 off;
	public FlightLoadPlan_OPR015 OPR015;
	public BuildupPlanning_ADD004 ADD004;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="androidhht";	
	
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
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR341=new ListIrregularity_OPR341(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		SHR094=new ListCheckSheetConfig_SHR094(driver, excelreadwrite, xls_Read);
		SHR093=new ListTemplates_SHR093(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004=new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR293=new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		bdhht = new BreakdownHHT(driver, excelreadwrite, xls_Read);
		sechht=new SecurityAndScreeningHHT(driver, excelreadwrite, xls_Read);
		off = new OffloadEnquiry_OPR011(driver, excelreadwrite, xls_Read);
		OPR015 = new FlightLoadPlan_OPR015(driver, excelreadwrite, xls_Read);
		ADD004 = new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "HHT49")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "HHT49")
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
			cust.createFlight("FlightNo");
			cust.createFlight("FlightNo2");
			String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			String flightCode=WebFunctions.getPropertyValue(proppath,"flight_code");
			cust.setPropertyValue("flightNumber",flightCode+flightNo,proppath);
			String flightNo2 = WebFunctions.getPropertyValue(proppath,"flightNo2");
			cust.setPropertyValue("flightNumber2",flightCode+flightNo2,proppath); 
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = cust.getPropertyValue(proppath, "flightNumber");
			String FlightNum2 = cust.getPropertyValue(proppath, "flightNumber2");
			map.put("FullFlightNo", FlightNum);
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("FlightNo2", FlightNum2.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum);
			System.out.println(FlightNum2);
			
			/*******FLT005 - Flight Creation*******/
			 //Create Flight F1
	         cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
	         FLT005.listNewFlight("FlightNo", startDate, endDate);
	         FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
	         FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
	         FLT005.legCapacityOkButton();
	         FLT005.save();
	         cust.closeTab("FLT005", "Maintain Schedule");
			
	         //Create Flight F2
	         cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
	         FLT005.listNewFlight("FlightNo2", startDate, endDate);
	         FLT005.enterFlightDetails("Route2", "scheduleType", "FCTL", "Office", "flightType");
	         FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
	         FLT005.legCapacityOkButton();
	         FLT005.save();
	         cust.closeTab("FLT005", "Maintain Schedule");
			
			
			/******MSG005-loading FBL****/

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			
			//Writing the AWB2 in property file
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			map.put("AWBNo2", cust.data("prop~AWBNo"));
			cust.setPropertyValue("AWBNo2", cust.data("prop~AWBNo"), proppath);
			cust.setPropertyValue("FullAWBNo2", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
			
			cust.searchScreen("OPR026","Capture AWB");
			//Writing the AWB1 in property file
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			map.put("AWBNo", cust.data("prop~AWBNo"));
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
			
			//Create the message FBL for AWB1
			
			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_1");

			//Process the message
			
			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);

			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			
			//Create the message FBL for AWB2
			
			cust.createTextMessage("MessageExcelAndSheet", "MessageParamFBL2");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_1");

			//Process the message
			
			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);

			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");

	        
			/***MESSAGE - loading FWB**/
			
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			//Create and Load FWB message for AWB1
			cust.createTextMessage("MessageExcelAndSheetFWB", "MessageParamFWB");			
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FWB_AWB1");
			//Create and Load FWB message for AWB1
			cust.createTextMessage("MessageExcelAndSheetFWB", "MessageParamFWB2");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FWB_AWB1");
			
			cust.closeTab("MSG005", "List Message");
			
			/**** OPR339 - Security & Screening****/
			 // Switch Role
			cust.switchRole("Origin", "Transit", "RoleGroup");
			//Security and Screening for AWB1
	        cust.searchScreen("OPR339", "Security and Screening");
	        OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
	        OPR339.clickYesButton();
	        OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
	        OPR339.saveSecurityDetails();
	        cust.closeTab("OPR339", "Security & Sceening");
	        
	        //Security and Screening for AWB1
	        cust.searchScreen("OPR339", "Security and Screening");
	        OPR339.listAWBNo("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
	        OPR339.clickYesButton();
	        OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
	        OPR339.saveSecurityDetails();
	        cust.closeTab("OPR339", "Security & Sceening");
						
			/***** OPR026 - Execute AWB****/
			
			//Execute AWB1
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterRouting("Transit","prop~flight_code");
			OPR026.enterSecondRouting("Destination","prop~flight_code"); 
			OPR026.clickBookingDetails();
			OPR026.enterBookingDetailsSingleLeg("Origin", "Transit", "FullFlightNo", "StartDate", "Pieces", "Weight", "Volume");
			OPR026.enterBookingDetailsSecondRow("Transit", "Destination", "FullFlightNo2", "StartDate", "Pieces", "Weight", "Volume");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			//Execute AWB2
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo2", "prop~CarrierNumericCode");
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterRouting("Transit","prop~flight_code"); 
			OPR026.clickBookingDetails();
			OPR026.enterBookingDetailsSingleLeg("Origin", "Transit", "FullFlightNo", "StartDate", "Pieces", "Weight", "Volume");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			
			/****OPR355 - Goods Acceptance****/
			
            //Loose acceptance for AWB1
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");  
            OPR335.looseShipmentDetails("Location", "Pieces","Weight");
            OPR335.addLooseShipment();
            OPR335.allPartsRecieved();
            OPR335.clickSave();
            OPR335.clickYesButton();
            OPR335.clickYesButton();
            cust.closeTab("OPR335", "Goods Acceptance");
            
            //Loose acceptance for AWB2
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo2", "prop~CarrierNumericCode", "Goods Acceptance");  
            OPR335.looseShipmentDetails("Location", "Pieces","Weight");
            OPR335.addLooseShipment();
            OPR335.allPartsRecieved();
            OPR335.saveAcceptance();
            cust.closeTab("OPR335", "Goods Acceptance");
            
            /****OPR015 - Flight Load Plan****/
            //Flight load plan and build up planning for AWB1
			cust.searchScreen("OPR015","Flight Load Plan");
			String flightCode1=WebFunctions.getPropertyValue(proppath,"flight_code");
			cust.listFlight("OPR015", flightCode1, cust.data("prop~flightNo"), cust.data("StartDate"), "Generic_Elements");
            OPR015.checkAWB(cust.data("prop~AWBNo"));
            OPR015.enterPosition("Position");
            OPR015.clickAssignAWB();
            OPR015.clickYesButton();
            String uldNum=OPR344.create_uld_number("UldType", "carrierCode");
            map.put("UldNum", uldNum);
            excelRead.writeDataInExcel(map, path1, sheetName, testName);
            OPR015.clickMorePanel();
            OPR015.enterPositionDetails("UldNum","Contour","Test");
            OPR015.clickSaveButton();
            cust.closeTab("OPR015", "Flight Load Plan");
            
            /****OPR015 - Flight Load Plan****/
            //Flight load plan for AWB2
			cust.searchScreen("OPR015","Flight Load Plan");
			String flightCode2=WebFunctions.getPropertyValue(proppath,"flight_code");
			cust.listFlight("OPR015", flightCode2, cust.data("prop~flightNo"), cust.data("StartDate"), "Generic_Elements");
			OPR015.checkAWB(cust.data("prop~AWBNo2"));
            OPR015.enterPosition("Position");
            OPR015.clickAssignAWB();
            OPR015.clickYesButton();
            String uldNum2=OPR344.create_uld_number("UldType", "carrierCode");
            map.put("UldNum2", uldNum2);
            excelRead.writeDataInExcel(map, path1, sheetName, testName);
            OPR015.clickMorePanel();
            OPR015.enterPositionDetails("UldNum","Contour","Test");
            OPR015.clickSaveButton();
            cust.closeTab("OPR015", "Flight Load Plan");
            
            
            /******ADD004 - Buildup planning******/
            //Build up planning for AWB1
            cust.searchScreen("ADD004", "Buildup Planning");
            ADD004.listFlight("carrierCode","FlightNo","StartDate");
            //Select AWB1
            ADD004.selectULD("prop~AWBNo");
            ADD004.clickAllocate();
            ADD004.enterAllocateToDetails("0", "0", "Specific ULD", "UldNum", "0");
            cust.closeTab("ADD004", "Buildup Planning");
            
            
            
            //Build up planning for AWB2
            cust.searchScreen("ADD004", "Buildup Planning");
            ADD004.listFlight("carrierCode","FlightNo","StartDate");
            //Select AWB2 from lying list
            ADD004.clickLyinglist();
            ADD004.selectULD("prop~AWBNo2");
            ADD004.clickAllocate();
            ADD004.enterAllocateToDetails("0", "0", "Specific ULD", "UldNum2", "0");
            cust.closeTab("ADD004", "Buildup Planning");
          
            
            /*****OPR344 - Export manifest****/
            
            //Load the shipment in flight from lying list and click on Build up complete
            cust.searchScreen("OPR344", "Export manifest");
            OPR344.listFlight("prop~flight_code", "prop~flightNo","StartDate");
            //Add ULD1
            OPR344.addNewULDWithAWB("UldNum","0","prop~CarrierNumericCode","prop~AWBNo","Pieces","Weight");
           /* OPR344.addNewULD("UldNum", "0");
            OPR344.assignLyingList("prop~AWBNo", "UldNum");*/
            //Add ULD2
            OPR344.addNewULDWithAWB("UldNum2","0","prop~CarrierNumericCode","prop~AWBNo2","Pieces","Weight");
            /*OPR344.addNewULD("UldNum2", "0");
            OPR344.assignLyingList("prop~AWBNo2", "UldNum");*/
            //Manifest,Finalize the flight
            OPR344.manifestDetails();
            OPR344.finalizeFlight();
            cust.closeTab("OPR344", "Export Manifest");  
            
            /****FLT006 - Mark Flight Movements*****/
            // Switch Role
			cust.switchRole("Transit", "Transit", "RoleGroup");
            
            cust.searchScreen("FLT006", "Mark Flight Movements");
            FLT006.listFlight("prop~flightNo", "StartDate");
            FLT006.clickFlightMovementArrivalDetailsLink();
            FLT006.clickFlightMovementDepartureDetailsLink();
            FLT006.clickSave();
            FLT006.close("FLT006");
			
			//QUIt browser
			libr.quitBrowser();
			

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);
			
			/*** HHT - BREAKDOWN****/

			//Thru breakdown for ULD1
			bdhht.invokeBreakdownHHTScreen();
			map.put("uldnum", cust.data("UldNum"));
			bdhht.enterValue("uldnum");
			bdhht.clickUnitizedYes();
            bdhht.clickSave();
        	
            
			libr.quitApp();
			
			//ULD2 breakdown
			libr.launchApp("hht-app-release");
			cust.loginHHT(hht[0], hht[1]);
			bdhht.invokeBreakdownHHTScreen();
			map.put("uldnum2", cust.data("UldNum2"));
			map.put("awbNumber2", cust.data("prop~stationCode")+cust.data("prop~AWBNo2"));
			bdhht.enterValue("uldnum2");
            bdhht.addAWB("awbNumber2");
            bdhht.addLocation("01");
            bdhht.addPcsWgt("Pieces","Weight");
            bdhht.clickSave();
            bdhht.clickCloseAWBButton();
            
            libr.quitApp();
        	//clicking BreakdownComplete
        	libr.launchApp("hht-app-release");
			cust.loginHHT(hht[0], hht[1]);
			bdhht.invokeBreakdownHHTScreen();
			bdhht.enterValue("uldnum2");
            bdhht.clickMoreOptions();
            bdhht.clickBreakdownComplete();
            libr.quitApp();
			
			/***** LOGIN TO ICARGO*****/
			//Relaunch browser
			
            driver=libr.relaunchBrowser("chrome");
			
            driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);
			
			/*******OPR004 - Breakdown Screen***/
			//Verify Thru Breakdown(ULD1)
			cust.searchScreen("OPR004", "Breakdown");
			OPR004.enterULDnumber(cust.data("UldNum"));
			OPR004.listFlight("prop~flight_code", "prop~flightNo", "StartDate");
			OPR004.verifyThruCheckbox();
			cust.closeTab("OPR004", "Breakdown");
			
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

