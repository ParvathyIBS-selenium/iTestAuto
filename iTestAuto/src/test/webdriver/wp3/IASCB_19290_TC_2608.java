package wp3;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
//TC_07_Check manifest print based on the filter from master and additional parameters - Include ULD destination
public class IASCB_19290_TC_2608 extends BaseSetup {
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
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ExportManifest_OPR344 OPR344;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public MaintainOperationalFlight_FLT003 FLT003;

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
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2605")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2605")
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
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			

			// creating flight number
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNumber", cust.data("carrierCode")+cust.data("prop~flightNo"),proppath);
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");		
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM",0, "DAY", ""));
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			
			
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Amsterdam");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Amsterdam");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);


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
			
			
			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));
			

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("prop~flight_code_KL","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			

			// Maintain Flight Screen (FLT005) . Taking fresh flight
			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/		
			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlightDetails("carrierCode","FlightNo","StartDate");

			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");

			FLT003.enterLegCapacityDetails("StartDate","EndDate","ATD_Local","ATA_Local", "AircraftType", "");			
			FLT003.clickSecondCheckbox();

			FLT003.clickLegCapacity();
			FLT003.enterLegCapacityDetails("StartDate","EndDate","ATD_Local1","ATA_Local1", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");

			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);


			map.put("awbNumber1", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum1", cust.data("CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));

			map.put("FullAWBNo", cust.data("awbNumber1"));
			map.put("AWBNo1", cust.data("awb1"));
			



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
			libr.quitBrowser();


			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc"),libr.data("FullAWBNo2") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
							+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC"),cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Transit"),cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** XFWB Message loading **/
			// Create XFWB message
			map.put("FullAWBNum", cust.data("FullAWBNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/***** XFWB Loading for AWB 2 ***/
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US"));
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			
			map.put("FullAWBNum", cust.data("FullAWBNo2"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB1", "MessageParamXFWB1");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

            /**** OPR026 - Capture AWB ****/	

			/***** OPR026 - Capture AWB ****/
			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR335 -Goods Acceptance for Awbno1 ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR335 -Goods Acceptance for Awbno1 ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			map.put("AWBNo1","15480032");
			map.put("AWBNo2","15480043");
			map.put("UldNum1", "AKC52614KL");
			map.put("UldNum", "AKC76500KL");
			/***** Export Manifest***/

			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code_KL", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			OPR344.addNewULDWithAWB("UldNum",cust.data("Transit"),"CarrierNumericCode","AWBNo1","Pieces","Weight");
			cust.waitForSync(2);
			OPR344.clickEditULDdetailsByJS("UldNum");
			OPR344.addULDDestination("Transit");
			OPR344.saveAndClose();
			String uldNum1=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNum1);
			OPR344.addNewULDWithAWB("UldNum1",cust.data("Transit"),"CarrierNumericCode","AWBNo2","Pieces","Weight");
			cust.waitForSync(2);
			OPR344.clickEditULDdetailsByJS("UldNum1");
			OPR344.addULDDestination("Destination");
			OPR344.saveAndClose();
		    OPR344.clickBuildUpComplete("UldNum1");
			OPR344.clickManifest();
			OPR344.switchToFrame("frameName","popupContainerFrame");

			OPR344.verifyElementSelected("ExportManifest_OPR344","chk_considerformanifestprint;xpath",
					"Consider For Manifest Print checbox in manifestpopup","ExportManifest"); 
			OPR344.enterUldDestinationInManifestPopup("Destination");
			
			OPR344.printManifestOkWithoutFrame();
			cust.printAndVerifyReport("val~CARGO MANIFEST","OPR344",true,cust.data("carrierCode")+" "+cust.data("FlightNo"),cust.data("UldNum1"),cust.data("FullAWBNo2"),cust.data("Origin"),cust.data("Destination"));
			OPR344.printManifestOk();
			cust.printAndVerifyReport("val~CARGO MANIFEST","OPR344",false,cust.data("UldNum"),cust.data("FullAWBNo"));
			OPR344.printManifestClose();

			libr.quitBrowser();


		}

		catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		finally {
			try {
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			}
			catch (Exception e) {
				e.printStackTrace();
				}
		}
	}
}


