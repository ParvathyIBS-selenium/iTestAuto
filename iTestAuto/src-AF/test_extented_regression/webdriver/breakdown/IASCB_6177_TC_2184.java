package breakdown;
import java.util.HashMap;
/**  TC_11_Test for Displaying all the AWBs inside the ULD  **/ 
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
import screens.BreakdownListHHT;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ImportManifest_OPR367;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.BreakDownScreen_OPR004;
import screens.MaintainAndListGenericMasters_SHR097;
import screens.Mercury;
import screens.BreakdownHHT;

public class IASCB_6177_TC_2184 extends BaseSetup {

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
	public MaintainAndListGenericMasters_SHR097 SHR097;
	public BreakdownListHHT bdlsthht;
	public BreakdownHHT bdhht;
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
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		bdlsthht = new BreakdownListHHT(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		FLT006= new  MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004=new BreakDownScreen_OPR004(driver,excelreadwrite,xls_Read);
		SHR097=new MaintainAndListGenericMasters_SHR097(driver,excelreadwrite,xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		bdhht=new BreakdownHHT(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "2174")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "2174")
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

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Amsterdam");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "Europe/Amsterdam");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "Europe/Amsterdam");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Amsterdam"));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam"));



			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

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


			/**Switch role to Origin**/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");

			// AWBNumber1
			map.put("awbNumber1",cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1",cust.data("prop~AWBNo"));

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");

			// AWBNumber2
			map.put("awbNumber2", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb2",cust.data("prop~AWBNo"));


			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("prop~flight_code","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			//Login to "MERCURY"
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

			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");

			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[0],
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[1]};
			String scc[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1]};

			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination") };

			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 1 **/
			map.put("awbNumber", cust.data("awbNumber1"));
			map.put("scc", cust.data("SCC").split(",")[0]);
			map.put("Description", cust.data("ShipmentDesc").split(",")[0]);
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
	

			/** XFWB Message loading for AWB 2 **/
			map.put("awbNumber", cust.data("awbNumber2"));
			map.put("scc", cust.data("SCC").split(",")[1]);
			map.put("Description", cust.data("ShipmentDesc").split(",")[1]);
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/** XFFM Message loading **/
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", "Europe/Amsterdam"));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment1[] = { cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[0],cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[1]};
			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport"), cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport")};
			String uld[] = { cust.data("UldType")+";"+ cust.data("ULDNo")+";"+cust.data("carrierCode")};
			int []shipments={2};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment1, scc, routing1, uld,shipments);
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

			
			/** Mark Flight Movement **/  
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode", "prop~flightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");

			
			/******** Import Manifest *********/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code","FlightNo", "StartDate");
			OPR367.selectBDInstructionforULD("UldNum","BDInstruction");
			OPR367.maximizeAllDetails();
			OPR367.verifyShipment("awb1");
			OPR367.verifyShipment("awb2");
			OPR367.SaveDetails();
			cust.closeTab("OPR367", "Import Manifest");
		   
			/******** Maintain And List Generic Master *********/
			cust.searchScreen("SHR097", "MaintainAndListGenericMaster");
			SHR097.selectMasterType("val~ShpRank Master");
			
			String sccs[]={cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1]};
			HashMap<Integer,String> rankMaster=new HashMap<Integer,String>();
			String sortedSccs[]=new String[sccs.length];
			
			int rank=SHR097.getShipmentRank("ShpRank Master",cust.data("SCC").split(",")[0],cust.data("Destination"));
			rankMaster.put(rank, sccs[0]);
			
			int rank2=SHR097.getShipmentRank("ShpRank Master",cust.data("SCC").split(",")[1],cust.data("Destination"));
			rankMaster.put(rank2, sccs[1]);
			
			//sorting the SCCs based on ranks		
			sortedSccs=SHR097.sortSCCs(sccs, rankMaster);
			cust.closeTab("SHR097", "MaintainAndListGenericMaster");
			libr.quitBrowser();
		
			
			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");		

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - LIST BREAKDOWN ****/
			bdlsthht.invokeBreakdownListHHTScreen();
			bdlsthht.selectBDInstruction("BDInstruction");
			bdlsthht.unselectHandlingArea();
			bdlsthht.clickNext();
			bdlsthht.clickBreakdownListFilter();
			map.put("FlightNum", cust.data("carrierCode") + "-" + cust.data("FlightNo"));
			bdlsthht.selectFlight("FlightNum");
			bdlsthht.clickOK();
			String ulds[]={"UldNum"};
			bdlsthht.verifyUldsPresent(ulds);
			bdlsthht.selectUld("UldNum");
			bdlsthht.clickBreakDown();
			bdhht.clickListAwbArrow();
			bdhht.verifySccRanking(sortedSccs);
			map.put("FullAWBNum1", cust.data("CarrierNumericCode")+" - "+cust.data("awb1")+" ");
			map.put("FullAWBNum2", cust.data("CarrierNumericCode")+" - "+cust.data("awb2")+" ");
			String awbNo[]={cust.data("FullAWBNum1"),cust.data("FullAWBNum2")};
			bdhht.verifyPiecesWeight(awbNo, "Pieces", "Weight", "val~Manifested");
			bdhht.verifyOriginAndDestination(awbNo, "Origin", "Destination");
			libr.quitApp();
			
			

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