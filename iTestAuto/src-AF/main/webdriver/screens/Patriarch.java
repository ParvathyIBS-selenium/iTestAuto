package screens;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;









import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class Patriarch extends CustomFunctions {

	String sheetName = "patriarch_screen";
	String screenName = "patriarch_screen";


	public Patriarch(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : Click Picasso link
	 */
	public void invokePicasso() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "htmlDiv_picasso;xpath", "Picasso link", screenName);
		waitForSync(2);
	}
	/**
	 * @author A-9847
	 * @Desc To click Cancel
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void cancel() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_cancel;xpath", "Cancel", screenName);
		waitForSync(2);
	}
	/**
	 * @author A-9847
	 * @Desc To enter Flight Number and Flight Date
	 * @param fullFlightNo
	 * @param fltDate
	 * @throws InterruptedException
	 */
	public void enterFlight(String fullFlightNo, String fltDate) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_fltNumber;id", data(fullFlightNo), "Flight Number", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_fltDate;id", data(fltDate), "Flight Date", screenName);
	}
	
	/**
	 * @author A-7271
	 * @param awb
	 * @throws InterruptedException
	 * Desc : enter awb number
	 */
	public void enterAWB(String awb) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(awb).replaceAll("-", ""), "Awb Number", screenName);
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : Click submit
	 */
	public void submit() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_submit;name", "Submit", screenName);
		waitForSync(2);
	}
	/**@author A-10328
	* Description - Enter ULD Number
	* @param ULDNo
	* @throws InterruptedException
	*/
	public void enterULDNo(String ULDNo) throws InterruptedException
	{
	enterValueInTextbox(sheetName, "inbx_UldNo;xpath", data(ULDNo), "ULD Number", screenName);
	waitForSync(1);
	}

	/**
	 * @author A-7271
	 * @param expRows
	 * Desc : verify awb details got listed in the patriarch
	 */
	public void verifyAwbRecords(int expRows,String awb)
	{
		try
		{
			String xpath=xls_Read.getCellValue(sheetName, "table_picasso;xpath");
			//Getting the row count

			int actRows=driver.findElements(By.xpath(xpath)).size();

			if(actRows>1)
			{
				if(actRows==(expRows+1))
				{
					writeExtent("Pass","Details got listed in the patriarch for "+data(awb));    
				}
				else
				{
					writeExtent("Fail","Expected records in the patriarch : "+expRows+" Actual"
							+ " records in the patriarch : "+(actRows-1));
				}
			}

			else
			{
				writeExtent("Fail","No details got listed in the patriarch for "+data(awb));
			}
		}



		catch(Exception e)
		{
			writeExtent("Fail","No details got listed in the patriarch for "+data(awb));
		}

		}

	
	
	public void verifyDocumentType(String...expDocTypes)
	{
		String xpath=xls_Read.getCellValue(sheetName, "table_picasso;xpath");
		boolean docType = true;
		try
		{
		//Getting the row count

		int actRows=driver.findElements(By.xpath(xpath)).size();
		String dynXpath="";
		List<WebElement> doc=driver.findElements(By.xpath(xpath));
		List<String>actDocType =new ArrayList<String>();

		if(actRows>1)
		{
			for(int i=2;i<=doc.size();i++)
			{
				dynXpath="("+xpath+")["+i+"]//td[3]";
				actDocType.add(driver.findElement(By.xpath(dynXpath)).getText());
			}
		}
		
		
		
		for(int j=0;j<expDocTypes.length;j++)
		{
			if(!actDocType.contains(expDocTypes[j]))
			docType =false;
		}
		
		if(docType)
		{
			writeExtent("Pass","Document type displayed are "+actDocType+" on "+screenName);
		}
		else
		{
			writeExtent("Fail","Mismatch in the document type displayed . Expected values are : "+Arrays.asList(expDocTypes)+" Actual values are : "
					+ actDocType);
		}
		
		
		}
		
		catch(Exception e)
		{
			writeExtent("Fail","Could not get the docType on "+screenName);
		}
	}
	/**
	 * @author A-7271
	 * @param fileName
	 * @throws InvalidPasswordException
	 * @throws IOException
	 * Desc : read data from the pdf
	 */
	public void readDocument(String fileName) throws InvalidPasswordException, IOException
	{
		String filePath=System.getProperty("user.dir")+"\\src\\resources\\patriarch_reports\\";
		List<String> docLines=new ArrayList<String>();
		 
		try (PDDocument document = PDDocument.load(new File(filePath+fileName))) {

            document.getClass();

            if (!document.isEncrypted()) {
			
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
               
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                   
                	 System.out.println(line);
                	 docLines.add(line);
                }
              
                writeExtent("Info","Document contents are : "+docLines);

            }

        }
		
		catch(Exception e)
		{
			writeExtent("Fail","Could not extract the data from the document "+fileName);
		}
	}
	/**
	 * @author A-7271
	 * @param tcName
	 * @throws AWTException
	 * @throws InterruptedException
	 * Desc : save document
	 */
	public void saveDocument(String fileName) throws AWTException, InterruptedException
	{
		try
		{
			String filePath=System.getProperty("user.dir")+"\\src\\resources\\patriarch_reports\\";
			deleteFileIfPresent(filePath,fileName);
			waitForSync(2);
			saveActionInRobot();
			waitForSync(5);
			copyContentsWithClipboard(filePath+fileName);
			pasteActionInRobot();
			waitForSync(2);
			keyPress("ENTER");
			waitForSync(1);
			writeExtent("Pass","File '"+fileName+"' generated and stored in "+filePath);

		}
		
		catch(Exception e)
		{
			writeExtent("Fail","Error in saving the file "+fileName);
		}
		
	}
	
	/**
	 * @author A-7271
	 * @param tcName
	 * @throws IOException
	 * Desc : takes and add screenshot to the report
	 */
	public void takescreenshot() throws IOException
	{
		try
		{
		writeExtent("AddScreenShot",captureScreenShot("Web"));
		}
		
		catch(Exception e)
		{
			
		}
				
				
	}
	/**
	 * @author A-7271
	 * Desc : save document
	 */
	public void generateDocument(String tcName)
	{
		try
		{
			String xpath=xls_Read.getCellValue(sheetName, "table_picasso;xpath");
			String dynXpath="";
			String dynXpath2="";
			String parentWin="";
			List<WebElement> doc=driver.findElements(By.xpath(xpath));
			
			int size=0;

			if(doc.size()>1)
			{
				for(int i=2;i<=doc.size();i++)
				{
					String docType=driver.findElement(By.xpath("//td[@id='picasso_"+(i-2)+"_1']")).getText();
					System.out.println(docType);
					dynXpath2="("+xpath+")["+i+"]//td[9]//a";
					dynXpath="("+xpath+")["+i+"]//td[10]//a";
					size=driver.findElements(By.xpath(dynXpath2)).size();
					 parentWin=driver.getWindowHandle();
					 
					 if(!docType.contains("XFWB"))
					 {		
						 if(!(size>=1))
							 driver.findElement(By.xpath(dynXpath)).click();
						 else
							 driver.findElement(By.xpath(dynXpath2)).click();
						 waitForSync(10);
						 for(String win:driver.getWindowHandles())
						 {

							 if(!win.equals(parentWin))
							 {
								 waitForSync(3);
								 driver.switchTo().window(win);
								 takescreenshot();
								 saveDocument(tcName+"_"+(i-1)+".pdf");
								 waitForSync(2);
								 driver.close();
								 waitForSync(2);
								 readDocument(tcName+"_"+(i-1)+".pdf");

							 }
						 }
						 driver.switchTo().window(parentWin);
					 }

				}
				
				writeExtent("Pass","Documents generated on "+ screenName);

			}


			

			else
			{
				writeExtent("Fail","No Awb details got listed on "+screenName);
			}
              System.out.println(driver.getWindowHandles().size());
              
             
             
             
              

		}


		catch(Exception e)
		{
			writeExtent("Fail","Documents not generated on " +screenName);
		}
	}
	/**
	 * @author A-7271
	 * Desc : save document
	 * To download the reports by giving the index
	 */
	public void generateDocument(String tcName,int startIndex)
	{
		try
		{
			String xpath=xls_Read.getCellValue(sheetName, "table_picasso;xpath");
			String dynXpath="";
			String dynXpath2="";
			String parentWin="";
			List<WebElement> doc=driver.findElements(By.xpath(xpath));
			
			int size=0;

			if(doc.size()>1)
			{
				for(int i=startIndex;i<=doc.size();i++)
				{
					dynXpath2="("+xpath+")["+i+"]//td[9]//a";
					dynXpath="("+xpath+")["+i+"]//td[10]//a";
					size=driver.findElements(By.xpath(dynXpath2)).size();
					 parentWin=driver.getWindowHandle();
					 if(!(size>=1))
					driver.findElement(By.xpath(dynXpath)).click();
					 else
				    driver.findElement(By.xpath(dynXpath2)).click();
					waitForSync(10);
					for(String win:driver.getWindowHandles())
					{
						
						if(!win.equals(parentWin))
						{
							waitForSync(3);
							driver.switchTo().window(win);
							takescreenshot();
							saveDocument(tcName+"_"+(i-1)+".pdf");
							waitForSync(2);
							driver.close();
							waitForSync(2);
							readDocument(tcName+"_"+(i-1)+".pdf");
			           
						}
					}
					driver.switchTo().window(parentWin);

				}
				
				writeExtent("Pass","Documents generated on "+ screenName);

			}


			

			else
			{
				writeExtent("Fail","No Awb details got listed on "+screenName);
			}
              System.out.println(driver.getWindowHandles().size());
              
             
             
             
              

		}


		catch(Exception e)
		{
			writeExtent("Fail","Documents not generated on " +screenName);
		}
	}
}

