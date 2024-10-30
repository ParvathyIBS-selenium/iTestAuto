package bookingandreservation;

/**
 * Capture a booking with Multiple the Commodity code.
 * 
 * **/

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

public class TC055 extends BaseSetup {
	
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
			
			map.put("ShipmentDate", customfunctions.createDateFormat("dd-MMM-YYYY",1,"DAY",""));
		
			//Login to iCargo
		
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			customfunctions.loginICargo(iCargo[1], iCargo[2],iCargo[3]);
			Thread.sleep(2000);
			
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
			CAP018.enterShipmentDetails("Origin", "Destination", "SCC", "AgentCode", "ShipmentDate");
			//enter product code
			CAP018.enterProductCode("ProductCode");
			//Enter shipment level details
			String commoditycode[]={customfunctions.data("CommodityCode"),customfunctions.data("CommodityCode1")};
			String pieces[]={customfunctions.data("Pieces"),customfunctions.data("Pieces")};
			String weight[]={customfunctions.data("Weight"),customfunctions.data("Weight")};
			String volume[]={customfunctions.data("Volume"),customfunctions.data("Volume")};
			CAP018.enterShipmentLevelDetails("0",commoditycode[0], pieces[0], weight[0], volume[0]);
			CAP018.clickAddShipment();
			CAP018.enterShipmentLevelDetails("1",commoditycode[1], pieces[1], weight[1], volume[1]);
			
			//Select flight from popup
			CAP018.selectFlight();
			CAP018.selectFlightfromPopup("Origin", "Destination", "ShipmentDate", "Aircraft", customfunctions.data("prop~flight_code"),"1",false);
			
			//CAP018.enterFlightShipmentDetails(1, pcs, wt, vol, true,"val~Confirm");
			CAP018.saveBookingDetails("Confirmed");
			CAP018.getFlightDetails(1,"Origin","Destination","FullFlightNo","flightDate","Pieces","Weight","Volume");
			customfunctions.closeTab("CAP018", "Maintain Booking");
		
            /** Verify Booking Details on CAP018**/
			
			customfunctions.searchScreen("CAP018", "Maintain Booking");
			CAP018.listAwb("prop~AWBNo");
			CAP018.verifyBkgStatus("val~Confirmed");
		    CAP018.verifyOriginDest("Origin", "Destination");
			CAP018.verifyAgentCode("AgentCode");
			String pcs[]={"Pieces"};
			String wt[]={"Weight"};
			String vol[]={"Volume"};
			String origin[]={"Origin"};
			String destination[]={"Destination"};
			String flightNo[]={"FullFlightNo"};
			String fltDate[]={"flightDate"};
			
			CAP018.verifyFlightLevelDetails(1, origin, destination, flightNo, fltDate, pcs, wt, vol);
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

