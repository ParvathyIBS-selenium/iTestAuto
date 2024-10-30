package buildup;

//TC_07_Autotrigger of relocation task at transit station for mixed shipment type


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
import screens.BuildUpHHT;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.TransportOrderListing;
import screens.WarehouseSetUpEnquiry_WHS013;
import screens.WarehouseShipmentEnquiry_WHS011;
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
	public GoodsAcceptance_OPR335 OPR335;
	public WarehouseShipmentEnquiry_WHS011 WHS011;
	public WarehouseSetUpEnquiry_WHS013 WHS013;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\Buildup.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";
	String sheetName = "Buildup_FT";

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
		WHS011=new WarehouseShipmentEnquiry_WHS011(driver,excelreadwrite,xls_Read); 
		WHS013=new WarehouseSetUpEnquiry_WHS013(driver,excelreadwrite,xls_Read); 

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

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("TransitAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "BCN"));

			map.put("TransitCountry", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));

			// creating flight number1

			cust.createFlight("FullFlightNumber");
			// Maintain Flight Screen (FLT005) . Taking fresh flight
			cust.searchScreen("FLT005", "FLT005 - Maintain Flight Schedule");
			FLT005.listNewFlight("carrierCode","prop~flightNo", startDate, startDate,"FullFlightNumber");
			cust.closeTab("FLT005", "Maintain Schedule");

			cust.setPropertyValue("flightNumber", cust.data("OtherCarrier") + cust.data("prop~flightNo"), proppath);
			String FlightNum = WebFunctions.getPropertyValue(proppath, "flightNumber");
			map.put("FullFlightNo", FlightNum);
			map.put("FlightNo", FlightNum.substring(2));

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

			/******* FLT003 - MAINTAIN OPERATIONAL FLIGHT F1 ******/

			cust.searchScreen("FLT003", "FLT003 - Maintain Operational Flight");
			FLT003.listNewFlightDetails("carrierCode", "FlightNo", "StartDate");


			FLT003.enterFlightDetails("Route1", "scheduleType", "FCTL", "Office", "flightType");
			String currtimeCDG=cust.createDateFormatWithTimeZone("dd-MMM-yyyy HH:mm", 0, "DAY", "Europe/Paris");
			System.out.println(currtimeCDG);
			String STD=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",3);
			System.out.println(STD.split(" ")[1]);
			map.put("STDTime", STD.split(" ")[1]);
			String STA=cust.timeCalculation(currtimeCDG, "dd-MMM-yyyy HH:mm","HOUR",5);
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
			String STD2=cust.timeCalculation(currtimeCDG2, "dd-MMM-yyyy HH:mm","HOUR",6);
			System.out.println(STD2.split(" ")[1]);
			map.put("STDTime2", STD2.split(" ")[1]);
			String STA2=cust.timeCalculation(currtimeCDG2, "dd-MMM-yyyy HH:mm","HOUR",9);
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
			libr.quitBrowser();


			// Relaunch browser
			driver = libr.relaunchBrowser("chrome");


			/*** Login to cgocxml **********/

			String[] cgocxml = libr.getApplicationParams("cgocxml");
			driver.get(cgocxml[0]); // Enters URL
			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);


			String shipment[] = { libr.data("FullAWBNo") + ";" + libr.data("Pieces") + ";" + libr.data("Weight") + ";"
					+ libr.data("Volume") + ";" + libr.data("ShipmentDesc") };
			String scc[] = { cust.data("SCC").split(",")[0]+";"+cust.data("SCC").split(",")[1]};



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
			map.put("ULDNo", cust.data("UldNum").replaceAll("[^0-9]", ""));

			cust.createXMLMessage("MessageExcelAndSheetXFFM", "MessageParamXFFM");

			String routing1[] = { cust.data("Origin") + ";" + cust.data("OriginAirport") + ";" + cust.data("Destination")
			+ ";" + cust.data("DestinationAirport") };
			String uld[] = { cust.data("UldType") + ";" + cust.data("ULDNo") + ";" + cust.data("carrierCode") };
			//				Create XFFM message
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


			map.put("Breakdown_Location", WebFunctions.getPropertyValue(toproppath, "Breakdown_Location"));
			/*** HHT - BREAKDOWN****/
			bdhht.invokeBreakdownHHTScreen();
			bdhht.enterValue("UldNum");
			bdhht.selectAwb("FullAWBNo");
			bdhht.enterLocation("Breakdown_Location");
			bdhht.selectMultipleSCC(scc);
			bdhht.addPcs("Pieces");	
			bdhht.clickSave();
			cust.clickBack("Breakdown");

			//Marking BreakdownComplete
			bdhht.enterValue("UldNum");
			bdhht.clickMoreOptions();
			bdhht.clickBreakdownCompleteBtn();
			cust.clickBack("Breakdown");
			libr.quitApp();

			map.put("AWB", cust.data("CarrierNumericCode") + cust.data("AWBNo"));
			map.put("SU", cust.data("AWB")+"001");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO	
			cust.loginTransportOrder(hht[0], hht[1]);

			to.searchShipment("SU");	

			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU", "Breakdown_Location");

			//fetch destination location
			String destnStorageAreaLocation=to.retrieveDestnLocation("SU");
			map.put("destnStorageAreaLocation", destnStorageAreaLocation);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "Breakdown_Location");
			libr.quitApp();


			/**** WHS013 -Warehouse Setup Enquiry ****/

			//verifying zone of the destination location
			cust.searchScreen("WHS013", "Warehouse Setup Enquiry");
			WHS013.enterLocation("destnStorageAreaLocation");
			WHS013.clickList();
			String StorageAreaZone=WHS013.getZoneCode();
			map.put("Breakdown_StorageAreaZone_Transit_CDG", WebFunctions.getPropertyValue(toproppath, "Breakdown_StorageAreaZone_Transit_CDG"));
			WHS013.verifyZone(cust.data("Breakdown_StorageAreaZone_Transit_CDG"),StorageAreaZone);
			cust.closeTab("WHS013", "Warehouse Setup Enquiry");


			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");		

			// Login in to TO
			cust.loginHHT(hht[0], hht[1]);

			to.searchShipment("SU");
			//completing the relocation task
			to.selectTask("destnStorageAreaLocation");
			to.confirmTaskList();
			to.clickRelocationComplete("destnStorageAreaLocation");
			libr.quitApp();


			/*****ADD004 - Build Up planning****/

			cust.searchScreen("ADD004","Buildup Planning");
			ADD004.listFlight("carrierCode","FlightNo2","StartDate");
			ADD004.clickLyinglist();
			ADD004.clickFilterLyingList();
			ADD004.filterByShipment("CarrierNumericCode", "AWBNo");
			ADD004.clickApplyInLyingListFilter();
			ADD004.verifyDetailsInLyingList("AWBNo");
			ADD004.selectULD("AWBNo");
			ADD004.clickAllocate();	
			ADD004.clickSaveAllocation();
			ADD004.clickRelease();
			cust.closeTab("ADD004","Buildup Planning");	




			/*** WHS059 - Assign flight location ***/
			/*cust.searchScreen("WHS059", " Assign Flight Locations");
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
			cust.waitForSync(60);*/

			/*** WHS059 - Assign flight location ***/

			//verifying the PIT location is in open status
			/*cust.searchScreen("WHS059", " Assign Flight Locations");
			libr.waitForSync(60);
			WHS059.enterFlightDetails("carrierCode","FlightNo2","StartDate");
			WHS059.clickList();
			WHS059.clickAssignedLocationTab();
			WHS059.verifyOpenStatusOfSegment("OPEN",cust.data("Transit")+"-"+cust.data("Destination"));
			cust.closeTab("WHS059", "Assign Flight Locations");*/



			/***Launch emulator - Transport Order**/
			libr.launchTransportOrder("TO-app");
			//Login in to TO	
			cust.loginTransportOrder(hht[0], hht[1]);

			to.searchShipment("SU");


			//fetch and verify the src location 
			to.retrieveAndVerifyOriginLocation("SU", "destnStorageAreaLocation");

			//fetch destination location
			String descPITLocationOpened=to.retrieveDestnLocation("SU");
			map.put("descPITLocationOpened", descPITLocationOpened);

			//verifying the generated TO status in the TO app
			to.verifyShipmentDetails("SU", "val~Open", "destnStorageAreaLocation");
			to.verifySourcelocandDestLocIsDifferent("destnStorageAreaLocation","descPITLocationOpened");
			libr.quitApp();


			/**** WHS013 -Warehouse Setup Enquiry ****/

			//verifying zone of the destination location
			cust.searchScreen("WHS013", "Warehouse Setup Enquiry");
			WHS013.enterLocation("descPITLocationOpened");
			WHS013.clickList();
			String PITLocZone_CDG=WHS013.getZoneCode();
			map.put("PITLocationZone_CDG", WebFunctions.getPropertyValue(toproppath, "PITLocationZone_CDG"));
			WHS013.verifyZone(cust.data("PITLocationZone_CDG"),PITLocZone_CDG);
			cust.closeTab("WHS013", "Warehouse Setup Enquiry");



			/*** Launch emulator - hht **/
			libr.launchApp("hht-app-release");

			// Login in to HHT
			cust.loginHHT(hht[0], hht[1]);


			/*** HHT - Build Up****/

			buhht.invokeBuildUpScreen();
			String uldNo1 = cust.create_uld_number("UldType", "carrierCode");
			map.put("UldNum1", uldNo1);
			map.put("ULDNo1", cust.data("UldNum").replaceAll("[^0-9]", ""));
			buhht.enterValue("UldNum1");
			buhht.updateFlightDetailsWithOutPopUp("prop~flight_code", "FlightNo2","selectCurrentDay");
			map.put("BuildupLoc", WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG"));
			buhht.enterBuildupLocation("BuildupLoc");
			buhht.clickMoreOptions();
			buhht.clickBuildUpCompleteBtn();
			buhht.clickSaveCaptureChecksheet();   
			buhht.clickTopUpNoOption();
			buhht.selectContourAndSave("Contour");
			cust.clickBack("Build Up");
			libr.quitApp();



		}catch (Exception e) {
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