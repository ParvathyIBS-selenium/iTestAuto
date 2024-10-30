package rest_sstunitch;

import java.util.Base64;
import org.openqa.selenium.WebDriver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import rest_sstunitch.Headers;
import rest_sstunitch.Payload;

public class JSONBody extends CustomFunctions {


	public JSONBody(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}
	
	public static String improppath = "\\src\\resources\\IM.properties";

	/**
	 * @author A-9847
	 * @param equipId - Storage Structure
	 * @param uld - UldNumber (removed this uld tag as part of new requirement)
	 * @param scaleweight - Scale weight
	 * @param height
	 * @param forwlinklen - forward length of Overhang/Indent
	 * @param afterlinklen - after length of Overhang/Indent
	 * @param leftlinklen - left length of Overhang/Indent
	 * @param rightlinklen - right length of Overhang/Indent
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */

	public void postRequest(String equipId,String uld,String scaleweight,String height,String forwlinklen,String afterlinklen,String leftlinklen,String rightlinklen) throws JsonProcessingException, InterruptedException
	{  

		afterlinklen=createRandomNumber("2");
		System.out.println(afterlinklen);
		map.put("AfterLinkLen", afterlinklen);

		forwlinklen=createRandomNumber("2");
		System.out.println(forwlinklen);
		map.put("ForwardLinkLen", forwlinklen);

		leftlinklen=createRandomNumber("2");
		System.out.println(leftlinklen);
		map.put("LeftLinkLen", leftlinklen);

		rightlinklen=createRandomNumber("2");
		System.out.println(rightlinklen);
		map.put("RightLinkLen", rightlinklen);
		
		equipId=getPropertyValue(improppath, "StorageStructure");	
		map.put("EquipmentID", equipId);	
		map.put("WeighScaleLoc", getPropertyValue(improppath, "SightingIMLoc"));
		
		//End point
		String url="https://mq-injector-cae-mdw1-00033-pks.qvi-cae.af-klm.com/api/injector/sendMessageToIBMMQ";

		//Message to be encoded
		String msg="<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.af-klm.com/services/cargo/MI-v1/xsd\"> <soapenv:Header> <trackingMessageHeader xmlns=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\"> <consumerRef> <userID>CGOIMCOM</userID> <partyID>AF</partyID> <consumerID>W002476</consumerID> <consumerLocation>VLB</consumerLocation> <consumerType>A</consumerType> <consumerTime>2022-10-04T13:17:11Z</consumerTime> </consumerRef> </trackingMessageHeader> <MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</MessageID> <RelatesTo RelationshipType=\"InitiatedBy\" xmlns=\"http://www.w3.org/2005/08/addressing\">4b8a127d-a48f-4893-8530-90d665ff666c</RelatesTo> </soapenv:Header> <soapenv:Body><q1:SendStorageUnitCharacteristicsRequestElement xmlns:q1=\"http://www.af-klm.com/services/cargo/MI-v1/xsd\"><messageHeader><messageType>D</messageType><sourceSystem>IM</sourceSystem><entityUpdateTime>2022-10-04T13:17:11.2820551Z</entityUpdateTime><messageCreationTime>2022-10-04T13:17:11.2820551Z</messageCreationTime></messageHeader><requestData><requestId>5414</requestId><airportCode>CDG</airportCode><equipment><equipmentID>"+equipId+"</equipmentID><equipmentName>"+equipId+"</equipmentName></equipment><count>1</count><weight>"+scaleweight+"</weight><height>"+height+"</height><length>15</length><weighingID>34648</weighingID><contour>A</contour><forwardLinkedLength>"+forwlinklen+"</forwardLinkedLength><afterLinkedLength>"+afterlinklen+"</afterLinkedLength><leftLinkedLength>"+leftlinklen+"</leftLinkedLength><rightLinkedLength>"+rightlinklen+"</rightLinkedLength></requestData></q1:SendStorageUnitCharacteristicsRequestElement></soapenv:Body> </soapenv:Envelope>";
		
		//Encoded message
		System.out.println(msg);
		
		String encodedMsg= Base64.getEncoder().encodeToString(msg.getBytes());
		Headers headers=new Headers("\"http://www.af-klm.com/services/cargo/SendStorageUnitCharacteristics-v1/sendStorageUnitCharacteristics\"");		

		/***** CREATE PAYLOAD****/			
		Payload p=new Payload(encodedMsg,"STUNICH-ICARGO-RCT",headers);

		ObjectMapper objMap=new ObjectMapper();

		String mydata=objMap.writerWithDefaultPrettyPrinter().writeValueAsString(p);

		String f=mydata.replaceAll("soapAction", "SoapAction");

		System.out.println(f);

		Object obj=f;

		//Post json request
		Response resp=RestAssured.given().header("Content-Type","application/json").log().all().body(obj).post(url);

		int val=resp.getStatusCode();

		System.out.println(val);

		if(val==200)
		{
			writeExtent("Pass","Response code of SendStorageUnitCharacteristics is "+val);
		}
		else
		{
			writeExtent("Fail","Response code of SendStorageUnitCharacteristics is "+val);
		}
	}
	
	
	
	
	
	/**
	 * @author A-9847
	 * @param equipId - Storage Structure (Usually pit for buildup)
	 * @param uld - UldNumber (removed this uld tag as part of new requirement)
	 * @param weighScaleId - Weight Scale ID
	 * @param scaleweight - Scale weight
	 * @param height
	 * @param forwlinklen - forward length of Overhang/Indent
	 * @param afterlinklen - after length of Overhang/Indent
	 * @param leftlinklen - left length of Overhang/Indent
	 * @param rightlinklen - right length of Overhang/Indent
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */

	public void postRequest_Export(String equipId,String uld,String weighScaleId,String scaleweight,String height,String forwlinklen,String afterlinklen,String leftlinklen,String rightlinklen) throws JsonProcessingException, InterruptedException
	{  
		
		//Creating unique ScaleId everytime
		weighScaleId=createRandomNumber("5");
		System.out.println(weighScaleId);
		map.put("WeighScaleId", weighScaleId);
		
		//End point
		String url="https://mq-injector-cae-mdw1-00033-pks.qvi-cae.af-klm.com/api/injector/sendMessageToIBMMQ";

		//Message to be encoded
		String msg="<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.af-klm.com/services/cargo/MI-v1/xsd\"> <soapenv:Header> <trackingMessageHeader xmlns=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\"> <consumerRef> <userID>CGOIMCOM</userID> <partyID>AF</partyID> <consumerID>W002476</consumerID> <consumerLocation>VLB</consumerLocation> <consumerType>A</consumerType> <consumerTime>2022-10-04T13:17:11Z</consumerTime> </consumerRef> </trackingMessageHeader> <MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</MessageID> <RelatesTo RelationshipType=\"InitiatedBy\" xmlns=\"http://www.w3.org/2005/08/addressing\">4b8a127d-a48f-4893-8530-90d665ff666c</RelatesTo> </soapenv:Header> <soapenv:Body><q1:SendStorageUnitCharacteristicsRequestElement xmlns:q1=\"http://www.af-klm.com/services/cargo/MI-v1/xsd\"><messageHeader><messageType>D</messageType><sourceSystem>IM</sourceSystem><entityUpdateTime>2022-10-04T13:17:11.2820551Z</entityUpdateTime><messageCreationTime>2022-10-04T13:17:11.2820551Z</messageCreationTime></messageHeader><requestData><requestId>5414</requestId><airportCode>CDG</airportCode><equipment><equipmentID>"+equipId+"</equipmentID><equipmentName>"+equipId+"</equipmentName></equipment><count>1</count><weight>"+scaleweight+"</weight><height>"+height+"</height><length>15</length><weighingID>"+weighScaleId+"</weighingID><contour>A</contour><forwardLinkedLength>"+forwlinklen+"</forwardLinkedLength><afterLinkedLength>"+afterlinklen+"</afterLinkedLength><leftLinkedLength>"+leftlinklen+"</leftLinkedLength><rightLinkedLength>"+rightlinklen+"</rightLinkedLength></requestData></q1:SendStorageUnitCharacteristicsRequestElement></soapenv:Body> </soapenv:Envelope>";
		
		System.out.println(msg);
		//Encoded message		
		String encodedMsg= Base64.getEncoder().encodeToString(msg.getBytes());
		Headers headers=new Headers("\"http://www.af-klm.com/services/cargo/SendStorageUnitCharacteristics-v1/sendStorageUnitCharacteristics\"");		

		/***** CREATE PAYLOAD****/				
		Payload p=new Payload(encodedMsg,"STUNICH-ICARGO-RCT",headers);
		ObjectMapper objMap=new ObjectMapper();
		String mydata=objMap.writerWithDefaultPrettyPrinter().writeValueAsString(p);
		String f=mydata.replaceAll("soapAction", "SoapAction");

		System.out.println(f);
		Object obj=f;

		//Post json request
		Response resp=RestAssured.given().header("Content-Type","application/json").log().all().body(obj).post(url);

		int val=resp.getStatusCode();

		System.out.println(val);

		if(val==200)
		{
			writeExtent("Pass","Response code of SendStorageUnitCharacteristics is "+val);
		}
		else
		{
			writeExtent("Fail","Response code of SendStorageUnitCharacteristics is "+val);
		}
	}


}
