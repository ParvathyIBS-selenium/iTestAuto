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
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.DeadloadStatement_OPR063;
import screens.DeliverCargo_OPR064;
import screens.DeliveryDocumentation_OPR293;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MarkFlightMovements_FLT006;
import screens.Mercury;

/**
 * Capture partial delivery
**/
public class PhysicalDelivery_IAD1_003 extends BaseSetup {
	
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
	public ListMessages_MSG005 MSG005; 
	public DeadloadStatement_OPR063 OPR063;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public DeliverCargo_OPR064 OPR064;
	public DeliveryDocumentation_OPR293 OPR293;
	public Mercury mercuryScreen;
	public Cgocxml Cgocxml;
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
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR063=new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367= new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR064 = new DeliverCargo_OPR064(driver, excelreadwrite, xls_Read);
		mercuryScreen = new Mercury(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		
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
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
		
			/**STORING VALUES TO MAP FOR XFWB LOAD **/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_PL"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_PL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_PL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_PL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_PL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_PL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_PL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_PL"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_PL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_PL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_PL"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_PL"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_PL"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_PL"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_PL"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_PL"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_PL"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_PL"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_PL"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_PL"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_PL"));

			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_PL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_PL"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "LCJ"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "WRO"));
			
			map.put("SenderAddressMercury", WebFunctions.getPropertyValue(telexproppath, "SenderAddressMercury"));
			map.put("DestinationAddressMercury", WebFunctions.getPropertyValue(telexproppath, "DestinationAddressMercury"));
			
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			 libr.quitBrowser();

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
			
			/******************MERCURY*********************/
			//Relaunch browser
	        driver=libr.relaunchBrowser("chrome");
			
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
			
			//Create XFWB message
	        cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
		
	       //Relaunch browser
	        driver=libr.relaunchBrowser("chrome");
	       
			// Login to "CGOCXML" and load XFWB message
			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");
			
			// Load XFFM message		
			/**** XFFM Message Creation and Upload ****/		
			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[]={cust.data("prop~FullAWBNo")+";"+cust.data("Pieces")+";"+cust.data("Weight")+";"+cust.data("Volume")+";"+cust.data("ShipmentDesc")};
			String scc[]={cust.data("SCC")};
	        String routing[]={cust.data("Origin")+";"+cust.data("OriginAirport")+";"+cust.data("Destination")+";"+cust.data("DestinationAirport")};
	        String uld[]={"BLK"};
	        cust.createXFFMMessage("XFFM",shipment,scc,routing,uld);
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
	        
	        //OPR026 - Update the consignee
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.enterConsigneeCode("ConsigneeCode");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");	
	        
            /** Import Manifest **/           
			cust.searchScreen("OPR367", "Import Manifest");
            OPR367.listFlight("carrierCode","prop~flightNo", "StartDate");
            map.put("pmkey",cust.data("val~BULK"));
            OPR367.clickCheckBox("pmkey");
            OPR367.verifyBreakdownInstructionsTag("val~Breakdown");
            OPR367.clickBreakDownandBreakdownComplete("Location", "Pieces","Weight");
			OPR367.closeTab("OPR367", "Import Manifest");
			
			/**********OPR293-Delivery Documentation**********/			
			//Capture handover details and generate delivery id
			cust.searchScreen("OPR293", "Delivery Documentation");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
			OPR293.verifyCustCode("ConsigneeCode");
			OPR293.selectAllAWBs();
			OPR293.chkVerifyBeforePayment();
			OPR293.clickAcceptPaymentButton();
			OPR293.verifyDNStatus("Paid");
			OPR293.selectAllAWBs();
			OPR293.enterCaptureHandOverDetails();
			OPR293.verifyHandoverTickMark("prop~AWBNo");
					
			/**********OPR293-Capture Delivery****************/			
			OPR293.clickCaptureDelivery();
			OPR293.selectAllAWBs();
			OPR064.enterPiecesAndWeight("Pieces2","Weight2");
			OPR064.enterDeliveredTo(cust.data("ConsigneeName"));
			OPR064.clickSave();
			OPR064.close("Deliver Cargo");
			cust.closeTab("OPR293", "Delivery Documentation");
			
			/********** CHECKING IF xFSU-DLV GOT TRIGGERD****/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Delivery");
			MSG005.clickList();
			String pmKeyDLV=cust.data("CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
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


