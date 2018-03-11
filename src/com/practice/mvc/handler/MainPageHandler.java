package com.practice.mvc.handler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.practice.mvc.beans.ProfessionalBean;
import com.practice.mvc.util.MvcUtility;

public class MainPageHandler extends BaseEventHandler{

	@Override
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String nextUrl = null;
		System.out.println("Password entered:: "+request.getParameter("userPassword"));
		String expDate = request.getParameter("expDate");
		String expCategory = request.getParameter("expCategory");
		String expSubCategory = request.getParameter("expSubCategory");
		String expAmount = request.getParameter("expAmount");
		
		getValues(request, response);
		insertExpense(request,response,expDate,expCategory,expSubCategory,expAmount);
				
		nextUrl = "success.jsp";
		
		return nextUrl;
	}
	
	private void insertExpense(HttpServletRequest request, HttpServletResponse response,String expDate,String expCategory, 
			String expSubCategory,String expAmount){
	
		System.out.println("inside insertExpense method");
		Statement stmt = null;
		int cat_id = 0;
		int subcat_id = 0;
		Connection con = MvcUtility.getConnection();
	    try{
		  stmt = con.createStatement();
	      String sql;
	      
	      sql = "SELECT category_id as cat_id from Category where category_name = '"+expCategory+"'";
	      ResultSet rs = stmt.executeQuery(sql);
	      while(rs.next()){
	    	  cat_id = rs.getInt("cat_id");
	    	  System.out.println("cat_id is: "+cat_id);
	      }
	      
	      sql = "SELECT subcategory_id as subcat_id from Subcategory where subcategory_name = '"+expSubCategory+"'";
	      ResultSet rs2 = stmt.executeQuery(sql);
	      while(rs2.next()){
	    	  subcat_id = rs2.getInt("subcat_id");
	    	  System.out.println("subcat_id is: "+subcat_id);
	      }
	      
	      int expAmt = Integer.parseInt(expAmount);
	      
	      sql = "INSERT INTO Expenses VALUES ('" + expDate +"',"+ cat_id + ",'"+ expCategory +"',"+ subcat_id+",'"+ expSubCategory +","+ expAmt +" )";
	      System.out.println("sql insert:"+sql);
	      stmt.executeUpdate(sql);
	      
	      System.out.println("end");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	private void getValues(HttpServletRequest request, HttpServletResponse response){
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

		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT prof_id, profname, profemail FROM professional";
		      ResultSet rs = stmt.executeQuery(sql);
		      
		      ArrayList<ProfessionalBean> list = new ArrayList<ProfessionalBean>();
		      
		      while(rs.next()){

		         System.out.println("ID: " + rs.getInt("prof_id"));
		         System.out.println("Name: " + rs.getString("profname"));
		         System.out.println("Email: " + rs.getString("profemail"));
		      
		         ProfessionalBean profBean = new ProfessionalBean();
		         
		         profBean.setProfId(rs.getInt("prof_id"));
		         profBean.setProfName(rs.getString("profname"));
		         profBean.setProfEmail(rs.getString("profemail"));
		         
		         list.add(profBean);
		      }
		      System.out.println("List Size:: "+list.size());
		      request.setAttribute("profbeanlist", list);
		   }catch(Exception sqle){
			   sqle.printStackTrace();
		   }
	}

}
