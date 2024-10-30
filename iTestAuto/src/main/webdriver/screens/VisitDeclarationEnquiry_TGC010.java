/**
 * @author A-8468
 */
package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class VisitDeclarationEnquiry_TGC010 extends CustomFunctions
{

	public VisitDeclarationEnquiry_TGC010(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	
	
	public String sheetName="VisitDeclarationEnquiry_TGC010";
	public String screenName="VisitDeclarationEnquiry_TGC010";
	
	/**
	 * @author A-7271
	 * @param Token
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc : enter Token
	 */
	public void enterToken(String token) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_tokenNo;name", data(token), " Token ", screenName);
			
	}
	

/**
	 * @author A-8783
	 * Desc - Verify expiry date
	 * @param expiryDate
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyExpiryDate(String expiryDate) throws InterruptedException, IOException {
		try {
		clickWebElement(sheetName, "lnk_driverDetails;xpath", "Driver name",screenName);
		waitForSync(1);
		String locator= xls_Read.getCellValue(sheetName, "txt_expiryDate;xpath");
		locator= locator.replace("expDate", data(expiryDate));
		int size=driver.findElements(By.xpath(locator)).size();
		if(size==1) {
			writeExtent("Pass", "Verified expiry date" + data(expiryDate) + "is displayed on "+screenName);
		}
		else {
			writeExtent("Fail", "Could not verify that expiry date" + data(expiryDate) + "is displayed on "+screenName);
		}
		}
		catch(Exception e) {
			writeExtent("Fail", "Could not verify expiry date in "+ screenName);
		}
		
	}
	/**
	 * @author A-8783
	 * Desc - Get token status
	 * @param status
	 * @throws InterruptedException
	 */
	public void getTokenStatus(String status) throws InterruptedException{
		 map.put(status, getElementText(sheetName, "txt_tokenStatus;xpath", "Token Status", screenName)); 
	}
	/**
	 * @author A-8783
	 * Desc - Get service point mode
	 * @param servicePointMode
	 * @throws InterruptedException
	 */
	public void getServicePointMode(String servicePointMode) throws InterruptedException{
		map.put(servicePointMode,getElementText(sheetName, "txt_servicePoint;xpath", "Service Point Mode", screenName));
	}

	/**
	 * @author A-8783
	 * Desc- Select source/ kiosk location
	 * @param kioskLocation
	 */
	public void selectSource(String kioskLocation) {
		selectValueInDropdown(sheetName, "lst_source;xpath", data(kioskLocation), "Kiosk location", "VisibleText");
		waitForSync(2);
	}
	/**
	 * @author A-9844
	 * Description...verify Column name
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyColumn(String[] columnName) throws InterruptedException, Exception{
		int i = 0;
		int flag=0;
		try {
			String locator=xls_Read.getCellValue(sheetName,"table_columNames;xpath");
			List<WebElement> column = driver.findElements(By.xpath(locator));
			for( i=0;i<columnName.length;i++){
				flag=0;
				for(WebElement col:column) {
					
					moveScrollBar(col);
					String actText = col.getText();
					System.out.println(actText);
					if(actText.equals(columnName[i])) {

						writeExtent("Pass", "Verified that the column " + columnName[i] + " is present in the table");
						break;
					}
					else {
						flag+=1;
					}

				}
				if(flag==column.size()) {
					writeExtent("Fail", "Failed to verify that the column " + columnName[i] + " is present in the table");

				}

			}

		}
		catch(Exception e) {
			writeExtent("Fail", "Failed to verify if columns are present");
		}
	}
	
	/**
	 * @author A-10690
	 * Desc - Verify Token status is secured
	 * @param tokenId
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyTokenIsSecured(String tokenId) throws InterruptedException, IOException {
		try {
		
			waitForSync(1);
			String locator= xls_Read.getCellValue(sheetName, "txt_tokensecurity;xpath");
			locator= locator.replace("*", data(tokenId));
			int size=driver.findElements(By.xpath(locator)).size();
			if(size!=1) {
				writeExtent("Pass", "Verified token status as  secured for " + data(tokenId) + screenName);
			}
			else {
				writeExtent("Fail", "Could not verify token status as  secured for " + data(tokenId) + screenName);
			}
		}
		catch(Exception e) {
			writeExtent("Fail", "Could not verify token status as  secured for " + data(tokenId));
		}

	}

	/**
	 * @author A-9844
	 * Description...verify seal captured icon
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifySealCapturedTickMark() throws InterruptedException, Exception{

		try
		{
			String locator=xls_Read.getCellValue(sheetName, "txt_SealCapturedIcon;xpath");   
			moveScrollBar(driver.findElement(By.xpath(locator)));
			waitForSync(2); 

			int size=driver.findElements(By.xpath(locator)).size();
			if(size==1) {
				writeExtent("Pass", "Verified the seal captured tick mark icon is displayed on "+screenName);
			}
			else 
			{
				writeExtent("Fail", "Could not verify the seal captured tick mark icon is displayed on "+screenName);
			}
		}
		catch (Exception e) {
			writeExtent("Fail", "Could not verify the seal captured tick mark icon is displayed on "+screenName);
		}

	}
	/**
	 * @author A-10690
	 * Desc - Verify Token status is unsecured
	 * @param tokenId
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyTokenIsunsecured(String tokenId) throws InterruptedException, IOException {
		try {
		
			waitForSync(1);
			String locator= xls_Read.getCellValue(sheetName, "txt_tokensecurity;xpath");
			locator= locator.replace("*", data(tokenId));
			int size=driver.findElements(By.xpath(locator)).size();
			if(size==1) {
				writeExtent("Pass", "Verified token status as  unsecured for " + data(tokenId) + screenName);
			}
			else {
				writeExtent("Fail", "Could not verify token status as  unsecured for " + data(tokenId) + screenName);
			}
		}
		catch(Exception e) {
			writeExtent("Fail", "Could not verify token status as  unsecured for " + data(tokenId));
		}

	}

	/**
	 * @author A-10690
	 * Desc - Verify LAT value displayed 
	 * @param expected LAT details
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void verifyLATvalue(String latdata) throws InterruptedException{
		
		String locator=xls_Read.getCellValue(sheetName, "txt_latcolumn;xpath");
		String columnnumber = driver.findElement(By.xpath(locator)).getAttribute("data-ic-csid");
		String locator1=xls_Read.getCellValue(sheetName, "txt_latvalue;xpath");
		locator1=locator1.replace("*",columnnumber);

		String acttext = driver.findElement(By.xpath(locator1)).getText();
		String[] LATValues=data(latdata).split(" ");
		String expLATValue=LATValues[1]+" "+LATValues[0];
		
		if(acttext.contains(expLATValue))
		{
			writeExtent("Pass","Successfully verified LAT  details on "+screenName);
		}
		else{
			writeExtent("Fail","Failed to verify LAT  details on "+screenName);
		}
		
	}
	/**
	 * @author A-8783
	 * Desc- Verify public side token is displayed
	 * @param tokenID
	 * @param tokenPresent
	 */
	public void verifyTokenDisplayed(String tokenID, Boolean tokenPresent) {
		if(tokenPresent) {
			String actToken=  driver
					.findElement(
							By.xpath(xls_Read.getCellValue(sheetName,
									"txt_token;xpath"))).getText();
			verifyScreenText(sheetName, data(tokenID), actToken, "Token number",
					"screenName");
		}
		else {

			verifyErrorMessages("TGC101", "No Results Found for the Specified Criteria");
		}
	}
	/**
	 * @author A-8783
	 * Desc - Verify token priority
	 * @param TokenInbound
	 * @param TokenOutbound
	 */
	public void verifyTokenPriority(String TokenInbound, String TokenOutbound) {
	
		int inboundIndex=0;
		int outboundIndex=0;
		List<WebElement> tokens= driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "txt_token;xpath")));

		
		for(WebElement token:tokens) {
			
			
			if(token.getText().equals(data(TokenInbound))) {
				
				 inboundIndex = tokens.indexOf(token);
				
			}
			
			else if(token.getText().equals(data(TokenOutbound))) {
				 outboundIndex = tokens.indexOf(token);
			}
		}
		
		if(inboundIndex<outboundIndex) {
			writeExtent("Pass", "Tokens are in correct order");
		}
		else {
			writeExtent("Fail", "Tokens are not in correct order");
		}
}

	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : list button
	 */
	public void clickList() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_list;name", " List Button ",screenName);
		waitForSync(3);
	}
	
	/**
	 * @author A-10690
	 * @param token
	 * @param servicepoint
	 * @param pmKey
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : verify visit declaration details
	 */
	   
	    public void assignToken(String token,String servicepoint ) throws InterruptedException, IOException {
	        String xpath1 = xls_Read.getCellValue(sheetName,
	                "btn_selecttoken;xpath").replace("*",data(token));
	        driver.findElement(By.xpath(xpath1)).click();  
	        clickWebElement(sheetName, "btn_assignservicepoint;id", " assign service Button ",screenName);
	        waitForSync(3);
	        switchToFrame("default");
			driver.switchTo().frame("iCargoContentFrameTGC010");
			driver.switchTo().frame("popupContainerFrame");
	        selectValueInDropdown(sheetName, "btn_servicepoint;name", data(servicepoint), "select service point", "VisibleText");
	        clickWebElement(sheetName, "btn_assigndock;name", " assign service Button in the pop up ",screenName);
	        switchToFrame("default");
			driver.switchTo().frame("iCargoContentFrameTGC010");
	        waitForSync(4);

	    }
	/**
	 * @author A-7271
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : verify visit declaration details
	 */
	public void verifyVisitDeclarationDetails(int verfCols[], String actVerfValues[],
			String pmKey) throws InterruptedException, IOException {
		
		verify_tbl_records_multiple_cols(sheetName, "table_visitDeclarationDtls;xpath",
				"//td", verfCols, pmKey, actVerfValues);
	}
}
