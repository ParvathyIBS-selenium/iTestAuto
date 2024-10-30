package mvp_reg_acceptance;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

//ULD partial acceptance. AWB data capture and screening not done (or fail) (FWB received)

public class Acceptance_IAD4_005 extends BaseSetup {

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
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainFlightSchedule_FLT005 FLT005;
	public Cgocxml Cgocxml;
	public Cgomon Cgomon;
	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="mvp_reg_acceptance";	

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
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);

		Cgocxml=new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon=new Cgomon(driver, excelreadwrite, xls_Read);
	}



	@DataProvider(name = "Acceptance_IAD4_005")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "Acceptance_IAD4_005")
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

			cust.switchRole("Origin", "FCTL", "RoleGroup");
			/**** UPDATING XFWB GENERAL DETAILS IN MAP****/


			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");			
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());

			excelRead.writeDataInExcel(map, path1, sheetName, testName);



			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP***/

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_EG"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_EG"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_EG"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_EG"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_EG"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_EG"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_EG"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_EG"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_EG"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_EG"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CAI"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));


			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_EG"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_EG"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_EG"));


			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			// Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

               libr.quitBrowser();
			
			// Login to "CGOCXML"
			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			

			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");

			libr.quitBrowser();
			 
			
			/**** LOGIN TO ICARGO***/

			driver=libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/****OPR355 - Goods Acceptance****/

			//ULD acceptance - Partial acceptance

			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			//Create ULD number1
			String uldNo1=OPR335.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum1", uldNo1);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			OPR335.verifyAWBDetails("Pieces", "Weight","Volume","CommodityCode");
			OPR335.uldShipmentDetails("Pieces1","Weight1", "Location", "UldNum1","Contour");
			//Select contour from dropdown		
			OPR335.selectContour("Contour");
			OPR335.addULDDetails();
			OPR335.clickSave();
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			//Accept Remaining pieces
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance"); 

			//Create ULD number2
			String uldNo2=OPR335.create_uld_number("UldType2", "prop~flight_code");
			map.put("UldNum2", uldNo2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);

			OPR335.addNewULDAcceptance("Pieces2","Weight2", "Location", "UldNum2","Contour2");
			//Select contour from dropdown
			//	Should use L as contour instead of L3
			OPR335.selectContour("Contour2");
			OPR335.addULDDetails();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptanceWithBlockExists();
			cust.switchToFrame("contentFrame","OPR335");
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");


			/**Message details  for xFSU-FOH and xFSU-RCS **/

			/*******Verify FSU-FOH message in MSG005******/


			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Freight On Hand");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFSU=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsFSU[]={9};
			String[] actVerfValuesFSU={"Sent"};
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU-FOH",true);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/*******Verify FSU-RCS is not sent message in MSG005******/
        	cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.enterMsgType("XFSU");
            MSG005.selectMsgSubType("Acceptance");
            MSG005.clickReference();
            MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
            MSG005.verifyNoMsgTriggered("MSG005");
            libr.waitForSync(1);
            MSG005.closeTab("MSG005", "MSG005 - List Messages");


		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
