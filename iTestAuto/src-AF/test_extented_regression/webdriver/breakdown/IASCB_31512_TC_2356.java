package breakdown;
/**  After breakdown in import station warehouse, warehouse check in time should be stamped - Shipment lying in warehouse after breakdown **/
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
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.WarehouseShipmentEnquiry_WHS011;


public class IASCB_31512_TC_2356 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public MaintainFlightSchedule_FLT005 FLT005;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public BreakDownScreen_OPR004 OPR004;
	public WarehouseShipmentEnquiry_WHS011 WHS011;
	public ListMessages_MSG005 MSG005;


	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Breakdown.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "Breakdown_FT";

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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);	
		WHS011 = new WarehouseShipmentEnquiry_WHS011(driver, excelreadwrite, xls_Read);	
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_2356")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2356")
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

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR1"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR1"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR1"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR1"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR1"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR1"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR1"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR1"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR1"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR1"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));


			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("prop~flight_code","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));


			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));



			/** SSM Message loading **/
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");


			/** XFSU-BKD **/
			cust.createXMLMessage("MessageExcelAndSheetXFSU", "MessageParamXFSU");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "BKD", true);

			/** XFBL Message loading **/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = {cust.data("SCC")};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);

			/** XFWB Message loading **/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);


			/** XFFM Message loading **/

			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			//	Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing1, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);


			/*** MSG005 - XTMV Message loading For flight******/

			map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
			cust.createXMLMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XTMV", true);
			cust.closeTab("MSG005", "List Message");


			/** Switch role to Destination **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");


			/******** Import Manifest *********/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode","FlightNo", "StartDate");
			OPR367.enterRemarks();
			OPR367.maximizeAllDetails();
			String ulds[]={cust.data("UldNum")};
			OPR367.verifyUldDetails(1,ulds);
			OPR367.verifyShipment("AWBNo");
			OPR367.SaveDetails();
			cust.closeTab("OPR367", "Import Manifest");	

			/******  Breakdown  *****/
			cust.searchScreen("OPR004", "Breakdown");
			OPR004.listFlightAndULD("UldNum", "carrierCode", "FlightNo", "StartDate");		
			OPR004.breakdownLocIfAutoPopulated();
			String[] pcs={"Pieces"};
			String[] wgt={"Weight"};
			OPR004.piecesWeightIfAutoPopulated(1, pcs, wgt);
			OPR004.clickBreakdownComplete();
			cust.closeTab("OPR004", "Breakdown");

			/*****  Retrieve message time from xFSU-RCF message - MSG005  ****/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.selectStatus("Sent");
			MSG005.clickList();			
			String messagetime=MSG005.retrieveMsgSentTime("AWBNo");
			System.out.println(messagetime);	
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** Warehouse Shipment Enquiry Screen  *****/		
			cust.searchScreen("WHS011", "Warehouse Shipment Enquiry");
			//verifying column Warehouse Check In
			String[] colName={"Warehouse Check In"};
			WHS011.verifyColumn(colName);
			WHS011.verifycheckInTypeDropDown();
			WHS011.verifyFieldDays();
			WHS011.verifyFieldHrs();
			WHS011.verifyFieldMins();
			WHS011.selectCheckinType("checkinType");
			WHS011.enterAWBdetails("CarrierNumericCode","AWBNo");
			WHS011.clickList();
			WHS011.verifyWarehouseCheckinTime(messagetime,10,"AWBNo");
			cust.closeTab("WHS011", "Warehouse Shipment Enquiry");

		}

		catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		finally {
			try {
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}