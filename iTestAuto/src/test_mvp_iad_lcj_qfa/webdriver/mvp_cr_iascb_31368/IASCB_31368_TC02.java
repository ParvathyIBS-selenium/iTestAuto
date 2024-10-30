package mvp_cr_iascb_31368;

import java.util.Map;

import org.testng.Assert;
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
import screens.AutoBlockSetUp_OPR031;
import screens.TransactionBlockSetUp_OPR041;

/**Auto Block Set up verification for Damage Captured discrepancy with Damage code as Others**/

public class IASCB_31368_TC02 extends BaseSetup {
	
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
	public TransactionBlockSetUp_OPR041 OPR041;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="mvp_cr_iascb_31368";	
	
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
		OPR041=new TransactionBlockSetUp_OPR041(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "IASCB_31368")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}
	

	@Test(dataProvider = "IASCB_31368")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			WebFunctions.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			//Login to iCargo
			
			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	
		
			// Switch Role
			cust.switchRole("val~QFA", "FCTL", "RoleGroup");
			
            /**** OPR031 - Auto Block Set Up****/
			//Verify the blocking details
            cust.searchScreen("OPR031","Auto Block Set Up");
            OPR031.selectTransaction("BlockedTransaction");
            OPR031.listByBlockType("BlockType");
			int [] colVal={4};
			int [] colVal2={3,6};
			String[] actVal={"Damage Code : "+cust.data("DmgCode")+" ( I )"};
			String[] actVal2={cust.data("BlockedTransaction"),"Screening Save"};
			OPR031.verifyAutoBlockdetails("2",cust.data("BlockType"),colVal,colVal2,actVal,actVal2);
			cust.closeTab("OPR031","Auto Block Set Up");	
			
			 /**** OPR041 - Transaction Block Set Up****/
			cust.searchScreen("OPR041","Transaction Block Set Up");
			OPR041.selectBlockTypeAndTransaction("BlockType", "val~Build Up");
			OPR041.clickList();
			int [] colVal4={3,4};
			String[] actVal4={"Build Up","Error"};
			int [] colVal3={};
			String[] actVal3={};
			OPR041.verifyTransactionBlockdetails("2",cust.data("BlockType"),colVal3,colVal4,actVal3,actVal4);
			cust.closeTab("OPR041","Auto Block Set Up");
			
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
	
}


