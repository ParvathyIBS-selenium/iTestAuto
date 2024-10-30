package mvp_reg_discrepancy;

import java.util.ArrayList;
import java.util.List;
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
import screens.CaptureAWB_OPR026;
import screens.CaptureMiscellaneousDiscrepancy_OPR045;
import screens.ImportDocumentation_OPR001;
import screens.ListDiscrepancies_OPR050;
import screens.ListMessages_MSG005;


/**
 * [Discrepancies] Missing AWB (MSAW)
 **/
public class Discrepancy_8447 extends BaseSetup {

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
	public ListDiscrepancies_OPR050 OPR050;
	public CaptureMiscellaneousDiscrepancy_OPR045 OPR045;
	public ImportDocumentation_OPR001 OPR001;
	

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_reg_discrepancy";

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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR050 = new ListDiscrepancies_OPR050(driver, excelreadwrite, xls_Read);
		OPR045 = new CaptureMiscellaneousDiscrepancy_OPR045(driver, excelreadwrite, xls_Read);
		OPR001 = new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);
		
	}

	@DataProvider(name = "Discrepancy_8447")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "Discrepancy_8447")
	public void getTestSuite(Map<Object, Object> map) throws Exception {

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

			/** Pre Condition Starts **/
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
		
			/*** Storing Values to Map ***/
			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/
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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US2"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US2"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US2"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US2"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US2"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US2"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US2"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_FR2"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_FR2"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));

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

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			

			/*** MSG005 - SSM Message loading******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");
			


			/****Load XFWB****/
			cust.createXMLMessage("MessageExcelAndSheetFWB","MessageParamFWB");   
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);	


			/**** XFFM Message Creation and Upload ****/
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { cust.data("prop~FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
					+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };

			cust.createXFFMMessage("XFFM", shipment, scc, routing, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);	
		
//			/**** XTMV Message Loading ****/
//			map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
//			cust.createXMLMessage("MessageExcelAndSheetXTMV","MessageParamXTMV");
//			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XTMV", true);
			
			/*** MSG005-- MVT AD loading ****/
			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "MVT_ATD");

			/*** MSG005-- MVT AA loading ****/

			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "MVT_ATA");
			cust.closeTab("MSG005", "List Message");
			

			
			//Switch role to destination
			cust.switchRole("Destination", "FCTL", "RoleGroup");
			
			/********* OPR001 Import Documentation ***********/
			cust.searchScreen("OPR001", "Import Documentation: OPR001");
			OPR001.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			OPR001.checkAWBDocumentRcvdNotChecked("AWBNo");
			OPR001.saveDetails();
			OPR001.closeTab("OPR001", "Import Documentation: OPR001");

			/***** OPR045 - Capture Miscellaneous Discrepancy *******/
			// Create MSAW discrepancy
			cust.searchScreen("OPR045", "Capture Miscellaneous Discrepancy");
			OPR045.selectDiscType("DOCDIS");
			OPR045.listAWB(cust.data("AWBNo"), "CarrierNumericCode", "Capture Miscellaneous Discrepancy");
			OPR045.selectDiscCode("MSAW");
			OPR045.enterRemarks("Missing AWB");
			OPR045.createDisc();
			OPR045.closeTab("OPR045", "Capture Miscellaneous Discrepancy");

			/******* Verify XFSU-DIS message in MSG005 ******/
			// verify XFSU-DIS message with message content as "MSAW"
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Discrepancy");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo","AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.getNumberOfRecordsPresent(cust.data("AWBNo"),1);
			String pmKeyFSUDIS = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo");
			MSG005.verifyIfMessageTriggered(pmKeyFSUDIS,cust.data("ProfileId"),"val~XFSU-DIS",true);
			MSG005.clickMessageCheckBox(cust.data("MsgRef"));
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			/**Verifying MSAW Discrepancy code**/
			msgContents.add("val~<DiscrepancyDescriptionCode>MSAW</DiscrepancyDescriptionCode>");
			MSG005.verifyMessageContent(msgContents,"XFSU",true);
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			libr.quitBrowser();
			
			

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
