package importtocra;

/** Allocating stocks to the specified Agent by creating the required stock range in HQ **/
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
import screens.AllocateNewStock_STK001;
import screens.CreateStock_STK004;
import screens.MonitorStock_STK007;

public class Precondition extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public MonitorStock_STK007 STK007;
	public CreateStock_STK004 STK004;
	public AllocateNewStock_STK001 STK001;

	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="importtocra";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		STK007= new MonitorStock_STK007(driver, excelreadwrite, xls_Read);
		STK004=new CreateStock_STK004(driver, excelreadwrite, xls_Read);
		STK001=new AllocateNewStock_STK001(driver, excelreadwrite, xls_Read);
	}
	
	@DataProvider(name = "Precondition")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "Precondition")
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
			int toValue = Integer.parseInt(stockfromPropertyFile)+Integer.parseInt(cust.data("prop~requiredStock"));
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
		    STK001.enterStockHolderDetails("StationStockHolderCode", "StockHolderType1", "StockHolderCode");
			STK001.enterAvailableStock("prop~stock_range_from", "prop~stock_range_to");
			STK001.allocateStock("prop~stock_range_from");
			cust.closeTab("STK001", "Allocate New Stock");
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		
	}
}
	


