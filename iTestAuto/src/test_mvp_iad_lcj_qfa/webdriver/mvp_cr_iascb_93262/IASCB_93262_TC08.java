package mvp_cr_iascb_93262;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
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
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.GeneratePaymentAdvice_CSH007;
import screens.ListMessages_MSG005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;

/**
 * Test Case Name :  Autopopulate SCI field based on EU region
 *TC_08_SCI field should be auto populated as T1 in Capture AWB Screen OPR026 if the Special Customs Information(SCI) details
 * are not received through XFWB message for Non EU Ports
 *
 **/

/*As per design document in the Step 6 its mentioned as 
Step 6 - Verify SCI field is auto populated for Non EU Port
1. Verify SCI field is Auto Populated as T1 by default if (X)FWB message doesn't contain SCI Information
2. Verify user can able to select the details from SCI drop down
3. Click on Save button

Answer : Again select as T1
 */
public class IASCB_93262_TC08 extends BaseSetup {

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
	public ListMessages_MSG005 MSG005;
	public SecurityAndScreening_OPR339 OPR339;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public GeneratePaymentAdvice_CSH007 CSH007;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_cr_iascb_93262";

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
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "IASCB_93262_TC08")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "IASCB_93262_TC08")
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
			

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_US"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_US"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("AgentStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US"));
			map.put("AgentCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("AgentCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US"));
			map.put("ShipperCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR2"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR2"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR2"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR2"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR2"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR2"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR2"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR2"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR2"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

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

			/****** OPR026-CAPTURE AWB ***/

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),
					proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			libr.quitBrowser();

			/****************** MERCURY *********************/
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");
			libr.quitBrowser();


			/*** LOADING XFBL - CGOCXML ***/

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** LOADING XFWB ***/
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


			/**** OPR026 - Capture AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			//Verify SCI
			OPR026.verifySCI(cust.data("SCI"));
			OPR026.selectSCI("SCI");
			//verify shipment details
			List<String> MandatoryComponents=new ArrayList<String>();
			MandatoryComponents.add(cust.data("Origin"));
			MandatoryComponents.add(cust.data("Destination"));
			MandatoryComponents.add(cust.data("carrierCode"));
			MandatoryComponents.add(cust.data("Destination"));
			MandatoryComponents.add(cust.data("AgentCode"));
			MandatoryComponents.add(cust.data("ShipperCode"));
			MandatoryComponents.add(cust.data("ConsigneeCode"));
			MandatoryComponents.add(cust.data("Pieces"));
			MandatoryComponents.add(cust.data("Weight"));
			MandatoryComponents.add(cust.data("CommodityCode"));
			OPR026.verifyXFWBMandatoryComponents(MandatoryComponents);
			cust.closeTab("OPR026", "Capture AWB");

			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
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

			/** CHECKING XFWB TRIGGERED FOR AWB **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFWB1 = cust.data("CarrierNumericCode") + " - " + cust.data("prop~AWBNo") + " - "
					+ cust.data("Origin") + 	" - " + cust.data("Destination");
			int verfColsXFWB1[] = { 9 };
			String[] actVerfValuesXFWB1 = { "Sent" };
			MSG005.verifyMessageDetails(verfColsXFWB1, actVerfValuesXFWB1, pmKeyXFWB1, "val~XFWB", true);
			libr.waitForSync(1);
			cust.closeTab("MSG005", "List Message");

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}

	}
}
