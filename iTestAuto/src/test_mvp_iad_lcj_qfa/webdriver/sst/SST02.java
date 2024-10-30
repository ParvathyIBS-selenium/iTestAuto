package sst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.CaptureCheckSheet_CHK002;
import screens.CaptureIrregularityHHT;
import screens.CreateVisitDeclaration_TGC013;
import screens.DropOffPickUpShipmentsSST;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ListIrregularity_OPR341;
import screens.ListMessages_MSG005;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;
import screens.ServicePointAllocationHHT;
import screens.VisitDeclarationEnquiry_TGC010;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class SST02 extends BaseSetup {

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
	public DropOffPickUpShipmentsSST sst;
	public CaptureIrregularityHHT cihht;
	public GoodsAcceptanceHHT gahht;
	public CreateVisitDeclaration_TGC013 tgc013;
	public ServicePointAllocationHHT serpointhht;
	public VisitDeclarationEnquiry_TGC010 tgc010;


	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="sst";	

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
		sst=new DropOffPickUpShipmentsSST(driver, excelreadwrite, xls_Read);
		cihht = new CaptureIrregularityHHT(driver, excelreadwrite, xls_Read);
		gahht=new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		tgc013=new CreateVisitDeclaration_TGC013(driver, excelreadwrite, xls_Read);
		tgc010=new VisitDeclarationEnquiry_TGC010(driver, excelreadwrite, xls_Read);
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
			tgc013.verifyAttributes("val~New","15");
			tgc013.clickMoreOptions("prop~AWBNo");
			tgc013.clickCaptureAwb();
			OPR026.handleShipmentStatusPopUp();
			OPR026.asisExecuteButton();
			customfunctions.handleAlert("Accept", "OPR026");
			customfunctions.switchToDefaultAndContentFrame("TGC013");
			customfunctions.close("OPR026");
			customfunctions.closeTab("TGC013", "Create Visit Declaration");
			customfunctions.searchScreen("TGC013","Create Visit Declaration");
			tgc013.enterTokenNo("TokenId");
			tgc013.clickList();
			tgc013.verifyAttributes("val~Executed","15");
			tgc013.editVerificationDetails();
			tgc013.performPhotoVerification();
			tgc013.addVerificationDetails();
			tgc013.clickDocCompleted();
			tgc013.save();
			customfunctions.closeTab("TGC013", "Create Visit Declaration");


		}	
		catch(Exception e)
		{

			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}


	}
}


