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

import org.apache.commons.io.FileUtils;


public class AwtDemo {

	   private Frame mainFrame;
	   private Label headerLabel;
	   private Label statusLabel;
	   private Panel controlPanel;

	   public AwtDemo(){
	      prepareGUI();
	   }

	   public static void main(String[] args){
		   AwtDemo  awtControlDemo = new AwtDemo();
	      awtControlDemo.showTextFieldDemo();
	   }
	   
	   public boolean copyFiles(String testEnv)
	   {
		   File source = new File(System.getProperty("user.dir")+"\\src\\resources\\propertyfiles\\"+testEnv+"\\");
		   File dest = new File(System.getProperty("user.dir")+"\\src\\resources\\");
		   try {
		       FileUtils.copyDirectory(source, dest);
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
	      headerLabel.setText("Enter the Test Evn Details"); 

	      Label  namelabel= new Label("Test Env: ", Label.RIGHT);
	     
	       final TextField userText = new TextField(6);

	      Button submitButton = new Button("Submit");
	      Button clearButton = new Button("Clear");
	   
	      submitButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {     
	        	String data="";
	            String testEnv=userText.getText();
	            
	            if(!testEnv.equals(""))
	            {
	            boolean actionDone=copyFiles(testEnv);
	            if(actionDone)
	            	data="Property File is copied successfully for the testEnv : "+testEnv;
	            else
	            	data="Property File does not exist for the testEnv : "+testEnv;
	            }
	            else
	            {
	            	data="TestEnv field cannot be blank";
	            }
	            statusLabel.setText(data);        
	         }
	      }); 
	      
	      clearButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {     
		            
		        	userText.setText(" ");  
		        	statusLabel.setText(" ");  
		         
		                
		         }
		      }); 

	      controlPanel.add(namelabel);
	      controlPanel.add(userText);
	      controlPanel.add(submitButton);
	      controlPanel.add(clearButton);
	      mainFrame.setVisible(true);  
	   }
	}


