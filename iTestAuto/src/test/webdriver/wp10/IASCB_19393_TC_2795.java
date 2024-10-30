package wp10;
/**Verify Container filter in ULD Sighting application **/
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
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.UldSightingHHT;
import screens.Mercury;
import screens.Cgocxml;


public class IASCB_19393_TC_2795 extends BaseSetup {

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
	public MarkFlightMovements_FLT006 FLT006;
	public MaintainFlightSchedule_FLT005 FLT005;
	public UldSightingHHT uldsighthht;
     public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "wp10";

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
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		uldsighthht=new UldSightingHHT(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2794")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2794")
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
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
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

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");

			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("prop~flight_code","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber1
			map.put("awbNumber1", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber1"));


			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber2
			map.put("awbNumber2", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber2"));

			// Checking AWB is fresh or Not (AWBNumber3)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber3
			map.put("awbNumber3", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb3", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber3"));

			// Checking AWB is fresh or Not (AWBNumber4)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber4
			map.put("awbNumber4", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb4", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber4"));

			// Checking AWB is fresh or Not (AWBNumber5)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber5
			map.put("awbNumber5", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb5", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber5"));

			// Checking AWB is fresh or Not (AWBNumber6)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber6
			map.put("awbNumber6", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb6", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber6"));

			// Checking AWB is fresh or Not (AWBNumber7)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber7
			map.put("awbNumber7", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb7", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber7"));

			// Checking AWB is fresh or Not (AWBNumber8)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber8
			map.put("awbNumber8", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb8", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber8"));

			// Checking AWB is fresh or Not (AWBNumber9)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber9
			map.put("awbNumber9", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb9", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber9"));

			// Checking AWB is fresh or Not (AWBNumber10)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber10
			map.put("awbNumber10", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb10", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber10"));

			// Checking AWB is fresh or Not (AWBNumber11)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber11
			map.put("awbNumber11", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb11", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber11"));

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/****************** MERCURY *********************/

			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** MSG005 -SSM Message loading **/
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");


			/***Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);




			/*** MSG005-XFWB message loading AWB 1 ***/
			map.put("FullAWBNo",cust.data("awbNumber1"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");



			/*** MSG005-XFWB message loading AWB 2***/
			map.put("FullAWBNo",cust.data("awbNumber2"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MSG005-XFWB message loading AWB 3 ***/
			map.put("FullAWBNo",cust.data("awbNumber3"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MSG005-XFWB message loading AWB 4 ***/
			map.put("FullAWBNo",cust.data("awbNumber4"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MSG005-XFWB message loading AWB 5 ***/
			map.put("FullAWBNo",cust.data("awbNumber5"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MSG005-XFWB message loading AWB 6 ***/
			map.put("FullAWBNo",cust.data("awbNumber6"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MSG005-XFWB message loading AWB 7 ***/
			map.put("FullAWBNo",cust.data("awbNumber7"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MSG005-XFWB message loading AWB 8***/
			map.put("FullAWBNo",cust.data("awbNumber8"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MSG005-XFWB message loading AWB 9 ***/
			map.put("FullAWBNo",cust.data("awbNumber9"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MSG005-XFWB message loading AWB 10 ***/
			map.put("FullAWBNo",cust.data("awbNumber10"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MSG005-XFWB message loading AWB 11 ***/
			map.put("FullAWBNo",cust.data("awbNumber11"));
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** MSG005 -XFFM Message loading **/

			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));

			// ULD Number 6
			String uldNo6 = cust.create_uld_number("UldType1", "carrierCode");
			map.put("UldNum6", uldNo6);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo6", cust.data("UldNum6").replaceAll("[^0-9]", ""));
			map.put("uldType",cust.data("UldType1"));
			map.put("ULDNo","ULDNo6");

			// ULD Number 7
			String uldNo7 = cust.create_uld_number("UldType1", "carrierCode");
			map.put("UldNum7", uldNo7);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo7", cust.data("UldNum7").replaceAll("[^0-9]", ""));

			map.put("ULDNo","ULDNo7");

			// ULD Number 8
			String uldNo8 = cust.create_uld_number("UldType1", "carrierCode");
			map.put("UldNum8", uldNo8);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo8", cust.data("UldNum8").replaceAll("[^0-9]", ""));

			map.put("ULDNo","ULDNo8");

			// ULD Number 9
			String uldNo9 = cust.create_uld_number("UldType2", "carrierCode");
			map.put("UldNum9", uldNo9);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo9", cust.data("UldNum9").replaceAll("[^0-9]", ""));
			map.put("uldType",cust.data("UldType2"));
			map.put("ULDNo","ULDNo9");

			// ULD Number 10
			String uldNo10 = cust.create_uld_number("UldType2", "carrierCode");
			map.put("UldNum10",uldNo10);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo10", cust.data("UldNum10").replaceAll("[^0-9]", ""));

			map.put("ULDNo","ULDNo10");

			// ULD Number 11
			String uldNo11 = cust.create_uld_number("UldType2", "carrierCode");
			map.put("UldNum11", uldNo11);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo11", cust.data("UldNum11").replaceAll("[^0-9]", ""));

			map.put("ULDNo","ULDNo11");

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");



			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
									cust.data("awbNumber3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
											+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
											cust.data("awbNumber4") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
													+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
													cust.data("awbNumber5") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
															+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
															cust.data("awbNumber6") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
																	+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
																	cust.data("awbNumber7") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
																			+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
																			cust.data("awbNumber8") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
																					+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
																					cust.data("awbNumber9") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
																							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
																							cust.data("awbNumber10") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
																									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
																									cust.data("awbNumber11") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
																											+ cust.data("Volume") + ";" + cust.data("ShipmentDesc")};



			String scc[] = { cust.data("SCC"),cust.data("SCC"),cust.data("SCC"),cust.data("SCC"),cust.data("SCC"),cust.data("SCC"),cust.data("SCC"),cust.data("SCC"),cust.data("SCC"),cust.data("SCC"),cust.data("SCC") };
			String routing[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport"),
							cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
									+ cust.data("DestinationAirport"),
									cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
											+ cust.data("DestinationAirport"),
											cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
													+ cust.data("DestinationAirport"),
													cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
															+ cust.data("DestinationAirport"),
															cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
																	+ cust.data("DestinationAirport"),
																	cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
																			+ cust.data("DestinationAirport"),
																			cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
																					+ cust.data("DestinationAirport"),
																					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
																							+ cust.data("DestinationAirport"),
																							cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
																									+ cust.data("DestinationAirport"),
																									cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
																											+ cust.data("DestinationAirport")};

			String uld[] = { "BLK",cust.data("UldType1") + ";" + cust.data("ULDNo6") + ";" + cust.data("carrierCode") ,cust.data("UldType1") + ";" + cust.data("ULDNo7") + ";" + cust.data("carrierCode"),cust.data("UldType1") + ";" + cust.data("ULDNo8") + ";" + cust.data("carrierCode"),cust.data("UldType2") + ";" + cust.data("ULDNo9") + ";" + cust.data("carrierCode"),cust.data("UldType2") + ";" + cust.data("ULDNo10") + ";" + cust.data("carrierCode"),cust.data("UldType2") + ";" + cust.data("ULDNo11") + ";" + cust.data("carrierCode")};


			int []shipments={11};
			
			int [] distribution= {5,1,1,1,1,1,1};
		
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing, uld,shipments, distribution);
			

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
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("prop~flightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Amsterdam");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
			libr.quitBrowser();

			/***Launch emulator - uldsighting app**/
			libr.launchUldSightingApp("uldsighting-app");

			//Login in to ULD Sighting App
			String [] hht=libr.getApplicationParams("hht");	

			cust.loginHHT(hht[0], hht[1]);
			uldsighthht.clickDone();
			uldsighthht.clickFilter();
			uldsighthht.clickUldType();
			//verfying filter options in ULD Type
			uldsighthht.verifyULDTypeFilterOptions("val~Bulk", "val~Container", "val~Pallet","val~Clear");
			uldsighthht.clickContainer();
			uldsighthht.clickDoneInFilter();
			uldsighthht.clickApply();
			//verifying AKE ulds displayed after applying filter
			uldsighthht.verifyContainerTypeUld("val~AKE");
			libr.quitApp();



		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
