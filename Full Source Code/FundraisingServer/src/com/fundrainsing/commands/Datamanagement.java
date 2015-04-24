 /*
AMPAIRE CHRISTINE    210008949 10/U/9183/PS
ABOK ISAAC 210007924 10/U/9168/PS
OTIM FREDRICK 212022189 12/U/13682/PS
*/

package com.fundrainsing.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abok
 */
public class Datamanagement 
{
    private final String connectionClass = "com.mysql.jdbc.Driver";//Mysql connector driver
    private final String databaseName = "fundraising";//Database Name
    private final String databaseServer = "localhost";//Server Name
    private final String databaseUser = "cmsuser";//Database username
    private final String databasePassword = "cmspass";//Database password
    //initial connection before the database is created
    private final String initialConnection = "jdbc:mysql://"+ databaseServer+":3306/mysql";
    //second connection to enable the creation tables and perform neccessay interaction
    //after the database is created
    private final String connectionString = "jdbc:mysql://"+databaseServer+":3306/"+databaseName;
    Connection con;
    Statement stmt = null;
    ResultSet rs;
    private boolean exists;
    private String nn;
            
    //Method to carry out initial database connection
    public void OpenInitialDatabaseConnection()
    {
        try 
        {
          Class.forName(connectionClass);
          con = (Connection)DriverManager.getConnection(initialConnection,databaseUser,databasePassword);
          stmt = con.createStatement();
        } catch (Exception e)
        {
            e.printStackTrace();
        } 
    }
    //Method to open the connection for all queries
    public void OpenDatabaseConnection()
    {
        try 
        {
            Class.forName(connectionClass);
            con = DriverManager.getConnection(connectionString,databaseUser,databasePassword);
            stmt = con.createStatement();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //Method to close the databaseConnection
    public void CloseDatabaseConnection()
    {
        try 
        {
            con.close();
            stmt.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //Method to create the fundarising database
    public void CreateDatabase()
    {
        try 
        {
            String createDatabase = "create database if not exists " + databaseName ;
            OpenInitialDatabaseConnection();
            stmt.executeUpdate(createDatabase);
            //System.out.println("Database ".toUpperCase() + databaseName + " created".toUpperCase());
        } catch (Exception e) 
        {
            e.printStackTrace();
        }finally
        {
            CloseDatabaseConnection();
        }
    }
    //creates collections table
  public void createFundraisingTable()
  {
    try
     {
      String create_table_sql = "CREATE TABLE if not exists `collections` ("
                          +"`names` varchar(25) NOT NULL,"
                          +"`dateandtime` varchar(32) NOT NULL,"
                          +"`amount` int(15) NOT NULL)";
      OpenDatabaseConnection();
      stmt.executeUpdate(create_table_sql);
      //System.out.println("collections".toUpperCase());
      return;
     }
    catch (Exception e)
     {
      System.out.println(e.getMessage());
      return;
     }
    finally
     {
      CloseDatabaseConnection();
     }
  }
  
  
  //method to store the pledge details
  public void  addFund(String names, String dateandtime, int amount){
      
      try {
          OpenDatabaseConnection();
            java.sql.PreparedStatement st = con.prepareStatement(""
            		+ "INSERT INTO collections"
            		+ "( `names`, `dateandtime`, `amount`)"
                    + "VALUES (?, ?, ?)");
            //insert data
            st.setString(1, names);st.setString(2, dateandtime);st.setInt(3, amount);
            st.execute(); 
            System.out.println("Fundraising added".toUpperCase());
      } catch (Exception ex) {
          Logger.getLogger(Datamanagement.class.getName()).log(Level.SEVERE,null,ex);
      }finally{
          CloseDatabaseConnection();
      }
  }
  
  //Method to search in the database
  public String searchRecords( String searchName)
  {
	  String out = "";
	  try 
	  {
		  String getfunder = "select * from collections";
          OpenDatabaseConnection();
          stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(getfunder);
          String result = "USER DOESNOT EXIST IN OUR DATABASE";
          while (rs.next()) 
          {
			if (searchName.equalsIgnoreCase(rs.getString("names")))
			{
//				result  = rs.getString("names") +  " Amount " + rs.getString("amount");
				result  = rs.getString("amount") + "/=";
			} 
          }
          //System.out.println("Search result: " + result+"/=");
          System.out.println("Success".toUpperCase());
          out = result;
	  } catch (Exception e) 
	  {
		e.printStackTrace();
	}
	  return out;
  }
  
  //Method to get total fundraising 
  public int totalFunds() 
  {
	  int total=0;
	  try 
	  {
		String getSum = "select SUM(amount) as sum from collections";
		OpenDatabaseConnection();
		 stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(getSum);
         
         while (rs.next()) 
         {
        	 total = rs.getInt("sum");
         }
         //System.out.println("The total is : " + total+ "/=");
         System.out.println("Success".toUpperCase());
	} catch (Exception e)
	  {
		e.printStackTrace();
	}finally
	{
		CloseDatabaseConnection();
	}
	  
	  return total;
  }
  
  
//Method to get minimum fundraising amount
  public int minFunds() 
  {
	  int minimum=0;
	  try 
	  {
		String getMin = "select MIN(amount) as minAmount from collections";
		OpenDatabaseConnection();
		 stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(getMin);
         
         while (rs.next()) 
         {
        	 minimum = rs.getInt("minAmount");
         }
         //System.out.println("The min is : " + min+ "/=");
         System.out.println("Success".toUpperCase());
	} catch (Exception e)
	  {
		e.printStackTrace();
	}finally
	{
		CloseDatabaseConnection();
	}
	  
	  return minimum;
  }
  
//Method to get maximum fundraising amount
  public int maxFunds() 
  {
	  int maximum=0;
	  try 
	  {
		String getMax = "select MAX(amount) as maxAmount from collections";
		OpenDatabaseConnection();
		 stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(getMax);
         
         while (rs.next()) 
         {
        	 maximum = rs.getInt("maxAmount");
         }
         //System.out.println("The Max is : " + max+ "/=");
         System.out.println("Success".toUpperCase());
	} catch (Exception e)
	  {
		e.printStackTrace();
	}finally
	{
		CloseDatabaseConnection();
	}
	  
	  return maximum;
  }
  
  //Method to get reminder amount fundraising 
  public String reminder(String targetAmount) 
  {
	  int total=totalFunds();
	  int target = Integer.parseInt(targetAmount);
	  int rem= target- total;
	  String balance = null;
	  
	  if (total > target){
		  int bonus = total - target;
		  String BonusAmount = Integer.toString(bonus);
		  balance = "TARGET MET WITH BONUS AMOUNT OF: " + BonusAmount+"/=";
	  }else{
		  String remainders = Integer.toString(rem);
		  balance = "TARGET NOT MET BALANCE IS: " + remainders +"/=" ;
	  }
	  //System.out.println("The Reminding Balance is : " + rem+ "/=");
	  System.out.println("Success".toUpperCase());
	  return balance;
  }
  
//Method to generate a report 
  public  void report() 
  {
	  List data = new ArrayList();
	  try 
	  {
		String allFundraisers = "select * from collections";
		OpenDatabaseConnection();
		 stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(allFundraisers);
         
         while (rs.next()) 
         {
        	 String names = rs.getString("names");
             String dateandtime = rs.getString("dateandtime");
             String amount = rs.getString("amount");
             data.add("Names: " +names + " Time Contributed: " + dateandtime + " Amount: " + amount);
         }

         writeToFile(data, "report.txt");
         System.out.println("Report created".toUpperCase());
         rs.close();
         con.close();
	} catch (Exception e)
	  {
		e.printStackTrace();
	}finally
	{
		CloseDatabaseConnection();
	}
  }
  
  //methods that writes to the TEXT FILE
 private static void writeToFile(java.util.List list, String path) {
      BufferedWriter out = null;
      try {
              File file = new File(path);
              out = new BufferedWriter(new FileWriter(file, true));
              for (Object s : list) {
            	  	out.write((String) s) ;
                    out.newLine();
                    out.newLine();

              }
              out.close();
      } catch (IOException e) {
      }
 }
  
}
