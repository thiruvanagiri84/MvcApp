package com.practice.mvc.util;

import java.sql.*;
import java.util.ArrayList;

import com.practice.mvc.beans.ProfessionalBean;

public class MvcUtility {
	public static Connection getConnection(){

		 // JDBC driver name and database URL
	    final String DB_URL = "jdbc:derby://localhost:1527/sample;create=true";
	     //  Database credentials
	    String USER = "user";
	    String PASS = "app";
	   
	   Connection conn = null;
	   Statement stmt = null;
	   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("org.apache.derby.jdbc.ClientDriver");

		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      
		   }catch(Exception sqle){
			   sqle.printStackTrace();
		   }
	   return conn;
	}
}

