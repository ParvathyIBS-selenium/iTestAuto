package mvp_reg_acceptance;

/**** Low Show Shipment Scenario**/

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
import screens.Cgocxml;
import screens.CaptureAWB_OPR026;
import screens.Cgomon;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.SecurityAndScreening_OPR339;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.ExportManifest_OPR344;

public class LowShow_TC_9484 extends BaseSetup

{

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
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public ListMessages_MSG005 MSG005; 
	public Mercury mercuryScreen;
	public Cgomon Cgomon;
	public Cgocxml Cgocxml;

	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName="mvp_reg_acceptance";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_9484")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_9484")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);


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

			/***Storing Values to Map***/			
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "credit_CustomerId_TG"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "credit_CustomerName_TG"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "credit_postCode_TG"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "credit_streetName_TG"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "credit_cityName_TG"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "credit_countryId_TG"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "credit_countryName_TG"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "credit_countrySubdivision_TG"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "credit_telephoneNo_TG"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "credit_email_TG"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "LFW"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "credit_CustomerId_TG"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "credit_CustomerName_TG"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "credit_CASSCode_TG"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "credit_IATACode_TG"));	
			
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			map.put("ReceipienttaddressAfls1", WebFunctions.getPropertyValue(telexproppath, "ReceiptaddressAfls1"));
			map.put("ReceiptaddressVccustoms", WebFunctions.getPropertyValue(telexproppath, "ReceiptaddressVccustoms"));
			
			//Regulated agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry"));


			/****************** MERCURY *********************/
			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			//Login to iCargo	
			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	


			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/**** CREATING XFBL MESSAGES **/

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);

			cust.createXMLMessage("MessageExcelAndSheet", "MessageParam");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC")};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			//XFWB Message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			/***** RELOGIN TO ICARGO***/
			driver=libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/**** OPR026 - Capture AWB****/		
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");	
			//As Is Execute AWB
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/** CHECKING XFWB TRIGGERED FOR AWB **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFWB=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination");
			int verfColsXFWB[]={9};
			String[] actVerfValuesXFWB={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFWB, actVerfValuesXFWB, pmKeyXFWB,"val~XFWB",true);
			libr.waitForSync(1);

			/*** VERIFY THE MESSAGE CONTENTS***/
			map.put("pmkey", pmKeyXFWB);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();	
			String wtUnit="\"KGM\"";
			String curr="\"EUR\"";
			String volUnit="\"MTQ\"";
			msgContents.add("val~<IncludedMasterConsignmentItem>"+"\n"+"<SequenceNumeric>1</SequenceNumeric>"+
					"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight")+"</GrossWeightMeasure>"+
					"\n"+"<GrossVolumeMeasure unitCode="+volUnit+">"+cust.data("Volume")+"</GrossVolumeMeasure>"+
					"\n"+"<PieceQuantity>"+cust.data("Pieces")+"</PieceQuantity>");


			msgContents.add("val~<ApplicableFreightRateServiceCharge>"+"\n"+"<CategoryCode>"+cust.data("rateClass")+"</CategoryCode>"+"\n"+
					"<ChargeableWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight")+"</ChargeableWeightMeasure>"+"\n"+
					"<AppliedRate>"+cust.data("IATARate")+"</AppliedRate>"+"\n"+"<AppliedAmount currencyID="+curr+">"+cust.data("IATAcharge")+"</AppliedAmount>"+"\n"+
					"</ApplicableFreightRateServiceCharge>");

			//Verify message contents
			MSG005.verifyMessageContent(msgContents,"XFWB");
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces","Weight1");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");

			//Reopen AWB
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.clickReopen();
			OPR026.updateStatedValues("Pieces","Weight1","Volume");
			OPR026.clickChargesAcc();
			OPR026.clearIATAChargeAndUpdateChargeableWgt("Weight1");
			OPR026.asIsExecute();       
			cust.closeTab("OPR026", "Capture AWB");

			/** CHECKING XFWB TRIGGERED FOR AWB **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFWB1=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination");
			int verfColsXFWB1[]={9};
			String[] actVerfValuesXFWB1={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFWB1, actVerfValuesXFWB1, pmKeyXFWB1,"val~XFWB",true);
			libr.waitForSync(1);

			/*** VERIFY THE MESSAGE CONTENTS***/
			map.put("pmkey", pmKeyXFWB);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents1=new ArrayList<String>();	
			/**Commodity Details**/
			msgContents1.add("val~<IncludedMasterConsignmentItem>"+"\n"+"<SequenceNumeric>1</SequenceNumeric>"+
					"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight1")+"</GrossWeightMeasure>"+
					"\n"+"<GrossVolumeMeasure unitCode="+volUnit+">"+cust.data("Volume")+"</GrossVolumeMeasure>"+
					"\n"+"<PieceQuantity>"+cust.data("Pieces")+"</PieceQuantity>");


			msgContents1.add("val~<ApplicableFreightRateServiceCharge>"+"\n"+"<CategoryCode>"+cust.data("rateClass")+"</CategoryCode>"+"\n"+
					"<ChargeableWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight1")+"</ChargeableWeightMeasure>"+"\n"+
					"<AppliedRate>"+cust.data("IATARate")+"</AppliedRate>"+"\n"+"<AppliedAmount currencyID="+curr+">"+cust.data("IATAcharge1")+"</AppliedAmount>"+"\n"+
					"</ApplicableFreightRateServiceCharge>");

			//Verify message contents
			MSG005.verifyMessageContent(msgContents1,"XFWB");
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.clickSave();
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			/*******Verify FSU-FOH message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Freight On Hand");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFSU=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsFSU[]={9};
			String[] actVerfValuesFSU={"Sent"};
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU",true);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");

			OPR335.clicksecurityAndScreening();
			cust.switchToFrame("frameName", "if11");
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight","val~Pass");
			OPR339.addAgentDetails("AgentType","AgentCountryId","RegulatedAgentCode","Expiry","OPR335","if11");
			OPR339.checkSecurityDataReviewed();
			OPR339.OkButtonAfterScreeningSave();
			cust.switchToFrame("contentFrame", "OPR335");		

			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/*******Verify FSU-RCS message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCS=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsRCS[]={9};
			String[] actVerfValuesRCS={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-RCS",true);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo", "StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWB("UldNum","0","CarrierNumericCode","AWBNo","Pieces","Weight1");
			cust.closeTab("OPR344", "Export Manifest");

			/*******Verify FSU-PRE message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Prepared for loading");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyPRE=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsPRE[]={9};
			String[] actVerfValuesPRE={"Sent"};
			MSG005.verifyMessageDetails(verfColsPRE, actVerfValuesPRE, pmKeyPRE,"val~XFSU-PRE",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo", "StartDate");
			OPR344.clickBuildUpComplete();
			cust.closeTab("OPR344", "Export Manifest");

			/** CHECKING XFUM TRIGGERED FOR AWB **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFUM");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFUM=cust.data("Origin")+" - "+cust.data("UldNum").substring(3,8);
			int verfColsFUM[]={9};
			String[] actVerfValuesFUM={"Sent"};
			String ULDNo = uldNum.substring(3,8);
			MSG005.getNumberOfRecordsPresent(ULDNo,1);
			MSG005.verifyMessageDetails(verfColsFUM, actVerfValuesFUM, pmKeyFUM,"val~xFUM",true);
			libr.waitForSync(1); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			OPR344.closeFLTforBDP();
			OPR344.verifyBDPbuttonStatus("Open");
			OPR344.clickManifest();
			OPR344.printManifestOk();
			cust.printAndVerifyReport("val~CARGO MANIFEST","OPR344",true,cust.data("carrierCode")+" "+cust.data("FlightNo"),cust.data("UldNum"),cust.data("FullAWBNo"));
			//Pieces, Weight Verification
			String elementstoVerify[]={cust.data("Weight1")+".00",cust.data("Pieces")};
	        int elementsIndexfromPmKey[]={6,1};		
	        OPR344.printManifestOk();
	        cust.verifyNumericElementsInReport("val~CARGO MANIFEST","OPR344", cust.data("FullAWBNo"), elementstoVerify, elementsIndexfromPmKey);			
			OPR344.printManifestClose();
			OPR344.verifyFlightStatus("val~Manifested");
			cust.closeTab("OPR344", "Export manifest");      

			/********** CHECKING IF XFSU-MAN GOT TRIGGERD ****/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Manifest Details");
			MSG005.clickList();
			String pmKeyMAN = cust.data("prop~CarrierNumericCode") + " - " + cust.data("prop~AWBNo");
			int verfColsMAN[] = { 9 };
			String[] actVerfValuesMAN = { "Sent" };
			MSG005.verifyMessageDetails(verfColsMAN, actVerfValuesMAN, pmKeyMAN, "val~XFSU-MAN", false);
			libr.waitForSync(2);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR344 - Export manifest ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("prop~flight_code", "FlightNo", "StartDate");
			OPR344.finalizeFlight(true);
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export Manifest");

			/** CHECKING XFUM TRIGGERED FOR AWB **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFUM");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.getNumberOfRecordsPresent(ULDNo,2);
			MSG005.verifyMessageDetails(verfColsFUM, actVerfValuesFUM, pmKeyFUM,"val~xFUM",false);
			libr.waitForSync(1); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/** CHECKING XFFM TRIGGERED FOR AWB **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFFM");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFFM = cust.data("prop~flight_code") + " - " + cust.data("FlightNo") + " - " + cust.data("Day")
			+ " - " + cust.data("Month").toUpperCase() + " - " + cust.data("Origin");
			int verfColsXFFM[] = { 9 };
			String[] actVerfValuesXFFM = { "Sent" };
			MSG005.verifyMessageDetails(verfColsXFFM, actVerfValuesXFFM, pmKeyXFFM, "val~XFFM", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			//Login to "CGOMON"
			String[] cgomon = libr.getApplicationParams("cgomon");
			driver.get(cgomon[0]); // Enters URL
			cust.loginToCgomon(cgomon[1], cgomon[2]);     

			/***********          Verifying Inbound Messages      ************/
			Cgomon.clickInboundMessage();
			map.put("awbNumber", cust.data("CarrierNumericCode")+"-"+cust.data("AWBNo"));

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			//XFWB on execute, re-execute, flight finalize and XFWB loaded from EML
			Cgomon.verifynumberOfRecords(4,"awbNumber");
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFWB", "ICARGO");

			Cgomon.cleanDetails();

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-FOH");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFSU-FOH", "ICARGO");

			Cgomon.cleanDetails();

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-RCS");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFSU-RCS", "ICARGO");

			Cgomon.cleanDetails();

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-PRE");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFSU-PRE", "ICARGO");        

			Cgomon.cleanDetails();

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-MAN");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFSU-MAN", "ICARGO");

			Cgomon.cleanDetails();

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterFlightNo("FullFlightNo");
			Cgomon.enterMessageType("XFUM");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifynumberOfRecords(2, "FullFlightNo");
			Cgomon.verifyMessageStatusForFlight("FullFlightNo", "Incoming XFUM", "ICARGO");

			Cgomon.cleanDetails();

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterFlightNo("FullFlightNo");
			Cgomon.enterMessageType("XFFM");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatusForFlight("FullFlightNo", "Incoming XFFM", "ICARGO");

			Cgomon.cleanDetails();
			
			Cgomon.clickOutboundMessage();

			//XFWB to PELICAN
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifynumberOfRecords(4,"awbNumber");
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFWB", "PELICAN");

			Cgomon.cleanDetails();

			//XFSU-FOH to PELICAN
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-FOH");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFSU-FOH", "PELICAN");

			Cgomon.cleanDetails();

			//XFSU-RCS to PELICAN
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-RCS");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFSU-RCS", "PELICAN");

			Cgomon.cleanDetails();

			//XFSU-PRE to PELICAN
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-PRE");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFSU-PRE", "PELICAN");              

			Cgomon.cleanDetails();

			//XFSU-MAN to PELICAN
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-MAN");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFSU-MAN", "PELICAN");

			Cgomon.cleanDetails();

			//XFFM to PELICAN
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterFlightNo("FullFlightNo");
			Cgomon.enterMessageType("XFFM");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatusForFlight("FullFlightNo", "Outgoing XFFM", "PELICAN");

			Cgomon.cleanDetails();

			//XFFM to VCCUSTOM
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterFlightNo("FullFlightNo");
			Cgomon.enterMessageType("XFFM");
			Cgomon.enterChannel("MERCURY","Outgoing");
			Cgomon.selectAdvancedSearchOption("val~Recipient");
			Cgomon.enterRecipientAddress("ReceiptaddressVccustoms");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatusForFlight("FullFlightNo", "Outgoing XFFM", "VCCUSTOM");

			Cgomon.cleanDetails();
			
			//XFWB to VCCUSTOM
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("MERCURY","Outgoing");
			Cgomon.enterRecipientAddress("ReceiptaddressVccustoms");
			Cgomon.clickSearch();
			Cgomon.verifynumberOfRecords(4,"awbNumber");
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFWB", "VCCUSTOM");
			
			Cgomon.cleanDetails();

			//XFSUs to AFLS
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-FOH");
			Cgomon.enterChannel("MERCURY","Outgoing");
			Cgomon.enterRecipientAddress("ReceipienttaddressAfls1");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFSU-FOH", "MERCURY");

			Cgomon.cleanDetails();

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFSU-RCS");
			Cgomon.enterChannel("MERCURY","Outgoing");
			Cgomon.enterRecipientAddress("ReceipienttaddressAfls1");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFSU-RCS", "MERCURY");

			Cgomon.cleanDetails();

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterFlightNo("FullFlightNo");
			Cgomon.enterMessageType("XFUM");
			Cgomon.enterChannel("MERCURY","Outgoing");
			Cgomon.enterRecipientAddress("ReceipienttaddressAfls1");
			Cgomon.clickSearch();
			Cgomon.verifynumberOfRecords(2, "FullFlightNo");
			Cgomon.verifyMessageStatusForFlight("FullFlightNo", "Outgoing XFUM", "ICARGO");

		}

		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
