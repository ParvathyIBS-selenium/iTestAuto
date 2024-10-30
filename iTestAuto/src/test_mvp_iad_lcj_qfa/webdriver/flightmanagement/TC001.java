package flightmanagement;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.ListFlightSchedules_FLT004;
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



//No message handling details for SSM on MSG003 screen
public class TC001 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions customfunctions;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ListFlightSchedules_FLT004 FLT004;
	public ListMessages_MSG005 MSG005;

	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="flightmanagement";	
	
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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		FLT004 = new ListFlightSchedules_FLT004(driver, excelreadwrite, xls_Read);
	
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
			customfunctions.loginICargo(iCargo[1], iCargo[2], iCargo[3]);
			Thread.sleep(2000);
			
			
			/******* FLT005 - MAINTAIN FLIGHT ******/

			// creating flight number

			
			customfunctions.createFlight("FullFlightNumber");
			
			
			
			String startDate = customfunctions.createDateFormat("dd-MMM-yyyy", 0, "DAY", "");
			String endDate = customfunctions.createDateFormat("dd-MMM-yyyy", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", customfunctions.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", customfunctions.createDateFormat("ddMMM",7, "DAY", ""));
			
			// Maintain Flight Screen (FLT005) . Taking fresh flight

			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("CarrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			customfunctions.closeTab("FLT005", "Maintain Schedule");
			
			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", WebFunctions.getPropertyValue(proppath, "flightNumber"));
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


			map.put("pmkey", "NEW"+" - "+customfunctions.data("CarrierCode")+" - "+customfunctions.data("FlightNo")+" - "+customfunctions.data("SSMStartDate").toUpperCase()
					+" - "+customfunctions.data("SSMEndDate").toUpperCase()+" - "+"1234567");
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			customfunctions.closeTab("MSG005", "List Message");
			
			/******* FLT004 - LIST FLIGHT SCHEDULE ******/
			
			//List the flight and verify SSM details
			
			customfunctions.searchScreen("FLT004", "FLT004 - List Flight Schedule");
			FLT004.listFlight("FLT004", customfunctions.data("CarrierCode"), customfunctions.data("FlightNo"), customfunctions.data("StartDate"), "ListFlightSchedules_FLT004");
			
			String pmKeyFLT004 = customfunctions.data("CarrierCode") + " " + customfunctions.data("FlightNo");
			int verfColsFLT004 [] = { 2,8,9,10,12,13,15};
			String[] actVerfValuesFLT004 = { "SSM",customfunctions.data("Route"),customfunctions.data("StartDate"),customfunctions.data("EndDate"),"1234567",customfunctions.data("AircraftType"),"Live" };
			FLT004.verifyLegDetails( verfColsFLT004, actVerfValuesFLT004,pmKeyFLT004);
			customfunctions.closeTab("FLT004", "FLT004 - List Flight Schedule");
			
			
			/******* FLT005 - MAINTAIN FLIGHT ******/
			
			//List flight and verify the SSM details
			
			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listFlight("CarrierCode", "FlightNo", customfunctions.data("StartDate"), customfunctions.data("EndDate"));
			FLT005.checkFrequency("All");
			FLT005.verifyFlightStatus("LIVE");
			String pmKeyFLT005 = customfunctions.data("Route");
			int verfColsFLT005 [] = { 4,6,8};
			
			 
			
			if(customfunctions.checkDSTExists(startDate,"Europe/Berlin"))
			{
				String[] actVerfValuesFLT005 = { customfunctions.data("DepartureTime"),customfunctions.data("ArrivalTime"),customfunctions.data("AircraftType") };
				FLT005.verifyLegDetails( verfColsFLT005, actVerfValuesFLT005,pmKeyFLT005);
			}
			else
			{
				
				String[] actVerfValuesFLT005 = { customfunctions.data("DepartureTime"),customfunctions.data("ArrivalTime_WithoutDST"),customfunctions.data("AircraftType") };
				FLT005.verifyLegDetails( verfColsFLT005, actVerfValuesFLT005,pmKeyFLT005);
			}
			
			customfunctions.closeTab("FLT005", "FLT005 - Maintain Flight Schedule");
			
		}	
		catch(Exception e)
		{
			
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		
		
	}
}

