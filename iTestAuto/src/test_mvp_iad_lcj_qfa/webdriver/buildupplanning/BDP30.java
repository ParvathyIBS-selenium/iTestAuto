package buildupplanning;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BuildUpHHT;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.FlightLoadPlan_OPR015;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class BDP30 extends BaseSetup {
	
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
	public GoodsAcceptance_OPR335 OPR335;
	public FlightLoadPlan_OPR015 OPR015;
	public GoodsAcceptanceHHT gahht;
	public BuildupPlanning_ADD004 add004;
	public BuildUpHHT buhht;
	public MaintainFlightSchedule_FLT005 FLT005;

	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="buildupplanning";	
	
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
		OPR015=new FlightLoadPlan_OPR015(driver, excelreadwrite, xls_Read);
		add004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "HHT07")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "HHT07")
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
			String startDate = cust.createDateFormat("dd-MMM-yyyy", 0, "DAY", "");
			System.out.println(startDate);
			String endDate = cust.createDateFormat("dd-MMM-yyyy", 7, "DAY", "");
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

			// Maintain Flight Screen (FLT005)

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");

			// Entering flight schedule data

			FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");

			FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();

			//Enter Second leg details
			FLT005.checkLeg("Leg2");
			FLT005.clickUpdateCapacity();
			FLT005.enterLegCapacityDetails("departureTime", "arrivalTime", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();

			cust.waitForSync(7);
			cust.closeTab("FLT005", "Maintain Schedule");
			cust.waitForSync(1);




			/******MSG005-loading FBL***/

			//Checking AWB is fresh or Not--AWB2
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);


			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo2", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
			//Writing the  AWB No
			cust.setPropertyValue("AWBNo2", cust.data("prop~AWBNo"), proppath);


			//Checking AWB is fresh or Not--AWB 1
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);


			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);


			//Create the message FBL
			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_2Shipments");



			//Process the message

			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);


			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");



			/***** OPR026 - Execute AWB 1****/



			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.enterRouting("Transit","prop~flight_code");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.clickBookingDetails();
			OPR026.enterBookingDetailsSingleLeg("Origin", "Transit", "FullFlightNo", "StartDate", "Pieces", "Weight", "Volume");
			OPR026.saveAWB();
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/***** OPR026 - Execute AWB 2****/



			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo2", "prop~CarrierNumericCode");
			OPR026.enterRouting("Destination","prop~flight_code");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.clickBookingDetails();
			OPR026.enterBookingDetailsSingleLeg("Origin", "Destination", "FullFlightNo", "StartDate", "Pieces", "Weight", "Volume");
			OPR026.saveAWB();
			OPR026.listAWB("prop~AWBNo2", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/**** FLIGHT LOAD PLAN**/
			cust.searchScreen("OPR015","Flight Load Plan");
			String flightCode=WebFunctions.getPropertyValue(proppath,"flight_code");
			cust.listFlight("OPR015", flightCode, cust.data("prop~flightNo"), cust.data("StartDate"), "Generic_Elements");
			//Assign position for AWB 1
			OPR015.checkAWB(cust.data("prop~AWBNo"));
			OPR015.enterPosition("Position");
			OPR015.clickAssignAWB();
			OPR015.clickYesButton();
			//Assign position for AWB 2
			OPR015.selectSegment("Segment2");
			OPR015.checkAWB(cust.data("prop~AWBNo2"));
			OPR015.enterPosition("Position2");
			OPR015.clickAssignAWB();
			OPR015.clickYesButton();
			OPR015.clickSaveButton();
			cust.closeTab("OPR015", "Flight Load Plan");

			/** OPR355 - Goods Acceptance for AWB 1**/

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			libr.waitForSync(6);
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/** OPR355 - Goods Acceptance for AWB 1**/

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo2", "prop~CarrierNumericCode", "Goods Acceptance");
			libr.waitForSync(6);
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/*** BUILD UP PLANNING SCREEN***/
			String uldNum1=cust.create_uld_number("UldType", "carrierCode");
			String uldNum2=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum1);
			map.put("UldNum2", uldNum2);
			cust.searchScreen("ADD004", "Buildup Planning");
			add004.listFlight("carrierCode","FlightNo","StartDate");
			add004.verifyPendingNoOfAWBs("val~2AWB");
			add004.selectULD("prop~AWBNo");
			add004.clickAllocate();
			add004.selectSegment(1);
			add004.enterAllocateToDetails("0","Specific ULD","UldNum","0");
			//2nd AWB
			add004.selectULD("prop~AWBNo2");
			add004.clickAllocate();
			add004.selectSegment(2);
			add004.enterAllocateToDetails("0","Specific ULD","UldNum2","0");
			cust.closeTab("ADD004", "Buildup Planning");




			//QUIt browser
			libr.quitBrowser();


			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - Build Up****/


			buhht.invokeBuildUpScreen();

			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code", "prop~flightNo","currentDay");
			map.put("awbNumber", cust.data("prop~stationCode")+cust.data("prop~AWBNo"));
			buhht.enterShipmentDetails("awbNumber","Pieces", "Weight");
			buhht.verifyBuildUpDetailsIfSaved();
			libr.quitApp();

			/**** BUILD UP COMPLETE****/
			libr.launchApp("hht-app-release");

			//Login in to HHT

			cust.loginHHT(hht[0], hht[1]);
			buhht.invokeBuildUpScreen();
			buhht.enterValue("UldNum");
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteBtn();
			buhht.clickAndVerifyMarkTopUpPopUp();
			libr.quitApp();


			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT

			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - Build Up for ULD 2****/


			buhht.invokeBuildUpScreen();

			buhht.enterValue("UldNum2");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code", "prop~flightNo","currentDay","Destination");

			map.put("awbNumber", cust.data("prop~stationCode")+cust.data("prop~AWBNo2"));
			buhht.enterShipmentDetails("awbNumber","Pieces", "Weight");
			buhht.verifyBuildUpDetailsIfSaved();
			libr.quitApp();


			/**** BUILD UP COMPLETE FOR ULD 2****/
			libr.launchApp("hht-app-release");

			//Login in to HHT

			cust.loginHHT(hht[0], hht[1]);
			buhht.invokeBuildUpScreen();
			buhht.enterValue("UldNum2");
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteBtn();
			buhht.clickAndVerifyMarkTopUpPopUp();
			libr.quitApp();

			/***** LOGIN TO ICARGO*****/
			//Relaunch browser

			driver=libr.relaunchBrowser("chrome");

			driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);

			/**** BUILD UP PLANNING SCRREEN*/
			cust.searchScreen("ADD004", "Buildup Planning");
			add004.listFlight("carrierCode","FlightNo","StartDate");
			add004.verifyAWBWithinULDInAllocated("prop~AWBNo","UldNum");
			add004.verifyStatus("val~BUILDUP COMPLETED");
			cust.closeTab("ADD004", "Buildup Planning");



		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test step is failed");
		}

	}
}




