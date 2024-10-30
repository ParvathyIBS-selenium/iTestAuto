package wp12;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
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
import screens.ImportManifest_OPR367;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.ListMessages_MSG005;


//TC_09_Verify split shipment indicator in import manifest screen_Indicator P


public class IASCB_37749_TC_2916 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	public MarkFlightMovements_FLT006 FLT006;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ImportManifest_OPR367 OPR367;
	public ListMessages_MSG005 MSG005;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "wp12";

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
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2916")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2916")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			//Login to iCargo

			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));

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

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// creating flight number 1
			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-yyyy", 7, "DAY", "Europe/Amsterdam");
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Amsterdam"));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "Europe/Amsterdam");
			map.put("XFWBDate", flightdate1);

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			//Maintain Flight Screen (FLT005) . Taking fresh flight

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight 1 details
			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum1 = FlightNum1.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum1);
			map.put("FlightNo", FlightNum1.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum1);



			// creating flight number 2
			cust.createFlight("FullFlightNumber");
			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo2", FlightNum2.substring(2));

			cust.setPropertyValue("flightNo2", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber2", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			//Maintain Flight Screen (FLT005) . Taking fresh flight

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight 2 details
			FlightNum2 = FlightNum2.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo2", FlightNum2.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(FlightNum2);



			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/****************** MERCURY *********************/

			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/**SSM Message loading for flight 1 **/

			map.put("flightNumber", FlightNum1);
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");



			/** SSM Message loading for flight 2 **/
			map.put("flightNumber", FlightNum2);
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.returnTosendMessage();
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");


			/***Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFBL Message loading for flight 1 **/

			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			map.put("flightNumber", FlightNum1);
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment1[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces1") + ";" + libr.data("Weight1") + ";"
					+ libr.data("Volume1") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment1, scc, routing);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFBL Message loading for flight 2 **/

			map.put("flightNumber", FlightNum2);
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment2[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces2") + ";" + libr.data("Weight2") + ";"
					+ libr.data("Volume2") + ";" + libr.data("ShipmentDesc") };

			cust.createXFBLMessage("XFBL_2", shipment2, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFWB Message loading ****/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**XFFM Message loading for Flight1 **/

			map.put("flightNumber", FlightNum1);
			map.put("weight", cust.data("Weight1"));
			map.put("pieces", cust.data("Pieces1"));
			map.put("volume", cust.data("Volume1"));
			map.put("FFMDate",  cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate2",  cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate3",  cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", "Europe/Amsterdam"));

			String uldNo1 = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNo1);
			map.put("ULDNo1", cust.data("UldNum1").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment3[] = { libr.data("FullAWBNo") + ";" + libr.data("pieces") + ";" + libr.data("weight") + ";"
					+ libr.data("volume") + ";" + libr.data("ShipmentDesc") };
			String routing2[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo1") + ";" + cust.data("carrierCode") };
			//	Create XFFM message
			cust.createXFFMMessage("XFFM", shipment3, scc, routing2, uld);
			cust.modifyMessageMap("<TransportSplitDescription>T</TransportSplitDescription>","<TransportSplitDescription>P</TransportSplitDescription>");
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/**XFFM Message loading for Flight2 **/

			map.put("flightNumber", FlightNum2);
			map.put("weight", cust.data("Weight2"));
			map.put("pieces", cust.data("Pieces2"));
			map.put("volume", cust.data("Volume2"));

			String uldNo2 = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum2", uldNo2);
			map.put("ULDNo2", cust.data("UldNum2").replaceAll("[^0-9]", ""));
			String shipment4[] = { libr.data("FullAWBNo") + ";" + libr.data("pieces") + ";" + libr.data("weight") + ";"
					+ libr.data("volume") + ";" + libr.data("ShipmentDesc") };
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String uld2[] = { cust.data("UldType") + ";" + cust.data("ULDNo2") + ";" + cust.data("carrierCode") };
			//	Create XFFM message
			cust.createXFFMMessage("XFFM", shipment4, scc, routing2, uld2);
			cust.modifyMessageMap("<TransportSplitDescription>T</TransportSplitDescription>","<TransportSplitDescription>P</TransportSplitDescription>");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			libr.quitBrowser();


			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			//Login to "MERCURY"

			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** Loading MVT : DEPARTURE for Flight 1  **/

			map.put("flightNumber", FlightNum1);
			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");


			/** Loading MVT : DEPARTURE for Flight 2  **/

			map.put("flightNumber", FlightNum2);
			mercuryScreen.returnTosendMessage();
			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");

			/** Loading MVT : ARRIVAL  for Flight 1**/

			map.put("flightNumber", FlightNum1);
			mercuryScreen.returnTosendMessage();
			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");


			/** Loading MVT : ARRIVAL for flight 2**/

			map.put("flightNumber", FlightNum2);
			mercuryScreen.returnTosendMessage();
			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");


			/***** RELOGIN TO ICARGO***/

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch role
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/** FLT006 -Mark Flight Movements **/

			// Verifying Actual time of Arrival is updated based on the MVT message in FLT006 for Flight1
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode", "FlightNo", "StartDate");

			if(cust.checkDSTExists(startDate,"Europe/Paris"))
			{
				FLT006.verifyATA(cust.data("ATA_Local1"),"1");
			}

			else
			{
				FLT006.verifyATA(cust.data("ATA_Local_withoutDST"),"1");
			}
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");

			// Verifying Actual time of Arrival is updated based on the MVT message in FLT006 for Flight2
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode", "FlightNo2", "StartDate");

			if(cust.checkDSTExists(startDate,"Europe/Paris"))
			{
				FLT006.verifyATA(cust.data("ATA_Local1"),"1");
			}

			else
			{
				FLT006.verifyATA(cust.data("ATA_Local_withoutDST"),"1");
			}
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");


			/*** MSG005- List Messages ***/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFFM");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FFM", "FlightNo", "prop~AWBNo");
			MSG005.selectStatus("ProcessedSuccessfully");
			MSG005.clickList();
			String pmKeyFFM1 = cust.data("carrierCode") + " - " + cust.data("FlightNo") + " - " + cust.data("Day") + " - " + cust.data("Month").toUpperCase()+ " - "+ cust.data("Origin");       
			map.put("pmkey1", pmKeyFFM1);
			MSG005.clickCheckBox("pmkey1");
			MSG005.clickView();
			List <String> msgContentsPresent1=new ArrayList<String>();
			
			/** Verifying P in transport split description tag for flight 1**/
			
			msgContentsPresent1.add("val~<TransportSplitDescription>P</TransportSplitDescription>");
			MSG005.verifyMessageContent(msgContentsPresent1,"XFFM",true);
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
 

			
			/*** MSG005- List Messages ***/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFFM");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FFM", "FlightNo2", "prop~AWBNo");
			MSG005.selectStatus("ProcessedSuccessfully");
			MSG005.clickList();
			String pmKeyFFM2 = cust.data("carrierCode") + " - " + cust.data("FlightNo2") + " - " + cust.data("Day") + " - " + cust.data("Month").toUpperCase()+ " - "+ cust.data("Origin");    
			map.put("pmkey2", pmKeyFFM2);
			MSG005.clickCheckBox("pmkey2");
			MSG005.clickView();
			List <String> msgContentsPresent2=new ArrayList<String>();
			
			/** Verifying P in transport split description tag for flight 2**/
			
			msgContentsPresent2.add("val~<TransportSplitDescription>P</TransportSplitDescription>");
			MSG005.verifyMessageContent(msgContentsPresent2,"XFFM",true);
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
			

			/*****OPR367 - Import Manifest*******/ 
			
			//Verify the AWB details for Flight1
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code","FlightNo", "StartDate");
			OPR367.maximizeAllDetails();
			String ULDNo[]={cust.data("UldNum1")};

			//verify uld details
			OPR367.verifyUldDetails(1,ULDNo);
			OPR367.verifyShipment("AWBNo");
			map.put("expText", "~");
			//Verifying split shipment indicator displayed 
			OPR367.verifySplitShipmentIndicator(1,"expText");
			OPR367.verifySplitShipmentIndicatorColor(1, "val~red");
			OPR367.verifySplitShipmentIndicatorIsPresentLeft("AWBNo");
			OPR367.SaveDetails();
			OPR367.closeTab("OPR367", "Import Manifest");


			/*****OPR367 - Import Manifest*******/
			
			//Verify the AWB details for Flight2
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code","FlightNo2", "StartDate");
			OPR367.maximizeAllDetails();
			String ULDNo2[]={cust.data("UldNum2")};
			//verify uld details
			OPR367.verifyUldDetails(1,ULDNo2);
			OPR367.verifyShipment("AWBNo");

			//Verifying split shipment indicator displayed 
			OPR367.verifySplitShipmentIndicator(1,"expText");
			OPR367.verifySplitShipmentIndicatorColor(1, "val~red");
			OPR367.verifySplitShipmentIndicatorIsPresentLeft("AWBNo");
			OPR367.SaveDetails();
			OPR367.closeTab("OPR367", "Import Manifest");


		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}
