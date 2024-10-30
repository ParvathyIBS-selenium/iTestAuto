package mvp_cr_iascb_9283_overhang;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BuildUpHHT;
import screens.CaptureAWB_OPR026;
import screens.CaptureConsumablesHHT;
import screens.Cgocxml;
import screens.DeadloadStatement_OPR063;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptanceHHT;
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

/**Capture Overhang/Indent details for Pallet ULD with Part Shipment**/

public class IASCB_9283_TC06 extends BaseSetup {

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
	public BuildUpHHT buhht;
	public GoodsAcceptanceHHT gahht;
	public CaptureConsumablesHHT cchht;
	public DeadloadStatement_OPR063 OPR063;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public SecurityAndScreening_OPR339 OPR339;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName="mvp_cr_iascb_9283_overhang";	

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
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		cchht=new CaptureConsumablesHHT(driver, excelreadwrite, xls_Read);
		OPR063=new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);

	}



	@DataProvider(name = "IASCB_9283_TC16")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}


	@Test(dataProvider = "IASCB_9283_TC16")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// creating flight number
			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			FlightNum=FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormat("ddMMMyyyy", 0, "DAY", "").toUpperCase());
			System.out.println(FlightNum);

			/*** Storing Values to Map ***/

			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/
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

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_US"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_US"));
			
			/******** TELEX ADDRESS****/
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			
			

			//Login to "MERCURY"
			String[] mercury = libr.getApplicationParams("mercury");
			driver.get(mercury[0]); // Enters URL
			cust.loginToMercury(mercury[1], mercury[2]);

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");
			mercuryScreen.returnTosendMessage();


			/***MESSAGE - loading ASM for flight 2**/
			cust.createFlight("FullFlightNumber2");

			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			FlightNum2=FlightNum2.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo2", FlightNum2.substring(2));

			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM2");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("ASM");
			libr.quitBrowser();


			// Login to iCargo
			//Relaunch browser
			driver=libr.relaunchBrowser("chrome");
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);


			//Checking AWB is fresh or Not--AWB 1
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);

			libr.quitBrowser();

			/************ LOADING MESSAGE VIA CGOCXML***/

			driver=libr.relaunchBrowser("chrome");
			// Login to "CGOCXML"
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);

			/***** XFBL Loading ***/ 
			map.put("FullFlightNumber", cust.data("FullFlightNo"));
			map.put("Pcs", cust.data("Pieces2"));
			map.put("Wgt", cust.data("Weight2"));
			map.put("Vol", cust.data("Volume2"));

			//Create XFBL message
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/***** XFBL Loading ***/ 

			//Create XFBL message 2
			map.put("FullFlightNumber", cust.data("FullFlightNo2"));
			map.put("Pcs", cust.data("Pieces1"));
			map.put("Wgt", cust.data("Weight1"));
			map.put("Vol", cust.data("Volume1"));

			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			/***MESSAGE - loading XFWB for AWB 1 **/
			cust.createXMLMessage("MessageExcelAndSheetFWB","MessageParamFWB");
			Cgocxml.sendMessageCgoCXML("ICARGO");

			libr.quitBrowser();



			/***** RELOGIN TO ICARGO***/
			driver=libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			/**** OPR339 - Security & Screening****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass"); 
			OPR339.enterSCC("val~SPX");
			OPR339.chkSecurityDataReviewed();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Sceening");

			//As Is Execute AWB
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.asIsExecuteOnly();

			//Generate Payment Advice Screen
			CSH007.verifyServiceCode("val~AWBI");
			CSH007.selectPaymentMode("Cash");
			CSH007.enterRemarks("val~Cash Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");	
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			cust.closeTab("OPR026", "Capture AWB");

			/** CHECKING XFWB TRIGGERED FOR AWB 1**/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFWB=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination");
			int verfColsXFWB[]={9};
			String[] actVerfValuesXFWB={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFWB, actVerfValuesXFWB, pmKeyXFWB,"val~XFWB",true);
			libr.waitForSync(1); 
			cust.closeTab("MSG005", "List Message");



			/***Launch emulator - hht**/
			libr.launchApp("hht-app-release");

			//Login in to HHT
			String [] hht=libr.getApplicationParams("hht");	
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - ACCEPTANCE ****/

			gahht.invokeAcceptanceScreen();
			map.put("awbNumber", cust.data("CarrierNumericCode")+cust.data("prop~AWBNo"));
			gahht.enterValue("awbNumber");
			gahht.clickSaveCaptureChecksheet();
			gahht.selectSCCValue("SCC");
			gahht.enterLooseAcceptanceDetails("Pieces", "Weight", "Location");
			gahht.checkAllPartsReceived();
			gahht.saveAcceptanceDetails();
			cust.clickBack("Acceptance");
			cust.clickBack("Acceptance");

			/*** HHT - Build Up****/

			buhht.invokeBuildUpScreen();

			String uldNum=cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNum);

			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("carrierCode", "prop~flightNo","currentDay");
			
			buhht.clickMoreOptions();
			buhht.clickUpdateULDHeightContour();
			buhht.captureContour(cust.data("Contour"));
			
			buhht.enterAWBDetailsWithoutPcsWgt("awbNumber");
			buhht.clickSaveCaptureChecksheet();
			buhht.enterShipmentDetails("Pieces2", "Weight2");
			buhht.verifyBuildUpDetailsIfSaved();

			cust.clickBack("Build Up");
			cust.clickBack("Build Up");


			/**** BUILD UP COMPLETE FOR ULD ****/

			buhht.enterValue("UldNum");
			buhht.clickMoreOptions();
			buhht.clickCaptureOverhangIndent();
			buhht.captureOverhangIndentDetails("Front","Rear","Left","Right");
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteWithTopUpNoOption();
			cchht.selectMaterial("val~Belt");
			cchht.clickSave();
			cust.clickBack("Build Up");


			/*******Verify FSU-RCS message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCS=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsRCS[]={9};
			String[] actVerfValuesRCS={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-RCS",false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/********** CHECKING IF FSU-PRE GOT TRIGGERD****/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Prepared for loading");
			MSG005.clickList();
			String pmKeyPRE=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsPRE[]={9};
			String[] actVerfValuesPRE={"Sent"};
			MSG005.verifyMessageDetails(verfColsPRE, actVerfValuesPRE, pmKeyPRE,"val~XFSU-PRE",false);
			libr.waitForSync(2); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/** CHECKING XFUM TRIGGERED FOR AWB **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");									
			MSG005.enterMsgType("XFUM");
			MSG005.clickList();
			String pmKeyFUM=cust.data("Origin")+" - "+cust.data("UldNum").substring(3,8);
			int  verfColsFUM[]={9};
			String[] actVerfValuesFUM={ "Sent"};
			MSG005. verifyMessageDetails(verfColsFUM,actVerfValuesFUM, pmKeyFUM,"val~xFUM",false);
			libr.waitForSync(1);
			MSG005.closeTab( "MSG005","MSG005 - List Messages");	


			/************* DEADLOAD STATEMENT****************/
			cust.searchScreen("OPR063", "Dead load statement");
			OPR063.listFlightDetails("carrierCode","prop~flightNo","StartDate");
			OPR063.selectULD(cust.data("UldNum"));
			OPR063.enterActualWeightOfUld("UldWeight");
			OPR063.clickSave();
			OPR063.selectULD(cust.data("UldNum"));
			OPR063.clickULDLoadingInstuctor();
			OPR063.verifyULDLoadingInstuctorPopUp("prop~flightNo", "UldNum","Contour","Front","Rear","Left","Right");
			OPR063.ULDLoadingInstructionOK();
			cust.closeTab("OPR063", "Dead load statement");
			

			/*** HHT - Build Up****/
			String uldNum2=cust.create_uld_number("UldType2", "carrierCode");
			map.put("UldNum2", uldNum2);
			buhht.invokeBuildUpScreen();
			buhht.enterValue("UldNum2");
			buhht.updateFlightDetailsWithOutPopUp("carrierCode", "prop~flightNo2","currentDay");
			
			buhht.clickMoreOptions();
			buhht.clickUpdateULDHeightContour();
			buhht.captureContour(cust.data("Contour"));
			
			buhht.enterAWBDetailsWithoutPcsWgt("awbNumber");
			buhht.clickSaveCaptureChecksheet();
			buhht.enterShipmentDetails("Pieces1", "Weight1");
			buhht.verifyBuildUpDetailsIfSaved();

			cust.clickBack("Build Up");
			cust.clickBack("Build Up");

			/**** BUILD UP COMPLETE FOR ULD ****/

			buhht.enterValue("UldNum2");
			buhht.clickMoreOptions();
			buhht.clickCaptureOverhangIndent();
			buhht.captureOverhangIndentDetails("Front","Rear","Left","Right");
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteWithTopUpNoOption();
			cchht.selectMaterial("val~Belt");
			cchht.clickSave();
			libr.quitApp();

			/********** CHECKING IF FSU-PRE GOT TRIGGERD****/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Prepared for loading");
			MSG005.clickList();
			String pmKeyPRE1=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsPRE1[]={9};
			String[] actVerfValuesPRE1={"Sent"};
			MSG005.verifyMessageDetails(verfColsPRE1, actVerfValuesPRE1, pmKeyPRE1,"val~XFSU-PRE",false);
			libr.waitForSync(2); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/** CHECKING XFUM TRIGGERED FOR AWB **/

			cust.searchScreen("MSG005", "MSG005 - List Messages");									
			MSG005.enterMsgType("XFUM");
			MSG005.clickList();
			String pmKeyFUM1=cust.data("Origin")+" - "+cust.data("UldNum2").substring(3,8);
			int  verfColsFUM1[]={9};
			String[] actVerfValuesFUM1={ "Sent"};
			MSG005. verifyMessageDetails(verfColsFUM1,actVerfValuesFUM1, pmKeyFUM1,"val~xFUM",false);
			libr.waitForSync(1);
			MSG005.closeTab( "MSG005","MSG005 - List Messages");	


			/************* DEADLOAD STATEMENT****************/
			cust.searchScreen("OPR063", "Dead load statement");
			OPR063.listFlightDetails("carrierCode","prop~flightNo2","StartDate");
			OPR063.selectULD(cust.data("UldNum2"));
			OPR063.enterActualWeightOfUld("UldWeight");
			OPR063.clickSave();
			OPR063.selectULD(cust.data("UldNum2"));
			OPR063.clickULDLoadingInstuctor();
			OPR063.verifyULDLoadingInstuctorPopUp("prop~flightNo2", "UldNum2","Contour","Front","Rear","Left","Right");
			OPR063.ULDLoadingInstructionOK();
			cust.closeTab("OPR063", "Dead load statement");



		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}

}




