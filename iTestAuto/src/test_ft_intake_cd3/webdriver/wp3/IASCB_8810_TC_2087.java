package wp3;
/** Flight level, shipment , group level instruction in scanner should be displayed **/
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
import screens.BreakDownScreen_OPR004;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;

import screens.ExportManifest_OPR344;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListCheckSheetConfig_SHR094;
import screens.ListMessages_MSG005;
import screens.ListTemplates_SHR093;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.TasksListExportBuildUp;

public class IASCB_8810_TC_2087 extends BaseSetup {

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
	public MarkFlightMovements_FLT006 FLT006;
	public ExportManifest_OPR344 OPR344;
	public ImportManifest_OPR367 OPR367;
	public GoodsAcceptance_OPR335 OPR335;
	public ListCheckSheetConfig_SHR094 SHR094;
	public MaintainFlightSchedule_FLT005 FLT005;
	public BreakDownScreen_OPR004 OPR004;
	public ListTemplates_SHR093 SHR093;
	public BuildupPlanning_ADD004 ADD004;
	public GoodsAcceptanceHHT gahht;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public TasksListExportBuildUp expbuildup;
	public ListMessages_MSG005 MSG005;

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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		SHR094 = new ListCheckSheetConfig_SHR094(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		SHR093 = new ListTemplates_SHR093(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		expbuildup= new TasksListExportBuildUp(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "TC_2087")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2087")
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

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));

			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_US"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_US"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US"));
			map.put("ShipperCountrySubDiv",
					WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US"));

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

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury",WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			/** Flight-1 Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber2", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			FlightNum1 = FlightNum1.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum1);
			map.put("FlightNo", FlightNum1.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			/** Flight-2 Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum2 = FlightNum2.replace(cust.data("prop~flight_code"), cust.data("OtherAirline"));
			map.put("FullFlightNo1", FlightNum2);
			map.put("FlightNo1", FlightNum2.substring(2));


			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);




			map.put("awbNumber1", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum1", cust.data("prop~CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));

			map.put("FullAWBNo1", cust.data("awbNumber1"));
			map.put("AWBNo", cust.data("awb1"));
			map.put("AWBNo1", cust.data("awb1"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2


			map.put("awbNumber2", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"));
			map.put("awbNum2", cust.data("prop~CarrierNumericCode")  + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));

			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo", cust.data("awb2"));
			map.put("AWBNo2", cust.data("awb2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
                          libr.quitBrowser();




			/****************** MERCURY *********************/
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** Flight - 1 **/
			map.put("FlightNumber", cust.data("FullFlightNo"));
			map.put("Org", cust.data("Transit"));
			map.put("Des", cust.data("Destination"));
			map.put("ATD",cust.data("ATD_Local"));
			map.put("ATA",cust.data("ATA_Local"));

			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
			Cgocxml.clickMessageLoader();

			/****** Loading XFBL for SECOND Flight ***/



			map.put("FullFlightNumber", cust.data("FullFlightNo"));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[0],
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[1] };
			String scc[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1] };
			String routing[] = { cust.data("Transit") + ";" + cust.data("Destination"),cust.data("Transit") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/***** XFWB Loading for AWB1 ***/
			// Create XFWB message
			map.put("awbnumber", cust.data("awbNumber1"));
			map.put("scc", cust.data("SCC").split(",")[0]);
			map.put("Shipmentdesc",cust.data("ShipmentDesc").split(",")[0]);
			String s1="unitCode=\"CMT\">"+cust.data("Dimension");
			String s3="unitCode=\"CMT\">1";
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");

			cust.modifyMessageMap("<WidthMeasure"+" "+s3+"</WidthMeasure>","<WidthMeasure"+" "+s1+"</WidthMeasure>");
			cust.modifyMessageMap("<LengthMeasure"+" "+s3+"</LengthMeasure>","<LengthMeasure"+" "+s1+"</LengthMeasure>");
			cust.modifyMessageMap("<HeightMeasure"+" "+s3+"</HeightMeasure>","<HeightMeasure"+" "+s1+"</HeightMeasure>");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			// Create XFWB message for AWB2
			map.put("awbnumber", cust.data("awbNumber2"));

			map.put("scc", cust.data("SCC").split(",")[1]);
			map.put("Shipmentdesc",cust.data("ShipmentDesc").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");

			cust.modifyMessageMap("<WidthMeasure"+" "+s3+"</WidthMeasure>","<WidthMeasure"+" "+s1+"</WidthMeasure>");
			cust.modifyMessageMap("<LengthMeasure"+" "+s3+"</LengthMeasure>","<LengthMeasure"+" "+s1+"</LengthMeasure>");
			cust.modifyMessageMap("<HeightMeasure"+" "+s3+"</HeightMeasure>","<HeightMeasure"+" "+s1+"</HeightMeasure>");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");


			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo1", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.checkSecurityDataReviewed();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/
			cust.setPropertyValue("AWBNo", cust.data("AWBNo2"),proppath);
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.checkSecurityDataReviewed();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/***** OPR026 - Capture AWB ****/
			//Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			//Check sheet for live animals
			String feedingTime=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");
			OPR026.captureCheckSheetLiveAnimals(true,startDate,feedingTime);
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");



			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);



			/*** HHT - ACCEPTANCE****/

			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo1"));
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			gahht.enterValue("UldNum");
			gahht.enterUldAcceptanceDetails("Location","awbNumber","Pieces");
			gahht.verifytranshipmentStatus("Yes");
			gahht.entertransShipmentDetails("OtherAirline","startDate");
			gahht.checkAllPartsReceivedForUldAcceptance();
			gahht.addULDDetails();
			gahht.save();
			gahht.CaptureChecksheet();


			/*** HHT - ACCEPTANCE****/



			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo2"));
			String uldNo1 = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNo1);
			gahht.enterValue("UldNum1");
			gahht.enterUldAcceptanceDetails("Location","awbNumber","Pieces");
			gahht.verifytranshipmentStatus("Yes");
			gahht.entertransShipmentDetails("OtherAirline","startDate");
			gahht.checkAllPartsReceivedForUldAcceptance();
			gahht.addULDDetails();
			gahht.save();
			gahht.CaptureChecksheet();
			libr.quitApp();

			//	AWB1
			/****OPR355 - Loose Acceptance****/

			cust.setPropertyValue("AWBNo", cust.data("AWBNo1"), proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");

			OPR335.verificationOfRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			//AWB2
			/****OPR355 - Loose Acceptance****/

			cust.setPropertyValue("AWBNo2", cust.data("AWBNo2"), proppath);
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo2", "CarrierNumericCode", "Goods Acceptance");


			OPR335.captureChecksheetLiveanimals(true,startDate,feedingTime);
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.verificationOfRFCStatus();
			libr.waitForSync(20);
			cust.closeTab("OPR335", "Goods Acceptance");

			/*****ADD004 - Build Up planning****/			
			cust.searchScreen("ADD004", "Buildup Planning");
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			ADD004.enterFlightlevelInstruction("flightlevelinstructions");

			ADD004.verifyShipmentInLoadPlan("AWBNo1");

			ADD004.selectULD("AWBNo1");

			ADD004.clickAllocate();
			ADD004.acceptAlertPopUp("val~This is a BUP shipment, Do you want to continue in BUP mode?");
			ADD004.entergrouplevelinstructions("group level instructions");
			ADD004.clickSaveAllocation();
			ADD004.enterShipmentLevelInstructions("AWBNo1","shipmentlevelinstructions");

			cust.closeTab("ADD004", "Buildup Planning");

			/*****ADD004 - Build Up planning****/			
			cust.searchScreen("ADD004", "Buildup Planning");
			ADD004.listFlight("carrierCode","FlightNo","StartDate");
			ADD004.verifyShipmentInLoadPlan("AWBNo2");

			ADD004.selectULD("AWBNo2");

			ADD004.clickAllocate();
			ADD004.acceptAlertPopUp("val~This is a BUP shipment, Do you want to continue in BUP mode?");
			ADD004.entergrouplevelinstructions("group level instructions");
			ADD004.clickSaveAllocation();
			ADD004.enterShipmentLevelInstructions("AWBNo2","shipmentlevelinstructions");
			cust.closeTab("ADD004", "Buildup Planning");
			


			/***Launch emulator -Export Build Up **/
			libr.launchExportBuildUpApp("exportbuildup-app-release");		

			//Login in to Export BuildUp App
			String [] expbuild=libr.getApplicationParams("hht");	
			cust.loginExportBuildUp(expbuild[0], expbuild[1]);
			expbuildup.verifygrouplevelinstrsuction("AWBNo2","group level instructions");
        	expbuildup.selectTaskCreatedaftersearching("FlightNo");
			expbuildup.markTaskInProgress(); 
			cust.waitForSync(5);
			expbuildup.verifyflightlevelinstrsuction("FlightNo","flightlevelinstructions");
			expbuildup.verifyShipmentlevelinstrsuction("AWBNo2","shipmentlevelinstructions");

			libr.quitApp();
			
			
			
			/*******Verify FSU-FOH message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Freight On Hand");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCS=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsRCS[]={9};
			String[] actVerfValuesRCS={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-FOH",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
			
			/*******Verify FSU-RCT message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Inbound CTM");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-RCT",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			libr.quitBrowser();



		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
