package androidhht;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import postconditions.CancelFlights;
import screens.CaptureAWB_OPR026;
import screens.DamageCaptureHHT;
import screens.DeliverCargo_OPR064;
import screens.DeliverNoteEnquiry_OPR034;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListIrregularity_OPR341;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
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

public class HHT51 extends BaseSetup {
	
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
	public MaintainOperationalFlight_FLT003 FLT003;
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
		dchht = new DamageCaptureHHT(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR341=new ListIrregularity_OPR341(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
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
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			 /**** FLT003 - Create flight****/
			
			cust.createFlight("FlightNo");
		    String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
		    String flightdate1 = cust.createDateFormat("dd-MMM-yyyy", 0, "DAY", "FlightDate");
		    map.put("FlightNo", flightNo);
		    map.put("StartDate", flightdate1);
		    String FlightNum = cust.getPropertyValue(proppath, "flightNumber");
		    cust.setPropertyValue("FlightNo",flightNo,proppath); 
		    cust.setPropertyValue("flightNumber", cust.data("prop~flight_code")+cust.data("prop~flightNo"), proppath);
            excelRead.writeDataInExcel(map, path1, sheetName, testName);
            libr.waitForSync(1);
            cust.searchScreen("FLT003","Maintain Operational Flight");
            FLT003.listNewFlight("prop~flightNo", "StartDate","FlightNo");
            FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
            FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
            FLT003.save("FLT003");
            cust.closeTab("FLT003", "Maintain Operational Flight");
        
			
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
			
			/***MESSAGE - loading FHL**/
			cust.createTextMessage("MessageExcelAndSheetFHL", "MessageParamFHL");
			//Load FWB message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FHL1");
			cust.closeTab("MSG005", "List Message");
			
			/***** OPR026 - Execute AWB****/
			
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.clickHAWBDocFinalized();
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
            

			//QUIt browser
			libr.quitBrowser();

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);


			/*** HHT - Damage Capture****/

			dchht.invokeDamageCaptureScreen();
			map.put("awbNumber", cust.data("prop~stationCode")+cust.data("prop~AWBNo"));
			map.put("hawb", cust.data("HAWB"));			
			dchht.enterAwbNumber("awbNumber");
			dchht.clickOnHAWB("hawb");
			dchht.enterPiecesAndWeight("Pieces", "Weight");
			dchht.selectDamageCode("Bent");
			dchht.enterPackageCodeDamageReasonCode("Sack","Improper Loading");
			dchht.enterPointOfNotice("Noticed at cargo acceptance");
			dchht.enterRemarks("Remarks");
			dchht.clickSave();
			libr.quitApp();
	
			/***** LOGIN TO ICARGO*****/
			//Relaunch browser
			
            driver=libr.relaunchBrowser("chrome");
			
            driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);
			
			/***OPR341 - List Irregularity**/
			//Check the Damage is captured or not
			cust.searchScreen("OPR341", "List Irregularity");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "List Irregularity");
			int[] verfCols={3};
			String[] actVerfValues={"Damage Capture Reported"};
			OPR341.verifyIrregularityDetailsValue(verfCols, actVerfValues, "DDCR");
			cust.closeTab("OPR341", "List Irregularity");

		
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
		}

	}
}

