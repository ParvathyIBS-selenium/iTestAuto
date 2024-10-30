package common;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BuildVersionCheck extends BaseSetup {

	public static String buildpath = "\\src\\resources\\buildVersion.properties";	
	public static String s2 = System.getProperty("user.dir");

	public void versionCheck(){

		try{

			if(Integer.parseInt(getPropertyValue(buildpath,"checkDate").replace("-",""))!= Integer.parseInt(java.time.LocalDateTime.now().toString().split("T")[0].replace("-",""))){

				System.setProperty("webdriver.chrome.driver", s2+"\\lib\\chromedriver.exe");		
				ChromeOptions opt= new ChromeOptions();
				opt.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
				opt.addArguments("headless");

				WebDriver headlessdriver=new ChromeDriver(opt);

				WebFunctions libr = new WebFunctions(headlessdriver, excelreadwrite, xls_Read);
				//Login to iCargo
				String[] iCargo = libr.getApplicationParams("iCargoSTG");
				headlessdriver.get(iCargo[0]);
				Thread.sleep(2000);
				System.out.println(headlessdriver.getTitle());	
				headlessdriver.findElement(By.id("username")).sendKeys(iCargo[1]+Keys.ENTER);
				Thread.sleep(1000);
				headlessdriver.findElement(By.id("inputPassName")).sendKeys(iCargo[2]+Keys.ENTER);
				System.out.println(headlessdriver.getTitle());	

				String parent=headlessdriver.getWindowHandle();
				Set<String> child = headlessdriver.getWindowHandles();
				Iterator<String> iter=child.iterator();

				while(iter.hasNext()){
					String indchild=iter.next();
					if(!parent.equalsIgnoreCase(indchild))
						headlessdriver.switchTo().window(indchild);
				}

				WebElement element=headlessdriver.findElement(By.xpath("//span[@title='Click Here']"));
				WebElement element1=headlessdriver.findElement(By.xpath("//span[@class='ic-about']"));

				JavascriptExecutor executor = (JavascriptExecutor) headlessdriver;
				executor.executeScript("arguments[0].click();", element);
				Thread.sleep(2000);	
				executor.executeScript("arguments[0].click();", element1);
				Thread.sleep(2000);	

				Alert alert=headlessdriver.switchTo().alert();
				String txt=alert.getText();	
				String newversion=txt.split("_")[1].split(" ")[0].trim();
				System.out.println("New = "+newversion);
				alert.accept();
				headlessdriver.quit();

				String oldversion=getPropertyValue(buildpath,"buildVersion");
				System.out.println("Old = "+oldversion);
				setPropertyValue("buildVersion", newversion,buildpath);
				setPropertyValue("checkDate",java.time.LocalDateTime.now().toString().split("T")[0] ,buildpath);

				if(Integer.parseInt(newversion.replace(".","")) != Integer.parseInt(oldversion.replace(".","")))
					System.out.println("VERSION CHANGE OBSERVED FROM "+oldversion+" to "+newversion);

			}

		}

		catch(Exception e){
			e.printStackTrace();
			System.out.println("Version Check Failed");
		}

	}
	public static void main(String[] args) throws IOException, InterruptedException{

		BuildVersionCheck  bvc = new BuildVersionCheck();
		bvc.versionCheck();
	}

}