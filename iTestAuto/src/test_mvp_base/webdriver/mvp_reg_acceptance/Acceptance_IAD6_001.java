package mvp_reg_acceptance;

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
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.SecurityAndScreening_OPR339;

/**
 * Goods acceptance of an ULD with COL. AWB data capture and screening are done
**/
public class Acceptance_IAD6_001 extends BaseSetup {
	
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
	public Cgocxml Cgocxml;
	
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
		Cgocxml=new Cgocxml(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "Acceptance_IAD6_001")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "Acceptance_IAD6_001")
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
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "America/New_York");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			map.put("UserName", iCargo[1]);
			
			/***Storing Values to Map***/			
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_TH"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_TH"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_TH"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_TH"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_TH"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_TH"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_TH"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_TH"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_TH"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_TH"));

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
			
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "BKK"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
						
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_TH"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_TH"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_TH"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_TH"));

			//Regulated agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry"));
			
			//Creating new Flights

			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo2", cust.data("prop~flightNo"), proppath);

			cust.setPropertyValue("flightNumber2", cust.data("carrierCode") + cust.data("prop~flightNo2"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			//Creating second flight
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNumber", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);

			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber");
			System.out.println(FlightNum2);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo1", FlightNum2);
			map.put("FlightNo1", FlightNum2.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();
			
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			
			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
					
			/***MESSAGE - loading XFWB **********/
	        //Create XFWB message			
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			 cust.modifyMessageMap("<PrimaryID schemeID=\"C\">TDVAGT01DHLGFXX</PrimaryID>","<PrimaryID schemeID=\"C\">QVIDOAF</PrimaryID>");
            Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
            libr.quitBrowser();
			
			/***** RELOGIN TO ICARGO***/
			driver=libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	
			
			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");
			
			/**** OPR026 - Capture AWB****/		
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.clickaddtionalInfo();
			String supplCustoms[]={"RegulatedAgentCode","screenmethod","UserName","SDtime","Expiry"};
			String source[]={"val~FWB","val~FWB","val~FWB","val~FWB","val~FWB"};
			String infoId[]={"val~ISS","val~ ","val~ ","val~ ","val~ "};
			String customsInfoId[]={"val~RC","val~SM","val~SN","val~SD","val~ED"};	
			OPR026.verifyOCIDetails(supplCustoms, source, infoId, customsInfoId);
			
            OPR026.clickSecurityScreening();
            cust.switchToFrame("frameName", "popupContainerFrame");
            OPR339.verifyAgentDetailsAutopopulated("AgentType", "AgentCountryId", "RegulatedAgentCode");
            OPR339.verifyScreeningMethodAutopopulated("screenmethod");
            OPR339.checkSecurityDataReviewed();
            OPR339.checkGivenSecurityStatusAccepted();       
            OPR339.OkButtonAfterScreeningSave();
            cust.switchToMainScreen("OPR026");
			OPR026.saveAWB(); 
			cust.closeTab("OPR026", "Capture AWB");
		
            //As Is Execute AWB
            cust.searchScreen("OPR026","Capture AWB");
            OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
            OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
	
			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			//Creating ULD number
			String uldNo=OPR335.create_uld_number("UldType", "prop~flight_code");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.uldShipmentDetails("Pieces","Weight", "Location", "UldNum","Contour");
			OPR335.selectContour("Contour");
            OPR335.addULDDetails();
            OPR335.provideCTMdetails("carrierCode", "StartDate");
            OPR335.allPartsRecieved();
			OPR335.clickSave();
            OPR335.verifyAcceptanceFinalized("finalised",false);
			OPR335.verificationOfRFCStatus();          
            cust.closeTab("OPR335", "Goods Acceptance");
			
			/**Message details  for xFSU-FOH and xFSU-RCT **/			
			/*******Verify FSU-FOH message in MSG005******/			
			cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.enterMsgType("XFSU");
            MSG005.selectMsgSubType("Freight On Hand");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
            String pmKeyFSU=cust.data("prop~CarrierNumericCode")+" - "+cust.data("AWBNo");
            int verfColsFSU[]={9};
            String[] actVerfValuesFSU={"Sent"};
            MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU-FOH",false);
            libr.waitForSync(1);
            MSG005.closeTab("MSG005", "MSG005 - List Messages");
             
            /*******Verify FSU-RCT message not triggered in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Inbound CTM");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
            MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU-RCT",false);
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

