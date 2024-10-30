package mvpcrs;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AutoBlockSetUp_OPR031;
import screens.BuildUpHHT;
import screens.CaptureAWB_OPR026;
import screens.CaptureConsumablesHHT;
import screens.DeadloadStatement_OPR063;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreeningHHT;
import screens.ULDTag_OPR013;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

/**Capture ULD Contour with Part Shipment from Build up Screen**/
public class IASCB_9283_TC18 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public BuildUpHHT buhht;
	public GoodsAcceptanceHHT gahht;
	public DeadloadStatement_OPR063 OPR063;
	public ULDTag_OPR013 OPR013;
	public CaptureConsumablesHHT cchht;
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
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		OPR063=new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);
		OPR013=new ULDTag_OPR013(driver, excelreadwrite, xls_Read);
		cchht=new CaptureConsumablesHHT(driver, excelreadwrite, xls_Read);

	}
	
	
	
	@DataProvider(name = "IASCB_9283_TC16")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}
	

	@Test(dataProvider = "IASCB_9283_TC16")
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
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
		    map.put("XFWBDate", flightdate1);
			System.out.println(FlightNum);
			
			/***MESSAGE - loading ASM 1 **/
			
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
			
			
			
			
			/***MESSAGE - loading ASM 2 **/
			
			cust.createFlight("FullFlightNumber");
			String FlightNum1 = cust.getPropertyValue(proppath, "flightNumber");
			FlightNum1=FlightNum1.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo1", FlightNum1);
			map.put("FlightNo1", FlightNum1.substring(2));
			map.put("FullFlightNo", FlightNum1);
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			//Load ASM message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "JMS", "", "Origin", "", "ASM_NEW");
			
			
			//Process ASM message
			
			MSG005.enterMsgType("ASM");
			MSG005.clickList();
			libr.waitForSync(6);
			map.put("pmkey", "NEW"+" - "+cust.data("carrierCode")+" - "+cust.data("FlightNo1")+" - "+cust.data("FBLDate").toUpperCase());
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
			map.put("FullFlightNo", FlightNum);
			
			/******MSG005-loading FBL 1***/

			//Checking AWB is fresh or Not--AWB 1
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			
			map.put("FullFlightNo", FlightNum);
			map.put("Pcs", cust.data("Pieces1"));
			map.put("Wgt", cust.data("Weight1"));
			map.put("Vol", cust.data("Volume1"));
			//Create the message FBL
			
			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "JMS", "", "Origin", "", "FBL_Partshipment");

			//Process the message

			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);


			map.put("pmkey", cust.data("carrierCode")+" - "+cust.data("FlightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
			//Create the message FBL 2
			
			map.put("FullFlightNo", FlightNum1);
			map.put("Pcs", cust.data("Pieces2"));
			map.put("Wgt", cust.data("Weight2"));
			map.put("Vol", cust.data("Volume2"));
			
			//Create the message FBL
			
			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "JMS", "", "Origin", "", "FBL_Partshipment");

			//Process the message

			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);


			map.put("pmkey", cust.data("carrierCode")+" - "+cust.data("FlightNo1")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
			
			/***MESSAGE - loading FWB **/
			
			map.put("Pcs", cust.data("Pieces"));
			map.put("Wgt", cust.data("Weight"));
			map.put("Vol", cust.data("Volume"));
			
			/***MESSAGE - loading FWB 1**/
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			//Load FWB message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "MQ-SERIES", "", "Origin", "", "XFWB_NoFlight",true);
			cust.closeTab("MSG005", "List Message");
			
			/***** OPR026 - Execute AWB****/
			
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();	
			cust.closeTab("OPR026", "Capture AWB");
			
			
		
			
			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);
			
			/*** HHT - ACCEPTANCE 1****/

			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			gahht.enterValue("awbNumber");
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "Location");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");
			
			
			/*** HHT - Build Up****/

			buhht.invokeBuildUpScreen();

			String uldNum=cust.create_uld_number("UldType2", "carrierCode");
			map.put("UldNum", uldNum);

			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("carrierCode", "FlightNo","currentDay");
			map.put("awbNumber1", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			buhht.enterShipmentDetails("awbNumber1","Pieces1", "Weight1");
			buhht.verifyBuildUpDetailsIfSaved();

			cust.clickBack("Build Up");
			cust.clickBack("Build Up");


			/**** BUILD UP COMPLETE FOR ULD ****/
			
			buhht.enterValue("UldNum");
			buhht.clickMoreOptions();
			buhht.clickCaptureOverhangIndent();
			buhht.captureOverhangIndentDetails("Front","Rear","Left","Right");
			buhht.clickMoreOptions();
			buhht.clickUpdateULDHeightContour();
			buhht.captureContour(cust.data("Contour"));
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteWithTopUpNoOption();
			cchht.selectMaterial("val~Belt");
			cchht.enterCount("val~2");
			cchht.clickSave();
			
			cust.clickBack("Build Up");
			
			/*** HHT - Build Up****/

			buhht.invokeBuildUpScreen();

			String uldNum1=cust.create_uld_number("UldType3", "carrierCode");
			map.put("UldNum1", uldNum1);

			buhht.enterValue("UldNum1");
			buhht.updateFlightDetailsWithOutPopUp("carrierCode", "FlightNo1","currentDay");
			map.put("awbNumber1", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			buhht.enterShipmentDetails("awbNumber1","Pieces2", "Weight2");
			buhht.verifyBuildUpDetailsIfSaved();

			cust.clickBack("Build Up");
			cust.clickBack("Build Up");


			/**** BUILD UP COMPLETE FOR ULD ****/
			
			buhht.enterValue("UldNum1");
			buhht.clickMoreOptions();
			buhht.clickCaptureOverhangIndent();
			buhht.captureOverhangIndentDetails("Front","Rear","Left","Right");
			buhht.clickMoreOptions();
			buhht.clickUpdateULDHeightContour();
			buhht.captureContour(cust.data("Contour"));
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteWithTopUpNoOption();
			cchht.selectMaterial("val~Belt");
			cchht.enterCount("val~2");
			cchht.clickSave();
			cust.clickBack("Build Up");
			
			/************* DEADLOAD STATEMENT FLIGHT 1****************/
			cust.searchScreen("OPR063", "Dead load statement");
			OPR063.listFlightDetails("carrierCode","FlightNo","StartDate");
			OPR063.selectULD(cust.data("UldNum"));
			OPR063.clickULDLoadingInstuctor();
			OPR063.verifyULDLoadingInstuctorWithContourDetails("FlightNo", "UldNum","Contour");
			OPR063.ULDLoadingInstructionOK();
			cust.closeTab("OPR063", "Dead load statement");
			
			/************* DEADLOAD STATEMENT FLIGHT 2****************/
			
			cust.searchScreen("OPR063", "Dead load statement");
			OPR063.listFlightDetails("carrierCode","FlightNo1","StartDate");
			OPR063.selectULD(cust.data("UldNum1"));
			OPR063.clickULDLoadingInstuctor();
			OPR063.verifyULDLoadingInstuctorWithContourDetails("FlightNo1", "UldNum1","Contour");
			OPR063.ULDLoadingInstructionOK();
			cust.closeTab("OPR063", "Dead load statement");
				
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
		}
	}
	
}


