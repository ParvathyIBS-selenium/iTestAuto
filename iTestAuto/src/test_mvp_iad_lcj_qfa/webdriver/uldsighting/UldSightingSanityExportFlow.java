package uldsighting;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.CreateVisitDeclaration_TGC013;
import screens.DropOffPickUpShipmentsSST;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptanceHHT;
import screens.ListMessages_MSG005;
import screens.MaintainOperationalFlight_FLT003;
import screens.OffloadHHT;
import screens.ReportingAtDockHHT;
import screens.SecurityAndScreening_OPR339;
import screens.ServicePointAllocationHHT;
import screens.UldSightingHHT;
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

public class UldSightingSanityExportFlow extends BaseSetup {

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
	public MaintainOperationalFlight_FLT003 FLT003;
	public ListMessages_MSG005 MSG005;
	public SecurityAndScreening_OPR339 OPR339;
	public DropOffPickUpShipmentsSST sst;
	public CreateVisitDeclaration_TGC013 tgc013;
	public ServicePointAllocationHHT serpointhht;
	public VisitDeclarationEnquiry_TGC010 tgc010;
	public ReportingAtDockHHT reportdockhht;
	public GoodsAcceptanceHHT gahht;
	public UldSightingHHT uldsighthht;
	public ExportManifest_OPR344 OPR344;
	public OffloadHHT offloadhht;
	public WarehouseShipmentEnquiry_WHS011 WHS011;


	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="uldsighting";	

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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR339=new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		sst=new DropOffPickUpShipmentsSST(driver, excelreadwrite, xls_Read);
		tgc013=new CreateVisitDeclaration_TGC013(driver, excelreadwrite, xls_Read);
		tgc010=new VisitDeclarationEnquiry_TGC010(driver, excelreadwrite, xls_Read);
		serpointhht=new ServicePointAllocationHHT(driver, excelreadwrite, xls_Read);
		reportdockhht=new ReportingAtDockHHT(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		uldsighthht=new UldSightingHHT(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		offloadhht = new OffloadHHT(driver, excelreadwrite, xls_Read);
		WHS011=new WarehouseShipmentEnquiry_WHS011(driver, excelreadwrite, xls_Read);
	}



	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) throws InterruptedException {

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

			/**** FLT003 - Create flight****/

			customfunctions.createFlight("FullFlightNumber");
			String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			String flightdate = customfunctions.createDateFormat("dd-MMM-yyyy", 0, "DAY", "FlightDate");
			map.put("FlightNo", flightNo);
			map.put("StartDate", flightdate);
			customfunctions.setPropertyValue("FlightNo",flightNo,proppath); 
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			customfunctions.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate","FullFlightNumber");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
			FLT003.save("FLT003");
			customfunctions.closeTab("FLT003", "Maintain Operational Flight");








			/*********MSG005-loading FBL*********/
			String FBLDate = customfunctions.createDateFormat("ddMMM", 0, "DAY", "FBLDate");
			map.put("FBLDate", FBLDate);
			map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);

			//Checking AWB is fresh or Not
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			String awbNo = customfunctions.data("prop~AWBNo");
			map.put("AWBNo",awbNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			//Writing the full AWB No to property file
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);

			//Create the message FBL
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_1");

			//Process the message

			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);     
			map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
					+" - "+customfunctions.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
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

			//Capture AWB Details
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.enterRouting("Destination","prop~flight_code");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.saveAWB();  
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.asIsExecute();
			customfunctions.closeTab("OPR026", "Capture AWB");




		

			/***Launch emulator - sst**/
			libr.launchSSTApp("sst-app");
			customfunctions.handleConnectivityPopUp();

			/*** TOKEN GENERATION IN DROP OFF PICK UP SST SCREEN**/
			sst.invokeDropOffPickUpShipmentsSSTScreen();
			sst.clickAddULD();
			map.put("uldNo", customfunctions.create_uld_number("UldType", "carrierCode"));
			System.out.println(libr.data("uldNo"));
			sst.enterULDDetails("uldNo");
			sst.addShipmentInUld("prop~CarrierNumericCode", "prop~AWBNo");
			sst.clickDone();
			sst.clickProceed();
			sst.enterDriverDetails("StartDate");
			sst.clickProceed();
			sst.selectVehicletype("VehicleType");
			sst.clickProceed();
			libr.waitForSync(2);
			sst.verifyTokenGeneration("TokenId");

			libr.quitApp();


			/***Launch emulator - uldsighting app**/
			libr.launchUldSightingApp("uldsighting-app");

			//Login in to ULD Sighting App
			String [] hht=libr.getApplicationParams("hht");	
			customfunctions.loginHHT(hht[0], hht[1]);

			//Enter Uld no

			uldsighthht.enterUldNumber("uldNo");
			uldsighthht.verifyIfBUPShipment();
			libr.quitApp();



			/*** HHT - ULD ACCEPTANCE ****/

			libr.launchApp("hht-app-release");
			customfunctions.loginHHT(hht[0], hht[1]);

			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", customfunctions.data("prop~stationCode")+customfunctions.data("prop~AWBNo"));
			gahht.enterValue("uldNo");
			gahht.enterUldAcceptanceDetails("Location","awbNumber","Pieces");
			gahht.checkAllPartsReceivedForUldAcceptance();
			gahht.addULDDetails();
			gahht.saveULDAcceptanceDetails();
			libr.quitApp();


			/**** OPR344 - Export manifest****/
			customfunctions.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo","StartDate");
			OPR344. addULDWithoutAWB("uldNo","0");
			OPR344.verifyFlightStatus("val~Built Up");
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			customfunctions.closeTab("OPR344", "Export manifest");
			
		

			/*** HHT-Offload**/

			libr.launchApp("hht-app-release");
			customfunctions.loginHHT(hht[0], hht[1]);

			/*** HHT - OFFLOAD***/

			offloadhht.invokeOffloadHHTScreen();
			offloadhht.enterValue("uldNo");
			offloadhht.clickUnitizedYes();
			offloadhht.selectOffloadReasons("Due Payload");
			offloadhht.clickSave();
			libr.quitApp();

			/*** VERIFY IF THE ULD IS OFFLOADED***/
			customfunctions.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo","StartDate");
			OPR344.verifyFlightStatus("val~Offloaded");
			customfunctions.closeTab("OPR344", "Export manifest");


			/***Launch emulator - uldsighting app**/
			libr.launchUldSightingApp("uldsighting-app");

			//Login in to ULD Sighting App
			customfunctions.loginHHT(hht[0], hht[1]);

			//Enter Uld no
			uldsighthht.verifyIfUldIsSighted("uldNo");
			uldsighthht.enterUldNumber("uldNo");
			uldsighthht.clickSight();
			uldsighthht.selectFwLocation("Location");
			uldsighthht.clickComplete();

			libr.quitApp();



			/*******WHS011 - Warehouse Shipment Enquiry***********/
			//Verify warehouse location, pieces and weight
			customfunctions.searchScreen("WHS011", "WHS011 - Warehouse Shipment Enquiry");
			WHS011.enterAWBdetails();
			WHS011.clickList();
			/**int[] col = {4,5,9,10};
			String[] values={customfunctions.data("Location"),customfunctions.data("uldNo"),customfunctions.data("Pieces"),customfunctions.data("Weight")};**/
			int[] col = {4,5};
			String[] values={customfunctions.data("Location"),customfunctions.data("uldNo")};
			WHS011.verifyWarehouseDetailsWithPmKey(col, values,"prop~AWBNo");
			customfunctions.closeTab("WHS011", "Warehouse Enquiry");

		}	
		catch(Exception e)
		{

			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}


	}
}


