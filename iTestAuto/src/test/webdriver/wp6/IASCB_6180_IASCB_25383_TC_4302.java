package wp6;


import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import screens.ListMessages_MSG005;
import common.Xls_Read;
import controls.ExcelRead;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.LoadUnloadRFSSST;


/**  TC_27_Validate the new fields of seal state and Trucking company when RFS Inbound token generation -KL **/


public class IASCB_6180_IASCB_25383_TC_4302 extends BaseSetup {

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
	public LoadUnloadRFSSST ldRfssst;
	public Mercury mercuryScreen;
	public ListMessages_MSG005 MSG005;
	
	public Cgocxml Cgocxml;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String proppathsst = "\\src\\resources\\SSTLocators.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "wp6";

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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		ldRfssst=new LoadUnloadRFSSST(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_4302")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_4302")
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

			/** Switch role to Origin **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Amsterdam");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "Europe/Amsterdam");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMMM",0, "DAY", "Europe/Amsterdam"));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Amsterdam"));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "Europe/Amsterdam");
			map.put("XFWBDate", flightdate1);

			


			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_ES"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_ES"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_ES"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_ES"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_ES"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_ES"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_ES"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_ES"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_ES"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_ES"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_ES"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_ES"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_ES"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "MAD"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Amsterdam");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Amsterdam");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);
			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));
		


			/******* OPR026 - Capture AWB *****/

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));

			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight details
			
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code_KL")+cust.data("prop~flightNo"),proppath);
	 		String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code_KL"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("FlightNumber", cust.data("FullFlightNo"));
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			/****************** MERCURY *********************/

			//	 Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** SSM Message loading **/

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
			
			/**XFBL Message loading **/

			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC").split(",")[0]+ ";"+cust.data("SCC").split(",")[1]};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			
			/**** XFWB Message loading ****/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			String sccs[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1]};
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs", sccs);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/** -XFFM Message loading **/

			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", "Europe/Amsterdam"));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", "Europe/Amsterdam"));
			
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			//	Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing1, uld);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			
			
			
			
			libr.quitBrowser();
			
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);


			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);


			/**XTMV  Message Loading **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
			cust.createXMLMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XTMV", true);
			cust.closeTab("MSG005", "List Message");
		libr.quitBrowser();



			/***Launch emulator - sst**/
			libr.launchSSTApp("sst_smartlox-app", true);

			//Login in to SST
			String [] sst=libr.getApplicationParams("hht2");	
			cust.loginSST(sst[0], sst[1],"Bonded",true);


			/*** TOKEN GENERATION IN DROP OFF PICK UP SST SCREEN**/
			ldRfssst.invokeLoadUnloadRFSSSTScreen();
			ldRfssst.addFlightDetails("prop~flight_code_KL", "FlightNo","CurrentDate");
			ldRfssst.clickProceed();
			ldRfssst.enterDriverDetailsWithScroll("StartDate","Destination");
			ldRfssst.verifyFiledIsPresent("val~Seal Numbers");
			String sealNumber= cust.create_sealNumber();
			map.put("SealNo", sealNumber);
			ldRfssst.enterSealNumber("SealNo");
			ldRfssst.clickProceed();
			ldRfssst.verifyMessageDisplayed("Trucking Company Name is Mandatory");
			ldRfssst.verifyFiledIsPresent("val~Seal State");
			ldRfssst.selectsealBrokenState();
			cust.scrollMobileDevice("Trucking Company Name");
			ldRfssst.verifyFiledIsPresent("val~Trucking Company Name");
			ldRfssst.enterTruckingCompanyName("TruckCompanyCode","TruckingCompany");
			ldRfssst.clickProceed();
			ldRfssst.selectVehicletype("VehicleType");
			ldRfssst.clickProceed();
			//verifying token generated
			libr.waitForSync(5);
			ldRfssst.verifyTokenGeneration("TokenId");
			ldRfssst.verifyDirectedToCounter("val~Counter");
			libr.quitApp();



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