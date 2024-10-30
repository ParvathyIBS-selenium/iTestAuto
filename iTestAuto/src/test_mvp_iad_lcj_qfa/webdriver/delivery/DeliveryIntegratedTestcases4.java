package delivery;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.BreakDownScreen_OPR004;
import screens.CaptureAWB_OPR026;
import screens.DeliverCargo_OPR064;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;

import screens.ImportManifest_OPR367;
import screens.MaintainAirportScreen_SHR006;
import screens.MaintainOperationalFlight_FLT003;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

public class DeliveryIntegratedTestcases4 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions customfunctions;
	public CaptureAWB_OPR026 OPR026;
	public ExportManifest_OPR344 OPR344;
	public MaintainOperationalFlight_FLT003 FLT003;
	public ImportManifest_OPR367 OPR367;
	public BreakDownScreen_OPR004 OPR004;
	public MaintainAirportScreen_SHR006 SHR006;
	public DeliveryDocumentation_OPR293 OPR293;
	public DeliverCargo_OPR064 OPR064;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="delivery";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		//excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		customfunctions = new CustomFunctions(driver, excelreadwrite, xls_Read);		
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR004 = new BreakDownScreen_OPR004(driver, excelreadwrite, xls_Read);
		SHR006 = new MaintainAirportScreen_SHR006(driver, excelreadwrite, xls_Read);
		OPR293 = new DeliveryDocumentation_OPR293(driver, excelreadwrite, xls_Read);
		OPR064 = new DeliverCargo_OPR064(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) throws InterruptedException, AWTException, IOException {
		
		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		
			//Login to iCargo
		
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			customfunctions.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
			customfunctions.switchRole("Origin", "Origin", "RoleGroup");
			
			 /**** FLT003 - Create flight****/
			
			 customfunctions.createFlight("FlightNo");
			 String flightNo = WebFunctions.getPropertyValue(proppath,"flightNo");
			 String flightdate1 = customfunctions.createDateFormat("dd-MMM-yyyy", 0, "DAY", "FlightDate");
			 map.put("FlightNo", flightNo);
			 map.put("StartDate", flightdate1);
	         customfunctions.setPropertyValue("FlightNo",flightNo,proppath); 
	         customfunctions.setPropertyValue("flightNumber", customfunctions.data("prop~flight_code")+customfunctions.data("prop~flightNo"), proppath);
	         excelRead.writeDataInExcel(map, path1, sheetName, testName);
	         libr.waitForSync(1);
	         customfunctions.searchScreen("FLT003","Maintain Operational Flight");
	         FLT003.listNewFlight("prop~flightNo", "StartDate","FlightNo");
	         FLT003.enterFlightDetails("Route", "scheduleType", "Origin", "FCTL", "flightType");
	         FLT003.enterLegCapacityDetails("departureTime","arrivalTime", "aircraftType","Configuration_name");
	         FLT003.save("FLT003");
	         customfunctions.closeTab("FLT003", "Maintain Operational Flight");
            
             /*********OPR367 - Create ULD and AWB and save the details*********/
			 //Checking AWB is fresh or Not
	          customfunctions.searchScreen("OPR026","Capture AWB");
		      OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
		      libr.waitForSync(1);
		       
		      String awbNo = customfunctions.data("prop~AWBNo");
		      map.put("AWBNo",awbNo);
		      
		      //Generate a new uld number and write in excel file
              String uldNum=OPR344.create_uld_number("UldType", "carrierCode");
              map.put("UldNum", uldNum);
                           
		      excelRead.writeDataInExcel(map, path1, sheetName, testName);
			  
		      //Switch role to destination stations
			  customfunctions.switchRole("Destination", "Origin", "RoleGroup");
			
			  //Add uld and awb number in OPR367 screen
	          customfunctions.searchScreen("OPR367", "Import Manifest");
	          OPR367.listFlight("prop~flight_code","prop~flightNo", "StartDate");
	          //Add new ULD and awb
	          OPR367.addNewULD("UldNum","CarrierNumericCode", "AWBNo", "Pieces", "Weight", "Origin", "Destination", "Pieces", "Weight");	          
	          OPR367.SaveDetails();
	          customfunctions.closeTab("OPR367", "Import Manifest");
	          
	          /*********OPR004 - verify the breakdown details***********************/
		         
		      customfunctions.searchScreen("OPR004", "Breakdown");
		      OPR004.enterULDnumber(customfunctions.data("UldNum"));
		      OPR004.listFlight("carrierCode", "FlightNo", "StartDate");
		      String[] pieces = {"5","5"};
		      String[]weight = {"50","50"};
		      String[]location = {"01","02"};
		      OPR004.splitBreakdown("2", pieces, weight, location);
		      OPR004.saveOPR004();
		      		  	
		      /**********SHR006 - Maintain Airport**************/			
	            //Enable station cashiering
	            customfunctions.searchScreen("SHR006", "Maintain Airport");
	            SHR006.listAirport("Destination");
	            SHR006.filterParameterBasedOnvalue("Parameter");
	            String paramValue = SHR006.getCashieringEnabledParameterValue();
	            map.put("paramVal", paramValue);
	            SHR006.changeParameterValuetoY();
	            SHR006.closeTab("SHR006", "SHR006 - Maintain Airport");
				
				/**********OPR293-Delivery Documentation**********/
				
				//Deliver partial pieces and weight
				customfunctions.searchScreen("OPR293", "Delivery Documentation");
				customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Delivery Documentation");
				OPR293.selectAllAWBs();
				OPR293.enterCaptureHandOverDetails();
				OPR293.enterCustomer("CustomerName");
				OPR293.changeDNpcs_wt("5", "50");
				OPR293.generateDeliveryIDWithPopUps("Remarks");
				OPR293.verifyDNStatus("Paid");
				customfunctions.closeTab("OPR293", "Delivery Documentation");
			
				/**********OPR293-Deliver Cargo*********************/
				//Deliver Cargo for partial pieces and weight
				customfunctions.searchScreen("OPR064", "Deliver Cargo");
				customfunctions.listAWB("AWBNo", "prop~CarrierNumericCode", "Deliver Cargo");
				OPR064.enterDeliveredTo("Test");
				OPR064.clickSave();
				customfunctions.closeTab("OPR064", "Deliver Cargo");
				
				
				
		}		
		catch(Exception e)
		{	
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
		}
		
		finally
		{
			customfunctions.closeTab();
			//Disable station cashiering
			customfunctions.searchScreen("SHR006", "Maintain Airport");
			SHR006.listAirport("Destination");
			SHR006.filterParameterBasedOnvalue("Parameter");
			if(customfunctions.data("paramVal")!=null)
			{
				SHR006.changeStationCashieringParameterValue(customfunctions.data("paramVal"));
			}
			SHR006.closeTab("SHR006", "SHR006 - Maintain Airport");
		}

	}
}

