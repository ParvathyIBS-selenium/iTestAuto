package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import controls.ExcelRead;

public class Common_Xml {
	private static ExtentTest test;
	public String excelfilename = null;
	public static ExcelReadWrite excelreadwrite;
	static String currentTestName;
	WebDriver driver;
	String browser;
	
	String screenShotPath;
	public static CommonUtility commonUtility;
	Xls_Read xls_Read;	
	public ExcelRead excelRead;
	static String s2 = System.getProperty("user.dir");
	static String path = s2 + "\\src\\resources\\TestData.xls";
	static int	counter=0;
	static ExcelReadWrite excelReadWrite;
	
	public Common_Xml(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2)

	// Initializing variables
	{
		this.driver = driver;
		this.excelReadWrite = excelReadWrite;
		commonUtility = new CommonUtility();
		this.xls_Read = xls_Read2;		
		excelRead = new ExcelRead();
		excelfilename = this.getClass().getSimpleName();	
		
	}
	
	public Common_Xml(){}

	public ExtentTest getExtentTestInstance() {
		return this.test;
	}

	public void setExtentTestInstance(ExtentTest test) {
		this.test = test;
	}

	public Map<String, String> xmlValuesUpdation(int valuerow, int headRow,
			String sheet) {
		Map<String, String> map = new HashMap<String, String>();
		int colNum = Excel.getColumnCount(path, sheet, headRow);

		for (int col = 1; col < colNum; col++) {

			String colName = Excel.getCellValue(path, sheet, headRow, col);// t
			String propName = Excel
					.getCellValue(path, sheet, valuerow + 1, col);
			map.put(colName, propName);
		}
		return map;

	}

	// Setting the values to the required node by passing node and value to the
	// XML
	public void SetNodeValueFromXl(Map<String, String> mapValue, String xmlDoc)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException, TransformerFactoryConfigurationError,
			TransformerException {
		String xpath2 = "";
		// updating values in XML based on xpath
		for (Map.Entry<String, String> entry : mapValue.entrySet()) {
			String value = entry.getValue();
			String xpathValue = entry.getKey();
			if (xpathValue.indexOf("~") > 0) {
				xpath2 = xpathValue.split("~")[1];
			} else {
				xpath2 = entry.getKey();
			}
			SetNodeValue(xpath2, value, xmlDoc);
		}
	}

	public void SetNodeValue(String xpathValue, String value, String xmlDoc)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException, TransformerFactoryConfigurationError,
			TransformerException {
		Document doc = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlDoc);
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			Node node = (Node) xpath.evaluate(xpathValue, doc,
					XPathConstants.NODE);
			node.setTextContent(value);
			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.transform(new DOMSource(doc), new StreamResult(new File(
					xmlDoc)));
		} catch (Exception e) {
			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.transform(new DOMSource(doc), new StreamResult(new File(
					xmlDoc)));
		}
	}

	// Copying the node values from xml to Excel
	public void updateXlWithNodeValue(String table, String sheet, String xmlDoc)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, TransformerFactoryConfigurationError,
			TransformerException {

		// updating values in XL getting values from XML based on Xpath
		int startRow = startRowNum(table, sheet);
		int col = Excel.getColumnCount(path, sheet, startRow);
		for (int colNm = 1; colNm < col; colNm++) {
			String xpathValue = Excel
					.getCellValue(path, sheet, startRow, colNm);
			if (xpathValue.indexOf("~") > 0) {
				String[] xpath1 = xpathValue.split("~");
				String nodeValue = getNodeValue(xpath1[0], xmlDoc);
				Excel.setCellValue(path, sheet, startRow + 1, colNm, nodeValue);
			} else {
				System.out.println("User needs to enter the value manually");
			}
		}
	}

	// Getting the Node Values
	public static String getNodeValue(String xpathValue, String xmlDoc)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException, TransformerFactoryConfigurationError,
			TransformerException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlDoc);
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			Node node = (Node) xpath.evaluate(xpathValue, doc,
					XPathConstants.NODE);
			return node.getTextContent();
		} catch (Exception e) {
		}
		return null;
	}
  
	 // Verifying the node Value as not NULL
		public void verifyNodeValue(Map<String, String> mapValue, String xmlDoc)
				throws ParserConfigurationException, SAXException, IOException,
				XPathExpressionException, TransformerFactoryConfigurationError,
				TransformerException {
			String nodeValue;
			for (Map.Entry<String, String> entry : mapValue.entrySet()) {
				String xpathValue = entry.getKey();
				String xpathXLValue = entry.getValue();

				if (xpathValue.contains("#")) {
					nodeValue = getNodeValue(xpathValue.substring(1), xmlDoc);
				} else {
					nodeValue = getNodeValue(xpathValue, xmlDoc);
				}
				if (xpathXLValue.length() > 0) {

					if (nodeValue.equalsIgnoreCase(xpathXLValue)
							|| nodeValue.contains(xpathXLValue)) {
						
						counter = counter + 1;
						excelReadWrite
						.insertData(
								currentTestName,
								commonUtility.getcurrentDateTime() + "_"
										+ String.valueOf(counter),
								"Suceesfully Executed Request",
								" Verifying the node value is matching as expected. ",
								
										"Actual : " + nodeValue + " Expected : "
												+ xpathXLValue + " values are same",
								true, "Yes", "Actual Node Value: " + nodeValue,
								" Expected Node Value : "+ xpathXLValue);
					         test.log(LogStatus.INFO, "Actual : " + nodeValue + " Expected : "+xpathXLValue + " values are same");

						  }else{
							  counter = counter + 1;
							  excelReadWrite
										.insertFailedData(
												currentTestName,
												commonUtility.getcurrentDateTime() + "_"
														+ String.valueOf(counter),
														"Suceesfully Executed Request",
														" Verifying the node value is not matching as expected. ",
														
																"Actual : " + nodeValue + " Expected : "
																		+ xpathXLValue + " values are not same",
														true, "Yes", "Actual Node Value: " + nodeValue,
														" Expected Node Value : "+ xpathXLValue);
								Assert.assertFalse(true, "Actual :" + nodeValue + " Expected :"	+ xpathXLValue + " values are not same");
								  test.log(LogStatus.INFO,  "Actual : "+nodeValue + " Expected : "+ xpathXLValue + " values are not same");
							}
											
				}else if (xpathXLValue.length()==0) {
					if (nodeValue!=null){
						
						counter = counter + 1;
						excelReadWrite
						.insertData(
								currentTestName,
								commonUtility.getcurrentDateTime() + "_"
										+ String.valueOf(counter),
								"Suceesfully Executed Request",
								" Verifying the node value contains as not null",
								
								"The node contains value " +nodeValue,
								true, "Yes", "Actual Node Value: " + nodeValue,
								" Checking not null value in perticular node ");
					         test.log(LogStatus.INFO,  "Actual Node Value: " + nodeValue," Checking not null value in perticular node ");

						  }else{
							  counter = counter + 1;
							  excelReadWrite
										.insertFailedData(
												currentTestName,
												commonUtility.getcurrentDateTime() + "_"
														+ String.valueOf(counter),
														"Suceesfully Executed Request",
														"The node contains null value " +nodeValue,
														
																"Actual : " + nodeValue + " Expected : "
																		+ xpathXLValue + " values are not same",
														true, "Yes", "Actual Node Value: " + nodeValue,
														" Checking not null value in perticular node ");
								Assert.assertFalse(true, "Actual :" + nodeValue + " Expected :"	+ xpathXLValue + " values are not same");
								  test.log(LogStatus.INFO,  "Actual Node Value: " + nodeValue," Checking not null value in perticular node ");
							}
						}
			}
		}

	
	
	// Verifying the Fare Value
	public void verifyFares(Map<String, String> mapValue, String xmlDoc)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException, TransformerFactoryConfigurationError,
			TransformerException {
		String nodeValue = "";
		float subTotl = 0, tax = 0, Totl = 0;
		for (Map.Entry<String, String> entry : mapValue.entrySet()) {
			String xpathValue = entry.getKey();
			if (xpathValue.contains("#")) {
				String xpathNewValue = xpathValue.substring(1);
				if (xpathNewValue.contains("Taxes")) {
					nodeValue = getNodeValue(xpathNewValue, xmlDoc);
					tax = Float.parseFloat(nodeValue);
				} else if (xpathNewValue.contains("SubTotal")) {
					nodeValue = getNodeValue(xpathNewValue, xmlDoc);
					subTotl = Float.parseFloat(nodeValue);
				} else if (xpathNewValue.contains("DetailCurrencyPrice")) {
					nodeValue = getNodeValue(xpathNewValue, xmlDoc);
					Totl = Float.parseFloat(nodeValue);
				}
			}
		}
		if ((tax + subTotl) == Totl) {
			counter = counter + 1;
			excelReadWrite
			.insertData(
					currentTestName,
					commonUtility.getcurrentDateTime() + "_"
							+ String.valueOf(counter),
					"Suceesfully Executed Request",
					"Verifying that fare values are matching",
					"Both Nodes values are   matching. Value1 : "+(tax + subTotl)+" Value2 : "+Totl,
					true, "Yes", " fare values are matching"+"Actual : "+(tax + subTotl),
					"Expected : "+Totl);
		         test.log(LogStatus.INFO,
			   "Fare values are  matching");

			  }else{
				  counter = counter + 1;
				  excelReadWrite
							.insertFailedData(
									currentTestName,
									commonUtility.getcurrentDateTime() + "_"
											+ String.valueOf(counter),
											"Suceesfully Executed Request",
											"Verifying that fare values are not matching",
											"Both Nodes values are   matching. Value1 : "+(tax + subTotl)+" Value2 : "+Totl,
											true, "Yes", " fare values are matching"+"Actual : "+(tax + subTotl),
											"Expected : "+Totl);
					Assert.assertFalse(true, "Actual : "+(tax + subTotl)+"Expected : "+Totl);
					test.log(LogStatus.FAIL,
							"Actual : "+(tax + subTotl)+"Expected : "+Totl); 	
			  }
	}
	/**
	* Method Name: verifyAlliance
	* Purpose: To verify the Alliance
	* Flow:                             
	* Method Summary: Alliance is mentioned in the test data sheet(which is expected) verifies it from the actual response file  
	* Creation Date: 15-04-17 
	* Created By: Manjunatha
	* Modified Date:  24-05-17 
	* Modified By : A-7688 
	*/
	
	
	public void verifyAlliance(Map<String, String> mapValue,
			String xmlDoc) throws ParserConfigurationException, SAXException,
			IOException, XPathExpressionException,
			TransformerFactoryConfigurationError, TransformerException {

		

		ArrayList<String> StarAlliance = new ArrayList();
		ArrayList<String> SkyTeamAlliance = new ArrayList();
		ArrayList<String> OneWorldAlliance = new ArrayList();
		StarAlliance.add("AB");
		StarAlliance.add("AA");
		StarAlliance.add("BA");
		StarAlliance.add("CX");
		StarAlliance.add("AY");
		StarAlliance.add("IB");
		StarAlliance.add("JL");
		StarAlliance.add("LA");
		StarAlliance.add("MH");
		StarAlliance.add("MX");
		StarAlliance.add("QF");
		StarAlliance.add("QR");
		StarAlliance.add("RJ");
		StarAlliance.add("S7");
		StarAlliance.add("UL");
		StarAlliance.add("JJ");
		StarAlliance.add("US");

		SkyTeamAlliance.add("SU");
		SkyTeamAlliance.add("AM");
		SkyTeamAlliance.add("UX");
		SkyTeamAlliance.add("AF");
		SkyTeamAlliance.add("AZ");
		SkyTeamAlliance.add("OK");
		SkyTeamAlliance.add("CI");
		SkyTeamAlliance.add("MU");
		SkyTeamAlliance.add("CZ");
		SkyTeamAlliance.add("DL");
		SkyTeamAlliance.add("GA");
		SkyTeamAlliance.add("KQ");
		SkyTeamAlliance.add("KL");
		SkyTeamAlliance.add("KE");
		SkyTeamAlliance.add("ME");
		SkyTeamAlliance.add("RO");
		SkyTeamAlliance.add("VN");

		OneWorldAlliance.add("JP");
		OneWorldAlliance.add("A3");
		OneWorldAlliance.add("AC");
		OneWorldAlliance.add("CA");
		OneWorldAlliance.add("AI");
		OneWorldAlliance.add("NZ");
		OneWorldAlliance.add("NH");
		OneWorldAlliance.add("OZ");
		OneWorldAlliance.add("OS");
		OneWorldAlliance.add("KF");
		OneWorldAlliance.add("SN");
		OneWorldAlliance.add("OU");
		OneWorldAlliance.add("MS");
		OneWorldAlliance.add("ET");
		OneWorldAlliance.add("BR");
		OneWorldAlliance.add("LO");
		OneWorldAlliance.add("LH");
		OneWorldAlliance.add("SK");
		OneWorldAlliance.add("SQ");
		OneWorldAlliance.add("SA");
		OneWorldAlliance.add("JK");
		OneWorldAlliance.add("LX");
		OneWorldAlliance.add("TP");
		OneWorldAlliance.add("TG");
		OneWorldAlliance.add("TK");
		OneWorldAlliance.add("UA");
		
		for (Map.Entry<String, String> entry : mapValue.entrySet()) {

			String xpathValue = entry.getKey();
			String xpathXLValue = entry.getValue();
			boolean bolValue = false;
			ArrayList<String> nodeValue;
			String value = null;		
			if (xpathXLValue.equals("*O")) {
               for(String i:OneWorldAlliance){
				
				{
						//if (!xpathXLValue.contains("")) {
							nodeValue = getMultipleNodeValue(xpathValue, xmlDoc);
							for (int j = 0; j <= nodeValue.size() - 1; j++) {
								bolValue = false;
								value = nodeValue.get(j);
								if (StarAlliance.contains(value)) {
									bolValue = true;
								}

								if (bolValue == true) {
									System.out
											.println("Verification of" + value+ "is done:"
													);
								} else {
									System.out
											.println("Expected values of multiple nodes are not matching: Actual :"
													+ value
													+ " Expected: "
													+ xpathXLValue);
									Assert.fail("The expected and actual values are incorrect"
											+ "Actual :"
											+ value
											+ " Expected: " + xpathXLValue);
								}
							}

						}

					}
				
				//}
               }else if (xpathXLValue.equals("*A")) {               
       				
       				
       						//if (!xpathXLValue.contains("")) {
       							nodeValue = getMultipleNodeValue(xpathValue, xmlDoc);
       							for (int j = 0; j <= nodeValue.size() - 1; j++) {
       								bolValue = false;
       								value = nodeValue.get(j);
       								if (OneWorldAlliance.contains(value)) {
       									bolValue = true;
       								}

       								if (bolValue == true) {
       									System.out
       											.println("Verification of multiple node values is done:"
       													+ xpathXLValue);
       								} else {
       									System.out
       											.println("Expected values of multiple nodes are not matching: Actual :"
       													+ value
       													+ " Expected: "
       													+ xpathXLValue);
       									Assert.fail("The expected and actual values are incorrect"
       											+ "Actual :"
       											+ value
       											+ " Expected: " + xpathXLValue);
       								}
       							}

       						}

       					     			
			else if (xpathXLValue.equals("*S")) {               
   				
   				
				//	if (!xpathXLValue.contains("")) {
						nodeValue = getMultipleNodeValue(xpathValue, xmlDoc);
						for (int j = 0; j <= nodeValue.size() - 1; j++) {
							bolValue = false;
							value = nodeValue.get(j);
							if (OneWorldAlliance.contains(value)) {
								bolValue = true;
							}

							if (bolValue == true) {
								System.out
										.println("Verification of multiple node values is done:"
												+ xpathXLValue);
							} else {
								System.out
										.println("Expected values of multiple nodes are not matching: Actual :"
												+ value
												+ " Expected: "
												+ xpathXLValue);
								Assert.fail("The expected and actual values are incorrect"
										+ "Actual :"
										+ value
										+ " Expected: " + xpathXLValue);
							}
						}

					}

				
		}// end of map loop	
	
		
		
		/*
		 * boolean bolValue=false; ArrayList<String> nodeValue; String value =
		 * null; for (Map.Entry<String, String> entry : mapValue.entrySet()) {
		 * 
		 * String xpathValue = entry.getKey(); String xpathXLValue =
		 * entry.getValue();
		 * 
		 * if (!xpathXLValue.contains("")) { nodeValue =
		 * getMultipleNodeValue(xpathValue, xmlDoc); for(int
		 * j=0;j<=nodeValue.size()-1;j++) { bolValue=false;
		 * value=nodeValue.get(j); if(value.equalsIgnoreCase(xpathXLValue)){
		 * bolValue=true; }
		 * 
		 * if(bolValue==true){
		 * System.out.println("Verification of multiple node values is done:"
		 * +xpathXLValue); } else { System.out.println(
		 * "Expected values of multiple nodes are not matching: Actual :"
		 * +value+" Expected: "+xpathXLValue);
		 * Assert.fail("The expected and actual values are incorrect"
		 * +"Actual :"+value+" Expected: "+xpathXLValue); } }
		 * 
		 * }
		 * 
		 * }
		 */
	}// end of method
	/**
	* Method Name: verifyPassportNo
	* Purpose: To verify the Passport No
	* Flow:                             
	* Method Summary: Passport No is mentioned in the test data sheet(which is expected) verifies it from the actual response file  
	* Creation Date: 24-05-17
	* Created By: A-7688 
	* Modified Date:   
	* Modified By : 
	*/
	
	public void verifyPassportNo(Map<String, String> mapValue, String xmlDoc)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, TransformerFactoryConfigurationError,
			TransformerException {

		String nodeValue = "";
		String DocumentId = null;
		for (Map.Entry<String, String> entry : mapValue.entrySet()) {
			String xpathValue = entry.getKey();
			if (xpathValue.contains("#")) {
				String xpathNewValue = xpathValue.substring(1);
				if (xpathNewValue.contains("DocumentId")) {
					nodeValue = getNodeValue(xpathNewValue, xmlDoc);
					DocumentId = nodeValue.toString();
					if (DocumentId.isEmpty()) {
						  counter = counter + 1;
                          excelReadWrite
                                             .insertFailedData(
                                                           currentTestName,
                                                           commonUtility.getcurrentDateTime() + "_"
                                                                        + String.valueOf(counter),
                                                                        "Verify Passport Number",
                                  	                                  "Passport Number is empty",
                                  	                                 "Passport Number is "+ DocumentId,
                                                           false, "NO", "Passport Number verified",
                                                           "Passport Number is empty");
                               Assert.assertFalse(true, "Passport Number is empty");
                               test.log(LogStatus.FAIL,
                                             "Passport Number is empty"); 
                        }
					else{
						System.out.println("Passport Number is " + DocumentId);
	                     
	                     counter = counter + 1;
	                     excelReadWrite
	                     .insertData(
	                                  currentTestName,
	                                  commonUtility.getcurrentDateTime() + "_"
	                                                + String.valueOf(counter),
	                                  "Verify Passport Number",
	                                  "Passport Number is not empty",
	                                  "Passport Number is "+ DocumentId,
	                                  true, "Yes", "Passport Number Verified",
	                                  "Passport Number Verified");
	                       test.log(LogStatus.INFO,
	                        "Passport Number Verified");
					}
						}
					}
		}}

	/**
	* Method Name: verifyBookingCancel
	* Purpose: To verify the Booking Status
	* Flow:                             
	* Method Summary: Verifies the Cancelled Booking status from actual response file  
	* Creation Date: 24-05-17
	* Created By: A-7688 
	* Modified Date:   
	* Modified By : 
	*/
	
	public void verifyBookingCancel(Map<String, String> mapValue, String xmlDoc)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, TransformerFactoryConfigurationError,
			TransformerException {
		String nodeValue = "";
		String BookingStatus = null;
		for (Map.Entry<String, String> entry : mapValue.entrySet()) {
			String xpathValue = entry.getKey();
			if (xpathValue.contains("#")) {
				String xpathNewValue = xpathValue.substring(1);
				if (xpathNewValue.contains("BookingStatus")) {
					nodeValue = getNodeValue(xpathNewValue, xmlDoc);
					BookingStatus = nodeValue.toString();
					{if (BookingStatus.equals("CANCELLED")) {
						  System.out.println("Booking status is " + BookingStatus);
		                     
		                     counter = counter + 1;
		                     excelReadWrite
		                     .insertData(
		                                  currentTestName,
		                                  commonUtility.getcurrentDateTime() + "_"
		                                                + String.valueOf(counter),
		                                  "Verify Booking Status",
		                                  "Verify Booking Status",
		                                  "Booking status is "+ BookingStatus,
		                                  true, "Yes", "Booking status CANCELLED",
		                                  "Booking status CANCELLED");
		                       test.log(LogStatus.INFO,
		                        "Booking status CANCELLED");

		                       }else{
		                             counter = counter + 1;
		                             excelReadWrite
		                                                .insertFailedData(
		                                                              currentTestName,
		                                                              commonUtility.getcurrentDateTime() + "_"
		                                                                           + String.valueOf(counter),
		                                                                           "Verify Booking Status",
		                                     	                                  "Verify Booking Status",
		                                     	                                 "Booking status is "+ BookingStatus,
		                                                              false, "NO", "Booking not Cancelled",
		                                                              "Booking Cancelled");
		                                  Assert.assertFalse(true, "Booking not Cancelled");
		                                  test.log(LogStatus.FAIL,
		                                                "Booking not Cancelled"); 
		                           }
		       }

					

				}
			}}
		}

	
	
	/**
	* Method Name: verifyBookingStatus
	* Purpose: To verify the Booking Status
	* Flow:                             
	* Method Summary: Verifies the Confirmed Booking status from actual response file  
	* Creation Date: 24-05-17
	* Created By: A-7688 
	* Modified Date:   
	* Modified By : 
	*/

	public void verifyBookingStatus(Map<String, String> mapValue, String xmlDoc)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, TransformerFactoryConfigurationError,
			TransformerException {
		String nodeValue = "";
		String BookingStatus = null;
		for (Map.Entry<String, String> entry : mapValue.entrySet()) {
			String xpathValue = entry.getKey();
			if (xpathValue.contains("#")) {
				String xpathNewValue = xpathValue.substring(1);
				if (xpathNewValue.contains("BookingStatus")) {
					nodeValue = getNodeValue(xpathNewValue, xmlDoc);
					BookingStatus = nodeValue.toString();
					if (BookingStatus.equals("CONFIRMED")) {
	                     System.out.println("Booking status is " + BookingStatus);
	                     
	                     counter = counter + 1;
	                     excelReadWrite
	                     .insertData(
	                                  currentTestName,
	                                  commonUtility.getcurrentDateTime() + "_"
	                                                + String.valueOf(counter),
	                                  "Verify Booking Status",
	                                  "Verify Booking Status",
	                                  "Booking status is "+ BookingStatus,
	                                  true, "Yes", "Booking status Confirmed",
	                                  "Booking status Confirmed");
	                       test.log(LogStatus.INFO,
	                        "Booking status Confirmed");

	                       }else{
	                             counter = counter + 1;
	                             excelReadWrite
	                                                .insertFailedData(
	                                                              currentTestName,
	                                                              commonUtility.getcurrentDateTime() + "_"
	                                                                           + String.valueOf(counter),
	                                                                           "Verify Booking Status",
	                                     	                                  "Verify Booking Status",
	                                     	                                 "Booking status is "+ BookingStatus,
	                                                              false, "NO", "Booking not Confirmed",
	                                                              "Booking status Confirmed");
	                                  Assert.assertFalse(true, "Booking not Confirmed");
	                                  test.log(LogStatus.FAIL,
	                                                "Booking not Confirmed"); 
	                           }
	       }

				

			}
		}
	}


	/**
	* Method Name: verifyRefund
	* Purpose: To verify the Refund amount
	* Flow:                             
	* Method Summary: Verifies the Confirmed Booking status from actual response file  
	* Creation Date: 24-05-17
	* Created By: A-7688 
	* Modified Date:   
	* Modified By : 
	*/

	
	public void verifyRefund(Map<String, String> mapValue, String xmlDoc)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException, TransformerFactoryConfigurationError,
			TransformerException {
		String nodeValue = "";
		float fee2 = 0, fee1 = 0, TotalPayment = 0, refundAmt = 0;
		for (Map.Entry<String, String> entry : mapValue.entrySet()) {
			String xpathValue = entry.getKey();
			if (xpathValue.contains("#")) {
				String xpathNewValue = xpathValue.substring(1);
				if (xpathNewValue.contains("Fee[1]")) {
					nodeValue = getNodeValue(xpathNewValue, xmlDoc);
					fee1 = Float.parseFloat(nodeValue);
				} else if (xpathNewValue.contains("Fee[2]")) {
					nodeValue = getNodeValue(xpathNewValue, xmlDoc);
					if (nodeValue!=null) {
						fee2 = Float.parseFloat(nodeValue);
						
					} else {
						fee2 = 0;
					}
				} else if (xpathNewValue.contains("RequestedAmount")) {
					nodeValue = getNodeValue(xpathNewValue, xmlDoc);
					refundAmt = Float.parseFloat(nodeValue);
				} else if (xpathNewValue.contains("PaymentAmount")) {
					nodeValue = getNodeValue(xpathNewValue, xmlDoc);
					TotalPayment = Float.parseFloat(nodeValue);

				}
			}
		}
		if ((fee1 + fee2 + refundAmt) == TotalPayment) {
            System.out.println("Refund Amount Verified");
            
            counter = counter + 1;
            excelReadWrite
            .insertData(
                         currentTestName,
                         commonUtility.getcurrentDateTime() + "_"
                                       + String.valueOf(counter),
                         "Verify Refund amount",
                         "Verify Refund amount",
                         "Refund Amount is + " + refundAmt,
                         true, "Yes", "Refund amount verified",
                         "Refund amount verified");
              test.log(LogStatus.INFO,
               "Refund amount verified");

              }else{
                    counter = counter + 1;
                    excelReadWrite
                                       .insertFailedData(
                                                     currentTestName,
                                                     commonUtility.getcurrentDateTime() + "_"
                                                                  + String.valueOf(counter),
                                                                  "Verify Refund amount",
                                                                  "Verify Refund amount",
                                                                  "Refund Amount is + " + refundAmt,
                                                     false, "NO", "Refund amount not verified",
                                                     "Refund amount not verified");
                         Assert.assertFalse(true, "Refund amount verified");
                         test.log(LogStatus.FAIL,
                                       "Refund amount not verified"); 
                  }
}

	
	

	/**
	* Method Name: getCountFlight
	* Purpose: To verify the Flight Count
	* Flow:                             
	* Method Summary: Verifies the Confirmed Booking status from actual response file  
	* Creation Date: 24-05-17
	* Created By: A-7688 
	* Modified Date:   
	* Modified By : 
	*/
	
	public static void getCountFlight(String filepath)
			throws ParserConfigurationException, SAXException, IOException {
		int noOfFlights = 19;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);

		NodeList list = doc.getElementsByTagName("SegmentReferences");
		int noofpassengers = list.getLength();
		System.out.println("Total of flights : " + noOfFlights);

		if (noofpassengers == noOfFlights) {
			System.out.println("The Actaul No Of Flights : " + noofpassengers
					+ "Expected No Of Flights : " + noOfFlights + "are Same");
		} else {
			System.out.println("The Actaul No Of Flights : " + noofpassengers
					+ "Expected No Of Flights : " + noOfFlights
					+ "are different");
		}

	}

	// Getting the values from the Excel and storing it in a map object
	/*public Map<String, String> buildMapFromXls(String table, String sheet)
			throws IOException {
		int startRow = 0, colNum = 0;

		Map<String, String> map = new HashMap<String, String>();

		startRow = startRowNum(table, sheet);
		colNum = Excel.getColumnCount(path, sheet, startRow);
		for (int col = 1; col < colNum - 1; col++) {
			map.putIfAbsent(Excel.getCellValue(path, sheet, startRow, col),
					Excel.getCellValue(path, sheet, startRow + 1, col));
		}

		return map;
	}*/
	
	public Map<String, String> buildMapFromXls(String table, String sheet)
			throws Exception {
		int startRow = 0, colNum = 0, num = 0;
		String key, keyValue;
		Map<String, String> map = new HashMap<String, String>();

		startRow = startRowNum(table, sheet);
		colNum = Excel.getColumnCount(path, sheet, startRow);
		for (int col = 1; col < colNum - 1; col++) {

			key = Excel.getCellValue(path, sheet, startRow, col);
			keyValue = Excel.getCellValue(path, sheet, startRow + 1, col);

			if (keyValue.contains("Today") || keyValue.contains("today")
					|| keyValue.contains("TODAY")) {
				if (keyValue.contains("+")) {
					num = Integer.parseInt(keyValue.split("\\+")[1]);
					keyValue = Common_Xml.getRequiredDate(num);
					map.put(key, keyValue);
				} else {
					num = Integer.parseInt(keyValue.split("-")[1]);
					keyValue = Common_Xml.getRequiredDate(-num);
					map.put(key, keyValue);
				}

			} else {
				map.put(key, keyValue);
			}
		}

		return map;
	}

	public static String getRequiredDate(int days) throws Exception {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = DateUtils.addDays(new Date(), days);
		String date = dateFormat.format(newDate);
		return date;
	}

		// Verifying the multiple node Value as not NULL or compare with particular node values
	public void verifyMultipleNodeValue(Map<String, String> mapValue, String xmlDoc)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException, TransformerFactoryConfigurationError,
			TransformerException {
		boolean bolValue=false;
		ArrayList<String> nodeValues;
		String value = null;
		for (Map.Entry<String, String> entry : mapValue.entrySet()) {
			
			String xpathValue = entry.getKey();
			String xpathXLValue = entry.getValue();
         int NodesCount=0;
         int nodeCnt=0;
			if (xpathXLValue.length()>0) 
			{				
				nodeValues = getMultipleNodeValue(xpathValue, xmlDoc);
				if(xpathXLValue.indexOf("|")>0){
					String[] arrXpathValues = xpathXLValue.split("\\|");
					for(int i=0;i<=arrXpathValues.length-1;i++)	{
						nodeCnt=getCountComparedNodevalues(xpathValue, arrXpathValues[i], xmlDoc);
						NodesCount+=nodeCnt;
					}
					
				}else{					
					NodesCount=getCountComparedNodevalues(xpathValue, xpathXLValue, xmlDoc);
					NodesCount+=nodeCnt;
				}
				if(nodeValues.size()==NodesCount){					
					counter = counter + 1;
					excelReadWrite
					.insertData(
							currentTestName,
							commonUtility.getcurrentDateTime() + "_"
									+ String.valueOf(counter),
							"Suceesfully Executed Request",
							" Verifying the multiple node values are matching as expected. ",
							
									"Actual : " + nodeValues.size() + " Expected : "
											+ NodesCount+":"+xpathXLValue + " values are same",
							true, "Yes", "Actual Node Count: " + nodeValues.size(),
							" Expected Node Value and count : "+ NodesCount+":"+xpathXLValue);
				         test.log(LogStatus.INFO, "Actual count : " + nodeValues.size() + " Expected count : "+NodesCount+":"+xpathXLValue + " values are same");

					  }else{
						  counter = counter + 1;
						  excelReadWrite
									.insertFailedData(
											currentTestName,
											commonUtility.getcurrentDateTime() + "_"
													+ String.valueOf(counter),
													"Suceesfully Executed Request",
													" Verifying the multiple node values are matching as expected. ",

													"Actual : " + nodeValues.size() + " Expected : "
															+ NodesCount+":"+xpathXLValue + " values are same",
											true, "Yes", "Actual Node Count: " + nodeValues.size(),
											" Expected Node Value and count : "+ NodesCount+":"+xpathXLValue);
							Assert.assertFalse(true, "Actual Node Count: " + nodeValues.size()+
									" Expected Node Value and count : "+ NodesCount+":"+xpathXLValue + " values are not same");
							  test.log(LogStatus.INFO,  "Actual Node Count: " + nodeValues.size()+
										" Expected Node Value and count : "+ NodesCount+":"+xpathXLValue + " values are not same");
						}				
				
			}else if (xpathXLValue.length()==0) {
				nodeValues = getMultipleNodeValue(xpathValue, xmlDoc);
				for(int j=0;j<=nodeValues.size()-1;j++)
				{
					bolValue=false;
					 value=nodeValues.get(j);
					 if (value!=null){
						bolValue=true;
					}
					
					if(bolValue==true){
						System.out.println("Verification of multiple node values is done:"+xpathXLValue);
					   }
					else
					{
						System.out.println("Node does not contains not null values ");
						Assert.fail("Node does not contains not null values ");						
					}
				}
			}
		}
	}
	
	//Comparing values got  from xmlDoc for particular nodes with Expected value passed in XL sheet
	public int getCountComparedNodevalues(String xpath,String nodeValue,String xmlDoc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException{
		
		ArrayList<String> nodeValues=null;
		int counter=0;
		String value=null;
		nodeValues = getMultipleNodeValue(xpath, xmlDoc);
		for(int j=0;j<=nodeValues.size()-1;j++)
		{
			 value=nodeValues.get(j);
			 if(value.equalsIgnoreCase(nodeValue)){
				 counter++;
			 }
					
		}
		return counter;
	}
	
		/*
		 * boolean bolValue=false; ArrayList<String> nodeValue; String value =
		 * null; for (Map.Entry<String, String> entry : mapValue.entrySet()) {
		 * 
		 * String xpathValue = entry.getKey(); String xpathXLValue =
		 * entry.getValue();
		 * 
		 * if (!xpathXLValue.contains("")) { nodeValue =
		 * getMultipleNodeValue(xpathValue, xmlDoc); for(int
		 * j=0;j<=nodeValue.size()-1;j++) { bolValue=false;
		 * value=nodeValue.get(j); if(value.equalsIgnoreCase(xpathXLValue)){
		 * bolValue=true; }
		 * 
		 * if(bolValue==true){
		 * System.out.println("Verification of multiple node values is done:"
		 * +xpathXLValue); } else { System.out.println(
		 * "Expected values of multiple nodes are not matching: Actual :"
		 * +value+" Expected: "+xpathXLValue);
		 * Assert.fail("The expected and actual values are incorrect"
		 * +"Actual :"+value+" Expected: "+xpathXLValue); } }
		 * 
		 * }
		 * 
		 * }
		 */
	

	// Getting the Multiple Node Values
	public static ArrayList<String> getMultipleNodeValue(String xpathValue,
			String xmlDoc) throws ParserConfigurationException, SAXException,
			IOException, XPathExpressionException,
			TransformerFactoryConfigurationError, TransformerException {
		ArrayList<String> nodeValues = new ArrayList<String>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlDoc);
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();

			// read a nodelist using xpath
			NodeList nodeList = (NodeList) xpath.evaluate(xpathValue, doc,
					XPathConstants.NODESET);
			System.out.println(nodeList.getLength());
			for (int i = 0; i <= nodeList.getLength() - 1; i++) {
				String nodeValue = nodeList.item(i).getTextContent();
				nodeValues.add(i, nodeValue);
			}
			return nodeValues;
		} catch (Exception e) {
		}
		return null;
	}

	private static Object getCellValue(Cell cell) {
		HSSFCell hssfCell = (HSSFCell) cell;
		if (XSSFCell.CELL_TYPE_NUMERIC == hssfCell.getCellType()) {
			DecimalFormat df = new DecimalFormat("#.000000000");
			String numeric = df.format(cell.getNumericCellValue());
			numeric = numeric + "";
			String[] strArray = (numeric.replace(".", "-")).split("-");
			if (strArray.length > 1) {
				if ((strArray[1].replace("0", "")).trim().length() == 0) {
					numeric = strArray[0];
				}
			}
			return numeric;

		} else if (XSSFCell.CELL_TYPE_STRING == hssfCell.getCellType()) {
			return hssfCell.getStringCellValue();
		} else if (XSSFCell.CELL_TYPE_NUMERIC == hssfCell.getCellType()) {
			return hssfCell.getNumericCellValue();
		} else if (XSSFCell.CELL_TYPE_BOOLEAN == hssfCell.getCellType()) {
			return hssfCell.getBooleanCellValue();
		} else if (XSSFCell.CELL_TYPE_BLANK == hssfCell.getCellType()) {
			return "";
		}
		return "";
	}

	public static int startRowNum(String str, String sheetName)
			throws IOException {
		FileInputStream fileInputStream = new FileInputStream(path);
		Workbook workBook = null;
		workBook = new HSSFWorkbook(fileInputStream);
		Sheet sheet = workBook.getSheet(sheetName);
		Iterator<Row> rows = sheet.rowIterator();

		int rowIndex = -1;

		Row row = null;

		ArrayList<Integer> rowNum = new ArrayList<Integer>();

		String rowNums = "";
		while (rows.hasNext()) {
			row = rows.next();
			for (Cell cell : row) {

				if (String.valueOf(getCellValue(cell)).equals(str)) {

					rowIndex = row.getRowNum();
					rowNum.add(rowIndex);
				}
			}

		}

		// extracting

		rowNums = rowNum.get(0).toString();

		int startrow = Integer.parseInt(rowNums);

		return startrow;

	}

	public static int endRowNum(String str, String sheetName)
			throws IOException {
		FileInputStream fileInputStream = new FileInputStream(path);
		Workbook workBook = null;
		workBook = new HSSFWorkbook(fileInputStream);
		Sheet sheet = workBook.getSheet(sheetName);
		Iterator<Row> rows = sheet.rowIterator();

		int rowIndex = -1;

		Row row = null;

		ArrayList<Integer> rowNum = new ArrayList<Integer>();

		String rowNums = "";
		while (rows.hasNext()) {
			row = rows.next();
			for (Cell cell : row) {

				if (String.valueOf(getCellValue(cell)).equals(str)) {

					rowIndex = row.getRowNum();
					rowNum.add(rowIndex);
				}
			}

		}

		// extracting

		rowNums = rowNum.get(1).toString();

		int startrow = Integer.parseInt(rowNums);

		return startrow;

	}

	// Converting Xml Doc to String
	public static String convertXMLFileToString(String fileName) {
		// converting XML to String
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			InputStream inputStream = new FileInputStream(new File(fileName));
			org.w3c.dom.Document doc = documentBuilderFactory
					.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance()
					.newTransformer();
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			return stw
					.toString()
					.replace(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>",
							"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	//changed
	/*public static String postSOAPRequestByUrlAndRequest(String url,
			String requests) throws ParseException, IOException {
		String result = "";
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory
					.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory
					.createConnection();

			SOAPMessage soapMsg = getSoapMessageFromString(requests);
			SOAPMessage soapResponse = soapConnection.call(soapMsg, url);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapResponse.writeTo(out);
			result = new String(out.toByteArray());
			// Return soap response xml
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}*/
//changed
	
	// Posting the request by passing URL and Request as a String
	/*public static String postSOAPRequestByUrlAndRequest(String url,
			String requests) throws ParseException, IOException {
		String result = "";
		int counter=0;
		String responseInline=null;
		SOAPMessage soapResponse=null;
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory
					.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory
					.createConnection();

			SOAPMessage soapMsg = getSoapMessageFromString(requests);
			soapResponse = soapConnection.call(soapMsg, url);
		
			responseInline=soapResponse.getSOAPBody().getFault().getFaultCode().toString();
			if(responseInline.contains("200")){
			counter = counter + 1;
			excelReadWrite
			.insertData(
					currentTestName,
					commonUtility.getcurrentDateTime() + "_"
							+ String.valueOf(counter),
					"Suceesfully Executed Request",
					"Status Code of Server Response : " +responseInline,
					soapResponse.toString(),
					true, "Yes", " Successful Response from the Server",
					"Successful Response from the Server");
		         test.log(LogStatus.INFO,
			   "Successful Response from the Server");

			  }else{
				  counter = counter + 1;
				  excelReadWrite
							.insertFailedData(
									currentTestName,
									commonUtility.getcurrentDateTime() + "_"
											+ String.valueOf(counter),
									"Request could not be executed Successfully",
									"Status Code of Server Response : " +responseInline,
									soapResponse.toString(),
									false, "", "Failed Response from the Server",
									"Successful Response from the Server");
					Assert.assertFalse(true, "Failed Response from the Server");
					test.log(LogStatus.FAIL,
							"Failed Response from the Server");
				}
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapResponse.writeTo(out);
			result = new String(out.toByteArray());
			// Return soap response xml
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			 counter = counter + 1;
			  excelReadWrite
						.insertFailedData(
								currentTestName,
								commonUtility.getcurrentDateTime() + "_"
										+ String.valueOf(counter),
								"Request could not be executed Successfully",
								"Status Code of Server Response : " +responseInline,
								soapResponse.toString(),
								false, "", "Failed Response from the Server",
								"Successful Response from the Server");
				Assert.assertFalse(true, "Failed Response from the Server");
				test.log(LogStatus.FAIL,
					"Failed Response from the Server");		
			
		}
		return result;
	}*/
	
	
	public static String postSOAPRequestByUrlAndRequest(String url,
			String requests) throws ParseException, IOException {
		String result = "";
		int counter=0;
		String responseInline=null;		
		String responseString=null;
		 HttpResponse response=null;
		try {
			
			 String body = requests;  //ConvertXmltoString.convertXMLFileToString(requests);
			    StringEntity stringEntity = new StringEntity(body, "UTF-8");
			    stringEntity.setChunked(true);	   
			    HttpPost httpPost = new HttpPost(url);
			    httpPost.setEntity(stringEntity);
			    // Execute and get the response.
			    HttpClient httpClient = new DefaultHttpClient();
			    response = httpClient.execute(httpPost);
			   responseInline = response.getStatusLine().toString();   		   
			
			if(responseInline.contains("200")){
			counter = counter + 1;
			excelReadWrite
			.insertData(
					currentTestName,
					commonUtility.getcurrentDateTime() + "_"
							+ String.valueOf(counter),
					"Suceesfully Executed Request",
					"Status Code of Server Response : " +responseInline,
					response.toString(),
					true, "Yes", " Successful Response from the Server",
					"Successful Response from the Server");
		         test.log(LogStatus.INFO,
			   "Successful Response from the Server");

			  }else{
				  counter = counter + 1;
				  excelReadWrite
							.insertFailedData(
									currentTestName,
									commonUtility.getcurrentDateTime() + "_"
											+ String.valueOf(counter),
									"Request could not be executed Successfully",
									"Status Code of Server Response : " +responseInline,
									response.toString(),
									false, "", "Failed Response from the Server",
									"Successful Response from the Server");
					Assert.assertFalse(true, "Failed Response from the Server");
					test.log(LogStatus.FAIL,
							"Failed Response from the Server");
				}
			
			 responseString = new BasicResponseHandler()
				.handleResponse(response);
			// Return soap response xml
		return responseString;
			
		} catch (Exception e) {
			e.printStackTrace();
			 counter = counter + 1;
			  excelReadWrite
						.insertFailedData(
								currentTestName,
								commonUtility.getcurrentDateTime() + "_"
										+ String.valueOf(counter),
								"Request could not be executed Successfully",
								"Status Code of Server Response : " +responseInline,
								response.toString(),
								false, "", "Failed Response from the Server",
								"Successful Response from the Server");
				Assert.assertFalse(true, "Failed Response from the Server");
				test.log(LogStatus.FAIL,
					"Failed Response from the Server");		
			
		}
		return result;
	}


	public static SOAPMessage getSoapMessageFromString(String xml)
			throws SOAPException, IOException {

		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory
				.createMessage(
						new MimeHeaders(),
						new ByteArrayInputStream(xml.getBytes(Charset
								.forName("UTF-8"))));

		return message;
	}

	// Writing Xml Response responseXmlFile
	public static void dumpxmltooutputdir(String requestInXml, String DestResXml) {
		try {
			// File file=new File(DestResXml);
			java.io.FileWriter fw = new java.io.FileWriter(DestResXml);
			fw.write(requestInXml);
			fw.close();
		} catch (Exception e) {
		}
	}

	public static String getUrlKey(String table) throws IOException {
		int startNm = startRowNum(table, "Urls");
		String getUrl = Excel.getCellValue(path, "Urls", startNm, 1);
		return getUrl;
	}

	public static void copyFolder() {

		File source = new File(Excel.getPropertyValue("xmlfilepath"));
		File dest = new File(Excel.getPropertyValue("outputxml"));

	}

	public static void getCountPassenger(String filepath)
			throws ParserConfigurationException, SAXException, IOException {
		int totalnumberofPassenger = 5;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);

		NodeList list = doc.getElementsByTagName("Passenger");
		int noofpassengers = list.getLength();
		System.out.println("Total of elements : " + noofpassengers);

		if (noofpassengers == totalnumberofPassenger) {
			System.out.println("The Actaul Passengers:" + noofpassengers
					+ "Expected Passengers:" + totalnumberofPassenger
					+ "are Same");
		} else {
			System.out.println("The Actaul Passengers:" + noofpassengers
					+ "Expected Passengers:" + totalnumberofPassenger
					+ "are different");
		}

	}

	public static String postRestRequestByUrlAndRequest(String url,
			String requests) throws AuthenticationException {
		

		String responseString = "";
		int counter=0;
		HttpResponse response = null;
		String responseInline = null;
		try {
			// create HTTP Client
			HttpClient httpClient = HttpClientBuilder.create().build();
			// Create new getRequest with below mentioned URL
			HttpPost getRequest = new HttpPost(url);
			UsernamePasswordCredentials crds = new UsernamePasswordCredentials(
					"ALL@IVV", "");
			getRequest.addHeader(new BasicScheme().authenticate(crds,
					getRequest, null));

			getRequest.addHeader("x-auth-channel", "ALL@IVV");

			// getRequest.addHeader("accept", "application/xml");

			getRequest.addHeader("Content-Type", "application/xml");

			// getRequest.addHeader("x-session-token",
			// "SVZWLzY5NzMyZjM1LWQyZjItNGQ4ZS1hZGUxLTJjMTQ3OTVlZDU2Yy8xNDg4ODg1OTc3MDAw");

			StringEntity input = new StringEntity(requests, "UTF-8");
			input.setContentType("text/xml");
			getRequest.setEntity(input);

			 response = httpClient.execute(getRequest);
			 
			System.out.println(response.toString());
			System.out.println("**********" + response.getStatusLine());
			responseInline=response.getStatusLine().toString();			
			
			
   if (responseInline.contains("200")){
	counter = counter + 1;
	excelReadWrite
	.insertData(
			currentTestName,
			commonUtility.getcurrentDateTime() + "_"
					+ String.valueOf(counter),
			"Suceesfully Executed Request",
			"Status Code of Server Response : " +responseInline,
			response.toString(),
			true, "Yes", " Successful Response from the Server",
			"Successful Response from the Server");
         test.log(LogStatus.INFO,
	   "Successful Response from the Server");

	  }else{
		  counter = counter + 1;
		  excelReadWrite
					.insertFailedData(
							currentTestName,
							commonUtility.getcurrentDateTime() + "_"
									+ String.valueOf(counter),
							"Request could not be executed Successfully",
							"Status Code of Server Response : " +responseInline,
							response.toString(),
							false, "", "Failed Response from the Server",
							"Successful Response from the Server");
			Assert.assertFalse(true, "Failed Response from the Server");
			test.log(LogStatus.FAIL,
					"Failed Response from the Server");
		}
    
   responseString = new BasicResponseHandler()
	.handleResponse(response);
		} catch (ClientProtocolException e) {
			
		e.printStackTrace();
		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return responseString;
	}

	public static String postRestRequestByUrlAndRequest(String url,
			String requests, String sessionToken)
			throws ClientProtocolException, IOException {
		int counter=0;
		String responseString = "";
		HttpResponse response=null;
		String 	responseInline=null;

		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			// Create new getRequest with below mentioned URL
			HttpPost getRequest = new HttpPost(url);
			// UsernamePasswordCredentials crds = new
			// UsernamePasswordCredentials(
			// "ALL@IVV", "838995afc6c9a1ce871e778b7c9592c3d93658b1");
			/*
			 * UsernamePasswordCredentials crds = new
			 * UsernamePasswordCredentials( "ALL@IVV", "");
			 * getRequest.addHeader(new BasicScheme().authenticate(crds,
			 * getRequest, null));
			 */

			getRequest.addHeader("x-auth-channel", "ALL@IVV");

			// getRequest.addHeader("accept", "application/xml");

			getRequest.addHeader("Content-Type", "application/xml");

			getRequest.addHeader("x-session-token", sessionToken);

			StringEntity input = new StringEntity(requests, "UTF-8");
			input.setContentType("text/xml");
			getRequest.setEntity(input);

			
			 response = httpClient.execute(getRequest);
			 
				System.out.println(response.toString());
				System.out.println("**********" + response.getStatusLine());
				responseInline=response.getStatusLine().toString();	
			System.out.println(response.toString());
			 if (responseInline.contains("200")){
					counter = counter + 1;
					excelReadWrite
					.insertData(
							currentTestName,
							commonUtility.getcurrentDateTime() + "_"
									+ String.valueOf(counter),
							"Suceesfully Executed Request",
							"Status Code of Server Response : " +responseInline,
							response.toString(),
							true, "Yes", " Successful Response from the Server",
							"Successful Response from the Server");
				         test.log(LogStatus.INFO,
					   "Successful Response from the Server");

					  }else{
						  counter = counter + 1;
						  excelReadWrite
									.insertFailedData(
											currentTestName,
											commonUtility.getcurrentDateTime() + "_"
													+ String.valueOf(counter),
											"Request could not be executed Successfully",
											"Status Code of Server Response : " +responseInline,
											response.toString(),
											false, "", "Failed Response from the Server",
											"Successful Response from the Server");
							Assert.assertFalse(true, "Failed Response from the Server");
							test.log(LogStatus.FAIL,
									"Failed Response from the Server");
						}
				    
				   responseString = new BasicResponseHandler()
					.handleResponse(response);
						} catch (ClientProtocolException e) {
							
						e.printStackTrace();
						
						} catch (IOException e) {
						e.printStackTrace();
						}
						return responseString;
					}
			

	public static void formatXMLFile(String file) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(
				new InputStreamReader(new FileInputStream(file))));
		TransformerFactory transformerfactory = TransformerFactory
				.newInstance();
		// transformerfactory.setAttribute("indent-number", new Integer(4));
		Transformer xformer = transformerfactory.newTransformer();
		xformer.setOutputProperty(OutputKeys.METHOD, "xml");
		xformer.setOutputProperty(OutputKeys.INDENT, "yes");

		xformer.setOutputProperty("{http://xml.apache.org/xslt};indent-amount",
				"4");
		xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		Source source = new DOMSource(document);
		Result result = new StreamResult(new File(file));
		xformer.transform(source, result);
	}

	public String getSessionToken(WebDriver driver) throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[contains(@name,'username')]"))
				.sendKeys(Excel.getPropertyValue("Username"));
		driver.findElement(By.xpath("//input[contains(@name,'password')]"))
				.sendKeys(Excel.getPropertyValue("Password"));
		driver.findElement(By.xpath("//input[@name='submit']")).click();
		Thread.sleep(3000);
		if (driver.manage().getCookieNamed("x-session-token").getValue()!=null)
		{
        
        counter = counter + 1;
        excelReadWrite
        .insertData(
                     currentTestName,
                     commonUtility.getcurrentDateTime() + "_"
                                   + String.valueOf(counter),
                     "Verify session token allocation",
                     "Session Token allocated",
                     "1. Enter Username, 2. Enter password, 3. Click Login Button",
                     true, "Yes", "Session Token Contains Not null Value",
                     "Session Token Contains Not null Value");
          test.log(LogStatus.INFO,
           "Session Token Contains Not null Value");

          }else{
                counter = counter + 1;
                excelReadWrite
                                   .insertFailedData(
                                                 currentTestName,
                                                 commonUtility.getcurrentDateTime() + "_"
                                                              + String.valueOf(counter),
                                                 "Verify session token allocation",
                                                 "Session Token Not allocated",
                                                 "1. Enter Username, 2. Enter password, 3. Click Login Button",
                                                 false, "NO", "Session Token contains null Value",
                                                 "Session Token Contains null Value");
                     Assert.assertFalse(true, "Session Token contains not Value");
                     test.log(LogStatus.FAIL,
                                   "Session Token contains null Value"); 
              }


			
		return (driver.manage().getCookieNamed("x-session-token")).getValue();
	}
public int getNodeCount(String xpathValue,String xmlDoc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException
	{
		 ArrayList<String> nodeValue = getMultipleNodeValue(xpathValue, xmlDoc);
		int nodeNumrs = nodeValue.size();
		return nodeNumrs;
	}
		
	
	
	/**
* Method Name: verifyPassCount
* Purpose: To verify Passenger Reference ID with Added item in modify request(Guest count)
* Flow:                             
* Method Summary: Verifies the Passenger Reference ID
* Creation Date: 22-05-17
* Created By: Shalini P 
* Modified Date:   
* Modified By : 
*/

	public void verifyPassCount(int nodCount1,int nodCount2) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException
	{
		if(nodCount1==nodCount2)
		{
			System.out.println("Both node count are matching");
			
			counter = counter + 1;
			excelReadWrite
			.insertData(
					currentTestName,
					commonUtility.getcurrentDateTime() + "_"
							+ String.valueOf(counter),
					"Suceesfully Executed Request",
					"Verifying that Node values are matching",
					"Both Nodes values are   matching. Value1 : "+nodCount1+" Value2 : "+nodCount2,
					true, "Yes", "Both node values are matching",
					"Both node values should match");
		         test.log(LogStatus.INFO,
			   "Both node values should matching");

			  }else{
				  counter = counter + 1;
				  excelReadWrite
							.insertFailedData(
									currentTestName,
									commonUtility.getcurrentDateTime() + "_"
											+ String.valueOf(counter),
									"Verifying that Node values are matching",
									"Both Nodes values are not matching. Value1 : "+nodCount1+" Value2 : "+nodCount2 ,
									"Both Nodes are not matching",
									false, "NO", "Both node values are not matching",
									"Both node values should not match");
					Assert.assertFalse(true, "Both node values should not  match");
					test.log(LogStatus.FAIL,
							"Both node values should not match"); 	
				}
	}
	
	public String getNodeValueParameter(String xpathValue,String xmlDoc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException
	{
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlDoc);
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			Node node = (Node) xpath.evaluate(xpathValue, doc,
					XPathConstants.NODE);
			System.out.println(node.getTextContent());
			return node.getTextContent();
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	* Method Name: verifyBookingdestination
	* Purpose: To verify Node values
	* Flow:                             
	* Method Summary: Verifies the Node values
	* Creation Date: 24-05-17
	* Created By: Shalini P 
	* Modified Date:   
	* Modified By : 
	*/
	public void verifyBookingdestination(String node1,String node2) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException
	{
		if(!(node1==node2)){
			System.out.println("Both nodes  are not matching");
			
			counter = counter + 1;
			excelReadWrite
			.insertData(
					currentTestName,
					commonUtility.getcurrentDateTime() + "_"
							+ String.valueOf(counter),
					"Suceesfully Executed Request",
					"Verifying that Node values are not matching",
					"Both Nodes values are  not matching. Value1 : "+node1+" Value2 : "+node2,
					true, "Yes", "Both node values are not matching",
					"Both node values should not match");
		         test.log(LogStatus.INFO,
			   "Both node values should not matching");

			  }else{
				  counter = counter + 1;
				  excelReadWrite
							.insertFailedData(
									currentTestName,
									commonUtility.getcurrentDateTime() + "_"
											+ String.valueOf(counter),
									"Verifying that Node values are matching",
									"Both Nodes values are  matching. Value1 : "+node1+" Value2 : "+node2 ,
									"Both Nodes are  matching",
									false, "NO", "Both node values are matching",
									"Both node values should  match");
					Assert.assertFalse(true, "Both node values should  match");
					test.log(LogStatus.FAIL,
							"Both node values should match"); 	
				}
	}

public void handleAlert() throws InterruptedException {
		Thread.sleep(1000);
		try{
		Wait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
		}
		catch(Exception e){}
	}
	
}