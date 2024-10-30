package patriarch;
/** TC_05_Capture Payment Advice  from icargo during AWB as-is execution and verify in Patriarch system-for cash payment.  **/

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


public class PaymentAdvice_TC_3083 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public Patriarch patriarch;
	

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Documentation.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";

	String sheetName = "Documentation_SIT";

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

	@DataProvider(name = "TC_3083")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}


	@Test(dataProvider = "TC_3083")
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
			
			

		        //CASH
			patriarch.invokePicasso();
			patriarch.enterAWB("FullAWBNo");
			
			
			patriarch.submit();
			patriarch.verifyAwbRecords(8,"FullAWBNo");
		
			patriarch.verifyDocumentType("DOCCS","DOCCS","DOCCS","PAYD","XFWB_CUSTOMER", "XFWB_CUSTOMER_REMAT","XFWB_HANDLER","XFWB_HANDLER_REMAT");
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




