package rest_wcsusu;

public class Headers {
	
	String SoapAction;
	
	public Headers(String SoapAction)
	{
		this.SoapAction=SoapAction;
	}

	public String getSoapAction() {
		return SoapAction;
	}

	public void setSoapAction(String soapAction) {
		SoapAction = soapAction;
	}

}
