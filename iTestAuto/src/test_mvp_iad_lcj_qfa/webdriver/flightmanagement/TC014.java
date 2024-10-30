package flightmanagement;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;



/** Verify user able to capture ATA via MVT **/

public class TC014 extends BaseSetup {

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
	public ListMessages_MSG005 MSG005;
	public MarkFlightMovements_FLT006 FLT006;


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
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
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
			cust.loginICargo(iCargo[1], iCargo[2], iCargo[3]);
			Thread.sleep(2000);


			/******* FLT005 - MAINTAIN FLIGHT ******/

			// creating flight number
			cust.createFlight("FullFlightNumber");

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("ASMDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));


			// Maintain Flight Screen (FLT005) . Taking fresh flight

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("CarrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", WebFunctions.getPropertyValue(proppath, "flightNumber"));
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** MSG005 -ASM Message loading **/

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			//Load ASM message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "ASM_NEW");

			//Process ASM message
			MSG005.enterMsgType("ASM");
			MSG005.clickList();
			libr.waitForSync(6);
			map.put("pmkey", "NEW"+" - "+cust.data("CarrierCode")+" - "+cust.data("FlightNo")+" - "+cust.data("ASMDate").toUpperCase());
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");


			/*** MSG005-- MVT AA loading ****/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "MVT_ATA");

			//Process MVT message
			MSG005.enterMsgType("MVT");
			MSG005.clickList();
			libr.waitForSync(6);
			map.put("pmkey", cust.data("CarrierCode")+" - "+cust.data("FlightNo")+" - "+cust.data("Day")+" - "+cust.data("Destination"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();

			cust.closeTab("MSG005", "List Message");


			/****FLT006 - Mark Flight Movements*****/
			//verifying flight arrival date and time

			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("CarrierCode","FlightNo", "StartDate");
			FLT006.verifyFlightMovementArrivalDetails("20:49",cust.data("StartDate"));
			cust.closeTab("FLT006", "Mark Flight Movements");

		}	
		catch(Exception e)
		{

			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}


	}
}

