package awm;

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
import screens.AssignOutboundFlightToEquipment_ADD013;
import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.GoodsAcceptance_OPR335;
import screens.ListAuditEnquiry_SHR011;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
/***TC_02_Verify user is able to view audit details for submodule as assign flight to equipment**/
public class IASCB_56364_TC_10951 extends BaseSetup {
	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public BreakDownScreen_OPR004 OPR004;
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainFlightSchedule_FLT005 FLT005;
	public GoodsAcceptance_OPR335 OPR335;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	public ListAuditEnquiry_SHR011 SHR011;
	public MaintainOperationalFlight_FLT003 FLT003;
	public AssignOutboundFlightToEquipment_ADD013 ADD013;


	String path1 = System.getProperty("user.dir") + "\\src\\resources\\AWM.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "AWM_FT";

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
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		SHR011 = new ListAuditEnquiry_SHR011(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		ADD013 = new AssignOutboundFlightToEquipment_ADD013(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_10951")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_10951")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);


			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String date=cust.createDateFormat("ddMMMYY", 0, "DAY", "");
			System.out.println(date);
			map.put("StartDate", startDate);
			map.put("Date", date);

			// Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);


			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");

			/** Flight-1 Creation **/
			cust.createFlight("FullFlightNumber");
			//Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber2", cust.data("carrierCode") + cust.data("prop~flightNo"), proppath);
			String FlightNum1 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			map.put("FullFlightNo", FlightNum1);
			map.put("FlightNo", FlightNum1.substring(2));


			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("FlightNo", "StartDate");
			FLT003.enterFlightDetails("Route", "scheduleType", "FCTL", "Office", "flightType");
			FLT003.enterLegCapacityDetails("ATD_Local","ATA_Local", "AircraftType", "");
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");


			cust.getUser("user");
			System.out.println(cust.data("user"));

			/**Add013 - Assign outbound flight to equipment **/
			cust.searchScreen("ADD013", "Assign outbound flight to Equipment");
			ADD013.enterFromDate("Date");
			ADD013.enterToDate("Date");
			ADD013.enterFlightCode("carrierCode");
			ADD013.enterFlightNum("FlightNo");
			ADD013.clickList();
			ADD013.selectEquipmentType(cust.data("EquipmentType1"), "Equipment1");
			String currtimecdg=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("Currtime", currtimecdg);

			System.out.println(cust.data("Currtime"));
			//assign equipment type TOP/RFS to outbound flight
			ADD013.verifyEquipmentTypeAssigned("Equipment1");
			cust.closeTab("ADD013", "Assign outbound flight to equipment");


			String currDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			map.put("CurrDate", currDate);

			/**SHR011 - List Audit Enquiry  **/
			cust.searchScreen("SHR011", "List Audit Enquiry");
			SHR011.selectModuleName(cust.data("ModuleName"));
			SHR011.selectSubModuleName(cust.data("expsubmodule"));
			SHR011.enterFromDate(cust.data("CurrDate"));
			SHR011.enterToDate(cust.data("CurrDate"));
			SHR011.enterFlightDetails("carrierCode","FlightNo","Date");		
			SHR011.listDetails();

			int verfcols[]={1,2,3,5};
			String pmkey=cust.data("Transaction");
			String flightDate = cust.createDateFormat("dd-MM-YYYY", 0, "DAY", "");
			map.put("flightdate",flightDate );

			String actverfValues[]={cust.data("Transaction"),cust.data("user"),cust.data("CurrDate")+" "+cust.data("Currtime"),cust.data("Origin")};

			// verify transaction details updated based on equipment assigned to flight
			SHR011.verifyTransactionDetails(verfcols,actverfValues,pmkey);
			int verfcols1[]={4};
			String actverfValues1[]={"Flight:"+" "+cust.data("carrierCode")+" "+cust.data("FlightNo")+","+" "+cust.data("flightdate")+";"+" "+"Assigned"+" "+"Equipments"+":"+" "+cust.data("EquipmentType1")+" "+"-"+" "+cust.data("Equipment1")};
			SHR011.verifyTransactionDetails(verfcols1,actverfValues1,pmkey);
			SHR011.closeTab("SHR011", "List Audit Enquiry");
			libr.quitBrowser();


		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}
		finally {
			try {
				excelRead.writeDataInExcel(map, path1, sheetName, testName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
