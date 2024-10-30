package ffl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.OffloadEnquiry_OPR338;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.ListMessages_MSG005;

/**************TC_15_Verify ULD is offloaded from the flight when FFL is processed for split shipments.********/

public class FFL_8879_TC_15 extends BaseSetup {
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
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public GoodsAcceptance_OPR335 OPR335;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public ListMessages_MSG005 MSG005;
	public OffloadEnquiry_OPR338 OPR338;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "ffl";

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
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);   
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR338=new OffloadEnquiry_OPR338(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "FFL_8879_TC_15")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "FFL_8879_TC_15")
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

			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_TypeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));
			map.put("RegulatedAgent", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));

			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Amsterdam");
			String currentday=cust.createDateFormatWithTimeZone("ddMMMYY", 0, "DAY", "");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);

			String startDate = cust.createDateFormat("dd-MMM-YYYY",0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 2, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("Year", cust.createDateFormat("yy", 0, "DAY", ""));
			System.out.println(cust.data("Year"));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));


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

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));


			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));

			libr.quitBrowser();

			/****************** MERCURY *********************/
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			//Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to cgocxml **********/
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/**** XFSU-BKD Message loading ****/
			cust.createXMLMessage("MessageExcelAndSheetBKD", "MessageParamBKD");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

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



			/**** OPR026 - Capture AWB ****/
			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/** ULDAcceptance **/

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCodeAMS", "Goods Acceptance");
			String uldNo1 = OPR335.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo1);
			String uldNo2 = OPR335.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNo2);
			OPR335.uldShipmentDetails("Pieces1", "Weight1", "Location", "UldNum", "");
			OPR335.addULDDetails();
			OPR335.addNewULDAcceptance("Pieces2", "Weight2", "Location", "UldNum1", "");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.clickSave("OPR335");
			cust.closeTab("OPR335", "Goods Acceptance");


			/**** OPR344 - Export manifest****/

			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			OPR344.assignUldPlanningSection("UldNum");
			OPR344.assignUldPlanningSection("UldNum1");
			OPR344.manifestDetails();
			cust.closeTab("OPR344", "Export Manifest");

			/**** OFFLOADING ULD using FFL****/

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury1"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));


			/****************** MERCURY *********************/

			// Login to "MERCURY"
			driver.get(mercury[0]); // Enters URL

			cust.loginToMercury(cust.data("prop~mercuryUN2"),cust.data("prop~mercuryPWD2"));

			/** Mercury-FFL Message loading **/

			String rkeyStart="200";
			Random random = new Random(); 
			int rand6Digt = random.nextInt(999999);
			String rkey=rkeyStart+rand6Digt;
			map.put("KeyVal", rkey);

			cust.createTextMessage("MessageExcelAndSheetFFL","MessageParamFFL");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury","DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("FFL");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			cust.switchRole("Origin", "Origin", "RoleGroup");

			/** CHECKING FFL IS processed FOR flight **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("FFL");
			MSG005.selectStatus("ProcessedSuccessfully");
			MSG005.clickList();
			String pmKeyFFL = cust.data("carrierCode") + " - " + cust.data("FlightNo") + " - " + cust.data("Day")
			+ " - " + cust.data("Month").toUpperCase() + " - " + cust.data("Year") + " - " + cust.data("Origin");
			int verfColsFFL[] = { 9 };
			String[] actVerfValuesFFL = { "Processed Successfully" };
			MSG005.verifyMessageDetails(verfColsFFL, actVerfValuesFFL, pmKeyFFL, "val~FFL", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");



			/******* Verify xFSU-DIS-OFLD for the offloaded AWB message in MSG005 ******/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Discrepancy");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "", "AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyDIS = cust.data("prop~CarrierNumericCodeAMS") + " - " + cust.data("AWBNo");
			int verfColsDIS[] = { 9 };
			String[] actVerfValuesDIS = { "Sent" };
			MSG005.verifyMessageDetails(verfColsDIS, actVerfValuesDIS, pmKeyDIS, "val~XFSU-DIS", false);
			MSG005.clickCheckBox("AWBNo");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			msgContents.add("val~<DiscrepancyDescriptionCode>"+cust.data("val~OFLD")+"</DiscrepancyDescriptionCode>");		
			MSG005.verifyMessageContent(msgContents,"XFSU",true);
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/*****OPR344 - Export manifest****/	

			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			OPR344.verifyFlightStatus("val~Offloaded");
			OPR344.verifyULDInAssignedShipment("UldNum1", true);
			OPR344.clickBuildUpComplete();
			OPR344.manifestDetails();
			OPR344.finalizeFlight(true);
			cust.closeTab("OPR344", "Export Manifest");

			/** CHECKING XFFM TRIGGERED FOR AWB **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFFM");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFFM = cust.data("carrierCode") + " - " + cust.data("FlightNo") + " - " + cust.data("Day")
			+ " - " + cust.data("Month").toUpperCase() + " - " + cust.data("Origin");
			int verfColsXFFM[] = { 9 };
			String[] actVerfValuesXFFM = { "Sent" };
			MSG005.verifyMessageDetails(verfColsXFFM, actVerfValuesXFFM, pmKeyXFFM, "val~XFFM", false);
			MSG005.clickCheckBox(pmKeyXFFM);
			MSG005.clickView();
			String wtUnit="\"KGM\"";
			String volUnit="\"MTQ\"";
			List <String> msgContentsPresent=new ArrayList<String>();		
			/**Verifying UldNum1 details**/
			msgContentsPresent.add("val~<TypeCode>ULD</TypeCode>"+"\n"+"<UtilizedUnitLoadTransportEquipment>"+"\n"+"<ID>"+cust.data("UldNum1").substring(3, 8)+"</ID>"+"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight2")+"</GrossWeightMeasure>"+"\n"+"<GrossVolumeMeasure unitCode="+volUnit+">"+cust.data("Volume2")+"</GrossVolumeMeasure>"+"\n"+"<PieceQuantity>"+cust.data("Pieces2")+"</PieceQuantity>");

			MSG005.verifyMessageContent(msgContentsPresent,"XFFM",true);
			MSG005.closeView();		
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/******* OFFLOAD ENQUIRY SCREEN *******/

			cust.searchScreen("OPR338","OffloadEnquiry");
			OPR338.listByFlight("carrierCode","FlightNo");
			int[] verfCols = {1,4,9,10,15};
			String[] actVerfValues1={cust.data("FullFlightNo"),cust.data("UldNum"),cust.data("Pieces1"),cust.data("Weight1"),"FFL Offload"};
			OPR338.verifyOffloadDetails(verfCols, actVerfValues1);
			cust.closeTab("OPR338","Offload Enquiry");
			libr.quitBrowser();



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
