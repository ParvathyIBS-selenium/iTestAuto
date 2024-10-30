package mvp_cr_iascb_31348;


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
import screens.CaptureIrregularity_OPR342;
import screens.Cgocxml;
import screens.DeadloadStatement_OPR063;
import screens.DeliveryDocumentation_OPR293;
import screens.GeneratePaymentAdvice_CSH007;
import screens.ImportManifest_OPR367;
import screens.ListIrregularity_OPR341;
import screens.ListMessages_MSG005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;


/**
 * Verify Split Shipment of AWB will be Blocked for Security and Screening while Creating the Found Cargo Discrepancy
**/
public class IASCB_31348_TC10 extends BaseSetup {
	
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
	public ListMessages_MSG005 MSG005; 
	public DeadloadStatement_OPR063 OPR063;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public DeliveryDocumentation_OPR293 OPR293;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public ListIrregularity_OPR341 OPR341;
	public AWBClearance_OPR023 OPR023;
	public SecurityAndScreening_OPR339 OPR339;
	public CaptureIrregularity_OPR342 OPR342;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName="mvp_cr_iascb_31348";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		//excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR063=new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367= new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		OPR341=new ListIrregularity_OPR341(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR342 = new CaptureIrregularity_OPR342(driver, excelreadwrite, xls_Read);
		
		
	}
	
	
	
	@DataProvider(name = "testdata")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "testdata")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			WebFunctions.map=map;		
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

			excelRead.writeDataInExcel(map, path1, sheetName, testName);
		
			/**STORING VALUES TO MAP FOR XFWB LOAD **/
			
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			
			
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR2"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR2"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR2"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR2"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR2"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR2"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR2"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR2"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR2"));
			
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_NL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_NL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_NL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_NL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_NL"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_NL"));
			
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_FR2"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_FR2"));
			
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			
			
			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			 libr.quitBrowser();

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),
					proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** Flight Creation in Mercury **/

			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo2", cust.data("prop~flightNo"), proppath);

			cust.setPropertyValue("flightNumber2", cust.data("carrierCode") + cust.data("prop~flightNo2"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			map.put("FlightNumber", cust.data("FullFlightNo"));
			map.put("ASMdep", cust.data("ASMdeparture1"));
			map.put("ASMarr", cust.data("ASMarrival1"));
			map.put("Org", cust.data("Origin"));
			map.put("Des", cust.data("Transit"));

			// Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** ASM Loading For First Flight **/

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");

			/** ASM Loading For Second Flight Flight **/

			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);

			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			System.out.println(FlightNum2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo1", FlightNum2);
			map.put("FlightNo1", FlightNum2.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			map.put("ASMdep", cust.data("ASMdeparture2"));
			map.put("ASMarr", cust.data("ASMarrival2"));
			map.put("Org", cust.data("Transit"));
			map.put("Des", cust.data("Destination"));
			map.put("FlightNumber", cust.data("FullFlightNo1"));

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.returnTosendMessage();
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");


			/****** MSG005-loading FBL for Second Flight ***/

			map.put("FullFlightNumber", cust.data("FullFlightNo1"));

			// Create the message FBL

			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());

			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipments[] = { libr.data("prop~FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
					+ ";" + cust.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String sccs[] = { cust.data("SCC") };
			String routings[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipments, sccs, routings);

			/*** CGOCXML **/
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/***** XFWB Loading for AWB ***/

			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/****** XFFM LOADING ****/

			/*** MESSAGE - loading and creating XFFM ****/

			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			// ULD Number
			String uldNo = cust.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			System.out.println(cust.data("ULDNo"));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { cust.data("prop~FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
					+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("prop~flight_code") };
			// Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing, uld);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			/** Loading MVT : DEPARTURE **/
			driver.get(mercury[0]); // Enters URL

			cust.loginToMercury(mercury[1], mercury[2]);

			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");

			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");

			/** Loading MVT : ARRIVAL **/

			mercuryScreen.returnTosendMessage();
			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/** Import Manifest **/

			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "prop~flightNo2", "StartDate");
			OPR367.verifyShipment("prop~AWBNo");
			cust.closeTab("OPR367", "Import Manifest");
		
            
			/****** OPR342 - Capture Irregularity ******/
			cust.searchScreen("OPR342", "Capture Irregularity");
			OPR342.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR342.selectOperation("Discrepancy");
			OPR342.clickIrregularitySelect("Found Cargo at Warehouse");
			OPR342.enterRemarks("Test");
			OPR342.clickSave();
			OPR342.closeTab("OPR342", "Capture Irregularity");
			
			/***OPR341 - LIST IRREGULARITY**/
            
			//Check the Found Cargo Discrepancy
            
            cust.searchScreen("OPR341", "List Irregularity");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "List Irregularity");
			int[] verfCols={3};
			String[] actVerfValues={"Found Cargo Discrepancy"};
			OPR341.verifyIrregularityDetailsValue(verfCols, actVerfValues, "FDCA");
			cust.closeTab("OPR341", "List Irregularity");

			
			 /*****OPR023 - AWB Clearance *******/  
			
            //Verify the block details are present
			
            cust.searchScreen("OPR023", "AWB Clearance");
            OPR023.listAWB("prop~CarrierNumericCode","prop~AWBNo");
            OPR023.verifySCCs("val~NSC");
            int[] verfColsBlock={8};
			String[] actVerfValuesBlock={"Blocked"};
			OPR023.verifyBlockDetails(verfColsBlock, actVerfValuesBlock,"Irregularity");
            OPR023.closeTab("OPR023", "AWB Clearance"); 
            
			
			/*******Verify FSU-DIS message in MSG005******/
			
			cust.searchScreen("MSG005", "MSG005 - List Messages");
	        MSG005.enterMsgType("XFSU");
	        MSG005.selectMsgSubType("Discrepancy");
	        MSG005.selectStatus("Sent");
	        MSG005.clickList();
	        String pmKeyRCS=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
	        int verfColsRCS[]={9};
	        String[] actVerfValuesRCS={"Sent"};
	        MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-DIS",false);
	        libr.waitForSync(1);
	        map.put("pmkey", cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo"));
            MSG005.clickCheckBox("pmkey");
            MSG005.clickView();
            libr.waitForSync(6);
            MSG005.verifyMessageContent("val~FDCA");
            MSG005.closeView();
            MSG005.closeTab("MSG005", "MSG005 - List Messages");
            
            
            
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}


