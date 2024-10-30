package exportmanifest;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.ExportFlightProgress_OPR336;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;

import com.relevantcodes.extentreports.LogStatus;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class EM33 extends BaseSetup {

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
	public SecurityAndScreening_OPR339 OPR339;
	public ExportManifest_OPR344 OPR344;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005; 
	public GoodsAcceptance_OPR335 OPR335;
	public ExportFlightProgress_OPR336 OPR336;
	public MaintainOperationalFlight_FLT003 FLT003;
	public String proppath = "\\src\\resources\\GlobalVariable.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	String sheetName = "exportmanifest";

	@BeforeClass
	public void setup() {
		testName = getTestName();
		excel = new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR339= new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR336=new ExportFlightProgress_OPR336(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "EM33")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;
	}

	@Test(dataProvider = "EM33")
	public void getTestSuite(Map<Object, Object> map) throws Exception {

		libr.map = map;
		libr.setExtentTestInstance(test);

		String className = this.getClass().getSimpleName();
		 //Map writeMap=new HashMap();
		System.out.println("className" + className);
		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		

			//create AWB

			String flightStartdate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("flightStartdate",flightStartdate);
			String FBLDate = cust.createDateFormat("ddMMM", 0, "DAY", "FBLDate");
			map.put("FBLDate", FBLDate);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
            map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			
			//Login to iCargo
		
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			
		   /** Flight Creation **/
			
			cust.createFlight("FlightNo");
			String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			map.put("FlightNo", flightNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code")+cust.data("prop~flightNo"), proppath);
			cust.searchScreen("FLT003","Maintain Operational Flight");
			
			
			FLT003.listNewFlight("prop~flightNo", "flightStartdate", "FlightNo");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("ATD_Local","ATA_Local", "AircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");
			
			
            
           /******* FBL message loading to be reoplaced by CAP018 once the screen is available*****//*

			
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
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_5");
			
			
			
			//Process the message
			
			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);
			
			
			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
			/**** OPR339 - Security & Screening****/
			
			cust.searchScreen("OPR339", "OPR339 - Security & Sceening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");
			
			/***** OPR026 - Execute AWB****/
			
			//Capture AWB Details
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","prop~flight_code");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","SCC", "ShipmentDescription");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.saveAWB();  
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
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
			
			/**** ASSIGN LYING LIST TO BE REPLACED WITH ASSIGN SHIPMENT FROM LPRL ONCE THE BOOKING IS DONE THROUGH SCREEN**/
			
			/**** OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "prop~flightNo","flightStartdate");
			 OPR344.addNewULDWithAWB("val~BULK","0","prop~CarrierNumericCode","prop~AWBNo","Pieces","Weight");
			OPR344.verifyFlightStatus("val~Built Up");
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			cust.closeTab("OPR344", "Export manifest");
			
			/***** USE EXPORT FLIGHT LISTING WHEN AVAILABLE****/
			
			/**** EXPORT FLIGHT PROGRESS****/
			cust.searchScreen("OPR336", "Export flight progress");
			OPR336.listFlight("prop~flight_code", "prop~flightNo", "flightStartdate", "Manifest");
			
			//Verify flight details
			
			OPR336.verifyFlightDetails("prop~flightNo");
			OPR336.verifyManifestStatusImage();
			cust.closeTab("OPR336", "Export flight progress");
			
			
			
				} 
		
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
	}
}


