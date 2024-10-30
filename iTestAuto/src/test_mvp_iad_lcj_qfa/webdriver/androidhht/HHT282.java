package androidhht;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AWBDeconsolidationHHT;
import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
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

public class HHT282 extends BaseSetup {
	
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
	public SecurityAndScreening_OPR339 OPR339;
	public AWBDeconsolidationHHT awbdeconsol;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public WarehouseShipmentEnquiry_WHS011 WHS011;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
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
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		
		awbdeconsol=new AWBDeconsolidationHHT(driver, excelreadwrite, xls_Read);
		OPR339= new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT006=new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367=new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		WHS011=new WarehouseShipmentEnquiry_WHS011(driver, excelreadwrite, xls_Read);
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
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			System.out.println(FlightNum);

		/**** SWITCH ROLE*****/
			cust.switchRole("Origin", "Destination", "prop~defRoleGroup");

		/***MESSAGE - loading ASM**/
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			//Load ASM message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "ASM_NEW");
			cust.closeTab("MSG005", "List Message");


			/******MSG005-loading FBL***/

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
			
			
			/***MESSAGE - loading FHL**/
			cust.createTextMessage("MessageExcelAndSheetFHL", "MessageParamFHL");
			//Load FWB message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FHL_2HAWBs");
			cust.closeTab("MSG005", "List Message");
			
/**** OPR339 - Security & Screening****/
			
		cust.searchScreen("OPR339", "OPR339 - Security & Sceening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");
			
		/***** OPR026 - Execute AWB****/
			
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateShipperZipCode("val~12345");
				
			OPR026.updateConsigneeZipCode("val~12345");
			OPR026.clickHAWBDocFinalized();

			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			
/** OPR355 - Goods Acceptance **/

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
		    OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
            OPR335.saveAcceptance();
            cust.closeTab("OPR335", "Goods Acceptance");
            
			
			
			/***** FFM Processing***/
            String uldNo=OPR335.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			cust.createTextMessage("MessageExcelAndSheetFFM", "MessageParamFFM");
			//Load FFM message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FFM_1ULD1SHIPMENTS");
	        
			//Process the message
			
			MSG005.enterMsgType("FFM");
			MSG005.clickList();
			libr.waitForSync(6);

			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin")+" - "+cust.data("Destination"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
			/*** FINALIZE FLIGHT***/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "prop~flightNo","StartDate");
			OPR344.clickBuildUpComplete();
			OPR344.manifestDetails();
			OPR344.finalizeFlight();
			cust.closeTab("OPR344", "Export manifest");
			
			
			/**Switch role to Destination**/
			cust.switchRole("Destination", "Destination", "val~ADMIN");
			
		/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("prop~flightNo", "StartDate");
			FLT006.clickFlightMovementArrivalDetailsLink();
			FLT006.clickFlightMovementDepartureDetailsLink();
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
           
			
			/** Import Manifest **/
            
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
			OPR367.clickCheckBox_ULD(cust.data("UldNum"));
			OPR367.enterBreakdownDetails("Location2","Pieces","Weight");
			OPR367.SaveDetailsInOPR004();
			OPR367.enterDetailsInViolations("val~Size of goods","val~Breakdown done");
			OPR367.clickYesButton();
			OPR367.clickBreakdownComplete();

			OPR367.closeTab("OPR367", "Import Manifest");


			//QUIt browser
			libr.quitBrowser();
			

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);
			
			/**HHT-AWB DECONSOLIDATION**/
          awbdeconsol.invokeAWBDeconsolidationHHTScreen();
          map.put("awbNumber", cust.data("prop~stationCode")+cust.data("prop~AWBNo"));
          //SU
          map.put("SU", cust.data("prop~stationCode")+cust.data("prop~AWBNo")+"001");
          awbdeconsol.enterAWBNumber("awbNumber");
          /***** 1st HAWB***/
          String pcs="1";
          map.put("pcs", pcs);
          awbdeconsol.enterLocation("Location2");
          awbdeconsol.enterHAWB("HAWB");
          awbdeconsol.enterSU("SU");
          
          awbdeconsol.enterPieces("Pieces2");
          awbdeconsol.saveDetails();
         /****2nd HAWB***/
          awbdeconsol.enterLocation("Location2");
          awbdeconsol.enterHAWB("HAWB2");
          awbdeconsol.enterSU("SU");
         
          awbdeconsol.enterPieces("Pieces2");
          awbdeconsol.saveDetails();
          
          libr.quitApp();
		
			/**** BUILD UP COMPLETE****/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			
			cust.loginHHT(hht[0], hht[1]);
			awbdeconsol.invokeAWBDeconsolidationHHTScreen();
			awbdeconsol.enterAWBNumber("awbNumber");
			awbdeconsol.clickMoreOptions();
			awbdeconsol.clickDeconsolidationComplete();
			awbdeconsol.enterAWBNumber("awbNumber");
			awbdeconsol.verifyAvailablePcs("Pieces");
			libr.quitApp();
			
			/***** LOGIN TO ICARGO*****/
			//Relaunch browser
			
            driver=libr.relaunchBrowser("chrome");
			
            driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);
			
			 /*******WHS011 - Warehouse Shipment Enquiry***********/
			//Verify warehouse location, pieces and weight
			cust.searchScreen("WHS011", "WHS011 - Warehouse Shipment Enquiry");
            WHS011.enterAWBdetails();
            WHS011.clickList();
            int[] col = {4,5,9,10};
          
            String[] values={cust.data("Location2"),cust.data("SU"),cust.data("Pieces2"),cust.data("Weight2")};
           /****** HAWB*****/
            WHS011.verifyWarehouseDetailsWithPmKey(col, values,"HAWB");
            
            /*******HAWB2*****/
            WHS011.verifyWarehouseDetailsWithPmKey(col, values,"HAWB2");
            cust.closeTab("WHS011", "Warehouse Enquiry");
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
		}

	}
}


