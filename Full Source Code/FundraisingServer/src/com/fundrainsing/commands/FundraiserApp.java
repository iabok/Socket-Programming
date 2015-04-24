 /*
AMPAIRE CHRISTINE    210008949 10/U/9183/PS
ABOK ISAAC 210007924 10/U/9168/PS
OTIM FREDRICK 212022189 12/U/13682/PS
*/

package com.fundrainsing.commands;

import java.util.Date;

//Beginning of class FundraiserApp
public class FundraiserApp {

	public static String processInput(String inputLine) {
		// TODO Auto-generated method stub
		
		//Storing the set of strings into an array where there is a space that becomes the first
		//array with index of tokens[0]
		//E.g (pledge Mary 500) becomes tokens['pledge', 'Mary', '500']. which equals
		//tokens[0] = pledge
		//tokens[1] = Mary
		//tokens[2] = 500
		//the split functions helps determine the start of the next array position
		String[] tokens = inputLine.trim().split(" ");
		
		//Creating a new object to manage Data Manipulation to the Mysql Database interactions
		Datamanagement db = new Datamanagement();
		db.CreateDatabase();//Creates an object for the creating the Database called Fundarising
		db.createFundraisingTable();//Creates the table collections
		
		String result = "";
		//switch statement to loop through by getting the first command in the tokens array
		//E.g (pledge Mary 500) has the command becomes tokens['pledge', 'Mary', '500']
		// And tokens[0]=pledge is passed to the switch statement
		switch( tokens[0].toLowerCase() )
		{
			
			case "pledge":
				//Validating making sure the client sends two more arguments alongside pledge
				if(tokens.length != 3)
				{
					result = "EXPECT 2 INPUTS FOR PLEDGE(E.g Try pledge mary 50000)";
					break;
				}
				String date = new Date().toString();
				String name = tokens[1];
				String amt  = tokens[2];
				int amount = Integer.parseInt(amt);//changing the string to an integer
				//Calling the Method addFund Datamanagment class Found in the Datamanagement.java
				db.addFund(name, date, amount);
				result = "SUCCESSFULLY ADDED "+name+" WITH PLEDGE "+amt+" AT TIME "+date+"";
			break;
			
			case "getcollection":
				//Validating making sure the client sends to only one command
				if(tokens.length >= 2)
				{
					result = "EXPECT NOT INPUT FOR TOTAL AMOUNT COLLECTED (E.g Try getCollection)";
					break;
				}
				//Calling the Method totalFunds  and returning the
				//result as a string
				result = "TOTAL  AMOUNT COLLECTED IS: " + db.totalFunds();
			break;
			
			case "printreport":
				//Validating making sure the client sends to only one command
				if(tokens.length >= 2)
				{
					result = "EXPECT NOT INPUT FOR THE REPORT (E.g Try Printreport)";
					break;
				}
				//Calling the Method report create the report.txt file
				db.report();
				result = "REPORT SUCCESSFULLY CREATED ON YOUR DESKTOP WITH FILENAME report.txt";
			break;
		
			case "search":
				//Validating making sure the client sends one more command
				if(tokens.length != 2)
				{
					result = "EXPECT 1 INPUT FOR SEARCH(E.g Try search mary)";
					break;
				}
				
				String query = tokens[1];
				//Calling the Method Search and returning the
				//result as a string
				result = "TOTAL AMOUNT FOR "+query+" : " + db.searchRecords(query);
			break;
			
			case "gethighest":
				//Validating making sure the client sends  only one command
				if(tokens.length >= 2)
				{
					result = "EXPECT NOT INPUT FOR HIGHEST AMOUNT (E.g Try getHighest)";
					break;
				}
				//Calling the Method maxFunds for getting the highest Amount and returning the
				//result as a string
				result = "HIGHEST AMOUNT CONTRIBUTED IS: " + db.maxFunds();
			break;
			
			case "getlowest":
				//Validating making sure the clients sends  only one command
				if(tokens.length >= 2)
				{
					result = "EXPECT NOT INPUT FOR LOWEST AMOUNT (E.g Try getLowest)";
					break;
				}
				//Calling the Method minFunds for getting the lowest Amount and returning the
				//result as a string
				result = "LOWEST AMOUNT CONTRIBUTED IS: " + db.minFunds();
			break;
			
			case "getremainder":
				//Validating making sure the client sends one more command
				if(tokens.length != 2)
				{
					result = "EXPECT 1 INPUT FOR REMINDER(Try getReminder 300000)";
					break;
				}
				String target = tokens[1];
				//Calling the Method reminder for getting the reminding  Amount and returning the
				//result as a string
				result = "TARGET AMOUNT IS <"+target+"> :" +  db.reminder(target);
			break;
			
			/*
			//Ends the connection or interaction
			case "bye":
				if(tokens.length >= 2)
				{
					//Validating making sure the client sends to only one command
					result = "EXPECT NOT INPUT FOR THE Bye (E.g Try bye)";
					break;
				}
				result = "Bye";
			break;
			*/
			
			default:
				//returns a string if the command is not either in the cases mentioned above.
				result = "COMMAND DOES NOT EXISTS";
			break;
		}//End of switch statement
		
		return result;// Returns the result as a string
	}//End of processInput

}//End of class FundraiserApp
