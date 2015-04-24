 /*
AMPAIRE CHRISTINE    210008949 10/U/9183/PS
ABOK ISAAC 210007924 10/U/9168/PS
OTIM FREDRICK 212022189 12/U/13682/PS
*/

package com.fundrainsing.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//Beginning of server class
public class Server {
	public static void main(String[] args) throws IOException
	{
		//Port Number
		int port = 9200;
		
		//Creating a socket
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);
	   
		System.out.println("Server is waiting...\n");
		while(true)
		{
			//Accepting the socket connection from the client
			Socket client = serverSocket.accept();
			
		   // Reading the message or commands received from the client
		   PrintWriter out =
			        new PrintWriter(client.getOutputStream(), true);
		// storing the message or the commands received from the client
		    BufferedReader in = new BufferedReader(
		        new InputStreamReader(client.getInputStream()));
		    
		    
		    String inputLine, outputLine;
		    
		    while ((inputLine = in.readLine()) != null) {
		        
		    	//calling the processInput class from the FundraiserApp.java and passing in
		    	//arguments received from the client
		    	outputLine = FundraiserApp.processInput(inputLine);
		        
		    	//Displaying the message or result from the processInput class in the FundraiserApp.java
		        out.println(outputLine);
		        
		        //Ends the server connection if the client or the user command is bye
		        if (outputLine.equals("Bye")){
		        	out.println(outputLine);
		        	//out.println("Connection has been Ended Successfully..");
		        	//serverSocket.close();//Closes Connection
		            break;
		        }
		    }// End of inputLine while loop	
		}//End of while loop
	}//End of Main 
}//End of class server
