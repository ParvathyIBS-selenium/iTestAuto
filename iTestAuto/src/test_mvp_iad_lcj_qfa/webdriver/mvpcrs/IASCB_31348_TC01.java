package mvpcrs;

import java.util.Map;

/**
 * Create Auto Block Set up with Release conditions for Found Cargo Discrepancy based on Irregularity Code with Close Flight Transaction
 */

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AutoBlockSetUp_OPR031;
import screens.CaptureAWB_OPR026;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreeningHHT;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class IASCB_31348_TC01 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public AutoBlockSetUp_OPR031 OPR031;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="mvpcrs";	
	
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
		OPR031 = new AutoBlockSetUp_OPR031(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "IASCB_31348_TC23")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "IASCB_31348_TC23")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// Login to "ICARGO"
			String[] iCargo = libr.getApplicationParams("iCargo");
			driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);

			cust.switchRole("Origin", "Origin", "RoleGroup");
			
			/**** OPR031 - Auto Block Set Up****/
			//Create Auto block setup for found cargo discrepancy with irregularity code as Close flight
            cust.searchScreen("OPR031","Auto Block Set Up");
            OPR031.listByBlockType("BlockType");
            //Add blocking details
            OPR031.clickAdd();
            //Select block type
            OPR031.selectBlockType("BlockType");
            //Select blocking transaction
            OPR031.selectTransaction("BlockingTransaction");
            //Click Add paramters link
            OPR031.addParamters();
            OPR031.selectBlockingParamter("IrregularityCode", "TransactionCode");
            //Select release transaction
            OPR031.selectReleaseTransaction("ReleaseTransaction");
            //Click Save
            OPR031.clickSave();
            cust.closeTab("OPR031","Auto Block Set Up");

            /**** OPR031 - Auto Block Set Up****/
			//Verify the blocking details
            cust.searchScreen("OPR031","Auto Block Set Up");
            OPR031.listByBlockType("BlockType");
			int [] colVal={4};
			int [] colVal2={3,6};
			String[] actVal={"Irregularity Code : FDCA ( I )"};
			String[] actVal2={"Close Flight","Screening Save"};
			OPR031.verifyAutoBlockdetails("2","Irregularity",colVal,colVal2,actVal,actVal2);
			cust.closeTab("OPR031","Auto Block Set Up");
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}

	}
}


