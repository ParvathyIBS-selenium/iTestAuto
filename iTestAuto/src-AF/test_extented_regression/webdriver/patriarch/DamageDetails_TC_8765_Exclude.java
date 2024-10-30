package patriarch;

/**   TC_03_Capture Damage for same AWB multiple times and verify multiple documents in Patriarch system.  **/

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.Patriarch;

public class DamageDetails_TC_8765_Exclude extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public Patriarch patriarch;
	
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Acceptance.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "Acceptance_SIT";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);	   
		patriarch = new Patriarch(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_8765")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}


	@Test(dataProvider = "TC_8765")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
			
			// Login to patriarch
			String[] patr = libr.getApplicationParams("Patriarch");
			driver.get(patr[0]);
			Thread.sleep(2000);
			cust.loginPatriarch(patr[1], patr[2]);
			Thread.sleep(2000);

		

			patriarch.invokePicasso();
			patriarch.enterAWB("FullAWBNo");
			patriarch.submit();
			
			patriarch.verifyAwbRecords(14,"FullAWBNo");
			patriarch.verifyDocumentType("ACCEPTR","XFWB_CUSTOMER","XFWB_CUSTOMER_REMAT","ACCEPTR","DOCCS","DOCCS","DOCCS","DOCCS","ACCCS","XFWB_HANDLER", "XFWB_HANDLER_REMAT","CDR","CDR","PAYD");
			patriarch.generateDocument(testName);

		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}

}

