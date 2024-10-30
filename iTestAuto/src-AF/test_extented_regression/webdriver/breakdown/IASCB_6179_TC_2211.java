package breakdown;

/**
 * TC_11_FSU-NFD message sending for split shipment
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.DeliveryDocumentation_OPR293;
import screens.ImportDocumentation_OPR001;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

public class IASCB_6179_TC_2211 extends BaseSetup {
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
	public ImportManifest_OPR367 OPR367;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ImportDocumentation_OPR001 OPR001;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public BreakDownScreen_OPR004 OPR004;
	public ListMessages_MSG005 MSG005;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Breakdown.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "Breakdown_FT";
	public DeliveryDocumentation_OPR293 OPR293;



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
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR001 = new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);

	}


	@DataProvider(name = "TC_2211")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;
	}


	@Test(dataProvider = "TC_2211")
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

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY",0, "DAY", "Europe/Amsterdam");
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

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

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
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			libr.quitBrowser();

			//Relaunch browser

			driver=libr.relaunchBrowser("chrome");

			//Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** SSM Message loading **/
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to cgocxml **********/
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);		

			/** XFWB Message loading **/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**XFFM Message Loading **/
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", "Europe/Amsterdam"));
			String shipment[] = {
					cust.data("FullAWBNo") + ";" + cust.data("Pieces1").split(",")[0] + ";" + cust.data("Weight1").split(",")[0] + ";"
							+ cust.data("Volume1").split(",")[0] + ";" + cust.data("ShipmentDesc"),
							cust.data("FullAWBNo") + ";" + cust.data("Pieces1").split(",")[1] + ";" + cust.data("Weight1").split(",")[1] + ";"
									+ cust.data("Volume1").split(",")[1] + ";" + cust.data("ShipmentDesc") };

			String routing[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport"),
							cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
									+ cust.data("DestinationAirport") };
			String scc[] = { cust.data("SCC"), cust.data("SCC") };

			// ULD Number 1
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);

			// ULD Number 2
			String uldNo1 = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNo1);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			map.put("ULDNo1", cust.data("UldNum1").replaceAll("[^0-9]", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode"),  cust.data("UldType") + ";" + cust.data("ULDNo1") + ";" + cust.data("carrierCode") };

			int []shipments={2};
			int [] distribution= {1,1};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing, uld,shipments, distribution);
			cust.modifyMessageMap("<TransportSplitDescription>T</TransportSplitDescription>","<TransportSplitDescription>S</TransportSplitDescription>");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			/*** LOGIN TO ICARGO***/
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			/*** MSG005 - XTMV Message loading For flight******/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
			cust.createXMLMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XTMV", true);
			cust.closeTab("MSG005", "List Message");
			
			

			/** Switch role  **/
			cust.switchRole("Destination","FCTL", "RoleGroup");

			/*** OPR001-Import documentation **/
			cust.searchScreen("OPR001", "Import Documentation: OPR001");
			OPR001.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			//Breakdown
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo"));
			OPR001.clickImportManifest();
			OPR367.SaveDetails();
			String pmkey = cust.data("UldNum");
			OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			String[] Location={cust.data("BDNlocation")};
			String[] Pieces={cust.data("Pieces1").split(",")[0]};
			String[] Weight={cust.data("Weight1").split(",")[0]};
			OPR367.enterBdnLocPiecesandVerifyWeightAutopopulated(1,Location,Pieces,Weight);
			OPR004.clickBreakdownComplete();
			OPR367.closeFromOPR004();
			OPR367.verifyBreakdownImageForMultipleUlds("green","Completed",cust.data("UldNum"));
			OPR367.closeTab("OPR367", "Import Manifest");
			/*******Verify xFSU-RCF message in MSG005******/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFSU1=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsXFSU[]={9};
			String[] actVerfValuesXFSU={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFSU, actVerfValuesXFSU, pmKeyXFSU1,"val~XFSU-RCF",false);
			/*** VERIFY THE MESSAGE CONTENTS***/
			map.put("pmkey",pmKeyXFSU1 );
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			String wtUnit="\"KGM\"";
			String volUnit="\"MTQ\"";
			msgContents.add("val~<AssociatedStatusConsignment>"+"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight1").split(",")[0]+".0"+"</GrossWeightMeasure>"+"\n"+"<GrossVolumeMeasure unitCode="+volUnit+">"+cust.data("Volume1").split(",")[0]+"</GrossVolumeMeasure>"+"\n"+"<PieceQuantity>"+cust.data("Pieces1").split(",")[0]+"</PieceQuantity>"+"\n"+"<TransportSplitDescription>S</TransportSplitDescription>");
			MSG005.verifyMessageContent(msgContents,"XFSU");
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/*** OPR001-Import documentation **/
			cust.searchScreen("OPR001", "Import Documentation: OPR001");
			OPR001.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			//			Breakdown
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo"));
			OPR001.clickImportManifest();
			OPR367.SaveDetails();
			String pmkey1 = cust.data("UldNum1");
			OPR367.clickCheckBox_ULD(pmkey1);
			OPR367.clickBreakdownButton();
			String[] Location1={cust.data("BDNlocation")};
			String[] Pieces1={cust.data("Pieces1").split(",")[1]};
			String[] Weight1={cust.data("Weight1").split(",")[1]};
			OPR367.enterBdnLocPiecesandVerifyWeightAutopopulated(1,Location1,Pieces1,Weight1);
			OPR004.clickBreakdownComplete();
			OPR367.closeFromOPR004();
			OPR367.verifyBreakdownImageForMultipleUlds("green","Completed",cust.data("UldNum1"));
			OPR367.closeTab("OPR367", "Import Manifest");


			/*******Verify xFSU-RCF message in MSG005******/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFSU2=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsXFSU2[]={9};
			String[] actVerfValuesXFSU2={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFSU2, actVerfValuesXFSU2, pmKeyXFSU2,"val~XFSU-RCF",false);

			/*** VERIFY THE MESSAGE CONTENTS***/
			map.put("pmkey",pmKeyXFSU2 );
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents1=new ArrayList<String>();
			msgContents1.add("val~<AssociatedStatusConsignment>"+"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight1").split(",")[1]+".0"+"</GrossWeightMeasure>"+"\n"+"<GrossVolumeMeasure unitCode="+volUnit+">"+cust.data("Volume1").split(",")[1]+"</GrossVolumeMeasure>"+"\n"+"<PieceQuantity>"+cust.data("Pieces1").split(",")[1]+"</PieceQuantity>"+"\n"+"<TransportSplitDescription>S</TransportSplitDescription>");
			MSG005.verifyMessageContent(msgContents1,"XFSU");
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");


			/*********relist- OPR001 Import Documentation ***********/

			cust.searchScreen("OPR001", "Import Documentation: OPR001");
			OPR001.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo"));
			OPR001.captureCheckSheetForDG(true, "leakage");	
			OPR001.clickPouchRcvd();
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo"));
			// Clicking AWB Document recieved checkboxes-nfd trigger point
			OPR001.clickAWBDocumentReceived(cust.data("prop~AWBNo"));
			OPR001.saveDetails();
			OPR001.closeTab("OPR001", "Import Documentation: OPR001");
			/*** MSG005- Verify xFSU-NFD message after saving handover doc operation ***/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Notification");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFSUNFD1 = cust.data("CarrierNumericCode")+ " - " +cust.data("AWBNo");;
			MSG005.verifyIfMessageTriggered(pmKeyXFSUNFD1,cust.data("ProfileId"),"XFSU-NFD",true);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");


			/**********OPR293-Delivery Documentation**********/
			//Capture handover details 
			cust.searchScreen("OPR293", "Delivery Documentation");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
			OPR293.selectAllAWBs();
			map.put("CustomerName",cust.data("ConsigneeCode"));
			OPR293.enterCaptureHandOverDetails();
			OPR293.verifyHandoverTickMark("prop~AWBNo");
			cust.closeTab("OPR293", "Delivery Documentation");

			/*** MSG005- Verify xFSU-AWD message ***/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("AWB Document Delivered");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFSU = cust.data("CarrierNumericCode") + " - " + cust.data("prop~AWBNo");
			int verfColsXFSU1[] = { 9 };
			String[] actVerfValueXFSU = { "Sent" };
			MSG005.verifyMessageDetails(verfColsXFSU1, actVerfValueXFSU, pmKeyXFSU, "val~XFSU-AWD", false);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");


			//verifiying alert sent in msg005 screen for XFSU-Notification msg
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("ALERT");
			MSG005.selectStatus("Sent");
			MSG005.clickList();

			String msgContents2="AWB: "+cust.data("CarrierNumericCode")+ " - " +cust.data("AWBNo");

			MSG005.verifyIfMessageTriggered("ALERT", msgContents2);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

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