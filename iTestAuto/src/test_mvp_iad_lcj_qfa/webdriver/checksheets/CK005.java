package checksheets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.CaptureCheckSheet_CHK002;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class CK005 extends BaseSetup {

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
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
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


			/*******CREATE FLIGHT- WITH JMX SCRIPT ******/

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

			// Maintain Flight Screen (FLT003)

			customfunctions.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate","FullFlightNumber");

			//Assigning the data to the arraylist
			List<String> flightDetails=new ArrayList<String>();
			flightDetails.add(customfunctions.data("carrierCode"));
			flightDetails.add(customfunctions.data("prop~flightNo"));
			flightDetails.add(startDate.toUpperCase());
			flightDetails.add(customfunctions.data("Origin"));
			flightDetails.add(customfunctions.data("Route"));
			flightDetails.add(customfunctions.data("AircraftType"));
			flightDetails.add(customfunctions.data("Configuration_name"));
			flightDetails.add(customfunctions.data("scheduleType"));
			flightDetails.add(customfunctions.data("flightType"));

			customfunctions.createCSVFile("createFlight_FLT003", flightDetails);
			customfunctions.triggerJMXScript("createflight");
			customfunctions.waitForSync(12);
			customfunctions.verifyResponseOfJMXTrigger("val~Flight Saved Successfully.","createflightres","equals");
			customfunctions.killProcesses("CMD");
			customfunctions.closeTab("FLT003", "Maintain Operational Flight");






			//Checking AWB is fresh or Not
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			String awbNo = customfunctions.data("prop~AWBNo");
			map.put("AWBNo",awbNo);


			//Writing the full AWB No to property file
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);


			/**** OPR026 - Capture AWB****/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
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
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			//Execute AWB

			OPR026.asIsExecute();
			customfunctions.closeTab("OPR026", "Capture AWB");

			/****OPR355 - Goods Acceptance****/
			//Goods acceptance
			customfunctions.searchScreen("OPR335", "Goods Acceptance");
			OPR335.listAWB(customfunctions.data("prop~AWBNo"), "prop~CarrierNumericCode");

			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			customfunctions.closeTab("OPR335", "Goods Acceptance");



       

			/**** OPR344 - Export manifest****/
            customfunctions.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo","StartDate");
			String uldNum=customfunctions.create_uld_number("UldType", "prop~flight_code");
			map.put("uldNum", uldNum);
			OPR344.addNewULDWithAWB("uldNum","0","prop~CarrierNumericCode","prop~AWBNo","Pieces","Weight");
			OPR344.clickManifest();
			OPR344.verifyErrorMsg("val~Check Sheet");
			customfunctions.closeTab("OPR344", "Export manifest");
			
			/**** CAPTURE CHECK SHEET***/
			customfunctions.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo","StartDate");
			OPR344.expandULDs();
			
			map.put("pmKey",customfunctions.data("ULDNo")+customfunctions.data("prop~CarrierNumericCode")+customfunctions.data("prop~AWBNo")+"11");
			OPR344.clickCheckSheet("pmKey");
			OPR344.captureCheckSheet(true);
			customfunctions.closeTab("OPR344", "Export manifest");

		}	
		catch(Exception e)
		{

			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}


	}
}


