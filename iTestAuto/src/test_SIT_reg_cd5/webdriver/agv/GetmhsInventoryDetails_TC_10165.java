package agv;

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
import screens.LocationSynchronisation_ADD020;
/****TC_03_Verify inventory is updated from AGV to iCargo for an ULD****/
public class GetmhsInventoryDetails_TC_10165 extends BaseSetup  {
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	
	public LocationSynchronisation_ADD020 ADD020;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "agv";


	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		ADD020=new LocationSynchronisation_ADD020(driver, excelreadwrite, xls_Read);



	}

	@DataProvider(name = "TC_10165")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_10165")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
      
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

            // Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");
			
			cust.searchScreen("ADD020", "LocationSynchronisation Screen");
			
			ADD020.clickSync();
			//extract the pallet having  mismatched AGV and Icargo  Location after Sync done
			String uldNo=ADD020.extractUld(cust.data("UldType")).trim();
			
			map.put("UldNum",uldNo);
			
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			ADD020.clickRelocation(cust.data("UldNum"));
			
			ADD020.completeRelocation(cust.data("Location"));	
			
			cust.closeTab("ADD020", "LocationSynchronisation Screen");
			
			//verify Icargo Location is updated after relocation
			cust.searchScreen("ADD020", "LocationSynchronisation Screen");
			
			ADD020.clickSync();
			
			ADD020.verifyLocationUpdated(cust.data("Location"),cust.data("UldNum"));
			
			cust.closeTab("ADD020", "LocationSynchronisation Screen");
			
			
        } catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
	}
}
