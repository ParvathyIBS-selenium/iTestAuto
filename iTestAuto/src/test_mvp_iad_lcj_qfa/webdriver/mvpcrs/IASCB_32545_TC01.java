package mvpcrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AutoBlockSetUp_OPR031;
import screens.CaptureAWB_OPR026;
import screens.CustomerCreditMaster_SHR110;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptanceHHT;
import screens.ListMessages_MSG005;
import screens.ShipperReturnNote_OPR040;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

public class IASCB_32545_TC01 extends BaseSetup {
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CustomerCreditMaster_SHR110 SHR011;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public CustomerCreditMaster_SHR110 SHR110;
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
		SHR011 = new CustomerCreditMaster_SHR110(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CSH007=new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		SHR110=new CustomerCreditMaster_SHR110(driver, excelreadwrite, xls_Read);
	}

	
	
	@DataProvider(name = "IASCB_32545_TC01")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}
	
	@Test(dataProvider="IASCB_32545_TC01")
	public void getTestSuite(Map<Object, Object> map) {
	try{
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

		
		// creating flight number
		
			cust.createFlight("FullFlightNumber");
		String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");			
		String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
		String FlightNum = cust.getPropertyValue(proppath, "flightNumber");
		map.put("FullFlightNo", FlightNum);
		map.put("FlightNo", FlightNum.substring(2));
		map.put("StartDate", startDate);
		map.put("EndDate", endDate);
		map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));			
		String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
		map.put("XFWBDate", flightdate1);
		map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
		map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
		map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
		System.out.println(FlightNum);
		excelRead.writeDataInExcel(map, path1, sheetName, testName);
		String issuedDate = cust.createDateFormat("dd-MMM-YYYY", 1, "DAY", "");
		//Switch role
		cust.switchRole("Origin", "val~AMS", "RoleGroup");
		
		/***MESSAGE - loading ASM  **/
		
		cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
		//Load ASM message
		cust.searchScreen("MSG005", "MSG005 - List Messages");
		MSG005.loadFromFile("All","ALL", "JMS", "", "Origin", "", "ASM_NEW");
		
		//Process ASM message
		
		MSG005.enterMsgType("ASM");
		MSG005.clickList();
		libr.waitForSync(6);
		map.put("pmkey", "NEW"+" - "+cust.data("carrierCode")+" - "+cust.data("FlightNo")+" - "+cust.data("FBLDate").toUpperCase());
		MSG005.clickCheckBox("pmkey");
		MSG005.clickprocess();
		cust.closeTab("MSG005", "List Message");
	
        
		/******MSG005-loading FBL****/


		//Checking AWB is fresh or Not
		cust.searchScreen("OPR026","Capture AWB");
		OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
		libr.waitForSync(1);
		cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
		
		map.put("FullFlightNo", FlightNum);
		map.put("Pcs", cust.data("Pieces"));
		map.put("Wgt", cust.data("Weight"));
		map.put("Vol", cust.data("Volume"));
		//Create the message FBL
		
		cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
		cust.searchScreen("MSG005", "MSG005 - List Messages");
		MSG005.loadFromFile("All","ALL", "JMS", "", "Origin", "", "FBL_Dimentions");

		//Process the message	

		MSG005.enterMsgType("FBL");
		MSG005.clickList();
		libr.waitForSync(6);


		map.put("pmkey", cust.data("carrierCode")+" - "+cust.data("FlightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
				+" - "+cust.data("Origin"));
		MSG005.clickCheckBox("pmkey");
		MSG005.clickprocess();
		cust.closeTab("MSG005", "List Message");
		
		/***MESSAGE - loading XFWB **/
		cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
		//Load FWB message
		cust.searchScreen("MSG005", "MSG005 - List Messages");
		MSG005.loadFromFile("All","ALL", "MQ-SERIES", "", "Origin", "", "XFWB_withDim",true);
		cust.closeTab("MSG005", "List Message");
		
		 /**** SHR110 - Customer Credit Master****/
		//Store Export guarantee details and credit balance details in map
        cust.searchScreen("SHR110","Customer Credit Master");
        SHR110.listCustomerCode("AgentCode");
        String balance = SHR110.getBalanceAvailable();
        map.put("BalanceAmountExport", balance);
        String guranteeAmount = SHR110.getExportGuaranteeAmount();
        map.put("ExportGuaranteeAmount", guranteeAmount);
        cust.closeTab("SHR110","Customer Credit Master");
        //Barter balance and Bank Transcation Balance has to be extracted
		/***** OPR026 - Execute AWB****/			
		//Execute AWB
		cust.searchScreen("OPR026","Capture AWB");
		OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
		OPR026.clickChargesAcc();
		OPR026.marketDetails("val~5","val~500");
		OPR026.checkVerifyAndExecute();
		OPR026.asIsExecuteOnly();
		
		
		//Generate Payment Advice Screen
		CSH007.selectPaymentMode("Cash");
		CSH007.enterRemarks("val~Cash Payment");
		CSH007.clickAdd();
		List <String> guarenteeDetails=new ArrayList<String>();
        guarenteeDetails.add("Credit Balance Export:" + map.get("BalanceAmountExport"));
        guarenteeDetails.add("Credit Balance Import:" + map.get("ExportGuaranteeAmount"));
        //Barter balance and Bank Transcation Balance has to be extracted and compared 
        guarenteeDetails.add("Barter Balance:");
        guarenteeDetails.add("Bank Transaction Balance:");
        CSH007.verifyGuarenteeAmount(guarenteeDetails);
		CSH007.clickFinalizePayment();
		CSH007.verifyPaymentStatus("Final");
		CSH007.clickClose();
		OPR026.asIsExecuteVP();
		cust.closeTab("OPR026", "Capture AWB");
		
		/**** SHR110 - Customer Credit Master****/
		//Verify Export guarantee details and credit balance details are unchanged
        cust.searchScreen("SHR110","Customer Credit Master");
        SHR110.listCustomerCode("AgentCode");
        SHR110.verifyBalanceAvailable("BalanceAmountExport");
        SHR110.verifyGuaranteeAmount("ExportGuaranteeAmount");
        cust.closeTab("SHR110","Customer Credit Master");
	}
	
	
	catch(Exception e)
	{
		libr.writeExtent("Fail", "Test case has failed steps");
		e.printStackTrace();
		Assert.assertFalse(true, "The test case has failed steps");
	}
	}
}
