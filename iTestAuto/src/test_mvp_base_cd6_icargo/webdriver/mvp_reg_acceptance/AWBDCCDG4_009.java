package mvp_reg_acceptance;

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
import screens.CaptureAWB_OPR026;
import screens.CaptureHAWB_OPR029;
import screens.GeneratePaymentAdvice_CSH007;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.SecurityAndScreening_OPR339;

/**
 * "Data capture of CNSL AWB for an account customer for local export of loose shipment which consignee is in China (FWB received)

 **/
public class AWBDCCDG4_009 extends BaseSetup {

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
	public CaptureHAWB_OPR029 OPR029;
	
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
		OPR029 = new CaptureHAWB_OPR029(driver, excelreadwrite, xls_Read);
		

	}

	@DataProvider(name = "AWBDCCDG4_009")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "AWBDCCDG4_009")
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
			
			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");

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

			/****** UPDATING XFWB CUSTOMER DETAILS IN MAP***/
			map.put("AgentCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_US"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_CASSCode_US"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "creditCustomer_IATACode_US"));

			map.put("ShipperCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerId_US"));
			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "creditCustomerName_US"));
			map.put("ShipperPostCode", WebFunctions.getPropertyValue(custproppath, "creditCustomerpostCode_US"));
			map.put("ShipperStreetName", WebFunctions.getPropertyValue(custproppath, "creditCustomerstreetName_US"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "creditCustomercityName_US"));
			map.put("ShipperCountryId", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryId_US"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "creditCustomercountryName_US"));
			map.put("ShipperCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "creditCustomercountrySubdivision_US"));
			map.put("ShipperPhoneNo", WebFunctions.getPropertyValue(custproppath, "creditCustomertelephoneNo_US"));
			map.put("ShipperEmail", WebFunctions.getPropertyValue(custproppath, "creditCustomeremail_US"));

			map.put("ConsigneeCode", WebFunctions.getPropertyValue(custproppath, "customerId2_CN"));
			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "customerName2_CN"));
			map.put("ConsigneePostCode", WebFunctions.getPropertyValue(custproppath, "postCode2_CN"));
			map.put("ConsigneeStreetName", WebFunctions.getPropertyValue(custproppath, "streetName2_CN"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "cityName2_CN"));
			map.put("ConsigneeCountryId", WebFunctions.getPropertyValue(custproppath, "countryId2_CN"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "countryName2_CN"));
			map.put("ConsigneeCountrySubDiv", WebFunctions.getPropertyValue(custproppath, "countrySubdivision2_CN"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "telephoneNo2_CN"));
			map.put("ConsigneeEmail", WebFunctions.getPropertyValue(custproppath, "email2_CN"));

			map.put("OriginAirport", WebFunctions.getPropertyValue(custproppath, "IAD"));
			map.put("DestinationAirport", WebFunctions.getPropertyValue(custproppath, "PEK"));

			// Checking AWB is fresh or Not
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"), proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);
			
			
			//Create and load XFWB message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			cust.createXMLMessage("MessageExcelAndSheetXFWB", "MessageParamXFWB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFWB_China", true);
			

			//Create and Load XFZB message
			cust.createXMLMessage("MessageExcelAndSheetXFZB", "MessageParamXFZB");
			MSG005.loadFromFile("All", "ALL", "MQ-SERIES", "", "Origin", "", "XFZB_China", true);
			cust.closeTab("MSG005", "List Message");
			

			

			/***** OPR026 - Execute AWB****/
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");

			//verify shipment details
			List<String> MandatoryComponents=new ArrayList<String>();
			MandatoryComponents.add(cust.data("Origin"));
			MandatoryComponents.add(cust.data("Destination"));
			MandatoryComponents.add(cust.data("carrierCode"));
			MandatoryComponents.add(cust.data("Destination"));
			MandatoryComponents.add(cust.data("AgentCode"));
			MandatoryComponents.add(cust.data("ShipperCode"));
			MandatoryComponents.add(cust.data("ConsigneeCode"));
			MandatoryComponents.add(cust.data("Pieces"));
			MandatoryComponents.add(cust.data("Weight"));
			MandatoryComponents.add(cust.data("CommodityCode"));
			OPR026.verifyXFWBMandatoryComponents(MandatoryComponents);
			OPR026.verifySCI(cust.data("SCI"));
			OPR026.verifyHouses("HAWB");

			/*****************************************************/
			OPR026.clickHAWBButton();
			String pmKey=cust.data("HAWB");
			int verfColmn[]={5, 6, 9,10,11, 12, 13};   
			String hawbShipperDetails=cust.data("ShipperName")+"/"+cust.data("ShipperStreetName");
			String hawbConsigneeDetails=cust.data("ConsigneeName")+"/"+cust.data("ConsigneeStreetName");
			String[] actVerfValue={cust.data("Pieces"),cust.data("Weight"),hawbShipperDetails,hawbConsigneeDetails,"Consol Shipment",cust.data("Origin"),cust.data("Destination")};
			OPR029.verifyHAWBTableDetails(verfColmn, actVerfValue, pmKey);
			OPR026.close("OPR029");
			cust.closeTab("OPR026", "Capture AWB");

			/**** RELIST AWB***/
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			//Click HAWB Doc Finalized checkbox
			OPR026.clickHAWBDocFinalized();
			/*******************************************************/
			//remove shipper telephone number and verify error message.
			OPR026.removeShipperPhoneNo();
			OPR026.asIsExecuteButtonOnly();
			OPR026.acceptMsgOnExecution();
			/********************************************/
			cust.verifyErrorMessages("OPR026", "Shipper telephone number is missing for "+cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			/*****************************************/
			cust.closeTab("OPR026", "Capture AWB");

			//remove consignee telephone number and verify error message. 
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.enterShipperPhoneNo("ShipperPhoneNo");
			OPR026.removeConsigneePhoneNo();
			OPR026.asIsExecuteButtonOnly();
			OPR026.acceptMsgOnExecution();
			/****************************************************/
			cust.verifyErrorMessages("OPR026", "Consignee telephone number is missing for "+cust.data("CarrierNumericCode")+"-"+cust.data("prop~AWBNo"));
			/*******************************************************/
			cust.closeTab("OPR026", "Capture AWB");

			/**** RELIST AWB ***/
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("AWBNo", "CarrierNumericCode");
			OPR026.enterShipperPhoneNo("ShipperPhoneNo");
			OPR026.enterConsigneePhoneNo("ConsigneePhoneNo");

			//Verify IATA rate and IATA charge
			OPR026.clickChargesAcc();
			OPR026.verifyIATAChargeDetails(cust.data("IATAcharge"), cust.data("IATARate"));
			OPR026.clickCalcCharges();
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");

			/*******MSG005 - List Messages******/
			/*** VERIFY THE XFWB MESSAGE CONTENTS***/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.clickList();
			String pmKeyXFWB=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination");
			map.put("pmkey", pmKeyXFWB);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			/**Origin**/
			msgContents.add("val~<OriginLocation>"+"\n"+"<ID>"+cust.data("Origin")+"</ID>");
			/**Destination**/
			msgContents.add("val~<FinalDestinationLocation>"+"\n"+"<ID>"+cust.data("Destination")+"</ID>");
			/**Agent**/
			msgContents.add("val~<FreightForwarderParty>"+"\n"+"<Name>"+cust.data("ShipperName")+"</Name>");
			/**Shipper**/
			msgContents.add("val~<ConsignorParty>"+"\n"+"<Name>"+cust.data("ShipperName")+"</Name>");
			/**Consignee**/
			msgContents.add("val~<ConsigneeParty>"+"\n"+"<Name>"+cust.data("ConsigneeName")+"</Name>");

			/*** SCI***/
			msgContents.add("val~<GoodsStatusCode>"+cust.data("SCI")+"</GoodsStatusCode>");

			/**Commodity Details**/
			String wtUnit="\"KGM\"";
			String volUnit="\"MTQ\"";
			String listAgency="\"1\"";
			msgContents.add("val~<IncludedMasterConsignmentItem>"+"\n"+"<SequenceNumeric>1</SequenceNumeric>"+"\n"+"<TypeCode listAgencyID="+listAgency+">HS12345"+"</TypeCode>"+
					"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight")+"</GrossWeightMeasure>"+
					"\n"+"<GrossVolumeMeasure unitCode="+volUnit+">"+cust.data("Volume")+"</GrossVolumeMeasure>"+
					"\n"+"<PieceQuantity>"+cust.data("Pieces")+"</PieceQuantity>");
			//Verify message contents
			MSG005.verifyMessageContent(msgContents,"XFWB");
			MSG005.closeView();

			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/*******MSG005 - List Messages******/
			/*** VERIFY THE XFZB MESSAGE CONTENTS***/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFZB");
			MSG005.clickList();
			String pmKeyXFZB=cust.data("CarrierNumericCode")+" - "+cust.data("AWBNo")+" - "+cust.data("HAWB");
			map.put("pmkey", pmKeyXFZB);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents1=new ArrayList<String>();
			/**Origin**/
			msgContents1.add("val~<OriginLocation>"+"\n"+"<ID>"+cust.data("Origin")+"</ID>");
			/**Destination**/
			msgContents1.add("val~<FinalDestinationLocation>"+"\n"+"<ID>"+cust.data("Destination")+"</ID>");
			/**Agent**/
			msgContents1.add("val~<FreightForwarderParty>"+"\n"+"<Name>"+cust.data("ShipperName")+"</Name>");
			/**Shipper**/
			msgContents1.add("val~<ConsignorParty>"+"\n"+"<Name>"+cust.data("ShipperName")+"</Name>");
			/**Consignee**/
			msgContents1.add("val~<ConsigneeParty>"+"\n"+"<Name>"+cust.data("ConsigneeName")+"</Name>");

			/**Commodity Details**/
			msgContents1.add("val~<IncludedHouseConsignmentItem>"+"\n"+"<SequenceNumeric>1</SequenceNumeric>"+
					"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight2")+"</GrossWeightMeasure>"+
					"\n"+"<PieceQuantity>"+cust.data("Pieces")+"</PieceQuantity>");
			//Verify message contents
			MSG005.verifyMessageContent(msgContents1,"XFZB");
			MSG005.closeView();
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

