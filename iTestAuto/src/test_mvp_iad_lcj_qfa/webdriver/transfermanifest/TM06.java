package transfermanifest;

import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import postconditions.CancelFlights;
import screens.CTMEnquiry_OPR003;
import screens.CTM_OPR002;
import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListAuditEnquiry_SHR011;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
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

public class TM06 extends BaseSetup {
	
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
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainOperationalFlight_FLT003 FLT003;
	public ListMessages_MSG005 MSG005;
	public SecurityAndScreening_OPR339 OPR339;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public ExportManifest_OPR344 OPR344;
	public CTM_OPR002 OPR002;
	public CTMEnquiry_OPR003 OPR003;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ListAuditEnquiry_SHR011 SHR011;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="transfermanifest";	
	
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
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367= new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR002=new CTM_OPR002(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		SHR011 = new ListAuditEnquiry_SHR011(driver, excelreadwrite, xls_Read);
		OPR003 = new CTMEnquiry_OPR003(driver, excelreadwrite, xls_Read);
		customfunctions.setPropertyValue("module", sheetName, globalVarPath);
		customfunctions.setPropertyValue("showStopper", "false", globalVarPath);
		customfunctions.setPropertyValue("isTcFailed", "false", globalVarPath);
		customfunctions.setPropertyValue("executionType", "Regression", globalVarPath);

	}
	
	
	
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) {
		
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
			
		
			//** Flight Creation 1 OAL carrier**/
			
			
			customfunctions.createFlight("FullFlightNumber");
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("carrierCode")+customfunctions.data("prop~flightNo"), proppath);
			String startDate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = customfunctions.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = customfunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", customfunctions.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
			map.put("Day2", customfunctions.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", customfunctions.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum);

			
			customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			 FLT005.listFlight("carrierCode", "FlightNo", startDate, endDate);
			 customfunctions.handleAlert("Accept", "FLT005");
			// Entering flight schedule data
			FLT005.enterFlightDetails("Route", "scheduleType2", "FCTL", "Office", "flightType");
			FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();
			customfunctions.closeTab("FLT005", "Maintain Schedule");
			
			// Writing value to property file
			customfunctions.setPropertyValue("flightNumber2", customfunctions.data("CarrierCode2")+customfunctions.data("prop~flightNo2"), proppath);
			
			
			// Flight Creation F2 OWN carrier
			
			customfunctions.createFlight("FullFlightNumber2");
			String FullFlightNo2 = customfunctions.getPropertyValue(proppath, "flightNumber2");
			map.put("FullFlightNo2", FullFlightNo2);
			map.put("FlightNo2", FullFlightNo2.substring(2));
			System.out.println(FullFlightNo2);
	        excelRead.writeDataInExcel(map, path1, sheetName, testName);       
	        
	        customfunctions.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
	        FLT005.listFlight("CarrierCode2", "FlightNo2", startDate, endDate);
	        customfunctions.handleAlert("Accept", "FLT005");
			// Entering flight schedule data
			FLT005.enterFlightDetails("Route2", "scheduleType", "FCTL", "Office", "flightType");
			FLT005.enterLegCapacityDetails("departureTime1", "arrivalTime1", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();
			customfunctions.closeTab("FLT005", "Maintain Schedule");
			
			//Checking AWB is fresh or Not
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo = WebFunctions.getPropertyValue(proppath,"AWBNo");
			map.put("AWBNo", AWBNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			
			
            //Writing the full AWB No to property file
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("CarrierNumericCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
                  
            
			//** Booking creation - FBL message
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_1");        
           
           	MSG005.enterMsgType("FBL");
            MSG005.clickList();
            libr.waitForSync(6);      
            map.put("pmkey", customfunctions.data("carrierCode")+" - "+customfunctions.data("FlightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
                              +" - "+customfunctions.data("Origin"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickprocess();
            customfunctions.closeTab("MSG005", "List Message");			
			
            //** Security and screening done for S1
            
			customfunctions.searchScreen("OPR339", "OPR339 - Security & Sceening");
            OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
            OPR339.clickYesButton();
            OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
            OPR339.saveSecurityDetails();
            customfunctions.closeTab("OPR339", "Security & Sceening");	
			
                       
            //** Load FWB message for data capture
            
            customfunctions.createTextMessage("MessageExcelAndSheet2", "MessageParam2");
			//Load FWB message
            customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FWB_multileg");
			MSG005.enterMsgType("FWB");
			MSG005.clickList();
			String pmKeyFWB=customfunctions.data("CarrierNumericCode")+" - "+customfunctions.data("prop~AWBNo")+" - "+customfunctions.data("Origin")+" - "+customfunctions.data("Destination");
            int verfColsFWB[]={9};
            String[] actVerfValuesFWB={"Processed Successfully"};
            MSG005.verifyMessageDetails(verfColsFWB, actVerfValuesFWB, pmKeyFWB);
			customfunctions.closeTab("MSG005", "List Message"); 			

			//** Load FSU-RCT message and verify status
			
            customfunctions.createTextMessage("MessageExcelAndSheetFSU", "MessageParamFSU");
            customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FSU_RCT");
			
			//Process the message and verify the status
			
			MSG005.enterMsgType("FSU");
			MSG005.selectMsgSubType("Inbound CTM");			
			MSG005.clickList();
			libr.waitForSync(6);
			String pmKeyFSU=customfunctions.data("CarrierNumericCode")+" - "+customfunctions.data("prop~AWBNo");
            int verfColsFSU[]={9};
            String[] actVerfValuesFSU={"Processed Successfully"};
            MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU);						
			customfunctions.closeTab("MSG005", "List Message");
			

			/**Switch role to Transit **/
			customfunctions.switchRole("Transit", "Transit", "RoleGroup");
			
			// Verify Transaction details in List audit enquiry//
			
			customfunctions.searchScreen("SHR011", "List Audit Enquiry");
            SHR011.selectModuleName("Operations");
            SHR011.selectSubModuleName("AWB");
            SHR011.enterFromDate(".");
            SHR011.enterAwbNumber("CarrierNumericCode","AWBNo");
            SHR011.listDetails();
            int[] cols={1};
            String[] values={"FSU-RCT Received"};
            SHR011.verifyTransactionDetailsValue(cols, values, "FSU-RCT Received");
            customfunctions.closeTab("SHR011", "List Audit Enquiry");

			
			//***** OPR026 - Execute AWB****//*
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			customfunctions.closeTab("OPR026", "Capture AWB");
			
			//****OPR355 - Goods Acceptance - Verify transhipment acceptance, Auto Finalization****/            
          
			customfunctions.searchScreen("OPR335", "Goods Acceptance");
			customfunctions.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyTranshipment();
            OPR335.verifyautoAcceptanceRCT("finalized");
            customfunctions.closeTab("OPR335", "Goods Acceptance");
			
            /**CTM Screen OPR002**/
			
			  String CTMRefNo= "CTM"+ customfunctions.data("AWBNo") + "001";	
            
            customfunctions.searchScreen("OPR002", "CTM");
            OPR002.selectinboundCTM(CTMRefNo);
            OPR002.enterOutGoingCarrierCode("CarrierCode2");
            OPR002.enterFromFlightDetails("carrierCode","FlightNo","StartDate");
            map.put("pmyKey",customfunctions.data("prop~AWBNo"));
            OPR002.addShipment("CarrierNumericCode", "AWBNo", "Pieces", "Weight", "val~Kilogram");
            OPR002.clickSave();
            customfunctions.closeTab("OPR002", "CTM");			
			
			//**Relist CTM002 with CTM Reference Number and Do transfer END**//
            
            customfunctions.searchScreen("OPR002", "CTM");
            OPR002.ListWithCTMREFno(CTMRefNo);
            String pmKeyAWBNo=customfunctions.data("CarrierNumericCode")+" "+customfunctions.data("prop~AWBNo");
		 	int verfCols[]={7};
			String[] actVerfValuesTransfferedPcs={"0"};
		 	OPR002.verifyTableDetails(verfCols, actVerfValuesTransfferedPcs, pmKeyAWBNo);
            customfunctions.closeTab("OPR002", "CTM");
            
            /********** CTM Enquiry Screen OPR003 - navigate to CTM screen*********/
			 
            customfunctions.searchScreen("OPR003", "CTM Enquiry");
            customfunctions.listAWB("AWBNo", "CarrierNumericCode", "CTM Enquiry");
            String pmKeyCTMRefNo=CTMRefNo;
		 	int verfColsshipmengtStatus[]={9};
		 	int verfColCTMPcs[]={6};
		 	int verfColCTMWt[]={7};
			String[] actVerfValuesShipmentStatus={"Manifest Generated"};
			String[] actVerfValuesCTMPcs={customfunctions.data("Pieces")};
			String[] actVerfValuesCTMWt={customfunctions.data("Weight")};
		 	OPR003.verifyTableDetails(verfColsshipmengtStatus, actVerfValuesShipmentStatus, pmKeyCTMRefNo);
		 	OPR003.verifyTableDetails(verfColCTMPcs, actVerfValuesCTMPcs, pmKeyCTMRefNo);
		 	OPR003.verifyTableDetails(verfColCTMWt, actVerfValuesCTMWt, pmKeyCTMRefNo);
		 	OPR003.selectShipmentclickDetails("AWBNo");
		 	
		 	 //** Verify Transferred pieces, weight in CTM screen and transfer end  **//
		 	
		 	int verfCols1[]={7,8,10};
			String[] actVerfValuesTransfferedPcs1={customfunctions.data("Pieces"),customfunctions.data("Weight"),"Transferred In"};
		 	OPR002.selectShipment(customfunctions.data("prop~AWBNo"));
		 	OPR002.clickTransferEnd();
		 	OPR002.verifyTableDetails(verfCols1, actVerfValuesTransfferedPcs1, pmKeyAWBNo);
           customfunctions.closeTab("OPR003", "CTM Enquiry");
            
           //** Verify FSU RCT message trigger  **//	
            
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
            MSG005.enterMsgType("FSU");
            MSG005.selectMsgSubType("Inbound CTM");
            MSG005.clickList();
            String pmKeyRCT=customfunctions.data("CarrierNumericCode")+" - "+customfunctions.data("prop~AWBNo");
            int verfColsRCT[]={9};
            String[] actVerfValuesRCT={"Sent"};
            MSG005.verifyMessageDetails(verfColsRCT, actVerfValuesRCT, pmKeyRCT,"val~FSU-RCT",true);
            libr.waitForSync(6);
            MSG005.closeTab("MSG005", "MSG005 - List Messages");

            
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace(	);
		}

	}
}

