package mvp_reg_acceptance;

/**  High Show Shipment Scenario  **/

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
import screens.AWBClearance_OPR023;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;

public class HighShow_TC_9483 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public Cgocxml Cgocxml;
	public GoodsAcceptance_OPR335 OPR335;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	public ExportManifest_OPR344 OPR344;
	public Mercury mercuryScreen;
	public AWBClearance_OPR023 OPR023;
	public Cgomon Cgomon;

	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	public static String telexproppathRC4 = "\\src\\resources\\TelexAddressRC4.properties";
	String sheetName="mvp_reg_acceptance";	

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR344=new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		Cgocxml=new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "HighShow_TC_9483")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "HighShow_TC_9483")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map=map;		
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

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/***Storing Values to Map***/			
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_LB"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_LB"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_LB"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_LB"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_LB"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_LB"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_LB"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_LB"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_LB"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_LB"));

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

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "BEY"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			map.put("ReceiptaddressAfls", WebFunctions.getPropertyValue(telexproppathRC4, "ReceiptaddressAfls"));
			map.put("ReceiptaddressVccustoms", WebFunctions.getPropertyValue(telexproppathRC4, "ReceiptaddressVccustoms"));
			
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_LB"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_LB"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_LB"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_LB"));

			//Regulated agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code_RED"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId_RED"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_RED"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry_RED"));

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();

			/****************** MERCURY *********************/
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");	
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

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");			
			//Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFBL Message loading **/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheet", "MessageParam");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = {cust.data("SCC")};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/***MESSAGE - loading XFWB **********/
			//Create XFWB message			
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
			
			/*********   OPR023 - Remove Compliance Block   ******/
			cust.searchScreen("OPR023","AWB CLearance");
			OPR023.listAWB("CarrierNumericCode", "AWBNo");
			OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Compliance Block removed");  
		    OPR023.closeTab("OPR023", "AWB Clearance"); 

			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");		
			OPR335.looseShipmentDetails("Location", "Pieces","Weight1");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();			
			OPR335.clickSaveOnly();
			OPR335.verifyMultipleErrorMessages("OPR335", "Accepted weight should not be greater than stated weight","Stated pieces equal to the total accepted pieces. But the stated weight is different from the total accepted weight");
			cust.closeTab("OPR335", "Goods Acceptance");

			//Save AWB with updated Weight
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.updateStatedValues("Pieces","Weight1","Volume");
			OPR026.clickChargesAcc();
			OPR026.clearIATAChargeAndUpdateChargeableWgt("Weight1");
			OPR026.saveAWB();      
			cust.closeTab("OPR026", "Capture AWB");
	
			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces","Weight1");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();	
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.clicksecurityAndScreening();
			cust.switchToFrame("frameName", "if11");
			String ScreeningMethod[]={cust.data("ScreeningMethod").split(",")[0],cust.data("ScreeningMethod").split(",")[1]};
            String Pieces[]={cust.data("Pieces"),cust.data("Pieces")};
            String Weight[]={cust.data("Weight1"),cust.data("Weight1")};
            String results[]={"Pass","Pass"};
            OPR339.captureDoubleScreeningDetails(ScreeningMethod,Pieces,Weight,results);
			OPR339.checkSecurityDataReviewed();
			OPR339.OkButtonAfterScreeningSave();
			cust.switchToFrame("contentFrame", "OPR335");	
			OPR335.saveAcceptance();
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			/*******Verify FSU-FOH message in MSG005******/		
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Freight On Hand");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFSU=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsFSU[]={9};
			String[] actVerfValuesFSU={"Sent"};
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU-FOH",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
			/**** OPR026 - Capture AWB****/		
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
		    //As Is Execute AWB
			OPR026.asIsExecute();	
			cust.closeTab("OPR026", "Capture AWB");
			
			/*** VERIFY THE XFWB MESSAGE CONTENTS***/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FWB", "FlightNo", "AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.clickMessageCheckBox("2");
			MSG005.clickView();
			List <String> msgContents1=new ArrayList<String>();
			String wtUnit="\"KGM\"";
			String curr=cust.data("Currency");
			String volUnit="\"MTQ\"";
		
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
			
			/*******Verify FSU-RCS message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU-RCS",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");  

			/**** OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNum = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			OPR344.addULDWithoutAWB("UldNum", "0");
			OPR344.clickShipemntFromPlannedSection("AWBNo");
			OPR344.selectULD("UldNum");
			cust.closeTab("OPR344", "Export manifest");

			/*******Verify FSU-PRE message in MSG005******/			
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Prepared for loading");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU-PRE",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			OPR344.clickBuildUpComplete();
			OPR344.verifyBuildUpComplete("UldNum");
			cust.closeTab("OPR344", "Export manifest");

			/** CHECKING XFUM TRIGGERED FOR AWB **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFUM");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFUM=cust.data("Origin")+" - "+cust.data("UldNum").substring(3,8);
			int verfColsFUM[]={9};
			String[] actVerfValuesFUM={"Sent"};
			MSG005.verifyMessageDetails(verfColsFUM, actVerfValuesFUM, pmKeyFUM,"val~xFUM",true);
			libr.waitForSync(1); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR344 - Export manifest****/
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

			/******* Verify xFSU-MAN message in MSG005 ******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Manifest Details");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU, "val~XFSU-MAN", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**** OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");			
			OPR344.finalizeFlight();
			OPR344.verifyFlightStatus("val~Finalized");
			cust.closeTab("OPR344", "Export manifest");	

			/** CHECKING XFUM TRIGGERED FOR AWB **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFUM");
			MSG005.selectStatus("Sent");
			MSG005.clickList();			
			MSG005.verifyMessageDetails(verfColsFUM, actVerfValuesFUM, pmKeyFUM,"val~xFUM",false);
			MSG005.getNumberOfRecordsPresent(cust.data("UldNum").substring(3,8),2);
			libr.waitForSync(1); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

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
			//XFWB on execute and XFWB loaded from EML
			Cgomon.verifynumberOfRecords(2,"awbNumber");
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFWB", "ICARGO");

			Cgomon.cleanDetails();

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterFlightNo("FullFlightNo");
			Cgomon.enterMessageType("XFUM");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifynumberOfRecords(2, "FullFlightNo");
			Cgomon.verifyMessageStatusForFlight("FullFlightNo", "Incoming XFUM", "ICARGO");

			Cgomon.cleanDetails();
			
			Cgomon.clickOutboundMessage();
			
			//XFWB to PELICAN
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifynumberOfRecords(2,"awbNumber");
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFWB", "PELICAN");

			Cgomon.cleanDetails();
			
			//XFWB to VCCUSTOM
			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("MERCURY","Outgoing");
			Cgomon.selectAdvancedSearchOption("val~Recipient");
			Cgomon.enterRecipientAddress("ReceiptaddressVccustoms");
			Cgomon.clickSearch();
			Cgomon.verifynumberOfRecords(2,"awbNumber");
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFWB", "VCCUSTOM");

			Cgomon.cleanDetails();

			Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterFlightNo("FullFlightNo");
			Cgomon.enterMessageType("XFUM");
			Cgomon.enterChannel("AFLS_MSCO_CXML","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifynumberOfRecords(2, "FullFlightNo");
			Cgomon.verifyMessageStatusForFlight("FullFlightNo", "Outgoing XFUM", "AFLS");
			

		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}


