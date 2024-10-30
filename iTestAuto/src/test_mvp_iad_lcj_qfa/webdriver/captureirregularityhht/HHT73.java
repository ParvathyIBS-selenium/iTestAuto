package captureirregularityhht;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import postconditions.CancelFlights;
import screens.CaptureAWB_OPR026;
import screens.CaptureIrregularityHHT;
import screens.CreateVisitDeclaration_TGC013;
import screens.DeliverCargo_OPR064;
import screens.DeliverNoteEnquiry_OPR034;
import screens.DeliveryDocumentation_OPR293;
import screens.DropOffPickUpShipmentsSST;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListIrregularity_OPR341;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreening_OPR339;
import screens.ServicePointAllocationHHT;
import screens.VisitDeclarationEnquiry_TGC010;
import screens.WarehouseShipmentEnquiry_WHS011;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class HHT73 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions customFunctions;
	public MaintainFlightSchedule_FLT005 FLT005;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005; 
	public ListIrregularity_OPR341 OPR341;
	public CaptureIrregularityHHT cihht;
	public GoodsAcceptanceHHT gahht;
	public DropOffPickUpShipmentsSST sst;
	public CreateVisitDeclaration_TGC013 tgc013;
	public ServicePointAllocationHHT serpointhht;
	public VisitDeclarationEnquiry_TGC010 tgc010;
	public SecurityAndScreening_OPR339 OPR339;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="captureirregularityhht";	
	
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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		cihht = new CaptureIrregularityHHT(driver, excelreadwrite, xls_Read);
		OPR341=new ListIrregularity_OPR341(driver, excelreadwrite, xls_Read);
		gahht=new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		sst=new DropOffPickUpShipmentsSST(driver, excelreadwrite, xls_Read);
		tgc013=new CreateVisitDeclaration_TGC013(driver, excelreadwrite, xls_Read);
		tgc010=new VisitDeclarationEnquiry_TGC010(driver, excelreadwrite, xls_Read);
		OPR339=new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "HHT11")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "HHT11")
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
			customfunctions.loginICargo(iCargo[1], iCargo[2]);

			// creating flight number

			customfunctions.createFlight("FullFlightNumber");
			String startDate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = customfunctions.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = customfunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", customfunctions.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", customfunctions.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("nextDay", customfunctions.createDateFormat("dd", 1, "DAY", ""));
			map.put("endDay", customfunctions.createDateFormat("dd", 2, "DAY", ""));
			System.out.println(FlightNum);

			/***MESSAGE - loading ASM**/
			customfunctions.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			//Load ASM message
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "ASM_NEW");
			customfunctions.closeTab("MSG005", "List Message");


			/******MSG005-loading FBL***/

			//Checking AWB is fresh or Not
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);


			//Writing the full AWB No
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);


			//Create the message FBL
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_1");



			//Process the message
			
			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);


			map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
					+" - "+customfunctions.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			customfunctions.closeTab("MSG005", "List Message");
			
			/***MESSAGE - loading FWB**/
			customfunctions.createTextMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			//Load FWB message
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FWB_AWB1");
			customfunctions.closeTab("MSG005", "List Message");

			/**** OPR339 - Security & Screening****/

			customfunctions.searchScreen("OPR339", "OPR339 - Security & Sceening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
			OPR339.enterSCC("val~SPX");
			OPR339.chkSecurityDataReviewed();
			OPR339.saveSecurityDetailsAfterDataReview();
			customfunctions.closeTab("OPR339", "Security & Sceening");
			

			/***** OPR026 - Execute AWB****/
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			customfunctions.closeTab("OPR026", "Capture AWB");
			

			/***Launch emulator - sst**/
			libr.launchSSTApp("sst-app");
			customfunctions.handleConnectivityPopUp();

			/*** TOKEN GENERATION IN DROP OFF PICK UP SST SCREEN**/
			sst.invokeDropOffPickUpShipmentsSSTScreen();
			sst.addShipment("prop~CarrierNumericCode", "prop~AWBNo");
			sst.clickProceed();
			sst.enterDriverDetails("StartDate");
			sst.clickProceed();
			sst.selectVehicletype("VehicleType");
			sst.clickProceed();
			libr.waitForSync(2);
			sst.verifyTokenGeneration("TokenId");

			libr.quitApp();
			
			/**** TGC013- CREATE VISIT DECLARATION****/

			customfunctions.searchScreen("TGC013","Create Visit Declaration");
			tgc013.enterTokenNo("TokenId");
			tgc013.clickList();
			tgc013.verifyAttributes("prop~FullAWBNo","2");
			tgc013.editVerificationDetails();
			tgc013.performPhotoVerification();
			tgc013.addVerificationDetails();
			tgc013.clickDocCompleted();
			tgc013.save();
			customfunctions.closeTab("TGC013", "Create Visit Declaration");

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			customfunctions.loginHHT(hht[0], hht[1]);

			/*** HHT - Capture Irregularity****/

			cihht.invokeCaptureIrregularityScreen();
			cihht.selectTransaction("Screening");
			cihht.enterTokenNumber("TokenId");
			cihht.selectDeviationCode("Screening Failed");
			cihht.enterRemarks("Remarks");
			cihht.clickSave();
			
			libr.quitApp();
			
		
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
		}

	}
}

