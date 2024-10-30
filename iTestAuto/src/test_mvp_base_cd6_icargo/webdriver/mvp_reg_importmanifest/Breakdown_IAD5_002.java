package mvp_reg_importmanifest;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.DeliveryDocumentation_OPR293;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MarkFlightMovements_FLT006;



import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

/**
 * 
 *Breakdown of a Thru unit with damages
 *
 */

public class Breakdown_IAD5_002 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public CaptureAWB_OPR026 OPR026;
	public DeliveryDocumentation_OPR293 OPR293;
	public GoodsAcceptance_OPR335 OPR335;
	public BreakDownScreen_OPR004 OPR004;
	
	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_reg_importmanifest";

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
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		
	}

	@DataProvider(name = "ImportManifestIAD1_001")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "ImportManifestIAD1_001")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			/** Pre Condition Starts **/
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
			map.put("FBLDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			// Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

		
		

			/*** Storing Values to Map ***/
			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/
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
		
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_NL"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

            /** Flight Creation - 1st flight**/	
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			map.put("FlightNumber", cust.data("FullFlightNo"));
			map.put("ASMdep", cust.data("ASMdeparture1"));
			map.put("ASMarr", cust.data("ASMarrival1"));
			map.put("Org", cust.data("Origin"));
			map.put("Des", cust.data("Transit"));

			

			/*** MSG005 - SSM Message loading******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");
			
			
			
             /** Flight Creation - 2nd flight**/	
			cust.createFlight("FullFlightNumber2");
			cust.setPropertyValue("flightNo2", cust.data("prop~flightNo2"), proppath);
			cust.setPropertyValue("flightNumber2", cust.data("prop~flight_code") + cust.data("prop~flightNo2"), proppath);
			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo2", FlightNum2.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
	
			map.put("FlightNumber", cust.data("FullFlightNo2"));
			map.put("ASMdep", cust.data("ASMdeparture2"));
			map.put("ASMarr", cust.data("ASMarrival2"));
			map.put("Org", cust.data("Transit"));
			map.put("Des", cust.data("Destination"));
			
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");
			cust.closeTab("MSG005", "List Message");
			
			
			
			// Checking AWB is fresh or Not (AWBNumber1)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//AWBNumber1
			map.put("awbNumber1", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb1", cust.data("prop~AWBNo"));

			// Checking AWB is fresh or Not (AWBNumber2)
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			
			//AWBNumber2
			map.put("awbNumber2", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			map.put("awb2", cust.data("prop~AWBNo"));		
			    
	        /** Creating  XFBL message for flight 2**/     
	        map.put("Origin1", cust.data("Transit"));
			map.put("Transit1", cust.data("Destination"));
			
			/** Creating 1st XFBL message**/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
	        cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");     
	        String shipment[]={libr.data("awbNumber1")+";"+libr.data("Pieces")+";"+libr.data("Weight")+";"+libr.data("Volume")+";"+libr.data("ShipmentDesc")
					,libr.data("awbNumber2")+";"+libr.data("Pieces")+";"+libr.data("Weight")+";"+libr.data("Volume")+";"+libr.data("ShipmentDesc")};
			String scc[]={cust.data("SCC"),cust.data("SCC")};
			String routing1[]={cust.data("Origin")+";"+cust.data("Destination"),cust.data("Origin")+";"+cust.data("Destination")};
	        cust.createXFBLMessage("XFBL_2", shipment, scc, routing1);
	       MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);
			
			/************ LOADING XFWB MESSAGE ***/
	     	//AWB 1
			map.put("awbNumber",cust.data("awbNumber1"));
	     	cust.createXMLMessage("MessageExcelAndSheetFWB2","MessageParamFWB2");
	     	MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_Transit_MSG", true);
			
			//AWB 2
		    map.put("awbNumber",cust.data("awbNumber2"));
			cust.createXMLMessage("MessageExcelAndSheetFWB2","MessageParamFWB2");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_Transit_MSG", true);

			/****** XFFM LOADING****/
			/*** MESSAGE - loading and creating XFFM ****/
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			//ULD Number
			String uldNo=OPR335.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));	
			System.out.println(cust.data("ULDNo"));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport"),cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType")+";"+ cust.data("ULDNo")+";"+cust.data("carrierCode")};
			int []shipments={2};
			// Create XFFM message
			cust.createXFFMMessage_MultipleShipments("XFFM", shipment, scc, routing, uld,shipments);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			
			/**** XTMV Message Loading ****/
			map.put("MVTDate", cust.createDateFormat("ddMM", 0, "DAY", ""));
			cust.createXMLMessage("MessageExcelAndSheetXTMV","MessageParamXTMV");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XTMV", true);
			
			

			cust.closeTab("MSG005", "List Message");
		
			
			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("prop~flight_code", "FlightNo", "StartDate");
			String pmkey = Excel.getCellValue(path1,sheetName, "Breakdown_IAD5_002", "UldNum");
            OPR367.clickCheckBox_ULD(pmkey);
            OPR367.clickBreakdownButton();
            OPR004.verifyThruCheckbox();
            OPR004.verifyAddButtonIfDisabled();
			OPR004.breakdownLocIfAutoPopulated();
            OPR004.captureDamage("DmgCode", "DamagePcs");
            OPR004.verifyDamageCaptured("Y");
		    OPR004.enterBreakdownLocationInPopup(cust.data("Transit"));
            OPR004.clickBreakdownComplete();
            OPR367.closeFromOPR004();
            OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");
			
			/*******Verify xFSU-RCF message in MSG005******/        
            cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.clickClearButton();
            MSG005.enterMsgType("XFSU");
            MSG005.selectMsgSubType("Breakdown");
            MSG005.clickReference();
            MSG005.enterReferenceValue("FSU", "FlightNo", "prop~AWBNo");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
            MSG005.verifyMessageTriggered("prop~AWBNo", "XFSU-RCF");
            libr.waitForSync(6);
            MSG005.closeTab("MSG005", "MSG005 - List Messages");			
            libr.quitBrowser();
			


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}