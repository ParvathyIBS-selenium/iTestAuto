package goodsacceptance;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.GoodsAcceptance_OPR335;

import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;


import com.relevantcodes.extentreports.LogStatus;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class GA22 extends BaseSetup {

	
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public MaintainFlightSchedule_FLT005 FLT005;
	public SecurityAndScreening_OPR339 OPR339;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005; 
	
	public GoodsAcceptance_OPR335 OPR335;
	public String proppath = "\\src\\resources\\GlobalVariable.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	String sheetName = "goodsacceptance";
	             

	@BeforeClass
	public void setup() {
		testName = getTestName();
		excel = new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR339= new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		
		cust.setPropertyValue("isClubbedTC", "Yes", globalVarPath); 
		
	}

	@DataProvider(name = "GA20")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;
	}

	@Test(dataProvider = "GA20")
	public void getTestSuite(Map<Object, Object> map) throws Exception {

		libr.map = map;
		libr.setExtentTestInstance(test);

		String className = this.getClass().getSimpleName();
		 //Map writeMap=new HashMap();
		System.out.println("className" + className);
		try {

			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			System.out.println("The Class Name is:" + this.getClass().getName());

			// Login to "ICARGO"
			String[] iCargo = libr.getApplicationParams("iCargo");
			driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);
            
			
		
			
		
			//Checking AWB is fresh or Not
			 cust.searchScreen("OPR026","Capture AWB");
            OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
            libr.waitForSync(1);
            
            
            //Writing the full AWB No
            cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
          
			
			
			
			
			
			/****OPR355 - Goods Acceptance****/
			
			//Goods acceptance : Capture AWB details

            cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyIfAwbIsNew();
			OPR335.addAWBDetails("Destination", "ShipmentDesc", "Pieces", "Weight", "CommodityCode");
			OPR335.addNewLooseAcceptance("Location","Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			//Goods acceptance-Verify ready for not carriage status is stamped

            cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			
			
			
			
				} 
		
		catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
			   
			   
		}
	}
}



