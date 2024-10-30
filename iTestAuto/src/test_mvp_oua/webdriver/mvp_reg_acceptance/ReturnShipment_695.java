package mvp_reg_acceptance;

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
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.ShipperReturnNote_OPR040;

/**
 * Capture return shipment
 **/
public class ReturnShipment_695 extends BaseSetup {

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
	public GeneratePaymentAdvice_CSH007 CSH007;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public ShipperReturnNote_OPR040 OPR040;
	public Mercury mercuryScreen;
	public Cgomon Cgomon;
	public Cgocxml Cgocxml;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_reg_delivery";
	
	/** Cancel button is not working in OPR040 screen so needs to verify the message which is coming for clikcing cancel button**/

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
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR040 = new ShipperReturnNote_OPR040(driver, excelreadwrite, xls_Read);
		mercuryScreen=new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml=new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon=new Cgomon(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "ReturnShipment_695")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "ReturnShipment_695")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);


			//Login to iCargo
			
			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver.get(iCargo[0]);
			Thread.sleep(3000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/**** UPDATING XFWB GENERAL DETAILS IN MAP****/


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
			System.out.println(cust.data("Day"));

			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			
			/**STORING VALUES TO MAP FOR XFWB LOAD **/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			
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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR2"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR2"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR2"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR2"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR2"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR2"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR2"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR2"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR2"));
			
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_US"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_US"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			
			

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			libr.quitBrowser();
			
			
			//Relaunch browser
	        driver=libr.relaunchBrowser("chrome");
	

			// Login to "CGOCXML"
	        
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
			
			//Create XFWB message
	        cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
	    
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			libr.quitBrowser();
			
			//Relaunch browser
	        driver=libr.relaunchBrowser("chrome");
	        
	        driver.get(iCargo[0]);
			Thread.sleep(3000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
						cust.switchRole("Origin", "FCTL", "RoleGroup");
			
			/**** OPR339 - Security & Screening ****/
			
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

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
			
			/** Loose Acceptance : OPR335 **/

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.clickShipperReturn();
			
			/**** Shipper Return Code : OPR040 ****/
			
			int exactStaffId = OPR040.getRandomNumber();
			String resultedStaffID = Integer.toString(exactStaffId);
			map.put("staffId", resultedStaffID);
			String validDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("ValidDate", validDate);
			OPR040.enterStaffIdAndValidUntil("staffId", "ValidDate");
			OPR040.enterReturnPiecesAndWeight("Pieces2", "Weight2");
			OPR040.selectReasonCode("Request From Shipper");
			OPR040.clickSaveForFetchingSRNNO();
			String alertMsg = OPR040.handleAlertAndReturnText();
			System.out.println(alertMsg);
			String exactSRNNo = OPR040.getSRNNo(alertMsg);
			map.put("srnno", exactSRNNo);
			OPR040.clickOkAfterSave();
			cust.closeTab("OPR040", "Shipper Return Note");

			cust.searchScreen("OPR040", "Shipper Return Note");
			// Finalize the payment
			OPR040.listWithSrnNo("srnno");
			OPR040.verifySRNStatus("Issued");
			OPR040.clickPay();

			/*** Generate Payment Screen : CSH007 ***/
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			cust.closeTab("CSH007", "Generate Payment Advice");

			/**** Shipper Return Code : OPR040 ****/

			cust.searchScreen("OPR040", "Shipper Return Note");
			OPR040.listWithSrnNo("srnno");
			OPR040.verifySRNStatus("Paid");
			OPR040.clickAcquitShipment();
			OPR040.verifySRNStatus("Delivered");
			OPR040.clickPrint();
			OPR040.clickReturnPrint();
			cust.printAndVerifyReport("val~Shipper Return Note","OPR040","SRN No.",cust.data("srnno"),"AWB No.",cust.data("prop~CarrierNumericCode"),cust.data("prop~AWBNo"));
			cust.closeTab("OPR040", "Shipper Return Note");
			
			
			cust.searchScreen("OPR040", "Shipper Return Note");
			// Finalize the payment
			OPR040.listWithSrnNo("srnno");
			OPR040.clickAwbDetails("prop~AWBNo");
			OPR040.clickCancel();
			OPR040.verifyMessage("val~Do you want to Cancel this SRN?");
			cust.closeTab("OPR040", "Shipper Return Note");


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
