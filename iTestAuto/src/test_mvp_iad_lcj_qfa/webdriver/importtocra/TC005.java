package importtocra;

/** Test case name : Verify the discrepancies in other charge**/
 
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.ListRateAuditExceptions_CRA193;
import screens.MaintainBooking_CAP018;
import screens.MaintainFlightSchedule_FLT005;
import screens.RateAuditDetailed_CRA212;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

public class TC005 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions customfunctions;
	public CaptureAWB_OPR026 OPR026;
	public MaintainBooking_CAP018 CAP018;
	public ListMessages_MSG005 MSG005;
	public MaintainFlightSchedule_FLT005 FLT005;
	public RateAuditDetailed_CRA212 CRA212;
	public GoodsAcceptance_OPR335 OPR335;
	public ListRateAuditExceptions_CRA193 CRA193;


	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="importtocra";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		customfunctions = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		CAP018 = new MaintainBooking_CAP018(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		CRA212 = new RateAuditDetailed_CRA212(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		CRA193 = new ListRateAuditExceptions_CRA193(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) throws InterruptedException {
		
		try {
			WebFunctions.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		
			//Login to iCargo
		
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			customfunctions.loginICargo(iCargo[1], iCargo[2],iCargo[3]);
			Thread.sleep(2000);
			
			customfunctions.createFlight("FullFlightNumber");
			map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
			String startDate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = customfunctions.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("ShipmentDate", customfunctions.createDateFormat("dd-MMM-YYYY",0,"DAY",""));
			map.put("flightDate", customfunctions.createDateFormat("dd-MMM-YYYY",0,"DAY",""));
			map.put("bkgStatus", "Confirmed");
			map.put("SSMStartDate", customfunctions.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", customfunctions.createDateFormat("ddMMM",7, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/****** UPDATING FWB CUSTOMER DETAILS IN MAP***/
			
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "agent_1_name"));
			map.put("ShipperAddress", WebFunctions.getPropertyValue(custproppath, "agent_1_address"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "agent_1_city_name"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "agent_1_country_code"));
			
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "agent_2_name"));
			map.put("ConsigneeAddress", WebFunctions.getPropertyValue(custproppath, "agent_2_address"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "agent_2_city"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "agent_2_country_name"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "agent_2_telephone"));

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "agent_1_name"));
			map.put("AgentCity", WebFunctions.getPropertyValue(custproppath, "agent_1_city_name"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "agent_1_iata_code"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "agent_1_cass_code"));
			
			map.put("Currency", WebFunctions.getPropertyValue(custproppath, "agent_1_currency"));
			map.put("FWBDate", customfunctions.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Maintain Flight Screen (FLT005) . Taking fresh flight
			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			customfunctions.closeTab("FLT005", "Maintain Schedule");
            //Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlighNo", WebFunctions.getPropertyValue(proppath, "flightNumber"));
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** MSG005 -SSM Message loading **/
            customfunctions.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "SSM_NEW");

            //Process the message
            MSG005.enterMsgType("SSM");
			MSG005.clickList();
			libr.waitForSync(3);
            map.put("pmkey", "NEW"+" - "+customfunctions.data("carrierCode")+" - "+customfunctions.data("FlightNo")+" - "+customfunctions.data("SSMStartDate").toUpperCase()
					+" - "+customfunctions.data("SSMEndDate").toUpperCase()+" - "+"1234567");
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			customfunctions.closeTab("MSG005", "List Message");

			//Creating Fresh AWB 

			customfunctions.searchScreen("CAP018", "Maintain Booking");
			CAP018.checkAWBExists_CAP018("Maintain Booking", "CAP018","AWBNo");
			// Writing the full AWB No
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("CarrierNumericCode") + "-" + customfunctions.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", customfunctions.data("prop~FullAWBNo"));
			map.put("AWBNo", customfunctions.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
		
			/** CAP018 - Maintain Booking**/
			
			customfunctions.searchScreen("CAP018", "Maintain Booking");
			CAP018.listAwb("prop~AWBNo");
			//Enter shipment details
			CAP018.enterShipmentDetails("Origin", "Destination", "AgentCode", "ShipmentDate");
			//Enter shipment level details
			CAP018.enterShipmentLevelDetails("CommodityCode", "Pieces", "Weight", "Volume");
			//Enter flight level details
			String origin[]={"Origin"};
			String destination[]={"Destination"};
			String flightNo[]={"FullFlighNo"};
			String fltDate[]={"flightDate"};
			String pcs[]={"Pieces"};
			String wt[]={"Weight"};
			String vol[]={"Volume"};
			CAP018.enterFlightLevelDetails(1, origin, destination, flightNo, fltDate, pcs, wt, vol, true,"val~Confirm");
			CAP018.saveBookingDetails("Confirmed");
			customfunctions.closeTab("CAP018", "Maintain Booking");
			
			 //Retrieve the chargable weight
			customfunctions.searchScreen("CAP018", "Maintain Booking");
			CAP018.listAwb("prop~AWBNo");
			CAP018.clickRatingTab();
			CAP018.getChargeCode(customfunctions.data("ChargeCode"),"OTchargeCodeVal");
			Integer changeCharge =Integer.parseInt(customfunctions.data("OTchargeCodeVal"))+20;
			map.put("ChargeCodeVal",changeCharge.toString());
			System.out.println(customfunctions.data("ChargeCodeVal"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			CAP018.storeChargableWeight("ChargableWeight");
			customfunctions.closeTab("CAP018", "Maintain Booking");
			
			/*** MSG005-- FWB loading ****/
			customfunctions.createTextMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			//load FWB with MY charge code
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "FWB_WithOC");
			customfunctions.closeTab("MSG005", "MSG005 - List Messages");
			
			/***** OPR026 - Capture AWB****/
		
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.verifyAndExecute();
			customfunctions.closeTab("OPR026", "Capture AWB");
			
			/*** MSG005-- FSU-RCS loading ****/
			customfunctions.createTextMessage("MessageExcelAndSheetRCS", "MessageParamRCS");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "FSU_RCS");
			customfunctions.closeTab("MSG005", "MSG005 - List Messages");
			
			//verify RFC status
			customfunctions.searchScreen("OPR335", "Goods Acceptance");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAcceptanceFinalizedinSameFrame("finalised");
			OPR335.verificationOfRFCStatus();
			customfunctions.closeTab("OPR335", "Goods Acceptance");
		

			/** Rate Audit Detailed  CRA212 **/
			
			customfunctions.searchScreen("CRA212","Rate Audit Detailed");
			CRA212.captureAWB("prop~CarrierNumericCode", "prop~AWBNo");
			CRA212.listDetails();
			
			//Import AWB to CRA
			
			CRA212.handleAlert("Accept","CRA212");
			customfunctions.switchToMainScreen("CRA212");
			CRA212.verifyRateAuditStatus("To Be Rate Audited");
			customfunctions.closeTab("CRA212", "Rate Audit Detailed");
			
			
			/** CRA193 : List rate audit exceptions Screen **/
			
			customfunctions.searchScreen("CRA193","List Rate Audit Exceptions");
			CRA193.enterAWB("prop~CarrierNumericCode", "prop~AWBNo");
			CRA193.List();
			int verfCols[] = { 18 };
			String actVerfValues[] = {"Discrepancy in Other charge"};
			CRA193.verifyExceptionRemarks("val~DISOTH",verfCols,actVerfValues);
			customfunctions.closeTab("CRA193", "List Rate Audit Exceptions");

			/********* Below Discrepancy handling Steps to modified after defect fix ********/
	         
			//Handling Discrepancy
	        customfunctions.searchScreen("CRA212","Rate Audit Detailed");
			CRA212.captureAWB("prop~CarrierNumericCode", "prop~AWBNo");
			CRA212.listDetails();
			CRA212.verifyErrorMessages("Discrepancy in Other charge");
			CRA212.getOtherChargesDiscrepancyCount();
			CRA212.handleOtherChargeDiscrepancy(customfunctions.data("ChargeCode"));
			CRA212.enterRemarks("AWB Other charge Discrepancy issue resolved");
			CRA212.clickSave();
			customfunctions.closeTab("CRA212", "Rate Audit Detailed");
			
			//Verify Discrepancies resolved
			customfunctions.searchScreen("CRA212","Rate Audit Detailed");
			CRA212.captureAWB("prop~CarrierNumericCode", "prop~AWBNo");
			CRA212.listDetails();
			CRA212.getOtherChargesDiscrepancyCount();
			customfunctions.closeTab("CRA212", "Rate Audit Detailed");
			
			/** List rate audit exceptions CRA193 **/
			//verify no data in rate audit exception screen
			customfunctions.searchScreen("CRA193","List Rate Audit Exceptions");
			CRA193.enterAWB("prop~CarrierNumericCode", "prop~AWBNo");
			CRA193.List();
			CRA193.checkIfExceptionResolved("CRA193-List Rate Audit Exceptions",false,4, "DISOTH","No Data Found");
			customfunctions.closeTab("CRA193", "List Rate Audit Exceptions");
			
			
			//Do Rate Audit
			customfunctions.searchScreen("CRA212","Rate Audit Detailed");
			CRA212.captureAWB("prop~CarrierNumericCode", "prop~AWBNo");
			CRA212.listDetails();
			CRA212.clickRateAudit();
			CRA212.handleDiscrepancy();
			CRA212.verifyRateAuditStatus("Rate Audited");
			
			//Do Finalize
			CRA212.clickBtnFinalize();
			CRA212.verifyRateAuditStatus("Finalized");
			
			customfunctions.closeTab("CRA212", "Rate Audit Detailed");
             

		}	
		catch(Exception e)
		{
			
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		
		
	}
}
