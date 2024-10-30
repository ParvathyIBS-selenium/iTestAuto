package mvp_reg_exportmanifest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.ExportManifest_OPR344;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;

/**
 * Reopen flight after finalization
 **/
public class ManifestIAD_3 extends BaseSetup {

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
	public GeneratePaymentAdvice_CSH007 CSH007;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public BuildupPlanning_ADD004 ADD004;
	public ListMessages_MSG005 MSG005;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_reg_exportmanifest";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		// excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		ADD004 = new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "ManifestIAD_3")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "ManifestIAD_3")
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
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/** Pre Condition Starts **/
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/*** Storing Values to Map ***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury",WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
		

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo2", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			cust.setPropertyValue("AWBNo2", cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo2", cust.data("prop~FullAWBNo2"));
			map.put("AWBNo2", cust.data("prop~AWBNo2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);		
			libr.quitBrowser();

			/***** ASM Message loading - MERCURY *****/
			// Re-launch browser
			driver = libr.relaunchBrowser("chrome");
			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]);

			// Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			/*** LOADING XFBL - CGOCXML ***/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);

			/**** CREATING XFBL MESSAGES - CGOCXML ****/
			// Create XFBL message
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/**** CREATING XFBL MESSAGES **/
			// Login to "CGOCXML" and load XFBL message
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc"),libr.data("FullAWBNo2") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
							+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC"),cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/***** LOGIN TO ICARGO *****/
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/**** OPR026 - Capture AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.verifySource("val~GBL", true);
			// Enter shipment details
			OPR026.enterRouting("Destination", "prop~flight_code");
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR026 - Capture AWB ****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			OPR026.verifySource("val~GBL", true);
			// Enter shipment details
			OPR026.enterRouting("Destination", "prop~flight_code");
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecuteOnly();

			// Generate Payment Advice Screen
			CSH007.verifyServiceCode("val~AWBI");
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			cust.closeTab("OPR026", "Capture AWB");

			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
			OPR026.asIsExecuteOnly();

			// Generate Payment Advice Screen
			CSH007.verifyServiceCode("val~AWBI");
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			cust.closeTab("OPR026", "Capture AWB");

			/** ULDAcceptance  - AWB1 **/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			String uldNo = OPR335.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			OPR335.uldShipmentDetails("Pieces", "Weight", "Location", "UldNum", "");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/** ULDAcceptance - AWB2 **/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo2", "CarrierNumericCode", "Goods Acceptance");
			String uldNo2 = OPR335.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNo2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			OPR335.uldShipmentDetails("Pieces", "Weight", "Location", "UldNum1", "");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR344.verifyULDInPlannedSection("UldNum");
			OPR344.assignUldPlanningSection("UldNum");
			OPR344.clickBuildUpComplete();
			
			OPR344.verifyULDInPlannedSection("UldNum1");
			OPR344.assignUldPlanningSection("UldNum1");
			OPR344.clickBuildUpComplete();	

			OPR344.clickManifest();
			OPR344.printManifestOk();	         
			cust.printAndVerifyReport("val~CARGO MANIFEST","OPR344",true,cust.data("carrierCode")+" "+cust.data("FlightNo"),cust.data("UldNum"),cust.data("UldNum1"));		
			OPR344.printManifestClose();
			OPR344.verifyFlightStatus("val~Manifested");
			cust.closeTab("OPR344", "Export manifest");

			/******* Verify xFSU-MAN message in MSG005 ******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Manifest Details");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFSU = cust.data("prop~CarrierNumericCode") + " - " + cust.data("AWBNo");
			int verfColsFSU[] = { 9 };
			String[] actVerfValuesFSU = { "Sent" };
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU, "val~XFSU-MAN", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/******* Verify xFSU-MAN message in MSG005 ******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Manifest Details");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFSU2 = cust.data("prop~CarrierNumericCode") + " - " + cust.data("AWBNo2");
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU2, "val~XFSU-MAN", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo", "StartDate");		
			OPR344.finalizeFlight();
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export manifest");

			/** CHECKING XFFM TRIGGERED FOR FLIGHT **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFFM");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FFM", "FlightNo", "AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFFM = cust.data("prop~flight_code") + " - " + cust.data("FlightNo") + " - " + cust.data("Day")
			+ " - " + cust.data("Month").toUpperCase() + " - " + cust.data("Origin");
			int verfColsXFFM[] = { 9 };
			String[] actVerfValuesXFFM = { "Sent" };
			MSG005.verifyMessageDetails(verfColsXFFM, actVerfValuesXFFM, pmKeyXFFM, "val~XFFM", false);
			MSG005.clickMessageCheckBox("2");
	        MSG005.clickView();	        
	        List <String> msgContentsPresent=new ArrayList<String>();		
			/**Verifying Both ULDS and AWBs**/
			msgContentsPresent.add("val~<TransportContractDocument>"+"\n"+"<ID>"+cust.data("FullAWBNo")+"</ID>"+"\n"+"</TransportContractDocument>");
			msgContentsPresent.add("val~<TransportContractDocument>"+"\n"+"<ID>"+cust.data("FullAWBNo2")+"</ID>"+"\n"+"</TransportContractDocument>");
			msgContentsPresent.add("val~<TypeCode>ULD</TypeCode>"+"\n"+"<UtilizedUnitLoadTransportEquipment>"+"\n"+"<ID>"+cust.data("UldNum").substring(3, 8)+"</ID>");
			msgContentsPresent.add("val~<TypeCode>ULD</TypeCode>"+"\n"+"<UtilizedUnitLoadTransportEquipment>"+"\n"+"<ID>"+cust.data("UldNum1").substring(3, 8)+"</ID>");			
			MSG005.verifyMessageContent(msgContentsPresent,"XFFM",true);
			MSG005.closeView();		
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR344.reOpenFlight();
			OPR344.verifyFlightStatus("val~Reopened");
			OPR344.offloadULDWithReasonAfterBuildupComplete("UldNum");
			OPR344.verifyFlightStatus("val~Offloaded");
			OPR344.clickManifest();
			OPR344.printManifestOk();
			cust.printAndVerifyReport("val~CARGO MANIFEST","OPR344",true,cust.data("prop~flight_code")+" "+cust.data("FlightNo"),cust.data("UldNum1"));
			OPR344.printManifestOk();
			cust.printAndVerifyReport("val~CARGO MANIFEST","OPR344",false,cust.data("UldNum"));
			OPR344.printManifestClose();
			OPR344.finalizeFlight();
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export manifest");

			/******* Verify xFSU-DIS-SSPD message in MSG005 ******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Discrepancy");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyDIS = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo");
			int verfColsDIS[] = { 9 };
			String[] actVerfValuesDIS = { "Sent" };
			MSG005.verifyMessageDetails(verfColsDIS, actVerfValuesDIS, pmKeyDIS, "val~XFSU-DIS", false);
			MSG005.clickCheckBox("AWBNo");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			msgContents.add("val~<DiscrepancyDescriptionCode>"+cust.data("val~SSPD")+"</DiscrepancyDescriptionCode>");        			
			MSG005.verifyMessageContent(msgContents,"XFSU",true);
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");			

			

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
