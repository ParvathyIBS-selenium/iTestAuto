package importmanifest;

import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import postconditions.CancelFlights;
import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

public class IM006 extends BaseSetup {
	
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
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainOperationalFlight_FLT003 FLT003;
	public ListMessages_MSG005 MSG005;
	public SecurityAndScreening_OPR339 OPR339;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public CancelFlights cancelFlights;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="importmanifest";	
	
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
	
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367= new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
	
		customfunctions.setPropertyValue("module", sheetName, globalVarPath);
		customfunctions.setPropertyValue("showStopper", "false", globalVarPath);
		customfunctions.setPropertyValue("isTcFailed", "false", globalVarPath);
		customfunctions.setPropertyValue("executionType", "Regression", globalVarPath);
	
	}
		
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		

			//create AWB

			String FBLDate = customfunctions.createDateFormat("ddMMM", 0, "DAY", "FBLDate");
			String flightStartdate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("flightStartdate",flightStartdate);
			map.put("FBLDate", FBLDate);
			map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
            map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			
			//Login to iCargo
		
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			customfunctions.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			/** Flight Creation 1 own carrier**/
			
			customfunctions.createFlight("FullFlightNumber");
			String startDate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = customfunctions.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("carrierCode")+customfunctions.data("prop~flightNo"), proppath);
			String FlightNum = customfunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			System.out.println(customfunctions.data("FullFlightNo"));
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", customfunctions.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", customfunctions.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum);
		
								
			/*******FLT005 - Flight Creation*******/
			
			
			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listFlight("carrierCode", "FlightNo", startDate, endDate);
			customfunctions.handleAlert("Accept", "FLT005");
			// Entering flight schedule data
			FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();
			customfunctions.closeTab("FLT005", "Maintain Schedule");
			
			
			/** Creating Flight 2***/
			customfunctions.createFlight("FullFlightNumber2");
			String FullFlightNo2 = customfunctions.getPropertyValue(proppath, "flightNumber2");
			map.put("FullFlightNo2", FullFlightNo2);
			map.put("FlightNo2", FullFlightNo2.substring(2));
			System.out.println(FullFlightNo2);
	        excelRead.writeDataInExcel(map, path1, sheetName, testName);
	       
	        customfunctions.setPropertyValue("flightNumber2", customfunctions.data("carrierCode")+customfunctions.data("prop~flightNo2"), proppath);
	        customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
	        FLT005.listFlight("carrierCode", "FlightNo2", startDate, endDate);
	        customfunctions.handleAlert("Accept", "FLT005");
			// Entering flight schedule data
			FLT005.enterFlightDetails("Route2", "scheduleType", "FCTL", "Office", "flightType");
			FLT005.enterLegCapacityDetails("departureTime1", "arrivalTime1", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();
			customfunctions.closeTab("FLT005", "Maintain Schedule");
			
			//Checking AWB is fresh or Not
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo = WebFunctions.getPropertyValue(proppath,"AWBNo");
			map.put("AWBNo", AWBNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			libr.waitForSync(1);
			
			
            //Writing the full AWB No to property file
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
                  
            /** Create the message FBL **/
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","carrierCode", "JMS","", "Origin", "", "FBL_1");
                  
           /** Process the message **/
           
           	MSG005.enterMsgType("FBL");
            MSG005.clickList();
            libr.waitForSync(6);      
            map.put("pmkey", customfunctions.data("carrierCode")+" - "+customfunctions.data("FlightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
                              +" - "+customfunctions.data("Origin"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickprocess();
            customfunctions.closeTab("MSG005", "List Message");

			
			/**** OPR339 - Security & Screening****/
            
			customfunctions.searchScreen("OPR339", "OPR339 - Security & Sceening");
            OPR339.listAWB("AWBNo", "prop~CarrierNumericCode", "OPR339 - Security & Sceening");
            OPR339.clickYesButton();
            OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
            OPR339.saveSecurityDetails();
            customfunctions.closeTab("OPR339", "Security & Sceening");

			
			
			/** Capture AWB Details **/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Transit","carrierCode");
			OPR026.enterSecondRouting("Destination", "carrierCode");	
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");	
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.clickBookingDetails();
			OPR026.enterBookingDetailsSingleLeg("Origin", "Transit", "prop~flightNumber", "StartDate", "Pieces", "Weight", "Volume");
			OPR026.enterBookingDetailsSecondRow("Transit", "Destination", "prop~flightNumber2", "StartDate","Pieces", "Weight", "Volume");
			OPR026.saveAWB();	
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			OPR026.close("OPR026");
		
			
			/****OPR355 - Goods Acceptance****/
            
          
			customfunctions.searchScreen("OPR335", "Goods Acceptance");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
            OPR335.looseShipmentDetails("Location", "Pieces","Weight");
            OPR335.addLooseShipment();
            OPR335.allPartsRecieved();
            OPR335.saveAcceptance();
            customfunctions.closeTab("OPR335", "Goods Acceptance");
			
			
			/**Export Manifest Bulk**/

            customfunctions.searchScreen("OPR344", "Export manifest");
            OPR344.listFlight("carrierCode", "FlightNo","StartDate");
            String uldNo=OPR335.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);		
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			OPR344.addNewULDWithAWB("UldNum","0","prop~CarrierNumericCode","prop~AWBNo","Pieces","Weight");
            OPR344.manifestDetails();
            OPR344.finalizeFlight();
            OPR344.verifyFlightStatus("val~Finalized");
            OPR344.closeTab("OPR344", "Export manifest");
            
			
            /**Switch role to Destination**/
			customfunctions.switchRole("Transit", "FCTL", "RoleGroup"); 
			
			/**Mark Flight Movement**/
            customfunctions.searchScreen("FLT006", "Mark Flight Movements");
            FLT006.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			FLT006.clickFlightMovementArrivalDetailsLink();
			FLT006.clickFlightMovementDepartureDetailsLink();
			FLT006.clickSave();
			FLT006.close("FLT006");
            
            /** Import Manifest **/
            
			customfunctions.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("carrierCode","FlightNo","StartDate");
            map.put("pmkey","UldNum");
            OPR367.clickCheckBox("pmkey");
            OPR367.clickBreakDownandBreakdownComplete("Location02","RcvdPcs","RcvdWt");
            OPR367.closeFromOPR004();
			OPR367.closeTab("OPR367", "Import Manifest");
	
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}

}

