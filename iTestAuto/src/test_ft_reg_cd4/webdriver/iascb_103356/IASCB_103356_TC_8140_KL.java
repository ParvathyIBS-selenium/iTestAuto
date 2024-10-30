package iascb_103356;

/**  TC_03_To verify newly added fields, filter and onward flight information in import shipment listing Screen  or  OPR043 - (Part shipment) **/


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
import screens.Cgocxml;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ImportShipmentListing_OPR043;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.ExportManifest_OPR344;
import screens.MarkFlightMovements_FLT006;

public class IASCB_103356_TC_8140_KL extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public ImportManifest_OPR367 OPR367;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ImportShipmentListing_OPR043 OPR043;
	public GoodsAcceptance_OPR335 OPR335;
	public SecurityAndScreening_OPR339 OPR339;
	public ExportManifest_OPR344 OPR344;
	public MarkFlightMovements_FLT006 FLT006;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "iascb_103356";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR043= new ImportShipmentListing_OPR043(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_8889")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_8889")
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
			String expSTDtime="";
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

			

			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));

			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury",WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			
			
			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Accepting_Carrier_CodeHUB"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Accepting_CountryIdHUB"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Accepting_TypeHUB"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Accepting_ExpiryHUB"));

			
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Amsterdam");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Amsterdam");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);
			
			
			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/** Flight-1 Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum1 = FlightNum1.replace(cust.data("carrierCode"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum1);
			map.put("FlightNo", FlightNum1.substring(2));
		

			/** Flight-2 Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber2", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			FlightNum2 = FlightNum2.replace(cust.data("carrierCode"), cust.data("carrierCode"));
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo2", FlightNum2.substring(2));
		

			/** Flight-3 Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber3", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum3 = WebFunctions.getPropertyValue(proppath, "flightNumber3");
			FlightNum3 = FlightNum3.replace(cust.data("carrierCode"), cust.data("carrierCode"));
			map.put("FullFlightNo3", FlightNum3);
			map.put("FlightNo3", FlightNum3.substring(2));
			

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			

			libr.quitBrowser();

			/****************** MERCURY *********************/
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** Flight - 1 **/
			map.put("FlightNumber", cust.data("FullFlightNo"));
			map.put("Org", cust.data("Origin"));
			map.put("Des", cust.data("Transit"));
			map.put("ATD",cust.data("ATD_Local"));
			map.put("ATA",cust.data("ATA_Local"));

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			
			/** Flight - 2 **/
			map.put("FlightNumber", cust.data("FullFlightNo2"));
			map.put("Org", cust.data("Origin"));
			map.put("Des", cust.data("Transit"));
			map.put("ATD",cust.data("ATD_Local"));
			map.put("ATA",cust.data("ATA_Local"));

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.returnTosendMessage();
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");

			/** Flight - 3 **/
			map.put("FlightNumber", cust.data("FullFlightNo3"));
			map.put("Org", cust.data("Transit"));
			map.put("Des", cust.data("Destination"));
			map.put("ATD",cust.data("ATD_Local2"));
			map.put("ATA",cust.data("ATA_Local2"));

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.returnTosendMessage();
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");		
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/****** Loading XFBL for first Flight ***/
			// Create the message XFBL with AWB
			map.put("FullFlightNumber", cust.data("FullFlightNo"));
			map.put("Orgn", cust.data("Origin"));
			map.put("Dest", cust.data("Transit"));
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + cust.data("Pieces").split(",")[0] + ";" + cust.data("Weight").split(",")[0]
			+ ";" + cust.data("Volume").split(",")[0] + ";" + libr.data("ShipmentDesc") };
			String sccs[] = { cust.data("SCC")};
			String routings[] = { cust.data("Origin") + ";" + cust.data("Transit") };
			cust.createXFBLMessage("XFBL_2", shipment, sccs, routings);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			/****** Loading XFBL for second Flight ***/
			// Create the message XFBL with AWB
			map.put("FullFlightNumber", cust.data("FullFlightNo2"));			
			map.put("Orgn", cust.data("Origin"));
			map.put("Dest", cust.data("Transit"));
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment1[] = { libr.data("FullAWBNo") + ";" + cust.data("Pieces").split(",")[1] + ";" + cust.data("Weight").split(",")[1]
			+ ";" + cust.data("Volume").split(",")[1] + ";" + libr.data("ShipmentDesc") };
			cust.createXFBLMessage("XFBL_2", shipment1, sccs, routings);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/****** Loading XFBL for third Flight ***/
			// Create the message XFBL with AWB
			map.put("FullFlightNumber", cust.data("FullFlightNo3"));
			map.put("Orgn", cust.data("Transit"));
			map.put("Dest", cust.data("Destination"));
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment2[] = { libr.data("FullAWBNo") + ";" + cust.data("TotalPieces") + ";" + cust.data("TotalWeight")
			+ ";" + cust.data("TotalVolume") + ";" + libr.data("ShipmentDesc") };
			String routings1[] = { cust.data("Transit") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment2, sccs, routings1);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/***** XFWB Loading for AWB -RA,SPX,SM***/
			// Create XFWB message
			map.put("FullFlightNumber", cust.data("FullFlightNo3"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			
			
			/**XFFM Message loading for Flight1 **/
			 
			map.put("flightNumber", FlightNum1);
			map.put("Pcs",cust.data("Pieces").split(",")[0]);
			map.put("Wt", cust.data("Weight").split(",")[0]);
			map.put("volume", cust.data("Volume").split(",")[0]);
			map.put("FFMDate",  cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate2",  cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate3",  cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", "Europe/Amsterdam"));
 
			String uldNo1 = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNo1);
			map.put("ULDNo1", cust.data("UldNum1").replaceAll("[^0-9]", ""));
 
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment3[] = { libr.data("FullAWBNo") + ";" + libr.data("Pcs") + ";" + libr.data("Wt") + ";"
					+ libr.data("volume") + ";" + libr.data("ShipmentDesc") };
			String routing2[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Transit")
			+ ";" + cust.data("TransitAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo1") + ";" + cust.data("carrierCode") };
			//	Create XFFM message
			cust.createXFFMMessage("XFFM", shipment3, sccs, routing2, uld);
			cust.modifyMessageMap("<TransportSplitDescription>T</TransportSplitDescription>","<TransportSplitDescription>P</TransportSplitDescription>");
			Cgocxml.sendMessageCgoCXML("ICARGO");
 
 
			/**XFFM Message loading for Flight2 **/
 
			map.put("flightNumber", FlightNum2);
			map.put("Pcs",cust.data("Pieces").split(",")[1]);
			map.put("Wt", cust.data("Weight").split(",")[1]);
			map.put("volume", cust.data("Volume").split(",")[1]);
 
			String uldNo2 = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum2", uldNo2);
			map.put("ULDNo2", cust.data("UldNum2").replaceAll("[^0-9]", ""));
			String shipment4[] = { libr.data("FullAWBNo") + ";" + libr.data("Pcs") + ";" + libr.data("Wt") + ";"
					+ libr.data("volume") + ";" + libr.data("ShipmentDesc") };
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String uld2[] = { cust.data("UldType") + ";" + cust.data("ULDNo2") + ";" + cust.data("carrierCode") };
			//	Create XFFM message
			cust.createXFFMMessage("XFFM", shipment4, sccs, routing2, uld2);
			cust.modifyMessageMap("<TransportSplitDescription>T</TransportSplitDescription>","<TransportSplitDescription>P</TransportSplitDescription>");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			
			libr.quitBrowser();					

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);


			/** Switch role to Destination **/
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			/** Mark Flight Movement for flight 1 **/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode","FlightNo","StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ETA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterETDDetails("CurrDate", "val~00:00");
			FLT006.enterETADetails("CurrDate","ETA");
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
			
			/** Mark Flight Movement for flight 2 **/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode","FlightNo2","StartDate");
			FLT006.enterETDDetails("CurrDate", "val~00:00");
			FLT006.enterETADetails("CurrDate","ETA");
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
			
		
			/** Import Shipment Listing **/
			cust.searchScreen("OPR043", "Import Shipment Listing");
			OPR043.clickClear();
			OPR043.selectDateType("val~Planned Date");
			OPR043.listAWB(cust.data("CarrierNumericCode"),cust.data("AWBNo"),cust.data("Transit"));
			if(cust.checkDSTExists(startDate,"Europe/Amsterdam"))
			{
			 expSTDtime=cust.data("ATD_LocalwithDST");
			}
			else
			{
			 expSTDtime=cust.data("ATD_LocalwithoutDST");
			}
			String pmKey=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
			//Verifying planned and onward flight details
			int verfCols [] = {23,25};
			String[] actVerfValues = {cust.data("FullFlightNo")+" "+cust.data("StartDate")+ "  , "+ cust.data("ETA")+"(E) / "+ cust.data("FullFlightNo2")+" "+cust.data("StartDate")+ "  , "+ cust.data("ETA")+"(E)",cust.data("FullFlightNo3")+" "+cust.data("StartDate")+ "  , "+expSTDtime+"(S)"}; 
			System.out.println(actVerfValues);
			cust.waitForSync(2);
			OPR043.verifyTableRecords(verfCols, actVerfValues,pmKey );

			OPR043.closeTab("OPR043", "Import Shipment Listing");

			libr.quitBrowser();


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
