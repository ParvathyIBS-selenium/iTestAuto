package wp3;

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
import screens.CaptureDGDetails_OPR350;
import screens.Cgocxml;

import screens.Mercury;


/** Test ID : 28263 - TC_01 Q value validation for DG data capture  **/

public class IASCB_28263_2629 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureDGDetails_OPR350 OPR350;
	public CaptureAWB_OPR026 OPR026;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "wp3";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		OPR350 = new CaptureDGDetails_OPR350(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "IASCB_28263")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "IASCB_28263")
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

			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");

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
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));

			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			// AWBNumber1
			map.put("awbNumber1",cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1",cust.data("prop~AWBNo"));
			

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			// AWBNumber2
			map.put("awbNumber2", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb2",cust.data("prop~AWBNo"));
			

			// Checking AWB is fresh or Not (AWBNumber3)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			// AWBNumber2
			map.put("awbNumber3", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb3",cust.data("prop~AWBNo"));
			
			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight


			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			//Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);
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

			/** XFSU-BKD - awb 1**/
			map.put("awbNumber", cust.data("awbNumber1"));
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFSU-BKD - awb2**/
			map.put("awbNumber", cust.data("awbNumber2"));
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFSU-BKD - awb3**/
			map.put("awbNumber", cust.data("awbNumber3"));
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFBL Message loading **/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");

			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc"),cust.data("awbNumber3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
											+ cust.data("Volume") + ";" + cust.data("ShipmentDesc")};
			String scc[] = { cust.data("SCC"), cust.data("SCC"),cust.data("SCC")};

			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination") };

			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 1 **/
			map.put("awbNumber", cust.data("awbNumber1"));
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 2 **/
			map.put("awbNumber", cust.data("awbNumber2"));
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 3 **/
			map.put("awbNumber", cust.data("awbNumber3"));
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");

			/**** Capture DG details screen**********/

			//Verify the behaviour when q value is greater than 1 for awbnumber1

			cust.searchScreen("OPR350", "Capture DG Details");
			String shippingnName=cust.data("Proper shipping name");	
			String packages=cust.data("Netquantityperpackages");
			System.out.println((shippingnName.split(";"))[0]);
			OPR350.listAWB("awb1", "prop~CarrierNumericCode");
			OPR350.captureUnidandProperShippingname("UNID",(shippingnName.split(";"))[0]);
			OPR350.capturePI("PI");
			OPR350.capturePackages(packages.split(",")[0],"No of pack","Netquantity per package unit");
			OPR350.selectReportableQuantity("Reportable quantity");
			OPR350.clickAddButton();
			OPR350.captureUnidandProperShippingname("UNID",shippingnName.split(";")[0]);
			OPR350.capturePI("PI");
			OPR350.capturePackages(packages.split(",")[1],"No of pack","Netquantity per package unit");
			OPR350.selectReportableQuantity("Reportable quantity");
			OPR350.clickAddButton();
			OPR350.selectIdenticalUNID("UNID");
			OPR350.allPackInOneoverPack("AllPackedInOne","No of pack","length","width","height",packages.split(",")[0],packages.split(",")[1],"Maximumquantityperpackage");
			cust.closeTab("OPR350", "Capture DG Details");



			//Verify the behaviour when q value is less  than 1 for awbnumber2

			cust.searchScreen("OPR350", "Capture DG Details");
			OPR350.listAWB("awb2", "prop~CarrierNumericCode");
			OPR350.captureUnidandProperShippingname("UNID",shippingnName.split(";")[0]);
			OPR350.capturePI("PI");
			OPR350.capturePackages(packages.split(",")[2],"No of pack","Netquantity per package unit");
			OPR350.selectReportableQuantity("Reportable quantity");
			OPR350.clickAddButton();		
			OPR350.captureUnidandProperShippingname("UNID",shippingnName.split(";")[0]);
			OPR350.capturePI("PI");
			OPR350.capturePackages(packages.split(",")[3],"No of pack","Netquantity per package unit");
			OPR350.selectReportableQuantity("Reportable quantity");
			OPR350.clickAddButton();
			OPR350.selectIdenticalUNID("UNID");			
			OPR350.allPackInOneoverPack("AllPackedInOne","No of pack","length","width","height",packages.split(",")[2],packages.split(",")[3],"Maximumquantityperpackage");
			cust.closeTab("OPR350", "Capture DG Details");




			//Verify the behaviour when q value is equal to 1 for awbnumber3(In discussion)

			cust.searchScreen("OPR350", "Capture DG Details");
			OPR350.listAWB("awb3", "prop~CarrierNumericCode");
			OPR350.captureUnidandProperShippingname("UNID",shippingnName.split(";")[0]);
			OPR350.capturePI("PI");
			OPR350.capturePackages(packages.split(",")[4],"No of pack","Netquantity per package unit");
			OPR350.selectReportableQuantity("Reportable quantity");
			OPR350.clickAddButton();
			OPR350.captureUnidandProperShippingname("UNID",shippingnName.split(";")[0]);
			OPR350.capturePI("PI");
			OPR350.capturePackages(packages.split(",")[5],"No of pack","Netquantity per package unit");
			OPR350.selectReportableQuantity("Reportable quantity");
			OPR350.clickAddButton();
			OPR350.selectIdenticalUNID("UNID");	
			OPR350.allPackInOneoverPack("AllPackedInOne","No of pack","length","width","height",packages.split(",")[4],packages.split(",")[5],"Maximumquantityperpackage");
			cust.closeTab("OPR350", "Capture DG Details");

		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}
