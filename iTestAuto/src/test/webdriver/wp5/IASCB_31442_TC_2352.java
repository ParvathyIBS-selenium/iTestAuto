package wp5;

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
import screens.CaptureAWB_OPR026;
import screens.CaptureCheckSheet_CHK002;
import screens.Cgocxml;
import screens.GoodsAcceptance_OPR335;
import screens.ImportDocumentation_OPR001;
import screens.ListDiscrepancies_OPR050;
import screens.ListFlightDiscrepancy_OPR047;
import screens.ListMessages_MSG005;
import screens.Mercury;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;
import screens.BuildupPlanning_ADD004;
import screens.ExportManifest_OPR344;
import screens.MarkFlightMovements_FLT006;
import screens.ImportManifest_OPR367;
import screens.ImportPlanningProgress_ADD008;


/**  Document discrepancies count should be displayed during flag flight from import documentation- missing AWBs.  **/



public class IASCB_31442_TC_2352 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public MaintainFlightSchedule_FLT005 FLT005;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public ImportPlanningProgress_ADD008 ADD008;
	public ListDiscrepancies_OPR050 OPR050;
	public ImportDocumentation_OPR001 OPR001;
	public CaptureCheckSheet_CHK002 CHK002;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "wp5";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		ADD008=new ImportPlanningProgress_ADD008(driver, excelreadwrite, xls_Read); 
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR001 = new ImportDocumentation_OPR001(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		OPR050 = new ListDiscrepancies_OPR050(driver, excelreadwrite, xls_Read);
		CHK002= new CaptureCheckSheet_CHK002(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2352")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2352")
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

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_WER"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_WER"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_WER"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_WER"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_WER"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_WER"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_WER"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_WER"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_WER"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_WER"));

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

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_WER"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_WER"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_WER"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_WER"));


			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
		
            String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);

			map.put("SSMStartDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormatWithTimeZone("ddMMM",7, "DAY", ""));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());


			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");

            /** Maintain Flight Screen (FLT005) . Taking fresh flight**/

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code_KL") + cust.data("prop~flightNo"),proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");

			//Flight details

			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("FlightNumber", cust.data("FullFlightNo"));



			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber1
			map.put("awbNumber1", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));
			map.put("FullAWBNo", cust.data("awbNumber1"));
			map.put("AWBNo", cust.data("awb1"));
			map.put("AWBNo1", cust.data("awb1"));
			
			

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber2
			map.put("awbNumber2", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));
			map.put("FullAWBNo2", cust.data("awbNumber2"));
			map.put("AWBNo2", cust.data("awb2"));
		
		// Checking AWB is fresh or Not (AWBNumber3)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// AWBNumber3
			map.put("awbNumber3", cust.data("prop~CarrierNumericCodeAMS") + "-" + cust.data("prop~AWBNo"));
			map.put("awb3", cust.data("prop~AWBNo"));
			map.put("FullAWBNo3", cust.data("awbNumber3"));
			map.put("AWBNo3", cust.data("awb3"));
			
		
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


			

			map.put("scc1", cust.data("SCC").split(",")[1]);
			map.put("Desc", cust.data("ShipmentDesc").split(",")[1]);
			
			
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");

			/**** OPR026 - Capture AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			// Enter shipment details
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","carrierCode"); 
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterSCC(cust.data("scc1"));
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "Desc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");

			

			map.put("scc1", cust.data("SCC").split(",")[0]);
			map.put("Desc", cust.data("ShipmentDesc").split(",")[0]);
			
			
			/**** OPR026 - Capture AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			// Enter shipment details
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","carrierCode"); 
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterSCC(cust.data("scc1"));
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "Desc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");



			
			map.put("scc1", cust.data("SCC").split(",")[0]);
			map.put("Desc", cust.data("ShipmentDesc").split(",")[0]);
			
			
			/**** OPR026 - Capture AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo3", "CarrierNumericCode");
			// Enter shipment details
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","carrierCode"); 
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterSCC(cust.data("scc1"));
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "Desc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");
			libr.quitBrowser();


			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
			



			/** MSG005 -XFFM Message loading **/

			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
			String uldNo1 = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo1);
			map.put("ULDNo1", cust.data("UldNum").replaceAll("[^0-9]", ""));



			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String shipment[] = {
					cust.data("awbNumber1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
							+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[1],
							cust.data("awbNumber2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
									+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[0],cust.data("awbNumber3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight") + ";"
											+ cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[0]};
			String scc[] = { cust.data("SCC").split(",")[1], cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[0] };

			String routing1[] = {
					cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
							+ cust.data("DestinationAirport"),
							cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
									+ cust.data("DestinationAirport"),cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination") + ";"
											+ cust.data("DestinationAirport")};
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo1") + ";" + cust.data("carrierCode") };


			int []shipments={3};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing1, uld,shipments);
			Cgocxml.sendMessageCgoCXML("ICARGO");


			libr.quitBrowser();
			
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			
			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");
			
			/**XTMV  Message Loading **/
			map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
			cust.createXMLMessage("MessageExcelAndSheetXTMV", "MessageParamXTMV");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XTMV", true);
			cust.closeTab("MSG005", "List Message");


			/**Switch role to Destination**/
			cust.switchRole("Destination", "Origin", "RoleGroup");
			
			

             /**** Import Manifest ***/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode","prop~flightNo", "StartDate");
			//Breakdown to stamp the discrepancies
			String pmkey = cust.data("UldNum");
			OPR367.clickCheckBox_ULD(pmkey);
			OPR367.clickBreakdownButton();
			OPR367.enterBdnDetailsforAWB(cust.data("BDNlocation"), cust.data("Pieces"), cust.data("Weight"),"AWBNo1");
			OPR367.enterBdnDetailsforAWB(cust.data("BDNlocation"), cust.data("Pieces"), cust.data("Weight"),"AWBNo2");
			OPR367.enterBdnDetailsforAWB(cust.data("BDNlocation"), cust.data("Pieces"), cust.data("Weight"),"AWBNo3");
			OPR367.clickBreakdownComplete();
			OPR367.ClickYesAlert();	
			cust.closeTab("OPR367", "Import Manifest");


			cust.searchScreen("OPR001", "Import Documentation: OPR001");
			OPR001.listFlightDetails("carrierCode", "FlightNo", "StartDate");
			// Clicking AWB Document received checkbox
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo1"));
			OPR001.captureCheckSheetForDG(true,"leakage");
			OPR001.clickAWBNumberCheckBox(cust.data("AWBNo1"));
			OPR001.clickAWBDocumentReceived(cust.data("AWBNo1"));
			OPR001.saveDetails();
			OPR001.ClickYesAlert();
			cust.waitForSync(5);
			OPR001.clickFlagButton();
			OPR001.verifyWarningMessageWith2AWBsWithDiscrepenciesAfterFlag("AWBNo2","AWBNo3");
			cust.closeTab("OPR001","Import Documentation: OPR001");


			/** List Flight Discrepancy **/
			cust.searchScreen("OPR050", "List Discrepancies");
			OPR050.listByFlight("carrierCode","prop~flightNo","StartDate");
			int[] verfCols={10};
			String[] actVerfValues={"MSAW"};
			map.put("AWBNo",cust.data("AWBNo2"));
			OPR050.verifyDiscrepancydetails(verfCols,actVerfValues);
			map.put("AWBNo", cust.data("AWBNo3"));
			OPR050.verifyDiscrepancydetails(verfCols,actVerfValues);
			cust.closeTab("OPR050","List Discrepancies");


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
