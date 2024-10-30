package exportmanifest;
import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import postconditions.CancelFlights;
import screens.BreakDownEnquiry_OPR005;
import screens.CaptureAWB_OPR026;
import screens.ExportFlightProgress_OPR336;
import screens.ExportManifest_OPR344;
import screens.ExportShipmentListing_OPR030;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
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

public class EM36 extends BaseSetup {
		
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
	public BreakDownEnquiry_OPR005 OPR005;
	public ExportShipmentListing_OPR030 OPR030;
	public ExportFlightProgress_OPR336 OPR336;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="exportmanifest";	
	
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
		OPR005=new BreakDownEnquiry_OPR005(driver, excelreadwrite, xls_Read);
		OPR030=new ExportShipmentListing_OPR030(driver, excelreadwrite, xls_Read);
		OPR336=new ExportFlightProgress_OPR336(driver, excelreadwrite, xls_Read);

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
		

			//create AWB

			String flightStartdate = customfunctions.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("flightStartdate",flightStartdate);
			String FBLDate = customfunctions.createDateFormat("ddMMM", 0, "DAY", "FBLDate");
			map.put("FBLDate", FBLDate);
			map.put("Day", customfunctions.createDateFormat("dd", 0, "DAY", ""));
            map.put("Month", customfunctions.createDateFormat("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(1);
			
			//Login to iCargo
		
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			customfunctions.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
		
			
			
			/** Flight Creation 1**/
			
			customfunctions.createFlight("FlightNo");
			String flightNo2 = WebFunctions.getPropertyValue(proppath,"flightNo");
			map.put("FlightNo", flightNo2);
			libr.waitForSync(1);
			customfunctions.setPropertyValue("flightNo",flightNo2, proppath);
			customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
			System.out.println(customfunctions.data("prop~flightNo"));
			System.out.println(customfunctions.data("prop~flightNumber"));
			customfunctions.searchScreen("FLT003","Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "flightStartdate", "FlightNo");
			FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
			FLT003.enterLegCapacityDetailsWithConfiguration("ATD_Local","ATA_Local", "AircraftType","Configuration_name");
			FLT003.save("FLT003");
			FLT003.close("FLT003");
			//Checking AWB1 is fresh or Not
			
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			String AWBNo1 = WebFunctions.getPropertyValue(proppath,"AWBNo");
			customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
			String FullAWBNo1 = WebFunctions.getPropertyValue(proppath,"FullAWBNo");
			map.put("AWBNo", AWBNo1);
			map.put("FullAWBNo", FullAWBNo1);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			
			/** Create the message FBL for AWB 1 **/
			customfunctions.createTextMessage("MessageExcelAndSheet", "MessageParam");
			customfunctions.searchScreen("MSG005", "MSG005 - List Messages");
           	MSG005.loadFromFile("Airline","prop~flight_code", "JMS","", "Origin", "", "FBL_5");
           	
           	/** Process the message 1 **/
            MSG005.enterMsgType("FBL");
            MSG005.clickList();
            libr.waitForSync(6);      
            map.put("pmkey", customfunctions.data("prop~flight_code")+" - "+customfunctions.data("prop~flightNo")+" - "+customfunctions.data("Day")+" - "+customfunctions.data("Month").toUpperCase()
                              +" - "+customfunctions.data("Origin"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickprocess();
            customfunctions.closeTab("MSG005", "List Message");
			
			/**** OPR339 - Security & Screening for awbno1****/
            
			customfunctions.searchScreen("OPR339", "OPR339 - Security & Sceening");
            OPR339.listAWB("prop~AWBNo", "prop~CarrierNumericCode", "OPR339 - Security & Sceening");
            OPR339.clickYesButton();
            OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
            OPR339.saveSecurityDetails();
            customfunctions.closeTab("OPR339", "Security & Sceening");
			
			/** Capture AWB Details for awbno1 **/
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","CarrierCode");		
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");	
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDescription");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.clickBookingDetails();
			OPR026.enterBookingDetailsSingleLeg("Origin", "Destination", "prop~flightNumber", "flightStartdate", "Pieces", "Weight", "Volume");
			OPR026.asIsExecute();
			OPR026.close("OPR026");
			
			
			/** OPR355 - Goods Acceptance : ULD**/
            
            
			customfunctions.searchScreen("OPR335", "Goods Acceptance");
			customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			 OPR335.looseShipmentDetails("Location", "Pieces","Weight");
	        OPR335.addLooseShipment();
	        OPR335.allPartsRecieved();
            OPR335.saveAcceptance();
            customfunctions.closeTab("OPR335", "Goods Acceptance");
				
           
			 
            /**Export Manifest**/
           
            customfunctions.searchScreen("OPR344", "Export manifest");
            OPR344.listFlight("prop~flight_code", "prop~flightNo","flightStartdate");
            OPR344.addNewULDWithAWB("val~BULK","0","prop~CarrierNumericCode","AWBNo","Pieces","Weight");
            customfunctions.closeTab("OPR344", "Export Manifest");

			
            /**** OPR336 - Export Flight Progress****/
            customfunctions.searchScreen("OPR336", "Export Flight Progress");
			OPR336.listFlight("prop~flight_code", "prop~flightNo", "flightStartdate", "");
			OPR336.verifyBUPStatus("val~0%");
			OPR336.closeTab("OPR336", "Export Flight Progress");

			
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
	
}

