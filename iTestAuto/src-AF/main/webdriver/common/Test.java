package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test {

	public static void main(String[] args) {

		try
		{

			
				//String path = message_files + fileName + ".xml";
				
				String path ="D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\TestNG_F.xml";
		         String oldtext = "";
		         BufferedReader reader = new BufferedReader(new FileReader(path));
		         String line = "";
		         String newLine="";
		       
		      
		         
		         while ((line = reader.readLine()) != null) {
		        	 

		        	 //Updating the SCC Details
		        	 if (line.contains("<testdetails>")) {
		        		

		        		 

		        			
		        				 newLine=newLine+"<updatedtestdetails>";
		        			
		        			 line = newLine;
		        			 oldtext += line+ System.getProperty("line.separator");	        		
		        			
		        			 newLine="";
		        			 System.out.println(line);
		        		
		        	 }      		 
		        	 
		        	
		           
		      		  else{
		      			  oldtext += line+System.getProperty("line.separator");
		      			 System.out.println(line);
		      		  }	

		      	
		      		
		         }
		         BufferedWriter erasor = new BufferedWriter(new FileWriter(path));
		         erasor.write(oldtext);
		         erasor.close();
		         reader.close();
		        
		        
		

				
		}
			
			catch(Exception e)
			{
				  
			}
   
	
	
	}
}

