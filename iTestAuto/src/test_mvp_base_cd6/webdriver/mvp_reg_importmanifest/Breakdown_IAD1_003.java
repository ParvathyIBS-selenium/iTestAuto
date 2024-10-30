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
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
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
 *
BDN of a ULD with dangerous goods to be stored
 *
 */

public class Breakdown_IAD1_003 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public CaptureAWB_OPR026 OPR026;
	public DeliveryDocumentation_OPR293 OPR293;
	public GoodsAcceptance_OPR335 OPR335;
	public BreakDownScreen_OPR004 OPR004;
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
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
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
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_NL"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_NL	"));
			
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_NL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_NL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_NL"));

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

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_NL"));
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("DestinationCountry", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US2"));
		
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

			/****************** MERCURY *********************/
			//Login to "MERCURY"
			map.put("FlightNumber", cust.data("FullFlightNo"));
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

			// Login to iCargo
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/************************* XFWB LOADING FOR AWB 1*********/
			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			//AWBNumber1
			map.put("awbNumber1", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			System.out.println(cust.data("CarrierNumericCode")+"-"+cust.data("awbNumber1"));
			map.put("Pcs", cust.data("Pieces"));
			map.put("Wgt", cust.data("Weight"));
			map.put("Vol", cust.data("Volume"));
			map.put("UNID", cust.data("UNIDDetails").split(",")[0]);

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber2
			map.put("awbNumber2", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
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
			map.put("awbNumber",cust.data("awbNumber1"));

			/***MESSAGE - loading XFWB **********/
			//Create XFWB message            
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			String sccs[]={cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1]};
			cust.createXFWBMessageWithSCCs("XFWB_DGR_MutipleSCCs", sccs);

			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/***** XFWB Loading for AWB 2***/ 
			map.put("awbNumber",cust.data("awbNumber2"));
			/***MESSAGE - loading XFWB **********/
			//Create XFWB message            
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");

			String sccs1[]={cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1]};
			cust.createXFWBMessageWithSCCs("XFWB_DGR_MutipleSCCs", sccs1);
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
         
//			/**** XTMV Message Loading ****/
//			map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
//			cust.createXMLMessage("MessageExcelAndSheetXTMV","MessageParamXTMV");
//			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();
			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			//Login to "MERCURY"
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");
		
			mercuryScreen.returnTosendMessage();
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

			//Switch Role
			cust.switchRole("Destination", "FCTL", "RoleGroup");
			
			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			String pmkey = Excel.getCellValue(path1,sheetName, "Breakdown_IAD1_003", "UldNum");
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
						
			/*******Verify FSU-RCF message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "prop~flightNo", "prop~AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyMessageTriggered("prop~AWBNo", "XFSU-RCF");
			libr.waitForSync(6);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/*******Verify xFSU-NFD message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Notification");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFSUNFD1=cust.data("CarrierNumericCode")+" - "+cust.data("awb1");
			String pmKeyXFSUNFD2=cust.data("CarrierNumericCode")+" - "+cust.data("awb2");
			int verfColsXFSUNFD[]={9};
			String[] actVerfValuesXFSUNFD={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFSUNFD, actVerfValuesXFSUNFD, pmKeyXFSUNFD1,"val~XFSU-NFD",true);
			MSG005.verifyMessageDetails(verfColsXFSUNFD, actVerfValuesXFSUNFD, pmKeyXFSUNFD2,"val~XFSU-NFD",true);
			libr.waitForSync(6); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			libr.quitBrowser();	

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}