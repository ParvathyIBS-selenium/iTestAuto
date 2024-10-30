package rapix;

/** TC_05_Verify Screening details must be received from RAPIX for a AWB in multiple SU must be displayed.-HHT **/
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
import rest_pawbs.JSONBody;
import rest_multiple_sfmi.Jsonbody;
import screens.AWBClearance_OPR023;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListCheckSheetConfig_SHR094;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import screens.WarehouseRelocation_WHS009;
import screens.RelocationHHT;

public class PSRAndSaveAWBScreening_TC_3094 extends BaseSetup {

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
	public GoodsAcceptance_OPR335 OPR335;
	public ListCheckSheetConfig_SHR094 SHR094;
	public MaintainFlightSchedule_FLT005 FLT005;
	public WarehouseRelocation_WHS009 WHS009;
	public AWBClearance_OPR023 OPR023;
	public RelocationHHT relocationhht;
	public ExportManifest_OPR344 OPR344;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public JSONBody jsonbody;
	public Jsonbody jsonbody1;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "rapix";

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
		WHS009=new WarehouseRelocation_WHS009(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		relocationhht=new RelocationHHT(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		jsonbody=new JSONBody(driver, excelreadwrite, xls_Read);
		jsonbody1=new Jsonbody(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "3095")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "3095")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			//Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			String timeStamp = cust.createDateFormatWithTimeZone("dd-MMM-yyyy hh:mm:ss", 0, "DAY", "Europe/Paris");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

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

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");

			//Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", WebFunctions.getPropertyValue(proppath, "flightNumber"));
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			/****************** MERCURY *********************/
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
			driver = libr.relaunchBrowser("chrome");
			/*** Login to cgocxml **********/
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
				
			/** XFBL Message loading **/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC").split(",")[0]+";"+cust.data("SCC").split(",")[1] };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/*** MESSAGE - loading XFWB **********/
			cust.createXMLMessage("MessageExcelAndSheetXFWB","MessageParamXFWB");
			String sccs[] = { cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1] };
			//Create XFWB message
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs", sccs);
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

			/**** OPR026 - Capture AWB ****/
			//As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			String pcs[] = { libr.data("Pcs").split(",")[0], libr.data("Pcs").split(",")[1]};
			String wgt[] = { libr.data("Wgt").split(",")[0], libr.data("Wgt").split(",")[1]};
			OPR026.splitShipmentWithSCC(libr.data("SCC"), pcs,wgt);
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			/**** OPR335 -Goods Acceptance ****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.captureCheckSheetCDGPHYCHCK();
			OPR335.clickLooseAccptTab();
			String suNumber1=OPR335.getSUNumber(1).split(cust.data("VPPAwb"))[1]+cust.data("VPPAwb");
			String suNumber2=OPR335.getSUNumber(2).split(cust.data("VPPAwb"))[1]+cust.data("VPPAwb");
			map.put("SU1tail", suNumber1.substring(0, 3));
			map.put("SU2tail", suNumber2.substring(0, 3));
			System.out.println(cust.data("SU1tail"));
			System.out.println(cust.data("SU2tail"));
			String locs[]={cust.data("Location"),cust.data("Location")};
			OPR335.editShipmentLocation("",cust.data("SCC").split(",")[0] ,locs[0]);
			OPR335.editShipmentLocation("",cust.data("SCC").split(",")[1] ,locs[1]);	
			OPR335.allPartsRecieved();
			OPR335.saveAcceptanceWithBlockExists();
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");
			
			map.put("AWB", cust.data("CarrierNumericCode") + cust.data("AWBNo"));

			/**** OPR023 - AWB Clearance ******/            
			cust.searchScreen("OPR023", "AWB Clearance");
			OPR023.listAWB("CarrierNumericCode","AWBNo");
			OPR023.verifyBlock(cust.data("BlockType"), cust.data("AWBNo"));
			OPR023.verifySCCs("val~NSC");
			OPR023.closeTab("OPR023", "AWB Clearance");					
			
			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");

			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);
			
			/*******HHT-Relocation*********/	
			//Relocating the first SU 
			relocationhht.invokeRelocationScreen();
			map.put("SU1", cust.data("AWB")+cust.data("SU1tail"));
			relocationhht.enterValueWithoutNext("SU1");
			relocationhht.clickStartRelocation();
			relocationhht.enterDestLocation("RapixEntryPoint");
			relocationhht.clickCompleteRelocation();
			cust.clickBack("Relocation");
			cust.clickBack("Relocation");
			

			/******* SFMI POST REQUEST SU1 ****/		
			jsonbody1.postRequest(cust.data("AWB"),cust.data("Wgt").split(",")[0],cust.data("Vol").split(",")[0],cust.data("Length"),cust.data("Width"),cust.data("Height"),suNumber1);
			
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);	

			/******* PAWBS POST REQUEST for SU1 ****/
			jsonbody.postRequest(cust.data("CarrierNumericCode"), cust.data("AWBNo"), timeStamp,cust.data("ScreeningResult").split(",")[0],screenmethod,cust.data("RapixEntryPoint"),cust.data("ScreenerName"),cust.data("SU1"));	
						
			/*******HHT-Relocation*********/		
			//Relocating the second SU
			relocationhht.invokeRelocationScreen();
			map.put("SU2", cust.data("AWB")+cust.data("SU2tail"));
			relocationhht.enterValueWithoutNext("SU2");
			relocationhht.clickStartRelocation();
			relocationhht.enterDestLocation("RapixEntryPoint");
			relocationhht.clickCompleteRelocation();
				
			/******* SFMI POST REQUEST SU2 ****/		
			jsonbody1.postRequest(cust.data("AWB"),cust.data("Wgt").split(",")[1],cust.data("Vol").split(",")[1],cust.data("Length"),cust.data("Width"),cust.data("Height"),suNumber2);
			
			/******* PAWBS POST REQUEST for SU2 ****/	
			jsonbody.postRequest(cust.data("CarrierNumericCode"), cust.data("AWBNo"), timeStamp,cust.data("ScreeningResult").split(",")[0],screenmethod,cust.data("RapixEntryPoint"),cust.data("ScreenerName"),cust.data("SU2"));
			
			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			
			String SUs[]={cust.data("SU1"),cust.data("SU2")};
			String ScreeningMethods[]={cust.data("screenmethod"),cust.data("screenmethod")};
			String ScreenedPcs[]={cust.data("Pcs").split(",")[0], cust.data("Pcs").split(",")[1]};
			String ScreenedWgt[]={cust.data("Wgt").split(",")[0], cust.data("Wgt").split(",")[1]};
			String ScreeningResult[]={cust.data("ScreeningResult").split(",")[1],cust.data("ScreeningResult").split(",")[1]};
			
			OPR339.verifyScreeningDetailsOfMultipleSUs(SUs,ScreeningMethods,ScreeningResult,ScreenedPcs,ScreenedWgt);		
			OPR339.verifyScreenerDetails("ScreenerName",timeStamp.split(" ")[0]);
			String Sccnotpresent[]={"NSC"};
			OPR339.verifySccNotPresent(Sccnotpresent);
			String Sccpresent[]={"SPX"};
			OPR339.verifyScc(Sccpresent);
			cust.closeTab("OPR339", "Security & Sceening");       

			/*****OPR023 - AWB Clearance *******/            
			//Verify that block is released
			cust.searchScreen("OPR023", "AWB Clearance");
			OPR023.listAWB("CarrierNumericCode","AWBNo");
			OPR023.verifyBlockReleasedForShipment(cust.data("BlockType"),cust.data("FullAWBNo"),cust.data("Origin"));
			OPR023.verifySCCs("val~SPX");
			OPR023.closeTab("OPR023", "AWB Clearance");	

			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAcceptanceFinalizedinSameFrame("finalised");
			OPR335.verificationOfRFCStatus();
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			cust.closeTab("OPR335", "Goods Acceptance");
			
			/**** OPR344 - Export manifest****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");	
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addULDWithoutAWB("UldNum", "0");
			OPR344.clickShipemntFromPlannedSection("AWBNo");
			OPR344.selectULD("UldNum");
			OPR344.clickBuildUpComplete();
			OPR344.verifyShipmentFromAssignedListUsingAWB("AWBNo");
			OPR344.verifyBuildUpComplete("UldNum");
			cust.closeTab("OPR344", "Export Manifest");

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

