package wp5;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.GoodsAcceptance_OPR335;
import screens.ListFlightDiscrepancy_OPR047;
import screens.ListMessages_MSG005;
import screens.Mercury;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;
import screens.BuildupPlanning_ADD004;
import screens.ExportManifest_OPR344;
import screens.MarkFlightMovements_FLT006;
import screens.ImportManifest_OPR367;
import screens.ImportPlanningProgress_ADD008;

/** Verify the count of discrepancies at the time of breakdown completion. **/

public class IASCB_31442_TC_2349 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainFlightSchedule_FLT005 FLT005;
	public BuildupPlanning_ADD004 ADD004;
	public ExportManifest_OPR344 OPR344;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public ImportPlanningProgress_ADD008 ADD008;
	public ListFlightDiscrepancy_OPR047 OPR047;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "wp5";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		ADD004 = new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		ADD008 = new ImportPlanningProgress_ADD008(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR047 = new ListFlightDiscrepancy_OPR047(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2349")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2349")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// Login to iCargo

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ShipperCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury",
					WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);

			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM", 7, "DAY", ""));
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());

			// Regulated Agent details
			map.put("RegulatedAgentCode",
					WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));
			String currtme1 = cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");
			String currentday = cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Paris");
			String SD = currentday + currtme1;
			map.put("SDtime", SD);
			String screenmethod = cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod", screenmethod);
			map.put("UserName", "T133072");

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");

			/** Maintain Flight Screen (FLT005) . Taking fresh flight **/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode", "prop~flightNo", startDate, endDate, "FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code_KL") + cust.data("prop~flightNo"),
					proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");

			// Flight details

			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FlightNumber", cust.data("FullFlightNo"));

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("awbNumber1", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			map.put("FullAWBNo", cust.data("awbNumber1"));
			map.put("AWBNo", cust.data("awb1"));
			map.put("AWBNo1", cust.data("awb1"));

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2
			map.put("awbNumber2", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));
			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo2", cust.data("awb2"));

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("awbNumber3", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"));
			map.put("awb3", cust.data("prop~AWBNo"));
			map.put("FullAWBNo3", cust.data("awbNumber3"));
			map.put("AWBNo3", cust.data("awb3"));
			map.put("AWBNo3", cust.data("awb3"));

			/** SSM Message loading **/

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");

			/** XFBL Message loading AWBs **/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
					cust.data("FullAWBNo2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
					cust.data("FullAWBNo3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC"), cust.data("SCC"), cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),
					cust.data("Origin") + ";" + cust.data("Destination"),
					cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "","XFBL_2", true);

			/** XFWB Message loading for AWB 1 **/

			map.put("FullAWBNum", cust.data("awbNumber1"));
			map.put("weight", cust.data("Weight"));
			map.put("pieces", cust.data("Pieces"));
			map.put("volume", cust.data("Volume"));
			map.put("scc", cust.data("SCC"));

			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "","XFWB_WithScreeningInfo", true);


			/** XFWB Message loading for AWB 2 **/

			map.put("FullAWBNum", cust.data("awbNumber2"));
			map.put("weight", cust.data("Weight"));
			map.put("pieces", cust.data("Pieces"));
			map.put("volume", cust.data("Volume"));
			map.put("scc", cust.data("SCC"));

			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "","XFWB_WithScreeningInfo", true);

			/** XFWB Message loading for AWB 3 **/

			map.put("FullAWBNum", cust.data("awbNumber3"));
			map.put("weight", cust.data("Weight"));
			map.put("pieces", cust.data("Pieces"));
			map.put("volume", cust.data("Volume"));
			map.put("scc", cust.data("SCC"));

			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "","XFWB_WithScreeningInfo", true);

			/*** MESSAGE - loading XFFM **********/
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Paris"));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", "Europe/Paris"));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", "Europe/Paris"));

			String routing1[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport"),
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport"),
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport") };

			// ULD Number 1
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);

			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };

			int[] shipments = { 3 };
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing1, uld, shipments);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");

			/** Switch role to Destination **/
			cust.switchRole("Destination", "Origin", "RoleGroup");

			// Checking AWB 2 is fresh or Not

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			cust.setPropertyValue("FullAWBNo4", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),
					proppath);
			cust.setPropertyValue("AWBNo4", cust.data("prop~AWBNo"), proppath);

			/** Mark Flight Movement **/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode", "prop~flightNo", "StartDate");
			FLT006.clickFlightMovementDepartureDetailsLink();
			FLT006.clickFlightMovementArrivalDetailsLink();
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");

			/**** Import Manifest ***/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "prop~flightNo", "StartDate");
			// Breakdown to stamp the discrepancies
			String pmkey = cust.data("UldNum");
			OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			OPR367.enterBdnDetailsforAWB(cust.data("BDNlocation"), cust.data("Pieces"), cust.data("Weight"), "AWBNo1");
			OPR367.enterBdnDetailsforAWB(cust.data("BDNlocation"), cust.data("Pieces"), cust.data("Weight"), "AWBNo2");
			OPR367.addNewAWB("CarrierNumericCode", "prop~AWBNo4", "Pieces", "Weight", "Origin", "Destination", "Pieces",
					"Weight");
			OPR367.SaveDetailsInOPR004();
			OPR367.ClickYesAlert();
			OPR367.clickBreakdownComplete();
			OPR367.verifyWarningMessageWith2AWBsWithDiscrepencies("AWBNo3", "prop~AWBNo4");
			cust.closeTab("OPR367", "Import Manifest");

			/** List Flight Discrepancy **/
			cust.searchScreen("OPR047", "List Flight Discrepancy");
			OPR047.listByFlight("carrierCode", "prop~flightNo", "StartDate");
			int[] verfCols = { 6 };
			String[] actVerfValues = { "UNCM" };
			String[] actVerfValues1 = { "UNCF" };
			OPR047.verifyDiscrepancydetails(verfCols, actVerfValues, "AWBNo3");
			OPR047.verifyDiscrepancydetails(verfCols, actVerfValues1, "prop~AWBNo4");
			cust.closeTab("OPR047", "List Flight Discrepancy");

		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		} finally {
			try {
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
