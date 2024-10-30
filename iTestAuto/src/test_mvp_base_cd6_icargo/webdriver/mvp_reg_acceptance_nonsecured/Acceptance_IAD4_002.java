package mvp_reg_acceptance_nonsecured;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AWBClearance_OPR023;
import screens.CaptureAWB_OPR026;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.SecurityAndScreening_OPR339;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

/**
 * ULD complete acceptance,AWB data capture is done and screening is not done""
 **/
public class Acceptance_IAD4_002 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public GoodsAcceptance_OPR335 OPR335;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public GeneratePaymentAdvice_CSH007 CSH007;
	public SecurityAndScreening_OPR339 OPR339;
	public MaintainFlightSchedule_FLT005 FLT005;
	public AWBClearance_OPR023 OPR023;
	
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
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		FLT005 = new MaintainFlightSchedule_FLT005(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "Acceptance_IAD4_002")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "Acceptance_IAD4_002")
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

			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");

			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");

			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

			/***Storing Values to Map***/			
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_PL"));
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_PL"));
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_CZ"));
			
			//Regulated agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code_RA"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId_RA"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_RA"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry_RA"));
			
			//Known Consignor details
			map.put("KCCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code_KC"));
			map.put("KCCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId_KC"));
			map.put("KCAgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_KC"));
			map.put("KCExpiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry_KC"));

//			//Checking AWB is fresh or Not
//			cust.searchScreen("OPR026","Capture AWB");
//			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
//			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);

//			/**** OPR026 - Capture AWB****/	
//			cust.searchScreen("OPR026","Capture AWB");
//			OPR026.listAWB("AWBNo", "CarrierNumericCode");
//			//Enter shipment details			
//			OPR026.updateOrigin("Origin");
//			OPR026.updateDestination("Destination");
//			OPR026.enterRouting("Destination","carrierCode");       
//			OPR026.selectSCI("SCI");
//			OPR026.enterAgentCode("AgentCode");    
//			OPR026.provideShipperCode("ShipperCode");
//			OPR026.provideConsigneeCode("ConsigneeCode");
//			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
//			OPR026.clickChargesAcc();
//			//Provide rating details
//			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
//			OPR026.saveAWB(); 
//			cust.closeTab("OPR026", "Capture AWB");			
//			
//			/*********   OPR023 - Remove Compliance Block   ******/
//			cust.searchScreen("OPR023","AWB CLearance");
//			OPR023.listAWB("CarrierNumericCode", "AWBNo");
//			OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Compliance Block removed");  
//		    OPR023.closeTab("OPR023", "AWB Clearance"); 

//			/**** OPR335 -Goods Acceptance****/
//			cust.searchScreen("OPR335", "Goods Acceptance");
//			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
//			//create uld number
//			String uldNo=OPR335.create_uld_number("UldType", "carrierCode");
//			map.put("UldNum", uldNo);
//			excelRead.writeDataInExcel(map, path1, sheetName, testName);
//			libr.waitForSync(6);
//			OPR335.verifyAWBDetails("Pieces", "Weight","Volume","CommodityCode");
//			OPR335.uldShipmentDetails("Pieces","Weight", "Location", "UldNum","Contour");
//			OPR335.selectContour("Contour");
//			OPR335.addULDDetails();
//			OPR335.allPartsRecieved();
//			OPR335.saveAcceptanceWithBlockExists();
//			cust.switchToFrame("contentFrame","OPR335");
//			OPR335.verificationOfNotRFCStatus();
//			cust.closeTab("OPR335", "Goods Acceptance");
//		
//			/*******Verify FSU-FOH message in MSG005******/			
//			cust.searchScreen("MSG005", "MSG005 - List Messages");
//			MSG005.enterMsgType("XFSU");
//			MSG005.selectMsgSubType("Freight On Hand");
//			MSG005.selectStatus("Sent");
//			MSG005.clickList();
//			String pmKeyFSU=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
//			int verfColsFSU[]={9};
//			String[] actVerfValuesFSU={"Sent"};
//			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU",true);
//			libr.waitForSync(1);
//			MSG005.closeTab("MSG005", "MSG005 - List Messages");
//			
			//Screening from OPR335
			/**Goods Acceptance**/
			cust.searchScreen("OPR335", "Goods Acceptance");
			OPR335.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.clicksecurityAndScreening();
			cust.switchToFrame("frameName", "if11");
			//RA Details
			OPR339.addAgentDetails("AgentType","AgentCountryId","RegulatedAgentCode","Expiry","OPR335","if11");
			//KC Details
			OPR339.addAgentDetails("KCAgentType","KCCountryId","KCCode","KCExpiry","OPR335","if11");
			OPR339.checkGivenSecurityStatusAccepted();	
			OPR339.OkButtonAfterScreeningSave();
			cust.switchToFrame("contentFrame", "OPR335");
			OPR335.clickSave();
			OPR335.verifyAcceptanceFinalized("finalised",false);
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");
			OPR339.verifySecurityDataReviewedIsTicked();
			OPR339.verifyGivenSecurityStatusAcceptedIsTicked();
			cust.closeTab("OPR339", "Security & Screening");	
			
			/*******Verify FSU-RCS is not sent message in MSG005******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo", "AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyNoMsgTriggered("MSG005","XFSU-RCS","AWBNo");
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
			
			//As Is Execute AWB
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.verifySCCCodes("VerifySCCExists", "SPX");
			OPR026.verifySCCCodes("VerifySCCNotExists", "NSC");
			OPR026.asIsExecute();	
			cust.closeTab("OPR026", "Capture AWB");	
				
			/** CHECKING XFWB TRIGGERED  **/
			//Verifying KC Details
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FWB", "", "AWBNo");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.clickMessageCheckBox("2");
			MSG005.clickView();	        
			List <String> msgContentsPresent=new ArrayList<String>();		
			msgContentsPresent.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode>RA</ContentCode>"+"\n"+"<Content>"+cust.data("RegulatedAgentCode")+"</Content>"+"\n"+"<SubjectCode>ISS</SubjectCode>"+"\n"+"<CountryID>"+cust.data("AgentCountryId")+"</CountryID>"+"\n"+"</IncludedCustomsNote>");
			msgContentsPresent.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode>"+"SN"+"</ContentCode>"+"\n"+"<Content>"+iCargo[1].toUpperCase()+"</Content>"+"\n"+"</IncludedCustomsNote>");
			msgContentsPresent.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode>"+"SD"+"</ContentCode>");	
			msgContentsPresent.add("val~<ContentCode>ED</ContentCode>"+"\n"+"<Content>"+cust.data("Expiry")+"</Content>");		
			msgContentsPresent.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode>KC</ContentCode>"+"\n"+"<Content>"+cust.data("KCCode")+"</Content>"+"\n"+"<CountryID>"+cust.data("KCCountryId")+"</CountryID>"+"\n"+"</IncludedCustomsNote>");
			msgContentsPresent.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode>ED</ContentCode>"+"\n"+"<Content>"+cust.data("KCExpiry")+"</Content>"+"\n"+"</IncludedCustomsNote>");
			//SPX is Present
			msgContentsPresent.add("val~<DescriptionCode>SPX</DescriptionCode>");
			MSG005.verifyMessageContent(msgContentsPresent,"XFWB",true);
			MSG005.closeView();		
			libr.waitForSync(1);
			
			//Verify NSC is not present
			List <String> msgContents1=new ArrayList<String>();
			msgContents1.add("val~<DescriptionCode>NSC</DescriptionCode>");  
			MSG005.clickMessageCheckBox("2");
			MSG005.clickView();
			MSG005.verifyMessageContent(msgContents1,"XFWB",false);				
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/****** Verify FSU-RCS message in MSG005 *****/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Acceptance");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyRCS=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsRCS[]={9};
			String[] actVerfValuesRCS={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-RCS",true);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
