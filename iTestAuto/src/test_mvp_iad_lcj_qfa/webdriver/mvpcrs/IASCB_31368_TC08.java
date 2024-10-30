package mvpcrs;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AWBClearance_OPR023;
import screens.AutoBlockSetUp_OPR031;
import screens.BuildUpHHT;
import screens.CaptureAWB_OPR026;
import screens.CaptureConsumablesHHT;
import screens.DamageCaptureHHT;
import screens.DeadloadStatement_OPR063;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.OffloadHHT;
import screens.SecurityAndScreeningHHT;
import screens.SecurityAndScreening_OPR339;
import screens.ULDTag_OPR013;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

//Verify System blocks the complete AWB even if Partial Pieces of AWB are captured as Damage

public class IASCB_31368_TC08 extends BaseSetup {
	
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
	public CaptureConsumablesHHT cchht;
	public DeadloadStatement_OPR063 OPR063;
	public OffloadHHT offloadhht;
	public ULDTag_OPR013 OPR013;
	public DamageCaptureHHT dchht;
	public SecurityAndScreening_OPR339 OPR339;
	public AWBClearance_OPR023 OPR023;
	public SecurityAndScreeningHHT sechht;
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
		cchht=new CaptureConsumablesHHT(driver, excelreadwrite, xls_Read);
		OPR063=new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);
		OPR013=new ULDTag_OPR013(driver, excelreadwrite, xls_Read);
		offloadhht = new OffloadHHT(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		dchht = new DamageCaptureHHT(driver, excelreadwrite, xls_Read);
		OPR023=new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		sechht=new SecurityAndScreeningHHT(driver, excelreadwrite, xls_Read);

	}
	
	
	
	@DataProvider(name = "IASCB_31368_TC03")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}
	

	@Test(dataProvider = "IASCB_31368_TC03")
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
			
			
			
			/******MSG005-loading FBL 1***/

			//Checking AWB is fresh or Not--AWB 1
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
			
			
			/***MESSAGE - loading FWB **/
			
			map.put("Pcs", cust.data("Pieces"));
			map.put("Wgt", cust.data("Weight"));
			map.put("Vol", cust.data("Volume"));
		
			/***MESSAGE - loading FWB 1**/
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			//Load FWB message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "MQ-SERIES", "", "Origin", "", "XFWB_withDim",true);
			cust.closeTab("MSG005", "List Message");
			
			/**** OPR339 - Security & Screening****/
            //Capture security details by entering SCC as 'SPX'
            cust.searchScreen("OPR339", "Security and Screening");
            OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
            OPR339.clickYesButton();
            OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");            
            OPR339.chkSecurityDataReviewed();
            OPR339.editSCC("SCC2");            
            cust.closeTab("OPR339", "Security & Sceening");
			
			/***** OPR026 - Execute AWB****/
			//Execute AWB
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			//Verify SCC
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.verifySCCCodes("VerifySCCExists", "GEN,SPX");			
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
			
			/*** HHT - Damage Capture****/
			//Capture damage details with partial pieces and weight
			dchht.invokeDamageCaptureScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			dchht.enterAwbNumber("awbNumber");
			dchht.enterPiecesAndWeight("Pieces2", "Weight2");
			dchht.selectDamageCode("Others");
			dchht.enterPackageCodeDamageReasonCode("Box","Improper Loading");
			dchht.enterPointOfNotice("Noticed at cargo acceptance");
			dchht.enterRemarks("val~Remarks");
			dchht.clickSave();
			
			/*****OPR023 - AWB Clearance*******/
            //Verify the block details are displayed            
	        cust.searchScreen("OPR023", "AWB Clearance");
	        OPR023.listAWB("prop~CarrierNumericCode", "prop~AWBNo");
	        //Verify SPX scc is replaced by NSC scc
	        OPR023.verifySCC("val~NSC");
	        OPR023.closeTab("OPR023", "AWB Clearance");
	        
	        /***Launch emulator - hht**/
			libr.launchApp("hht-app-release");		

			//Login in to HHT
			cust.loginHHT(hht[0], hht[1]);
	        
	        /*** HHT - Build Up****/
		    //Verify system shouldn't allow user to save the build up transaction and show the error for Damage captured awb
			buhht.invokeBuildUpScreen();
			String uldNum1=cust.create_uld_number("UldType", "carrierCode");
            map.put("UldNum", uldNum1);
            excelRead.writeDataInExcel(map, path1, sheetName, testName);
			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code", "prop~flightNo","currentDay");
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			buhht.enterShipmentDetails("awbNumber","Pieces", "Weight");	
			buhht.verifyBuildUpDetailsNotSaved();
			
			cust.clickBack("Build Up");
			cust.clickBack("Build Up");
			
			/**HHT-SECURITY AND SCREENING**/
			//Capture Screening information with received pieces, received weight
			sechht.invokeSecurityAndScreeningScreen();
			map.put("awbNumber", cust.data("prop~stationCode")+cust.data("prop~AWBNo"));
			sechht.enterValue("awbNumber");
			sechht.enterScreeningDetails("Pieces");
			sechht.selectScreeningMethod("X-ray equipment");
			sechht.enterScreeningStatus("Pass");
			sechht.saveScreeningDetails();			
			cust.clickBack("Security Screening");
			
			sechht.enterValue("awbNumber");
			sechht.enterScreeningDetails("Pieces");
			sechht.selectScreeningMethod("Visual check");
			sechht.enterScreeningStatus("Pass");
			sechht.saveScreeningDetails();	
			
			/*****OPR023 - AWB Clearance*******/
            //Verify the block details are displayed            
	        cust.searchScreen("OPR023", "AWB Clearance");
	        OPR023.listAWB("prop~CarrierNumericCode", "prop~AWBNo");
	        //Verify SHR SCC
	        OPR023.verifySCC("val~SHR");
	        OPR023.closeTab("OPR023", "AWB Clearance");
			
	        /***Launch emulator - hht**/
			libr.launchApp("hht-app-release");		

			//Login in to HHT
			cust.loginHHT(hht[0], hht[1]);	
	        
	        /*** HHT - Build Up****/
		    //Verify Build up transaction is allowed
			buhht.invokeBuildUpScreen();			
			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code", "prop~flightNo","currentDay");
			buhht.enterShipmentDetails("awbNumber","Pieces", "Weight");
			buhht.invokeBuildUpScreen();			
			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code", "prop~flightNo","currentDay");
			buhht.enterShipmentDetails("awbNumber","Pieces", "Weight");
			buhht.verifyBuildUpDetailsIfSaved();
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
	
}


