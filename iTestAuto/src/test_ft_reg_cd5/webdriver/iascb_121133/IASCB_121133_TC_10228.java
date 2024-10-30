package iascb_121133;

import java.util.Arrays;

/** TC_08_Verify breakdown of part shipment with split SCC **/

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.SecurityAndScreening_OPR339;


public class IASCB_121133_TC_10228 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public BreakDownScreen_OPR004 OPR004;
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainFlightSchedule_FLT005 FLT005;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public ListMessages_MSG005 MSG005;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "IASCB_121133";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004=new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		

	}

	@DataProvider(name = "TC_10228")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_10228")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));


			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Login to iCargo
			
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			/** Switch role to Origin **/
			
			cust.switchRole("Origin", "Origin", "RoleGroup");

			// creating flight number
			String startDate = cust.createDateFormat("dd-MMM-YYYY",0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM",0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			// Checking AWB is fresh or Not
			
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
		
			/** SSM Loading For First Flight **/

			cust.createFlight("FullFlightNumber");
			
			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");
			
			cust.setPropertyValue("flightNo2", cust.data("prop~flightNo"), proppath);

			cust.setPropertyValue("flightNumber2", cust.data("carrierCode") + cust.data("prop~flightNo2"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			System.out.println(FlightNum);
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("fullFlightNum", cust.data("FullFlightNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** MSG005-SSM Message Loading **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");
			cust.closeTab("MSG005", "List Message");

			/** SSM Loading For Second Flight Flight **/

			cust.createFlight("FullFlightNumber");
			
			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");
			
			cust.setPropertyValue("flightNumber", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);

			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			System.out.println(FlightNum2);
			map.put("FullFlightNo1", FlightNum2);
			map.put("FlightNo1", FlightNum2.substring(2));
			map.put("fullFlightNum", cust.data("FullFlightNo1"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			
			System.out.println(cust.data("FullFlightNo1"));
			System.out.println(cust.data("fullFlightNum"));
			
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");
		
						
			/****** MSG005-loading FBL for First Flight ***/

			map.put("FullFlightNumber", cust.data("FullFlightNo"));
			System.out.println(cust.data("FullFlightNumber"));
			
			// Create the message FBL

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());

			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipments1[] = { libr.data("prop~FullAWBNo") + ";" + libr.data("SplitPcs").split(",")[0] + ";" + cust.data("Weight1")
					+ ";" + cust.data("Volume1") + ";" + libr.data("ShipmentDesc1") };
			String sccs1[] = { cust.data("SCC").split(",")[0] };
			String routings1[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipments1, sccs1, routings1);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2",true);
			
			/****** MSG005-loading FBL for Second Flight ***/
			
			map.put("FullFlightNumber", cust.data("FullFlightNo1"));
			System.out.println(cust.data("FullFlightNumber"));
			// Create the message FBL

			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipments2[] = { libr.data("prop~FullAWBNo") + ";" + libr.data("SplitPcs").split(",")[0] + ";" + cust.data("Weight2")
					+ ";" + cust.data("Volume2") + ";" + libr.data("ShipmentDesc2") };
			String sccs2[] = { cust.data("SCC").split(",")[1] };
			String routings2[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipments2, sccs2, routings2);
			
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2",true);

			/*** MESSAGE - loading XFWB **********/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			String sccs[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1] };
			// Create XFWB message
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs", sccs);
			// Create XFWB message
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_MultipleSCCs", true);
			cust.closeTab("MSG005", "List Message");
			
			/**** OPR026 - Capture AWB ****/
			
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			String pcs[] = { libr.data("SplitPcs").split(",")[0], libr.data("SplitPcs").split(",")[1] };
			String wgt[] = { libr.data("Weight1"), libr.data("Weight2") };
			OPR026.splitShipmentWithSCC(libr.data("SCC"), pcs,wgt);
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");
			
			
			/****** XFFM LOADING PART 1****/
			
			map.put("FullFlightNumber", cust.data("FullFlightNo"));
			
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));
			
			//ULD Number
			String uldNo1=OPR335.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNo1);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo1", cust.data("UldNum").replaceAll("[^0-9]", ""));	
			System.out.println(cust.data("ULDNo1"));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String ship1[] = { cust.data("FullAWBNo") + ";" + libr.data("SplitPcs").split(",")[0] + ";" + cust.data("Weight1")
					+ ";" + cust.data("Volume1") + ";" + cust.data("ShipmentDesc1")};
			String scc1[] = {cust.data("SCC").split(",")[0]};
			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport")};
			String uld1[] = { cust.data("UldType")+";"+ cust.data("ULDNo1")+";"+cust.data("carrierCode")};

			// Create XFFM message
			cust.createXFFMMessage("XFFM", ship1, scc1, routing1, uld1);
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			
			/****** XFFM LOADING PART2****/
			
			map.put("FullFlightNumber", cust.data("FullFlightNo1"));
			
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			//ULD Number
			String uldNo2=OPR335.create_uld_number("UldType", "carrierCode");
			map.put("UldNum2", uldNo2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo2", cust.data("UldNum").replaceAll("[^0-9]", ""));	
			System.out.println(cust.data("ULDNo2"));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String ship2[] = { cust.data("FullAWBNo") + ";" + libr.data("SplitPcs").split(",")[1] + ";" + cust.data("Weight2")
					+ ";" + cust.data("Volume2") + ";" + cust.data("ShipmentDesc2")};
			String scc2[] = {cust.data("SCC").split(",")[1]};
			String routing2[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport")};
			String uld2[] = { cust.data("UldType")+";"+ cust.data("ULDNo2")+";"+cust.data("carrierCode")};

			// Create XFFM message
			cust.createXFFMMessage("XFFM", ship2, scc2, routing2, uld2);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");

			/** Switch role to Destination **/
			
			cust.switchRole("Destination", "Origin", "RoleGroup");

			/******** Mark Flight Movement ******/

			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode", "prop~flightNo2", "StartDate");
			FLT006.clickFlightMovementArrivalDetailsLink();
			FLT006.clickFlightMovementDepartureDetailsLink();
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");

			/******** Import Manifest *********/

			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "prop~flightNo2", "StartDate");
			OPR367.verifyShipment("AWBNo");
			OPR367.SaveDetails();
			OPR367.closeTab("OPR367", "Import Manifest");

			/** Breakdown Screen **/
			
			cust.searchScreen("OPR004", "Breakdown");
			OPR004.listFlightwithShipment(cust.data("UldNum"), "carrierCode", "prop~flightNo2", "StartDate");
			System.out.println(cust.data("BDNlocation").split(",")[0]);
			String[] Location = { cust.data("BDNlocation").split(",")[0], cust.data("BDNlocation").split(",")[1] };
			String[] Pieces = { cust.data("UpdatedRcvdPcs").split(",")[0], cust.data("UpdatedRcvdPcs").split(",")[1] };
			String[] Weight = { cust.data("UpdatedRcvdWt").split(",")[0], cust.data("UpdatedRcvdWt").split(",")[1] };
			String scs[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1] };
			
			//Verification of Split scc Summary Table
			
			OPR004.switchInToSplitWindow();
			
			int[] verfPieces0={1};
			String[] actVerfValues2={pcs[0]};
			OPR004.verifySplitSccSummaryDetails(verfPieces0,actVerfValues2,scs[0]);
			
			int[] verfPieces1={1};
			String[] actVerfValues3={pcs[1]};
			OPR004.verifySplitSccSummaryDetails(verfPieces1,actVerfValues3,scs[1]);
			
			OPR004.switchOutToSplitWindow();
			
			//Split Breakdown
			OPR004.captureSplitBreakdownandVerifySccColumn(2, Pieces, Weight, Location, scs);
			
			//Capture check sheet		
			OPR004.clickCheckBoxAll();
			OPR004.clickCaptureCheckSheet();
			OPR004.captureChecksheet(true);
			
			OPR004.saveOPR004Alert();
			String[] SU=OPR004.getSplitSUNumber("AWBNo");
			System.out.println(Arrays.asList(SU));
			OPR004.clickBreakdownComplete(); 
			OPR004.closeTab("OPR004", "Breakdown");
			
			/** Import Manifest **/
			
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "prop~flightNo2", "StartDate");
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");
			
			/*******Verify FSU-RCF message in MSG005******/


			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "prop~flightNo2", "prop~AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyMessageTriggered("prop~AWBNo", "XFSU-RCF");
			libr.waitForSync(6);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
