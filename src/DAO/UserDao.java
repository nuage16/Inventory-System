package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Beans.Inventory;
import Beans.Sales;
import Beans.User;
import Home.DBConnection;

public class UserDao {
	
	//VALIDATE PASSWORD DURING LOGIN
	 public static boolean validate(String Password) {        
	       Connection con = null;
		 boolean status = false;
	     PreparedStatement pst = null;
	     ResultSet rs = null;
	        
	        try {
	        	DBConnection db = new DBConnection();
		        con = db.getConnection(); 

	            pst = con
	                    .prepareStatement("select * from user where Password= ?");
	            pst.setString(1, Password);

	            rs = pst.executeQuery();
	            status = rs.next();

	        } catch (Exception e) {
	            System.out.println(e);
	        } finally {
	            if (con != null) {
	                try {
	                    con.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (pst != null) {
	                try {
	                    pst.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (rs != null) {
	                try {
	                    rs.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return status;
	    }
	 
	 //FOR SETTING A NEW PASSWORD
	 public static int updatePassword(User u){  
		    int status=0;  
		    try{  
		    	DBConnection db = new DBConnection();
		        Connection con= db.getConnection(); 
		        PreparedStatement ps=null;
		        
		        ps=con.prepareStatement("UPDATE user SET Password=? WHERE ID=?");  
	 	
		        ps.setString(1,u.getPassw());  
		        ps.setInt(2,1);	

		        status=ps.executeUpdate();  
		        
		        System.out.println("\nPassword Updated!");
		        ps.close();
				con.close();
		        
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
		}  

	 
}