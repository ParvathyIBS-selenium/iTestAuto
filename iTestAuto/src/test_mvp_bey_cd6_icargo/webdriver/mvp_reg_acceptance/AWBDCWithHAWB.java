package mvp_reg_acceptance;
/**  Automatic CC,CG charge code population based on manually captured House and XFZB received **/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
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
import screens.CaptureHAWB_OPR029;
import screens.ListMessages_MSG005;

public class AWBDCWithHAWB extends BaseSetup{

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

	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="mvp_reg_acceptance";	

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR029 = new CaptureHAWB_OPR029(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		
	}

	@DataProvider(name = "AWBDCWithHAWB")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "AWBDCWithHAWB")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map=map;		
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
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/************************AWB 1- CC Verification	****************************************/
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_LB"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_LB"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_LB"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_LB"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_LB"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_LB"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_LB"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_LB"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_LB"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_LB"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "cashCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "cashCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cashCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryId_FR"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "cashCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "cashCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "cashCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "cashCustomeremail_FR"));	

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "BEY"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			//Paper Capture - OPR026
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			//Enter shipment details
			OPR026.updateOrigin("Origin");
			map.put("Destination", cust.data("Destination"));
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination", "carrierCode");
			OPR026.selectSCI("SCI");
			OPR026.enterSCC(cust.data("SCC"));
			OPR026.enterAgentCode("ShipperCode");
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "ShipmentDesc");
			OPR026.saveAWB();
			cust.closeTab("OPR026", "Capture AWB");

			//Paper capture - HAWB
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
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

			//Click calculate charges button		
			OPR026.clickCalcCharges();		
			//Store charge code in map and compare
			HashMap<String,String> hm = OPR026.checkAndStoreOtherChargesValue();
			String expValues[]={cust.data("OtherCharges")+"="+cust.data("OCValue"),cust.data("OtherCharges2").split(",")[0]+"="+cust.data("OCValue2").split(",")[0]};
			cust.compareMaps(hm, expValues,"OPR026","Other Charges");		

			//As Is Execute AWB
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/** CHECKING XFWB TRIGGERED FOR AWB **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFWB=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination");
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
					"<ID>"+cust.data("OtherCharges2").split(",")[0]+"</ID>"+
					"\n"+"<PrepaidIndicator>true</PrepaidIndicator>"+
					"\n"+"<PartyTypeCode>C</PartyTypeCode>" +
					"\n"+ "<ActualAmount currencyID="+curr+">"+cust.data("OCValue2").split(",")[0]+"</ActualAmount>" +
					"\n"+"</ApplicableLogisticsAllowanceCharge>");

			//Verify message contents
			MSG005.verifyMessageContent(msgContents,"XFWB");
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");	


//			/************************AWB 2- CG Verification	****************************************/										
//			//Checking AWB is fresh or Not
//			cust.searchScreen("OPR026","Capture AWB");
//			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
//			libr.waitForSync(1);
//
//			//Writing the full AWB No
//			cust.setPropertyValue("FullAWBNo2", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
//			cust.setPropertyValue("AWBNo2", cust.data("prop~AWBNo"), proppath);
//			map.put("FullAWBNo2", cust.data("prop~FullAWBNo2"));
//			map.put("AWBNo2",cust.data("prop~AWBNo2"));
//			excelRead.writeDataInExcel(map, path1, sheetName, testName);
//
//			//Paper Capture - OPR026
//			cust.searchScreen("OPR026","Capture AWB");
//			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
//			//Enter shipment details
//			OPR026.updateOrigin("Origin");
//			OPR026.updateDestination("Destination");
//			OPR026.enterRouting("Destination", "carrierCode");
//			OPR026.selectSCI("SCI");
//			OPR026.enterSCC(cust.data("SCC"));
//			OPR026.enterAgentCode("ShipperCode");
//			OPR026.provideShipperCode("ShipperCode");
//			OPR026.provideConsigneeCode("ConsigneeCode");
//			OPR026.clickOverrideCertifications();
//			OPR026.enterShipmentDetails("Pieces", "Weight", "Volume", "CommodityCode", "ShipmentDesc");
//			OPR026.saveAWB();
//			cust.closeTab("OPR026", "Capture AWB");
//			
//			/*** XFZB LOADING***/
//			// Create XFZB message
//			cust.createXMLMessage("MessageExcelAndSheetXFZB", "MessageParamXFZB");
//			cust.searchScreen("MSG005", "MSG005 - List Messages");
//			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFZB", true);
//			cust.closeTab("MSG005", "MSG005 - List Messages");
//            //OPR026
//			cust.searchScreen("OPR026","Capture AWB");
//			OPR026.listAWB("AWBNo2", "CarrierNumericCode");
//
//			//Click HAWB Doc Finalized checkbox
//			OPR026.clickHAWBDocFinalized();
//			OPR026.clickChargesAcc();
//			//Provide rating details
//			OPR026.provideRatingDetails1("rateClass","IATARate");
//			OPR026.clickCalcCharges();
//
//			//Store charge code in map and compare
//			HashMap<String,String> hm1 = OPR026.checkAndStoreOtherChargesValue();
//			String expValues1[]={cust.data("OtherCharges")+"="+cust.data("OCValue"),cust.data("OtherCharges2").split(",")[1]+"="+cust.data("OCValue2").split(",")[1]};
//			cust.compareMaps(hm1, expValues1,"OPR026","Other Charges");		
//
//			//Click As Is Execute button
//			OPR026.asIsExecute();
//			cust.closeTab("OPR026", "Capture AWB");
//
//			/** CHECKING XFWB TRIGGERED FOR AWB 2 **/
//			cust.searchScreen("MSG005", "MSG005 - List Messages");
//			MSG005.enterMsgType("XFWB");
//			MSG005.selectStatus("Sent");
//			MSG005.clickList();
//			String pmKeyXFWB1=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo2")+" - "+cust.data("Origin")+" - "+cust.data("Destination");
//			MSG005.verifyMessageDetails(verfColsXFWB, actVerfValuesXFWB, pmKeyXFWB1,"val~XFWB",true);
//			libr.waitForSync(1); 
//
//			/*** VERIFY THE MESSAGE CONTENTS***/
//			map.put("pmkey", pmKeyXFWB1);
//			MSG005.clickCheckBox("pmkey");
//			MSG005.clickView();
//			List <String> msgContents1=new ArrayList<String>();
//
//			msgContents1.add("val~<ApplicableLogisticsAllowanceCharge>"+"\n"+
//					"<ID>"+cust.data("OtherCharges")+"</ID>"+
//					"\n"+"<PrepaidIndicator>true</PrepaidIndicator>"+
//					"\n"+"<PartyTypeCode>C</PartyTypeCode>" +
//					"\n"+ "<ActualAmount currencyID="+curr+">"+cust.data("OCValue")+"</ActualAmount>" +
//					"\n"+"</ApplicableLogisticsAllowanceCharge>");
//
//			msgContents1.add("val~<ApplicableLogisticsAllowanceCharge>"+"\n"+
//					"<ID>"+cust.data("OtherCharges2").split(",")[1]+"</ID>"+
//					"\n"+"<PrepaidIndicator>true</PrepaidIndicator>"+
//					"\n"+"<PartyTypeCode>C</PartyTypeCode>" +
//					"\n"+ "<ActualAmount currencyID="+curr+">"+cust.data("OCValue2").split(",")[1]+"</ActualAmount>" +
//					"\n"+"</ApplicableLogisticsAllowanceCharge>");		
//
//			//Verify message contents
//			MSG005.verifyMessageContent(msgContents1,"XFWB");
//			MSG005.closeView();
//			MSG005.closeTab("MSG005", "MSG005 - List Messages");			
//			libr.quitBrowser();

     }
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}


