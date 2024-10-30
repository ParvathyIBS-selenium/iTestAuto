package wp3;

//TC_07_Autotrigger of relocation task at transit station for mixed shipment type


import java.util.Map;

import org.testng.Assert;
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
import screens.BuildUpHHT;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ImportManifest_OPR367;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.TransportOrderListing;
import screens.BreakdownHHT;
import screens.HandlingAreaSetUpScreen_WHS008;
import screens.AssignFlightLocations_WHS059;

public class IASCB_9130_TC_2130 extends BaseSetup{

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public BuildUpHHT buhht;
	public Cgocxml Cgocxml;
	public BuildupPlanning_ADD004 ADD004;
	public MaintainOperationalFlight_FLT003 FLT003;
	public TransportOrderListing to;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public MaintainFlightSchedule_FLT005 FLT005;
	public BreakdownHHT bdhht;
	public HandlingAreaSetUpScreen_WHS008 WHS008;
	public AssignFlightLocations_WHS059 WHS059;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "wp3";

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
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
		bdhht=new BreakdownHHT(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
		ADD004=new BuildupPlanning_ADD004(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		to=new TransportOrderListing(driver, excelreadwrite, xls_Read);
		WHS008= new HandlingAreaSetUpScreen_WHS008(driver, excelreadwrite, xls_Read);
		WHS059= new AssignFlightLocations_WHS059(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "TC_2130")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2130")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
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
			Thread.sleep(2300);	

			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");

			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("StartDate", startDate);
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", "Europe/Paris"));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", "Europe/Paris"));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", "Europe/Paris"));
			map.put("FWBDate", cust.createDateFormatWithTimeZone("ddMMMyy", 0, "DAY", "Europe/Paris").toUpperCase());
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "Europe/Paris");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate3", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());


			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP ***/

			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_CASSCode_US"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "cashCustomer_IATACode_US"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "cash_customerName_US"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "cash_postCode_US"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "cash_streetName_US"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "cash_cityName_US"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "cash_countryId_US"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "cash_countryName_US"));
			map.put("ShipperCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "cash_countrySubdivision_US"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "cash_telephoneNo_US"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "cash_email_US"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_ES"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_ES"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_ES"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_ES"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_ES"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_ES"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_ES"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_ES"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_ES"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_ES"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "BCN"));

			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));

			// creating flight number1

			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("OtherCarrier","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber", cust.data("OtherCarrier") + cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// creating flight number2

			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber2", cust.data("prop~flight_code") + cust.data("prop~flightNo"), proppath);
			String FlightNum2 = WebFunctions.getPropertyValue(proppath, "flightNumber2");
			map.put("FullFlightNo2", FlightNum2);
			map.put("FlightNo2", FlightNum2.substring(2));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);



			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT F1 ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlightDetails("OtherCarrier", "FlightNo", "StartDate");


			FLT003.enterFlightDetails("Route1", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeCDG=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			System.out.println(currtimeCDG);
			String STD=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",3);
			System.out.println(STD.split(" ")[1]);
			map.put("STDTime", STD.split(" ")[1]);
			String STA=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",10);
			System.out.println(STA.split(" ")[1]);
			map.put("STATime", STA.split(" ")[1]);
			map.put("STDDate", STD.split(" ")[0]);
			map.put("STADate", STA.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate","STADate","STDTime","STATime", "AircraftType", "");		
			cust.switchToWindow("getParent");
			cust.switchToFrame("contentFrame", "FLT003");
			FLT003.clickSave();
			cust.closeTab("FLT003", "Maintain Operational Flight");


			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT F2 ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlight("FlightNo2", "StartDate");


			FLT003.enterFlightDetails("Route2", "scheduleType", "FCTL", "Office", "flightType2");
			String currtimeCDG2=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			System.out.println(currtimeCDG2);
			String STD2=cust.timeCalculation(currtimeCDG2, "dd-MMM-yyyy HH:mm","HOUR",11);
			System.out.println(STD2.split(" ")[1]);
			map.put("STDTime2", STD2.split(" ")[1]);
			String STA2=cust.timeCalculation(currtimeCDG2, "dd-MMM-yyyy HH:mm","HOUR",13);
			System.out.println(STA2.split(" ")[1]);
			map.put("STATime2", STA2.split(" ")[1]);
			map.put("STDDate2", STD2.split(" ")[0]);
			map.put("STADate2", STA2.split(" ")[0]);
			FLT003.enterLegCapacityDetails("STDDate2","STADate2","STDTime2","STATime2", "AircraftType2", "");		
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
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");


			/*** Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			/**XFBL Message loading **/

			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMMyyyy", 0, "DAY", "Europe/Paris").toUpperCase());
			cust.createXMLMessage("MessageExcelAndSheetXFBL", "MessageParamXFBL");
			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC").split(",")[0]+";"+cust.data("SCC").split(",")[1]};
			String routing[] = { cust.data("Origin") + ";" + cust.data("Destination") };
			cust.createXFBLMessage("XFBL_2", shipment, scc, routing);
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/**** XFWB Message loading ****/
			// Create XFWB message
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			String sccs[] = { cust.data("SCC").split(",")[0], cust.data("SCC").split(",")[1] };
			cust.createXFWBMessageWithSCCs("XFWB_Transit_MultipleSCCs", sccs);
			Cgocxml.clickMessageLoader();
			Cgocxml.sendMessageCgoCXML("ICARGO");


			/** XFFM Message loading **/

			map.put("FFMDate", cust.createDateFormat("ddMMMyyyy", 0, "DAY", ""));
			map.put("FFMDate2", cust.createDateFormat("ddMMyy", 0, "DAY", ""));
			map.put("FFMDate3", cust.createDateFormat("yyyyMMdd", 0, "DAY", ""));

			String uldNo = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			//	Create XFFM message
			cust.createXFFMMessage("XFFM", shipment, scc, routing1, uld);
			Cgocxml.sendMessageCgoCXML("ICARGO");
			libr.quitBrowser();

			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);

			// Switch Role
			cust.switchRole("Transit", "FCTL", "RoleGroup");


			/**Mark Flight Movement**/
			cust.searchScreen("FLT006", "Mark Flight Movements");
			FLT006.listFlight("OtherCarrier", "FlightNo", "StartDate");
			String currtime=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("ATA", currtime);
			String currDate=cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "Europe/Paris");
			map.put("CurrDate", currDate);
			FLT006.enterFlightMovementDepartureDetail("val~00:00","CurrDate");
			FLT006.enterFlightMovementArrivalDetails(currtime,currDate);
			FLT006.clickSave();
			FLT006.closeTab("FLT006", "Mark Flight Movements");


			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");
			// Login in to HHT
			String[] hht = libr.getApplicationParams("hht");
			cust.loginHHT(hht[0], hht[1]);

			/*** HHT - BREAKDOWN ****/

			bdhht.invokeBreakdownHHTScreen();
			bdhht.enterValue("UldNum");
			map.put("Breakdown_Location", WebFunctions.getPropertyValue(toproppath, "Breakdown_Location"));
			bdhht.enterLocation("Breakdown_Location");
			bdhht.clickSaveButton();
			String ThruOptions[]={"Partial THRU","Direct THRU"};
			bdhht.verifyThruOptions(2, ThruOptions);
			bdhht.selectThruOption("Direct THRU");
			cust.clickBack("Breakdown");
			libr.quitApp();


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO	
			cust.loginTransportOrder(hht[0], hht[1]);

			to.searchShipment("UldNum");

			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("UldNum", "Breakdown_Location");

			//fetch destination location
			String destnStorageAreaLocation=to.retrieveDestnLocation("UldNum");
			map.put("destnStorageAreaLocation", destnStorageAreaLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("UldNum", "val~Open", "Breakdown_Location");
			libr.quitApp();


			/**** WHS008 -HandlingAreaSetUpScreen ****/

			cust.searchScreen("WHS008", "Handling Area Set Up");
			int verfCols [] = {3};


			//Verifying storage area destination location and zone for su
			String[] actVerfValues2= {WebFunctions.getPropertyValue(toproppath, "Breakdown_StorageAreaZone_Transit_CDG")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("destnStorageAreaLocation", verfCols, actVerfValues2);
			cust.closeTab("WHS008", "Handling Area Set Up");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			// Login in to TO
			cust.loginHHT(hht[0], hht[1]);

			to.searchShipment("UldNum");
			//completing the relocation task
			to.selectTask("destnStorageAreaLocation");
			to.confirmTaskList();
			to.clickRelocationComplete("destnStorageAreaLocation");
			libr.quitApp();


			/*****ADD004 - Build Up planning****/

			cust.searchScreen("ADD004","Buildup Planning");
			ADD004.listFlight("carrierCode","FlightNo2","StartDate");
			ADD004.verifyShipmentInLoadPlan("prop~AWBNo");
			ADD004.selectULD("prop~AWBNo");
			ADD004.clickAllocate();	
			ADD004.acceptAlertPopUp("val~This is a BUP shipment, Do you want to continue in BUP mode?");
			ADD004.clickSaveAllocation();
			ADD004.clickRelease();
			cust.closeTab("ADD004","Buildup Planning");	




			/*** WHS059 - Assign flight location ***/
			cust.searchScreen("WHS059", " Assign Flight Locations");
			WHS059.enterFlightDetails("carrierCode","FlightNo2","StartDate");
			WHS059.clickList();

			//second Segment
			WHS059.clickMoreOption(cust.data("Transit")+"-"+cust.data("Destination"),"Index");		
			WHS059.clickAssignLocation(cust.data("Index"));
			map.put("PITLocation_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG"));
			WHS059.enterAssignZoneandLocation("Zone","PITLocation_CDG");				
			WHS059.clickAssignedLocationTab();
			String currtme=cust.createDateFormatWithTimeZone("HH:mm", 0, "DAY", "Europe/Paris");
			map.put("openTime",cust.timeCalculation(currtme, "HH:mm","MINUTE",2));

			//Enter the open time 
			WHS059.enterOpenTimeForSegment("StartDate", "openTime",cust.data("Transit")+"-"+cust.data("Destination"));		
			cust.closeTab("WHS059", "Assign Flight Locations");
			cust.waitForSync(60);



			/*** WHS059 - Assign flight location ***/

			//verifying the PIT location is in open status
			cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(60);
			WHS059.enterFlightDetails("carrierCode","FlightNo2","StartDate");
			WHS059.clickList();
			WHS059.clickAssignedLocationTab();
			WHS059.verifyOpenStatusOfSegment("OPEN",cust.data("Transit")+"-"+cust.data("Destination"));
			cust.closeTab("WHS059", "Assign Flight Locations");



			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO	
			cust.loginTransportOrder(hht[0], hht[1]);

			to.searchShipment("UldNum");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("UldNum", "destnStorageAreaLocation");

			//fetch destination location
			String descPITLocationOpened=to.retrieveDestnLocation("UldNum");
			map.put("descPITLocationOpened", descPITLocationOpened);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("UldNum", "val~Open", "destnStorageAreaLocation");
			libr.quitApp();


			/**** WHS008 -HandlingAreaSetUpScreen ****/

			cust.searchScreen("WHS008", "Handling Area Set Up");


			//Verifying opened PIT destination location and zone for su
			String[] actVerfValues3= {WebFunctions.getPropertyValue(toproppath, "PITLocationZone_CDG")};
			//verifying the location displayed is in the correct Zone as per the configuration
			WHS008.verifyLocationAndCorrespondingZone("descPITLocationOpened", verfCols, actVerfValues3);
			cust.closeTab("WHS008", "Handling Area Set Up");



			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");

			// Login in to HHT
			cust.loginHHT(hht[0], hht[1]);


			/*** HHT - Build Up****/

			buhht.invokeBuildUpScreen();
			buhht.enterValue("UldNum");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code", "FlightNo2","selectCurrentDay");
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteBtn();
			buhht.clickSaveCaptureChecksheet();   
			buhht.clickTopUpNoOption();
			buhht.selectContourAndSave("Contour");
			cust.clickBack("Build Up");
			libr.quitApp();



		}catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}
