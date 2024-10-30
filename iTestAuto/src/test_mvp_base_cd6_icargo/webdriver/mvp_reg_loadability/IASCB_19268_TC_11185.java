package mvp_reg_loadability;

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
import screens.AWBClearance_OPR023;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.DeadloadStatement_OPR063;
import screens.ExportManifest_OPR344;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainOperationalFlight_FLT003;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;

/** TC_16_Verify Neutral loadability  status of ULD when built up volume in ULD is less than 75 percent and rigid ratio in builtup volume is less than 50 percent***/


public class IASCB_19268_TC_11185 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public ListMessages_MSG005 MSG005;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public DeadloadStatement_OPR063 OPR063;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public MaintainOperationalFlight_FLT003 FLT003;
	public AWBClearance_OPR023 OPR023;
	public GeneratePaymentAdvice_CSH007 CSH007;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_reg_lodability_intake";

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
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR063 = new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);



	}

	@DataProvider(name = "TC_11185")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_11185")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);


			/*** Storing Values to Map ***/
			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP***/
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_US"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_US"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			//Regulated agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry"));



			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());



			/** Pre Condition Starts **/
			cust.createFlight("FullFlightNumber");
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);




			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");


			/*** MSG005 - SSM Message loading******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");
			cust.closeTab("MSG005", "List Message");


			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("awbNumber1", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum1", cust.data("CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			map.put("FullAWBNo1", cust.data("awbNumber1"));
			map.put("AWBNo1", cust.data("awb1"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2
			map.put("awbNumber2", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum2", cust.data("CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));
			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo2", cust.data("awb2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			// Checking AWB is fresh or Not (AWBNumber3)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber3
			map.put("awbNumber3", cust.data("OtherCarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum3", cust.data("OtherCarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb3", cust.data("prop~AWBNo"));
			map.put("FullAWBNo3", cust.data("awbNumber3"));
			map.put("AWBNo3", cust.data("awb3"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			/**** XFSU-BKD Message loading ****/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			map.put("FullAWBNo", cust.data("FullAWBNo1"));
			map.put("Vol", cust.data("Volume").split(",")[0]);
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "BKD", true);


			/**** XFSU-BKD Message loading ****/

			map.put("FullAWBNo", cust.data("FullAWBNo2"));
			map.put("Vol", cust.data("Volume").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "BKD", true);


			/**** XFSU-BKD Message loading ****/

			map.put("FullAWBNo", cust.data("FullAWBNo3"));
			map.put("Vol", cust.data("Volume").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "BKD", true);



			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume").split(",")[0] + ";" + cust.data("ShipmentDesc").split(",")[0],
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume").split(",")[1] + ";" + cust.data("ShipmentDesc").split(",")[1],
									cust.data("awbNumber3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
											+ cust.data("Volume").split(",")[1] + ";" + cust.data("ShipmentDesc").split(",")[2]};
			String scc[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1], cust.data("SCC").split(",")[2]};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination") ,cust.data("Origin") + ";" + cust.data("Destination")};
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);


			/**** XFWB Message loading AWB1 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo1"));
			map.put("scc", cust.data("SCC").split(",")[0]);
			map.put("Shipmentdesc1",cust.data("ShipmentDesc").split(",")[0]);
			map.put("Vol", cust.data("Volume").split(",")[0]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);


			/**** XFWB Message loading AWB2 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo2"));
			map.put("scc", cust.data("SCC").split(",")[1]);
			map.put("Shipmentdesc1",cust.data("ShipmentDesc").split(",")[1]);
			map.put("Vol", cust.data("Volume").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);


			/**** XFWB Message loading AWB3 ****/

			map.put("FullAWBNo", cust.data("FullAWBNo3"));
			map.put("scc", cust.data("SCC").split(",")[2]);
			map.put("Shipmentdesc1",cust.data("ShipmentDesc").split(",")[2]);
			map.put("Vol", cust.data("Volume").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);
			cust.closeTab("MSG005", "List Message");





			/**** OPR339 - Security & Screening -AWB1****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo1", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening -AWB2 ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening -AWB3****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo3"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo3", "OtherCarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");


			/***** OPR026 - Execute AWB -AWB1****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecuteOnly();
			// Generate Payment Advice Screen
			CSH007.verifyServiceCode("val~AWBI");
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			cust.closeTab("OPR026", "Capture AWB");


			/***** OPR026 - Capture AWB -AWB2 ****/
			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			OPR026.asIsExecuteOnly();
			// Generate Payment Advice Screen
			CSH007.verifyServiceCode("val~AWBI");
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			cust.closeTab("OPR026", "Capture AWB");

			/***** OPR026 - Execute AWB -AWB3****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo3", "OtherCarrierNumericCode");
			OPR026.asIsExecuteOnly();
			// Generate Payment Advice Screen
			CSH007.verifyServiceCode("val~AWBI");
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			cust.closeTab("OPR026", "Capture AWB");

			/*************OPR023 - Remove compliance block************/
			cust.searchScreen("OPR023","AWB CLearance");
			OPR023.listAWB("CarrierNumericCode", "AWBNo1");
			OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Customs Block removed");  
			OPR023.closeTab("OPR023", "AWB Clearance");

			/*************OPR023 - Remove compliance block************/
			cust.searchScreen("OPR023","AWB CLearance");
			OPR023.listAWB("CarrierNumericCode", "AWBNo2");
			OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Customs Block removed");  
			OPR023.closeTab("OPR023", "AWB Clearance");

			/*************OPR023 - Remove compliance block************/
			cust.searchScreen("OPR023","AWB CLearance");
			OPR023.listAWB("OtherCarrierNumericCode", "AWBNo3");
			OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Customs Block removed");  
			OPR023.closeTab("OPR023", "AWB Clearance");



			/**** OPR335 -Goods Acceptance for AWB1 ****/

			cust.setPropertyValue("awbNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("awbNo", "CarrierNumericCode", "Goods Acceptance");
			map.put("Vol", cust.data("Volume").split(",")[0]);
			OPR335.verifyAWBDetails("Pieces", "Weight", "Vol");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");


			/**** OPR335 -Goods Acceptance for AWB2 ****/


			cust.setPropertyValue("awbNo",cust.data("AWBNo2"),proppath);	
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("awbNo", "CarrierNumericCode", "Goods Acceptance");
			map.put("Vol", cust.data("Volume").split(",")[1]);
			OPR335.verifyAWBDetails("Pieces", "Weight", "Vol");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");



			/**** OPR335 -Goods Acceptance for AWB3 ****/

			cust.setPropertyValue("awbNo", cust.data("AWBNo3"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("awbNo", "OtherCarrierNumericCode", "Goods Acceptance");
			map.put("Vol", cust.data("Volume").split(",")[1]);
			OPR335.verifyAWBDetails("Pieces", "Weight", "Vol");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");


			/*****OPR344 - Export manifest****/

			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNo=cust.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWBAndContour("UldNum","0","CarrierNumericCode","AWBNo1","Pieces","Weight","contour");
			cust.waitForSync(1);
			OPR344.clickEditULDdetailsByJS("UldNum");
			OPR344.addAWBstoExistingULDwithPcsWeight("UldNum","CarrierNumericCode","AWBNo2","Pieces","Weight");
			cust.waitForSync(1);
			OPR344.addAWBstoExistingULDwithPcsWeight("UldNum","OtherCarrierNumericCode","AWBNo3","Pieces","Weight");
			cust.waitForSync(2);
			cust.closeTab("OPR344", "Export Manifest");


			/** DEAD LOAD STATEMENT - OPR063 **/

			//Verify loadability status getting displayed as Neutral
			cust.searchScreen("OPR063", "Dead load statement");
			OPR063.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			String pmKey = cust.data("Destination");
			int verfCols[]={2};
			String[] actVerfValues={cust.data("UldNum")};
			OPR063.verifyULDDetails(verfCols, actVerfValues, pmKey);
			OPR063.selectULD(cust.data("UldNum"));
			OPR063.clickULDLoadingInstuctor();
			OPR063.verifyLoadabilityStatus(cust.data("LoadabilityStatus"));
			OPR063.closeULDLoadingInstuctor();
			cust.closeTab("OPR063", "Dead load statement");
			libr.quitBrowser();




		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}
