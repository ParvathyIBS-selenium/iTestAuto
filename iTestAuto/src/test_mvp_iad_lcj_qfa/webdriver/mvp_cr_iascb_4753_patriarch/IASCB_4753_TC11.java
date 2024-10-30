package mvp_cr_iascb_4753_patriarch;

import java.util.Map;

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
import screens.BreakdownHHT;
import screens.CaptureAWB_OPR026;
import screens.DeliverCargo_OPR064;
import screens.DeliveryDocumentation_OPR293;
import screens.DeliveryHHT;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.Patriarch;

/**
 * Verify Print Delivery Slip is triggered to Patriach System from iCargo web application for Partial Delivery 
 */

public class IASCB_4753_TC11 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public ImportManifest_OPR367 OPR367;
	public BreakdownHHT bdhht;
	public DeliveryDocumentation_OPR293 OPR293;
	public DeliverCargo_OPR064 OPR064;
	public CaptureAWB_OPR026 OPR026;
	public DeliveryHHT deliveryhht;
	public Patriarch patriarch;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "mvp_cr_iascb_4753";

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
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		bdhht=new BreakdownHHT(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR064 = new DeliverCargo_OPR064(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		deliveryhht = new DeliveryHHT(driver, excelreadwrite, xls_Read);
		patriarch = new Patriarch(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "IASCB_4753")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "IASCB_4753")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

	// Login to Patriarch
			
			String[] patr = libr.getApplicationParams("Patriarch");
			driver.get(patr[0]);
			Thread.sleep(2000);
			cust.loginPatriarch(patr[1], patr[2]);
			Thread.sleep(2000);
			
			patriarch.invokePicasso();
			patriarch.enterAWB("FullAWBNo");
			patriarch.submit();
			patriarch.verifyAwbRecords(1,"FullAWBNo");
			patriarch.verifyDocumentType("POD");
			patriarch.generateDocument(testName);
			


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}