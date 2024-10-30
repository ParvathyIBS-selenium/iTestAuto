package androidhht;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import postconditions.CancelFlights;
import screens.BreakDownScreen_OPR004;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.ChecksheetHHT;
import screens.DamageCaptureHHT;
import screens.DeliverCargo_OPR064;
import screens.DeliverNoteEnquiry_OPR034;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.FlightLoadPlan_OPR015;
import screens.FoundShipmentHHT;
import screens.FoundShipment_OPR037;
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

public class HHT261 extends BaseSetup {
	
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
	public FoundShipmentHHT foundShipmentHHT;
	public SecurityAndScreeningHHT sechht;
	public OffloadEnquiry_OPR011 off;
	public ChecksheetHHT checkhht;
	public FlightLoadPlan_OPR015 OPR015;
	public BuildupPlanning_ADD004 ADD004;
	public FoundShipment_OPR037 OPR037;
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
		foundShipmentHHT = new FoundShipmentHHT(driver, excelreadwrite, xls_Read);
		sechht=new SecurityAndScreeningHHT(driver, excelreadwrite, xls_Read);
		checkhht=new ChecksheetHHT(driver, excelreadwrite, xls_Read);
		OPR037 = new FoundShipment_OPR037(driver, excelreadwrite, xls_Read);
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
			String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			String flightCode=WebFunctions.getPropertyValue(proppath,"flight_code");
			cust.setPropertyValue("flightNumber",flightCode+flightNo,proppath); 
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
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum);
			
			
			/*******FLT005 - Flight Creation*******/
	         cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
	         FLT005.listNewFlight("FlightNo", startDate, endDate);
	         // Entering flight schedule data
	         FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
	         FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
	         FLT005.legCapacityOkButton();
	         FLT005.save();
	         cust.closeTab("FLT005", "Maintain Schedule");
			
			
			
			/******MSG005-loading FBL****/

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);


			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);


			//Create the message FBL
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
			cust.closeTab("MSG005", "List Message");

			/***MESSAGE - loading FWB**/
			cust.createTextMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			//Load FWB message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FWB_AWB1");
			cust.closeTab("MSG005", "List Message");
			
			/**** OPR339 - Security & Screening****/
			 // Switch Role
			cust.switchRole("Origin", "Destination", "RoleGroup");
			//Security and Screening
	        cust.searchScreen("OPR339", "Security and Screening");
	        OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
	        OPR339.clickYesButton();
	        OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
	        OPR339.saveSecurityDetails();
	        cust.closeTab("OPR339", "Security & Sceening");
			
            /***** OPR026 - Execute AWB****/
			
			//Execute AWB
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode"); 
		    OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			
			/****OPR355 - Goods Acceptance****/
			
            //Goods acceptance
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");  
            OPR335.looseShipmentDetails("Location", "Pieces","Weight");
            OPR335.addLooseShipment();
            OPR335.allPartsRecieved();
            OPR335.saveAcceptance();
            cust.closeTab("OPR335", "Goods Acceptance");
            
                    
            /*****OPR344 - Export manifest****/
            
            //Load the shipment in flight from lying list and click on Build up complete
            cust.searchScreen("OPR344", "Export manifest");
            OPR344.listFlight("prop~flight_code", "prop~flightNo","StartDate");
            String uldNum=OPR344.create_uld_number("UldType", "carrierCode");
            map.put("UldNum", uldNum);
            excelRead.writeDataInExcel(map, path1, sheetName, testName);
            /*OPR344.addNewULD("UldNum", "0");
            OPR344.assignLyingList("prop~AWBNo", "UldNum");*/
            OPR344.addNewULDWithAWB("UldNum","0","prop~CarrierNumericCode","prop~AWBNo","Pieces","Weight");
            OPR344.manifestDetails();
            OPR344.finalizeFlight();
            cust.closeTab("OPR344", "Export Manifest");
            
		
			//QUIt browser
			libr.quitBrowser();
				

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);
			
			/*** HHT - ACCEPTANCE****/

			foundShipmentHHT.invokeFoundShipmentScreen();
			foundShipmentHHT.clickCreateFSID();
			foundShipmentHHT.enterLocationPiecesWeight("Location", "Pieces", "Weight");
			foundShipmentHHT.enterAdditionalMarkingRemarks("Remarks", "Remarks");
			foundShipmentHHT.clickAddDetails();
			foundShipmentHHT.enterULDNumber("UldNum");
			foundShipmentHHT.enterFlightDetails("prop~flight_code", "prop~flightNo", "currentDay");
			foundShipmentHHT.enterOriginDestination("Origin", "Destination");
			foundShipmentHHT.clickAdd();
			foundShipmentHHT.clickSave();
			String Result=foundShipmentHHT.verifyTextAfterSave("Found Shipment");
			//Create an array to get last value from string that is FS ID
			String[] ResultArr = Result.split(" ");
			String FSID = ResultArr[ResultArr.length-1];
			map.put("FSID", FSID);
			libr.quitApp();
			
			/***** LOGIN TO ICARGO*****/
			//Relaunch browser
			
            driver=libr.relaunchBrowser("chrome");
			
            driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);
			
			/*******OPR037 - Found Shipment***/
			cust.searchScreen("OPR037", "Found Shipment");
			OPR037.listByFSID("FSID");
			OPR037.clickCheckAllLocationDetails();
			OPR037.clickModifyButton();
			OPR037.enterPiecesAndWeight("val~20", "val~200");
			int[] verfCols={5,6};
			String[] actVerfValues={"20","200"};
			map.put("pmkey", cust.data("Destination")+"WHS");
			OPR037.verifyLocationDetailsWithPmKey(verfCols, actVerfValues, "pmkey");
			cust.closeTab("OPR037", "Found Shipment");
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
		}

	}
}

