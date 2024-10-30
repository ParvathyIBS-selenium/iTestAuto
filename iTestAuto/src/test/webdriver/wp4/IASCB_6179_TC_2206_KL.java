package wp4;

/************xFSU-NFD is triggered as part of RCF**********/

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.DeliveryDocumentation_OPR293;
import screens.ImportManifest_OPR367;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.ImportDocumentation_OPR001;
import screens.Mercury;
import screens.UldSightingHHT;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.ListMessages_MSG005;
public class IASCB_6179_TC_2206_KL extends BaseSetup {

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
	public ImportManifest_OPR367 OPR367;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ImportDocumentation_OPR001 OPR001;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public BreakDownScreen_OPR004 OPR004;
	public ListMessages_MSG005 MSG005;
	public UldSightingHHT uldsighthht;
	public MarkFlightMovements_FLT006 FLT006;
	public DeliveryDocumentation_OPR293 OPR293;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "wp4";

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
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR001 = new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		uldsighthht=new UldSightingHHT(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_2206")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;
	}


	@Test(dataProvider = "TC_2206")
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

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY",0, "DAY", "Europe/Paris");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "Europe/Paris");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "Europe/Paris");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Paris"));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", "Europe/Paris"));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "Europe/Paris").toUpperCase());
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Paris"));
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Paris"));
		
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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_NL2"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_NL2"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_NL2"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_NL2"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_NL2"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_NL2"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_NL2"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_NL2"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_NL2"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_NL2"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			

			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "Europe/Paris");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", "T133072");

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
		

			/** SSM Message loading **/
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			/*** MSG005 - SSM Message loading******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");

			/** XFWB Message loading **/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo", true);

			/**XFFM Message Loading **/
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Paris"));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", "Europe/Paris"));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", "Europe/Paris"));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
		    map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = {cust.data("SCC")};
			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };

			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing1, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");

			/**Switch role to Destination**/
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("carrierCode", "FlightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Amsterdam");
			map.put("ATA", currtime);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","StartDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,startDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");

			libr.quitBrowser();


			/***Launch emulator - uldsighting app**/
			libr.launchUldSightingApp("uldsighting-app");
			//Login in to ULD Sighting App
			String [] hht=libr.getApplicationParams("hht2");		
			cust.loginHHT(hht[0], hht[1]);		

			uldsighthht.clickDone();
			//sighting and sighting complete for intact uld
			uldsighthht.enterUldNumber("UldNum");
			uldsighthht.clickSight();
			uldsighthht.selectFwLocation("ForwardLocation");
			uldsighthht.clickComplete() ;
			libr.quitApp();
			
			
			


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			/** Switch role  **/
			cust.switchRole("Destination","FCTL", "RoleGroup");
			
			
			/** Import Manifest **/
            cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			String pmkey = Excel.getCellValue(path1,sheetName, "IASCB_6179_TC_2206_KL", "UldNum");
	        OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			String[] Location={cust.data("BDNlocation")};
			String[] Pieces={cust.data("Pieces")};
			String[] Weight={cust.data("Weight")};
			OPR367.enterBdnDetails_multipleShipments(1,Location,Pieces,Weight);
			OPR004.clickBreakdownComplete();
			OPR367.closeFromOPR004();
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");


			/*******Verify xFSU-RCF message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFSU1=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsXFSU[]={9};	
			String[] actVerfValuesXFSU={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFSU, actVerfValuesXFSU, pmKeyXFSU1,"val~XFSU-RCF",false);
			libr.waitForSync(3); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/*********relist- OPR001 Import Documentation ***********/

			cust.searchScreen("OPR001", "Import Documentation: OPR001");
			OPR001.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			
             OPR001.clickAWBNumberCheckBox(cust.data("AWBNo"));
			// Clicking AWB Document recieved checkboxes-nfd trigger point
			OPR001.clickAWBDocumentReceived(cust.data("prop~AWBNo"));
			OPR001.saveDetails();
			OPR001.ClickYesAlert();
			OPR001.closeTab("OPR001", "Import Documentation: OPR001");

			/**********OPR293-Delivery Documentation**********/
			//Capture handover details 
			cust.searchScreen("OPR293", "Delivery Documentation");
			cust.listAWB("AWBNo", "prop~CarrierNumericCodeAMS", "Delivery Documentation");
			OPR293.selectAllAWBs();
			map.put("CustomerName", cust.data("ConsigneeCode"));
		    OPR293.enterCaptureHandOverDetails();
			OPR293.verifyHandoverTickMark("prop~AWBNo");
			cust.closeTab("OPR293", "Delivery Documentation");

			/*** MSG005- Verify xFSU-NFD message after saving handover doc operation ***/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Notification");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFSUNFD1 = cust.data("CarrierNumericCode")+ " - " +cust.data("AWBNo");;
			MSG005.verifyIfMessageTriggered(pmKeyXFSUNFD1,cust.data("ProfileId").split(",")[0],"XFSU-NFD",true);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");




			/*** MSG005- Verify xFSU-AWD message ***/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("AWB Document Delivered");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFSU = cust.data("CarrierNumericCode") + " - " + cust.data("prop~AWBNo");
			int verfColsXFSU1[] = { 9 };
			String[] actVerfValueXFSU = { "Sent" };
			MSG005.verifyMessageDetails(verfColsXFSU1, actVerfValueXFSU, pmKeyXFSU, "val~XFSU-AWD", false);
			libr.waitForSync(6);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

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


