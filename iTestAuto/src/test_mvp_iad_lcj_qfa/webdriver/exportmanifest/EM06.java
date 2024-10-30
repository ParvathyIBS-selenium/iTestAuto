package exportmanifest;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.CheckInCheckOutHistory_WHS022;
import screens.ExportFlightProgress_OPR336;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;
import screens.WarehouseRelocation_WHS009;
import screens.WarehouseShipmentEnquiry_WHS011;

import com.relevantcodes.extentreports.LogStatus;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class EM06 extends BaseSetup {

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
	public WarehouseShipmentEnquiry_WHS011 WHS011;
	public WarehouseRelocation_WHS009 WHS009;
	public CheckInCheckOutHistory_WHS022 WHS022;
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
		WHS011=new WarehouseShipmentEnquiry_WHS011(driver, excelreadwrite, xls_Read);
		WHS009=new WarehouseRelocation_WHS009(driver, excelreadwrite, xls_Read);
		WHS022=new CheckInCheckOutHistory_WHS022(driver, excelreadwrite, xls_Read);
	
	}

	@DataProvider(name = "EM06")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;
	}

	@Test(dataProvider = "EM06")
	public void getTestSuite(Map<Object, Object> map) throws Exception {

		libr.map = map;
		libr.setExtentTestInstance(test);

		String className = this.getClass().getSimpleName();
		 //Map writeMap=new HashMap();
		System.out.println("className" + className);
		try {

			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			System.out.println("The Class Name is:" + this.getClass().getName());

			// Login to "ICARGO"
			String[] iCargo = libr.getApplicationParams("iCargo");
			driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);

			/******* FLT005 - MAINTAIN FLIGHT ******/

			// creating flight number 1

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
			System.out.println(FlightNum);
			
			// Maintain Flight Screen (FLT005)

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("CarrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");

			// Entering flight schedule data

			FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");

			FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();

			cust.waitForSync(1);
			cust.closeTab("FLT005", "Maintain Schedule");
			cust.waitForSync(1);
			
			
			
            
          /******* FBL message loading to be reoplaced by CAP018 once the screen is available*****/

			
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
			
			
			/**** WAREHOUSE SHIPMENT ENQUIRY***/

			cust.searchScreen("WHS011", "Warehouse Shipment Enquiry");
			WHS011.enterAWBdetails();
			WHS011.clickList();
			//verify the location
			int verfColmn[]={4};  
			map.put("AWBNo", cust.data("prop~AWBNo"));
			String[] actVerfValue={cust.data("Location")};
			WHS011.verifyWarehouseDetails(verfColmn, actVerfValue);

			//Shipment relocation
			WHS011.clickAWBcheckBox();
			WHS011.clickShipmentRelocation();

			//Full Relocation
			WHS009.markCheckbox();
			WHS009.clickFullRelocationButton();
			WHS009.enterLocationnoFrameSwitch("Location2");
			cust.switchToMainScreen("WHS011");
			WHS009.clickSaveButton();
			WHS009.clickCloseButton();
			WHS011.verifyTitle();
			cust.closeTab("WHS011", "Warehouse Shipment Enquiry");

			/***WAREHOUSE SHIPMENT ENQUIRY . VERIFY WHETHER THE SHIPMENT IS RELOCATED***/
			cust.searchScreen("WHS011", "Warehouse Shipment Enquiry");
			WHS011.enterAWBdetails();
			WHS011.clickList();
			int verfColmn2[]={4};  
			String[] actVerfValue2={cust.data("Location2")};
			WHS011.verifyWarehouseDetails(verfColmn2, actVerfValue2);
			cust.closeTab("WHS011", "Warehouse Shipment Enquiry");
			
			/*** EXPORT MANIFEST**/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo","StartDate");
			OPR344.splitAndAssign("prop~AWBNo","OffloadPcs","OffloadWt","val~BULK");
			cust.waitForSync(5);
			OPR344.manifestDetails();
			OPR344.closeFlight();
			cust.closeTab("OPR344", "Export manifest");
			
			/*** CHECK IN -CHECK OUT HISTORY****/
			cust.searchScreen("WHS022", "Check in Check out History");
			WHS022.enterAWB("prop~CarrierNumericCode","prop~AWBNo");
			WHS022.listAwbDetails();
			//Verify the txn code , src loc and dest loca
			map.put("pmkey", cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo"));
			int verfColmn3[]={2,10,15};  
			String[] actVerfValue3={"BLDUPIN",cust.data("Location2"),"DEFBDP"};
			WHS022.verifyChkinChkOutDetails("pmkey", verfColmn3, actVerfValue3);
			cust.closeTab("WHS022", "Check in Check out History");
			
				} 
		
		catch (Exception e) {
			counter = counter + 1;
			excelreadwrite.insertFailedData(testName,
					commonUtility.getcurrentDateTime() + "_" + String.valueOf(counter), className, className, className,
					false, "", "Element is not found", "Element is found");
			libr.quitBrowser();
			test.log(LogStatus.FAIL, "The test step is failed");
			e.printStackTrace();
			Assert.assertFalse(true, "The test step is failed");

		}
	}
}



