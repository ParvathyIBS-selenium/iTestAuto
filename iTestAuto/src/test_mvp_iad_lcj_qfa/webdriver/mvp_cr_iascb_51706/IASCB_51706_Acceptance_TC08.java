package mvp_cr_iascb_51706;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

/** Capture Split Shipment details for the Multi-line Part Shipment from Goods Acceptance Screen **/

public class IASCB_51706_Acceptance_TC08 extends BaseSetup {
	
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
	public CaptureAWB_OPR026 OPR026;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public Cgomon Cgomon;
	public Cgocxml Cgocxml;
	public Mercury mercuryScreen;
	public GoodsAcceptance_OPR335 OPR335;
	public SecurityAndScreening_OPR339 OPR339;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName = "mvp_cr_iascb_51706";	
	
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
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon = new Cgomon(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		
	}
	
	
	
	@DataProvider(name = "IASCB_31368_TC03")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}
	

	@Test(dataProvider = "IASCB_31368_TC03")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			WebFunctions.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
			
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
			
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
		

			// Creating Flight Number
			
			cust.createFlight("FullFlightNumber");
			cust.createFlight("FullFlightNumber2");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			String FlightNum2= WebFunctions.getPropertyValue(proppath, "flightNumber2");
			FlightNum=FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			FlightNum2=FlightNum2.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);	
			map.put("FullFlightNo2", FlightNum2);	
			map.put("FlightNo", FlightNum.substring(2));
			map.put("FlightNo2", FlightNum2.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/** LOADING ASM VIA MERCURY **/
			
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			/** ASM Loading For First Flight **/
			
			map.put("FlightNumber", cust.data("FullFlightNo"));
			
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury", true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");
			
			map.put("FlightNumber", cust.data("FullFlightNo2"));
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			
			mercuryScreen.returnTosendMessage();
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			
			// Login to iCargo STG
			
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			/******MSG005-loading XFBL***/
			
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			
			map.put("FullFlightNo", FlightNum2);
			
			//Create the message XFBL
			
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());

			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("prop~FullAWBNo") + ";" + cust.data("Pieces2") + ";" + cust.data("Weight2") + ";"+cust.data("Volume2") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC").split(",")[0] };
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			/*****LOADING FBL-2***/
			map.put("FullFlightNo", FlightNum);
			map.put("CommodityCode", "PERI");
			map.put("ShipmentDesc", "PERISHABLES");
			
			
			//Create the message XFBL
			
			map.put("FBLDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			map.put("FlightNumber", cust.data("FullFlightNo1"));

			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment2[] = { libr.data("prop~FullAWBNo") + ";" + cust.data("Pieces1") + ";" + cust.data("Weight1") + ";"
					+cust.data("Volume1") + ";" + libr.data("ShipmentDesc") };
			String scc2[] = { cust.data("SCC").split(",")[1] };
			cust.createXFBLMessage("XFBL_2", shipment2, scc2, routing);
			
			//Loading messages from CGOCXML
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			/***MESSAGE - loading XFWB **/
			
			//PER,BUP
            
            map.put("Pcs", cust.data("Pieces"));
            map.put("Wgt", cust.data("Weight"));
            map.put("Vol", cust.data("Volume"));
            map.put("CommCode", "PERISHABLES");
        	map.put("ShipmentDesc", "GENERAL COMMODI");
            cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");

            //Create XFWB with multi line shipments
            String sccs[]={cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1]};
            String flightDetails1 = cust.data("FullFlightNo")+";"+cust.data("Origin")+";"+cust.data("Destination");
            String flightDetails2 = cust.data("FullFlightNo2")+";"+cust.data("Origin")+";"+cust.data("Destination");
            String fltDetails[]={flightDetails1,flightDetails2};
            String shipmentDetails1=cust.data("Pieces2")+";"+cust.data("Weight2")+";"+cust.data("Volume2")+";"+cust.data("ShipmentDesc");
            String shipmentInfo[]={shipmentDetails1};
            cust.createXFWBMutliLineShipment("XFWB_MultiLineShipments", sccs,fltDetails,shipmentInfo);

			Cgocxml.sendMessageCgoCXML("ICARGO");

			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			
			// Re-Login to iCargo STG

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

            /********OPR026 - Capture AWB********/
            //Split SCC
            cust.searchScreen("OPR026","Capture AWB");
            OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
           
            String pcs2[]={libr.data("SplitPcs").split(",")[0],libr.data("SplitPcs").split(",")[1]};
            OPR026.splitShipmentWithSCC(libr.data("SCC2"),pcs2);
            OPR026.saveAWB();
            cust.closeTab("OPR026", "Capture AWB");
            
            /**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode",
					"OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight","val~Pass");
			OPR339.chkSecurityDataReviewed();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");
			
			
			/***** OPR026 - Execute AWB****/
			//Execute AWB
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.asIsExecuteOnly();
			
			/** Generate Payment Advice screen **/
			
			CSH007.verifyServiceCode("val~AWBI");
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			cust.closeTab("OPR026", "Capture AWB");
			

			//Goods acceptance
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.clickLooseAccptTab();
			String locs[]={libr.data("Location").split(",")[0],libr.data("Location").split(",")[1]};
			OPR335.editShipmentLocation(pcs2[0],sccs[0],locs[0]);
			OPR335.editShipmentLocation(pcs2[1],sccs[1],locs[1]);
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			OPR335.clickLooseAccptTab();
			OPR335.verifySUNotNull(cust.data("SCC").split(",")[0]);
			OPR335.verifySUNotNull(cust.data("SCC").split(",")[1]);
			cust.closeTab("OPR335", "Goods Acceptance");
			
			/** CHECKING XFWB TRIGGERED FOR AWB **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");	
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFWB1 = cust.data("CarrierNumericCode") + " - " + cust.data("prop~AWBNo") + " - "
					+ cust.data("Origin") + " - " + cust.data("Destination");
			int verfColsXFWB1[] = { 9 };
			String[] actVerfValuesXFWB1 = { "Sent" };
			MSG005.verifyMessageDetails(verfColsXFWB1, actVerfValuesXFWB1, pmKeyXFWB1, "val~XFWB", false);
			libr.waitForSync(1);
			cust.closeTab("MSG005", "List Message");
			

			/*******Verify FSU-RCS message in MSG005******/
			
			cust.searchScreen("MSG005", "MSG005 - List Messages");
	        MSG005.enterMsgType("XFSU");
	        MSG005.selectMsgSubType("Acceptance");
	        MSG005.selectStatus("Sent");
	        MSG005.clickList();
	        String pmKeyRCS=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
	        int verfColsRCS[]={9};
	        String[] actVerfValuesRCS={"Sent"};
	        MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-RCS",false);
	        libr.waitForSync(1);
	        MSG005.closeTab("MSG005", "MSG005 - List Messages");
	        
	      
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
	
}


