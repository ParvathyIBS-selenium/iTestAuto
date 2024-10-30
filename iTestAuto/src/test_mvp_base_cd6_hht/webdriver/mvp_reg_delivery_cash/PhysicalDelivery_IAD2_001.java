package mvp_reg_delivery_cash;

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
import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.CaptureIrregularityHHT;
import screens.CaptureIrregularity_OPR342;
import screens.DeliverCargo_OPR064;
import screens.DeliveryDocumentation_OPR293;
import screens.DeliveryHHT;
import screens.ImportManifest_OPR367;
import screens.ListIrregularity_OPR341;
import screens.ListMessages_MSG005;
import screens.MarkFlightMovements_FLT006;
import screens.TracingReports_TRC006;

/**
 * Capture delivery of shipments with irregularities (e.g damaged =>TRC006)
 **/
public class PhysicalDelivery_IAD2_001 extends BaseSetup {

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
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public DeliveryDocumentation_OPR293 OPR293;
	public BreakDownScreen_OPR004 OPR004;
	public CaptureIrregularity_OPR342 OPR342;
	public ListIrregularity_OPR341 OPR341;
	public TracingReports_TRC006 TRC006;
	public DeliverCargo_OPR064 OPR064;
	public DeliveryHHT deliveryhht;
	public CaptureIrregularityHHT cihht;
	
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_reg_delivery";

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
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR342 = new CaptureIrregularity_OPR342(driver, excelreadwrite, xls_Read);
		OPR341 = new ListIrregularity_OPR341(driver, excelreadwrite, xls_Read);
		TRC006 = new TracingReports_TRC006(driver, excelreadwrite, xls_Read);
		OPR064 = new DeliverCargo_OPR064(driver, excelreadwrite, xls_Read);
		deliveryhht = new DeliveryHHT(driver, excelreadwrite, xls_Read);
		cihht = new CaptureIrregularityHHT(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "PhysicalDelivery_IAD2_001")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "PhysicalDelivery_IAD2_001")
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

			/** Pre Condition Starts **/
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
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR2"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR2"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR2"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR2"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR2"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR2"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR2"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR2"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR2"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US2"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US2"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US2"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US2"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US2"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US2"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US2"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US2"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US2"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US2"));

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

			/*MSG005-SSM Message Loading**/
			cust.searchScreen("MSG005", "MSG005 - List Messages");	
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");

			/****Load XFWB***/ 
			cust.createXMLMessage("MessageExcelAndSheetFWB","MessageParamFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);
		  
			/*** MESSAGE - loading and creating XFFM ****/
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String shipment[] = { cust.data("prop~FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { "BLK" };

			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);

			/**** XTMV Message Loading ****/
			/***map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
			cust.createXMLMessage("MessageExcelAndSheetXTMV","MessageParamXTMV");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XTMV", true);
			cust.closeTab("MSG005", "List Message");***/
			
			/*** MSG005-- MVT AD loading ****/
			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "MVT_ATD");

			/*** MSG005-- MVT AA loading ****/

			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "MVT_ATA");
		    cust.closeTab("MSG005", "List Message");
				
			//Switch role
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			map.put("pmkey", cust.data("val~BULK"));
			OPR367.clickCheckBox("pmkey");
			OPR367.verifyBreakdownInstructionsTag("val~Breakdown");
			OPR367.enterBreakdownDetails("Location", "Pieces", "Weight");
			OPR367.clickBreakdownComplete();
			OPR004.closeTab("OPR004", " Breakdown");

			/**** TRC006 - Tracing Reports ******/
			cust.searchScreen("TRC006", "Tracing Reports");
			TRC006.addReportType("Damage Report");
			TRC006.listReportWithAWB("AWBNo", "CarrierNumericCode");
			TRC006.addDamageDetails("DmgCode", "Pieces2", "Remarks");
			TRC006.printDamage();
			TRC006.closeTab("TRC006", "Tracing Reports");

			/****** OPR342 - Capture Irregularity ******/
			cust.searchScreen("OPR342", "Capture Irregularity");
			OPR342.listAWB("AWBNo", "CarrierNumericCode");
			OPR342.selectOperation("Damage");
			OPR342.clickIrregularitySelect("ULD Damage");
			OPR342.enterRemarks("Test");
			OPR342.clickSave();
			OPR342.closeTab("OPR342", "Capture Irregularity");
				
			/************** OPR341 - List Irregularity ******/
			// Verify Irregularity (ULD Damage) for AWB
			cust.searchScreen("OPR341", "List Irregularity");
			OPR341.listAWB("AWBNo", "CarrierNumericCode", "List Irregularity");
			int verfCols[] = { 6 };
			String actVerfValues[] = { "Damage" };
			OPR341.verifyIrregularityDetailsValue(verfCols, actVerfValues, "ULD Damage");
			OPR341.closeTab("OPR341", "List Irregularity");		

			/********** OPR293-Delivery Documentation **********/
			// Capture hand-over details and generate delivery id
			cust.searchScreen("OPR293", "Delivery Documentation");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Delivery Documentation");
			OPR293.verifyCustCode("ConsigneeCode");
			OPR293.generateDeliveryID3("Remarks", "CASH");
			OPR293.verifyDNStatus("Paid");
			OPR293.selectAllAWBs();
			OPR293.enterCaptureHandOverDetails();
			OPR293.verifyHandoverTickMark("AWBNo");
			OPR293.closeTab("OPR293", "Delivery Documentation");	
			
			/*** Launch emulator - hht **/
 			libr.launchApp("hht-app-release");
 		
 			// Login in to HHT
 			String[] hht = libr.getApplicationParams("hht3");
 			cust.loginHHT(hht[0], hht[1]);
			
			/** Delivery HHT **/
			//Perform delivery in HHT by listing the AWBnumber
			deliveryhht.invokeDeliveryHHTScreen();
			deliveryhht.enterAWBULDNum("awbNumber");
			deliveryhht.clickNext();
			deliveryhht.clickPendingButton();
			deliveryhht.selectShipment();
			deliveryhht.clickSelectAll();
			deliveryhht.enterPieces("Pieces2");
			deliveryhht.clickNext();
			deliveryhht.enterDeliverRemarks("val~Delivered");
			deliveryhht.enterCustomsReferenceNumber("customRefNo");
			deliveryhht.clickNext();
			deliveryhht.verifyDeliveryStatus("val~INPROGRESS");
			libr.quitApp();

			/********** CHECKING IF xFSU-DLV GOT TRIGGERD****/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Delivery");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyDLV=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsDLV[]={9};
			String[] actVerfValuesDLV={"Sent"};
			MSG005.verifyMessageDetails(verfColsDLV, actVerfValuesDLV, pmKeyDLV,"val~XFSU-DLV",false);
			MSG005.clickMessageCheckBox("2");
			MSG005.clickView();	        
			List <String> msgContentsPresent=new ArrayList<String>();	
			String wtUnit="\"KGM\"";
			msgContentsPresent.add("val~<ReasonCode>DLV</ReasonCode>"+"\n"+"<AssociatedStatusConsignment>"+"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight2")+".0</GrossWeightMeasure>"+"\n"+"<PieceQuantity>"+cust.data("Pieces2")+"</PieceQuantity>");
			MSG005.verifyMessageContent(msgContentsPresent,"XFSU-DLV",true);
			MSG005.closeView();		
			libr.waitForSync(2);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
