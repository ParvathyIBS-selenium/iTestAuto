package mvp_reg_discrepancy;

import java.util.ArrayList;
import java.util.List;

/** [Discrepancies] Found cargo (FDCA) at breakdown level (but the pcs dont exceed the number of stated on the flight) OPR001  **/

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

import screens.ImportManifest_OPR367;

import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Cgocxml;
import screens.Mercury;
import screens.ListMessages_MSG005;
import screens.Cgomon;
import screens.BreakDownScreen_OPR004;


public class Discrepancy_8442 extends BaseSetup {

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
	public ListMessages_MSG005 MSG005;
	public Mercury mercuryScreen;
	public BreakDownScreen_OPR004 OPR004;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "mvp_reg_discrepancy";

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
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);


	}

	@DataProvider(name = "TC_8442")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_8442")
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
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			
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
			
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "credit_CustomerId_TG"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "credit_CustomerName_TG"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "credit_postCode_TG"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "credit_streetName_TG"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "credit_cityName_TG"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "credit_countryId_TG"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "credit_countryName_TG"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "credit_countrySubdivision_TG"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "credit_telephoneNo_TG"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "credit_email_TG"));
			
			
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_FR2"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_FR2"));
			

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "LFW"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			map.put("ReceiptaddressCargoal", WebFunctions.getPropertyValue(telexproppath, "ReceiptaddressCargoal"));
			map.put("ReceiptaddressAfls1", WebFunctions.getPropertyValue(telexproppath, "ReceiptaddressAfls1"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");
			
			
			/******* OPR026 - Capture AWB *****/
			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No 1
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			cust.setPropertyValue("AWBNo", cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();

			/****************** MERCURY *********************/
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			//Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/**ASM Message Loading Needs to be replace with Mercury **/		
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();			

			
			//Create XFWB message
	        cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
		
	       //Relaunch browser
	        driver=libr.relaunchBrowser("chrome");
	       
			// Login to "CGOCXML" and load XFWB message
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			// Load XFFM message
	        
	        /**** XFFM Message Creation and Upload ****/
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { cust.data("prop~FullAWBNo") + ";" + cust.data("Pieces1") + ";" + cust.data("Weight1")
					+ ";" + cust.data("Volume1") + ";" + cust.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			cust.createXFFMMessage("XFFM", shipment, scc, routing, uld);
			Cgocxml.sendMessageCgoCXML("ICARGO");
		    libr.quitBrowser();
	        
	        

			//Relaunch browser
	        driver=libr.relaunchBrowser("chrome");
	        
			
			/** Loading MVT : DEPARTURE  **/
	        driver.get(mercury[0]); // Enters URL
	    	cust.loginToMercury(mercury[1], mercury[2]);
	        
			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");
			
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");
			
			/** Loading MVT : ARRIVAL  **/
			
			mercuryScreen.returnTosendMessage();
			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");
			libr.quitBrowser();
			
		
			//Relaunch browser
	        driver=libr.relaunchBrowser("chrome");
	        
	        driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			  //Switch role
	        cust.switchRole("Destination", "FCTL", "RoleGroup");
          
            /** Import Manifest **/
            
			cust.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("carrierCode","prop~flightNo", "StartDate");
            OPR367.checkAWBDocReceived("AWBNo");
            OPR367.SaveDetails();
			OPR367.closeTab("OPR367", "Import Manifest");
			
			
			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.clickCheckBox_ULD(uldNo);
			OPR367.clickBreakdownButton();			
			OPR367.enterBdnDetailsforAWB(cust.data("Location"), cust.data("Pieces2"), cust.data("Weight2"), "AWBNo");
			//Save details
			OPR367.SaveDetailsInOPR004();	
			OPR004.closeTab("OPR004", "Breakdown");	
			
			/********** CHECKING IF xFSU-RCF GOT TRIGGERD****/


			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.clickList();
			String pmKeyRCF=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsRCF[]={9};
			String[] actVerfValuesRCF={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCF, actVerfValuesRCF, pmKeyRCF,"val~XFSU-RCF",false);
			libr.waitForSync(2); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
			
			/******  Breakdown  *****/
			cust.searchScreen("OPR004", "Breakdown");
			OPR004.listFlightAndULD("UldNum", "carrierCode", "FlightNo", "StartDate");
			OPR004.clickBreakdownComplete();
			OPR004.clickYesAlert();
			OPR004.closeTab("OPR004", "BreakDownScreen");
			
			/********** CHECKING IF xFSU-NFD GOT TRIGGERD****/


			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Notification");
			MSG005.clickList();
			String pmKeyNFD=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsNFD[]={9};
			String[] actVerfValuesNFD={"Sent"};
			MSG005.verifyMessageDetails(verfColsNFD, actVerfValuesNFD, pmKeyNFD,"val~XFSU-NFD",false);
			libr.waitForSync(2); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
			
			/** OPR367- Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			//Close flight
			OPR367.closeFlight("Confirmed Discrepancies will be stamped for the following","The specified flight "+cust.data("FlightNo")+" is closed");
			cust.closeTab("OPR367", "Import Manifest");	       
			
			
			/******* Verify xFSU-DIS-FDCA message in MSG005 ******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Discrepancy");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyDIS = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo");
			MSG005.verifyIfMessageTriggered(pmKeyDIS,cust.data("ProfileId"),"XFSU-DIS",true);
			MSG005.clickMessageCheckBox("2");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			msgContents.add("val~<DiscrepancyDescriptionCode>"+cust.data("val~FDCA")+"</DiscrepancyDescriptionCode>");        			
			MSG005.verifyMessageContent(msgContents,"XFSU-DIS",true);
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
			libr.quitBrowser();
			
			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			//Login to "CGOMON"
			String[] cgomon = libr.getApplicationParams("cgomon");
			driver.get(cgomon[0]); // Enters URL
			cust.loginToCgomon(cgomon[1], cgomon[2]);
			
			
			//Verifying Outbound Message -XFSU-RCF
			Cgomon.clickOutboundMessage();
			map.put("awbNumber", cust.data("prop~CarrierNumericCodeAMS")+"-"+cust.data("prop~AWBNo"));
			Cgomon.enterFromandToDates(cust.createDateFormatWithTimeZone("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormatWithTimeZone("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-RCF");
			Cgomon.enterChannel("MERCURY","Outgoing");
			Cgomon.selectAdvancedSearchOption("val~Recipient");
			Cgomon.enterRecipientAddress("ReceiptaddressCargoal");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFSU-RCF", "Mercury");
			
			Cgomon.cleanDetails();
			
			//Verifying Outbound Message - XFSU-NFD
			Cgomon.enterFromandToDates(cust.createDateFormatWithTimeZone("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormatWithTimeZone("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-NFD");
			Cgomon.enterChannel("MERCURY","Outgoing");
			Cgomon.enterRecipientAddress("ReceiptaddressCargoal");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFSU-NFD", "Mercury");
			
			Cgomon.cleanDetails();
			
			//Verifying Outbound Message -XFSU-DIS
			
			Cgomon.enterFromandToDates(cust.createDateFormatWithTimeZone("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormatWithTimeZone("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-DIS");
			Cgomon.enterChannel("MERCURY","Outgoing");
			Cgomon.enterRecipientAddress("ReceiptaddressAfls1");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFSU-DIS", "Mercury");
			
			libr.quitBrowser();
			
		

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}


}
