package mvp_reg_acceptance;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.CaptureAWB_OPR026;
import screens.CaptureHAWB_OPR029;
import screens.Cgomon;
import screens.GeneratePaymentAdvice_CSH007;
import screens.ListMessages_MSG005;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

//Data capture of non secured shipments with paper CNSL AWB for a cash/paycargo customer
public class PaperDCNSCHAWB_4_2 extends BaseSetup {
	
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
	public GeneratePaymentAdvice_CSH007 CSH007;
	public Cgomon Cgomon;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="mvp_reg_acceptance";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR029 = new CaptureHAWB_OPR029(driver, excelreadwrite, xls_Read);
		CSH007=new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
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
		
			//Login to iCargo		
			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MM-YYYY", 2, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			
			/****** Store XFWB CUSTOMER DETAILS IN MAP***/
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "cash_customerId_US"));
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR"));
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));		
			
			//Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");
					 
            /**** OPR026 - Capture AWB****/
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			//Enter shipment details
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","prop~flight_code");       	
			OPR026.selectSCI("SCI");
			OPR026.enterSCC(cust.data("SCC"));
			OPR026.enterAndValidateAgentCode("ShipperCode");
			OPR026.enterAndValidateShipperCode("ShipperCode");
			OPR026.enterAndValidateConsigneeCode("ConsigneeCode");
			OPR026.clickOverrideCertifications();
			OPR026.enterShipmentDetailsAndValidateCommodityCode("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			OPR026.clickSave();
			cust.handleAlert("Accept", "OPR026");
			cust.closeTab("OPR026", "Capture AWB");
			
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.clickHAWBWithoutClickingOnConsole("OPR026");
			//Click 'Add/Update' HAWB button
			OPR029.clickAddUpdateHAWBBtn();
			//Capture HAWB details
			OPR026.addHAWBDetailsAndValidateShipperAndConsignee("HAWB", "ShipperCode", "ConsigneeCode", "Origin", "Destination", "Pieces", "Weight");
			OPR029.clickHAWBSaveBtn();
			OPR026.close("OPR029");
			cust.waitForSync(3);
			OPR026.handleShipmentStatusPopUp();
			//Click HAWB Doc Finalized checkbox
			OPR026.clickHAWBDocFinalized();
			OPR026.clickChargesAcc();
			//Provide rating details
			OPR026.provideRatingDetails1("rateClass","IATARate");
			OPR026.storeOtherChargesValue("OtherCharges2","OCValue2");
			//Click calculate charges button
			OPR026.clickCalcCharges();
			
			//Store charge code in map and compare
			HashMap<String,String> hm = OPR026.checkAndStoreOtherChargesValue();
			String expValues[]={cust.data("OtherCharges")+"="+cust.data("OCValue"),cust.data("OtherCharges2")+"="+cust.data("OCValue2")};
			cust.compareMaps(hm, expValues,"OPR026","Other Charges");
			
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");
			
			//As Is Execute AWB
            cust.searchScreen("OPR026","Capture AWB");
            OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
            
			//Click As Is Execute button
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
					
			/** CHECKING XFWB TRIGGERED FOR AWB **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFWB=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination");
			int verfColsXFWB[]={9};
			String[] actVerfValuesXFWB={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFWB, actVerfValuesXFWB, pmKeyXFWB,"val~XFWB",true);
			libr.waitForSync(1); 

			/*** VERIFY THE MESSAGE CONTENTS***/
			map.put("pmkey", pmKeyXFWB);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			
			/**Rate lines**/
			String curr=cust.data("Currency");
			
			msgContents.add("val~<ApplicableLogisticsAllowanceCharge>"+"\n"+
         "<ID>"+cust.data("OtherCharges")+"</ID>"+
    	     "\n"+"<PrepaidIndicator>true</PrepaidIndicator>"+
         "\n"+"<PartyTypeCode>C</PartyTypeCode>" +
         "\n"+ "<ActualAmount currencyID="+curr+">"+cust.data("OCValue")+"</ActualAmount>" +
         "\n"+"</ApplicableLogisticsAllowanceCharge>");
			
			msgContents.add("val~<ApplicableLogisticsAllowanceCharge>"+"\n"+
			         "<ID>"+cust.data("OtherCharges2")+"</ID>"+
			    	     "\n"+"<PrepaidIndicator>true</PrepaidIndicator>"+
			         "\n"+"<PartyTypeCode>C</PartyTypeCode>" +
			         "\n"+ "<ActualAmount currencyID="+curr+">"+cust.data("OCValue2")+"</ActualAmount>" +
			         "\n"+"</ApplicableLogisticsAllowanceCharge>");
				
			//Verify message contents
			MSG005.verifyMessageContent(msgContents,"XFWB");
			MSG005.closeView();
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
	    	map.put("awbNumber", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
	    	Cgomon.enterFromandToDates(cust.createDateFormat("dd-MM-YYYY", -1, "DAY", ""), cust.createDateFormat("dd-MM-YYYY", 1, "DAY", ""));
			Cgomon.enterAWB("awbNumber");
			Cgomon.enterMessageType("XFWB");
			Cgomon.enterChannel("ICARGO","Incoming");
			Cgomon.clickSearch();
			Cgomon.verifyMessageStatus("awbNumber", "Incoming XFWB", "ICARGO");			
			
			//Verifying Outbound Message
	    	Cgomon.clickOutboundMessage();
	    	map.put("awbNumber", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
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

