package rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

public class JSONBody {
	
	
	public static void main(String args[]) throws JsonProcessingException
	{
		 String encodeURL="";
		 String url="https://mq-injector-cae-mdw1-00033-pks.qvi-cae.af-klm.com/api/injector/sendMessageToIBMMQ";
		 try {  
              encodeURL=URLEncoder.encode( url, "UTF-8" );  
             
        } catch (UnsupportedEncodingException e) {  
            
        }  
		 
		
		 
		 System.out.println(encodeURL);
//		       System.setProperty("http.proxyHost", "webproxy.ibsplc.com");
//				System.setProperty("http.proxyPort", "80");
				String zipCode="77777";
	//			String msg="<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header><ns0:Action xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">http://www.af-klm.com/services/cargo/SendCargoCustomerUpdate-v3/customerUpdateNotify</ns0:Action><ns0:To xmlns:ns0=\"http://www.w3.org/2005/08/addressing\"/><ns0:MessageID xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">uuid:871383d7-c249-4934-a7fa-67838086363c</ns0:MessageID><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/InitiatedBy\">uuid:871383d7-c249-4934-a7fa-67838086363c</ns0:RelatesTo><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/PrecededBy\"/><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/ReplyTo\"/><ns2:trackingMessageHeader xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\" xmlns:ns1=\"soa://Framework/Ops/Context/Schemas/LocalContext-v1_0\"><ns2:consumerRef><ns2:userID>m330347</ns2:userID><ns2:partyID>AF</ns2:partyID><ns2:consumerID>w02227589</ns2:consumerID><ns2:consumerLocation>QVI</ns2:consumerLocation><ns2:consumerType>A</ns2:consumerType><ns2:consumerTime>2021-10-29T13:35:14.209Z</ns2:consumerTime></ns2:consumerRef></ns2:trackingMessageHeader><ns2:EventHeader xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/services/common/EventHeader-v1/xsd\" xmlns:ns1=\"soa://Framework/Ops/Context/Schemas/LocalContext-v1_0\"><ns2:Publication><ns2:Publisher Name=\"w02227589\" Type=\"soa\" Category=\"system\"/></ns2:Publication><ns2:Addressing><ns2:ContentBasedAddressing Scope=\"all\">true</ns2:ContentBasedAddressing><ns2:DeliverTo><ns2:TriggeredSubscriber Name=\"ESOADA\" Type=\"soa\" Category=\"system\"><ns2:TriggeredByRule><ns2:Rule Name=\"Generated Rule name\" Version=\"1\"/></ns2:TriggeredByRule></ns2:TriggeredSubscriber></ns2:DeliverTo><ns2:AlreadyDeliveredTo><ns2:Subscriber Name=\"w02227589\" Type=\"soa\" Category=\"system\"/></ns2:AlreadyDeliveredTo></ns2:Addressing></ns2:EventHeader></SOAP-ENV:Header><SOAP-ENV:Body><ns11:WSCustomerTechOutElement xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/services/cargo/cargoAccountData-v5/xsd\" xmlns:ns4=\"http://www.af-klm.com/services/cargo/collaboratorData-v4/xsd\" xmlns:ns3=\"http://www.af-klm.com/services/cargo/cargoAccountDataOut-v4/xsd\" xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns6=\"http://www.af-klm.com/services/cargo/dataCommon-v5/xsd\" xmlns:ns5=\"http://www.af-klm.com/services/cargo/collaboratorDataOut-v5/xsd\" xmlns:ns8=\"http://www.af-klm.com/services/cargo/contactData-v5/xsd\" xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns7=\"http://www.af-klm.com/services/cargo/dataCommonOut-v5/xsd\" xmlns:ns9=\"http://www.af-klm.com/services/cargo/contactDataOut-v3/xsd\" xmlns:ns12=\"http://www.af-klm.com/services/cargo/permissionDataOut-v2/xsd\" xmlns:ns11=\"http://www.af-klm.com/services/cargo/customerDataOut-v7/xsd\" xmlns:ns10=\"http://www.af-klm.com/services/cargo/customerData-v14/xsd\"><reference>201812</reference><version>24</version><customerData><name>TEST IT</name><station><code>CDG</code></station><airline><code>AF</code></airline><shortName>TESTIT</shortName><deleteFlag>false</deleteFlag><validityInd>true</validityInd><address><street>TEST</street><stateCode/><zipCode>"+zipCode+"</zipCode><city>PARIS</city><country><code>FR</code><name>FRANCE</name></country></address><electronicExchangeData><traxonIndicator>true</traxonIndicator><traxonBookingIndicator>false</traxonBookingIndicator><traxonProactiveStatus>false</traxonProactiveStatus><cpsIndicator>false</cpsIndicator><ediAgree>false</ediAgree><eWithoutFWB>false</eWithoutFWB></electronicExchangeData><globalaccounts><iataAgentReference/><accountIdType>P</accountIdType><accountStatus>PENDING</accountStatus><group><localSegmentation/><managerAccount><name>MALGORZATA SIKORSKA</name></managerAccount></group><meansOfPayment><invoicingCarrier>false</invoicingCarrier><invoicingDemand>false</invoicingDemand><cheque>false</cheque><creditCard>false</creditCard></meansOfPayment><creditExport><creditQuality/><creditLimit>101</creditLimit><creditCurrency>EUR</creditCurrency></creditExport><creditImport><creditQuality/></creditImport></globalaccounts><generalContact><language/></generalContact><miscDetails><creationDate>2019-08-08T00:00:00Z</creationDate></miscDetails><indicators><securityInd>false</securityInd></indicators><mailSection><mailIndicator>I</mailIndicator></mailSection><pointOfSale><code>CDG</code></pointOfSale><roleList><id>1</id><name>FORWARDER</name></roleList><disableQuoteAndBook>false</disableQuoteAndBook><whatCounts>false</whatCounts><blueBizProgram><eligible>YES</eligible><selected>TO_BE_DECIDED</selected></blueBizProgram><generalInformation/><uldDeliveryAuthorized>false</uldDeliveryAuthorized><isPhysicallyDeletedInd>false</isPhysicallyDeletedInd></customerData></ns11:WSCustomerTechOutElement></SOAP-ENV:Body></SOAP-ENV:Envelope>";
				String msg="<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header><ns0:Action xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">http://www.af-klm.com/services/cargo/publishAWBScreening-V1/saveAWBScreening</ns0:Action><ns0:To xmlns:ns0=\"http://www.w3.org/2005/08/addressing\"/><ns0:MessageID xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">988d2a2e-25df-4985-93e0-b952a89d8f40</ns0:MessageID><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/InitiatedBy\">b762bf9e-2487-42a3-bc88-be998364e51d</ns0:RelatesTo><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/PrecededBy\">b762bf9e-2487-42a3-bc88-be998364e51d</ns0:RelatesTo><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/ReplyTo\"/><ns2:trackingMessageHeader xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\" xmlns:ns1=\"soa://Framework/Ops/Context/Schemas/LocalContext-v1_0\"><ns2:consumerRef><ns2:userID>unknown</ns2:userID><ns2:partyID>AF</ns2:partyID><ns2:consumerID>w07576423</ns2:consumerID><ns2:consumerLocation>CDG</ns2:consumerLocation><ns2:consumerType>A</ns2:consumerType><ns2:consumerTime>2022-06-01T14:44:45Z</ns2:consumerTime></ns2:consumerRef></ns2:trackingMessageHeader><ns2:EventHeader xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/services/common/EventHeader-v1/xsd\" xmlns:ns1=\"soa://Framework/Ops/Context/Schemas/LocalContext-v1_0\"><ns2:Publication><ns2:Publisher Name=\"w07576423\" Type=\"soa\" Category=\"system\"/></ns2:Publication><ns2:Addressing><ns2:ContentBasedAddressing Scope=\"all\">true</ns2:ContentBasedAddressing><ns2:DeliverTo><ns2:TriggeredSubscriber Name=\"ICARGO\" Type=\"soa\" Category=\"system\"><ns2:TriggeredByRule><ns2:Rule Name=\"Generated Rule name\" Version=\"1\"/></ns2:TriggeredByRule></ns2:TriggeredSubscriber></ns2:DeliverTo><ns2:AlreadyDeliveredTo><ns2:Subscriber Name=\"w07576423\" Type=\"soa\" Category=\"system\"/></ns2:AlreadyDeliveredTo></ns2:Addressing></ns2:EventHeader></SOAP-ENV:Header><SOAP-ENV:Body><saveAWBScreening xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns=\"http://www.af-klm.com/services/cargo/publishAWBScreening-V1/publishAWBScreening/xsd\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><awb>057-15634146</awb><airport>CDG</airport><method>XRY</method><storageUnitCode>05715634146001</storageUnitCode><locationCode>MX7ENT</locationCode><statusCode>P</statusCode><recommendationCode>Espacer les colis</recommendationCode><agent>12345678</agent><screeningDate>21-Jul-2022 16:44:45</screeningDate></saveAWBScreening></SOAP-ENV:Body></SOAP-ENV:Envelope>";
				String encodedMsg= Base64.getEncoder().encodeToString(msg.getBytes());
				
				System.out.println(encodedMsg);
				
			     Headers headers=new Headers("http://www.af-klm.com/services/cargo/publishAWBScreening-V1/saveAWBScreening");
			     System.out.println(headers);
			     Payload p=new Payload(encodedMsg,"PAWBS-ICARGO-RCT",headers);
			   
			    ObjectMapper objMap=new ObjectMapper();
			     
			     String mydata=objMap.writerWithDefaultPrettyPrinter().writeValueAsString(p);
			     
			     String f=mydata.replaceAll("soapAction", "SoapAction");
			     
			     System.out.println(f);
			     
			    Object obj=f;
			     
			   //  RestAssured.useRelaxedHTTPSValidation();
			     
			     Response resp=RestAssured.given()
			    .header("Content-Type","application/json").log().all().body(obj).post(url);
			     
			      int val=resp.getStatusCode();
			      
			      System.out.println(val);
			    //System.out.println(resp.getBody().jsonPath().prettify());
			     
			
			     

	     
	     

	}

}
