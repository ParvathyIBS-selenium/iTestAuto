package mvp_reg_importmanifest;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.DeliveryDocumentation_OPR293;
import screens.GoodsAcceptance_OPR335;
import screens.ImportDocumentation_OPR001;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;


import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

/**
 * 
 *BDN of a ULD with multiple shipments to be stored in different locations
 *
 *
 */



public class Breakdown_IAD1_002 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ListMessages_MSG005 MSG005;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public CaptureAWB_OPR026 OPR026;
	public DeliveryDocumentation_OPR293 OPR293;
	public GoodsAcceptance_OPR335 OPR335;
	public BreakDownScreen_OPR004 OPR004;
	public ImportDocumentation_OPR001 OPR001;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_reg_importmanifest";

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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR001=new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon=new Cgomon(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "ImportManifestIAD1_001")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "ImportManifestIAD1_001")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);




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
			
			/******** TELEX ADDRESS****/
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

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
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR2"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR2"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR2"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_EG3"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_EG3"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_EG3"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_EG3"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_EG3"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_EG3"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_EG3"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_EG3"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_EG3"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_EG3"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_FR2"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_FR2"));
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CAI"));



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

			/****************** MERCURY *********************/

			//Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/**ASM Message Loading Needs to be replace with Mercury **/
			map.put("FlightNumber", cust.data("FullFlightNo"));
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();


			// Login to iCargo
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			/** Switch role  **/
			cust.switchRole("Origin","FCTL", "RoleGroup");

			/************************* XFWB LOADING FOR AWB *********/

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber1
			map.put("awbNumber1", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			System.out.println(cust.data("prop~CarrierNumericCode")+"-"+cust.data("awbNumber1"));

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber2
			map.put("awbNumber2", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber2"));

			libr.quitBrowser();

			/************ LOADING XFWB MESSAGE VIA CGOCXML***/
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/***** XFWB Loading for AWB 1***/ 

			map.put("DestinationLoc",cust.data("Destination"));
			map.put("awbNumber",cust.data("awbNumber1"));

			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetFWB","MessageParamFWB");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/***** XFWB Loading for AWB 2***/ 


			map.put("awbNumber",cust.data("awbNumber2"));

			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetFWB","MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");





			/****** XFFM LOADING****/

			/*** MESSAGE - loading and creating XFFM ****/

			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			//ULD Number
			String uldNo=OPR335.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));	
			System.out.println(cust.data("ULDNo"));



			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
					+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc"),cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
					+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") ,cust.data("SCC")};
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport"),cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType")+";"+ cust.data("ULDNo")+";"+cust.data("carrierCode")};

			int []shipments={2};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing, uld,shipments);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();



			//Login to "MERCURY"
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/**MVT Message Loading Needs to be replace with MVT in case of Mercury **/

			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("val~RSYIDAF", "val~QVIFFAF",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");
			mercuryScreen.returnTosendMessage();
			/**MVT Message Loading Needs to be replace with ASM in case of Mercury **/
			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");

			libr.quitBrowser();


			
			/***** RELOGIN TO ICARGO***/
			driver=libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);


			/** Switch role  **/
			cust.switchRole("Destination","FCTL", "RoleGroup");

			/** Import Manifest **/

			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code", "FlightNo", "StartDate");
			String pmkey = Excel.getCellValue(path1,sheetName, "Breakdown_IAD1_002", "UldNum");
			OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			String[] Location={cust.data("BDNlocation").split(",")[0],cust.data("BDNlocation").split(",")[1]};
			String[] Pieces={cust.data("RcvdPcs"),cust.data("RcvdPcs")};
			String[] Weight={cust.data("RcvdWt"),cust.data("RcvdWt")};
			OPR367.enterBdnDetails_multipleShipments(2,Location,Pieces,Weight);
			OPR004.clickBreakdownComplete();
			OPR367.closeFromOPR004();
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");
			
		
			
			/*******Verify xFSU-RCF message in MSG005******/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFSU1=cust.data("prop~CarrierNumericCode")+" - "+cust.data("awb1");
			String pmKeyXFSU2=cust.data("prop~CarrierNumericCode")+" - "+cust.data("awb2");
			int verfColsXFSU[]={9};
			String[] actVerfValuesXFSU={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFSU, actVerfValuesXFSU, pmKeyXFSU1,"val~XFSU-RCF",true);
			MSG005.verifyMessageDetails(verfColsXFSU, actVerfValuesXFSU, pmKeyXFSU2,"val~XFSU-RCF",true);
			libr.waitForSync(6); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
		

			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			//Login to "CGOMON"
			String[] cgomon = libr.getApplicationParams("cgomon");
			driver.get(cgomon[0]); // Enters URL
			cust.loginToCgomon(cgomon[1], cgomon[2]);

			//Verifying Inbound Message
			Cgomon.clickInboundMessage();
			map.put("awbNumber", cust.data("prop~CarrierNumericCode")+"-"+cust.data("awb1"));
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFSU", "ICARGO");
			map.put("awbNumber", cust.data("prop~CarrierNumericCode")+"-"+cust.data("awb2"));
			Cgomon.enterAWB("awbNumber");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFSU", "ICARGO");


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}