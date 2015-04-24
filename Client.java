 package com.fundraising.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

//Beginning of Main
public static void main(String[] args) throws UnknownHostException, IOException
{

	String hostname = "localhost";
	int port = 9200;
	System.out.println("Please Enter the Commnads\n");
	//reads input string from the user
	   BufferedReader in = new BufferedReader(
		        new InputStreamReader(System.in));
	
	   String input;
	   
	   while((input = in.readLine()) != null)
	   {
		   //breaks if user has not typed any command
		   if(input.isEmpty())
		   {
			   System.out.println("Connection Ended Because NO input was Entered\n");
			   System.out.println("Connect Again and Try pledge mary 50000\n");
			   break;
		   }
		   
		   //send user inputs, hostname, port to the server with the function send
		   System.out.println(send(input, hostname, port));
	   }//End of while
	   
}//End of Main



//Method Send
private static String send(String msg, String hostname, int port) throws UnknownHostException, IOException
{
	//Creating a new socket connection
	Socket server = new Socket(hostname, port);
	
	//Sends the message to the server for processing
	PrintWriter out = new PrintWriter(server.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
	
    String fromServer = "";
    
    //Displays the message from user
    out.println(msg);
    fromServer = in.readLine();
    
    server.close();//Closes Connection
    return fromServer;//Returns message from the server
    
}//End of method send

}//End of class client