package common;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;

import org.apache.commons.io.FileUtils;

public class SelectStation {

	   private Frame mainFrame;
	   private Label headerLabel;
	   private Label statusLabel;
	   private Panel controlPanel;

	   public SelectStation(){
	      prepareGUI();
	   }

	   public static void main(String[] args){
		   SelectStation  awtControlDemo = new SelectStation();
	      awtControlDemo.showTextFieldDemo();
	   }
	   
	   public boolean copyFiles(String station)
	   {
		   //Copy test data
		   File source = new File(System.getProperty("user.dir")+"\\src\\resources\\TestData\\"+station+"\\");
		   File dest = new File(System.getProperty("user.dir")+"\\src\\resources\\");
		   
		   //Copy test case , suite and module excels
		   File source2 = new File(System.getProperty("user.dir")+"\\src\\resources\\TestCase\\"+station+"\\");
		   File dest2 = new File(System.getProperty("user.dir")+"\\src\\resources\\TestCase\\");
		   
		   
		   //Raname the folder
		   File sourceFile = new File(System.getProperty("user.dir")+"\\src\\test_"+station);
		   File destFile = new File(System.getProperty("user.dir")+"\\src\\test");
		   
		   sourceFile.renameTo(destFile);
		   
		   try {
		       FileUtils.copyDirectory(source, dest);
		       FileUtils.copyDirectory(source2, dest2);
		     
		       return true;
		   } catch (IOException e) {
		       e.printStackTrace();
		       return false;
		   }
	   }

	   private void prepareGUI(){
	      mainFrame = new Frame("Java AWT");
	      mainFrame.setSize(400,400);
	      mainFrame.setLayout(new GridLayout(3, 1));
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });    
	      headerLabel = new Label();
	      headerLabel.setAlignment(Label.CENTER);
	      statusLabel = new Label();        
	      statusLabel.setAlignment(Label.CENTER);
	      statusLabel.setSize(350,100);

	      controlPanel = new Panel();
	      controlPanel.setLayout(new FlowLayout());

	      mainFrame.add(headerLabel);
	      mainFrame.add(controlPanel);
	      mainFrame.add(statusLabel);
	      mainFrame.setVisible(true);  
	   }

	   private void showTextFieldDemo(){
	      headerLabel.setText("Enter Suite Name"); 

	      Label  namelabel= new Label("Stations : ", Label.RIGHT);
	      String projects[]={"--Select--","sanity","smoke","regression"};
	     
	     final JComboBox combobox=new JComboBox(projects);

	      Button submitButton = new Button("Submit");
	  
	   
	      submitButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {     
	        	String data="";
	            String station=combobox.getSelectedItem().toString();
	            
	            if(!station.equals("--Select--"))
	            {
	            	boolean actionDone=copyFiles(station);
	            	 if(actionDone)
	 	            	data="Data copied for the station : "+station;
	 	            else
	 	            	data="Could not copy data for the station : "+station;
	            }
	            else
	            {
	            	data="Please select a station";
	            }
	            statusLabel.setText(data);        
	         }
	      }); 
	      
	     

	      controlPanel.add(namelabel);
	      controlPanel.add(combobox);
	      controlPanel.add(submitButton);
	      mainFrame.setVisible(true);  
	   }
	}



