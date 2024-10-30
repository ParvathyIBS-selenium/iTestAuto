package iascb_164029;

/**  TC_40_Verify shipment is marked non secure when XFWB received with no valid RA , NSC security status for shipment and block is not getting released  when Screening Method is Visual check -AMS
 **/

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
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.AWBClearance_OPR023;
import screens.CaptureAWB_OPR026;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.SecurityAndScreening_OPR339;

public class IASCB_164029_TC_12660 extends BaseSetup

{
	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public GoodsAcceptance_OPR335 OPR335;
	public SecurityAndScreening_OPR339 OPR339;
	public ListMessages_MSG005 MSG005;
	public AWBClearance_OPR023 OPR023;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "iascb_164029";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_12660")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_12660")
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
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	

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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));		
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			//Regulated agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_TypeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));
			map.put("RegulatedAgent", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));

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

			//Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** XFWB Message loading without any RA/KC or SPX or screening Info**/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);
			cust.closeTab("MSG005", "MSG005 - List Messages");

			/**OPR026 - Capture AWB ***/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.clickCheckStatus();
			OPR026.checkStatus("val~Not Validated Agent","close");
			OPR026.clickCheckStatus();
			OPR026.checkStatus("val~AWB Not Secure","close");
			OPR026.clickCheckStatus();
			OPR026.checkSectionStatus("val~Security","IN ERROR");
			//Verify shipment details
			List<String> MandatoryComponents = new ArrayList<String>();
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
			OPR026.verifySCI(cust.data("SCI"));
			//Verify IATA rate and IATA charge
			OPR026.clickChargesAcc();
			OPR026.verifyIATAChargeDetails(cust.data("IATAcharge"), cust.data("IATARate"));
			OPR026.verifySCCPresent(cust.data("SCC"));
			OPR026.verifySCCCodes("VerifySCCExists", "NSC");
			OPR026.verifySCCCodes("VerifySCCNotExists", "SPX");
			OPR026.verifyeCSDiconNotDisplayed();
			cust.closeTab("OPR026", "Capture AWB");

			/*****OPR023 - AWB Clearance *******/            
			//Verify that Screening-ACC3 Block exists
			cust.searchScreen("OPR023", "AWB Clearance");
			OPR023.listAWB("CarrierNumericCode","AWBNo");
			OPR023.verifySCCs("val~NSC");
			OPR023.verifyBlock(cust.data("BlockType"),cust.data("AWBNo"));	
			OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Compliance Block removed");			
			OPR023.closeTab("OPR023", "AWB Clearance");	

			/**** OPR335 - Goods Acceptance ****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptanceWithBlockExists();
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.verifyNotFinalizedReason("val~Blocked for Screening");
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			//Adding VCK as SM
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			//Add Regulated Agent
			OPR339.addAgentDetails("AgentType","AgentCountryId","RegulatedAgentCode","Expiry");
			OPR339.checkNewSecurityStatusGiven();
			OPR339.checkSecurityDataReviewed();
			OPR339.saveSecurityDetails();

			/**** Relist OPR339 - Security & Screening****/
			cust.switchToFrame("contentFrame", "OPR339");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");
			//Verify SCC NSC present and SPX not present
			String[] sccSPX = {"SPX"};
			String[] sccNSC = {"NSC"};
			OPR339.verifySccNotPresent(sccSPX);
			OPR339.verifyScc(sccNSC);
			cust.closeTab("OPR339", "Security & Screening");

			/*****OPR023 - AWB Clearance *******/            
			//Verify that Screening-ACC3 Block Still exists
			cust.searchScreen("OPR023", "AWB Clearance");
			OPR023.listAWB("CarrierNumericCode","AWBNo");
			OPR023.verifySCCs("val~NSC");
			OPR023.verifyBlock(cust.data("BlockType"),cust.data("AWBNo"));	
			OPR023.closeTab("OPR023", "AWB Clearance");	

			/**** OPR335 - Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.verifyNotFinalizedReason("val~Blocked for Screening");
			OPR335.verificationOfNotRFCStatus();	
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			//Screening with Another SM
			OPR339.enterScreeningDetails("ScreeningMethod2", "Pieces", "Weight", "val~Pass");
			OPR339.checkNewSecurityStatusGiven();
			OPR339.checkSecurityDataReviewed();
			OPR339.saveSecurityDetails();

			/**** Relist OPR339 - Security & Screening****/
			cust.switchToFrame("contentFrame", "OPR339");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");
			OPR339.verifyScc(sccSPX);
			OPR339.verifySccNotPresent(sccNSC);
			OPR339.verifyNoBlock();
			cust.closeTab("OPR339", "Security & Screening");

			/*****OPR023 - AWB Clearance *******/            
			//Verify that Screening-ACC3 Block is released
			cust.searchScreen("OPR023", "AWB Clearance");
			OPR023.listAWB("CarrierNumericCode","AWBNo");
			OPR023.verifySCCs("val~SPX");
			OPR023.verifyBlockReleasedForShipment(cust.data("BlockType"),cust.data("FullAWBNo"),cust.data("Origin"));
			OPR023.closeTab("OPR023", "AWB Clearance");	

			/** OPR026 - As is Execute ***/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.verifySCCCodes("VerifySCCExists", "SPX");
			OPR026.verifySCCCodes("VerifySCCNotExists", "NSC");
			OPR026.verifyeCSDicon();
			OPR026.asIsExecute();			
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR335 - Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAcceptanceFinalized("finalised",false);
			OPR335.verificationOfRFCStatus();	
			cust.closeTab("OPR335", "Goods Acceptance");
		}


		catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}