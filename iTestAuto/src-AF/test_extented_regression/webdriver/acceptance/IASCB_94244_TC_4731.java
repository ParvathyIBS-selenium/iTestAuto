package acceptance;

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
import screens.ChecksheetHHT;
import screens.GoodsAcceptanceHHT;
import screens.ListCheckSheetConfig_SHR094;
import screens.ListTemplates_SHR093;
import screens.SecurityAndScreening_OPR339;

/**
 * 
 * TC_07_Verify error thrown when no SCC captured for SU during acceptance
 *
 *
 */

public class IASCB_94244_TC_4731 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public SecurityAndScreening_OPR339 OPR339;
	public CaptureAWB_OPR026 OPR026;
	public ListCheckSheetConfig_SHR094 SHR094;
	public ListTemplates_SHR093 SHR093;
	public GoodsAcceptanceHHT gahht;
	public ChecksheetHHT checkhht;
	public Cgocxml Cgocxml;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Acceptance.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "Acceptance_FT";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		SHR093 = new ListTemplates_SHR093(driver, excelreadwrite, xls_Read);
		SHR094 = new ListCheckSheetConfig_SHR094(driver, excelreadwrite, xls_Read);
		checkhht=new ChecksheetHHT(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_4731")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_4731")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Paris"));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", "Europe/Paris"));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "Europe/Paris").toUpperCase());
			String xfwbdate = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "Europe/Paris");
			map.put("XFWBDate", xfwbdate);
			String currDate = cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Paris");
			String feedingTime=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");

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
			map.put("ConsigneeCountryName",WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));


			// Login to iCargo

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");


			/***Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/**** XFWB Message loading ****/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			String sccs[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1], cust.data("SCC").split(",")[2] };
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs", sccs);
			Cgocxml.clickMessageLoader();
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
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");

			//Check sheet for live animals
			OPR026.captureCheckSheetLiveAnimals(true,currDate,feedingTime);

			// verify customer and shipment details
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


			// Verify IATA rate and IATA charge
			OPR026.clickChargesAcc();
			OPR026.verifyIATAChargeDetails(cust.data("IATAcharge"), cust.data("IATARate"));
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			libr.quitBrowser();

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");		

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - ACCEPTANCE ****/



			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			gahht.enterValue("awbNumber");
			//verify Stated pieces and Stated weight,
			gahht.verifyStatedPiecesWeight("Pieces", "Weight");
			//verify SCC
			gahht.verifyMultipleSCCs("FullAWBNo",cust.data("SCC"));
			//verifying the error mesage
			gahht.verifyErrorMessage("val~SCC is mandatory!!");
			libr.quitApp();



		} 

		catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

		finally
		{
			try
			{
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}