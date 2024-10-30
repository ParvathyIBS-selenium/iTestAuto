package screens;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class Mercury extends CustomFunctions {

	String sheetName = "mercury_screen";
	String screenName = "mercury_screen";


	public Mercury(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	}

	/**
	 * @author A-7271
	 * Desc : click send message button
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void clickSendMessage() throws InterruptedException, IOException
	{

		switchToFrame("frameName","main");
		switchToFrame("frameName","menu");

		clickWebElement(sheetName, "lnk_mercuryMessaging;id", "Send message link", screenName);
		clickWebElement(sheetName, "btn_mercurySendMsg;xpath", "Send message link", screenName);
		waitForSync(2);
		switchToFrame("default");
	}


	public void enterTelexAddress(String senderAddress,String destinationAddress,boolean switchToFrame) throws InterruptedException
	{
		if(switchToFrame)
		{
			switchToFrame("frameName","main");
			switchToFrame("frameName","page");
		}
		enterValueInTextbox(sheetName, "inbx_senderAdd;name", data(senderAddress), "Sender Address", screenName);
		enterValueInTextbox(sheetName, "inbx_destinationAdd;name", data(destinationAddress), "Destination Address", screenName);
	}
	/**
	 * @author A-9175
	 * Description : Sending created message by selecting option
	 */
	public void sendMessageInMercury() throws InterruptedException, IOException {

		setValueInTextbox(sheetName, "inbx_msg;id", parameters.get("messageLine"), "Message", screenName);
		clickWebElement(sheetName, "btn_send;id", "Send Button", screenName);
		waitForSync(5);


	}
	/**
	 * @author A-7271
	 * @param msg
	 * Desc : verify message status
	 */
	public void verifyMsgStatus(String msg)
	{
		String loc=xls_Read.getCellValue(sheetName, "table_msgStatus;xpath");

		try
		{
			String msgStatus=driver.findElement(By.xpath(loc)).getText();

			if(msgStatus.contains("message you've sent"))
			{
				writeExtent("Pass",msg+" Message is sent from mercury");
			}
			else
			{
				writeExtent("Fail",msg+" Message is not sent from mercury.Message status is shown as "+msgStatus);
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail",msg+" Message is not sent from mercury");
		}

	}

	/**
	 * @author A-9175
	 * Desc : Return to Message screen
	 */
	public void returnTosendMessage() throws InterruptedException, IOException 
	{
		clickWebElement(sheetName, "btn_resend;xpath", "Return To Message Button", screenName);
		waitForSync(3);
	}

}
