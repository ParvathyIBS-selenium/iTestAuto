package mvp_reg_exportmanifest;

import java.util.Map;

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
import screens.BreakDownScreen_OPR004;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.DeadloadStatement_OPR063;
import screens.ExportManifest_OPR344;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;

/**
 * Transit shipments from IAD to an offline station.FBL has been received for the onward flight
 **/
public class TransittoOfline_690 extends BaseSetup {

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
	public GeneratePaymentAdvice_CSH007 CSH007;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public BuildupPlanning_ADD004 ADD004;
	public ListMessages_MSG005 MSG005;
	public DeadloadStatement_OPR063 OPR063;
	public ImportManifest_OPR367 OPR367;
	public BreakDownScreen_OPR004 OPR004;
	
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_reg_exportmanifest";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		// excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		ADD004 = new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		OPR063 = new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		

	}

	@DataProvider(name = "ExportAIAWB_689")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "ExportAIAWB_689")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));

			/*** Storing Values to Map ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR2"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR2"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR2"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR2"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR2"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR2"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR2"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR2"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR2"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US3"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US3"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US3"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US3"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US3"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US3"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US3"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US3"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US3"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US3"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_FR2"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_FR2"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "ORD"));
		
			
			//Regulated agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** CREATE FLIGHT 1 **/
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo2", cust.data("prop~flightNo"), proppath);
		
			cust.setPropertyValue("flightNumber2", cust.data("carrierCode") + cust.data("prop~flightNo2"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			map.put("FlightNumber", cust.data("FullFlightNo"));
			map.put("ASMdep", cust.data("ASMdeparture1"));
			map.put("ASMarr", cust.data("ASMarrival1"));
			map.put("Org", cust.data("Origin"));
			map.put("Des", cust.data("Transit"));
		
			// Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");
			
			/*** MSG005 - SSM Message loading******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");
			
			
			/** CREATE FLIGHT 2 **/
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			
			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			System.out.println(FlightNum2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo1", FlightNum2);
			map.put("FlightNo1", FlightNum2.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			map.put("ASMdep", cust.data("ASMdeparture2"));
			map.put("ASMarr", cust.data("ASMarrival2"));
			map.put("Org", cust.data("Transit"));
			map.put("Des", cust.data("Destination"));
			map.put("FlightNumber", cust.data("FullFlightNo1"));

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");
			cust.closeTab("MSG005", "List Message");
			
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/** XFBL LOADING FLIGHT 2 **/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FlightNumber", cust.data("FullFlightNo1"));
			map.put("Org", cust.data("Transit"));
			map.put("Des", cust.data("Destination"));

			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);
			
			
		
			/**** XFWB LOADING ****/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_Transit_MSG", true);
				
			/** MSG005 -XFFM Message loading for AWB **/
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			map.put("UldNum1", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String routing2[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };

			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing2, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);	
	
			/**** XTMV Message Loading ****/
			map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
			cust.createXMLMessage("MessageExcelAndSheetXTMV","MessageParamXTMV");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XTMV", true);
			cust.closeTab("MSG005", "List Message");
			
			
			
			
			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			// Click As Is Execute button
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			/**** OPR339 - Security & Screening ****/	
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");
				
	
			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.verifyBreakdownInstructionsTag("val~Thru unit");
			OPR367.SaveDetails();
			map.put("pmkey", cust.data("UldNum"));
			OPR367.clickCheckBox("pmkey");
			OPR367.clickBreakdownButton();
			OPR367.SaveDetailsInOPR004();
			OPR004.closeTab("OPR004", " Breakdown");
			
		
			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo1", "StartDate");
			System.out.println(cust.data("FlightNo1"));
			OPR344.verifyULDInPlannedSection("UldNum");
			OPR344.assignUldPlanningSection("UldNum");
			OPR344.verifyULDInAssignedShipment("UldNum", true);
			OPR344.clickBuildUpComplete();
			OPR344.clickEditULDdetails("UldNum");
			OPR344.acceptAlertPopUp("val~The ULD is build-up completed. Do you want to proceed?");
			OPR344.verifyULDValues();
			cust.closeTab("OPR344", "Export manifest");

			/** DEAD LOAD STATEMENT - OPR063 **/
			cust.searchScreen("OPR063", "Dead load statement");
			OPR063.listFlightDetails("carrierCode", "FlightNo1", "StartDate");
			OPR063.verifyULDActualWeight("UldNum","actwght");
			OPR063.verifyFlightDetails();
			OPR063.selectULD(cust.data("UldNum"));
			OPR063.clickSendProvisional();
			cust.closeTab("OPR063", "Dead load statement");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo1", "StartDate");
			System.out.println(cust.data("FlightNo1"));
			//OPR344.clickBuildUpComplete();
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			OPR344.finalizeFlight(true);
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export manifest");

			/********** CHECKING IF FSU-PRE GOT TRIGGERD ****/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Prepared for loading");
			MSG005.clickList();
			String pmKeyPRE = cust.data("CarrierNumericCode") + " - " + cust.data("prop~AWBNo");
			int verfColsPRE[] = { 9 };
			String[] actVerfValuesPRE = { "Sent" };
			MSG005.verifyMessageDetails(verfColsPRE, actVerfValuesPRE, pmKeyPRE, "val~XFSU-PRE", false);
			libr.waitForSync(2);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/** CHECKING XFUM TRIGGERED FOR AWB **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");									
			MSG005.enterMsgType("XFUM");
			MSG005.clickList();
			String pmKeyFUM=cust.data("Transit")+" - "+cust.data("UldNum").substring(3,8);
			int  verfColsFUM[]={9};
			String[] actVerfValuesFUM={ "Sent"};
			MSG005. verifyMessageDetails(verfColsFUM,actVerfValuesFUM, pmKeyFUM,"val~xFUM",false);
			libr.waitForSync(1);
			MSG005.closeTab( "MSG005","MSG005 - List Messages");	
				
			/******* Verify xFSU-MAN message in MSG005 ******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Manifest Details");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFSU = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo");
			int verfColsFSU[] = { 9 };
			String[] actVerfValuesFSU = { "Sent" };
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU, "val~XFSU-MAN", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
			/** CHECKING XFFM TRIGGERED FOR FLIGHT **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFFM");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFFM = cust.data("carrierCode") + " - " + cust.data("FlightNo1") + " - " + cust.data("Day")
					+ " - " + cust.data("Month").toUpperCase() + " - " + cust.data("Transit");
			int verfColsXFFM[] = { 9 };
			String[] actVerfValuesXFFM = { "Sent" };
			MSG005.verifyMessageDetails(verfColsXFFM, actVerfValuesXFFM, pmKeyXFFM, "val~XFFM", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
			/** CHECKING XFWB TRIGGERED FOR AWB **/
			 cust.searchScreen("MSG005", "MSG005 - List Messages");
			 MSG005.enterMsgType("XFWB"); MSG005.selectStatus("Sent");
			 MSG005.clickList(); 
			 String pmKeyXFWB=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination"); 
			 int verfColsXFWB[]={9}; 
			 String[]actVerfValuesXFWB={"Sent"};
			 MSG005.verifyMessageDetails(verfColsXFWB, actVerfValuesXFWB,pmKeyXFWB,"val~XFWB",false); 
			 libr.waitForSync(1);
			 MSG005.closeTab("MSG005", "MSG005 - List Messages");	
		

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}