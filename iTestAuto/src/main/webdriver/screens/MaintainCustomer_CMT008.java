package screens;

import java.awt.Robot;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class MaintainCustomer_CMT008 extends CustomFunctions{

	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName="MaintainCustomer_CMT008";
	String ScreenName="Maintain Customer Screen";
	String screenId="CMT008";
	 WebFunctions libr=new WebFunctions(driver, excelreadwrite, xls_Read);

	public MaintainCustomer_CMT008(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction=new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
/**
 * Description...	Click List Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickListButton() throws InterruptedException, IOException{
		clickWebElement(sheetName, "inbx_list;name", "List Button", ScreenName);
		waitForSync(10);
	}
/**
 * Description... Enter Customer Code
 * @param custCode
 * @throws InterruptedException
 */
	public void enterCustCode(String custCode) throws InterruptedException{
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_customerCode;name", custCode, "Customer Code", ScreenName);
	}
	
	/**
	 * @author A-9844
	 * @param key
	 * @throws InterruptedException
	 * Desc : store Delivery Slot Time in the map
	 */
	public void storeDeliverySlotTime(String key) throws InterruptedException {

		String locator = xls_Read.getCellValue(sheetName, "txt_deliverySlot;xpath");
		WebElement entry=driver.findElement(By.xpath(locator));
		moveScrollBar(entry);
		String deliverySlot =getAttributeWebElement(sheetName, "txt_deliverySlot;xpath", "Delivery Slot","value", ScreenName);
		
		if(deliverySlot.equals(""))
		map.put(key, "noTimeSlot");
		else	
		map.put(key, deliverySlot);
		System.out.println(deliverySlot);

	}

	
/**
 * Description...	Verify Rows
 */
	public void verifyRows(){
		By b=getElement(sheetName, "lst_custDetails;xpath");
		List <WebElement> list=driver.findElements(b);
		
		for(WebElement ele:list){			
			Select select=new Select(ele);
			String option=select.getFirstSelectedOption().getText();
			if(option.equalsIgnoreCase("eTracking"))
				verifyValueOnPage(true, true, "1. Verify Row for eTracking is Displayed in CMT008", "Maintain Customer", "eTracking Row in Customer Details");
			if(option.equalsIgnoreCase("eTrackingTOA"))
				verifyValueOnPage(true, true, "1. Verify Row for eTrackingTOA is Displayed in CMT008", "Maintain Customer", "eTrackingTOA Row in Customer Details");
		
		
		}
	}

/**
 * Description... Click Customer Preference Button
 * @param expFSUToBeNotified
 * @throws InterruptedException
 * @throws IOException 
 */
	
	public void clickCustPref(String expFSUToBeNotified) throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_custPref;xpath", "Customer Preference Button", ScreenName);
		String actFSUToBeNotified=getAttributeWebElement(sheetName, "txt_FSUNotified;xpath", "FSUs to be notified", "defaultValue", ScreenName);

		verifyValueOnPage(actFSUToBeNotified, expFSUToBeNotified, "1. Open CMT008 Screen\n2. Verify FSU To be Notified", ScreenName, "FSU To be Notified");
		clickWebElement(sheetName, "btn_custDetailsTab;xpath", "Customer Details Button", ScreenName);
		waitForSync(5);
	
		
	}
/**
 * Description... Verify Notification Language
 * @param lang
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyNotificationLang(String lang) throws InterruptedException, IOException{
		By b= getElement(sheetName, "lst_LangCode;name");
		ele=driver.findElement(b);
		Select select= new Select(ele);
		String actopt = select.getFirstSelectedOption().getText().replace(" ", "").trim();
		verifyValueOnPageContains(actopt, lang, "Verify Preffered Language", "Notification Preferences", "Preffered Language");
		
		
	}
/**
 * Description...	Click Table Record with primary Key 
 * @param referenceVar
 * @param sheetName
 * @param locator
 * @param locatorEle
 * @param loopCount
 */
	public void clickTableRecordpmyKeyDropdown(String referenceVar, String sheetName,
			String locator, String locatorEle, int loopCount) {

		try {
			
			boolean flag = false;
			int row = 0;
			String ScreenName = sheetName.split("_")[0];
			// get the required row
			String tableBody = xls_Read.getCellValue(sheetName, locator);
			List<WebElement> rows = driver.findElements(By.xpath(xls_Read
					.getCellValue(sheetName, locator)));
			String dynXpath = xls_Read.getCellValue(sheetName, locator) ;

			System.out.println("row size  " + rows.size());
			
			
				for (int i = 0; i < rows.size(); i++) {
					dynXpath = xls_Read.getCellValue(sheetName,
							"lst_custDetails;xpath");
					List<WebElement> cols = driver.findElements(By.xpath(dynXpath));

					for (int j = 0; j < cols.size(); j++) {

						Select select = new Select(cols.get(j));
						if (select.getFirstSelectedOption().getText()
								.equals(referenceVar)) {
							flag = true;
							i = j;
							break;

						}
					}
					if (flag) {
						row = i + 1;
						break;
					}
				}
				String imgXpath = xls_Read.getCellValue(sheetName, locatorEle);
				 dynXpath = "(" + imgXpath + ")[" + row + "]";
				driver.findElement(By.xpath(dynXpath)).click();

			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
/**
 * Description...	Click a link in the table using primary key
 * @param sheetName
 * @param locator
 * @param tableCell
 * @param xpath
 * @param pmyKey
 * @param tableTag
 */
	public void click_tbl_records_multiple_cols(String sheetName,
			String locator, String tableCell, String xpath, String pmyKey,
			String tableTag) {
		boolean flag = false;
		int row = 0;

		// get the required row

		List<WebElement> rows = driver.findElements(By.xpath(xls_Read
				.getCellValue(sheetName, locator)));

		String dynXpath = xls_Read.getCellValue(sheetName, locator) + tableTag;
		System.out.println("row size  " + rows.size());

		{
			for (int i = 0; i < rows.size(); i++) {
				dynXpath = xls_Read.getCellValue(sheetName, locator);
				List<WebElement> cols = driver.findElements(By.xpath(dynXpath));

				for (int j = 0; j < cols.size(); j++) {

					Select select = new Select(cols.get(j));
					System.out.println(select.getFirstSelectedOption()
							.getText());
					if (select.getFirstSelectedOption().getText()
							.equals(pmyKey)) {
						flag = true;
						i = j;
						break;

					}
				}
				if (flag) {
					row = i + 1;
					break;
				}
			}

			String tableCell1 = xls_Read.getCellValue(sheetName, tableCell);
			String xpath1 = xls_Read.getCellValue(sheetName, xpath);
			WebElement ele = null;

			String dynXpath1 = "(" + xpath1 + tableCell1 + ")[" + row + "]";
			System.out.println(dynXpath1);
			ele = driver.findElement(By.xpath(dynXpath1));
			ele.click();
			
		}}
/**
 * Description... Verify Customer Details info 
 * @throws Exception
 */
	    public void CustomerDetailsinfoVerify() throws Exception{
		clickWebElement(sheetName, "btn_custDetailsTab;xpath", "Customer Details Button", ScreenName);
		waitForSync(5);
		String actText="";
		int rowNo=0;
		try{
		List <WebElement>list=driver.findElements(By.xpath("//select[@name='contactTypes']"));
		System.out.println("List Size=>"+list.size());
		for(int i=0;i<list.size();i++)
		{
			Select select=new Select(list.get(i));
			String option=select.getFirstSelectedOption().getText();
			System.out.println("String option=>"+option);
			if(option.equals("eTracking")){
				rowNo=i+1;     
				break;
			}
			
		}
		System.out.println("rowNo=>"+rowNo ); 
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		
		try{
		 actText=driver.findElement(By.xpath("(//*[contains(@name,'contactFax')])["+rowNo+"]")).getAttribute("value");
		System.out.println("actText=>"+actText);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		String expText=libr.data("pmyFAX");     
		System.out.println("expText=>"+expText);
		if(actText.equals(expText)){
			customFuction.verifyScreenText(ScreenName, expText, actText,"Primary mode of channel verified", sheetName);
		}
		else{
			customFuction.verifyScreenText(ScreenName, expText, actText,"Primary mode of channel verified failed", sheetName);

		}
	
		switchToWindow("storeParent");
		Thread.sleep(2000);
		WebElement abc = driver.findElement(By.xpath("(//*[contains(@name,'additionalContacts')])["+rowNo+"]"));
		abc.click();
		waitForSync(2);
		switchToWindow("child");
		Thread.sleep(4000);

		String actText2=driver.findElement(By.xpath("(//*[@name='contactAddress'])[1]")).getAttribute("value");
		String actText3=driver.findElement(By.xpath("(//*[@name='contactAddress'])[2]")).getAttribute("value");
		String actText4=driver.findElement(By.xpath("(//*[@name='contactAddress'])[3]")).getAttribute("value");
		String expText2=libr.data("pmyEmail");
		String expText3=libr.data("secEmail");
		String expText4=libr.data("mailID");
		if((actText2.equals(expText2)) && (actText3.equals(expText3)) && (actText4.equals(expText4))){
			customFuction.verifyScreenText(ScreenName, expText2, actText2,"Secondary mode of channel verified", sheetName);
			customFuction.verifyScreenText(ScreenName, expText3, actText3,"Secondary mode of channel verified", sheetName);
			customFuction.verifyScreenText(ScreenName, expText4, actText4,"Secondary mode of channel verified", sheetName);

		}
		else{
			customFuction.verifyScreenText(ScreenName, expText2, actText2,"Secondary mode of channel verified failed", sheetName);
			customFuction.verifyScreenText(ScreenName, expText3, actText3,"Secondary mode of channel verified failed", sheetName);
			customFuction.verifyScreenText(ScreenName, expText4, actText4,"Secondary mode of channel verified failed", sheetName);

		}
		Thread.sleep(2000);
		clickButtonSwitchtoParentWindow(sheetName,"btn_popupClose;xpath", ScreenName, "Additional Contact Ok");
	}
	   /**
	    * Description... Verify Notification Preference
	    * @throws Exception
	    */
	    public void verifyNotificationPrefrence() throws Exception{
		customFuction.switchToDefaultAndContentFrame("CMT008");
		int rowNo=0;
		try{
		List <WebElement>list=driver.findElements(By.xpath("//select[@name='contactTypes']"));
		for(int i=0;i<list.size();i++)
		{
			Select select=new Select(list.get(i));
			String option=select.getFirstSelectedOption().getText();
			if(option.equals("eTracking")){
				rowNo=i+1;   
				break;
			}
		}
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		

		waitForSync(4);
		switchToWindow("storeParent");
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//*[contains(@name,'notificationPreferences')])["+rowNo+"]")).click();
		waitForSync(8);
		switchToWindow("child");

		Thread.sleep(2000);
		String locate[]={"chck_RCSemail;xpath","chck_DEPemail;xpath","chck_ARRemail;xpath","chck_RCFemail;xpath",
				"chck_NFDemail;xpath","chck_DLVemail;xpath","chck_TOAemail;xpath","chck_SSPDemail;xpath","chck_OFLDemail;xpath",
				"chck_MSCAemail;xpath","chck_FDCAemail;xpath","chck_RCSsms;xpath","chck_DEPsms;xpath","chck_ARRsms;xpath",
				"chck_RCFsms;xpath","chck_NFDsms;xpath","chck_DLVsms;xpath","chck_TOAsms;xpath","chck_SSPDsms;xpath",
				"chck_OFLDsms;xpath","chck_MSCAsms;xpath","chck_FDCAsms;xpath","chck_RCSfax;xpath","chck_DEPfax;xpath",
				"chck_ARRfax;xpath","chck_RCFfax;xpath","chck_NFDfax;xpath","chck_DLVfax;xpath","chck_TOAfax;xpath"};
		;
		String eleName[]={"RCS email","DEP email","ARR email","RCF email","NFD email","DLV email","TOA email","SSPD email",
				"OFLD email","MSCA email","FDCA email","RCS sms","DEP sms","ARR sms","RCF sms","NFD sms","DLV sms","TOA sms",
				"SSPD sms","OFLD sms","MSCA sms",
				"FDCA sms","RCS fax","DEP fax","ARR fax","RCF fax","NFD fax","DLV fax","TOA fax"};
		for (int i = 0; i <locate.length; i++){
			waitForSync(3);
			customFuction.checkIfUnchecked(sheetName, locate[i], eleName[i],
					"Company Notification Settings");
		}
		clickButtonSwitchtoParentWindow(sheetName,"btn_notificationOK;xpath",ScreenName,"Notification Prefrence Ok");
	}
/**
 * Description... Verify Customer Prefrences
 * @param expectedFSU
 * @throws Exception
 */
	    public void customerPrefrences(String expectedFSU) throws Exception {
		Thread.sleep(2000);
		customFuction.switchToDefaultAndContentFrame("CMT008");
		
		clickWebElement(sheetName,"tab_customerPrefrences;xpath","Customer Prefrences tab", ScreenName);
		waitForSync(4);
		
		String actual=getAttributeWebElement(sheetName, "tab_customerPrefrencesFSUstobenotified;xpath", "FSUs to be notified", "value", ScreenName);
		String expected=expectedFSU;
		if(actual.equals(expected))
		{
			customFuction.verifyScreenText(ScreenName, expected, actual,"FSU to be notified", sheetName);

		}
		else{
			customFuction.verifyScreenText(ScreenName, expected, actual,"FSU to be notified failed", sheetName);

		}
	}
/**
 * Description...	Click Customer Details Button, Check all checkboxes in Company Notification Settings pop up
 * @throws Exception
 */
public void verifycustDetailsTOA()throws Exception{
              clickWebElement(sheetName, "btn_custDetailsTab;xpath", "Customer Details Button", ScreenName);
                     waitForSync(5);

                     libr.getFirstSelectedOptionDropdown(sheetName,"lst_typeDetails;xpath", "eTracking Verification");
                     libr.getFirstSelectedOptionDropdown(sheetName,"lst_typeDetails2;xpath", "eTrackingTOA Verification");
                     String actual=driver.findElement(By.xpath("(//input[@value='rubashree.arun@ibsplc.com']/ancestor::tr//*[contains(@name,'contactMobile')])")).getAttribute("value");
               String expected=libr.data("pmySMS");
               if(actual.equals(expected)){
                           customFuction.verifyScreenText(ScreenName, expected, actual,"Primary mode of channel verified", sheetName);
                     }
                     else{
                           customFuction.verifyScreenText(ScreenName, expected, actual,"Primary mode of channel verified failed", sheetName);

                     }
               clickButtonSwitchWindow(sheetName,"btn_additionalContactTOA;xpath", ScreenName, "Additional Contact");
                     Thread.sleep(4000);
                     Thread.sleep(4000);
                     
                     String actText2=driver.findElement(By.xpath("(//*[@name='contactAddress'])[1]")).getAttribute("value");
                     String actText3=driver.findElement(By.xpath("(//*[@name='contactAddress'])[2]")).getAttribute("value");
                     String actText4=driver.findElement(By.xpath("(//*[@name='contactAddress'])[3]")).getAttribute("value");
            String expText2=libr.data("pmyEmail");
            String expText3=libr.data("secEmail");
            String expText4=libr.data("mailID");
            if((actText2.equals(expText2)) && (actText3.equals(expText3)) && (actText4.equals(expText4))){
                           customFuction.verifyScreenText(ScreenName, expText2, actText2,"Secondary mode of channel verified", sheetName);
                           customFuction.verifyScreenText(ScreenName, expText3, actText3,"Secondary mode of channel verified", sheetName);
                           customFuction.verifyScreenText(ScreenName, expText4, actText4,"Secondary mode of channel verified", sheetName);

            }
            else{
                          customFuction.verifyScreenText(ScreenName, expText2, actText2,"Secondary mode of channel verified failed", sheetName);
                          customFuction.verifyScreenText(ScreenName, expText3, actText3,"Secondary mode of channel verified failed", sheetName);
                          customFuction.verifyScreenText(ScreenName, expText4, actText4,"Secondary mode of channel verified failed", sheetName);

            }
            Thread.sleep(2000);
            clickButtonSwitchtoParentWindow(sheetName,"btn_popupClose;xpath", ScreenName, "Additional Contact Ok");
            
            customFuction.switchToDefaultAndContentFrame("CMT008");
              waitForSync(4);
              clickButtonSwitchWindow(sheetName,"btn_notificationPrefrenceTOA;xpath", ScreenName, "Notification Prefrence");
              Thread.sleep(2000);
              String locate[]={"chck_RCSemail;xpath","chck_DEPemail;xpath","chck_ARRemail;xpath","chck_RCFemail;xpath",
                     "chck_NFDemail;xpath","chck_DLVemail;xpath","chck_TOAemail;xpath","chck_SSPDemail;xpath","chck_OFLDemail;xpath",
                     "chck_MSCAemail;xpath","chck_FDCAemail;xpath","chck_RCSsms;xpath","chck_DEPsms;xpath","chck_ARRsms;xpath",
                     "chck_RCFsms;xpath","chck_NFDsms;xpath","chck_DLVsms;xpath","chck_TOAsms;xpath","chck_SSPDsms;xpath",
                           "chck_OFLDsms;xpath","chck_MSCAsms;xpath","chck_FDCAsms;xpath"};
                           ;
              String eleName[]={"RCS email","DEP email","ARR email","RCF email","NFD email","DLV email","TOA email","SSPD email",
                           "OFLD email","MSCA email","FDCA email","RCS sms","DEP sms","ARR sms","RCF sms","NFD sms","DLV sms","TOA sms",
                           "SSPD sms","OFLD sms","MSCA sms",
                           "FDCA sms"};
              for (int i = 0; i <locate.length; i++){
                     waitForSync(3);
                     customFuction.checkIfUnchecked(sheetName, locate[i], eleName[i],
                                         "Company Notification Settings");
              }
               clickButtonSwitchtoParentWindow(sheetName,"btn_notificationOK;xpath",ScreenName,"Notification Prefrence Ok");
               
           }

/**
 * Description... 	Verify LAT and TOA Customer Preferences TOA
 * @throws Exception
 */
public void verifycustPreferencesTOA()throws Exception{
              String actual=driver.findElement(By.xpath("(//*[@class='iCargoTextFieldExtraLong'])[7]")).getAttribute("value");
              String expected="Y";
              if(actual.equals(expected)){
                           customFuction.verifyScreenText(ScreenName, expected, actual,"Yes verified for LAT and TOA", sheetName);
                     }
                     else{
                           customFuction.verifyScreenText(ScreenName, expected, actual,"Yes verified for LAT and TOA FAILED", sheetName);

                     }
              
           }
/**
 * Description... Click Customer Preferences Tab
 * @throws Exception
 */
public void clickCustomerPreferencesTab()throws Exception{
              customFuction.switchToDefaultAndContentFrame("CMT008");
              clickWebElement(sheetName,"tab_customerPrefrences;xpath","Customer Prefrences tab", ScreenName);
              }
/**
 * Description... Click Save Button
 * @throws Exception
 */
public void clickSave()throws Exception{
              clickWebElement(sheetName,"btn_Save1;xpath","Save button",ScreenName);
              Thread.sleep(2000);
           }
		   
		   public void verifycustDetailsTOA2() throws Exception{
		clickWebElement(sheetName, "btn_custDetailsTab;xpath", "Customer Details Button", ScreenName);
		waitForSync(5);
		String actText="";
		int rowNo=0;
		try{
		List <WebElement>list=driver.findElements(By.xpath("//select[@name='contactTypes']"));
		for(int i=0;i<list.size();i++)
		{
			Select select=new Select(list.get(i));
			String option=select.getFirstSelectedOption().getText();
			if(option.equals("eTracking")){
				rowNo=i+1;     
				break;
			}
			
		}
		System.out.println("rowNo=>"+rowNo ); 
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		
		try{
		 actText=driver.findElement(By.xpath("(//*[contains(@name,'contactMobile')])["+rowNo+"]")).getAttribute("value");
		System.out.println("actText=>"+actText);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		String expText=libr.data("pmySMS");     
		System.out.println("expText=>"+expText);
		if(actText.equals(expText)){
			customFuction.verifyScreenText(ScreenName, expText, actText,"Primary mode of channel verified", sheetName);
		}
		else{
			customFuction.verifyScreenText(ScreenName, expText, actText,"Primary mode of channel verified failed", sheetName);

		}
		switchToWindow("storeParent");
		Thread.sleep(2000);
		WebElement abc = driver.findElement(By.xpath("(//*[contains(@name,'additionalContacts')])["+rowNo+"]"));
		abc.click();
		waitForSync(2);
		switchToWindow("child");
		Thread.sleep(4000);

		String actText2=driver.findElement(By.xpath("(//*[@name='contactAddress'])[1]")).getAttribute("value");
		String actText3=driver.findElement(By.xpath("(//*[@name='contactAddress'])[2]")).getAttribute("value");
		String actText4=driver.findElement(By.xpath("(//*[@name='contactAddress'])[3]")).getAttribute("value");
		String expText2=libr.data("pmyEmail");
		String expText3=libr.data("secEmail");
		String expText4=libr.data("mailID");
		if((actText2.equals(expText2)) && (actText3.equals(expText3)) && (actText4.equals(expText4))){
			customFuction.verifyScreenText(ScreenName, expText2, actText2,"Secondary mode of channel verified", sheetName);
			customFuction.verifyScreenText(ScreenName, expText3, actText3,"Secondary mode of channel verified", sheetName);
			customFuction.verifyScreenText(ScreenName, expText4, actText4,"Secondary mode of channel verified", sheetName);

		}
		else{
			customFuction.verifyScreenText(ScreenName, expText2, actText2,"Secondary mode of channel verified failed", sheetName);
			customFuction.verifyScreenText(ScreenName, expText3, actText3,"Secondary mode of channel verified failed", sheetName);
			customFuction.verifyScreenText(ScreenName, expText4, actText4,"Secondary mode of channel verified failed", sheetName);

		}
		Thread.sleep(2000);
		clickButtonSwitchtoParentWindow(sheetName,"btn_popupClose;xpath", ScreenName, "Additional Contact Ok");
		
		waitForSync(5);
		customFuction.switchToDefaultAndContentFrame("CMT008");
		waitForSync(3);
		switchToWindow("storeParent");
		waitForSync(2);
		driver.findElement(By.xpath("(//*[contains(@name,'notificationPreferences')])["+rowNo+"]")).click();	
		waitForSync(8);
		switchToWindow("child");
		
		waitForSync(2);
		String locate[]={"chck_RCSemail;xpath","chck_DEPemail;xpath","chck_ARRemail;xpath","chck_RCFemail;xpath",
				"chck_NFDemail;xpath","chck_DLVemail;xpath","chck_TOAemail;xpath","chck_SSPDemail;xpath","chck_OFLDemail;xpath",
				"chck_MSCAemail;xpath","chck_FDCAemail;xpath","chck_RCSsms;xpath","chck_DEPsms;xpath","chck_ARRsms;xpath",
				"chck_RCFsms;xpath","chck_NFDsms;xpath","chck_DLVsms;xpath","chck_TOAsms;xpath","chck_SSPDsms;xpath",
				"chck_OFLDsms;xpath","chck_MSCAsms;xpath","chck_FDCAsms;xpath"};
		
		String eleName[]={"RCS email","DEP email","ARR email","RCF email","NFD email","DLV email","TOA email","SSPD email",
				"OFLD email","MSCA email","FDCA email","RCS sms","DEP sms","ARR sms","RCF sms","NFD sms","DLV sms","TOA sms",
				"SSPD sms","OFLD sms","MSCA sms","FDCA sms"};
		for (int i = 0; i <locate.length; i++){
			waitForSync(3);
			customFuction.checkIfUnchecked(sheetName, locate[i], eleName[i],
					"Company Notification Settings");
		}
		clickButtonSwitchtoParentWindow(sheetName,"btn_notificationOK;xpath",ScreenName,"Notification Prefrence Ok");

		
	}
/**
 * Description... Enter PIMA Address
 * @param pimaAdd
 * @throws InterruptedException
 */
	public void enterPIMAAddress(String pimaAdd) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_PIMAAddress;xpath", pimaAdd, "PIMA Address", ScreenName);
		
		
	}

	



}