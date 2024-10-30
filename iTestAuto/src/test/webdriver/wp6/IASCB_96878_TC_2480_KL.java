package wp6;

import java.util.Map;

import org.testng.Assert;
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
import screens.BreakDownScreen_OPR004;
import screens.MarkFlightMovements_FLT006;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.DamageCaptureHHT;
import screens.DeliveryDocumentation_OPR293;
import screens.DeliveryHHT;
import screens.ImportDocumentation_OPR001;
import screens.ImportManifest_OPR367;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;



/**  

TC_03_Provision to capture damage for AWB  in delivery screen-split shipment


.  **/



public class IASCB_96878_TC_2480_KL extends BaseSetup {

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
	public ImportManifest_OPR367 OPR367;
	public BreakDownScreen_OPR004 OPR004;
	public ImportDocumentation_OPR001 OPR001;
	public DeliveryDocumentation_OPR293 OPR293;
	public MarkFlightMovements_FLT006 FLT006;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public DeliveryHHT deliveryhht;
	public DamageCaptureHHT dchht;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "wp6";

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
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);  
		OPR001 = new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		deliveryhht = new DeliveryHHT(driver, excelreadwrite, xls_Read);
		dchht = new DamageCaptureHHT(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2480")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2480")
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

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));

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
			

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_FR"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_FR"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			
			//Regulated agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_TypeHUB_FR"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB"));
			map.put("RegulatedAgent", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB"));
			
            String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMMM",0, "DAY", ""));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			
			//SM details
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);


			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");

			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("carrierCode") + cust.data("prop~flightNo"),proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));

			//Flight details

			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
		    map.put("FlightNumber", cust.data("FullFlightNo"));

			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			
            libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			/****************** MERCURY *********************/

			//	 Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** SSM Message loading **/

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();


			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/*** MESSAGE - loading XFWB **********/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			/*** MESSAGE - loading XFFM **********/
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));

			String shipment[] = {
					cust.data("FullAWBNo") + ";" + cust.data("Pieces1").split(",")[0] + ";" + cust.data("Weight1").split(",")[0] + ";"
							+ cust.data("Volume1").split(",")[0] + ";" + cust.data("ShipmentDesc"),
					cust.data("FullAWBNo") + ";" + cust.data("Pieces1").split(",")[1] + ";" + cust.data("Weight1").split(",")[1] + ";"
							+ cust.data("Volume1").split(",")[1] + ";" + cust.data("ShipmentDesc") };

			String routing[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport"),
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport") };
			String scc[] = { cust.data("SCC"), cust.data("SCC") };
			
			// ULD Number 1
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			
			// ULD Number 2
			String uldNo1 = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNo1);
			
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			map.put("ULDNo1", cust.data("UldNum1").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode"),  cust.data("UldType") + ";" + cust.data("ULDNo1") + ";" + cust.data("carrierCode") };

			int []shipments={2};
			int [] distribution= {1,1};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing, uld,shipments, distribution);
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
			
			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.clickCheckBox_ULD(cust.data("UldNum"));
			OPR367.clickBreakdownButton();
			String[] Location={cust.data("BDNLocation").split(",")[0]};
			String[] Pieces={cust.data("Pieces1").split(",")[0]};
			String[] Weight={cust.data("Weight1").split(",")[0]};
			OPR367.enterBdnDetails_multipleShipments(1,Location,Pieces,Weight);	
			OPR004.clickBreakdownComplete();
			OPR367.closeFromOPR004();
			 OPR367.clickCheckBox_ULD(cust.data("UldNum1"));
			OPR367.clickBreakdownButton();
			String[] Location1={cust.data("BDNLocation").split(",")[1]};
			String[] Pieces1={cust.data("Pieces1").split(",")[1]};
			String[] Weight1={cust.data("Weight1").split(",")[1]};
			OPR367.enterBdnDetails_multipleShipments(1,Location1,Pieces1,Weight1);	
			OPR004.clickBreakdownComplete();
			OPR367.closeFromOPR004();
			
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");
			
			/********** OPR293-Delivery Documentation **********/

			// Generate delivery id
			cust.searchScreen("OPR293", "Delivery Documentation");
			OPR293.listWithFlightNumber("carrierCode", "FlightNo","StartDate");
			OPR293.selectAllAWBs();
			OPR293.generateDeliveryID3();
			OPR293.verifyDNStatus("Paid");
			map.put("DeliveryID", OPR293.getDeliveryID());
			OPR293.closeTab("OPR293", "Delivery Documentation");
			
			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");		

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht2");	
			cust.loginHHT(hht[0], hht[1]);
			/** Delivery HHT **/
			//Perform delivery in HHT by listing the Delivery ID
			deliveryhht.invokeDeliveryHHTScreen();
			map.put("awbnumber",cust.data("CarrierNumericCode")+cust.data("AWBNo"));
			deliveryhht.enterAWBULDNum("awbnumber");
			libr.waitForSync(4);
			deliveryhht.verifyShipment("CarrierNumericCode", "AWBNo");
			deliveryhht.verifyOriginAndDestination("Origin", "Destination");
			deliveryhht.verifyPiecesWeight("Pieces","Weight");
			deliveryhht.clickSelectOptionIcon();
			deliveryhht.clickAddOnMenu();
			deliveryhht.verifyAndSelectDamageCapture();
			libr.waitForSync(4);
			dchht.enterPiecesAndWeight("DmgPieces", "DmgWeight");
			dchht.selectDamageCode("Bent");
			dchht.enterPackageCodeDamageReasonCode("Sack","Improper Loading");
			
			dchht.enterPointOfNotice("Noticed at cargo acceptance");
			dchht.clickSave();
			deliveryhht.clickPendingButton();
			libr.waitForSync(3);
			deliveryhht.clickSelectAll();
			deliveryhht.clickNext();
			deliveryhht.enterDeliverRemarks("val~Delivered");
			deliveryhht.enterCustomsReferenceNumber("customRefNo");
			deliveryhht.clickNext();
			deliveryhht.deliveryStatusVerify("val~DELIVERED");
			deliveryhht.clickDeliveryComplete();
			deliveryhht.enterDeliveredTo("ConsigneeCode");
			deliveryhht.enterVehicleInfo("VehicleInfo");
			deliveryhht.enterContactNumber("ContactNumber");
			deliveryhht.clickNext();
			deliveryhht.captureSignature();
			deliveryhht.enterRemarks("val~Delivery complete");
			deliveryhht.clickPrintPOD();
			libr.quitApp();
			

		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
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
