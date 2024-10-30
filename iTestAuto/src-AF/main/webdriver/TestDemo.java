import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;


public class TestDemo {
	
	
	public static void main(String[] args) throws IOException
	
	
{
		try {
            String[] command = {"cmd.exe", "/C", "Start", "D:\\tests\\UI.bat"};
            Process p =  Runtime.getRuntime().exec(command);           
        } catch (IOException ex) {
        }
}
}
