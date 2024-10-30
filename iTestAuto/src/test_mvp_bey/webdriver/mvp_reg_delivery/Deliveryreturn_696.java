package mvp_reg_delivery;


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
import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.Cgomon;
import screens.DeliverCargo_OPR064;
import screens.DeliveryDocumentation_OPR293;
import screens.DeliveryReturn_OPR036;
import screens.GeneratePaymentAdvice_CSH007;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;


/**
 * Delivery Return
**/
public class Deliveryreturn_696 extends BaseSetup {
	
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
	public GeneratePaymentAdvice_CSH007 CSH007;
	public ListMessages_MSG005 MSG005;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public DeliveryDocumentation_OPR293 OPR293;
	public BreakDownScreen_OPR004 OPR004;
	public DeliverCargo_OPR064 OPR064;
	public Mercury mercuryScreen;
	public Cgomon Cgomon;
	public Cgocxml Cgocxml;
	public DeliveryReturn_OPR036 OPR036;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";
	String sheetName="mvp_reg_delivery";	
	
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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		OPR064 = new DeliverCargo_OPR064(driver, excelreadwrite, xls_Read);
		mercuryScreen=new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml=new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon=new Cgomon(driver, excelreadwrite, xls_Read);
		OPR036=new DeliveryReturn_OPR036(driver, excelreadwrite, xls_Read);
		
	}
	
	
	

	@DataProvider(name = "testdata")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "testdata")
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
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			System.out.println(cust.data("Day"));

			excelRead.writeDataInExcel(map, path1, sheetName, testName);
					
			/**STORING VALUES TO MAP FOR XFWB LOAD **/		
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
					
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR2"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR2"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR2"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR2"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR2"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR2"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR2"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR2"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR2"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR2"));
			
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "credit_exp_cash_imp_CustomerId_LB"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "credit_exp_cash_imp_CustomerName_LB"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "credit_exp_cash_imp_postCode_LB"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "credit_exp_cash_imp_streetName_LB"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "credit_exp_cash_imp_cityName_LB"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "credit_exp_cash_imp_countryId_LB"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "credit_exp_cash_imp_countryName_LB"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "credit_exp_cash_imp_countrySubdivision_LB"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "credit_exp_cash_imp_telephoneNo_LB"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "credit_exp_cash_imp_email_LB"));
			
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_FR2"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_FR2"));
			
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "BEY"));
			
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			
			
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
						
			/** Flight Creation 1**/						
			cust.createFlight("FullFlightNumber");
			cust.setPropertyValue("flightNo", cust.data("prop~flightNo"), proppath);
			cust.setPropertyValue("flightNumber", cust.data("carrierCode")+cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			System.out.println(FlightNum);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);			
			libr.quitBrowser();
			
			//Relaunch browser
	        driver=libr.relaunchBrowser("chrome");
			
			/******************MERCURY*********************/			
			//Login to "MERCURY"
	    	String[] mercury = libr.getApplicationParams("mercury");
	    	driver.get(mercury[0]); // Enters URL
	    	cust.loginToMercury(mercury[1], mercury[2]);
					
			/**ASM Message Loading Needs to be replace with Mercury **/			
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("SSM");
			libr.quitBrowser();
			
			//Relaunch browser
	        driver=libr.relaunchBrowser("chrome");
	
			// Login to "CGOCXML"	        
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
			
			//Create XFWB message
	        cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");	    
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			/**** XFFM Message Creation and Upload ****/			
	        String uldNo=cust.create_uld_number("UldType", "carrierCode");
		    map.put("UldNum", uldNo);
		    excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));
			map.put("ULDNo",cust.data("UldNum").replaceAll("[^0-9]", ""));
	
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[]={cust.data("prop~FullAWBNo")+";"+cust.data("Pieces")+";"+cust.data("Weight")+";"+cust.data("Volume")+";"+cust.data("ShipmentDesc")};
			String scc[]={cust.data("SCC")};
	        String routing[]={cust.data("Origin")+";"+cust.data("OriginAirport")+";"+cust.data("Destination")+";"+cust.data("DestinationAirport")};
	        String uld[]={cust.data("UldType")+";"+cust.data("ULDNo")+";"+cust.data("carrierCode")};
	     	
	     	cust.createXFFMMessage("XFFM",shipment,scc,routing,uld);     	
	        //LOADING XFFM 
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			/****** MERCURY***/		
			//Relaunch browser
	        driver=libr.relaunchBrowser("chrome");        
			
			/** Loading MVT : DEPARTURE  **/
	        driver.get(mercury[0]); // Enters URL
	    	cust.loginToMercury(mercury[1], mercury[2]);
	        
			cust.createTextMessage("MessageExcelAndSheetMVTDEP", "MessageParamMVTDEP");		
			mercuryScreen.clickSendMessage();
			mercuryScreen.enterTelexAddress("SenderAddressMercury", "DestinationAddressMercury",true);
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");
			
			/** Loading MVT : ARRIVAL  **/		
			mercuryScreen.returnTosendMessage();
			cust.createTextMessage("MessageExcelAndSheetMVTATA", "MessageParamMVTATA");
			mercuryScreen.sendMessageInMercury();
			mercuryScreen.verifyMsgStatus("MVT");
			libr.quitBrowser();
			
			//Relaunch browser		
	        driver=libr.relaunchBrowser("chrome");
	        
	        driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			  //Switch role
	        cust.switchRole("Destination", "FCTL", "RoleGroup");

			/** IMPORT MANIFEST **/     
			cust.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("carrierCode","prop~flightNo", "StartDate");
            map.put("pmkey",cust.data("UldNum"));
            OPR367.clickCheckBox("pmkey");
            OPR367.enterBreakdownDetails("BreakdownLoc", "Pieces", "Weight");
            OPR367.clickBreakdownComplete();
            OPR367.SaveDetailsInOPR004();
			OPR367.closeTab("OPR367", "Import Manifest");
			
			/** OPR293 : DELIVERY DOCUMENTATION **/			
			//Capture handover details and generate delivery id
			cust.searchScreen("OPR293", "Delivery Documentation");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
			OPR293.selectAllAWBs();
			OPR293.enterCustName("ConsigneeCode");
			OPR293.generateDeliveryIDWithOthercharges("val~MI","Remarks","CASH",true);
			OPR293.verifyDNStatus("Paid");
			OPR293.selectAllAWBs();
			OPR293.enterCaptureHandOverDetails();
			OPR293.verifyHandoverTickMark("prop~AWBNo");
			
			/** OPR293 : CAPTURE DELIVERY **/			
			OPR293.clickCaptureDelivery();
			OPR293.selectAllAWBs();
			OPR064.enterDeliveredTo(cust.data("ConsigneeName"));
			OPR064.clickSave();
			OPR064.close("Deliver Cargo");
			cust.closeTab("OPR293", "Delivery Documentation");
			
			/** OPR036 : DELIVERY RETURN **/			
			cust.searchScreen("OPR036", "Delivery Return");
			OPR036.ListByAWB("CarrierNumericCode", "prop~AWBNo");
			OPR036.enterNumberOfPiecesAndWeight("Pieces", "Weight");
			OPR036.enterLocation("BreakdownLoc");
			/****OPR036.enterSU("UldNum");*****/
			OPR036.selectSCC();
			OPR036.selectReasonCode("Damage");
			OPR036.saveInOPR036();
			cust.closeTab("OPR036", "Delivery Return");
			
			/********** CHECKING IF xFSU-DLV GOT TRIGGERD****/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Delivery");
			MSG005.clickList();
			String pmKeyDLV=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsDLV[]={9};
			String[] actVerfValuesDLV={"Sent"};
			MSG005.verifyMessageDetails(verfColsDLV, actVerfValuesDLV, pmKeyDLV,"val~XFSU-DLV",false);
			libr.waitForSync(2); 
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}


