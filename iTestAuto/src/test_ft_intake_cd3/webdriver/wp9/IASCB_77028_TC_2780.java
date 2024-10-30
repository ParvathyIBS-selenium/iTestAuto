package wp9;


import java.util.ArrayList;
import java.util.List;
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
import screens.Cgomon;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;



/**
 * 
 *   TC_01_ Charge code logic when  incoming xFWB have prepaid indicator as true and payment type as PP with FOP as cash
 *
 *
 */

public class IASCB_77028_TC_2780 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public SecurityAndScreening_OPR339 OPR339;
	public CaptureAWB_OPR026 OPR026;
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainFlightSchedule_FLT005 FLT005;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;


	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "wp9";

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
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);


	}

	@DataProvider(name = "TC_2780")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2780")
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

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);	
			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM",0, "DAY", ""));
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());

			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR3"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_FR3"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_FR3"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR3"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR3"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR3"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR3"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR3"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR3"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR3"));
			map.put("ShipperCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR3"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR3"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR3"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));


			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", WebFunctions.getPropertyValue(proppath, "flightNumber"));
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/****************** MERCURY *********************/

			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			map.put("FlightNumber", cust.data("FullFlightNo"));
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

			/**** XFSU-BKD Message loading ****/
			cust.createXMLMessage("MessageExcelAndSheetXFSU", "MessageParamXFSU");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC").split(",")[0]+";"+cust.data("SCC").split(",")[1] };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFWB Message loading ****/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB","MessageParamXFWB");
			String sccs[] = { cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1] };
			// Create XFWB message
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs", sccs);
			//Load XFWB 
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/**** OPR339 - Security & Screening ****/

			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Screening");

			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
			OPR026.clickChargesAcc();
			OPR026.verifyPaymentType("PaymentType");
			OPR026.asIsExecuteOnly();

			// Generate Payment Advice Screen
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			cust.closeTab("OPR026", "Capture AWB");

			/**	Verifying charge code in OPR026***/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "prop~CarrierNumericCode");
			OPR026.clickChargesAcc();
			OPR026.verifyChargeCode("PaymentType");
			cust.closeTab("OPR026", "Capture AWB");

			/** CHECKING XFWB TRIGGERED FOR AWB **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFWB=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination");
			int verfColsXFWB[]={9};
			String[] actVerfValuesXFWB={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFWB, actVerfValuesXFWB, pmKeyXFWB,"val~XFWB",true);
			libr.waitForSync(1); 

			/*** VERIFY THE MESSAGE CONTENTS***/
			map.put("pmkey", pmKeyXFWB);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();

			/**Payment method**/
			msgContents.add("val~<TransportPaymentMethodCode>" + cust.data("PaymentType") +"</TransportPaymentMethodCode>");


			//Verify message contents
			MSG005.verifyMessageContent(msgContents,"XFWB");
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");



			//QUIt browser
			libr.quitBrowser();


			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			//Login to "CGOMON"
			String[] cgomon = libr.getApplicationParams("cgomon");
			driver.get(cgomon[0]); // Enters URL
			cust.loginToCgomon(cgomon[1], cgomon[2]);

			//Verifying Inbound Message
			Cgomon.clickInboundMessage();
			map.put("awbNumber", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFWB", "ICARGO");

			//Verifying Outbound Message
			Cgomon.clickOutboundMessage();
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFWB", "PELICAN");


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

