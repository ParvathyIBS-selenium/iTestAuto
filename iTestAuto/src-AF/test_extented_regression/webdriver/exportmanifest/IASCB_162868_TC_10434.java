package exportmanifest;

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
import rest_sstunitch.JSONBody;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.GoodsAcceptance_OPR335;
import screens.SecurityAndScreening_OPR339;
import screens.WarehouseRelocation_WHS009;
import screens.WarehouseShipmentEnquiry_WHS011;
/***TC_05_Verify auto generation of Storage unit (SU) for split relocation in warehouse relocation screen(WHS009) for multiple locations, when SU field is empty .**/
public class IASCB_162868_TC_10434 extends BaseSetup {
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
	public Cgocxml Cgocxml;
    public JSONBody jsonbody;
    public WarehouseRelocation_WHS009 WHS009;
    public WarehouseShipmentEnquiry_WHS011 WHS011; 
    String path1 = System.getProperty("user.dir") + "\\src\\resources\\ExportManifest.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "ExportManifest_FT";

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
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		jsonbody=new JSONBody(driver, excelreadwrite, xls_Read);
		WHS009=new WarehouseRelocation_WHS009(driver, excelreadwrite, xls_Read);
		WHS011=new WarehouseShipmentEnquiry_WHS011(driver,excelreadwrite,xls_Read); 
	}

	@DataProvider(name = "TC_10432")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_10432")
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

			String startDate = cust.createDateFormat("dd-MMM-YYYY",0, "DAY", "");
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
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

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
			/*** Login to cgocxml **********/
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
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR026 - Capture AWB ****/
			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/****OPR355 - Loose Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			/** WAREHOUSE RELOCATION  - WHS009 **/
			cust.searchScreen("WHS009", "Warehouse Relocation");
			map.put("SU", cust.data("CarrierNumericCode")+cust.data("AWBNo")+"001");
			WHS009.enterAWB("CarrierNumericCode","AWBNo");
			WHS009.listAwbDetails();
			 int verfCols[]={3,7,8};
			 String pmkey=cust.data("AWBNo");
			 String[] actVerfValues={cust.data("AWBNo"),cust.data("Location"),cust.data("SU")};
			 //verify awb details
			WHS009.verifyAWBDetails(verfCols, actVerfValues,pmkey,true);
			WHS009.markCheckbox();
			
			WHS009.clickSplitRelocationButton();
			map.put("pieces",cust.data("Pieces1").split(",")[0]);
			map.put("weight",cust.data("Weight1").split(",")[0]);
			map.put("location",cust.data("Location1").split(",")[0]);
			WHS009.enterSplitRelocationDetails("location","pieces","weight");
			
			cust.switchToMainScreen("WHS009");
			map.put("SU1", cust.data("CarrierNumericCode")+cust.data("AWBNo")+"002");
			int[] verfCols1={9,10};
			String[] actVerfValues1={cust.data("pieces") ,cust.data("weight")};
			String pmkey1=cust.data("SU1");
			//verify split pcs/wt relocated to SU1
			WHS009.verifyRelocationDetails(verfCols1,actVerfValues1,pmkey1,"pcs/wgt after relocation",true);
			
			map.put("pieces1",cust.data("Pieces1").split(",")[1]);
			map.put("weight1",cust.data("Weight1").split(",")[1]);
			map.put("location1",cust.data("Location1").split(",")[1]);
			WHS009.clickSplitRelocationButton();
			
			WHS009.enterSplitRelocationDetails("location1","pieces1","weight1");
			
			cust.switchToMainScreen("WHS009");
			map.put("SU2", cust.data("CarrierNumericCode")+cust.data("AWBNo")+"003");
			int[] verfCols2={9,10};
			String[] actVerfValues2={cust.data("pieces1") ,cust.data("weight1")};
			String pmkey2=cust.data("SU2");
			//verify split pcs/wt relocated to SU2
			WHS009.verifyRelocationDetails(verfCols2,actVerfValues2,pmkey2,"pcs/wgt  after relocation",true);
			int[] verfCols3={8};
			String[] actVerfValues3={cust.data("SU1")};
			String pmkey3=cust.data("location");
			//verify SU1 generated
			WHS009.verifyRelocationDetails(verfCols3,actVerfValues3,pmkey3,"SU autogenerated for Relocation of" +cust.data("pieces")+"pcs" ,true);
			
			String[] actVerfValues4={cust.data("SU2")};
			String pmkey4=cust.data("location1");
			//verify SU2 generated
			WHS009.verifyRelocationDetails(verfCols3,actVerfValues4,pmkey4,"SU autogenerated for Relocation of" +cust.data("pieces1")+"pcs",true);
			WHS009.clickSaveButton();
			cust.closeTab("WHS009", "Warehouse Relocation");
			
			
			/** WAREHOUSE RELOCATION  - WHS009 **/
			cust.searchScreen("WHS009", "Warehouse Relocation");
			WHS009.enterAWB("CarrierNumericCode","AWBNo");
			WHS009.listAwbDetails();
			//verify current location for su1
			WHS009.VerifyCurrentLocOfULD("SU1","location");
			
			WHS009.VerifyCurrentLocOfULD("SU2","location1");
			
			cust.closeTab("WHS009", "Warehouse Relocation");
			
			/** WAREHOUSE shipment Enquiry screen  - WHS009 **/
			cust.searchScreen("WHS011", "Warehouse shipment Enquiry");
			WHS011.enterAWBdetails("CarrierNumericCode","AWBNo");
			WHS011.clickList();
			int[] verfCols5={4,5};
			String[] actVerfValues5={cust.data("location"),cust.data("SU1")};
			//verify SU1 details
			WHS011.verifyWarehouseDetailsWithPmKey(verfCols5, actVerfValues5,"location");
			
			String[] actVerfValues6={cust.data("location1"),cust.data("SU2")};
			//verify SU2 details
			WHS011.verifyWarehouseDetailsWithPmKey(verfCols5, actVerfValues6,"location1");
			cust.closeTab("WHS011", "Warehouse shipment Enquiry");
			
			
			
			} catch (Exception e) {
				libr.writeExtent("Fail", "Test case has failed steps");
				e.printStackTrace();
				Assert.assertFalse(true, "The test case has failed steps");
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