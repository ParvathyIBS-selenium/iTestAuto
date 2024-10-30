package mvpcrs;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.AWBClearance_OPR023;
import screens.BreakDownScreen_OPR004;
import screens.BreakdownHHT;
import screens.BuildUpHHT;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.DamageCaptureHHT;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.FlightLoadPlan_OPR015;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListCheckSheetConfig_SHR094;
import screens.ListIrregularity_OPR341;
import screens.ListMessages_MSG005;
import screens.ListTemplates_SHR093;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.OffloadEnquiry_OPR011;
import screens.RelocationTaskMonitor_WHS052;
import screens.SecurityAndScreeningHHT;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

/** Test Case Name : Verify user can able to split the AWB to multiple SUs by adding multiple rows and associate AWB pieces to SCCs **/

public class IASCB_51706_Acceptance_TC02 extends BaseSetup {
	
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
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005; 
	public GoodsAcceptance_OPR335 OPR335;
	public DamageCaptureHHT dchht;
	public ListIrregularity_OPR341 OPR341;
	public ExportManifest_OPR344 OPR344;
	public MarkFlightMovements_FLT006 FLT006;
	public SecurityAndScreening_OPR339 OPR339;
	public ImportManifest_OPR367 OPR367;
	public ListCheckSheetConfig_SHR094 SHR094;
	public ListTemplates_SHR093 SHR093;
	public BreakDownScreen_OPR004 OPR004;
	public DeliveryDocumentation_OPR293 OPR293;
	public BreakdownHHT bdhht;
	public SecurityAndScreeningHHT sechht;
	public OffloadEnquiry_OPR011 off;
	public FlightLoadPlan_OPR015 OPR015;
	public BuildupPlanning_ADD004 ADD004;
	public GoodsAcceptanceHHT gahht;
	public BuildUpHHT buhht;
	public AWBClearance_OPR023 OPR023;
	public RelocationTaskMonitor_WHS052 WHS052;
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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR341=new ListIrregularity_OPR341(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		SHR094=new ListCheckSheetConfig_SHR094(driver, excelreadwrite, xls_Read);
		SHR093=new ListTemplates_SHR093(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004=new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR293=new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		bdhht = new BreakdownHHT(driver, excelreadwrite, xls_Read);
		sechht=new SecurityAndScreeningHHT(driver, excelreadwrite, xls_Read);
		off = new OffloadEnquiry_OPR011(driver, excelreadwrite, xls_Read);
		OPR015 = new FlightLoadPlan_OPR015(driver, excelreadwrite, xls_Read);
		ADD004 = new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		WHS052=new RelocationTaskMonitor_WHS052(driver, excelreadwrite, xls_Read);
	}
	
	@DataProvider(name = "HHT49")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "HHT49")
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
			
			// creating flight number
			
			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");			
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = cust.getPropertyValue(proppath, "flightNumber");
			FlightNum=FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
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
			
			//Switch role
			cust.switchRole("Origin", "Origin", "RoleGroup");
			
			/***MESSAGE - loading ASM**/
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			//Load ASM message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","carrierCode", "JMS", "", "Origin", "", "ASM_NEW");
			
			//Process ASM message
			
			MSG005.enterMsgType("ASM");
			MSG005.clickList();
			libr.waitForSync(6);
			map.put("pmkey", "NEW"+" - "+cust.data("carrierCode")+" - "+cust.data("FlightNo")+" - "+cust.data("FBLDate").toUpperCase());
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
            
            map.put("Pcs", cust.data("Pieces"));
            map.put("Wgt", cust.data("Weight"));
            map.put("Vol", cust.data("Volume"));
				
			/******MSG005-loading FBL****/

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);


			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);


			//Create the message FBL
			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","carrierCode", "JMS", "", "Origin", "", "FBL_1");

			//Process the message
			
			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);

			map.put("pmkey", cust.data("carrierCode")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			   
            cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
              
            String sccs[]={cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1]};
            cust.createXFWBMessageWithSCCs("XFWB_withDim_MultipleSCCs", sccs);
            
            
            //Load FWB message
            cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.loadFromFile("All","ALL", "MQ-SERIES", "", "Origin", "", "XFWB_withDim_MultipleSCCs",true);
            cust.closeTab("MSG005", "List Message");

            
           cust.searchScreen("OPR026","Capture AWB");
            String pcs[]={libr.data("SplitPcs").split(",")[0],libr.data("SplitPcs").split(",")[1]};
            OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
            OPR026.splitShipmentWithSCC(libr.data("SCC"),pcs);
            cust.closeTab("OPR026", "Capture AWB");

			
						
	        /***** OPR026 - Execute AWB****/
           
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
            
            
            /****OPR355 - Goods Acceptance****/
			
			//Goods acceptance
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.clickLooseAccptTab();
			String locs[]={libr.data("Location2").split(",")[0],libr.data("Location2").split(",")[1]};
			OPR335.editShipmentLocation(pcs[0],sccs[0],locs[0]);
			OPR335.editShipmentLocation(pcs[1],sccs[1],locs[1]);
			OPR335.verifySUNotNull(cust.data("SCC").split(",")[0]);
			OPR335.verifySUNotNull(cust.data("SCC").split(",")[1]);
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");
            
            /** CHECKING FSU-RCS TRIGGERED FOR AWB **/
            
		    /*cust.searchScreen("MSG005", "MSG005 - List Messages");
		 	MSG005.enterMsgType("FSU");
		 	MSG005.selectMsgSubType("Acceptance");
		 	MSG005.clickList();
		 	String pmKeyRCS1=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsRCS[]={9};
			String[] actVerfValuesRCS={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS1,"val~FSU-RCS",false);
            libr.waitForSync(2); 
            MSG005.closeTab("MSG005", "MSG005 - List Messages");*/
            
            //Relocation Task Manager :: needs to be modified once configuration is done
			
           /* WHS052.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterAWB("CarrierNumericCode", "prop~AWBNo");
			WHS052.listAwbDetails();
			WHS052.expandAWB();
            //Verification of activation time and updated pieces
            int verfCols2[]={1,7,8};
            String[] actVerfValues3={cust.data("StartDate"),"",cust.data("Pieces")};
            WHS052.verifyMessageDetails(verfCols2, actVerfValues3);
            cust.closeTab("WHS052", "Relocation Task Monitor");*/
		
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}

	}
}

