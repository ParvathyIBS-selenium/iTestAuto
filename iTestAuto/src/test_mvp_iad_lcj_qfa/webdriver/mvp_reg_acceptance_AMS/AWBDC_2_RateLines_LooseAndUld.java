package mvp_reg_acceptance_AMS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.CaptureHAWB_OPR029;
import screens.Cgocxml;
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

/** "Data Capture for Credit Customer known in VC Client, for local export lose and ULD shipment. Two lines are created in Charges and Accounting tab under Rating Details Section. For the one corresponding to ULD Rate Class is mentionned as ""U"". Use print button after AWB execution to print AWB;
Verify that rate class is working fine for mix acceptance (bulk and ULD)
Print button is working as expected"
**/
public class AWBDC_2_RateLines_LooseAndUld extends BaseSetup {
	
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
		CSH007 = new GeneratePaymentAdvice_CSH007(driver, excelreadwrite, xls_Read);
		Cgocxml=new Cgocxml(driver, excelreadwrite, xls_Read);
		Cgomon=new Cgomon(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "AWBDC")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "AWBDC")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			WebFunctions.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		
			

			/***Storing Values to Map***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_NL"));
			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "cashCustomerId_FR"));;

			//Login to iCargo
			
			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);	
			
			//Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");
			
            /**** OPR026 - Capture AWB****/
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
			map.put("AWBNo",cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","prop~flight_code_KL");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			
			//enter shipment details
			String[] Pieces={cust.data("Pieces"),cust.data("Pieces1")};
			String[] Weight={cust.data("Weight"),cust.data("Weight1")};
			String[] Volume={cust.data("Volume"),cust.data("Volume")};
			String[] CommodityCode={cust.data("CommodityCode"),cust.data("CommodityCode")};
			OPR026.enterShipmentDetails(2, Pieces, Weight, Volume,CommodityCode);
			
			//Enter ULD Type/Number details
			String[] ULDNum={cust.data("UldType")};
			String[] ULDWeight={cust.data("Weight1")};
			OPR026.enterRatingULDDetails(2, 1, ULDNum, ULDWeight);
			
			OPR026.clickChargesAcc();
			//Provide rating details
			String[] RateClass={cust.data("rateClass").split(",")[0],cust.data("rateClass").split(",")[1]};
			String[] IATArate={cust.data("IATARate").split(",")[0],cust.data("IATARate").split(",")[1]};
			String[] IATAcharge={cust.data("IATAcharge").split(",")[0],cust.data("IATAcharge").split(",")[1]};
			OPR026.provideRatingDetails(2,RateClass,IATArate,IATAcharge);
			
			//Click calculate charges button		
			OPR026.clickCalcCharges();
			
			OPR026.asIsExecute();
			OPR026.handleShipmentStatusPopUp();
			
			//click laser print AWB
			OPR026.printAndVerifyReport("LaserPrint","val~STAPLE DOCUMENT ABOVE PERFORATIO",cust.data("prop~FullAWBNo"));
			cust.closeTab("OPR026", "Capture AWB");
			
			//Switch role
			cust.switchRole("FCTL", "FCTL", "RoleGroup");

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
			String wtUnit="\"KGM\"";
			String volUnit="\"MTQ\"";
			String currency="\"EUR\"";
			msgContents.add("val~<IncludedMasterConsignmentItem>"+"\n"+"<SequenceNumeric>1</SequenceNumeric>"+
					"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight")+"</GrossWeightMeasure>"+
					"\n"+"<GrossVolumeMeasure unitCode="+volUnit+">"+cust.data("Volume")+"</GrossVolumeMeasure>"+
					"\n"+"<PieceQuantity>"+cust.data("Pieces")+"</PieceQuantity>"+
					"\n"+"<Information>NDA</Information>"+
					"\n"+"<NatureIdentificationTransportCargo>"+"\n"+"<Identification>"+cust.data("ShipmentDesc")+"</Identification>"+
					"\n"+"</NatureIdentificationTransportCargo>"+
					"\n"+"<ApplicableFreightRateServiceCharge>"+
					"\n"+"<CategoryCode>"+cust.data("rateClass").split(",")[0]+"</CategoryCode>"+
					"\n"+"<ChargeableWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight2")+"</ChargeableWeightMeasure>"+
					"\n"+"<AppliedRate>"+cust.data("IATARate").split(",")[0]+"</AppliedRate>"+
					"\n"+"<AppliedAmount currencyID="+currency+">"+cust.data("IATAcharge").split(",")[0]+"</AppliedAmount>"+
					"\n"+"</ApplicableFreightRateServiceCharge>"+
					"\n"+"</IncludedMasterConsignmentItem>");
			
			msgContents.add("val~<IncludedMasterConsignmentItem>"+"\n"+"<SequenceNumeric>2</SequenceNumeric>"+
					"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight1")+"</GrossWeightMeasure>"+
					"\n"+"<GrossVolumeMeasure unitCode="+volUnit+">"+cust.data("Volume")+"</GrossVolumeMeasure>"+
					"\n"+"<PieceQuantity>"+cust.data("Pieces1")+"</PieceQuantity>"+
					"\n"+"<Information>NDA</Information>"+
					"\n"+"<NatureIdentificationTransportCargo>"+"\n"+"<Identification>"+cust.data("ShipmentDesc")+"</Identification>"+
					"\n"+"</NatureIdentificationTransportCargo>"+
					"\n"+"<ApplicableFreightRateServiceCharge>"+
					"\n"+"<CategoryCode>"+cust.data("rateClass").split(",")[1]+"</CategoryCode>"+
					"\n"+"<ChargeableWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight1")+"</ChargeableWeightMeasure>"+
					"\n"+"<AppliedRate>"+cust.data("IATARate").split(",")[1]+"</AppliedRate>"+
					"\n"+"<AppliedAmount currencyID="+currency+">"+cust.data("IATAcharge").split(",")[1]+"</AppliedAmount>"+
					"\n"+"</ApplicableFreightRateServiceCharge>"+
					"\n"+"</IncludedMasterConsignmentItem>");
			
			//Verify message contents
			MSG005.verifyMessageContent(msgContents,"XFWB");
			MSG005.closeView();

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
	    	Cgomon.cleanDetails();
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

