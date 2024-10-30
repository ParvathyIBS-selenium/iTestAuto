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
import screens.ImportFlightProgress_OPR337;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
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

public class IM083 extends BaseSetup {
	
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
	public ImportFlightProgress_OPR337 OPR337;
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
		OPR337=new ImportFlightProgress_OPR337(driver, excelreadwrite, xls_Read);
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
			
			
			/** Flight Creation **/
			
			customfunctions.createFlight("FlightNo");
			String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			map.put("FlightNo", flightNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
			customfunctions.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "flightStartdate", "FlightNo");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");
            
            /** Create the message FFM ULD BultUp **/
            
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FFM_NIL");
                  
           /** Process the message **/
			
            MSG005.enterMsgType("FFM");
            MSG005.clickList();
            libr.waitForSync(6);      
            map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()+" - "+customfunctions.data("Origin")+" - "+customfunctions.data("Destination"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickprocess();
            libr.waitForSync(5);
	        MSG005.verifyMessageTriggered("pmkey", "FFM");    
            customfunctions.closeTab("MSG005", "List Message");			
            
			
			/**Export Manifest Bulk**/

           customfunctions.searchScreen("OPR344", "Export manifest");
            OPR344.listFlight("prop~flight_code", "FlightNo","flightStartdate");
            OPR344.manifestDetails();
            OPR344.finalizeFlight(true);
            OPR344.verifyFlightStatus("val~Finalized");
            OPR344.closeTab("OPR344", "Export manifest");
            

			
			/**Switch role to Destination**/
			customfunctions.switchRole("Destination", "Origin", "RoleGroup");
			
			/**Mark Flight Movement**/
            customfunctions.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("prop~flightNo", "flightStartdate");
			FLT006.clickFlightMovementArrivalDetailsLink();
			FLT006.clickFlightMovementDepartureDetailsLink();
			FLT006.clickSave();
			FLT006.close("FLT006");
            
            /** Import Flight Progress screen **/
			customfunctions.searchScreen("OPR337", "Import Flight Progress");
			OPR337.listFlightBasedOnNilFlightCheckbox("prop~flight_code", "FlightNo","flightStartdate",false);
			OPR337.verifyFlightDetailsNotFound("FlightNo");
			OPR337.closeTab("OPR337", "Import Flight Progress");
	
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}

}

