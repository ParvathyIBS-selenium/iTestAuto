package exportmanifest;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.ExportShipmentListing_OPR030;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;

import com.relevantcodes.extentreports.LogStatus;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class EM27 extends BaseSetup {

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
	public SecurityAndScreening_OPR339 OPR339;
	public ExportManifest_OPR344 OPR344;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005; 
	public GoodsAcceptance_OPR335 OPR335;
	public String proppath = "\\src\\resources\\GlobalVariable.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	String sheetName = "exportmanifest";
	public ExportShipmentListing_OPR030 OPR030;

	@BeforeClass
	public void setup() {
		testName = getTestName();
		excel = new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR339= new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR030=new ExportShipmentListing_OPR030(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "EM01")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;
	}

	@Test(dataProvider = "EM01")
	public void getTestSuite(Map<Object, Object> map) throws Exception {

		libr.map = map;
		libr.setExtentTestInstance(test);

		String className = this.getClass().getSimpleName();
		//Map writeMap=new HashMap();
		System.out.println("className" + className);
		try {

			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			System.out.println("The Class Name is:" + this.getClass().getName());

			//Login to iCargo

			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			//Flight Creation
			cust.createFlight("FullFlightNumber");
			String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			String flightCode=WebFunctions.getPropertyValue(proppath,"flight_code");
			cust.setPropertyValue("flightNumber",flightCode+flightNo,proppath); 
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
			//excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum);

			/*******FLT005 - Flight Creation*******/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("CarrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			
			// Entering flight schedule data
			FLT005.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT005.enterLegCapacityDetails("ATD_Local", "ATA_Local", "AircraftType", "Configuration_name");
			FLT005.legCapacityOkButton();
			FLT005.save();
			cust.closeTab("FLT005", "Maintain Schedule");

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");

			//Writing the AWB2 in property file
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			map.put("AWBNo2", cust.data("prop~AWBNo"));
			cust.setPropertyValue("AWBNo2", cust.data("prop~AWBNo"), proppath);
			cust.setPropertyValue("FullAWBNo2", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);

			cust.searchScreen("OPR026","Capture AWB");
			//Writing the AWB1 in property file
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			map.put("AWBNo", cust.data("prop~AWBNo"));
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);

			//Create the message FBL for AWB1

			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_5");

			//Process the message

			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);

			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();

			//Create the message FBL for AWB2
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo2"), proppath);

			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			MSG005.loadFromFile("Airline","prop~flight_code", "JMS", "", "Origin", "", "FBL_5");

			//Process the message

			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);

			map.put("pmkey", cust.data("prop~flight_code")+" - "+cust.data("prop~flightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			

			/**** OPR339 - Security & Screening****/
			//for AWB 1
			cust.searchScreen("OPR339", "OPR339 - Security & Sceening");
			OPR339.listAWBNo("AWBNo", "prop~CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");
			

			/**** OPR339 - Security & Screening****/
			//for AWB 2
			cust.searchScreen("OPR339", "OPR339 - Security & Sceening");
			OPR339.listAWBNo("AWBNo2", "prop~CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			
			/***** OPR026 - Execute AWB****/
			//Execute AWB1
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination", "CarrierCode");
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");	
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","val~GEN", "ShipmentDescription");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			//Execute AWB2
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo2", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","CarrierCode");	
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");	
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","val~GEN", "ShipmentDescription");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			
			/******OPR355 - Goods Acceptance*********/
			//Loose acceptance for AWB1
			cust.searchScreen("OPR335", "Goods Acceptance");
			OPR335.listAWB(cust.data("prop~AWBNo"), "prop~CarrierNumericCode"); 
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			//Loose acceptance for AWB2
			cust.searchScreen("OPR335", "Goods Acceptance");
			OPR335.listAWB(cust.data("prop~AWBNo2"), "prop~CarrierNumericCode"); 
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			
			/**** OPR030 - Export Shipment Listing****/
			cust.searchScreen("OPR030", "Export Shipment Listing");
			OPR030.listWithODandFilterMode("Origin","Destination","FilterMode");
			OPR030.verifyAnyColumnData("1", "15", "Accepted / Breakdown Pcs/Wgt/Vol", 
					cust.data("Pieces")+"/"+cust.data("Weight"));
			OPR030.verifyAnyColumnData("2", "15", "Accepted / Breakdown Pcs/Wgt/Vol", 
					cust.data("Pieces")+"/"+cust.data("Weight"));
			OPR030.closeTab("OPR030","Export Shipment Listing");


		} 

		catch (Exception e) {
			counter = counter + 1;
			excelreadwrite.insertFailedData(testName,
					commonUtility.getcurrentDateTime() + "_" + String.valueOf(counter), className, className, className,
					false, "", "Element is not found", "Element is found");
			libr.quitBrowser();
			test.log(LogStatus.FAIL, "The test step is failed");
			e.printStackTrace();
			Assert.assertFalse(true, "The test step is failed");

		}
	}
}


