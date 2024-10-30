import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TimeCalc {

	public static void main(String[] args) throws IOException {
		 String folderName="D:\\Reports\\";
		 BufferedReader br1 = new BufferedReader(new FileReader(folderName+"mergedReport.txt"));
		 
		 String line1 = br1.readLine(); 
		 
		 int hour=0;
		 int mins=0;
		 int sec=0;
		 String time="";
		 
		 while (line1 != null ) 
     	{
			 
			if(line1.contains("<span title='Time taken to finish'")) 
			{
				time=line1.split(">")[1].split("</span>")[0];
				hour=hour+Integer.parseInt(time.split(" ")[0].split("h")[0]);
				mins=mins+Integer.parseInt(time.split(" ")[1].split("m")[0]);
				sec=sec+Integer.parseInt(time.split(" ")[2].split("s")[0]);
				line1 = br1.readLine();
			}
			
			else
			{
				line1 = br1.readLine(); 
			}
			 
     	}
		  
		  System.out.println("Total Time Taken "+(hour)+"h "+(mins)+"m "+(sec)+"s ");
		 

	}

}
