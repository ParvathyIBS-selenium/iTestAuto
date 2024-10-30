package rest_wcsusu;

import java.util.Base64;
import org.openqa.selenium.WebDriver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class JSONBody extends CustomFunctions {
	
	
	public JSONBody(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	}

	public void postRequest(String type,String uldType,String uldNo,String carrierCode,String destination,String flightDate,String flightNo) throws JsonProcessingException
	{  
		        
		        //End point
		        String url="https://mq-injector-cae-mdw1-00033-pks.qvi-cae.af-klm.com/api/injector/sendMessageToIBMMQ";
		        
		        //Message to be encoded
		        String msg="<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header><ns0:Action xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">http://www.af-klm.com/services/cargo/SendWCSUnitStatusUpdate-v2_0/wCSUnitStatusUpdate</ns0:Action><ns0:MessageID xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">uuid:93b6f048-7152-4584-bc89-fe0a3afb8fce</ns0:MessageID><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/InitiatedBy\">uuid:93b6f048-7152-4584-bc89-fe0a3afb8fce</ns0:RelatesTo><ns0:To xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">jms:queue:PUB.ESB.WCSUSU</ns0:To><ns0:trackingMessageHeader xmlns:ns0=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\"><ns0:consumerRef><ns0:userID>unknown</ns0:userID><ns0:partyID>unknown</ns0:partyID><ns0:consumerID>unknown</ns0:consumerID><ns0:consumerLocation>unknown</ns0:consumerLocation><ns0:consumerType>A</ns0:consumerType><ns0:consumerTime>2021-12-17T14:59:48.657+01:00</ns0:consumerTime></ns0:consumerRef></ns0:trackingMessageHeader><ns0:EventHeader xmlns:ns0=\"http://www.af-klm.com/services/common/EventHeader-v1/xsd\"><ns0:Publication><ns0:Publisher Name=\"UNKNOWN\" Type=\"soa\" Category=\"system\"/><ns0:Type>regular</ns0:Type></ns0:Publication><ns0:Addressing><ns0:ContentBasedAddressing Scope=\"all\">true</ns0:ContentBasedAddressing><ns0:DeliverTo><ns0:TriggeredSubscriber Name=\"ICARGO\" Type=\"soa\" Category=\"system\"><ns0:TriggeredByRule><ns0:Rule Name=\"Generated Rule name\" Version=\"1\"/></ns0:TriggeredByRule></ns0:TriggeredSubscriber></ns0:DeliverTo><ns0:AlreadyDeliveredTo><ns0:Subscriber Name=\"UNKNOWN\" Type=\"soa\" Category=\"system\"/><ns0:Subscriber Name=\"CHAIN\" Type=\"soa\" Category=\"system\"/><ns0:Subscriber Name=\"SORTERPL\" Type=\"soa\" Category=\"system\"/></ns0:AlreadyDeliveredTo></ns0:Addressing></ns0:EventHeader></SOAP-ENV:Header><SOAP-ENV:Body><ns0:WCSUnitStatusUpdateEvent xmlns:ns0=\"http://www.af-klm.com/services/cargo/SendWCSUnitStatusUpdate-v3/WCSUnitStatusUpdateEvent/xsd\"><ns0:EventHeader><ns0:EventDateTime>2024-06-11T10:51:31</ns0:EventDateTime><ns0:EventActionCode>UPD</ns0:EventActionCode><ns0:EventSourceSystem>WCS_VG1</ns0:EventSourceSystem><ns0:EventCorrelationID>791577390</ns0:EventCorrelationID></ns0:EventHeader><ns0:LoadUnit><ns0:LoadUnitID><ns0:LoadUnitTypeCode LoadUnitClass=\""+type+"\">"+uldType+"</ns0:LoadUnitTypeCode><ns0:LoadUnitSerialNbr>"+uldNo+"</ns0:LoadUnitSerialNbr><ns0:LoadUnitOwnerCode>"+carrierCode+"</ns0:LoadUnitOwnerCode></ns0:LoadUnitID><ns0:LoadUnitTransport><ns0:DestinationLocationID>"+destination+"</ns0:DestinationLocationID><ns0:OutboundDatedTransport><ns0:DepartureDate>"+flightDate+"</ns0:DepartureDate><ns0:ConveyanceID><ns0:CarrierCode>"+carrierCode+"</ns0:CarrierCode><ns0:FlightNumber>"+flightNo+"</ns0:FlightNumber></ns0:ConveyanceID></ns0:OutboundDatedTransport></ns0:LoadUnitTransport><ns0:LoadUnitWarehouseStatus><ns0:StatusCode>UOB</ns0:StatusCode><ns0:AssociatedBuildUpDetails><ns0:BuildUpStatus>EXP</ns0:BuildUpStatus><ns0:UnitMixing>Cargo</ns0:UnitMixing></ns0:AssociatedBuildUpDetails></ns0:LoadUnitWarehouseStatus></ns0:LoadUnit></ns0:WCSUnitStatusUpdateEvent></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		        
				System.out.println(msg);
				//Encoded message
				String encodedMsg= Base64.getEncoder().encodeToString(msg.getBytes());
				
				Headers headers=new Headers("\"http://www.af-klm.com/services/cargo/SendWCSUnitStatusUpdate-v2_0/wCSUnitStatusUpdate\"");		
				
				/***** CREATE PAYLOAD****/				
				Payload p=new Payload(encodedMsg,"WCSUSU-ICARGO-RCT",headers);

				ObjectMapper objMap=new ObjectMapper();

				String mydata=objMap.writerWithDefaultPrettyPrinter().writeValueAsString(p);

				String f=mydata.replaceAll("soapAction", "SoapAction");

				System.out.println(f);

				Object obj=f;
  
			     //Post json request
			     Response resp=RestAssured.given()
			    .header("Content-Type","application/json").log().all().body(obj).post(url);
			     
			      int val=resp.getStatusCode();
			      
			      System.out.println(val);
			      
			      if(val==200)
			      {
			    		writeExtent("Pass","Response code of SendWCSUnitStatusUpdate is "+val);
			      }
			      else
			      {
			    	  writeExtent("Fail","Response code of SendWCSUnitStatusUpdate is "+val);
			      }
	}

}
