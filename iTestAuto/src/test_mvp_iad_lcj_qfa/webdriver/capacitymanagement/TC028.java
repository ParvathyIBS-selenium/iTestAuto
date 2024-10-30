package capacitymanagement;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.ListFlightSchedules_FLT004;
import screens.ListMessages_MSG005;
import screens.MaintainAircraftType_SHR003;
import screens.MaintainFlightSchedule_FLT005;
import screens.Monitor_Flights_CAP147;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

/*** Adhoc flight creation via ASM and capacity consumptions  ***/


public class TC028 extends BaseSetup {
	
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
	public Monitor_Flights_CAP147 CAP147;
	public MaintainAircraftType_SHR003 SHR003;

	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="capacitymanagement";	
	
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
		CAP147=new Monitor_Flights_CAP147(driver, excelreadwrite, xls_Read);
		SHR003=new MaintainAircraftType_SHR003(driver, excelreadwrite, xls_Read);
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
			String startDate = customfunctions.createDateFormat("dd-MMM-YYYY", 1, "DAY", "");
			map.put("StartDate", startDate);
			map.put("ASMStartDate", customfunctions.createDateFormat("ddMMM", 1, "DAY", ""));
			
			
			// Maintain Flight Screen (FLT005) . Taking fresh flight

			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			customfunctions.closeTab("FLT005", "Maintain Schedule");
			
			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", WebFunctions.getPropertyValue(proppath, "flightNumber"));
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/** MSG005 -ASM Message loading **/

			customfunctions.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "ASM_NEW");
			
			//Process the message
			
			MSG005.enterMsgType("ASM");
			MSG005.clickList();
			libr.waitForSync(3);


			map.put("pmkey", "NEW"+" - "+customfunctions.data("carrierCode")+" - "+customfunctions.data("FlightNo")+" - "+customfunctions.data("ASMStartDate").toUpperCase());
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			customfunctions.closeTab("MSG005", "List Message");
			
			
			

			/** SHR003 - Maintain Aircraft Type- Take aircraft capacity **/
			
			customfunctions.searchScreen("SHR003", "Maintain Aircraft Type");
			SHR003.listAircraft(customfunctions.data("AircraftType"));
			List<String> capacityDetails=SHR003.getAircraftCapacityDetails();
			map.put("AircraftWgt", capacityDetails.get(0));
			map.put("AircraftVol", capacityDetails.get(1));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			customfunctions.closeTab("SHR003", "Maintain Aircraft Type");
			
			
			
           /******* FLT004 - LIST FLIGHT SCHEDULE ******/
			
			//List the flight and verify capacity details
			
			customfunctions.searchScreen("FLT004", "FLT004 - List Flight Schedule");
			FLT004.listFlight("FLT004", customfunctions.data("carrierCode"), customfunctions.data("FlightNo"), customfunctions.data("StartDate"), "ListFlightSchedules_FLT004");
			FLT004.clickSpecificCheckBox("FlightNo");
			FLT004.clickDetails();
			FLT005.clickUpdateCapacity();
			FLT005.verifyFlightCapacity("AircraftWgt", "AircraftVol");
			FLT005.legCapacityOkButtonFromFLT004();	
			customfunctions.closeTab("FLT005", "Maintain Schedule");
	
			/** CAP147 -Monitor Flights**/
			
			customfunctions.searchScreen("CAP147", "Monitor Flights");
			CAP147.listFlight("carrierCode", "FlightNo", "StartDate");
			CAP147.clickViewCapacitySummary();
			CAP147.verifyDepletionDetailsAfterFSBooking(customfunctions.data("AircraftWgt"),customfunctions.data("AircraftVol"),customfunctions.data("Weight"),customfunctions.data("Volume"));
			CAP147.verifyTotalConsumedCapacity(customfunctions.data("Weight"),customfunctions.data("Volume"));
			customfunctions.closeTab("CAP147", "Monitor Flights");
			
			
		}	
		catch(Exception e)
		{
			
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		
		
	}
}

