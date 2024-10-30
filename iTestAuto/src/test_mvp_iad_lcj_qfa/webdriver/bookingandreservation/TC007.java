package bookingandreservation;

/**
 * Request for AWB Space Allocation with alternative flight not acceptable using FFR
 *
 * Clarifications
 * 
 * Scripted using OWN concept not Inter-line concept as per message sample attached with test case design
 * **/
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.ListMessages_MSG005;
import screens.MaintainAircraftType_SHR003;
import screens.MaintainBooking_CAP018;
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

public class TC007 extends BaseSetup {
	
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
	public MaintainAircraftType_SHR003 SHR003;
	public Monitor_Flights_CAP147 CAP147;
	public MaintainFlightSchedule_FLT005 FLT005;

	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="bookingandreservation";	
	
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
		CAP018 = new MaintainBooking_CAP018(driver, excelreadwrite, xls_Read);
		SHR003=new MaintainAircraftType_SHR003(driver, excelreadwrite, xls_Read);
		CAP147=new Monitor_Flights_CAP147(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
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
			String startDate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = customfunctions.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("ShipmentDate", customfunctions.createDateFormat("dd-MMM-YYYY",1,"DAY",""));
			map.put("flightDate", customfunctions.createDateFormat("dd-MMM-YYYY",1,"DAY",""));
			map.put("bkgStatus", "Confirmed");
			map.put("SSMStartDate", customfunctions.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", customfunctions.createDateFormat("ddMMM",7, "DAY", ""));
			map.put("FFRDate", customfunctions.createDateFormat("ddMMM", 1, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			
			
			// Maintain Flight Screen (FLT005) . Taking fresh flight

			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
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
			
			/** SHR003 - Maintain Aircraft Type- Take aircraft capacity **/
			
			customfunctions.searchScreen("SHR003", "Maintain Aircraft Type");
			SHR003.listAircraft(customfunctions.data("AircraftType"));
			List<String> capacityDetails=SHR003.getAircraftCapacityDetails();
			map.put("AircraftWgt", capacityDetails.get(0));
			map.put("AircraftVol", capacityDetails.get(1));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			customfunctions.closeTab("SHR003", "Maintain Aircraft Type");
			
			/** FFR MESSAGE LOADING **/
			map.put("StatusCode", "NN");
			map.put("Mode", "FC");
			
			/** MSG005 -FFR	 Message loading **/

			customfunctions.createTextMessage("MessageExcelAndSheetFFR", "MessageParamFFR");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "FFR");
			customfunctions.closeTab("MSG005", "List Message");
			
			/** CAP018 - Maintain Booking**/
			
			customfunctions.searchScreen("CAP018", "Maintain Booking");
			CAP018.listAwb("prop~AWBNo");
			CAP018.verifyBkgStatus("val~Confirmed");
			CAP018.verifyOriginDest("Origin", "Destination");
			CAP018.VerifyUpdatedSCCValues("SCC");
			String origin[]={"Origin"};
			String destination[]={"Destination"};
			String flightNo[]={"FullFlightNo"};
			String fltDate[]={"flightDate"};
			String pcs[]={"Pieces"};
			String wt[]={"Weight"};
			String vol[]={"Volume"};
			CAP018.verifyFlightLevelDetails(1, origin, destination, flightNo, fltDate, pcs, wt, vol);
			customfunctions.closeTab("CAP018", "Maintain Booking");
			
			
			/** CAP147 -Monitor Flights**/
			
			customfunctions.searchScreen("CAP147", "Monitor Flights");
			CAP147.listFlight("carrierCode", "FlightNo", "flightDate");
			CAP147.clickViewCapacitySummary();
			CAP147.verifyDepletionDetailsAfterFSBooking(customfunctions.data("AircraftWgt"),customfunctions.data("AircraftVol"),customfunctions.data("Weight"),customfunctions.data("Volume"));
			customfunctions.closeTab("CAP147", "Monitor Flights");
			

			/*******Verify FSU-BKD message in MSG005******/
			
			
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.enterMsgType("FSU");
            MSG005.selectMsgSubType("Booked");
            MSG005.clickList();
            String pmKeyFSU=customfunctions.data("prop~CarrierNumericCode")+" - "+customfunctions.data("AWBNo");
            int verfColsFSU[]={9};
            String[] actVerfValuesFSU={"Sent"};
            MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~FSU-BKD",true);
            libr.waitForSync(1);
            MSG005.closeTab("MSG005", "MSG005 - List Messages");
             

		}	
		catch(Exception e)
		{
			
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		
		
	}
}

