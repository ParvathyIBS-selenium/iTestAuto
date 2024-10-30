package importmanifest;

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
import screens.Cgocxml;
import screens.ImportShipmentListing_OPR043;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.Mercury;

/**
 * Verify the provision to filter shipments based on SCC
 **/
public class IASCB_19215_TC_2725_AF extends BaseSetup {
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
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public ImportShipmentListing_OPR043 OPR043;
	public ListMessages_MSG005 MSG005;

	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\ImportManifest.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "ImportManifest_FT";

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
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR043 = new ImportShipmentListing_OPR043(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
	

	}

	@DataProvider(name = "TC_01")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_01")
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

			// Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_NL"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_NL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_NL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_NL"));

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

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			// creating flight number
			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("SSMStartDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("SSMEndDate", cust.createDateFormat("ddMMM",0, "DAY", ""));
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);

			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);


			//Maintain Flight Screen (FLT005) . Taking fresh flight

			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, endDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			//Flight details
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"), cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			System.out.println(FlightNum);


			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");


			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			cust.setPropertyValue("AWBNo",cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			map.put("FullAWBNo1", cust.data("prop~FullAWBNo"));
			map.put("AWBNo1", cust.data("prop~AWBNo"));


			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo2", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			cust.setPropertyValue("AWBNo2",cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo2", cust.data("prop~FullAWBNo2"));
			map.put("AWBNo2", cust.data("prop~AWBNo2"));


			// Checking AWB is fresh or Not (AWBNumber3)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo3", cust.data("prop~CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			cust.setPropertyValue("AWBNo3",cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo3", cust.data("prop~FullAWBNo3"));
			map.put("AWBNo3", cust.data("prop~AWBNo3"));

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

			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/*** MESSAGE - loading XFWB for AWB 1 **********/

			map.put("FullAWBNumber", cust.data("prop~FullAWBNo"));
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[0]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB","MessageParamXFWB");
			String sccs[] = { cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1] };

			// Create XFWB message
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs", sccs);
			//Load XFWB 
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/*** MESSAGE - loading XFWB for AWB 2 **********/

			map.put("FullAWBNumber", cust.data("prop~FullAWBNo2"));
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[1]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB","MessageParamXFWB");

			String sccs2[] = { cust.data("SCC").split(",")[1],cust.data("SCC").split(",")[2] };
			// Create XFWB message
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs", sccs2);
			//Load XFWB 
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/*** MESSAGE - loading XFWB for AWB 3 **********/

			map.put("FullAWBNumber", cust.data("prop~FullAWBNo3"));
			map.put("ShipmentDescription", cust.data("ShipmentDesc").split(",")[2]);
			cust.createXMLMessage("MessageExcelAndSheetXFWB","MessageParamXFWB");

			String sccs3[] = { cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[2] };
			//Create XFWB message
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs", sccs3);
			//Load XFWB 
			Cgocxml.sendMessageCgoCXML("ICARGO");



			/****** XFFM LOADING****/

			/*** MESSAGE - loading and creating XFFM ****/

			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			//ULD Number
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));	
			System.out.println(cust.data("ULDNo"));
			System.out.println(cust.data("ULDNum"));


			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { cust.data("FullAWBNo1") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[0],cust.data("FullAWBNo2") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[1],cust.data("FullAWBNo3") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
			+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc").split(",")[2]};
			String scc[] = {cust.data("SCC").split(",")[0] + ";" +cust.data("SCC").split(",")[1],cust.data("SCC").split(",")[1] + ";" +cust.data("SCC").split(",")[2],cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[2]};
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport"), cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport"), cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport")};
			String uld[] = { cust.data("UldType")+";"+ cust.data("ULDNo")+";"+cust.data("carrierCode")};
			int []shipments={3};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing, uld,shipments);
			Cgocxml.sendMessageCgoCXML("ICARGO");

			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");

			// Re-Login to iCargo STG
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch role
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/*** MSG005 - XTMV Message loading For flight******/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
			cust.createXMLMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XTMV", true);
			cust.closeTab("MSG005", "List Message");
			
			/********** OPR043-Import Shipment Listing ****************/

			//Verifying clearing agent field
			cust.searchScreen("OPR043", "Import Shipment Listing");
			OPR043.selectDateType("val~Planned Date");
			OPR043.enterFlightNumber("carrierCode", "FlightNo");
			OPR043.enterFlightDate("StartDate");

			//Verify SCC field is present
			OPR043.verifySCCField();

			//Verify different sccs are present on SCC Lov
			OPR043.verifySCCsFromLov();
			String[] arrSCC=new String[3];
			arrSCC[0]=cust.data("SCC").split(",")[0];
			arrSCC[1]=cust.data("SCC").split(",")[1];
			arrSCC[2]=cust.data("SCC").split(",")[2];

			String[] shipmentDesc=new String[3];
			shipmentDesc[0]=cust.data("ShipmentDesc").split(",")[0];
			shipmentDesc[1]=cust.data("ShipmentDesc").split(",")[1];
			shipmentDesc[2]=cust.data("ShipmentDesc").split(",")[2];


			//select single SCC from the LOV and verify the shipments with the selected SCC is listed
			OPR043.selectSingleSCCFromLOV(arrSCC[1]);
			int verfCols1 [] = {2};
			String[] actVerfValues1 = {cust.data("FullAWBNo") };
			OPR043.verifyTableRecords(verfCols1, actVerfValues1,shipmentDesc[0] );
			int verfCols2 [] = {2};
			String[] actVerfValues2 = { cust.data("FullAWBNo2") };
			OPR043.verifyTableRecords(verfCols2, actVerfValues2,shipmentDesc[1] );		
			cust.closeTab("OPR043", "Import Shipment Listing");

		} 

		catch (Exception e) {
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
