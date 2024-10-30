package iascb_164029;

/** TC_37_Verify RA accepting issue ID is getting populated in OPR339 when acceptance done by the user which is configured in ADM007
 **/




// Update RA accepting ID  NL|00001-00|0126 on ADM007 against the TID which is used for execution

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import screens.AWBClearance_OPR023;
import screens.CaptureAWB_OPR026;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.SecurityAndScreening_OPR339;

public class IASCB_164029_TC_12270_KL extends BaseSetup {

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
	public SecurityAndScreening_OPR339 OPR339;
	public AWBClearance_OPR023 OPR023;
	
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="iascb_164029";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		
	}
	
	@DataProvider(name = "Acceptance_IAD1")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "Acceptance_IAD1")
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
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			/***Storing Values to Map***/			
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_NL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_NL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_NL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_NL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_NL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_NL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_NL"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_FR"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_FR"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_FR"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_FR"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_FR"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_FR"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_FR"));
			map.put("ConsigneeCountrySubDiv",WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_FR"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_FR"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_FR"));
			
			

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "AMS"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_NL"));
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_NL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_NL"));
			
			//Regulated Agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_TypeHUB"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB"));
			
			//Regulated agent Accepting details
			map.put("RAAgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Accepting_Type_CodeAMS"));
			map.put("RAAcceptingCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Accepting_CountryIdAMS"));
			map.put("RAAcceptingCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Accepting_Carrier_CodeAMS"));
			map.put("RAAcceptingExpiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Accepting_ExpiryAMS"));
			
			//Switch Role
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
						
	        //Create XFWB message			
			/**** MESSAGE - loading XFWB with Valid RA and SPX, without SM ******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithRA_WithoutScreeningInf", true);
			cust.closeTab("MSG005", "MSG005 - List Messages");
								
			/**** OPR026 - Capture AWB****/		
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			//Verify validated agent and AWB secure SPX is validated 
			OPR026.clickCheckStatus();
			OPR026.checkStatus("val~Validated Agent","green");
			OPR026.clickCheckStatus();
			OPR026.checkStatus("val~AWB Secure SPX","green");
			OPR026.clickCheckStatus();
			OPR026.checkSectionStatus("val~Security","VALIDATED");
			OPR026.verifySCCCodes("VerifySCCExists", "SPX");
			OPR026.verifySCCCodes("VerifySCCNotExists", "NSC");
			OPR026.verifyeCSDicon();
			cust.closeTab("OPR026", "Capture AWB");	
			
			/*********   OPR023 - Remove Customs Block   ******/
			cust.searchScreen("OPR023","AWB CLearance");
			OPR023.listAWB("CarrierNumericCode", "AWBNo");
			OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Compliance Block removed");  
		    OPR023.closeTab("OPR023", "AWB Clearance"); 
			
			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod", "Pieces", "Weight", "val~Pass");
			OPR339.getNumberOfAgentDetailsPresent(1);
			OPR339.verifyAgentDetailsAutopopulated("AgentType", "AgentCountryId", "RegulatedAgentCode","Expiry");	
			//Verify SCC
			String[] sccSPX = {"SPX"};
			OPR339.verifyScc(sccSPX);
			//Verify SCC does not contain NSC
			String[] sccNSC = {"NSC"};
			OPR339.verifySccNotPresent(sccNSC);
			OPR339.verifySecurityDataReviewedIsTicked();
			OPR339.verifyGivenSecurityStatusAcceptedIsTicked();
			OPR339.saveSecurityDetails();
			cust.closeTab("OPR339", "Security & Screening");	
			
			/**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");
			OPR339.getNumberOfAgentDetailsPresent(1);
			OPR339.verifyAgentDetailsAutopopulated("AgentType", "AgentCountryId", "RegulatedAgentCode","Expiry");	
			cust.closeTab("OPR339", "Security & Screening");	
			
			/**** OPR335 - Goods Acceptance ****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.looseShipmentDetails("Location", "Pieces","Weight");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
            OPR335.verificationOfNotRFCStatus();
            cust.closeTab("OPR335", "Goods Acceptance");
            
            /**** OPR339 - Security & Screening ****/
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWBNo("AWBNo", "CarrierNumericCode", "OPR339 - Security & Screening");
			OPR339.getNumberOfAgentDetailsPresent(2);
			OPR339.verifyAgentDetailsAutopopulated("AgentType", "AgentCountryId", "RegulatedAgentCode","Expiry");
			//Verify RA-OSS auto-populated on GoodsAcceptance First Save
			OPR339.verifyAgentDetailsAutopopulated("RAAgentType", "RAAcceptingCountryId", "RAAcceptingCode","RAAcceptingExpiry");
			OPR339.verifyScc(sccSPX);
			OPR339.verifySccNotPresent(sccNSC);
			OPR339.verifySecurityDataReviewedIsTicked();
			OPR339.verifyGivenSecurityStatusAcceptedIsTicked();
			cust.closeTab("OPR339", "Security & Screening");	
        					 	
			/*******Verify FSU-FOH message in MSG005******/			
			cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.enterMsgType("XFSU");
            MSG005.selectMsgSubType("Freight On Hand");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
            String pmKeyFSU=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
            int verfColsFSU[]={9};
            String[] actVerfValuesFSU={"Sent"};
            MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU-FOH",false);
            libr.waitForSync(1);
            MSG005.closeTab("MSG005", "MSG005 - List Messages");
            
            //As Is Execute AWB
            cust.searchScreen("OPR026","Capture AWB");
            OPR026.listAWB("AWBNo", "CarrierNumericCode");
            OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
            OPR335.verificationOfRFCStatus();
            OPR335.verifyAcceptanceFinalized("finalised",false);
            cust.closeTab("OPR335", "Goods Acceptance");
			
			/** CHECKING XFWB TRIGGERED FOR AWB **/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFWB=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination");
			int verfColsXFWB[]={9};
			String[] actVerfValuesXFWB={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFWB, actVerfValuesXFWB, pmKeyXFWB,"val~XFWB",false);
			libr.waitForSync(1); 
			
			/*** VERIFY THE MESSAGE CONTENTS***/
			map.put("pmkey", pmKeyXFWB);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			
			/** Screening details **/	
			msgContents.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode>"+"SM"+"</ContentCode>"+
				"\n"+"<Content>"+cust.data("ScreeningMethod").split("-")[0].trim()+"</Content>"+"\n"+"</IncludedCustomsNote>"+"\n"+"<IncludedCustomsNote>"+"\n"+"<ContentCode>"+"SN"+"</ContentCode>"+
				"\n"+"<Content>"+iCargo[1]+"</Content>"+"\n"+"</IncludedCustomsNote>");
			msgContents.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode>"+cust.data("AgentTypeCode")+"</ContentCode>"+"\n"+"<Content>"+cust.data("RegulatedAgentCode")+"</Content>"+"\n"+"<SubjectCode>ISS</SubjectCode>"+"\n"+"<CountryID>"+cust.data("AgentCountryId")
			+"</CountryID>"+"\n"+"</IncludedCustomsNote>");
			msgContents.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode>ED</ContentCode>"+"\n"+"<Content>"+cust.data("Expiry")+"</Content>"+"\n"+"</IncludedCustomsNote>");
			msgContents.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode>"+"SD"+"</ContentCode>");	
			//SPX is Present
			msgContents.add("val~<DescriptionCode>SPX</DescriptionCode>");
			//RA Accepting details
			msgContents.add("val~<IncludedCustomsNote>"+"\n"+"<ContentCode>"+cust.data("AgentTypeCode")+"</ContentCode>"+"\n"+"<Content>"+
			cust.data("RAAcceptingCode")+"</Content>"+"\n"+"<SubjectCode>OSS</SubjectCode>"+"\n"+"<CountryID>"+cust.data("RAAcceptingCountryId")
			+"</CountryID>"+"\n"+"</IncludedCustomsNote>"+"\n"+"<IncludedCustomsNote>"+"\n"+"<ContentCode>ED</ContentCode>"+"\n"+"<Content>"+
			cust.data("RAAcceptingExpiry")+"</Content>"+"\n"+"</IncludedCustomsNote>");			
			//Verify message contents
			MSG005.verifyMessageContent(msgContents,"XFWB");
			MSG005.closeView();
			
			//Verify NSC is not present
			List <String> msgContents1=new ArrayList<String>();
			msgContents1.add("val~<DescriptionCode>NSC</DescriptionCode>");  
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			MSG005.verifyMessageContent(msgContents1,"XFWB",false);				
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");
					        
            /*******Verify FSU-RCS message in MSG005******/
        	cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.enterMsgType("XFSU");
            MSG005.selectMsgSubType("Acceptance");
            MSG005.selectStatus("Sent");
            MSG005.clickList();
            String pmKeyRCS=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
            int verfColsRCS[]={9};
            String[] actVerfValuesRCS={"Sent"};
            MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU-RCS",false);
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

