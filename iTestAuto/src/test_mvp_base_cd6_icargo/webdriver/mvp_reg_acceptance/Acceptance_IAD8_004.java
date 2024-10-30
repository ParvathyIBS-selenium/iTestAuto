package mvp_reg_acceptance;

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
import screens.AWBClearance_OPR023;
import screens.CaptureAWB_OPR026;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.SecurityAndScreening_OPR339;


/**
 * "Acceptance of MAWB and HAWB with all parts received, AWB data capture not done with paper FWB and FHL

**/
public class Acceptance_IAD8_004 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public GoodsAcceptance_OPR335 OPR335;
	public ListMessages_MSG005 MSG005;
	public AWBClearance_OPR023 OPR023;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="mvp_reg_acceptance";	
	
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
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
	
	}	
	
	@DataProvider(name = "Acceptance_IAD1")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "Acceptance_IAD1")
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
			
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/***Storing Values to Map***/		
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR"));
			
			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");
						
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyIfAwbIsNew();
			
			//Adding AWB details	
			OPR335.addAWBDetails("Destination", "ShipmentDesc", "Pieces", "Weight", "Volume", "CommodityCode","ShipperCode");
			
			//Adding loose acceptance details
			OPR335.looseAcceptanceDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
				
			//adding HAWB details
			OPR335.addHAWBdetailsGoodsAcceptance("HAWB", "Pieces","Weight");
			
			OPR335.allPartsRecieved();
			OPR335.saveAcceptanceWithBlockExists("Restricted due to Compliance");
			cust.switchToFrame("contentFrame","OPR335");
			
			
			
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			/*******Verify xFSU-FOH message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.enterMsgType("XFSU");
            MSG005.selectMsgSubType("Freight On Hand");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
            String pmKeyFSU=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
            int verfColsFSU[]={9};
            String[] actVerfValuesFSU={"Sent"};
            MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU-FOH",false);
            libr.waitForSync(1);
            MSG005.closeTab("MSG005", "MSG005 - List Messages");
             
            /*******Verify XFSU-RCS is not sent message in MSG005******/
        	cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.enterMsgType("XFSU");
            MSG005.selectMsgSubType("Acceptance");
            MSG005.clickReference();
            MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
            MSG005.verifyNoMsgTriggered("MSG005","XFSU-RCS","AWBNo");
            libr.waitForSync(1);
            MSG005.closeTab("MSG005", "MSG005 - List Messages");
              

		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

