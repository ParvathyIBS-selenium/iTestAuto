package breakdown;

/** Categorization of High, Medium and Low discrepancies at Destination Airport - CDG  **/

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
import screens.CaptureAWB_OPR026;
import screens.CaptureMiscellaneousDiscrepancy_OPR045;
import screens.ImportManifest_OPR367;
import screens.ListDiscrepancies_OPR050;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Cgocxml;
import screens.ImportDocumentation_OPR001;
import screens.Mercury;


public class IASCB_31304_TC_2328 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ImportManifest_OPR367 OPR367;
	public CaptureAWB_OPR026 OPR026;
	public MarkFlightMovements_FLT006 FLT006;
	public MaintainFlightSchedule_FLT005 FLT005;
	public CaptureMiscellaneousDiscrepancy_OPR045 OPR045;
	public ImportDocumentation_OPR001 OPR001;
	public ListDiscrepancies_OPR050 OPR050;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;

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
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR045 = new CaptureMiscellaneousDiscrepancy_OPR045(driver, excelreadwrite, xls_Read);
		OPR001 = new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);
		OPR050 = new ListDiscrepancies_OPR050(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2328")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2328")
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

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY",0, "DAY", "");
			map.put("StartDate", startDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));


			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_WERNL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_WERNL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_WERNL"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_WERNL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_WERNL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_WERNL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_WERNL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_WERNL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_WERNL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_WERNL"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_WERNL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_WERNL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_WERNL"));

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


			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));


			//Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");

			// AWBNumber1
			map.put("awbNumber1", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));


			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");

			// AWBNumber2
			map.put("awbNumber2", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));


			// Checking AWB is fresh or Not (AWBNumber3)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");

			// AWBNumber3
			map.put("awbNumber3", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb3", cust.data("prop~AWBNo"));


			// Checking AWB is fresh or Not (AWBNumber4)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");

			//	 AWBNumber4
			map.put("awbNumber4", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb4", cust.data("prop~AWBNo"));

			libr.quitBrowser();

			/****************** MERCURY *********************/
			driver = libr.relaunchBrowser("chrome");
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/*** Login to cgocxml **********/
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFBL Message loading **/
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
					cust.data("awbNumber3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
					cust.data("awbNumber4") + ";" + cust.data("ManPcs")+";" + cust.data("ManWgt") + ";"+ cust.data("Volume") + ";" + cust.data("ShipmentDesc")};

			String scc[] = { cust.data("SCC"),cust.data("SCC"),cust.data("SCC") };

			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination") };

			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");

			/**** OPR026 - Capture AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("awb1", "CarrierNumericCode");
			// Enter shipment details
			OPR026.enterRouting("Destination", "prop~flight_code");
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");


			/**** OPR026 - Capture AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("awb2", "CarrierNumericCode");
			// Enter shipment details
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination", "prop~flight_code");
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR026 - Capture AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("awb3", "CarrierNumericCode");
			// Enter shipment details
			OPR026.enterRouting("Destination", "prop~flight_code");
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR026 - Capture AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("awb4", "CarrierNumericCode");
			// Enter shipment details
			OPR026.enterRouting("Destination", "prop~flight_code");
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");
			libr.quitBrowser();


			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Login to "CGOCXML"

			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);



			/** XFFM Message loading **/
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String routing1[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"+ cust.data("DestinationAirport"),
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"+ cust.data("DestinationAirport"),
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"+ cust.data("DestinationAirport")};
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			int []shipments={3};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing1, uld,shipments);
			Cgocxml.sendMessageCgoCXML("ICARGO");
		    libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/**Switch role to Destination**/
			cust.switchRole("Destination", "Origin", "RoleGroup");

			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("FlightNo", "StartDate");
			FLT006.clickFlightMovementDepartureDetailsLink();
			FLT006.clickFlightMovementArrivalDetailsLink();
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");		

			/********* OPR001 Import Documentation ***********/
			cust.searchScreen("OPR001", "Import Documentation: OPR001");
			OPR001.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			OPR001.checkAWBDocumentRcvdNotChecked("awb1");
			OPR001.saveDetails();
			OPR001.closeTab("OPR001", "Import Documentation: OPR001");

			/*** OPR045-Capture Miscellaneous Discrepancy ***/
			cust.searchScreen("OPR045", "Capture Miscellaneous Discrepancy");
			OPR045.enterAWB("awb1","CarrierNumericCode");
			OPR045.selectDiscType(cust.data("DisType"));
			OPR045.clickList();	
			OPR045.clickYesNo("yes");
			OPR045.selectDiscCode(cust.data("DisCode").split(",")[0]);
			OPR045.enterFlightDetails("carrierCode","FlightNo","StartDate");
			OPR045.enterRemarks(cust.data("DisRemarks").split(",")[0]);			
			//Creating Doc Discrepancy - MSAW
			OPR045.clickCreateDisc();
			OPR045.verifyWarningMsg("Document Discrepancy Saved Successfully.Do you want to relist ?");
			OPR045.clickYesNo("yes");
			cust.closeTab("OPR045", "Capture Miscellaneous Discrepancy");

			/*** OPR045-Capture Miscellaneous Discrepancy ***/
			cust.searchScreen("OPR045", "Capture Miscellaneous Discrepancy");
			OPR045.enterAWB("awb2","CarrierNumericCode");
			OPR045.selectDiscType(cust.data("DisType"));
			OPR045.clickList();	
			OPR045.clickYesNo("yes");
			OPR045.selectDiscCode(cust.data("DisCode").split(",")[1]);
			OPR045.enterFlightDetails("carrierCode","FlightNo","StartDate");
			OPR045.enterRemarks(cust.data("DisRemarks").split(",")[1]);
			//Creating Doc Discrepancy - FDAW
			OPR045.clickCreateDisc();
			OPR045.verifyWarningMsg("Document Discrepancy Saved Successfully.Do you want to relist ?");
			OPR045.clickYesNo("yes");
			cust.closeTab("OPR045", "Capture Miscellaneous Discrepancy");

			/**** Import Manifest ***/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode","FlightNo", "StartDate");
			//Breakdown to stamp the discrepancies FDCA and MSCA

			OPR367.clickCheckBox_ULD(uldNo);
			OPR367.clickBreakdownButton();
			OPR367.enterBdnDetailsforAWB(cust.data("BDNlocation"), cust.data("RcvdPcs").split(",")[0], cust.data("RcvdWt").split(",")[0], "awb1");
			OPR367.enterBdnDetailsforAWB(cust.data("BDNlocation"), cust.data("RcvdPcs").split(",")[2], cust.data("RcvdWt").split(",")[2], "awb3");
			OPR367.enterBdnDetailsforAWB(cust.data("BDNlocation"), cust.data("RcvdPcs").split(",")[3], cust.data("RcvdWt").split(",")[3], "awb4");		
			OPR367.clickBreakdownComplete();
			OPR367.ClickYesAlert();	
			OPR367.closeFromOPR004();	
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.maximizeAllDetails();
			OPR367.checkAWBDocReceived("awb3");
			OPR367.checkAWBDocReceived("awb4");
			OPR367.SaveDetails();
			//Close flight
			OPR367.closeFlight("Confirmed Discrepancies will be stamped for the following","The specified flight "+cust.data("FlightNo")+" is closed");
			cust.closeTab("OPR367", "Import Manifest");	 

			/** List Discrepancies - OPR050 **/
			cust.searchScreen("OPR050", "List Discrepancies");
			OPR050.listByFlight("carrierCode","FlightNo","StartDate");

			//Verifying discrepancy details
			int[] verfCols={10};
			String[] actVerfValues={"MSAW","FDAW","MSCA","FDCA"};
			String[] pmkey={"awb1","awb2","awb3","awb4"};			
			OPR050.verifyDiscrepancydetails(verfCols,actVerfValues,pmkey);

			//Categorizing Discrepancies as High,Low and Medium
			OPR050.clickCheckbox("MSCA");
			OPR050.clickCategorize();
			OPR050.selectCategoryAndSave("val~H");

			OPR050.clickCheckbox("FDAW");
			OPR050.clickCategorize();
			OPR050.selectCategoryAndSave("val~M");

			OPR050.clickCheckbox("FDCA");
			OPR050.clickCategorize();
			OPR050.selectCategoryAndSave("val~L");

			OPR050.clickCheckbox("MSAW");
			OPR050.clickCategorize();
			OPR050.selectCategoryAndSave("val~M");

			//Filtering based on Categories -High
			OPR050.selectCategory("High");
			OPR050.clickListButton();			
			OPR050.verifyCategoryFilter(true,"val~High",1);
			OPR050.verifyCategoryFilter(false,"val~Low",0);
			OPR050.verifyCategoryFilter(false,"val~Medium",0);
			OPR050.clickPrint();
			cust.printAndVerifyReport("val~List Discrepancy", "OPR050","High","MSCA",cust.data("awb3"));

			//Medium
			OPR050.selectCategory("Medium");
			OPR050.clickListButton();			
			OPR050.verifyCategoryFilter(false,"val~High",0);
			OPR050.verifyCategoryFilter(false,"val~Low",0);
			OPR050.verifyCategoryFilter(true,"val~Medium",2);
			OPR050.clickPrint();
			cust.printAndVerifyReport("val~List Discrepancy", "OPR050","Medium","MSAW","FDAW",cust.data("awb1"),cust.data("awb2"));

			//Low
			OPR050.selectCategory("Low");
			OPR050.clickListButton();			
			OPR050.verifyCategoryFilter(false,"val~High",0);
			OPR050.verifyCategoryFilter(true,"val~Low",1);
			OPR050.verifyCategoryFilter(false,"val~Medium",0);
			OPR050.clickPrint();
			cust.printAndVerifyReport("val~List Discrepancy", "OPR050","Low","FDCA",cust.data("awb4"));

			//Verify Sorting(Alphabetical Order) of Category Column
			OPR050.selectCategory("--Select--");
			OPR050.clickListButton();
			String SortOrderAsc[]={"High","Low","Medium","Medium"};
			OPR050.sortCategoryColumnandVerify(SortOrderAsc);
			String SortOrderDes[]={"Medium","Medium","Low","High"};
			OPR050.sortCategoryColumnandVerify(SortOrderDes);
			cust.closeTab("OPR050","List Discrepancies");			


		} catch (Exception e) {
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