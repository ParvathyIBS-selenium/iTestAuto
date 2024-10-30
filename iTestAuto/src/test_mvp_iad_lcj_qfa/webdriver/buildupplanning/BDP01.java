package buildupplanning;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BuildUpHHT;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.ExportPlanningProgress_ADD007;
import screens.FlightLoadPlan_OPR015;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreeningHHT;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class BDP01 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public FlightLoadPlan_OPR015 OPR015;
	public BuildupPlanning_ADD004 ADD004;
	public ExportPlanningProgress_ADD007 ADD007;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="buildupplanning";	
	
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
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR015=new FlightLoadPlan_OPR015(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		ADD007=new ExportPlanningProgress_ADD007(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "HHT08")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "HHT08")
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
						map.put("FullFlightNo", FlightNum);
						map.put("FlightNo", FlightNum.substring(2));
						map.put("StartDate", startDate);
						map.put("EndDate", endDate);
						map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
						map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
						map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
						map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
						System.out.println(FlightNum);

						// Maintain Flight Screen (FLT005)

						cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
						FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");

						// Entering flight schedule data

						FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
						FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
						FLT005.legCapacityOkButton();
						FLT005.save();
						cust.waitForSync(7);
						cust.closeTab("FLT005", "Maintain Schedule");
						cust.waitForSync(1);




						/******MSG005-loading FBL***/

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
								
								
						/***** OPR026 - Execute AWB****/
						cust.searchScreen("OPR026","Capture AWB");
						OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
						OPR026.updateOrigin("Origin");
						OPR026.updateDestination("Destination");
						OPR026.enterRouting("Destination","carrierCode");	
						OPR026.selectSCI("SCI");
						OPR026.enterAgentCode("AgentCode");	
						OPR026.provideShipperCode("shipperCode");
						OPR026.provideConsigneeCode("consigneeCode");
						OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
						OPR026.clickChargesAcc();
						OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
						OPR026.clickBookingDetails();
						OPR026.enterBookingDetailsSingleLeg("Origin", "Destination", "prop~flightNumber", "StartDate", "Pieces", "Weight", "Volume");
						OPR026.saveAWB();	
						OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
						OPR026.asIsExecute();
						cust.closeTab("OPR026", "Capture AWB");


						//Goods acceptance
						cust.searchScreen("OPR335", "Goods Acceptance");
						OPR335.listAWB(cust.data("prop~AWBNo"), "prop~CarrierNumericCode"); 
			            OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			            OPR335.addLooseShipment();
			            OPR335.allPartsRecieved();
			            OPR335.saveAcceptance();
			            cust.closeTab("OPR335", "Goods Acceptance");
						
						/** ADD007 - Export Planning Progress screen **/
						cust.searchScreen("ADD007", "Export Planning Progress");
						ADD007.EnterFlightDetails("carrierCode","FlightNo","StartDate", "EndDate");
						ADD007.clickList();
						ADD007.selectflight();
						ADD007.clickBuildupButton();
			            ADD004.verifyShipmentInLoadPlan("prop~AWBNo");
			            ADD004.verifyPendingSection("val~1AWB","Pieces","Weight");
			            ADD004.closeScreen();
			            cust.closeTab("ADD007", "Export Planning Progress");
		
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test step is failed");
			
		}

	}
}



