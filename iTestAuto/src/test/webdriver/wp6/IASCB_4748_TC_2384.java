package wp6;
/**   Delivery the AWB through Android Screen   **/

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
import screens.BreakdownHHT;
import screens.CaptureAWB_OPR026;
import screens.CaptureCheckSheet_CHK002;
import screens.Cgocxml;
import screens.DeliveryDocumentation_OPR293;
import screens.DeliveryHHT;
import screens.DeliverySlip_OPR038;
import screens.ExportManifest_OPR344;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.UldSightingHHT;

public class IASCB_4748_TC_2384 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ImportManifest_OPR367 OPR367;
	public CaptureAWB_OPR026 OPR026;
	public DeliveryDocumentation_OPR293 OPR293;
	public SecurityAndScreening_OPR339 OPR339;
	public ListMessages_MSG005 MSG005;
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public DeliveryHHT deliveryhht;
	public DeliverySlip_OPR038 OPR038;
	public MarkFlightMovements_FLT006 FLT006;
	public UldSightingHHT uldsighthht;
	public BreakdownHHT bdhht;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public CaptureCheckSheet_CHK002 CHK002;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
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
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		deliveryhht = new DeliveryHHT(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		bdhht=new BreakdownHHT(driver, excelreadwrite, xls_Read);
		uldsighthht=new UldSightingHHT(driver, excelreadwrite, xls_Read);
		OPR038=new DeliverySlip_OPR038(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		CHK002= new CaptureCheckSheet_CHK002(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2384")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2384")
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

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY",0, "DAY", "");
			map.put("StartDate", startDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));

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

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");
			//Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			//Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** SSM Message loading **/
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/*** Login to cgocxml **********/
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);		

			/** XFBL Message loading **/
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/** MSG005 -XFWB Message loading **/
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**XFFM Message Loading **/
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));
			//ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing1, uld);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);


			/** Switch role to Destination **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");	

			/**Mark Flight Movement**/
            cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("FlightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","StartDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,startDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");
			
			
			/***** CAPTURE CHECK SHEET for breakdown DGR***/
            cust.searchScreen("CHK002", "Capture Check Sheet");
            CHK002.listCheckSheetType("AWB");
            cust.setPropertyValue("AWBNo", cust.data("AWBNo"),proppath);
            CHK002.listAWBWithTransaction("AWBNo", "CarrierNumericCode","Breakdown");
            CHK002.captureCheckSheetAnswers(true, "leakage");
            CHK002.closeTab("CHK002", "Capture Check Sheet");

			/** OPR367- Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.verifyShipment("AWBNo");
			OPR367.SaveDetails();
			cust.closeTab("OPR367", "Import Manifest");

			/***Launch emulator - uldsighting app**/
			libr.launchUldSightingApp("uldsighting-app");
			//Login in to ULD Sighting App
			String hht1[]=libr.getApplicationParams("hht");	
			cust.loginHHT(hht1[0], hht1[1]);
			
			uldsighthht.clickDone();
			uldsighthht.enterUldNumber("UldNum");

			uldsighthht.selectFwLocationBeforeSighting("ForwardLocation");
			uldsighthht.clickSight();
			uldsighthht.verifySighted("UldNum");
			uldsighthht.clickComplete();
			libr.quitApp();

			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			// Login in to HHT
			cust.loginHHT(hht1[0], hht1[1]);

			/*** HHT - BREAKDOWN****/
			bdhht.invokeBreakdownHHTScreen();
			bdhht.enterValue("UldNum");
			bdhht.selectAwb("FullAWBNo");
			bdhht.clickSaveCaptureChecksheet();
			bdhht.selectMultipleSCC(scc);
			bdhht.addPcs("Pieces");	
			bdhht.clickSave();
			bdhht.clickSaveCaptureChecksheet();
			bdhht.clickMoreOptions();
			bdhht.clickBreakdownCompleteBtn();
			cust.clickBack("Breakdown");

			/********** OPR293-Delivery Documentation **********/
			cust.searchScreen("OPR293", "Delivery Documentation");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Delivery Documentation");
			OPR293.selectAllAWBs();
			OPR293.generateDeliveryID3();
			OPR293.verifyDNStatus("Paid");
			String DNInfo1=OPR293.retrieveDeliveryDocumentationDetails("AWBNo", "12");
			map.put("DNinfo1",DNInfo1);
			OPR293.selectAllAWBs();
			OPR293.enterCaptureHandOverDetails();
			OPR293.closeTab("OPR293", "Delivery Documentation");
			
			
			/***** CAPTURE CHECK SHEET for delivery DGR***/
			cust.searchScreen("CHK002", "Capture Check Sheet");
			CHK002.listCheckSheetType("AWB");
			cust.setPropertyValue("AWBNo", cust.data("AWBNo"),proppath);
			CHK002.listAWBWithTransaction("AWBNo", "CarrierNumericCode","Delivery");
			CHK002.captureCheckSheetAnswers(true, "leakage");
			CHK002.closeTab("CHK002", "Capture Check Sheet");


           /** Delivery HHT **/		
			deliveryhht.invokeDeliveryHHTScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			deliveryhht.enterAWBULDNum("awbNumber");
			deliveryhht.clickNext();
			deliveryhht.clickPendingButton();
			deliveryhht.selectShipment();
			libr.waitForSync(8);
			deliveryhht.clickSelectAll();
			deliveryhht.clickNext();
			deliveryhht.enterDeliverRemarks("val~Delivered");
			deliveryhht.enterCustomsReferenceNumberIfNotAutopopulated("customRefNo");
			deliveryhht.clickNext();
			deliveryhht.clickSaveCaptureChecksheet();
			deliveryhht.deliveryStatusVerify("val~DELIVERED");
			deliveryhht.clickDeliveryComplete();
			deliveryhht.enterDeliveredTo("consigneeCode");
			deliveryhht.enterVehicleInfo("VehicleNo");
			deliveryhht.enterContactNumber("ContactNumber");
			deliveryhht.clickNext();
			deliveryhht.captureSignature();
			deliveryhht.enterRemarks("val~Delivery complete");
			deliveryhht.clickPrintPOD();
			libr.waitForSync(12);
			cust.clickBack("Delivery");
			libr.quitApp();

			/*****OPR038 - Delivery Slip*******/ 			
			cust.searchScreen("OPR038", "OPR038- Delivery Slip");
			OPR038.listByAWB("CarrierNumericCode", "AWBNo");
			String pmKey = cust.data("ConsigneeCode");
			int verfCols1 [] = { 3,4,9};
			String[] actVerfValues1= { cust.data("Pieces"),cust.data("Weight"),"DELIVERY COMPLETE" };
			OPR038.verifyTableRecords( verfCols1, actVerfValues1,pmKey);
			OPR038.selectCheckbox();
			OPR038.clickReprint();
			//Steps to verify the delivery slip contents 
			String DNdetails=cust.data("DNinfo1").substring(0, 6)+"( "+cust.data("Pieces")+"/"+cust.data("Weight")+"kg,"+"\n"+"Customs reference "+"number: "+cust.data("customRefNo")+" )";
			cust.printAndVerifyReport("val~DELIVERY SLIP", "OPR038",cust.data("Destination"),cust.data("FullAWBNo"),cust.data("Origin"),cust.data("ShipmentDesc"),cust.data("Pieces"),cust.data("Weight1"),cust.data("ConsigneeName"),DNdetails,"DELIVERY COMPLETE");
			OPR038.closeTab("OPR038", "Delivery Slip");			

			/*******Verify FSU-DLV message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Delivery");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyDLV=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsDLV[]={9};
			String[] actVerfValuesDLV={"Sent"};
			MSG005.verifyMessageDetails(verfColsDLV, actVerfValuesDLV, pmKeyDLV,"val~XFSU-DLV",false);
			libr.waitForSync(1);
			
			/*** VERIFY THE MESSAGE CONTENTS***/
			map.put("pmkey", pmKeyDLV);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			msgContents.add("val~<Content>"+cust.data("customRefNo")+"</Content>");
			MSG005.verifyMessageContent(msgContents,"XFSU");
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}