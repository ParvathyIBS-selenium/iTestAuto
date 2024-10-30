package mvp_reg_acceptance_CDG;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.CaptureHAWB_OPR029;
import screens.Cgocxml;
import screens.Cgomon;
import screens.ListMessages_MSG005;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
//Data capture of paper CNSL AWB for an account customer for local export of loose shipment which consignee is in China
public class AWBDCCDG8_009 extends BaseSetup {
	
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
	public CaptureHAWB_OPR029 OPR029;
	public Cgomon Cgomon;
	public Cgocxml Cgocxml; 
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="mvp_reg_acceptance";	
	
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
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR029 = new CaptureHAWB_OPR029(driver, excelreadwrite, xls_Read);
		Cgocxml=new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon=new Cgomon(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			WebFunctions.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		
			

			/***Storing Values to Map***/
			
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "countryId2_CN"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
		
		
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "customerId2_CN"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "telephoneNo2_CN"));
		
			// Login to iCargo

			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
         	cust.switchRole("Origin", "FCTL", "RoleGroup");
		 
         	 /**** OPR026 - Capture AWB****/
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
				libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
			//Mark AWB as Console
			OPR026.clickConsoleButton();
			//Enter shipment details
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","CarrierNumericCode");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("ShipperCode");
			OPR026.enterShipperPhoneNo("ShipperPhoneNo");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterConsigneePhoneNo("ConsigneePhoneNo");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickSave();
			OPR026.clickYesButton();
			//Click 'Add/Update' HAWB button
			OPR029.clickAddUpdateHAWBBtn();
			//Capture HAWB details
			OPR026.addHAWBDetails("HAWB", "ShipperCode", "ConsigneeCode", "Origin", "Destination", "Pieces", "Weight");
			OPR029.clickHAWBSaveBtn();
			OPR026.close("OPR029");
			cust.waitForSync(3);
			OPR026.handleShipmentStatusPopUp();
			//Click HAWB Doc Finalized checkbox
			OPR026.clickHAWBDocFinalized();
			OPR026.clickChargesAcc();
			//Provide rating details
			OPR026.provideRatingDetails1("rateClass","IATARate");
			//Click calculate charges button
			OPR026.clickCalcCharges();
			//Enter OCI details
			
			String[] serialnumber={"1","1.1","1.2","1.3","2","3","4","5","6","7"};
			String[] countryCode={cust.data("ShipperCountryId"),"","","",cust.data("ShipperCountryId"),cust.data("ShipperCountryId"),cust.data("ShipperCountryId"),cust.data("ConsigneeCountryId"),cust.data("ConsigneeCountryId"),cust.data("ConsigneeCountryId")};
			String[] InformationID={"ISS","","","","SHP","SHP","SHP","CNE","CNE","CNE"};
			String[] customsInfoID={"RA","ED","SM","SD","T","CP","CT","T","CP","CT"};
			String[] suplCustomsInfo={"FR RA 05009-11 0215","0521","AOM-EDD","PARAM600915","EUROPEAN VAT NUMBERFR77769800202","LAUT PAULINE","33247323333","USCI 91110105X000624120","FU LUCIA","00861057407237"};
			OPR026.enterOCIDetails(serialnumber,countryCode,InformationID,customsInfoID,suplCustomsInfo);
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");
			
			
		
            //As Is Execute AWB
            cust.searchScreen("OPR026","Capture AWB");
            OPR026.listAWB("prop~AWBNo", "CarrierNumericCode");
            //Click As Is Execute button
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			//Switch role
			cust.switchRole("FCTL", "FCTL", "RoleGroup");
			
			/*******MSG005 - List Messages******/
			//Verify XFWB message is triggered or not
			cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.enterMsgType("XFWB");
            MSG005.clickReference();
            MSG005.enterReferenceValue("FWB", "FlightNo", "prop~AWBNo");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
            MSG005.verifyMessageTriggered("prop~AWBNo", "XFWB");
            MSG005.closeTab("MSG005", "MSG005 - List Messages");
		
            /*******MSG005 - List Messages******/
			//Verify XFZB message is triggered or not
			cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.enterMsgType("XFZB");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
            MSG005.verifyMessageTriggered("prop~AWBNo", "XFZB");
            MSG005.closeTab("MSG005", "MSG005 - List Messages");

            libr.quitBrowser();
			
          //Relaunch browser
	        driver=libr.relaunchBrowser("chrome");
		
	        //Login to "CGOMON"
	    	String[] cgomon = libr.getApplicationParams("cgomon");
	    	driver.get(cgomon[0]); // Enters URL
	    	cust.loginToCgomon(cgomon[1], cgomon[2]);
	    	
	    	//Verifying Inbound Message
	    	Cgomon.clickInboundMessage();
	    	map.put("awbNumber", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
	    	Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFWB", "ICARGO");
			
			
			//Verifying Outbound Message
	    	Cgomon.clickOutboundMessage();
	    	map.put("awbNumber", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
	    	Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("PELICAN","Outgoing");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Outgoing XFWB", "PELICAN");
			            
		  

		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

