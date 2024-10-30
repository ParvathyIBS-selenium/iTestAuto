package mvp_cr_iascb_51706;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BreakdownHHT;
import screens.CaptureAWB_OPR026;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.RelocationTaskMonitor_WHS052;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

/** Test Case Name : Verify user can able to Add or Modify the Split Shipment details in Acceptance screen with the new business privilege **/

public class IASCB_51706_Breakdown_Scanner_TC05 extends BaseSetup {
	
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
	public ListMessages_MSG005 MSG005;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public SecurityAndScreening_OPR339 OPR339;
	public GoodsAcceptance_OPR335 OPR335;
	public RelocationTaskMonitor_WHS052 WHS052;
	public ImportManifest_OPR367 OPR367;
	public GoodsAcceptanceHHT gahht;
	public BreakdownHHT bdhht;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_cr_iascb_51706";

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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		bdhht = new BreakdownHHT(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "IASCB_51706_CaptureAwb_TC04")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "IASCB_51706_CaptureAwb_TC04")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			
			// Login to iCargo STG

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1],iCargo[2]);
			Thread.sleep(2000);


	

			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_US"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_US"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("AgentStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US"));
			map.put("AgentCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("AgentCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR2"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR2"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR2"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR2"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR2"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR2"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR2"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR2"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR2"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
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
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			
			/*** CAPTURE AWB -0PR026 ***/

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("	prop~AWBNo"),proppath);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			

			// Switch Role
			cust.switchRole("val~AMS", "FCTL", "RoleGroup");
			
			
			/** ASM LOADING **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "ASM_NEW");

			/*** MESSAGE - loading XFWB **********/

			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");

			String sccs[] = {cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1],cust.data("SCC").split(",")[2], cust.data("SCC").split(",")[3]};
			cust.createXFWBMessageWithSCCs("XFWB_MultipleSCCs_WithVol_NoDim", sccs);
			
			//Load XFWB
			
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_MultipleSCCs_WithVol_NoDim", true);
			cust.closeTab("MSG005", "List Message");
			
			// Switch Role
			cust.switchRole("val~IAD", "FCTL", "RoleGroup");

			/******** OPR026 - Capture AWB ********/
			// Split SCC
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			String pcs[] = { libr.data("SplitPcs").split(",")[0], libr.data("SplitPcs").split(",")[1],libr.data("SplitPcs").split(",")[2] };
			OPR026.splitShipmentWithSCC(libr.data("SCC2"), pcs);
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");
			
			// Switch Role
			cust.switchRole("val~AMS", "FCTL", "RoleGroup");

			/*** MESSAGE - loading XFFM ****/

			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { cust.data("prop~FullAWBNo") + ";" + cust.data("Pieces") + ";" + cust.data("Weight")
					+ ";" + cust.data("Volume") + ";" + cust.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1],
					cust.data("SCC").split(",")[2], cust.data("SCC").split(",")[3] };
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
					+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode")};
			cust.createXFFMMessage("XFFM", shipment, scc, routing, uld);

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);

			/*** MSG005-- MVT AD loading ****/

			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "MVT_ATD");

			/*** MSG005-- MVT AA loading ****/

			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			MSG005.loadFromFile("All", "ALL", "JMS", "", "Origin", "", "MVT_ATA");
			cust.closeTab("MSG005", "List Message");
			

			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");

			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			 cust.loginHHT(hht[0], hht[1]);

			/*** HHT - BREAKDOWN ****/

			bdhht.invokeBreakdownHHTScreen();
			map.put("uldnum", cust.data("UldNum"));
			bdhht.enterValue("uldnum");

			// Adding AWB
			map.put("awbNumber", cust.data("CarrierNumericCode") + cust.data("prop~AWBNo"));
			System.out.println(cust.data("awbNumber"));	
			bdhht.addAWB("awbNumber");

			// Capture Checksheet
			bdhht.clickSaveCaptureChecksheet();

			String pcsinfo[]={libr.data("SplitPcs2").split(",")[0],libr.data("SplitPcs2").split(",")[1],libr.data("SplitPcs2").split(",")[2]};

			// Select Split SCC¿

			
			bdhht.updateShipment(libr.data("SCC").split(",")[0]+" +");
			bdhht.updateSplitPcsandWgt(pcsinfo[0]);
			
			bdhht.updateShipment(libr.data("SCC").split(",")[2]);
			bdhht.updateSplitPcsandWgt(pcsinfo[1]);
			
			bdhht.updateShipment(libr.data("SCC").split(",")[3]);
			bdhht.updateSplitPcsandWgt(pcsinfo[2]);
			
			bdhht.save();
			
			bdhht.selectSplitSCCValue(libr.data("SCC").split(",")[0]);
			bdhht.selectSplitSCCValue(libr.data("SCC").split(",")[2]);
			bdhht.selectSplitSCCValue(libr.data("SCC").split(",")[3]);

			bdhht.clickMoreOptions();
			bdhht.clickBreakdownComplete();

			libr.quitApp();
			

			/******* Verify FSU-RCF message in MSG005 ******/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Breakdown");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCF = cust.data("prop~CarrierNumericCode") + " - " + cust.data("prop~AWBNo");
			int verfColsRCF[] = { 9 };
			String[] actVerfValuesRCF = { "Sent" };
			MSG005.verifyMessageDetails(verfColsRCF, actVerfValuesRCF, pmKeyRCF, "val~XFSU-RCF", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
	        		
	      //QUIt browser
			libr.quitBrowser();
		
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}

	}
}

