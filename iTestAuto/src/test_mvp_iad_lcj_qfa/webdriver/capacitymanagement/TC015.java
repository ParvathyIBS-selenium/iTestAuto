package capacitymanagement;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.ListActiveAllotments_CAP030;
import screens.ListMessages_MSG005;
import screens.MaintainAircraftType_SHR003;
import screens.MaintainBooking_CAP018;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainGlobalCustomerAllotment_CAP006;
import screens.MaintainOperationalFlight_FLT003;
import screens.MaintainStationAllotment_CAP012;
import screens.Monitor_Flights_CAP147;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

/**
 * Action to be taken on overbooked flight.
 * 
 *
 */
public class TC015 extends BaseSetup {

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
	public MaintainOperationalFlight_FLT003 FLT003;
	public MaintainStationAllotment_CAP012 CAP012;
	public MaintainGlobalCustomerAllotment_CAP006 CAP006;
	public ListActiveAllotments_CAP030 CAP030;

	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="capacitymanagement";	

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
		CAP006 = new MaintainGlobalCustomerAllotment_CAP006(driver, excelreadwrite, xls_Read);
		CAP030 = new ListActiveAllotments_CAP030(driver, excelreadwrite, xls_Read);
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

			/******* FLT005 - MAINTAIN FLIGHT ******/

			// creating flight number

			customfunctions.createFlight("FullFlightNumber");
			String startDate = customfunctions.createDateFormat("dd-MMM-YYYY", 1, "DAY", "");
			String endDate = customfunctions.createDateFormat("dd-MMM-YYYY", 8, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", customfunctions.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", customfunctions.createDateFormat("ddMMM",8, "DAY", ""));
			map.put("flightDate", customfunctions.createDateFormat("dd-MMM-YYYY",1,"DAY",""));
			map.put("ShipmentDate", customfunctions.createDateFormat("dd-MMM-YYYY", 1, "DAY", ""));
			
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

			/** SHR003 - Maintain Aircraft Type- Take aircraft capacity **/

			customfunctions.searchScreen("SHR003", "Maintain Aircraft Type");
			SHR003.listAircraft(customfunctions.data("AircraftType"));
			List<String> capacityDetails=SHR003.getAircraftCapacityDetails();
			map.put("AircraftWgt", capacityDetails.get(0));
			map.put("AircraftVol", capacityDetails.get(1));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			customfunctions.closeTab("SHR003", "Maintain Aircraft Type");


			// Maintain Flight Schedule (FLT005)

			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listFlight("carrierCode","FlightNo",customfunctions.data("StartDate"),customfunctions.data("EndDate") );
			FLT005.clickUpdateCapacity();
			FLT005.enterOVBWeightAndOVBVolume(customfunctions.data("OVBWgt"),customfunctions.data("OVBVol"));
			FLT005.legCapacityOkButton();
			FLT005.save();
			customfunctions.closeTab("FLT005", "Maintain Schedule");


			/** CAP006 - Global Customer Allotment**/

			customfunctions.searchScreen("CAP006", "Maintain Global Customer Allotment");
			CAP006.enterCustomerCode("AgentCode");
			CAP006.enterFlightDetails("carrierCode", "FlightNo", "Origin", "Destination");
			CAP006.enterAllotmentRange(startDate,endDate);
			CAP006.enterFrequency(); //selects ALL
			CAP006.enterCategory("val~AB");
			CAP006.enterAllotmentSubType("val~ACA");
			CAP006.enterShipmetType("val~Loose");
			CAP006.enterCapacity("AllotmentWeight", "AllotmentVolume");
			CAP006.saveDetails("AllotmentID");
			System.out.println(customfunctions.data("AllotmentID"));
			customfunctions.closeTab("CAP006", "Maintain Global Customer Allotment");

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
			CAP018.enterShipmentDetails("Origin", "Destination",  "AgentCode", "ShipmentDate");
			//Enter shipment level details
			CAP018.enterShipmentLevelDetails("CommodityCode", "Pieces", "Weight", "Volume");
			//Enter flight level details
			String origin[]={"Origin"};
			String destination[]={"Destination"};
			String flightNo[]={"FullFlightNo"};
			String fltDate[]={"flightDate"};
			String pcs[]={"Pieces"};
			String wt[]={"Weight"};
			String vol[]={"Volume"};
			CAP018.enterFlightLevelDetails(1, origin, destination, flightNo, fltDate, pcs, wt, vol, true,"val~Confirm");
			CAP018.selectFlightRow("0");
			CAP018.selectGlobalAllotment(customfunctions.data("AllotmentID"));
			CAP018.saveBookingDetails("Confirmed");
			customfunctions.closeTab("CAP018", "Maintain Booking");


			/** CAP147 -Monitor Flights **/

			customfunctions.searchScreen("CAP147", "Monitor Flights");
			CAP147.listFlight("carrierCode", "FlightNo", "flightDate");
			CAP147.clickViewCapacitySummary();
			CAP147.remainingCapacityFSSalesAllotment(customfunctions.data("AircraftWgt"), customfunctions.data("OVBWgt"), customfunctions.data("AircraftVol"), customfunctions.data("OVBVol"),customfunctions.data("AllotmentWeight"),customfunctions.data("AllotmentVolume"));
			CAP147.remainingcapacityFSHandlingAllotment(customfunctions.data("AircraftWgt"), customfunctions.data("AircraftVol"), customfunctions.data("AllotmentWeight"),customfunctions.data("AllotmentVolume")); 
			CAP147.remainingAllotmentCapacity(customfunctions.data("AllotmentWeight"), customfunctions.data("AllotmentVolume"), customfunctions.data("Weight"),customfunctions.data("Volume"));
			CAP147.clickCloseOnViewCapacitySummary();
			CAP147.clickAllotments();
			CAP147.releaseAllotments("AllotmentID");
			customfunctions.closeTab("CAP147", "Monitor Flights");


			/** CAP030 - listing active allotments**/

			customfunctions.searchScreen("CAP030", "List active allotments");
			CAP030.enterAllotment("AllotmentID");
			CAP030.enterDateRange("StartDate","StartDate");
			CAP030.clicklistallotments();
			String pmKey=customfunctions.data("AllotmentID");
			int verfCols[]={37};
			String[] actVerfValues={"Released"};
			CAP030.verifyAllotmentDetails(verfCols,actVerfValues,pmKey);
			customfunctions.closeTab("CAP030", "List Active Allotments");
			
			/**** Should cross check and update once the allotment defect(release allotment) is fixed***/

			/** CAP147 -Monitor Flights **/

			customfunctions.searchScreen("CAP147", "Monitor Flights");
			CAP147.listFlight("carrierCode", "FlightNo", "flightDate");
			CAP147.clickViewCapacitySummary();
			CAP147.remainingCapacityFSSaleswithoutAllotment(customfunctions.data("AircraftWgt"), customfunctions.data("OVBWgt"), customfunctions.data("AircraftVol"), customfunctions.data("OVBVol"), customfunctions.data("Weight"),customfunctions.data("Volume"));
			CAP147.remainingcapacityFSHandlingwithoutAllotment(customfunctions.data("AircraftWgt"), customfunctions.data("AircraftVol"), customfunctions.data("Weight"),customfunctions.data("Volume"));
			CAP147.totalAllotment("0","0");
			customfunctions.closeTab("CAP147", "Monitor Flights");
		}
		catch(Exception e)
		{

			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}				
