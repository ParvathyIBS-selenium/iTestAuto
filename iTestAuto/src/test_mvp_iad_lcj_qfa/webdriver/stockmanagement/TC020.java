package stockmanagement;

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
import screens.ListStockHolders_STK006;
import screens.MaintainStockHolder_STK014;

public class TC020 extends BaseSetup {
	
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

	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="stockmanagement";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		//excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		
		STK014= new MaintainStockHolder_STK014(driver, excelreadwrite, xls_Read);
		STK006= new ListStockHolders_STK006(driver, excelreadwrite, xls_Read);
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
			
			/******* STK014 - MAINTAIN STATION STOCK HOLDER ******/

			cust.searchScreen("STK014", "STK014- Maintain Stock Holder");
			STK014.enterNewStockHolderDetails("val~Station", "StockHolderPrefix");
			map.put("StationStockHolderCode", cust.data("prop~stockHolderCode"));
			STK014.enterStockApprover("Approver");
			STK014.enterDocTypeAndSubType("DocType", "DocSubType");
			STK014.clickSave();
			cust.closeTab("STK014", "STK014- Maintain Stock Holder");

			/******* STK014 - MAINTAIN AGENT STOCK HOLDER ******/

			cust.searchScreen("STK014", "STK014- Maintain Stock Holder");
			STK014.enterNewStockHolderDetails("StockHolderType", "StockHolderPrefix");
			map.put("StockHolderCode", cust.data("prop~stockHolderCode"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			STK014.enterStockApprover("StationStockHolderCode");
			STK014.enterDocTypeAndSubType("DocType", "DocSubType");
			STK014.clickSave();
			cust.closeTab("STK014", "STK014- Maintain Stock Holder");

			/******* STK006 - LIST STOCK HOLDER ******/

			cust.searchScreen("STK006", "List Stock Holders");
			STK006.listStockHolderDetails("StockHolderType", "StockHolderCode", "DocType", "DocSubType");
			// verify stock holder details
			int[] verfCols = { 2, 3, 4, 5 };
			String[] actVerfValues = { cust.data("StockHolderType"), cust.data("StockHolderCode"), cust.data("DocType"),
					cust.data("DocSubType") };
			STK006.verifyStockHolderDetails(verfCols, actVerfValues, "StockHolderCode");
			cust.closeTab("STK006", "List Stock Holders");
						
		}	
		catch(Exception e)
		{
			
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		
		
	}
}

