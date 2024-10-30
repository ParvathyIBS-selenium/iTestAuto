package rest;

public class Payload {
	
	String body;
	String key;
	Headers headers;
	
	public Payload(String body,String key,Headers headers)
	{
		this.body=body;
		this.key=key;
		this.headers=headers;
	}
	
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Headers getHeaders() {
		return headers;
	}
	public void setHeaders(Headers headers) {
		this.headers = headers;
	}
	
	
	

}
