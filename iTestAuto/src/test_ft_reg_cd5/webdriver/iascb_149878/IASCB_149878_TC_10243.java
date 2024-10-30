package iascb_149878;

/** TC_30_Verify No Updation of short captured AWB details based on XFBL or FBL or XFSU-BKD or FSU-BKD after XFWB process or AWB save when config is set   **/

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
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.GoodsAcceptance_OPR335;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;

public class IASCB_149878_TC_10243 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public GoodsAcceptance_OPR335 OPR335;
	public CaptureAWB_OPR026 OPR026;
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainFlightSchedule_FLT005 FLT005;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;

	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="IASCB_149878";	

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
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml=new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon=new Cgomon(driver, excelreadwrite, xls_Read);
	}



	@DataProvider(name = "TC_10243")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_10243")
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

			/**** UPDATING GENERAL DETAILS IN MAP****/
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

			/****** UPDATING DETAILS IN MAP ***/
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			//Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			//Creating flight number
			cust.createFlight("FullFlightNumber");
			//Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			/****************** MERCURY *********************/
			//Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** SSM Message loading for the Flight with AMS-CDG route **/
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

			/** XFBL Message loading - with  Origin, Pieces, Weight, Volume **/

			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = {cust.data("SCC")};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination")};
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			//Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");



			/*** Capture AWB***/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			// Enter shipment details
			OPR026.verifyOrigin("Origin");
			OPR026.verifyDestination("Destination");
			OPR026.enterRouting("Destination", "prop~flight_code");
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			map.put("CommodityCode1", cust.data("CommodityCode").split(",")[0]);
			map.put("Shipmentdesc1", cust.data("ShipmentDesc").split(",")[0]);
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode1", "Shipmentdesc1");
			OPR026.clickChargesAcc();
			// Provide rating details
			OPR026.provideRatingDetails("rateClass", "IATARate", "IATAcharge", "netCharge");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");
			libr.quitBrowser();


			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			// Login to "CGOCXML"

			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/** XFBL Message loading - with Updated Origin, Destination, Pieces, Weight, Volume,SCC **/
			map.put("Org",cust.data("NewOrigin1"));
			map.put("Dest",cust.data("NewDestination1"));
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment1[] = { libr.data("FullAWBNo") + ";" + libr.data("Pcs").split(",")[0] + ";" + libr.data("Weight1").split(",")[0] + ";"+ libr.data("Vol").split(",")[0] + ";" + libr.data("ShipmentDesc").split(",")[1] };
			String scc1[] = {cust.data("SCC1")};
			String routing1[] = { cust.data("Org") + ";" + cust.data("Dest")};
			cust.createXFBLMessage("XFBL_2", shipment1, scc1, routing1);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			//Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/***** Capture AWB OPR026- Verifying Short Captured Details are not overwritten with XFBL data after AWB save on OPR026 *****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.verifyStatedPieces("Pieces");
			OPR026.verifyStatedWeight("Weight");
			OPR026.verifyStatedVolume("Volume");
			OPR026.verifySCCCodes("VerifySCCExists", cust.data("SCC"));
			OPR026.verifySCCCodes("VerifySCCNotExists", cust.data("SCC1"));
			OPR026.verifyOrigin("Origin");
			OPR026.verifyDestination("Destination");
			cust.closeTab("OPR026", "Capture AWB");


			/**** OPR335 -Goods Acceptance - Verifying Short Captured Details are not overwritten with XFBL data ****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.verifySCCDetailsNotUpdated(cust.data("SCC1"));
			OPR335.verifyAWBOriginDestination("Origin","Destination");
			cust.closeTab("OPR335", "Goods Acceptance");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/*** Login to cgocxml **********/

			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/**** XFSU-BKD Message loading ****/

			map.put("Org1",cust.data("NewOrigin2"));
			map.put("Dest1",cust.data("NewDestination2"));
			map.put("Pcs", cust.data("Vol").split(",")[1]);
			map.put("Wgt", cust.data("Weight1").split(",")[1]);
			map.put("Vol", cust.data("Vol").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetXFSU", "MessageParamXFSU");
			cust.modifyMessageMap("        <ram:OriginLocation>"+"\n"+"            "+"<ram:ID>"+cust.data("Origin")+"</ram:ID>","<ram:OriginLocation>"+"\n"+"            "+"<ram:ID>"+cust.data("Org1")+"</ram:ID>");
			cust.modifyMessageMap("        <ram:FinalDestinationLocation>"+"\n"+"            "+"<ram:ID>"+cust.data("Destination")+"</ram:ID>","<ram:FinalDestinationLocation>"+"\n"+"            "+"<ram:ID>"+cust.data("Dest1")+"</ram:ID>");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();


			//Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);


			//Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/***** Capture AWB OPR026- Verifying Short Captured Details are not overwritten with XFSU BKD data after AWB save on OPE026  *****/
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.verifyStatedPieces("Pieces");
			OPR026.verifyStatedWeight("Weight");
			OPR026.verifyStatedVolume("Volume");
			OPR026.verifySCCCodes("VerifySCCExists", cust.data("SCC"));
			OPR026.verifySCCCodes("VerifySCCNotExists", cust.data("SCC1"));
			OPR026.verifyOrigin("Origin");
			OPR026.verifyDestination("Destination");
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR335 -Goods Acceptance - Verifying Short Captured Details are not overwritten with XFSU BKD data ****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.verifySCCDetailsNotUpdated(cust.data("SCC1"));
			OPR335.verifyAWBOriginDestination("Origin","Destination");
			cust.closeTab("OPR335", "Goods Acceptance");
			libr.quitBrowser();


		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

