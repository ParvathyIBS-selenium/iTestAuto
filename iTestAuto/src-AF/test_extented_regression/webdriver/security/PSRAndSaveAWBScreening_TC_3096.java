package security;

/**  TC_07_Verify block is not released when screening for some pieces is received as FAIL for a AWB from RAPIX.-HHT  **/

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
import rest_multiple_sfmi.Jsonbody;
import rest_pawbs.JSONBody;
import screens.AWBClearance_OPR023;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.RelocationHHT;
import screens.SecurityAndScreening_OPR339;

public class PSRAndSaveAWBScreening_TC_3096 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public AWBClearance_OPR023 OPR023;
	public GoodsAcceptanceHHT gahht;
	public RelocationHHT relocationhht;
	public Cgocxml Cgocxml;
	public JSONBody jsonbody;
	public Jsonbody jsonbody1;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Security.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "Security_SIT";

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
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		relocationhht=new RelocationHHT(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		jsonbody=new JSONBody(driver, excelreadwrite, xls_Read);
		jsonbody1=new Jsonbody(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "3096")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "3096")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			//Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			String timeStamp = cust.createDateFormatWithTimeZone("dd-MMM-yyyy hh:mm:ss", 0, "DAY", "Europe/Paris");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));

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

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			libr.quitBrowser();

			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/*** Login to cgocxml **********/
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/*** MESSAGE - loading XFWB **********/
			cust.createXMLMessage("MessageExcelAndSheetXFWB","MessageParamXFWB");
			String sccs[] = { cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1] };
			//Create XFWB message
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs", sccs);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/** Switch role to Origin **/
			cust.switchRole("Origin", "Origin", "RoleGroup");

			/**** OPR026 - Capture AWB ****/
			//As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			map.put("Pcs1",cust.data("Pcs").split(",")[0]); 
			map.put("Pcs2",cust.data("Pcs").split(",")[1]); 

			map.put("Wgt1",cust.data("Wgt").split(",")[0]); 
			map.put("Wgt2",cust.data("Wgt").split(",")[1]); 

			map.put("Scc1",cust.data("SCC").split(",")[0]); 
			map.put("Scc2",cust.data("SCC").split(",")[1]); 

			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");

			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - ACCEPTANCE****/
			gahht.invokeAcceptanceScreen();
			map.put("AWB", cust.data("CarrierNumericCode") + cust.data("AWBNo"));
			gahht.enterValue("AWB");

			gahht.selectSCCValue("Scc1");
			gahht.enterLooseAcceptanceDetails("Pcs1", "Wgt1", "Location");
			gahht.clickSaveOnly();

			gahht.selectSCCValue("Scc2");
			gahht.enterLooseAcceptanceDetails("Pcs2", "Wgt2", "Location1");
			gahht.checkAllPartsReceived();
			gahht.clickSaveOnly();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");

			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verificationOfNotRFCStatus();
			OPR335.verifyAcceptanceFinalizedinSameFrame("not finalised");
			OPR335.verifyWeightCheckStatus("not completed",false);	
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR023 - AWB Clearance ******/            
			cust.searchScreen("OPR023", "AWB Clearance");
			OPR023.listAWB("CarrierNumericCode","AWBNo");
			OPR023.verifyBlock(cust.data("BlockType"), cust.data("AWBNo"));
			OPR023.verifySCCs("val~NSC");
			OPR023.closeTab("OPR023", "AWB Clearance");		

			/******* HHT-Relocation*********/
			relocationhht.invokeRelocationScreen();
			map.put("SU1", cust.data("AWB")+"001");
			relocationhht.enterValueWithoutNext("SU1");
			relocationhht.clickStartRelocation();
			map.put("RapixEntryLocation", WebFunctions.getPropertyValue(toproppath, "RapixEntryLocation"));
			relocationhht.enterDestLocation("RapixEntryLocation");
			relocationhht.clickCompleteRelocation();


			/******* SFMI POST REQUEST FOR SU1 ****/		
			jsonbody1.postRequest(cust.data("AWB"),cust.data("Wgt").split(",")[0],cust.data("Vol").split(",")[0],cust.data("Length"),cust.data("Width"),cust.data("Height"),"001"+cust.data("AWB"));

			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);	

			/******* PAWBS POST REQUEST for SU1 - SU1 pcs are screened as PASS ****/	
			jsonbody.postRequest(cust.data("CarrierNumericCode"), cust.data("AWBNo"), timeStamp,cust.data("ScreeningResult").split(",")[0],screenmethod,cust.data("RapixEntryLocation"),cust.data("ScreenerName"),cust.data("SU1"));	

			/******* HHT-Relocation*********/
			map.put("SU2", cust.data("AWB")+"002");
			relocationhht.enterValueWithoutNext("SU2");
			relocationhht.clickStartRelocation();
			relocationhht.enterDestLocation("RapixEntryLocation");
			relocationhht.clickCompleteRelocation();

			/******* SFMI POST REQUEST SU2 ****/		
			jsonbody1.postRequest(cust.data("AWB"),cust.data("Wgt").split(",")[1],cust.data("Vol").split(",")[1],cust.data("Length"),cust.data("Width"),cust.data("Height"),"002"+cust.data("AWB"));

			/******* PAWBS POST REQUEST for SU2 pcs are screened as FAIL ****/	
			jsonbody.postRequest(cust.data("CarrierNumericCode"), cust.data("AWBNo"), timeStamp,cust.data("ScreeningResult1").split(",")[0],screenmethod,cust.data("RapixEntryLocation"),cust.data("ScreenerName"),cust.data("SU2"));
			libr.quitApp();	     

			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");

			String SUs[]={cust.data("SU1"),cust.data("SU2")};
			String ScreeningMethods[]={cust.data("screenmethod"),cust.data("screenmethod")};
			String ScreenedPcs[]={cust.data("Pcs").split(",")[0], cust.data("Pcs").split(",")[1]};
			String ScreenedWgt[]={cust.data("Wgt").split(",")[0], cust.data("Wgt").split(",")[1]};
			String ScreeningResult[]={cust.data("ScreeningResult").split(",")[1],cust.data("ScreeningResult1").split(",")[1]};

			OPR339.verifyScreeningDetailsOfMultipleSUs(SUs,ScreeningMethods,ScreeningResult,ScreenedPcs,ScreenedWgt);		
			OPR339.verifyScreenerDetails("ScreenerName",timeStamp.split(" ")[0]);
			String Sccnotpresent[]={"SPX"};
			OPR339.verifySccNotPresent(Sccnotpresent);
			String Sccpresent[]={"NSC"};
			OPR339.verifyScc(Sccpresent);
			cust.closeTab("OPR339", "Security & Screening");  

			/**** OPR023 - AWB Clearance - Verifying Block still exists ******/            
			cust.searchScreen("OPR023", "AWB Clearance");
			OPR023.listAWB("CarrierNumericCode","AWBNo");
			OPR023.verifyBlock(cust.data("BlockType"), cust.data("AWBNo"));
			OPR023.verifySCCs("val~NSC");
			OPR023.closeTab("OPR023", "AWB Clearance");	

			/**** OPR335 -Goods Acceptance ****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.verifyAcceptanceFinalizedinSameFrame("not finalised");
			OPR335.verificationOfNotRFCStatus();
			OPR335.verifyWeightCheckStatus("completed",false);	
			cust.closeTab("OPR335", "Goods Acceptance");


		}catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		finally
		{
			try {
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
