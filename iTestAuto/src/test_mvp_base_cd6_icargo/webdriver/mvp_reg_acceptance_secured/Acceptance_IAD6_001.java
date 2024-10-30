package mvp_reg_acceptance_secured;

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
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.SecurityAndScreening_OPR339;

/**
 * Goods acceptance of an ULD with COL. AWB data capture and screening are done
**/
public class Acceptance_IAD6_001 extends BaseSetup {
	
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
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "Acceptance_IAD6_001")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "Acceptance_IAD6_001")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			WebFunctions.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		
			// Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	
			
			String startDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormatWithTimeZone("dd-MMM-YYYY", 7, "DAY", "");
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			String flightdate1 = cust.createDateFormatWithTimeZone("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			map.put("FBLDate", cust.createDateFormatWithTimeZone("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormatWithTimeZone("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormatWithTimeZone("MMM", 0, "DAY", ""));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			String currtme1=cust.createDateFormatWithTimeZone("HHmm", 0, "DAY", "Europe/Paris");
			String currentday=cust.createDateFormatWithTimeZone("ddMMYY", 0, "DAY", "");
			String SD=currentday+currtme1;
			map.put("SDtime",SD);
			String screenmethod=cust.data("ScreeningMethod").split("-")[0].trim();
			map.put("screenmethod",screenmethod);
			
			/***Storing Values to Map***/			
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_PL"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_PL"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_PL"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_PL"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_PL"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_PL"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_PL"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_PL"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_PL"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_PL"));

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
			
			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "WRO"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "CDG"));
						
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_PL"));
			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_PL"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_PL"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_PL"));

			//Regulated agent details
			map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code_RA"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId_RA"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type_RA"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry_RA"));
			
			//Regulated agent Accepting details
			map.put("RAAcceptingCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId_RA_Accepting"));
			map.put("RAAcceptingCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code_RA_Accepting"));
			map.put("RAAcceptingExpiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry_RA_Accepting"));
		
			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");
		
//			//Checking AWB is fresh or Not
//			cust.searchScreen("OPR026","Capture AWB");
//			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
//			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
//		    /***MESSAGE - loading XFWB **********/
//	        //Create XFWB message			
//			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
//			cust.searchScreen("MSG005", "MSG005 - List Messages");
//			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithScreeningInfo", true);
//			cust.closeTab("MSG005", "MSG005 - List Messages");
			
		    /**** OPR026 - Capture AWB****/		
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
            OPR026.clickSecurityScreening();
            cust.switchToFrame("frameName", "popupContainerFrame");
            OPR339.verifyAgentDetailsAutopopulated("AgentType", "AgentCountryId", "RegulatedAgentCode");
            OPR339.verifyScreeningMethodAutopopulated("screenmethod");
            OPR339.verifyGivenSecurityStatusAcceptedIsTicked();
            
            OPR339.verifySecurityDataReviewedIsTicked();               
            OPR339.addRAAcceptingDetails("RAAcceptingCountryId", "RAAcceptingCode","RAAcceptingExpiry");  
            OPR339.OkButtonAfterScreeningSave();
            cust.switchToMainScreen("OPR026");
			OPR026.saveAWB(); 
			cust.closeTab("OPR026", "Capture AWB");
			
			/*********   OPR023 - Remove Compliance Block   ******/
			cust.searchScreen("OPR023","AWB CLearance");
			OPR023.listAWB("CarrierNumericCode", "AWBNo");
			OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Compliance Block removed");  
		    OPR023.closeTab("OPR023", "AWB Clearance"); 
	
			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			//Creating ULD number
			String uldNo=OPR335.create_uld_number("UldType", "carrierCode");
			map.put("UldNum", uldNo);
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			libr.waitForSync(6);
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.uldShipmentDetails("Pieces","Weight", "Location", "UldNum","Contour");
			OPR335.selectContour("Contour");
            OPR335.addULDDetails();
            OPR335.allPartsRecieved();
			OPR335.clickSave();
            OPR335.verifyAcceptanceFinalized("finalised",false);
			OPR335.verificationOfNotRFCStatus();          
            cust.closeTab("OPR335", "Goods Acceptance");
			
			/**Message details  for xFSU-FOH and xFSU-RCS **/			
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
            OPR026.verifySCCCodes("VerifySCCExists", "SPX");
			OPR026.verifySCCCodes("VerifySCCNotExists", "NSC");
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
			
			//RA Issuing details
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

