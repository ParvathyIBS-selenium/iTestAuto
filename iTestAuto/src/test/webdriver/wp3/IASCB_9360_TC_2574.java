package wp3;
/**  Verify warning when satchel is printed with discrepancies  **/
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
import screens.ExportFlightProgress_OPR336;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;
import screens.ListAuditEnquiry_SHR011;
import screens.MaintainFlightSchedule_FLT005;
import screens.PrintLabel_OPR343;
import screens.ScanAWBDocumentintoSatchel_OPR086;
import screens.SecurityAndScreening_OPR339;
import screens.ListMessages_MSG005;

public class IASCB_9360_TC_2574 extends BaseSetup {

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
	public ExportManifest_OPR344 OPR344;
	public MaintainFlightSchedule_FLT005 FLT005;
	public GoodsAcceptance_OPR335 OPR335;
	public ListAuditEnquiry_SHR011 SHR011;
	public PrintLabel_OPR343 OPR343;
	public ScanAWBDocumentintoSatchel_OPR086 OPR086;
	public ExportFlightProgress_OPR336 OPR336;
	public ListMessages_MSG005 MSG005;


	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "wp3";

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
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		SHR011 = new ListAuditEnquiry_SHR011(driver, excelreadwrite, xls_Read);
		OPR343 = new PrintLabel_OPR343(driver, excelreadwrite, xls_Read);
		OPR086 = new ScanAWBDocumentintoSatchel_OPR086(driver, excelreadwrite, xls_Read);
		OPR336 = new ExportFlightProgress_OPR336(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2574")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2574")
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

			String startDate = cust.createDateFormat("dd-MMM-YYYY",0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 2, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
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
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_ES"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_ES"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_ES"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_ES"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_ES"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_ES"));
			map.put("ConsigneeCountryName",WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_ES"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_ES"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_ES"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_ES"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "MAD"));

			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** Switch role to Origin **/
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/** Flight-1 Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber2", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			map.put("FullFlightNo", FlightNum1);
			map.put("FlightNo", FlightNum1.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/** Flight-2 Creation **/
			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo1", FlightNum2);
			map.put("FlightNo1", FlightNum2.substring(2));

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo2", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			cust.setPropertyValue("AWBNo2", cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo2"));
			map.put("AWBNo", cust.data("prop~AWBNo2"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			cust.setPropertyValue("AWBNo", cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo1", cust.data("prop~FullAWBNo"));
			map.put("AWBNo1", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			/** SSM Message loading **/

			map.put("FlightNumber", cust.data("FullFlightNo"));
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");

			map.put("FlightNumber", cust.data("FullFlightNo1"));
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");



			/**** XFBL Message loading ****/
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc"),libr.data("FullAWBNo1") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
							+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC"),cust.data("SCC")};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination"),cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);


			/**** XFWB Message loading- AWB1 ****/
			map.put("awbnumber",cust.data("FullAWBNo"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);

			/**** XFWB Message loading- AWB2 ****/
			map.put("awbnumber",cust.data("FullAWBNo1"));
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);
			cust.closeTab("MSG005", "List Message");


			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo2", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			/**** OPR026 - Capture AWB ****/
			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/**** OPR026 - Capture AWB ****/
			// As Is Execute AWB
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo1", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/****OPR355 - Loose Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo2", "CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/****OPR355 - Loose Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR344 - Export manifest - AWB 1****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWB("UldNum","0","CarrierNumericCode","AWBNo","Pieces","Weight");
			OPR344.verifyULDInAssignedShipment("UldNum", true);		
			OPR344.clickManifest();
			OPR344.printManifestOk();
			cust.printAndVerifyReport("val~CARGO MANIFEST","OPR344",cust.data("carrierCode")+" "+cust.data("FlightNo"),cust.data("UldNum"));
			OPR344.printManifestClose();
			OPR344.verifyFlightStatus("val~Manifested");
			cust.closeTab("OPR344", "Export Manifest");


			/**** OPR344 - Export manifest - AWB 2 manifested into another flight ****/
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo1","StartDate");
			String uldNum1=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum1);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDWithAWB("UldNum","0","CarrierNumericCode","AWBNo1","Pieces","Weight");
			OPR344.verifyULDInAssignedShipment("UldNum", true);			
			OPR344.manifestDetails();
			OPR344.verifyFlightStatus("val~Manifested");
			cust.closeTab("OPR344", "Export Manifest");

			
			
			/** OPR343 - Print Label screen **/
			cust.searchScreen("OPR343", "Print Labels");
			OPR343.selectLabel("Labeltype");
			OPR343.listFlightDetails("carrierCode", "FlightNo","StartDate");
			OPR343.clickprint("FullFlightNo");
			OPR343.verifySatchelGeneration("Satchel","FullFlightNo");
			cust.closeTab("OPR343", "Print Labels");			

			
			/**  OPR086 - Scan AWB document into satchel **/
			cust.searchScreen("OPR086", "Scan AWB Document Into Satchel");
			OPR086.listSatchel("Satchel");
			map.put("FullAWBNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo"));
			OPR086.enterAWB("FullAWBNumber");
			map.put("FullAWBNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo1"));
			OPR086.enterAWB("FullAWBNumber");
			OPR086.clickSave();
			OPR086.verifyAndHandleWarning("All AWBs with discrepancies shall be removed from the list", "no");
			cust.closeTab("OPR086", "Scan AWB Document Into Satchel");

			/*** Verify Status of Added AWBs **/
			cust.searchScreen("OPR086", "Scan AWB Document Into Satchel");
			OPR086.listSatchel("Satchel");
			int[] cols={3};
			String[] values={"Manifested"};
			String[] values1={"Manifested in "+cust.data("carrierCode")+" "+cust.data("FlightNo1")+" "+startDate+" "+cust.data("Destination")};
			OPR086.verifyAWBDetails(cols, values, cust.data("AWBNo"));
			OPR086.verifyAWBDetails(cols, values1, cust.data("AWBNo1"));
			cust.closeTab("OPR086", "Scan AWB Document Into Satchel");

			/** OPR336 - Export Flight Progress  **/
			cust.searchScreen("OPR336", "Export Flight Progress");
			OPR336.captureDates("StartDate", "EndDate");
			OPR336.clickMoreOptions();
			OPR336.enterFlightDetails("carrierCode", "FlightNo","StartDate");
			OPR336.clickList();

			OPR336.verifyColumn("val~Satchel Handover");
			//Clicking "no" on warning message display
			OPR336.clickDropdownOptions();
			OPR336.clickCaptureRemoveSatchelHandover();
			OPR336.verifyWarningMsg("The satchel has discrepancies. Do you want to continue?","no");
			OPR336.verifySatchelHandoverStatus("val~Pending");

			//Clicking "yes" on warning message display
			OPR336.clickDropdownOptions();
			OPR336.clickCaptureRemoveSatchelHandover();
			OPR336.verifyWarningMsg("The satchel has discrepancies. Do you want to continue?","yes");
			OPR336.verifySatchelHandoverStatus("val~Completed");					
			cust.closeTab("OPR336", "Export Flight Progress");


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
