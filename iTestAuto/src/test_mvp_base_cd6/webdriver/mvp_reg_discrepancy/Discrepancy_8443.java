package mvp_reg_discrepancy;

/** Found cargo (FDCA) at breakdown level **/

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
import screens.Cgocxml;
import screens.Cgomon;
import screens.DeliveryDocumentation_OPR293;
import screens.GeneratePaymentAdvice_CSH007;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;

public class Discrepancy_8443 extends BaseSetup {

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
	public GeneratePaymentAdvice_CSH007 CSH007;
	public ListMessages_MSG005 MSG005;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public DeliveryDocumentation_OPR293 OPR293;
	public BreakDownScreen_OPR004 OPR004;
	public Mercury mercuryScreen;
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
		// excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon=new Cgomon(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_8443")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_8443")
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
			map.put("StartDate", startDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerId_US"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerName_US"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerpostCode_US"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomerstreetName_US"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercityName_US"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountryId_US"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountryName_US"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "paycargoCustomercountrySubdivision_US"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "paycargoCustomertelephoneNo_US"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "paycargoCustomeremail_US"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			map.put("ReceiptaddressCargoal", WebFunctions.getPropertyValue(telexproppath, "ReceiptaddressCargoal"));
			map.put("ReceiptaddressAfls1", WebFunctions.getPropertyValue(telexproppath, "ReceiptaddressAfls1"));

			/******* OPR026 - Capture AWB *****/
			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No 2
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			cust.setPropertyValue("AWBNo", cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));

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

			/*** MESSAGE - loading XFWB needs to be load from CGOCXML ****/
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/****Load XFWB****/
			cust.createXMLMessage("MessageExcelAndSheetFWB","MessageParamFWB");   
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");		

			/*** MESSAGE - loading and creating XFFM ****/
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
    		String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");		
			String shipment[] = { cust.data("FullAWBNo") + ";" + cust.data("Pieces2") + ";" + cust.data("Weight2") + ";"+  cust.data("Volume2") + ";" +  cust.data("ShipmentDesc")};
			String scc[] = { cust.data("SCC")};
			String routing[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"+ cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };

			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing, uld);
			Cgocxml.sendMessageCgoCXML("ICARGO");
		
//			/**** XTMV Message Loading ****/
//			map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
//			cust.createXMLMessage("MessageExcelAndSheetXTMV","MessageParamXTMV");
//			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();
			
			
			//Relaunch browser
	        driver=libr.relaunchBrowser("chrome");		
	        driver.get(mercury[0]); // Enters URL
	    	cust.loginToMercury(mercury[1], mercury[2]);
	        
	    	/** Loading MVT : DEPARTURE  **/
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

			//Switch role to Destination
			cust.switchRole("Destination", "FCTL", "RoleGroup");
			
			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.clickCheckBox_ULD(uldNo);
			OPR367.clickBreakdownButton();

			OPR367.enterBdnDetailsforAWB(cust.data("Location"), cust.data("RcvdPcs").split(",")[0], cust.data("RcvdWt").split(",")[0], "AWBNo");
			OPR367.SaveDetailsInOPR004();
			//verify Error Message
			cust.verifyErrorMessage("Breakdown", "val~Total Received Pcs/Wt is greater than the stated Pcs/Wt for the AWB(s) "+cust.data("FullAWBNo"));
			
			//Breakdown to stamp FDCA
			OPR367.enterBdnDetailsforAWB(cust.data("Location"), cust.data("RcvdPcs").split(",")[1], cust.data("RcvdWt").split(",")[1], "AWBNo");
			OPR367.SaveDetailsInOPR004();		
			OPR004.closeTab("OPR004", "Breakdown");	

			/*******Verify FSU-RCF message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFSU=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsXFSU[]={9};
			String[] actVerfValuesXFSU={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFSU, actVerfValuesXFSU, pmKeyXFSU,"val~XFSU-RCF",true);
			libr.waitForSync(6);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");         

			/*******Verify xFSU-NFD message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Notification");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyMessageDetails(verfColsXFSU, actVerfValuesXFSU, pmKeyXFSU,"val~XFSU-NFD",true);
			libr.waitForSync(6);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");		

			/** OPR367- Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.clickCheckBox_ULD(uldNo);
			OPR367.clickBreakdownButton();	
			//Breakdown Complete
			OPR004.clickBreakdownComplete();
			OPR367.ClickYesAlert();	
			OPR367.closeFromOPR004();
			OPR367.verifyBreakdownSuccessfullImage();
			
			OPR367.checkAWBDocReceived("AWBNo");
			OPR367.SaveDetails();
			//Close flight
            OPR367.closeFlight("Confirmed Discrepancies will be stamped for the following","The specified flight "+cust.data("FlightNo")+" is closed");
			OPR367.closeTab("OPR367", "Import Manifest");
			
            /******* Verify xFSU-DIS-FDCA message in MSG005 ******/
            cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.enterMsgType("XFSU");
            MSG005.selectMsgSubType("Discrepancy");
            MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
 
            MSG005.verifyIfMessageTriggered(pmKeyXFSU,cust.data("ProfileId"),"XFSU-DIS",true);
            
            MSG005.clickMessageCheckBox("2");
            MSG005.clickView();
            List <String> msgContents=new ArrayList<String>();
            msgContents.add("val~<DiscrepancyDescriptionCode>"+cust.data("val~FDCA")+"</DiscrepancyDescriptionCode>");                    
            MSG005.verifyMessageContent(msgContents,"XFSU-DIS",true);
            MSG005.closeView();
            MSG005.closeTab("MSG005", "MSG005 - List Messages");
            libr.quitBrowser();
							
			


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}