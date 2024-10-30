package common;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class screenShots {
	
	public WebDriver driver;
	
	
	screenShots (WebDriver driver,String screenshotfilepath) throws InterruptedException, IOException
	{
		        this.driver = driver;
	      
		        // take the screenshot at the end of every test
		       
		        Date dNow = new Date( ); //create object of date class 
		        SimpleDateFormat SDF = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz"); // sending the date format for display the mention format.
		          
		        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); // take the  screen shot 
		        
		        // now save the screenshto to a file some place
		        
		        Thread.sleep(500);
		       
		        String SSFN = screenshotfilepath + SDF.format(dNow)+".png";// creating out put file path and screen shot file name with time stamp load to folder.
		        
		        FileUtils.copyFile(scrFile, new File(SSFN ));  // saving file in path mention folder 
                Thread.sleep(1000);
		       
                //quit WebDriver session
		       driver.quit(); // closeing the browser 
   	}
}



