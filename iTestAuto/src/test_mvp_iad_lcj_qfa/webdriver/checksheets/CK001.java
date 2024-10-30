package checksheets;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import postconditions.CancelFlights;
import screens.CaptureAWB_OPR026;
import screens.CaptureCheckSheet_CHK002;
import screens.DeliverCargo_OPR064;
import screens.DeliverNoteEnquiry_OPR034;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainAndListSystemParameters_SHR048;
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

public class CK001 extends BaseSetup {
	
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
	public MaintainFlightSchedule_FLT005 FLT005;
	public CaptureCheckSheet_CHK002 CHK002;
	public ListMessages_MSG005 MSG005;

	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="checksheets";	
	
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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		CHK002= new CaptureCheckSheet_CHK002(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) throws InterruptedException {
		
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
			
			
			/******* FLT005 - MAINTAIN FLIGHT ******/

			// creating flight number

			customfunctions.createFlight("FullFlightNumber");
			String startDate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = customfunctions.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = customfunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", customfunctions.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
			System.out.println(FlightNum);

			// Maintain Flight Screen (FLT005)

			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");

			// Entering flight schedule data

			FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");

			FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();

			customfunctions.waitForSync(7);
			customfunctions.closeTab("FLT005", "Maintain Schedule");
			customfunctions.waitForSync(1);





			/******MSG005-loading FBL****/

			//Checking AWB is fresh or Not
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);


			//Writing the full AWB No
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);


			//Create the message FBL
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_1");



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
			OPR026.enterRouting("Destination","prop~flight_code");       
			OPR026.selectSCI("SCI");
			OPR026.enterSCC(customfunctions.data("SCC"));
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
            
			customfunctions.searchScreen("OPR335", "Goods Acceptance");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");  
			//Loose acceptance
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
            OPR335.addLooseShipment();
            OPR335.clickSave();
            OPR335.verifyAcceptanceFinalized("not finalised",false);
            OPR335.verificationOfNotRFCStatus();
            OPR335.verifyNotFinalizedReason("val~check sheet not complete");
            customfunctions.closeTab("OPR335", "Goods Acceptance");
			
		/***** CAPTURE CHECK SHEET***/
			customfunctions.searchScreen("CHK002", "Capture Check Sheet");
			CHK002.listCheckSheetType("AWB");
			CHK002.listAWBWithTransaction("AWBNo", "prop~CarrierNumericCode","Acceptance");
			CHK002.enterCheckSheetAns();
			CHK002.save();
			CHK002.verifyPrint();
			customfunctions.closeTab("CHK002", "Capture Check Sheet");
			 
			
			 /****OPR355 - Goods Acceptance****/
		    customfunctions.searchScreen("OPR335", "Goods Acceptance");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");  
            OPR335.verificationOfRFCStatus();
            OPR335.verifyAcceptanceFinalized("finalised",false);
            customfunctions.closeTab("OPR335", "Goods Acceptance");
            
            
            
          /** CHECKING FSU-RCS TRIGGERED FOR AWB1 **/
            
            customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
		 	MSG005.enterMsgType("FSU");
		 	MSG005.selectMsgSubType("Acceptance");
		 	MSG005.clickList();
		 	String pmKeyRCS1=customfunctions.data("prop~CarrierNumericCode")+" - "+customfunctions.data("prop~AWBNo");
		 
			int verfColsRCS[]={9};
			String[] actVerfValuesRCS={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS1,"val~FSU-RCS",true);
            libr.waitForSync(6); 
            MSG005.closeTab("MSG005", "MSG005 - List Messages");
	
		
	        
		}	
		catch(Exception e)
		{
			
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		
		
	}
}

