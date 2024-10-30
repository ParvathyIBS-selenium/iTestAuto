package goodsacceptance;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListHandlingActivities_OPR310;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;
import screens.TracingReports_TRC006;

import com.relevantcodes.extentreports.LogStatus;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class GA20 extends BaseSetup {

	//Testcase is failed since the details are not listing in OPR310 screen
	
	//Testcase GA21 is also clubbed
	
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
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005; 
	public TracingReports_TRC006 TRC006;
	public ListHandlingActivities_OPR310 OPR310;
	public GoodsAcceptance_OPR335 OPR335;
	public String proppath = "\\src\\resources\\GlobalVariable.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	String sheetName = "goodsacceptance";
	             

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
		OPR310=new ListHandlingActivities_OPR310(driver, excelreadwrite, xls_Read);
		TRC006=new TracingReports_TRC006(driver, excelreadwrite, xls_Read);
		cust.setPropertyValue("isClubbedTC", "Yes", globalVarPath); 
		
	}

	@DataProvider(name = "GA20")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;
	}

	@Test(dataProvider = "GA20")
	public void getTestSuite(Map<Object, Object> map) throws Exception {

	
		String className = this.getClass().getSimpleName();
		 //Map writeMap=new HashMap();
		System.out.println("className" + className);

		
		try {
			libr.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			
            /***** Testcase GA20 starts****/
            libr.setExtentTestInstance(test);
			System.out.println("The Class Name is:" + this.getClass().getName());

			// Login to "ICARGO"
			String[] iCargo = libr.getApplicationParams("iCargo");
			driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);

			/******* FLT005 - MAINTAIN FLIGHT ******/

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
			System.out.println(FlightNum);
			
			// Maintain Flight Screen (FLT005)

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");

			// Entering flight schedule data

			FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");

			FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();

			cust.waitForSync(1);
			cust.closeTab("FLT005", "Maintain Schedule");
			cust.waitForSync(1);
			
            
          /******* FBL message loading to be reoplaced by CAP018 once the screen is available*****/

			
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
			MSG005.clickClearButton();
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
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.saveAWB();  
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
            
			
			
			/****OPR355 - Goods Acceptance****/
			
			//Goods acceptance

			String ULDNo=cust.create_uld_number("val~AKE", "carrierCode");
			
			map.put("ULDNo",ULDNo);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.uldShipmentDetails("Pieces", "Weight","Location","ULDNo", "");
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			
			
			/**** LIST HANDLING INSTRUCTION***/
			cust.searchScreen("OPR310", "List Handling Instruction");
			OPR310.listAWB("prop~stationCode", "prop~AWBNo");
			
			//Verify table details
			String pmKey=cust.data("prop~stationCode")+" - "+cust.data("prop~AWBNo");
	          
            int verfColmn[]={6};   
            String[] actVerfValue={"TEMPCHECK"};
            OPR310.verifyWorkDetails(verfColmn, actVerfValue, pmKey);
        	cust.closeTab("OPR310", "List Handling Instruction");
			
        	
        	//Closing the extent report
			cust.closeExtentReport();                     
			
            /***** Testcase GA20 ends****/
        	
        	
        	/******TEST CASE GA21****/
            testName ="GA21";                                      
            libr.setExtentTestInstance(test);   
            cust.openExtentReport(testName);  
           

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.clickCaptureAWBDamage();
			TRC006.addDamageDetails("val~BAND", "Pieces2", "val~Captured Damage Details");
			TRC006.printFromAcceptanceScreen();
			TRC006.close();
			OPR335.verifyTitle();
			OPR335.verifyAcceptanceFinalized("finalised");
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");
           
			cust.closeExtentReport();    
			
			
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



