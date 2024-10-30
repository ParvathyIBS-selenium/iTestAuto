package awbdatacapture;

import java.awt.AWTException;
import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import postconditions.CancelFlights;
import screens.AWBHAWBHistory_OPR276;
import screens.CaptureAWB_OPR026;
import screens.DeliverCargo_OPR064;
import screens.DeliverNoteEnquiry_OPR034;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListAuditEnquiry_SHR011;
import screens.ListMessages_MSG005;
import screens.MaintainAirportScreen_SHR006;
import screens.MaintainAndListSystemParameters_SHR048;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

public class CA13 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions customfunctions;
	public CaptureAWB_OPR026 OPR026;
	public MaintainAirportScreen_SHR006 SHR006;
	public ListAuditEnquiry_SHR011 SHR011;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public AWBHAWBHistory_OPR276 OPR276;
	public ListMessages_MSG005 MSG005;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="awbdatacapture";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		//excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		customfunctions = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		SHR006= new MaintainAirportScreen_SHR006(driver, excelreadwrite, xls_Read);
		SHR011 = new ListAuditEnquiry_SHR011(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR276 = new AWBHAWBHistory_OPR276(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) throws Exception {
		
		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		
			//Login to iCargo
		
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			customfunctions.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
			customfunctions.switchRole("Origin", "Origin", "RoleGroup");
			
			/*******Pre-Condition***********/
			customfunctions.searchScreen("SHR006","Maintain Airport Screen");
			SHR006.listAirport("Origin");
			SHR006.filterParameterBasedOnvalue("parameterValue");
			SHR006.changeParameterValuetoY();
			customfunctions.closeTab("SHR006", "Maintain Airport Screen");
			
	        /**** OPR026 - Capture AWB****/
	        
			//Checking AWB is fresh or Not
	        customfunctions.searchScreen("OPR026","Capture AWB");
		    OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
		    libr.waitForSync(1);
		       
		    String awbNo = customfunctions.data("prop~AWBNo");
		    map.put("AWBNo",awbNo);
		    excelRead.writeDataInExcel(map, path1, sheetName, testName);
		       
		    //Writing the full AWB No to property file
	        customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
	        
	        //Enter details in Capture AWB screen and verify
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","prop~flight_code");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.saveAWB(); 
			
			//Modify the data(pieces)

			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.enterShipmentDetails("Pieces2", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.saveAWB();
			
			//As Is Execute AWB
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asisExecuteButton();
			
			//Generate Payment Advice Screen
			
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("Remarks");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			CSH007.clickClose();
			OPR026.clickYesButton();
			customfunctions.closeTab("OPR026", "Capture AWB");
			
			/********SHR011 - List Audit Enquiry screen***********/
			
	        customfunctions.searchScreen("SHR011", "List Audit Enquiry");
	        SHR011.selectModuleName("Operations");
	        SHR011.selectSubModuleName("AWB");
	        SHR011.enterFromDate(".");
	        SHR011.enterAwbNumber("CarrierNumericCode","AWBNo");
	        SHR011.listDetails();
	        int[] cols={1};
	        String[] values={"Shipment Created"};
	        SHR011.verifyTransactionDetailsValue(cols, values, "Shipment created");
	        String[] values1={"Shipment Updated"};
	        SHR011.verifyTransactionDetailsValue(cols, values1, "Shipment Updated");
	        String[] values2={"Shipment Executed"};
	        SHR011.verifyTransactionDetailsValue(cols, values2, "Shipment Executed");
	        String[] values3={"Payment Advice Generated"};
	        SHR011.verifyTransactionDetailsValue(cols, values3, "Payment Advice Generated"); 
			
	        //Verify Additional details - User, Date, Station
	        int[] cols1={2,3,5};
	        String date1 = customfunctions.createDateFormat("dd-MMM-yyyy", 0, "DAY", "FlightDate");
	        String username = customfunctions.data("prop~iCargoUN");
	        String[] values4={username,date1,customfunctions.data("Origin")};
	        SHR011.verifyTransactionDetailsValue(cols1, values4, "Payment Advice Generated");
	        customfunctions.closeTab("SHR011", "List Audit Enquiry");
	        
	        /*******OPR276 - AWB HAWB History****/
	        //Verify updated pieces and weight values
	        customfunctions.searchScreen("OPR276","AWB HAWB History");
	        OPR276.listAWB("AWBNo", "CarrierNumericCode", "AWB HAWB History");
	        int cols2[]={9,10};
	        String[] values5={"11","100"};
	        OPR276.verifyDetailsWithSameVersion(cols2, values5, "4", 2);
	        customfunctions.closeTab("OPR276", "AWB HAWB History");
	        
	        /*******Verify FWB message in MSG005******/
            customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.clickClearButton();
            MSG005.enterMsgType("FWB");
            MSG005.clickReference();
            MSG005.enterReferenceValue("FWB", "FlightNo", "AWBNo");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
            MSG005.verifyMessageTriggered("prop~AWBNo", "FWB");
            libr.waitForSync(6); 
            MSG005.closeTab("MSG005", "MSG005 - List Messages");

		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
		}
		finally
		{
			
			/*******Post-Condition***********/
			customfunctions.closeTab();          
	        		customfunctions.searchScreen("SHR006","Maintain Airport Screen");
			SHR006.listAirport("Origin");
			SHR006.filterParameterBasedOnvalue("parameterValue");
			SHR006.changeParameterValuetoN();
			customfunctions.closeTab("SHR006", "Maintain Airport Screen");
		}

	}
	
}

