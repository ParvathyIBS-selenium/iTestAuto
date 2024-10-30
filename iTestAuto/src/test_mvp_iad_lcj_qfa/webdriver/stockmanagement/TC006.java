package stockmanagement;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AllocateNewStock_STK001;
import screens.CreateStock_STK004;
import screens.MaintainStockHolder_STK014;
import screens.MonitorStock_STK007;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

/** Allocate stocks to the station stock holder by HQ.  **/

public class TC006 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public MaintainStockHolder_STK014 STK014;
	public MonitorStock_STK007 STK007;
	public CreateStock_STK004 STK004;
	public AllocateNewStock_STK001 STK001;;

	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
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
		STK007= new MonitorStock_STK007(driver, excelreadwrite, xls_Read);
		STK004=new CreateStock_STK004(driver, excelreadwrite, xls_Read);
		STK001=new AllocateNewStock_STK001(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC006")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC006")
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
			
			//Creating station level stock holder with HQ as approver
			cust.searchScreen("STK014","Maintain StockHolder");
			STK014.enterNewStockHolderDetails("StockHolderType", "StockHolderPrefix");
			STK014.enterStockApprover("Approver");
			STK014.clickSave();
			cust.closeTab("STK014", "Maintain StockHolder");
			
			//setting stock range from and to
			String stockfromPropertyFile = getPropertyValue(proppath, "stock_range_from");
			int toValue = Integer.parseInt(stockfromPropertyFile) + 1;
			String stockRange_to=Integer.toString(toValue);

			map.put("StockRange_from", cust.data("prop~stock_range_from"));
			map.put("StockRange_to", stockRange_to);
			
			map.put("StockHolderCode", cust.data("prop~stockHolderCode"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			//Creating stock for HQ
			cust.searchScreen("STK007","Monitor Stock");
			STK007.enterStockHolderDetails("val~H", "Approver", "DocType", "DocSubType");
			STK007.clickCheckBox("val~HEADQUATERS");
			STK007.clickCreateStock();
			STK004.enterDocTypeDetails("DocType", "DocSubType");
			STK004.createStock("StockRange_from", "StockRange_to");
			cust.closeTab("STK007", "Monitor Stock");
			
			//Allocate stocks to the station stock holder by HQ.
			cust.searchScreen("STK001","Allocate New Stock");
			STK001.enterDocumentDetails("DocType", "DocSubType");
			STK001.enterStockHolderDetails("Approver", "StockHolderType", "StockHolderCode");
			STK001.enterAvailableStock("prop~stock_range_from", "prop~stock_range_to");
			STK001.allocateStock("prop~stock_range_from");
			cust.closeTab("STK001", "Allocate New Stock");
			
			//verifying stock holder details and allocated stock range
			cust.searchScreen("STK007","Monitor Stock");
			STK007.enterStockHolderDetails("val~S", "StockHolderCode", "DocType", "DocSubType");
			int verfCols[] = { 4,5};
			String actVerfValues[] = { cust.data("StockHolderCode"),"2" };
			STK007.verifyStockHolderDetails("StockHolderCode", "//td", verfCols, actVerfValues);
			STK007.clickCheckBox("StockHolderCode");
			STK007.clickViewRange();
			
			int val = Integer.parseInt(getPropertyValue(proppath, "stock_range_to"));
			int modValue = val % 7;
			String stockTo = Integer.toString(val) + Integer.toString(modValue);
			
			int verfCols1[] = { 3,4 };
			String actVerfValues1[] = {stockTo,"2" };
			STK007.verifyViewRange("prop~stock_range_from", "//td", verfCols1, actVerfValues1);
			cust.closeTab("STK007", "Monitor Stock");
			
		
	        
		}	
		catch(Exception e)
		{
			
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		finally
		{
			//setting the stock range from as stock range from + 2
			String stockfrom = getPropertyValue(proppath, "stock_range_from");
			int newValue = Integer.parseInt(stockfrom) + 2;
			String Stock_range_from=Integer.toString(newValue);
			String Stock_range_to=Integer.toString(newValue+10);
			
			cust.setPropertyValue("stock_range_from",Stock_range_from,proppath);
			cust.setPropertyValue("stock_range_to",Stock_range_to,proppath);
			
		
		}
		
		
	}
}

