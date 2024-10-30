package bookingandreservation;
/**Capture a booking using Multiple Allotments  for multiple flights **/
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.CaptureAWB_OPR026;
import screens.ListMessages_MSG005;
import screens.MaintainAircraftType_SHR003;
import screens.MaintainBooking_CAP018;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainGlobalCustomerAllotment_CAP006;
import screens.MaintainStationAllotment_CAP012;
import screens.Monitor_Flights_CAP147;

public class TC043 extends BaseSetup {

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
	public MaintainStationAllotment_CAP012 CAP012;
	public MaintainGlobalCustomerAllotment_CAP006 CAP006;


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
		CAP012=new MaintainStationAllotment_CAP012(driver, excelreadwrite, xls_Read);
		CAP006=new MaintainGlobalCustomerAllotment_CAP006(driver, excelreadwrite, xls_Read);
	}



	@DataProvider(name = "TC042")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC042")
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
			customfunctions.createFlight("FullFlightNumber2");
			String startDate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String nextDate = customfunctions.createDateFormat("dd-MMM-YYYY", 1, "DAY", "");
			String endDate = customfunctions.createDateFormat("dd-MMM-YYYY", 8, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("ShipmentDate", customfunctions.createDateFormat("dd-MMM-YYYY",1,"DAY",""));
			map.put("flightDate", customfunctions.createDateFormat("dd-MMM-YYYY",1,"DAY",""));
			map.put("bkgStatus", "Confirmed");
			map.put("SSMStartDate", customfunctions.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", customfunctions.createDateFormat("ddMMM",8, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
		    // Maintain Flight Screen (FLT005) . Taking fresh flight 1

			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			customfunctions.closeTab("FLT005", "Maintain Schedule");
			
			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlighNum", WebFunctions.getPropertyValue(proppath, "flightNumber"));
			map.put("FullFlighNo1", WebFunctions.getPropertyValue(proppath, "flightNumber"));
			map.put("FlightNo1", FlightNum.substring(2));
			map.put("Org", customfunctions.data("Origin"));
			map.put("Dest",customfunctions.data("Transit"));
			map.put("ATD", customfunctions.data("ATD_Local"));
			map.put("ATA", customfunctions.data("ATA_Local"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/** MSG005 -SSM Message loading **/

			customfunctions.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "SSM_NEW");
            //Process the message
			MSG005.enterMsgType("SSM");
			MSG005.clickList();
			libr.waitForSync(3);
            map.put("pmkey", "NEW"+" - "+customfunctions.data("carrierCode")+" - "+customfunctions.data("FlightNo1")+" - "+customfunctions.data("SSMStartDate").toUpperCase()
					+" - "+customfunctions.data("SSMEndDate").toUpperCase()+" - "+"1234567");
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			customfunctions.closeTab("MSG005", "List Message");
			
			// Maintain Flight Screen (FLT005) . Taking fresh flight 2

			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo2", startDate, endDate,"FullFlightNumber");
			customfunctions.closeTab("FLT005", "Maintain Schedule");
			
			//Flight details
			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			map.put("FullFlighNum", WebFunctions.getPropertyValue(proppath, "flightNumber2"));
			map.put("FullFlighNo2", WebFunctions.getPropertyValue(proppath, "flightNumber2"));
			map.put("FlightNo2", FlightNum2.substring(2));
			map.put("Org", customfunctions.data("Transit"));
			map.put("Dest", customfunctions.data("Destination"));
			map.put("ATD", customfunctions.data("ATD_Local2"));
			map.put("ATA", customfunctions.data("ATA_Local2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/** MSG005 -SSM Message loading **/

			customfunctions.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "SSM_NEW");
            //Process the message
			MSG005.enterMsgType("SSM");
			MSG005.clickList();
			libr.waitForSync(3);
            map.put("pmkey", "NEW"+" - "+customfunctions.data("carrierCode")+" - "+customfunctions.data("FlightNo2")+" - "+customfunctions.data("SSMStartDate").toUpperCase()
					+" - "+customfunctions.data("SSMEndDate").toUpperCase()+" - "+"1234567");
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			customfunctions.closeTab("MSG005", "List Message");

            //Creating Fresh AWB 
            customfunctions.searchScreen("CAP018", "Maintain Booking");
			CAP018.checkAWBExists_CAP018("Maintain Booking", "CAP018","AWBNo");
		    //Writing the full AWB No
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("CarrierNumericCode") + "-" + customfunctions.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", customfunctions.data("prop~FullAWBNo"));
			map.put("AWBNo", customfunctions.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/** SHR003 - Maintain Aircraft Type --Take aircraft capacity **/
			
			customfunctions.searchScreen("SHR003", "Maintain Aircraft Type");
			SHR003.listAircraft(customfunctions.data("AircraftType"));
			List<String> capacityDetails=SHR003.getAircraftCapacityDetails();
			map.put("AircraftWgt", capacityDetails.get(0));
			map.put("AircraftVol", capacityDetails.get(1));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			customfunctions.closeTab("SHR003", "Maintain Aircraft Type");
			
	        /** CAP012 - Maintain Station Allotment for Flight 1**/
			
			customfunctions.searchScreen("CAP012", "Maintain Station Allotment");
			CAP012.enterStationCode("Origin");
			CAP012.enterFlightDetails("carrierCode", "FlightNo1", "Origin", "Transit");
			CAP012.enterAllotmentRange(nextDate,endDate);
			CAP012.enterFrequency();
			CAP012.enterCategory("val~AB");
			CAP012.enterCapacity("AllotmentWeight", "AllotmentVolume");
			CAP012.saveDetails("StationAllotmentID");
			System.out.println(customfunctions.data("StationAllotmentID"));
			customfunctions.closeTab("CAP012", "Maintain Station Allotment");

             /** CAP006 - Global Customer Allotment  for Flight 2**/
			
			customfunctions.searchScreen("CAP006", "Maintain Global Customer Allotment");
			CAP006.enterCustomerCode("AgentCode");
			CAP006.enterFlightDetails("carrierCode", "FlightNo2", "Transit", "Destination");
			CAP006.enterAllotmentRange(nextDate,endDate);
			CAP006.enterFrequency(); //selects ALL
			CAP006.enterCategory("val~AB");
			CAP006.enterAllotmentSubType("val~ACA");
			CAP006.enterShipmetType("val~Loose");
			CAP006.enterCapacity("AllotmentWeight2", "AllotmentVolume2");
			CAP006.saveDetails("CustomerAllotmentID");
			System.out.println(customfunctions.data("CustomerAllotmentID"));
			customfunctions.closeTab("CAP006", "Maintain Global Customer Allotment");
			
	        /** CAP018 - Maintain Booking**/
			
			customfunctions.searchScreen("CAP018", "Maintain Booking");
			CAP018.listAwb("prop~AWBNo");
			//Enter shipment details
			CAP018.enterShipmentDetails("Origin", "Destination", "SCC", "AgentCode", "ShipmentDate");
			//Enter shipment level details	
			String commoditycode[]={customfunctions.data("CommodityCode")};
			String pieces[]={customfunctions.data("Pieces")};
			String weight[]={customfunctions.data("Weight")};
			String volume[]={customfunctions.data("Volume")};	
			CAP018.enterShipmentLevelDetails("0",commoditycode[0], pieces[0], weight[0], volume[0]);
			
			//Enter flight level details
			String origin[]={"Origin","Transit"};
			String destination[]={"Transit","Destination"};
			String flightNo[]={"FullFlighNo1","FullFlighNo2"};
			String fltDate[]={"flightDate","flightDate"};
			String pcs[]={"Pieces1","Pieces2"};
			String wt[]={"Weight1","Weight2"};
			String vol[]={"Volume1","Volume2"};
			CAP018.enterFlightLevelDetails(2, origin, destination, flightNo, fltDate, pcs, wt, vol, true,"val~Confirm");
			CAP018.selectFlightRow("0");
			CAP018.selectStationAllotment(customfunctions.data("StationAllotmentID"));
			CAP018.selectFlightRow("1");
			CAP018.selectGlobalAllotment(customfunctions.data("CustomerAllotmentID"));
			CAP018.saveBookingDetails("Confirmed","FullFlighNo1","StationAllotmentID");
			CAP018.saveBookingDetails("Confirmed","FullFlighNo2","CustomerAllotmentID");
			customfunctions.closeTab("CAP018", "Maintain Booking");
			
			  /** CAP147 -Monitor Flights - Flight 1**/
			
			customfunctions.searchScreen("CAP147", "Monitor Flights");
			CAP147.listFlight("carrierCode", "FlightNo1", "flightDate");
			CAP147.clickViewCapacitySummary();
			CAP147.verifyDepletionDetailsAfterAllotmentBooking(customfunctions.data("AircraftWgt"),customfunctions.data("AircraftVol"), customfunctions.data("AllotmentWeight"),customfunctions.data("Weight1"),
			customfunctions.data("AllotmentVolume"), customfunctions.data("Volume1"));
			customfunctions.closeTab("CAP147", "Monitor Flights");
			
			/** CAP147 -Monitor Flights - Flight 2**/
			
			customfunctions.searchScreen("CAP147", "Monitor Flights");
			CAP147.listFlight("carrierCode", "FlightNo2", "flightDate");
			CAP147.clickViewCapacitySummary();
			CAP147.verifyDepletionDetailsAfterAllotmentBooking(customfunctions.data("AircraftWgt"),customfunctions.data("AircraftVol"), customfunctions.data("AllotmentWeight2"),customfunctions.data("Weight2"),
					customfunctions.data("AllotmentVolume2"), customfunctions.data("Volume2"));
			customfunctions.closeTab("CAP147", "Monitor Flights");
			
             /** CAP018 - Verify Booking Details**/
			
			customfunctions.searchScreen("CAP018", "Maintain Booking");
			CAP018.listAwb("prop~AWBNo");
			CAP018.verifyBkgStatus("val~Confirmed");
			CAP018.verifyOriginDest("Origin", "Destination");
			CAP018.verifyAgentCode("AgentCode");
			CAP018.VerifyUpdatedSCCValues("SCC");
			String origins[]={"Origin","Transit"};
			String destinations[]={"Transit","Destination"};
			String flightNos[]={"FullFlighNo1","FullFlighNo2"};
			String fltDates[]={"flightDate","flightDate"};
			String pcss[]={"Pieces1","Pieces2"};
			String wts[]={"Weight1","Weight2"};
			String vols[]={"Volume1","Volume2"};
			CAP018.verifyFlightLevelDetails(2, origins, destinations, flightNos, fltDates, pcss, wts, vols);
			customfunctions.closeTab("CAP018", "Maintain Booking");
			
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