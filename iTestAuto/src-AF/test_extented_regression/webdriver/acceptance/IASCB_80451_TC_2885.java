package acceptance;

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
import screens.GoodsAcceptance_OPR335;
import screens.TransportOrderListing;
import screens.ListMessages_MSG005;
import screens.GoodsAcceptanceHHT;
import screens.SecurityAndScreening_OPR339;
import screens.WarehouseSetUpEnquiry_WHS013;
import screens.RelocationTaskMonitor_WHS052;

/**
 * 
 * TC_02_Verify TO generation during acceptance_ULD shipment
 *
 *
 */


public class IASCB_80451_TC_2885 extends BaseSetup {

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
	public TransportOrderListing to;
	public ListMessages_MSG005 MSG005;
	public GoodsAcceptanceHHT gahht;
	public SecurityAndScreening_OPR339 OPR339;
	public WarehouseSetUpEnquiry_WHS013 WHS013;
	public RelocationTaskMonitor_WHS052 WHS052;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Acceptance.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "Acceptance_FT";

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
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);	
		WHS013=new WarehouseSetUpEnquiry_WHS013(driver,excelreadwrite,xls_Read); 
		WHS052=new RelocationTaskMonitor_WHS052(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2885")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2885")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);


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



			// Login to iCargo

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);

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

			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB"));

			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");
			String currentday=cust.createDateFormatWithTimeZone("ddMMMYY", 0, "DAY", "Europe/Paris").toUpperCase();
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);


			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");


			// Checking AWB is fresh or Not 
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));


			/*** MESSAGE - loading XFWB **********/
			// Create XFWB message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo", true);
			cust.closeTab("MSG005", "List Message");


			/***** OPR026 - Execute AWB ****/

			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");	



			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");

			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);



			/*** HHT - ACCEPTANCE****/

			gahht.invokeAcceptanceScreen();
			//Acceptance for AWB
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("AWBNo"));
			map.put("UldNum", cust.create_uld_number("UldType", "carrierCode"));
			gahht.enterValue("UldNum");
			map.put("AcceptanceLocation_From_CTXzone", WebFunctions.getPropertyValue(toproppath, "AcceptanceLocation_From_CTXzone"));
			gahht.enterUldAcceptanceDetails("AcceptanceLocation_From_CTXzone","awbNumber","Pieces");
			gahht.addULDDetails();
			gahht.save();
			libr.quitApp();


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO
			cust.loginTransportOrder(hht[0], hht[1]);


			to.searchShipment("UldNum");
			//fetch the src location
			String acceptanceLocation=to.retrieveSrcLocation("UldNum");
			map.put("acceptanceLocation", acceptanceLocation);

			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("UldNum", "AcceptanceLocation_From_CTXzone");

			//fetch destination location
			String storageAreaLocation=to.retrieveDestnLocation("UldNum");
			map.put("storageAreaLocation", storageAreaLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("UldNum", "val~Open", "AcceptanceLocation_From_CTXzone");
			libr.quitApp();



			/**** WHS013 -Warehouse Setup Enquiry ****/

			//verifying zone of the destination location
			cust.searchScreen("WHS013", "Warehouse Setup Enquiry");
			WHS013.enterLocation("storageAreaLocation");
			WHS013.clickList();
			String StorageZone=WHS013.getZoneCode();
			map.put("ControlLocationZone_COLscc_CDG", WebFunctions.getPropertyValue(toproppath, "ControlLocationZone_COLscc_CDG"));
			WHS013.verifyZone(cust.data("ControlLocationZone_COLscc_CDG"),StorageZone);
			cust.closeTab("WHS013", "Warehouse Setup Enquiry");



			/**** WHS052 -Relocation Task Monitor****/
			cust.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterULDNumber("UldNum");
			WHS052.listAwbDetails();
			//Verifying TO details in the table
			String pmKey = cust.data("UldNum");
			map.put("UldNumber", pmKey);
			String ColumnNames[]={"Status","Source HA","Dest. HA","Remarks"};
			String TODetails[]={"Open", WebFunctions.getPropertyValue(toproppath, "AcceptanceHA_CDG_FlightDestination"),WebFunctions.getPropertyValue(toproppath, "StorageAreaHA_CDG"),"RELOCATION"};
			WHS052.verifyTODetails(4, ColumnNames, "UldNumber", TODetails);
			WHS052.maximizeAwbDetails("UldNum");
			WHS052.verifyCurrentLocation("UldNum", "Current.Loc","Current.Loc"+"\n"+cust.data("acceptanceLocation"));
			WHS052.verifyDestinationLocation("UldNum", "Dest.Loc","Dest.Loc"+"\n"+cust.data("storageAreaLocation"));
			cust.closeTab("WHS052", "Relocation Task Monitor");




		} catch (Exception e) {
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