package stockmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AllocateNewStock_STK001;
import screens.CreateStock_STK004;
import screens.ListStockHolders_STK006;
import screens.MaintainStockHolder_STK014;
import screens.MonitorStock_STK007;
import screens.MaintainBooking_CAP018;
import screens.ListMessages_MSG005;
import screens.CaptureAWB_OPR026;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

/** Processing FWB message with AWB number from agent stock.  **/

public class TC015 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListStockHolders_STK006 STK006;
	public MaintainStockHolder_STK014 STK014;
	public MonitorStock_STK007 STK007;
	public CreateStock_STK004 STK004;
	public AllocateNewStock_STK001 STK001;
	public MaintainBooking_CAP018 CAP018;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	
	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="stockmanagement";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		
		STK014= new MaintainStockHolder_STK014(driver, excelreadwrite, xls_Read);
		STK006= new ListStockHolders_STK006(driver, excelreadwrite, xls_Read);
		STK007= new MonitorStock_STK007(driver, excelreadwrite, xls_Read);
		STK004=new CreateStock_STK004(driver, excelreadwrite, xls_Read);
		STK001=new AllocateNewStock_STK001(driver, excelreadwrite, xls_Read);
		CAP018 = new MaintainBooking_CAP018(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC002")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC002")
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
			
			//setting stock range from and to
			String stockfromPropertyFile = getPropertyValue(proppath, "stock_range_from");
			//setting the required stock range by passing its value from property file
			int toValue = Integer.parseInt(stockfromPropertyFile)+2;
			String stockRange_to=Integer.toString(toValue);
			map.put("StockRange_from", cust.data("prop~stock_range_from"));
			map.put("StockRange_to", stockRange_to);
			
			/******* STK007 -MONITOR STOCK ******/
			
			//Creating stock for HQ
			cust.searchScreen("STK007","Monitor Stock");
			STK007.enterStockHolderDetails("val~H", "Approver", "DocType", "DocSubType");
			STK007.clickCreateStock();
			STK004.enterDocTypeDetails("DocType", "DocSubType");
			STK004.createStock("StockRange_from", "StockRange_to");
			cust.closeTab("STK007", "Monitor Stock");
			
			/******* STK001 -ALLOCATE NEW STOCK ******/
			
			//Allocate stocks to the station stock holder by HQ.
		    cust.searchScreen("STK001","Allocate New Stock");
			STK001.enterDocumentDetails("DocType", "DocSubType");
			STK001.enterStockHolderDetails("Approver", "StockHolderType2", "StationStockHolderCode");
			STK001.enterAvailableStock("prop~stock_range_from", "prop~stock_range_to");
			STK001.allocateStock("prop~stock_range_from");
			
           //Allocate stocks to the Agent stock holder by station.
		    STK001.enterStockHolderDetails("StationStockHolderCode", "StockHolderType", "StockHolderCode");
			STK001.enterAvailableStock("prop~stock_range_from", "prop~stock_range_to");
			STK001.allocateStock("prop~stock_range_from");
			cust.closeTab("STK001", "Allocate New Stock");
			
			
            /****** UPDATING XFWB CUSTOMER DETAILS IN MAP***/
			
			
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
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			
			
			
			//Creating Fresh AWB 

			cust.searchScreen("CAP018", "Maintain Booking");
			CAP018.checkAWBExists_CAP018("Maintain Booking", "CAP018","AWBNo");
			
			 //Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			map.put("CarrierNumericCode",cust.data("CarrierNumericCode"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			
			//Load FWB from MSG005
			cust.createTextMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "FWB_AWB1");
			cust.closeTab("MSG005", "MSG005 - List Messages");
			
			
			
			/***** OPR026 - Capture AWB****/
		
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.verifySource("val~FWB", true);
			List<String> MandatoryComponents=new ArrayList<String>();
			MandatoryComponents.add(cust.data("Origin"));
			MandatoryComponents.add(cust.data("Destination"));
			MandatoryComponents.add(cust.data("CarrierCode"));
			MandatoryComponents.add(cust.data("Destination"));
			MandatoryComponents.add(cust.data("AgentCode"));
			MandatoryComponents.add(cust.data("ShipperCode"));
			MandatoryComponents.add(cust.data("ConsigneeCode"));
			MandatoryComponents.add(cust.data("Pieces"));
			MandatoryComponents.add(cust.data("Weight"));
			MandatoryComponents.add(cust.data("CommodityCode"));
			System.out.println(cust.data("CommodityCode"));
			OPR026.verifyXFWBMandatoryComponents(MandatoryComponents);
			OPR026.verifySCI(cust.data("SCI"));
			cust.closeTab("OPR026", "Capture AWB");
			
			
			/*** MSG005-verify the handling details and Configuration Profile ***/
			 cust.searchScreen("MSG005", "MSG005 - List Messages");
             MSG005.enterMsgType("FWB");
             MSG005.selectStatus("ProcessedSuccessfully");
             MSG005.clickList();
             String pmKeyXFWB =cust.data("prop~FullAWBNo");
             MSG005.clickViewlogs(pmKeyXFWB);
             String[] MessageProfiles= { cust.data("MessageProfile").split(";")[0], cust.data("MessageProfile").split(";")[1] };
             MSG005.VerifyHandlingCode(MessageProfiles);
             MSG005.closeViewlogs();
             MSG005.closeTab("MSG005", "MSG005 - List Messages");
             
             
            /*** MSG005-Verify FMA message ***/
            cust.searchScreen("MSG005", "MSG005 - List Messages");
 			MSG005.enterMsgType("FMA");
 			MSG005.selectStatus("Sent");
 			MSG005.clickList();
 			String pmKeyFSU = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo")+ " - " + cust.data("Origin")+ " - " + cust.data("Destination");
 			int verfColsFSU[] = { 9 };
 			String[] actVerfValuesFSU = { "Sent" };
 			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU, "val~FMA", false);
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
