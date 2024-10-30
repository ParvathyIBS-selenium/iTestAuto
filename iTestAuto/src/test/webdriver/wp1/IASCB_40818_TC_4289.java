	package wp1;

/***Security Method Not getting Stamped for NSC AWBs***/

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
import screens.Cgocxml;
import screens.CaptureAWB_OPR026;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;

public class IASCB_40818_TC_4289 extends BaseSetup
{
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
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainFlightSchedule_FLT005 FLT005;
	public Cgocxml Cgocxml;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="wp1";	

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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
	}
	@DataProvider(name = "TC_4289")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;
	}
	@Test(dataProvider = "TC_4289")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map=map;		
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

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

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

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

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

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("awbNumber1", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			cust.setPropertyValue("FullAWBNo1", cust.data("awbNumber1"),proppath);
			cust.setPropertyValue("AWBNo1", cust.data("awb1"),proppath);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2
			map.put("awbNumber2", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));
			cust.setPropertyValue("FullAWBNo3", cust.data("awbNumber2"),proppath);
			cust.setPropertyValue("AWBNo3", cust.data("awb2"),proppath);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();


			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			/***Login to cgocxml **********/
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFWB Message loading for AWB 1 **/
			map.put("FullAWBNo", cust.data("awbNumber1"));
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading for AWB 2 **/
			map.put("FullAWBNo", cust.data("awbNumber2"));
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();



			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/**** OPR339 - Security & Screening AWB1****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo1", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			String[] screeningMethod={cust.data("ScreeningMethod").split(",")[0],cust.data("ScreeningMethod").split(",")[1]};
			map.put("ScreeningMethodFail", screeningMethod[0]);
			OPR339.enterScreeningDetails("ScreeningMethodFail","Pieces","Weight","val~Fail");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo1", "CarrierNumericCode", "OPR339 - Security & Sceening");
			map.put("ScreeningMethodPass", screeningMethod[1]);
			String ScreeningMethodPassCode= screeningMethod[1].substring(0, 3);
			map.put("ScreeningMethodPassCode",ScreeningMethodPassCode);
			OPR339.enterScreeningDetails("ScreeningMethodPass","Pieces","Weight","val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo1", "CarrierNumericCode", "OPR339 - Security & Screening");
			OPR339.clickPrintForVerification();
			cust.printAndVerifyReport("val~Consignment Security Declaration","OPR339",
					cust.data("val~SPX"),cust.data("ScreeningMethodPassCode"));
			cust.closeTab("OPR339", "Security & Screening");


			//capturing screening details with another screening method
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo3", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			String[] screeningMethod1={cust.data("ScreeningMethod").split(",")[0],cust.data("ScreeningMethod").split(",")[1]};
			map.put("ScreeningMethodFail", screeningMethod1[0]);
			OPR339.enterScreeningDetails("ScreeningMethodFail","Pieces","Weight","val~Fail");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");


			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo3", "CarrierNumericCode", "OPR339 - Security & Sceening");
			map.put("ScreeningMethodFail", screeningMethod[1]);
			String ScreeningMethodFailCode = screeningMethod[1].substring(0, 3);
			System.out.println(ScreeningMethodFailCode);
			map.put("ScreeningMethodFailCode",ScreeningMethodFailCode);	
			OPR339.enterScreeningDetails("ScreeningMethodPass","Pieces","Weight","val~Fail");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo3", "CarrierNumericCode", "OPR339 - Security & Screening");
			OPR339.clickPrintForVerification();
			cust.printAndVerifyReport("val~Consignment Security Declaration","OPR339",
					cust.data("val~NSC"),cust.data("ScreeningMethodFailCode"));
			cust.closeTab("OPR339", "Security & Screening");
		}
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
	}
}



