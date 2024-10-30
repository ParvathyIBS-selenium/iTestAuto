package stationcashiering;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AWBHAWBHistory_OPR276;
import screens.CaptureAWB_OPR026;
import screens.GeneratePaymentAdvice_CSH007;
import screens.ListAuditEnquiry_SHR011;
import screens.ListMessages_MSG005;
import screens.MaintainAirportScreen_SHR006;
import screens.PaymentAdviceEnquiry_CSH009;
import screens.SecurityAndScreening_OPR339;

import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

public class CSH13 extends BaseSetup {
	
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
	public MaintainAirportScreen_SHR006 SHR006;
	public ListAuditEnquiry_SHR011 SHR011;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public PaymentAdviceEnquiry_CSH009 CSH009;
	public AWBHAWBHistory_OPR276 OPR276;
	public ListMessages_MSG005 MSG005;
	public SecurityAndScreening_OPR339 OPR339;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="stationcashiering";	
	
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
		SHR006= new MaintainAirportScreen_SHR006(driver, excelreadwrite, xls_Read);
		SHR011 = new ListAuditEnquiry_SHR011(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR276 = new AWBHAWBHistory_OPR276(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR339=new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		CSH009=new PaymentAdviceEnquiry_CSH009(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) throws Exception {
		
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
			customfunctions.switchRole("Origin", "Destination", "RoleGroup");
			
		/*******Pre-Condition***********/
			 //Enable station cashiering
            customfunctions.searchScreen("SHR006", "Maintain Airport");
            SHR006.listAirport("Origin");
            SHR006.filterParameterBasedOnvalue("Parameter");
            String paramValue = SHR006.getCashieringEnabledParameterValue();
            map.put("paramVal", paramValue);
            SHR006.changeParameterValuetoY();
            SHR006.closeTab("SHR006", "SHR006 - Maintain Airport");
			
	     /**** OPR026 - Capture AWB****/
	        
			//Checking AWB is fresh or Not
	        customfunctions.searchScreen("OPR026","Capture AWB");
		    OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
		    libr.waitForSync(1);
		       
		   
		       
		    //Writing the full AWB No to property file
	        customfunctions.setPropertyValue("FullAWBNo", customfunctions.data("prop~stationCode")+"-"+customfunctions.data("prop~AWBNo"), proppath);
	        
	        //Enter details in Capture AWB screen and verify
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","prop~flight_code");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("shipperCode");
			OPR026.provideConsigneeCode("consigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickChargesAcc();
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			OPR026.saveAWB(); 
				customfunctions.closeTab("OPR026", "Capture AWB");

         /**** OPR339 - Security & Screening****/
            
	            customfunctions.searchScreen("OPR339", "Security and Screening");
	            OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
	            OPR339.clickYesButton();
	            OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
	            OPR339.saveSecurityDetails();
	            customfunctions.closeTab("OPR339", "Security & Sceening");

			
			//As Is Execute AWB
			customfunctions.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			OPR026.asIsExecuteOnly();
			
			//Generate Payment Advice Screen
			CSH007.verifyServiceCode("val~AWBI");
			CSH007.selectPaymentMode("CARD");
			CSH007.enterRemarks("val~Credit Payment");
			CSH007.clickAdd();
			CSH007.clickFinalizePayment();
			CSH007.verifyPaymentStatus("Final");
			CSH007.getPaymentAdviceNo("PaymentAdviceNo");
			CSH007.clickClose();
			OPR026.asIsExecuteVP();
			customfunctions.closeTab("OPR026", "Cap	ture AWB");
			
			
			
			// Verify if credit check box is checked in Payment advice screen
			customfunctions.searchScreen("CSH007","Generate Payment advice screen");
			CSH007.listWithPaymentAdviceNo("PaymentAdviceNo");
			CSH007.verifyCreditCheckBox();
			customfunctions.closeTab("CSH007", "Generate Payment advice screen");
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
		}
		finally
		{
			
			/*******Post-Condition***********/
			customfunctions.closeTab();
			//Disable station cashiering
			customfunctions.searchScreen("SHR006", "Maintain Airport");
			SHR006.listAirport("Origin");
			SHR006.filterParameterBasedOnvalue("Parameter");
			
			if(customfunctions.data("paramVal")!=null)
			{
				SHR006.changeStationCashieringParameterValue(customfunctions.data("paramVal"));
			}
			SHR006.closeTab("SHR006", "SHR006 - Maintain Airport");
		}

	}
	
}


