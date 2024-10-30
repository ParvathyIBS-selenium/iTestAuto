package buildup;

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
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.GoodsAcceptance_OPR335;
import screens.MaintainOperationalFlight_FLT003;
import screens.SecurityAndScreening_OPR339;
import screens.ListMessages_MSG005;
import screens.ExportManifest_OPR344;
import screens.AssignOutboundFlightToEquipment_ADD013;
import screens.Destocking_ADD017;
import screens.RelocationTaskMonitor_WHS052;


/**
 * 
 *TC_12_01_Verify TO to destocking IM- AGV
 *
 *
 */

public class Destocking_IM_AGV_TC_13531 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public GoodsAcceptance_OPR335 OPR335;
	public MaintainOperationalFlight_FLT003 FLT003;
	public BuildupPlanning_ADD004 ADD004;
	public SecurityAndScreening_OPR339 OPR339;
	public ListMessages_MSG005 MSG005;
	public ExportManifest_OPR344 OPR344;
	public AssignOutboundFlightToEquipment_ADD013 ADD013;
	public Destocking_ADD017 ADD017;
	public RelocationTaskMonitor_WHS052 WHS052;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";

	String sheetName = "buildup";

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
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		ADD013 = new AssignOutboundFlightToEquipment_ADD013(driver, excelreadwrite, xls_Read);
		ADD017 = new Destocking_ADD017(driver, excelreadwrite, xls_Read);
		WHS052=new RelocationTaskMonitor_WHS052(driver, excelreadwrite, xls_Read);
	}

	@DataProvider(name = "Destocking_IM_AGV_TC_13531")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "Destocking_IM_AGV_TC_13531")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// creating flight number
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNumber", cust.data("carrierCode")+cust.data("prop~flightNo"),proppath);

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");		
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Paris"));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Paris"));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", "Europe/Paris"));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "Europe/Paris").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "Europe/Paris");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());

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
			map.put("ConsigneeCountryName",WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			// Login to iCargo

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("prop~flightNo", "StartDate");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum = FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeCDG=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			System.out.println(currtimeCDG);
			String STD=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",3);
			System.out.println(STD.split(" ")[1]);
			map.put("STDTime", STD.split(" ")[1]);
			String STA=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",5);
			System.out.println(STA.split(" ")[1]);
			map.put("STATime", STA.split(" ")[1]);
			map.put("STDDate", STD.split(" ")[0]);
			map.put("STADate", STA.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");



			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("FullAWBNum", cust.data("CarrierNumericCode")+ cust.data("prop~AWBNo"));
			map.put("FullAWBNumber", cust.data("prop~CarrierNumericCode") + cust.data("prop~AWBNo")+"001");
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			/** MSG005 - List Messages **/

			//XFBL Message loading 
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC") };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFBL_2", true);

			//XFWB Message loading
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB", true);
			cust.closeTab("MSG005", "List Message");


			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");


			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");


			/**** OPR335 -Goods Acceptance****/
			//Loose acceptance
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			map.put("AcceptanceLocation", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation"));
			OPR335.looseShipmentDetails("AcceptanceLocation", "Pieces", "Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.clickSave();
			cust.closeTab("OPR335", "Goods Acceptance");


			/**** OPR344 - Export manifest****/

			//Assigning the buildup location
			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			OPR344.addNewULDandPOU("UldNum", "0");
			OPR344.addCountour("0");
			OPR344.clickMoreUldDetails();
			map.put("BuildupLocation_CDG", WebFunctions.getPropertyValue(toproppath, "BuildupLocation_CDG"));
			OPR344.captureLocationAndAWBDetails("BuildupLocation_CDG", "CarrierNumericCode", "AWBNo", "Pieces", "Weight", "Volume");
			cust.closeTab("OPR344", "Export Manifest");


			/**** ADD013 - Assign Outbound Flight To Equipment****/

			//Assigning the IM equipment to the flight
			cust.searchScreen("ADD013", "Assign Outbound Flight To Equipment");
			ADD013.enterFromDate("StartDate");
			ADD013.enterToDate("StartDate");
			ADD013.enterFlightCode("carrierCode");
			ADD013.enterFlightNum("FlightNo");
			ADD013.clickList();
			map.put("IM_Equipment", WebFunctions.getPropertyValue(toproppath, "IM_Equipment"));
			map.put("IM_EquipmentType_AGV", WebFunctions.getPropertyValue(toproppath, "IM_EquipmentType_AGV"));
			ADD013.selectEquipmentType(cust.data("IM_Equipment"),"IM_EquipmentType_AGV");
			cust.closeTab("ADD013", "Assign Outbound Flight To Equipment");


			/**** ADD017 - Destocking****/

			cust.searchScreen("ADD017", "Destocking");
			ADD017.enterFromDate("StartDate");
			ADD017.enterToDate("StartDate");
			ADD017.enterFlightDetails("carrierCode", "FlightNo");
			ADD017.clickList();
			ADD017.clickDestocking("FullFlightNo");
			//Done destocking
			ADD017.verifyDestockingListDetails("val~Destocked", "val~Y");
			cust.closeTab("ADD017", "Destocking");


			/**** OPR344 - Export manifest****/

			cust.searchScreen("OPR344", "Export manifest");
			OPR344.listFlight("carrierCode", "FlightNo","StartDate");
			//done buildup complete
			OPR344.clickBuildUpComplete();
			cust.closeTab("OPR344", "Export Manifest");


			/**** WHS052 -Relocation Task Monitor****/
			//verifying TO generated to the IM Exit (AGV)
			cust.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterULDNumber("UldNum");
			WHS052.listAwbDetails();
			//Verifying TO details in the table
			String pmKey = cust.data("UldNum");
			map.put("UldNumber", pmKey);
			String ColumnNames[]={"Status","Source HA","Dest. HA","Remarks"};
			String TODetails[]={"Open",WebFunctions.getPropertyValue(toproppath, "BuildupHA_CDG"),WebFunctions.getPropertyValue(toproppath, "IM_AGVExit_HA")};
			WHS052.verifyTODetails(3, ColumnNames, "UldNumber", TODetails);
			WHS052.maximizeAwbDetails("UldNum");
			WHS052.verifyCurrentLocation("UldNum", "Current.Loc","Current.Loc"+"\n"+cust.data("BuildupLocation_CDG"));
			WHS052.verifyDestinationLocation("UldNum", "Dest.Loc","Dest.Loc"+"\n"+cust.data("IM_Equipment"));
			//verifying the vehicle type as AGV
			WHS052.verifyVehicleType("UldNum", "Vehicle Type", "Vehicle Type"+"\n"+cust.data("val~AGV"));
			cust.closeTab("WHS052", "Relocation Task Monitor");



		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}