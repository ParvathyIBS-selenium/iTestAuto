package iascb_158972;

/**   TC_01_Verify xFSU DLV message is sent with subject code and content code as blank for airside delivery  **/

import java.util.ArrayList;
import java.util.List;
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
import screens.BreakdownHHT;
import screens.CaptureAWB_OPR026;
import screens.DeliveryDocumentation_OPR293;
import screens.DeliveryHHT;
import screens.DeliverySlip_OPR038;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;

public class IASCB_158972_TC_10610 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public MaintainFlightSchedule_FLT005 FLT005;
	public DeliveryDocumentation_OPR293 OPR293;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public DeliverySlip_OPR038 OPR038;
	public BreakdownHHT bdhht;
	public DeliveryHHT deliveryhht;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName = "iascb_158972";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		FLT006= new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR038=new DeliverySlip_OPR038(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		bdhht=new BreakdownHHT(driver, excelreadwrite, xls_Read);
		deliveryhht = new DeliveryHHT(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_10610")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_10610")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			//Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			map.put("StartDate", startDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "").toUpperCase());
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/****** UPDATING CUSTOMER DETAILS IN MAP ***/
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR1"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR1"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR1"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR1"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR1"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR1"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR1"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR1"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR1"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR1"));

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));	

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			//Regulated agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_TypeHUB_NL"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL"));
			map.put("RegulatedAgent", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_CodeHUB_NL"));

			//SM details
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Amsterdam");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);

			//Switch role to Destination
			cust.switchRole("Destination", "FCTL", "RoleGroup");

			/** Flight Creation **/
			cust.createFlight("FullFlightNumber");		
			//Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/*** MSG005 - SSM Message loading******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createTextMessage("MessageExcelAndSheetSSM", "MessageParamSSM");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "SSM_NEW");

			/** XFWB with RA, SM and SPX Message loading  **/
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo", true);

			/**XFFM Message Loading **/
			map.put("FFMDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormatWithTimeZone("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormatWithTimeZone("yyyyMMdd", 0, "DAY", ""));

			//ULD Number
			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));
			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			//Adding SPX to XFFM
			String scc[] = {cust.data("SCC")+";"+cust.data("val~SPX")};	
			String routing[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };

			//Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing, uld);
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFFM", true);
			cust.closeTab("MSG005", "List Message");
 
			/** Mark Flight Movement  **/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("FlightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			FLT006.enterFlightMovementDepartureDetail("val~00:00","StartDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,startDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements"); 

			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - BREAKDOWN****/
			bdhht.invokeBreakdownHHTScreen();
			bdhht.enterValue("UldNum");

			//Verifying Unitized button is Selected
			bdhht.verifyUnitizedButton("val~Yes");
			bdhht.enterLocation("BDNlocation");
			bdhht.clickSaveButton();

			//AirSide Delivery
			bdhht.clickAirSide();
			cust.verifyHHTSaveDetails("BreakdownHHT");
			cust.clickBack("Breakdown");
			cust.clickBack("Breakdown");

			/** Import Manifest **/
			cust.searchScreen("OPR367", "Import Manifest");
			OPR367.listFlight("carrierCode", "FlightNo", "StartDate");
			OPR367.verifyBreakdownInstructionsTag("val~Intact Unit");
			//Breakdown should be completed
			OPR367.verifyBreakdownSuccessfullImage();
			OPR367.closeTab("OPR367", "Import Manifest");

			/********** OPR293-Delivery Documentation **********/
			//Generate delivery id
			cust.searchScreen("OPR293", "Delivery Documentation");
			OPR293.listWithUld("UldNum");
			OPR293.selectAllAWBs();
			OPR293.generateDeliveryIDWithOthercharges("val~CD","Remarks","",false,true);	
			OPR293.verifyDNStatus("Paid");
			String DNInfo=OPR293.retrieveDeliveryDocumentationDetails("AWBNo", "12");
			map.put("DNinfo",DNInfo);
			OPR293.selectAllAWBs();
			OPR293.enterCaptureHandOverDetails();
			OPR293.verifyHandoverTickMark("AWBNo");
			OPR293.closeTab("OPR293", "Delivery Documentation");

			/** Delivery HHT **/
			//Perform delivery in HHT by listing the ULD number
			deliveryhht.invokeDeliveryHHTScreen();
			deliveryhht.enterAWBULDNum("UldNum");	
			deliveryhht.clickNext();
			deliveryhht.clickPendingButton();
			deliveryhht.clickNext();
			deliveryhht.enterDeliverRemarks("val~Delivered");
			deliveryhht.enterCustomsReferenceNumber("customRefNo");
			deliveryhht.clickNext();
			deliveryhht.deliveryStatusVerify("val~DELIVERED");
			deliveryhht.clickDeliveryComplete();
			deliveryhht.enterDeliveredTo("ConsigneeName");
			deliveryhht.enterVehicleInfo("VehicleInfo");
			deliveryhht.enterContactNumber("ContactNumber");
			deliveryhht.clickNext();
			deliveryhht.captureSignature();
			deliveryhht.enterRemarks("val~Delivery Completed");
			deliveryhht.clickPrintPOD();	
			libr.quitApp();		

			/***** OPR038 - Delivery Slip ******/ 
			cust.searchScreen("OPR038", "OPR038- Delivery Slip");
			OPR038.listByAWB("CarrierNumericCode", "AWBNo");
			String pmKey = cust.data("ConsigneeName");
			int verfCols [] = {3,4};
			String[] actVerfValues= { cust.data("Pieces"),cust.data("Weight")};
			OPR038.verifyTableRecords( verfCols, actVerfValues,pmKey);
			OPR038.selectCheckbox();
			OPR038.clickReprint();
			//Steps to verify the delivery slip contents 
			String DNdetails=cust.data("DNinfo").split("\\[")[0]+"( "+cust.data("Pieces")+"/"+cust.data("Weight")+"kg,"+"\n"+"Customs reference number: "+cust.data("customRefNo")+" )";	
			cust.printAndVerifyReport("val~DELIVERY SLIP", "OPR038",cust.data("Origin"),cust.data("FullAWBNo"),cust.data("Pieces")+"/"+cust.data("Weight"),cust.data("SCC"),cust.data("ShipmentDesc"),cust.data("Destination"),cust.data("ConsigneeName"),cust.data("UldNum"),DNdetails);
			//Pieces, Weight Verification
			OPR038.clickReprint();
			String elementstoVerify[]={cust.data("Weight")+".00",cust.data("Pieces")};
			int elementsIndexfromPmKey[]={8,7};		
			cust.verifyNumericElementsInReport("val~DELIVERY SLIP", "OPR038", cust.data("FullAWBNo"), elementstoVerify, elementsIndexfromPmKey);											
			OPR038.closeTab("OPR038", "Delivery Slip");		

			/********** CHECKING IF xFSU-DLV GOT TRIGGERD****/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Delivery");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyDLV=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsDLV[]={9};
			String[] actVerfValuesDLV={"Sent"};
			MSG005.verifyMessageDetails(verfColsDLV, actVerfValuesDLV, pmKeyDLV,"val~XFSU-DLV",true);
			MSG005.clickMessageCheckBox("2");
			MSG005.clickView();	        
			List <String> msgContentsPresent=new ArrayList<String>();	
			msgContentsPresent.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode></ContentCode>"+"\n"+"<Content>"+cust.data("customRefNo")+"</Content>"+"\n"+"<SubjectCode>"+"LOC"+"</SubjectCode>"+"\n"+"<CountryID>"+cust.data("ConsigneeCountryId")+"</CountryID>"+"\n"+"</IncludedCustomsNote>");
			MSG005.verifyMessageContent(msgContentsPresent,"XFSU-DLV",true);
			MSG005.closeView();		
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

		} catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
