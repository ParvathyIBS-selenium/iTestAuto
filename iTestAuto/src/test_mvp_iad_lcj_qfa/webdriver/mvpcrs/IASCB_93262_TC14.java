package mvpcrs;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BuildUpHHT;
import screens.CaptureAWB_OPR026;
import screens.CaptureConsumablesHHT;
import screens.DeadloadStatement_OPR063;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.ULDTag_OPR013;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class IASCB_93262_TC14 extends BaseSetup {
	
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
	public GoodsAcceptance_OPR335 OPR335;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="mvpcrs";	
	
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
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "IASCB_9283_TC16")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}
	

	@Test(dataProvider = "IASCB_9283_TC16")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// Login to "ICARGO"
			String[] iCargo = libr.getApplicationParams("iCargo");
			driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);
			
			

			// creating flight number
			cust.createFlight("FullFlightNumber");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = cust.getPropertyValue(proppath, "flightNumber");
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
			System.out.println(FlightNum);
			
			// Switch Role
			cust.switchRole("Origin", "val~AMS", "RoleGroup");
			
			/***MESSAGE - loading ASM**/
			
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			//Load ASM message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "JMS", "", "Origin", "", "ASM_NEW");
			
			//Process ASM message
			
			MSG005.enterMsgType("ASM");
			MSG005.clickList();
			libr.waitForSync(6);
			map.put("pmkey", "NEW"+" - "+cust.data("carrierCode")+" - "+cust.data("prop~flightNo")+" - "+cust.data("FBLDate").toUpperCase());
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");


			//Checking AWB is fresh or Not--AWB 1
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			
			/****** Store XFWB CUSTOMER DETAILS IN MAP***/
			
			map.put("AgentCode", cust.getPropertyValue(custproppath, "customerId_US"));
			map.put("AgentName", cust.getPropertyValue(custproppath, "customerName_US"));
			map.put("AgentPostCode", cust.getPropertyValue(custproppath, "postCode_US"));
			map.put("AgentStreetName", cust.getPropertyValue(custproppath, "streetName_US"));
			map.put("AgentCityName", cust.getPropertyValue(custproppath, "cityName_US"));
			map.put("AgentCountryId", cust.getPropertyValue(custproppath, "countryId_US"));
			map.put("AgentCountryName", cust.getPropertyValue(custproppath, "countryName_US"));
			map.put("AgentCountrySubDiv", cust.getPropertyValue(custproppath, "countrySubdivision_US"));
			map.put("AgentPhoneNo", cust.getPropertyValue(custproppath, "telephoneNo_US"));
			map.put("AgentEmail", cust.getPropertyValue(custproppath, "email_US"));
			
			map.put("ShipperCode", cust.getPropertyValue(custproppath, "customerId_US"));
			map.put("ShipperName", cust.getPropertyValue(custproppath, "customerName_US"));
			map.put("ShipperPostCode", cust.getPropertyValue(custproppath, "postCode_US"));
			map.put("ShipperStreetName", cust.getPropertyValue(custproppath, "streetName_US"));
			map.put("ShipperCityName", cust.getPropertyValue(custproppath, "cityName_US"));
			map.put("ShipperCountryId", cust.getPropertyValue(custproppath, "countryId_US"));
			map.put("ShipperCountryName", cust.getPropertyValue(custproppath, "countryName_US"));
			map.put("ShipperCountrySubDiv", cust.getPropertyValue(custproppath, "countrySubdivision_US"));
			map.put("ShipperPhoneNo", cust.getPropertyValue(custproppath, "telephoneNo_US"));
			map.put("ShipperEmail", cust.getPropertyValue(custproppath, "email_US"));

			map.put("ConsigneeCode", cust.getPropertyValue(custproppath, "vcc_cashCustomerId_NL"));
			map.put("ConsigneeName", cust.getPropertyValue(custproppath, "vcc_cashCustomerName_NL"));
			map.put("ConsigneePostCode", cust.getPropertyValue(custproppath, "vcc_cashCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", cust.getPropertyValue(custproppath, "vcc_cashCustomerstreetName_NL"));
			map.put("ConsigneeCityName", cust.getPropertyValue(custproppath, "vcc_cashCustomercityName_NL"));
			map.put("ConsigneeCountryId", cust.getPropertyValue(custproppath, "vcc_cashCustomercountryId_NL"));
			map.put("ConsigneeCountryName", cust.getPropertyValue(custproppath, "vcc_cashCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv", cust.getPropertyValue(custproppath, "vcc_cashCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", cust.getPropertyValue(custproppath, "vcc_cashCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", cust.getPropertyValue(custproppath, "vcc_cashCustomeremail_NL"));

			map.put("OriginAirport", cust.getPropertyValue(custproppath, "IAD"));
			map.put("DestinationAirport", cust.getPropertyValue(custproppath, "AMS"));
				
			/****OPR355 - Goods Acceptance****/
            
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyPopUpAndHandle("val~AWB does not exist.Do you want to capture?","Yes");
			OPR335.clickCaptureAWB();
			OPR026.verifySCI("T1");
			
			//Capture AWB Details
			
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","prop~flight_code");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			//Provide rating details
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			//Click calculate charges button
			OPR026.clickCalcCharges();
			//Click As Is Execute button
			OPR026.saveAWB();   
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			//Capture loose acceptance details
            OPR335.looseShipmentDetails("Location", "Pieces","Weight");
            OPR335.addLooseShipment();
            OPR335.allPartsRecieved();
            OPR335.saveAcceptance();
            cust.closeTab("OPR335", "Goods Acceptance");
	
			
			
				
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
	
}



