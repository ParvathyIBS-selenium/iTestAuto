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
import screens.Cgomon;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.SecurityAndScreening_OPR339;


/**
 * "Loose shipments partial acceptance,AWB data capture and screening are executed"
 **/
public class Acceptance_IAD1_004 extends BaseSetup {

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
	public Cgomon Cgomon;	

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
		OPR335=new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		Cgomon=new Cgomon(driver, excelreadwrite, xls_Read);

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

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_CZ"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_CZ"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_CZ"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_CZ"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_CZ"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_CZ"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_CZ"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_CZ"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_CZ"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_CZ"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "WRO"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "PRG"));

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
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_WithRA_WithoutScreeningInf", true);
			cust.closeTab("MSG005", "MSG005 - List Messages");



			/**** OPR339 - Security & Screening ****/	
			cust.searchScreen("OPR339", "Security and Screening");
			OPR339.listAWB("AWBNo", "CarrierNumericCode", "OPR339 - Security & Sceening");
			OPR339.clickYesButton();
			OPR339.enterScreeningDetails("ScreeningMethod","Pieces","Weight","val~Pass");
			OPR339.verifyAgentDetailsAutopopulated("AgentType", "AgentCountryId", "RegulatedAgentCode");          
			OPR339.verifyGivenSecurityStatusAcceptedIsTicked();
			OPR339.verifySecurityDataReviewedIsTicked();               
			OPR339.addRAAcceptingDetails("RAAcceptingCountryId", "RAAcceptingCode","RAAcceptingExpiry");
			OPR339.OkButtonAfterScreeningSave();
			cust.closeTab("OPR339", "Security & Sceening");



			/*********   OPR023 - Remove Compliance Block   ******/
			cust.searchScreen("OPR023","AWB CLearance");
			OPR023.listAWB("CarrierNumericCode", "AWBNo");
			OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Compliance Block removed");  
			OPR023.closeTab("OPR023", "AWB Clearance"); 


			/**** OPR335 -Goods Acceptance****/
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance");
			OPR335.verifyAWBDetails("Pieces", "Weight", "Volume");
			OPR335.verifyAWBDetails(cust.data("SCC"));
			OPR335.looseShipmentDetails("Location", "Pieces1","Weight1");
			OPR335.addLooseShipment();
			OPR335.clickSave();
			OPR335.verifyAcceptanceFinalized("not finalised",false);
			OPR335.verificationOfNotRFCStatus();
			cust.closeTab("OPR335", "Goods Acceptance");

			//Accept Remaining pieces
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "CarrierNumericCode", "Goods Acceptance"); 
			OPR335.looseAcceptanceDetails("Location", "Pieces2","Weight2");
			OPR335.addLooseShipment();
			OPR335.allPartsRecieved();              
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");

			/*******Verify FSU-FOH message in MSG005******/			
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Freight On Hand");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFSU=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo");
			int verfColsFSU[]={9};
			String[] actVerfValuesFSU={"Sent"};
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU,"val~XFSU",true);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/*******Verify FSU-RCS is not sent in MSG005******/
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
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS,"val~XFSU",true);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");          
			libr.quitBrowser();		


		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

