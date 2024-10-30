package afklregression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.AWBClearance_OPR023;
import screens.BreakDownScreen_OPR004;
import screens.BreakdownHHT;
import screens.BuildUpHHT;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.DamageCaptureHHT;
import screens.DeliveryDocumentation_OPR293;
import screens.ExportManifest_OPR344;
import screens.FlightLoadPlan_OPR015;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListCheckSheetConfig_SHR094;
import screens.ListIrregularity_OPR341;
import screens.ListMessages_MSG005;
import screens.ListTemplates_SHR093;
import screens.MaintainFlightSchedule_FLT005;
import screens.MarkFlightMovements_FLT006;
import screens.OffloadEnquiry_OPR011;
import screens.RelocationTaskMonitor_WHS052;
import screens.SecurityAndScreeningHHT;
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
 * 
 * Data capture of AWB for a cash customer not registered in VC Client for local export of loose shipment which consignee is not in China 

(FWB received)
 *
 */
public class AWBDCCDG1_001 extends BaseSetup {

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
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="afklregression";	

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
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);

	}

	@DataProvider(name = "AWBDCCDG1_001")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "AWBDCCDG1_001")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);



			// Login to "ICARGO"
			String[] iCargo = libr.getApplicationParams("iCargo");
			driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);


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
            
			map.put("ShipperName", cust.getPropertyValue(custproppath, "cashCustomerName_NL"));
			map.put("ShipperPostCode", cust.getPropertyValue(custproppath, "cashCustomerpostCode_NL"));
			map.put("ShipperStreetName", cust.getPropertyValue(custproppath, "cashCustomerstreetName_NL"));
			map.put("ShipperCityName", cust.getPropertyValue(custproppath, "cashCustomercityName_NL"));
			map.put("ShipperCountryId", cust.getPropertyValue(custproppath, "cashCustomercountryId_NL"));
			map.put("ShipperCountryName", cust.getPropertyValue(custproppath, "cashCustomercountryName_NL"));
			map.put("ShipperCountrySubDiv", cust.getPropertyValue(custproppath, "cashCustomercountrySubdivision_NL"));
			map.put("ShipperPhoneNo", cust.getPropertyValue(custproppath, "cashCustomertelephoneNo_NL"));
			map.put("ShipperEmail", cust.getPropertyValue(custproppath, "cashCustomeremail_NL"));

			map.put("ConsigneeName", cust.getPropertyValue(custproppath, "cashCustomerName2_NL"));
			map.put("ConsigneePostCode", cust.getPropertyValue(custproppath, "cashCustomerpostCode2_NL"));
			map.put("ConsigneeStreetName", cust.getPropertyValue(custproppath, "cashCustomerstreetName2_NL"));
			map.put("ConsigneeCityName", cust.getPropertyValue(custproppath, "cashCustomercityName2_NL"));
			map.put("ConsigneeCountryId", cust.getPropertyValue(custproppath, "cashCustomercountryId2_NL"));
			map.put("ConsigneeCountryName", cust.getPropertyValue(custproppath, "cashCustomercountryName2_NL"));
			map.put("ConsigneeCountrySubDiv", cust.getPropertyValue(custproppath, "cashCustomercountrySubdivision2_NL"));
			map.put("ConsigneePhoneNo", cust.getPropertyValue(custproppath, "cashCustomertelephoneNo2_NL"));
			map.put("ConsigneeEmail", cust.getPropertyValue(custproppath, "cashCustomeremail2_NL"));

			map.put("OriginAirport", cust.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", cust.getPropertyValue(custproppath, "AMS"));

			//Switch role
			cust.switchRole("Origin", "FCTL", "RoleGroup");



			//Checking AWB is fresh or Not--AWB 1
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			 


			/***MESSAGE - loading XFWB **/
			cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			//Load FWB message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "MQ-SERIES", "", "FCTL", "", "XFWB",true);
			cust.closeTab("MSG005", "List Message");

			/***** OPR026 - Execute AWB****/

			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
			List<String> MandatoryComponents=new ArrayList<String>();
			MandatoryComponents.add(cust.data("Origin"));
			MandatoryComponents.add(cust.data("Destination"));
			MandatoryComponents.add(cust.data("carrierCode"));
			MandatoryComponents.add(cust.data("Destination"));
			MandatoryComponents.add(cust.data("AgentCode"));
			MandatoryComponents.add(cust.data("shipperCode"));
			MandatoryComponents.add(cust.data("consigneeCode"));
			MandatoryComponents.add(cust.data("Pieces"));
			MandatoryComponents.add(cust.data("Weight"));
			MandatoryComponents.add(cust.data("CommodityCode"));
			OPR026.verifyXFWBMandatoryComponents(MandatoryComponents);
			OPR026.verifySCI(cust.data("SCI"));
			OPR026.asIsExecute();	
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
			/**Origin**/
			msgContents.add("val~<OriginLocation>"+"\n"+"<ID>"+cust.data("Origin")+"</ID>");
			/**Destination**/
			msgContents.add("val~<FinalDestinationLocation>"+"\n"+"<ID>"+cust.data("Destination")+"</ID>");
			/**Agent**/
			msgContents.add("val~<FreightForwarderParty>"+"\n"+"<Name>"+cust.data("ShipperName")+"</Name>"+
					"\n"+"<AccountID>"+cust.data("AgentCode")+"</AccountID>");
			/**Shipper**/
			msgContents.add("val~<ConsignorParty>"+"\n"+"<Name>"+cust.data("ShipperName")+"</Name>");
			/**Consignee**/
			msgContents.add("val~<ConsigneeParty>"+"\n"+"<Name>"+cust.data("ConsigneeName")+"</Name>");

			/*** SCI***/
			msgContents.add("val~<GoodsStatusCode>"+cust.data("SCI")+"</GoodsStatusCode>");
			
			/**Commodity Details**/
			String wtUnit="\"KGM\"";
			String volUnit="\"MTQ\"";
			msgContents.add("val~<IncludedMasterConsignmentItem>"+"\n"+"<SequenceNumeric>1</SequenceNumeric>"+
					"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight")+"</GrossWeightMeasure>"+
					"\n"+"<GrossVolumeMeasure unitCode="+volUnit+">"+cust.data("Volume")+"</GrossVolumeMeasure>"+
					"\n"+"<PieceQuantity>"+cust.data("Pieces")+"</PieceQuantity>"+
					"\n"+"<NatureIdentificationTransportCargo>"+"\n"+"<Identification>"+cust.data("ShipmentDesc")+"</Identification>");
			//Verify message contents
			MSG005.verifyMessageContent(msgContents,"XFWB");
			MSG005.closeView();

			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**Verification of XFWB message content and Pelican EML is Scripting is Pending**//*

			 */

		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}

	}
}

