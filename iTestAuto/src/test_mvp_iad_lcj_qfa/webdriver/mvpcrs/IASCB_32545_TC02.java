package mvpcrs;

import java.util.Map;

/**
 * Create Auto Block Set up with Release conditions for Found Cargo Discrepancy based on Irregularity Code with Close Flight Transaction
 */

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AWBClearance_OPR023;
import screens.AutoBlockSetUp_OPR031;
import screens.BreakdownHHT;
import screens.BuildUpHHT;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.CustomerCreditMaster_SHR110;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.FlightLoadPlan_OPR015;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.OffloadEnquiry_OPR011;
import screens.SecurityAndScreeningHHT;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;
//Guarantee availability info and info icon against the customer validation for an Import transaction
public class IASCB_32545_TC02 extends BaseSetup {
	
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
	public CustomerCreditMaster_SHR110 SHR110;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005;
	public BreakdownHHT bdhht;
	public GoodsAcceptanceHHT gahht;
	public BuildUpHHT buhht;
	public ExportManifest_OPR344 OPR344;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public DeliveryDocumentation_OPR293 OPR293;
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
		SHR110 = new CustomerCreditMaster_SHR110(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		bdhht = new BreakdownHHT(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
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
			
			//Switch station
			cust.switchRole("Origin", "Origin", "RoleGroup");
			
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
			
			/***MESSAGE - loading ASM**/
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			//Load ASM message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "ASM_NEW");
			cust.closeTab("MSG005", "List Message");
						
				
			/******MSG005-loading FBL****/

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);


			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);


			//Create the message FBL
			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_1");



			//Process the message
			
			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);

			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
           /******MSG005-loading XFWB****/
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			//Load FWB message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "MQ-SERIES", "", "Origin", "", "XFWB_1",true);
			cust.closeTab("MSG005", "List Message");
			
			
          
			/***** OPR026 - Execute AWB****/
			//Execute the AWB
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
          
			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);
			
			/*** HHT - LOOSE ACCEPTANCE****/
			
			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", cust.data("prop~stationCode")+cust.data("prop~AWBNo"));
			gahht.enterValue("awbNumber");
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "Location");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");
			
			/*** HHT - Build Up****/
	       
			buhht.invokeBuildUpScreen();
			String uldNum1=cust.create_uld_number("UldType", "carrierCode");
          	map.put("UldNum", uldNum1);
          	excelRead.writeDataInExcel(map, path1, sheetName, testName);
			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code", "prop~flightNo","currentDay");
			buhht.enterShipmentDetails("awbNumber","Pieces", "Weight");
			buhht.verifyBuildUpDetailsIfSaved();
			
			
           /*****OPR344 - Export manifest and finalize****/
         
		   cust.searchScreen("OPR344", "Export manifest");
           OPR344.listFlight("prop~flight_code", "prop~flightNo","StartDate");                      
           OPR344.manifestDetails();
           OPR344.finalizeFlight();
           cust.closeTab("OPR344", "Export Manifest"); 
          
        
			/*** SWITCH ROLE***/
         
			cust.switchRole("Destination", "Origin", "RoleGroup");
			
			/**Mark Flight Movement**/
			
			cust.searchScreen("FLT006", "Mark Flight Movements");
            FLT006.listFlightDetails("prop~flight_code","prop~flightNo", "StartDate");
            FLT006.clickFlightMovementArrivalDetailsLink();
            FLT006.clickFlightMovementDepartureDetailsLink();
            FLT006.clickSave();
            cust.closeTab("FLT006", "Mark Flight Movements");
			
            /*****OPR367 - Import Manifest*******/
            
            //Enter breakdown pieces and weight more than stated pieces and weight
            cust.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
            OPR367.maximizeAllDetails();
            String pmkey = Excel.getCellValue(path1,sheetName, "IASCB_32545_TC02", "UldNum");
            OPR367.clickCheckBox_ULD(pmkey);
            OPR367.clickBreakDownandBreakdownComplete("Location", "Pieces","Weight");
            OPR367.clickYesButton();  
            OPR367.closeTab("OPR367", "Import Manifest");
			
            /**** SHR110 - Customer Credit Master****/
			//Store Export guarantee details and credit balance details in map
            cust.searchScreen("SHR110","Customer Credit Master");
            SHR110.listCustomerCode("AgentCode");
            String balance = SHR110.getBalanceAvailableImport();
            map.put("BalanceAmountImport", balance);
            String guranteeAmount = SHR110.getImportGuaranteeAmount();
            map.put("ImportGuaranteeAmount", guranteeAmount);
            cust.closeTab("SHR110","Customer Credit Master");
            
            //Capture handover details and generate delivery id for flight1
			customfunctions.searchScreen("OPR293", "Delivery Documentation");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
			OPR293.selectAllAWBs();
			OPR293.enterCaptureHandOverDetails();
			OPR293.enterCustomer("AgentCode");
			OPR293.changeDNpcs_wt("Pieces","Weight");
			OPR293.generateDeliveryIDWithPopUps("Remarks");
			OPR293.verifyDNStatus("Paid");
			customfunctions.closeTab("OPR293", "Delivery Documentation");
			
			/**** SHR110 - Customer Credit Master****/
			//Verify Export guarantee details and credit balance details are unchanged
	        cust.searchScreen("SHR110","Customer Credit Master");
	        SHR110.listCustomerCode("AgentCode");
	        SHR110.verifyBalanceAvailableImport("BalanceAmountImport");
	        SHR110.verifyImportGuaranteeAmount("ImportGuaranteeAmount");
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


