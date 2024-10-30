package trucking;


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
import screens.MaintainFlightSchedule_FLT005;
import screens.ImportManifest_OPR367;
import screens.UldSightingHHT;
import screens.BreakdownHHT;
import screens.DeliveryDocumentation_OPR293;
import screens.DeliveryHHT;
import screens.DeliverySlip_OPR038;
import screens.ListMessages_MSG005;
import screens.MarkFlightMovements_FLT006;


/**  Deliver the shipment by scanning AWB/HAWB number through android screen. **/


public class IASCB_4748_TC_2386_Descoped extends BaseSetup {

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
	public UldSightingHHT uldsighthht;
	public BreakdownHHT bdhht;
	public DeliveryDocumentation_OPR293 OPR293;
	public DeliveryHHT deliveryhht;
	public DeliverySlip_OPR038 OPR038;
	public ListMessages_MSG005 MSG005;
	public MarkFlightMovements_FLT006 FLT006;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Trucking.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String proppathsst = "\\src\\resources\\SSTLocators.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "Trucking_FT";

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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		uldsighthht=new UldSightingHHT(driver, excelreadwrite, xls_Read);
		bdhht=new BreakdownHHT(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		deliveryhht = new DeliveryHHT(driver, excelreadwrite, xls_Read);
		OPR038=new DeliverySlip_OPR038(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2386")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2386")
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

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			cust.createFlight("FullFlightNumber");
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

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);


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


			/******* OPR026 - Capture AWB *****/

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("FullAWBNumber", cust.data("CarrierNumericCode")+" "+"-"+" "+cust.data("prop~AWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));


			/** Maintain Flight Screen (FLT005) . Taking fresh flight**/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			System.out.println(FlightNum);



			/** SSM Message loading **/

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");




			/** XFSU-BKD Message loading **/
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("FlightNumber", cust.data("FullFlightNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFSU", "MessageParamXFSU");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "BKD", true);


			/** XFBL Message loading **/


			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);

			/** XFWB Message loading **/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);


			//Create XFZB message
			cust.createXMLMessage("MessageExcelAndSheetXFZB", "MessageParamXFZB");
			String hawbNo=cust.data("AWBNo")+"H1";
			map.put("hawbNo", hawbNo);
			cust.modifyXml("XFZB","ns2:BusinessHeaderDocument", "ID", hawbNo);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFZB", true);

			/** XFFM Message loading**/
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };

			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing1, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");

			/** Switch role to Destination **/
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("prop~flightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");


			/*****OPR367 - Import Manifest*******/ 

			//Verify the AWB details
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
			OPR367.maximizeAllDetails();
			OPR367.clickDeconsolCheckbox();
			OPR367.SaveDetails();
			OPR367.verifyDeconsolChecked();
			OPR367.closeTab("OPR367", "Import Manifest");


			/***Launch emulator - uldsighting app**/
			libr.launchUldSightingApp("uldsighting-app");
			//Login in to ULD Sighting App
			String [] hht2=libr.getApplicationParams("hht");	
			cust.loginHHT(hht2[0], hht2[1]);

			uldsighthht.clickDone();
			uldsighthht.enterUldNumber("UldNum");
			//Select forward location
			uldsighthht.selectFwLocationBeforeSighting("ForwardLocation");
			uldsighthht.clickSight();
			uldsighthht.verifySighted("UldNum");
			uldsighthht.clickComplete();
			libr.quitApp();




			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");
			//	Login in to HHT
			cust.loginHHT(hht2[0], hht2[1]);

			/*** HHT - BREAKDOWN ****/


			bdhht.invokeBreakdownHHTScreen();
			bdhht.enterValue("UldNum");
			bdhht.selectAwb("FullAWBNo");
			bdhht.enterHAWB("hawbNo");
			bdhht.selectMultipleSCC(scc);
			bdhht.addPcs("Pieces");	
			bdhht.clickSave();

			//Marking BreakdownComplete
			cust.clickBack("Breakdown");
			bdhht.enterValue("UldNum");
			bdhht.clickMoreOptions();
			bdhht.clickBreakdownCompleteWithHAWB();
			cust.clickBack("Breakdown");




			// Generate delivery id
			cust.searchScreen("OPR293", "Delivery Documentation");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Delivery Documentation");
			OPR293.selectAllAWBs();
			OPR293.generateDeliveryID3();
			OPR293.verifyDNStatus("Paid");
			String DNInfo=OPR293.retrieveDeliveryDocumentationDetails("prop~AWBNo", "12");
			map.put("DNinfo",DNInfo);
			System.out.println(cust.data("DNinfo"));
			map.put("DeliveryID", OPR293.getDeliveryID());
			OPR293.selectAllAWBs();
			OPR293.enterCaptureHandOverDetails();
			OPR293.verifyHandoverTickMark("prop~AWBNo");
			OPR293.closeTab("OPR293", "Delivery Documentation");


			/** Delivery HHT **/
			//Perform delivery in HHT by listing the Delivery ID
			deliveryhht.invokeDeliveryHHTScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			deliveryhht.enterHAWB("hawbNo");
			deliveryhht.selectHAWBForDelivery("prop~AWBNo","FullAWBNumber");
			deliveryhht.clickNext();
			deliveryhht.clickPendingButton();
			deliveryhht.selectShipment();
			deliveryhht.clickSelectAll();
			deliveryhht.clickNext();
			deliveryhht.enterDeliverRemarks("val~Delivered");
			deliveryhht.enterCustomsReferenceNumber("customRefNo");	
			deliveryhht.clickNext();
			deliveryhht.deliveryStatusVerify("val~DELIVERED");
			deliveryhht.clickDeliveryComplete();
			deliveryhht.enterDeliveredTo("consigneeCode");
			deliveryhht.enterVehicleInfo("VehicleInfo");
			deliveryhht.enterContactNumber("ContactNumber");
			deliveryhht.clickNext();
			deliveryhht.captureSignature();
			deliveryhht.enterRemarks("val~Delivery complete");
			deliveryhht.clickPrintPOD();
			libr.quitApp();




			/******MSG005 - List Messages******/

			//Verify FSU-DLV message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.clickClearButton();
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Delivery");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyDLV=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
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



			/*******  Delivery Slip*******/ 

			cust.searchScreen("OPR038", "OPR038- Delivery Slip");
			OPR038.listByAWB("CarrierNumericCode", "AWBNo");
			String pmKey = cust.data("ConsigneeCode");
			int verfCols1 [] = {3,4,9};
			String[] actVerfValues1= { cust.data("Pieces"),cust.data("Weight"),"DELIVERY COMPLETE" };
			OPR038.verifyTableRecords( verfCols1, actVerfValues1,pmKey);
			OPR038.selectCheckbox();
			OPR038.clickReprint();
			//Steps to verify the delivery slip contents 
			String DNdetails=cust.data("DNinfo").substring(0, 5)+"( "+cust.data("Pieces")+"/"+cust.data("Weight")+"kg,"+"\n"+"Customs reference "+"number: "+cust.data("customRefNo")+" )";
			cust.printAndVerifyReport("val~DELIVERY SLIP", "OPR038",cust.data("Destination"),cust.data("FullAWBNo"),cust.data("Origin"),cust.data("val~Consol Shipment"),cust.data("Pieces"),cust.data("Weight"),cust.data("ConsigneeName"),DNdetails,"DELIVERY COMPLETE");
			OPR038.closeTab("OPR038", "Delivery Slip");



		} catch (Exception e) {
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