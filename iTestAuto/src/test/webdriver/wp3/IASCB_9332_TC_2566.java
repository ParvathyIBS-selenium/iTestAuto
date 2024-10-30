package wp3;


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
import screens.LoadUnloadRFSSST;
import screens.MaintainFlightSchedule_FLT005;
import screens.ReportingAtDockHHT;
import screens.ServicePointAllocationHHT;
import screens.Servicepointoverview_TGC015;
import screens.ListMessages_MSG005;
import screens.SecurityAndScreening_OPR339;
import screens.GoodsAcceptance_OPR335;
import screens.VisitDeclarationEnquiry_TGC010;



//TC_04_ Verify green tick present for RFS outbound truck when Seal Numbers is captured and coulmn chooser present in Visit Declaration screen

public class IASCB_9332_TC_2566 extends BaseSetup {


	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public MaintainFlightSchedule_FLT005 FLT005;
	public LoadUnloadRFSSST ldRfssst;
	public ReportingAtDockHHT reportdockhht;
	public ServicePointAllocationHHT serpointhht;
	public Servicepointoverview_TGC015 TGC015;
	public ListMessages_MSG005 MSG005;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public VisitDeclarationEnquiry_TGC010 TGC010;



	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
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
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		ldRfssst=new LoadUnloadRFSSST(driver, excelreadwrite, xls_Read);
		reportdockhht=new ReportingAtDockHHT(driver, excelreadwrite, xls_Read);
		serpointhht=new ServicePointAllocationHHT(driver, excelreadwrite, xls_Read);
		TGC015 = new Servicepointoverview_TGC015(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		TGC010=new VisitDeclarationEnquiry_TGC010(driver, excelreadwrite, xls_Read);


	}

	@DataProvider(name = "TC_2566")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2566")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Amsterdam");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Amsterdam");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMMM",0, "DAY", "Europe/Amsterdam"));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Amsterdam"));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "Europe/Amsterdam").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "Europe/Amsterdam");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Amsterdam"));
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Amsterdam"));
			
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Amsterdam");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Amsterdam");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);


			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_ES"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_ES"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_ES"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_ES"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_ES"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_ES"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_ES"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_ES"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_ES"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_ES"));


			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "MAD"));


			/** Switch role to Origin **/
			cust.switchRole("Origin", "Origin", "RoleGroup");


			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");

			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code_KL")+cust.data("prop~flightNo"),proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code_KL"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));



			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");


			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));



			/** MSG005 - List Messages **/

			//SSM Message loading
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");


			//XFBL Message loading 
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);


			//XFWB Message loading
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo", true);
			cust.closeTab("MSG005", "List Message");



			/**** OPR026 - Capture AWB ****/

			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/** OPR335 - ULD Acceptance **/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			OPR335.uldShipmentDetails("Pieces", "Weight", "Location", "UldNum", "");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");


			/***Launch emulator - sst**/
			libr.launchSSTApp("sst_smartlox-app", true);

			//Login to sst
			String [] sst=libr.getApplicationParams("hht2");	
			cust.loginSST(sst[0], sst[1],"Public",true);

			ldRfssst.invokeLoadUnloadRFSSSTScreen();
			ldRfssst.addFlightDetails("carrierCode","FlightNo", "CurrentDate");
			ldRfssst.clickProceed();
			ldRfssst.enterTruckingCompanyName("TruckCompanyCode","TruckingCompany");
			ldRfssst.enterDriverDetailsWithScroll("EndDate");
			String sealNo1=ldRfssst.create_sealNumber();
			String sealNo2=ldRfssst.create_sealNumber();
			map.put("sealNumber1", sealNo1);
			map.put("sealNumber2", sealNo2);
			ldRfssst.enterMultipleSealNumbers("sealNumber1","sealNumber2");
			ldRfssst.clickProceed();
			ldRfssst.selectVehicletype("VehicleType");
			ldRfssst.clickProceed();
			ldRfssst.verifyTokenGeneration("TokenId");
			libr.quitApp();



			/************TGC015- SERVICE POINT OVERVIEW*****/
			cust.searchScreen("TGC015", "Servicepointoverview");
			TGC015.selectWarehouse("servicetype");
			//verifying token generated got displayed
			TGC015.verifyTokenIsDisplayed("TokenId");
			cust.closeTab("TGC015", "Service Point Overview");


	

			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");
			//Login in to HHT
			String [] hht2=libr.getApplicationParams("hht2");
			cust.loginHHT(hht2[0], hht2[1]);


			if(cust.data("tokenInWaitingArea").equals("true"))
			{


				//Call forwarding the token to dock
				serpointhht.invokeServicePointAllocationScreen();
				serpointhht.enterToken("TokenId");
				serpointhht.clickselectServicePointDropdown("servicePointName");
				serpointhht.callForward();
				serpointhht.confirmIfCallForwarded();
				cust.clickBack("Service Point Allocation");

			}


			/*** HHT - REPORTING AT DOCK****/

			reportdockhht.invokeReportingAtDockScreen();
			reportdockhht.enterToken("TokenId");
			reportdockhht.start();
			reportdockhht.enterSealNumbers("sealNumber1","sealNumber2");
			reportdockhht.complete();
			reportdockhht.releaseDock();
			libr.quitApp();


			/************TGC010- VISIT DECLARATION ENQUIRY*****/
			cust.searchScreen("TGC010","Visit Declaration Enquiry");
			TGC010.enterToken("TokenId");
			TGC010.clickList();
			//verifying Seal Captured Column is present
			String[] colName={"Seal Captured"};
			TGC010.verifyColumn(colName);
			//verifying seal captured tick mark is displayed against the token
			TGC010.verifySealCapturedTickMark();
			cust.closeTab("TGC010", "Visit Declaration Enquiry");


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
