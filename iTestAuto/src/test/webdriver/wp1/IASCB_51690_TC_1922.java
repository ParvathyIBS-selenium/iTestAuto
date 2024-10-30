package wp1;




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
import screens.CreateVisitDeclaration_TGC013;
import screens.DropOffPickUpShipmentsSST;

import screens.ListAuditEnquiry_SHR011;

import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.ServicePointAllocationHHT;
import screens.Servicepointoverview_TGC015;
import screens.VisitDeclarationEnquiry_TGC010;

/**
 * 
 *  1922 - Timestamp of the Documentation in progress and Document Verified details must be available in audit.
 *
 *
 */

public class IASCB_51690_TC_1922 extends BaseSetup {

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
	public VisitDeclarationEnquiry_TGC010 TGC010;
	public CreateVisitDeclaration_TGC013 TGC013;
	public DropOffPickUpShipmentsSST sstDP;
	public ServicePointAllocationHHT serpointhht;
	public ListAuditEnquiry_SHR011 SHR011;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public Servicepointoverview_TGC015 TGC015;
	
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "wp1";

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
		sstDP=new DropOffPickUpShipmentsSST(driver, excelreadwrite, xls_Read);
		TGC010=new VisitDeclarationEnquiry_TGC010(driver, excelreadwrite, xls_Read);
		TGC013=new CreateVisitDeclaration_TGC013(driver, excelreadwrite, xls_Read);
		SHR011 = new ListAuditEnquiry_SHR011(driver, excelreadwrite, xls_Read);
		serpointhht=new ServicePointAllocationHHT(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		TGC015 = new Servicepointoverview_TGC015(driver, excelreadwrite, xls_Read);
		
	}

	@DataProvider(name = "TC_1922")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_1922")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

		
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);	
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());

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
			map.put("ShipperCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			
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
			
			
			// Login to iCargo STG

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
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

	     	libr.quitBrowser();

		

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			
			
			/***Login to cgocxml **********/
			
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
			
			
			/**** XFWB Message loading ****/
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
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			/***Launch emulator - sst**/
			libr.launchSSTApp("sst-app", true);
			
			//Login to sst
			String [] sst=libr.getApplicationParams("hht");	
			cust.loginSST(sst[0], sst[1],"Public");

			/*** PUBLIC SIDE TOKEN GENERATION IN DROP OFF PICK UP SST SCREEN**/
			sstDP.invokeDropOffPickUpShipmentsSSTScreen();
			sstDP.addShipment("prop~CarrierNumericCode", "prop~AWBNo");
			sstDP.clickProceed();
			sstDP.enterDriverDetailsWithScroll("StartDate");
			sstDP.clickProceed();
			sstDP.selectVehicletype("VehicleType");
			sstDP.clickProceed();
			libr.waitForSync(4);
			sstDP.verifyTokenGeneration("TokenID");

			libr.quitApp();
			
			/**TGC015***/
			cust.searchScreen("TGC015", "Servicepointoverview");	
			libr.waitForSync(3);
			TGC015.selectWarehouse("servicetype");
			TGC015.verifyTokenIsDisplayed("TokenID");
			cust.closeTab("TGC015","Servicepointoverview");
	
			if(cust.data("tokenInWaitingArea").equals("true"))
			{
			libr.launchApp("hht-app-release");

			// Login in to HHT
		
			cust.loginHHT(sst[0], sst[1]);

			
			/*** HHT - SERVICE POINT ALLOCATION****/
			
			
			serpointhht.invokeServicePointAllocationScreen();
			serpointhht.clickCounter();
			serpointhht.enterToken("TokenID");
			serpointhht.enterServicePoint("ServicePoint");
			serpointhht.callForward();
			serpointhht.confirmIfCallForwarded();
			cust.clickBack("Service Point Allocation");
			}
		
			/**** TGC013- CREATE VISIT DECLARATION****/

			cust.searchScreen("TGC013","Create Visit Declaration");
			TGC013.enterTokenNo("TokenID");
			TGC013.clickList();
			TGC013.verifyAttributes("prop~FullAWBNo", "2");
			TGC013.editVerificationDetails();
			TGC013.performPhotoVerification();
			TGC013.addVerificationDetails();
			TGC013.save();
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("currtime", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Amsterdam");
			map.put("CurrDate", currDate);
			cust.closeTab("TGC013", "Create Visit Declaration");
			
			
			
			/**** TGC013- CREATE VISIT DECLARATION****/

			cust.searchScreen("TGC013","Create Visit Declaration");
			TGC013.enterTokenNo("TokenID");
			TGC013.clickList();
			TGC013.clickDocCompleted();
			TGC013.save();
			String currtime1=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("currtime1", currtime1);
			String currDate1=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Amsterdam");
			map.put("CurrDate1", currDate1);
			cust.closeTab("TGC013", "Create Visit Declaration");
			
			/********SHR011 - List Audit Enquiry screen***********/

			// Verify irregularity capture event is displayed in SHR011 screen
			cust.searchScreen("SHR011", "List Audit Enquiry");
			SHR011.selectModuleName("Others");
			SHR011.selectSubModuleName("Truck Guidance");
			SHR011.enterFromDate(currDate1);
			SHR011.enterToDate(currDate1);
			SHR011.enterAirportCode("Origin");
			SHR011.enterToken("TokenID");
			
			SHR011.listDetails();
			
			
		    /***Timestamp of the Documentation in progress and Document Verified details *****/
			
			int[] cols={3};
	     	        String[] values={currDate+" "+currtime};
			String[] values1={currDate1+" "+currtime1};
		    	SHR011.verifyTransactionDetailsValue(cols, values, "Documentation In Progress");
		    	SHR011.verifyTransactionDetailsValue(cols, values1, "Document Verified");
			cust.closeTab("SHR011", "List Audit Enquiry"); 
			
			
			

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
